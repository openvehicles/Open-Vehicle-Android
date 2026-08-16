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
 * Created by balzer on 28.03.15.
 */
class BatteryData {

    //
    // Storage data:
    //
    private var vehicleId = ""
	var cellCount = 0
	var packHistory: ArrayList<PackStatus> = ArrayList(24 * 60)

    fun saveFile(vehicleId: String): Boolean {
        val outputStream: FileOutputStream
        val filename = "batterydata-$vehicleId-default.json"
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
        var cells: ArrayList<CellStatus?>? = null
        var cellStatus: CellStatus
        var nrCell: Int
        var nrPack = 0
        for (i in 0 until cmdSeries.size()) {
            val cmd = cmdSeries[i]
            if (cmd.commandCode != 32) continue

            // init:
            if (cmd.command == "32,RT-BAT-P") {
                packHistory.clear()
                cellCount = 0
            } else if (cmd.command == "32,RT-BAT-C") {
                // check BattPack result:
                if (packHistory.size == 0) continue

                // "BattCell" only transmits changed cells, so create complete group storage:
                cells = ArrayList(16)
                for (j in 0..15) {
                    cellStatus = CellStatus()
                    cells.add(cellStatus)
                }

                // get first pack status:
                nrPack = 0
                packStatus = packHistory[0]
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
                    if (recType == "RT-BAT-P") {
                        try {
                            // create record:
                            packStatus = PackStatus()
                            packStatus.timeStamp = timeStamp
                            packStatus.voltAlert = result[7].toInt()
                            packStatus.tempAlert = result[8].toInt()
                            packStatus.soc = result[9].toInt().toFloat() / 100
                            packStatus.socMin = result[10].toInt().toFloat() / 100
                            packStatus.socMax = result[11].toInt().toFloat() / 100
                            packStatus.volt = result[12].toInt().toFloat() / 10
                            packStatus.voltMin = result[13].toInt().toFloat() / 10
                            packStatus.voltMax = result[14].toInt().toFloat() / 10
                            packStatus.temp = result[15].toFloat()
                            packStatus.tempMin = result[16].toFloat()
                            packStatus.tempMax = result[17].toFloat()
                            packStatus.voltDevMax = result[18].toInt().toFloat() / 100
                            packStatus.tempDevMax = result[19].toFloat()
                            packStatus.maxDrivePwr = (result[20].toInt() * 100).toLong()
                            packStatus.maxRecupPwr = (result[21].toInt() * 100).toLong()

                            // store record:
                            if (packStatus.volt > 0) packHistory.add(packStatus)
                        } catch (e: Exception) {
                            // invalid record: skip
                            Log.e(TAG, "BattPack skip: " + e.message)
                        }
                    } else if (recType == "RT-BAT-C") {
                        try {
                            nrCell = result[6].toInt()
                            if (nrCell > cellCount) cellCount = nrCell

                            // Pack record(s) complete?
                            // (while handles Pack records without Cell records,
                            //  30 seconds covers server timestamp offsets for cells)
                            while (timeStamp.time - packStatus!!.timeStamp!!.time > 30000) {
                                // set pack cells:
                                packStatus.cells = ArrayList(cells)

                                // get next pack:
                                packStatus = packHistory[++nrPack]
                                if (packStatus.timeStamp!!.compareTo(timeStamp) != 0) {
                                    Log.w(
                                        TAG, "timeStamp differ: pack=" + packStatus.timeStamp
                                                + " / cell=" + timeStamp
                                    )
                                }
                            }

                            // create new record:
                            cellStatus = CellStatus()
                            cellStatus.voltAlert = result[7].toInt()
                            cellStatus.tempAlert = result[8].toInt()
                            cellStatus.volt = result[9].toInt().toFloat() / 1000
                            cellStatus.voltMin = result[10].toInt().toFloat() / 1000
                            cellStatus.voltMax = result[11].toInt().toFloat() / 1000
                            cellStatus.voltDevMax = result[12].toInt().toFloat() / 1000
                            cellStatus.temp = result[13].toFloat()
                            cellStatus.tempMin = result[14].toFloat()
                            cellStatus.tempMax = result[15].toFloat()
                            cellStatus.tempDevMax = result[16].toFloat()

                            // store record:
                            cells!![nrCell - 1] = cellStatus
                        } catch (e: Exception) {
                            // invalid record: skip
                            Log.e(TAG, "BattCell skip: " + e.message)
                        }
                    }
                } catch (e: Exception) {
                    // most probably parse error, skip row
                    e.printStackTrace()
                }
            }

            // no more results: finish
            if (cmd.command == "32,RT-BAT-C") {
                try {
                    // set last cell collection into remaining pack records:
                    while (nrPack < packHistory.size) {
                        packStatus = packHistory[nrPack++]
                        packStatus.cells = ArrayList(cells)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "BattCell finish error: " + e.message)
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
        private val TAG = "BatteryData"

        //
        // System environment:
        //
        @Transient
        private val context: Context? = app

        @Transient
        private val gson = Gson()

        @JvmStatic
		fun loadFile(vehicleId: String): BatteryData? {
            val inputStream: FileInputStream
            val filename = "batterydata-$vehicleId-default.json"
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
                gson.fromJson(json, BatteryData::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    /**
     * Note: this is currently tailored to the Twizy battery
     * which consists of only one pack. For other pack structures
     * this should be extended as needed.
     */
    class PackStatus {

        @JvmField
        var timeStamp: Date? = null
        var voltAlert = 0
        var tempAlert = 0
        @JvmField
        var soc = 0f
        var socMin = 0f
        var socMax = 0f
        @JvmField
        var volt = 0f
        @JvmField
        var voltMin = 0f
        var voltMax = 0f
        var voltDevMax = 0f
        @JvmField
        var temp = 0f
        var tempMin = 0f
        var tempMax = 0f
        var tempDevMax = 0f
        var maxDrivePwr: Long = 0
        var maxRecupPwr: Long = 0
        @JvmField
        var cells: ArrayList<CellStatus?>? = null

        fun isNewSection(previous: PackStatus?): Boolean {
            return previous != null &&
                    (voltMin > previous.voltMin || tempMin > previous.tempMin || socMax < previous.socMax)
        }
    }

    class CellStatus {

        var voltAlert = 0
        var tempAlert = 0
        @JvmField
        var volt = 0f
        @JvmField
        var voltMin = 0f
        var voltMax = 0f
        @JvmField
        var voltDevMax = 0f
        @JvmField
        var temp = 0f
        @JvmField
        var tempMin = 0f
        @JvmField
        var tempMax = 0f
        @JvmField
        var tempDevMax = 0f
    }
}
