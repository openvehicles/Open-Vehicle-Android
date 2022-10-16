package com.openvehicles.OVMS.entities;

import android.content.Context;
import android.util.Log;

import com.openvehicles.OVMS.BaseApp;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.OnResultCommandListener;

import java.util.ArrayList;

/**
 * Created by balzer on 09.03.15.
 *
 * This class takes care of execution of a series of commands.
 * All results will be stored in the Cmd.results arrays.
 * Execution stops on command errors (except empty history records).
 *
 * The Listener can react to progress and finish events.
 * Return code -1 on finish means execution was cancelled.
 *
 * See LogsFragment and BatteryFragment for complete usage examples.
 *
 */
public class CmdSeries implements OnResultCommandListener {
	private static final transient String TAG = "CmdSeries";

	public interface Listener {
		/**
		 * Progress callback.
		 *
		 * @param message -- user message for current command
		 * @param pos -- position of current command
		 * @param posCnt -- size of command series
		 * @param step -- on multiple results: record position (else 0)
		 * @param stepCnt -- on multiple results: record count (else 0)
		 */
		void onCmdSeriesProgress(String message, int pos, int posCnt, int step, int stepCnt);

		/**
		 * Success / abort callback.
		 *
		 * @param cmdSeries -- the series
		 * @param returnCode -- last result code or -1 on abort
		 */
		void onCmdSeriesFinish(CmdSeries cmdSeries, int returnCode);
	}


	/**
	 * Single command entry of the series
	 */
	public class Cmd {
		private CmdSeries series;

		/** User message & command specification */
		public String message;
		public String command;
		public int commandCode;

		/** Last return code */
		public int returnCode;

		/** All results collected */
		public ArrayList<String[]> results;

		public Cmd(CmdSeries series) {
			this.series = series;
			this.results = new ArrayList<String[]>();
		}

		public int pos() {
			return series.current;
		}

		public int posCnt() {
			return series.cmdList.size();
		}
	}


	private final Context context;
	private final ApiService mService;
	private final Listener mListener;
	private final ArrayList<Cmd> cmdList;
	private int current;

	/**
	 * Create new CmdSeries
	 *
	 * @param pContext -- the Context
	 * @param pService -- the ApiService (i.e. getService())
	 * @param pListener -- the Listener (optional)
	 */

	public CmdSeries(Context pContext, ApiService pService, Listener pListener) {
		context = pContext;
		mService = pService;
		mListener = pListener;
		cmdList = new ArrayList<Cmd>();
		current = -1;
	}

	public CmdSeries(ApiService pService, Listener pListener) {
		this(BaseApp.getApp(), pService, pListener);
	}


	public int size() {
		return cmdList.size();
	}

	public Cmd get(int i) {
		return cmdList.get(i);
	}


	/**
	 * Add a command to be executed to the series.
	 * The command will be added at the end.
	 *
	 * Example:
	 * 		CmdSeries series = new CmdSeries(...)
	 * 			.add("Setting feature #8 to 1...", "2,8,1")
	 * 			.add("Setting param #11 to abc...", "4,11,abc");
	 *
	 * @param pMessage -- user message to display & log
	 * @param pCommand -- command string
	 * @return -- CmdSeries for daisy chaining
	 */
	public CmdSeries add(String pMessage, String pCommand) {
		Cmd cmd = new Cmd(this);
		cmd.message = pMessage;
		cmd.command = pCommand;
		String code = pCommand.split(",", 2)[0];
		cmd.commandCode = Integer.parseInt(code);
		cmdList.add(cmd);
		return this; // for daisy chaining
	}

	public CmdSeries add(int pMessageId, String pCommand) {
		return add(context.getString(pMessageId), pCommand);
	}


	/**
	 * Get current command.
	 *
	 * @return -- current command or null if series is not running
	 */
	public Cmd getCurrent() {
		if (current >= 0 && current < cmdList.size())
			return cmdList.get(current);
		else
			return null;
	}


	/**
	 * Advance execution to next command in series.
	 *
	 * @return -- next command or null if end of series
	 */
	public Cmd getNext() {
		current += 1;
		return getCurrent();
	}


	/**
	 * Start execution of series at first command scheduled.
	 *
	 * @return -- this CmdSeries for daisy chaining
	 */
	public CmdSeries start() {
		Log.v(TAG, "started");
		current = -1;
		executeNext();
		return this;
	}


