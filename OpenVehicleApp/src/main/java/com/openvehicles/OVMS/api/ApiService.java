package com.openvehicles.OVMS.api;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Browser;
import android.support.annotation.IntegerRes;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiTask.OnUpdateStatusListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.receiver.AutoStart;
import com.openvehicles.OVMS.ui.MainActivity;
import com.openvehicles.OVMS.ui.settings.GlobalOptionsFragment;
import com.openvehicles.OVMS.utils.CarsStorage;

import java.util.Date;

public class ApiService extends Service implements OnUpdateStatusListener {

	private static final String TAG = "ApiService";
	private static final int ONGOING_NOTIFICATION_ID = 0x4f564d53; // "OVMS"

	private final IBinder mBinder = new ApiBinder();
	private volatile CarData mCarData;
    private ApiTask mApiTask;
	private OnResultCommandListener mOnResultCommandListener;
	private AppPrefes appPrefes;


	@Override
	public void onCreate() {
		super.onCreate();

		appPrefes = new AppPrefes(getApplicationContext(), "ovms");

		// check if the service shall run in foreground:
		boolean startService = appPrefes.getData("option_service_enabled", "0").equals("1");

		if (startService) {

			Intent notificationIntent = new Intent(this, MainActivity.class);
			PendingIntent pendingIntent =
					PendingIntent.getActivity(this, 0, notificationIntent, 0);

			Notification notification =
					(new android.support.v7.app.NotificationCompat.Builder(this))
							.setContentTitle(getText(R.string.service_notification_title))
							.setContentText(getText(R.string.service_notification_text))
							.setTicker(getText(R.string.service_notification_ticker))
							.setSmallIcon(R.drawable.ic_service)
							.setPriority(Notification.PRIORITY_MIN)
							.setContentIntent(pendingIntent)
							.build();

			startForeground(ONGOING_NOTIFICATION_ID, notification);
		}

		// Login for selected car:
		openConnection();

		// Register command receiver:
		Log.d(TAG, "Registering command receiver for Intent: " + getPackageName() + ".SendCommand");
		registerReceiver(mCommandReceiver,
				new IntentFilter(getPackageName() + ".SendCommand"));

		// Register network status receiver:
		registerReceiver(mNetworkStatusReceiver,
				new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

	}
	

	@Override
	public void onDestroy() {

		closeConnection();

		unregisterReceiver(mCommandReceiver);
		unregisterReceiver(mNetworkStatusReceiver);

		super.onDestroy();
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		createNotificationChannel();

		// Reconnect?
		if (!isLoggedIn()) {
			Log.d(TAG, "doing reconnect");
			openConnection();
		}

		return super.onStartCommand(intent, flags, startId);
	}

	private void createNotificationChannel() {
		// Create the NotificationChannel, but only on API 26+ because
		// the NotificationChannel class is new and not in the support library
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = getString(R.string.app_name);
			String description = getString(R.string.channel_description);
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel("default", name, importance);
			channel.setDescription(description);
			// Register the channel with the system; you can't change the importance
			// or other notification behaviors after this
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind");

		// Reconnect?
		if (!isLoggedIn()) {
			Log.d(TAG, "doing reconnect");
			openConnection();
		}

		return mBinder;
	}

	public class ApiBinder extends Binder {
		public ApiService getService() {
			return ApiService.this;
		}
	}


	/**
	 * closeConnection: terminate ApiTask
	 */
	public void closeConnection() {
		try {
			if (mApiTask != null) {
				Log.v(TAG, "closeConnection: shutting down TCP connection");
				mApiTask.cancel(true);
				mApiTask = null;
			}
		} catch (Exception e) {
			Log.e(TAG, "closeConnection: ERROR stopping ApiTask", e);
		}
	}


	/**
	 * openConnection: start ApiTask
	 */
	public void openConnection() {
		if (mCarData == null) {
			Log.v(TAG, "openConnection: getting CarData");
			mCarData = CarsStorage.get().getSelectedCarData();
		}
		if (mApiTask != null) {
			Log.v(TAG, "openConnection: closing previous connection");
			closeConnection();
		}
		if (mCarData != null) {
			Log.v(TAG, "openConnection: starting TCP Connection");

			// reset the paranoid mode flag in car data
			// it will be set again when the TCP task detects paranoid mode messages
			mCarData.sel_paranoid = false;

			// start the new ApiTask:
			mApiTask = new ApiTask(getApplicationContext(), mCarData, this);

			if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
				mApiTask.execute();
				// Note: this may take a while to start when changing a car
				// 	because the old ApiTask waits until the next protocol
				// 	message arrives
			} else {
				mApiTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				// Note: this will start immediately
			}
		}
	}


