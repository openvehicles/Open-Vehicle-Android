package com.openvehicles.OVMS.ui;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ValueFormatter;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.entities.CmdSeries;
import com.openvehicles.OVMS.entities.GPSLogData;
import com.openvehicles.OVMS.ui.utils.ProgressOverlay;
import com.openvehicles.OVMS.utils.CarsStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by balzer on 06.04.15.
 */
public class PowerFragment
	extends BaseFragment
	implements CmdSeries.Listener, ProgressOverlay.OnCancelListener
{
	private static final String TAG = "PowerFragment";


	// data set colors:

	private static final int COLOR_ALTITUDE = Color.parseColor("#C0C8AB37");
	private static final int COLOR_ALTITUDE_GRID = Color.parseColor("#80C8AB37");

	private static final int COLOR_SPEED = Color.parseColor("#FF7777FF");
	private static final int COLOR_SPEED_GRID = Color.parseColor("#807777FF");

	private static final int COLOR_POWER_MIN = Color.parseColor("#FF999999");
	private static final int COLOR_POWER_MAX = Color.parseColor("#FF999999");
	private static final int COLOR_POWER_AVG = Color.parseColor("#FFFF0000");

	private static final int COLOR_ENERGY_USED = Color.parseColor("#FF999999");
	private static final int COLOR_ENERGY_RECD = Color.parseColor("#FF999999");
	private static final int COLOR_ENERGY_AVG = Color.parseColor("#FFFFFF00");


	// data storage:

	private GPSLogData logData;

	private ArrayList<GPSLogData.Entry> chartEntries;


	// user interface:

	private Menu optionsMenu;

	private LineChart tripChart;
	private LineChart powerChart;
	private LineChart energyChart;

	private CoupleChartGestureListener chartCoupler;

	private boolean mShowPower = true;
	private boolean mShowEnergy = true;
	private boolean mLiveUpdate = false;


	// system services:

	private final static Handler mHandler = new Handler();

	private CarData mCarData;
	private CmdSeries cmdSeries;
	private AppPrefes appPrefes;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// Init data storage:

		logData = new GPSLogData();


		// Load prefs:

		appPrefes = new AppPrefes(getActivity(), "ovms");

		mShowPower = appPrefes.getData("power_show_power").equals("on");
		mShowEnergy = appPrefes.getData("power_show_energy").equals("on");
		if (!mShowPower && !mShowEnergy)
			mShowPower = true;
		mLiveUpdate = appPrefes.getData("power_liveupdate").equals("on");


		// Setup UI:

		ProgressOverlay progressOverlay = createProgressOverlay(inflater, container, false);
		progressOverlay.setOnCancelListener(this);

		View rootView = inflater.inflate(R.layout.fragment_power, null);

		XAxis xAxis;
		YAxis yAxis;
		LineChart chart;

		//
		// Setup trip chart:
		//

		tripChart = chart = (LineChart) rootView.findViewById(R.id.chart_trip);
		chart.setDescription(getString(R.string.power_trip_description));
		chart.getPaint(LineChart.PAINT_DESCRIPTION).setColor(Color.LTGRAY);
		chart.setDrawGridBackground(false);
		chart.setDrawBorders(true);
		chart.setHighlightEnabled(false);

		xAxis = chart.getXAxis();
		xAxis.setTextColor(Color.WHITE);

		yAxis = chart.getAxisLeft(); // altitude
		yAxis.setTextColor(COLOR_ALTITUDE);
		yAxis.setGridColor(COLOR_ALTITUDE_GRID);
		yAxis.setStartAtZero(false);
		yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

		yAxis = chart.getAxisRight(); // speed
		yAxis.setTextColor(COLOR_SPEED);
		yAxis.setGridColor(COLOR_SPEED_GRID);
		yAxis.setStartAtZero(true);
		yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);


		//
		// Setup power chart:
		//

		powerChart = chart = (LineChart) rootView.findViewById(R.id.chart_power);
		chart.setDescription(getString(R.string.power_power_description));
		chart.getPaint(LineChart.PAINT_DESCRIPTION).setColor(Color.LTGRAY);
		chart.setDrawGridBackground(false);
		chart.setDrawBorders(true);
		chart.setHighlightEnabled(false);

		xAxis = chart.getXAxis();
		xAxis.setTextColor(Color.WHITE);

		yAxis = chart.getAxisLeft();
		yAxis.setTextColor(Color.WHITE);
		yAxis.setGridColor(Color.LTGRAY);
		yAxis.setStartAtZero(false);
		yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
		yAxis.setValueFormatter(new ValueFormatter() {
			@Override
			public String getFormattedValue(float value) {
				return String.format("%.0fkW", value / 1000f);
			}
		});

		yAxis = chart.getAxisRight();
		yAxis.setTextColor(Color.WHITE);
		yAxis.setGridColor(Color.LTGRAY);
		yAxis.setStartAtZero(false);
		yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
		yAxis.setValueFormatter(new ValueFormatter() {
			@Override
			public String getFormattedValue(float value) {
				return String.format("%.0fkW", value / 1000f);
			}
		});


		//
		// Setup energy chart:
		//

		energyChart = chart = (LineChart) rootView.findViewById(R.id.chart_energy);
		chart.setDescription(getString(R.string.power_energy_description));
		chart.getPaint(LineChart.PAINT_DESCRIPTION).setColor(Color.LTGRAY);
		chart.setDrawGridBackground(false);
		chart.setDrawBorders(true);
		chart.setHighlightEnabled(false);

		xAxis = chart.getXAxis();
		xAxis.setTextColor(Color.WHITE);

		yAxis = chart.getAxisLeft();
		yAxis.setTextColor(Color.WHITE);
		yAxis.setGridColor(Color.LTGRAY);
		yAxis.setStartAtZero(false);
		yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

		yAxis = chart.getAxisRight();
		yAxis.setTextColor(Color.WHITE);
		yAxis.setGridColor(Color.LTGRAY);
		yAxis.setStartAtZero(false);
		yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);


		//
		// Couple chart viewports:
		//

		tripChart.setOnChartGestureListener(chartCoupler = new CoupleChartGestureListener(
				tripChart, new Chart[] { powerChart, energyChart }));
		powerChart.setOnChartGestureListener(new CoupleChartGestureListener(
				powerChart, new Chart[] { tripChart, energyChart }));
		energyChart.setOnChartGestureListener(new CoupleChartGestureListener(
				energyChart, new Chart[] { tripChart, powerChart }));

		// attach menu:
		setHasOptionsMenu(true);

		return rootView;
	}


	public class CoupleChartGestureListener implements OnChartGestureListener {

		private Chart srcChart;
		private Chart[] dstCharts;

		public CoupleChartGestureListener(Chart srcChart, Chart[] dstCharts) {
			this.srcChart = srcChart;
			this.dstCharts = dstCharts;
		}

		@Override
		public void onChartLongPressed(MotionEvent me) {
			// nop
		}

		@Override
		public void onChartDoubleTapped(MotionEvent me) {
			// nop
		}

		@Override
		public void onChartSingleTapped(MotionEvent me) {
			// nop
		}

		@Override
		public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
			// nop
		}

		@Override
		public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
			//Log.d(TAG, "onChartScale " + scaleX + "/" + scaleY + " X=" + me.getX() + "Y=" + me.getY());
			syncCharts();
		}

		@Override
		public void onChartTranslate(MotionEvent me, float dX, float dY) {
			//Log.d(TAG, "onChartTranslate " + dX + "/" + dY + " X=" + me.getX() + "Y=" + me.getY());
			syncCharts();
		}

		public void syncCharts() {
			Matrix srcMatrix;
			float[] srcVals = new float[9];
			Matrix dstMatrix;
			float[] dstVals = new float[9];

			// get src chart translation matrix:
			srcMatrix = srcChart.getViewPortHandler().getMatrixTouch();
			srcMatrix.getValues(srcVals);

			// apply X axis scaling and position to dst charts:
			for (Chart dstChart : dstCharts) {
				if (dstChart.getVisibility() == View.VISIBLE) {
					dstMatrix = dstChart.getViewPortHandler().getMatrixTouch();
					dstMatrix.getValues(dstVals);
					dstVals[Matrix.MSCALE_X] = srcVals[Matrix.MSCALE_X];
					dstVals[Matrix.MTRANS_X] = srcVals[Matrix.MTRANS_X];
					dstMatrix.setValues(dstVals);
					dstChart.getViewPortHandler().refresh(dstMatrix, dstChart, true);
				}
			}
		}

	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.power_chart_options, menu);

		optionsMenu = menu;

		// configure checkbox items:
		optionsMenu.findItem(R.id.mi_chk_power).setChecked(mShowPower);
		optionsMenu.findItem(R.id.mi_chk_energy).setChecked(mShowEnergy);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		if (mCarData != null) {
			// insert user distance units into menu titles:
			optionsMenu.findItem(R.id.mi_power_zoom5).setTitle(
					getString(R.string.power_btn_zoom5, mCarData.car_distance_units));
			optionsMenu.findItem(R.id.mi_power_zoom10).setTitle(
					getString(R.string.power_btn_zoom10, mCarData.car_distance_units));
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getSherlockActivity().setTitle(R.string.power_title);
		getSherlockActivity().getSupportActionBar().setIcon(R.drawable.ic_action_chart);

		// get data of current car:
		mCarData = CarsStorage.get().getSelectedCarData();

		getSherlockActivity().invalidateOptionsMenu();

		// schedule data loader:
		showProgressOverlay(getString(R.string.power_msg_loading_data));
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// load and display saved vehicle data:
				GPSLogData saved = GPSLogData.loadFile(mCarData.sel_vehicleid);
				if (saved != null) {
					Log.v(TAG, "GPSLogData loaded for " + mCarData.sel_vehicleid);
					logData = saved;
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

		switch (menuId) {

			case R.id.mi_get_data:
				cmdSeries = new CmdSeries(getService(), this)
						.add(R.string.power_msg_getting_gpslog, "32,RT-GPS-Log")
						.start();
				return true;

			case R.id.mi_power_zoom5:
				zoomOdometerRange(5);
				return true;

			case R.id.mi_power_zoom10:
				zoomOdometerRange(10);
				return true;

			case R.id.mi_reset_view:
				tripChart.fitScreen();
				powerChart.fitScreen();
				energyChart.fitScreen();
				return true;

			case R.id.mi_help:
				new AlertDialog.Builder(getActivity())
						.setTitle(R.string.power_btn_help)
						.setMessage(Html.fromHtml(getString(R.string.power_help)))
						.setPositiveButton(android.R.string.ok, null)
						.show();
				return true;

			case R.id.mi_chk_power:
				mShowPower = newState;
				if (!mShowPower && !mShowEnergy) {
					mShowEnergy = true;
					optionsMenu.findItem(R.id.mi_chk_energy).setChecked(true);
				}
				item.setChecked(newState);
				dataFilterChanged();
				return true;

			case R.id.mi_chk_energy:
				mShowEnergy = newState;
				if (!mShowPower && !mShowEnergy) {
					mShowPower = true;
					optionsMenu.findItem(R.id.mi_chk_power).setChecked(true);
				}
				item.setChecked(newState);
				dataFilterChanged();
				return true;

			case R.id.mi_chk_liveupdate:
				mLiveUpdate = newState;
				appPrefes.SaveData("power_liveupdate", mLiveUpdate ? "on" : "off");
				item.setChecked(newState);
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
				logData.processCmdResults(cmdSeries);
				logData.saveFile(mCarData.sel_vehicleid);
				dataSetChanged();
				hideProgressOverlay();
				return;

			case 1: // failed
				errorDetail = cmdSeries.getErrorDetail();
				if (errorDetail.contains("B "))
					errorDetail += getString(R.string.hint_sevcon_offline);
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
		return (logData != null
				&& logData.entries != null
				&& logData.entries.size() > 1);
	}


	/**
	 * Call when underlying batteryData object ready/changed
	 */
	public void dataSetChanged() {

		if (!isPackValid())
			return;

		// update charts:
		updateCharts();

	}


	/**
	 * Call when user changed data filter (i.e. show flags)
	 */
	private void dataFilterChanged() {

		// save prefs:
		appPrefes.SaveData("power_show_power", mShowPower ? "on" : "off");
		appPrefes.SaveData("power_show_energy", mShowEnergy ? "on" : "off");

		// check data status:
		if (!isPackValid())
			return;

		// update charts:
		updateCharts();

		// sync viewports:
		chartCoupler.syncCharts();
	}


	/**
	 * Update the charts
	 */
	public void updateCharts() {

		if (!isPackValid())
			return;

		ArrayList<GPSLogData.Entry> logEntries = logData.entries;
		GPSLogData.Entry entry, refEntry;
		int refIndex;
		SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm");


		//
		// Create value arrays:
		//

		chartEntries = new ArrayList<GPSLogData.Entry>();

		ArrayList<String> xValues = new ArrayList<String>();
		ArrayList<LimitLine> xSections = new ArrayList<LimitLine>();

		ArrayList<Entry> altValues = new ArrayList<Entry>();
		ArrayList<Entry> spdValues = new ArrayList<Entry>();

		ArrayList<Entry> pwrMinValues = new ArrayList<Entry>();
		ArrayList<Entry> pwrMaxValues = new ArrayList<Entry>();
		ArrayList<Entry> pwrAvgValues = new ArrayList<Entry>();

		ArrayList<Entry> energyUseValues = new ArrayList<Entry>();
		ArrayList<Entry> energyRecValues = new ArrayList<Entry>();
		ArrayList<Entry> energyAvgValues = new ArrayList<Entry>();


		String units = mCarData.car_distance_units_raw;

		String xLabel;
		int xIndex = 0;

		refIndex = 0;
		refEntry = logEntries.get(0);

		for (int i = 1; i < logEntries.size(); i++) {

			entry = logEntries.get(i);

			// check data distance from ref:
			if (entry.isNewTimePoint(refEntry)) {

				// X axis to log entry association:
				chartEntries.add(entry);

				//xLabel = String.format("%.0f", entry.getOdometer(units));
				xLabel = timeFmt.format(entry.timeStamp);

				xValues.add(xLabel);

				altValues.add(new Entry(entry.altitude, xIndex));
				spdValues.add(new Entry(entry.getSpeed(refEntry, units), xIndex));

				pwrMinValues.add(new Entry(entry.getMinPower(logEntries, refEntry), xIndex));
				pwrMaxValues.add(new Entry(entry.getMaxPower(logEntries, refEntry), xIndex));
				pwrAvgValues.add(new Entry(entry.getSegAvgPwr(refEntry), xIndex));

				energyUseValues.add(new Entry(entry.getSegUsedWh(refEntry), xIndex));
				energyRecValues.add(new Entry(entry.getSegRecdWh(refEntry), xIndex));
				energyAvgValues.add(new Entry(entry.getSegAvgEnergy(refEntry, units), xIndex));

				// add section markers:
				if (entry.isSectionStart(refEntry)) {
					LimitLine l = new LimitLine(xIndex);
					l.setLabel(String.format("%.0f", entry.getOdometer(units)));
					l.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
					l.setTextColor(Color.WHITE);
					l.setTextStyle(Paint.Style.FILL);
					l.enableDashedLine(3f, 2f, 0f);
					xSections.add(l);
				}

				xIndex++;

				// advance reference point:
				while (entry.isNewTimePoint(refEntry) && refIndex <= i)
					refEntry = logEntries.get(++refIndex);
			}
		}


		//
		// Update trip chart:
		//

		ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
		LineDataSet dataSet;

		dataSet = new LineDataSet(altValues,
				getString(R.string.power_data_altitude));
		dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
		dataSet.setColor(COLOR_ALTITUDE);
		dataSet.setDrawFilled(true);
		dataSet.setLineWidth(2f);
		dataSet.setDrawCircles(false);
		dataSet.setDrawValues(false);
		dataSet.setDrawCubic(true);
		dataSets.add(dataSet);

		dataSet = new LineDataSet(spdValues,
				getString(R.string.power_data_speed, mCarData.car_speed_units));
		dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
		dataSet.setColor(COLOR_SPEED);
		dataSet.setLineWidth(4f);
		dataSet.setDrawCircles(false);
		dataSet.setDrawValues(false);
		dataSet.setDrawCubic(true);
		dataSets.add(dataSet);


		// display data sets:

		LineData data;
		data = new LineData(xValues, dataSets);
		data.setValueTextColor(Color.WHITE);
		data.setValueTextSize(9f);

		tripChart.setData(data);

		XAxis xAxis = tripChart.getXAxis();
		xAxis.removeAllLimitLines();
		for (int i = 0; i < xSections.size(); i++) {
			xAxis.addLimitLine(xSections.get(i));
		}

		tripChart.getLegend().setTextColor(Color.WHITE);

		tripChart.invalidate();


		//
		// Update power chart:
		//

		if (!mShowPower) {

			powerChart.setVisibility(View.GONE);
			powerChart.clear();

		} else {

			powerChart.setVisibility(View.VISIBLE);

			dataSets = new ArrayList<LineDataSet>();

			dataSet = new LineDataSet(pwrMinValues,
					getString(R.string.power_data_pwr_min));
			dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
			dataSet.setColor(COLOR_POWER_MIN);
			dataSet.setDrawFilled(true);
			dataSet.setLineWidth(1f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSet.setDrawCubic(true);
			dataSets.add(dataSet);

			dataSet = new LineDataSet(pwrMaxValues,
					getString(R.string.power_data_pwr_max));
			dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
			dataSet.setColor(COLOR_POWER_MAX);
			dataSet.setDrawFilled(true);
			dataSet.setLineWidth(1f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSet.setDrawCubic(true);
			dataSets.add(dataSet);

			dataSet = new LineDataSet(pwrAvgValues,
					getString(R.string.power_data_pwr_avg));
			dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
			dataSet.setColor(COLOR_POWER_AVG);
			dataSet.setDrawFilled(false);
			dataSet.setLineWidth(4f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSet.setDrawCubic(true);
			dataSets.add(dataSet);


			// display data sets:

			data = new LineData(xValues, dataSets);
			data.setValueTextColor(Color.WHITE);
			data.setValueTextSize(9f);

			powerChart.setData(data);

			xAxis = powerChart.getXAxis();
			xAxis.removeAllLimitLines();
			for (int i = 0; i < xSections.size(); i++) {
				xAxis.addLimitLine(xSections.get(i));
			}

			powerChart.getLegend().setTextColor(Color.WHITE);

			powerChart.invalidate();

		}


		//
		// Update energy chart:
		//

		if (!mShowEnergy) {

			energyChart.setVisibility(View.GONE);
			energyChart.clear();

		} else {

			energyChart.setVisibility(View.VISIBLE);

			dataSets = new ArrayList<LineDataSet>();

			dataSet = new LineDataSet(energyUseValues,
					getString(R.string.power_data_energy_used));
			dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
			dataSet.setColor(COLOR_ENERGY_USED);
			dataSet.setDrawFilled(true);
			dataSet.setLineWidth(1f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSet.setDrawCubic(true);
			dataSets.add(dataSet);

			dataSet = new LineDataSet(energyRecValues,
					getString(R.string.power_data_energy_recd));
			dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
			dataSet.setColor(COLOR_ENERGY_RECD);
			dataSet.setDrawFilled(true);
			dataSet.setLineWidth(1f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSet.setDrawCubic(true);
			dataSets.add(dataSet);

			dataSet = new LineDataSet(energyAvgValues,
					getString(R.string.power_data_energy_avg, mCarData.car_distance_units));
			dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
			dataSet.setColor(COLOR_ENERGY_AVG);
			dataSet.setDrawFilled(false);
			dataSet.setLineWidth(4f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSet.setDrawCubic(true);
			dataSets.add(dataSet);


			// display data sets:

			data = new LineData(xValues, dataSets);
			data.setValueTextColor(Color.WHITE);
			data.setValueTextSize(9f);

			energyChart.setData(data);

			xAxis = energyChart.getXAxis();
			xAxis.removeAllLimitLines();
			for (int i = 0; i < xSections.size(); i++) {
				xAxis.addLimitLine(xSections.get(i));
			}

			energyChart.getLegend().setTextColor(Color.WHITE);

			energyChart.invalidate();

		}

	}


	/**
	 * Zoom to odometer range
	 *
	 * @param size -- size of range in user units (km/mi)
	 */
	private void zoomOdometerRange(int size) {

		int start = 0, end = chartEntries.size()-1;
		GPSLogData.Entry entry;
		float odoEnd = 0;

		// find last drive entry:

		for (int i = chartEntries.size()-1; i >= 0; i--) {
			entry = chartEntries.get(i);
			if (entry.getOpStatus() == 1) {
				odoEnd = entry.getOdometer(mCarData.car_distance_units_raw);
				end = i;
				break;
			}
		}

		// find entry for (odoEnd - size):

		for (int i = end-1; i >= 0; i--) {
			entry = chartEntries.get(i);
			if (entry.getOdometer(mCarData.car_distance_units_raw) <= (odoEnd - size)) {
				start = i;
				break;
			}
		}

		// zoom charts:

		float scaleX = (float) chartEntries.size() / (end - start + 1);

		for (BarLineChartBase chart : new BarLineChartBase[] { tripChart, powerChart, energyChart } ) {
			chart.fitScreen();
			chart.zoom(scaleX, 1f, chart.getWidth() / 2f, chart.getHeight() / 2f);
			chart.moveViewToX(start);
		}

	}


	/**
	 * Live update function
	 *
	 */

	public void doLiveUpdate() {



	}

}

