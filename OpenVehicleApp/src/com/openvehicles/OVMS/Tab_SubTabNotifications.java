package com.openvehicles.OVMS;

import java.text.SimpleDateFormat;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Tab_SubTabNotifications extends ListActivity {
	private ItemsAdapter adapter;
	private NotificationData[] cachedData;
	private CarData data;
	private Handler handler = new Handler() {
		public void handleMessage(Message paramAnonymousMessage) {
			if ((!Tab_SubTabNotifications.this.lastVehicleID
					.equals(Tab_SubTabNotifications.this.data.VehicleID))
					&& (Tab_SubTabNotifications.this.notifications.Notifications
							.size() == 0))
				Toast.makeText(Tab_SubTabNotifications.this,
						"No notifications received", 0).show();
			if ((Tab_SubTabNotifications.this.lastVehicleID
					.equals(Tab_SubTabNotifications.this.data.VehicleID))
					&& (Tab_SubTabNotifications.this.notifications.Notifications
							.size() == Tab_SubTabNotifications.this.cachedData.length))
				return;
			Tab_SubTabNotifications.this.lastVehicleID = Tab_SubTabNotifications.this.data.VehicleID;
			NotificationData[] arrayOfNotificationData = new NotificationData[Tab_SubTabNotifications.this.notifications.Notifications
			                                                                  .size()];
			Tab_SubTabNotifications.this.notifications.Notifications
			.toArray(arrayOfNotificationData);
			Tab_SubTabNotifications.this.cachedData = new NotificationData[arrayOfNotificationData.length];
			for (int i = 0;; i++) {
				if (i >= Tab_SubTabNotifications.this.cachedData.length) {
					Tab_SubTabNotifications.this.adapter = new Tab_SubTabNotifications.ItemsAdapter(
							Tab_SubTabNotifications.this,
							Tab_SubTabNotifications.this.mContext, 2130903049,
							Tab_SubTabNotifications.this.cachedData);
					Tab_SubTabNotifications.this
					.setListAdapter(Tab_SubTabNotifications.this.adapter);
					break;
				}
				Tab_SubTabNotifications.this.cachedData[i] = arrayOfNotificationData[(-1
						+ arrayOfNotificationData.length - i)];
			}
		}
	};
	private boolean isLoggedIn;
	private String lastVehicleID = "";
	private Context mContext;
	private OVMSActivity mOVMSActivity;
	private OVMSNotifications notifications;

	public void Refresh(CarData paramCarData, boolean paramBoolean) {
		this.data = paramCarData;
		this.notifications = new OVMSNotifications(this, this.data.VehicleID);
		this.handler.sendEmptyMessage(0);
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(2130903048);
		this.mOVMSActivity = ((OVMSActivity) getParent().getParent());
		if (this.mOVMSActivity == null)
			this.mOVMSActivity = ((OVMSActivity) getParent());
		if (this.mOVMSActivity == null)
			Toast.makeText(this, "Unknown Layout Error", 1).show();
		this.notifications = new OVMSNotifications(this);
		this.mContext = this;
	}

	protected void onListItemClick(ListView paramListView, View paramView,
			int paramInt, long paramLong) {
		Log.d("OVMS", "Displaying notification: #" + paramInt);
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(getParent());
		localBuilder
		.setMessage(this.cachedData[paramInt].Message)
		.setTitle(this.cachedData[paramInt].Title)
		.setCancelable(false)
		.setPositiveButton("Close",
				new DialogInterface.OnClickListener() {
			public void onClick(
					DialogInterface paramAnonymousDialogInterface,
					int paramAnonymousInt) {
				paramAnonymousDialogInterface.dismiss();
			}
		});
		localBuilder.create().show();
	}

	private class ItemsAdapter extends ArrayAdapter<NotificationData> {
		private SimpleDateFormat dateFormatter;
		private NotificationData[] items;
		private SimpleDateFormat timeFormatter;

		public ItemsAdapter(Context paramInt, int paramArrayOfNotificationData,
				NotificationData[] arg4) {
			super(paramArrayOfNotificationData, arrayOfObject);
			this.items = arrayOfObject;
			this.dateFormatter = new SimpleDateFormat("MMMMM d");
			this.timeFormatter = new SimpleDateFormat("h:mm a");
		}

		public View getView(int paramInt, View paramView,
				ViewGroup paramViewGroup) {
			View localView = paramView;
			if (localView == null)
				localView = ((LayoutInflater) Tab_SubTabNotifications.this
						.getSystemService("layout_inflater")).inflate(
								2130903049, null);
			NotificationData localNotificationData = this.items[paramInt];
			TextView localTextView;
			if (localNotificationData != null) {
				localTextView = (TextView) localView.findViewById(2131296278);
				if ((paramInt != 0)
						&& (localNotificationData.Timestamp.getDate() == this.items[(paramInt - 1)].Timestamp
						.getDate()))
					break label151;
				localTextView.setText(this.dateFormatter
						.format(localNotificationData.Timestamp));
				localTextView.setVisibility(0);
			}
			while (true) {
				((TextView) localView.findViewById(2131296280))
				.setText(localNotificationData.Message);
				((TextView) localView.findViewById(2131296279))
				.setText(this.timeFormatter
						.format(localNotificationData.Timestamp));
				return localView;
				label151: localTextView.setVisibility(4);
			}
		}
	}
}