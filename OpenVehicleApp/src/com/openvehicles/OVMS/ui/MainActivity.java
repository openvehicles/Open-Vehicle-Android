package com.openvehicles.OVMS.ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiStatusObservable;
import com.openvehicles.OVMS.api.ApiTask;
import com.openvehicles.OVMS.api.ApiTask.UpdateStatusListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.utils.NotificationData;
import com.openvehicles.OVMS.utils.OVMSNotifications;

public class MainActivity extends SherlockFragmentActivity implements 
		ActionBar.TabListener, UpdateStatusListener  {
	private static final String SETTINGS_FILENAME = "OVMSSavedCars.obj";

	public View mapview_container;
	
	private ViewPager mViewPager;
	private ArrayList<CarData> mSavedCars;
	private CarData mCarData;
	private Handler mC2dmHandler = new Handler();
	private ApiTask mApiTask;
	private AlertDialog mAlertDialog;
	private boolean isLoggedIn = false;
	private boolean isSuppressServerErrorDialog = false;
	private ApiStatusObservable mObservable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(android.R.id.tabhost);
		setContentView(mViewPager);
		
		mObservable = ApiStatusObservable.get();
		
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);

		MainPagerAdapter pagerAdapter = new MainPagerAdapter(
			new TabInfo(R.string.Settings, R.drawable.ic_action_settings, SettingsFragment.class),
			new TabInfo(R.string.Battery, R.drawable.ic_action_battery, InfoFragment.class),
			new TabInfo(R.string.Car, R.drawable.ic_action_car, CarFragment.class),
			new TabInfo(R.string.Location, R.drawable.ic_action_location, MapFragment.class)
//			new TabInfo(R.string.Messages, R.drawable.ic_action_email, NotificationsFragment.class)
		);
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
		
		mapview_container = LayoutInflater.from(this).inflate(R.layout.fragment_map, null);
		pagerAdapter.initTabUi();

		// restore saved cars
		loadCars();

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
		}
		saveCars();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		changeCar(mCarData);
	}
	
	@Override
	public void onTabSelected(Tab tab, android.support.v4.app.FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, android.support.v4.app.FragmentTransaction ft) {}

	@Override
	public void onTabReselected(Tab tab, android.support.v4.app.FragmentTransaction ft) {}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.layout.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuQuit:
			finish();
			return true;
		case R.id.menuDeleteSavedNotifications:
			OVMSNotifications notifications = new OVMSNotifications(this);
			notifications.notifications = new ArrayList<NotificationData>();
			notifications.save();
