package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService

/**
 * Quick action handling climate control
 */
class ChargingQuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null) :
    QuickAction(ACTION_ID, R.drawable.ic_charging, apiServiceGetter,
        actionOnTint = R.attr.colorSecondaryContainer,
        actionOffTint = R.color.cardview_dark_background, label = context?.getString(R.string.state_charging_label)) {

    companion object {
        const val ACTION_ID = "charging"
    }
    override fun onAction() {
        var context = context ?: return
        if (getCarData()?.car_charging == true) {
            MaterialAlertDialogBuilder(context)
                .setTitle(R.string.lb_charger_confirm_stop)
                .setNegativeButton(R.string.Cancel) {_, _ ->}
                .setPositiveButton(android.R.string.ok) { dlg, which -> sendCommand("12") }
                .show()
        } else {
            MaterialAlertDialogBuilder(context)
                .setTitle(R.string.lb_charger_confirm_start)
                .setNegativeButton(R.string.Cancel) {_, _ ->}
                .setPositiveButton(android.R.string.ok) { dlg, which -> sendCommand("11") }
                .show()
        }
    }

    override fun getStateFromCarData(): Boolean {
        return getCarData()?.car_charging == true
    }

    override fun commandsAvailable(): Boolean {
        return when (getCarData()?.car_type) {
            "SQ" -> false
            else -> true
        }
    }
}