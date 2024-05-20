package com.openvehicles.OVMS.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openvehicles.OVMS.BaseApp.Companion.app
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.CarData
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.ObjectInputStream

// TODO: Remove SuppressLint and improve code to avoid warning
@SuppressLint("StaticFieldLeak")
object CarsStorage {

    private const val TAG = "CarsStorage"

    private val context: Context? = app
    private var storedCars: ArrayList<CarData>? = null
    private var lastSelectedCarId: String? = null
    private var preferences: SharedPreferences? = null

    fun getStoredCars(): ArrayList<CarData> {
        var doSave = false

        // Already loaded?
        if (storedCars != null && storedCars!!.size > 0) {
            return storedCars!!
        }

        // Load JSON car storage file:
        try {
            val inputStream = context!!.openFileInput(Consts.STOREDCARS_FILENAME_JSON)
            Log.i(TAG, "getStoredCars: loading " + Consts.STOREDCARS_FILENAME_JSON)
            val isr = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(isr)
            val sb = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            val json = sb.toString()
            val gson = Gson()
            val type = object : TypeToken<ArrayList<CarData?>?>() {}.type
            storedCars = gson.fromJson(json, type)
        } catch (e: Exception) {
            Log.e(TAG, "getStoredCars: failed loading " + Consts.STOREDCARS_FILENAME_JSON)
            e.printStackTrace()
        }

        // Fallback to old ObjectStream storage:
        if (storedCars == null || storedCars!!.size == 0) {
            try {
                val fis = context!!.openFileInput(Consts.STOREDCARS_FILENAME_OBJ)
                Log.i(TAG, "getStoredCars: fallback; loading " + Consts.STOREDCARS_FILENAME_OBJ)
                val `is` = ObjectInputStream(fis)
                storedCars = `is`.readObject() as ArrayList<CarData>
                `is`.close()
                doSave = true // upgrade to JSON storage
            } catch (e: Exception) {
                Log.e(TAG, "getStoredCars: failed loading " + Consts.STOREDCARS_FILENAME_OBJ)
                e.printStackTrace()
            }
        }
        if (storedCars != null && storedCars!!.size > 0) {
            Log.i(TAG, "getStoredCars: OK, loaded " + storedCars!!.size + " car(s)")
        } else {
            Log.i(TAG, "getStoredCars: initializing car list")
            storedCars = ArrayList()
            initDemoCar()
            doSave = true
        }

        // Upgrade to JSON:
        if (doSave) {
            saveStoredCars()
        }
        return storedCars!!
    }

    fun saveStoredCars() {
        if (storedCars == null) {
            return
        }

        // Save JSON car storage file:
        try {
            val fos = context!!.openFileOutput(
                Consts.STOREDCARS_FILENAME_JSON,
                Context.MODE_PRIVATE
            )
            Log.i(TAG, "saveStoredCars: saving to " + Consts.STOREDCARS_FILENAME_JSON)
            val gson = Gson()
            val json = gson.toJson(storedCars)
            fos.write(json.toByteArray())
            fos.close()
        } catch (e: Exception) {
            Log.e(TAG, "saveStoredCars: FAILED saving to " + Consts.STOREDCARS_FILENAME_JSON)
            e.printStackTrace()
        }
    }

    fun getCarById(carId: String?): CarData? {
        if (storedCars == null) {
            getStoredCars()
        }
        for (car in storedCars!!) {
            if (car.sel_vehicleid == carId) {
                return car
            }
        }
        return null
    }

    private fun initDemoCar() {
        Log.v(TAG, "Initializing demo car.")
        val demoCar = CarData()
        demoCar.sel_vehicleid = "DEMO"
        demoCar.sel_vehicle_label = "Demonstration Car"
        demoCar.sel_server_password = "DEMO"
        demoCar.sel_module_password = "DEMO"
        demoCar.sel_vehicle_image = "car_roadster_lightninggreen"
        val mServers = context!!.resources.getStringArray(R.array.select_server_options)
        val mGcmSenders = context.resources.getStringArray(R.array.select_server_gcm_senders)
        demoCar.sel_server = mServers[0]
        demoCar.sel_gcm_senderid = mGcmSenders[0]
        storedCars!!.add(demoCar)
        setSelectedCarId(demoCar.sel_vehicleid)
    }

    fun getLastSelectedCarId(): String? {
        if (!TextUtils.isEmpty(lastSelectedCarId)) {
            return lastSelectedCarId
        }
        lastSelectedCarId = getPrefs()!!.getString("LASTSELECTEDCARID", null)
        return lastSelectedCarId
    }

    fun getSelectedCarData(): CarData? {
        var result = getCarById(getLastSelectedCarId())
        if (result == null && storedCars!!.size > 0) {
            result = storedCars!![0]
            setSelectedCarId(result.sel_vehicleid)
        }
        return result
    }

    fun setSelectedCarId(pCarId: String?) {
        lastSelectedCarId = pCarId
        getPrefs()!!.edit().putString("LASTSELECTEDCARID", lastSelectedCarId).commit()
    }

    fun getSelectedCarIndex(): Int {
        val id = getLastSelectedCarId()
        if (id != null && storedCars!!.size > 0) {
            for (i in storedCars!!.indices) {
                if (storedCars!![i].sel_vehicleid == id) return i
            }
        }
        return 0
    }

    fun getPrefs(): SharedPreferences? {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context)
        }
        return preferences
    }
}
