package com.openvehicles.OVMS.ui.settings;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.BaseFragmentActivity;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.ui.validators.PasswdValidator;
import com.openvehicles.OVMS.ui.validators.StringValidator;
import com.openvehicles.OVMS.ui.validators.ValidationException;
import com.openvehicles.OVMS.utils.CarsStorage;

public class CarEditorFragment extends BaseFragment {

	private static final String TAG = "CarEditorFragment";

	private CarData mCarData;
	private boolean isSelectedCar;
	private int mEditPosition;
	private Gallery mGalleryCar;
	private Spinner mSelectServer;
	private int mSelectServerPosition;
	private String[] mServers, mGcmSenders;
	private EditText mServer, mGcmSender;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_careditor, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getCompatActivity().getSupportActionBar().setIcon(R.drawable.ic_action_edit);
		
		mEditPosition = getArguments().getInt("position", -1);
		if (mEditPosition >= 0) {
			try {
				mCarData = CarsStorage.get().getStoredCars().get(mEditPosition);
				CarData selectedCarData = CarsStorage.get().getSelectedCarData();
				isSelectedCar = selectedCarData != null && mCarData != null
						&& selectedCarData.sel_vehicleid.equals(mCarData.sel_vehicleid);
			} catch(Exception e) {
				mCarData = null;
				mEditPosition = -1;
				isSelectedCar = false;
			}
		}

		mSelectServer = (Spinner) getView().findViewById(R.id.select_server);
		mSelectServerPosition = -1;
		mServers = getResources().getStringArray(R.array.select_server_options);
		mGcmSenders = getResources().getStringArray(R.array.select_server_gcm_senders);
		mServer = (EditText) getView().findViewById(R.id.txt_server_address);
		mGcmSender = (EditText) getView().findViewById(R.id.txt_gcm_senderid);

