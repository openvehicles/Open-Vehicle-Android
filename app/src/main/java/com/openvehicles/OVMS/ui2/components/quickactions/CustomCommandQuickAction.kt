package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import android.graphics.drawable.Drawable
import com.openvehicles.OVMS.api.ApiService

/**
 * Quick action handling custom commands
 */
class CustomCommandQuickAction(actionId: String, val drawable: Drawable, val command: String, apiServiceGetter: () -> ApiService?, label: String? = null) :
    QuickAction(actionId, -1, apiServiceGetter, label = label) {

    override fun onAction() {
        sendCommand(ApiService.makeMsgCommand(command))
    }

    override fun getStateFromCarData(): Boolean {
        return false
    }

    override fun getLiveCarIcon(state: Boolean, context: Context): Drawable? {
        return drawable
    }

    override fun commandsAvailable(): Boolean {
        return true
    }
}