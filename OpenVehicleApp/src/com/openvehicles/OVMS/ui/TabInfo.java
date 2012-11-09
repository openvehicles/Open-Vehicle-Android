package com.openvehicles.OVMS.ui;

import java.lang.ref.WeakReference;
import java.util.Date;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.R.id;
import com.openvehicles.OVMS.R.layout;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.witdet.ReversedSeekBar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TabInfo extends Activity {

	// Private data
	private CarData data;
	private Handler handler = new TabInfoHandler(this);
	private Handler lastUpdateTimerHandler = new Handler();

	// onCreate: System event to tell us when the activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabinfo);
	}

	// onResume: System event to tell us the App has resumed
	@Override
	protected void onResume() {
		super.onResume();

		// set update timer
		lastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000);
	}

	// onPause: System event to tell us the app is about to pause
	@Override
	protected void onPause() {
		super.onPause();

		// remove update timer
		lastUpdateTimerHandler.removeCallbacks(lastUpdateTimer);
	}

	// This is a periodic timer to refresh the info screen
	private Runnable lastUpdateTimer = new Runnable() {
		public void run() {
			// update the last updated textview
			updateLastUpdatedView();

			// schedule next run
			lastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000);
		}
	};

	// New car data has been received from the server
	// We store our local copy, the refresh the display
	public void RefreshStatus(CarData carData) {
		data = carData;
		handler.sendEmptyMessage(0);
	}

	// This updates the part of the view with times shown.
	// It is called by a periodic timer so it gets updated every few seconds.
	public void updateLastUpdatedView() {

		// Quick exit if the car data is not there yet...
		if ((data == null) || (data.car_lastupdated == null))
			return;

		// Let's update the Info tab view...

		// First the last updated section...
		TextView tv = (TextView)findViewById(R.id.tabInfoTextLastUpdated);
		Date now = new Date();
		long seconds = (now.getTime() - data.car_lastupdated.getTime()) / 1000;
		long minutes = (seconds)/60;
		long hours = minutes/60;
		long days = minutes/(60*24);
		Log.d("OVMS", "Last updated: " + seconds + " secs ago");

		if (data.car_lastupdated == null) {
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
		LinearLayout parkinglayoutv = (LinearLayout)findViewById(R.id.tabInfoLayoutParking);
		if ((!this.data.car_started) && (this.data.car_parked_time != null))
		{
			// Car is parked
			parkinglayoutv.setVisibility(View.VISIBLE);
			tv = (TextView)findViewById(R.id.tabInfoTextParkedTime);
			seconds = (now.getTime() - data.car_parked_time.getTime()) / 1000;
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
		}
		else
		{
			parkinglayoutv.setVisibility(View.INVISIBLE);
		}
	}

	// This updates the main informational part of the view.
	// It is called when the server gets new data.
	public void updateCarInfoView() {

		TextView tv = (TextView)findViewById(R.id.textVehicleID);
		tv.setText(data.sel_vehicleid);

		tv = (TextView)findViewById(R.id.tabInfoTextSOC);
		tv.setText(data.car_soc);

		RelativeLayout pluglayoutv = (RelativeLayout)findViewById(R.id.tabInfoCharger);
		TextView cmtv = (TextView)findViewById(R.id.tabInfoTextChargeMode);
		ImageView coiv = (ImageView)findViewById(R.id.tabInfoImageBatteryChargingOverlay);
		if ((!data.car_chargeport_open)||(data.car_charge_substate_i_raw==0x07)) {
			// Charge port is closed or car is not plugged in
			pluglayoutv.setVisibility(View.INVISIBLE);
			cmtv.setVisibility(View.INVISIBLE);
			coiv.setVisibility(View.INVISIBLE);
		}
		else {
			// Car is plugged in
			pluglayoutv.setVisibility(View.VISIBLE);

			ReversedSeekBar bar = (ReversedSeekBar)findViewById(R.id.tabInfoSliderChargerControl);
			TextView tvl = (TextView)findViewById(R.id.tabInfoTextChargeStatusLeft);
			TextView tvr = (TextView)findViewById(R.id.tabInfoTextChargeStatusRight);
			switch (data.car_charge_state_i_raw) {
			case 0x04:    // Done
			case 0x115:   // Stopping
			case 0x15:    // Stopped
			case 0x16:    // Stopped
			case 0x17:    // Stopped
			case 0x18:    // Stopped
			case 0x19:    // Stopped
				// Slider on the left, message is "Slide to charge"
				bar.setProgress(100);
				tvl.setText("");
				tvr.setText("SLIDE TO\nCHARGE");
				coiv.setVisibility(View.VISIBLE);
				cmtv.setVisibility(View.INVISIBLE);
				break;
			case 0x0e:    // Wait for schedule charge
				// Slider on the left, message is "Timed Charge"
				bar.setProgress(100);
				tvl.setText("");
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
				tvl.setText(String.format("CHARGING\n%s %s", data.car_charge_linevoltage, data.car_charge_current));
				tvr.setText("");
				cmtv.setText(String.format("%s %s", data.car_charge_mode, data.car_charge_currentlimit).toUpperCase());
				coiv.setVisibility(View.VISIBLE);
				cmtv.setVisibility(View.VISIBLE);
				break;
			default:
				// Slider on the right, message blank
				bar.setProgress(100);
				tvl.setText("");
				tvr.setText("");
				cmtv.setVisibility(View.INVISIBLE);
				coiv.setVisibility(View.INVISIBLE);
				break;				
			}
		}

		tv = (TextView)findViewById(R.id.tabInfoTextIdealRange);
		tv.setText(data.car_range_ideal);
		tv = (TextView)findViewById(R.id.tabInfoTextEstimatedRange);
		tv.setText(data.car_range_estimated);

		ImageView iv = (ImageView)findViewById(R.id.tabInfoImageBatteryOverlay);
		iv.getLayoutParams().width = 268 * data.car_soc_raw / 100;

		iv = (ImageView)findViewById(R.id.tabInfoImageSignalRSSI);
		int resId = getResources().getIdentifier("signal_strength_"+data.car_gsm_bars, "drawable", "com.openvehicles.OVMS");
		iv.setImageResource(resId);

		iv = (ImageView)findViewById(R.id.tabInfoImageCar);
		resId = getResources().getIdentifier(data.sel_vehicle_image, "drawable", "com.openvehicles.OVMS");
		iv.setImageResource(resId);
	}
}

class TabInfoHandler extends Handler {
	private final WeakReference<TabInfo> m_tabInfo; 

	TabInfoHandler(TabInfo tabInfo) {
		m_tabInfo = new WeakReference<TabInfo>(tabInfo);
	}
	@Override
	public void handleMessage(Message msg) {
		TabInfo tabInfo = m_tabInfo.get();

		tabInfo.updateLastUpdatedView();
		tabInfo.updateCarInfoView();
	}
}