		mSelectServer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				setSelectedServer(position, true);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// nop
			}
		});

		mGalleryCar = (Gallery) getView().findViewById(R.id.ga_car);
		mGalleryCar.setAdapter(new CarImgAdapter());
		
		setHasOptionsMenu(true);

		load();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.control_save_delete, menu);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		Log.d("CarEditorFragment", "onPrepareOptionsMenu edit car: " + (CarsStorage.get().getStoredCars().size() > 1));
		menu.findItem(R.id.mi_delete).setVisible(mCarData != null && CarsStorage.get().getStoredCars().size() > 1);
		menu.findItem(R.id.mi_control).setVisible(isSelectedCar);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mi_save:
			save();
			return true;
		case R.id.mi_delete:
			delete();
			return true;
		case R.id.mi_control:
			Bundle args = new Bundle();
			args.putInt("position", mEditPosition);
			BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
			activity.setNextFragment(ControlFragment.class, args);
			return true;
		default:
			return false;
		}
	}

	private void delete() {
		new AlertDialog.Builder(getActivity())
			.setMessage(R.string.msg_delete_this_car)
			.setNegativeButton(R.string.No, null)
			.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ArrayList<CarData> carList = CarsStorage.get().getStoredCars();
					// remove car:
					carList.remove(mEditPosition);
					CarsStorage.get().saveStoredCars();
					CarData selCar;
					// select closest remaining car:
					if (mEditPosition < carList.size())
						selCar = carList.get(mEditPosition);
					else
						selCar = carList.get(carList.size()-1);
					changeCar(selCar);
					// back to previous fragment:
					getActivity().finish();
				}
			})
			.show();
	}
	
	private void save() {
		View rootView = getView();

		if (mCarData == null) {
			mCarData = new CarData();
		}

		try {
			mCarData.sel_vehicleid = Ui.getValidValue(rootView, R.id.txt_vehicle_id, 
				new StringValidator() {
					@Override
					public boolean valid(EditText pEditText, Object pValue) {
						if (!super.valid(pEditText, pValue)) return false;
						setErrorMessage(pEditText.getContext().getString(R.string.msg_invalid_id_already_registered, pValue));
						
						List<CarData> mAllCars = CarsStorage.get().getStoredCars();
						int count = mAllCars.size();
						for (int i=0; i<count; i++) {
							if (mAllCars.get(i).sel_vehicleid.equals(pValue) && i != mEditPosition) {
								return false;
							}
						}
						return true;
					}
				});
			mCarData.sel_vehicle_label = Ui.getValidValue(rootView, R.id.txt_vehicle_label, new StringValidator()); 
			mCarData.sel_server_password = Ui.getValidValue(rootView, R.id.txt_server_passwd, new PasswdValidator(4, 255));
			mCarData.sel_module_password = Ui.getValidValue(rootView, R.id.txt_module_passwd, new PasswdValidator(4, 255));
			mCarData.sel_server = Ui.getValidValue(rootView, R.id.txt_server_address, new StringValidator());
			mCarData.sel_gcm_senderid = Ui.getValue(rootView, R.id.txt_gcm_senderid);
			mCarData.sel_vehicle_image = sAvailableColors[mGalleryCar.getSelectedItemPosition()];

		} catch (ValidationException e) {
			Log.e("Validation", e.getMessage(), e);
			return;
		}
		
		if (mEditPosition < 0) {
			CarsStorage.get().getStoredCars().add(mCarData);
		}
		
		CarsStorage.get().saveStoredCars();
		
		getActivity().finish();
	}
	
	
	private void load() {
		View rootView = getView();

		if (mCarData == null) {
			// edit new car:
			setSelectedServer(0, false);
		}
		else {
			// edit existing car:
			getCompatActivity().setTitle(mCarData.sel_vehicleid);
			Ui.setValue(rootView, R.id.txt_vehicle_id, mCarData.sel_vehicleid);
			Ui.setValue(rootView, R.id.txt_vehicle_label, mCarData.sel_vehicle_label);
			Ui.setValue(rootView, R.id.txt_server_passwd, mCarData.sel_server_password);
			Ui.setValue(rootView, R.id.txt_module_passwd, mCarData.sel_module_password);

			// set server:
			int position = mServers.length - 1;
			for (int i = 0; i < mServers.length; i++) {
				if (mServers[i].equals(mCarData.sel_server) && mGcmSenders[i].equals(mCarData.sel_gcm_senderid)) {
					position = i;
					break;
				}
			}
			Log.d(TAG, "load: server=" + mCarData.sel_server + " → position=" + position);
			setSelectedServer(position, false);

			// set car image:
			int index = -1;
			for (String imgRes : sAvailableColors) {
				index++;
				if (imgRes.equals(mCarData.sel_vehicle_image)) break;
			}
			if (index >= 0) mGalleryCar.setSelection(index);

			// save selected vehicle label:
			AppPrefes appPrefes = new AppPrefes(getActivity(), "ovms");
			Log.d(TAG, "load: sel_vehicle_label=" + mCarData.sel_vehicle_label);
			appPrefes.SaveData("sel_vehicle_label", mCarData.sel_vehicle_label);
		}
	}

	private void setSelectedServer(int position, boolean userAction) {
		if (position != mSelectServerPosition) {
			mSelectServerPosition = position;
			Log.d(TAG, "setSelectedServer: new position=" + position
					+ " → server=" + mServers[position]);
			if (position < mServers.length-1) {
				mServer.setText(mServers[position]);
				mGcmSender.setText(mGcmSenders[position]);
				mServer.setVisibility(View.GONE);
				mGcmSender.setVisibility(View.GONE);
			} else {
				if (userAction) {
					mServer.setText("");
					mGcmSender.setText("");
					mServer.requestFocus();
				} else {
					mServer.setText((mCarData != null) ? mCarData.sel_server : "");
					mGcmSender.setText((mCarData != null) ? mCarData.sel_gcm_senderid : "");
				}
				mServer.setVisibility(View.VISIBLE);
				mGcmSender.setVisibility(View.VISIBLE);
			}
			if (!userAction) {
				mSelectServer.setSelection(position);
			}
		}
	}

	private static class CarImgAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return sAvailableColors.length;
		}

		@Override
		public Object getItem(int position) {
			return sAvailableColors[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = convertView != null ? (ImageView)convertView : new ImageView(parent.getContext());
			iv.setScaleType(ImageView.ScaleType.FIT_START);
			iv.setAdjustViewBounds(true);
			iv.setImageResource(Ui.getDrawableIdentifier(parent.getContext(), sAvailableColors[position]));
			return iv;
		}
	}
	
	private static final String[] sAvailableColors = {
			"car_roadster_arcticwhite",
			"car_roadster_brilliantyellow",
			"car_roadster_electricblue",
			"car_roadster_fushionred",
			"car_roadster_glacierblue",
			"car_roadster_jetblack",
			"car_roadster_lightninggreen",
			"car_roadster_obsidianblack",
			"car_roadster_racinggreen",
			"car_roadster_radiantred",
			"car_roadster_sterlingsilver",
			"car_roadster_thundergray",
			"car_roadster_twilightblue",
			"car_roadster_veryorange",
			"car_twizy_diamondblackwithivygreen",
			"car_twizy_snowwhiteandflameorange",
			"car_twizy_snowwhiteandurbanblue",
			"car_twizy_snowwhitewithblack",
			"car_kiasoul_carribianblueclearwhite",
			"car_kiasoul_cherryblackinfernored",
			"car_kiasoul_clearwhite",
			"car_kiasoul_pearlwhiteelectronicblue",
			"car_kiasoul_titaniumsilver",
			"car_kianiro_black",
			"car_kianiro_blue",
			"car_kianiro_grey",
			"car_kianiro_silver",
			"car_kianiro_snowwhite",
			"car_kona_grey",
			"car_kona_white",
			"car_kona_red",
			"car_kona_blue",
			"car_kona_yellow",
			"car_ioniq_polarwhite",
			"car_leaf_coulisred",
			"car_leaf_deepblue",
			"car_leaf_planetblue",
			"car_leaf_forgedbronze",
			"car_leaf_gunmetallic",
			"car_leaf_pearlwhite",
			"car_leaf_superblack",
			"car_leaf2_gunmetallic",
			"car_leaf2_jadefrostmetallic",
			"car_leaf2_pearlwhite",
			"car_leaf2_superblack",
			"car_leaf2_vividblue",
			"car_env200_white",
			"car_smart_ed_white",
			"car_smart_eq_red",
			"car_smart_eq_black",
			"car_smart_eq_white",
			"car_zoe_black",
			"car_vwup_black",
			"car_vwup_blue",
			"car_vwup_red",
			"car_vwup_silver",
			"car_vwup_white",
			"car_vwup_yellow",
			"car_zoe_brown",
			"car_zoe_grey",
			"car_zoe_hellblau",
			"car_zoe_lila",
			"car_zoe_red",
			"car_zoe_white",
			"car_zoe_ytriumgrau",
			"car_mgzs_white",
			"car_mgzs_blue",
			"car_mgzs_lightblue",
			"car_mgzs_red",
			"car_mgzs_black",
			"car_edeliver3_white",
			"car_ampera_black",
			"car_ampera_crystalred",
			"car_ampera_cybergray",
			"car_ampera_lithiumwhite",
			"car_ampera_powerblue",
			"car_ampera_silvertopas",
			"car_ampera_sovereignsilver",
			"car_ampera_summitwhite",
			"car_holdenvolt_black",
			"car_holdenvolt_crystalclaret",
			"car_holdenvolt_silvernitrate",
			"car_holdenvolt_urbanfresh",
			"car_holdenvolt_whitediamond",
			"car_imiev_black",
			"car_imiev_blue",
			"car_imiev_cherrybrown",
			"car_imiev_coolsilver",
			"car_imiev_white",
			"car_imiev_whitered",
			"car_thinkcity_brightred",
			"car_thinkcity_citrusyellow",
			"car_thinkcity_classicblack",
			"car_thinkcity_skyblue",
			"car_kangoo_white",
			"car_kangoo_black",
			"car_kangoo_grey",
			"car_kangoo_red",
			"car_kangoo_blue",
			"car_kangoo_brown"
	};
}
