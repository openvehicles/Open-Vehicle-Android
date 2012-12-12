package com.openvehicles.OVMS.ui.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.ui.utils.Ui;

public class CarEditorFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_careditor, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ViewPager viewPager = (ViewPager) getView().findViewById(R.id.vp_car);
		viewPager.setAdapter(new CarPagerAdapter());
	}
	
	private class CarPagerAdapter extends FragmentPagerAdapter {
		public CarPagerAdapter() {
			super(getFragmentManager());
		}

		@Override
		public Fragment getItem(int pPosition) {
			return new CarFragment(sAvailableColors[pPosition]);
		}

		@Override
		public int getCount() {
			return sAvailableColors.length;
		}
	}
	
	private static class CarFragment extends SherlockFragment {
		private String mCarResName;
		
		public CarFragment(String pCarResName) {
			mCarResName = pCarResName;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			ImageView iv = new ImageView(container.getContext());
			iv.setScaleType(ImageView.ScaleType.FIT_START);
			iv.setImageResource(Ui.getDrawableIdentifier(container.getContext(), mCarResName));
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
