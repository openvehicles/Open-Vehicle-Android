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

	private transient static final Context context = BaseApp.getApp();

	public interface Listener {
		void onCmdSeriesProgress(String message, int pos, int posCnt, int step, int stepCnt);
		void onCmdSeriesFinish(CmdSeries cmdSeries, int returnCode);
	}


	public class Cmd {
		private CmdSeries series;
		public String message;
		public String command;
		public int commandCode;
		public int returnCode;
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


	private ApiService mService;
	private Listener mListener;
	private ArrayList<Cmd> cmdList;
	private int current;


	public CmdSeries(ApiService pService, Listener pListener) {
		mService = pService;
		mListener = pListener;
		cmdList = new ArrayList<Cmd>();
		current = -1;
	}


	public int size() {
		return cmdList.size();
	}

	public Cmd get(int i) {
		return cmdList.get(i);
	}


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


	public Cmd getCurrent() {
		if (current >= 0 && current < cmdList.size())
			return cmdList.get(current);
		else
			return null;
	}


	public Cmd getNext() {
		current += 1;
		return getCurrent();
	}


	public CmdSeries start() {
		Log.v(TAG, "started");
		current = -1;
		executeNext();
		return this;
	}


	private void executeNext() {
		if (mService == null || !mService.isLoggedIn())
			return;

		Cmd cmd = getNext();

		if (cmd != null) {
			// send next command:
			Log.v(TAG, "executeNext: " + cmd.message + ": cmd=" + cmd.command);
			mListener.onCmdSeriesProgress(cmd.message, cmd.pos(), cmd.posCnt(), 0, 0);
			mService.sendCommand(cmd.command, this);
		}
		else {
			// series finished:
			mListener.onCmdSeriesFinish(this, 0);
		}
	}


	@Override
	public void onResultCommand(String[] result) {

		if (result.length < 2)
			return;

		int commandCode = Integer.parseInt(result[0]);
		int returnCode = Integer.parseInt(result[1]);

		// check command:
		Cmd cmd = getCurrent();
		if (cmd == null) {
			// we're not active, cancel subscription:
			mService.cancelCommand();
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

		if (commandCode == 30 || commandCode == 31 || commandCode == 32) {
			// multiple result command:

			if (result[2].equals("No historical data available")) {
				// no records: continue without error
				executeNext();

			} else if (returnCode != 0) {
				// error: stop execution
				Log.e(TAG, "ABORT: cmd failed: key=" + cmd.commandCode + " => returnCode=" + cmd.returnCode);
				mService.cancelCommand();
				mListener.onCmdSeriesFinish(this, returnCode);

			} else {
				// success: check record count
				int recNr = Integer.parseInt(result[2]);
				int recCnt = Integer.parseInt(result[3]);
				if (recNr == recCnt) {
					// got all records
					executeNext();
				} else {
					// update progress sub step:
					mListener.onCmdSeriesProgress(cmd.message, cmd.pos(), cmd.posCnt(), recNr, recCnt);
				}
			}

		} else if (returnCode != 0) {
			// single result command error: stop execution
			Log.e(TAG, "ABORT: cmd failed: key=" + cmd.commandCode + " => returnCode=" + cmd.returnCode);
			mService.cancelCommand();
			mListener.onCmdSeriesFinish(this, returnCode);

		} else {
			// single result command success:
			executeNext();
		}

	}


	public void cancel() {
		Log.v(TAG, "cancelled");
		mService.cancelCommand();
		mListener.onCmdSeriesFinish(this, -1);
	}


	public int getReturnCode() {
		Cmd cmd = getCurrent();
		return (cmd != null) ? cmd.returnCode : 0;
	}


	public String getMessage() {
		Cmd cmd = getCurrent();
		return (cmd != null) ? cmd.message : "";
	}


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
