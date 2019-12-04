package com.openvehicles.OVMS.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;

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
import com.luttu.AppPrefes;

import com.luttu.Main;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiObservable;
import com.openvehicles.OVMS.api.ApiObserver;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.receiver.RegistrationIntentService;
import com.openvehicles.OVMS.ui.FragMap.UpdateLocation;
import com.openvehicles.OVMS.ui.GetMapDetails.GetMapDetailsListener;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.utils.ConnectionList;
import com.openvehicles.OVMS.utils.ConnectionList.ConnectionsListener;

import org.json.JSONObject;


public class MainActivity extends ApiActivity implements
		ActionBar.OnNavigationListener, GetMapDetailsListener, ConnectionsListener, UpdateLocation {

	private static final String TAG = "MainActivity";

	public static final int REQUEST_LOCATION = 1;

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
			Log.d(TAG, "generated new UUID: " + uuid);
		} else {
			Log.d(TAG, "using UUID: " + uuid);
		}

		// OCM init:
		getMapDetails = null;
		getMapDetailList = new ArrayList<LatLng>();
		updateLocation = this;
		updatelocation();
		ConnectionList connectionList = new ConnectionList(this,this,true);

		// Start background ApiService:
		Log.i(TAG, "Starting ApiService");
		startService(new Intent(this, ApiService.class));

		// set up receiver for server communication service:
		registerReceiver(mApiEventReceiver, new IntentFilter(
				getPackageName() + ".ApiEvent"));

		// set up receiver for notifications:
		Log.d(TAG, "Notifications registering receiver for Intent: " + getPackageName() + ".Notification");
		registerReceiver(mNotificationReceiver, new IntentFilter(
				getPackageName() + ".Notification"));

		// init UI tabs:
		mViewPager = new ViewPager(this);
		mViewPager.setId(android.R.id.tabhost);
		setContentView(mViewPager);

		// check for update:
		checkVersion();

		// check for Google Play Services:
		checkPlayServices();

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
			Log.i(TAG, "Stopping ApiService");
			stopService(new Intent(this, ApiService.class));
		}

		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");


		//ApiService apiService = getService();


		LocalBroadcastManager.getInstance(this).registerReceiver(mGcmRegistrationBroadcastReceiver,
				new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
	}

	@Override
	protected void onPause() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mGcmRegistrationBroadcastReceiver);
		Log.d(TAG, "onPause");
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

			if (appPrefes.getData("lastUsedVersionName", "").equals(versionName))
				return;

			showVersion();

		} catch (PackageManager.NameNotFoundException e) {
			// ignore
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
				.setPositiveButton(R.string.msg_ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						appPrefes.SaveData("lastUsedVersionName", versionName);
					}
				})
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
					.setNegativeButton(R.string.dontremind, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							appPrefes.SaveData("skipPlayServicesCheck", "1");
						}
					})
					.show();
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

	private void gcmStartRegistration(CarData pCarData) {

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
		startService(intent);
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

	private final Handler mGcmHandler = new Handler();

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

			if (intent.getSerializableExtra("onServerSocketError") != null) {
				Log.d(TAG, "mApiEventReceiver: onServerSocketError");

				setSupportProgressBarIndeterminateVisibility(false);

				// check if this message needs to be displayed:
				String message = intent.getStringExtra("message");
				if (mApiErrorDialog == null || (mApiErrorDialog != null &&
						(!mApiErrorDialog.isShowing()
								|| !message.equals(mApiErrorMessage)))) {

					mApiErrorDialog = new AlertDialog.Builder(MainActivity.this)
							.setTitle(R.string.Error)
							.setMessage(message)
							.setPositiveButton(android.R.string.ok, null)
							.show();

					mApiErrorMessage = message;
				}
			}

			if (intent.getBooleanExtra("onLoginBegin", false)) {
				Log.d(TAG, "mApiEventReceiver: login process started");

				setSupportProgressBarIndeterminateVisibility(true);

				// observe for login success:
				ApiObservable.get().addObserver(mApiObserver);
			}

		}
	};

	private ApiObserver mApiObserver = new ApiObserver() {
		@Override
		public void update(CarData pCarData) {
			Log.d(TAG, "mApiObserver: login successful");

			// data update implies login successful => hide progress:
			setSupportProgressBarIndeterminateVisibility(false);

			// ...and hide error dialog:
			if (mApiErrorDialog != null && mApiErrorDialog.isShowing()) {
				mApiErrorDialog.hide();
			}

			// schedule GCM registration:
			gcmStartRegistration(pCarData);

			// done waiting for login:
			ApiObservable.get().deleteObserver(this);
		}

		@Override
		public void onServiceAvailable(ApiService pService) {
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
			Log.d(TAG, "Notifications: received " + intent.toString());

			// update messages list:
			NotificationsFragment frg = (NotificationsFragment) mPagerAdapter.getItemByTitle(R.string.Messages);
			if (frg != null) {
				Log.d(TAG, "Notifications: calling frg.update()");
				frg.update();
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
		if (cursor.getCount() == 0) {
			return false;
		}
		else if (cursor.moveToFirst()) {
			// check if last tile update was more than 24 hours ago:
			long last_update = cursor.getLong(cursor.getColumnIndex("last_update"));
			long now = System.currentTimeMillis() / 1000;
			if (now > last_update + (3600 * 24))
				return false;
		}

		Log.d(TAG, "isMapCacheValid: cache valid for lat/lng=" + latitude + "/" + longitude);
		return true;
	}

	public void StartGetMapDetails(LatLng center) {
		if (!isMapCacheValid(center)) {
			getMapDetailList.add(center);
			StartGetMapDetails();
		}
	}

	public void StartGetMapDetails() {
		// check if task is still running:
		if (getMapDetails != null && getMapDetails.getStatus() != AsyncTask.Status.FINISHED) {
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

	@Override
	public void getMapDetailsDone(boolean success, LatLng center) {
		if (success) {
			// mark cache tile valid:
			Log.i(TAG, "OCM updates received for " + center);
			database.addlatlngdetail((int)center.latitude, (int)center.longitude);
			// update map:
			FragMap frg = (FragMap) mPagerAdapter.getItemByTitle(R.string.Location);
			if (frg != null) {
				Log.d(TAG, "OCM updates received => calling FragMap.update()");
				frg.update();
			}
		} else {
			Log.e(TAG, "OCM updates failed for " + center);
		}
		// check for next fetch job:
		StartGetMapDetails();
	}

}
