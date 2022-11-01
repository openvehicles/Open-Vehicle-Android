package com.openvehicles.OVMS.api;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.entities.CmdSeries;
import com.openvehicles.OVMS.ui.ApiActivity;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.NotificationData;
import com.openvehicles.OVMS.utils.Sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * CommandActivity is registered to receive command intents meant for interactive execution,
 * i.e. StoredCommand bookmark execution Intents or command broadcast from third party
 * Apps like Automagic or Tasker (Intent action com.openvehicles.OVMS.action.COMMAND).
 *
 * Intent extras expected/supported:
 * - vehicleid (optional)
 * - apikey or (with sel_vehicleid) password
 * - title (optional)
 * - command (optional, no command means only change the car)
 *
 * Commands need to be in user input syntax:
 * 	- MMI/USSD commands: prefix "*", example: "*100#"
 * 	- Modem commands:    prefix "@", example: "@ATI"
 * 	- MP MSG commands:   prefix "#", example: "#31"
 * 	- Text commands:     everything else, example: "stat"
 *
 * Command results are shown in an overlay (dialog style) window for DISPLAY_TIMEOUT_SECONDS,
 * or until the user clicks on the text. Scrolling or holding the display stops the timeout.
 * Result rows exceeding DISPLAY_MAXROWS+5 will be omitted.
 */