//			updateStatus();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void sendCommand(int pResIdMessage, String pCommand) {
		sendCommand(getString(pResIdMessage), pCommand);
	}
	
	public void sendCommand(String pMessage, String pCommand) {
		if (mApiTask == null) return;
		
		mApiTask.sendCommand(String.format("MP-0 C%s", pCommand));
		Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show();
	}
	
	public ArrayList<CarData> getSavedCarData() {
		return mSavedCars;
	}
	
	public void changeCar(CarData pCarData) {
		runOnUiThread(progressLoginShowDialog);
		Log.d("OVMS", "Changed car to: " + pCarData.sel_vehicleid);

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
		mCarData = pCarData;
		// reset the paranoid mode flag in car data
		// it will be set again when the TCP task detects paranoid mode messages
		pCarData.sel_paranoid = false;
		mApiTask = new ApiTask(mCarData, this);
		Log.v("TCP", "Starting TCP Connection (ChangeCar())");
		mApiTask.execute();
//		getTabHost().setCurrentTabByTag("tabInfo");
//		updateStatus();
		onUpdateStatus();
	}
	
	
	@SuppressWarnings("unchecked")
	private void loadCars() {
		try {
			Log.d("OVMS", "Loading saved cars from internal storage file: " + SETTINGS_FILENAME);
			FileInputStream fis = this.openFileInput(SETTINGS_FILENAME);
			ObjectInputStream is = new ObjectInputStream(fis);
			mSavedCars = (ArrayList<CarData>) is.readObject();
			is.close();

			SharedPreferences settings = getSharedPreferences("OVMS", 0);
			String lastVehicleID = settings.getString("LastVehicleID", "").trim();
			if (lastVehicleID.length() == 0) {
				// no vehicle ID saved
				mCarData = mSavedCars.get(0);
			} else {
				Log.d("OVMS", String.format("Loaded %s cars. Last used car is ", mSavedCars.size(), lastVehicleID));
				for (int idx = 0; idx < mSavedCars.size(); idx++) {
					if ((mSavedCars.get(idx)).sel_vehicleid.equals(lastVehicleID)) {
						mCarData = mSavedCars.get(idx);
						break;
					}
				}
				if (mCarData == null) {
					mCarData = mSavedCars.get(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.d("ERR", e.getMessage());

			Log.d("OVMS", "Invalid save file. Initializing with demo car.");
			// No settings file found. Initialize settings.
			mSavedCars = new ArrayList<CarData>();
			CarData demoCar = new CarData();
			demoCar.sel_vehicleid = "DEMO";
			demoCar.sel_server_password = "DEMO";
			demoCar.sel_module_password = "DEMO";
			demoCar.sel_server = "tmc.openvehicles.com";
			demoCar.sel_vehicle_image = "car_roadster_lightninggreen";
			mSavedCars.add(demoCar);

			mCarData = demoCar;
			saveCars();
		}
	}

	public void saveCars() {
		try {
			Log.d("OVMS", "Saving cars to interal storage...");

			// save last used vehicleID
			SharedPreferences settings = getSharedPreferences("OVMS", 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("LastVehicleID", mCarData.sel_vehicleid);
			editor.commit();

			FileOutputStream fos = this.openFileOutput(SETTINGS_FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(mSavedCars);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("ERR", e.getMessage());
		}
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

				Log.d("OVMS", "Generated New C2DM UUID: " + uuid);
			} else {
				uuid = settings.getString("UUID", "");
				Log.d("OVMS", "Loaded Saved C2DM UUID: " + uuid);
			}

			// MP-0
			// p<appid>,<pushtype>,<pushkeytype>{,<vehicleid>,<netpass>,<pushkeyvalue>}
			if ((registrationID.length() == 0)
					|| !mApiTask.sendCommand(String.format(
							"MP-0 p%s,c2dm,production,%s,%s,%s", uuid,
							mCarData.sel_vehicleid, mCarData.sel_server_password,
							registrationID))) {
				// command not successful, reschedule reporting after 5 seconds
				Log.d("OVMS", "Reporting C2DM ID failed. Rescheduling.");
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
		if (isSuppressServerErrorDialog) return;
	
		if (mAlertDialog != null && mAlertDialog.isShowing()){
			return; // do not show duplicated alert dialogs
		}
		
		if (mProgressLoginDialog != null) {
			mProgressLoginDialog.hide();
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setMessage(isLoggedIn ? R.string.err_connection_lost : R.string.err_connection_lost)
			.setTitle(R.string.lb_communications_problem)
			.setCancelable(false)
			.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
//								MainActivity.this.getTabHost().setCurrentTabByTag("tabCars");
				}
			});
		mAlertDialog = builder.create();
		mAlertDialog.show();
	}

	@Override
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	
	private static class TabInfo {
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
	}

	private class MainPagerAdapter extends FragmentPagerAdapter {
		private final TabInfo[] mTabInfoItems;
		
		public MainPagerAdapter(TabInfo ...pTabInfoItems) {
			super(getSupportFragmentManager());
			mTabInfoItems = pTabInfoItems;
		}
		
		public void initTabUi() {
			ActionBar actionBar = getSupportActionBar();
			if (actionBar.getTabCount() > 0) actionBar.removeAllTabs();
			
			for (TabInfo tabInfo : mTabInfoItems) {
				actionBar.addTab(actionBar.newTab()
						.setText(tabInfo.title_res_id)
						.setIcon(tabInfo.icon_res_id)
						.setTabListener(MainActivity.this));
			}
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
