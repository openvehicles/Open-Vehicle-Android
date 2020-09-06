package com.openvehicles.OVMS.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.ConnectionList;

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
	private String broadcastCodes;
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
		switch (v.getId()) {
			case R.id.cb_options_service:
				serviceEnabled = ((CheckBox) v).isChecked();
				appPrefes.SaveData("option_service_enabled", serviceEnabled ? "1" : "0");
				break;
			case R.id.cb_options_broadcast:
				broadcastEnabled = ((CheckBox) v).isChecked();
				txtCodes.setEnabled(broadcastEnabled);
				btnRevert.setEnabled(broadcastEnabled);
				appPrefes.SaveData("option_broadcast_enabled", broadcastEnabled ? "1" : "0");
				break;
			case R.id.btn_options_broadcast_codes_revert:
				broadcastCodes = defaultBroadcastCodes;
				txtCodes.setText(broadcastCodes);
				appPrefes.SaveData("option_broadcast_codes", broadcastCodes);
				break;
			case R.id.cb_options_commands:
				commandsEnabled = ((CheckBox) v).isChecked();
				appPrefes.SaveData("option_commands_enabled", commandsEnabled ? "1" : "0");
				break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
			case R.id.txt_options_broadcast_codes:
				if (!hasFocus) {
					broadcastCodes = ((EditText) v).getText().toString();
					appPrefes.SaveData("option_broadcast_codes", broadcastCodes);
				}
				break;
		}
	}

}
