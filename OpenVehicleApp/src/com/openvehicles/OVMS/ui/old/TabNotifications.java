package com.openvehicles.OVMS.ui.old;

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

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.utils.NotificationData;
import com.openvehicles.OVMS.utils.OVMSNotifications;

public class TabNotifications extends ListActivity {
	private ItemsAdapter mAdapter;
	private NotificationData[] mCachedData;
	private OVMSNotifications mNotifications;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	private class ItemsAdapter extends ArrayAdapter<NotificationData> {

		private NotificationData[] items;

		public ItemsAdapter(Context context, int textViewResourceId,
				NotificationData[] items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.tabnotifications_listitem, null);
			}

			NotificationData it = items[position];
			if (it != null) {
				TextView tv = (TextView) v.findViewById(R.id.textNotificationsListTitle);
				tv.setText(it.Title);
				tv = (TextView) v.findViewById(R.id.textNotificationsListMessage);
				tv.setText(it.Message);

				SimpleDateFormat fmt = new SimpleDateFormat("MMM d, k:mm");
				tv = (TextView) v.findViewById(R.id.textNotificationsListTimestamp);
				tv.setText(fmt.format(it.Timestamp));

			}
			
			return v;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.d("OVMS", "Displaying notification: #" + position);
		
		new AlertDialog.Builder(TabNotifications.this)
			.setTitle(mCachedData[position].Title)
			.setMessage(mCachedData[position].Message)
			.setCancelable(false)
			.setPositiveButton(R.string.Close, null)
			.show();
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			NotificationData[] data = new NotificationData[mNotifications.notifications.size()];
			mNotifications.notifications.toArray(data);
			
			// reverse the array so that the latest notification is displayed on top
			mCachedData = new NotificationData[data.length];
			for (int i=0; i<mCachedData.length; i++) {
				mCachedData[i] = data[data.length - 1 - i];
			}
			
			mAdapter = new ItemsAdapter(TabNotifications.this, 
					R.layout.tabnotifications_listitem, mCachedData);
			setListAdapter(mAdapter);
		}
	};

	public void Refresh() {
		mNotifications = new OVMSNotifications(this);
		handler.sendEmptyMessage(0);

	}

}
