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
 * Created by Michael Balzer <dexter></dexter>@dexters-web.de> on 2019-12-13.
 */
class AuxBatteryData {

    //
    // Storage data:
    //
    private var vehicleId = ""
	var packHistory: ArrayList<PackStatus> = ArrayList(24 * 60)


    fun saveFile(vehicleId: String): Boolean {
        val outputStream: FileOutputStream
        val filename = "auxbatterydata-$vehicleId-default.json"
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
        var packStatus: PackStatus? = null
        var doors: Int
        for (i in 0 until cmdSeries.size()) {
            val cmd = cmdSeries[i]
            if (cmd.commandCode != 32) continue

            // init:
            if (cmd.command == "32,D") {
                packHistory.clear()
            }
            for (resNr in cmd.results.indices) {
                val result = cmd.results[resNr]
                if (result[2] == "No historical data available") continue
                try {
                    recNr = result[2].toInt()
                    recCnt = result[3].toInt()
                    recType = result[4]
                    timeStamp = serverTime.parse(result[5])
                    Log.v(TAG, "processing recType $recType entryNr $recNr/$recCnt")
                    if (recType == "D") {
                        try {
                            // create record:
                            packStatus = PackStatus()
                            packStatus.timeStamp = timeStamp
                            packStatus.volt = result[21].toFloat()
                            if (result.size > 23) packStatus.voltRef = result[23].toFloat()
                            if (result.size > 26) packStatus.current = result[26].toFloat()
                            packStatus.tempAmbient = result[17].toFloat()
                            if (result.size > 25) packStatus.tempCharger = result[25].toFloat()
                            doors = result[18].toInt() // doors3
                            packStatus.isCarAwake = doors and 0x01 != 0
                            if (result.size > 24) {
                                doors = result[24].toInt() // doors5
                                packStatus.isCharging12V = doors and 0x10 != 0
                            }

                            // add to history:
                            packHistory.add(packStatus)
                        } catch (e: Exception) {
                            // invalid record: skip
                            Log.e(TAG, "skip: " + e.message)
                        }
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
        private val TAG = "AuxBatteryData"

        //
        // System environment:
        //

        @Transient
        private val context: Context? = app

        @Transient
        private val gson = Gson()

		fun loadFile(vehicleId: String): AuxBatteryData? {
            val inputStream: FileInputStream
            val filename = "auxbatterydata-$vehicleId-default.json"
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
                gson.fromJson<AuxBatteryData>(json, AuxBatteryData::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    class PackStatus {

        var timeStamp: Date? = null
        var volt = 0f
        var voltRef = 0f
        var current = 0f
        var tempAmbient = 0f
        var tempCharger = 0f
        var isCharging12V = false
        var isCarAwake = false


        fun isNewSection(previous: PackStatus?): Boolean {
            return (previous != null
                    && (isCharging12V && !previous.isCharging12V || isCarAwake && !previous.isCarAwake))
        }
    }
}
