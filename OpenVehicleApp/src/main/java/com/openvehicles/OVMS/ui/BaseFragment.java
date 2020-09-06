package com.openvehicles.OVMS.ui;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.api.ApiObservable;
import com.openvehicles.OVMS.api.ApiObserver;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.OnResultCommandListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.ui.utils.ProgressOverlay;
import com.openvehicles.OVMS.utils.CarsStorage;

import java.util.HashMap;

public class BaseFragment extends Fragment implements ApiObserver {
	private static final String TAG = "BaseFragment";

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
		update(pService.getCarData());
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

	public void sendCommand(int pResIdMessage, String pCommand,
							OnResultCommandListener pOnResultCommandListener) {
		sendCommand(getString(pResIdMessage), pCommand, pOnResultCommandListener);
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
		AppPrefes prefs = new AppPrefes(getActivity(), "ovms");
		Database database = new Database(getActivity());
		Log.i(TAG, "changeCar: switching to vehicle ID " + pCarData.sel_vehicleid);

		// select car:
		CarsStorage.get().setSelectedCarId(pCarData.sel_vehicleid);
		prefs.SaveData("sel_vehicle_label", pCarData.sel_vehicle_label);
		prefs.SaveData("autotrack", "on");
		prefs.SaveData("Id", database.getConnectionFilter(pCarData.sel_vehicle_label));

		// inform API service:
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

	public AppCompatActivity getCompatActivity() {
		return (AppCompatActivity) getActivity();
	}

}
