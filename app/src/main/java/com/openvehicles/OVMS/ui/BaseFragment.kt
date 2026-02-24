package com.openvehicles.OVMS.ui

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.openvehicles.OVMS.api.ApiObservable.addObserver
import com.openvehicles.OVMS.api.ApiObservable.deleteObserver
import com.openvehicles.OVMS.api.ApiObserver
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.utils.Database
import com.openvehicles.OVMS.ui.utils.ProgressOverlay
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.CarsStorage.setSelectedCarId

open class BaseFragment : Fragment(), ApiObserver {

    private var sentCommandMessage: HashMap<String, String> = HashMap()
    private var onResultCommandListener: OnResultCommandListener? = null
    private var progressOverlay: ProgressOverlay? = null
    private var progressShowOnStart = false

    protected val compatActivity: AppCompatActivity?
        get() = activity as AppCompatActivity?


    // create progress overlay: (call this from onCreateView):
    // ATT: if you enable showOnStart you need to take care about resumes etc.
    fun createProgressOverlay(
        container: View?,
        showOnStart: Boolean
    ): ProgressOverlay {
        progressOverlay = ProgressOverlay(container!!)
        progressShowOnStart = showOnStart
        return progressOverlay!!
    }

    // show/switch progress overlay in indeterminate mode (spinner icon):
    fun showProgressOverlay() {
        progressOverlay?.show()
    }

    // show/switch progress overlay in indeterminate mode (spinner icon):
    fun showProgressOverlay(message: String?) {
        progressOverlay?.setLabel(message)
        progressOverlay?.show()
    }

    // show/switch progress overlay in determinate mode (bar),
    //  with optional sub step progress (if stepCnt > 0)
    //	hide overlay if maxPos reached:
    // show/switch progress overlay in determinate mode (bar),
    //	hide overlay if maxPos reached:
    @JvmOverloads
    fun stepProgressOverlay(pos: Int, maxPos: Int, step: Int = 0, stepCnt: Int = 0) {
        progressOverlay?.step(pos, maxPos, step, stepCnt)
    }

    // show/switch progress overlay in determinate mode (bar),
    //  with optional sub step progress (if stepCnt > 0)
    //	hide overlay if maxPos reached:
    fun stepProgressOverlay(message: String?, pos: Int, maxPos: Int, step: Int, stepCnt: Int) {
        progressOverlay?.setLabel(message)
        progressOverlay?.step(pos, maxPos, step, stepCnt)
    }

    // hide progress overlay:
    fun hideProgressOverlay() {
        progressOverlay?.hide()
    }

    override fun onStart() {
        Log.d(TAG, "onStart $javaClass")
        super.onStart()

        if (progressShowOnStart) {
            progressOverlay?.show()
        }
        addObserver(this)
        val service = getService()
        if (service != null) {
            onServiceAvailable(service)
            if (service.isLoggedIn()) {
                update(service.getCarData())
            }
        }
    }

    override fun onStop() {
        Log.d(TAG, "onStop $javaClass")
        super.onStop()

        cancelCommand()
        deleteObserver(this)
        if (progressOverlay != null) {
            progressOverlay!!.hide()
        }
    }

    override fun update(carData: CarData?) {
        // Override as needed
    }

    override fun onServiceAvailable(service: ApiService) {
        // Override as needed, default:
        update(service.getCarData())
    }

    override fun onServiceLoggedIn(service: ApiService?, isLoggedIn: Boolean) {
        // Override as needed
    }

    fun getSentCommandMessage(cmd: String): String {
        return sentCommandMessage[cmd] ?: cmd
    }

    fun cancelCommand() {
        val service = getService()
            ?: return

        if (onResultCommandListener != null) {
            Log.d(TAG, "cancelCommand listener=$onResultCommandListener")
            service.cancelCommand(onResultCommandListener)
            onResultCommandListener = null
        }
        sentCommandMessage.clear()
    }

    fun findViewById(resId: Int): View {
        return requireView().findViewById(resId)
    }

    fun sendCommand(
        resIdMessage: Int,
        command: String,
        onResultCommandListener: OnResultCommandListener?
    ) {
        sendCommand(getString(resIdMessage), command, onResultCommandListener)
    }

    fun sendCommand(
        message: String,
        command: String,
        onResultCommandListener: OnResultCommandListener?
    ) {
        val service = getService()
            ?: return

        // remember pMessage for result display:
        try {
            val cmd = command.split(",".toRegex(), limit = 2).toTypedArray()[0]
            sentCommandMessage[cmd] = message
        } catch (e: Exception) {
            // ignore
        }

        // Display message:
        if (message.isNotEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // pass on to API service:
        this.onResultCommandListener = onResultCommandListener
        service.sendCommand(message, command, onResultCommandListener)
    }

    fun sendCommand(
        command: String?,
        onResultCommandListener: OnResultCommandListener?
    ) {
        val service = getService()
            ?: return
        this.onResultCommandListener = onResultCommandListener
        service.sendCommand(command!!, onResultCommandListener)
    }

    fun changeCar(carData: CarData) {
        val prefs = AppPrefs(requireActivity(), "ovms")
        val database = Database(requireActivity())
        Log.i(TAG, "changeCar: switching to vehicle ID " + carData.sel_vehicleid)
        cancelCommand()

        // select car:
        setSelectedCarId(carData.sel_vehicleid)
        prefs.saveData("sel_vehicle_label", carData.sel_vehicle_label)
        prefs.saveData("autotrack", "on")
        prefs.saveData("Id", database.getConnectionFilter(carData.sel_vehicle_label))

        // inform API service:
        getService()?.changeCar(carData)
    }

    fun triggerCarDataUpdate() {
        getService()?.triggerUpdate()
    }

    protected fun getService(): ApiService? {
        return if (activity is ApiActivity) {
            (activity as ApiActivity).service
        } else {
            null
        }
    }

    /*
     * Inner types
     */

    private companion object {
        private const val TAG = "BaseFragment"
    }
}
