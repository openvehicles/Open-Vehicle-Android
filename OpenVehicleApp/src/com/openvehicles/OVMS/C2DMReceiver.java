package com.openvehicles.OVMS;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class C2DMReceiver extends BroadcastReceiver {
	private void handleMessage(Context paramContext, Intent paramIntent) {
		if ((!paramIntent.hasExtra("title"))
				|| (!paramIntent.hasExtra("message")))
			Log.d("ERR", "An invalid C2DM message was received.");
		while (true) {
			return;
			Log.d("C2DM", "C2DM Message Received");
			ArrayList localArrayList = null;
			try {
				Log.d("OVMS",
						"Loading saved cars from internal storage file: OVMSSavedCars.obj");
				ObjectInputStream localObjectInputStream = new ObjectInputStream(
						paramContext.openFileInput("OVMSSavedCars.obj"));
				localArrayList = (ArrayList) localObjectInputStream
						.readObject();
				localObjectInputStream.close();
				NotificationManager localNotificationManager = (NotificationManager) paramContext
						.getSystemService("notification");
				String str1 = paramIntent.getStringExtra("title");
				String str2 = paramIntent.getStringExtra("message");
				Integer i = 17301623;
				OVMSNotifications localOVMSNotifications = new OVMSNotifications(
						paramContext);
				localOVMSNotifications.AddNotification(str1.toString(),
						str2.toString());
				localOVMSNotifications.Save();
				if (localArrayList != null) {
					Iterator localIterator = localArrayList.iterator();
					if (localIterator.hasNext())
						;
				} else {
					Notification localNotification = new Notification(i, str2,
							System.currentTimeMillis());
					localNotification.flags = 16;
					localNotification.defaults = 7;
					Intent localIntent = new Intent(paramContext,
							OVMSActivity.class);
					localIntent.putExtra("SetTab", "tabInfo");
					localIntent
					.setAction("com.openvehicles.OVMS.NOTIFICATIONS_CLICK");
					localIntent.putExtra("VehicleID", str1);
					localIntent.setFlags(603979776);
					localNotification.setLatestEventInfo(paramContext, str1,
							str2, PendingIntent.getActivity(paramContext, 0,
									localIntent, 134217728));
					localNotificationManager.notify(1, localNotification);
				}
			} catch (Exception localException) {
				while (true) {
					String str1;
					int i;
					Iterator localIterator;
					localException.printStackTrace();
					continue;
					CarData localCarData = (CarData) localIterator.next();
					if (localCarData.VehicleID.equals(str1))
						i = paramContext.getResources().getIdentifier(
								localCarData.VehicleImageDrawable + "32x32",
								"drawable", "com.openvehicles.OVMS");
				}
			}
		}
	}

	private void handleRegistration(Context paramContext, Intent paramIntent) {
		String str1 = paramIntent.getStringExtra("registration_id");
		String str2;
		if (paramIntent.getStringExtra("error") != null) {
			Log.d("C2DM", "registration failed");
			str2 = paramIntent.getStringExtra("error");
			if (str2 == "SERVICE_NOT_AVAILABLE")
				Log.d("C2DM", "SERVICE_NOT_AVAILABLE");
		}
		while (true) {
			return;
			if (str2 == "ACCOUNT_MISSING") {
				Log.d("C2DM", "ACCOUNT_MISSING");
			} else if (str2 == "AUTHENTICATION_FAILED") {
				Log.d("C2DM", "AUTHENTICATION_FAILED");
			} else if (str2 == "TOO_MANY_REGISTRATIONS") {
				Log.d("C2DM", "TOO_MANY_REGISTRATIONS");
			} else if (str2 == "INVALID_SENDER") {
				Log.d("C2DM", "INVALID_SENDER");
			} else if (str2 == "PHONE_REGISTRATION_ERROR") {
				Log.d("C2DM", "PHONE_REGISTRATION_ERROR");
				continue;
				if (paramIntent.getStringExtra("unregistered") != null) {
					Log.d("C2DM", "unregistered");
				} else if (str1 != null) {
					Log.d("C2DM", "New C2DM ID: " + str1);
					SharedPreferences.Editor localEditor = paramContext
							.getSharedPreferences("C2DM", 0).edit();
					localEditor.putString("RegID", str1);
					localEditor.commit();
				}
			}
		}
	}

	public void onReceive(Context paramContext, Intent paramIntent) {
		if (paramIntent.getAction().equals(
				"com.google.android.c2dm.intent.REGISTRATION"))
			handleRegistration(paramContext, paramIntent);
		while (true) {
			return;
			if (paramIntent.getAction().equals(
					"com.google.android.c2dm.intent.RECEIVE"))
				handleMessage(paramContext, paramIntent);
		}
	}
}