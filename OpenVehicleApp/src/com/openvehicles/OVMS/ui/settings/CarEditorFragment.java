package com.openvehicles.OVMS.ui.settings;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.openvehicles.OVMS.BaseApp;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.ui.validators.PasswdValidator;
import com.openvehicles.OVMS.ui.validators.StringValidator;
import com.openvehicles.OVMS.ui.validators.ValidationException;

public class CarEditorFragment extends SherlockFragment {
	private CarData mCarData;
	private int mEditPosition;
	private int mImagePosition;
	private ViewPager mImgViewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_careditor, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mEditPosition = getArguments().getInt("position", -1);
		if (mEditPosition >= 0) {
			mCarData = BaseApp.getStoredCars().get(mEditPosition);
		}
		
		mImgViewPager = (ViewPager) getView().findViewById(R.id.vp_car);
		mImgViewPager.setAdapter(new CarPagerAdapter());
		mImgViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				mImagePosition = position;
			}
		});
		
		setHasOptionsMenu(true);
		if (mCarData != null) approveCarData();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(mCarData != null && BaseApp.getStoredCars().size() > 1
			&& BaseApp.getSelectedCarData() != mCarData ? R.menu.save_delete : R.menu.save, menu);
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
					BaseApp.getStoredCars().remove(mEditPosition);
					BaseApp.saveStoredCars();
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
						
						List<CarData> mAllCars = BaseApp.getStoredCars();
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
			mCarData.sel_vehicle_image = sAvailableColors[mImagePosition];
		} catch (ValidationException e) {
			Log.e("Validation", e.getMessage(), e);
			return;
		}
		
		if (mEditPosition < 0) {
			BaseApp.getStoredCars().add(mCarData);
		}
		
		BaseApp.saveStoredCars();
		getActivity().finish();
	}
	
	
	private void approveCarData() {
		View rootView = getView();
		Ui.setValue(rootView, R.id.txt_vehicle_id, mCarData.sel_vehicleid);
		Ui.setValue(rootView, R.id.txt_vehicle_label, mCarData.sel_vehicle_label);
		Ui.setValue(rootView, R.id.txt_server_passwd, mCarData.sel_server_password);
		Ui.setValue(rootView, R.id.txt_module_passwd, mCarData.sel_module_password);

		int index = -1;
		for (String imgRes: sAvailableColors) {
			index++;
			if (imgRes.equals(mCarData.sel_vehicle_image)) break;
		}
		if (index > 0) mImgViewPager.setCurrentItem(index);
	}

	private class CarPagerAdapter extends FragmentPagerAdapter {
		public CarPagerAdapter() {
			super(getFragmentManager());
		}

		@Override
		public Fragment getItem(int pPosition) {
			return new CarImageFragment(sAvailableColors[pPosition]);
		}

		@Override
		public int getCount() {
			return sAvailableColors.length;
		}
	}
	
	@SuppressLint("ValidFragment")
	private static class CarImageFragment extends SherlockFragment {
		public final String car_img_resname;
		
		public CarImageFragment(String pCarResName) {
			car_img_resname = pCarResName;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			ImageView iv = new ImageView(container.getContext());
			iv.setScaleType(ImageView.ScaleType.FIT_START);
			iv.setImageResource(Ui.getDrawableIdentifier(container.getContext(), car_img_resname));
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
		"car_holdenvolt_whitediamond"
	 };		
}
