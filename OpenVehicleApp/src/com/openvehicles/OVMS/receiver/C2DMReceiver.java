package com.openvehicles.OVMS.receiver;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.MainActivity;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.OVMSNotifications;

public class C2DMReceiver extends BroadcastReceiver {
	private static String KEY = "C2DM";
	private static String REGISTRATION_KEY = "RegID";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) {
	        handleRegistration(context, intent);
	    } else if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
	        handleMessage(context, intent);
	    }
	 }

	private void handleRegistration(Context context, Intent intent) {
	    String registration = intent.getStringExtra("registration_id");
	    if (intent.getStringExtra("error") != null) {
	        // Registration failed, should try again later.
		    Log.d("c2dm", "registration failed");
		    String error = intent.getStringExtra("error");
		    if(error == "SERVICE_NOT_AVAILABLE"){
		    	Log.d("c2dm", "SERVICE_NOT_AVAILABLE");
		    }else if(error == "ACCOUNT_MISSING"){
		    	Log.d("c2dm", "ACCOUNT_MISSING");
		    }else if(error == "AUTHENTICATION_FAILED"){
		    	Log.d("c2dm", "AUTHENTICATION_FAILED");
		    }else if(error == "TOO_MANY_REGISTRATIONS"){
		    	Log.d("c2dm", "TOO_MANY_REGISTRATIONS");
		    }else if(error == "INVALID_SENDER"){
		    	Log.d("c2dm", "INVALID_SENDER");
		    }else if(error == "PHONE_REGISTRATION_ERROR"){
		    	Log.d("c2dm", "PHONE_REGISTRATION_ERROR");
		    }
	    } else if (intent.getStringExtra("unregistered") != null) {
	        // unregistration done, new messages from the authorized sender will be rejected
	    	Log.d("c2dm", "unregistered");

	    } else if (registration != null) {
	    	Log.d("c2dm", registration);
	    	Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
            editor.putString(REGISTRATION_KEY, registration);
    		editor.commit();
    		
    		Toast.makeText(context, "Push Notification Registered", Toast.LENGTH_SHORT).show();
    		
	       // Send the registration ID to the 3rd party site that is sending the messages.
	       // This should be done in a separate thread.
	       // When done, remember that all registration is done.
    		
    		//TODO:
	    }
	}

	@SuppressWarnings("unchecked")
	private void handleMessage(Context context, Intent intent) {
		// display message		
		if (!intent.hasExtra("title") || !intent.hasExtra("message")) {
			Log.d("ERR", "An invalid C2DM message was received.");
			return;
		}
		

		// set up notification

		CharSequence contentTitle = intent.getStringExtra("title");
		CharSequence contentText = intent.getStringExtra("message");
		CharSequence tickerText = contentText;

		// save notification to file
		OVMSNotifications savedList = new OVMSNotifications(context);
		boolean is_new = savedList.addNotification(contentTitle.toString(), contentText.toString());
		if (is_new) {

			// try to find the correct icon for this car
			int icon = 0;
			CarData car = CarsStorage.get().getCarById(contentTitle.toString());
			if (car != null)
				icon = Ui.getDrawableIdentifier(context, "map_" + car.sel_vehicle_image);
			if (icon == 0)
				icon = android.R.drawable.ic_lock_idle_alarm;

			// create Notification:
			long when = System.currentTimeMillis();
			Notification notification = new Notification(icon, tickerText, when);
			notification.flags = Notification.FLAG_AUTO_CANCEL;
			notification.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;

			Intent notificationIntent = new Intent(context, MainActivity.class);
			notificationIntent.putExtra("onNotification", true);
			PendingIntent launchOVMSIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
			notification.setLatestEventInfo(context, contentTitle, contentText, launchOVMSIntent);
			context.sendBroadcast(notificationIntent);

			// announce Notification via Android system:
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
			mNotificationManager.notify(1, notification);

			// update UI:
			Log.d("C2DM", "Notifications: sending Intent: " + context.getPackageName() + ".Notification");
			Intent uiNotify = new Intent(context.getPackageName() + ".Notification");
			context.sendBroadcast(uiNotify);
		}
	}
}
