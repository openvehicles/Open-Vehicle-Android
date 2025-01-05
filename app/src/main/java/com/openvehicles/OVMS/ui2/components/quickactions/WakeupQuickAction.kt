package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService

/**
 * Quick action handling custom commands
 */
class WakeupQuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null) :
    QuickAction(ACTION_ID, R.drawable.ic_car_sleep, apiServiceGetter, label = context?.getString(R.string.Wakeup),
        actionOnTint = R.attr.colorSecondaryContainer,
        actionOffTint = R.color.cardview_dark_background) {

    companion object {
        const val ACTION_ID = "wakeup"
    }

    override fun onAction() {
        sendCommand("18")
    }

    override fun getStateFromCarData(): Boolean {
        return getCarData()?.car_awake == true
    }

    override fun commandsAvailable(): Boolean {
        return true
    }
}