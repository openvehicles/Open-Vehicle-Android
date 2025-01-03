package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import android.util.Log
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService

/**
 * Quick action handling Homelink
 */

class Homelink1QuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null):
    HomelinkQuickAction(0, R.drawable.ic_homelink_1, "${context?.getString(R.string.Garage)} 1", apiServiceGetter)

class Homelink2QuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null):
    HomelinkQuickAction(1, R.drawable.ic_homelink_2, "${context?.getString(R.string.Garage)} 2", apiServiceGetter)

class Homelink3QuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null):
    HomelinkQuickAction(2, R.drawable.ic_homelink_3, "${context?.getString(R.string.Garage)} 3", apiServiceGetter)
open class HomelinkQuickAction(val garageNumber: Int,  icon: Int, label: String?, apiServiceGetter: () -> ApiService?) :
    QuickAction("hl_$garageNumber", icon, apiServiceGetter, label = label) {

    override fun onAction() {
        sendCommand("24,${garageNumber}")
    }

    override fun getStateFromCarData(): Boolean {
        return true
    }

    override fun commandsAvailable(): Boolean {
        return getCarData()?.car_type != "RT"
    }
}