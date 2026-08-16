package com.openvehicles.OVMS.entities

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.openvehicles.OVMS.BaseApp.Companion.app
import com.openvehicles.OVMS.R
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

/**
 * Created by balzer on 07.03.15.
 *
 * This is the model for LogsFragment.
 *
 * It's currently designed to meet the storage requirements for the
 * SEVCON diagnostic logs as supported by the Twizy firmware,
 * but can be used as a container for other vehicles and/or logs.
 *
 */
class LogsData {

    private var vehicleId = ""
    private var keyTime: KeyTime
    @JvmField
    var alerts: ArrayList<Alert>
    @JvmField
    var alertsTime: KeyTime
    @JvmField
    var faultEvents: ArrayList<Event>
    @JvmField
    var faultEventsTime: KeyTime
    @JvmField
    var systemEvents: ArrayList<Event>
    @JvmField
    var systemEventsTime: KeyTime
    @JvmField
    var counters: ArrayList<Counter>
    @JvmField
    var countersTime: KeyTime
    @JvmField
    var minMaxes: ArrayList<MinMax>
    @JvmField
    var minMaxesTime: KeyTime

    init {
        keyTime = KeyTime()
        alerts = ArrayList()
        alertsTime = KeyTime()
        faultEvents = ArrayList()
        faultEventsTime = KeyTime()
        systemEvents = ArrayList()
        systemEventsTime = KeyTime()
        counters = ArrayList()
        countersTime = KeyTime()
        minMaxes = ArrayList()
        minMaxesTime = KeyTime()
    }

