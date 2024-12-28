package com.openvehicles.OVMS.ui2.components.quickactions

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService

/**
 * Quick action handling climate control
 */
class ChargingQuickAction(apiServiceGetter: () -> ApiService?) :
    QuickAction("charging", R.drawable.ic_charging, apiServiceGetter, actionOnTint = R.attr.colorPrimaryContainer, actionOffTint = R.attr.colorSurfaceContainer) {
    override fun renderAction() {
        super.renderAction()
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
        return this.getCarData()?.car_chargeport_open == true && getCarData()?.car_charge_state_i_raw != 0x101 && getCarData()?.car_charge_state_i_raw != 0x115
    }
}