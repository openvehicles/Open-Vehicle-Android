package com.openvehicles.OVMS.ui.settings

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.utils.Ui.setValue

class CellularStatsFragment : BaseFragment(), OnResultCommandListener {

    // data storage:
    private var lastRetrieve: Long = 0
    private var usageData: ArrayList<UsageData>? = null
    private var recNr = 0
    private var recCnt = 0
    private var carTotalBytes: Long? = null
    private var appTotalBytes: Long? = null

    // UI:
    private var chartViewCar: BarChart? = null
    private var chartViewApp: BarChart? = null

    // system services:
    private var service: ApiService? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_cellular_stats, null)
        createProgressOverlay(rootView, true)

        //
        // setup car chart:
        //
        val xFormatter: ValueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return try {
                    val day = usageData!![value.toInt()]
                    day.date.substring(5)
                } catch (e: Exception) {
                    ""
                }
            }
        }
        chartViewCar = rootView.findViewById<View>(R.id.cellular_usage_chart_car) as BarChart
        chartViewCar!!.description.isEnabled = false
        chartViewCar!!.setMaxVisibleValueCount(30)
        chartViewCar!!.setPinchZoom(false)
        chartViewCar!!.setDrawBarShadow(false)
        chartViewCar!!.setDrawValueAboveBar(true)
        chartViewCar!!.setDrawGridBackground(false)
        chartViewCar!!.setDrawBorders(true)
        var xAxis: XAxis = chartViewCar!!.xAxis
        xAxis.textColor = Color.WHITE
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = xFormatter
        xAxis.isGranularityEnabled = true
        xAxis.setGranularity(1f)
        var yAxis: YAxis = chartViewCar!!.axisLeft
        yAxis.textColor = Color.WHITE
        yAxis.gridColor = Color.LTGRAY
        yAxis.valueFormatter = DefaultAxisValueFormatter(0)
        yAxis.spaceBottom = 0f
        yAxis = chartViewCar!!.axisRight
        yAxis.isEnabled = false

        //
        // setup app chart:
        //
        chartViewApp = rootView.findViewById<View>(R.id.cellular_usage_chart_app) as BarChart
        chartViewApp!!.description.isEnabled = false
        chartViewApp!!.setMaxVisibleValueCount(30)
        chartViewApp!!.setPinchZoom(false)
        chartViewApp!!.setDrawBarShadow(false)
        chartViewApp!!.setDrawValueAboveBar(true)
        chartViewApp!!.setDrawGridBackground(false)
        chartViewApp!!.setDrawBorders(true)
        xAxis = chartViewApp!!.xAxis
        xAxis.textColor = Color.WHITE
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = xFormatter
        yAxis = chartViewApp!!.axisLeft
        yAxis.textColor = Color.WHITE
        yAxis.gridColor = Color.LTGRAY
        yAxis.valueFormatter = DefaultAxisValueFormatter(0)
        yAxis.spaceBottom = 0f
        yAxis = chartViewApp!!.axisRight
        yAxis.isEnabled = false

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        compatActivity?.setTitle(R.string.CellularStats)
    }

    override fun onServiceAvailable(service: ApiService) {
        this.service = service
    }

    override fun update(carData: CarData?) {
        // called after login / if new data is available
        requestData()
    }

    // request data from server:
    private fun requestData() {
        // check if server is available:
        if (service == null || !service!!.isLoggedIn()) {
            return
        }

        // check if we need an update:
        val now = System.currentTimeMillis() / 1000
        if (now - lastRetrieve > 3600) {
            // last update was >1 hour ago: refresh data
            lastRetrieve = now

            // Init storage:
            usageData = ArrayList(90)
            carTotalBytes = 0
            appTotalBytes = 0
            recCnt = 0
            recNr = 0

            // Show progress bar:
            showProgressOverlay()

            // Request cellular usage data:
            sendCommand("30", this)
        } else {
            // need to hide explicitly because onStart=true:
            hideProgressOverlay()
        }
    }

    override fun onResultCommand(result: Array<String>) {
        if (result.size < 2) {
            return
        }
        if (context == null) {
            return
        }
        val command = result[0].toInt()
        val returnCode = result[1].toInt()
        val resText = if (result.size > 2) result[2] else ""
        if (command != 30) {
            // Not for us
            return
        }

        when (returnCode) {
            0 -> {
                /*
                 * Result array structure:
                 * 0=command
                 * 1=return code
                 * 2=record number
                 * 3=maximum number of records
                 * 4=date (YYYY-MM-DD) (chronologically reverse)
                 * 5=car received bytes
                 * 6=car transmitted bytes
                 * 7=apps received bytes
                 * 8=apps transmitted bytes
                 */
                if (result.size >= 9) {
                    try {
                        // Progress info:
                        recNr = result[2].toInt()
                        recCnt = result[3].toInt()
                        stepProgressOverlay(recNr, recCnt)

                        // parse result data:
                        val nDate = result[4]
                        val nCarRxBytes = result[5].toInt()
                        val nCarTxBytes = result[6].toInt()
                        val nAppRxBytes = result[7].toInt()
                        val nAppTxBytes = result[8].toInt()

                        // add UsageData in reverse order:
                        usageData!!.add(
                            0,
                            UsageData(nDate, nCarRxBytes, nCarTxBytes, nAppRxBytes, nAppTxBytes)
                        )

                        // got all?
                        if (recNr == recCnt) {
                            cancelCommand()
                            updateUi()
                        }
                    } catch (e: Exception) {
                        // malformed record, ignore
                    }
                }
            }
            1 -> {
                Toast.makeText(
                    activity, getString(R.string.err_failed, resText),
                    Toast.LENGTH_SHORT
                ).show()
                cancelCommand()
            }
            2 -> {
                Toast.makeText(
                    activity, getString(R.string.err_unsupported_operation),
                    Toast.LENGTH_SHORT
                ).show()
                cancelCommand()
            }
            3 -> {
                Toast.makeText(
                    activity, getString(R.string.err_unimplemented_operation),
                    Toast.LENGTH_SHORT
                ).show()
                cancelCommand()
            }
        }
    }

    private fun updateUi() {
        if (recCnt == 0 || recNr < recCnt) {
            return
        }

        // Data ready: show results
        val days = usageData!!.size
        val carMBPerMonth = (carTotalBytes!! / days * 30 / 1000000).toFloat()
        val appMBPerMonth = (appTotalBytes!! / days * 30 / 1000000).toFloat()
        val rootView = view
        setValue(
            rootView!!, R.id.cellular_usage_info_car,
            getString(
                R.string.cellular_usage_info_car,
                carMBPerMonth, days
            )
        )
        setValue(
            rootView, R.id.cellular_usage_info_app,
            getString(
                R.string.cellular_usage_info_app,
                appMBPerMonth, days
            )
        )

        // Update charts:
        var day: UsageData
        val carValues = ArrayList<BarEntry>()
        val appValues = ArrayList<BarEntry>()
        for (i in usageData!!.indices) {
            day = usageData!![i]
            carValues.add(
                BarEntry(
                    i.toFloat(), floatArrayOf(
                        (
                                Math.round((day.carTxBytes / 100).toFloat()) / 10).toFloat(),
                        (
                                Math.round((day.carRxBytes / 100).toFloat()) / 10
                                ).toFloat()
                    ), i
                )
            )
            appValues.add(
                BarEntry(
                    i.toFloat(), floatArrayOf(
                        (
                                Math.round((day.appTxBytes / 100).toFloat()) / 10).toFloat(),
                        (
                                Math.round((day.appRxBytes / 100).toFloat()) / 10
                                ).toFloat()
                    ), i
                )
            )
        }
        var data: BarData
        val stackColors = intArrayOf(COLOR_TX, COLOR_RX)
        val stackLabels = arrayOf("Tx", "Rx")

        // ...car chart:
        var dataSet = BarDataSet(carValues, getString(R.string.chart_axis_datavolume))
        dataSet.setColors(*stackColors)
        dataSet.stackLabels = stackLabels
        var dataSets: ArrayList<IBarDataSet?> = ArrayList()
        dataSets.add(dataSet)
        data = BarData(dataSets)
        //data.setValueTextColor(Color.WHITE);
        //data.setValueTextSize(9f);
        data.setDrawValues(false)
        chartViewCar!!.setData(data)
        chartViewCar!!.zoom(3.5f, 1f, 0f, 0f)
        chartViewCar!!.moveViewToX((usageData!!.size - 1).toFloat())
        var legend: Legend = chartViewCar!!.legend
        legend.textColor = Color.WHITE
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        chartViewCar!!.invalidate()

        // ...app chart:
        dataSet = BarDataSet(appValues, getString(R.string.chart_axis_datavolume))
        dataSet.setColors(*stackColors)
        dataSet.stackLabels = stackLabels
        dataSets = ArrayList()
        dataSets.add(dataSet)
        data = BarData(dataSets)
        //data.setValueTextColor(Color.WHITE);
        //data.setValueTextSize(9f);
        data.setDrawValues(false)
        chartViewApp!!.setData(data)
        chartViewApp!!.zoom(3.5f, 1f, 0f, 0f)
        chartViewApp!!.moveViewToX((usageData!!.size - 1).toFloat())
        legend = chartViewApp!!.legend
        legend.textColor = Color.WHITE
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        chartViewApp!!.invalidate()
    }

    /*
     * Inner types
     */

    companion object {

        private const val TAG = "CellularStatsFragment"

        // chart colors:
        private val COLOR_TX = Color.parseColor("#33B5E5")
        private val COLOR_RX = Color.parseColor("#99CC00")
    }

    // data model:
    private inner class UsageData(
        var date: String,
        var carRxBytes: Int,
        var carTxBytes: Int,
        var appRxBytes: Int,
        var appTxBytes: Int
    ) {
        init {
            // fill object:

            // update totals:
            carTotalBytes = carTotalBytes!! + (carRxBytes + carTxBytes).toLong()
            appTotalBytes = appTotalBytes!! + (appRxBytes + appTxBytes).toLong()
        }
    }
}
