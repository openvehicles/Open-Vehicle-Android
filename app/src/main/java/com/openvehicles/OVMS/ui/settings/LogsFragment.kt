package com.openvehicles.OVMS.ui.settings

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TabHost
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.entities.CmdSeries
import com.openvehicles.OVMS.entities.LogsData
import com.openvehicles.OVMS.entities.LogsData.Companion.loadFile
import com.openvehicles.OVMS.entities.LogsData.MinMax
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.utils.ProgressOverlay
import com.openvehicles.OVMS.ui.utils.Ui.setOnClick
import com.openvehicles.OVMS.utils.CarsStorage.getSelectedCarData
import com.openvehicles.OVMS.utils.CarsStorage.getStoredCars

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
 */
class LogsFragment
    : BaseFragment(), View.OnClickListener, CmdSeries.Listener, ProgressOverlay.OnCancelListener {

    // Data storage:
    private var logsData: LogsData? = null
    private var editPosition = 0
    private var carData: CarData? = null

    // System:
    private var layoutInflater: LayoutInflater? = null
    private var cmdSeries: CmdSeries? = null

    // UI:
    private var listAlertsInfo: TextView? = null
    private var listFaultEventsInfo: TextView? = null
    private var listSystemEventsInfo: TextView? = null
    private var listCountersInfo: TextView? = null
    private var listMinMaxesInfo: TextView? = null
    private var listAlerts: ListView? = null
    private var listFaultEvents: ListView? = null
    private var listSystemEvents: ListView? = null
    private var listCounters: ListView? = null
    private var listMinMaxes: ListView? = null
    private var listAlertsAdapter: TableAdapter<LogsData.Alert>? = null
    private var listFaultEventsAdapter: TableAdapter<LogsData.Event>? = null
    private var listSystemEventsAdapter: TableAdapter<LogsData.Event>? = null
    private var listCountersAdapter: TableAdapter<LogsData.Counter>? = null
    private var listMinMaxesAdapter: TableAdapter<MinMax>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Init data storage:
        logsData = LogsData()

        // Setup UI:
        layoutInflater = inflater
        val rootView = inflater.inflate(R.layout.fragment_logs, null)
        val progressOverlay = createProgressOverlay(rootView, false)
        progressOverlay.setOnCancelListener(this)

        // Setup TabHost:
        val tabHost = rootView.findViewById<View>(R.id.tabHost) as TabHost
        tabHost.setup()
        tabHost.addTab(
            tabHost.newTabSpec("tab1")
                .setContent(R.id.tab1)
                .setIndicator(getString(R.string.logs_lb_alerts))
        )
        tabHost.addTab(
            tabHost.newTabSpec("tab2")
                .setContent(R.id.tab2)
                .setIndicator(getString(R.string.logs_lb_faults))
        )
        tabHost.addTab(
            tabHost.newTabSpec("tab3")
                .setContent(R.id.tab3)
                .setIndicator(getString(R.string.logs_lb_events))
        )
        tabHost.addTab(
            tabHost.newTabSpec("tab4")
                .setContent(R.id.tab4)
                .setIndicator(getString(R.string.logs_lb_counter))
        )
        tabHost.addTab(
            tabHost.newTabSpec("tab5")
                .setContent(R.id.tab5)
                .setIndicator(getString(R.string.logs_lb_minmax))
        )

        // Setup ListView "Alerts":
        var tab: LinearLayout = tabHost.findViewById<View>(R.id.tab1) as LinearLayout
        listAlertsInfo = tab.findViewById<View>(R.id.listInfo1) as TextView
        listAlerts = tab.findViewById<View>(R.id.listView1) as ListView
        listAlertsAdapter = object : TableAdapter<LogsData.Alert>(logsData!!.alerts) {

            override fun makeColumns() {
                addColumn(getString(R.string.logs_th_code), 1f, Gravity.LEFT)
                addColumn(getString(R.string.logs_th_description), 3f, Gravity.LEFT)
            }

            override fun getValue(item: LogsData.Alert, row: Int, column: Int): String? {
                return when (column) {
                    0 -> item.code
                    1 -> item.description
                    else -> ""
                }
            }
        }
        (listAlerts!!.parent as LinearLayout).addView(
            (listAlertsAdapter as TableAdapter<LogsData.Alert>).makeRow(container?.context, HEAD),
            0
        )
        listAlerts!!.setAdapter(listAlertsAdapter)

        // Setup ListView "Faults":
        tab = tabHost.findViewById<View>(R.id.tab2) as LinearLayout
        listFaultEventsInfo = tab.findViewById<View>(R.id.listInfo2) as TextView
        listFaultEvents = tab.findViewById<View>(R.id.listView2) as ListView
        listFaultEventsAdapter = object : TableAdapter<LogsData.Event>(logsData!!.faultEvents) {

            override fun getItem(i: Int): Any {
                // reverse order:
                return items[items.size - 1 - i]
            }

            override fun makeColumns() {
                addColumn(getString(R.string.logs_th_code), 1f, Gravity.LEFT)
                addColumn(getString(R.string.logs_th_description), 3f, Gravity.LEFT)
                addColumn(getString(R.string.logs_th_keytime), 2f, Gravity.CENTER_HORIZONTAL)
                addColumn(getString(R.string.logs_th_data, 1), 1f, Gravity.LEFT)
                addColumn(getString(R.string.logs_th_data, 2), 1f, Gravity.LEFT)
                addColumn(getString(R.string.logs_th_data, 3), 1f, Gravity.LEFT)
            }

            override fun getValue(item: LogsData.Event, row: Int, column: Int): String? {
                return when (column) {
                    0 -> item.code
                    1 -> item.description
                    2 -> if (item.time != null) item.time!!.fmtKeyTime() else ""
                    else -> {
                        if (item.data.size > column) item.data[column] else ""
                    }
                }
            }
        }
        (listFaultEvents!!.parent as LinearLayout).addView(
            (listFaultEventsAdapter as TableAdapter<LogsData.Event>)
                .makeRow(container?.context, HEAD),
            0
        )
        listFaultEvents!!.setAdapter(listFaultEventsAdapter)

        // Setup ListView "Events":
        tab = tabHost.findViewById<View>(R.id.tab3) as LinearLayout
        listSystemEventsInfo = tab.findViewById<View>(R.id.listInfo3) as TextView
        listSystemEvents = tab.findViewById<View>(R.id.listView3) as ListView
        listSystemEventsAdapter = object : TableAdapter<LogsData.Event>(logsData!!.systemEvents) {

            override fun getItem(i: Int): Any {
                // reverse order:
                return items[items.size - 1 - i]
            }

            override fun makeColumns() {
                addColumn(getString(R.string.logs_th_code), 1f, Gravity.LEFT)
                addColumn(getString(R.string.logs_th_description), 3f, Gravity.LEFT)
                addColumn(getString(R.string.logs_th_keytime), 2f, Gravity.CENTER_HORIZONTAL)
                addColumn(getString(R.string.logs_th_data, 1), 1f, Gravity.LEFT)
                addColumn(getString(R.string.logs_th_data, 2), 1f, Gravity.LEFT)
                addColumn(getString(R.string.logs_th_data, 3), 1f, Gravity.LEFT)
            }

            override fun getValue(item: LogsData.Event, row: Int, column: Int): String? {
                return when (column) {
                    0 -> item.code
                    1 -> item.description
                    2 -> if (item.time != null) item.time!!.fmtKeyTime() else ""
                    else -> {
                        if (item.data.size > column) item.data[column] else ""
                    }
                }
            }
        }
        (listSystemEvents!!.parent as LinearLayout).addView(
            (listSystemEventsAdapter as TableAdapter<LogsData.Event>)
                .makeRow(container?.context, HEAD),
            0
        )
        listSystemEvents!!.setAdapter(listSystemEventsAdapter)

        // Setup ListView "Counters":
        tab = tabHost.findViewById<View>(R.id.tab4) as LinearLayout
        listCountersInfo = tab.findViewById<View>(R.id.listInfo4) as TextView
        listCounters = tab.findViewById<View>(R.id.listView4) as ListView
        listCountersAdapter = object : TableAdapter<LogsData.Counter>(logsData!!.counters) {

            override fun makeColumns() {
                addColumn(getString(R.string.logs_th_code), 1f, Gravity.LEFT)
                addColumn(getString(R.string.logs_th_description), 3f, Gravity.LEFT)
                addColumn(getString(R.string.logs_th_lasttime), 2f, Gravity.CENTER_HORIZONTAL)
                addColumn(getString(R.string.logs_th_firsttime), 2f, Gravity.CENTER_HORIZONTAL)
                addColumn(getString(R.string.logs_th_count), 1f, Gravity.CENTER_HORIZONTAL)
            }

            override fun getValue(item: LogsData.Counter, row: Int, column: Int): String? {
                return when (column) {
                    0 -> item.code
                    1 -> item.description
                    2 -> if (item.lastTime != null) item.lastTime!!.fmtKeyTime() else ""
                    3 -> if (item.firstTime != null) item.firstTime!!.fmtKeyTime() else ""
                    4 -> "" + item.count
                    else -> ""
                }
            }
        }
        (listCounters!!.parent as LinearLayout).addView(
            (listCountersAdapter as TableAdapter<LogsData.Counter>)
                .makeRow(container?.context, HEAD),
            0
        )
        listCounters!!.setAdapter(listCountersAdapter)

        // Setup ListView "MinMax":
        tab = tabHost.findViewById<View>(R.id.tab5) as LinearLayout
        listMinMaxesInfo = tab.findViewById<View>(R.id.listInfo5) as TextView
        listMinMaxes = tab.findViewById<View>(R.id.listView5) as ListView
        listMinMaxesAdapter = object : TableAdapter<MinMax>(logsData!!.minMaxes) {

            private val dummy = MinMax()
            private val sensorNames = resources.getStringArray(R.array.logs_th_sensornames)

            override fun getCount(): Int {
                return 10 // number of sensors
            }

            override fun getItem(i: Int): Any {
                // horizontal layout needs all items per row
                return dummy
            }

            override fun makeColumns() {
                addColumn(getString(R.string.logs_th_sensor), 3f, Gravity.LEFT)
                addColumn(getString(R.string.logs_th_user), 1f, Gravity.CENTER)
                addColumn(getString(R.string.logs_th_sevcon), 1f, Gravity.CENTER)
            }

            override fun getValue(item: MinMax, row: Int, column: Int): String? {
                return when (column) {
                    0 -> sensorNames[row]
                    1 -> {
                        val value = if (items.size > 0) items[0] else null
                        value?.getSensor(row) ?: ""
                    }
                    2 -> {
                        val value = if (items.size > 1) items[1] else null
                        value?.getSensor(row) ?: ""
                    }
                    else -> ""
                }
            }
        }
        (listMinMaxes!!.parent as LinearLayout).addView(
            (listMinMaxesAdapter as TableAdapter<MinMax>).makeRow(container?.context, HEAD),
            0
        )
        listMinMaxes!!.setAdapter(listMinMaxesAdapter)

        // Done creating View:
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        compatActivity?.setTitle(R.string.logs_title)

        // get data of car to edit:
        editPosition = requireArguments().getInt("position", -1)
        carData = if (editPosition >= 0) {
            getStoredCars()[editPosition]
        } else {
            getSelectedCarData()
        }

        // load vehicle logs data:
        val saved = loadFile(carData!!.sel_vehicleid)
        if (saved != null) {
            //Log.v(TAG, "LogsData loaded for " + mCarData.sel_vehicleid);
            logsData = saved
            updateUi()
        }
        val rootView = view
        setOnClick(rootView!!, R.id.btn_get_logs, this)
        setOnClick(rootView, R.id.btn_reset_logs, this)
    }

    override fun onClick(view: View) {
        val content: View
        when (view.id) {
            R.id.btn_get_logs -> {
                content = layoutInflater!!.inflate(R.layout.dlg_logs_cmd, null)
                AlertDialog.Builder(requireActivity())
                    .setTitle(R.string.logs_btn_get)
                    .setView(content)
                    .setNegativeButton(R.string.Cancel, null)
                    .setPositiveButton(
                        android.R.string.ok
                    ) { dialog, which ->
                        val dlg = dialog as AppCompatDialog
                        val tbAlerts = dlg.findViewById<View>(R.id.tbAlerts) as ToggleButton?
                        val tbFaults = dlg.findViewById<View>(R.id.tbFaults) as ToggleButton?
                        val tbEvents = dlg.findViewById<View>(R.id.tbEvents) as ToggleButton?
                        val tbCounter = dlg.findViewById<View>(R.id.tbCounter) as ToggleButton?
                        val tbMinMax = dlg.findViewById<View>(R.id.tbMinMax) as ToggleButton?
                        cmdSeries = CmdSeries(getService(), this@LogsFragment)
                        if (tbAlerts!!.isChecked) {
                            cmdSeries!!.add(R.string.logs_msg_query_alerts, "210,1")
                        }
                        if (tbFaults!!.isChecked) {
                            cmdSeries!!.add(R.string.logs_msg_query_faults, "210,2,0")
                                .add(R.string.logs_msg_query_faults, "210,2,10")
                                .add(R.string.logs_msg_query_faults, "210,2,20")
                                .add(R.string.logs_msg_query_faults, "210,2,30")
                        }
                        if (tbEvents!!.isChecked) {
                            cmdSeries!!.add(R.string.logs_msg_query_events, "210,3,0")
                                .add(R.string.logs_msg_query_events, "210,3,10")
                        }
                        if (tbCounter!!.isChecked) {
                            cmdSeries!!.add(R.string.logs_msg_query_counter, "210,4")
                        }
                        if (tbMinMax!!.isChecked) {
                            cmdSeries!!.add(R.string.logs_msg_query_minmax, "210,5")
                        }
                        if (cmdSeries!!.size() > 0) {
                            cmdSeries!!.add(R.string.logs_msg_fetch_keytime, "32,RT-ENG-LogKeyTime")
                            if (tbAlerts.isChecked) {
                                cmdSeries!!.add(
                                    R.string.logs_msg_fetch_alerts,
                                    "32,RT-ENG-LogAlerts"
                                )
                            }
                            if (tbFaults.isChecked) {
                                cmdSeries!!.add(
                                    R.string.logs_msg_fetch_faults,
                                    "32,RT-ENG-LogFaults"
                                )
                            }
                            if (tbEvents.isChecked) {
                                cmdSeries!!.add(
                                    R.string.logs_msg_fetch_events,
                                    "32,RT-ENG-LogSystem"
                                )
                            }
                            if (tbCounter.isChecked) {
                                cmdSeries!!.add(
                                    R.string.logs_msg_fetch_counter,
                                    "32,RT-ENG-LogCounts"
                                )
                            }
                            if (tbMinMax.isChecked) {
                                cmdSeries!!.add(
                                    R.string.logs_msg_fetch_minmax,
                                    "32,RT-ENG-LogMinMax"
                                )
                            }
                            cmdSeries!!.start()
                        }
                    }
                    .show()
            }

            R.id.btn_reset_logs -> {
                content = layoutInflater!!.inflate(R.layout.dlg_logs_cmd, null)
                content.findViewById<View>(R.id.tbAlerts).visibility = View.GONE
                AlertDialog.Builder(requireActivity())
                    .setTitle(R.string.logs_btn_reset)
                    .setView(content)
                    .setNegativeButton(R.string.Cancel, null)
                    .setPositiveButton(
                        android.R.string.ok
                    ) { dialog, which ->
                        val dlg = dialog as AppCompatDialog
                        val tbFaults = dlg.findViewById<View>(R.id.tbFaults) as ToggleButton?
                        val tbEvents = dlg.findViewById<View>(R.id.tbEvents) as ToggleButton?
                        val tbCounter = dlg.findViewById<View>(R.id.tbCounter) as ToggleButton?
                        val tbMinMax = dlg.findViewById<View>(R.id.tbMinMax) as ToggleButton?
                        cmdSeries = CmdSeries(getService(), this@LogsFragment)
                        if (tbFaults!!.isChecked) {
                            cmdSeries!!.add(R.string.logs_msg_reset_faults, "209,2")
                        }
                        if (tbEvents!!.isChecked) {
                            cmdSeries!!.add(R.string.logs_msg_reset_events, "209,3")
                        }
                        if (tbCounter!!.isChecked) {
                            cmdSeries!!.add(R.string.logs_msg_reset_counter, "209,4")
                        }
                        if (tbMinMax!!.isChecked) {
                            cmdSeries!!.add(R.string.logs_msg_reset_minmax, "209,5")
                        }
                        if (cmdSeries!!.size() > 0) {
                            cmdSeries!!.start()
                        }
                    }
                    .show()
            }
        }
    }

    override fun onStop() {
        if (cmdSeries != null) {
            cmdSeries!!.cancel()
        }
        super.onStop()
    }

    override fun onCmdSeriesProgress(
        message: String?,
        pos: Int,
        posCnt: Int,
        step: Int,
        stepCnt: Int
    ) {
        stepProgressOverlay(message, pos, posCnt, step, stepCnt)
    }

    override fun onProgressCancel() {
        if (cmdSeries != null) {
            cmdSeries!!.cancel()
        }
        hideProgressOverlay()
    }

    override fun onCmdSeriesFinish(cmdSeries: CmdSeries, returnCode: Int) {
        var errorMsg = ""
        var errorDetail = ""
        hideProgressOverlay()
        when (returnCode) {
            -1 -> return
            0 -> {
                showProgressOverlay(getString(R.string.msg_processing_data))
                logsData!!.processCmdResults(cmdSeries)
                logsData!!.saveFile(carData!!.sel_vehicleid)
                updateUi()
                hideProgressOverlay()
                return
            }
            1 -> {
                errorDetail = cmdSeries.getErrorDetail()
                if (errorDetail.contains("B ")) errorDetail += getString(R.string.hint_sevcon_offline)
                errorMsg = getString(R.string.err_failed, errorDetail)
            }
            2 -> errorMsg = getString(R.string.err_unsupported_operation)
            3 -> errorMsg = getString(R.string.err_unimplemented_operation)
        }

        // getting here means error:
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.Error)
            .setMessage(cmdSeries.getMessage() + " => " + errorMsg)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    private fun updateUi() {
        // Data ready/changed: show
        listAlertsInfo!!.text = getString(
            R.string.logs_fmt_listinfo,
            logsData!!.alertsTime.fmtTimeStamp(),
            logsData!!.alertsTime.fmtKeyTime()
        )
        listAlertsAdapter!!.items = logsData!!.alerts
        listFaultEventsInfo!!.text = getString(
            R.string.logs_fmt_listinfo,
            logsData!!.faultEventsTime.fmtTimeStamp(),
            logsData!!.faultEventsTime.fmtKeyTime()
        )
        listFaultEventsAdapter!!.items = logsData!!.faultEvents
        listSystemEventsInfo!!.text = getString(
            R.string.logs_fmt_listinfo,
            logsData!!.systemEventsTime.fmtTimeStamp(),
            logsData!!.systemEventsTime.fmtKeyTime()
        )
        listSystemEventsAdapter!!.items = logsData!!.systemEvents
        listCountersInfo!!.text = getString(
            R.string.logs_fmt_listinfo,
            logsData!!.countersTime.fmtTimeStamp(),
            logsData!!.countersTime.fmtKeyTime()
        )
        listCountersAdapter!!.items = logsData!!.counters
        listMinMaxesInfo!!.text = getString(
            R.string.logs_fmt_listinfo,
            logsData!!.minMaxesTime.fmtTimeStamp(),
            logsData!!.minMaxesTime.fmtKeyTime()
        )
        listMinMaxesAdapter!!.items = logsData!!.minMaxes
    }

    /*
     * Inner types
     */

    companion object {
        private const val TAG = "LogsFragment"

        private const val HEAD = true
        private const val BODY = false
    }

    abstract inner class TableAdapter<T>(
        items: ArrayList<T>
    ) : BaseAdapter() {

        var items: ArrayList<T> = items
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        private inner class ColumnDef(var title: String, var weight: Float, var gravity: Int)

        private val columns: ArrayList<ColumnDef> = ArrayList(10)

        init {
            makeColumns()
        }

        fun addColumn(title: String, weight: Float, gravity: Int) {
            columns.add(ColumnDef(title, weight, gravity))
        }

        // insert addColumn() calls in implementation of...
        abstract fun makeColumns()

        // insert data mapping in implementation of...
        abstract fun getValue(item: T, row: Int, column: Int): String?

        override fun getCount(): Int {
            return items.size
        }

        override fun getItem(i: Int): Any? {
            return items[i]
        }

        override fun getItemId(i: Int): Long {
            return i.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var row = convertView as? LinearLayout
            if (row == null) {
                row = makeRow(parent.context, Companion.BODY) as LinearLayout
            }
            val item: T? = getItem(position) as T
            if (item != null) {
                var textView: TextView?
                for (col in columns.indices) {
                    textView = row.getChildAt(col) as? TextView
                    if (textView != null) {
                        textView.text = getValue(item, position, col)
                    }
                }
            }
            return row
        }

        fun makeRow(context: Context?, isHeader: Boolean): View {
            val row = LinearLayout(context)
            row.orientation = LinearLayout.HORIZONTAL
            row.setPadding(2, 2, 2, 2)
            if (isHeader) {
                row.setBackgroundResource(
                    R.drawable.abc_list_selector_background_transition_holo_light
                )
            }
            var layout: LinearLayout.LayoutParams
            var textView: TextView
            var columnDef: ColumnDef
            for (col in columns.indices) {
                columnDef = columns[col]
                layout = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
                layout.gravity = columnDef.gravity
                layout.weight = columnDef.weight
                textView = TextView(context)
                textView.setLayoutParams(layout)
                textView.setPadding(2, 2, 2, 2)
                // TODO: gravity does not work. setTextAlignment() necessary but needs API >= 17
                if (isHeader) {
                    textView.setTypeface(Typeface.DEFAULT_BOLD)
                    textView.text = columnDef.title
                }
                row.addView(textView)
            }
            return row
        }
    }
}
