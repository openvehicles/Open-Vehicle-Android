package com.openvehicles.OVMS.ui;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.OnResultCommandListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.settings.CarInfoFragment;
import com.openvehicles.OVMS.ui.settings.CellularStatsFragment;
import com.openvehicles.OVMS.ui.settings.FeaturesFragment;
import com.openvehicles.OVMS.ui.settings.GlobalOptionsFragment;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.ui.utils.Ui.OnChangeListener;
import com.openvehicles.OVMS.ui.witdet.ReversedSeekBar;
import com.openvehicles.OVMS.ui.witdet.ScaleLayout;
import com.openvehicles.OVMS.ui.witdet.SlideNumericView;
import com.openvehicles.OVMS.ui.witdet.SwitcherView;
import com.openvehicles.OVMS.utils.CarsStorage;

public class InfoFragment extends BaseFragment implements OnClickListener,
		OnResultCommandListener {
	private static final String TAG = "InfoFragment";

	protected CarsStorage mCarsStorage;
	protected CarData mCarData;
	protected Gallery mCarSelect;
	protected int mCarSelectPos;
	protected Handler mHandler;
	protected Runnable mCarChanger;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// init car data:
		mCarsStorage = CarsStorage.get();
		mCarData = mCarsStorage.getSelectedCarData();

		// inflate layout:
		View rootView = inflater.inflate(R.layout.fragment_info, null);

		// init car selector:
		mHandler = new Handler(Looper.getMainLooper());
		mCarSelect = (Gallery) rootView.findViewById(R.id.tabInfoImageCar);
		mCarSelectPos = 0;
		mCarChanger = null;
		mCarSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == mCarSelectPos)
					return; // no user action
				try {
					mCarSelectPos = position;
					// while mCarChanger is defined, updates are inhibited to avoid
					// interference from scripts or incoming updates during interaction
					if (mCarChanger != null)
						mHandler.removeCallbacks(mCarChanger);
					mCarChanger = () -> {
						CarData carData = mCarsStorage.getStoredCars().get(mCarSelectPos);
						if (!carData.sel_vehicleid.equals(mCarData.sel_vehicleid)) {
							Log.d(TAG, "onItemSelected: pos=" + mCarSelectPos + ", id=" + carData.sel_vehicleid);
							changeCar(carData);
							mCarChanger = null; // car is changed, allow updates
							update(carData); // …and do a first update
						}
					};
					mHandler.postDelayed(mCarChanger, 750);
				} catch (Exception e) {
					Log.e(TAG, "Car selection: position invalid: " + position);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// nop
			}
		});

		// init ScaleLayout:
		final ScaleLayout scaleLayout = (ScaleLayout) rootView
				.findViewById(R.id.scaleLayout);
		scaleLayout.setOnScale(new Runnable() {
			@Override
			public void run() {
				SeekBar sb = (SeekBar) scaleLayout
						.findViewById(R.id.tabInfoSliderChargerControl);
				ScaleLayout.LayoutParams lp = (ScaleLayout.LayoutParams) sb
						.getLayoutParams();

				Bitmap srcBmp = BitmapFactory
						.decodeResource(
								scaleLayout.getContext().getResources(),
								R.drawable.charger_button);
				int tw = (int) (srcBmp.getWidth() * (lp.height / srcBmp
						.getHeight()));
				int th = lp.height;

				if (tw < 40) {
					tw = 61;
				} // Sane lower limit
				if (th < 10) {
					th = 22;
				} // Sane lower limit

				Bitmap dstBmp = Bitmap.createScaledBitmap(srcBmp, tw,
						lp.height, true);
				srcBmp.recycle();

				BitmapDrawable drw = new BitmapDrawable(scaleLayout
						.getContext().getResources(), dstBmp);
				sb.setThumb(drw);
				// sb.setThumbOffset(dstBmp.getWidth() / 9);
			}
		});

		setHasOptionsMenu(true);

		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.battery_options, menu);
	}

	@Override
	public void onPrepareOptionsMenu(@NonNull Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (mCarData != null && mCarData.car_type != null) {
			menu.findItem(R.id.mi_battery_stats).setVisible(mCarData.car_type.equals("RT"));
			menu.findItem(R.id.mi_power_stats).setVisible(mCarData.car_type.equals("RT"));
		}
	}

	@Override
	public void onDestroyView() {
		cancelCommand();
		super.onDestroyView();
	}

	@Override
	public void onResume() {
		super.onResume();
		mCarSelect.setAdapter(new CarSelectAdapter());
		mCarSelectPos = mCarsStorage.getSelectedCarIndex();
		mCarSelect.setSelection(mCarSelectPos);
		Log.d(TAG, "onResume: pos=" + mCarSelectPos + ", id=" + mCarData.sel_vehicleid);
	}

	private class CarSelectAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return mCarsStorage.getStoredCars().size();
		}
		@Override
		public Object getItem(int position) {
			return mCarsStorage.getStoredCars().get(position);
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			try {
				CarData carData = mCarsStorage.getStoredCars().get(position);
				ImageView iv = convertView != null ? (ImageView) convertView : new ImageView(parent.getContext());
				iv.setLayoutParams(new Gallery.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				iv.setScaleType(ImageView.ScaleType.FIT_START);
				iv.setAdjustViewBounds(true);
				iv.setImageResource(Ui.getDrawableIdentifier(parent.getContext(), carData.sel_vehicle_image));
				return iv;
			} catch (Exception e) {
				return null;
			}
		}
	}


	@Override
	public void update(CarData pCarData) {

		// while mCarChanger is defined, updates are inhibited to avoid
		// interference from scripts or incoming updates during interaction
		if (mCarChanger != null) {
			Log.d(TAG, "update: inhibited, UI car change is in progress");
			return;
		}

		// store pointer to new car:
		mCarData = pCarData;

		// update UI:
		getCompatActivity().invalidateOptionsMenu();
		updateLastUpdatedView(pCarData);
		updateCarInfoView(pCarData);
		updateChargeAlerts();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setHasOptionsMenu(true);

		findViewById(R.id.tabInfoTextSOC).setOnClickListener(this);
		findViewById(R.id.tabInfoTextChargeMode).setOnClickListener(this);
		findViewById(R.id.tabInfoImageBatteryChargingOverlay)
				.setOnClickListener(this);
		findViewById(R.id.tabInfoImageBatteryOverlay).setOnClickListener(this);

		ReversedSeekBar bar = (ReversedSeekBar) findViewById(R.id.tabInfoSliderChargerControl);
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
				if (mStartProgress == progress)
					return;

				if (progress == 0)
					chargerConfirmStart();
				else
					chargerConfirmStop();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				mStartProgress = seekBar.getProgress();
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});

	}

	private void chargerConfirmStart() {
		ReversedSeekBar bar = (ReversedSeekBar) findViewById(R.id.tabInfoSliderChargerControl);
		// create & open dialog:
		View dialogView = LayoutInflater.from(getActivity()).inflate(
				R.layout.dlg_charger_confirm, null);

		new AlertDialog.Builder(getActivity())
				.setTitle(R.string.lb_charger_confirm_start)
				.setView(dialogView)
				.setNegativeButton(R.string.Cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface pDlg, int pWhich) {
								bar.setProgress(100);
							}
						})
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface pDlg, int pWhich) {
								startCharge();
							}
						})
				.show();
	}

	private void chargerConfirmStop() {
		ReversedSeekBar bar = (ReversedSeekBar) findViewById(R.id.tabInfoSliderChargerControl);
		// create & open dialog:
		View dialogView = LayoutInflater.from(getActivity()).inflate(
				R.layout.dlg_charger_confirm, null);

		new AlertDialog.Builder(getActivity())
				.setTitle(R.string.lb_charger_confirm_stop)
				.setView(dialogView)
				.setNegativeButton(R.string.Cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface pDlg, int pWhich) {
								bar.setProgress(0);
							}
						})
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface pDlg, int pWhich) {
								stopCharge();
							}
						})
				.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.mi_battery_stats) {
			BaseFragmentActivity.show(getActivity(), BatteryFragment.class, null,
					Configuration.ORIENTATION_UNDEFINED);
			return true;
		} else if (itemId == R.id.mi_power_stats) {
			BaseFragmentActivity.show(getActivity(), PowerFragment.class, null,
					Configuration.ORIENTATION_UNDEFINED);
			return true;
		} else if (itemId == R.id.mi_show_carinfo) {
			BaseFragmentActivity.show(getActivity(), CarInfoFragment.class, null,
					Configuration.ORIENTATION_UNDEFINED);
			return true;
		} else if (itemId == R.id.mi_aux_battery_stats) {
			BaseFragmentActivity.show(getActivity(), AuxBatteryFragment.class, null,
					Configuration.ORIENTATION_UNDEFINED);
			return true;
		} else if (itemId == R.id.mi_show_features) {
			BaseFragmentActivity.show(getActivity(), FeaturesFragment.class, null,
					Configuration.ORIENTATION_UNDEFINED);
			return true;
		} else if (itemId == R.id.mi_globaloptions) {
			BaseFragmentActivity.show(getActivity(), GlobalOptionsFragment.class, null,
					Configuration.ORIENTATION_UNDEFINED);
			return true;
		} else if (itemId == R.id.mi_cellular_usage) {
			BaseFragmentActivity.show(getActivity(), CellularStatsFragment.class, null,
					Configuration.ORIENTATION_UNDEFINED);
			return true;
		} else if (itemId == R.id.mi_app_about) {
			((MainActivity) getActivity()).showVersion();
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		chargerSetting();
	}


	@Override
	public void onResultCommand(String[] result) {
		if (result.length <= 1)
			return;

		int command = Integer.parseInt(result[0]);
		int resCode = Integer.parseInt(result[1]);
		String resText = (result.length > 2) ? result[2] : "";
		String cmdMessage = getSentCommandMessage(result[0]);

		Context context = getActivity();
		if (context != null) {
			switch (resCode) {
				case 0: // ok
					Toast.makeText(context, cmdMessage + " => " + getString(R.string.msg_ok),
							Toast.LENGTH_SHORT).show();
					break;
				case 1: // failed
					Toast.makeText(context, cmdMessage + " => " + getString(R.string.err_failed, resText),
							Toast.LENGTH_SHORT).show();
					break;
				case 2: // unsupported
					Toast.makeText(context, cmdMessage + " => " + getString(R.string.err_unsupported_operation),
							Toast.LENGTH_SHORT).show();
					break;
				case 3: // unimplemented
					Toast.makeText(context, cmdMessage + " => " + getString(R.string.err_unimplemented_operation),
							Toast.LENGTH_SHORT).show();
					break;
			}
		}

		cancelCommand();
	}


	private void startCharge() {
		sendCommand(R.string.msg_starting_charge, "11", this);
		mCarData.car_charge_linevoltage_raw = 0;
		mCarData.car_charge_current_raw = 0;
		mCarData.car_charge_state_s_raw = "starting";
		mCarData.car_charge_state_i_raw = 0x101;
		updateCarInfoView(mCarData);
	}

	private void stopCharge() {
		sendCommand(R.string.msg_stopping_charge, "12", this);
		mCarData.car_charge_linevoltage_raw = 0;
		mCarData.car_charge_current_raw = 0;
		mCarData.car_charge_state_s_raw = "stopping";
		mCarData.car_charge_state_i_raw = 0x115;
		updateCarInfoView(mCarData);
	}

	private void chargerSetting() {
		if (mCarData.car_type.equals("RT"))
			chargerSettingRenaultTwizy();
		else if (mCarData.car_type.equals("VWUP"))
			chargerSettingVWUP();
		else
			chargerSettingDefault();
	}

	private void chargerSettingDefault() {
		View content = LayoutInflater.from(getActivity()).inflate(
				R.layout.dlg_charger, null);
		SwitcherView sw = (SwitcherView) content.findViewById(R.id.sv_state);
		sw.setOnChangeListener(new OnChangeListener<SwitcherView>() {
			@Override
			public void onAction(SwitcherView pData) {
				TextView txtInfo = (TextView) ((View) pData.getParent())
						.findViewById(R.id.txt_info);
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

		new AlertDialog.Builder(getActivity())
				.setTitle(R.string.lb_charger_setting)
				.setView(content)
				.setNegativeButton(R.string.Cancel, null)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface pDlg, int pWhich) {
								AppCompatDialog dlg = (AppCompatDialog) pDlg;
								SwitcherView sw = (SwitcherView) dlg
										.findViewById(R.id.sv_state);
								SlideNumericView snv = (SlideNumericView) dlg
										.findViewById(R.id.snv_amps);

								int ncm = sw.getSelected();
								if (ncm >= 2)
									ncm++;

								int ncl = snv.getValue();

								if (ncm != mCarData.car_charge_mode_i_raw
										&& ncl != mCarData.car_charge_currentlimit_raw) {
									sendCommand(
											R.string.msg_setting_charge_mc,
											String.format("16,%d,%d", ncm, ncl),
											InfoFragment.this);
								} else if (ncm != mCarData.car_charge_mode_i_raw) {
									sendCommand(R.string.msg_setting_charge_m,
											String.format("10,%d", ncm),
											InfoFragment.this);
								} else if (ncl != mCarData.car_charge_currentlimit_raw) {
									sendCommand(R.string.msg_setting_charge_c,
											String.format("15,%d", ncl),
											InfoFragment.this);
								}
							}
						})
				.show();
	}

	// Charger settings for Renault Twizy:
	// 	(charge alert setup)
	private void chargerSettingRenaultTwizy() {

		// create & open dialog:

		View dialogView = LayoutInflater.from(getActivity()).inflate(
				R.layout.dlg_charger_twizy, null);

		// add distance units to range label:
		TextView lbRange = (TextView) dialogView.findViewById(R.id.lb_sufficient_range);
		lbRange.setText(getString(R.string.lb_sufficient_range, mCarData.car_distance_units));

		// set range:
		SlideNumericView snvRange = (SlideNumericView) dialogView.findViewById(R.id.snv_sufficient_range);
		if (snvRange != null) {
			snvRange.init(0, Math.max(mCarData.car_max_idealrange_raw+25, 50), 1);
			snvRange.setValue(mCarData.car_chargelimit_rangelimit_raw);
		}

		// set SOC:
		SlideNumericView snvSOC = (SlideNumericView) dialogView.findViewById(R.id.snv_sufficient_soc);
		if (snvSOC != null) {
			snvSOC.setValue(mCarData.car_chargelimit_soclimit);
		}

		// set charge power limit:
		Spinner spnChargePower = (Spinner) dialogView.findViewById(R.id.spn_charge_power_limit);
		if (spnChargePower != null) {
			spnChargePower.setSelection((int) mCarData.car_charge_currentlimit_raw / 5);
		}

		SwitcherView svChargeMode = (SwitcherView) dialogView.findViewById(R.id.sv_twizy_charge_mode);
		if (svChargeMode != null && mCarData.car_charge_mode_i_raw >= 0 && mCarData.car_charge_mode_i_raw <= 1) {
			svChargeMode.setSelected(mCarData.car_charge_mode_i_raw);
		}

		new AlertDialog.Builder(getActivity())
				.setTitle(R.string.lb_charger_setting_twizy)
				.setView(dialogView)
				.setNegativeButton(R.string.Cancel, null)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface pDlg, int pWhich) {
								AppCompatDialog dlg = (AppCompatDialog) pDlg;

								SlideNumericView snvRange = (SlideNumericView) dlg
										.findViewById(R.id.snv_sufficient_range);
								SlideNumericView snvSOC = (SlideNumericView) dlg
										.findViewById(R.id.snv_sufficient_soc);
								Spinner spnChargePower = (Spinner) dlg
										.findViewById(R.id.spn_charge_power_limit);
								SwitcherView svChargeMode = (SwitcherView) dlg
										.findViewById(R.id.sv_twizy_charge_mode);

								int suffRange = snvRange.getValue();
								int suffSOC = snvSOC.getValue();
								int chgPower = spnChargePower.getSelectedItemPosition();
								int chgMode = svChargeMode.getSelected();

								// SetChargeAlerts (204):
								sendCommand(
										R.string.msg_setting_charge_alerts,
										String.format("204,%d,%d,%d,%d",
												suffRange, suffSOC, chgPower, chgMode),
										InfoFragment.this);
							}
						})
				.show();
	}

	// Charger settings for VW e-Up:
	// 	(charge alert setup)
	private void chargerSettingVWUP() {
		// create & open dialog:
		View dialogView = LayoutInflater.from(getActivity()).inflate(
				R.layout.dlg_charger_vwup, null);

		// set SOC:
		SlideNumericView snvSOC = (SlideNumericView) dialogView.findViewById(R.id.snv_sufficient_soc);
		if (snvSOC != null) {
			snvSOC.setValue(mCarData.car_chargelimit_soclimit);
		}

		// set charge current:
		SlideNumericView snv = (SlideNumericView) dialogView.findViewById(R.id.snv_amps);
		if (snv != null) {
			snv.setValue((int) mCarData.car_charge_currentlimit_raw);
		}

		// set charge power limit:
/*		Spinner spnChargePower = (Spinner) dialogView.findViewById(R.id.spn_charge_power_limit);
		if (spnChargePower != null) {
			spnChargePower.setSelection((int) mCarData.car_charge_currentlimit_raw / 5);
		}*/

		SwitcherView svChargeMode = (SwitcherView) dialogView.findViewById(R.id.sv_twizy_charge_mode);
		if (svChargeMode != null) {
			if (mCarData.car_charge_mode_i_raw == 3)
				svChargeMode.setSelected(1); // hack to map charge mode keys 3 ("range") and 0 to this element
			else
				svChargeMode.setSelected(0);
		}

		new AlertDialog.Builder(getActivity())
				.setTitle(R.string.lb_charger_setting_twizy)
				.setView(dialogView)
				.setNegativeButton(R.string.Cancel, null)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface pDlg, int pWhich) {
								AppCompatDialog dlg = (AppCompatDialog) pDlg;

								SlideNumericView snvSOC = (SlideNumericView) dlg
										.findViewById(R.id.snv_sufficient_soc);
/*								Spinner spnChargePower = (Spinner) dlg
										.findViewById(R.id.spn_charge_power_limit);*/
								SlideNumericView snv = (SlideNumericView) dlg
										.findViewById(R.id.snv_amps);
								SwitcherView svChargeMode = (SwitcherView) dlg
										.findViewById(R.id.sv_twizy_charge_mode);

								int suffSOC = snvSOC.getValue();
//								int chgPower = spnChargePower.getSelectedItemPosition();
								int chgMode = svChargeMode.getSelected();
								int ncl = snv.getValue();

								// SetChargeAlerts (204):
								sendCommand(
										R.string.msg_setting_charge_alerts,
										String.format("204,%d,%d,%d",
												suffSOC, ncl, chgMode),
										InfoFragment.this);
							}
						})
				.show();
	}


	// load ChargeAlerts / ETR data into UI:
	public void updateChargeAlerts() {

		String infoEtr = "";
		TextView textView;
		boolean etrVisible = false;

		int etrFull = mCarData.car_chargefull_minsremaining;
		int suffSOC = mCarData.car_chargelimit_soclimit;
		int etrSuffSOC = mCarData.car_chargelimit_minsremaining_soc;
		int suffRange = mCarData.car_chargelimit_rangelimit_raw;
		int etrSuffRange = mCarData.car_chargelimit_minsremaining_range;

		if (etrSuffRange > 0) {
			String infoEtrRange = getString(R.string.info_etr_suffrange,
					suffRange, mCarData.car_distance_units,
					String.format("%02d:%02d", etrSuffRange / 60, etrSuffRange % 60));
			if (infoEtr.length() > 0)
				infoEtr += "\n";
			infoEtr += infoEtrRange;
		}

		if (etrSuffSOC > 0) {
			String infoEtrSOC = getString(R.string.info_etr_suffsoc,
					suffSOC,
					String.format("%02d:%02d", etrSuffSOC / 60, etrSuffSOC % 60));
			if (infoEtr.length() > 0)
				infoEtr += "\n";
			infoEtr += infoEtrSOC;
		}

		textView = (TextView) findViewById(R.id.tabInfoTextChargeEtrSuff);
		if (textView != null) {
			textView.setText(infoEtr);
			if (!infoEtr.equals("")) {
				etrVisible = true;
				textView.setVisibility(View.VISIBLE);
			} else {
				textView.setVisibility(View.INVISIBLE);
			}
		}


		infoEtr = "";

		if (etrFull > 0) {
			String infoEtrFull = getString(R.string.info_etr_full,
					String.format("%02d:%02d", etrFull / 60, etrFull % 60));
			if (infoEtr.length() > 0)
				infoEtr += "\n";
			infoEtr += infoEtrFull;
		}

		textView = (TextView) findViewById(R.id.tabInfoTextChargeEtrFull);
		if (textView != null) {
			textView.setText(infoEtr);
			if (!infoEtr.equals("")) {
				etrVisible = true;
				textView.setVisibility(View.VISIBLE);
			} else {
				textView.setVisibility(View.INVISIBLE);
			}
		}

		// display background if any ETR visible:
		ImageView bgImg = (ImageView) findViewById(R.id.tabInfoImageChargeEtr);
		if (bgImg != null) {
			bgImg.setVisibility(etrVisible ? View.VISIBLE : View.INVISIBLE);
		}

	}


	// This updates the part of the view with times shown.
	// It is called by a periodic timer so it gets updated every few seconds.
	public void updateLastUpdatedView(CarData pCarData) {
		// Quick exit if the car data is not there yet...
		if ((pCarData == null) || (pCarData.car_lastupdated == null))
			return;

		// Let's update the Info tab view...

		// First the last updated section...
		TextView tv = (TextView) findViewById(R.id.txt_last_updated);
		long now = System.currentTimeMillis();
		long seconds = (now - pCarData.car_lastupdated.getTime()) / 1000;
		long minutes = (seconds) / 60;
		long hours = minutes / 60;
		long days = minutes / (60 * 24);
		Log.d(TAG, "Last updated: " + seconds + " secs ago");

		if (pCarData.car_lastupdated == null) {
			tv.setText("");
			tv.setTextColor(0xFFFFFFFF);
		} else if (minutes == 0) {
			tv.setText(getText(R.string.live));
			tv.setTextColor(0xFFFFFFFF);
		} else if (minutes == 1) {
			tv.setText(getText(R.string.min1));
			tv.setTextColor(0xFFFFFFFF);
		} else if (days > 1) {
			tv.setText(String.format(getText(R.string.ndays).toString(), days));
			tv.setTextColor(0xFFFF0000);
		} else if (hours > 1) {
			tv.setText(String
					.format(getText(R.string.nhours).toString(), hours));
			tv.setTextColor(0xFFFF0000);
		} else if (minutes > 60) {
			tv.setText(String.format(getText(R.string.nmins).toString(),
					minutes));
			tv.setTextColor(0xFFFF0000);
		} else {
			tv.setText(String.format(getText(R.string.nmins).toString(),
					minutes));
			tv.setTextColor(0xFFFFFFFF);
		}

		// Then the parking timer...
		tv = (TextView) findViewById(R.id.txt_parked_time);
		if ((!pCarData.car_started) && (pCarData.car_parked_time != null)) {
			// Car is parked
			tv.setVisibility(View.VISIBLE);
			seconds = (now - pCarData.car_parked_time.getTime()) / 1000;
			minutes = (seconds) / 60;
			hours = minutes / 60;
			days = minutes / (60 * 24);

			if (minutes == 0)
				tv.setText(getText(R.string.justnow));
			else if (minutes == 1)
				tv.setText("1 min");
			else if (days > 1)
				tv.setText(String.format(getText(R.string.ndays).toString(),
						days));
			else if (hours > 1)
				tv.setText(String.format(getText(R.string.nhours).toString(),
						hours));
			else if (minutes > 60)
				tv.setText(String.format(getText(R.string.nmins).toString(),
						minutes));
			else
				tv.setText(String.format(getText(R.string.nmins).toString(),
						minutes));
		} else {
			tv.setVisibility(View.INVISIBLE);
		}

		// The signal strength indicator
		ImageView iv = (ImageView) findViewById(R.id.img_signal_rssi);
		iv.setImageResource(Ui.getDrawableIdentifier(getActivity(),
				"signal_strength_" + pCarData.car_gsm_bars));
	}

	// This updates the main informational part of the view.
	// It is called when the server gets new data.
	public void updateCarInfoView(CarData pCarData) {

		TextView tv = (TextView) findViewById(R.id.txt_title);
		tv.setText(pCarData.sel_vehicle_label);

		int carPos = mCarsStorage.getStoredCars().indexOf(pCarData);
		if (carPos != mCarSelectPos) {
			Log.d(TAG, "updateCarInfoView: id=" + pCarData.sel_vehicleid + " pos=" + carPos);
			mCarSelectPos = carPos;
			mCarSelect.setSelection(mCarSelectPos);
		}

		tv = (TextView) findViewById(R.id.tabInfoTextSOC);
		tv.setText(pCarData.car_soc);

		TextView cmtv = (TextView) findViewById(R.id.tabInfoTextChargeMode);
		ImageView coiv = (ImageView) findViewById(R.id.tabInfoImageBatteryChargingOverlay);
		ReversedSeekBar bar = (ReversedSeekBar) findViewById(R.id.tabInfoSliderChargerControl);
		TextView tvl = (TextView) findViewById(R.id.tabInfoTextChargeStatusLeft);
		TextView tvr = (TextView) findViewById(R.id.tabInfoTextChargeStatusRight);
		TextView tvf = (TextView) findViewById(R.id.tabInfoTextChargeStatus);
		TextView tvPowerInput = (TextView) findViewById(R.id.tabInfoTextChargePowerInput);
		TextView tvPowerLoss = (TextView) findViewById(R.id.tabInfoTextChargePowerLoss);

		if ((!pCarData.car_chargeport_open)
				|| (pCarData.car_charge_substate_i_raw == 0x07)) {

			// Charge port is closed or car is not plugged in

			findViewById(R.id.tabInfoImageCharger).setVisibility(View.INVISIBLE);
			bar.setVisibility(View.INVISIBLE);
			cmtv.setVisibility(View.INVISIBLE);
			coiv.setVisibility(View.INVISIBLE);
			tvl.setVisibility(View.INVISIBLE);
			tvr.setVisibility(View.INVISIBLE);
			tvf.setVisibility(View.INVISIBLE);
			tvPowerInput.setVisibility(View.INVISIBLE);
			tvPowerLoss.setVisibility(View.INVISIBLE);

		} else {
			// Car is plugged in

			String cmst;
			if (!pCarData.car_battery_rangespeed.equals("")) {
				cmst = String.format("%s ≤%s ⏲%s ▾%.1fkWh",
						pCarData.car_charge_mode.toUpperCase(),
						pCarData.car_charge_currentlimit,
						pCarData.car_battery_rangespeed,
						pCarData.car_charge_kwhconsumed);
			} else {
				cmst = String.format("%s ≤%s ▾%.1fkWh",
						pCarData.car_charge_mode.toUpperCase(),
						pCarData.car_charge_currentlimit,
						pCarData.car_charge_kwhconsumed);
			}
			cmtv.setText(cmst);
			cmtv.setVisibility(View.VISIBLE);

			if (mCarData.car_type.equals("RT")) {

				// Renault Twizy: no charge control

				findViewById(R.id.tabInfoImageCharger).setVisibility(View.VISIBLE);

				bar.setVisibility(View.INVISIBLE);
				tvl.setVisibility(View.INVISIBLE);
				tvr.setVisibility(View.INVISIBLE);
				tvPowerInput.setVisibility(View.INVISIBLE);
				tvPowerLoss.setVisibility(View.INVISIBLE);

				int chargeStateInfo = 0;
				switch (pCarData.car_charge_state_i_raw) {
					case 1: // Charging
						chargeStateInfo = R.string.state_charging;
						break;
					case 2: // Topping off
						chargeStateInfo = R.string.state_topping_off;
						break;
					case 4: // Done
						chargeStateInfo = R.string.state_done;
						break;
					case 21: // Stopped
						chargeStateInfo = R.string.state_stopped;
						break;
				}

				if (chargeStateInfo != 0) {
					tvf.setText(String.format(
							getText(chargeStateInfo).toString(),
							pCarData.car_charge_linevoltage,
							pCarData.car_charge_current));
					tvf.setVisibility(View.VISIBLE);
				}

				coiv.setVisibility(View.VISIBLE);

			} else {

				// Standard car:

				findViewById(R.id.tabInfoImageCharger).setVisibility(View.VISIBLE);
				bar.setVisibility(View.VISIBLE);
				tvl.setVisibility(View.VISIBLE);
				tvr.setVisibility(View.VISIBLE);

				switch (pCarData.car_charge_state_i_raw) {
					case 0x04: // Done
					case 0x115: // Stopping
					case 0x15: // Stopped
					case 0x16: // Stopped
					case 0x17: // Stopped
					case 0x18: // Stopped
					case 0x19: // Stopped
						// Slider on the left, message is "Slide to charge"
						bar.setProgress(100);
						tvl.setText(null);
						tvr.setText(getText(R.string.slidetocharge));
						coiv.setVisibility(View.INVISIBLE);
						tvPowerInput.setVisibility(View.INVISIBLE);
						tvPowerLoss.setVisibility(View.INVISIBLE);
						break;
					case 0x0e: // Wait for schedule charge
						// Slider on the left, message is "Timed Charge"
						bar.setProgress(100);
						tvl.setText(null);
						tvr.setText(getText(R.string.timedcharge));
						coiv.setVisibility(View.INVISIBLE);
						tvPowerInput.setVisibility(View.INVISIBLE);
						tvPowerLoss.setVisibility(View.INVISIBLE);
						break;
					case 0x01: // Charging
					case 0x101: // Starting
					case 0x02: // Top-off
					case 0x0d: // Preparing to charge
					case 0x0f: // Heating
						// Slider on the right, message blank
						bar.setProgress(0);
						tvl.setText(String.format(
								getText(R.string.state_charging).toString(),
								pCarData.car_charge_linevoltage,
								pCarData.car_charge_current));
						tvr.setText("");
						coiv.setVisibility(View.VISIBLE);
						if (pCarData.car_charge_power_input_kw_raw > 0) {
							tvPowerInput.setText(pCarData.car_charge_power_input_kw);
							tvPowerInput.setVisibility(View.VISIBLE);
						} else {
							tvPowerInput.setVisibility(View.INVISIBLE);
						}
						if (pCarData.car_charge_power_loss_kw_raw > 0) {
							tvPowerLoss.setText(pCarData.car_charge_power_loss_kw);
							tvPowerLoss.setVisibility(View.VISIBLE);
						} else {
							tvPowerLoss.setVisibility(View.INVISIBLE);
						}
						break;
					default:
						// Slider on the right, message blank
						bar.setProgress(100);
						tvl.setText(null);
						tvr.setText(null);
						coiv.setVisibility(View.INVISIBLE);
						tvPowerInput.setVisibility(View.INVISIBLE);
						tvPowerLoss.setVisibility(View.INVISIBLE);
						break;
				}
			}
		}

		tv = (TextView) findViewById(R.id.tabInfoTextIdealRange);
		tv.setText(pCarData.car_range_ideal);
		tv = (TextView) findViewById(R.id.tabInfoTextEstimatedRange);
		tv.setText(pCarData.car_range_estimated);

		int maxWeight = ((TextView) findViewById(R.id.tabInfoTextSOC)).getLayoutParams().width;
		int realWeight = Math
				.round((maxWeight * pCarData.car_soc_raw / 100) * 1.1f);
		View v = findViewById(R.id.tabInfoImageBatteryOverlay);

		v.getLayoutParams().width = Math.min(maxWeight, realWeight);
		v.requestLayout();

		ImageView iv = (ImageView) findViewById(R.id.img_signal_rssi);
		iv.setImageResource(Ui.getDrawableIdentifier(getActivity(),
				"signal_strength_" + pCarData.car_gsm_bars));
	}

}
