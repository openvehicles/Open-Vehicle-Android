package com.openvehicles.OVMS;

import java.util.Date;

import com.openvehicles.OVMS.CarData.DataStale;

import android.app.Activity;
import android.graphics.Typeface;
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

		// The signal strength indicator
		ImageView iv = (ImageView)findViewById(R.id.tabCarImageSignalRSSI);
		int resId = getResources().getIdentifier("signal_strength_"+data.car_gsm_bars, "drawable", "com.openvehicles.OVMS");
		iv.setImageResource(resId);

		// Now, the car background image	
		iv = (ImageView)findViewById(R.id.tabCarImageCarOutline);
		resId = getResources().getIdentifier("ol_"+data.sel_vehicle_image, "drawable", "com.openvehicles.OVMS");
		iv.setImageResource(resId);

		// Ambient weather
		tv = (TextView) findViewById(R.id.tabCarTextAmbient);
		if (data.stale_ambient_temp == DataStale.NoValue) {
			tv.setText("");
			tv.setTextColor(0xFF808080);
		}
		else if ((data.stale_ambient_temp == DataStale.Stale)||(!data.car_coolingpump_on)) {
			tv.setText(data.car_temp_ambient);
			tv.setTextColor(0xFF808080);
		}
		else {
			tv.setText(data.car_temp_ambient);
			tv.setTextColor(0xFFFFFFFF);
		}

		// TPMS
		String tirePressureDisplayFormat = "%s\n%s";
        TextView fltv = (TextView) findViewById(R.id.textFLWheel);
        TextView frtv = (TextView) findViewById(R.id.textFRWheel);
        TextView rltv = (TextView) findViewById(R.id.textRLWheel);
        TextView rrtv = (TextView) findViewById(R.id.textRRWheel);
    	iv = (ImageView)findViewById(R.id.tabCarImageCarTPMSBoxes);
        if (data.stale_tpms == DataStale.NoValue) {
        	iv.setVisibility(View.INVISIBLE);
    		fltv.setText("");
    		frtv.setText("");
    		rltv.setText("");
    		rrtv.setText("");
        }
        else {
        	iv.setVisibility(View.VISIBLE);
    		fltv.setText(String.format(tirePressureDisplayFormat,data.car_tpms_fl_p, data.car_tpms_fl_t));
    		frtv.setText(String.format(tirePressureDisplayFormat,data.car_tpms_fr_p, data.car_tpms_fr_t));
    		rltv.setText(String.format(tirePressureDisplayFormat,data.car_tpms_rl_p, data.car_tpms_rl_t));
    		rrtv.setText(String.format(tirePressureDisplayFormat,data.car_tpms_rr_p, data.car_tpms_rr_t));
    		if (data.stale_tpms == DataStale.Stale) {
    			fltv.setTextColor(0xFF808080);
    			frtv.setTextColor(0xFF808080);
    			rltv.setTextColor(0xFF808080);
    			rrtv.setTextColor(0xFF808080);
    		}
    		else {
    			fltv.setTextColor(0xFFFFFFFF);
    			frtv.setTextColor(0xFFFFFFFF);
    			rltv.setTextColor(0xFFFFFFFF);
    			rrtv.setTextColor(0xFFFFFFFF);
    		}
        }

        // Temperatures
		TextView pemtv = (TextView) findViewById(R.id.tabCarTextPEM);
		TextView motortv = (TextView) findViewById(R.id.tabCarTextMotor);
		TextView batterytv = (TextView) findViewById(R.id.tabCarTextBattery);
		if (data.stale_car_temps == DataStale.NoValue) {
			pemtv.setText("");
			motortv.setText("");
			batterytv.setText("");
		}
		else {
			pemtv.setText(data.car_temp_pem);
			motortv.setText(data.car_temp_motor);
			batterytv.setText(data.car_temp_battery);
			if ((data.stale_car_temps == DataStale.Stale)||(!data.car_coolingpump_on)) {
				pemtv.setTextColor(0xFF808080);
				motortv.setTextColor(0xFF808080);
				batterytv.setTextColor(0xFF808080);
			}
			else {
				pemtv.setTextColor(0xFFFFFFFF);
				motortv.setTextColor(0xFFFFFFFF);
				batterytv.setTextColor(0xFFFFFFFF);
			}
		}

		// Speed
		tv = (TextView) findViewById(R.id.tabCarTextSpeed);
		if (!data.car_started) 
			tv.setText("");
		else
			tv.setText(data.car_speed);
				
		// Car Hood
		iv = (ImageView) findViewById(R.id.tabCarImageCarHoodOpen);
		iv.setVisibility(data.car_bonnet_open ? View.VISIBLE : View.INVISIBLE);
		
		// Left Door
		iv = (ImageView) findViewById(R.id.tabCarImageCarLeftDoorOpen);
		iv.setVisibility(data.car_frontleftdoor_open ? View.VISIBLE : View.INVISIBLE);
		
		// Right Door
		iv = (ImageView) findViewById(R.id.tabCarImageCarRightDoorOpen);
		iv.setVisibility(data.car_frontrightdoor_open ? View.VISIBLE : View.INVISIBLE);
		
		// Trunk
		iv = (ImageView) findViewById(R.id.tabCarImageCarTrunkOpen);
		iv.setVisibility(data.car_trunk_open ? View.VISIBLE : View.INVISIBLE);
		
		// Headlights
		iv = (ImageView) findViewById(R.id.tabCarImageCarHeadlightsON);
		iv.setVisibility(data.car_headlights_on ? View.VISIBLE : View.INVISIBLE);

		// Car locked
		iv = (ImageView) findViewById(R.id.tabCarImageCarLocked);
		iv.setImageResource(data.car_locked ? R.drawable.carlock : R.drawable.carunlock);
		
		// Valet mode
		iv = (ImageView) findViewById(R.id.tabCarImageCarValetMode);
		iv.setImageResource(data.car_valetmode ? R.drawable.carvaleton : R.drawable.carvaletoff);
		
		// Charge Port
	    iv = (ImageView) findViewById(R.id.tabCarImageCarChargePortOpen);
	    if (!data.car_chargeport_open) {
			iv.setVisibility(View.INVISIBLE);
	    }
	    else {
			iv.setVisibility(View.VISIBLE);
			
			if (data.car_charge_substate_i_raw == 0x07) {
				// We need to connect the power cable
				iv.setImageResource(R.drawable.roadster_outline_cu);
			}
			else if ((data.car_charge_state_i_raw == 0x0d)||(data.car_charge_state_i_raw == 0x0e)||(data.car_charge_state_i_raw == 0x101)) {
				// Preparing to charge, timer wait, or fake 'starting' state
				iv.setImageResource(R.drawable.roadster_outline_ce);
			}
			else if ((data.car_charge_state_i_raw == 0x01)||
					 (data.car_charge_state_i_raw == 0x02)||
					 (data.car_charge_state_i_raw == 0x0f)||
					 (data.car_charging)) {
				// Charging
				iv.setImageResource(R.drawable.roadster_outline_cp);
			}
			else if (data.car_charge_state_i_raw == 0x04) {
				// Charging done
				iv.setImageResource(R.drawable.roadster_outline_cd);
			}
			else if ((data.car_charge_state_i_raw >= 0x15)&&(data.car_charge_state_i_raw <= 0x19)) {
				// Stopped
				iv.setImageResource(R.drawable.roadster_outline_cs);
			}
			else {
				// Fake 0x115 'stopping' state, or something else not understood
				iv.setImageResource(R.drawable.roadster_outline_cp);
			}
	    }
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			updateLastUpdatedView();
		}
	};

	public void RefreshStatus(CarData carData) {
		Log.d("Tab", "TabCar Refresh");
		data = carData;
		handler.sendEmptyMessage(0);

	}

}
