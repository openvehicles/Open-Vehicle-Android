package com.openvehicles.OVMS.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import com.openvehicles.OVMS.R;

import android.content.Context;
import android.util.Log;

public class OVMSNotifications {
	private static final String SETTINGS_FILENAME = "OVMSSavedNotifications.obj";
	
	public ArrayList<NotificationData> notifications;
	private Context mContext;
	
	@SuppressWarnings("unchecked")
	public OVMSNotifications(Context context) {
		mContext = context;
		try {
			Log.d("OVMS", "Loading saved notifications list from internal storage file: " + SETTINGS_FILENAME);
			FileInputStream fis = context.openFileInput(SETTINGS_FILENAME);
			ObjectInputStream is = new ObjectInputStream(fis);
			notifications = (ArrayList<NotificationData>) is.readObject();
			is.close();
			Log.d("OVMS", String.format("Loaded %s saved notifications.", notifications.size()));
		} catch (Exception e) {
			//e.printStackTrace();
			Log.d("ERR", e.getMessage());

			Log.d("OVMS", "Initializing with save notifications list.");
			notifications = new ArrayList<NotificationData>();
			
			// load demos
			addNotification(mContext.getText(R.string.pushnotifications).toString(),
							mContext.getText(R.string.pushnotifications_welcome).toString());
			save();
		}
	}
	
	public void addNotification(NotificationData notification) {
		notifications.add(notification);
	}
	
	public void addNotification(String title, String message) {
		notifications.add(new NotificationData(new Date(), title, message));
	}
	
	public void save() {
		try {
			Log.d("OVMS", "Saving notifications list to interal storage...");

			FileOutputStream fos = mContext.openFileOutput(SETTINGS_FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(this.notifications);
			os.close();
			Log.d("OVMS", String.format("Saved %s notifications.", notifications.size()));
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("ERR", e.getMessage());
		}
	}
}
