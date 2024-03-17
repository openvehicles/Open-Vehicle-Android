package com.openvehicles.OVMS.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.utils.AppPrefes

/**
 * Created by balzer on 04.12.16.
 */
class AutoStart : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val appPrefes = AppPrefes(context, "ovms")
        val serviceEnabled = appPrefes.getData("option_service_enabled", "0") == "1"
        if (serviceEnabled) {
            Log.i(TAG, "Starting ApiService")
            try {
                context.startService(Intent(context, ApiService::class.java))
            } catch (e: Exception) {
                Log.e(TAG, "Can't start ApiService: ERROR", e)
            }
        }
    }

    companion object {

        private const val TAG = "AutoStart"
    }
}