	/**
	 * changeCar: terminate existing connection if any, connect to car
	 *
	 * @param pCarData
	 */
	public void changeCar(CarData pCarData) {
		Log.i(TAG, "changeCar: changing car to: " + pCarData.sel_vehicleid);
		closeConnection();
		mCarData = pCarData;
		openConnection();
	}

	
	public void sendCommand(int pResIdMessage, String pCommand, OnResultCommandListener pOnResultCommandListener) {
		sendCommand(getString(pResIdMessage), pCommand, pOnResultCommandListener);
	}

	public void sendCommand(String pMessage, String pCommand, OnResultCommandListener pOnResultCommandListener) {
		if (mApiTask == null) return;

		mOnResultCommandListener = pOnResultCommandListener;
		mApiTask.sendCommand(String.format("MP-0 C%s", pCommand));
		Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show();
	}
	
	public boolean sendCommand(String pCommand, OnResultCommandListener pOnResultCommandListener) {
		if (mApiTask == null || TextUtils.isEmpty(pCommand)) return false;
		
		mOnResultCommandListener = pOnResultCommandListener;
		return mApiTask.sendCommand(pCommand.startsWith("MP-0") ? pCommand : String.format("MP-0 C%s", pCommand));
	}
	
	public void cancelCommand() {
		mOnResultCommandListener = null;
	}


	@Override
	public void onUpdateStatus(char msgCode, String msgData) {
		Log.d(TAG, "onUpdateStatus " + msgCode);

		// Update internal observers:
		ApiObservable.get().notifyUpdate(mCarData);

		// Send system broadcast for Automagic / Tasker / ...
		if (appPrefes.getData("option_broadcast_enabled", "0").equals("1")) {
			// for these message codes:
			String broadcastCodes = appPrefes.getData("option_broadcast_codes",
					GlobalOptionsFragment.defaultBroadcastCodes);
			if (broadcastCodes.indexOf(msgCode) >= 0) {

				Log.d(TAG, "onUpdateStatus " + msgCode + ": sending broadcast");
				Intent intent = new Intent(getPackageName() + ".Update");

				intent.putExtra("sel_server", mCarData.sel_server);
				intent.putExtra("sel_vehicleid", mCarData.sel_vehicleid);
				intent.putExtra("sel_vehicle_label", mCarData.sel_vehicle_label);

				intent.putExtra("msg_code", "" + msgCode);
				intent.putExtra("msg_data", msgData);

				if (msgCode == 'P' && msgData.length() > 1) {
					intent.putExtra("msg_notify_class", msgData.substring(0, 1));
					intent.putExtra("msg_notify_text", msgData.substring(1));
				} else {
					intent.putExtra("msg_notify_class", "");
					intent.putExtra("msg_notify_text", "");
				}

				// Add car status:
				intent.putExtras(mCarData.getBroadcastData());

				sendBroadcast(intent);
			}
		}
	}

	@Override
	public void onServerSocketError(Throwable e) {
		Intent intent = new Intent(getPackageName() + ".ApiEvent");
		intent.putExtra("onServerSocketError", e);
		intent.putExtra("message", getString((mApiTask != null && mApiTask.isLoggedIn())
				? R.string.err_connection_lost
				: R.string.err_check_following));
		sendBroadcast(intent);
	}

