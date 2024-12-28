package com.openvehicles.OVMS.ui2.components.quickactions

import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService

/**
 * Quick action handling climate control
 */
class ClimateQuickAction(apiServiceGetter: () -> ApiService?) :
    QuickAction("climate", R.drawable.ic_ac, apiServiceGetter, actionOnTint = R.attr.colorTertiaryContainer, actionOffTint = R.attr.colorSurfaceContainer) {
    override fun renderAction() {
        super.renderAction()
    }

    override fun onAction() {
        if (getCarData()?.car_hvac_on == true)
            sendCommand("26,1")
        else
            sendCommand("26,0")
    }

    override fun onCommandFinish(command: String) {
        super.onCommandFinish(command)

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