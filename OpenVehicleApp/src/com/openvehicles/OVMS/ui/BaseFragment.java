package com.openvehicles.OVMS.ui;

import android.app.Activity;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragment;
import com.openvehicles.OVMS.api.ApiObservable;
import com.openvehicles.OVMS.api.ApiObserver;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.OnResultCommandListenner;
import com.openvehicles.OVMS.entities.CarData;

import java.util.HashMap;

public class BaseFragment extends SherlockFragment implements ApiObserver {

	public HashMap<String, String> mSentCommandMessage;

	public BaseFragment() {
		mSentCommandMessage = new HashMap<String, String>();
	}

	@Override
	public void onStart() {
		super.onStart();
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
	}

	@Override
	public void update(CarData pCarData) {
	}

	@Override
	public void onServiceAvailable(ApiService pService) {
	}

	public String getSentCommandMessage(String cmd) {
		String msg = mSentCommandMessage.get(cmd);
		return (msg == null) ? "" : msg;
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

	public void sendCommand(int pResIdMessage, String pCommand,
			OnResultCommandListenner pOnResultCommandListenner) {
		sendCommand(getString(pResIdMessage), pCommand, pOnResultCommandListenner);
	}

	public void sendCommand(String pMessage, String pCommand,
			OnResultCommandListenner pOnResultCommandListenner) {

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
		service.sendCommand(pMessage, pCommand, pOnResultCommandListenner);
	}

	public void sendCommand(String pCommand,
			OnResultCommandListenner pOnResultCommandListenner) {
		ApiService service = getService();
		if (service == null)
			return;
		service.sendCommand(pCommand, pOnResultCommandListenner);
	}

	public void changeCar(CarData pCarData) {
		ApiService service = getService();
		if (service == null)
			return;
		service.changeCar(pCarData);
	}

	public ApiService getService() {
		Activity activity = getActivity();
		if (activity instanceof ApiActivity) {
			return ((ApiActivity) activity).getService();
		}
		return null;
	}

}
