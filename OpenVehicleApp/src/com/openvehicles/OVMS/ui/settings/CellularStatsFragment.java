package com.openvehicles.OVMS.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.OnResultCommandListenner;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.utils.Ui;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class CellularStatsFragment extends BaseFragment implements OnResultCommandListenner {
	private static final String TAG = "CellularStatsFragment";

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

	// system services:
	private ApiService mService;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		createProgressOverlay(inflater, container, true);
		return inflater.inflate(R.layout.fragment_cellular_stats, null);
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

		View rootView = getView();

		ColumnChartView mChartViewCar;
		ColumnChartData mChartDataCar;
		ColumnChartView mChartViewApp;
		ColumnChartData mChartDataApp;

		int days = mUsageData.size();
		float carMBPerMonth = carTotalBytes / days * 30 / 1000000;
		float appMBPerMonth = appTotalBytes / days * 30 / 1000000;

		Ui.setValue(rootView, R.id.cellular_usage_info_car, getString(R.string.cellular_usage_info_car,
				carMBPerMonth, days));
		Ui.setValue(rootView, R.id.cellular_usage_info_app, getString(R.string.cellular_usage_info_app,
				appMBPerMonth, days));


		// Update graphs:

		UsageData day;
		List<Column> columns;
		List<SubcolumnValue> values;


		// Create axes:

		Axis axisX = new Axis();
		Axis axisY = new Axis().setHasLines(true);
		axisX.setName(getString(R.string.chart_axis_date));
		axisY.setName(getString(R.string.chart_axis_datavolume));

		axisX.setHasTiltedLabels(true);
		axisX.setMaxLabelChars(5);

		List<AxisValue> axisValues;

		axisValues = new ArrayList<AxisValue>();
		for (int i = 0; i < mUsageData.size(); ++i) {
			day = mUsageData.get(i);
			axisValues.add(new AxisValue((float) i, day.date.substring(5).toCharArray()));
		}

		axisX.setValues(axisValues);


		// Update car graph:

		columns = new ArrayList<Column>();
		for (int i = 0; i < mUsageData.size(); ++i) {

			day = mUsageData.get(i);

			values = new ArrayList<SubcolumnValue>();
			values.add(new SubcolumnValue(
					Math.round(day.carTxBytes / 100) / 10,
					ChartUtils.COLOR_BLUE));
			values.add(new SubcolumnValue(
					Math.round(day.carRxBytes / 100) / 10,
					ChartUtils.COLOR_GREEN));

			Column column = new Column(values);
			column.setHasLabels(true);
			column.setHasLabelsOnlyForSelected(true);
			columns.add(column);
		}

		mChartDataCar = new ColumnChartData(columns);
		mChartDataCar.setStacked(true);
		mChartDataCar.setAxisXBottom(axisX);
		mChartDataCar.setAxisYLeft(axisY);

		// Load data into chart view:
		mChartViewCar = (ColumnChartView) rootView.findViewById(R.id.cellular_usage_chart_car);
		mChartViewCar.setColumnChartData(mChartDataCar);
		mChartViewCar.setZoomType(ZoomType.HORIZONTAL);
		mChartViewCar.setValueSelectionEnabled(true);


		// Update app graph:

		columns = new ArrayList<Column>();
		for (int i = 0; i < mUsageData.size(); ++i) {

			day = mUsageData.get(i);

			values = new ArrayList<SubcolumnValue>();
			values.add(new SubcolumnValue(
					Math.round(day.appTxBytes / 100) / 10,
					ChartUtils.COLOR_BLUE));
			values.add(new SubcolumnValue(
					Math.round(day.appRxBytes / 100) / 10,
					ChartUtils.COLOR_GREEN));

			Column column = new Column(values);
			column.setHasLabels(true);
			column.setHasLabelsOnlyForSelected(true);
			columns.add(column);
		}

		mChartDataApp = new ColumnChartData(columns);
		mChartDataApp.setStacked(true);
		mChartDataApp.setAxisXBottom(axisX);
		mChartDataApp.setAxisYLeft(axisY);

		// Load data into chart view:
		mChartViewApp = (ColumnChartView) rootView.findViewById(R.id.cellular_usage_chart_app);
		mChartViewApp.setColumnChartData(mChartDataApp);
		mChartViewApp.setZoomType(ZoomType.HORIZONTAL);
		mChartViewApp.setValueSelectionEnabled(true);

	}

}
