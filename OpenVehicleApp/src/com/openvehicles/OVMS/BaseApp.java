package com.openvehicles.OVMS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.utils.Consts;

public class BaseApp extends Application {
	private static final String TAG = "BaseApp";
	private static BaseApp sInstance = null;
	private static ArrayList<CarData> sStoredCars;
	private static String sLastSelectedCarId;
	private static SharedPreferences sPreferences;	
	
	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
	}
	
	public static Context getContext() {
		return sInstance;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<CarData> getStoredCars() {
		if (sStoredCars != null) return sStoredCars; 
		
		File storedFile = new File(sInstance.getFilesDir()+ "/" + Consts.STOREDCARS_FILENAME);
		if (!storedFile.exists()) {
			sStoredCars = new ArrayList<CarData>();
			initDemoCar();
			return sStoredCars; 
		}
		
		Log.v(TAG, "Loading cars from file: " + storedFile);
		try {
			FileInputStream fis = new FileInputStream(storedFile);
			ObjectInputStream is = new ObjectInputStream(fis);
			sStoredCars = (ArrayList<CarData>) is.readObject();
			is.close();
		} catch (Exception e) {
			Log.e(TAG, "ERROR Loading cars from file: " + storedFile, e);
			initDemoCar();
		}
		return sStoredCars; 
	}
	
	public static void saveStoredCars() {
		if (sStoredCars == null) return;
		Log.d("OVMS", "Saving cars to interal storage...");
		try {

			FileOutputStream fos = sInstance.openFileOutput(Consts.STOREDCARS_FILENAME,
					Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(sStoredCars);
			os.close();
		} catch (Exception e) {
			Log.e(TAG, "ERROR Save cars to file", e);
		}
	}
	
	public static CarData getCarById(String pCarId) {
		if (sStoredCars == null) getStoredCars();
		for (CarData car: sStoredCars) {
			if (car.sel_vehicleid.equals(pCarId)) return car;
		}
		return null;
	}
	
	public static void initDemoCar() {
		Log.v(TAG, "Initializing demo car.");
		
		CarData demoCar = new CarData();
		demoCar.sel_vehicleid = "DEMO";
		demoCar.sel_vehicle_label = "Demonstration Car";
		demoCar.sel_server_password = "DEMO";
		demoCar.sel_module_password = "DEMO";
		demoCar.sel_vehicle_image = "car_roadster_lightninggreen";

		sStoredCars = new ArrayList<CarData>();
		sStoredCars.add(demoCar);
		
		saveStoredCars();
	}
	
	public static String getLastSelectedCarId() {
		if (!TextUtils.isEmpty(sLastSelectedCarId)) return sLastSelectedCarId;
		sLastSelectedCarId = getPrefs().getString("LASTSELECTEDCARID", null);
		return sLastSelectedCarId; 
	}
	
	public static CarData getSelectedCarData() {
		CarData result = getCarById(getLastSelectedCarId());
		if (result == null && sStoredCars.size() > 0) {
			result = sStoredCars.get(0);
		}
		return result;
	}
	
	public static void setSelectedCarId(String pCarId) {
		sLastSelectedCarId = pCarId;
		getPrefs().edit().putString("LASTSELECTEDCARID", sLastSelectedCarId).commit();
	}
	
	public static SharedPreferences getPrefs() {
		if (sPreferences == null) {
			sPreferences = PreferenceManager.getDefaultSharedPreferences(sInstance);
		}
		return sPreferences;
	}
}
