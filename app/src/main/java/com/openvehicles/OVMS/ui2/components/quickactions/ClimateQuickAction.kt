package com.openvehicles.OVMS.ui2.components.quickactions

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService

/**
 * Quick action handling climate control
 */
class ClimateQuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null) :
    QuickAction(ACTION_ID, R.drawable.ic_ac, apiServiceGetter,
        actionOnTint = R.attr.colorSecondaryContainer,
        actionOffTint = R.color.cardview_dark_background,
        label = context?.getString(R.string.climate_control_short)) {
    companion object {
        const val ACTION_ID = "climate"
    }

    override fun onAction() {
        if (getCarData()?.car_type != "SQ") {
            if (getCarData()?.car_hvac_on == true)
                sendCommand("26,0")
            else
                sendCommand("26,1")
        } else {
            val context = context
                ?: return
            val items = arrayOf(
                context.getString(R.string.lb_5min),
                context.getString(R.string.lb_10min),
                context.getString(R.string.lb_15min),
                context.getString(R.string.lb_booster_time_off),
            )

            val builder = AlertDialog.Builder(context)
            var checkedItem = 0
            builder.setTitle(context.getString(R.string.climate_control_short))
            builder.setSingleChoiceItems(items, checkedItem) { _: DialogInterface, item: Int ->
                checkedItem = item // Update the selected item index
            }
            builder.setNegativeButton(R.string.Close, null)
            builder.setPositiveButton(R.string.execute) { _, _ ->
                val cmd: String = when (checkedItem) {
                    0 -> "24,0"
                    1 -> "24,1"
                    2 -> "24,2"
                    3 -> "7,metrics set xsq.climate.data 1,2,2,-1,-1,-1,-1"
                    else -> ""
                }
                sendCommand(cmd)
            }
            builder.show()
        }
    }


    override fun getStateFromCarData(): Boolean {
        return getCarData()?.car_hvac_on == true
    }



    override fun commandsAvailable(): Boolean {
        return this.getCarData()?.hasCommand(26) == true
                || getCarData()?.car_type in listOf("NL","SE","SQ","VWUP","VWUP.T26","RZ2")
    }
}