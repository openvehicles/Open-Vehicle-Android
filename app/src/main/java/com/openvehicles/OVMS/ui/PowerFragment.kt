package com.openvehicles.OVMS.ui

import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.ChartTouchListener.ChartGesture
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.entities.CmdSeries
import com.openvehicles.OVMS.entities.GPSLogData
import com.openvehicles.OVMS.entities.GPSLogData.Companion.loadFile
import com.openvehicles.OVMS.ui.utils.ProgressOverlay
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.CarsStorage.getSelectedCarData
import java.text.SimpleDateFormat

/**
 * Created by balzer on 06.04.15.
 */
class PowerFragment : BaseFragment(), CmdSeries.Listener, ProgressOverlay.OnCancelListener {

    // data storage:
    private lateinit var logData: GPSLogData
    private var chartEntries: ArrayList<GPSLogData.Entry>? = null

    // user interface:
    private lateinit var optionsMenu: Menu
    private lateinit var tripChart: LineChart
    private lateinit var powerChart: LineChart
    private lateinit var energyChart: LineChart

    private lateinit var chartCoupler: CoupleChartGestureListener
    private var showPower = true
    private var showEnergy = true
    private var carData: CarData? = null
    private var cmdSeries: CmdSeries? = null
    private lateinit var appPrefs: AppPrefs

    private val isPackValid: Boolean
        // Check data model validity
        get() = logData.entries.size > 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Init data storage:
        logData = GPSLogData()

        // Load prefs:
        appPrefs = AppPrefs(requireActivity(), "ovms")
        showPower = appPrefs.getData("power_show_power") == "on"
        showEnergy = appPrefs.getData("power_show_energy") == "on"
        if (!showPower && !showEnergy) {
            showPower = true
        }

        // Setup UI:
        //val progressOverlay = createProgressOverlay(inflater, container, false)
        //progressOverlay.setOnCancelListener(this)
        val rootView = inflater.inflate(R.layout.fragment_power, null)
        var xAxis: XAxis
        val xFormatter = XAxisValueFormatter()
        var yAxis: YAxis

