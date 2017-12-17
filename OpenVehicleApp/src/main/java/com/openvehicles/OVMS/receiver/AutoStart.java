package com.openvehicles.OVMS.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.api.ApiService;

/**
 * Created by balzer on 04.12.16.
 */

public class AutoStart extends BroadcastReceiver {

	private static final String TAG = "AutoStart";

	@Override
	public void onReceive(Context context, Intent intent) {
		AppPrefes appPrefes = new AppPrefes(context, "ovms");
		boolean serviceEnabled = appPrefes.getData("option_service_enabled", "0").equals("1");
		if (serviceEnabled) {
			Log.i(TAG, "Starting ApiService");
			context.startService(new Intent(context, ApiService.class));
		}
	}
}
