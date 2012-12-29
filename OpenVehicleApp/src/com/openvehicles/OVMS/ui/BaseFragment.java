package com.openvehicles.OVMS.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragment;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.ApiService.ApiBinder;
import com.openvehicles.OVMS.api.ApiStatusObservable;
import com.openvehicles.OVMS.api.ApiStstusObserver;
import com.openvehicles.OVMS.api.OnResultCommandListenner;
import com.openvehicles.OVMS.entities.CarData;

public class BaseFragment extends SherlockFragment implements ApiStstusObserver {
	private ApiService mApiService;
	
	@Override
	public void onStart() {
		super.onStart();
		Log.e("BaseFragment", "onStart:" + getClass().getSimpleName());		
		
		Activity activity = getActivity();
        Intent intent = new Intent(activity, ApiService.class);
        activity.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		
		ApiStatusObservable.get().addObserver(this);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		ApiStatusObservable.get().deleteObserver(this);

		if (mApiService != null) {
        	getActivity().unbindService(mConnection);
        }
	}	

	@Override
	public void update(CarData pCarData) {
	}
	
	public View findViewById(int pResId) {
		return getView().findViewById(pResId);
	}
	
	public void sendCommand(int pResIdMessage, String pCommand) {
		if (mApiService == null) return;
		mApiService.sendCommand(pResIdMessage, pCommand);
	}
	
	public void sendCommand(String pMessage, String pCommand) {
		if (mApiService == null) return;
		mApiService.sendCommand(pMessage, pCommand);
	}
	
	public void sendCommand(String pCommand, OnResultCommandListenner pOnResultCommandListenner) {
		if (mApiService == null) return;
		mApiService.sendCommand(pCommand, pOnResultCommandListenner);
	}
	
	public void changeCar(CarData pCarData) {
		if (mApiService == null) return;
		mApiService.changeCar(pCarData);
	}
	
	public void onServiceBind() {
		Log.e("BaseFragment", "onServiceBind:" + getClass().getSimpleName());		
	}

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ApiBinder binder = (ApiBinder) service;
            mApiService = binder.getService();
            onServiceBind();
            
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        	mApiService = null;
        }
    };    

}
