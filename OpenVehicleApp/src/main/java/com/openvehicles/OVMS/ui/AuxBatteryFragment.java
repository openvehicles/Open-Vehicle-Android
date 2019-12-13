package com.openvehicles.OVMS.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.AuxBatteryData;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.entities.CmdSeries;
import com.openvehicles.OVMS.ui.utils.ProgressOverlay;
import com.openvehicles.OVMS.utils.CarsStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Auxiliary battery pack status history charts.
 *
 * Data model: AuxBatteryData
 *
 * Created by Michael Balzer <dexter@dexters-web.de> on 2019-12-13.
 *
 */
public class AuxBatteryFragment
		extends BaseFragment
		implements CmdSeries.Listener, ProgressOverlay.OnCancelListener
{
	private static final String TAG = "AuxBatteryFragment";

	// data set colors:

	private static final int COLOR_CURR_LINE = Color.parseColor("#FF3333");
	private static final int COLOR_CURR_TEXT = Color.parseColor("#FFAAAA");
	private static final int COLOR_CURR_GRID = Color.parseColor("#AA7777");

	private static final int COLOR_VOLT = Color.parseColor("#CCFF33");
	private static final int COLOR_VOLT_REF = Color.parseColor("#0077AA");
	private static final int COLOR_VOLT_GRID = Color.parseColor("#77AA77");

	private static final int COLOR_TEMP_AMBIENT = Color.parseColor("#FFEE33");
	private static final int COLOR_TEMP_CHARGER = Color.parseColor("#FFAA33");


	// data storage:

	private AuxBatteryData batteryData;


	// user interface:

	private Menu optionsMenu;

	private LineChart packChart;
	private LineData packData;

	private int highlightSetNr = -1;
	private String highlightSetLabel = "";

	private boolean mShowVolt = true;
	private boolean mShowTemp = false;


	// system services:

	private final static Handler mHandler = new Handler();

	private CarData mCarData;
	private CmdSeries cmdSeries;
	private AppPrefes appPrefes;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// Init data storage:

		batteryData = new AuxBatteryData();


		// Load prefs:

		appPrefes = new AppPrefes(getActivity(), "ovms");

		mShowVolt = appPrefes.getData("aux_battery_show_volt").equals("on");
		mShowTemp = appPrefes.getData("aux_battery_show_temp").equals("on");
		if (!mShowVolt && !mShowTemp) {
			mShowVolt = true;
			mShowTemp = true;
		}


		// Setup UI:

		ProgressOverlay progressOverlay = createProgressOverlay(inflater, container, false);
		progressOverlay.setOnCancelListener(this);

		View rootView = inflater.inflate(R.layout.fragment_auxbattery, null);


		//
		// Setup Pack history chart:
		//

		XAxis xAxis;
		YAxis yAxis;

		packChart = (LineChart) rootView.findViewById(R.id.auxbattery_chart_pack);
		packChart.getPaint(LineChart.PAINT_DESCRIPTION).setColor(Color.LTGRAY);
		packChart.setDrawGridBackground(false);
		packChart.setDrawBorders(true);
		packChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
			@Override
			public void onValueSelected(Entry entry, int dataSet, Highlight highlight) {
				// remember user data set selection:
				highlightSetNr = dataSet;
				highlightSetLabel = packChart.getData().getDataSetByIndex(dataSet).getLabel();
			}

			@Override
			public void onNothingSelected() {
				// nop
			}
		});

		xAxis = packChart.getXAxis();
		xAxis.setTextColor(Color.WHITE);

		yAxis = packChart.getAxisLeft();
		yAxis.setTextColor(COLOR_CURR_TEXT);
		yAxis.setGridColor(COLOR_CURR_GRID);
		yAxis.setValueFormatter(new YAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, YAxis yAxis) {
				return String.format("%.0f", value);
			}
		});

		yAxis = packChart.getAxisRight();
		yAxis.setTextColor(COLOR_VOLT);
		yAxis.setGridColor(COLOR_VOLT_GRID);
		yAxis.setValueFormatter(new YAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, YAxis yAxis) {
				return String.format("%.1f", value);
			}
		});
		yAxis.setGranularity(0.1f);


		// default data set to highlight:
		highlightSetLabel = getString(R.string.aux_battery_data_volt);

		// attach menu:
		setHasOptionsMenu(true);

		return rootView;
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.auxbattery_chart_options, menu);
		optionsMenu = menu;
		optionsMenu.findItem(R.id.mi_chk_volt).setChecked(mShowVolt);
		optionsMenu.findItem(R.id.mi_chk_temp).setChecked(mShowTemp);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getCompatActivity().setTitle(R.string.aux_battery_title);
		getCompatActivity().getSupportActionBar().setIcon(R.drawable.ic_action_chart);

		// get data of current car:
		mCarData = CarsStorage.get().getSelectedCarData();

		// schedule data loader:
		showProgressOverlay(getString(R.string.battery_msg_loading_data));
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// load and display saved vehicle data:
				AuxBatteryData saved = AuxBatteryData.loadFile(mCarData.sel_vehicleid);
				if (saved != null) {
					Log.v(TAG, "AuxBatteryData loaded for " + mCarData.sel_vehicleid);
					batteryData = saved;
					dataSetChanged();
				}
				hideProgressOverlay();
			}
		}, 200);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int menuId = item.getItemId();
		boolean newState = !item.isChecked();

		switch(menuId) {

			case R.id.mi_get_data:
				cmdSeries = new CmdSeries(getService(), AuxBatteryFragment.this)
						.add(R.string.aux_battery_msg_get_battpack, "32,D")
						.start();
				return true;

			case R.id.mi_reset_view:
				packChart.fitScreen();
				return true;

			case R.id.mi_help:
				new AlertDialog.Builder(getActivity())
						.setTitle(R.string.battery_btn_help)
						.setMessage(Html.fromHtml(getString(R.string.aux_battery_help)))
						.setPositiveButton(android.R.string.ok, null)
						.show();
				return true;

			case R.id.mi_chk_volt:
				mShowVolt = newState;
				if (!mShowVolt && !mShowTemp) {
					mShowTemp = true;
					optionsMenu.findItem(R.id.mi_chk_temp).setChecked(true);
				}
				item.setChecked(newState);
				dataFilterChanged();
				return true;

			case R.id.mi_chk_temp:
				mShowTemp = newState;
				if (!mShowVolt && !mShowTemp) {
					mShowVolt = true;
					optionsMenu.findItem(R.id.mi_chk_volt).setChecked(true);
				}
				item.setChecked(newState);
				dataFilterChanged();
				return true;

		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onStop() {
		if (cmdSeries != null)
			cmdSeries.cancel();
		super.onStop();
	}


	@Override
	public void onCmdSeriesProgress(String message, int pos, int posCnt, int step, int stepCnt) {
		stepProgressOverlay(message, pos, posCnt, step, stepCnt);
	}


	@Override
	public void onProgressCancel() {
		if (cmdSeries != null)
			cmdSeries.cancel();
		hideProgressOverlay();
	}


	@Override
	public void onCmdSeriesFinish(CmdSeries cmdSeries, int returnCode) {
		String errorMsg = "";
		String errorDetail = "";

		hideProgressOverlay();

		switch (returnCode) {

			case -1: // abort
				return;

			case 0: // ok: process & display results:
				showProgressOverlay(getString(R.string.msg_processing_data));
				batteryData.processCmdResults(cmdSeries);
				batteryData.saveFile(mCarData.sel_vehicleid);
				dataSetChanged();
				hideProgressOverlay();
				return;

			case 1: // failed
				errorDetail = cmdSeries.getErrorDetail();
				errorMsg = getString(R.string.err_failed, errorDetail);
				break;
			case 2: // unsupported
				errorMsg = getString(R.string.err_unsupported_operation);
				break;
			case 3: // unimplemented
				errorMsg = getString(R.string.err_unimplemented_operation);
				break;
		}

		// getting here means error:
		new AlertDialog.Builder(getActivity())
				.setTitle(R.string.Error)
				.setMessage(cmdSeries.getMessage() + " => " + errorMsg)
				.setPositiveButton(android.R.string.ok, null)
				.show();
	}


	/**
	 * Check data model validity
	 */
	private boolean isPackValid() {
		return (batteryData != null
				&& batteryData.packHistory != null
				&& batteryData.packHistory.size() > 0);
	}

	/**
	 * Check data model and pack index validity
	 */
	private boolean isPackIndexValid(int index) {
		return (isPackValid()
				&& index < batteryData.packHistory.size());
	}


	/**
	 * Call when underlying batteryData object ready/changed
	 */
	public void dataSetChanged() {

		if (!isPackValid())
			return;

		// update pack chart:
		showPackHistory();

		// highlight latest entry:
		int lastEntry = batteryData.packHistory.size() - 1;
		highlightPackEntry(lastEntry);
	}


	/**
	 * Call when user changed data filter (i.e. show Volt / Temp sets)
	 */
	private void dataFilterChanged() {

		// save prefs:
		appPrefes.SaveData("aux_battery_show_volt", mShowVolt ? "on" : "off");
		appPrefes.SaveData("aux_battery_show_temp", mShowTemp ? "on" : "off");

		// check data status:
		if (!isPackValid())
			return;

		// update pack chart:
		showPackHistory();
	}


	/**
	 * Update the pack chart
	 */
	public void showPackHistory() {

		if (!isPackValid())
			return;

		ArrayList<AuxBatteryData.PackStatus> packHistory = batteryData.packHistory;
		AuxBatteryData.PackStatus packStatus, lastStatus;
		SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm");

		//
		// Pack chart:
		//

		// create value arrays:

		ArrayList<String> xValues = new ArrayList<String>();
		ArrayList<LimitLine> xSections = new ArrayList<LimitLine>();
		ArrayList<Entry> voltValues = new ArrayList<Entry>();
		ArrayList<Entry> voltRefValues = new ArrayList<Entry>();
		ArrayList<Entry> currentValues = new ArrayList<Entry>();
		ArrayList<Entry> tempAmbientValues = new ArrayList<Entry>();
		ArrayList<Entry> tempChargerValues = new ArrayList<Entry>();

		lastStatus = null;

		for (int i = 0; i < packHistory.size(); i++) {

			packStatus = packHistory.get(i);

			xValues.add(timeFmt.format(packStatus.timeStamp));

			voltValues.add(new Entry(packStatus.volt, i));
			voltRefValues.add(new Entry(packStatus.voltRef, i));
			currentValues.add(new Entry(packStatus.current, i));
			tempAmbientValues.add(new Entry(packStatus.tempAmbient, i));
			tempChargerValues.add(new Entry(packStatus.tempCharger, i));

			// add section markers:
			if (packStatus.isNewSection(lastStatus)) {
				LimitLine l = new LimitLine(i);
				l.setLabel(timeFmt.format(packStatus.timeStamp));
				l.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
				l.setTextColor(Color.WHITE);
				l.setTextStyle(Paint.Style.FILL);
				l.enableDashedLine(3f, 2f, 0f);
				xSections.add(l);
			}

			lastStatus = packStatus;
		}


		// create data sets:

		ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
		LineDataSet dataSet;
		LineDataSet packVoltSet=null, packVoltRefSet=null, packCurrentSet=null;
		LineDataSet packTempAmbientSet=null, packTempChargerSet=null;

		if (mShowTemp) {
			packTempAmbientSet = dataSet = new LineDataSet(tempAmbientValues, getString(R.string.aux_battery_data_temp_ambient));
			dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
			dataSet.setColor(COLOR_TEMP_AMBIENT);
			dataSet.setLineWidth(3f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSets.add(dataSet);

			packTempChargerSet = dataSet = new LineDataSet(tempChargerValues, getString(R.string.aux_battery_data_temp_charger));
			dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
			dataSet.setColor(COLOR_TEMP_CHARGER);
			dataSet.setLineWidth(3f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSets.add(dataSet);
		}

		packCurrentSet = dataSet = new LineDataSet(currentValues, getString(R.string.aux_battery_data_current));
		dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
		dataSet.setColor(COLOR_CURR_LINE);
		dataSet.setLineWidth(3f);
		dataSet.setDrawCircles(false);
		dataSet.setDrawValues(false);
		dataSets.add(dataSet);

		if (mShowVolt) {
			packVoltRefSet = dataSet = new LineDataSet(voltRefValues, getString(R.string.aux_battery_data_volt_ref));
			dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
			dataSet.setColor(COLOR_VOLT_REF);
			dataSet.setLineWidth(5f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSets.add(dataSet);

			packVoltSet = dataSet = new LineDataSet(voltValues, getString(R.string.aux_battery_data_volt));
			dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
			dataSet.setColor(COLOR_VOLT);
			dataSet.setLineWidth(3f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSets.add(dataSet);
		}

		// configure y axes:

		float yMin, yMax;

		// voltage axis:
		YAxis yAxis = packChart.getAxisRight();
		yAxis.setEnabled(mShowVolt);
		if (mShowVolt && (packVoltSet != null) && (packVoltRefSet != null)) {
			yMax = Math.max(packVoltSet.getYMax(), packVoltRefSet.getYMax());
			yMin = Math.min(packVoltSet.getYMin(), packVoltRefSet.getYMin());
			yMax += 0.3;
			yMin -= 0.3;
			yAxis.setAxisMaxValue(yMax);
			yAxis.setAxisMinValue(yMin-(yMax-yMin)); // half height
		}

		// current & temperature axis:
		yAxis = packChart.getAxisLeft();
		yMax = packCurrentSet.getYMax();
		yMin = packCurrentSet.getYMin();
		if (mShowTemp && (packTempAmbientSet != null) && (packTempChargerSet != null)) {
			yMax = Math.max(yMax, packTempAmbientSet.getYMax());
			yMin = Math.min(yMin, packTempAmbientSet.getYMin());
			yMax = Math.max(yMax, packTempChargerSet.getYMax());
			yMin = Math.min(yMin, packTempChargerSet.getYMin());
		}
		yMax += 3.0;
		yMin -= 3.0;
		if (mShowVolt)
			yAxis.setAxisMaxValue(yMax + (yMax - yMin)); // half height
		else
			yAxis.setAxisMaxValue(yMax); // full height
		yAxis.setAxisMinValue(yMin);

		// display data sets:

		LineData data;
		packData = data = new LineData(xValues, dataSets);
		data.setValueTextColor(Color.WHITE);
		data.setValueTextSize(9f);

		packChart.setData(data);

		XAxis xAxis = packChart.getXAxis();
		xAxis.removeAllLimitLines();
		for (int i=0; i < xSections.size(); i++) {
			xAxis.addLimitLine(xSections.get(i));
		}

		packChart.getLegend().setTextColor(Color.WHITE);

		packChart.invalidate();

	}


	/**
	 * Set pack chart highlight to a specific pack history index.
	 *
	 * This will try to keep the last selected data set. If highlightSetNr == -1
	 * it will try to determine the data set by the last selected label.
	 *
	 * @param index - pack history index to highlight
	 */
	private void highlightPackEntry(int index) {

		// check model status:
		if (!isPackIndexValid(index))
			return;

		// determine data set to highlight:
		if (highlightSetNr == -1) {
			// get user selection by set label:
			ILineDataSet dataSet = packData.getDataSetByLabel(highlightSetLabel, false);
			highlightSetNr = packData.getIndexOfDataSet(dataSet);
		}
		if (highlightSetNr == -1) {
			// fallback to foreground set:
			highlightSetNr = packData.getDataSetCount() - 1;
		}

		// highlight entry:
		packChart.highlightValue(index, highlightSetNr); // does not fire listener event

		// center highlight in chart viewport:
		ILineDataSet dataSet = packChart.getData().getDataSetByIndex(highlightSetNr);
		packChart.centerViewTo(index, dataSet.getYValForXIndex(index),
				dataSet.getAxisDependency());

	}


}

