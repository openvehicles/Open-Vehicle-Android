package com.openvehicles.OVMS.ui2.components.quickactions

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.utils.Ui

/**
 * Quick action handling door lock
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
        if (getCarData()?.car_type != "SQ") {
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
                }, true
            )
        } else {
            val context = context
                ?: return
            val items = arrayOf(
                context.getString(R.string.lb_lock_car),
                context.getString(R.string.lb_unlock_car),
                context.getString(R.string.lb_open_trunk),
                context.getString(R.string.lb_indicator_on),
            )

            val builder = AlertDialog.Builder(context)
            var checkedItem = 0
            builder.setTitle(context.getString(R.string.select_an_action))
            builder.setSingleChoiceItems(items, checkedItem) { _: DialogInterface, item: Int ->
                checkedItem = item // Update the selected item index
            }
            builder.setNegativeButton(R.string.Close, null)
            builder.setPositiveButton(R.string.execute) { _, _ ->
                val cmd: String
                cmd = when (checkedItem) {
                    0 -> "20, 1234"
                    1 -> "22, 1234"
                    2 -> "7,xsq ddt4all 1"
                    3 -> "7,xsq ddt4all 0"
                    else -> ""
                }
                sendCommand(cmd)
            }
            builder.show()
        }
    }

    override fun getLiveCarIconId(state: Boolean): Int {
        return if (state) R.drawable.ic_lock_closed else R.drawable.ic_lock_open
    }

    override fun getStateFromCarData(): Boolean {
        return getCarData()?.car_locked == true
    }

    override fun commandsAvailable(): Boolean {
        return when (getCarData()?.car_type) {
            "EN", "NRJK" -> false
            else -> true
        }
    }
}