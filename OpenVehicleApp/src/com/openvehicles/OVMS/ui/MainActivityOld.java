package com.openvehicles.OVMS.ui;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.Mac;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.entities.CarData.DataStale;
import com.openvehicles.OVMS.receiver.CommandReceiver;
import com.openvehicles.OVMS.utils.NotificationData;
import com.openvehicles.OVMS.utils.OVMSNotifications;

@SuppressWarnings("deprecation")
public class MainActivityOld extends TabActivity implements OnTabChangeListener {
	
	private CommandReceiver mCommandReceiver = new CommandReceiver() {
		@Override
		public void onResult(String[] pData) {}
		
		@Override
		public void onCommand(String pCommand) {
			MainActivityOld.this.sendCommand(pCommand);
		}
	};
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mCommandReceiver.registerAsSender(this);
		
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

			c2dmReportTimerHandler.postDelayed(reportC2DMRegistrationID, 2000);

			/*
			 * unregister Intent unregIntent = new
			 * Intent("com.google.android.c2dm.intent.UNREGISTER");
			 * unregIntent.putExtra("app", PendingIntent.getBroadcast(this, 0,
			 * new Intent(), 0)); startService(unregIntent);
			 */
		} else {
			Log.d("C2DM", "Loaded Saved C2DM registration ID: "
					+ registrationID);
			c2dmReportTimerHandler.postDelayed(reportC2DMRegistrationID, 2000);
		}

		// setup tabs
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Reusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab
		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, TabInfoActivity.class);
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("tabInfo");
		spec.setContent(intent);
		spec.setIndicator("Battery",getResources().getDrawable(getResources().getIdentifier("ic_battery", "drawable", getPackageName())));
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, TabCarActivity.class);
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("tabCar");
		spec.setContent(intent);
		spec.setIndicator("Vehicle",getResources().getDrawable(getResources().getIdentifier("ic_lock", "drawable", getPackageName())));
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, TabMapActivity.class);
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("tabMap");
		spec.setContent(intent);
		spec.setIndicator("Location",getResources().getDrawable(getResources().getIdentifier("ic_location", "drawable", getPackageName())));
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, TabNotifications.class);
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("tabNotifications");
		spec.setContent(intent);
		spec.setIndicator("Messages",getResources().getDrawable(getResources().getIdentifier("ic_envelope", "drawable", getPackageName())));
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, TabCarsActivity.class);
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("tabCars");
		spec.setContent(intent);
		spec.setIndicator("Settings",getResources().getDrawable(getResources().getIdentifier("ic_gear", "drawable", getPackageName())));
		tabHost.addTab(spec);

		getTabHost().setOnTabChangedListener(this);

		if (tabHost.getCurrentTabTag() == "")
			getTabHost().setCurrentTabByTag("tabInfo");
	}
	
	@Override
	protected void onDestroy() {
		mCommandReceiver.unregister(this);
		super.onDestroy();
	};

	@Override
	public void onNewIntent(Intent launchingIntent) {
		TabHost tabHost = getTabHost();
		if ((launchingIntent != null) && launchingIntent.hasExtra("SetTab")) {
			tabHost.setCurrentTabByTag(launchingIntent.getStringExtra("SetTab"));
		} else {
			tabHost.setCurrentTabByTag("tabInfo");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.main_menu, menu);
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
			this.updateStatus();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private static final String SETTINGS_FILENAME = "OVMSSavedCars.obj";
	
	private TCPTask mTcpTask;
	private ArrayList<CarData> mSavedCars;
	private CarData mCarData;
	private Handler c2dmReportTimerHandler = new Handler();
//	private Handler mPingServerTimerHandler = new Handler();
//	private Exception mLastServerException;
	private AlertDialog mAlertDialog;
	private boolean isLoggedIn;
	public boolean isSuppressServerErrorDialog = false;
	private ProgressDialog mProgressLoginDialog = null;

	private Runnable progressLoginCloseDialog = new Runnable() {
		public void run() {
			try {
				if (mProgressLoginDialog != null) mProgressLoginDialog.dismiss();
			} catch (Exception e) {}
		}
	};
	
	private Runnable progressLoginShowDialog = new Runnable() {
		public void run() {
			mProgressLoginDialog = ProgressDialog.show(MainActivityOld.this, "", "Connecting to OVMS Server...");
		}
	};

	private Runnable serverSocketErrorDialog = new Runnable() {
		public void run() {
			if (isSuppressServerErrorDialog)
				return;
			else if ((mAlertDialog != null) && mAlertDialog.isShowing())
				return; // do not show duplicated alert dialogs

			if (mProgressLoginDialog != null)
				mProgressLoginDialog.hide();

			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivityOld.this);
			builder.setMessage(
					(isLoggedIn) ? String.format("OVMS Server Connection Lost")
							: String.format("Please check the following:\n1. OVMS Server address\n2. Your vehicle ID and passwords"))
							.setTitle("Communications Problem")
							.setCancelable(false)
							.setPositiveButton("Open Settings",
									new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									MainActivityOld.this.getTabHost()
									.setCurrentTabByTag("tabCars");
								}
							});
			mAlertDialog = builder.create();
			mAlertDialog.show();
		}

	};

