package com.openvehicles.OVMS;

import android.app.Application;

public class BaseApp extends Application {
	private static BaseApp sInstance = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
	}
	
	public static BaseApp getApp() {
		return sInstance;
	}
	
}
