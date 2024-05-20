package com.openvehicles.OVMS.ui

import android.graphics.Color
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
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.AuxBatteryData
import com.openvehicles.OVMS.entities.AuxBatteryData.Companion.loadFile
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.entities.CmdSeries
import com.openvehicles.OVMS.ui.utils.ProgressOverlay
import com.openvehicles.OVMS.ui.utils.TimeAxisValueFormatter
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.CarsStorage.getSelectedCarData
import java.text.SimpleDateFormat
import kotlin.math.max
import kotlin.math.min

/**
 * Auxiliary battery pack status history charts.
 *
 * Data model: AuxBatteryData
 *
 * Created by Michael Balzer <dexter></dexter>@dexters-web.de> on 2019-12-13.
 */
class AuxBatteryFragment : BaseFragment(), CmdSeries.Listener, ProgressOverlay.OnCancelListener {

    // data storage:
    private var batteryData: AuxBatteryData? = null

    // user interface:
    private var optionsMenu: Menu? = null
    private lateinit var packChart: LineChart
    private var packData: LineData? = null
    private lateinit var xAxis: XAxis
    private lateinit var yAxisLeft: YAxis
    private lateinit var yAxisRight: YAxis
    private var timeStart: Long = 0
    private var highlightSetNr = -1
    private var highlightSetLabel = ""
    private var showVolt = true
    private var showTemp = false
    private var carData: CarData? = null
    private var cmdSeries: CmdSeries? = null
    private var appPrefs: AppPrefs? = null

    /**
     * Check data model validity
     */
    private val isPackValid: Boolean
        get() = batteryData != null && batteryData!!.packHistory.size > 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Init data storage:
        batteryData = AuxBatteryData()

        // Load prefs:
        appPrefs = AppPrefs(requireActivity(), "ovms")
        showVolt = appPrefs!!.getData("aux_battery_show_volt") == "on"
        showTemp = appPrefs!!.getData("aux_battery_show_temp") == "on"
        if (!showVolt && !showTemp) {
            showVolt = true
            showTemp = true
        }

        // Setup UI:
        val progressOverlay = createProgressOverlay(inflater, container, false)
        progressOverlay.setOnCancelListener(this)
        val rootView = inflater.inflate(R.layout.fragment_auxbattery, null)

