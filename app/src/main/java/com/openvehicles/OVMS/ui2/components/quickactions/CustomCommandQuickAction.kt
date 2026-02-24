package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.CommandActivity
import com.openvehicles.OVMS.utils.AppPrefs

/**
 * Quick action handling custom commands
 */
class CustomCommandQuickAction(actionId: String, val drawable: Drawable, val command: String, apiServiceGetter: () -> ApiService?, label: String? = null) :
    QuickAction(actionId, -1, apiServiceGetter, label = label) {

    companion object {
        private const val TAG = "CustomCmdQA"
    }

    override fun onAction() {
        //sendCommand(ApiService.makeMsgCommand(command))
        if (context != null) {
            val appPrefs = AppPrefs(context!!, "ovms")
            val intent = Intent(context, CommandActivity::class.java).apply {
                setAction("com.openvehicles.OVMS.action.COMMAND")
                setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NO_USER_ACTION)
                putExtra("apikey", appPrefs.getData("APIKey"))
                putExtra("command", command)
            }
            Log.d(TAG, "Sending COMMAND intent for: " + command)
            context!!.startActivity(intent)
        }
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