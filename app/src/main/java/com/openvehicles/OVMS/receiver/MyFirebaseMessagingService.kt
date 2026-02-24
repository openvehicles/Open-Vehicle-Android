package com.openvehicles.OVMS.receiver

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.ApiService.Companion.sendKustomBroadcast
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.ui.MainActivity
import com.openvehicles.OVMS.ui.utils.Ui
import com.openvehicles.OVMS.ui2.MainActivityUI2
import com.openvehicles.OVMS.utils.CarsStorage
import com.openvehicles.OVMS.utils.OVMSNotifications
import com.openvehicles.OVMS.utils.Sys
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.locks.ReentrantLock

class MyFirebaseMessagingService : FirebaseMessagingService() {

    var appPrefs: AppPrefs? = null

    // lock to prevent concurrent uses of OVMSNotifications:
    // 	(necessary for dupe check)
    private val dbAccess = ReentrantLock()

    // timestamp parser:
    private var serverTime: SimpleDateFormat? = null

    override fun onCreate() {
        super.onCreate()
        appPrefs = AppPrefs(this, "ovms")

        // create timestamp parser:
        serverTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        serverTime!!.timeZone = TimeZone.getTimeZone("UTC")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val from = remoteMessage.from
        Log.d(TAG, "From: $from")

        // Extract message data payload.
        val data = remoteMessage.data
        var contentTitle = data["title"] // vehicle id
        var contentType = data["type"] // A=alert / I=info / E=error
        val contentText = data["message"]
        val contentTime = data["time"]
        Log.i(TAG, "Notification received from=$from, title=$contentTitle, type=$contentType, message=$contentText, time=$contentTime")
        if (contentTitle == null || contentText == null) {
            Log.w(TAG, "no title/message => abort")
            return
        }
        if (contentType == null) {
            contentType = OVMSNotifications.guessType(contentText)
        }

        // parse timestamp:
        var timeStamp: Date? = null
        try {
            timeStamp = serverTime!!.parse(contentTime)
        } catch (ignored: Exception) {
        }
        if (timeStamp == null) timeStamp = Date()

        // identify the vehicle:
        // Note: Use case-insensitive lookup to handle inconsistent casing between
        // server-sent notifications and locally stored vehicle IDs
        var car = CarsStorage.getCarById(contentTitle)
        if (car == null) {
            // Retry with case-insensitive search if direct match fails
            car = CarsStorage.getStoredCars().find { 
                it.sel_vehicleid.equals(contentTitle, ignoreCase = true) 
            }
            if (car == null) {
                Log.w(TAG, "vehicle ID '$contentTitle' not found (tried case-insensitive) => drop message")
                return
            } else {
                Log.i(TAG, "vehicle ID '$contentTitle' matched case-insensitively with '${car.sel_vehicleid}'")
                contentTitle = car.sel_vehicleid
            }
        }

        // add vehicle label to title:
        contentTitle += " (" + car.sel_vehicle_label + ")"

        // add notification to database:
        var isNew = true
        isNew = try {
            dbAccess.lock()
            val savedList = OVMSNotifications(this)
            savedList.addNotification(contentType, contentTitle, contentText, timeStamp)
        } finally {
            dbAccess.unlock()
        }
        var isUiNotified = false
        if (isNew) {
            // Send system broadcast for Automagic / Tasker / ...
            if (appPrefs!!.getData("option_broadcast_enabled", "0") == "1") {
                Log.d(TAG, "onMessageReceived: sending broadcast")
                val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val intent = Intent(ApiService.ACTION_NOTIFICATION)
                intent.putExtra("msg_notify_vehicleid", car.sel_vehicleid)
                intent.putExtra("msg_notify_vehicle_label", car.sel_vehicle_label)
                intent.putExtra("msg_notify_from", from)
                intent.putExtra("msg_notify_title", contentTitle)
                intent.putExtra("msg_notify_type", contentType)
                intent.putExtra("msg_notify_text", contentText)
                intent.putExtra("msg_notify_time", timeFormat.format(timeStamp))
                sendBroadcast(intent)
                sendKustomBroadcast(this, intent)
                isUiNotified = true
            }
        }

        // prepare user notification filter check:
        val filterInfo = appPrefs!!.getData("notifications_filter_info") == "on"
        val filterAlert = appPrefs!!.getData("notifications_filter_alert") == "on"
        val selectedCarId = CarsStorage.getLastSelectedCarId()
        val isSelectedCar = car.sel_vehicleid == selectedCarId
        if (!isNew) {
            Log.d(TAG, "message is duplicate => skip user notification")
        } else if (!isSelectedCar && filterInfo && contentType == "I") {
            Log.d(TAG, "info notify for other car => skip user notification")
        } else if (!isSelectedCar && filterAlert && contentType != "I") {
            Log.d(TAG, "alert/error notify for other car => skip user notification")
        } else {
            // add notification to system & UI:
            Log.d(TAG, "adding notification to system & UI")

            // create App launch Intent:
            val notificationIntent = Intent(this,
                if (appPrefs?.getData("option_oldui_enabled", "0") == "1") MainActivity::class.java
                else MainActivityUI2::class.java)
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            notificationIntent.putExtra("onNotification", true)
            val launchOVMSIntent = PendingIntent.getActivity(
                this, 0, notificationIntent,
                Sys.getMutableFlags(PendingIntent.FLAG_ONE_SHOT, false)
            )

            /*
                PNG icons (colored) are not shown anymore on newer androids, better use drawable vector
             */
            // determine icon for this car:
            /*val icon: Int = when {
                car.sel_vehicle_image.startsWith("car_imiev_") -> R.drawable.map_car_imiev // one map icon for all colors
                car.sel_vehicle_image.startsWith("car_i3_") -> R.drawable.map_car_i3 // one map icon for all colors
                car.sel_vehicle_image.startsWith("car_smart_") -> R.drawable.map_car_smart // one map icon for all colors
                car.sel_vehicle_image.startsWith("car_kianiro_") -> R.drawable.map_car_kianiro_grey // one map icon for all colors
                car.sel_vehicle_image.startsWith("car_kangoo_") -> R.drawable.map_car_kangoo // one map icon for all colors
                car.sel_vehicle_image.startsWith("car_nrjk") -> R.drawable.map_car_nrjk // one map icon for all colors and models
                else -> Ui.getDrawableIdentifier(this, "map_" + car.sel_vehicle_image)
            }*/

            // create Notification builder:
            val channelId = if (contentType == "I") "info" else "alert"
            val mBuilder = NotificationCompat.Builder(this, channelId)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // compatibility for Android < 8.0
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentTitle(contentTitle)
                .setContentText(contentText.replace('\r', '\n'))
                .setContentIntent(launchOVMSIntent)

            // announce Notification via Android system:
            val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.notify(1, mBuilder.build())

            // update UI (NotificationsFragment) if not done already:
            if (!isUiNotified) {
                val uiNotify = Intent(ApiService.ACTION_NOTIFICATION)
                sendBroadcast(uiNotify)
            }
        }
    }

    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "onNewToken: $token")

        // Notify MainActivity to update the push subscription at the OVMS server:
        val notify = Intent(MainActivity.ACTION_FCM_NEW_TOKEN)
        LocalBroadcastManager.getInstance(this).sendBroadcast(notify)
    }

    companion object {

        private const val TAG = "MyFirebaseMsgService"
    }
}
