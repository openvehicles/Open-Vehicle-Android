package com.openvehicles.OVMS.api;

import java.util.Arrays;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.utils.CarsStorage;

public class ApiService extends Service implements OnUpdateStatusListener {
	private static final String TAG = "MainActivity";
    private final IBinder mBinder = new ApiBinder();
	private volatile CarData mCarData;
    private ApiTask mApiTask;
//	private AlertDialog mAlertDialog;
	private OnResultCommandListenner mOnResultCommandListenner;
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		changeCar(CarsStorage.get().getSelectedCarData());
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
		super.onDestroy();
	}
	
	public void changeCar(CarData pCarData) {
		Log.d(TAG, "Changed car to: " + pCarData.sel_vehicleid);
		mCarData = pCarData;

		// kill previous connection
		if (mApiTask != null) {
			Log.v("TCP", "Shutting down pervious TCP connection (ChangeCar())");
			mApiTask.connClose();
			mApiTask.cancel(true);
		}

		// start new connection
		// reset the paranoid mode flag in car data
		// it will be set again when the TCP task detects paranoid mode messages
		mCarData.sel_paranoid = false;
		mApiTask = new ApiTask(mCarData, this);
		
		Log.v(TAG, "Starting TCP Connection (changeCar())");
		mApiTask.execute();
	}
	
	public void sendCommand(int pResIdMessage, String pCommand) {
		sendCommand(getString(pResIdMessage), pCommand);
	}
	
	public void sendCommand(String pMessage, String pCommand) {
		if (mApiTask == null) return;

		mOnResultCommandListenner = null;
		mApiTask.sendCommand(String.format("MP-0 C%s", pCommand));
		Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show();
	}
	
	public void sendCommand(String pCommand, OnResultCommandListenner pOnResultCommandListenner) {
		if (mApiTask == null) return;
		
		mOnResultCommandListenner = pOnResultCommandListenner;
		mApiTask.sendCommand(String.format("MP-0 C%s", pCommand));
	}
	
	public void cancelCommand() {
		mOnResultCommandListenner = new OnResultCommandListenner() {
			@Override
			public void onResultCommand(String[] result) {
				Log.w(TAG, "Canceled result: " + Arrays.toString(result));
			}
		};
	}

	@Override
	public void onUpdateStatus() {
		ApiStatusObservable.get().notifyObservers(mCarData);		
	}

	@Override
	public void onServerSocketError(Throwable e) {
		Toast.makeText(this, mApiTask.isLoggedIn() ? R.string.err_connection_lost : R.string.err_check_following,
				Toast.LENGTH_LONG).show();
		
//		if (mAlertDialog != null && mAlertDialog.isShowing()){
//			return;
//		}
//
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setMessage(mApiTask.isLoggedIn() ? R.string.err_connection_lost : R.string.err_check_following)
//			.setTitle(R.string.lb_communications_problem)
//			.setCancelable(false)
//			.setPositiveButton(android.R.string.ok, null);
//		mAlertDialog = builder.create();
//		mAlertDialog.show();
	}

	@Override
	public void onResultCommand(String pCmd) {
		if (TextUtils.isEmpty(pCmd)) return;
		String[] data = pCmd.split(",\\s*");
		
		if (mOnResultCommandListenner != null) {
			mOnResultCommandListenner.onResultCommand(data);
			return;
		}
		
		if (data.length >= 3) {
			Toast.makeText(this, data[2], Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onLoginBegin() {
		Log.d(TAG, "onLoginBegin");
	}

	@Override
	public void onLoginComplete() {
		Log.d(TAG, "onLoginComplete");
	}
	
    public class ApiBinder extends Binder {
    	public ApiService getService() {
            return ApiService.this;
        }
    }

}
