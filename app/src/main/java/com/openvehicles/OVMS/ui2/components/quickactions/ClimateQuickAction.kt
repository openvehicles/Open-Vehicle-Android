package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
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
        if (getCarData()?.car_hvac_on == true)
            sendCommand("26,0")
        else
            sendCommand("26,1")
    }


    override fun getStateFromCarData(): Boolean {
        return getCarData()?.car_hvac_on == true
    }



    override fun commandsAvailable(): Boolean {
        return this.getCarData()?.hasCommand(26) == true
                || getCarData()?.car_type == "NL"
                || getCarData()?.car_type == "SE"
                || getCarData()?.car_type == "SQ"
                || getCarData()?.car_type == "VWUP"
                || getCarData()?.car_type == "VWUP.T26"
    }
}