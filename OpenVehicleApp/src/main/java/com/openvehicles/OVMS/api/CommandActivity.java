package com.openvehicles.OVMS.api;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.DialogActivity;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.NotificationData;

import java.util.Date;

/**
 * This Activity is registered to receive StoredCommand bookmark execution Intents
 * (action com.openvehicles.OVMS.action.COMMAND).
 *
 * Its purpose is to forward the command to the ApiService. The ApiService cannot
 * process these directly, as Android seems to require a static Activity for this (?).
 *
 * In the future it may also be possible to use this to provide a way to temporarily start
 * the ApiService for a command execution if it's not enabled.
 *
 * TODO: there should be a better way to implement this, services are meant to serve
 *       intents like this by their manifest intent filters, so we should not need
 *       an activity (in theory). The ApiService would then need to wait for the
 *       ApiTask before sending the command, and implement some timeout before
 *       closing the connection.
 *
 * TODO: this should be the dedicated way to issue commands based on user interaction,
 * 		 i.e. ApiService should not do any UI notifications by itself but leave that
 * 		 to the Activities. The SendCommand intent action can then be used for background
 * 		 operation, action.COMMAND for interactive tasks (shortcut invocation etc.)
 */
public class CommandActivity extends Activity {
	private static final String TAG = "CommandActivity";

	private AppPrefes appPrefes;
	private Database mDatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		appPrefes = new AppPrefes(this, "ovms");
		mDatabase = new Database(getApplicationContext());
		processCommandIntent(getIntent());
		finish();
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		mDatabase.close();
		super.onDestroy();
	}

	public void processCommandIntent(Intent intent) {
		Log.d(TAG, "processCommandIntent: " + intent);
		if (intent == null)
			return;

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
			Log.e(TAG, "processCommandIntent: vehicle/authorization invalid");
			DialogActivity.show(this, getString(R.string.Error), getString(R.string.CommandAuthFailed));
			return;
		}

		// Forward command to ApiService:
		Intent sendCommand = new Intent(ApiService.ACTION_SENDCOMMAND)
				.putExtras(intent);

		String command = intent.getStringExtra("command");
		if (command != null && command.length() > 0) {
			// Add command to notifications:
			mDatabase.addNotification(new NotificationData(NotificationData.TYPE_COMMAND,
					new Date(), carData.sel_vehicleid + ": " + command, command));
			sendBroadcast(new Intent(ApiService.ACTION_NOTIFICATION)
					.putExtra("onNotification", true));
		}

		sendBroadcast(sendCommand);
	}

}