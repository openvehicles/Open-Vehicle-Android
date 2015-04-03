package com.openvehicles.OVMS.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.entities.CmdSeries;
import com.openvehicles.OVMS.entities.LogsData;
import com.openvehicles.OVMS.ui.BaseFragment;
import com.openvehicles.OVMS.ui.utils.ProgressOverlay;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.CarsStorage;

import java.util.ArrayList;


/**
 * Created by balzer on 07.03.15.
 *
 * This is the user interface for the SEVCON diagnostic logs
 * as provided by the Twizy firmware.
 *
 * It allows to query, fetch and reset the SEVCON log classes
 * alerts, fault events, system events, counters and min/max sensors.
 *
 * Storage:		LogsData
 * Layouts:		fragment_logs, dlg_logs_cmd
 *
 * TODO: check if the TableAdapter class should become a common class
 *
 *
 */
public class LogsFragment extends BaseFragment
		implements View.OnClickListener, CmdSeries.Listener, ProgressOverlay.OnCancelListener {
	private static final String TAG = "LogsFragment";


	// Data storage:

	private LogsData logsData;

	private int mEditPosition;
	private CarData mCarData;


	// System:

	private LayoutInflater layoutInflater;
	private CmdSeries cmdSeries;


	// UI:

	private TextView
			listAlertsInfo,
			listFaultEventsInfo,
			listSystemEventsInfo,
			listCountersInfo,
			listMinMaxesInfo;

	private ListView
			listAlerts,
			listFaultEvents,
			listSystemEvents,
			listCounters,
			listMinMaxes;

	private TableAdapter<LogsData.Alert> listAlertsAdapter;
	private TableAdapter<LogsData.Event> listFaultEventsAdapter;
	private TableAdapter<LogsData.Event> listSystemEventsAdapter;
	private TableAdapter<LogsData.Counter> listCountersAdapter;
	private TableAdapter<LogsData.MinMax> listMinMaxesAdapter;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// Init data storage:

		logsData = new LogsData();


		// Setup UI:

		layoutInflater = inflater;

		ProgressOverlay progressOverlay = createProgressOverlay(inflater, container, false);
		progressOverlay.setOnCancelListener(this);

		View rootView = inflater.inflate(R.layout.fragment_logs, null);


		// Setup TabHost:

		TabHost tabHost = (TabHost) rootView.findViewById(R.id.tabHost);
		tabHost.setup();

		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setContent(R.id.tab1)
				.setIndicator(getString(R.string.logs_lb_alerts)));

		tabHost.addTab(tabHost.newTabSpec("tab2")
				.setContent(R.id.tab2)
				.setIndicator(getString(R.string.logs_lb_faults)));

		tabHost.addTab(tabHost.newTabSpec("tab3")
				.setContent(R.id.tab3)
				.setIndicator(getString(R.string.logs_lb_events)));

		tabHost.addTab(tabHost.newTabSpec("tab4")
				.setContent(R.id.tab4)
				.setIndicator(getString(R.string.logs_lb_counter)));

		tabHost.addTab(tabHost.newTabSpec("tab5")
				.setContent(R.id.tab5)
				.setIndicator(getString(R.string.logs_lb_minmax)));


		LinearLayout tab;


		// Setup ListView "Alerts":

		tab = (LinearLayout) tabHost.findViewById(R.id.tab1);
		listAlertsInfo = (TextView) tab.findViewById(R.id.listInfo1);
		listAlerts = (ListView) tab.findViewById(R.id.listView1);

		listAlertsAdapter = new TableAdapter<LogsData.Alert>(container.getContext(),
				logsData.alerts) {
			@Override
			public void makeColumns() {
				addColumn(getString(R.string.logs_th_code), 1, Gravity.LEFT);
				addColumn(getString(R.string.logs_th_description), 3, Gravity.LEFT);
			}
			@Override
			public String getValue(LogsData.Alert item, int row, int column) {
				switch (column) {
					case 0:
						return item.code;
					case 1:
						return item.description;
					default:
						return "";
				}
			}
		};

		((LinearLayout)listAlerts.getParent()).addView(
				listAlertsAdapter.makeRow(container.getContext(), TableAdapter.HEAD), 0);
		listAlerts.setAdapter(listAlertsAdapter);


		// Setup ListView "Faults":

		tab = (LinearLayout) tabHost.findViewById(R.id.tab2);
		listFaultEventsInfo = (TextView) tab.findViewById(R.id.listInfo2);
		listFaultEvents = (ListView) tab.findViewById(R.id.listView2);

		listFaultEventsAdapter = new TableAdapter<LogsData.Event>(container.getContext(),
				logsData.faultEvents) {
			@Override
			public Object getItem(int i) {
				// reverse order:
				return mItems.get(mItems.size() - 1 - i);
			}

			@Override
			public void makeColumns() {
				addColumn(getString(R.string.logs_th_code), 1, Gravity.LEFT);
				addColumn(getString(R.string.logs_th_description), 3, Gravity.LEFT);
				addColumn(getString(R.string.logs_th_keytime), 2, Gravity.CENTER_HORIZONTAL);
				addColumn(getString(R.string.logs_th_data, 1), 1, Gravity.LEFT);
				addColumn(getString(R.string.logs_th_data, 2), 1, Gravity.LEFT);
				addColumn(getString(R.string.logs_th_data, 3), 1, Gravity.LEFT);
			}
			@Override
			public String getValue(LogsData.Event item, int row, int column) {
				switch (column) {
					case 0:
						return item.code;
					case 1:
						return item.description;
					case 2:
						return (item.time != null) ? item.time.fmtKeyTime() : "";
					default:
						if (item.data != null && item.data.length > column)
							return item.data[column];
						return "";
				}
			}
		};

		((LinearLayout)listFaultEvents.getParent()).addView(
				listFaultEventsAdapter.makeRow(container.getContext(), TableAdapter.HEAD), 0);
		listFaultEvents.setAdapter(listFaultEventsAdapter);


		// Setup ListView "Events":

		tab = (LinearLayout) tabHost.findViewById(R.id.tab3);
		listSystemEventsInfo = (TextView) tab.findViewById(R.id.listInfo3);
		listSystemEvents = (ListView) tab.findViewById(R.id.listView3);

		listSystemEventsAdapter = new TableAdapter<LogsData.Event>(container.getContext(),
				logsData.systemEvents) {
			@Override
			public Object getItem(int i) {
				// reverse order:
				return mItems.get(mItems.size() - 1 - i);
			}

			@Override
			public void makeColumns() {
				addColumn(getString(R.string.logs_th_code), 1, Gravity.LEFT);
				addColumn(getString(R.string.logs_th_description), 3, Gravity.LEFT);
				addColumn(getString(R.string.logs_th_keytime), 2, Gravity.CENTER_HORIZONTAL);
				addColumn(getString(R.string.logs_th_data, 1), 1, Gravity.LEFT);
				addColumn(getString(R.string.logs_th_data, 2), 1, Gravity.LEFT);
				addColumn(getString(R.string.logs_th_data, 3), 1, Gravity.LEFT);
			}
			@Override
			public String getValue(LogsData.Event item, int row, int column) {
				switch (column) {
					case 0:
						return item.code;
					case 1:
						return item.description;
					case 2:
						return (item.time != null) ? item.time.fmtKeyTime() : "";
					default:
						if (item.data != null && item.data.length > column)
							return item.data[column];
						return "";
				}
			}
		};

		((LinearLayout)listSystemEvents.getParent()).addView(
				listSystemEventsAdapter.makeRow(container.getContext(), TableAdapter.HEAD), 0);
		listSystemEvents.setAdapter(listSystemEventsAdapter);


		// Setup ListView "Counters":

		tab = (LinearLayout) tabHost.findViewById(R.id.tab4);
		listCountersInfo = (TextView) tab.findViewById(R.id.listInfo4);
		listCounters = (ListView) tab.findViewById(R.id.listView4);

		listCountersAdapter = new TableAdapter<LogsData.Counter>(container.getContext(),
				logsData.counters) {
			@Override
			public void makeColumns() {
				addColumn(getString(R.string.logs_th_code), 1, Gravity.LEFT);
				addColumn(getString(R.string.logs_th_description), 3, Gravity.LEFT);
				addColumn(getString(R.string.logs_th_lasttime), 2, Gravity.CENTER_HORIZONTAL);
				addColumn(getString(R.string.logs_th_firsttime), 2, Gravity.CENTER_HORIZONTAL);
				addColumn(getString(R.string.logs_th_count), 1, Gravity.CENTER_HORIZONTAL);
			}
			@Override
			public String getValue(LogsData.Counter item, int row, int column) {
				switch (column) {
					case 0:
						return item.code;
					case 1:
						return item.description;
					case 2:
						return (item.lastTime != null) ? item.lastTime.fmtKeyTime() : "";
					case 3:
						return (item.firstTime != null) ? item.firstTime.fmtKeyTime() : "";
					case 4:
						return "" + item.count;
					default:
						return "";
				}
			}
		};

		((LinearLayout)listCounters.getParent()).addView(
				listCountersAdapter.makeRow(container.getContext(), TableAdapter.HEAD), 0);
		listCounters.setAdapter(listCountersAdapter);


		// Setup ListView "MinMax":

		tab = (LinearLayout) tabHost.findViewById(R.id.tab5);
		listMinMaxesInfo = (TextView) tab.findViewById(R.id.listInfo5);
		listMinMaxes = (ListView) tab.findViewById(R.id.listView5);

		listMinMaxesAdapter = new TableAdapter<LogsData.MinMax>(container.getContext(),
				logsData.minMaxes) {
			private LogsData.MinMax dummy = new LogsData.MinMax();
			private String[] sensorNames = getResources().getStringArray(R.array.logs_th_sensornames);

			@Override
			public int getCount() {
				return 10; // number of sensors
			}

			@Override
			public Object getItem(int i) {
				// horizontal layout needs all items per row
				return dummy;
			}

			@Override
			public void makeColumns() {
				addColumn(getString(R.string.logs_th_sensor), 3, Gravity.LEFT);
				addColumn(getString(R.string.logs_th_user), 1, Gravity.CENTER);
				addColumn(getString(R.string.logs_th_sevcon), 1, Gravity.CENTER);
			}

			@Override
			public String getValue(LogsData.MinMax item, int row, int column) {
				switch (column) {
					case 0:
						return sensorNames[row];
					case 1:
						item = (mItems.size() > 0) ? mItems.get(0) : null;
						return (item != null) ? item.getSensor(row) : "";
					case 2:
						item = (mItems.size() > 1) ? mItems.get(1) : null;
						return (item != null) ? item.getSensor(row) : "";
					default:
						return "";
				}
			}
		};

		((LinearLayout)listMinMaxes.getParent()).addView(
				listMinMaxesAdapter.makeRow(container.getContext(), TableAdapter.HEAD), 0);
		listMinMaxes.setAdapter(listMinMaxesAdapter);


		// Done creating View:

		return rootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getSherlockActivity().setTitle(R.string.logs_title);

		// get data of car to edit:
		mEditPosition = getArguments().getInt("position", -1);
		if (mEditPosition >= 0) {
			mCarData = CarsStorage.get().getStoredCars().get(mEditPosition);
		}

		// load vehicle logs data:
		LogsData saved = LogsData.loadFile(mCarData.sel_vehicleid);
		if (saved != null) {
			//Log.v(TAG, "LogsData loaded for " + mCarData.sel_vehicleid);
			logsData = saved;
			updateUi();
		}

		View rootView = getView();
		Ui.setOnClick(rootView, R.id.btn_get_logs, this);
		Ui.setOnClick(rootView, R.id.btn_reset_logs, this);
	}


	@Override
	public void onClick(View view) {
		View content;

		switch (view.getId()) {

			case R.id.btn_get_logs:
				content = layoutInflater.inflate(R.layout.dlg_logs_cmd, null);
				new AlertDialog.Builder(getActivity())
						.setTitle(R.string.logs_btn_get)
						.setView(content)
						.setNegativeButton(R.string.Cancel, null)
						.setPositiveButton(android.R.string.ok,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface pDlg, int pWhich) {
										Dialog dlg = (Dialog) pDlg;

										ToggleButton tbAlerts = (ToggleButton) dlg.findViewById(R.id.tbAlerts);
										ToggleButton tbFaults = (ToggleButton) dlg.findViewById(R.id.tbFaults);
										ToggleButton tbEvents = (ToggleButton) dlg.findViewById(R.id.tbEvents);
										ToggleButton tbCounter = (ToggleButton) dlg.findViewById(R.id.tbCounter);
										ToggleButton tbMinMax = (ToggleButton) dlg.findViewById(R.id.tbMinMax);

										cmdSeries = new CmdSeries(getService(), LogsFragment.this);

										if (tbAlerts.isChecked()) {
											cmdSeries.add(R.string.logs_msg_query_alerts, "210,1");
										}
										if (tbFaults.isChecked()) {
											cmdSeries.add(R.string.logs_msg_query_faults, "210,2,0")
													.add(R.string.logs_msg_query_faults, "210,2,10")
													.add(R.string.logs_msg_query_faults, "210,2,20")
													.add(R.string.logs_msg_query_faults, "210,2,30");
										}
										if (tbEvents.isChecked()) {
											cmdSeries.add(R.string.logs_msg_query_events, "210,3,0")
													.add(R.string.logs_msg_query_events, "210,3,10");
										}
										if (tbCounter.isChecked()) {
											cmdSeries.add(R.string.logs_msg_query_counter, "210,4");
										}
										if (tbMinMax.isChecked()) {
											cmdSeries.add(R.string.logs_msg_query_minmax, "210,5");
										}

										if (cmdSeries.size() > 0) {
											cmdSeries.add(R.string.logs_msg_fetch_keytime, "32,RT-ENG-LogKeyTime");

											if (tbAlerts.isChecked()) {
												cmdSeries.add(R.string.logs_msg_fetch_alerts, "32,RT-ENG-LogAlerts");
											}
											if (tbFaults.isChecked()) {
												cmdSeries.add(R.string.logs_msg_fetch_faults, "32,RT-ENG-LogFaults");
											}
											if (tbEvents.isChecked()) {
												cmdSeries.add(R.string.logs_msg_fetch_events, "32,RT-ENG-LogSystem");
											}
											if (tbCounter.isChecked()) {
												cmdSeries.add(R.string.logs_msg_fetch_counter, "32,RT-ENG-LogCounts");
											}
											if (tbMinMax.isChecked()) {
												cmdSeries.add(R.string.logs_msg_fetch_minmax, "32,RT-ENG-LogMinMax");
											}

											cmdSeries.start();
										}
									}
								})
						.show();
				break;

			case R.id.btn_reset_logs:
				content = layoutInflater.inflate(R.layout.dlg_logs_cmd, null);
				content.findViewById(R.id.tbAlerts).setVisibility(View.GONE);
				new AlertDialog.Builder(getActivity())
						.setTitle(R.string.logs_btn_reset)
						.setView(content)
						.setNegativeButton(R.string.Cancel, null)
						.setPositiveButton(android.R.string.ok,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface pDlg, int pWhich) {
										Dialog dlg = (Dialog) pDlg;

										ToggleButton tbFaults = (ToggleButton) dlg.findViewById(R.id.tbFaults);
										ToggleButton tbEvents = (ToggleButton) dlg.findViewById(R.id.tbEvents);
										ToggleButton tbCounter = (ToggleButton) dlg.findViewById(R.id.tbCounter);
										ToggleButton tbMinMax = (ToggleButton) dlg.findViewById(R.id.tbMinMax);

										cmdSeries = new CmdSeries(getService(), LogsFragment.this);

										if (tbFaults.isChecked()) {
											cmdSeries.add(R.string.logs_msg_reset_faults, "209,2");
										}
										if (tbEvents.isChecked()) {
											cmdSeries.add(R.string.logs_msg_reset_events, "209,3");
										}
										if (tbCounter.isChecked()) {
											cmdSeries.add(R.string.logs_msg_reset_counter, "209,4");
										}
										if (tbMinMax.isChecked()) {
											cmdSeries.add(R.string.logs_msg_reset_minmax, "209,5");
										}

										if (cmdSeries.size() > 0) {
											cmdSeries.start();
										}
									}
								})
						.show();
				break;

		}
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

			case 0: // ok
				showProgressOverlay(getString(R.string.msg_processing_data));
				logsData.processCmdResults(cmdSeries);
				logsData.saveFile(mCarData.sel_vehicleid);
				updateUi();
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


	public void updateUi() {

		// Data ready/changed: show

		listAlertsInfo.setText(getString(R.string.logs_fmt_listinfo,
				logsData.alertsTime.fmtTimeStamp(),
				logsData.alertsTime.fmtKeyTime()));
		listAlertsAdapter.setItems(logsData.alerts);

		listFaultEventsInfo.setText(getString(R.string.logs_fmt_listinfo,
				logsData.faultEventsTime.fmtTimeStamp(),
				logsData.faultEventsTime.fmtKeyTime()));
		listFaultEventsAdapter.setItems(logsData.faultEvents);

		listSystemEventsInfo.setText(getString(R.string.logs_fmt_listinfo,
				logsData.systemEventsTime.fmtTimeStamp(),
				logsData.systemEventsTime.fmtKeyTime()));
		listSystemEventsAdapter.setItems(logsData.systemEvents);

		listCountersInfo.setText(getString(R.string.logs_fmt_listinfo,
				logsData.countersTime.fmtTimeStamp(),
				logsData.countersTime.fmtKeyTime()));
		listCountersAdapter.setItems(logsData.counters);

		listMinMaxesInfo.setText(getString(R.string.logs_fmt_listinfo,
				logsData.minMaxesTime.fmtTimeStamp(),
				logsData.minMaxesTime.fmtKeyTime()));
		listMinMaxesAdapter.setItems(logsData.minMaxes);

	}


	public abstract class TableAdapter<T> extends BaseAdapter {

		protected ArrayList<T> mItems;

		private class ColumnDef {
			String title;
			float weight;
			int gravity;

			private ColumnDef(String title, float weight, int gravity) {
				this.title = title;
				this.weight = weight;
				this.gravity = gravity;
			}
		}

		public static final boolean HEAD = true;
		public static final boolean BODY = false;

		private ArrayList<ColumnDef> columns;


		public TableAdapter(Context context, ArrayList<T> items) {
			super();
			mItems = items;
			columns = new ArrayList<ColumnDef>(10);
			makeColumns();
		}

		public void addColumn(String title, float weight, int gravity) {
			columns.add(new ColumnDef(title, weight, gravity));
		}

		// insert addColumn() calls in implementation of...
		public abstract void makeColumns();

		// insert data mapping in implementation of...
		public abstract String getValue(T item, int row, int column);

		public void setItems(ArrayList<T> items) {
			mItems = items;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int i) {
			return mItems.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LinearLayout row = (LinearLayout) convertView;
			if (row == null) {
				row = (LinearLayout) makeRow(parent.getContext(), BODY);
			}

			T item = (T) getItem(position);
			if (item != null) {
				TextView textView;

				for (int col = 0; col < columns.size(); col++) {
					textView = (TextView) row.getChildAt(col);
					if (textView != null) {
						textView.setText(getValue(item, position, col));
					}
				}
			}

			return row;
		}


		public View makeRow(Context context, boolean isHeader) {

			LinearLayout row = new LinearLayout(context);
			row.setOrientation(LinearLayout.HORIZONTAL);
			row.setPadding(2, 2, 2, 2);

			if (isHeader) {
				row.setBackgroundResource(
						R.drawable.abs__list_selector_background_transition_holo_light);
			}

			LinearLayout.LayoutParams layout;
			TextView textView;
			ColumnDef columnDef;

			for (int col = 0; col < columns.size(); col++) {
				columnDef = columns.get(col);

				layout = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.gravity = columnDef.gravity;
				layout.weight = columnDef.weight;

				textView = new TextView(context);
				textView.setLayoutParams(layout);
				textView.setPadding(2, 2, 2, 2);
				// TODO: gravity does not work. setTextAlignment() necessary but needs API >= 17

				if (isHeader) {
					textView.setTypeface(Typeface.DEFAULT_BOLD);
					textView.setText(columnDef.title);
				}

				row.addView(textView);
			}

			return row;
		}


	}


}
