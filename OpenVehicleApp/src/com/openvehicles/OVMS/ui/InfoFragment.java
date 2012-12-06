package com.openvehicles.OVMS.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.ui.witdet.ReversedSeekBar;

public class InfoFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_info, null);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		registerForUpdate();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		unregisterForUpdate();
	}
	
	@Override
	public void update(CarData pCarData) {
		updateLastUpdatedView(pCarData);
		updateCarInfoView(pCarData);
	}
	
	// This updates the part of the view with times shown.
	// It is called by a periodic timer so it gets updated every few seconds.
	public void updateLastUpdatedView(CarData pCarData) {
		// Quick exit if the car data is not there yet...
		if ((pCarData == null) || (pCarData.car_lastupdated == null)) return;

		// Let's update the Info tab view...

		// First the last updated section...
		TextView tv = (TextView)findViewById(R.id.txt_last_updated);
		long now = System.currentTimeMillis();
		long seconds = (now - pCarData.car_lastupdated.getTime()) / 1000;
		long minutes = (seconds)/60;
		long hours = minutes/60;
		long days = minutes/(60*24);
		Log.d("OVMS", "Last updated: " + seconds + " secs ago");

		if (pCarData.car_lastupdated == null) {
			tv.setText("");
			tv.setTextColor(0xFFFFFFFF);
		}
		else if (minutes == 0) {
			tv.setText("live");
			tv.setTextColor(0xFFFFFFFF);
		}
		else if (minutes == 1) {
			tv.setText("1 min");
			tv.setTextColor(0xFFFFFFFF);
		}
		else if (days > 1) {
			tv.setText(String.format("%d days",days));
			tv.setTextColor(0xFFFF0000);
		}
		else if (hours > 1) {
			tv.setText(String.format("%d hours",hours));
			tv.setTextColor(0xFFFF0000);
		}
		else if (minutes > 60) {
			tv.setText(String.format("%d mins",minutes));
			tv.setTextColor(0xFFFF0000);			
		}
		else {
			tv.setText(String.format("%d mins",minutes));
			tv.setTextColor(0xFFFFFFFF);
		}

		// Then the parking timer...
		tv = (TextView) findViewById(R.id.txt_parked_time);
		if ((!pCarData.car_started) && (pCarData.car_parked_time != null)) {
			// Car is parked
			tv.setVisibility(View.VISIBLE);
			seconds = (now - pCarData.car_parked_time.getTime()) / 1000;
			minutes = (seconds)/60;
			hours = minutes/60;
			days = minutes/(60*24);

			if (minutes == 0)
				tv.setText("just now");
			else if (minutes == 1)
				tv.setText("1 min");
			else if (days > 1)
				tv.setText(String.format("%d days",days));
			else if (hours > 1)
				tv.setText(String.format("%d hours",hours));
			else if (minutes > 60)
				tv.setText(String.format("%d mins",minutes));
			else
				tv.setText(String.format("%d mins",minutes));
		} else {
			tv.setVisibility(View.INVISIBLE);
		}
	}

	// This updates the main informational part of the view.
	// It is called when the server gets new data.
	public void updateCarInfoView(CarData pCarData) {
		TextView tv = (TextView)findViewById(R.id.textVehicleID);
		tv.setText(pCarData.sel_vehicleid);

		tv = (TextView)findViewById(R.id.tabInfoTextSOC);
		tv.setText(pCarData.car_soc);

		TextView cmtv = (TextView)findViewById(R.id.tabInfoTextChargeMode);
		ImageView coiv = (ImageView)findViewById(R.id.tabInfoImageBatteryChargingOverlay);
		ReversedSeekBar bar = (ReversedSeekBar)findViewById(R.id.tabInfoSliderChargerControl);
		TextView tvl = (TextView)findViewById(R.id.tabInfoTextChargeStatusLeft);
		TextView tvr = (TextView)findViewById(R.id.tabInfoTextChargeStatusRight);
		if ((!pCarData.car_chargeport_open)||(pCarData.car_charge_substate_i_raw==0x07)) {
			// Charge port is closed or car is not plugged in
			findViewById(R.id.tabInfoImageCharger).setVisibility(View.INVISIBLE);
			bar.setVisibility(View.INVISIBLE);
			cmtv.setVisibility(View.INVISIBLE);
			coiv.setVisibility(View.INVISIBLE);
			tvl.setVisibility(View.INVISIBLE);
			tvr.setVisibility(View.INVISIBLE);
		} else {
			// Car is plugged in
			findViewById(R.id.tabInfoImageCharger).setVisibility(View.VISIBLE);
			bar.setVisibility(View.VISIBLE);
			tvl.setVisibility(View.VISIBLE);
			tvr.setVisibility(View.VISIBLE);
			
			switch (pCarData.car_charge_state_i_raw) {
			case 0x04:    // Done
			case 0x115:   // Stopping
			case 0x15:    // Stopped
			case 0x16:    // Stopped
			case 0x17:    // Stopped
			case 0x18:    // Stopped
			case 0x19:    // Stopped
				// Slider on the left, message is "Slide to charge"
				bar.setProgress(100);
				tvl.setText(null);
				tvr.setText("SLIDE TO\nCHARGE");
				coiv.setVisibility(View.VISIBLE);
				cmtv.setVisibility(View.INVISIBLE);
				break;
			case 0x0e:    // Wait for schedule charge
				// Slider on the left, message is "Timed Charge"
				bar.setProgress(100);
				tvl.setText(null);
				tvr.setText("TIMED CHARGE");
				coiv.setVisibility(View.VISIBLE);
				cmtv.setVisibility(View.INVISIBLE);
				break;				
			case 0x01:    // Charging
			case 0x101:   // Starting
			case 0x02:    // Top-off
			case 0x0d:    // Preparing to charge
			case 0x0f:    // Heating
				// Slider on the right, message blank
				bar.setProgress(0);
				tvl.setText(String.format("CHARGING\n%s %s", pCarData.car_charge_linevoltage, pCarData.car_charge_current));
				tvr.setText("");
				cmtv.setText(String.format("%s %s", pCarData.car_charge_mode, pCarData.car_charge_currentlimit).toUpperCase());
				coiv.setVisibility(View.VISIBLE);
				cmtv.setVisibility(View.VISIBLE);
				break;
			default:
				// Slider on the right, message blank
				bar.setProgress(100);
				tvl.setText(null);
				tvr.setText(null);
				cmtv.setVisibility(View.INVISIBLE);
				coiv.setVisibility(View.INVISIBLE);
				break;				
			}
		}

		tv = (TextView)findViewById(R.id.tabInfoTextIdealRange);
		tv.setText(pCarData.car_range_ideal);
		tv = (TextView)findViewById(R.id.tabInfoTextEstimatedRange);
		tv.setText(pCarData.car_range_estimated);

		int maxWeight = findViewById(R.id.tabInfoTextSOC).getLayoutParams().width;
		int realWeight = Math.round((maxWeight * pCarData.car_soc_raw / 100) * 1.1f);
		View v = findViewById(R.id.tabInfoImageBatteryOverlay);
		
		v.getLayoutParams().width = Math.min(maxWeight, realWeight);
		v.requestLayout();
		

		ImageView iv = (ImageView) findViewById(R.id.tabInfoImageCar);
		iv.setImageResource(Ui.getDrawableIdentifier(getActivity(), "signal_strength_" + pCarData.car_gsm_bars));
		
		iv = (ImageView) findViewById(R.id.tabInfoImageCar);
		iv.setImageResource(Ui.getDrawableIdentifier(getActivity(), pCarData.sel_vehicle_image));
	}
}
