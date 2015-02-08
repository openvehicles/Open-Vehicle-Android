package com.openvehicles.OVMS.ui;

import java.util.UUID;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Window;
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
	// private static final String TAG = "MainActivity";

	private final Handler mC2dmHandler = new Handler();
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

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setSupportProgressBarIndeterminateVisibility(false);

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
		Log.d("Main", "Notifications registering receiver for Intent: " + getPackageName() + ".Notification");
		registerReceiver(mNotificationReceiver, new IntentFilter(
				getPackageName() + ".Notification"));

		// init UI tabs:
		mViewPager = new ViewPager(this);
		mViewPager.setId(android.R.id.tabhost);
		setContentView(mViewPager);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);

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
				}
			});

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(
				new NavAdapter(this, mPagerAdapter.getTabInfoItems()), this);

		// check for C2DM registration
		// Restore saved registration id
		SharedPreferences settings = getSharedPreferences("C2DM", 0);
		String registrationID = settings.getString("RegID", "");
		if (registrationID.length() == 0) {
			Log.d("C2DM", "Doing first time registration.");

			// No C2DM ID available. Register now.
			// ProgressDialog progress = ProgressDialog.show(this,
			// "Push Notification Network", "Sending one-time registration...");
			Intent intent = new Intent(
					"com.google.android.c2dm.intent.REGISTER");
			intent.putExtra("app",
					PendingIntent.getBroadcast(this, 0, new Intent(), 0)); // boilerplate
			intent.putExtra("sender", "openvehicles@gmail.com");
			startService(intent);
			// progress.dismiss();

			mC2dmHandler.postDelayed(mC2DMRegistrationID, 2000);

			/*
			 * unregister Intent unregIntent = new
			 * Intent("com.google.android.c2dm.intent.UNREGISTER");
			 * unregIntent.putExtra("app", PendingIntent.getBroadcast(this, 0,
			 * new Intent(), 0)); startService(unregIntent);
			 */
		} else {
			Log.d("C2DM", "Loaded Saved C2DM registration ID: "
					+ registrationID);
			mC2dmHandler.postDelayed(mC2DMRegistrationID, 2000);
		}
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
		super.onDestroy();
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		TabInfo ti = mPagerAdapter.getTabInfoItems()[itemPosition];
		getSupportActionBar().setIcon(ti.icon_res_id);
		mViewPager.setCurrentItem(itemPosition, false);
		return true;
	}

	private final Runnable mC2DMRegistrationID = new Runnable() {
		public void run() {
			// check if tcp connection is still active (it may be closed as the
			// user leaves the program)
			ApiService service = getService();
			if (service == null || !service.isLoggined())
				return;

			SharedPreferences settings = getSharedPreferences("C2DM", 0);
			String registrationID = settings.getString("RegID", "");
			String uuid;

			if (!settings.contains("UUID")) {
				// generate a new UUID
				uuid = UUID.randomUUID().toString();
				Editor editor = getSharedPreferences("C2DM",
						Context.MODE_PRIVATE).edit();
				editor.putString("UUID", uuid);
				editor.commit();

				Log.d("C2DM", "Generated New C2DM UUID: " + uuid);
			} else {
				uuid = settings.getString("UUID", "");
				Log.d("C2DM", "Loaded Saved C2DM UUID: " + uuid);
			}

			// MP-0
			// p<appid>,<pushtype>,<pushkeytype>{,<vehicleid>,<netpass>,<pushkeyvalue>}
			CarData carData = service.getCarData();
			String cmd = String.format("MP-0 p%s,c2dm,production,%s,%s,%s",
					uuid, carData.sel_vehicleid, carData.sel_server_password,
					registrationID);
			service.sendCommand(cmd, null);

			if ((registrationID.length() == 0)
					|| !service.sendCommand(cmd, null)) {
				// command not successful, reschedule reporting after 5 seconds
				Log.d("C2DM", "Reporting C2DM ID failed. Rescheduling.");
				mC2dmHandler.postDelayed(mC2DMRegistrationID, 5000);
			}
		}
	};

	private final BroadcastReceiver mApiEventReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getSerializableExtra("onServerSocketError") != null) {
				setSupportProgressBarIndeterminateVisibility(false);

				new AlertDialog.Builder(MainActivity.this)
						.setTitle(R.string.Error)
						.setMessage(intent.getStringExtra("message"))
						.setPositiveButton(android.R.string.ok, null).show();
			}
			if (intent.getBooleanExtra("onLoginBegin", false)) {
				setSupportProgressBarIndeterminateVisibility(true);
				ApiObservable.get().addObserver(mApiObserver);
			}
		}
	};

	private ApiObserver mApiObserver = new ApiObserver() {
		@Override
		public void update(CarData pCarData) {
			setSupportProgressBarIndeterminateVisibility(false);
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
			super(context, R.layout.sherlock_spinner_item, objects);
			setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
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
			//Log.d("MainActivity", "MainPagerAdapter: pos=" + pPosition + " => frg=" + mTabInfoItems[pPosition].fragment);
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
			//Log.d("Main", "Notifications: received " + intent.toString());

			// show messages list:
			onNavigationItemSelected(3, 0);

			// update messages list:
			NotificationsFragment frg = (NotificationsFragment) mPagerAdapter.getItem(3);
			if (frg != null) {
				//Log.d("Main", "Notifications: calling frg.update()");
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
			System.out.println("nulllllllllllllll");
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
			Log.d("OCM", "MainActivity.getdata: cache valid for lat/lng=" + latitude + "/" + longitude
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

			Log.d("OCM", "MainActivity.getdata: new fetch for lat/lng=" + latitude + "/" + longitude
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
