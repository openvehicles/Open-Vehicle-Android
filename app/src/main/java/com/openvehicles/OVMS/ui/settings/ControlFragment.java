package com.openvehicles.OVMS.ui.settings;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.OnResultCommandListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.BaseFragmentActivity;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.ConnectionList;
import com.openvehicles.OVMS.utils.ConnectionList.ConnectionsListener;
import com.openvehicles.OVMS.utils.NotificationData;
import com.openvehicles.OVMS.utils.OVMSNotifications;

public class ControlFragment extends BaseFragment implements OnClickListener,
		OnResultCommandListener, ConnectionsListener {
	ConnectionList connectionList;
	private int mEditPosition;
	private CarData mCarData;
	private String ussdCmd;
	private OVMSNotifications ovmsNotifications;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		AppCompatActivity activity = getCompatActivity();

		// get data of car to edit:
		mEditPosition = getArguments().getInt("position", -1);
		if (mEditPosition >= 0) {
			mCarData = CarsStorage.get().getStoredCars().get(mEditPosition);
		}

		connectionList = new ConnectionList(getActivity(), this, false);

		activity.getSupportActionBar().setIcon(R.drawable.ic_action_control);
		activity.setTitle(R.string.Control);

		View pRootView = getView();
		Ui.setOnClick(pRootView, R.id.btn_features, this);
		Ui.setOnClick(pRootView, R.id.btn_parameters, this);
		Ui.setOnClick(pRootView, R.id.btn_mmi_ussd_code, this);
		Ui.setOnClick(pRootView, R.id.btn_connections, this);
		Ui.setOnClick(pRootView, R.id.btn_cellular_usage, this);
		Ui.setOnClick(pRootView, R.id.btn_reset_ovms_module, this);

		// diag logs only available on Renault Twizy (up to now):
		if (mCarData.car_type.equals("RT")) {
			Ui.setOnClick(pRootView, R.id.btn_diag_logs, this);
		} else {
			pRootView.findViewById(R.id.btn_diag_logs).setVisibility(View.GONE);
		}

		ussdCmd = "";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_control, null);
	}

	@Override
	public void onDestroyView() {
		cancelCommand();
		super.onDestroyView();
	}

	@Override
	public void onClick(View v) {
		BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
		Bundle args;

		int id = v.getId();
		if (id == R.id.btn_mmi_ussd_code) {
			Ui.showEditDialog(v.getContext(), getString(R.string.msg_mmi_ssd_code),
					"*100#", R.string.Send, false, new Ui.OnChangeListener<String>() {
						@Override
						public void onAction(String pData) {

							if (TextUtils.isEmpty(pData))
								return;
							ussdCmd = pData;

							Context context = getActivity();

							if (ovmsNotifications == null)
								ovmsNotifications = new OVMSNotifications(context);

							boolean is_new = ovmsNotifications.addNotification(
									NotificationData.TYPE_COMMAND,
									mCarData.sel_vehicleid + ": " + ussdCmd,
									ussdCmd);
							if (is_new) {
								// signal App to reload notifications:
								Intent uiNotify = new Intent(context.getPackageName() + ".Notification");
								context.sendBroadcast(uiNotify);
							}

							sendCommand(R.string.lb_mmi_ussd_code, "41," + pData, ControlFragment.this);
						}
					});
		} else if (id == R.id.btn_features) {
			args = new Bundle();
			args.putInt("position", mEditPosition);
			activity.setNextFragment(FeaturesFragment.class, args);
		} else if (id == R.id.btn_parameters) {
			args = new Bundle();
			args.putInt("position", mEditPosition);
			activity.setNextFragment(ControlParametersFragment.class, args);
		} else if (id == R.id.btn_reset_ovms_module) {
			sendCommand(R.string.msg_rebooting_car_module, "5", this);
		} else if (id == R.id.btn_connections) {
			connectionList.sublist();
		} else if (id == R.id.btn_cellular_usage) {
			activity.setNextFragment(CellularStatsFragment.class);
		} else if (id == R.id.btn_diag_logs) {
			args = new Bundle();
			args.putInt("position", mEditPosition);
			activity.setNextFragment(LogsFragment.class, args);
		}
	}

	@Override
	public void onResultCommand(String[] result) {

		if (result.length < 2)
			return;
		if (getContext() == null)
			return;

		Context context = getActivity();
		int command = Integer.parseInt(result[0]);
		int resCode = Integer.parseInt(result[1]);
		String resText = (result.length > 2) ? result[2] : "";
		String cmdMessage = getSentCommandMessage(result[0]);

		switch (resCode) {
			case 0: // ok

				if (command == 41) {
					// only process second cmd result carrying data:
					if (result.length >= 3) {
						cancelCommand();

						// add MMI/USSD result to Notifications:
						if (ovmsNotifications == null)
							ovmsNotifications = new OVMSNotifications(context);

						boolean is_new = ovmsNotifications.addNotification(
								NotificationData.TYPE_USSD,
								mCarData.sel_vehicleid + ": " + ussdCmd,
								result[2]);
						if (is_new) {

							// signal App to reload notifications:
							Intent uiNotify = new Intent(context.getPackageName() + ".Notification");
							context.sendBroadcast(uiNotify);

							// user info dialog:
							new AlertDialog.Builder(context)
									.setTitle(cmdMessage)
									.setMessage(ussdCmd + " =>\n" + result[2])
									.setPositiveButton(android.R.string.ok, null)
									.show();
						}
					}
				} else {
					// default:
					cancelCommand();
					Toast.makeText(context, cmdMessage + " => " + getString(R.string.msg_ok),
							Toast.LENGTH_SHORT).show();
				}

				break;
			case 1: // failed
				cancelCommand();
				Toast.makeText(context, cmdMessage + " => " + getString(R.string.err_failed, resText),
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // unsupported
				cancelCommand();
				Toast.makeText(context, cmdMessage + " => " + getString(R.string.err_unsupported_operation),
						Toast.LENGTH_SHORT).show();
				break;
			case 3: // unimplemented
				cancelCommand();
				Toast.makeText(context, cmdMessage + " => " + getString(R.string.err_unimplemented_operation),
						Toast.LENGTH_SHORT).show();
				break;
		}
	}

	@Override
	public void onConnectionChanged(String conId, String conName) {
		Log.d("ControlFragment", "onConnectionChanged: conName=" + conName);
	}
}
