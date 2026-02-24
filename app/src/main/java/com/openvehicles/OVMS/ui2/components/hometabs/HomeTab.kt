package com.openvehicles.OVMS.ui2.components.hometabs

class HomeTab {
    var tabId: Int
    var tabIcon: Int
    var tabName: String
    var tabDesc: String? = null
    var tabColor: Int? = null

    constructor(tabId: Int, tabIcon: Int, tabName: String, tabDesc: String?) {
        this.tabId = tabId
        this.tabIcon = tabIcon
        this.tabName = tabName
        this.tabDesc = tabDesc
    }
}