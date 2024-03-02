package com.openvehicles.OVMS.ui;

import androidx.appcompat.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.openvehicles.OVMS.utils.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.BatteryData;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.entities.CmdSeries;
import com.openvehicles.OVMS.ui.utils.ProgressOverlay;
import com.openvehicles.OVMS.utils.CarsStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Battery pack and cell status history charts.
 *
 * Currently only usable for Renault Twizy, but can be extended to support
 * other battery layouts easily as soon as other vehicles deliver this info.
 *
 * Data model: BatteryData
 *
 * Created by balzer on 27.03.15.
 *
 */
public class BatteryFragment
		extends BaseFragment
		implements CmdSeries.Listener, ProgressOverlay.OnCancelListener
{
	private static final String TAG = "BatteryFragment";

	// data set colors:

	private static final int COLOR_SOC_LINE = Color.parseColor("#A04455FF");
	private static final int COLOR_SOC_TEXT = Color.parseColor("#AAAAFF");
	private static final int COLOR_SOC_GRID = Color.parseColor("#7777AA");

	private static final int COLOR_VOLT = Color.parseColor("#CCFF33");
	private static final int COLOR_VOLT_MIN = Color.parseColor("#77AA00");
	private static final int COLOR_VOLT_GRID = Color.parseColor("#77AA77");

	private static final int COLOR_TEMP = Color.parseColor("#FFEE33");
	private static final int COLOR_TEMP_GRID = Color.parseColor("#AAAA77");


	// data storage:

	private BatteryData batteryData;


	// user interface:

	private Menu optionsMenu;

	private LineChart packChart;
	private LineData packData;
	private LineDataSet packVoltSet, packVoltMinSet, packTempSet;
	private CandleStickChart cellChart;
	private SeekBar seekPack;

	public class PackTimeValueFormatter extends ValueFormatter {
		SimpleDateFormat timeFmt;
		public PackTimeValueFormatter() {
			timeFmt = new SimpleDateFormat("HH:mm");
		}
		@Override
		public String getFormattedValue(float value) {
			try {
				BatteryData.PackStatus entry = batteryData.getPackHistory().get((int)value);
				return timeFmt.format(entry.timeStamp);
			} catch(Exception e) {
				return "";
			}
		}
	};


	private int highlightSetNr = -1;
	private String highlightSetLabel = "";

	private boolean mShowVolt = true;
	private boolean mShowTemp = false;


	// system services:

	private final static Handler mHandler = new Handler(Looper.getMainLooper());

	private CarData mCarData;
	private CmdSeries cmdSeries;
	private AppPrefes appPrefes;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// Init data storage:

		batteryData = new BatteryData();


		// Load prefs:

		appPrefes = new AppPrefes(getActivity(), "ovms");

		mShowVolt = appPrefes.getData("battery_show_volt").equals("on");
		mShowTemp = appPrefes.getData("battery_show_temp").equals("on");
		if (!mShowVolt && !mShowTemp)
			mShowVolt = true;


		// Setup UI:

		ProgressOverlay progressOverlay = createProgressOverlay(inflater, container, false);
		progressOverlay.setOnCancelListener(this);

		View rootView = inflater.inflate(R.layout.fragment_battery, null);


		//
		// Setup Cell status chart:
		//

		XAxis xAxis;
		YAxis yAxis;
		Description description;

		cellChart = (CandleStickChart) rootView.findViewById(R.id.chart_cells);
		description = cellChart.getDescription();
		description.setText(getString(R.string.battery_cell_description));
		description.setTextColor(Color.LTGRAY);
		cellChart.setDescription(description);
		cellChart.setDrawGridBackground(false);
		cellChart.setDrawBorders(true);

		xAxis = cellChart.getXAxis();
		xAxis.setTextColor(Color.WHITE);
		xAxis.setValueFormatter(new ValueFormatter() {
			@Override
			public String getFormattedValue(float value) {
				return "#" + ((int)value+1);
			}
		});
		xAxis.setGranularityEnabled(true);
		xAxis.setGranularity(1f);

		yAxis = cellChart.getAxisLeft();
		yAxis.setTextColor(COLOR_VOLT);
		yAxis.setGridColor(COLOR_VOLT_GRID);
		yAxis.setValueFormatter(new DefaultAxisValueFormatter(2));
		yAxis.setGranularity(0.01f);

		yAxis = cellChart.getAxisRight();
		yAxis.setTextColor(COLOR_TEMP);
		yAxis.setGridColor(COLOR_TEMP_GRID);
		yAxis.setValueFormatter(new DefaultAxisValueFormatter(0));


		//
		// Setup Pack history chart:
		//

		packChart = (LineChart) rootView.findViewById(R.id.chart_pack);
		packChart.getDescription().setEnabled(false);
		packChart.setDrawGridBackground(false);
		packChart.setDrawBorders(true);
		packChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
			@Override
			public void onValueSelected(Entry entry, Highlight highlight) {
				// remember user data set selection:
				int dataSet = highlight.getDataSetIndex();
				highlightSetNr = dataSet;
				highlightSetLabel = packChart.getData().getDataSetByIndex(dataSet).getLabel();

				// update seek bar:
				seekPack.setProgress((int)entry.getX()); // fires listener event (fromUser=false)

				// update cell chart:
				showCellStatus((int)entry.getX());
			}

			@Override
			public void onNothingSelected() {
				// nop
			}
		});

		xAxis = packChart.getXAxis();
		xAxis.setTextColor(Color.WHITE);
		xAxis.setValueFormatter(new PackTimeValueFormatter());
		xAxis.setGranularityEnabled(true);
		xAxis.setGranularity(1f);

		yAxis = packChart.getAxisLeft();
		yAxis.setSpaceTop(5f);
		yAxis.setSpaceBottom(5f);
		yAxis.setTextColor(COLOR_SOC_TEXT);
		yAxis.setGridColor(COLOR_SOC_GRID);
		yAxis.setValueFormatter(new DefaultAxisValueFormatter(0) {
			@Override
			public String getFormattedValue(float value) {
				return super.getFormattedValue(value) + "%";
			}
		});

		yAxis = packChart.getAxisRight();
		yAxis.setSpaceTop(15f);
		yAxis.setSpaceBottom(15f);
		yAxis.setTextColor(COLOR_VOLT);
		yAxis.setGridColor(COLOR_VOLT_GRID);
		yAxis.setValueFormatter(new DefaultAxisValueFormatter(1) {
			@Override
			public String getFormattedValue(float value) {
				return super.getFormattedValue(value) + "%";
			}
		});
		yAxis.setGranularity(0.1f);


		//
		// Setup Pack history seek bar:
		//

		seekPack = (SeekBar) rootView.findViewById(R.id.seek_pack);
		seekPack.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int val, boolean fromUser) {
				if (fromUser) {
					// highlight entry:
					highlightPackEntry(val);
					// update cell chart:
					showCellStatus(val);
				}
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// nop
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// nop
			}
		});

		// default data set to highlight:
		highlightSetLabel = getString(R.string.battery_data_soc);

		// attach menu:
		setHasOptionsMenu(true);

		return rootView;
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.battery_chart_options, menu);
		optionsMenu = menu;
		optionsMenu.findItem(R.id.mi_chk_volt).setChecked(mShowVolt);
		optionsMenu.findItem(R.id.mi_chk_temp).setChecked(mShowTemp);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getCompatActivity().setTitle(R.string.battery_title);
		getCompatActivity().getSupportActionBar().setIcon(R.drawable.ic_action_chart);

		// get data of current car:
		mCarData = CarsStorage.INSTANCE.getSelectedCarData();

		// schedule data loader:
		showProgressOverlay(getString(R.string.battery_msg_loading_data));
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// load and display saved vehicle data:
				BatteryData saved = BatteryData.loadFile(mCarData.sel_vehicleid);
				if (saved != null) {
					Log.v(TAG, "BatteryData loaded for " + mCarData.sel_vehicleid);
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
				cmdSeries = new CmdSeries(getService(), BatteryFragment.this)
						.add(R.string.battery_msg_get_status, "206") // ensure non-empty history
						.add(R.string.battery_msg_get_battpack, "32,RT-BAT-P")
						.add(R.string.battery_msg_get_battcell, "32,RT-BAT-C")
						.start();
				return true;

			case R.id.mi_reset_view:
				packChart.fitScreen();
				cellChart.fitScreen();
				return true;

			case R.id.mi_help:
				new AlertDialog.Builder(getActivity())
						.setTitle(R.string.battery_btn_help)
						.setMessage(Html.fromHtml(getString(R.string.battery_help)))
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
		return (batteryData != null
				&& batteryData.getCellCount() != 0
				&& batteryData.getPackHistory() != null
				&& batteryData.getPackHistory().size() > 0);
	}

	/**
	 * Check data model and pack index validity
	 */
	private boolean isPackIndexValid(int index) {
		return (isPackValid()
				&& index < batteryData.getPackHistory().size());
	}


	/**
	 * Call when underlying batteryData object ready/changed
	 */
	public void dataSetChanged() {

		if (!isPackValid())
			return;

		// update pack chart:
		showPackHistory();

		int lastEntry = batteryData.getPackHistory().size() - 1;

		// update seek bar:
		seekPack.setMax(lastEntry);
		seekPack.setProgress(lastEntry);

		// highlight latest entry:
		highlightPackEntry(lastEntry);

		// show latest cell status:
		showCellStatus(lastEntry);

	}


	/**
	 * Call when user changed data filter (i.e. show Volt / Temp sets)
	 */
	private void dataFilterChanged() {

		// save prefs:
		appPrefes.saveData("battery_show_volt", mShowVolt ? "on" : "off");
		appPrefes.saveData("battery_show_temp", mShowTemp ? "on" : "off");

		// check data status:
		if (!isPackValid())
			return;

		// update pack chart:
		showPackHistory();

		int seekEntry = seekPack.getProgress();

		// highlight last selected entry:
		highlightSetNr = -1; // ...
		highlightPackEntry(seekEntry);

		// show last selected entry cell status:
		showCellStatus(seekEntry);

	}


	/**
	 * Update the pack chart
	 */
	public void showPackHistory() {

		if (!isPackValid())
			return;

		ArrayList<BatteryData.PackStatus> packHistory = batteryData.getPackHistory();
		BatteryData.PackStatus packStatus, lastStatus;
		SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm");


		//
		// Pack chart:
		//

		// create value arrays:

		ArrayList<LimitLine> xSections = new ArrayList<LimitLine>();
		ArrayList<Entry> socValues = new ArrayList<Entry>();
		ArrayList<Entry> voltValues = new ArrayList<Entry>();
		ArrayList<Entry> voltMinValues = new ArrayList<Entry>();
		ArrayList<Entry> tempValues = new ArrayList<Entry>();

		lastStatus = null;

		for (int i = 0; i < packHistory.size(); i++) {

			packStatus = packHistory.get(i);

			float xpos = i;

			socValues.add(new Entry(xpos, packStatus.soc));
			voltValues.add(new Entry(xpos, packStatus.volt));
			voltMinValues.add(new Entry(xpos, packStatus.voltMin));
			tempValues.add(new Entry(xpos, packStatus.temp));

			// add section markers:
			if (packStatus.isNewSection(lastStatus)) {
				LimitLine l = new LimitLine(xpos);
				l.setLabel(timeFmt.format(packStatus.timeStamp));
				l.setTextColor(Color.WHITE);
				l.setTextStyle(Paint.Style.FILL);
				l.setTextSize(6f);
				l.enableDashedLine(3f, 2f, 0f);
				xSections.add(l);
			}

			lastStatus = packStatus;
		}


		// create data sets:

		ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
		LineDataSet dataSet;

		packTempSet = null;
		packVoltSet = null;

		if (mShowTemp) {
			packTempSet = dataSet = new LineDataSet(tempValues, getString(R.string.battery_data_temp));
			dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
			dataSet.setColor(COLOR_TEMP);
			dataSet.setLineWidth(3f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSets.add(dataSet);
		}

		if (mShowVolt) {
			packVoltMinSet = dataSet = new LineDataSet(voltMinValues, getString(R.string.battery_data_volt_min));
			dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
			dataSet.setColor(COLOR_VOLT_MIN);
			dataSet.setLineWidth(2f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSets.add(dataSet);

			packVoltSet = dataSet = new LineDataSet(voltValues, getString(R.string.battery_data_volt));
			dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
			dataSet.setColor(COLOR_VOLT);
			dataSet.setLineWidth(3f);
			dataSet.setDrawCircles(false);
			dataSet.setDrawValues(false);
			dataSets.add(dataSet);
		}

		dataSet = new LineDataSet(socValues, getString(R.string.battery_data_soc));
		dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
		dataSet.setColor(COLOR_SOC_LINE);
		dataSet.setLineWidth(4f);
		dataSet.setDrawCircles(false);
		dataSet.setDrawValues(false);
		dataSets.add(dataSet);


		// display data sets:

		LineData data;
		packData = data = new LineData(dataSets);
		data.setValueTextColor(Color.WHITE);
		data.setValueTextSize(9f);

		packChart.setData(data);

		XAxis xAxis = packChart.getXAxis();
		xAxis.removeAllLimitLines();
		for (int i=0; i < xSections.size(); i++) {
			LimitLine l = xSections.get(i);
			if (i < xSections.size()/2)
				l.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
			else
				l.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
			xAxis.addLimitLine(l);
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

		if (!isPackIndexValid(index))
			return;

		// check model status:
		if (batteryData == null || batteryData.getPackHistory() == null
				|| index >= batteryData.getPackHistory().size()) {
			Log.e(TAG, "highlighPackEntry: #" + index + " out of bounds");
			return;
		}

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
		BatteryData.PackStatus packStatus = batteryData.getPackHistory().get(index);
		packChart.highlightValue(index, highlightSetNr); // does not fire listener event

		// center highlight in chart viewport:
		ILineDataSet dataSet = packChart.getData().getDataSetByIndex(highlightSetNr);
		if (dataSet != null) {
			Entry entry = dataSet.getEntryForXValue(index, 0);
			if (entry != null)
				packChart.centerViewTo(entry.getX(), entry.getY(), dataSet.getAxisDependency());
		}

	}


	/**
	 * Update the cell chart
	 *
	 * @param index - pack history index to display
	 */
	private void showCellStatus(int index) {

		if (!isPackIndexValid(index))
			return;

		ArrayList<BatteryData.PackStatus> packHistory = batteryData.getPackHistory();
		BatteryData.PackStatus pack;
		ArrayList<BatteryData.CellStatus> cells;
		BatteryData.CellStatus cell;

		pack = packHistory.get(index);
		cells = pack.cells;
		if (cells == null) {
			Log.w(TAG, "showCellStatus x=" + index + ": cells=null");
			return;
		}


		// create value arrays:

		ArrayList<CandleEntry> voltValues = new ArrayList<CandleEntry>();
		ArrayList<CandleEntry> tempValues = new ArrayList<CandleEntry>();
		float low, high, open, close;

		for (int i = 0; i < batteryData.getCellCount(); i++) {

			cell = cells.get(i);

			// Volt: high=current, low=min

			high = cell.volt;
			low = cell.voltMin;

			if (cell.voltDevMax < 0) {
				// bad: cell voltage breaks more down than normal
				// => filled candle (close < open)
				open = high;
				close = high + cell.voltDevMax;
				if (close < low)
					low = close;
			} else {
				// ok:
				open = high - cell.voltDevMax;
				close = high;
				if (open < low)
					low = open;
			}

			voltValues.add(new CandleEntry(i, high, low, open, close));

			// Temp: high=max, low=min

			open = cell.temp + (cell.tempDevMax / 2.1f);
			close = cell.temp - (cell.tempDevMax / 2.1f);
			high = Math.max(cell.tempMax, Math.max(open, close));
			low = Math.min(cell.tempMin, Math.min(open, close));

			tempValues.add(new CandleEntry(i, high, low, open, close));

		}


		// create data sets:

		ArrayList<ICandleDataSet> dataSets = new ArrayList<ICandleDataSet>();
		CandleDataSet dataSet;

		if (mShowTemp) {
			dataSet = new CandleDataSet(tempValues, getString(R.string.battery_data_temp));
			dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
			dataSet.setNeutralColor(COLOR_TEMP);
			dataSet.setIncreasingColor(COLOR_TEMP);
			dataSet.setDecreasingColor(COLOR_TEMP);
			dataSet.setShadowColor(COLOR_TEMP);
			dataSet.setDrawValues(true);
			dataSet.setShadowWidth(4f);
			dataSet.setValueFormatter(new ValueFormatter() {
				@Override
				public String getFormattedValue(float value) {
					return String.format("%.0f", value);
				}
			});
			dataSets.add(dataSet);
		}

		if (mShowVolt) {
			dataSet = new CandleDataSet(voltValues, getString(R.string.battery_data_volt));
			dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
			dataSet.setNeutralColor(COLOR_VOLT);
			dataSet.setIncreasingColor(COLOR_VOLT);
			dataSet.setDecreasingColor(COLOR_VOLT);
			dataSet.setShadowColor(COLOR_VOLT);
			dataSet.setDrawValues(true);
			dataSet.setShadowWidth(4f);
			dataSet.setValueFormatter(new ValueFormatter() {
				@Override
				public String getFormattedValue(float value) {
					return String.format("%.3f", value);
				}
			});
			dataSets.add(dataSet);
		}


		// configure y axes:

		YAxis yAxis = cellChart.getAxisLeft();
		yAxis.setEnabled(mShowVolt);
		if (mShowVolt && (packVoltSet != null)) {
			float yMax = packVoltSet.getYMax() / (float)batteryData.getCellCount() + 0.1f;
			float yMin = packVoltMinSet.getYMin() / (float)batteryData.getCellCount() - 0.1f;
			yAxis.setAxisMaximum(yMax);
			if (mShowTemp)
				yAxis.setAxisMinimum(yMin-(yMax-yMin)); // half height
			else
				yAxis.setAxisMinimum(yMin); // full height
		}

		yAxis = cellChart.getAxisRight();
		yAxis.setEnabled(mShowTemp);
		if (mShowTemp && (packTempSet != null)) {
			float yMax = packTempSet.getYMax() + 3f;
			float yMin = packTempSet.getYMin() - 3f;
			if (mShowVolt)
				yAxis.setAxisMaximum(yMax + (yMax - yMin)); // half height
			else
				yAxis.setAxisMaximum(yMax); // full height
			yAxis.setAxisMinimum(yMin);
		}


		// display data sets:

		CandleData data = new CandleData(dataSets);
		data.setValueTextColor(Color.WHITE);
		data.setValueTextSize(9f);

		cellChart.setData(data);
		cellChart.getLegend().setTextColor(Color.WHITE);
		cellChart.invalidate();

	}


}