    fun saveFile(vehicleId: String): Boolean {
        val outputStream: FileOutputStream
        val filename = "logsdata-$vehicleId-default.json"
        Log.v(TAG, "saving to file: $filename")
        this.vehicleId = vehicleId
        val json = gson.toJson(this)
        return try {
            outputStream = context!!.openFileOutput(filename, Context.MODE_PRIVATE)
            outputStream.write(json.toByteArray())
            outputStream.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun processCmdResults(cmdSeries: CmdSeries) {
        var recNr: Int
        var recCnt: Int
        var recType: String
        var timeStamp: String
        var entryNr: Int
        val serverTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        serverTime.timeZone = TimeZone.getTimeZone("UTC")
        for (i in 0 until cmdSeries.size()) {
            val cmd = cmdSeries[i]
            if (cmd.commandCode != 32) continue
            for (resNr in cmd.results.indices) {
                val result = cmd.results[resNr]
                if (result.size > 2 && result[2] == "No historical data available") continue
                try {
                    recNr = result[2].toInt()
                    recCnt = result[3].toInt()
                    recType = result[4]
                    timeStamp = result[5]
                    entryNr = result[6].toInt()

                    // [7++] = Payload
                    Log.v(TAG, "processing recType $recType entryNr $entryNr")
                    if (recType == "RT-ENG-LogKeyTime") {
                        keyTime = KeyTime()
                        keyTime.keyHour = result[7].toInt()
                        keyTime.keyMinSec = result[8].toInt()
                        try {
                            keyTime.timeStamp = serverTime.parse(timeStamp)
                        } catch (e: Exception) {
                            keyTime.timeStamp = Date()
                        }
                    } else if (recType == "RT-ENG-LogAlerts") {
                        if (entryNr == 0) {
                            alerts = ArrayList(20)
                            alertsTime = keyTime
                        }
                        val alert = Alert()
                        alert.code = result[7]
                        alert.description = result[8]
                        alerts.add(alert)
                    } else if (recType == "RT-ENG-LogFaults") {
                        if (entryNr == 0) {
                            faultEvents = ArrayList(40)
                            faultEventsTime = keyTime
                        }
                        val event = Event()
                        event.code = result[7]
                        event.description = result[8]
                        event.time = KeyTime()
                        event.time!!.keyHour = result[9].toInt()
                        event.time!!.keyMinSec = result[10].toInt()
                        event.data = arrayOfNulls(3)
                        event.data[0] = result[11]
                        event.data[1] = result[12]
                        event.data[2] = result[13]
                        faultEvents.add(event)
                    } else if (recType == "RT-ENG-LogSystem") {
                        if (entryNr == 0) {
                            systemEvents = ArrayList(20)
                            systemEventsTime = keyTime
                        }
                        val event = Event()
                        event.code = result[7]
                        event.description = result[8]
                        event.time = KeyTime()
                        event.time!!.keyHour = result[9].toInt()
                        event.time!!.keyMinSec = result[10].toInt()
                        event.data = arrayOfNulls(3)
                        event.data[0] = result[11]
                        event.data[1] = result[12]
                        event.data[2] = result[13]
                        systemEvents.add(event)
                    } else if (recType == "RT-ENG-LogCounts") {
                        if (entryNr == 0) {
                            counters = ArrayList(10)
                            countersTime = keyTime
                        }
                        val counter = Counter()
                        counter.code = result[7]
                        counter.description = result[8]
                        counter.lastTime = KeyTime()
                        counter.lastTime!!.keyHour = result[9].toInt()
                        counter.lastTime!!.keyMinSec = result[10].toInt()
                        counter.firstTime = KeyTime()
                        counter.firstTime!!.keyHour = result[11].toInt()
                        counter.firstTime!!.keyMinSec = result[12].toInt()
                        counter.count = result[13].toInt()
                        counters.add(counter)
                    } else if (recType == "RT-ENG-LogMinMax") {
                        if (entryNr == 0) {
                            minMaxes = ArrayList(2)
                            minMaxesTime = keyTime
                        }
                        val minMax = MinMax()
                        minMax.batteryVoltageMin = result[7].toDouble() / 16
                        minMax.batteryVoltageMax = result[8].toDouble() / 16
                        minMax.capacitorVoltageMin = result[9].toDouble() / 16
                        minMax.capacitorVoltageMax = result[10].toDouble() / 16
                        minMax.motorCurrentMin = result[11].toInt()
                        minMax.motorCurrentMax = result[12].toInt()
                        minMax.motorSpeedMin = result[13].toInt()
                        minMax.motorSpeedMax = result[14].toInt()
                        minMax.deviceTempMin = result[15].toInt()
                        minMax.deviceTempMax = result[16].toInt()
                        minMaxes.add(minMax)
                    }
                } catch (e: Exception) {
                    // most probably parse error, skip row
                    e.printStackTrace()
                }
            }
        }
        Log.v(TAG, "processCmdResults done")
    }

    /*
     * Inner types
     */

    companion object {

        @Transient
        private val TAG = "LogsData"

        //
        // System environment:
        //
        @Transient
        private val context: Context? = app

        @Transient
        private val gson = Gson()
        @JvmStatic
        fun loadFile(vehicleId: String): LogsData? {
            val inputStream: FileInputStream
            val filename = "logsdata-$vehicleId-default.json"
            Log.v(TAG, "loading from file: $filename")
            return try {
                inputStream = context!!.openFileInput(filename)
                val isr = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(isr)
                val sb = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                val json = sb.toString()
                gson.fromJson<LogsData>(json, LogsData::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    //
    // Storage classes:
    //

    class KeyTime {

        var keyHour = 0
        var keyMinSec = 0
        var timeStamp: Date? = null
        fun fmtKeyTime(): String {
            val days = keyHour / 24
            val hours = keyHour % 24
            val mins = keyMinSec * 15 / 60
            val secs = keyMinSec * 15 % 60
            return context!!.getString(
                R.string.logs_fmt_keytime,
                days, hours, mins, secs
            )
        }

        fun fmtTimeStamp(): String {
            return if (timeStamp != null) DateFormat.getDateTimeInstance()
                .format(timeStamp) else "-"
        }
    }

    class Alert {

        @JvmField
        var code: String? = null
        @JvmField
        var description: String? = null
    }

    class Event {

        @JvmField
        var code: String? = null
        @JvmField
        var description: String? = null
        @JvmField
        var time: KeyTime? = null
        @JvmField
        var data: Array<String?> = arrayOf()
    }

    class Counter {

        @JvmField
        var code: String? = null
        @JvmField
        var description: String? = null
        @JvmField
        var lastTime: KeyTime? = null
        @JvmField
        var firstTime: KeyTime? = null
        @JvmField
        var count = 0
    }

    class MinMax {

        var batteryVoltageMin: Double? = null
        var batteryVoltageMax: Double? = null
        var capacitorVoltageMin: Double? = null
        var capacitorVoltageMax: Double? = null
        var motorCurrentMin = 0
        var motorCurrentMax = 0
        var motorSpeedMin = 0
        var motorSpeedMax = 0
        var deviceTempMin = 0
        var deviceTempMax = 0
        fun getSensor(nr: Int): String {
            return when (nr) {
                0 -> batteryVoltageMin.toString()
                1 -> batteryVoltageMax.toString()
                2 -> capacitorVoltageMin.toString()
                3 -> capacitorVoltageMax.toString()
                4 -> "" + motorCurrentMin
                5 -> "" + motorCurrentMax
                6 -> "" + motorSpeedMin
                7 -> "" + motorSpeedMax
                8 -> "" + deviceTempMin
                9 -> "" + deviceTempMax
                else -> ""
            }
        }
    }
}
