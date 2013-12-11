package com.openvehicles.OVMS;

import android.app.Application;
import com.testflightapp.lib.TestFlight;

public class BaseApp extends Application {
//	private static final String TAG = "BaseApp";
	private static BaseApp sInstance = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		TestFlight.takeOff(this, "e34e1b78-366e-49b4-bd0f-ec704a76213e");
	}
	
	public static BaseApp getApp() {
		return sInstance;
	}
	
}
