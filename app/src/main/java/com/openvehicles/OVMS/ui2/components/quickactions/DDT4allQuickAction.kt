package com.openvehicles.OVMS.ui2.components.quickactions

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService

/**
 * Quick action handling none protected DDT4all commands
 */
class DDT4allQuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null) :
    QuickAction(ACTION_ID, R.drawable.ic_controls_tab, apiServiceGetter,
        actionOnTint = R.attr.colorSecondaryContainer,
        actionOffTint = R.attr.colorTertiaryContainer,
        label = context?.getString(R.string.select_ddt4all_action)) {

    companion object {
        const val ACTION_ID = "ddt4all"
    }

    override fun onAction() {

        val context = context
            ?: return
        val items = arrayOf(
            context.getString(R.string.lb_autowiper_off),
            context.getString(R.string.lb_autowiper_on),
            context.getString(R.string.lb_wifi),
            context.getString(R.string.lb_modem),
            context.getString(R.string.lb_bipbip_off),
            context.getString(R.string.lb_bipbip_on),
            context.getString(R.string.lb_rearwiper_off),
            context.getString(R.string.lb_rearwiper_on),
        )

        val builder = AlertDialog.Builder(context)
        var checkedItem = 0
        builder.setTitle(context.getString(R.string.select_ddt4all_action))
        builder.setSingleChoiceItems(items, checkedItem) { _: DialogInterface, item: Int ->
            checkedItem = item // Update the selected item index
        }
        builder.setNegativeButton(R.string.Close, null)
        builder.setPositiveButton(R.string.execute) { _, _ ->
            val cmd: String = when (checkedItem) {
                0 -> "7,xsq ddt4all 2"
                1 -> "7,xsq ddt4all 3"
                2 -> "7,xsq ddt4all 4"
                3 -> "7,xsq ddt4all 5"
                4 -> "7,xsq ddt4all 6"
                5 -> "7,xsq ddt4all 7"
                6 -> "7,xsq ddt4all 8"
                7 -> "7,xsq ddt4all 9"
                else -> ""
            }
            sendCommand(cmd)
        }
        builder.show()
    }

    override fun getStateFromCarData(): Boolean {
        return true
    }

    override fun commandsAvailable(): Boolean {
        return getCarData()?.car_type in listOf("SQ")
    }
}