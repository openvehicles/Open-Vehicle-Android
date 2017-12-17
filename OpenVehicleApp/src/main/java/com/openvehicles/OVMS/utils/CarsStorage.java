package com.openvehicles.OVMS.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.openvehicles.OVMS.BaseApp;
import com.openvehicles.OVMS.entities.CarData;

public class CarsStorage {
	private static final String TAG = "CarsStorage";

	private static CarsStorage sInstance;
	private final Context mContext = BaseApp.getApp();
	private ArrayList<CarData> mStoredCars;
	private String mLastSelectedCarId;
	private SharedPreferences mPreferences;	
	
	public static CarsStorage get() {
		if (sInstance == null) {
			sInstance = new CarsStorage(); 
		}
		return sInstance;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<CarData> getStoredCars() {
		if (mStoredCars != null) return mStoredCars; 
		
		File storedFile = new File(mContext.getFilesDir()+ "/" + Consts.STOREDCARS_FILENAME);
		if (!storedFile.exists()) {
			mStoredCars = new ArrayList<CarData>();
			initDemoCar();
			return mStoredCars; 
		}
		
		Log.v(TAG, "Loading cars from file: " + storedFile);
		try {
			FileInputStream fis = new FileInputStream(storedFile);
			ObjectInputStream is = new ObjectInputStream(fis);
			mStoredCars = (ArrayList<CarData>) is.readObject();
			is.close();
		} catch (Exception e) {
			Log.e(TAG, "ERROR Loading cars from file: " + storedFile, e);
			initDemoCar();
		}
		return mStoredCars; 
	}
	
	public void saveStoredCars() {
		if (mStoredCars == null) return;
		Log.d(TAG, "Saving cars to interal storage...");
		try {

			FileOutputStream fos = mContext.openFileOutput(Consts.STOREDCARS_FILENAME,
					Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(mStoredCars);
			os.close();
		} catch (Exception e) {
			Log.e(TAG, "ERROR Save cars to file", e);
		}
	}
	
	public CarData getCarById(String pCarId) {
		if (mStoredCars == null) getStoredCars();
		for (CarData car: mStoredCars) {
			if (car.sel_vehicleid.equals(pCarId)) return car;
		}
		return null;
	}
	
	public void initDemoCar() {
		Log.v(TAG, "Initializing demo car.");
		
		CarData demoCar = new CarData();
		demoCar.sel_vehicleid = "DEMO";
		demoCar.sel_vehicle_label = "Demonstration Car";
		demoCar.sel_server_password = "DEMO";
		demoCar.sel_module_password = "DEMO";
		demoCar.sel_vehicle_image = "car_roadster_lightninggreen";

		mStoredCars = new ArrayList<CarData>();
		mStoredCars.add(demoCar);
		
		saveStoredCars();
	}
	
	public String getLastSelectedCarId() {
		if (!TextUtils.isEmpty(mLastSelectedCarId)) return mLastSelectedCarId;
		mLastSelectedCarId = getPrefs().getString("LASTSELECTEDCARID", null);
		return mLastSelectedCarId; 
	}
	
	public CarData getSelectedCarData() {
		CarData result = getCarById(getLastSelectedCarId());
		if (result == null && mStoredCars.size() > 0) {
			result = mStoredCars.get(0);
		}
		return result;
	}
	
	public void setSelectedCarId(String pCarId) {
		mLastSelectedCarId = pCarId;
		getPrefs().edit().putString("LASTSELECTEDCARID", mLastSelectedCarId).commit();
	}

	public SharedPreferences getPrefs() {
		if (mPreferences == null) {
			mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		}
		return mPreferences;
	}

}
