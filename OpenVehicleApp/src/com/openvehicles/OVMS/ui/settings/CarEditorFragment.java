package com.openvehicles.OVMS.ui.settings;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.BaseFragmentActivity;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.ui.validators.PasswdValidator;
import com.openvehicles.OVMS.ui.validators.StringValidator;
import com.openvehicles.OVMS.ui.validators.ValidationException;
import com.openvehicles.OVMS.utils.CarsStorage;

public class CarEditorFragment extends SherlockFragment {
	private CarData mCarData;
	private boolean isSelectedCar;
	private int mEditPosition;
	private Gallery mGalleryCar; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_careditor, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getSherlockActivity().getSupportActionBar().setIcon(R.drawable.ic_action_edit);		
		
		mEditPosition = getArguments().getInt("position", -1);
		if (mEditPosition >= 0) {
			mCarData = CarsStorage.get().getStoredCars().get(mEditPosition);
			
			CarData selectedCarData = CarsStorage.get().getSelectedCarData();
			isSelectedCar = selectedCarData != null && mCarData != null 
				&& selectedCarData.sel_vehicleid.equals(mCarData.sel_vehicleid); 
		}
		
		mGalleryCar = (Gallery) getView().findViewById(R.id.ga_car);
		mGalleryCar.setAdapter(new CarImgAdapter());
		
		setHasOptionsMenu(true);
		if (mCarData != null) approveCarData();
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
					CarsStorage.get().getStoredCars().remove(mEditPosition);
					CarsStorage.get().saveStoredCars();
					getActivity().finish();
				}
			})
			.show();
	}
	
	private void save() {
		if (mCarData == null) {
			mCarData = new CarData();
		}
		View rootView = getView();
		
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
			mCarData.sel_server_password = Ui.getValidValue(rootView, R.id.txt_server_passwd, new PasswdValidator(4, 16)); 
			mCarData.sel_module_password = Ui.getValidValue(rootView, R.id.txt_module_passwd, new PasswdValidator(4, 16));
			mCarData.sel_server = Ui.getValidValue(rootView, R.id.txt_server_address, new StringValidator());
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
	
	
	private void approveCarData() {
		View rootView = getView();
		getSherlockActivity().setTitle(mCarData.sel_vehicleid);
		Ui.setValue(rootView, R.id.txt_vehicle_id, mCarData.sel_vehicleid);
		Ui.setValue(rootView, R.id.txt_vehicle_label, mCarData.sel_vehicle_label);
		Ui.setValue(rootView, R.id.txt_server_passwd, mCarData.sel_server_password);
		Ui.setValue(rootView, R.id.txt_module_passwd, mCarData.sel_module_password);
		Ui.setValue(rootView, R.id.txt_server_address, mCarData.sel_server);

		AppPrefes appPrefes = new AppPrefes(getActivity(), "ovms");
		Log.d("CarEditorFragment", "sel_vehicle_label=" + mCarData.sel_vehicle_label);
		appPrefes.SaveData("sel_vehicle_label", mCarData.sel_vehicle_label);
		int index = -1;
		for (String imgRes: sAvailableColors) {
			index++;
			if (imgRes.equals(mCarData.sel_vehicle_image)) break;
		}
		if (index > 0) mGalleryCar.setSelection(index);
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
		"car_twizy_diamondblackwithivygreen",
		"car_twizy_snowwhiteandflameorange",
		"car_twizy_snowwhiteandurbanblue",
		"car_twizy_snowwhitewithblack",
		"car_thinkcity_brightred",
		"car_thinkcity_citrusyellow",
		"car_thinkcity_classicblack",
		"car_thinkcity_skyblue"
	 };		
}
