package com.openvehicles.OVMS.ui.settings;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.CarsStorage;

public class CarInfoFragment extends BaseFragment {
	private CarData mCarData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_car_info, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SherlockFragmentActivity activity = getSherlockActivity(); 
		
		activity.getSupportActionBar().setIcon(R.drawable.ic_action_about);
		activity.setTitle(R.string.lb_vehicle_info);
		
		int editPosition = getArguments().getInt("position", -1);
		if (editPosition >= 0) {
			mCarData = CarsStorage.get().getStoredCars().get(editPosition);
			
		}
		
		if (mCarData != null) approveCarData();
	}
	
	private void approveCarData() {
		View rootView = getView();
		Context context = rootView.getContext();
		
		Ui.setValue(rootView, R.id.txt_vehicle_id, mCarData.sel_vehicleid);
		Ui.setValue(rootView, R.id.txt_win, mCarData.car_vin);
		Ui.setValue(rootView, R.id.txt_type, mCarData.car_type);
		Ui.setValue(rootView, R.id.txt_server, mCarData.server_firmware);
		Ui.setValue(rootView, R.id.txt_car, mCarData.car_firmware);
		Ui.setValue(rootView, R.id.txt_gsm, mCarData.car_gsm_signal);
		Ui.setValue(rootView, R.id.txt_cac, String.format("%.2f",mCarData.car_CAC));

		Ui.setValue(rootView, R.id.txt_12v_info, String.format("%.1fV (%s)",
			mCarData.car_12vline_voltage,
			mCarData.car_charging_12v
				? "charging"
				: (mCarData.car_12vline_ref <= 1.5)
					? String.format("calmdown, %d min left", 15 - (int)(mCarData.car_12vline_ref*10))
					: String.format("ref=%.1fV", mCarData.car_12vline_ref)));

		ImageView iv = (ImageView)rootView.findViewById(R.id.img_signal_rssi);
		iv.setImageResource(Ui.getDrawableIdentifier(context, "signal_strength_" + mCarData.car_gsm_bars));
		
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			Ui.setValue(rootView, R.id.txt_app, String.format("%s (%s)", pi.versionName, pi.versionCode));
		} catch (NameNotFoundException e) { }
		
	}
	
	@Override
	public void update(CarData pCarData) {
		mCarData = pCarData;
		approveCarData();
	}
}
