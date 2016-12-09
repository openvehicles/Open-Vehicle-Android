package com.openvehicles.OVMS.api;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntegerRes;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiTask.OnUpdateStatusListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.settings.GlobalOptionsFragment;
import com.openvehicles.OVMS.utils.CarsStorage;

public class ApiService extends Service implements OnUpdateStatusListener {
	private static final String TAG = "ApiService";
    private final IBinder mBinder = new ApiBinder();
	private volatile CarData mCarData;
    private ApiTask mApiTask;
	private OnResultCommandListener mOnResultCommandListener;
	private AppPrefes appPrefes;
	

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	

	@Override
	public void onCreate() {
		super.onCreate();

		appPrefes = new AppPrefes(getApplicationContext(), "ovms");

		// Login for selected car:
		changeCar(CarsStorage.get().getSelectedCarData());

		// Register command receiver:
		Log.d(TAG, "Registering command receiver for Intent: " + getPackageName() + ".SendCommand");
		registerReceiver(mCommandReceiver, new IntentFilter(getPackageName() + ".SendCommand"));
	}
	

	@Override
	public void onDestroy() {

		try {
			if (mApiTask != null) {
				Log.v(TAG, "Shutting down TCP connection");
				mApiTask.connClose();
				mApiTask.cancel(true);
				mApiTask = null;
			}
		} catch (Exception e) {
			Log.e(TAG, "ERROR stop ApiTask", e);
		}

		unregisterReceiver(mCommandReceiver);

		super.onDestroy();
	}


	public void changeCar(CarData pCarData) {
		Log.i(TAG, "Changed car to: " + pCarData.sel_vehicleid);
		mCarData = pCarData;

		// kill previous connection
		if (mApiTask != null) {
			Log.v("TCP", "Shutting down previous TCP connection (ChangeCar())");
			mApiTask.connClose();
			mApiTask.cancel(true);
		}

		// start new connection
		// reset the paranoid mode flag in car data
		// it will be set again when the TCP task detects paranoid mode messages
		mCarData.sel_paranoid = false;
		mApiTask = new ApiTask(getApplicationContext(), mCarData, this);
		
		Log.v(TAG, "Starting TCP Connection (changeCar())");
		mApiTask.execute();
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
		// Update internal observers:
		ApiObservable.get().notifyUpdate(mCarData);

		// Send system broadcast for Automagic / Tasker / ...
		if (appPrefes.getData("option_broadcast_enabled", "0").equals("1")) {
			// for these message codes:
			String broadcastCodes = appPrefes.getData("option_broadcast_codes",
					GlobalOptionsFragment.defaultBroadcastCodes);
			if (broadcastCodes.indexOf(msgCode) >= 0) {

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
		intent.putExtra("message", getString(mApiTask.isLoggedIn() ? 
				R.string.err_connection_lost : R.string.err_check_following));
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
		return mApiTask.isLoggedIn();
	}
	
	public CarData getCarData() {
		return mCarData;
	}
	
	public CarData getLoggedInCarData() {
		return mApiTask.isLoggedIn() ? mCarData : null;
	}
	
    public class ApiBinder extends Binder {
		public ApiService getService() {
			return ApiService.this;
		}
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
				if (!mApiTask.sendCommand(String.format("MP-0 C%s", vehCommand))) {
					Log.e(TAG, "CommandReceiver: sendCommand failed");
				}
			}
		}
	};


}
