package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService

/**
 * Quick action handling custom commands
 */
class WakeupQuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null) :
    QuickAction(ACTION_ID, R.drawable.ic_wakeup, apiServiceGetter, label = context?.getString(R.string.Wakeup)) {

    companion object {
        const val ACTION_ID = "wakeup"
    }

    override fun onAction() {
        sendCommand("18")
    }

    override fun getStateFromCarData(): Boolean {
        return false
    }

    override fun commandsAvailable(): Boolean {
        return true
    }
}