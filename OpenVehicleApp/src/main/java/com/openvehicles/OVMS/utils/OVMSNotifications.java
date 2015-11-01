package com.openvehicles.OVMS.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.ui.utils.Database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class OVMSNotifications {
	private static final String TAG = "OVMSNotifications";
	private static final int MAX_SIZE = 200;

	public ArrayList<NotificationData> notifications;
	private Context mContext;
	private Database db;
	

	public OVMSNotifications(Context context) {

		mContext = context;

		// create storage space:
		notifications = new ArrayList<NotificationData>(MAX_SIZE+1);

		// load:
		Log.d(TAG, "Loading saved notifications list from database");
		db = new Database(context);
		Cursor cursor = db.getNotifications();
		NotificationData data;
		while ((data = db.getNextNotification(cursor)) != null) {
			notifications.add(data);
		}
		cursor.close();
		Log.d(TAG, String.format("Loaded %d saved notifications", notifications.size()));

		if (notifications.size() == 0) {
			// first time: load welcome notification
			addNotification(
					mContext.getText(R.string.pushnotifications).toString(),
					mContext.getText(R.string.pushnotifications_welcome).toString());
		} else {
			db.beginWrite();
			removeOldNotifications();
			db.endWrite(true);
		}
	}


	public boolean addNotification(int type, String title, String message) {

		NotificationData newNotify = new NotificationData(type, new Date(), title, message);

		// Check if new notification is a duplicate:
		if (notifications.size() > 0) {
			NotificationData lastNotify = notifications.get(notifications.size() - 1);
			if (newNotify.equals(lastNotify)) {
				Log.d(TAG, "addNotification: dropping duplicate");
				return false;
			}
		}

		db.beginWrite();
		notifications.add(newNotify);
		db.addNotification(newNotify);
		removeOldNotifications();
		db.endWrite(true);

		return true;
	}


	public boolean addNotification(String title, String message) {

		// unless a type classification is added to the protocol, we can only
		// try to derive the type from the text:
		int type;
		if (message.contains("ALERT") || message.contains("WARNING"))
			type = NotificationData.TYPE_ALERT;
		else
			type = NotificationData.TYPE_INFO;

		return addNotification(type, title, message);
	}


	private void removeOldNotifications() {
		NotificationData oldNotify;

		if (notifications.size() > MAX_SIZE) {
			while (notifications.size() > MAX_SIZE) {
				oldNotify = notifications.get(0);
				notifications.remove(0);
				db.removeNotification(oldNotify);
			}
			Log.d(TAG, "removeOldNotifications: new size=" + notifications.size());
		}
	}


}
