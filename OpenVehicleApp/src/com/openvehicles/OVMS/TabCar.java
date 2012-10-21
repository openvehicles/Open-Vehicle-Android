package com.openvehicles.OVMS;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabCar extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabcar);

	}

	private CarData data;
	private Handler lastUpdateTimerHandler = new Handler();

	private Runnable lastUpdateTimer = new Runnable() {
		public void run() {
			// update the last updated textview
			updateLastUpdatedView();

			// schedule next run
			lastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000);
		}
	};

	@Override
	protected void onResume() {
		super.onResume();

		// set update timer
		lastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// remove update timer
		lastUpdateTimerHandler.removeCallbacks(lastUpdateTimer);
	}

	private void updateLastUpdatedView() {
		if ((data == null) || (data.car_lastupdated == null))
			return;

		// First the last updated section...
		TextView tv = (TextView)findViewById(R.id.tabCarTextLastUpdated);
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
		LinearLayout parkinglayoutv = (LinearLayout)findViewById(R.id.tabCarLayoutParking);
		if ((!this.data.car_started) && (this.data.car_parked_time != null))
		{
			// Car is parked
			parkinglayoutv.setVisibility(View.VISIBLE);
			tv = (TextView)findViewById(R.id.tabCarTextParkedTime);
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

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			updateLastUpdatedView();

			ImageView iv = (ImageView)findViewById(R.id.tabCarImageCarOutline);
			int resId = getResources().getIdentifier("ol_"+data.sel_vehicle_image, "drawable", "com.openvehicles.OVMS");
			iv.setImageResource(resId);

			iv = (ImageView)findViewById(R.id.tabCarImageSignalRSSI);
			resId = getResources().getIdentifier("signal_strength_"+data.car_gsm_bars, "drawable", "com.openvehicles.OVMS");
			iv.setImageResource(resId);

			String tirePressureDisplayFormat = "%s\n%s";

			TextView tv = (TextView) findViewById(R.id.textFLWheel);
			tv.setText(String.format(tirePressureDisplayFormat,data.car_tpms_fl_p, data.car_tpms_fl_t));
			tv = (TextView) findViewById(R.id.textFRWheel);
			tv.setText(String.format(tirePressureDisplayFormat,data.car_tpms_fr_p, data.car_tpms_fr_t));
			tv = (TextView) findViewById(R.id.textRLWheel);
			tv.setText(String.format(tirePressureDisplayFormat,data.car_tpms_rl_p, data.car_tpms_rl_t));
			tv = (TextView) findViewById(R.id.textRRWheel);
			tv.setText(String.format(tirePressureDisplayFormat,data.car_tpms_rr_p, data.car_tpms_rr_t));

			tv = (TextView) findViewById(R.id.tabCarTextAmbient);
			tv.setText(data.car_temp_ambient);

			tv = (TextView) findViewById(R.id.tabCarTextPEM);
			tv.setText(data.car_temp_pem);

			tv = (TextView) findViewById(R.id.tabCarTextMotor);
			tv.setText(data.car_temp_motor);

			tv = (TextView) findViewById(R.id.tabCarTextBattery);
			tv.setText(data.car_temp_battery);

		    iv = (ImageView) findViewById(R.id.tabCarImageCarChargePortOpen);
			iv.setVisibility(data.car_chargeport_open ? View.VISIBLE : View.INVISIBLE);
			
			iv = (ImageView) findViewById(R.id.tabCarImageCarHoodOpen);
			iv.setVisibility(data.car_bonnet_open ? View.VISIBLE : View.INVISIBLE);
			
			iv = (ImageView) findViewById(R.id.tabCarImageCarLeftDoorOpen);
			iv.setVisibility(data.car_frontleftdoor_open ? View.VISIBLE : View.INVISIBLE);
			
			iv = (ImageView) findViewById(R.id.tabCarImageCarRightDoorOpen);
			iv.setVisibility(data.car_frontrightdoor_open ? View.VISIBLE : View.INVISIBLE);
			
			iv = (ImageView) findViewById(R.id.tabCarImageCarTrunkOpen);
			iv.setVisibility(data.car_trunk_open ? View.VISIBLE : View.INVISIBLE);
			
			iv = (ImageView) findViewById(R.id.tabCarImageCarHeadlightsON);
			iv.setVisibility(data.car_headlights_on ? View.VISIBLE : View.INVISIBLE);

			iv = (ImageView) findViewById(R.id.tabCarImageCarLocked);
			iv.setImageResource(data.car_locked ? R.drawable.carlock
					: R.drawable.carunlock);
		}
	};

	public void RefreshStatus(CarData carData) {
		Log.d("Tab", "TabCar Refresh");
		data = carData;
		handler.sendEmptyMessage(0);

	}

}
