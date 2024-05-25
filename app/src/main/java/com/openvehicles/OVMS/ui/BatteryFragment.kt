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
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AlertDialog
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.BatteryData
import com.openvehicles.OVMS.entities.BatteryData.CellStatus
import com.openvehicles.OVMS.entities.BatteryData.Companion.loadFile
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.entities.CmdSeries
import com.openvehicles.OVMS.ui.utils.ProgressOverlay
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.CarsStorage.getSelectedCarData
import java.text.SimpleDateFormat
import kotlin.math.max
import kotlin.math.min

/**
 * Battery pack and cell status history charts.
 *
 * Currently only usable for Renault Twizy, but can be extended to support
 * other battery layouts easily as soon as other vehicles deliver this info.
 *
 * Data model: BatteryData
 *
 * Created by balzer on 27.03.15.
 */
class BatteryFragment : BaseFragment(), CmdSeries.Listener, ProgressOverlay.OnCancelListener {

    // data storage:
    private lateinit var batteryData: BatteryData

    // user interface:
    private lateinit var optionsMenu: Menu
    private lateinit var packChart: LineChart
    private var packData: LineData? = null
    private var packVoltSet: LineDataSet? = null
    private var packVoltMinSet: LineDataSet? = null
    private var packTempSet: LineDataSet? = null
    private lateinit var cellChart: CandleStickChart
    private lateinit var seekPack: SeekBar

    private var highlightSetNr = -1
    private var highlightSetLabel = ""
    private var showVolt = true
    private var showTemp = false
    private var carData: CarData? = null
    private var cmdSeries: CmdSeries? = null
    private lateinit var appPrefs: AppPrefs

    private val isPackValid: Boolean
        // Check data model validity
        get() = batteryData.cellCount != 0
                && batteryData.packHistory.isNotEmpty()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Init data storage:
        batteryData = BatteryData()

        // Load prefs:
        appPrefs = AppPrefs(requireActivity(), "ovms")
        showVolt = appPrefs.getData("battery_show_volt") == "on"
        showTemp = appPrefs.getData("battery_show_temp") == "on"
        if (!showVolt && !showTemp) {
            showVolt = true
        }

        // Setup UI:
        val progressOverlay = createProgressOverlay(inflater, container, false)
        progressOverlay.setOnCancelListener(this)
        val rootView = inflater.inflate(R.layout.fragment_battery, null)

