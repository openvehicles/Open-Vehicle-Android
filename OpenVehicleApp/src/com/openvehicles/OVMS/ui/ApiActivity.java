package com.openvehicles.OVMS.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.openvehicles.OVMS.api.ApiObservable;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.ApiService.ApiBinder;

public class ApiActivity extends SherlockFragmentActivity {
	private static ApiService mApiService;
	
	@Override
	protected void onStart() {
		super.onStart();
		if(mApiService == null)
			bindService(new Intent(this, ApiService.class), mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onStop() {
/*
		 * Currently service is not disconnected never, because I do not know where to do it		
		if (mApiService != null) {
        	unbindService(mConnection);
        	mApiService = null;
        }
*/        
		super.onStop();
	}
	
	public ApiService getService() {
		return mApiService;
	}
	
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ApiBinder binder = (ApiBinder) service;
            mApiService = binder.getService();
            ApiObservable.get().notifyOnBind(mApiService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        	mApiService = null;
        }
    };
	
}
