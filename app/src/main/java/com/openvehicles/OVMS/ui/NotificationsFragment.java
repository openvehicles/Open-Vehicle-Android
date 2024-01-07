package com.openvehicles.OVMS.ui;

import android.app.NotificationManager;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.openvehicles.OVMS.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.OnResultCommandListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.settings.StoredCommandFragment;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.NotificationData;
import com.openvehicles.OVMS.utils.OVMSNotifications;

import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;


public class NotificationsFragment extends BaseFragment
		implements OnItemClickListener, AdapterView.OnItemLongClickListener,
		TextView.OnEditorActionListener, OnResultCommandListener {
	private static final String TAG = "NotificationsFragment";

	private ListView mListView;
	private ItemsAdapter mItemsAdapter;
	private OVMSNotifications mNotifications;

	private EditText mCmdInput;

	private AppPrefes appPrefes;
	public boolean mFontMonospace = false;
	public boolean mFilterList = false, mFilterInfo = false, mFilterAlert = false;
	public float mFontSize = 10;

	public String mVehicleId;

	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// Load prefs:
		appPrefes = new AppPrefes(requireActivity(), "ovms");

		mFontMonospace = appPrefes.getData("notifications_font_monospace").equals("on");
		try {
			mFontSize = Float.parseFloat(appPrefes.getData("notifications_font_size"));
		} catch(Exception e) {
			mFontSize = 10;
		}

		mFilterList = appPrefes.getData("notifications_filter_list").equals("on");
		mFilterInfo = appPrefes.getData("notifications_filter_info").equals("on");
		mFilterAlert = appPrefes.getData("notifications_filter_alert").equals("on");

		// Create UI:

		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_notifications, null);

		mListView = (ListView) layout.findViewById(R.id.listView);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);

		mCmdInput = (EditText) layout.findViewById(R.id.cmdInput);
		mCmdInput.setOnEditorActionListener(this);
		mCmdInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, mFontSize*1.2f);

		setHasOptionsMenu(true);

		return layout;
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.notifications_options, menu);
		menu.findItem(R.id.mi_chk_monospace).setChecked(mFontMonospace);
		menu.findItem(R.id.mi_chk_filter_list).setChecked(mFilterList);
		menu.findItem(R.id.mi_chk_filter_info).setChecked(mFilterInfo);
		menu.findItem(R.id.mi_chk_filter_alert).setChecked(mFilterAlert);
	}

	@Override
	public void onResume() {
		super.onResume();

		// cancel Android system notification:
		NotificationManager mNotificationManager = (NotificationManager)
				requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancelAll();

		// update list:
		mVehicleId = CarsStorage.get().getLastSelectedCarId();
		update();
	}

	@Override
	public void onItemClick(@NonNull AdapterView<?> parent, View view, int position, long id) {

		NotificationData data = (NotificationData) parent.getAdapter().getItem(position);

		if (data.Type == NotificationData.TYPE_COMMAND) {
			// use as history:
			mCmdInput.setText(data.Message);
			mCmdInput.requestFocus();
			mCmdInput.postDelayed(new Runnable() {
				@Override
				public void run() {
					InputMethodManager keyboard = (InputMethodManager)
							requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					keyboard.showSoftInput(mCmdInput, 0);
				}
			},200);

		} else {
			// display:
			Log.d(TAG, "Displaying notification: #" + position);
			AlertDialog dialog = new AlertDialog.Builder(parent.getContext())
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

			TextView textView = (TextView) dialog.findViewById(android.R.id.message);
			if (textView != null) {
				if (mFontMonospace) {
					textView.setTypeface(Typeface.MONOSPACE);
				}
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mFontSize * 1.2f);
				Linkify.addLinks(textView, Linkify.WEB_URLS);
			}

		}
	}


	@Override
	public boolean onItemLongClick(@NonNull AdapterView<?> parent, View view, int position, long id) {
		Log.d(TAG, "Long click on notification: #" + position);

		// copy message text to clipboard:

		NotificationData data = (NotificationData) parent.getAdapter().getItem(position);
		String message = data.getMessageFormatted();

		android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
				requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText("label", message);
		clipboard.setPrimaryClip(clip);

		Toast.makeText(getContext(),
				R.string.notifications_toast_copied, Toast.LENGTH_SHORT).show();

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {

		int menuId = item.getItemId();
		boolean newState = !item.isChecked();

		if (menuId == R.id.mi_help) {
			new AlertDialog.Builder(getContext())
					.setTitle(R.string.notifications_btn_help)
					.setMessage(Html.fromHtml(getString(R.string.notifications_help)))
					.setPositiveButton(android.R.string.ok, null)
					.show();
			return true;
		} else if (menuId == R.id.mi_chk_filter_list) {
			mFilterList = newState;
			appPrefes.SaveData("notifications_filter_list", newState ? "on" : "off");
			item.setChecked(newState);
			initList();
			return true;
		} else if (menuId == R.id.mi_chk_filter_info) {
			mFilterInfo = newState;
			appPrefes.SaveData("notifications_filter_info", newState ? "on" : "off");
			item.setChecked(newState);
			return true;
		} else if (menuId == R.id.mi_chk_filter_alert) {
			mFilterAlert = newState;
			appPrefes.SaveData("notifications_filter_alert", newState ? "on" : "off");
			item.setChecked(newState);
			return true;
		} else if (menuId == R.id.mi_chk_monospace) {
			mFontMonospace = newState;
			appPrefes.SaveData("notifications_font_monospace", newState ? "on" : "off");
			item.setChecked(newState);
			mItemsAdapter.notifyDataSetChanged();
			return true;
		} else if (menuId == R.id.mi_set_fontsize) {
			Ui.showPinDialog(getActivity(),
					getString(R.string.notifications_set_fontsize),
					Float.toString(mFontSize), R.string.Set, false,
					new Ui.OnChangeListener<String>() {
						@Override
						public void onAction(String pData) {
							float val;
							try {
								val = Float.parseFloat(pData);
							} catch (Exception e) {
								val = 10;
							}
							mFontSize = val;
							appPrefes.SaveData("notifications_font_size",
									Float.toString(mFontSize));
							mItemsAdapter.notifyDataSetChanged();
							mCmdInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, mFontSize * 1.2f);
						}
					});
			return true;
		} else if (menuId == R.id.mi_stored_commands) {
			BaseFragmentActivity.showForResult(this, StoredCommandFragment.class,
					StoredCommandFragment.REQUEST_SELECT_EXECUTE, null,
					Configuration.ORIENTATION_UNDEFINED);
			return false;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		String command = null;
		if (data != null) {
			command = data.getStringExtra("command");
		}
		Log.d(TAG, "onActivityResult: reqCode=" + requestCode + ", resCode=" + resultCode + ", command: " + command);
		sendUserCommand(command);
	}

	public void update() {
		initList();
	}

	@Override
	public void update(CarData pCarData) {
		super.update(pCarData);

		// check if the car filter needs to be reapplied:
		if (mFilterList && !pCarData.sel_vehicleid.equals(mVehicleId)) {
			String vehicleId = CarsStorage.get().getLastSelectedCarId();
			if (vehicleId != null && !vehicleId.equals(mVehicleId)) {
				Log.d(TAG, "update: vehicle changed to '" + vehicleId + "' => filter reload");
				mVehicleId = vehicleId;
				initList();
			}
		}
	}

	private void initList() {
		Context context = getActivity();
		if (context == null) return;

		Log.d(TAG, "initUi: (re-)loading notifications, filter=" + mFilterInfo + ", vehicle=" + mVehicleId);

		// (re-)load notifications:
		// TODO: this scheme of recreating the OVMSNotifications object on every change is a PITA,
		//       it needs to be replaced by a singleton or service
		mNotifications = new OVMSNotifications(context);
		NotificationData[] data = mNotifications.getArray(mFilterList ? mVehicleId : "");

		// attach array to ListView:
		mItemsAdapter = new ItemsAdapter(context, this, data);
		mListView.setAdapter(mItemsAdapter);
	}

	// ATTENTION: use this only to display local updates to mNotifications!
	//            (will not show changes from other OVMSNotifications instances)
	private void updateList() {
		Context context = getActivity();
		if (context == null) return;

		if (mNotifications == null || mItemsAdapter == null) {
			initList();
		} else {
			NotificationData[] data = mNotifications.getArray(mFilterList ? mVehicleId : "");
			mItemsAdapter = new ItemsAdapter(context, this, data);
			mListView.setAdapter(mItemsAdapter);
		}
	}

	private int lastCommandSent = 0;

	@Override
	public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
		boolean handled = false;
		if (actionId == EditorInfo.IME_ACTION_SEND) {
			String userCmd = textView.getText().toString();
			handled = sendUserCommand(userCmd);
		}
		return handled;
	}

	public boolean sendUserCommand(String userCmd) {
		if (userCmd == null || userCmd.isEmpty())
			return false;

		if (mNotifications == null)
			initList();

		// Add command to history:
		String vehicle_id = CarsStorage.get().getLastSelectedCarId();
		mNotifications.addNotification(
				NotificationData.TYPE_COMMAND, vehicle_id + ": " + userCmd, userCmd);
		updateList();

		// Send command:
		String mpCmd = ApiService.makeMsgCommand(userCmd);
		String[] cp = mpCmd.split(",");
		try {
			lastCommandSent = Integer.parseInt(cp[0]);
			sendCommand(userCmd, mpCmd, this);
			return true;
		} catch (Exception e) {
			Toast.makeText(getActivity(), getString(R.string.err_unimplemented_operation),
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	@Override
	public void onResultCommand(@NonNull String[] result) {
		if (result.length <= 1)
			return;

		if (mNotifications == null)
			initList();

		int command = Integer.parseInt(result[0]);
		String cmdMessage = getSentCommandMessage(result[0]);
		int resCode = Integer.parseInt(result[1]);

		if (command != 7 && command != 41 && command != 49 && command != lastCommandSent)
			return; // not for us

		String cmdOutput = null;
		if (result.length >= 3 && result[2] != null) {
			cmdOutput = result[2];
			for (int i = 3; i < result.length; i++)
				cmdOutput += "," + result[i];
		}

		String vehicle_id = CarsStorage.get().getLastSelectedCarId();

		switch (resCode) {
			case 0: // ok: result[2] = command output
				int type = (command == 41) ? NotificationData.TYPE_USSD
						: NotificationData.TYPE_RESULT_SUCCESS;
				// suppress first (empty) OK result for cmd 41:
				if (command == 7 || cmdOutput != null) {
					cancelCommand();
					mNotifications.addNotification(
							type, vehicle_id + ": " + cmdMessage,
							(cmdOutput != null) ? cmdOutput : getString(R.string.msg_ok));
					updateList();
				}
				break;
			case 1: // failed: result[2] = command output
				cancelCommand();
				mNotifications.addNotification(
						NotificationData.TYPE_RESULT_ERROR, vehicle_id + ": " + cmdMessage,
						getString(R.string.err_failed_smscmd));
				updateList();
				break;
			case 2: // unsupported
				cancelCommand();
				if (getActivity() != null) {
					Toast.makeText(getActivity(), cmdMessage + " => " + getString(R.string.err_unsupported_operation),
							Toast.LENGTH_SHORT).show();
				}
				break;
			case 3: // unimplemented
				cancelCommand();
				if (getActivity() != null) {
					Toast.makeText(getActivity(), cmdMessage + " => " + getString(R.string.err_unimplemented_operation),
							Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}


	private static class ItemsAdapter extends ArrayAdapter<NotificationData> {
		private final LayoutInflater mInflater;
		private final SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM d, HH:mm");
		private final NotificationsFragment mFragment;

		public ItemsAdapter(Context context, NotificationsFragment fragment, NotificationData[] items) {
			super(context, R.layout.item_notifications, items);
			mInflater = LayoutInflater.from(context);
			mFragment = fragment;
		}

		@NonNull
		@Override
		public View getView(int position, View convertView, @NonNull ViewGroup parent) {
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

				if (mFragment.mFontMonospace) {
					tv.setTypeface(Typeface.MONOSPACE);
				} else {
					tv.setTypeface(Typeface.DEFAULT);
				}
				tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mFragment.mFontSize);

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
