package com.openvehicles.OVMS.ui;

import java.util.UUID;
import android.support.v7.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.support.v7.app.ActionBar;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.luttu.AppPrefes;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiObservable;
import com.openvehicles.OVMS.api.ApiObserver;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.FragMap.UpdateLocation;
import com.openvehicles.OVMS.ui.GetMapDetails.afterasytask;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.utils.ConnectionList;
import com.openvehicles.OVMS.utils.ConnectionList.Con;

public class MainActivity extends ApiActivity implements
		ActionBar.OnNavigationListener, afterasytask, Con, UpdateLocation {
	private static final String TAG = "MainActivity";

	private ViewPager mViewPager;
	private MainPagerAdapter mPagerAdapter;
	AppPrefes appPrefes;
	Database database;
	public static UpdateLocation updateLocation;
	public GetMapDetails getMapDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		appPrefes = new AppPrefes(this, "ovms");
		database = new Database(this);

		// OCM init:
		updateLocation = this;
		updatelocation();
		String url = "http://api.openchargemap.io/v2/referencedata/";
		ConnectionList connectionList = new ConnectionList(this, this, url,
				true);

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
				new TabInfo(R.string.Battery, R.drawable.ic_action_battery, InfoFragment.class),
				new TabInfo(R.string.Car, R.drawable.ic_action_car, CarFragment.class),
				new TabInfo(R.string.Location, R.drawable.ic_action_location_map, FragMap.class),
				new TabInfo(R.string.Messages, R.drawable.ic_action_email, NotificationsFragment.class),
				new TabInfo(R.string.Settings, R.drawable.ic_action_settings, SettingsFragment.class)
		);

		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(
			new ViewPager.SimpleOnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					actionBar.setSelectedNavigationItem(position);

					// cancel system notifications on page "Messages":
					if (position == 3) {
						NotificationManager mNotificationManager = (NotificationManager)
								getSystemService(Context.NOTIFICATION_SERVICE);
						mNotificationManager.cancelAll();
					}
				}
			});

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(
				new NavAdapter(this, mPagerAdapter.getTabInfoItems()), this);

		// schedule GCM registration:
		mGcmHandler.postDelayed(mGcmDoRegistration, 2000);

		// process Activity startup intent:
		onNewIntent(getIntent());
	}

	@Override
	public void onNewIntent(Intent newIntent) {
		super.onNewIntent(newIntent);

		if (newIntent.getBooleanExtra("onNotification", false)) {
			onNavigationItemSelected(3, 0);
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mApiEventReceiver);
		unregisterReceiver(mNotificationReceiver);
		database.close();
		super.onDestroy();
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		TabInfo ti = mPagerAdapter.getTabInfoItems()[itemPosition];
		getSupportActionBar().setIcon(ti.icon_res_id);
		mViewPager.setCurrentItem(itemPosition, false);
		return true;
	}

	@Override
	public void setSupportProgressBarIndeterminateVisibility(boolean visible) {
		getSupportActionBar().getCustomView().setVisibility(visible ? View.VISIBLE : View.GONE);
	}


	// GCM push notification registration:
	// 	- register with GCM hub
	//	- create UUID for this device
	//	- subscribe at OVMS server
	private final Handler mGcmHandler = new Handler();
	private final Runnable mGcmDoRegistration = new Runnable() {
		private AsyncTask<Void, Void, Void> getTokenTask;

		public void run() {

			SharedPreferences settings = getSharedPreferences("GCM", 0);
			String registrationID = settings.getString("RegID", "");
			boolean retry = true;

			// get/create GCM registration ID:
			if (registrationID.length() == 0) {

				// may block, needs to be done in sub task:

				if (getTokenTask != null && getTokenTask.getStatus() == AsyncTask.Status.FINISHED)
					getTokenTask = null; // failed, retry

				if (getTokenTask == null) {
					getTokenTask = new AsyncTask<Void, Void, Void>() {
						@Override
						protected Void doInBackground(Void... params) {
							try {
								Log.d(TAG, "GCM: doing first time registration.");

								InstanceID instanceID = InstanceID.getInstance(getBaseContext());
								String registrationID = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
										GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

								Log.i(TAG, "GCM: new registration ID: " + registrationID);

								// store new registration ID:
								SharedPreferences settings = getSharedPreferences("GCM", 0);
								Editor editor = settings.edit();
								editor.putString("RegID", registrationID);
								editor.commit();

							} catch (Exception e) {
								Log.e(TAG, "GCM: registration failed", e);
							}
							return null;
						}
					}.execute();
				}

			} else {

				Log.d(TAG, "GCM: using registration ID: " + registrationID);

				// get/create device UUID:
				String uuid = settings.getString("UUID", "");
				if (uuid.length() == 0) {
					uuid = UUID.randomUUID().toString();
					Editor editor = settings.edit();
					editor.putString("UUID", uuid);
					editor.commit();
					Log.d(TAG, "GCM: generated new UUID: " + uuid);
				} else {
					Log.d(TAG, "GCM: using UUID: " + uuid);
				}

				// subscribe at OVMS server:
				ApiService service = getService();
				if (service != null && service.isLoggedIn()) {
					// MP-0
					// p<appid>,<pushtype>,<pushkeytype>{,<vehicleid>,<netpass>,<pushkeyvalue>}
					CarData carData = service.getCarData();
					String cmd = String.format("MP-0 p%s,gcm,production,%s,%s,%s",
							uuid, carData.sel_vehicleid, carData.sel_server_password,
							registrationID);
					if (!service.sendCommand(cmd, null)) {
						Log.w(TAG, "GCM: push subscription failed, scheduling retry");
					} else {
						Log.d(TAG, "GCM: push subscription done.");
						retry = false;
					}
				}
			}

			if (retry) {
				// retry after 5 seconds:
				mGcmHandler.postDelayed(mGcmDoRegistration, 5000);
			}
		}
	};


	private AlertDialog mApiErrorDialog;
	private String mApiErrorMessage;

	private final BroadcastReceiver mApiEventReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getSerializableExtra("onServerSocketError") != null) {

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

				setSupportProgressBarIndeterminateVisibility(true);

				// observe for login success:
				ApiObservable.get().addObserver(mApiObserver);
			}

		}
	};

	private ApiObserver mApiObserver = new ApiObserver() {
		@Override
		public void update(CarData pCarData) {
			// data update implies login successful => hide progress:
			setSupportProgressBarIndeterminateVisibility(false);

			// ...and hide error dialog:
			if (mApiErrorDialog != null && mApiErrorDialog.isShowing()) {
				mApiErrorDialog.hide();
			}

			// done waiting for login:
			ApiObservable.get().deleteObserver(this);
		}

		@Override
		public void onServiceAvailable(ApiService pService) {
		}
	};


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
			if (mTabInfoItems[pPosition].fragment == null) {
				// instantiate fragment:
				mTabInfoItems[pPosition].fragment = Fragment.instantiate(
						MainActivity.this,
						mTabInfoItems[pPosition].getFragmentName());
			}
			//Log.d(TAG, "MainPagerAdapter: pos=" + pPosition + " => frg=" + mTabInfoItems[pPosition].fragment);
			return mTabInfoItems[pPosition].fragment;
		}

		@Override
		public int getCount() {
			return mTabInfoItems.length;
		}

		@Override
		public CharSequence getPageTitle(int pPosition) {
			return getString(mTabInfoItems[pPosition].title_res_id);
		}
	}

	@Override
	public void connections(String al, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void after(boolean flBoolean) {
		// TODO Auto-generated method stub

	}


	// Make notification updates visible immediately:
	private final BroadcastReceiver mNotificationReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			//Log.d(TAG, "Notifications: received " + intent.toString());

			// show messages list:
			// ...or better not, as it can interrupt user activity...
			//onNavigationItemSelected(3, 0);

			// update messages list:
			NotificationsFragment frg = (NotificationsFragment) mPagerAdapter.getItem(3);
			if (frg != null) {
				//Log.d(TAG, "Notifications: calling frg.update()");
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


		// As OCM does not yet support incremental queries,
		// we're using a cache with key = int(lat/lng)
		// resulting in a tile size of max. 112 x 112 km
		// = diagonal max 159 km
		// The API call will fetch a fixed radius of 160 km
		// covering all adjacent tiles.

		// check OCM cache for key int(lat/lng):

		boolean cache_valid = true;

		String[] strings = lat.split("\\.");
		String[] strings2 = lng.split("\\.");
		int latitude = Integer.parseInt(strings[0]);
		int longitude = Integer.parseInt(strings2[0]);

		Cursor cursor = database.getlatlngdetail(latitude, longitude);
		if (cursor.getCount() == 0) {
			cache_valid = false;
		}
		else if (cursor.moveToFirst()) {
			// check if last tile update was more than 4 weeks (28 days) ago:
			long last_update = cursor.getLong(cursor.getColumnIndex("last_update"));
			long now = System.currentTimeMillis() / 1000;
			if (now > last_update + (3600 * 24 * 28))
				cache_valid = false;
		}

		if (cache_valid) {
			Log.d(TAG, "getdata: cache valid for lat/lng=" + latitude + "/" + longitude
					+ ", lat_main=" + appPrefes.getData("lat_main"));
		} else {
			database.addlatlngdetail(latitude, longitude);

			// make OCM API URL:
			String maxresults = appPrefes.getData("maxresults");
			String url = "http://api.openchargemap.io/v2/poi/?output=json&verbose=false"
					+ "&latitude=" + lat
					+ "&longitude=" + lng
					+ "&distance=160" // see above
					+ "&distanceunit=KM"
					+ "&maxresults=" + (maxresults.equals("") ? "500" : maxresults);

			Log.d(TAG, "getdata: new fetch for lat/lng=" + latitude + "/" + longitude
					+ ", lat_main=" + appPrefes.getData("lat_main")
					+ " => url=" + url);

			// cancel old fetcher task:
			if (getMapDetails != null) {
				getMapDetails.cancel(true);
			}

			// create new fetcher task:
			getMapDetails = new GetMapDetails(MainActivity.this, url, this);

			// After Android 3.0, the default behavior of AsyncTask is execute in a single
			// thread using SERIAL_EXECUTOR. This means the thread will no longer run while
			// the App is in the foreground...
			// Solution source:
			// http://stackoverflow.com/questions/13080367/android-async-task-behavior-in-2-3-3-and-4-0-os
			if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
				getMapDetails.execute();
			} else {
				getMapDetails.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}

		}

	}

}
