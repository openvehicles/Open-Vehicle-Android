package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import android.widget.ImageView
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.utils.Ui

/**
 * Quick action handling climate control
 */
class LockQuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null) :
    QuickAction(ACTION_ID, R.drawable.ic_lock_open, apiServiceGetter,
        actionOnTint = R.attr.colorSecondaryContainer,
        actionOffTint = R.attr.colorTertiaryContainer,
        label = context?.getString(R.string.central_locking_action_label)) {

    companion object {
        const val ACTION_ID = "lock"
    }

    override fun onAction() {
        val context = context
            ?: return
        val carData: CarData = getCarData()
            ?: return
        val dialogTitle: Int
        val dialogButton: Int
        val isPinEntry: Boolean
        if (getCarData()?.car_type == "RT") {
            dialogTitle = R.string.lb_lock_mode_twizy
            dialogButton =
                if (carData.car_locked) {
                    if (carData.car_valetmode) {
                        R.string.lb_valet_mode_extend_twizy
                    } else {
                        R.string.lb_unlock_car_twizy
                    }
                } else {
                    R.string.lb_lock_car_twizy
                }
            isPinEntry = false
        } else {
            dialogTitle = if (carData.car_locked) {
                R.string.lb_unlock_car
            } else {
                R.string.lb_lock_car
            }
            dialogButton = dialogTitle
            isPinEntry = true
        }
        Ui.showPinDialog(
            context,
            dialogTitle,
            dialogButton,
            isPinEntry,
            object : Ui.OnChangeListener<String?> {
                override fun onAction(data: String?) {
                    val cmd: String
                    if (carData.car_locked) {
                        cmd = "22,$data"
                    } else {
                        cmd = "20,$data"
                    }
                    sendCommand(cmd)
                }
            }, true)
    }

    override fun getLiveCarIconId(state: Boolean): Int {
        return if (state) R.drawable.ic_lock_closed else R.drawable.ic_lock_open
    }

    override fun onCommandFinish(command: String) {
        super.onCommandFinish(command)

    }

    override fun getStateFromCarData(): Boolean {
        return when (getCarData()?.car_type) {
            "SQ" -> {
                getCarData()?.car_started == true
            }

            else -> {
                getCarData()?.car_locked == true
            }
        }
    }



    override fun commandsAvailable(): Boolean {
        return when (getCarData()?.car_type) {
            "EN", "NRJK" -> false
            "SQ" -> false
            else -> true
        }
    }
}