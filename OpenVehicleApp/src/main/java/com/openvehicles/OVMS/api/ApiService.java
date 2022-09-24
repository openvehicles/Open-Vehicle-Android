package com.openvehicles.OVMS.api;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiTask.ApiTaskListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.MainActivity;
import com.openvehicles.OVMS.utils.CarsStorage;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ApiService extends Service implements ApiTaskListener, ApiObserver {

	private static final String TAG = "ApiService";
	private static final int ONGOING_NOTIFICATION_ID = 0x4f564d53; // "OVMS"

	// Internal broadcast actions:
	public static final String ACTION_APIEVENT = "com.openvehicles.OVMS.ApiEvent";
	public static final String ACTION_PING = "com.openvehicles.OVMS.service.intent.PING";
	public static final String ACTION_ENABLE = "com.openvehicles.OVMS.service.intent.ENABLE";
	public static final String ACTION_DISABLE = "com.openvehicles.OVMS.service.intent.DISABLE";

	// System broadcast actions for Tasker et al:
	public static final String ACTION_UPDATE = "com.openvehicles.OVMS.Update";
	public static final String ACTION_NOTIFICATION = "com.openvehicles.OVMS.Notification";
	public static final String ACTION_SENDCOMMAND = "com.openvehicles.OVMS.SendCommand";
	public static final String ACTION_COMMANDRESULT = "com.openvehicles.OVMS.CommandResult";

	private static final int PING_INTERVAL = 5; // Minutes

	private final IBinder mBinder = new ApiBinder();
	private volatile CarData mCarData;
    private ApiTask mApiTask;
	private OnResultCommandListener mOnResultCommandListener;
	private AppPrefes appPrefes;

	private boolean mEnabled = false;	// Service in "foreground" mode
	private boolean mStopped = false;	// Service stopped

	private ConnectivityManager mConnectivityManager;
	private AlarmManager mAlarmManager;
	private volatile Looper mServiceLooper;
	private volatile ApiServiceHandler mServiceHandler;

	@Override
	public void onCreate() {
		super.onCreate();
		mStopped = false;

		mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		appPrefes = new AppPrefes(this, "ovms");

		createNotificationChannel();

		// check if the service shall run in foreground:
		boolean foreground = appPrefes.getData("option_service_enabled", "0").equals("1");
		if (foreground) {
			enableService();
		}

		// Register command receiver:
		Log.d(TAG, "Registering command receiver for Intent: " + ACTION_SENDCOMMAND);
		registerReceiver(mCommandReceiver,
				new IntentFilter(ACTION_SENDCOMMAND));

		// Register network status receiver:
		registerReceiver(mNetworkStatusReceiver,
				new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

		// Start intent handler thread:
		final HandlerThread thread = new HandlerThread("ApiServiceHandler");
		thread.start();
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ApiServiceHandler(mServiceLooper);

		// Register action receiver:
		IntentFilter actionFilter = new IntentFilter();
		actionFilter.addAction(ACTION_ENABLE);
		actionFilter.addAction(ACTION_DISABLE);
		registerReceiver(mActionReceiver, actionFilter);

		// Register as an ApiObserver:
		ApiObservable.get().addObserver(this);

		// Login for selected car:
		openConnection();

		// Schedule ping:
		final PendingIntent pi = PendingIntent.getService(this, 0,
				new Intent(ACTION_PING), PendingIntent.FLAG_UPDATE_CURRENT);
		long pingIntervalMs = PING_INTERVAL * 60 * 1000;
		mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() + pingIntervalMs, pingIntervalMs, pi);

		sendApiEvent("ServiceCreated");
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy: close");
		mStopped = true;

		// Stop ping:
		final PendingIntent pi = PendingIntent.getService(this, 0,
				new Intent(ACTION_PING), PendingIntent.FLAG_UPDATE_CURRENT);
		mAlarmManager.cancel(pi);

		// Logout:
		closeConnection();

		unregisterReceiver(mCommandReceiver);
		unregisterReceiver(mNetworkStatusReceiver);
		unregisterReceiver(mActionReceiver);

		ApiObservable.get().deleteObserver(this);

		sendApiEvent("ServiceDestroyed");

		Log.d(TAG, "onDestroy: done");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand: " + intent);

		// Forward intent to our handler thread:
		final Message msg = mServiceHandler.obtainMessage();
		msg.arg1 = startId;
		msg.obj = intent;
		mServiceHandler.sendMessage(msg);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind:" + intent);
		checkConnection();
		return mBinder;
	}

	public void onStop() {
		if (!mEnabled) {
			Log.d(TAG, "onStop (not enabled)");
			mStopped = true;
			sendApiEvent("ServiceStopped");
		}
	}

	public void onStart() {
		if (mStopped) {
			Log.d(TAG, "onStart");
			mStopped = false;
			sendApiEvent("ServiceStarted");
		}
	}

	public class ApiBinder extends Binder {
		public ApiService getService() {
			return ApiService.this;
		}
	}


	private final class ApiServiceHandler extends Handler {
		public ApiServiceHandler(final Looper looper) {
			super(looper);
		}
		@Override
		public void handleMessage(final Message msg) {
			handleIntent((Intent) msg.obj);
		}
	}

	private final BroadcastReceiver mActionReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			handleIntent(intent);
		}
	};

	private void handleIntent(final Intent intent) {
		Log.d(TAG, "handleIntent: " + intent);
		if (intent == null) return;
		String action = intent.getAction();
		if (ACTION_PING.equals(action)) {
			checkConnection();
		}
		else if (ACTION_ENABLE.equals(action)) {
			enableService();
		}
		else if (ACTION_DISABLE.equals(action)) {
			disableService();
		}
	}


	/**
	 * checkConnection: reconnect if necessary
	 */
	public void checkConnection() {
		if (!isLoggedIn()) {
			Log.i(TAG, "checkConnection: doing reconnect");
			openConnection();
		} else {
			Log.i(TAG, "checkConnection: connection OK");
		}
	}

	/**
	 * closeConnection: terminate ApiTask
	 */
	public synchronized void closeConnection() {
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
	 * enableService: ask Android to keep this service running ("foreground") after stopping the MainActivity
	 */
	private void enableService() {
		Log.i(TAG, "enableService: starting foreground mode");
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent =
				PendingIntent.getActivity(this, 0, notificationIntent, 0);
		Notification notification =
				(new androidx.core.app.NotificationCompat.Builder(this, "default"))
						.setContentTitle(getText(R.string.service_notification_title))
						.setContentText(getText(R.string.service_notification_text))
						.setTicker(getText(R.string.service_notification_ticker))
						.setSmallIcon(R.drawable.ic_service)
						.setPriority(Notification.PRIORITY_MIN)
						.setContentIntent(pendingIntent)
						.build();
		startForeground(ONGOING_NOTIFICATION_ID, notification);
		mEnabled = true;
		mStopped = false;
		sendApiEvent("ServiceEnabled");
	}

	/**
	 * disableService: ask Android to stop this service when the MainActivity is stopped
	 */
	private void disableService() {
		Log.i(TAG, "disableService: stopping foreground mode");
		stopForeground(true);
		mEnabled = false;
		sendApiEvent("ServiceDisabled");
	}


	/**
	 * openConnection: start ApiTask
	 */
	public synchronized void openConnection() {
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
			mApiTask = new ApiTask(this, mCarData, this);
			mApiTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}


	public boolean isLoggedIn() {
		return (mApiTask != null && mApiTask.isLoggedIn());
	}

	public CarData getCarData() {
		return mCarData;
	}


	/**
	 * Check for service / network availability.
	 *
	 * @return true if service is running and has network access
	 */
	public boolean isOnline() {
		if (mStopped)
			return false;
		NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
		return (info != null && info.isConnected());
	}


	/**
	 * Broadcast Receiver for Network Connectivity Changes
	 */
	private final BroadcastReceiver mNetworkStatusReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(TAG, "mNetworkStatusReceiver: new state: "
					+ (isOnline() ? "ONLINE" : "OFFLINE"));
			if (!isOnline() && mApiTask != null) {
				closeConnection();
			}
			else if (isOnline() && mApiTask == null) {
				openConnection();
			}
		}
	};


	/**
	 * changeCar: terminate existing connection if any, connect to car
	 *
	 * @param pCarData
	 */
	public void changeCar(CarData pCarData) {
		Log.i(TAG, "changeCar: changing car to: " + pCarData.sel_vehicleid);
		closeConnection();
		mCarData = pCarData;
		ApiObservable.get().notifyUpdate(mCarData);
		openConnection();
	}

	
	public void sendCommand(int pResIdMessage, String pCommand, OnResultCommandListener pOnResultCommandListener) {
		sendCommand(getString(pResIdMessage), pCommand, pOnResultCommandListener);
	}

	public void sendCommand(String pMessage, String pCommand, OnResultCommandListener pOnResultCommandListener) {
		if (mApiTask == null) return;

		mOnResultCommandListener = pOnResultCommandListener;
		mApiTask.sendMessage(String.format("MP-0 C%s", pCommand));
		Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show();
	}
	
	public boolean sendCommand(String pCommand, OnResultCommandListener pOnResultCommandListener) {
		if (mApiTask == null || TextUtils.isEmpty(pCommand)) return false;
		
		mOnResultCommandListener = pOnResultCommandListener;
		return mApiTask.sendMessage(pCommand.startsWith("MP-0") ? pCommand : String.format("MP-0 C%s", pCommand));
	}
	
	public void cancelCommand() {
		mOnResultCommandListener = null;
	}


	public class ApiEvent extends Intent {
		public ApiEvent(String event) {
			super(ACTION_APIEVENT);
			setPackage(getPackageName());
			putExtra("event", event);
			putExtra("isOnline", isOnline());
			putExtra("isLoggedIn", isLoggedIn());
		}
		public ApiEvent(String event, Serializable detail) {
			this(ACTION_APIEVENT);
			putExtra("detail", detail);
		}
		public void send() {
			sendBroadcast(this);
		}
	}

	public void sendApiEvent(String event) {
		new ApiEvent(event).send();
	}

	/* sendBroadcast debug helper
	@Override
	public void sendBroadcast(Intent intent) {
		Log.v(TAG, "sendBroadcast: " + intent + ":" + intent.getExtras().toString());
		super.sendBroadcast(intent);
	}
	*/

	@Override
	public void onUpdateStatus(char msgCode, String msgData) {
		Log.d(TAG, "onUpdateStatus " + msgCode);
		// Route the update through the ApiObservable queue to merge multiple
		//  adjacent server messages into one broadcast:
		ApiObservable.get().notifyUpdate(mCarData);
	}

	@Override
	public void onPushNotification(char msgClass, String msgText) {
		// Send system broadcast for Automagic / Tasker / ...
		if (appPrefes.getData("option_broadcast_enabled", "0").equals("1")) {
			Log.d(TAG, "onPushNotification class=" + msgClass + ": sending broadcast");
			Intent intent = new Intent(ACTION_NOTIFICATION);
			intent.putExtra("sel_server", mCarData.sel_server);
			intent.putExtra("sel_vehicleid", mCarData.sel_vehicleid);
			intent.putExtra("sel_vehicle_label", mCarData.sel_vehicle_label);
			intent.putExtra("msg_notify_class", "" + msgClass);
			intent.putExtra("msg_notify_text", msgText);
			intent.putExtras(mCarData.getBroadcastData());
			sendBroadcast(intent);
			sendKustomBroadcast(intent);
		}
	}

	// ApiObserver interface:
	@Override
	public void update(CarData pCarData) {
		// Update ApiEvent listeners (App Widgets):
		sendApiEvent("UpdateStatus");

		// Send system broadcast for Automagic / Tasker / ...
		if (appPrefes.getData("option_broadcast_enabled", "0").equals("1")) {

			Log.d(TAG, "update: sending system broadcast " + ACTION_UPDATE);
			Intent intent = new Intent(ACTION_UPDATE);

			intent.putExtra("sel_server", mCarData.sel_server);
			intent.putExtra("sel_vehicleid", mCarData.sel_vehicleid);
			intent.putExtra("sel_vehicle_label", mCarData.sel_vehicle_label);

			// Avoid issues with scripts relying on the existence of these:
			intent.putExtra("msg_code", "");
			intent.putExtra("msg_data", "");
			intent.putExtra("msg_notify_class", "");
			intent.putExtra("msg_notify_text", "");

			// Add car status:
			intent.putExtras(mCarData.getBroadcastData());

			sendBroadcast(intent);
			sendKustomBroadcast(intent);
		}
	}

	// ApiObserver interface:
	@Override
	public void onServiceAvailable(ApiService pService) {
		// nop
	}

	@Override
	public void onServerSocketError(Throwable error) {
		ApiEvent apiEvent = new ApiEvent("ServerSocketError", error);
		apiEvent.putExtra("message", getString((mApiTask != null && mApiTask.isLoggedIn())
						? R.string.err_connection_lost
						: R.string.err_check_following));
		apiEvent.send();
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

			Log.v(TAG, "onResultCommand: sending broadcast " + ACTION_COMMANDRESULT + ": " + pCmd);
			Intent intent = new Intent(ACTION_COMMANDRESULT);

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
			sendKustomBroadcast(intent);
		}
	}

	@Override
	public void onLoginBegin() {
		Log.d(TAG, "onLoginBegin");
		sendApiEvent("LoginBegin");
	}

	@Override
	public void onLoginComplete() {
		Log.d(TAG, "onLoginComplete");
		sendApiEvent("LoginComplete");
	}
	

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
				if (!mApiTask.sendMessage(String.format("MP-0 C%s", vehCommand))) {
					Log.e(TAG, "CommandReceiver: sendCommand failed");
				}
			}
		}
	};


	/**
	 * Create NotificationChannel "default" for Android >= 8.0
	 * This is done here in ApiService because the service may start
	 * on boot, independant of the MainActivity.
 	 */
	private void createNotificationChannel() {
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

	/**
	 * Kustom support:
	 *  The KustomWidget App (KWGT) cannot read generic intent extras, data
	 *  needs to be sent as string arrays. See:
	 *  https://help.kustom.rocks/i195-send-variables-to-kustom-via-broadcast
	 */
	public static final String KUSTOM_ACTION = "org.kustom.action.SEND_VAR";
	public static final String KUSTOM_ACTION_EXT_NAME = "org.kustom.action.EXT_NAME";
	public static final String KUSTOM_ACTION_VAR_NAME_ARRAY = "org.kustom.action.VAR_NAME_ARRAY";
	public static final String KUSTOM_ACTION_VAR_VALUE_ARRAY = "org.kustom.action.VAR_VALUE_ARRAY";

	public void sendKustomBroadcast(Intent srcIntent) {
		Bundle extras = srcIntent.getExtras();
		if (extras == null) return;

		// create Kustom intent:
		Intent kIntent = new Intent(KUSTOM_ACTION);
		kIntent.putExtra(KUSTOM_ACTION_EXT_NAME, "ovms");

		// create string arrays from extras:
		ArrayList<String> names = new ArrayList<String>(extras.size());
		ArrayList<String> values = new ArrayList<String>(extras.size());
		for (String key : extras.keySet()) {
			Object value = extras.get(key);
			if (value == null) {
				names.add(key);
				values.add("");
			}
			else if (value.getClass().isArray()) {
				// unroll arrays by adding index suffix:
				int cnt = Array.getLength(value);
				names.add(key + "_cnt");
				values.add("" + cnt);
				for (int i = 0; i < cnt; i++) {
					names.add(key + "_" + (i+1));
					values.add(Array.get(value, i).toString());
				}
			} else {
				names.add(key);
				values.add(value.toString());
			}
		}

		// send to Kustom:
		String[] name_array = names.toArray(new String[0]);
		String[] value_array = values.toArray(new String[0]);
		kIntent.putExtra(KUSTOM_ACTION_VAR_NAME_ARRAY, name_array);
		kIntent.putExtra(KUSTOM_ACTION_VAR_VALUE_ARRAY, value_array);
		sendBroadcast(kIntent);
	}

}
