package com.openvehicles.OVMS.ui;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.entities.CarData.DataStale;
import com.openvehicles.OVMS.receiver.CommandReceiver;

public class TabCarActivity extends Activity implements OnClickListener {

	private CarData data;
	private Handler handler = new TabCarHandler(this);
	private Handler lastUpdateTimerHandler = new Handler();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_car);
		
		mCommandReceiver.registerAsReceiver(this);
		
		registerForContextMenu(findViewById(R.id.btn_wakeup));
		registerForContextMenu(findViewById(R.id.txt_homelink));
		registerForContextMenu(findViewById(R.id.tabCarImageHomelink));
		
		findViewById(R.id.btn_lock_car).setOnClickListener(this);
		findViewById(R.id.btn_valet_maode).setOnClickListener(this);
	}
	
	@Override
	protected void onDestroy() {
		mCommandReceiver.unregister(this);
		super.onDestroy();
	};
	
	private CommandReceiver mCommandReceiver = new CommandReceiver() {

		@Override
		public void onResult(String[] pData) {
			if (pData.length >= 3) {
				Toast.makeText(mContext, pData[2], Toast.LENGTH_LONG).show();
			}
		}
		
		@Override
		public void onCommand(String pCommand) {}
	};

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
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_lock_car:
			Ui.showPinDialog(this, data.car_locked ? R.string.lb_unlock_car : R.string.lb_lock_car,
				new Ui.OnChangeListener<String>() {
					@Override
					public void onAction(String pData) {
						String cmd;
						int resId;
						if (data.car_locked) {
							resId = R.string.lb_unlock_car;
							cmd = "22,"+pData;
						} else {
							resId = R.string.lb_lock_car;
							cmd = "20,"+pData;
						}
						mCommandReceiver.sendCommand(resId, cmd);						
					}
				});
			break;
		case R.id.btn_valet_maode:
			Ui.showPinDialog(this, R.string.lb_valet_mode,
				data.car_valetmode ? R.string.lb_valet_mode_off : R.string.lb_valet_mode_on,
				new Ui.OnChangeListener<String>() {
					@Override
					public void onAction(String pData) {
						String cmd;
						int resId;
						if (data.car_valetmode) {
							resId = R.string.lb_valet_mode_off;
							cmd = "23,"+pData;
						} else {
							resId = R.string.lb_valet_mode_on;
							cmd = "21,"+pData;
						}
						mCommandReceiver.sendCommand(resId, cmd);						
					}
				});
			break;
		default:
			v.performLongClick();
		}
	}
	
	private static final int MI_WAKEUP	= Menu.FIRST;
	private static final int MI_HL_01		= Menu.FIRST + 1;
	private static final int MI_HL_02		= Menu.FIRST + 2;
	private static final int MI_HL_03		= Menu.FIRST + 3;
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		switch (v.getId()) {
		case R.id.btn_wakeup:
			menu.setHeaderTitle(R.string.lb_wakeup_car);
			menu.add(0, MI_WAKEUP, 0, R.string.Wakeup);
			menu.add(R.string.Cancel);
			break;
		case R.id.tabCarImageHomelink:
		case R.id.txt_homelink:
			menu.setHeaderTitle("Homelink");
			menu.add(0, MI_HL_01, 0, "1");
			menu.add(0, MI_HL_02, 0, "2");
			menu.add(0, MI_HL_03, 0, "3");
			menu.add(R.string.Cancel);
			break;
		}
	}
	
	ProgressDialog mDlg;
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MI_WAKEUP:
			mCommandReceiver.sendCommand(R.string.msg_wakeup_car, "18");			
			return true;
		case MI_HL_01:
			mCommandReceiver.sendCommand(R.string.msg_issuing_homelink, "24,0");			
			return true;
		case MI_HL_02:
			mCommandReceiver.sendCommand(R.string.msg_issuing_homelink, "24,1");			
			return true;
		case MI_HL_03:
			mCommandReceiver.sendCommand(R.string.msg_issuing_homelink, "24,2");			
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public void registerForContextMenu(View view) {
		super.registerForContextMenu(view);
		view.setOnClickListener(this);
	}
	
	// New car data has been received from the server
	// We store our local copy, the refresh the display
	public void RefreshStatus(CarData carData) {
		data = carData;
		handler.sendEmptyMessage(0);
	}

	// This updates the part of the view with times shown.
	// It is called by a periodic timer so it gets updated every few seconds.
	public void updateLastUpdatedView() {
		if ((data == null) || (data.car_lastupdated == null)) return;

		// First the last updated section...
		TextView tv = (TextView)findViewById(R.id.txt_last_updated);
		long now = System.currentTimeMillis();
		long seconds = (now - data.car_lastupdated.getTime()) / 1000;
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
//		LinearLayout parkinglayoutv = (LinearLayout)findViewById(R.id.tabCarLayoutParking);
		tv = (TextView)findViewById(R.id.txt_parked_time);
		if ((!this.data.car_started) && (this.data.car_parked_time != null)) {
			// Car is parked
//			parkinglayoutv.setVisibility(View.VISIBLE);
			tv.setVisibility(View.VISIBLE);
			
			seconds = (now - data.car_parked_time.getTime()) / 1000;
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
//			parkinglayoutv.setVisibility(View.INVISIBLE);
			tv.setVisibility(View.INVISIBLE);
		}

		// The signal strength indicator
		ImageView iv = (ImageView)findViewById(R.id.img_signal_rssi);
		int resId = getResources().getIdentifier("signal_strength_"+data.car_gsm_bars,
				"drawable", getPackageName());
		iv.setImageResource(resId);
	}
	
	// This updates the main informational part of the view.
	// It is called when the server gets new data.
	public void updateCarBodyView() {
		if ((data == null) || (data.car_lastupdated == null)) return;

		// Now, the car background image	
		ImageView iv = (ImageView)findViewById(R.id.tabCarImageCarOutline);
		int resId = getResources().getIdentifier("ol_"+data.sel_vehicle_image,
				"drawable", getPackageName());
		iv.setImageResource(resId);

		// Ambient weather
		TextView tv = (TextView) findViewById(R.id.tabCarTextAmbient);
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
//		String tirePressureDisplayFormat = "%s\n%s";
        TextView fltv = (TextView) findViewById(R.id.textFLWheel);
        TextView fltvv = (TextView) findViewById(R.id.textFLWheelVal);

        TextView frtv = (TextView) findViewById(R.id.textFRWheel);
        TextView frtvv = (TextView) findViewById(R.id.textFRWheelVal);
        
        TextView rltv = (TextView) findViewById(R.id.textRLWheel);
        TextView rltvv = (TextView) findViewById(R.id.textRLWheelVal);
        
        TextView rrtv = (TextView) findViewById(R.id.textRRWheel);
        TextView rrtvv = (TextView) findViewById(R.id.textRRWheelVal);
        
    	iv = (ImageView)findViewById(R.id.tabCarImageCarTPMSBoxes);
        if (data.stale_tpms == DataStale.NoValue) {
        	iv.setVisibility(View.INVISIBLE);
    		fltv.setText(null);
    		frtv.setText(null);
    		rltv.setText(null);
    		rrtv.setText(null);
    		
    		fltvv.setText(null);
    		frtvv.setText(null);
    		rltvv.setText(null);
    		rrtvv.setText(null);
    		
        } else {
        	iv.setVisibility(View.VISIBLE);

        	fltv.setText(data.car_tpms_fl_p);
    		frtv.setText(data.car_tpms_fr_p);
    		rltv.setText(data.car_tpms_rl_p);
    		rrtv.setText(data.car_tpms_rr_p);
    		
        	fltvv.setText(data.car_tpms_fl_t);
    		frtvv.setText(data.car_tpms_fr_t);
    		rltvv.setText(data.car_tpms_rl_t);
    		rrtvv.setText(data.car_tpms_rr_t);
    		
    		if (data.stale_tpms == DataStale.Stale) {
    			fltv.setTextColor(0xFF808080);
    			frtv.setTextColor(0xFF808080);
    			rltv.setTextColor(0xFF808080);
    			rrtv.setTextColor(0xFF808080);

    			fltvv.setTextColor(0xFF808080);
    			frtvv.setTextColor(0xFF808080);
    			rltvv.setTextColor(0xFF808080);
    			rrtvv.setTextColor(0xFF808080);
    			
    		} else {
    			fltv.setTextColor(0xFFFFFFFF);
    			frtv.setTextColor(0xFFFFFFFF);
    			rltv.setTextColor(0xFFFFFFFF);
    			rrtv.setTextColor(0xFFFFFFFF);

    			fltvv.setTextColor(0xFFFFFFFF);
    			frtvv.setTextColor(0xFFFFFFFF);
    			rltvv.setTextColor(0xFFFFFFFF);
    			rrtvv.setTextColor(0xFFFFFFFF);
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
		} else {
			pemtv.setText(data.car_temp_pem);
			motortv.setText(data.car_temp_motor);
			batterytv.setText(data.car_temp_battery);
			if ((data.stale_car_temps == DataStale.Stale)||(!data.car_coolingpump_on)) {
				pemtv.setTextColor(0xFF808080);
				motortv.setTextColor(0xFF808080);
				batterytv.setTextColor(0xFF808080);
			} else {
				pemtv.setTextColor(0xFFFFFFFF);
				motortv.setTextColor(0xFFFFFFFF);
				batterytv.setTextColor(0xFFFFFFFF);
			}
		}

		// Speed
		tv = (TextView) findViewById(R.id.tabCarTextSpeed);
		if (!data.car_started) tv.setText("");
		else tv.setText(data.car_speed);
				
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
	    } else {
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
	
}

class TabCarHandler extends Handler {
	private final WeakReference<TabCarActivity> m_tabCar; 

	TabCarHandler(TabCarActivity tabCar) {
		m_tabCar = new WeakReference<TabCarActivity>(tabCar);
	}
	@Override
	public void handleMessage(Message msg) {
		TabCarActivity tabCar = m_tabCar.get();

		tabCar.updateLastUpdatedView();
		tabCar.updateCarBodyView();
	}
}