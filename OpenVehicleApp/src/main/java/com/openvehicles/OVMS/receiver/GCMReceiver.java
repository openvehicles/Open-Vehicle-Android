package com.openvehicles.OVMS.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.MainActivity;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.OVMSNotifications;


public class GCMReceiver extends GcmListenerService {

	private static final String TAG = "GCMReceiver";


	@Override
	public void onMessageReceived(String from, Bundle data) {

		Context context = getApplicationContext();

		// get notification text:
		String contentTitle = data.getString("title");
		String contentText = data.getString("message");

		Log.i(TAG, "Notification received from=" + from + ", title=" + contentTitle + ", message=" + contentText);

		// save notification to file:
		OVMSNotifications savedList = new OVMSNotifications(context);
		boolean is_new = savedList.addNotification(contentTitle, contentText);
		if (is_new) {

			// try to find the correct icon for this car
			int icon = 0;
			CarData car = CarsStorage.get().getCarById(contentTitle);
			if (car != null)
				icon = Ui.getDrawableIdentifier(context, "map_" + car.sel_vehicle_image);
			if (icon == 0)
				icon = android.R.drawable.ic_lock_idle_alarm;

			// create Notification builder:
			NotificationCompat.Builder mBuilder =
					(NotificationCompat.Builder) new NotificationCompat.Builder(context)
							.setAutoCancel(true)
							.setDefaults(Notification.DEFAULT_ALL)
							.setSmallIcon(icon)
							.setContentTitle(contentTitle)
							.setContentText(contentText);

			// add Intents:
			Intent notificationIntent = new Intent(context, MainActivity.class);
			notificationIntent.putExtra("onNotification", true);
			PendingIntent launchOVMSIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
			mBuilder.setContentIntent(launchOVMSIntent);

			context.sendBroadcast(notificationIntent);

			// announce Notification via Android system:
			NotificationManager mNotificationManager =
					(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(1, mBuilder.build());

			// update UI:
			Log.d(TAG, "Notifications: sending Intent: " + context.getPackageName() + ".Notification");
			Intent uiNotify = new Intent(context.getPackageName() + ".Notification");
			context.sendBroadcast(uiNotify);
		}
	}

}
