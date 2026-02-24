package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import android.content.res.Configuration
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.ui.BaseFragmentActivity.Companion.show
import com.openvehicles.OVMS.ui.settings.CarInfoFragment

/**
 * Quick action carinfo
 */
class CarInfoQuickAction(apiServiceGetter: () -> ApiService?, context: Context? = null) :
    QuickAction(ACTION_ID, R.drawable.ic_homelink, apiServiceGetter,
        actionOnTint = R.attr.colorSecondaryContainer,
        actionOffTint = R.color.cardview_dark_background,
        label = context?.getString(R.string.service_notification_title)) {
    companion object {
        const val ACTION_ID = "carinfo"
    }

    override fun onAction() {
        val context = context
            ?: return
        show(
            context,
            CarInfoFragment::class.java,
            null,
            Configuration.ORIENTATION_UNDEFINED
        )
    }

    override fun getStateFromCarData(): Boolean {
        return true
    }

    override fun commandsAvailable(): Boolean {
        return getCarData()?.car_type in listOf("SQ")
    }
}