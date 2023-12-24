package com.openvehicles.OVMS.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.api.ApiObservable;
import com.openvehicles.OVMS.api.ApiObserver;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.ApiService.ApiBinder;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.utils.CarsStorage;

public class ApiActivity extends AppCompatActivity
	implements ApiObserver {
	private static final String TAG = "ApiActivity";

	protected ApiService mApiService;
	
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
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
		if (mApiService != null) {
			mApiService.onActivityStart();
		}
		ApiObservable.get().addObserver(this);
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStop");
		ApiObservable.get().deleteObserver(this);
		if (mApiService != null) {
			mApiService.onActivityStop();
		}
		super.onStop();
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

	@Override
	public void update(CarData pCarData) {
		// Override as needed
	}

	@Override
	public void onServiceAvailable(ApiService pService) {
		// Override as needed
	}

	@Override
	public void onServiceLoggedIn(ApiService pService, boolean pIsLoggedIn) {
		// Override as needed
	}

	public boolean hasService() {
		return (mApiService != null);
	}

	public boolean isOnline() {
		return (mApiService != null && mApiService.isOnline());
	}

	public boolean isLoggedIn() {
		return (mApiService != null && mApiService.isLoggedIn());
	}

	public boolean changeCar(String pVehicleId) {
		CarData carData = CarsStorage.get().getCarById(pVehicleId);
		if (carData != null) {
			return changeCar(carData);
		}
		return false;
	}

	public boolean changeCar(CarData pCarData) {
		AppPrefes prefs = new AppPrefes(this, "ovms");
		Database database = new Database(this);
		Log.i(TAG, "changeCar: switching to vehicle ID " + pCarData.sel_vehicleid);

		// select car:
		CarsStorage.get().setSelectedCarId(pCarData.sel_vehicleid);
		prefs.SaveData("sel_vehicle_label", pCarData.sel_vehicle_label);
		prefs.SaveData("autotrack", "on");
		prefs.SaveData("Id", database.getConnectionFilter(pCarData.sel_vehicle_label));

		// inform API service:
		if (mApiService == null) {
			return false;
		}
		mApiService.changeCar(pCarData);
		return true;
	}

}
