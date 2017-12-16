package com.openvehicles.OVMS;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class BaseApp extends Application {
//	private static final String TAG = "BaseApp";
	private static BaseApp sInstance = null;
	private static Context sContext = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		sContext = getApplicationContext();
	}

	public static BaseApp getApp() {
		return sInstance;
	}

	public static Context getContext() {
		return sContext;
	}

}
