package com.openvehicles.OVMS.api;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.widget.Toast;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiTask.ApiTaskListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.DialogActivity;
import com.openvehicles.OVMS.ui.MainActivity;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.NotificationData;
import com.openvehicles.OVMS.utils.Sys;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

	// MP command response result codes:
	public static final int COMMAND_RESULT_OK = 0;
	public static final int COMMAND_RESULT_FAILED = 1;
	public static final int COMMAND_RESULT_UNSUPPORTED = 2;
	public static final int COMMAND_RESULT_UNIMPLEMENTED = 3;

	private static final int PING_INTERVAL = 5; // Minutes

	private final IBinder mBinder = new ApiBinder();
	private volatile CarData mCarData;
	private ApiTask mApiTask;
	private OnResultCommandListener mOnResultCommandListener;
	private AppPrefes appPrefes;
	private Database mDatabase;

	private boolean mEnabled = false;    // Service in "foreground" mode
	private boolean mStopped = false;    // Service stopped

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
		mDatabase = new Database(getApplicationContext());

		createNotificationChannel();

		// check if the service shall run in foreground:
		boolean foreground = appPrefes.getData("option_service_enabled", "0").equals("1");
		if (foreground) {
			enableService();
		}

		// Register command receiver:
		Log.d(TAG, "Registering command receiver for Intent: " + ACTION_SENDCOMMAND);
		registerReceiver(mCommandReceiver, new IntentFilter(ACTION_SENDCOMMAND));

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
				new Intent(ACTION_PING), Sys.getMutableFlags(PendingIntent.FLAG_UPDATE_CURRENT, false));
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
				new Intent(ACTION_PING), Sys.getMutableFlags(PendingIntent.FLAG_UPDATE_CURRENT, false));
		mAlarmManager.cancel(pi);

		// Logout:
		closeConnection();

		unregisterReceiver(mCommandReceiver);
		unregisterReceiver(mNetworkStatusReceiver);
		unregisterReceiver(mActionReceiver);

		ApiObservable.get().deleteObserver(this);

		mDatabase.close();

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
		} else if (ACTION_ENABLE.equals(action)) {
			enableService();
		} else if (ACTION_DISABLE.equals(action)) {
			disableService();
		}
	}


	/**
	 * checkConnection: reconnect if necessary
	 */
	public void checkConnection() {
		if (!isLoggedIn()) {
			if (!isOnline()) {
				Log.i(TAG, "checkConnection: no network, skipping reconnect");
			} else {
				Log.i(TAG, "checkConnection: doing reconnect");
				openConnection();
			}
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
				sendApiEvent("UpdateStatus");
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
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, Sys.getMutableFlags(0, false));

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
			} else if (isOnline() && mApiTask == null) {
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


	public boolean sendCommand(int pResIdMessage, String pCommand, OnResultCommandListener pOnResultCommandListener) {
		return sendCommand(getString(pResIdMessage), pCommand, pOnResultCommandListener);
	}

	public boolean sendCommand(String pMessage, String pCommand, OnResultCommandListener pOnResultCommandListener) {
		if (sendCommand(pCommand, pOnResultCommandListener)) {
			if (!TextUtils.isEmpty(pMessage)) {
				// TODO: remove UI interaction from Service
				Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show();
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean sendCommand(String pCommand, OnResultCommandListener pOnResultCommandListener) {
		if (mApiTask == null || TextUtils.isEmpty(pCommand)) return false;
		// TODO: use command request queue, move result listener into command request
		mOnResultCommandListener = pOnResultCommandListener;
		return mApiTask.sendMessage(pCommand.startsWith("MP-0") ? pCommand : String.format("MP-0 C%s", pCommand));
	}

	public void cancelCommand(OnResultCommandListener pOnResultCommandListener) {
		// TODO: use command request queue, move result listener into command request
		if (mOnResultCommandListener == pOnResultCommandListener || pOnResultCommandListener == null)
			mOnResultCommandListener = null;
	}

	/**
	 * convertUserCommand: convert user command input to the corresponding MP command
	 *
	 * User input syntax support:
	 * 	- MMI/USSD commands: prefix "*", example: "*100#"
	 * 	- Modem commands:    prefix "@", example: "@ATI"
	 * 	- MP MSG commands:   prefix "#", example: "#31"
	 * 	- Text commands:     everything else, example: "stat"
	 *
	 * @param userCmd - user command input
	 * @return MP command
	 */
	@NonNull
	public static String makeMsgCommand(@Nullable String userCmd) {
		String mpCmd;
		if (userCmd == null || userCmd.isEmpty()) {
			mpCmd = "";
		}
		else if (userCmd.startsWith("*")) {
			// MMI/USSD command
			mpCmd = "41," + userCmd;
		}
		else if (userCmd.startsWith("@")) {
			// Modem command
			mpCmd = "49," + userCmd.substring(1);
		}
		else if (userCmd.startsWith("#")) {
			// MSG protocol command
			mpCmd = userCmd.substring(1);
		}
		else {
			// Text (former SMS) command
			mpCmd = "7," + userCmd;
		}
		return mpCmd;
	}

	@NonNull
	public static String getCommandName(final int cmdCode) {
		// TODO: localize command names?
		switch (cmdCode) {
			case 1:		return "Get Features";
			case 2:		return "Set Feature";
			case 3:		return "Get Parameters";
			case 4:		return "Set Parameter";
			case 5:		return "Reboot";
			case 6:		return "Status";
			case 7:		return "Command";
			case 10:	return "Set Charge Mode";
			case 11:	return "Start Charge";
			case 12:	return "Stop Charge";
			case 15:	return "Set Charge Current";
			case 16:	return "Set Charge Parameters";
			case 17:	return "Set Charge Timer";
			case 18:	return "Wakeup Car";
			case 19:	return "Wakeup Subsystem";
			case 20:	return "Lock Car";
			case 21:	return "Set Valet Mode";
			case 22:	return "Unlock Car";
			case 23:	return "Clear Valet Mode";
			case 24:	return "Home Link";
			case 25:	return "Cooldown";
			case 30:	return "Get Usage";
			case 31:	return "Get Data Summary";
			case 32:	return "Get Data Records";
			case 40:	return "Send SMS";
			case 41:	return "Send MMI/USSD";
			case 49:	return "Modem Command";
			default:	return "#" + cmdCode;
		}
	}

	public static boolean hasMultiRowResponse(int cmdCode) {
		return (cmdCode == 1 || cmdCode == 3 || cmdCode == 30 || cmdCode == 31 || cmdCode == 32);
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
			this(event);
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
		Log.v(TAG, "onUpdateStatus " + msgCode);
		// Route the update through the ApiObservable queue to merge multiple
		//  adjacent server messages into one broadcast:
		ApiObservable.get().notifyUpdate(mCarData);
	}

	@Override
	public void onPushNotification(char msgClass, String msgText) {
		// This callback only receives MP push notifications for the currently selected vehicle.
		// See MyGcmListenerService for system notification broadcasting.
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
			intent.putExtras(mCarData.getBroadcastData());
			sendBroadcast(intent);
			sendKustomBroadcast(this, intent);
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
	public void onResultCommand(String pCmdResponse) {

		if (TextUtils.isEmpty(pCmdResponse)) {
			Log.d(TAG, "onResultCommand: response: null");
			return;
		}
		Log.d(TAG, "onResultCommand: response: " + pCmdResponse.replace('\r', '|'));

		// Parse command response:
		int cmd_code, cmd_error;
		String cmd_data = "";
		String[] data = pCmdResponse.split(",\\s*");
		try {
			cmd_code = Integer.parseInt(data[0]);
			cmd_error = Integer.parseInt(data[1]);
			int offset = data[0].length() + data[1].length() + 2;
			if (pCmdResponse.length() > offset)
				cmd_data = pCmdResponse.substring(offset);
		} catch (Exception e) {
			Log.e(TAG, "onResultCommand: invalid response: " + pCmdResponse);
			return;
		}

		if (mOnResultCommandListener != null) {

			// TODO: use command request queue, move result listener into command request
			// Forward command response to listener:
			//Log.d(TAG, "onResultCommand: forward to listener");
			mOnResultCommandListener.onResultCommand(data);

		} else {

			//Log.d(TAG, "onResultCommand: no listener, display to user");
			boolean displayResult = true;

			if (cmd_error == COMMAND_RESULT_OK && hasMultiRowResponse(cmd_code)) {
				try {
					int recNr = Integer.parseInt(data[2]);
					// Feature & parameter lists begin at recNr 0, other at 1:
					if (recNr > 0 && (cmd_code == 1 || cmd_code == 3))
						displayResult = false;
					else if (recNr > 1)
						displayResult = false;
				} catch (Exception e) {
					// if data[2] is no integer, display the message
				}
			}

			if (displayResult) {

				String title = mCarData.sel_vehicleid + ":";	// TODO: add command sent?

				// Add command to notifications:
				if (cmd_error == COMMAND_RESULT_OK) {
					int type = (cmd_code == 41) ? NotificationData.TYPE_USSD : NotificationData.TYPE_RESULT_SUCCESS;
					// suppress first (empty) OK result for cmd 41:
					if (cmd_code == 7 || !cmd_data.isEmpty()) {
						mDatabase.addNotification(new NotificationData(type, new Date(), title,
								cmd_data.isEmpty() ? getString(R.string.msg_ok) : cmd_data));
						sendBroadcast(new Intent(ApiService.ACTION_NOTIFICATION)
								.putExtra("onNotification", true));
					}
				} else {
					mDatabase.addNotification(new NotificationData(NotificationData.TYPE_ERROR, new Date(), title,
							getString(R.string.err_failed_smscmd)));
					sendBroadcast(new Intent(ApiService.ACTION_NOTIFICATION)
							.putExtra("onNotification", true));
				}

				// TODO: remove UI interaction from Service
				// Display the command result to the user:
				SpannableStringBuilder text = new SpannableStringBuilder();
				int start = 0;
				if (cmd_code != 7) {
					text.append(getCommandName(cmd_code));
					text.setSpan(new StyleSpan(Typeface.ITALIC), start, text.length(), 0);
					text.append(" ");
					start = text.length();
				}
				switch (cmd_error) {
					case COMMAND_RESULT_OK:
						if (!cmd_data.isEmpty()) {
							text.append(cmd_data.replace('\r', '\n'));
							text.setSpan(new TypefaceSpan("monospace"), start, text.length(), 0);
							text.setSpan(new RelativeSizeSpan(0.8f), start, text.length(), 0);
						} else {
							text.append(getString(R.string.msg_ok));
							text.setSpan(new ForegroundColorSpan(Color.GREEN), start, text.length(), 0);
						}
						break;
					case COMMAND_RESULT_FAILED:
						text.append(getString(R.string.err_failed, cmd_data));
						text.setSpan(new ForegroundColorSpan(Color.RED), start, text.length(), 0);
						break;
					case COMMAND_RESULT_UNSUPPORTED:
						text.append(getString(R.string.err_unsupported_operation));
						text.setSpan(new ForegroundColorSpan(Color.RED), start, text.length(), 0);
						break;
					case COMMAND_RESULT_UNIMPLEMENTED:
						text.append(getString(R.string.err_unimplemented_operation));
						text.setSpan(new ForegroundColorSpan(Color.RED), start, text.length(), 0);
						break;
				}

				DialogActivity.show(getApplicationContext(), title, text);
			}
		}

		// Check broadcast API configuration:
		if (appPrefes.getData("option_commands_enabled", "0").equals("1")) {
			Log.v(TAG, "onResultCommand: sending broadcast " + ACTION_COMMANDRESULT + ": " + pCmdResponse);
			Intent intent = new Intent(ACTION_COMMANDRESULT);
			intent.putExtra("sel_server", mCarData.sel_server);
			intent.putExtra("sel_vehicleid", mCarData.sel_vehicleid);
			intent.putExtra("sel_vehicle_label", mCarData.sel_vehicle_label);
			intent.putExtra("cmd_vehicleid", mCarData.sel_vehicleid);
			intent.putExtra("cmd_code", cmd_code);
			intent.putExtra("cmd_error", cmd_error);
			intent.putExtra("cmd_data", cmd_data);
			intent.putExtra("cmd_result", data);
			sendBroadcast(intent);
			sendKustomBroadcast(this, intent);
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
	 * - apikey or sel_vehicleid + sel_server_password
	 * - msg_command or command
	 *
	 * msg_command: MP command syntax, e.g. "7,stat" / "20,1234"
	 * command: user command syntax, e.g. "stat" / "#20,1234"
	 *
	 * Car will be changed as necessary (persistent change).
	 * Fails silently on errors.
	 * Results will be broadcasted & displayed by onResultCommand().
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
			else if (!isLoggedIn()) {
				Log.e(TAG, "CommandReceiver: not logged in");
				return;
			}

			// Get parameters:
			String apikey = intent.getStringExtra("apikey");
			String vehicleid = intent.getStringExtra("sel_vehicleid");
			String vehiclepass = intent.getStringExtra("sel_server_password");

			// Get vehicle config:
			String carApiKey = appPrefes.getData("APIKey");
			CarData carData;
			if (vehicleid != null && !vehicleid.isEmpty()) {
				carData = CarsStorage.get().getCarById(vehicleid);
			} else {
				carData = CarsStorage.get().getSelectedCarData();
			}

			// Check authorization:
			if ((carData == null) || (apikey == null && vehiclepass == null) ||
					(apikey != null && !carApiKey.equals(apikey)) ||
					(vehiclepass != null && !carData.sel_server_password.equals(vehiclepass))) {
				Log.e(TAG, "CommandReceiver: vehicle/authorization invalid");
				return;
			}

			// Get command parameters:
			String msgCommand = intent.getStringExtra("msg_command");
			String userCommand = intent.getStringExtra("command");

			if (msgCommand == null || msgCommand.isEmpty()) {
				msgCommand = makeMsgCommand(userCommand);
			}

			// Note: command may be empty/missing, to only change the current vehicle

			// Change car if necessary:
			if (!mCarData.sel_vehicleid.equals(carData.sel_vehicleid)) {
				Log.i(TAG, "CommandReceiver: changing car to: " + carData.sel_vehicleid);
				changeCar(carData);
				CarsStorage.get().setSelectedCarId(carData.sel_vehicleid);
			}

			// Send command:
			if (!msgCommand.isEmpty()) {
				Log.i(TAG, "CommandReceiver: sending command: " + msgCommand);
				cancelCommand(null);
				if (!mApiTask.sendMessage(String.format("MP-0 C%s", msgCommand))) {
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
	 * The KustomWidget App (KWGT) cannot read generic intent extras, data
	 * needs to be sent as string arrays. See:
	 * https://help.kustom.rocks/i195-send-variables-to-kustom-via-broadcast
	 */
	public static final String KUSTOM_ACTION = "org.kustom.action.SEND_VAR";
	public static final String KUSTOM_ACTION_EXT_NAME = "org.kustom.action.EXT_NAME";
	public static final String KUSTOM_ACTION_VAR_NAME_ARRAY = "org.kustom.action.VAR_NAME_ARRAY";
	public static final String KUSTOM_ACTION_VAR_VALUE_ARRAY = "org.kustom.action.VAR_VALUE_ARRAY";

	public static void sendKustomBroadcast(Context context, Intent srcIntent) {
		Bundle extras = srcIntent.getExtras();
		if (extras == null) return;

		// create Kustom intent:
		Intent kIntent = new Intent(KUSTOM_ACTION);
		kIntent.putExtra(KUSTOM_ACTION_EXT_NAME, "ovms");

		// create string arrays from extras:
		ArrayList<String> names = new ArrayList<>(extras.size());
		ArrayList<String> values = new ArrayList<>(extras.size());
		for (String key : extras.keySet()) {
			Object value = extras.get(key);
			if (value == null) {
				names.add(key);
				values.add("");
			} else if (value.getClass().isArray()) {
				// unroll arrays by adding index suffix:
				int cnt = Array.getLength(value);
				names.add(key + "_cnt");
				values.add("" + cnt);
				for (int i = 0; i < cnt; i++) {
					names.add(key + "_" + (i + 1));
					Object elem = Array.get(value, i);
					if (elem != null) values.add(elem.toString());
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
		context.sendBroadcast(kIntent);
	}
}
