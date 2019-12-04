package com.openvehicles.OVMS.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openvehicles.OVMS.BaseApp;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.BatteryData;
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
		boolean doSave = false;

		// Already loaded?
		if (mStoredCars != null) {
			return mStoredCars;
		}

		// Load JSON car storage file:
		try {
			FileInputStream inputStream = mContext.openFileInput(Consts.STOREDCARS_FILENAME_JSON);
			Log.i(TAG, "getStoredCars: loading " + Consts.STOREDCARS_FILENAME_JSON);
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
			String json = sb.toString();
			Gson gson = new Gson();
			Type type = new TypeToken<ArrayList<CarData>>(){}.getType();
			mStoredCars = gson.fromJson(json, type);
		} catch (Exception e) {
			Log.e(TAG, "getStoredCars: failed loading " + Consts.STOREDCARS_FILENAME_JSON);
			e.printStackTrace();
		}

		// Fallback to old ObjectStream storage:
		if (mStoredCars == null || mStoredCars.size() == 0) {
			try {
				FileInputStream fis = mContext.openFileInput(Consts.STOREDCARS_FILENAME_OBJ);
				Log.i(TAG, "getStoredCars: fallback; loading " + Consts.STOREDCARS_FILENAME_OBJ);
				ObjectInputStream is = new ObjectInputStream(fis);
				mStoredCars = (ArrayList<CarData>) is.readObject();
				is.close();
				doSave = true; // upgrade to JSON storage
			} catch (Exception e) {
				Log.e(TAG, "getStoredCars: failed loading " + Consts.STOREDCARS_FILENAME_OBJ);
				e.printStackTrace();
			}
		}

		if (mStoredCars != null && mStoredCars.size() > 0) {
			Log.i(TAG, "getStoredCars: OK, loaded " + mStoredCars.size() + " car(s)");
		} else {
			Log.i(TAG, "getStoredCars: initializing car list");
			mStoredCars = new ArrayList<CarData>();
			initDemoCar();
			doSave = true;
		}

		// Upgrade to JSON:
		if (doSave) {
			saveStoredCars();
		}

		return mStoredCars;
	}

	public void saveStoredCars() {
		if (mStoredCars == null) {
			return;
		}

		// Save JSON car storage file:
		try {
			FileOutputStream fos = mContext.openFileOutput(Consts.STOREDCARS_FILENAME_JSON,
					Context.MODE_PRIVATE);
			Log.i(TAG, "saveStoredCars: saving to " + Consts.STOREDCARS_FILENAME_JSON);
			Gson gson = new Gson();
			String json = gson.toJson(mStoredCars);
			fos.write(json.getBytes());
			fos.close();
		} catch(Exception e) {
			Log.e(TAG, "saveStoredCars: FAILED saving to " + Consts.STOREDCARS_FILENAME_JSON);
			e.printStackTrace();
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

		String[] mServers = mContext.getResources().getStringArray(R.array.select_server_options);
		String[] mGcmSenders = mContext.getResources().getStringArray(R.array.select_server_gcm_senders);
		demoCar.sel_server = mServers[0];
		demoCar.sel_gcm_senderid = mGcmSenders[0];

		mStoredCars = new ArrayList<CarData>();
		mStoredCars.add(demoCar);
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