	@Override
	public void onResultCommand(String pCmd) {

		if (TextUtils.isEmpty(pCmd))
			return;

		String[] data = pCmd.split(",\\s*");
		if (data.length < 2)
			return;

		if (mOnResultCommandListener != null) {
			mOnResultCommandListener.onResultCommand(data);
		}

		// Check broadcast API configuration:
		if (appPrefes.getData("option_commands_enabled", "0").equals("1")) {

			Log.i(TAG, "onResultCommand: sending broadcast: " +
					getPackageName() + ".CommandResult = " + pCmd);
			Intent intent = new Intent(getPackageName() + ".CommandResult");

			intent.putExtra("sel_server", mCarData.sel_server);
			intent.putExtra("sel_vehicleid", mCarData.sel_vehicleid);
			intent.putExtra("sel_vehicle_label", mCarData.sel_vehicle_label);

			intent.putExtra("cmd_code", data[0]);
			intent.putExtra("cmd_error", Integer.parseInt(data[1]));
			int offset = data[0].length() + data[1].length() + 2;
			if (pCmd.length() > offset)
				intent.putExtra("cmd_data", pCmd.substring(offset));
			else
				intent.putExtra("cmd_data", "");
			intent.putExtra("cmd_result", data);

			sendBroadcast(intent);
		}
	}

	@Override
	public void onLoginBegin() {
		Log.d(TAG, "onLoginBegin");
		
		Intent intent = new Intent(getPackageName() + ".ApiEvent");
		intent.putExtra("onLoginBegin", true);
		sendBroadcast(intent);
	}

	@Override
	public void onLoginComplete() {
		Log.d(TAG, "onLoginComplete");
		
		Intent intent = new Intent(getPackageName() + ".ApiEvent");
		intent.putExtra("onLoginComplete", true);
		sendBroadcast(intent);
	}
	

	public boolean isLoggedIn() {
		return (mApiTask != null && mApiTask.isLoggedIn());
	}
	
	public CarData getCarData() {
		return mCarData;
	}
	
	public CarData getLoggedInCarData() {
		return isLoggedIn() ? mCarData : null;
	}
	

	/**
	 * Check for network availability.
	 *
	 * @return true if any network is available
	 */
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return (info != null && info.isConnectedOrConnecting());
	}


	/**
	 * Broadcast Receiver for Network Connectivity Changes
	 */
	private final BroadcastReceiver mNetworkStatusReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "mNetworkStatusReceiver: new state: "
					+ (isOnline() ? "ONLINE" : "OFFLINE"));
			if (!isOnline() && mApiTask != null)
				closeConnection();
			else if (isOnline() && mApiTask == null)
				openConnection();
		}
	};


	/**
	 * Broadcast Command Receiver for Automagic / Tasker / ...
	 *
	 * Intent extras:
	 * - sel_vehicleid
	 * - sel_server_password
	 * - msg_command (optional) (fixed to "C" sends, max length 199 chars)
	 *
	 * Car will be changed as necessary (persistent change).
	 * Fails silently on errors.
	 * Results will be broadcasted by onResultCommand().
	 *
	 */
	private final BroadcastReceiver mCommandReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "CommandReceiver: received " + intent.toString());

			// Check API configuration:
			if (appPrefes.getData("option_commands_enabled", "0").equals("0")) {
				Log.e(TAG, "CommandReceiver: disabled");
				return;
			}

			// Get command parameters:
			String vehID = intent.getStringExtra("sel_vehicleid");
			String vehPassword = intent.getStringExtra("sel_server_password");
			String vehCommand = intent.getStringExtra("msg_command");

			// Verify command:
			if (vehCommand != null && vehCommand.length() > 199) {
				Log.e(TAG, "CommandReceiver: invalid command");
				return;
			}

			// Check vehicle access:
			CarData vehCarData = CarsStorage.get().getCarById(vehID);
			if (vehCarData == null ||
					vehCarData.sel_server_password == null ||
					vehCarData.sel_server_password.equals("") ||
					!vehCarData.sel_server_password.equals(vehPassword)) {
				// Unknown car
				Log.e(TAG, "CommandReceiver: unknown car / wrong password");
				return;
			}

			// Change car if necessary:
			if (!mCarData.sel_vehicleid.equals(vehCarData.sel_vehicleid)) {
				Log.i(TAG, "CommandReceiver: changing car to: " + vehID);
				changeCar(vehCarData);
				CarsStorage.get().setSelectedCarId(vehID);
			}

			// Send command:
			if (vehCommand != null && vehCommand.length() > 0) {
				Log.i(TAG, "CommandReceiver: sending command: " + vehCommand);
				if (!mApiTask.sendCommand(String.format("MP-0 C%s", vehCommand))) {
					Log.e(TAG, "CommandReceiver: sendCommand failed");
				}
			}
		}
	};


}
