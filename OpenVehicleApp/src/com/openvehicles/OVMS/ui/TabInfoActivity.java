package com.openvehicles.OVMS.ui;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.receiver.CommandReceiver;
import com.openvehicles.OVMS.ui.Ui.OnChangeListener;
import com.openvehicles.OVMS.ui.witdet.ReversedSeekBar;
import com.openvehicles.OVMS.ui.witdet.SlideNumericView;
import com.openvehicles.OVMS.ui.witdet.SwitcherView;

public class TabInfoActivity extends Activity implements OnClickListener {
	// Private data
	private CarData mCarData;
	private Handler mHandler = new TabInfoHandler(this);
	private Handler mLastUpdateTimerHandler = new Handler();

	// onCreate: System event to tell us when the activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_info);

		mCommandReceiver.registerAsReceiver(this);
		
		findViewById(R.id.tabInfoTextSOC).setOnClickListener(this);
		findViewById(R.id.tabInfoTextChargeMode).setOnClickListener(this);
		findViewById(R.id.tabInfoImageBatteryChargingOverlay).setOnClickListener(this);
		findViewById(R.id.tabInfoImageBatteryOverlay).setOnClickListener(this);
		
		ReversedSeekBar bar = (ReversedSeekBar)findViewById(R.id.tabInfoSliderChargerControl);
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			private int mStartProgress;
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				int progress = seekBar.getProgress();
				if (progress > 50) {
					seekBar.setProgress(100);
					progress = 100;
				} else {
					seekBar.setProgress(0);
					progress = 0;
				}
				if (mStartProgress == progress) return;
				