        //
        // Setup trip chart:
        //
        var chart: LineChart = rootView.findViewById<View>(R.id.chart_trip) as LineChart
        tripChart = chart
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.setDrawBorders(true)
        chart.isHighlightPerTapEnabled = false
        xAxis = chart.xAxis
        xAxis.textColor = Color.WHITE
        xAxis.valueFormatter = xFormatter
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        yAxis = chart.axisLeft // altitude
        yAxis.textColor = COLOR_ALTITUDE
        yAxis.gridColor = COLOR_ALTITUDE_GRID
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxis = chart.axisRight // speed
        yAxis.textColor = COLOR_SPEED
        yAxis.gridColor = COLOR_SPEED_GRID
        yAxis.axisMinimum = 0f
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)

        //
        // Setup power chart:
        //
        chart = rootView.findViewById<View>(R.id.chart_power) as LineChart
        powerChart = chart
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.setDrawBorders(true)
        chart.isHighlightPerTapEnabled = false
        xAxis = chart.xAxis
        xAxis.textColor = Color.WHITE
        xAxis.valueFormatter = xFormatter
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        yAxis = chart.axisLeft
        yAxis.textColor = Color.WHITE
        yAxis.gridColor = Color.LTGRAY
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxis.setDrawTopYLabelEntry(false)
        yAxis = chart.axisRight
        yAxis.textColor = Color.WHITE
        yAxis.gridColor = Color.LTGRAY
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)

        //
        // Setup energy chart:
        //
        chart = rootView.findViewById<View>(R.id.chart_energy) as LineChart
        energyChart = chart
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.setDrawBorders(true)
        chart.isHighlightPerTapEnabled = false
        xAxis = chart.xAxis
        xAxis.textColor = Color.WHITE
        xAxis.valueFormatter = xFormatter
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        yAxis = chart.axisLeft
        yAxis.textColor = Color.WHITE
        yAxis.gridColor = Color.LTGRAY
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxis = chart.axisRight
        yAxis.textColor = Color.WHITE
        yAxis.gridColor = Color.LTGRAY
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)

        //
        // Couple chart viewports:
        //
        tripChart.onChartGestureListener = CoupleChartGestureListener(
            tripChart, arrayOf(powerChart, energyChart)
        ).also { chartCoupler = it }
        powerChart.onChartGestureListener = CoupleChartGestureListener(
            powerChart, arrayOf(tripChart, energyChart)
        )
        energyChart.onChartGestureListener = CoupleChartGestureListener(
            energyChart, arrayOf(tripChart, powerChart)
        )

        // attach menu:
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.power_chart_options, menu)
        optionsMenu = menu

        // configure checkbox items:
        optionsMenu.findItem(R.id.mi_chk_power).setChecked(showPower)
        optionsMenu.findItem(R.id.mi_chk_energy).setChecked(showEnergy)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (carData != null) {
            // insert user distance units into menu titles:
            optionsMenu.findItem(R.id.mi_power_zoom5).setTitle(
                getString(R.string.power_btn_zoom5, carData!!.car_distance_units)
            )
            optionsMenu.findItem(R.id.mi_power_zoom10).setTitle(
                getString(R.string.power_btn_zoom10, carData!!.car_distance_units)
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        compatActivity!!.setTitle(R.string.power_title)
       // compatActivity!!.supportActionBar!!.setIcon(R.drawable.ic_action_chart)

        // get data of current car:
        carData = getSelectedCarData()
        compatActivity!!.invalidateOptionsMenu()

        // schedule data loader:
        showProgressOverlay(getString(R.string.power_msg_loading_data))
        handler.postDelayed({ // load and display saved vehicle data:
            val saved = loadFile(carData!!.sel_vehicleid)
            if (saved != null) {
                Log.v(TAG, "GPSLogData loaded for " + carData!!.sel_vehicleid)
                logData = saved
                dataSetChanged()
            }
            hideProgressOverlay()
        }, 200)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        val newState = !item.isChecked
        when (menuId) {
            R.id.mi_get_data -> {
                cmdSeries = CmdSeries(getService(), this)
                    .add(R.string.power_msg_getting_gpslog, "32,RT-GPS-Log")
                    .start()
                return true
            }
            R.id.mi_power_zoom5 -> {
                zoomOdometerRange(5)
                return true
            }
            R.id.mi_power_zoom10 -> {
                zoomOdometerRange(10)
                return true
            }
            R.id.mi_reset_view -> {
                tripChart.fitScreen()
                powerChart.fitScreen()
                energyChart.fitScreen()
                return true
            }
            R.id.mi_help -> {
                AlertDialog.Builder(requireActivity())
                    .setTitle(R.string.power_btn_help)
                    .setMessage(Html.fromHtml(getString(R.string.power_help)))
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
                return true
            }
            R.id.mi_chk_power -> {
                showPower = newState
                if (!showPower && !showEnergy) {
                    showEnergy = true
                    optionsMenu.findItem(R.id.mi_chk_energy).setChecked(true)
                }
                item.setChecked(newState)
                dataFilterChanged()
                return true
            }
            R.id.mi_chk_energy -> {
                showEnergy = newState
                if (!showPower && !showEnergy) {
                    showPower = true
                    optionsMenu.findItem(R.id.mi_chk_power).setChecked(true)
                }
                item.setChecked(newState)
                dataFilterChanged()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
                logData.processCmdResults(cmdSeries)
                logData.saveFile(carData!!.sel_vehicleid)
                dataSetChanged()
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

    /**
     * Call when underlying batteryData object ready/changed
     */
    private fun dataSetChanged() {
        if (!isPackValid) {
            return
        }

        // update charts:
        updateCharts()
    }

    /**
     * Call when user changed data filter (i.e. show flags)
     */
    private fun dataFilterChanged() {
        // save prefs:
        appPrefs.saveData("power_show_power", if (showPower) "on" else "off")
        appPrefs.saveData("power_show_energy", if (showEnergy) "on" else "off")

        // check data status:
        if (!isPackValid) {
            return
        }

        // update charts:
        updateCharts()

        // sync viewports:
        chartCoupler.syncCharts()
    }

    /**
     * Update the charts
     */
    private fun updateCharts() {
        if (!isPackValid) {
            return
        }
        val logEntries = logData.entries
        var entry: GPSLogData.Entry
        var refEntry: GPSLogData.Entry
        var refIndex: Int
        var xAxis: XAxis

        //
        // Create value arrays:
        //
        chartEntries = ArrayList()
        val xSections = ArrayList<LimitLine>()
        val altValues = ArrayList<Entry>()
        val spdValues = ArrayList<Entry>()
        val pwrMinValues = ArrayList<Entry>()
        val pwrMaxValues = ArrayList<Entry>()
        val pwrAvgValues = ArrayList<Entry>()
        val bmsRecupLimitValues = ArrayList<Entry>()
        val bmsDriveLimitValues = ArrayList<Entry>()
        val energyUseValues = ArrayList<Entry>()
        val energyRecValues = ArrayList<Entry>()
        val energyAvgValues = ArrayList<Entry>()
        val units = carData!!.car_distance_units_raw
        refIndex = 0
        refEntry = logEntries[0]
        for (i in 1 until logEntries.size) {
            entry = logEntries[i]

            // check data distance from ref:
            if (entry.isNewTimePoint(refEntry)) {
                val xpos = chartEntries!!.size.toFloat()
                chartEntries!!.add(entry)
                altValues.add(Entry(xpos, entry.altitude))
                spdValues.add(Entry(xpos, entry.getSpeed(refEntry, units)))
                pwrMinValues.add(Entry(xpos, entry.getMinPower(logEntries, refEntry).toFloat()))
                pwrMaxValues.add(Entry(xpos, entry.getMaxPower(logEntries, refEntry).toFloat()))
                pwrAvgValues.add(
                    Entry(
                        xpos, entry.getSegAvgPwr(
                            refEntry
                        )
                    )
                )
                bmsRecupLimitValues.add(
                    Entry(
                        xpos,
                        entry.getBmsRecupLimit(logEntries, refEntry).toFloat()
                    )
                )
                bmsDriveLimitValues.add(
                    Entry(
                        xpos,
                        entry.getBmsDriveLimit(logEntries, refEntry).toFloat()
                    )
                )
                energyUseValues.add(
                    Entry(
                        xpos, entry.getSegUsedWh(
                            refEntry
                        ).toFloat()
                    )
                )
                energyRecValues.add(
                    Entry(
                        xpos, entry.getSegRecdWh(
                            refEntry
                        ).toFloat()
                    )
                )
                energyAvgValues.add(
                    Entry(
                        xpos, entry.getSegAvgEnergy(
                            refEntry, units
                        )
                    )
                )

                // add section markers:
                if (entry.isSectionStart(refEntry)) {
                    val l = LimitLine(xpos)
                    l.label = String.format("%.0f", entry.getOdometer(units))
                    l.textColor = Color.WHITE
                    l.textStyle = Paint.Style.FILL
                    l.textSize = 6f
                    l.enableDashedLine(3f, 2f, 0f)
                    xSections.add(l)

                    // use as new reference:
                    refIndex = i
                    refEntry = entry
                } else {
                    // advance reference point:
                    while (entry.isNewTimePoint(refEntry) && refIndex <= i) refEntry =
                        logEntries[++refIndex]
                }
            }
        }

        //
        // Update trip chart:
        //
        var dataSets = ArrayList<ILineDataSet?>()
        var dataSet: LineDataSet
        dataSet = LineDataSet(altValues, getString(R.string.power_data_altitude))
        dataSet.axisDependency = YAxis.AxisDependency.LEFT
        dataSet.color = COLOR_ALTITUDE
        dataSet.setDrawFilled(true)
        dataSet.lineWidth = 2f
        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(false)
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.cubicIntensity = 0.1f
        dataSets.add(dataSet)
        dataSet =
            LineDataSet(spdValues, getString(R.string.power_data_speed, carData!!.car_speed_units))
        dataSet.axisDependency = YAxis.AxisDependency.RIGHT
        dataSet.color = COLOR_SPEED
        dataSet.lineWidth = 4f
        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(false)
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.cubicIntensity = 0.1f
        dataSets.add(dataSet)

        // display data sets:
        var data: LineData
        data = LineData(dataSets)
        data.setValueTextColor(Color.WHITE)
        data.setValueTextSize(9f)
        tripChart.data = data
        xAxis = tripChart.xAxis
        xAxis.removeAllLimitLines()
        for (i in xSections.indices) {
            val l = xSections[i]
            if (i < xSections.size / 2) {
                l.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
            } else {
                l.labelPosition = LimitLine.LimitLabelPosition.LEFT_BOTTOM
            }
            xAxis.addLimitLine(l)
        }
        tripChart.legend.textColor = Color.WHITE
        tripChart.invalidate()

        //
        // Update power chart:
        //
        if (!showPower) {
            powerChart.visibility = View.GONE
            powerChart.clear()
        } else {
            powerChart.visibility = View.VISIBLE
            dataSets = ArrayList()
            dataSet = LineDataSet(pwrMinValues, getString(R.string.power_data_pwr_min))
            dataSet.axisDependency = YAxis.AxisDependency.LEFT
            dataSet.color = COLOR_POWER_MIN
            dataSet.setDrawFilled(true)
            dataSet.lineWidth = 1f
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSet.mode = LineDataSet.Mode.LINEAR
            dataSets.add(dataSet)
            dataSet = LineDataSet(pwrMaxValues, getString(R.string.power_data_pwr_max))
            dataSet.axisDependency = YAxis.AxisDependency.LEFT
            dataSet.color = COLOR_POWER_MAX
            dataSet.setDrawFilled(true)
            dataSet.lineWidth = 1f
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSet.mode = LineDataSet.Mode.LINEAR
            dataSets.add(dataSet)
            dataSet = LineDataSet(pwrAvgValues, getString(R.string.power_data_pwr_avg))
            dataSet.axisDependency = YAxis.AxisDependency.LEFT
            dataSet.color = COLOR_POWER_AVG
            dataSet.setDrawFilled(false)
            dataSet.lineWidth = 4f
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            dataSet.cubicIntensity = 0.1f
            dataSets.add(dataSet)
            dataSet =
                LineDataSet(bmsRecupLimitValues, getString(R.string.power_data_pwr_recuplimit))
            dataSet.axisDependency = YAxis.AxisDependency.LEFT
            dataSet.color = COLOR_POWER_RECUPLIMIT
            dataSet.setDrawFilled(false)
            dataSet.lineWidth = 2f
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSet.mode = LineDataSet.Mode.LINEAR
            dataSets.add(dataSet)
            dataSet =
                LineDataSet(bmsDriveLimitValues, getString(R.string.power_data_pwr_drivelimit))
            dataSet.axisDependency = YAxis.AxisDependency.LEFT
            dataSet.color = COLOR_POWER_DRIVELIMIT
            dataSet.setDrawFilled(false)
            dataSet.lineWidth = 2f
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSet.mode = LineDataSet.Mode.LINEAR
            dataSets.add(dataSet)

            // display data sets:
            data = LineData(dataSets)
            data.setValueTextColor(Color.WHITE)
            data.setValueTextSize(9f)
            powerChart.data = data
            xAxis = powerChart.xAxis
            xAxis.removeAllLimitLines()
            for (i in xSections.indices) {
                val l = xSections[i]
                if (i < xSections.size / 2) {
                    l.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
                } else {
                    l.labelPosition = LimitLine.LimitLabelPosition.LEFT_BOTTOM
                }
                xAxis.addLimitLine(l)
            }
            powerChart.legend.textColor = Color.WHITE
            powerChart.invalidate()
        }

        //
        // Update energy chart:
        //
        if (!showEnergy) {
            energyChart.visibility = View.GONE
            energyChart.clear()
        } else {
            energyChart.visibility = View.VISIBLE
            dataSets = ArrayList()
            dataSet = LineDataSet(energyUseValues, getString(R.string.power_data_energy_used))
            dataSet.axisDependency = YAxis.AxisDependency.LEFT
            dataSet.color = COLOR_ENERGY_USED
            dataSet.setDrawFilled(true)
            dataSet.lineWidth = 1f
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            dataSet.cubicIntensity = 0.1f
            dataSets.add(dataSet)
            dataSet = LineDataSet(energyRecValues, getString(R.string.power_data_energy_recd))
            dataSet.axisDependency = YAxis.AxisDependency.LEFT
            dataSet.color = COLOR_ENERGY_RECD
            dataSet.setDrawFilled(true)
            dataSet.lineWidth = 1f
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            dataSet.cubicIntensity = 0.1f
            dataSets.add(dataSet)
            dataSet = LineDataSet(
                energyAvgValues,
                getString(R.string.power_data_energy_avg, carData!!.car_distance_units)
            )
            dataSet.axisDependency = YAxis.AxisDependency.LEFT
            dataSet.color = COLOR_ENERGY_AVG
            dataSet.setDrawFilled(false)
            dataSet.lineWidth = 4f
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            dataSet.cubicIntensity = 0.1f
            dataSets.add(dataSet)

            // display data sets:
            data = LineData(dataSets)
            data.setValueTextColor(Color.WHITE)
            data.setValueTextSize(9f)
            energyChart.data = data
            xAxis = energyChart.xAxis
            xAxis.removeAllLimitLines()
            for (i in xSections.indices) {
                val l = xSections[i]
                if (i < xSections.size / 2) {
                    l.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
                } else {
                    l.labelPosition = LimitLine.LimitLabelPosition.LEFT_BOTTOM
                }
                xAxis.addLimitLine(l)
            }
            energyChart.legend.textColor = Color.WHITE
            energyChart.invalidate()
        }
    }

    /**
     * Zoom to odometer range
     *
     * @param size -- size of range in user units (km/mi)
     */
    private fun zoomOdometerRange(size: Int) {
        if (!isPackValid) {
            return
        }
        if (chartEntries == null) {
            return
        }
        var start = 0
        var end = chartEntries!!.size - 1
        var entry: GPSLogData.Entry
        var odoEnd = 0f

        // find last drive entry:
        for (i in chartEntries!!.indices.reversed()) {
            entry = chartEntries!![i]
            if (entry.opStatus == 1) {
                odoEnd = entry.getOdometer(carData!!.car_distance_units_raw)
                end = i
                break
            }
        }

        // find entry for (odoEnd - size):
        for (i in end - 1 downTo 0) {
            entry = chartEntries!![i]
            if (entry.getOdometer(carData!!.car_distance_units_raw) <= odoEnd - size) {
                start = i
                break
            }
        }

        // zoom charts:
        val scaleX = chartEntries!!.size.toFloat() / (end - start + 1)
        for (chart in arrayOf<BarLineChartBase<*>?>(tripChart, powerChart, energyChart)) {
            chart!!.clearAnimation()
            chart.fitScreen()
            chart.zoom(scaleX, 1f, chart.width / 2f, chart.height / 2f)
            chart.moveViewToX(end.toFloat())
        }
    }

    /*
     * Inner types
     */

    companion object {

        private const val TAG = "PowerFragment"

        // data set colors:
        private val COLOR_ALTITUDE = Color.parseColor("#C0C8AB37")
        private val COLOR_ALTITUDE_GRID = Color.parseColor("#80C8AB37")
        private val COLOR_SPEED = Color.parseColor("#FF7777FF")
        private val COLOR_SPEED_GRID = Color.parseColor("#807777FF")
        private val COLOR_POWER_MIN = Color.parseColor("#FF999999")
        private val COLOR_POWER_MAX = Color.parseColor("#FF999999")
        private val COLOR_POWER_AVG = Color.parseColor("#FFFF0000")
        private val COLOR_POWER_RECUPLIMIT = Color.parseColor("#FFFF7777")
        private val COLOR_POWER_DRIVELIMIT = Color.parseColor("#FFFF7777")
        private val COLOR_ENERGY_USED = Color.parseColor("#FF999999")
        private val COLOR_ENERGY_RECD = Color.parseColor("#FF999999")
        private val COLOR_ENERGY_AVG = Color.parseColor("#FFFFFF00")

        // system services:
        private val handler = Handler(Looper.getMainLooper())
    }

    inner class XAxisValueFormatter : ValueFormatter() {

        private var timeFmt: SimpleDateFormat = SimpleDateFormat("HH:mm")

        override fun getFormattedValue(value: Float): String {
            return try {
                val entry = chartEntries!![value.toInt()]
                timeFmt.format(entry.timeStamp!!)
            } catch (e: Exception) {
                ""
            }
        }
    }

    inner class CoupleChartGestureListener(
        private val srcChart: Chart<*>?,
        private val dstCharts: Array<Chart<*>?>
    ) : OnChartGestureListener {
        override fun onChartGestureStart(me: MotionEvent, lastPerformedGesture: ChartGesture) {
            // nop
        }

        override fun onChartGestureEnd(me: MotionEvent, lastPerformedGesture: ChartGesture) {
            // nop
        }

        override fun onChartLongPressed(me: MotionEvent) {
            // nop
        }

        override fun onChartDoubleTapped(me: MotionEvent) {
            // nop
        }

        override fun onChartSingleTapped(me: MotionEvent) {
            // nop
        }

        override fun onChartFling(
            me1: MotionEvent,
            me2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ) {
            // nop
        }

        override fun onChartScale(me: MotionEvent, scaleX: Float, scaleY: Float) {
            //Log.d(TAG, "onChartScale " + scaleX + "/" + scaleY + " X=" + me.getX() + "Y=" + me.getY());
            syncCharts()
        }

        override fun onChartTranslate(me: MotionEvent, dX: Float, dY: Float) {
            //Log.d(TAG, "onChartTranslate " + dX + "/" + dY + " X=" + me.getX() + "Y=" + me.getY());
            syncCharts()
        }

        fun syncCharts() {
            val srcVals = FloatArray(9)
            var dstMatrix: Matrix
            val dstVals = FloatArray(9)

            // get src chart translation matrix:
            val srcMatrix: Matrix = srcChart!!.viewPortHandler.matrixTouch
            srcMatrix.getValues(srcVals)

            // apply X axis scaling and position to dst charts:
            for (dstChart in dstCharts) {
                if (dstChart!!.visibility == View.VISIBLE) {
                    dstMatrix = dstChart.viewPortHandler.matrixTouch
                    dstMatrix.getValues(dstVals)
                    dstVals[Matrix.MSCALE_X] = srcVals[Matrix.MSCALE_X]
                    dstVals[Matrix.MTRANS_X] = srcVals[Matrix.MTRANS_X]
                    dstMatrix.setValues(dstVals)
                    dstChart.viewPortHandler.refresh(dstMatrix, dstChart, true)
                }
            }
        }
    }
}
