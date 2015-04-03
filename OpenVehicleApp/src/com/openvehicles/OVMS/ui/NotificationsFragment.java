package com.openvehicles.OVMS.ui;

import java.text.SimpleDateFormat;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.OnResultCommandListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.NotificationData;
import com.openvehicles.OVMS.utils.OVMSNotifications;


public class NotificationsFragment extends BaseFragment
		implements OnItemClickListener, TextView.OnEditorActionListener, OnResultCommandListener {
	private static final String TAG = "NotificationsFragment";

	private ListView mListView;
	private OVMSNotifications mNotifications;

	private EditText mCmdInput;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_notifications, null);

		mListView = (ListView) layout.findViewById(R.id.listView);
		mListView.setOnItemClickListener(this);

		mCmdInput = (EditText) layout.findViewById(R.id.cmdInput);
		mCmdInput.setOnEditorActionListener(this);

		return layout;
	}
	
	@Override
	public void onResume() {
		super.onResume();

		// cancel Android system notification:
		NotificationManager mNotificationManager = (NotificationManager) getActivity()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancelAll();

		// update list:
		update();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		NotificationData data = (NotificationData) parent.getAdapter().getItem(position);

		if (data.Type == NotificationData.TYPE_COMMAND) {
			// use as history:
			mCmdInput.setText(data.Message);
			mCmdInput.requestFocus();
			mCmdInput.postDelayed(new Runnable() {
				@Override
				public void run() {
					InputMethodManager keyboard = (InputMethodManager)
							getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					keyboard.showSoftInput(mCmdInput, 0);
				}
			},200);

		} else {
			// display:
			Log.d(TAG, "Displaying notification: #" + position);
			new AlertDialog.Builder(parent.getContext())
					.setIcon(data.getIcon())
					.setTitle(data.Title)
					.setMessage(data.getMessageFormatted())
					.setCancelable(false)
					.setPositiveButton(R.string.Close, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					})
					.show();
		}
	}
	
	@Override
	public void update(CarData pCarData) {
		/* why should we reload notifications on every cardata update?
			doesn't seem to be necessary
		Context context = getActivity();
		if (context != null)
			initUi(context);
		*/
	}

	public void update() {
		Context context = getActivity();
		if (context != null)
			initUi(context);
	}

	private void initUi(Context pContext) {

		// (re-)load notifications:
		mNotifications = new OVMSNotifications(pContext);
		NotificationData[] data = new NotificationData[mNotifications.notifications.size()];
		mNotifications.notifications.toArray(data);
		
		// attach array to ListView:
		mListView.setAdapter(new ItemsAdapter(pContext, data));

	}


	@Override
	public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
		boolean handled = false;
		if (actionId == EditorInfo.IME_ACTION_SEND) {

			String cmd = textView.getText().toString();
			if (cmd.length() > 0) {

				// add cmd to notifications:
				String vehicle_id = CarsStorage.get().getLastSelectedCarId();
				mNotifications.addNotification(
						NotificationData.TYPE_COMMAND, vehicle_id + ": " + cmd, cmd);
				initUi(getActivity());

				// send command:
				if (cmd.matches("[0-9*#]+"))
					sendCommand(cmd, "41," + cmd, this); // MMI/USSD command
				else
					sendCommand(cmd, "7," + cmd, this); // SMS command

				handled = true;
			}
		}
		return handled;
	}


	@Override
	public void onResultCommand(String[] result) {
		if (result.length <= 1)
			return;

		int command = Integer.parseInt(result[0]);
		String cmdMessage = getSentCommandMessage(result[0]);
		int resCode = Integer.parseInt(result[1]);

		if (command != 7 && command != 41)
			return; // not for us

		String cmdOutput = null;
		if (result.length >= 3 && result[2] != null)
			cmdOutput = result[2];

		String vehicle_id = CarsStorage.get().getLastSelectedCarId();

		switch (resCode) {
			case 0: // ok: result[2] = command output
				int type = (command == 41) ? NotificationData.TYPE_USSD
						: NotificationData.TYPE_RESULT_SUCCESS;
				// suppress first (empty) OK result for cmd 41:
				if (command == 7 || cmdOutput != null) {
					mNotifications.addNotification(
							type, vehicle_id + ": " + cmdMessage,
							(cmdOutput != null) ? cmdOutput : getString(R.string.msg_ok));
					initUi(getActivity());
				}
				break;
			case 1: // failed: result[2] = command output
				mNotifications.addNotification(
						NotificationData.TYPE_RESULT_ERROR, vehicle_id + ": " + cmdMessage,
						getString(R.string.err_failed_smscmd));
				initUi(getActivity());
				break;
			case 2: // unsupported
				Toast.makeText(getActivity(), cmdMessage + " => " + getString(R.string.err_unsupported_operation),
						Toast.LENGTH_SHORT).show();
				break;
			case 3: // unimplemented
				Toast.makeText(getActivity(), cmdMessage + " => " + getString(R.string.err_unimplemented_operation),
						Toast.LENGTH_SHORT).show();
				break;
		}
	}


	private static class ItemsAdapter extends ArrayAdapter<NotificationData> {
		private final LayoutInflater mInflater;
		private final SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM d, HH:mm");

		public ItemsAdapter(Context context, NotificationData[] items) {
			super(context, R.layout.item_notifications, items);
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				v = mInflater.inflate(R.layout.item_notifications, null);
			}

			NotificationData it = getItem(position);
			if (it != null) {

				// set icon according to notification type:
				ImageView iv = (ImageView) v.findViewById(R.id.textNotificationsIcon);
				iv.setImageResource(it.getIcon());

				// set title, message & timestamp:
				TextView tv = (TextView) v.findViewById(R.id.textNotificationsListTitle);
				tv.setText(it.Title);

				tv = (TextView) v.findViewById(R.id.textNotificationsListMessage);
				if (it.Type == NotificationData.TYPE_COMMAND) {
					tv.setVisibility(View.GONE); // cmd shown in title
				} else {
					tv.setVisibility(View.VISIBLE);
					tv.setText(it.getMessageFormatted());
				}

				tv = (TextView) v.findViewById(R.id.textNotificationsListTimestamp);
				tv.setText(mDateFormat.format(it.Timestamp));

			}
			return v;
		}
	}
}
