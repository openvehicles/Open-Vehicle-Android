package com.openvehicles.OVMS.api;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.openvehicles.OVMS.BuildConfig;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiTask.OnUpdateStatusListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.MyElement;
import com.openvehicles.OVMS.utils.MyException;

public class ApiService extends Service implements OnUpdateStatusListener {
	private static final String TAG = "MainActivity";
    private final IBinder mBinder = new ApiBinder();
	private volatile CarData mCarData;
    private ApiTask mApiTask;
	private OnResultCommandListener mOnResultCommandListener;
	private static OnResultCommandListener mOnResultCommandListenerRetrieve24hourLogData;
	
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
/*		
*	Attantion!!! Do not remove this comments
* because receiving of logged path is broke
* if user rotates device from portrait to landscape orientation.
* Currently service is not disconnected never, because I do not know where to do it
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
*/		
		super.onDestroy();
	}

	public void changeCar(CarData pCarData) {
		Log.d(TAG, "Changed car to: " + pCarData.sel_vehicleid);
		mCarData = pCarData;
		reconnectToServer();
	}
	
	public void reconnectToServer()
	{
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
		mApiTask = new ApiTask(mCarData, this);
		
		Log.v(TAG, "Starting TCP Connection (changeCar())");
		mApiTask.execute();
	}
		
	//For debugging
	public void socketClose() throws IOException
	{ mApiTask.socketClose();}

	public void sendCommand(int pResIdMessage, String pCommand, OnResultCommandListener pOnResultCommandListener) {
		sendCommand(getString(pResIdMessage), pCommand, pOnResultCommandListener);
	}

	public void sendCommand(String pMessage, String pCommand, OnResultCommandListener pOnResultCommandListener) {
		if (mApiTask == null) return;

		mOnResultCommandListener = pOnResultCommandListener;
		mApiTask.sendCommand(String.format("MP-0 C%s", pCommand));
		Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show();
	}
	
	public void sendCommandRetrieve24hourLogData(int pResIdMessage, String pCommand, OnResultCommandListener pOnResultCommandListener) {
		sendCommandRetrieve24hourLogData(getString(pResIdMessage), pCommand, pOnResultCommandListener);
	}

	public void sendCommandRetrieve24hourLogData(String pMessage, String pCommand, OnResultCommandListener pOnResultCommandListener) {
		if (mApiTask == null) return;

		mOnResultCommandListenerRetrieve24hourLogData = pOnResultCommandListener;
		mApiTask.sendCommand(String.format("MP-0 C%s", pCommand));
		Toast.makeText(this, pMessage, Toast.LENGTH_SHORT).show();
	}
	
	public boolean sendCommand(String pCommand, OnResultCommandListener pOnResultCommandListener) {
		if (mApiTask == null || TextUtils.isEmpty(pCommand)) return false;
		
		mOnResultCommandListener = pOnResultCommandListener;
		return mApiTask.sendCommand(pCommand.startsWith("MP-0") ? pCommand : String.format("MP-0 C%s", pCommand));
	}
	
	public void cancelCommand() {
		//mOnResultCommandListener = null;
	}

	@Override
	public void onUpdateStatus() {
		ApiObservable.get().notifyUpdate(mCarData);		
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
	public void onServerSocketErrorToast(Throwable e) {
		Intent intent = new Intent(getPackageName() + ".ApiEvent");
		intent.putExtra("onServerSocketErrorToast", e.getMessage());
		sendBroadcast(intent);
	}
	
	@Override
	public void onResultCommand(String pCmd) {
		if (TextUtils.isEmpty(pCmd)) return;
		String[] data = pCmd.split(",\\s*");
		
//Log.d("MyLine", MyElement.getName() + " mOnResultCommandListener = " + mOnResultCommandListener);
		if (mOnResultCommandListener != null) {
			mOnResultCommandListener.onResultCommand(data);
			return;
		}
	}
	
	@Override
	public void onResultCommandRetrieve24hourLogData(String pCmd) {
		if (TextUtils.isEmpty(pCmd)) return;
		String[] data = pCmd.split(",\\s*");
		
//Log.d("MyLine", MyElement.getName() + " mOnResultCommandListenerRetrieve24hourLogData = " + mOnResultCommandListenerRetrieve24hourLogData);
		if (mOnResultCommandListenerRetrieve24hourLogData != null) {
			mOnResultCommandListenerRetrieve24hourLogData.onResultCommand(data);
			return;
		}
		else throw new MyException(MyElement.getName() + " mOnResultCommandListenerRetrieve24hourLogData == null");
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
		if(BuildConfig.DEBUG && mCarData == null)
			Log.e("MyLine", MyElement.getName() + " mCarData == null");
		return mCarData;
	}
	
	public CarData getLogginedCarData() {
		if(mApiTask.isLoggedIn())
			return  mCarData;
		Log.e("MyLine", MyElement.getName() + " return  null");
		return  null;
	}
	
    public class ApiBinder extends Binder {
    	
    	public ApiService getService() {
            return ApiService.this;
        }
    	
    }

}
