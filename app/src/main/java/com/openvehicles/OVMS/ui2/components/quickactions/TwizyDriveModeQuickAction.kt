package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService

/**
 * Quick action handling Twizy driver profiles
 */
class TwizyDriveModeDefaultQuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null):
    TwizyDriveModeQuickAction(0, R.drawable.ic_default, "Default ${context?.getString(R.string.Profile)}", apiServiceGetter)

class TwizyDriveMode1QuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null):
    TwizyDriveModeQuickAction(1, R.drawable.ic_profile1, "${context?.getString(R.string.Profile)} 1", apiServiceGetter)

class TwizyDriveMode2QuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null):
    TwizyDriveModeQuickAction(2, R.drawable.ic_profile2, "${context?.getString(R.string.Profile)} 2", apiServiceGetter)

class TwizyDriveMode3QuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null):
    TwizyDriveModeQuickAction(3, R.drawable.ic_profile3, "${context?.getString(R.string.Profile)} 3", apiServiceGetter)
open class TwizyDriveModeQuickAction(private val profileNumber: Int, icon: Int, label: String?, apiServiceGetter: () -> ApiService?) :
    QuickAction("rt_profile_${profileNumber}", icon, apiServiceGetter,
        actionOnTint = R.attr.colorSecondaryContainer,
        actionOffTint = R.attr.colorTertiaryContainer, label = label) {

    override fun onAction() {
        sendCommand(if (profileNumber == 0) "24" else "24,${profileNumber}")
    }

    override fun getStateFromCarData(): Boolean {
        return getCarData()?.rt_cfg_profile_user == profileNumber
    }

    override fun commandsAvailable(): Boolean {
        return getCarData()?.car_type == "RT"
    }
}