				if (progress == 0) startCharge();
				else stopCharge();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				mStartProgress = seekBar.getProgress();
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
		});
	}

	@Override
	protected void onDestroy() {
		mCommandReceiver.unregister(this);
		super.onDestroy();
	};
	
	private void startCharge() {
		mCommandReceiver.sendCommand(R.string.msg_starting_charge, "11");
		mCarData.car_charge_linevoltage_raw = 0;
		mCarData.car_charge_current_raw = 0;
		mCarData.car_charge_state_s_raw = "starting";
		mCarData.car_charge_state_i_raw = 0x101;
		updateCarInfoView();
	}
	
	private void stopCharge() {
		mCommandReceiver.sendCommand(R.string.msg_stopping_charge, "12");
		mCarData.car_charge_linevoltage_raw = 0;
		mCarData.car_charge_current_raw = 0;
		mCarData.car_charge_state_s_raw = "stopping";
		mCarData.car_charge_state_i_raw = 0x115;
		updateCarInfoView();
	}
	
	
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
	

	// onResume: System event to tell us the App has resumed
	@Override
	protected void onResume() {
		super.onResume();

		// set update timer
		mLastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000);
	}

	// onPause: System event to tell us the app is about to pause
	@Override
	protected void onPause() {
		super.onPause();

		// remove update timer
		mLastUpdateTimerHandler.removeCallbacks(lastUpdateTimer);
	}
	
	@Override
	public void onClick(View v) {
		showDialog(0);
	}
	
	@Override
	protected void onPrepareDialog(int pId, Dialog pDlg) {
		int ncm = mCarData.car_charge_mode_i_raw;
		if (ncm > 2) ncm--;
		
		if (ncm > 0 && ncm < 4) {
			SwitcherView sw = (SwitcherView) pDlg.findViewById(R.id.sv_state);
			sw.setSelected(ncm);
		}

		SlideNumericView snv = (SlideNumericView) pDlg.findViewById(R.id.snv_amps);
		snv.setValue(mCarData.car_charge_currentlimit_raw);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		View content = LayoutInflater.from(this).inflate(R.layout.dlg_charger, null);
		SwitcherView sw = (SwitcherView) content.findViewById(R.id.sv_state);
		sw.setOnChangeListener(new OnChangeListener<SwitcherView>() {
			@Override
			public void onAction(SwitcherView pData) {
				TextView txtInfo = (TextView) ((View) pData.getParent()).findViewById(R.id.txt_info);
				switch (pData.getSelected()) {
				case 2:
					txtInfo.setText(R.string.msg_charger_range);					
					break;
				case 3:
					txtInfo.setText(R.string.msg_charger_perform);					
					break;
				default:
					txtInfo.setText(null);					
				}
			}
		});
		
		return new AlertDialog.Builder(this)
		.setTitle(R.string.lb_charger_setting)
		.setView(content)
		.setNegativeButton(R.string.Cancel, null)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface pDlg, int pWhich) {
				Dialog dlg = (Dialog) pDlg;
				SwitcherView sw = (SwitcherView) dlg.findViewById(R.id.sv_state);
				SlideNumericView snv = (SlideNumericView) dlg.findViewById(R.id.snv_amps);
				
				 int ncm = sw.getSelected();
				 if (ncm >= 2) ncm++;
				 
				 int ncl = snv.getValue();
				 
				 if (ncm != mCarData.car_charge_mode_i_raw && ncl != mCarData.car_charge_currentlimit_raw) {
					 mCommandReceiver.sendCommand(R.string.msg_setting_charge_mc,
							 String.format("16,%d,%d", ncm, ncl));
				 } else
				 if (ncm != mCarData.car_charge_mode_i_raw) {
					 mCommandReceiver.sendCommand(R.string.msg_setting_charge_m,
							 String.format("10,%d", ncm));
				 } else
				 if (ncl != mCarData.car_charge_currentlimit_raw) {
					 mCommandReceiver.sendCommand(R.string.msg_setting_charge_c,
							 String.format("15,%d", ncl));
				 }
			}
		}).create();
	}

	// This is a periodic timer to refresh the info screen
	private Runnable lastUpdateTimer = new Runnable() {
		public void run() {
			// update the last updated textview
			updateLastUpdatedView();

			// schedule next run
			mLastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000);
		}
	};

	// New car data has been received from the server
	// We store our local copy, the refresh the display
	public void RefreshStatus(CarData carData) {
		mCarData = carData;
		mHandler.sendEmptyMessage(0);
	}

	// This updates the part of the view with times shown.
	// It is called by a periodic timer so it gets updated every few seconds.
	public void updateLastUpdatedView() {

		// Quick exit if the car data is not there yet...
		if ((mCarData == null) || (mCarData.car_lastupdated == null))
			return;

		// Let's update the Info tab view...

		// First the last updated section...
		TextView tv = (TextView)findViewById(R.id.txt_last_updated);
		long now = System.currentTimeMillis();
		long seconds = (now - mCarData.car_lastupdated.getTime()) / 1000;
		long minutes = (seconds)/60;
		long hours = minutes/60;
		long days = minutes/(60*24);
		Log.d("OVMS", "Last updated: " + seconds + " secs ago");

		if (mCarData.car_lastupdated == null) {
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
//		LinearLayout parkinglayoutv = (LinearLayout)findViewById(R.id.tabInfoLayoutParking);
		tv = (TextView) findViewById(R.id.txt_parked_time);
		if ((!this.mCarData.car_started) && (this.mCarData.car_parked_time != null)) {
			// Car is parked
//			parkinglayoutv.setVisibility(View.VISIBLE);
			tv.setVisibility(View.VISIBLE);
			seconds = (now - mCarData.car_parked_time.getTime()) / 1000;
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
	public void updateCarInfoView() {
		TextView tv = (TextView)findViewById(R.id.textVehicleID);
		tv.setText(mCarData.sel_vehicleid);

		tv = (TextView)findViewById(R.id.tabInfoTextSOC);
		tv.setText(mCarData.car_soc);

		TextView cmtv = (TextView)findViewById(R.id.tabInfoTextChargeMode);
		ImageView coiv = (ImageView)findViewById(R.id.tabInfoImageBatteryChargingOverlay);
		ReversedSeekBar bar = (ReversedSeekBar)findViewById(R.id.tabInfoSliderChargerControl);
		TextView tvl = (TextView)findViewById(R.id.tabInfoTextChargeStatusLeft);
		TextView tvr = (TextView)findViewById(R.id.tabInfoTextChargeStatusRight);
		if ((!mCarData.car_chargeport_open)||(mCarData.car_charge_substate_i_raw==0x07)) {
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
			
			switch (mCarData.car_charge_state_i_raw) {
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
				tvl.setText(String.format("CHARGING\n%s %s", mCarData.car_charge_linevoltage, mCarData.car_charge_current));
				tvr.setText("");
				cmtv.setText(String.format("%s %s", mCarData.car_charge_mode, mCarData.car_charge_currentlimit).toUpperCase());
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
		tv.setText(mCarData.car_range_ideal);
		tv = (TextView)findViewById(R.id.tabInfoTextEstimatedRange);
		tv.setText(mCarData.car_range_estimated);

		int maxWeight = findViewById(R.id.tabInfoTextSOC).getLayoutParams().width;
		int realWeight = Math.round((maxWeight * mCarData.car_soc_raw / 100) * 1.1f);
		View v = findViewById(R.id.tabInfoImageBatteryOverlay);
		
		v.getLayoutParams().width = Math.min(maxWeight, realWeight);
		v.requestLayout();
		
		ImageView iv = (ImageView)findViewById(R.id.img_signal_rssi);
		int resId = getResources().getIdentifier("signal_strength_"+mCarData.car_gsm_bars, "drawable", getPackageName());
		iv.setImageResource(resId);

		iv = (ImageView)findViewById(R.id.tabInfoImageCar);
		resId = getResources().getIdentifier(mCarData.sel_vehicle_image, "drawable", getPackageName());
		iv.setImageResource(resId);
	}
}

class TabInfoHandler extends Handler {
	private final WeakReference<TabInfoActivity> mTabInfo; 

	TabInfoHandler(TabInfoActivity tabInfo) {
		mTabInfo = new WeakReference<TabInfoActivity>(tabInfo);
	}
	
	@Override
	public void handleMessage(Message msg) {
		TabInfoActivity tabInfo = mTabInfo.get();

		tabInfo.updateLastUpdatedView();
		tabInfo.updateCarInfoView();
	}
}