        //
        // Setup Pack history chart:
        //
        packChart = rootView.findViewById<View>(R.id.auxbattery_chart_pack) as LineChart
        packChart.description.isEnabled = false
        packChart.setPinchZoom(false) // = pinch zoom in one direction at a time
        packChart.setDrawGridBackground(false)
        packChart.setDrawBorders(true)
        packChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {

            override fun onValueSelected(entry: Entry, highlight: Highlight) {
                // remember user data set selection:
                val dataSet = highlight.dataSetIndex
                highlightSetNr = dataSet
                highlightSetLabel = packChart.data.getDataSetByIndex(dataSet).label
            }

            override fun onNothingSelected() {
                // nop
            }
        })
        xAxis = packChart.xAxis
        xAxis.textColor = Color.WHITE
        xAxis.isGranularityEnabled = true
        xAxis.setGranularity(60f) // one minute
        yAxisLeft = packChart.axisLeft
        yAxisLeft.textColor = COLOR_CURR_TEXT
        yAxisLeft.gridColor = COLOR_CURR_GRID
        yAxisLeft.valueFormatter = DefaultAxisValueFormatter(0)
        yAxisRight = packChart.axisRight
        yAxisRight.textColor = COLOR_VOLT
        yAxisRight.gridColor = COLOR_VOLT_GRID
        yAxisRight.valueFormatter = DefaultAxisValueFormatter(1)
        yAxisRight.setGranularity(0.1f)

        // default data set to highlight:
        highlightSetLabel = getString(R.string.aux_battery_data_volt)

        // attach menu:
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.auxbattery_chart_options, menu)
        optionsMenu = menu.apply {
            findItem(R.id.mi_chk_volt).setChecked(showVolt)
            findItem(R.id.mi_chk_temp).setChecked(showTemp)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        compatActivity?.setTitle(R.string.aux_battery_title)
        compatActivity?.supportActionBar!!.setIcon(R.drawable.ic_action_chart)

        // get data of current car:
        carData = getSelectedCarData()

        // schedule data loader:
        showProgressOverlay(getString(R.string.battery_msg_loading_data))
        handler.postDelayed({ // load and display saved vehicle data:
            val saved = loadFile(carData!!.sel_vehicleid)
            if (saved != null) {
                Log.v(TAG, "AuxBatteryData loaded for " + carData!!.sel_vehicleid)
                batteryData = saved
                dataSetChanged()
            }
            hideProgressOverlay()
        }, 200)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        val newState = !item.isChecked
        return when (menuId) {
            R.id.mi_get_data -> {
                cmdSeries = CmdSeries(getService(), this@AuxBatteryFragment)
                    .add(R.string.aux_battery_msg_get_battpack, "32,D")
                    .start()
                true
            }
            R.id.mi_reset_view -> {
                packChart.fitScreen()
                true
            }
            R.id.mi_help -> {
                AlertDialog.Builder(requireActivity())
                    .setTitle(R.string.battery_btn_help)
                    .setMessage(Html.fromHtml(getString(R.string.aux_battery_help)))
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
                true
            }
            R.id.mi_chk_volt -> {
                showVolt = newState
                if (!showVolt && !showTemp) {
                    showTemp = true
                    optionsMenu!!.findItem(R.id.mi_chk_temp).setChecked(true)
                }
                item.setChecked(newState)
                dataFilterChanged()
                true
            }
            R.id.mi_chk_temp -> {
                showTemp = newState
                if (!showVolt && !showTemp) {
                    showVolt = true
                    optionsMenu!!.findItem(R.id.mi_chk_volt).setChecked(true)
                }
                item.setChecked(newState)
                dataFilterChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        cmdSeries?.cancel()
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
        cmdSeries?.cancel()
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
                batteryData!!.processCmdResults(cmdSeries)
                batteryData!!.saveFile(carData!!.sel_vehicleid)
                dataSetChanged()
                hideProgressOverlay()
                return
            }
            1 -> {
                errorDetail = cmdSeries.getErrorDetail()
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
     * Check data model and pack index validity
     */
    private fun isPackIndexValid(index: Int): Boolean {
        return isPackValid && index < batteryData!!.packHistory.size
    }

    /**
     * Call when underlying batteryData object ready/changed
     */
    private fun dataSetChanged() {
        if (!isPackValid) {
            return
        }

        // update pack chart:
        showPackHistory()

        // highlight latest entry:
        val lastEntry = batteryData!!.packHistory.size - 1
        highlightPackEntry(lastEntry)
    }

    /**
     * Call when user changed data filter (i.e. show Volt / Temp sets)
     */
    private fun dataFilterChanged() {
        // save prefs:
        appPrefs!!.saveData("aux_battery_show_volt", if (showVolt) "on" else "off")
        appPrefs!!.saveData("aux_battery_show_temp", if (showTemp) "on" else "off")

        // check data status:
        if (!isPackValid) {
            return
        }

        // update pack chart:
        showPackHistory()
    }

    /**
     * Update the pack chart
     */
    private fun showPackHistory() {
        if (!isPackValid) {
            return
        }
        val packHistory = batteryData!!.packHistory
        var packStatus: AuxBatteryData.PackStatus
        var lastStatus: AuxBatteryData.PackStatus?
        val timeFmt = SimpleDateFormat("HH:mm")

        //
        // Pack chart:
        //

        // create value arrays:
        val xSections = ArrayList<LimitLine>()
        val voltValues = ArrayList<Entry>()
        val voltRefValues = ArrayList<Entry>()
        val currentValues = ArrayList<Entry>()
        val tempAmbientValues = ArrayList<Entry>()
        val tempChargerValues = ArrayList<Entry>()
        timeStart = packHistory[0].timeStamp!!.time / 1000
        xAxis.valueFormatter = TimeAxisValueFormatter(timeStart, "HH:mm")

        // create y values:
        lastStatus = null
        for (i in packHistory.indices) {
            packStatus = packHistory[i]
            val xpos = (packStatus.timeStamp!!.time / 1000 - timeStart).toFloat()
            voltValues.add(Entry(xpos, packStatus.volt))
            voltRefValues.add(Entry(xpos, packStatus.voltRef))
            currentValues.add(Entry(xpos, packStatus.current))
            tempAmbientValues.add(Entry(xpos, packStatus.tempAmbient))
            tempChargerValues.add(Entry(xpos, packStatus.tempCharger))

            // add section markers:
            if (packStatus.isNewSection(lastStatus)) {
                val l = LimitLine(xpos)
                l.label = timeFmt.format(packStatus.timeStamp!!)
                l.textColor = Color.WHITE
                l.textStyle = Paint.Style.FILL
                l.setTextSize(6f)
                l.enableDashedLine(3f, 2f, 0f)
                xSections.add(l)
            }
            lastStatus = packStatus
        }

        // create data sets:
        val dataSets = ArrayList<ILineDataSet>()
        var dataSet: LineDataSet
        var packVoltSet: LineDataSet? = null
        var packVoltRefSet: LineDataSet? = null
        val packCurrentSet: LineDataSet?
        var packTempAmbientSet: LineDataSet? = null
        var packTempChargerSet: LineDataSet? = null
        if (showTemp) {
            if (carData!!.stale_ambient_temp != CarData.DataStale.NoValue) {
                dataSet = LineDataSet(
                    tempAmbientValues,
                    getString(R.string.aux_battery_data_temp_ambient)
                )
                packTempAmbientSet = dataSet
                dataSet.axisDependency = YAxis.AxisDependency.LEFT
                dataSet.setColor(COLOR_TEMP_AMBIENT)
                dataSet.setLineWidth(3f)
                dataSet.setDrawCircles(false)
                dataSet.setDrawValues(false)
                dataSets.add(dataSet)
            }
            dataSet =
                LineDataSet(tempChargerValues, getString(R.string.aux_battery_data_temp_charger))
            packTempChargerSet = dataSet
            dataSet.axisDependency = YAxis.AxisDependency.LEFT
            dataSet.setColor(COLOR_TEMP_CHARGER)
            dataSet.setLineWidth(3f)
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSets.add(dataSet)
        }
        dataSet = LineDataSet(currentValues, getString(R.string.aux_battery_data_current))
        packCurrentSet = dataSet
        dataSet.axisDependency = YAxis.AxisDependency.LEFT
        dataSet.setColor(COLOR_CURR_LINE)
        dataSet.setLineWidth(3f)
        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(false)
        dataSets.add(dataSet)
        if (showVolt) {
            dataSet = LineDataSet(voltRefValues, getString(R.string.aux_battery_data_volt_ref))
            packVoltRefSet = dataSet
            dataSet.axisDependency = YAxis.AxisDependency.RIGHT
            dataSet.setColor(COLOR_VOLT_REF)
            dataSet.setLineWidth(5f)
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSets.add(dataSet)
            dataSet = LineDataSet(voltValues, getString(R.string.aux_battery_data_volt))
            packVoltSet = dataSet
            dataSet.axisDependency = YAxis.AxisDependency.RIGHT
            dataSet.setColor(COLOR_VOLT)
            dataSet.setLineWidth(3f)
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSets.add(dataSet)
        }

        // configure y axes:
        var yMin: Float
        var yMax: Float

        // voltage axis:
        var yAxis = packChart.axisRight
        yAxis.isEnabled = showVolt
        if (packVoltSet != null) {
            yMax = max(packVoltSet.yMax.toDouble(), packVoltRefSet!!.yMax.toDouble())
                .toFloat()
            yMin = min(packVoltSet.yMin.toDouble(), packVoltRefSet.yMin.toDouble())
                .toFloat()
            yMax += 0.3.toFloat()
            yMin -= 0.3.toFloat()
            yAxis.setAxisMaximum(yMax)
            yAxis.setAxisMinimum(yMin - (yMax - yMin)) // half height
        }

        // current & temperature axis:
        yAxis = packChart.axisLeft
        yMax = packCurrentSet.yMax
        yMin = packCurrentSet.yMin
        if (packTempChargerSet != null) {
            yMax = max(yMax.toDouble(), packTempChargerSet.yMax.toDouble()).toFloat()
            yMin = min(yMin.toDouble(), packTempChargerSet.yMin.toDouble()).toFloat()
        }
        if (packTempAmbientSet != null) {
            yMax = max(yMax.toDouble(), packTempAmbientSet.yMax.toDouble()).toFloat()
            yMin = min(yMin.toDouble(), packTempAmbientSet.yMin.toDouble()).toFloat()
        }
        yMax += 3.0.toFloat()
        yMin -= 3.0.toFloat()
        if (showVolt) {
            // half height
            yAxis.setAxisMaximum(yMax + (yMax - yMin))
        } else {
            // full height
            yAxis.setAxisMaximum(yMax)
        }
        yAxis.setAxisMinimum(yMin)

        // display data sets:
        val data = LineData(dataSets)
        packData = data
        data.setValueTextColor(Color.WHITE)
        data.setValueTextSize(9f)
        packChart.setData(data)
        val xAxis = packChart.xAxis
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
        packChart.legend.textColor = Color.WHITE
        packChart.invalidate()
    }

    /**
     * Set pack chart highlight to a specific pack history index.
     *
     * This will try to keep the last selected data set. If highlightSetNr == -1
     * it will try to determine the data set by the last selected label.
     *
     * @param index - pack history index to highlight
     */
    private fun highlightPackEntry(index: Int) {
        // check model status:
        if (!isPackIndexValid(index)) {
            return
        }

        // determine data set to highlight:
        if (highlightSetNr == -1) {
            // get user selection by set label:
            val dataSet = packData!!.getDataSetByLabel(highlightSetLabel, false)
            highlightSetNr = packData!!.getIndexOfDataSet(dataSet)
        }
        if (highlightSetNr == -1) {
            // fallback to foreground set:
            highlightSetNr = packData!!.getDataSetCount() - 1
        }

        // highlight entry:
        val packStatus = batteryData!!.packHistory[index]
        val xpos = (packStatus.timeStamp!!.time / 1000 - timeStart).toFloat()
        packChart.highlightValue(xpos, highlightSetNr) // does not fire listener event

        // center highlight in chart viewport:
        val dataSet = packChart.data.getDataSetByIndex(highlightSetNr)
        if (dataSet != null) {
            val entry = dataSet.getEntryForXValue(xpos, 0f)
            if (entry != null) {
                packChart.centerViewTo(entry.x, entry.y, dataSet.axisDependency)
            }
        }
    }

    /*
     * Inner types
     */

    companion object {

        private const val TAG = "AuxBatteryFragment"

        // data set colors:
        private val COLOR_CURR_LINE = Color.parseColor("#FF3333")
        private val COLOR_CURR_TEXT = Color.parseColor("#FFAAAA")
        private val COLOR_CURR_GRID = Color.parseColor("#AA7777")
        private val COLOR_VOLT = Color.parseColor("#CCFF33")
        private val COLOR_VOLT_REF = Color.parseColor("#0077AA")
        private val COLOR_VOLT_GRID = Color.parseColor("#77AA77")
        private val COLOR_TEMP_AMBIENT = Color.parseColor("#FFEE33")
        private val COLOR_TEMP_CHARGER = Color.parseColor("#FFAA33")

        // system services:
        private val handler = Handler(Looper.getMainLooper())
    }
}
