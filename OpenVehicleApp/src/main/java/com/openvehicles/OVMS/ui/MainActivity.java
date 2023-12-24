package com.openvehicles.OVMS.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.google.android.gms.maps.model.LatLng;
import com.openvehicles.OVMS.luttu.AppPrefes;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.receiver.RegistrationIntentService;
import com.openvehicles.OVMS.ui.FragMap.UpdateLocation;
import com.openvehicles.OVMS.ui.GetMapDetails.GetMapDetailsListener;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.utils.ConnectionList;
import com.openvehicles.OVMS.utils.ConnectionList.ConnectionsListener;
import com.openvehicles.OVMS.utils.Sys;


public class MainActivity extends ApiActivity implements
		ActionBar.OnNavigationListener,
		GetMapDetailsListener,
		ConnectionsListener,
		UpdateLocation {

	private static final String TAG = "MainActivity";

	public String versionName = "";
	public int versionCode = 0;

	AppPrefes appPrefes;
	Database database;
	public String uuid;

	private ViewPager mViewPager;
	private MainPagerAdapter mPagerAdapter;
	public static UpdateLocation updateLocation;
	public GetMapDetails getMapDetails;
	public List<LatLng> getMapDetailList;
	public long getMapDetailsBlockUntil = 0;
	public long getMapDetailsBlockSeconds = 0;


	/**
	 * App lifecycle management:
	 *
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

		appPrefes = new AppPrefes(this, "ovms");
		database = new Database(this);

		// get/create App UUID:
		uuid = appPrefes.getData("UUID");
		if (uuid.length() == 0) {
			uuid = UUID.randomUUID().toString();
			appPrefes.SaveData("UUID", uuid);
			Log.d(TAG, "onCreate: generated new UUID: " + uuid);
		} else {
			Log.d(TAG, "onCreate: using UUID: " + uuid);
		}

		// Check/create API key:
		String apiKey = appPrefes.getData("APIKey");
		if (apiKey.length() == 0) {
			apiKey = Sys.getRandomString(25);
			appPrefes.SaveData("APIKey", apiKey);
			Log.d(TAG, "onCreate: generated new APIKey: " + apiKey);
		} else {
			Log.d(TAG, "onCreate: using APIKey: " + apiKey);
		}

		// OCM init:
		getMapDetails = null;
		getMapDetailList = new ArrayList<LatLng>();
		updateLocation = this;
		updatelocation();
		// update connection list if OCM is enabled:
		if (appPrefes.getData("option_ocm_enabled", "1").equals("1")) {
			new ConnectionList(this,this,true);
		}

		// Start background ApiService:
		Log.i(TAG, "onCreate: starting ApiService");
		try {
			startService(new Intent(this, ApiService.class));
		} catch (Exception e) {
			Log.w(TAG, "onCreate: starting ApiService failed: " + e);
		}

		// set up receiver for server communication service:
		registerReceiver(mApiEventReceiver, new IntentFilter(ApiService.ACTION_APIEVENT));

		// set up receiver for notifications:
		registerReceiver(mNotificationReceiver, new IntentFilter(ApiService.ACTION_NOTIFICATION));

		// init UI tabs:
		mViewPager = new ViewPager(this);
		mViewPager.setId(android.R.id.tabhost);
		setContentView(mViewPager);

		// check for update, Google Play Services & permissions:
		checkVersion();

		// configure ActionBar:
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);

		// Progress bar init:
		ProgressBar progressBar = new ProgressBar(this);
		progressBar.setVisibility(View.GONE);
		progressBar.setIndeterminate(true);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(progressBar);

		mPagerAdapter = new MainPagerAdapter(
				new TabInfo(R.string.Messages, R.drawable.ic_action_email, NotificationsFragment.class),
				new TabInfo(R.string.Battery, R.drawable.ic_action_battery, InfoFragment.class),
				new TabInfo(R.string.Car, R.drawable.ic_action_car, CarFragment.class),
				new TabInfo(R.string.Location, R.drawable.ic_action_location_map, FragMap.class),
				new TabInfo(R.string.Settings, R.drawable.ic_action_settings, SettingsFragment.class)
		);

		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount()-1);
		mViewPager.addOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);

						// cancel system notifications on page "Messages":
						if (mPagerAdapter.getItemId(position) == R.string.Messages) {
							NotificationManager mNotificationManager = (NotificationManager)
									getSystemService(Context.NOTIFICATION_SERVICE);
							mNotificationManager.cancelAll();
						}
					}
				});

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(
				new NavAdapter(this, mPagerAdapter.getTabInfoItems()), this);

		// start on battery tab:
		onNavigationItemSelected(mPagerAdapter.getPosition(R.string.Battery), 0);

		// process Activity startup intent:
		onNewIntent(getIntent());
	}

	@Override
	public void onNewIntent(Intent newIntent) {
		if (newIntent == null)
			return;
		Log.d(TAG, "onNewIntent: " + newIntent.toString());

		super.onNewIntent(newIntent);

		// if launched from notification, switch to messages tab:
		if (newIntent.getBooleanExtra("onNotification", false)) {
			onNavigationItemSelected(mPagerAdapter.getPosition(R.string.Messages), 0);
		}
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		unregisterReceiver(mApiEventReceiver);
		unregisterReceiver(mNotificationReceiver);
		database.close();

		// Stop background ApiService?
		boolean serviceEnabled = appPrefes.getData("option_service_enabled", "0").equals("1");
		if (!serviceEnabled) {
			Log.i(TAG, "onDestroy: stopping ApiService");
			stopService(new Intent(this, ApiService.class));
		}

		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		LocalBroadcastManager.getInstance(this).registerReceiver(mGcmRegistrationBroadcastReceiver,
				new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
		onNewIntent(getIntent());
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mGcmRegistrationBroadcastReceiver);
		super.onPause();
	}


	@Override
	public void setSupportProgressBarIndeterminateVisibility(boolean visible) {
		getSupportActionBar().getCustomView().setVisibility(visible ? View.VISIBLE : View.GONE);
	}


	/**
	 * Check for update, show changes info
	 */
	private void checkVersion() {
		try {
			// get App version
			PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			versionName = pinfo.versionName;
			versionCode = pinfo.versionCode;

			if (!appPrefes.getData("lastUsedVersionName", "").equals(versionName)) {
				showVersion();
			} else {
				checkPlayServices();
			}

		} catch (PackageManager.NameNotFoundException e) {
			// ignore
			checkPlayServices();
		}
	}

	public void showVersion() {
		TextView msg = new TextView(this);
		final float scale = getResources().getDisplayMetrics().density;
		final int pad = (int) (25 * scale + 0.5f);
		msg.setPadding(pad, pad, pad, pad);
		msg.setText(Html.fromHtml(getString(R.string.about_message)));
		msg.setMovementMethod(LinkMovementMethod.getInstance());
		msg.setClickable(true);

		AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
				.setTitle(getString(R.string.about_title, versionName, versionCode))
				.setView(msg)
				.setPositiveButton(R.string.msg_ok, (dialog1, which) ->
						appPrefes.SaveData("lastUsedVersionName", versionName))
				.setOnDismissListener(dialog12 -> checkPlayServices())
				.show();
	}


	/**
	 * Check the device for Google Play Services, tell user if missing.
	 */
	private void checkPlayServices() {

		if (appPrefes.getData("skipPlayServicesCheck", "0").equals("1"))
			return;

		GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
		int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {

			AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
					.setTitle(R.string.common_google_play_services_install_title)
					.setMessage(R.string.play_services_recommended)
					.setPositiveButton(R.string.remind, null)
					.setNegativeButton(R.string.dontremind, (dialog1, which) ->
							appPrefes.SaveData("skipPlayServicesCheck", "1"))
					.setOnDismissListener(dialog12 -> checkPermissions())
					.show();
		} else {
			checkPermissions();
		}
	}

	/**
	 * Check / request permissions
	 */
	private void checkPermissions() {
		ArrayList<String> permissions = new ArrayList<>(2);
		boolean show_rationale = false;

		// ACCESS_FINE_LOCATION: needed for the "My location" map button
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED) {
			permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
			show_rationale = ActivityCompat.shouldShowRequestPermissionRationale(
					this, Manifest.permission.ACCESS_FINE_LOCATION);
		}

		// POST_NOTIFICATIONS: needed on Android >= 13 to create notifications
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU &&
				ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
						!= PackageManager.PERMISSION_GRANTED) {
			permissions.add(Manifest.permission.POST_NOTIFICATIONS);
			show_rationale |= ActivityCompat.shouldShowRequestPermissionRationale(
					this, Manifest.permission.POST_NOTIFICATIONS);
		}

		if (!permissions.isEmpty()) {
			String[] permArray = new String[permissions.size()];
			permissions.toArray(permArray);
			if (show_rationale) {
				AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
						.setTitle(R.string.needed_permissions_title)
						.setMessage(R.string.needed_permissions_message)
						.setNegativeButton(R.string.later, null)
						.setPositiveButton(R.string.msg_ok, (dialog1, which) -> {
							ActivityCompat.requestPermissions(MainActivity.this, permArray, 0);
						})
						.show();
			} else {
				ActivityCompat.requestPermissions(MainActivity.this, permArray, 0);
			}
		}
	}

	/**
	 * GCM push notification registration:
	 * 	- server login => gcmStartRegistration
	 * 	- gcmStartRegistration invokes RegistrationIntentService for selected car
	 * 	- RegistrationIntentService invokes mGcmRegistrationBroadcastReceiver on success
	 * 	- mGcmRegistrationBroadcastReceiver starts GcmDoSubscribe runnable
	 * 	- GcmDoSubscribe does push subscription (retries if necessary)
	 */

	private void gcmStartRegistration() {

		ApiService service = getService();
		if (service == null)
			return;
		CarData pCarData = service.getCarData();
		if (pCarData == null)
			return;

		// get GCM sender ID for car:
		String gcmSenderId;
		if (pCarData.sel_gcm_senderid != null && pCarData.sel_gcm_senderid.length() > 0)
			gcmSenderId = pCarData.sel_gcm_senderid;
		else
			gcmSenderId = getString(R.string.gcm_defaultSenderId);

		Log.d(TAG, "starting RegistrationIntentService for vehicleId=" + pCarData.sel_vehicleid
				+ ", gcmSenderId=" + gcmSenderId);

		// start GCM registration service:
		Intent intent = new Intent(MainActivity.this, RegistrationIntentService.class);
		intent.putExtra("ovmsVehicleId", pCarData.sel_vehicleid);
		intent.putExtra("ovmsGcmSenderId", gcmSenderId);

		try {
			startService(intent);
		} catch (Exception e) {
			Log.w(TAG, "starting RegistrationIntentService failed: " + e);
		}
	}

	private BroadcastReceiver mGcmRegistrationBroadcastReceiver = new BroadcastReceiver() {
		private static final String TAG = "mGcmRegReceiver";
		@Override
		public void onReceive(Context context, Intent intent) {

			Log.d(TAG, "onReceive intent: " + intent.toString());

			String vehicleId = intent.getStringExtra("ovmsVehicleId");
			if (vehicleId != null) {
				Log.d(TAG, "subscribing vehicleId=" + vehicleId);
				mGcmHandler.post(new GcmDoSubscribe(vehicleId));
			}
		}
	};

	private final Handler mGcmHandler = new Handler(Looper.getMainLooper());

	private class GcmDoSubscribe implements Runnable {
		private static final String TAG = "GcmDoSubscribe";
		private String vehicleId;

		public GcmDoSubscribe(String vehicleId) {
			this.vehicleId = vehicleId;
		}

		@Override
		public void run() {

			Log.d(TAG, "trying to subscribe vehicleId " + vehicleId);

			ApiService service = getService();

			if (service == null) {
				Log.d(TAG, "ApiService terminated, cancelling");
				return;
			} else if (!service.isLoggedIn()) {
				Log.d(TAG, "ApiService not yet logged in, scheduling retry");
				mGcmHandler.postDelayed(this, 5000);
				return;
			}

			CarData carData = service.getCarData();
			if (carData == null || !carData.sel_vehicleid.equals(vehicleId)) {
				Log.d(TAG, "ApiService logged in to vehicleid " + carData.sel_vehicleid + " now, cancelling");
				return;
			}

			SharedPreferences settings = getSharedPreferences("GCM." + vehicleId, 0);
			String gcmToken = settings.getString(RegistrationIntentService.GCM_TOKEN, "");
			Log.d(TAG, "login OK, subscribing vehicleId " + vehicleId + " on gcmToken " + gcmToken);

			// subscribe at OVMS server:
			// MP-0
			// p<appid>,<pushtype>,<pushkeytype>{,<vehicleid>,<netpass>,<pushkeyvalue>}
			String cmd = String.format("MP-0 p%s,gcm,production,%s,%s,%s",
					uuid, carData.sel_vehicleid, carData.sel_server_password,
					gcmToken);
			if (!service.sendCommand(cmd, null)) {
				Log.w(TAG, "push subscription failed, scheduling retry");
				mGcmHandler.postDelayed(this, 5000);
			} else {
				Log.d(TAG, "push subscription done.");
			}

		}
	}


	/**
	 * ApiService / OVMS server communication:
	 *
	 */

	private AlertDialog mApiErrorDialog;
	private String mApiErrorMessage;

	private final BroadcastReceiver mApiEventReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String event = intent.getStringExtra("event");
			Log.v(TAG, "mApiEventReceiver: event=" + event);

			if ("LoginBegin".equals(event)) {
				Log.d(TAG, "mApiEventReceiver: login process started");

				// show progress indicator:
				setSupportProgressBarIndeterminateVisibility(true);
			}

			else if ("LoginComplete".equals(event)) {
				Log.d(TAG, "mApiEventReceiver: login successful");

				// hide progress indicator:
				setSupportProgressBarIndeterminateVisibility(false);

				// ...and hide error dialog:
				if (mApiErrorDialog != null && mApiErrorDialog.isShowing()) {
					mApiErrorDialog.hide();
				}

				// schedule GCM registration:
				gcmStartRegistration();
			}

			else if ("ServerSocketError".equals(event)) {
				Log.d(TAG, "mApiEventReceiver: server/login error");

				// hide progress indicator:
				setSupportProgressBarIndeterminateVisibility(false);

				// check if this message needs to be displayed:
				String message = intent.getStringExtra("message");
				if (message == null) return;
				if (message.equals(mApiErrorMessage) && mApiErrorDialog != null && mApiErrorDialog.isShowing()) {
					return;
				}

				// display message:
				if (mApiErrorDialog != null) {
					mApiErrorDialog.dismiss();
				}
				mApiErrorMessage = message;
				mApiErrorDialog = new AlertDialog.Builder(MainActivity.this)
						.setTitle(R.string.Error)
						.setMessage(mApiErrorMessage)
						.setPositiveButton(android.R.string.ok, null)
						.show();
			}
		}
	};


	/**
	 * User interface handling:
	 *
	 */

	private class TabInfo {
		public final int title_res_id, icon_res_id;
		public final Class<? extends BaseFragment> fragment_class;
		public Fragment fragment;

		public String getFragmentName() {
			return fragment_class.getName();
		}

		public TabInfo(int pTitleResId, int pIconResId,
				Class<? extends BaseFragment> pFragmentClass) {
			title_res_id = pTitleResId;
			icon_res_id = pIconResId;
			fragment_class = pFragmentClass;
			fragment = null;
		}

		@Override
		public String toString() {
			return getString(title_res_id);
		}
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if (itemPosition < 0 || itemPosition >= mPagerAdapter.getCount())
			return false;
		TabInfo ti = mPagerAdapter.getTabInfoItems()[itemPosition];
		getSupportActionBar().setIcon(ti.icon_res_id);
		mViewPager.setCurrentItem(itemPosition, false);
		return true;
	}

	private static class NavAdapter extends ArrayAdapter<TabInfo> {
		public NavAdapter(Context context, TabInfo[] objects) {
			super(context, android.R.layout.simple_spinner_item, objects);
			setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			TextView tv = (TextView) super.getDropDownView(position,
					convertView, parent);
			tv.setCompoundDrawablesWithIntrinsicBounds(
					getItem(position).icon_res_id, 0, 0, 0);
			return tv;
		}
	}

	private class MainPagerAdapter extends FragmentPagerAdapter {
		private final TabInfo[] mTabInfoItems;

		public MainPagerAdapter(TabInfo... pTabInfoItems) {
			super(getSupportFragmentManager());
			mTabInfoItems = pTabInfoItems;
		}

		public TabInfo[] getTabInfoItems() {
			return mTabInfoItems;
		}

		@Override
		public Fragment getItem(int pPosition) {
			if (pPosition < 0 || pPosition >= mTabInfoItems.length)
				return null;
			if (mTabInfoItems[pPosition].fragment == null) {
				// instantiate fragment:
				mTabInfoItems[pPosition].fragment = Fragment.instantiate(
						MainActivity.this,
						mTabInfoItems[pPosition].getFragmentName());
			}
			Log.d(TAG, "MainPagerAdapter: pos=" + pPosition + " => frg=" + mTabInfoItems[pPosition].fragment);
			return mTabInfoItems[pPosition].fragment;
		}

		public Fragment getItemByTitle(int pTitle) {
			return getItem(getPosition(pTitle));
		}

		@Override
		public int getCount() {
			return mTabInfoItems.length;
		}

		@Override
		public long getItemId(int position) {
			// use Title String resource id as Item id:
			return mTabInfoItems[position].title_res_id;
		}

		public int getPosition(int itemId) {
			for (int p = 0; p < mTabInfoItems.length; p++) {
				if (mTabInfoItems[p].title_res_id == itemId)
					return p;
			}
			return -1;
		}

		@Override
		public CharSequence getPageTitle(int pPosition) {
			return getString(mTabInfoItems[pPosition].title_res_id);
		}
	}


	@Override
	public void onConnectionChanged(String al, String name) {
		// TODO Auto-generated method stub

	}


	// Make notification updates visible immediately:
	private final BroadcastReceiver mNotificationReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "mNotificationReceiver: received " + intent.toString());

			// update messages list:
			NotificationsFragment frg = (NotificationsFragment) mPagerAdapter.getItemByTitle(R.string.Messages);
			if (frg != null) {
				Log.d(TAG, "mNotificationReceiver: update notifications fragment");
				frg.update();
				if (intent.getBooleanExtra("onNotification", false)) {
					Log.d(TAG, "mNotificationReceiver: switch to notifications fragment");
					BaseFragmentActivity.finishCurrent();
					onNavigationItemSelected(mPagerAdapter.getPosition(R.string.Messages), 0);
				}
			}
		}
	};



	@Override
	public void updatelocation() {

		// get car location:
		String lat = "37.410866";
		String lng = "-122.001946";

		if (appPrefes.getData("lat_main").equals("")) {
			// init car position:
			appPrefes.SaveData("lat_main", lat);
			appPrefes.SaveData("lng_main", lng);
			Log.i(TAG, "updatelocation: init car position");
		} else {
			// get current car position:
			lat = appPrefes.getData("lat_main");
			lng = appPrefes.getData("lng_main");
		}

		// Start OpenChargeMap task:
		try {
			double dlat = Double.parseDouble(lat), dlng = Double.parseDouble(lng);
			StartGetMapDetails(new LatLng(dlat, dlng));
		} catch (Exception e) {
			// ignore
		}
	}

	public boolean isMapCacheValid(LatLng center) {
		// As OCM does not yet support incremental queries,
		// we're using a cache with key = int(lat/lng)
		// resulting in a tile size of max. 112 x 112 km
		// = diagonal max 159 km
		// The API call will fetch a fixed radius of 160 km
		// covering all adjacent tiles.

		// check OCM cache for key int(lat/lng):

		int latitude = (int) center.latitude;
		int longitude = (int) center.longitude;

		Cursor cursor = database.getlatlngdetail(latitude, longitude);
		int colLastUpdate = cursor.getColumnIndex("last_update");
		if (cursor.getCount() == 0) {
			cursor.close();
			return false;
		}
		else if (cursor.moveToFirst()) {
			// check if last tile update was more than 24 hours ago:
			long last_update = (colLastUpdate >= 0) ? cursor.getLong(colLastUpdate) : 0;
			long now = System.currentTimeMillis() / 1000;
			if (now > last_update + (3600 * 24)) {
				cursor.close();
				return false;
			}
		}

		Log.d(TAG, "isMapCacheValid: cache valid for lat/lng=" + latitude + "/" + longitude);
		cursor.close();
		return true;
	}

	public void StartGetMapDetails(LatLng center) {
		if (!isMapCacheValid(center)) {
			getMapDetailList.add(center);
			StartGetMapDetails();
		} else {
			Log.d(TAG, "StartGetMapDetails: map cache valid for center=" + center);
		}
	}

	public void StartGetMapDetails() {
		// check if task is still running:
		if (getMapDetails != null && getMapDetails.getStatus() != AsyncTask.Status.FINISHED) {
			return;
		}
		// check if error block period is active:
		if (System.currentTimeMillis() < getMapDetailsBlockUntil) {
			return;
		}
		// check if OCM has been disabled:
		if (appPrefes.getData("option_ocm_enabled", "1").equals("0")) {
			return;
		}

		do {
			// get next coordinates to fetch:
			if (getMapDetailList.isEmpty()) {
				Log.i(TAG, "StartGetMapDetails: done.");
				return;
			}

			LatLng center = getMapDetailList.remove(0);
			if (isMapCacheValid(center)) {
				continue;
			}

			// create new fetcher task:
			Log.i(TAG, "StartGetMapDetails: starting task for " + center);
			getMapDetails = new GetMapDetails(MainActivity.this, center, this);
			getMapDetails.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			return;

		} while(true);
	}

	private final Handler mGetMapDetailsHandler = new Handler(Looper.getMainLooper());

	@Override
	public void getMapDetailsDone(boolean success, LatLng center) {
		if (success) {
			// mark cache tile valid:
			Log.i(TAG, "getMapDetailsDone: OCM updates received for " + center);
			database.addlatlngdetail((int)center.latitude, (int)center.longitude);
			// update map:
			FragMap frg = (FragMap) mPagerAdapter.getItemByTitle(R.string.Location);
			if (frg != null) {
				Log.d(TAG, "getMapDetailsDone: OCM updates received => calling FragMap.update()");
				frg.update();
			}
			// clear blocking:
			getMapDetailsBlockUntil = 0;
			getMapDetailsBlockSeconds = 0;
			// check for next fetch job:
			StartGetMapDetails();
		} else {
			Log.e(TAG, "getMapDetailsDone: OCM updates failed for " + center);
			// increase blocking time up to 120 seconds:
			if (getMapDetailsBlockSeconds < 120) {
				getMapDetailsBlockSeconds += 10;
			}
			Log.d(TAG, "getMapDetailsDone: blocking OCM API calls for " + getMapDetailsBlockSeconds + " seconds");
			getMapDetailsBlockUntil = System.currentTimeMillis() + (1000 * getMapDetailsBlockSeconds);
			// schedule retry:
			if (getMapDetailsBlockSeconds < 120) {
				mGetMapDetailsHandler.postDelayed(() -> StartGetMapDetails(center), 1000 * getMapDetailsBlockSeconds);
			} else {
				Log.w(TAG, "getMapDetailsDone: maximum block time reached, no further retries");
				// Note: retry blocking will be resolved by the next regular fetch from a vehicle movement
			}
		}
	}

}
