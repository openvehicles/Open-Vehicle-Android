package com.openvehicles.OVMS.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.openvehicles.OVMS.api.ApiObservable;
import com.openvehicles.OVMS.api.ApiObserver;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.OnResultCommandListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.utils.ProgressOverlay;
import com.openvehicles.OVMS.utils.MyElement;

import java.util.HashMap;

public class BaseFragment extends SherlockFragment implements ApiObserver {

	public HashMap<String, String> mSentCommandMessage;

	public ProgressOverlay mProgressOverlay;
	public boolean mProgressShowOnStart;


	public BaseFragment() {
		mSentCommandMessage = new HashMap<String, String>();
	}


	// create progress overlay: (call this from onCreateView):
	// ATT: if you enable showOnStart you need to take care about resumes etc.
	public ProgressOverlay createProgressOverlay(LayoutInflater inflater, ViewGroup container, boolean showOnStart) {
		mProgressOverlay = new ProgressOverlay(inflater, container);
		mProgressShowOnStart = showOnStart;
		return mProgressOverlay;
	}

	// show/switch progress overlay in indeterminate mode (spinner icon):
	public void showProgressOverlay() {
		if (mProgressOverlay != null)
			mProgressOverlay.show();
	}

	// show/switch progress overlay in indeterminate mode (spinner icon):
	public void showProgressOverlay(String message) {
		if (mProgressOverlay != null) {
			mProgressOverlay.setLabel(message);
			mProgressOverlay.show();
		}
	}

	// show/switch progress overlay in determinate mode (bar),
	//	hide overlay if maxPos reached:
	public void stepProgressOverlay(int pos, int maxPos) {
		stepProgressOverlay(pos, maxPos, 0, 0);
	}

	// show/switch progress overlay in determinate mode (bar),
	//  with optional sub step progress (if stepCnt > 0)
	//	hide overlay if maxPos reached:
	public void stepProgressOverlay(int pos, int maxPos, int step, int stepCnt) {
		if (mProgressOverlay != null)
			mProgressOverlay.step(pos, maxPos, step, stepCnt);
	}

	// show/switch progress overlay in determinate mode (bar),
	//  with optional sub step progress (if stepCnt > 0)
	//	hide overlay if maxPos reached:
	public void stepProgressOverlay(String message, int pos, int maxPos, int step, int stepCnt) {
		if (mProgressOverlay != null) {
			mProgressOverlay.setLabel(message);
			mProgressOverlay.step(pos, maxPos, step, stepCnt);
		}
	}

	// hide progress overlay:
	public void hideProgressOverlay() {
		if (mProgressOverlay != null)
			mProgressOverlay.hide();
	}

	private static final String TAG = "OVMS";
	
	private BaseApi mBaseApi;

	@Override
	public void onStart() {
		super.onStart();

		if (mProgressOverlay != null && mProgressShowOnStart)
			mProgressOverlay.show();

		ApiObservable.get().addObserver(this);
		ApiService service = getService();
		if (service != null) {
			onServiceAvailable(service);
			if (service.isLoggedIn())
				update(service.getCarData());
		}
	}

	@Override
	public void onStop() {
		super.onStop();

		ApiObservable.get().deleteObserver(this);

		if (mProgressOverlay != null)
			mProgressOverlay.hide();
	}

	@Override
	public void update(CarData pCarData) {
	}

	@Override
	public void onServiceAvailable(ApiService pService) {
	}

	public String getSentCommandMessage(String cmd) {
		String msg = mSentCommandMessage.get(cmd);
		return (msg == null) ? cmd : msg;
	}

	public void cancelCommand() {
		ApiService service = getService();
		if (service == null)
			return;
		service.cancelCommand();
		mSentCommandMessage.clear();
	}

	public View findViewById(int pResId) {
		return getView().findViewById(pResId);
	}

	// Commands and expected responses for message types "C" and "c".
	// See "Commands and Expected Responses" header of the
	// https://github.com/openvehicles/Open-Vehicle-Monitoring-System/blob/master/docs/OVMS_Protocol.docx
	// for details. 
	public void sendCommand(int pResIdMessage, String pCommand,
							OnResultCommandListener pOnResultCommandListener) {
		sendCommand(getString(pResIdMessage), pCommand, pOnResultCommandListener);
		try
		{
			if(mBaseApi == null)
				mBaseApi = new BaseApi();
			mBaseApi.sendCommand(pResIdMessage, pCommand, pOnResultCommandListener);
		}
		catch (Exception e)
		{
			Log.e(TAG, MyElement.getName() + " " + e);
		}
	}
	
	// Commands and expected responses for retrieve 24 hour log data
	public void sendCommandRetrieve24hourLogData(int pResIdMessage, String pCommand,
			OnResultCommandListener pOnResultCommandListener) {
		try
		{
			if(mBaseApi == null)
				mBaseApi = new BaseApi();
			mBaseApi.sendCommandRetrieve24hourLogData(pResIdMessage, pCommand, pOnResultCommandListener);
		}
		catch (Exception e)
		{
			Log.e(TAG, MyElement.getName() + " " + e);
		}
	}

	public void sendCommand(String pMessage, String pCommand,
							OnResultCommandListener pOnResultCommandListener) {

		ApiService service = getService();
		if (service == null)
			return;

		// remember pMessage for result display:
		try {
			String cmd = pCommand.split(",", 2)[0];
			mSentCommandMessage.put(cmd, pMessage);
		} catch (Exception e) {
			// ignore
		}

		// pass on to API service:
		service.sendCommand(pMessage, pCommand, pOnResultCommandListener);
	}

	public void sendCommand(String pCommand,
							OnResultCommandListener pOnResultCommandListener) {
		ApiService service = getService();
		if (service == null)
			return;
		service.sendCommand(pCommand, pOnResultCommandListener);
	}

	public void changeCar(CarData pCarData) {
		ApiService service = getService();
		if (service == null)
			return;
		service.changeCar(pCarData);
	}

	public ApiService getService() {
//		Activity activity = getActivity();
		Activity activity = BaseApi.getActivity();
		if (activity instanceof ApiActivity) {
			return ((ApiActivity) activity).getService();
		}
		Log.e("MyLine", MyElement.getName() + " return null");
		return null;
	}

}
