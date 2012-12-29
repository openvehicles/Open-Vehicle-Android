package com.openvehicles.OVMS.ui;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.openvehicles.OVMS.R;

public class MainActivity extends SherlockFragmentActivity implements ActionBar.OnNavigationListener  {
	private static final String TAG = "MainActivity";

	public View mapview_container;
	private ViewPager mViewPager;
//	private Handler mC2dmHandler = new Handler();
	private MainPagerAdapter mPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(android.R.id.tabhost);
		setContentView(mViewPager);
		
		
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);

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

//		// check for C2DM registration
//		// Restore saved registration id
//		SharedPreferences settings = getSharedPreferences("C2DM", 0);
//		String registrationID = settings.getString("RegID", "");
//		if (registrationID.length() == 0) {
//			Log.d("C2DM", "Doing first time registration.");
//
//			// No C2DM ID available. Register now.
//			ProgressDialog progress = ProgressDialog.show(this,
//					"Push Notification Network",
//					"Sending one-time registration...");
//			Intent registrationIntent = new Intent(
//					"com.google.android.c2dm.intent.REGISTER");
//			registrationIntent.putExtra("app",
//					PendingIntent.getBroadcast(this, 0, new Intent(), 0)); // boilerplate
//			registrationIntent.putExtra("sender", "openvehicles@gmail.com");
//			startService(registrationIntent);
//			progress.dismiss();
//
//			mC2dmHandler.postDelayed(reportC2DMRegistrationID, 2000);
//
//			/*
//			 * unregister Intent unregIntent = new
//			 * Intent("com.google.android.c2dm.intent.UNREGISTER");
//			 * unregIntent.putExtra("app", PendingIntent.getBroadcast(this, 0,
//			 * new Intent(), 0)); startService(unregIntent);
//			 */
//		} else {
//			Log.d("C2DM", "Loaded Saved C2DM registration ID: " + registrationID);
//			mC2dmHandler.postDelayed(reportC2DMRegistrationID, 2000);
//		}
	}
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		TabInfo ti = mPagerAdapter.getTabInfoItems()[itemPosition];
		getSupportActionBar().setIcon(ti.icon_res_id);
		mViewPager.setCurrentItem(itemPosition);
		return true;
	}	
	
	private final Runnable reportC2DMRegistrationID = new Runnable() {
		public void run() {
			// check if tcp connection is still active (it may be closed as the user leaves the program)
//			if (mApiTask == null) return;
//
//			SharedPreferences settings = getSharedPreferences("C2DM", 0);
//			String registrationID = settings.getString("RegID", "");
//			String uuid;
//
//			if (!settings.contains("UUID")) {
//				// generate a new UUID
//				uuid = UUID.randomUUID().toString();
//				Editor editor = getSharedPreferences("C2DM", Context.MODE_PRIVATE).edit();
//				editor.putString("UUID", uuid);
//				editor.commit();
//
//				Log.d(TAG, "Generated New C2DM UUID: " + uuid);
//			} else {
//				uuid = settings.getString("UUID", "");
//				Log.d(TAG, "Loaded Saved C2DM UUID: " + uuid);
//			}
//
//			// MP-0
//			// p<appid>,<pushtype>,<pushkeytype>{,<vehicleid>,<netpass>,<pushkeyvalue>}
//			if ((registrationID.length() == 0)
//					|| !mApiTask.sendCommand(String.format(
//							"MP-0 p%s,c2dm,production,%s,%s,%s", uuid,
//							mCarData.sel_vehicleid, mCarData.sel_server_password,
//							registrationID))) {
//				// command not successful, reschedule reporting after 5 seconds
//				Log.d(TAG, "Reporting C2DM ID failed. Rescheduling.");
//				mC2dmHandler.postDelayed(reportC2DMRegistrationID, 5000);
//			}
		}
	};
	
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
