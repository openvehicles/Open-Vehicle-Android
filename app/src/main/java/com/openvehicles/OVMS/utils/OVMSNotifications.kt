package com.openvehicles.OVMS.utils

import android.content.Context
import android.util.Log
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.ui.utils.Database
import java.util.Date

class OVMSNotifications(
    context: Context
) {

    var notifications: ArrayList<NotificationData> = ArrayList(MAX_SIZE + 1)

    private val db: Database

    init {
        // load:
        Log.d(TAG, "Loading saved notifications list from database")
        db = Database(context)
        val cursor = db.notifications
        var data: NotificationData?
        while (db.getNextNotification(cursor).also { data = it } != null) {
            notifications.add(data!!)
        }
        cursor.close()
        Log.d(TAG, String.format("Loaded %d saved notifications", notifications.size))
        if (notifications.size == 0) {
            // first time: load welcome notification
            addNotification(
                NotificationData.TYPE_INFO,
                context.getText(R.string.pushnotifications).toString(),
                context.getText(R.string.pushnotifications_welcome).toString()
            )
        } else {
            db.beginWrite()
            removeOldNotifications()
            db.endWrite(true)
        }
    }

    @JvmOverloads
    fun addNotification(
        type: Int,
        title: String?,
        message: String?,
        timestamp: Date? = Date()
    ): Boolean {
        val newNotify = NotificationData(type, timestamp!!, title!!, message!!)

        // add to array, insert sorted by time, check for dupes:
        var pos: Int = notifications.size
        while (pos > 0) {
            val old = notifications[pos - 1]
            if (old.Timestamp.compareTo(timestamp) <= 0) {
                // found insert position, check for dupe:
                if (old.equals(newNotify)) {
                    Log.d(TAG, "addNotification: dropping duplicate")
                    return false
                }
                // ok, insert here:
                break
            }
            pos--
        }
        notifications.add(pos, newNotify)

        // add to database:
        db.beginWrite()
        db.addNotification(newNotify)
        removeOldNotifications()
        db.endWrite(true)
        return true
    }

    fun addNotification(
        stype: String?,
        title: String?,
        message: String?,
        timestamp: Date?
    ): Boolean {
        val type: Int = when (stype) {
            "A" -> NotificationData.TYPE_ALERT
            "E" -> NotificationData.TYPE_ERROR
            else -> NotificationData.TYPE_INFO
        }
        return addNotification(type, title, message, timestamp)
    }

    private fun removeOldNotifications() {
        var oldNotify: NotificationData
        if (notifications.size > MAX_SIZE) {
            while (notifications.size > MAX_SIZE) {
                oldNotify = notifications[0]
                notifications.removeAt(0)
                db.removeNotification(oldNotify)
            }
            Log.d(TAG, "removeOldNotifications: new size=" + notifications.size)
        }
    }

    fun getArray(filterId: String?): Array<NotificationData?> {
        val list: ArrayList<NotificationData>

        // filter notifications:
        if (filterId.isNullOrEmpty()) {
            list = notifications
            Log.d(TAG, "getArray: unfiltered => " + list.size + " notification(s)")
        } else {
            list = ArrayList()
            for (i in notifications.indices) {
                val n = notifications[i]
                if (n.isVehicleId(filterId)) {
                    list.add(n)
                }
            }
            Log.d(
                TAG,
                "getArray: filtered for " + filterId + " => " + list.size + " notification(s)"
            )
        }

        // convert to array:
        val data = arrayOfNulls<NotificationData>(list.size)
        list.toArray(data)
        return data
    }

    /*
     * Inner types
     */

    companion object {

        private const val TAG = "OVMSNotifications"
        private const val MAX_SIZE = 200

        fun guessType(message: String): String {
            // if the type classification is missing, we can only
            // try to derive the type from the text:
            return if (message.contains("ALERT") || message.contains("WARNING")) {
                "A"
            } else {
                "I"
            }
        }
    }
}
