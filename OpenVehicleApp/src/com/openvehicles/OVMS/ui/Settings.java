package com.openvehicles.OVMS.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;

public class Settings extends Fragment {
	private static final String TAG = "Settings(OCM)";

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
		public void clearcache();
	}

	private void setUpClusteringViews() {
		CheckBox clusterCheckbox = (CheckBox) view
				.findViewById(R.id.checkbox_cluster);
		final SeekBar clusterSizeSeekbar = (SeekBar) view
				.findViewById(R.id.seekbar_cluster_size);
		Spinner maxResultsSpinner = (Spinner) view
				.findViewById(R.id.ocm_maxresults);

		if (appPrefes.getData("check").equals("false")) {
			clusterCheckbox.setChecked(false);
		}
		try {
			clusterSizeSeekbar.setProgress(Integer.parseInt(appPrefes
					.getData("progress")));
		} catch (Exception e) {
			// TODO: handle exception
		}

		// set maxresults spinner to current value:
		try {
			String ocm_maxresults_value = appPrefes.getData("maxresults");
			String[] ocm_maxresults_options = getResources().getStringArray(R.array.ocm_options_maxresults);
			int ocm_maxresults_index = 0;
			for (int i=0; i<ocm_maxresults_options.length; i++) {
				if (ocm_maxresults_options[i].equals(ocm_maxresults_value)) {
					ocm_maxresults_index = i;
					break;
				}
			}
			maxResultsSpinner.setSelection(ocm_maxresults_index);
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

		maxResultsSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
						String selected = adapterView.getItemAtPosition(i).toString();
						if (!appPrefes.getData("maxresults").equals(selected)) {
							appPrefes.SaveData("maxresults", "" + selected);
							FragMap.updateclust.clearcache();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> adapterView) {

					}
				});

	}
}
