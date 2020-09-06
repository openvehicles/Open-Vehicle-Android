package com.openvehicles.OVMS.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.openvehicles.OVMS.api.ApiObservable;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.ApiService.ApiBinder;

public class ApiActivity extends AppCompatActivity {
	private static final String TAG = "ApiActivity";

	private ApiService mApiService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate: binding service");
        bindService(new Intent(this, ApiService.class), mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onDestroy() {
		if (mApiService != null) {
			Log.d(TAG, "onDestroy: unbinding service");
        	unbindService(mConnection);
        	mApiService = null;
        }
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mApiService != null) {
			Log.d(TAG, "onResume: check connection");
			mApiService.checkConnection();
		}
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
