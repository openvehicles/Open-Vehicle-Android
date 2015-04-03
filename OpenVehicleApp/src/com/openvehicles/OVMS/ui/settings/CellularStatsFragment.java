package com.openvehicles.OVMS.ui.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.utils.ValueFormatter;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.OnResultCommandListener;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.utils.Ui;

import java.util.ArrayList;



public class CellularStatsFragment extends BaseFragment implements OnResultCommandListener {
	private static final String TAG = "CellularStatsFragment";


	// chart colors:

	private static final int COLOR_TX = Color.parseColor("#33B5E5");
	private static final int COLOR_RX = Color.parseColor("#99CC00");


	// data model:

	private class UsageData {
		public String date;
		public int carRxBytes;
		public int carTxBytes;
		public int appRxBytes;
		public int appTxBytes;

		private UsageData(String date, int carRxBytes, int carTxBytes, int appRxBytes, int appTxBytes) {
			// fill object:
			this.date = date;
			this.carRxBytes = carRxBytes;
			this.carTxBytes = carTxBytes;
			this.appRxBytes = appRxBytes;
			this.appTxBytes = appTxBytes;

			// update totals:
			carTotalBytes += carRxBytes + carTxBytes;
			appTotalBytes += appRxBytes + appTxBytes;
		}
	}

	// data storage:
	private long lastRetrieve;
	private ArrayList<UsageData> mUsageData;
	private int recNr;
	private int recCnt;
	private long carTotalBytes;
	private long appTotalBytes;

	// UI:
	BarChart mChartViewCar;
	BarChart mChartViewApp;

	// system services:
	private ApiService mService;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		createProgressOverlay(inflater, container, true);

		View rootView = inflater.inflate(R.layout.fragment_cellular_stats, null);


		//
		// setup car chart:
		//

		XAxis xAxis;
		YAxis yAxis;

		mChartViewCar = (BarChart) rootView.findViewById(R.id.cellular_usage_chart_car);
		mChartViewCar.setDescription("");
		mChartViewCar.setMaxVisibleValueCount(30);
		mChartViewCar.setDrawValuesForWholeStack(false);
		mChartViewCar.setPinchZoom(false);
		mChartViewCar.setDrawBarShadow(false);
		mChartViewCar.setDrawValueAboveBar(true);
		mChartViewCar.getPaint(LineChart.PAINT_DESCRIPTION).setColor(Color.LTGRAY);
		mChartViewCar.setDrawGridBackground(false);
		mChartViewCar.setDrawBorders(true);

		xAxis = mChartViewCar.getXAxis();
		xAxis.setTextColor(Color.WHITE);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

		yAxis = mChartViewCar.getAxisLeft();
		yAxis.setTextColor(Color.WHITE);
		yAxis.setGridColor(Color.LTGRAY);
		yAxis.setValueFormatter(new ValueFormatter() {
			@Override
			public String getFormattedValue(float v) {
				return String.format("%.0f", v);
			}
		});

		yAxis = mChartViewCar.getAxisRight();
		yAxis.setEnabled(false);


		//
		// setup app chart:
		//

		mChartViewApp = (BarChart) rootView.findViewById(R.id.cellular_usage_chart_app);
		mChartViewApp.setDescription("");
		mChartViewApp.setMaxVisibleValueCount(30);
		mChartViewApp.setDrawValuesForWholeStack(false);
		mChartViewApp.setPinchZoom(false);
		mChartViewApp.setDrawBarShadow(false);
		mChartViewApp.setDrawValueAboveBar(true);
		mChartViewApp.getPaint(LineChart.PAINT_DESCRIPTION).setColor(Color.LTGRAY);
		mChartViewApp.setDrawGridBackground(false);
		mChartViewApp.setDrawBorders(true);

		xAxis = mChartViewApp.getXAxis();
		xAxis.setTextColor(Color.WHITE);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

		yAxis = mChartViewApp.getAxisLeft();
		yAxis.setTextColor(Color.WHITE);
		yAxis.setGridColor(Color.LTGRAY);
		yAxis.setValueFormatter(new ValueFormatter() {
			@Override
			public String getFormattedValue(float v) {
				return String.format("%.0f", v);
			}
		});

		yAxis = mChartViewApp.getAxisRight();
		yAxis.setEnabled(false);