//	private Runnable pingServer = new Runnable() {
//		public void run() {
//			if (isLoggedIn) {
//				Log.d("OVMS", "Pinging server...");
//				tcpTask.Ping();
//			}
//
//			pingServerTimerHandler.postDelayed(pingServer, 60000);
//		}
//	};

	private Runnable reportC2DMRegistrationID = new Runnable() {
		public void run() {
			// check if tcp connection is still active (it may be closed as the user leaves the program)
			if (mTcpTask == null) return;

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
					|| !mTcpTask.sendCommand(String.format(
							"MP-0 p%s,c2dm,production,%s,%s,%s", uuid,
							mCarData.sel_vehicleid, mCarData.sel_server_password,
							registrationID))) {
				// command not successful, reschedule reporting after 5 seconds
				Log.d("OVMS", "Reporting C2DM ID failed. Rescheduling.");
				c2dmReportTimerHandler.postDelayed(reportC2DMRegistrationID, 5000);
			}
		}
	};

	public void onTabChanged(String currentActivityId) {
		notifyTabUpdate(currentActivityId);
	}

	private void loginComplete() {
		isLoggedIn = true;
		runOnUiThread(progressLoginCloseDialog);
	}

	public void changeCar(CarData car) {
		runOnUiThread(progressLoginShowDialog);

		Log.d("OVMS", "Changed car to: " + car.sel_vehicleid);

		isLoggedIn = false;

		// kill previous connection
		if (mTcpTask != null) {
			Log.v("TCP", "Shutting down pervious TCP connection (ChangeCar())");
			isSuppressServerErrorDialog = true;
			mTcpTask.connClose();
			mTcpTask.cancel(true);
			mTcpTask = null;
			isSuppressServerErrorDialog = false;
		}

		// start new connection
		mCarData = car;
		// reset the paranoid mode flag in car data
		// it will be set again when the TCP task detects paranoid mode messages
		car.sel_paranoid = false;
		mTcpTask = new TCPTask(mCarData);
		Log.v("TCP", "Starting TCP Connection (ChangeCar())");
		mTcpTask.execute();
		getTabHost().setCurrentTabByTag("tabInfo");
		updateStatus();
	}

	public void updateStatus() {
		Log.d("OVMS", "Status Update");
		String currentActivityId = getLocalActivityManager().getCurrentId();
		notifyTabUpdate(currentActivityId);
	}

	private void notifyTabUpdate(String currentActivityId) {
		Log.d("Tab", "Tab change to: " + currentActivityId);

		if (currentActivityId == "tabInfo") {
			TabInfoActivity tab = (TabInfoActivity) getLocalActivityManager().getActivity(currentActivityId);
			tab.RefreshStatus(mCarData);
		} else if (currentActivityId == "tabCar") {
			Log.d("Tab", "Telling tabCar to update");
			TabCarActivity tab = (TabCarActivity) getLocalActivityManager().getActivity(currentActivityId);
			tab.RefreshStatus(mCarData);
		} else if (currentActivityId == "tabMap") {
			TabMapActivity tab = (TabMapActivity) getLocalActivityManager().getActivity(currentActivityId);
			tab.RefreshStatus(mCarData);
		} else if (currentActivityId == "tabNotifications") {
			TabNotifications tab = (TabNotifications) getLocalActivityManager().getActivity(currentActivityId);
			tab.Refresh();
		} else if (currentActivityId == "tabCars") {
			TabCarsActivity tab = (TabCarsActivity) getLocalActivityManager().getActivity(currentActivityId);
			tab.LoadCars(mSavedCars);
		} else
			getTabHost().setCurrentTabByTag("tabInfo");
	}

	private void notifyServerSocketError(Exception e) {
		// only show alerts for specific errors
//		mLastServerException = e;
		runOnUiThread(serverSocketErrorDialog);
	}

	@Override
	protected void onResume() {
		super.onResume();

		//getTabHost().setOnTabChangedListener(this);
		changeCar(mCarData);
	}

	@Override
	protected void onPause() {
		super.onPause();

		//getTabHost().setOnTabChangedListener(null);

		try {
			if (mTcpTask != null) {
				Log.v("TCP", "Shutting down TCP connection");
				mTcpTask.connClose();
				mTcpTask.cancel(true);
				mTcpTask = null;
			}
		} catch (Exception e) {
		}

		saveCars();
	}
	
	public void sendCommand(String pCommand) {
		if (mTcpTask == null) return;
		mTcpTask.sendCommand(String.format("MP-0 C%s", pCommand));
	}

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

	private class TCPTask extends AsyncTask<Void, Integer, Void> {
		public Socket mSocket;
		private CarData mCarData;
		private Cipher mTxCipher, mRxCipher, mPmCipher;

		private byte[] mPmDigestBuf;

		private PrintWriter mOutputstream;
		private BufferedReader mInputstream;

		public TCPTask(CarData pCarData) {
			mCarData = pCarData;
			Log.v("OVMS", "Create TCPTask");
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				connInit();

				String rx, msg;
				while (mSocket.isConnected()) {
					// if (Inputstream.ready()) {
					rx = mInputstream.readLine().trim();
					msg = new String(mRxCipher.update(Base64.decode(rx, 0))).trim();
					Log.d("OVMS", String.format("RX: %s (%s)", msg, rx));

					if (msg.substring(0, 5).equals("MP-0 ")) {
						handleMessage(msg.substring(5));
					} else {
						Log.d("OVMS", "Unknown protection scheme");
						// short pause after receiving message
					}
					
					try {
						Thread.sleep(100, 0);
					} catch (InterruptedException e) {
						// ??
					}
				}
			} catch (SocketException e) {
				// connection lost, attempt to reconnect
				try {
					mSocket.close();
					mSocket = null;
				} catch (Exception ex) {
				}
				connInit();
			} catch (IOException e) {
				if (!isSuppressServerErrorDialog)
					notifyServerSocketError(e);
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
		
		public void ping() {
			String msg = "TX: MP-0 A";
			mOutputstream.println(Base64.encodeToString(mTxCipher.update(msg.getBytes()), Base64.NO_WRAP));
			Log.d("OVMS", msg);
		}

		public void connClose() {
			try {
				if ((mSocket != null) && mSocket.isConnected())
					mSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public boolean sendCommand(String command) {
			Log.i("OVMS", "TX: " + command);
			if (!MainActivityOld.this.isLoggedIn) {
				Log.w("OVMS", "Server not ready. TX aborted.");
				return false;
			}

			try {
				mOutputstream.println(Base64.encodeToString(mTxCipher.update(command.getBytes()), Base64.NO_WRAP));
			} catch (Exception e) {
				notifyServerSocketError(e);
			}
			return true;
		}
		

		private void handleMessage(String msg) {
			char code = msg.charAt(0);
			String cmd = msg.substring(1);

			if (code == 'E') {
				// We have a paranoid mode message
				char innercode = msg.charAt(1);
				if (innercode == 'T') {
					// Set the paranoid token
					Log.v("TCP", "ET MSG Received: " + msg);

					try {
						String pmToken = msg.substring(2);
						Mac pm_hmac = Mac.getInstance("HmacMD5");
						javax.crypto.spec.SecretKeySpec sk = new javax.crypto.spec.SecretKeySpec(
								mCarData.sel_server_password.getBytes(), "HmacMD5");
						pm_hmac.init(sk);
						mPmDigestBuf = pm_hmac.doFinal(pmToken.getBytes());
						Log.d("OVMS", "Paranoid Mode Token Accepted. Entering Privacy Mode.");
					} catch (Exception e) {
						Log.e("ERR", e.getMessage());
						e.printStackTrace();
					}
				} else if (innercode == 'M') {
					// Decrypt the paranoid message
					Log.v("TCP", "EM MSG Received: " + msg);

					code = msg.charAt(2);
					cmd = msg.substring(3);

					byte[] decodedCmd = Base64.decode(cmd,
							Base64.NO_WRAP);

					try {
						mPmCipher = Cipher.getInstance("RC4");
						mPmCipher.init(Cipher.DECRYPT_MODE,
								new javax.crypto.spec.SecretKeySpec(mPmDigestBuf,
										"RC4"));

						// prime ciphers
						String primeData = "";
						for (int cnt = 0; cnt < 1024; cnt++)
							primeData += "0";

						mPmCipher.update(primeData.getBytes());
						cmd = new String(mPmCipher.update(decodedCmd));
					} catch (Exception e) {
						Log.d("ERR", e.getMessage());
						e.printStackTrace();
					}

					// notify main process of paranoid mode detection
					if (!mCarData.sel_paranoid) {
						Log.d("OVMS", "Paranoid Mode Detected");
						mCarData.sel_paranoid = true;
						MainActivityOld.this.updateStatus();
					}
				}
			}

			Log.v("TCP", code + " MSG Received: " + cmd);
			switch (code) {
			case 'Z': // Number of connected cars
			{
				mCarData.server_carsconnected = Integer.parseInt(cmd);
				MainActivityOld.this.updateStatus();
				break;
			}
			case 'S': // STATUS
			{
				String[] dataParts = cmd.split(",\\s*");
				if (dataParts.length >= 8) {
					Log.v("TCP", "S MSG Validated");
					mCarData.car_soc_raw = Integer.parseInt(dataParts[0]);
					mCarData.car_soc = String.format("%d%%",mCarData.car_soc_raw);
					mCarData.car_distance_units_raw = dataParts[1].toString();
					mCarData.car_distance_units = (mCarData.car_distance_units_raw == "M")?"m":"km";
					mCarData.car_speed_units = (mCarData.car_distance_units_raw == "M")?"mph":"kph";
					mCarData.car_charge_linevoltage_raw = Integer.parseInt(dataParts[2]);
					mCarData.car_charge_linevoltage = String.format("%d%s", mCarData.car_charge_linevoltage_raw,"V");
					mCarData.car_charge_current_raw = Integer.parseInt(dataParts[3]);
					mCarData.car_charge_current = String.format("%d%s",mCarData.car_charge_current_raw,"A");
					mCarData.car_charge_voltagecurrent = String.format("%d%s %d%s",
							mCarData.car_charge_linevoltage_raw,"V",
							mCarData.car_charge_current_raw,"A");
					mCarData.car_charge_state_s_raw = dataParts[4].toString();
					mCarData.car_charge_state = mCarData.car_charge_state_s_raw;
					mCarData.car_mode_s_raw = dataParts[5].toString();
					mCarData.car_charge_mode = mCarData.car_mode_s_raw;
					mCarData.car_range_ideal_raw = Integer.parseInt(dataParts[6]);
					mCarData.car_range_ideal = String.format("%d%s",mCarData.car_range_ideal_raw,mCarData.car_distance_units);
					mCarData.car_range_estimated_raw = Integer.parseInt(dataParts[7]);
					mCarData.car_range_estimated = String.format("%d%s",mCarData.car_range_estimated_raw,mCarData.car_distance_units);
					mCarData.stale_status = DataStale.Good;
				}
				if (dataParts.length >= 15) {
					mCarData.car_charge_currentlimit_raw = Integer.parseInt(dataParts[8]);
					mCarData.car_charge_currentlimit = String.format("%d%s",mCarData.car_charge_currentlimit_raw,"A");
					mCarData.car_charge_duration_raw = Integer.parseInt(dataParts[9]);
					mCarData.car_charge_b4byte_raw = Integer.parseInt(dataParts[10]);
					mCarData.car_charge_kwhconsumed = Integer.parseInt(dataParts[11]);
					mCarData.car_charge_substate_i_raw = Integer.parseInt(dataParts[12]);
					mCarData.car_charge_state_i_raw = Integer.parseInt(dataParts[13]);
					mCarData.car_charge_mode_i_raw = Integer.parseInt(dataParts[14]);
				}				
				if (dataParts.length >= 18) {
					mCarData.car_charge_timermode_raw = Integer.parseInt(dataParts[15]);
					mCarData.car_charge_timer = (mCarData.car_charge_timermode_raw > 0);
					mCarData.car_charge_timerstart_raw = Integer.parseInt(dataParts[16]);
					mCarData.car_charge_time = ""; // TODO: Implement later
					mCarData.car_stale_chargetimer_raw = Integer.parseInt(dataParts[17]);
					if (mCarData.car_stale_chargetimer_raw < 0)
						mCarData.stale_chargetimer = DataStale.NoValue;
					else if (mCarData.car_stale_chargetimer_raw == 0)
						mCarData.stale_chargetimer = DataStale.Stale;
					else
						mCarData.stale_chargetimer = DataStale.Good;

				}

				Log.v("TCP", "Notify Vehicle Status Update: " + mCarData.sel_vehicleid);
				if (MainActivityOld.this != null) // OVMSActivity may be null if it is not in foreground
					MainActivityOld.this.updateStatus();
				break;
			}
			case 'T': // TIME
			{
				if (cmd.length() > 0) {
					mCarData.car_lastupdate_raw = Long.parseLong(cmd);
					mCarData.car_lastupdated = new Date(System.currentTimeMillis() - mCarData.car_lastupdate_raw * 1000);
					MainActivityOld.this.updateStatus();
				} else
					Log.w("TCP", "T MSG Invalid");
				break;
			}
			case 'L': // LOCATION
			{
				String[] dataParts = cmd.split(",\\s*");
				if (dataParts.length >= 2) {
					Log.v("TCP", "L MSG Validated");
					mCarData.car_latitude = Double.parseDouble(dataParts[0]);
					mCarData.car_longitude = Double.parseDouble(dataParts[1]);
				}
				if (dataParts.length >= 6) {
					mCarData.car_direction = Integer.parseInt(dataParts[2]);
					mCarData.car_altitude = Integer.parseInt(dataParts[3]);
					mCarData.car_gpslock_raw = Integer.parseInt(dataParts[4]);
					mCarData.car_gpslock = (mCarData.car_gpslock_raw > 0);
					mCarData.car_stale_gps_raw = Integer.parseInt(dataParts[5]);
					if (mCarData.car_stale_gps_raw < 0)
						mCarData.stale_gps = DataStale.NoValue;
					else if (mCarData.car_stale_gps_raw == 0)
						mCarData.stale_gps = DataStale.Stale;
					else
						mCarData.stale_gps = DataStale.Good;
				}

				// Update the visible location
				MainActivityOld.this.updateStatus();
				
				break;
			}
			case 'D': // Doors and switches
			{
				String[] dataParts = cmd.split(",\\s*");
				if (dataParts.length >= 9) {
					Log.v("TCP", "D MSG Validated");
					int dataField = Integer.parseInt(dataParts[0]);
					mCarData.car_doors1_raw = dataField;
					mCarData.car_frontleftdoor_open = ((dataField & 0x1) == 0x1);
					mCarData.car_frontrightdoor_open = ((dataField & 0x2) == 0x2);
					mCarData.car_chargeport_open = ((dataField & 0x4) == 0x4);
					mCarData.car_pilot_present = ((dataField & 0x8) == 0x8);
					mCarData.car_charging = ((dataField & 0x10) == 0x10);
					// bit 5 is always 1
					mCarData.car_handbrake_on = ((dataField & 0x40) == 0x40);
					mCarData.car_started = ((dataField & 0x80) == 0x80);

					dataField = Integer.parseInt(dataParts[1]);
					mCarData.car_doors2_raw = dataField;
					mCarData.car_bonnet_open = ((dataField & 0x40) == 0x40);
					mCarData.car_trunk_open = ((dataField & 0x80) == 0x80);
					mCarData.car_headlights_on = ((dataField & 0x20) == 0x20);
					mCarData.car_valetmode = ((dataField & 0x10) == 0x10);
					mCarData.car_locked = ((dataField & 0x08) == 0x08);

					mCarData.car_lockunlock_raw = Integer.parseInt(dataParts[2]);

					mCarData.car_temp_pem_raw = Integer.parseInt(dataParts[3]);
					mCarData.car_temp_pem = String.format("%d\u00B0C",mCarData.car_temp_pem_raw);
					mCarData.car_temp_motor_raw = Integer.parseInt(dataParts[4]);
					mCarData.car_temp_motor = String.format("%d\u00B0C",mCarData.car_temp_motor_raw);
					mCarData.car_temp_battery_raw = Integer.parseInt(dataParts[5]);
					mCarData.car_temp_battery = String.format("%d\u00B0C",mCarData.car_temp_battery_raw);
					mCarData.car_tripmeter_raw = Integer.parseInt(dataParts[6]);
					mCarData.car_tripmeter = String.format("%d%s",mCarData.car_tripmeter_raw,mCarData.car_distance_units);
					mCarData.car_odometer_raw = Integer.parseInt(dataParts[7]);
					mCarData.car_odometer = String.format("%d%s",mCarData.car_odometer_raw,mCarData.car_distance_units);
					mCarData.car_speed_raw = Integer.parseInt(dataParts[8]);
					mCarData.car_speed = String.format("%d%s", mCarData.car_speed_raw,mCarData.car_speed_units);
					
					mCarData.stale_environment = DataStale.Good;

					if (dataParts.length >= 14) {
						mCarData.car_parking_timer_raw = Long.parseLong(dataParts[9]);
						mCarData.car_parked_time = new Date((new Date()).getTime() - mCarData.car_parking_timer_raw * 1000);

						mCarData.car_temp_ambient_raw = Integer.parseInt(dataParts[10]);
						mCarData.car_temp_ambient = String.format("%d\u00B0C",mCarData.car_temp_ambient_raw);

						dataField = Integer.parseInt(dataParts[11]);
						mCarData.car_doors3_raw =  dataField;
						mCarData.car_coolingpump_on =  ((dataField & 0x02) == 0x02);

						mCarData.car_stale_car_temps_raw = Integer.parseInt(dataParts[12]);
						if (mCarData.car_stale_car_temps_raw < 0)
							mCarData.stale_car_temps = DataStale.NoValue;
						else if (mCarData.car_stale_car_temps_raw == 0)
							mCarData.stale_car_temps = DataStale.Stale;
						else
							mCarData.stale_car_temps = DataStale.Good;

						mCarData.car_stale_ambient_temp_raw = Integer.parseInt(dataParts[13]);
						if (mCarData.car_stale_ambient_temp_raw < 0)
							mCarData.stale_ambient_temp = DataStale.NoValue;
						else if (mCarData.car_stale_ambient_temp_raw == 0)
							mCarData.stale_ambient_temp = DataStale.Stale;
						else
							mCarData.stale_ambient_temp = DataStale.Good;
					}
					if (dataParts.length >= 16) {
						mCarData.car_12vline_voltage = Double.parseDouble(dataParts[14]);
						dataField = Integer.parseInt(dataParts[15]);
						mCarData.car_doors4_raw = dataField;
						mCarData.car_alarm_sounding = ((dataField & 0x02) == 0x02);
					}

					// Update the displayed tab
					MainActivityOld.this.updateStatus();
				}
				break;
			}
			case 'F': // VIN and Firmware
			{
				String[] dataParts = cmd.split(",\\s*");
				if (dataParts.length >= 3) {
					Log.v("TCP", "F MSG Validated");
					mCarData.car_firmware = dataParts[0].toString();
					mCarData.car_vin = dataParts[1].toString();
					mCarData.car_gps_signal_raw = Integer.parseInt(dataParts[2]);

					int car_gsmdbm = 0;
					if (mCarData.car_gps_signal_raw <= 31)
						car_gsmdbm = -113 + (mCarData.car_gps_signal_raw*2);
					mCarData.car_gsm_signal = String.format("%d%s", car_gsmdbm," dbm");
					if ((car_gsmdbm < -121)||(car_gsmdbm >= 0))
						mCarData.car_gsm_bars = 0;
					else if (car_gsmdbm < -107)
						mCarData.car_gsm_bars = 1;
					else if (car_gsmdbm < -98)
						mCarData.car_gsm_bars = 2;
					else if (car_gsmdbm < -87)
						mCarData.car_gsm_bars = 3;
					else if (car_gsmdbm < -76)
						mCarData.car_gsm_bars = 4;
					else
						mCarData.car_gsm_bars = 5;

					mCarData.stale_firmware = DataStale.Good;
				}
				if (dataParts.length >= 5) {
					mCarData.car_canwrite = (Integer.parseInt(dataParts[3])>0);
					mCarData.car_type = dataParts[4].toString();
				}
				if (dataParts.length >= 6) {
					mCarData.car_gsmlock = dataParts[5].toString();
				}

				// Update the displayed tab
				MainActivityOld.this.updateStatus();

			}
			case 'f': // OVMS Server Firmware
			{
				String[] dataParts = cmd.split(",\\s*");
				if (dataParts.length >= 1) {
					Log.v("TCP", "f MSG Validated");
					mCarData.server_firmware = dataParts[0].toString();

					// Update the displayed tab
					MainActivityOld.this.updateStatus();
				}
				break;
			}
			case 'W': // TPMS
			{
				String[] dataParts = cmd.split(",\\s*");
				if (dataParts.length >= 9) {
					Log.v("TCP", "W MSG Validated");
					mCarData.car_tpms_fr_p_raw = Double.parseDouble(dataParts[0]);
					mCarData.car_tpms_fr_t_raw = Double.parseDouble(dataParts[1]);
					mCarData.car_tpms_rr_p_raw = Double.parseDouble(dataParts[2]);
					mCarData.car_tpms_rr_t_raw = Double.parseDouble(dataParts[3]);
					mCarData.car_tpms_fl_p_raw = Double.parseDouble(dataParts[4]);
					mCarData.car_tpms_fl_t_raw = Double.parseDouble(dataParts[5]);
					mCarData.car_tpms_rl_p_raw = Double.parseDouble(dataParts[6]);
					mCarData.car_tpms_rl_t_raw = Double.parseDouble(dataParts[7]);
					mCarData.car_tpms_fl_p = String.format("%.1f%s",mCarData.car_tpms_fl_p_raw, "psi"); 
					mCarData.car_tpms_fr_p = String.format("%.1f%s",mCarData.car_tpms_fr_p_raw, "psi"); 
					mCarData.car_tpms_rl_p = String.format("%.1f%s",mCarData.car_tpms_rl_p_raw, "psi"); 
					mCarData.car_tpms_rr_p = String.format("%.1f%s",mCarData.car_tpms_rr_p_raw, "psi"); 
					mCarData.car_tpms_fl_t = String.format("%.0f%s",mCarData.car_tpms_fl_t_raw, "\u00B0C"); 
					mCarData.car_tpms_fr_t = String.format("%.0f%s",mCarData.car_tpms_fr_t_raw, "\u00B0C"); 
					mCarData.car_tpms_rl_t = String.format("%.0f%s",mCarData.car_tpms_rl_t_raw, "\u00B0C"); 
					mCarData.car_tpms_rr_t = String.format("%.0f%s",mCarData.car_tpms_rr_t_raw, "\u00B0C"); 
					
					mCarData.car_stale_tpms_raw = Integer.parseInt(dataParts[8]);
					if (mCarData.car_stale_tpms_raw < 0)
						mCarData.stale_tpms = DataStale.NoValue;
					else if (mCarData.car_stale_tpms_raw == 0)
						mCarData.stale_tpms = DataStale.Stale;
					else
						mCarData.stale_tpms = DataStale.Good;

					// Update the displayed tab
					MainActivityOld.this.updateStatus();
				}
				break;
			}
			case 'a': {
				Log.v("TCP", "Server acknowleged ping");
				break;
			}
			
			case 'c': {
				Log.i("TCP", "c MSG Validated");
				mCommandReceiver.sendResult(cmd);
				break;
			}
			
			}
		}

		private void connInit() {
			String shared_secret = mCarData.sel_server_password;
			String vehicleID = mCarData.sel_vehicleid;
			String b64tabString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
			char[] b64tab = b64tabString.toCharArray();

			// generate session client token
			Random rnd = new Random();
			String client_tokenString = "";
			for (int cnt = 0; cnt < 22; cnt++) client_tokenString += b64tab[rnd.nextInt(b64tab.length - 1)];

			byte[] client_token = client_tokenString.getBytes();
			try {
				Mac client_hmac = Mac.getInstance("HmacMD5");
				javax.crypto.spec.SecretKeySpec sk = new javax.crypto.spec.SecretKeySpec(
						shared_secret.getBytes(), "HmacMD5");
				client_hmac.init(sk);
				byte[] hashedBytes = client_hmac.doFinal(client_token);
				String client_digest = Base64.encodeToString(hashedBytes, Base64.NO_WRAP);

				mSocket = new Socket(mCarData.sel_server, 6867);

				mOutputstream = new PrintWriter(
						new java.io.BufferedWriter(
								new java.io.OutputStreamWriter(mSocket.getOutputStream())), true);
				Log.d("OVMS", String.format("TX: MP-A 0 %s %s %s",
						client_tokenString, client_digest, vehicleID));

				mOutputstream.println(String.format("MP-A 0 %s %s %s",
						client_tokenString, client_digest, vehicleID));// \r\n

				mInputstream = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

				// get server welcome message
				String[] serverWelcomeMsg = null;
				try {
					serverWelcomeMsg = mInputstream.readLine().trim().split("[ ]+");
				} catch (Exception e) {
					// if server disconnects us here, our auth is probably wrong
					if (!isSuppressServerErrorDialog) notifyServerSocketError(e);
					return;
				}
				Log.d("OVMS", String.format("RX: %s %s %s %s",
						serverWelcomeMsg[0], serverWelcomeMsg[1],
						serverWelcomeMsg[2], serverWelcomeMsg[3]));

				String server_tokenString = serverWelcomeMsg[2];
				byte[] server_token = server_tokenString.getBytes();
				byte[] server_digest = Base64.decode(serverWelcomeMsg[3], 0);

				if (!Arrays.equals(client_hmac.doFinal(server_token), server_digest)) {
					// server hash failed
					Log.d("OVMS", String.format(
							"Server authentication failed. Expected %s Got %s",
							Base64.encodeToString(client_hmac
									.doFinal(serverWelcomeMsg[2].getBytes()),
									Base64.NO_WRAP), serverWelcomeMsg[3]));
				} else {
					Log.d("OVMS", "Server authentication OK.");
				}

				// generate client_key
				String server_client_token = server_tokenString + client_tokenString;
				byte[] client_key = client_hmac.doFinal(server_client_token.getBytes());

				Log.d("OVMS", String.format(
						"Client version of the shared key is %s - (%s) %s",
						server_client_token, toHex(client_key).toLowerCase(),
						Base64.encodeToString(client_key, Base64.NO_WRAP)));

				// generate ciphers
				mRxCipher = Cipher.getInstance("RC4");
				mRxCipher.init(Cipher.DECRYPT_MODE,
						new javax.crypto.spec.SecretKeySpec(client_key, "RC4"));

				mTxCipher = Cipher.getInstance("RC4");
				mTxCipher.init(Cipher.ENCRYPT_MODE,
						new javax.crypto.spec.SecretKeySpec(client_key, "RC4"));

				// prime ciphers
				String primeData = "";
				for (int cnt = 0; cnt < 1024; cnt++) primeData += "0";

				mRxCipher.update(primeData.getBytes());
				mTxCipher.update(primeData.getBytes());

				Log.d("OVMS", String.format(
						"Connected to %s. Ciphers initialized. Listening...",
						mCarData.sel_server));

				loginComplete();

			} catch (UnknownHostException e) {
				notifyServerSocketError(e);
				e.printStackTrace();
			} catch (IOException e) {
				notifyServerSocketError(e);
				e.printStackTrace();
			} catch (NullPointerException e) {
				// notifyServerSocketError(e);
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}

		private String toHex(byte[] bytes) {
			BigInteger bi = new BigInteger(1, bytes);
			return String.format("%0" + (bytes.length << 1) + "X", bi);
		}
	}
	
}