        //
        // Setup Cell status chart:
        //
        cellChart = (rootView.findViewById<View>(R.id.chart_cells) as CandleStickChart).apply {
            description = cellChart.description.apply {
                text = getString(R.string.battery_cell_description)
                textColor = Color.LTGRAY
            }
            setDrawGridBackground(false)
            setDrawBorders(true)
        }
        var xAxis: XAxis = cellChart.xAxis
        xAxis.textColor = Color.WHITE
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "#" + (value.toInt() + 1)
            }
        }
        xAxis.isGranularityEnabled = true
        xAxis.setGranularity(1f)
        var yAxis: YAxis = cellChart.axisLeft
        yAxis.textColor = COLOR_VOLT
        yAxis.gridColor = COLOR_VOLT_GRID
        yAxis.valueFormatter = DefaultAxisValueFormatter(2)
        yAxis.setGranularity(0.01f)
        yAxis = cellChart.axisRight
        yAxis.textColor = COLOR_TEMP
        yAxis.gridColor = COLOR_TEMP_GRID
        yAxis.valueFormatter = DefaultAxisValueFormatter(0)

        //
        // Setup Pack history chart:
        //
        packChart = rootView.findViewById<View>(R.id.chart_pack) as LineChart
        packChart.description.isEnabled = false
        packChart.setDrawGridBackground(false)
        packChart.setDrawBorders(true)
        packChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(entry: Entry, highlight: Highlight) {
                // remember user data set selection:
                val dataSet = highlight.dataSetIndex
                highlightSetNr = dataSet
                highlightSetLabel = packChart.data.getDataSetByIndex(dataSet).label

                // update seek bar:
                seekPack.progress = entry.x.toInt() // fires listener event (fromUser=false)

                // update cell chart:
                showCellStatus(entry.x.toInt())
            }

            override fun onNothingSelected() {
                // nop
            }
        })
        xAxis = packChart.xAxis
        xAxis.textColor = Color.WHITE
        xAxis.valueFormatter = PackTimeValueFormatter()
        xAxis.isGranularityEnabled = true
        xAxis.setGranularity(1f)
        yAxis = packChart.axisLeft
        yAxis.spaceTop = 5f
        yAxis.spaceBottom = 5f
        yAxis.textColor = COLOR_SOC_TEXT
        yAxis.gridColor = COLOR_SOC_GRID
        yAxis.valueFormatter = object : DefaultAxisValueFormatter(0) {
            override fun getFormattedValue(value: Float): String {
                return super.getFormattedValue(value) + "%"
            }
        }
        yAxis = packChart.axisRight
        yAxis.spaceTop = 15f
        yAxis.spaceBottom = 15f
        yAxis.textColor = COLOR_VOLT
        yAxis.gridColor = COLOR_VOLT_GRID
        yAxis.valueFormatter = object : DefaultAxisValueFormatter(1) {
            override fun getFormattedValue(value: Float): String {
                return super.getFormattedValue(value) + "%"
            }
        }
        yAxis.setGranularity(0.1f)

        //
        // Setup Pack history seek bar:
        //
        seekPack = rootView.findViewById<View>(R.id.seek_pack) as SeekBar
        seekPack.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, `val`: Int, fromUser: Boolean) {
                if (fromUser) {
                    // highlight entry:
                    highlightPackEntry(`val`)
                    // update cell chart:
                    showCellStatus(`val`)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // nop
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // nop
            }
        })

        // default data set to highlight:
        highlightSetLabel = getString(R.string.battery_data_soc)

        // attach menu:
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.battery_chart_options, menu)
        optionsMenu = menu
        optionsMenu.findItem(R.id.mi_chk_volt).setChecked(showVolt)
        optionsMenu.findItem(R.id.mi_chk_temp).setChecked(showTemp)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        compatActivity!!.setTitle(R.string.battery_title)
        compatActivity!!.supportActionBar!!.setIcon(R.drawable.ic_action_chart)

        // get data of current car:
        carData = getSelectedCarData()

        // schedule data loader:
        showProgressOverlay(getString(R.string.battery_msg_loading_data))
        handler.postDelayed({ // load and display saved vehicle data:
            val saved = loadFile(carData!!.sel_vehicleid)
            if (saved != null) {
                Log.v(TAG, "BatteryData loaded for " + carData!!.sel_vehicleid)
                batteryData = saved
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
                cmdSeries = CmdSeries(getService(), this@BatteryFragment)
                    .add(R.string.battery_msg_get_status, "206") // ensure non-empty history
                    .add(R.string.battery_msg_get_battpack, "32,RT-BAT-P")
                    .add(R.string.battery_msg_get_battcell, "32,RT-BAT-C")
                    .start()
                return true
            }
            R.id.mi_reset_view -> {
                packChart.fitScreen()
                cellChart.fitScreen()
                return true
            }
            R.id.mi_help -> {
                AlertDialog.Builder(requireActivity())
                    .setTitle(R.string.battery_btn_help)
                    .setMessage(Html.fromHtml(getString(R.string.battery_help)))
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
                return true
            }
            R.id.mi_chk_volt -> {
                showVolt = newState
                if (!showVolt && !showTemp) {
                    showTemp = true
                    optionsMenu.findItem(R.id.mi_chk_temp).setChecked(true)
                }
                item.setChecked(newState)
                dataFilterChanged()
                return true
            }
            R.id.mi_chk_temp -> {
                showTemp = newState
                if (!showVolt && !showTemp) {
                    showVolt = true
                    optionsMenu.findItem(R.id.mi_chk_volt).setChecked(true)
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
        var errorDetail: String
        hideProgressOverlay()
        when (returnCode) {
            -1 -> return
            0 -> {
                showProgressOverlay(getString(R.string.msg_processing_data))
                batteryData.processCmdResults(cmdSeries)
                batteryData.saveFile(carData!!.sel_vehicleid)
                dataSetChanged()
                hideProgressOverlay()
                return
            }
            1 -> {
                errorDetail = cmdSeries.getErrorDetail()
                if (errorDetail.contains("B ")) {
                    errorDetail += getString(R.string.hint_sevcon_offline)
                }
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
        return isPackValid
                && index < batteryData.packHistory.size
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
        val lastEntry = batteryData.packHistory.size - 1

        // update seek bar:
        seekPack.setMax(lastEntry)
        seekPack.progress = lastEntry

        // highlight latest entry:
        highlightPackEntry(lastEntry)

        // show latest cell status:
        showCellStatus(lastEntry)
    }

    /**
     * Call when user changed data filter (i.e. show Volt / Temp sets)
     */
    private fun dataFilterChanged() {
        // save prefs:
        appPrefs.saveData("battery_show_volt", if (showVolt) "on" else "off")
        appPrefs.saveData("battery_show_temp", if (showTemp) "on" else "off")

        // check data status:
        if (!isPackValid) {
            return
        }

        // update pack chart:
        showPackHistory()
        val seekEntry = seekPack.progress

        // highlight last selected entry:
        highlightSetNr = -1 // ...
        highlightPackEntry(seekEntry)

        // show last selected entry cell status:
        showCellStatus(seekEntry)
    }

    /**
     * Update the pack chart
     */
    private fun showPackHistory() {
        if (!isPackValid) {
            return
        }
        val packHistory = batteryData.packHistory
        var packStatus: BatteryData.PackStatus
        var lastStatus: BatteryData.PackStatus?
        val timeFmt = SimpleDateFormat("HH:mm")

        //
        // Pack chart:
        //

        // create value arrays:
        val xSections = ArrayList<LimitLine>()
        val socValues = ArrayList<Entry>()
        val voltValues = ArrayList<Entry>()
        val voltMinValues = ArrayList<Entry>()
        val tempValues = ArrayList<Entry>()
        lastStatus = null
        for (i in packHistory.indices) {
            packStatus = packHistory[i]
            val xPos = i.toFloat()
            socValues.add(Entry(xPos, packStatus.soc))
            voltValues.add(Entry(xPos, packStatus.volt))
            voltMinValues.add(Entry(xPos, packStatus.voltMin))
            tempValues.add(Entry(xPos, packStatus.temp))

            // add section markers:
            if (packStatus.isNewSection(lastStatus)) {
                val limitLine = LimitLine(xPos)
                limitLine.label = timeFmt.format(packStatus.timeStamp)
                limitLine.textColor = Color.WHITE
                limitLine.textStyle = Paint.Style.FILL
                limitLine.setTextSize(6f)
                limitLine.enableDashedLine(3f, 2f, 0f)
                xSections.add(limitLine)
            }
            lastStatus = packStatus
        }

        // create data sets:
        val dataSets = ArrayList<ILineDataSet>()
        var dataSet: LineDataSet
        packTempSet = null
        packVoltSet = null
        if (showTemp) {
            dataSet = LineDataSet(tempValues, getString(R.string.battery_data_temp))
            packTempSet = dataSet
            dataSet.axisDependency = YAxis.AxisDependency.RIGHT
            dataSet.setColor(COLOR_TEMP)
            dataSet.setLineWidth(3f)
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSets.add(dataSet)
        }
        if (showVolt) {
            dataSet = LineDataSet(voltMinValues, getString(R.string.battery_data_volt_min))
            packVoltMinSet = dataSet
            dataSet.axisDependency = YAxis.AxisDependency.RIGHT
            dataSet.setColor(COLOR_VOLT_MIN)
            dataSet.setLineWidth(2f)
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSets.add(dataSet)
            dataSet = LineDataSet(voltValues, getString(R.string.battery_data_volt))
            packVoltSet = dataSet
            dataSet.axisDependency = YAxis.AxisDependency.RIGHT
            dataSet.setColor(COLOR_VOLT)
            dataSet.setLineWidth(3f)
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSets.add(dataSet)
        }
        dataSet = LineDataSet(socValues, getString(R.string.battery_data_soc))
        dataSet.axisDependency = YAxis.AxisDependency.LEFT
        dataSet.setColor(COLOR_SOC_LINE)
        dataSet.setLineWidth(4f)
        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(false)
        dataSets.add(dataSet)

        // display data sets:
        val data: LineData = LineData(dataSets)
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
        if (!isPackIndexValid(index)) {
            return
        }

        // check model status:
        if (index >= batteryData.packHistory.size) {
            Log.e(TAG, "highlighPackEntry: #$index out of bounds")
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
        packChart.highlightValue(index.toFloat(), highlightSetNr) // does not fire listener event

        // center highlight in chart viewport:
        val dataSet = packChart.data.getDataSetByIndex(highlightSetNr)
        if (dataSet != null) {
            val entry = dataSet.getEntryForXValue(index.toFloat(), 0f)
            if (entry != null) {
                packChart.centerViewTo(entry.x, entry.y, dataSet.axisDependency)
            }
        }
    }

    /**
     * Update the cell chart
     *
     * @param index - pack history index to display
     */
    private fun showCellStatus(index: Int) {
        if (!isPackIndexValid(index)) {
            return
        }

        val packHistory = batteryData.packHistory
        var cell: CellStatus?
        val pack: BatteryData.PackStatus = packHistory[index]
        val cells: ArrayList<CellStatus?>? = pack.cells
        if (cells == null) {
            Log.w(TAG, "showCellStatus x=$index: cells=null")
            return
        }

        // create value arrays:
        val voltValues = ArrayList<CandleEntry>()
        val tempValues = ArrayList<CandleEntry>()
        var low: Float
        var high: Float
        var open: Float
        var close: Float
        for (i in 0 until batteryData.cellCount) {
            cell = cells[i]

            // Volt: high=current, low=min
            high = cell!!.volt
            low = cell.voltMin
            if (cell.voltDevMax < 0) {
                // bad: cell voltage breaks more down than normal
                // => filled candle (close < open)
                open = high
                close = high + cell.voltDevMax
                if (close < low) {
                    low = close
                }
            } else {
                // ok:
                open = high - cell.voltDevMax
                close = high
                if (open < low) {
                    low = open
                }
            }
            voltValues.add(CandleEntry(i.toFloat(), high, low, open, close))

            // Temp: high=max, low=min
            open = cell.temp + cell.tempDevMax / 2.1f
            close = cell.temp - cell.tempDevMax / 2.1f
            high = max(cell.tempMax.toDouble(), max(open.toDouble(), close.toDouble()))
                .toFloat()
            low = min(cell.tempMin.toDouble(), min(open.toDouble(), close.toDouble()))
                .toFloat()
            tempValues.add(CandleEntry(i.toFloat(), high, low, open, close))
        }

        // create data sets:
        val dataSets = ArrayList<ICandleDataSet>()
        var dataSet: CandleDataSet
        if (showTemp) {
            dataSet = CandleDataSet(tempValues, getString(R.string.battery_data_temp))
            dataSet.axisDependency = YAxis.AxisDependency.RIGHT
            dataSet.neutralColor = COLOR_TEMP
            dataSet.increasingColor = COLOR_TEMP
            dataSet.decreasingColor = COLOR_TEMP
            dataSet.shadowColor = COLOR_TEMP
            dataSet.setDrawValues(true)
            dataSet.shadowWidth = 4f
            dataSet.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return String.format("%.0f", value)
                }
            }
            dataSets.add(dataSet)
        }
        if (showVolt) {
            dataSet = CandleDataSet(voltValues, getString(R.string.battery_data_volt))
            dataSet.axisDependency = YAxis.AxisDependency.LEFT
            dataSet.neutralColor = COLOR_VOLT
            dataSet.increasingColor = COLOR_VOLT
            dataSet.decreasingColor = COLOR_VOLT
            dataSet.shadowColor = COLOR_VOLT
            dataSet.setDrawValues(true)
            dataSet.shadowWidth = 4f
            dataSet.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return String.format("%.3f", value)
                }
            }
            dataSets.add(dataSet)
        }

        // configure y axes:
        var yAxis = cellChart.axisLeft
        yAxis.isEnabled = showVolt
        if (showVolt && packVoltSet != null) {
            val yMax = packVoltSet!!.yMax / batteryData.cellCount.toFloat() + 0.1f
            val yMin = packVoltMinSet!!.yMin / batteryData.cellCount.toFloat() - 0.1f
            yAxis.setAxisMaximum(yMax)
            if (showTemp) {
                // half height
                yAxis.setAxisMinimum(yMin - (yMax - yMin))
            } else {
                // full height
                yAxis.setAxisMinimum(yMin)
            }
        }
        yAxis = cellChart.axisRight
        yAxis.isEnabled = showTemp
        if (showTemp && packTempSet != null) {
            val yMax = packTempSet!!.yMax + 3f
            val yMin = packTempSet!!.yMin - 3f
            if (showVolt) {
                // half height
                yAxis.setAxisMaximum(yMax + (yMax - yMin))
            } else {
                // full height
                yAxis.setAxisMaximum(yMax)
            }
            yAxis.setAxisMinimum(yMin)
        }

        // display data sets:
        val data = CandleData(dataSets)
        data.setValueTextColor(Color.WHITE)
        data.setValueTextSize(9f)
        cellChart.setData(data)
        cellChart.legend.textColor = Color.WHITE
        cellChart.invalidate()
    }

    /*
     * Inner types
     */

    companion object {

        private const val TAG = "BatteryFragment"

        // data set colors:
        private val COLOR_SOC_LINE = Color.parseColor("#A04455FF")
        private val COLOR_SOC_TEXT = Color.parseColor("#AAAAFF")
        private val COLOR_SOC_GRID = Color.parseColor("#7777AA")
        private val COLOR_VOLT = Color.parseColor("#CCFF33")
        private val COLOR_VOLT_MIN = Color.parseColor("#77AA00")
        private val COLOR_VOLT_GRID = Color.parseColor("#77AA77")
        private val COLOR_TEMP = Color.parseColor("#FFEE33")
        private val COLOR_TEMP_GRID = Color.parseColor("#AAAA77")

        // system services:
        private val handler = Handler(Looper.getMainLooper())
    }

    inner class PackTimeValueFormatter : ValueFormatter() {

        private var timeFmt: SimpleDateFormat = SimpleDateFormat("HH:mm")

        override fun getFormattedValue(value: Float): String {
            return try {
                val entry = batteryData.packHistory[value.toInt()]
                timeFmt.format(entry.timeStamp)
            } catch (e: Exception) {
                ""
            }
        }
    }
}