		return rootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SherlockFragmentActivity activity = getSherlockActivity(); 
		activity.setTitle(R.string.CellularStats);
	}


	@Override
	public void onServiceAvailable(ApiService pService) {
		mService = pService;
	}


	@Override
	public void update(CarData pCarData) {
		// called after login / if new data is available
		requestData();
	}


	// request data from server:
	public void requestData() {
		// check if server is available:
		if (mService == null || mService.isLoggedIn() == false)
			return;

		// check if we need an update:
		long now = System.currentTimeMillis() / 1000;
		if ((now - lastRetrieve) > 3600) {

			// last update was >1 hour ago: refresh data

			lastRetrieve = now;

			// Init storage:
			mUsageData = new ArrayList<UsageData>(90);
			carTotalBytes = 0;
			appTotalBytes = 0;
			recCnt = 0;
			recNr = 0;

			// Show progress bar:
			showProgressOverlay();

			// Request cellular usage data:
			mService.sendCommand("30", this);

		} else {
			// need to hide explicitly because onStart=true:
			hideProgressOverlay();
		}
	}

	@Override
	public void onResultCommand(String[] result) {

		if (result.length < 2)
			return;

		int command = Integer.parseInt(result[0]);
		int returnCode = Integer.parseInt(result[1]);
		
		if (command != 30) return; // Not for us
		
		switch (returnCode) {
			case 0: // ok
				/** Result array structure:
				 * 	0=command
				 * 	1=return code
				 * 	2=record number
				 * 	3=maximum number of records
				 * 	4=date (YYYY-MM-DD) (chronologically reverse)
				 * 	5=car received bytes
				 * 	6=car transmitted bytes
				 * 	7=apps received bytes
				 * 	8=apps transmitted bytes
				 */

				if (result.length >= 9) {
					try {
						// Progress info:
						recNr = Integer.parseInt(result[2]);
						recCnt = Integer.parseInt(result[3]);

						stepProgressOverlay(recNr, recCnt);

						// parse result data:
						String nDate = result[4];
						int nCarRxBytes = Integer.parseInt(result[5]);
						int nCarTxBytes = Integer.parseInt(result[6]);
						int nAppRxBytes = Integer.parseInt(result[7]);
						int nAppTxBytes = Integer.parseInt(result[8]);

						// add UsageData in reverse order:
						mUsageData.add(0, new UsageData(nDate, nCarRxBytes, nCarTxBytes, nAppRxBytes,nAppTxBytes));

						// got all?
						if (recNr == recCnt) {
							cancelCommand();
							updateUi();
						}

					} catch(Exception e) {
						// malformed record, ignore
					}
				}
				break;
			case 1: // failed
				Toast.makeText(getActivity(), getString(R.string.err_failed, result[2]),
						Toast.LENGTH_SHORT).show();
				cancelCommand();
				break;
			case 2: // unsupported
				Toast.makeText(getActivity(), getString(R.string.err_unsupported_operation),
						Toast.LENGTH_SHORT).show();
				cancelCommand();
				break;
			case 3: // unimplemented
				Toast.makeText(getActivity(), getString(R.string.err_unimplemented_operation),
						Toast.LENGTH_SHORT).show();
				cancelCommand();
		}
	}


	public void updateUi() {

		if (recCnt == 0 || recNr < recCnt)
			return;

		// Data ready: show results

		int days = mUsageData.size();
		float carMBPerMonth = carTotalBytes / days * 30 / 1000000;
		float appMBPerMonth = appTotalBytes / days * 30 / 1000000;

		View rootView = getView();

		Ui.setValue(rootView, R.id.cellular_usage_info_car, getString(R.string.cellular_usage_info_car,
				carMBPerMonth, days));
		Ui.setValue(rootView, R.id.cellular_usage_info_app, getString(R.string.cellular_usage_info_app,
				appMBPerMonth, days));


		// Update charts:

		UsageData day;

		ArrayList<String> xValues = new ArrayList<String>();
		ArrayList<BarEntry> carValues = new ArrayList<BarEntry>();
		ArrayList<BarEntry> appValues = new ArrayList<BarEntry>();

		for (int i = 0; i < mUsageData.size(); ++i) {
			day = mUsageData.get(i);
			xValues.add(day.date.substring(5));
			carValues.add(new BarEntry(new float[] {
					Math.round(day.carTxBytes / 100) / 10,
					Math.round(day.carRxBytes / 100) / 10
					}, i));
			appValues.add(new BarEntry(new float[] {
					Math.round(day.appTxBytes / 100) / 10,
					Math.round(day.appRxBytes / 100) / 10
					}, i));
		}


		BarDataSet dataSet;
		BarData data;
		ArrayList<BarDataSet> dataSets;
		Legend legend;
		int stackColors[] = { COLOR_TX, COLOR_RX };
		String stackLabels[] = { "Tx", "Rx" };

		// ...car chart:

		dataSet = new BarDataSet(carValues, getString(R.string.chart_axis_datavolume));
		dataSet.setColors(stackColors);
		dataSet.setStackLabels(stackLabels);
		dataSets = new ArrayList<BarDataSet>();
		dataSets.add(dataSet);

		data = new BarData(xValues, dataSets);
		//data.setValueTextColor(Color.WHITE);
		//data.setValueTextSize(9f);
		data.setDrawValues(false);

		mChartViewCar.setData(data);
		mChartViewCar.zoom(3.5f, 1f, 0f, 0f);
		mChartViewCar.moveViewToX(mUsageData.size() - 1);

		legend = mChartViewCar.getLegend();
		legend.setTextColor(Color.WHITE);
		legend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);

		mChartViewCar.invalidate();


		// ...app chart:

		dataSet = new BarDataSet(appValues, getString(R.string.chart_axis_datavolume));
		dataSet.setColors(stackColors);
		dataSet.setStackLabels(stackLabels);
		dataSets = new ArrayList<BarDataSet>();
		dataSets.add(dataSet);

		data = new BarData(xValues, dataSets);
		//data.setValueTextColor(Color.WHITE);
		//data.setValueTextSize(9f);
		data.setDrawValues(false);

		mChartViewApp.setData(data);
		mChartViewApp.zoom(3.5f, 1f, 0f, 0f);
		mChartViewApp.moveViewToX(mUsageData.size()-1);

		legend = mChartViewApp.getLegend();
		legend.setTextColor(Color.WHITE);
		legend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);

		mChartViewApp.invalidate();

	}

}
