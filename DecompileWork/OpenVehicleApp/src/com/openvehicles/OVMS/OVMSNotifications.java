package com.openvehicles.OVMS;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.Log;

public class OVMSNotifications {
	public ArrayList<NotificationData> Notifications;
	private Context mContext;
	private final String settingsFileName = "OVMSSavedNotifications.obj";

	public OVMSNotifications(Context paramContext) {
		this(paramContext, null);
	}

	public OVMSNotifications(Context paramContext, String paramString) {
		this.mContext = paramContext;
		while (true) {
			int i;
			try {
				Log.d("OVMS",
						"Loading saved notifications list from internal storage file: OVMSSavedNotifications.obj");
				ObjectInputStream localObjectInputStream = new ObjectInputStream(
						paramContext
						.openFileInput("OVMSSavedNotifications.obj"));
				this.Notifications = ((ArrayList) localObjectInputStream
						.readObject());
				localObjectInputStream.close();
				if (paramString != null) {
					i = -1 + this.Notifications.size();
				} else {
					Object[] arrayOfObject = new Object[1];
					arrayOfObject[0] = Integer.valueOf(this.Notifications
							.size());
					Log.d("OVMS", String.format(
							"Loaded %s saved notifications.", arrayOfObject));
					break;
					if (!((NotificationData) this.Notifications.get(i)).Title
							.equals(paramString))
						this.Notifications.remove(i);
					i--;
				}
			} catch (Exception localException) {
				Log.d("ERR", localException.getMessage());
				Log.d("OVMS", "Initializing with save notifications list.");
				this.Notifications = new ArrayList();
				AddNotification("Push Notifications",
						"Push notifications received for your registered vehicles are archived here.");
				Save();
				break;
			}
			if (i >= 0)
				;
		}
	}

	public void AddNotification(NotificationData paramNotificationData) {
		this.Notifications.add(paramNotificationData);
	}

	public void AddNotification(String paramString1, String paramString2) {
		Date localDate = new Date();
		this.Notifications.add(new NotificationData(localDate, paramString1,
				paramString2));
	}

	public void Clear() {
		this.Notifications = new ArrayList();
	}

	public void Save() {
		try {
			Log.d("OVMS", "Saving notifications list to interal storage...");
			ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(
					this.mContext.openFileOutput("OVMSSavedNotifications.obj",
							0));
			localObjectOutputStream.writeObject(this.Notifications);
			localObjectOutputStream.close();
			Object[] arrayOfObject = new Object[1];
			arrayOfObject[0] = Integer.valueOf(this.Notifications.size());
			Log.d("OVMS",
					String.format("Saved %s notifications.", arrayOfObject));
			return;
		} catch (Exception localException) {
			while (true) {
				localException.printStackTrace();
				Log.d("ERR", localException.getMessage());
			}
		}
	}
}