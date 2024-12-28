package com.openvehicles.OVMS.ui2.components.quickactions

import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.ui.utils.Ui

/**
 * Quick action handling climate control
 */
class HomeLinkQuickAction(actionName: String, icon: Int, val command: String, apiServiceGetter: () -> ApiService?) :
    QuickAction(actionName, icon, apiServiceGetter) {
    override fun renderAction() {
        super.renderAction()
    }

    override fun onAction() {
        sendCommand(command)
    }

    override fun onCommandFinish(command: String) {
        super.onCommandFinish(command)

    }

    override fun getStateFromCarData(): Boolean {
        return false
    }

    override fun commandsAvailable(): Boolean {
        return true
    }
}