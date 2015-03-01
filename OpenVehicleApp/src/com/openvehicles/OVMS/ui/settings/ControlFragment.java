package com.openvehicles.OVMS.ui.settings;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.OnResultCommandListenner;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.BaseFragmentActivity;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.ConnectionList;
import com.openvehicles.OVMS.utils.ConnectionList.Con;

public class ControlFragment extends BaseFragment implements OnClickListener,
		OnResultCommandListenner, Con {
	ConnectionList connectionList;
	private int mEditPosition;
	private CarData mCarData;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SherlockFragmentActivity activity = getSherlockActivity();

		// get data of car to edit:
		mEditPosition = getArguments().getInt("position", -1);
		if (mEditPosition >= 0) {
			mCarData = CarsStorage.get().getStoredCars().get(mEditPosition);
		}

		String url = "http://api.openchargemap.io/v2/referencedata/";
		connectionList = new ConnectionList(getActivity(), this, url,false);

		activity.getSupportActionBar().setIcon(R.drawable.ic_action_control);
		activity.setTitle(R.string.Control);

		View pRootView = getView();
		Ui.setOnClick(pRootView, R.id.btn_features, this);
		Ui.setOnClick(pRootView, R.id.btn_parameters, this);
		Ui.setOnClick(pRootView, R.id.btn_mmi_ussd_code, this);
		Ui.setOnClick(pRootView, R.id.btn_connections, this);
		Ui.setOnClick(pRootView, R.id.btn_cellular_usage, this);
		Ui.setOnClick(pRootView, R.id.btn_reset_ovms_module, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_control, null);
	}

	@Override
	public void onClick(View v) {
		BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
		Bundle args;

		switch (v.getId()) {
			case R.id.btn_mmi_ussd_code:
				Ui.showEditDialog(v.getContext(), getString(R.string.msg_mmi_ssd_code),
						"*100#", R.string.Send, false, new Ui.OnChangeListener<String>() {
							@Override
							public void onAction(String pData) {
								if (TextUtils.isEmpty(pData))
									return;
								sendCommand(R.string.lb_mmi_ussd_code, "41,"
										+ pData, ControlFragment.this);
							}
						});
				break;
			case R.id.btn_features:
				args = new Bundle();
				args.putInt("position", mEditPosition);
				activity.setNextFragment(FeaturesFragment.class, args);
				break;
			case R.id.btn_parameters:
				args = new Bundle();
				args.putInt("position", mEditPosition);
				activity.setNextFragment(ControlParametersFragment.class, args);
				break;
			case R.id.btn_reset_ovms_module:
				sendCommand(R.string.msg_rebooting_car_module, "5", this);
				break;
			case R.id.btn_connections:
				connectionList.sublist();
				break;
			case R.id.btn_cellular_usage:
				activity.setNextFragment(CellularStatsFragment.class);
				break;
		}
	}

	@Override
	public void onResultCommand(String[] result) {
		if (result.length >= 3) {
			Toast.makeText(getActivity(), result[2], Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void connections(String al, String name) {
		// TODO Auto-generated method stub
		Log.d("ControlFragment", "connections: al=" + al);
	}
}
