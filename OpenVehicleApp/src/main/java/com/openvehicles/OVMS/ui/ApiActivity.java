package com.openvehicles.OVMS.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.openvehicles.OVMS.api.ApiObservable;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.ApiService.ApiBinder;

public class ApiActivity extends AppCompatActivity {
	private static final String TAG = "ApiActivity";

	private ApiService mApiService;
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart: binding service");
        bindService(new Intent(this, ApiService.class), mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onStop() {
		if (mApiService != null) {
			Log.d(TAG, "onStop: unbinding service");
        	unbindService(mConnection);
        	mApiService = null;
        }
		super.onStop();
	}
	
	public ApiService getService() {
		return mApiService;
	}
	
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
			Log.d(TAG, "service connected");
            ApiBinder binder = (ApiBinder) service;
            mApiService = binder.getService();
            ApiObservable.get().notifyOnBind(mApiService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "service disconnected");
			mApiService = null;
        }
    };
	
}