	/**
	 * Execution handler: sends the current command to the server.
	 *  The command position in the series is told to the listener as the main progress.
	 */
	private void executeNext() {
		if (mService == null || !mService.isLoggedIn())
			return;

		Cmd cmd = getNext();

		if (cmd != null) {
			// send next command:
			Log.v(TAG, "executeNext: " + cmd.message + ": cmd=" + cmd.command);
			if (mListener != null)
				mListener.onCmdSeriesProgress(cmd.message, cmd.pos(), cmd.posCnt(), 0, 0);
			mService.sendCommand(cmd.command, this);
		}
		else {
			// series finished:
			mService.cancelCommand(this);
			if (mListener != null)
				mListener.onCmdSeriesFinish(this, 0);
		}
	}


	/**
	 * Result handler. Adds a cmd result matching the current command
	 * 	to the command results. On success, the next command will be executed,
	 *  on failure the series aborts.
	 *
	 * Commands 1,3,30-32: multiple results are handled by checking the
	 * 	result records count and position. Error "no historical messages"
	 *  is handled as a normal result (no failure = no abort). The record
	 *  count/position are told to the listener as sub step progress.
	 *
	 * @param result -- return string received from server
	 */
	@Override
	public void onResultCommand(String[] result) {

		if (result.length < 2)
			return;

		int commandCode = Integer.parseInt(result[0]);
		int returnCode = Integer.parseInt(result[1]);
		String returnText = (result.length > 2) ? result[2] : "";

		// check command:
		Cmd cmd = getCurrent();
		if (cmd == null) {
			// we're not active, cancel subscription:
			mService.cancelCommand(this);
			return;
		} else if (cmd.commandCode != commandCode) {
			// not for us:
			return;
		}

		// store result:
		cmd.returnCode = returnCode;
		cmd.results.add(result);

		Log.v(TAG, "onResult: " + cmd.message + " / key=" + cmd.commandCode
				+ " => returnCode=" + cmd.returnCode);

		if (ApiService.hasMultiRowResponse(commandCode)) {
			// multiple result command:

			if (returnText.equals("No historical data available")) {
				// no records: continue without error
				executeNext();

			} else if (returnCode != 0) {
				// error: stop execution
				Log.e(TAG, "ABORT: cmd failed: key=" + cmd.commandCode + " => returnCode=" + cmd.returnCode);
				mService.cancelCommand(this);
				if (mListener != null)
					mListener.onCmdSeriesFinish(this, returnCode);

			} else {
				// success: check record count
				int recNr = Integer.parseInt(result[2]);
				int recCnt = Integer.parseInt(result[3]);
				if (commandCode <= 3) recNr++; // fix record numbering for feature & param lists
				if (recNr == recCnt) {
					// got all records
					executeNext();
				} else {
					// update progress sub step:
					if (mListener != null)
						mListener.onCmdSeriesProgress(cmd.message, cmd.pos(), cmd.posCnt(), recNr, recCnt);
				}
			}

		} else if (returnCode != 0) {
			// single result command error: stop execution
			Log.e(TAG, "ABORT: cmd failed: key=" + cmd.commandCode + " => returnCode=" + cmd.returnCode);
			mService.cancelCommand(this);
			if (mListener != null)
				mListener.onCmdSeriesFinish(this, returnCode);

		} else {
			// single result command success:
			executeNext();
		}

	}


	/**
	 * Cancel series execution. Pending results will be ignored.
	 * 	Triggers Listener callback onCmdSeriesFinish with result code -1.
	 */
	public void cancel() {
		Log.v(TAG, "cancelled");
		mService.cancelCommand(this);
		if (mListener != null && current >= 0 && current < cmdList.size()) {
			mListener.onCmdSeriesFinish(this, -1);
		}
	}


	/**
	 * Get return code of current command
	 * @return -- command return code (0..3)
	 */
	public int getReturnCode() {
		Cmd cmd = getCurrent();
		return (cmd != null) ? cmd.returnCode : 0;
	}


	/**
	 * Get user message for current command
	 * @return -- message string
	 */
	public String getMessage() {
		Cmd cmd = getCurrent();
		return (cmd != null) ? cmd.message : "";
	}


	/**
	 * Get optional error detail string returned by the module if available.
	 *  (On return code 1-3 the command may supply a detail message, see protocol)
	 * @return -- error detail description or ""
	 */
	public String getErrorDetail() {
		Cmd cmd = getCurrent();
		if (cmd == null)
			return "";

		String result[] = (cmd.results.size() > 0)
				? cmd.results.get(cmd.results.size()-1)
				: null;
		if (result != null && result.length >= 3)
			return result[2];
		else
			return "";
	}


}
