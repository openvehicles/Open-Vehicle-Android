package com.openvehicles.OVMS.utils

import com.openvehicles.OVMS.R
import java.io.Serializable
import java.util.Date

class NotificationData : Serializable {

    @JvmField
    var ID: Long
    @JvmField
    var Type: Int
    @JvmField
    var Timestamp: Date
    @JvmField
    var Title: String
    @JvmField
    var Message: String

    val messageFormatted: String
        get() {
            // default: use line breaks as sent by the module:
            return Message.replace('\r', '\n')
        }

    val icon: Int
        get() = when (Type) {
            TYPE_ALERT, TYPE_ERROR -> android.R.drawable.ic_dialog_alert
            TYPE_USSD -> android.R.drawable.ic_menu_call
            TYPE_COMMAND -> R.drawable.ic_action_send
            TYPE_RESULT_SUCCESS -> android.R.drawable.ic_menu_revert
            TYPE_RESULT_ERROR -> android.R.drawable.ic_menu_help
            else -> android.R.drawable.ic_menu_info_details
        }

    constructor(ID: Long, type: Int, timestamp: Date, title: String, message: String) {
        this.ID = ID
        Type = type
        Timestamp = timestamp
        Title = title
        Message = message
    }

    constructor(type: Int, timestamp: Date, title: String, message: String) {
        ID = 0
        Type = type
        Timestamp = timestamp
        Title = title
        Message = message
    }

    constructor(timestamp: Date, title: String, message: String) {
        ID = 0
        Type = TYPE_INFO
        Timestamp = timestamp
        Title = title
        Message = message
    }

    // equals operator: used to detect duplicates
    fun equals(o: NotificationData): Boolean {
        return o.Timestamp === Timestamp && o.Type == Type && o.Title == Title && o.Message == Message
    }

    fun isVehicleId(vehicleId: String): Boolean {
        return Title == vehicleId ||
                Title.startsWith("$vehicleId:") ||
                Title.startsWith("$vehicleId ")
    }

    /*
     * Inner types
     */

    companion object {

        private const val serialVersionUID = -3173247800500433809L
        const val TYPE_INFO = 0 // "PI"
        const val TYPE_ALERT = 1 // "PA"
        const val TYPE_COMMAND = 2
        const val TYPE_RESULT_SUCCESS = 3
        const val TYPE_RESULT_ERROR = 4
        const val TYPE_USSD = 5
        const val TYPE_ERROR = 6 // "PE"
    }
}
