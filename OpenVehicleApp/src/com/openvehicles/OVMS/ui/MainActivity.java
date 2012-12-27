package com.openvehicles.OVMS.ui;

import java.util.ArrayList;
import java.util.UUID;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.openvehicles.OVMS.BaseApp;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiStatusObservable;
import com.openvehicles.OVMS.api.ApiTask;
import com.openvehicles.OVMS.api.ApiTask.UpdateStatusListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.utils.NotificationData;
import com.openvehicles.OVMS.utils.OVMSNotifications;

public class MainActivity extends SherlockFragmentActivity implements 
		ActionBar.OnNavigationListener, UpdateStatusListener  {
	private static final String TAG = "MainActivity";

	public View mapview_container;
	private ViewPager mViewPager;
	private CarData mCarData;
	private Handler mC2dmHandler = new Handler();
	private ApiTask mApiTask;
	private AlertDialog mAlertDialog;
	private boolean isLoggedIn = false;
	private boolean isSuppressServerErrorDialog = false;
	private ApiStatusObservable mObservable;
	private MainPagerAdapter mPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		mViewPager = new ViewPager(this);
//		mViewPager.setId(android.R.id.tabhost);
//		setContentView(mViewPager);
		setContentView(R.layout.activity_main);
		mViewPager = (ViewPager) findViewById(R.id.vp_main);
		
		mObservable = ApiStatusObservable.get();
		
		final ActionBar actionBar = getSupportActionBar();
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
//		actionBar.setDisplayShowHomeEnabled(false);

		mPagerAdapter = new MainPagerAdapter(
			new TabInfo(R.string.Battery, R.drawable.ic_action_battery, InfoFragment.class),
			new TabInfo(R.string.Car, R.drawable.ic_action_car, CarFragment.class),
			new TabInfo(R.string.Location, R.drawable.ic_action_location_map, MapFragment.class),
			new TabInfo(R.string.Messages, R.drawable.ic_action_email, NotificationsFragment.class),
			new TabInfo(R.string.Settings, R.drawable.ic_action_settings, SettingsFragment.class)
		);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(new NavAdapter(this, mPagerAdapter.getTabInfoItems()), this);

		mapview_container = LayoutInflater.from(this).inflate(R.layout.fragment_map, null);
//		pagerAdapter.initTabUi();

		// restore saved cars
//		loadCars();
//		changeCar(BaseApp.getSelectedCarData());

		// check for C2DM registration
		// Restore saved registration id
		SharedPreferences settings = getSharedPreferences("C2DM", 0);
		String registrationID = settings.getString("RegID", "");
		if (registrationID.length() == 0) {
			Log.d("C2DM", "Doing first time registration.");

			// No C2DM ID available. Register now.
			ProgressDialog progress = ProgressDialog.show(this,
					"Push Notification Network",
					"Sending one-time registration...");
			Intent registrationIntent = new Intent(
					"com.google.android.c2dm.intent.REGISTER");
			registrationIntent.putExtra("app",
					PendingIntent.getBroadcast(this, 0, new Intent(), 0)); // boilerplate
			registrationIntent.putExtra("sender", "openvehicles@gmail.com");
			startService(registrationIntent);
			progress.dismiss();

			mC2dmHandler.postDelayed(reportC2DMRegistrationID, 2000);

			/*
			 * unregister Intent unregIntent = new
			 * Intent("com.google.android.c2dm.intent.UNREGISTER");
			 * unregIntent.putExtra("app", PendingIntent.getBroadcast(this, 0,
			 * new Intent(), 0)); startService(unregIntent);
			 */
		} else {
			Log.d("C2DM", "Loaded Saved C2DM registration ID: " + registrationID);
			mC2dmHandler.postDelayed(reportC2DMRegistrationID, 2000);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		try {
			if (mApiTask != null) {
				Log.v("TCP", "Shutting down TCP connection");
				mApiTask.connClose();
				mApiTask.cancel(true);
				mApiTask = null;
			}
		} catch (Exception e) {
			Log.e(TAG, "ERROR pause ApiTask", e);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		changeCar(mCarData != null ? mCarData : BaseApp.getSelectedCarData());
	}
	
//	@Override
//	public void onTabSelected(Tab tab, android.support.v4.app.FragmentTransaction ft) {
//		mViewPager.setCurrentItem(tab.getPosition());
//	}
//
//	@Override
//	public void onTabUnselected(Tab tab, android.support.v4.app.FragmentTransaction ft) {}
//
//	@Override
//	public void onTabReselected(Tab tab, android.support.v4.app.FragmentTransaction ft) {}
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		TabInfo ti = mPagerAdapter.getTabInfoItems()[itemPosition];
		getSupportActionBar().setIcon(ti.icon_res_id);
		mViewPager.setCurrentItem(itemPosition);
		return true;
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getSupportMenuInflater().inflate(R.layout.main_menu, menu);
//		return true;
//	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.menuQuit:
//			finish();
//			return true;
//		case R.id.menuDeleteSavedNotifications:
//			OVMSNotifications notifications = new OVMSNotifications(this);
//			notifications.notifications = new ArrayList<NotificationData>();
//			notifications.save();
////			updateStatus();
//			return true;
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//	}
	
	public void sendCommand(int pResIdMessage, String pCommand) {
		sendCommand(getString(pResIdMessage), pCommand);
	}
	
	public void sendCommand(String pMessage, String pCommand) {
		if (mApiTask == null) return;
		
		mApiTask.sendCommand(String.format("MP-0 C%s", pCommand));
		Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show();
	}
	
	public CarData getCardata() {
		return mCarData;
	}
	
	public void changeCar(CarData pCarData) {
		runOnUiThread(progressLoginShowDialog);
		
		Log.d(TAG, "Changed car to: " + pCarData.sel_vehicleid);
		mCarData = pCarData;
		isLoggedIn = false;

		// kill previous connection
		if (mApiTask != null) {
			Log.v("TCP", "Shutting down pervious TCP connection (ChangeCar())");
			isSuppressServerErrorDialog = true;
			mApiTask.connClose();
			mApiTask.cancel(true);
			mApiTask = null;
			isSuppressServerErrorDialog = false;
		}

		// start new connection
		// reset the paranoid mode flag in car data
		// it will be set again when the TCP task detects paranoid mode messages
		mCarData.sel_paranoid = false;
		mApiTask = new ApiTask(mCarData, this);
		Log.v(TAG, "Starting TCP Connection (changeCar())");
		mApiTask.execute();
//		getTabHost().setCurrentTabByTag("tabInfo");
//		updateStatus();
		onUpdateStatus();
	}
	
	private ProgressDialog mProgressLoginDialog = null;
	private final Runnable progressLoginCloseDialog = new Runnable() {
		public void run() {
			if (mProgressLoginDialog != null) {
				mProgressLoginDialog.dismiss();
			}
		}
	};
	
	private final Runnable progressLoginShowDialog = new Runnable() {
		public void run() {
			mProgressLoginDialog = ProgressDialog.show(MainActivity.this, "", "Connecting to OVMS Server...");
		}
	};
	
	private final Runnable reportC2DMRegistrationID = new Runnable() {
		public void run() {
			// check if tcp connection is still active (it may be closed as the user leaves the program)
			if (mApiTask == null) return;

			SharedPreferences settings = getSharedPreferences("C2DM", 0);
			String registrationID = settings.getString("RegID", "");
			String uuid;

			if (!settings.contains("UUID")) {
				// generate a new UUID
				uuid = UUID.randomUUID().toString();
				Editor editor = getSharedPreferences("C2DM", Context.MODE_PRIVATE).edit();
				editor.putString("UUID", uuid);
				editor.commit();

				Log.d(TAG, "Generated New C2DM UUID: " + uuid);
			} else {
				uuid = settings.getString("UUID", "");
				Log.d(TAG, "Loaded Saved C2DM UUID: " + uuid);
			}

			// MP-0
			// p<appid>,<pushtype>,<pushkeytype>{,<vehicleid>,<netpass>,<pushkeyvalue>}
			if ((registrationID.length() == 0)
					|| !mApiTask.sendCommand(String.format(
							"MP-0 p%s,c2dm,production,%s,%s,%s", uuid,
							mCarData.sel_vehicleid, mCarData.sel_server_password,
							registrationID))) {
				// command not successful, reschedule reporting after 5 seconds
				Log.d(TAG, "Reporting C2DM ID failed. Rescheduling.");
				mC2dmHandler.postDelayed(reportC2DMRegistrationID, 5000);
			}
		}
	};
	
	@Override
	public void onUpdateStatus() {
		mObservable.notifyObservers(mCarData);
	}

	@Override
	public void onResultCommand(String pCmd) {
		// TODO Auto-generated method stub
		if (TextUtils.isEmpty(pCmd)) return;
		String[] data = pCmd.split(",\\s*");
		
		if (data.length >= 3) {
			Toast.makeText(this, data[2], Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onLoginComplete() {
		isLoggedIn = true;
		runOnUiThread(progressLoginCloseDialog);
	}
	
	@Override
	public void onServerSocketError(Throwable e) {
		if (mProgressLoginDialog != null) {
			mProgressLoginDialog.hide();
		}
		
		if (isSuppressServerErrorDialog) return;
	
		if (mAlertDialog != null && mAlertDialog.isShowing()){
			return;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setMessage(isLoggedIn ? R.string.err_connection_lost : R.string.err_check_following)
			.setTitle(R.string.lb_communications_problem)
			.setCancelable(false)
			.setPositiveButton(android.R.string.ok, null);
		mAlertDialog = builder.create();
		mAlertDialog.show();
	}

	@Override
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	
	private class TabInfo {
		public final int title_res_id, icon_res_id;
		public final Class<? extends BaseFragment> fragment_class;
		
		public String getFragmentName() {
			return fragment_class.getName();
		}
		
		public TabInfo(int pTitleResId, int pIconResId, Class<? extends BaseFragment> pFragmentClass) {
			title_res_id = pTitleResId;
			icon_res_id = pIconResId;
			fragment_class = pFragmentClass;
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
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			TextView tv = (TextView) super.getDropDownView(position, convertView, parent);
			tv.setCompoundDrawablesWithIntrinsicBounds(getItem(position).icon_res_id, 0, 0, 0);
			return tv; 
		}
	}

	private class MainPagerAdapter extends FragmentPagerAdapter {
		private final TabInfo[] mTabInfoItems;
		
		public MainPagerAdapter(TabInfo ...pTabInfoItems) {
			super(getSupportFragmentManager());
			mTabInfoItems = pTabInfoItems;
		}
		
		public TabInfo[] getTabInfoItems() {
			return mTabInfoItems;
		}
		
//		public void initTabUi() {
//			ActionBar actionBar = getSupportActionBar();
//			if (actionBar.getTabCount() > 0) actionBar.removeAllTabs();
//			
//			for (TabInfo tabInfo : mTabInfoItems) {
//				actionBar.addTab(actionBar.newTab()
//						.setText(tabInfo.title_res_id)
//						.setIcon(tabInfo.icon_res_id)
//						.setTabListener(MainActivity.this));
//			}
//		}

		@Override
		public Fragment getItem(int pPosition) {
			return Fragment.instantiate(MainActivity.this, mTabInfoItems[pPosition].getFragmentName());
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
}
