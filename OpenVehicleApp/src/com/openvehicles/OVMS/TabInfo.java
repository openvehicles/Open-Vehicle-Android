package com.openvehicles.OVMS;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabInfo extends Activity {

	// Private data
	private CarData data;
	private Handler lastUpdateTimerHandler = new Handler();
	private AlertDialog softwareInformation;

	// onCreate: System event to tell us when the activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabinfo);
		initUI();
	}

	// onResume: System event to tell us the app has resumed
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

		// Dismiss the software information pop-up
		if ((softwareInformation != null) && softwareInformation.isShowing())
			softwareInformation.dismiss();

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

	// Initialise the UI
	private void initUI() {
		// TODO:
	}

	// Force the status display to be refreshed with the specified data
	public void RefreshStatus(CarData carData) {
		data = carData;
		handler.sendEmptyMessage(0);
	}

	// Refresh the Info view
	private void updateLastUpdatedView() {

		// Quick exit if the car data is not there yet...
		if ((data == null) || (data.car_lastupdated == null))
			return;

		// Let's update the Info tab view...

		// First the last updated section...
		TextView tv = (TextView)findViewById(R.id.tabInfoTextLastUpdated);
		Date now = new Date();
		long lastUpdateSecondsAgo = (now.getDate() - data.car_lastupdated.getDate()) / 1000;
		Log.d("OVMS", "Last updated: " + lastUpdateSecondsAgo + " secs ago");

		if (lastUpdateSecondsAgo < 60)
		{
			tv.setText("live");
		}
		else if (lastUpdateSecondsAgo < 3600)
		{
			int displayValue = (int)Math.ceil(lastUpdateSecondsAgo / 60);
			tv.setText(String.format("Updated: %d minute%s ago", displayValue, (displayValue > 1) ? "s" : ""));
		}
		else if (lastUpdateSecondsAgo < 86400)
		{
			int displayValue = (int)Math.ceil(lastUpdateSecondsAgo / 3600);
			tv.setText(String.format("Updated: %d hour%s ago", displayValue, (displayValue > 1) ? "s" : ""));
		}
		else if (lastUpdateSecondsAgo < 864000)
		{
			int displayValue = (int)Math.ceil(lastUpdateSecondsAgo / 86400);
			tv.setText(String.format("Updated: %d day%s ago", displayValue, (displayValue > 1) ? "s" : ""));
		}
		else
			tv.setText(String.format(getString(R.string.LastUpdated), data.car_lastupdated));

		// Then the parking timer...
		LinearLayout parkinglayoutv = (LinearLayout)findViewById(R.id.tabInfoLayoutParking);
		if ((!this.data.car_started) && (this.data.car_parked_time != null))
		{
			// Car is parked
			parkinglayoutv.setVisibility(8);
			TextView parkedtimev = (TextView)findViewById(R.id.tabInfoTextParkedTime);
			long parktime = (now.getDate() - data.car_parked_time.getDate()) / 1000;
			if (parktime < 60)
				parkedtimev.setText("just now");
			else
			{
				parkedtimev.setText(String.format("%1$tD %1$tT", this.data.car_parked_time));
			}
		}
		else
		{
			parkinglayoutv.setVisibility(0);
		}
	}

	// Handle an incoming data message
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			updateLastUpdatedView();

			TextView tv = (TextView)findViewById(R.id.textVehicleID);
			tv.setText(data.sel_vehicleid);

			tv = (TextView)findViewById(R.id.tabInfoTextSOC);
			tv.setText(data.car_soc);

			tv = (TextView)findViewById(R.id.tabInfoTextChargeStatus);
			if (data.car_charge_state.equals("charging"))
				tv.setText(String.format("Charging - %s (%sV %sA)", data.car_charge_mode, data.car_charge_linevoltage, data.car_charge_current)); 
			else
				tv.setText("");

			tv = (TextView)findViewById(R.id.tabInfoTextIdealRange);
			tv.setText(data.car_range_ideal);
			tv = (TextView)findViewById(R.id.tabInfoTextEstimatedRange);
			tv.setText(data.car_range_estimated);
			
			ImageView iv = (ImageView)findViewById(R.id.tabInfoImageBatteryOverlay);
			iv.getLayoutParams().width = 268 * data.car_soc_raw / 100;

			iv = (ImageView)findViewById(R.id.tabInfoImageCar);
			int resId = getResources().getIdentifier(data.sel_vehicle_image, "drawable", "com.openvehicles.OVMS");
			iv.setImageResource(resId);

			iv.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							TabInfo.this);
					builder.setMessage(String.format(
							"Power: %s\nVIN: %s\nGSM Signal: %s\nHandbrake: %s\n\nCar Module: %s\nOVMS Server: %s",
							(data.car_started ? "ON" : "OFF"),
							data.car_vin,
							data.car_gsm_signal,
							(data.car_handbrake_on ? "ENGAGED" : "DISENGAGED"),
							data.car_firmware, 
							data.server_firmware))
					.setTitle("Software Information")
					.setCancelable(false)
					.setPositiveButton("Close",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int id) {
							dialog.dismiss();
						}
					});
					softwareInformation = builder.create();
					softwareInformation.show();
				}
			});
		}
	};

}