public class CommandActivity extends ApiActivity
		implements CmdSeries.Listener {
	private static final String TAG = "CommandActivity";

	private final int DISPLAY_MAXROWS = 10;
	private final int DISPLAY_TIMEOUT_SECONDS = 5;

	private AppPrefes appPrefes;
	private Database mDatabase;

	private final Queue<Intent> mCommandQueue = new LinkedList<>();

	private Intent mCurrentIntent;
	private String mVehicleId;
	private String mTitle;
	private String mCommand;
	private String mMsgCommand;
	private int mMsgCommandCode;
	private CmdSeries mCmdSeries;

	private ProgressBar mProgressBar;
	private TextView mTextViewMessage;

	private final Handler mTimeoutHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		if (intent == null) {
			finish();
			return;
		}
		Log.d(TAG, "onCreate " + Sys.toString(intent.getExtras()));

		appPrefes = new AppPrefes(this, "ovms");
		mDatabase = new Database(getApplicationContext());

		// Create UI:

		mProgressBar = new ProgressBar(this);
		mProgressBar.setPadding(20,10,20,20);
		mProgressBar.setIndeterminate(true);

		mTextViewMessage = new TextView(this);
		mTextViewMessage.setPadding(20,10,20,20);
		if (appPrefes.getData("notifications_font_monospace").equals("on")) {
			mTextViewMessage.setTypeface(Typeface.MONOSPACE);
		}
		try {
			float fontSize = Float.parseFloat(appPrefes.getData("notifications_font_size"));
			mTextViewMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
		} catch(Exception ignore) {
			// keep default font size
		}

		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.addView(mProgressBar);
		linearLayout.addView(mTextViewMessage);

		ScrollView scrollView = new ScrollView(this);
		scrollView.addView(linearLayout);

		setContentView(scrollView);

		queueCommand(intent);
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");

		// Stop command execution and clear queue:
		if (mCmdSeries != null) {
			mCmdSeries.cancel();
			mCmdSeries = null;
		}
		mCurrentIntent = null;
		mCommandQueue.clear();
		stopFinishTimeout();

		mDatabase.close();

		super.onDestroy();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.d(TAG, "onNewIntent " + Sys.toString(intent.getExtras()));
		queueCommand(intent);
	}

	private void queueCommand(Intent intent) {
		// Is there currently a command executing?
		if (mCurrentIntent != null) {
			Log.d(TAG, "queueCommand: adding intent to queue");
			mCommandQueue.add(intent);
		} else {
			Log.d(TAG, "queueCommand: processing intent now");
			startCommand(intent);
		}
	}

	@Override
	public void onServiceAvailable(ApiService pService) {
		Log.d(TAG, "onServiceAvailable " + pService);
		processCommand();
	}

	@Override
	public void onServiceLoggedIn(ApiService pService, boolean pIsLoggedIn) {
		Log.d(TAG, "onServiceLoggedIn loggedin=" + pIsLoggedIn);
		processCommand();
	}

	private void processCommand() {
		if (mCurrentIntent != null) {
			// Progress with current command:
			Log.d(TAG, "processCommand: progress with current command");
			startCommand(mCurrentIntent);
		} else {
			Log.d(TAG, "processCommand: check for new command");
			checkQueue();
		}
	}

	private void checkQueue() {
		Intent intent = mCommandQueue.poll();
		if (intent != null) {
			Log.d(TAG, "checkQueue: starting next command");
			startCommand(intent);
		} else {
			Log.d(TAG, "checkQueue: all commands done, finish activity");
			finish();
		}
	}


	private void startCommand(Intent intent) {

		if (mCurrentIntent != intent) {
			// Init new command:
			String apikey = intent.getStringExtra("apikey");
			String vehicleid = intent.getStringExtra("vehicleid");
			String vehiclepass = intent.getStringExtra("password");
			String title = intent.getStringExtra("title");
			String command = intent.getStringExtra("command");

			// Get vehicle config:
			String carApiKey = appPrefes.getData("APIKey");
			CarData carData;
			if (vehicleid != null && !vehicleid.isEmpty()) {
				carData = CarsStorage.get().getCarById(vehicleid);
			} else {
				carData = CarsStorage.get().getSelectedCarData();
			}

			// Configure job:
			mCurrentIntent = intent;
			if (mCmdSeries != null) {
				Log.w(TAG, "startCommand: need to cancel previous command -- this should not happen");
				mCmdSeries.cancel();
			}
			mCmdSeries = null;
			mVehicleId = (carData != null) ? carData.sel_vehicleid : vehicleid;
			mTitle = (title != null) ? title : "";
			mCommand = (command != null) ? command : "";
			mMsgCommand = ApiService.makeMsgCommand(mCommand);
			if (!mMsgCommand.isEmpty()) {
				try {
					mMsgCommandCode = Integer.parseInt(mMsgCommand.split(",")[0]);
				} catch (Exception e) {
					mMsgCommandCode = 0;
				}
			} else {
				mMsgCommandCode = 0;
			}
			if (mTitle.isEmpty() && mMsgCommandCode > 0) {
				mTitle = (mMsgCommandCode == 7)
						? mCommand : ApiService.getCommandName(mMsgCommandCode);
			}

			// Set job title:
			StringBuilder sb = new StringBuilder();
			sb.append(mVehicleId).append(": ").append(mTitle);
			setTitle(sb.toString());
			Log.i(TAG, "startCommand: " + sb.toString() + " → " + mMsgCommand);

			// Check vehicle:
			if (carData == null) {
				Log.e(TAG, "startCommand: vehicle unknown: " + mVehicleId);
				showError(getString(R.string.err_vehicle_unknown));
				return;
			}

			// Check authorization:
			if ((apikey == null && vehiclepass == null) ||
					(apikey != null && !carApiKey.equals(apikey)) ||
					(vehiclepass != null && !carData.sel_server_password.equals(vehiclepass))) {
				Log.e(TAG, "startCommand: vehicle/authorization invalid");
				showError(getString(R.string.err_command_auth_failed));
				return;
			}
		}

		// Check service & network:
		if (!hasService()) {
			Log.d(TAG, "startCommand: connecting service");
			showStatus(getString(R.string.msg_connecting_service));
			return;
		}
		if (!isOnline()) {
			Log.e(TAG, "startCommand: offline");
			showError(getString(R.string.err_offline));
			return;
		}

		// Check selected car:
		String selectedCarId = CarsStorage.get().getLastSelectedCarId();
		if (!TextUtils.equals(selectedCarId, mVehicleId)) {
			Log.d(TAG, "startCommand: changing car to " + mVehicleId);
			changeCar(mVehicleId);
			showStatus(getString(R.string.msg_connecting_vehicle));
			return;
		}

		// Check login status:
		if (!isLoggedIn()) {
			Log.d(TAG, "startCommand: connecting vehicle");
			showStatus(getString(R.string.msg_connecting_vehicle));
			return;
		}

		// Is there a command to run, or are we done after changing the car?
		if (mCommand == null || mCommand.isEmpty()) {
			Log.i(TAG, "startCommand: no command, done");
			showResult(getString(R.string.msg_ok));
			return;
		}

		if (mCmdSeries == null) {
			Log.i(TAG, "startCommand: connected, starting command execution");

			// Add command to notifications:
			mDatabase.addNotification(new NotificationData(NotificationData.TYPE_COMMAND,
					new Date(), mVehicleId + ": " + mCommand, mCommand));
			sendBroadcast(new Intent(ApiService.ACTION_NOTIFICATION)
					.putExtra("onNotification", true));

			// Start command:
			mCmdSeries = new CmdSeries(this, mApiService, this);
			mCmdSeries.add(mTitle, mMsgCommand);
			mCmdSeries.start();

			String command;
			if (mMsgCommandCode == 7) {
				command = mCommand;
			} else {
				command = ApiService.getCommandName(mMsgCommandCode) + " (" + mCommand + ")";
			}
			showStatus("> " + command);
		}
	}

	@Override
	public void onCmdSeriesProgress(String message, int pos, int posCnt, int step, int stepCnt) {
		// unused for now
	}

	@Override
	public void onCmdSeriesFinish(CmdSeries cmdSeries, int returnCode) {
		Log.i(TAG, "onCmdSeriesFinish: command done returnCode=" + returnCode);
		String title = mVehicleId + ": " + mCommand;
		SpannableStringBuilder text = new SpannableStringBuilder();

		// Failure?
		if (returnCode != ApiService.COMMAND_RESULT_OK) {
			String errorDetail = mCmdSeries.getErrorDetail();
			switch (returnCode) {
				case ApiService.COMMAND_RESULT_FAILED:
					if (!errorDetail.isEmpty())
						text.append(getString(R.string.err_failed, errorDetail));
					else
						text.append(getString(R.string.err_command_failed));
					break;
				case ApiService.COMMAND_RESULT_UNSUPPORTED:
					text.append(getString(R.string.err_unsupported_operation)).append('\n').append(errorDetail);
					break;
				case ApiService.COMMAND_RESULT_UNIMPLEMENTED:
					text.append(getString(R.string.err_unimplemented_operation)).append('\n').append(errorDetail);
					break;
				default:
					text.append(getString(R.string.err_command_cancelled)).append('\n').append(errorDetail);
			}
			int red = getResources().getColor(R.color.colorTextError);
			text.setSpan(new ForegroundColorSpan(red), 0, text.length(), 0);

			// Add result to notifications list:
			if (returnCode != -1) {
				mDatabase.addNotification(new NotificationData(NotificationData.TYPE_ERROR, new Date(),
						title, text.toString()));
				sendBroadcast(new Intent(ApiService.ACTION_NOTIFICATION)
						.putExtra("onNotification", true));
			}


			showError(text);
			return;
		}

		// Success:
		CmdSeries.Cmd cmd = cmdSeries.get(0);
		ArrayList<String[]> results = cmd.results;

		// Collect results, skip empty lines:
		int rows = 0;
		int maxRows = (results.size() <= DISPLAY_MAXROWS+5) ? results.size() : DISPLAY_MAXROWS;
		for (int i = 0; i < results.size(); i++) {
			String[] result = results.get(i);
			if (result.length < 3) continue;
			String line = String.join(",", Arrays.copyOfRange(result, 2, result.length));
			if (line.isEmpty()) continue;
			text.append(line.replace('\r', '\n')).append('\n');
			if (++rows == maxRows) {
				if (++i < results.size())
					text.append(getString(R.string.msg_more_rows_omitted, results.size()-i));
				break;
			}
		}

		// If no text result was received, output "OK":
		if (rows == 0) {
			text.append(getString(R.string.msg_ok));
			text.setSpan(new ForegroundColorSpan(Color.GREEN), 0, text.length(), 0);
		} else {
			text.setSpan(new LeadingMarginSpan.Standard(0, 50), 0, text.length(), 0);
		}

		// Add result to notifications:
		int type = (cmd.commandCode == 41)
				? NotificationData.TYPE_USSD : NotificationData.TYPE_RESULT_SUCCESS;
		mDatabase.addNotification(new NotificationData(type, new Date(), title, text.toString()));
		sendBroadcast(new Intent(ApiService.ACTION_NOTIFICATION)
				.putExtra("onNotification", true));

		// Display result:
		showResult(text);
	}


	private void showStatus(CharSequence text) {
		mTextViewMessage.setText(text);
		mTextViewMessage.setOnClickListener(null);
		mTextViewMessage.setOnLongClickListener(null);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	private void showResult(CharSequence text) {
		mTextViewMessage.setText(text);
		mTextViewMessage.setOnClickListener(v -> finishCommand());
		mTextViewMessage.setOnLongClickListener(v -> true);
		mProgressBar.setVisibility(View.GONE);
		startFinishTimeout();
	}

	private void showError(CharSequence text) {
		SpannableStringBuilder sb = new SpannableStringBuilder();
		sb.append(getString(R.string.Error));
		sb.append(": ");
		sb.append(text);
		int red = getResources().getColor(R.color.colorTextError);
		sb.setSpan(new ForegroundColorSpan(red), 0, sb.length(), 0);
		showResult(text);
	}


	// Note: finishTimeout needs to be a Runnable for removeCallbacks() to work
	private final Runnable finishTimeout = this::finishCommand;

	private void startFinishTimeout() {
		mTimeoutHandler.postDelayed(finishTimeout, DISPLAY_TIMEOUT_SECONDS *1000);
	}

	private void stopFinishTimeout() {
		mTimeoutHandler.removeCallbacks(finishTimeout);
	}

	@Override
	public void onUserInteraction() {
		Log.d(TAG, "onUserInteraction");
		stopFinishTimeout();
	}

	@Override
	protected void onUserLeaveHint() {
		Log.d(TAG, "onUserLeaveHint");
		stopFinishTimeout();
	}

	private void finishCommand() {
		Log.d(TAG, "finishCommand");

		// Clear job:
		if (mCmdSeries != null) {
			mCmdSeries.cancel();
		}
		mCurrentIntent = null;
		mCmdSeries = null;
		mVehicleId = null;
		mTitle = null;
		mCommand = null;
		mMsgCommand = null;
		mMsgCommandCode = 0;

		// In case we've been called by user interaction:
		stopFinishTimeout();

		// See if there are more commands to run:
		checkQueue();
	}
}
