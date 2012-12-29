package com.openvehicles.OVMS.ui;

import java.text.SimpleDateFormat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.utils.NotificationData;
import com.openvehicles.OVMS.utils.OVMSNotifications;


public class NotificationsFragment extends BaseFragment implements OnItemClickListener {
	private ListView mListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mListView = new ListView(container.getContext());
		mListView.setOnItemClickListener(this);
		return mListView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initUi(getActivity());
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.d("OVMS", "Displaying notification: #" + position);
		
		NotificationData data = (NotificationData) parent.getAdapter().getItem(position);
		new AlertDialog.Builder(parent.getContext())
			.setTitle(data.Title)
			.setMessage(data.Message)
			.setCancelable(false)
			.setPositiveButton(R.string.Close, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).show();
	}
	
	@Override
	public void update(CarData pCarData) {
		Context context = getActivity();
		if (context == null) initUi(context);
	}
	
	private void initUi(Context pContext) {
		OVMSNotifications notifications = new OVMSNotifications(pContext);
		NotificationData[] data = new NotificationData[notifications.notifications.size()];
		notifications.notifications.toArray(data);
		
		// reverse the array so that the latest notification is displayed on top
		NotificationData[] cachedData = new NotificationData[data.length];
		for (int i = 0; i < cachedData.length; i++) {
			cachedData[i] = data[data.length - 1 - i];
		}
		mListView.setAdapter(new ItemsAdapter(pContext, R.layout.tabnotifications_listitem, cachedData));
	}
	
	
	private static class ItemsAdapter extends ArrayAdapter<NotificationData> {
		private final LayoutInflater mInflater;
		private final SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM d, k:mm");

		public ItemsAdapter(Context context, int textViewResourceId, NotificationData[] items) {
			super(context, textViewResourceId, items);
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				v = mInflater.inflate(R.layout.tabnotifications_listitem, null);
			}

			NotificationData it = getItem(position);
			if (it != null) {
				TextView tv = (TextView) v.findViewById(R.id.textNotificationsListTitle);
				tv.setText(it.Title);
				tv = (TextView) v.findViewById(R.id.textNotificationsListMessage);
				tv.setText(it.Message);

				tv = (TextView) v.findViewById(R.id.textNotificationsListTimestamp);
				tv.setText(mDateFormat.format(it.Timestamp));

			}
			return v;
		}
	}
}
