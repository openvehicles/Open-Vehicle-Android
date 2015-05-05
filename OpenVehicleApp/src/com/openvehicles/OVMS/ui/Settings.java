package com.openvehicles.OVMS.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView.OnEditorActionListener;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.utils.ConnectionList;
import com.openvehicles.OVMS.utils.MyElement;
import com.openvehicles.OVMS.utils.MyException;

public class Settings extends Fragment implements ConnectionList.Con {
	private static final String TAG = "Settings(OCM)";

	View view;
	AppPrefes appPrefes;
	ConnectionList connectionList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.setting, null);
		appPrefes = new AppPrefes(getActivity(), "ovms");

		String url = "http://api.openchargemap.io/v2/referencedata/";
		connectionList = new ConnectionList(getActivity(), this, url,false);

		setUpClusteringViews();
		setUpLoggedPathViews();
		SetEnabled();

		return view;
	}

	public interface UpdateMap {
		public void updateClustering(int clusterSizeIndex, boolean enabled);
		public void clearCache();
		public void updateFilter(String connectionList);
	}

	@Override
	public void connections(String al, String name) {
		Log.d(TAG, "connections: al=" + al);
		FragMap.updateMap.updateFilter(al);
	}

	private void setUpClusteringViews() {
		CheckBox clusterCheckbox = (CheckBox) view
				.findViewById(R.id.checkbox_cluster);
		final SeekBar clusterSizeSeekbar = (SeekBar) view
				.findViewById(R.id.seekbar_cluster_size);
		Spinner maxResultsSpinner = (Spinner) view
				.findViewById(R.id.ocm_maxresults);
		Button btnConnections = (Button) view
				.findViewById(R.id.btn_connections);


		if (appPrefes.getData(BaseApi.mKeyChargingStations).equals("false")) {
			clusterCheckbox.setChecked(false);
		}
		else clusterCheckbox.setChecked(true);
		try {
			clusterSizeSeekbar.setProgress(Integer.parseInt(appPrefes
					.getData(BaseApi.mKeyProgress)));
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
						appPrefes.SaveData(BaseApi.mKeyChargingStations, "" + isChecked);
						SetEnabled();
						FragMap.updateMap.updateClustering(
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
						appPrefes.SaveData(BaseApi.mKeyProgress, "" + progress);
						FragMap.updateMap.updateClustering(progress, true);
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
							FragMap.updateMap.clearCache();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> adapterView) {

					}
				});

		btnConnections
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						connectionList.sublist();
					}
				});

	}
	public interface UpdateclustLoggedPath {
		public void updateclustLoggedPath(int clusterSizeIndex, boolean enabled);
	}
	
	private static String mstrAveragedPointsCircleRadiusDefault = "60";
	
	static double GetAveragedPointsCircleRadius(AppPrefes appPrefes)
	{
		String strAveragedPointsCircleRadius = appPrefes.getData(BaseApi.mKeyAveragedPointsCircleRadius);
		if(strAveragedPointsCircleRadius.isEmpty())
			strAveragedPointsCircleRadius = mstrAveragedPointsCircleRadiusDefault;//60
		return Double.parseDouble(strAveragedPointsCircleRadius);
	}
	
	private boolean mboOnEditorAction = false;
	
	private void SetEnabled()
	{
		boolean isChecked;
		
		//CHARGING STATIONS
		isChecked = !appPrefes.getData(BaseApi.mKeyChargingStations).equals("false");
		((SeekBar)view.findViewById(R.id.seekbar_cluster_size)).setEnabled(isChecked);
		
		//Logged path
		isChecked = appPrefes.IsTrue(BaseApi.mKeyTurnLoggedPath, false);
		((SeekBar)view.findViewById(R.id.seekbar_cluster_size_logged_path)).setEnabled(isChecked);
		((EditText)view.findViewById(R.id.editText_logged_path)).setEnabled(isChecked);
	}
	
	private void setUpLoggedPathViews() {
		CheckBox clusterCheckbox = (CheckBox) view.findViewById(R.id.checkbox_cluster_logged_path);
		clusterCheckbox.setChecked(appPrefes.IsTrue(BaseApi.mKeyTurnLoggedPath, false));
		final SeekBar clusterSizeSeekbar = (SeekBar) view.findViewById(R.id.seekbar_cluster_size_logged_path);
		final EditText editTextAveragedPointsCircleRadius = (EditText) view.findViewById(R.id.editText_logged_path);
		double dAveragedPointsCircleRadius = GetAveragedPointsCircleRadius(appPrefes);
		clusterSizeSeekbar.setProgress((int)dAveragedPointsCircleRadius);
		clusterCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						appPrefes.SaveBoolean(BaseApi.mKeyTurnLoggedPath, isChecked);
						SetEnabled();
					}
				});
		clusterSizeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						if(mboOnEditorAction)
							mboOnEditorAction = false;
						else
						{
							appPrefes.SaveData(BaseApi.mKeyAveragedPointsCircleRadius, "" + progress);
							editTextAveragedPointsCircleRadius.setText(String.format("%d", progress));
						}
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				});
		editTextAveragedPointsCircleRadius.setText(String.format("%.1f"//http://developer.android.com/reference/java/util/Formatter.html
				, dAveragedPointsCircleRadius));
		
		//http://www.seostella.com/ru/article/2012/10/10/poluchenie-sobytiya-nazhatiya-enter-v-komponente-edittext-na-platforme-android.html
		//http://developer.alexanderklimov.ru/android/keyboard.php
		editTextAveragedPointsCircleRadius.setOnEditorActionListener(new OnEditorActionListener()
		{
			@Override
	        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				double dAveragedPointsCircleRadius = -1;
				if(
						(event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)//enter from hardware keyboard 
						||
						(actionId == EditorInfo.IME_ACTION_DONE)//enter from virtual keyboard
					)
				{
					String strAveragedPointsCircleRadius = v.getText().toString();
					if(strAveragedPointsCircleRadius.isEmpty())
					{
						v.setError(getString(R.string.Enter_distance));//Please enter minimum distance from 0 to 120 meters
						return true;
					}
					dAveragedPointsCircleRadius = Double.parseDouble(strAveragedPointsCircleRadius);
					if((dAveragedPointsCircleRadius < 0) || (dAveragedPointsCircleRadius > 120))
					{
						v.setError(getString(R.string.Enter_distance));//Please enter minimum distance from 0 to 120 meters
						return true;
					}
				}
				if(dAveragedPointsCircleRadius == -1)
			        throw new MyException(MyElement.getName() + " failed!");
				
				//new averaged points circle radius is correct
				mboOnEditorAction = true;
				clusterSizeSeekbar.setProgress((int)dAveragedPointsCircleRadius);
				appPrefes.SaveData(BaseApi.mKeyAveragedPointsCircleRadius, "" + dAveragedPointsCircleRadius);
				return false;
			}
		});
	}
	public void Default()
	{
		//CHARGING STATIONS
		
		appPrefes.SaveData(BaseApi.mKeyChargingStations, "true");
		appPrefes.SaveData(BaseApi.mKeyProgress, "0");
		setUpClusteringViews();
		
		//LOGGED PATH
		
		appPrefes.SaveBoolean(BaseApi.mKeyTurnLoggedPath, false);
		appPrefes.SaveData(BaseApi.mKeyAveragedPointsCircleRadius, mstrAveragedPointsCircleRadiusDefault);//60
		setUpLoggedPathViews();
	}
}
