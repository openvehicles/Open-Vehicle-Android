package com.openvehicles.OVMS.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;

public class Settings extends Fragment {
	View view;
	AppPrefes appPrefes;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.setting, null);
		appPrefes = new AppPrefes(getActivity(), "ovms");
		setUpClusteringViews();
		return view;
	}

	public interface Updateclust {
		public void updateclust(int clusterSizeIndex, boolean enabled);
	}

	private void setUpClusteringViews() {
		CheckBox clusterCheckbox = (CheckBox) view
				.findViewById(R.id.checkbox_cluster);
		final SeekBar clusterSizeSeekbar = (SeekBar) view
				.findViewById(R.id.seekbar_cluster_size);
		if (appPrefes.getData("check").equals("false")) {
			clusterCheckbox.setChecked(false);
		}
		try {
			clusterSizeSeekbar.setProgress(Integer.parseInt(appPrefes
					.getData("progress")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		clusterCheckbox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						clusterSizeSeekbar.setEnabled(isChecked);
						appPrefes.SaveData("check", "" + isChecked);
						FragMap.updateclust.updateclust(
								clusterSizeSeekbar.getProgress(), isChecked);
					}
				});
		clusterSizeSeekbar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						appPrefes.SaveData("progress", "" + progress);
						FragMap.updateclust.updateclust(progress, true);
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				});
	}
}
