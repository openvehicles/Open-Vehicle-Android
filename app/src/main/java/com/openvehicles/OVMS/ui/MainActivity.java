package com.openvehicles.OVMS.ui;

import android.Manifest;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.utils.AppPrefes;
import com.openvehicles.OVMS.ui.FragMap.UpdateLocation;
import com.openvehicles.OVMS.ui.GetMapDetails.GetMapDetailsListener;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.utils.ConnectionList;
import com.openvehicles.OVMS.utils.ConnectionList.ConnectionsListener;
import com.openvehicles.OVMS.utils.Sys;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MainActivity extends ApiActivity implements
		ActionBar.OnNavigationListener,
		GetMapDetailsListener,
		ConnectionsListener,
		UpdateLocation {

	private static final String TAG = "MainActivity";

	public static final String ACTION_FCM_NEW_TOKEN = "fcmNewToken";
	private static final String FCM_BROADCAST_TOPIC = "global";

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
			appPrefes.saveData("UUID", uuid);
			Log.d(TAG, "onCreate: generated new UUID: " + uuid);
		} else {
			Log.d(TAG, "onCreate: using UUID: " + uuid);
		}

		// Check/create API key:
		String apiKey = appPrefes.getData("APIKey");
		if (apiKey.length() == 0) {
			apiKey = Sys.getRandomString(25);
			appPrefes.saveData("APIKey", apiKey);
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
				new IntentFilter(ACTION_FCM_NEW_TOKEN));
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
						appPrefes.saveData("lastUsedVersionName", versionName))
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
							appPrefes.saveData("skipPlayServicesCheck", "1"))
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
	 * FCM push notification registration:
	 * 	- server login => gcmStartRegistration
	 * 	- init gcmSenderId specific FirebaseApp instance as needed
	 * 	- subscribe App instance to FCM broadcast channel (async)
	 * 	- get the App instance FCM token (async)
	 * 	- start GcmDoSubscribe for server push subscription (async, retries if necessary)
	 */

	// onNewToken() callback also fires from getToken(), so we need a recursion inhibitor:
	private boolean mTokenRequested = false;

	private void gcmStartRegistration() {
		CarData carData = getLoggedInCar();
		if (carData == null)
			return;
		String vehicleId = carData.sel_vehicleid;

		// Initialize App for server/car specific GCM sender ID:
		FirebaseApp myApp = FirebaseApp.getInstance();
		FirebaseOptions defaults = myApp.getOptions();

		String gcmSenderId;
		if (carData.sel_gcm_senderid != null && carData.sel_gcm_senderid.length() > 0) {
			gcmSenderId = carData.sel_gcm_senderid;
		} else {
			gcmSenderId = defaults.getGcmSenderId();
		}
		Log.d(TAG, "gcmStartRegistration: vehicleId=" + vehicleId
				+ ", gcmSenderId=" + gcmSenderId);

		if (gcmSenderId != null && !gcmSenderId.equals(defaults.getGcmSenderId())) {
			try {
				myApp = FirebaseApp.getInstance(gcmSenderId);
				Log.i(TAG, "gcmStartRegistration: reusing FirebaseApp " + myApp.getName());
			}
			catch (Exception ex1) {
				try {
					// Note: we assume here we can simply replace the sender ID. This has been tested
					//  successfully, but may need to be reconsidered & changed in the future.
					// It works because FirebaseMessaging relies on Metadata.getDefaultSenderId(),
					//  which prioritizes gcmSenderId if set. If gcmSenderId isn't set, it falls back
					//  to extracting the project number from the applicationId.
					// FCM token creation needs Project ID, Application ID and API key, but these
					//  currently don't need to match additional sender ID projects, so we can
					//  use the defaults. If/when this changes in the future, users will need to
					//  supply these three instead of the sender ID (or build the App using their
					//  "google-services.json" file).
					FirebaseOptions myOptions = new FirebaseOptions.Builder(defaults)
							//.setProjectId("…")
							//.setApplicationId("…")
							//.setApiKey("…")
							.setGcmSenderId(gcmSenderId)
							.build();
					myApp = FirebaseApp.initializeApp(this, myOptions, gcmSenderId);
					Log.i(TAG, "gcmStartRegistration: initialized new FirebaseApp " + myApp.getName());
				}
				catch (Exception ex2) {
					Log.e(TAG, "gcmStartRegistration: failed to initialize FirebaseApp, skipping token registration", ex2);
					return;
				}
			}
		}

		// Get messaging interface:
		FirebaseMessaging myMessaging = myApp.get(FirebaseMessaging.class);

		// Subscribe to broadcast channel:
		myMessaging.subscribeToTopic(FCM_BROADCAST_TOPIC)
				.addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if (!task.isSuccessful()) {
							Log.e(TAG, "gcmStartRegistration: broadcast topic subscription failed");
						} else {
							Log.i(TAG, "gcmStartRegistration: broadcast topic subscription done");
						}
					}
				});

		// Start OVMS server push subscription:
		mTokenRequested = true; // inhibit onNewToken() callback
		myMessaging.getToken()
				.addOnCompleteListener(new OnCompleteListener<String>() {
					@Override
					public void onComplete(@NonNull Task<String> task) {
						mTokenRequested = false; // allow onNewToken() callback
						if (!task.isSuccessful()) {
							Log.w(TAG, "gcmStartRegistration: fetching FCM registration token failed", task.getException());
							return;
						}
						// as this is an async callback, verify we're still logged in as the initial vehicle:
						if (!isLoggedIn(vehicleId)) {
							Log.d(TAG, "gcmStartRegistration: discard callback, logged in vehicle has changed");
							return;
						}
						// Get FCM registration token
						String token = task.getResult();
						Log.i(TAG, "gcmStartRegistration: vehicleId=" + vehicleId
								+ ", gcmSenderId=" + gcmSenderId
								+ " => token=" + token);
						// Start push subscription at OVMS server
						mGcmHandler.post(new GcmDoSubscribe(vehicleId, token));
					}
				});
	}

	private BroadcastReceiver mGcmRegistrationBroadcastReceiver = new BroadcastReceiver() {
		private static final String TAG = "mGcmRegReceiver";
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "onReceive intent: " + intent.toString());
			if (!mTokenRequested) {
				Log.i(TAG, "FCM token renewal detected => redo server registration");
				gcmStartRegistration();
			}
		}
	};

	private final Handler mGcmHandler = new Handler(Looper.getMainLooper());

	private class GcmDoSubscribe implements Runnable {
		private static final String TAG = "GcmDoSubscribe";
		private String mVehicleId, mToken;

		public GcmDoSubscribe(String pVehicleId, String pToken) {
			mVehicleId = pVehicleId;
			mToken = pToken;
		}

		@Override
		public void run() {
			ApiService service = getService();
			if (service == null) {
				Log.d(TAG, "ApiService terminated, cancelling");
				return;
			}
			else if (!service.isLoggedIn()) {
				Log.d(TAG, "ApiService not yet logged in, scheduling retry");
				mGcmHandler.postDelayed(this, 5000);
				return;
			}

			CarData carData = service.getCarData();
			if (carData == null || carData.sel_vehicleid == null || carData.sel_vehicleid.isEmpty()) {
				Log.d(TAG, "ApiService not logged in / has no defined car, cancelling");
				return;
			}

			// Async operation, verify we're still logged in to the same vehicle:
			if (!carData.sel_vehicleid.equals(mVehicleId)) {
				Log.d(TAG, "ApiService logged in to different car, cancelling");
				return;
			}

			// Subscribe at OVMS server:
			Log.d(TAG, "subscribing vehicle ID " + mVehicleId + " to FCM token " + mToken);
			// MP-0 p<appid>,<pushtype>,<pushkeytype>{,<vehicleid>,<netpass>,<pushkeyvalue>}
			String cmd = String.format("MP-0 p%s,gcm,production,%s,%s,%s",
					uuid, carData.sel_vehicleid, carData.sel_server_password, mToken);
			if (!service.sendCommand(cmd, null)) {
				Log.w(TAG, "FCM server push subscription failed, scheduling retry");
				mGcmHandler.postDelayed(this, 5000);
			} else {
				Log.i(TAG, "FCM server push subscription done");
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
			appPrefes.saveData("lat_main", lat);
			appPrefes.saveData("lng_main", lng);
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

		Log.v(TAG, "isMapCacheValid: cache valid for lat/lng=" + latitude + "/" + longitude);
		cursor.close();
		return true;
	}

	public void StartGetMapDetails(LatLng center) {
		if (!isMapCacheValid(center)) {
			getMapDetailList.add(center);
			StartGetMapDetails();
		} else {
			Log.v(TAG, "StartGetMapDetails: map cache valid for center=" + center);
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
