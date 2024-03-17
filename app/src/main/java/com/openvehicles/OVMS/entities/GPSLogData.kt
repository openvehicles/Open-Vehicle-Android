package com.openvehicles.OVMS.entities

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.openvehicles.OVMS.BaseApp.Companion.app
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

/**
 * Created by balzer on 06.04.15.
 */
class GPSLogData {

    //
    // Storage data:
    //
    private var vehicleId = ""
    @JvmField
    var entries: ArrayList<Entry> = ArrayList(24 * 60)

    fun saveFile(vehicleId: String): Boolean {
        val outputStream: FileOutputStream
        val filename = "gpslog-$vehicleId-default.json"
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
        var timeStamp: Date
        val serverTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        serverTime.timeZone = TimeZone.getTimeZone("UTC")
        var entry: Entry
        for (i in 0 until cmdSeries.size()) {
            val cmd = cmdSeries[i]
            if (cmd.commandCode != 32) continue

            // init:
            if (cmd.command == "32,RT-GPS-Log") {
                entries.clear()
            }
            for (resNr in cmd.results.indices) {
                val result = cmd.results[resNr]
                if (result.size > 2 && result[2] == "No historical data available") continue
                try {
                    recNr = result[2].toInt()
                    recCnt = result[3].toInt()
                    recType = result[4]
                    timeStamp = serverTime.parse(result[5])
                    Log.v(TAG, "processing recType $recType entryNr $recNr/$recCnt")
                    if (recType == "RT-GPS-Log") {
                        try {
                            // create record:
                            entry = Entry()
                            entry.timeStamp = timeStamp
                            var j = 6
                            entry.odometerMi = result[j++].toInt() / 10f
                            entry.latitude = result[j++].toDouble()
                            entry.longitude = result[j++].toDouble()
                            entry.altitude = result[j++].toFloat()
                            entry.direction = result[j++].toFloat()
                            entry.speed = result[j++].toFloat()
                            entry.gspFix = result[j++].toInt()
                            entry.gpsStaleCnt = result[j++].toInt()
                            entry.gsmSignal = result[j++].toInt()
                            entry.currentPower = result[j++].toInt()
                            entry.powerUsedWh = result[j++].toInt()
                            entry.powerRecdWh = result[j++].toInt()
                            entry.powerDistance = result[j++].toInt()
                            entry.minPower = result[j++].toInt()
                            entry.maxPower = result[j++].toInt()
                            entry.carStatus = result[j++].toInt(16)

                            // V3.6 extensions:
                            if (result.size > j) {
                                entry.bmsDriveLimit = result[j++].toInt()
                                entry.bmsRecupLimit = result[j++].toInt()
                                entry.autoDriveLevel = result[j++].toFloat()
                                entry.autoRecupLevel = result[j++].toFloat()
                            }
                            if (result.size > j) {
                                entry.minCurrent = result[j++].toFloat()
                                entry.maxCurrent = result[j++].toFloat()
                            }

                            // store record:
                            entries.add(entry)
                        } catch (e: Exception) {
                            // invalid record: skip
                            Log.e(TAG, "GPS-Log skip: " + e.message)
                        }
                    }
                } catch (e: Exception) {
                    // most probably parse error, skip row
                    e.printStackTrace()
                }
            }

            // no more results: finish
            if (cmd.command == "32,RT-GPS-Log") {
                try {
                    // ...?
                } catch (e: Exception) {
                    Log.e(TAG, "GPS-Log finish error: " + e.message)
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
        private val TAG = "GPSLogData"

        //
        // System environment:
        //
        @Transient
        private val context: Context? = app

        @Transient
        private val gson = Gson()
        @JvmStatic
        fun loadFile(vehicleId: String): GPSLogData? {
            val inputStream: FileInputStream
            val filename = "gpslog-$vehicleId-default.json"
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
                gson.fromJson<GPSLogData>(json, GPSLogData::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    class Entry {

        @JvmField
        var timeStamp: Date? = null
        var odometerMi = 0f
        var latitude = 0.0
        var longitude = 0.0
        @JvmField
        var altitude = 0f
        var direction = 0f
        var speed = 0f
        var gspFix = 0
        var gpsStaleCnt = 0
        var gsmSignal = 0
        var currentPower = 0
        var powerUsedWh = 0
        var powerRecdWh = 0
        var powerDistance = 0
        var minPower = 0
        var maxPower = 0
        var carStatus = 0

        // Twizy status flags:
        //  bit 0 = 0x01: 1 = Footbrake
        //  bit 1 = 0x02: 1 = Forward mode "D"
        //  bit 2 = 0x04: 1 = Reverse mode "R"
        //  bit 3 = 0x08: 1 = "GO" = Motor ON (Ignition)
        //  bit 4 = 0x10: 1 = Car awake (key turned)
        //  bit 5 = 0x20: 1 = Charging
        //  bit 6 = 0x40: 1 = Switch-ON/-OFF phase / 0 = normal operation
        //  bit 7 = 0x80: 1 = CAN-Bus online (test flag to detect offline)
        // V3.6.0: add BMS power limits [W] and autopower levels [%]
        var bmsDriveLimit = 0
        var bmsRecupLimit = 0
        var autoDriveLevel = 0f
        var autoRecupLevel = 0f

        // V3.6.0: add current min/max
        var minCurrent = 0f
        var maxCurrent = 0f

        /**
         * Operative status
         *
         * Values: -1=charging / 0=off / 1=on
         */
        val opStatus: Int
            get() = if (carStatus and 0x60 == 0x20) -1 else if (carStatus and 0x50 == 0x10) 1 else 0

        /**
         * Key status.
         *
         * Values: 0x10=on / 0x00=off
         */
        private val keyStatus: Int
            get() = carStatus and 0x10

        /**
         * Check if this is a new data point to be displayed (= X axis entry):
         * - time delta >= 60 seconds
         *
         * @param ref -- last data point displayed
         * @return
         */
        fun isNewTimePoint(ref: Entry?): Boolean {
            return (ref != null && (timeStamp!!.time - ref.timeStamp!!.time) / 1000f >= 60
                    && (opStatus != 0 || ref.opStatus != 0))
        }

        /**
         * Check if this data point is the beginning of a new section (drive/charge).
         *
         * @param ref
         * @return
         */
        fun isSectionStart(ref: Entry?): Boolean {
            return (ref != null
                    && (keyStatus > ref.keyStatus || powerDistance < ref.powerDistance || powerUsedWh < ref.powerUsedWh || powerRecdWh < ref.powerRecdWh))
        }

        private fun getTimeDiff(ref: Entry): Float {
            return (timeStamp!!.time - ref.timeStamp!!.time) / 1000f / 60f / 60f
        }

        fun getOdometer(unit: String): Float {
            return if (unit == "M") odometerMi else odometerMi * 1.609344f
        }

        private fun getOdoDiff(ref: Entry, unit: String): Float {
            val odoDiff = odometerMi - ref.odometerMi
            return if (unit == "M") odoDiff else odoDiff * 1.609344f
        }

        fun getSpeed(ref: Entry, unit: String): Float {
            val calcSpeed = (odometerMi - ref.odometerMi) / getTimeDiff(ref)
            return if (unit == "M") calcSpeed else calcSpeed * 1.609344f
        }

        fun getSegUsedWh(ref: Entry): Int {
            return if (ref.powerUsedWh <= powerUsedWh) powerUsedWh - ref.powerUsedWh else powerUsedWh
        }

        fun getSegRecdWh(ref: Entry): Int {
            return if (ref.powerRecdWh <= powerRecdWh) -(powerRecdWh - ref.powerRecdWh) else -powerRecdWh
        }

        fun getSegAvgPwr(ref: Entry): Float {
            return (getSegUsedWh(ref) + getSegRecdWh(ref)) / getTimeDiff(ref)
        }

        fun getSegAvgEnergy(ref: Entry, unit: String): Float {
            val odoDiff = getOdoDiff(ref, unit)
            return if (odoDiff > 0.050f) (getSegUsedWh(ref) + getSegRecdWh(ref)) / odoDiff else 0f
        }

        // minPower & maxPower are per GPS log entry instead of cumulative, thus need to be
        //  scanned for a segment spanning multiple entries:
        fun getMinPower(entries: ArrayList<Entry>, ref: Entry): Int {
            var min = 32767
            var entry: Entry
            for (i in entries.indexOf(ref)..entries.indexOf(this)) {
                entry = entries[i]
                if (entry.minPower < min) min = entry.minPower
            }
            return min
        }

        fun getMaxPower(entries: ArrayList<Entry>, ref: Entry): Int {
            var max = -32768
            var entry: Entry
            for (i in entries.indexOf(ref)..entries.indexOf(this)) {
                entry = entries[i]
                if (entry.maxPower > max) max = entry.maxPower
            }
            return max
        }

        // bmsDriveLimit & bmsRecupLimit are per GPS log entry instead of cumulative, thus need to be
        //  scanned for a segment spanning multiple entries:
        fun getBmsRecupLimit(entries: ArrayList<Entry>, ref: Entry): Int {
            var min = 32767
            var entry: Entry
            for (i in entries.indexOf(ref)..entries.indexOf(this)) {
                entry = entries[i]
                if (entry.bmsRecupLimit < min) min = entry.bmsRecupLimit
            }
            return min
        }

        fun getBmsDriveLimit(entries: ArrayList<Entry>, ref: Entry): Int {
            var max = -32768
            var entry: Entry
            for (i in entries.indexOf(ref)..entries.indexOf(this)) {
                entry = entries[i]
                if (entry.bmsDriveLimit > max) max = entry.bmsDriveLimit
            }
            return max
        }
    }
}
