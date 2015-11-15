/**
 * OVMS GCM listener
 *
 * (invoked by com.google.android.gms.gcm.GcmReceiver)
 */

package com.openvehicles.OVMS.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.MainActivity;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.OVMSNotifications;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.locks.ReentrantLock;


public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

	// lock to prevent concurrent uses of OVMSNotifications:
	// 	(necessary for dupe check)
	private final ReentrantLock dbAccess = new ReentrantLock();

	// timestamp parser:
	SimpleDateFormat serverTime;


	public MyGcmListenerService() {

		super();

		// create timestamp parser:
		serverTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		serverTime.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@Override
    public void onMessageReceived(String from, Bundle data) {

        // get notification text:
        String contentTitle = data.getString("title");
        String contentText = data.getString("message");
        String contentTime = data.getString("time");

        Log.i(TAG, "Notification received from=" + from
                + ", title=" + contentTitle
                + ", message=" + contentText
                + ", time=" + contentTime);

        if (contentTitle == null || contentText == null) {
            Log.w(TAG, "no title/message => abort");
            return;
        }

		// parse timestamp:
		Date timeStamp;
		try {
			timeStamp = serverTime.parse(contentTime);
		} catch (Exception e) {
			timeStamp = new Date();
		}

        // add notification to database:
		boolean is_new;
		dbAccess.lock();
		try {
			OVMSNotifications savedList = new OVMSNotifications(this);
			is_new = savedList.addNotification(contentTitle, contentText, timeStamp);
		} finally {
			dbAccess.unlock();
		}

		if (!is_new) {
			Log.d(TAG, "message is duplicate => ignore");
		} else {
            // add notification to system & UI:

            // create App launch Intent:
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            notificationIntent.putExtra("onNotification", true);
            PendingIntent launchOVMSIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_ONE_SHOT);

            // try to find the correct icon for this car
            int icon = 0;
            CarData car = CarsStorage.get().getCarById(contentTitle);
            if (car != null)
                icon = Ui.getDrawableIdentifier(this, "map_" + car.sel_vehicle_image);
            if (icon == 0)
                icon = android.R.drawable.ic_lock_idle_alarm;

            // create Notification builder:
            android.support.v7.app.NotificationCompat.Builder mBuilder =
                    (android.support.v7.app.NotificationCompat.Builder) new android.support.v7.app.NotificationCompat.Builder(this)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setSmallIcon(icon)
                            .setContentTitle(contentTitle)
                            .setContentText(contentText)
                            .setContentIntent(launchOVMSIntent);

            // announce Notification via Android system:
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());

            // update UI (NotificationsFragment):
            Log.d(TAG, "Notifications: sending Intent: " + getPackageName() + ".Notification");
            Intent uiNotify = new Intent(getPackageName() + ".Notification");
            sendBroadcast(uiNotify);
        }

    }
}
