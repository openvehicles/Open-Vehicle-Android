package com.openvehicles.OVMS.ui

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.openvehicles.OVMS.api.ApiObservable.addObserver
import com.openvehicles.OVMS.api.ApiObservable.deleteObserver
import com.openvehicles.OVMS.api.ApiObservable.notifyOnBind
import com.openvehicles.OVMS.api.ApiObserver
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.ApiService.ApiBinder
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.utils.Database
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.CarsStorage.getCarById
import com.openvehicles.OVMS.utils.CarsStorage.setSelectedCarId

open class ApiActivity : AppCompatActivity(), ApiObserver {

    var service: ApiService? = null
        protected set

    val isOnline: Boolean
        get() = service?.isOnline() == true
    val isLoggedIn: Boolean
        get() = service?.isLoggedIn() == true
    val loggedInCar: CarData?
        get() = if (isLoggedIn) service!!.getCarData() else null

    private val connection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            Log.d(TAG, "service connected")

            this@ApiActivity.service = (binder as ApiBinder).service
            notifyOnBind(this@ApiActivity.service!!)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "service disconnected")
            service = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate: binding service")
        bindService(Intent(this, ApiService::class.java), connection, BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        if (service != null) {
            Log.d(TAG, "onDestroy: unbinding service")
            unbindService(connection)
            service = null
        }

        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart")
        if (service != null) {
            service!!.onActivityStart()
        }
        addObserver(this)
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        deleteObserver(this)
        if (service != null) {
            service!!.onActivityStop()
        }

        super.onStop()
    }

    override fun onResume() {
        super.onResume()

        if (service != null) {
            Log.d(TAG, "onResume: check connection")
            service!!.checkConnection()
        }
    }

    override fun update(carData: CarData?) {
        // Override as needed
    }

    override fun onServiceAvailable(service: ApiService) {
        // Override as needed
    }

    override fun onServiceLoggedIn(service: ApiService?, isLoggedIn: Boolean) {
        // Override as needed
    }

    fun hasService(): Boolean {
        return service != null
    }

    fun isLoggedIn(vehicleId: String): Boolean {
        val carData = loggedInCar
        return carData != null && carData.sel_vehicleid == vehicleId
    }

    fun changeCar(pVehicleId: String?): Boolean {
        val carData = getCarById(pVehicleId)
        return carData?.let { changeCar(it) } ?: false
    }

    private fun changeCar(pCarData: CarData): Boolean {
        val prefs = AppPrefs(this, "ovms")
        val database = Database(this)
        Log.i(TAG, "changeCar: switching to vehicle ID " + pCarData.sel_vehicleid)

        // select car:
        setSelectedCarId(pCarData.sel_vehicleid)
        prefs.saveData("sel_vehicle_label", pCarData.sel_vehicle_label)
        prefs.saveData("autotrack", "on")
        prefs.saveData("Id", database.getConnectionFilter(pCarData.sel_vehicle_label))

        // inform API service:
        if (service == null) {
            return false
        }
        service!!.changeCar(pCarData)
        return true
    }

    /*
     * Inner types
     */

    private companion object {
        private const val TAG = "ApiActivity"
    }
}
