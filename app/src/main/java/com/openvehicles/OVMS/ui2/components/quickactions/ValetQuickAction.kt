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
class ValetQuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null) :
    QuickAction(ACTION_ID, R.drawable.ic_valet, apiServiceGetter,
        actionOnTint = R.attr.colorSecondaryContainer,
        actionOffTint = R.attr.colorSurfaceContainer,
        label = context?.getString(R.string.lb_valet_mode)) {

    companion object {
        const val ACTION_ID = "valet"
    }

    override fun onAction() {
        val context = context
            ?: return
        val carData: CarData = getCarData()
            ?: return
        val dialogTitle: Int
        val dialogButton: Int
        val isPinEntry: Boolean
        when (carData.car_type) {
            "RT" -> {
                dialogTitle = R.string.lb_valet_mode_twizy
                dialogButton =
                    if (carData!!.car_valetmode) {
                        if (carData!!.car_locked) {
                            R.string.lb_unvalet_unlock_twizy
                        } else {
                            R.string.lb_valet_mode_off_twizy
                        }
                    } else {
                        R.string.lb_valet_mode_on_twizy
                    }
                isPinEntry = false
            }
            "SE" -> {
                dialogTitle = R.string.lb_valet_mode_smart
                dialogButton = if (carData!!.car_valetmode) {
                    R.string.lb_valet_mode_smart_off
                } else {
                    R.string.lb_valet_mode_smart_on
                }
                isPinEntry = true
            }
            else -> {
                dialogTitle = R.string.lb_valet_mode
                dialogButton = if (carData!!.car_valetmode) {
                    R.string.lb_valet_mode_off
                } else {
                    R.string.lb_valet_mode_on
                }
                isPinEntry = true
            }
        }

        // show dialog:
        Ui.showPinDialog(
            context,
            dialogTitle,
            dialogButton,
            isPinEntry,
            object : Ui.OnChangeListener<String?> {
                override fun onAction(data: String?) {
                    val cmd: String
                    if (carData.car_valetmode) {
                        cmd = "23,$data"
                    } else {
                        cmd = "21,$data"
                    }
                    sendCommand(cmd)
                }
            }, true)
    }

    override fun onCommandFinish(command: String) {
        super.onCommandFinish(command)

    }

    override fun getStateFromCarData(): Boolean {
        return when (getCarData()?.car_type) {
            "SQ" -> {
                getCarData()?.car_handbrake_on == true
            }
            else -> {
                getCarData()?.car_valetmode == true
            }
        }
    }



    override fun commandsAvailable(): Boolean {
        return when (getCarData()?.car_type) {
            "SQ" -> false
            else -> true
        }
    }
}