package com.openvehicles.OVMS.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.openvehicles.OVMS.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.Sys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by balzer on 03.12.16.
 */

public class GlobalOptionsFragment extends BaseFragment
		implements View.OnClickListener, View.OnFocusChangeListener {
	private static final String TAG = "GlobalOptionsFragment";

	public final static String defaultBroadcastCodes = "DFLPSTVWZ";

	private AppPrefes appPrefes;

	private EditText txtCodes;
	private ImageButton btnRevert;

	private boolean serviceEnabled;
	private boolean broadcastEnabled;
	private String broadcastCodes;			// Currently unused, may be reused if single messages shall be sent
	private boolean commandsEnabled;


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		AppCompatActivity activity = getCompatActivity();
		activity.getSupportActionBar().setIcon(R.drawable.ic_action_settings);
		activity.setTitle(R.string.Options);

		appPrefes = new AppPrefes(activity, "ovms");
		serviceEnabled = appPrefes.getData("option_service_enabled", "0").equals("1");
		broadcastEnabled = appPrefes.getData("option_broadcast_enabled", "0").equals("1");
		broadcastCodes = appPrefes.getData("option_broadcast_codes", defaultBroadcastCodes);
		commandsEnabled = appPrefes.getData("option_commands_enabled", "0").equals("1");

		CheckBox checkBox;

		checkBox = (CheckBox) findViewById(R.id.cb_options_service);
		checkBox.setChecked(serviceEnabled);
		checkBox.setOnClickListener(this);

		checkBox = (CheckBox) findViewById(R.id.cb_options_broadcast);
		checkBox.setChecked(broadcastEnabled);
		checkBox.setOnClickListener(this);

		txtCodes = (EditText) findViewById(R.id.txt_options_broadcast_codes);
		txtCodes.setText(broadcastCodes);
		txtCodes.setOnFocusChangeListener(this);

		btnRevert = (ImageButton) findViewById(R.id.btn_options_broadcast_codes_revert);
		btnRevert.setOnClickListener(this);

		txtCodes.setEnabled(broadcastEnabled);
		btnRevert.setEnabled(broadcastEnabled);

		checkBox = (CheckBox) findViewById(R.id.cb_options_commands);
		checkBox.setChecked(commandsEnabled);
		checkBox.setOnClickListener(this);

		Ui.setOnClick(getView(), R.id.cb_options_apikey_renew, this);
		Ui.setValue(getView(), R.id.tv_options_apikey, appPrefes.getData("APIKey"));

		TextView info = (TextView) findViewById(R.id.txt_options_broadcast_info);
		info.setText(Html.fromHtml(getString(R.string.lb_options_broadcast_info)));
		info.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_globaloptions, null);
	}

	@Override
	public void onClick(View v) {
		Context context = getContext();
		if (context == null) return;
		int id = v.getId();
		if (id == R.id.cb_options_service) {
			serviceEnabled = ((CheckBox) v).isChecked();
			appPrefes.SaveData("option_service_enabled", serviceEnabled ? "1" : "0");
			Intent intent = new Intent(serviceEnabled ? ApiService.ACTION_ENABLE : ApiService.ACTION_DISABLE);
			context.sendBroadcast(intent);
		} else if (id == R.id.cb_options_broadcast) {
			broadcastEnabled = ((CheckBox) v).isChecked();
			txtCodes.setEnabled(broadcastEnabled);
			btnRevert.setEnabled(broadcastEnabled);
			appPrefes.SaveData("option_broadcast_enabled", broadcastEnabled ? "1" : "0");
		} else if (id == R.id.btn_options_broadcast_codes_revert) {
			broadcastCodes = defaultBroadcastCodes;
			txtCodes.setText(broadcastCodes);
			appPrefes.SaveData("option_broadcast_codes", broadcastCodes);
		} else if (id == R.id.cb_options_commands) {
			commandsEnabled = ((CheckBox) v).isChecked();
			appPrefes.SaveData("option_commands_enabled", commandsEnabled ? "1" : "0");
		} else if (id == R.id.cb_options_apikey_renew) {
			new AlertDialog.Builder(context)
					.setMessage(R.string.lb_options_apikey_renew_confirm)
					.setNegativeButton(R.string.Cancel, null)
					.setPositiveButton(R.string.Yes, (dialog1, which) -> {
						String apiKey = Sys.getRandomString(25);
						appPrefes.SaveData("APIKey", apiKey);
						Log.d(TAG, "onClick: generated new APIKey: " + apiKey);
						Ui.setValue(getView(), R.id.tv_options_apikey, apiKey);
					})
					.create().show();
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (v.getId() == R.id.txt_options_broadcast_codes) {
			if (!hasFocus) {
				broadcastCodes = ((EditText) v).getText().toString();
				appPrefes.SaveData("option_broadcast_codes", broadcastCodes);
			}
		}
	}

}
