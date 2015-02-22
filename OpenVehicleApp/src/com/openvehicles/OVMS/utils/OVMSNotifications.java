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
	private static final String TAG = "OVMSNotifications";
	private static final String SETTINGS_FILENAME = "OVMSSavedNotifications.obj";
	
	public ArrayList<NotificationData> notifications;
	private Context mContext;
	
	@SuppressWarnings("unchecked")
	public OVMSNotifications(Context context) {
		mContext = context;
		try {
			Log.d(TAG, "Loading saved notifications list from internal storage file: " + SETTINGS_FILENAME);
			FileInputStream fis = context.openFileInput(SETTINGS_FILENAME);
			ObjectInputStream is = new ObjectInputStream(fis);
			notifications = (ArrayList<NotificationData>) is.readObject();
			is.close();
			Log.d(TAG, String.format("Loaded %s saved notifications.", notifications.size()));
		} catch (Exception e) {
			//e.printStackTrace();
			Log.e(TAG, e.getMessage());

			Log.d(TAG, "Initializing with save notifications list.");
			notifications = new ArrayList<NotificationData>();
			
			// load demos
			addNotification(mContext.getText(R.string.pushnotifications).toString(),
							mContext.getText(R.string.pushnotifications_welcome).toString());
			save();
		}
	}
	
	public boolean addNotification(String title, String message) {

		NotificationData newNotify = new NotificationData(new Date(), title, message);

		// Check if new notification is a duplicate:
		if (notifications.size() > 0) {
			NotificationData lastNotify = notifications.get(notifications.size() - 1);
			if (newNotify.equals(lastNotify)) {
				Log.d(TAG, "addNotification: dropping duplicate");
				return false;
			}
		}

		notifications.add(newNotify);
		return true;
	}
	
	public void save() {
		try {
			Log.d(TAG, "Saving notifications list to internal storage...");

			FileOutputStream fos = mContext.openFileOutput(SETTINGS_FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(this.notifications);
			os.close();
			Log.d(TAG, String.format("Saved %s notifications.", notifications.size()));
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
	}
}
