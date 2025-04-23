package com.openvehicles.OVMS.ui2.pages

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.entities.CarData.DataStale
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.ui2.components.quickactions.ChargingQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.CarInfoQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.Homelink1QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.Homelink2QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.Homelink3QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.LockQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.TwizyDriveMode1QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.TwizyDriveMode2QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.TwizyDriveMode3QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.TwizyDriveModeDefaultQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.ValetQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.WakeupQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.adapters.QuickActionsAdapter
import com.openvehicles.OVMS.ui2.rendering.CarRenderingUtils
import com.openvehicles.OVMS.utils.CarsStorage
import com.openvehicles.OVMS.utils.CarsStorage.getLastSelectedCarId
import kotlin.collections.get
import kotlin.compareTo
import kotlin.math.floor


class ControlsFragment : BaseFragment(), OnResultCommandListener {

    private var carData: CarData? = null

    private lateinit var sideActionsAdapter: QuickActionsAdapter
    private lateinit var bottomActionsAdapter: QuickActionsAdapter
    private lateinit var centerActionsAdapter: QuickActionsAdapter
    private lateinit var appPrefs: AppPrefs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_controls, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        carData = CarsStorage.getSelectedCarData()
        appPrefs = AppPrefs(requireContext(), "ovms")

        val sideActionsRecyclerView = findViewById(R.id.sideActions) as RecyclerView
        val bottomActionsRecyclerView = findViewById(R.id.bottomActions) as RecyclerView
        val mainActionsRecyclerView = findViewById(R.id.mainActions) as RecyclerView

        sideActionsAdapter = QuickActionsAdapter(context)
        bottomActionsAdapter = QuickActionsAdapter(context)
        centerActionsAdapter = QuickActionsAdapter(context)

        sideActionsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        sideActionsRecyclerView.adapter = sideActionsAdapter

        bottomActionsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bottomActionsRecyclerView.adapter = bottomActionsAdapter

        mainActionsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, VERTICAL, false)
        mainActionsRecyclerView.adapter = centerActionsAdapter

        updateServiceInfo(carData)
        updateTPMSData(carData)
        initialiseSideActions(carData)
        initialiseMainActions(carData)
        initialiseCarRendering(carData)
    }

    private fun updateServiceInfo(carData: CarData?) {
        // Show known car service interval info:
        val serviceBtn= findViewById(R.id.serviceToggle) as ExtendedFloatingActionButton
        val serviceTextView = findViewById(R.id.serviceinfo) as TextView

        var serviceInfo = ""
        if (carData!!.car_servicerange >= 0) {
            serviceInfo += String.format("%d km", carData.car_servicerange)
        }
        if (carData.car_servicetime >= 0) {
            if (serviceInfo != "") {
                serviceInfo += "\n"
            }
            val now = System.currentTimeMillis() / 1000
            val serviceDays = (carData.car_servicetime - now) / 86400
            var daystring = getString(R.string.ndays)
            serviceInfo += String.format(daystring, serviceDays)
        }
        if (carData.car_gen_substate != "" && carData.car_type == "SQ") {
            var servicelvl = carData.car_gen_substate
            serviceInfo += "\n"
            serviceInfo += String.format(" %s", servicelvl)
        }
        if (serviceInfo == "") {
            serviceBtn.visibility = View.INVISIBLE
            serviceBtn.isEnabled = false
            serviceTextView.visibility = View.INVISIBLE
        } else {
            serviceBtn.visibility = View.VISIBLE
            serviceTextView.text = serviceInfo
        }

        serviceBtn.setOnClickListener {
            serviceTextView.visibility =
                if (serviceTextView.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun updateTPMSData(carData: CarData?) {
        val frTPMS = findViewById(R.id.tpmsFR) as TextView
        val flTPMS = findViewById(R.id.tpmsFL) as TextView
        val rrTPMS = findViewById(R.id.tpmsRR) as TextView
        val rlTPMS = findViewById(R.id.tpmsRL) as TextView
        val staleTPMS = findViewById(R.id.tpmsStale) as TextView
        val tpmsFAB = findViewById(R.id.tpmsToggle) as ExtendedFloatingActionButton

        tpmsFAB.setOnClickListener {
            frTPMS.visibility =
                if (frTPMS.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
            flTPMS.visibility =
                if (flTPMS.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
            rrTPMS.visibility =
                if (rrTPMS.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
            rlTPMS.visibility =
                if (rlTPMS.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
            staleTPMS.visibility =
                if (staleTPMS.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }
        // Determine primary and (if available) secondary TPMS values:
        var stale1 = DataStale.NoValue
        var stale2 = DataStale.NoValue
        var val1 = carData?.car_tpms_wheelname
        var val2: Array<String?>? = null
        var alert: IntArray? = intArrayOf(0, 0, 0, 0)
        val vehicleId = getLastSelectedCarId()
        val isTpmsAlertsEnabled = appPrefs.getData("alert_tpms_by_app_$vehicleId", "off") == "on"  // check if TPMS by App alerts are enabled

        // check TPMS size and fill up with "---" if needed to 4 values
        fun checkTPMSsize(result: Array<String?>?, value: String = "pressure"): Array<String?> {
            return when {
                result == null -> {
                    when (value) {
                        "wheelname" -> arrayOf(getString(R.string.fl_tpms), getString(R.string.fr_tpms), getString(R.string.rl_tpms), getString(R.string.rr_tpms))
                        "alert" -> arrayOf("0", "0", "0", "0")
                        else -> arrayOf("---", "---", "---", "---")
                    }
                }
                result.size < 4 && value == "alert" -> {
                    result + Array(4 - result.size) { "0" } // Fill with "0"
                }
                else -> {
                    result + Array(4 - result.size) { "---" } // Fill with "---"
                }
            }
        }

        if (carData?.car_tpms_wheelname != null && carData.car_tpms_wheelname!!.isNotEmpty()) {
            // New data (msg code 'Y'):
            if (carData.stale_tpms_pressure != CarData.DataStale.NoValue && carData.car_tpms_pressure!!.isNotEmpty()) {
                stale1 = carData.stale_tpms_pressure
                val1 = checkTPMSsize(carData.car_tpms_pressure, "pressure")  // size check and fill up with "---" for pressure
            }
            if (carData.stale_tpms_temp != CarData.DataStale.NoValue && carData.car_tpms_temp!!.isNotEmpty()) {
                stale2 = carData.stale_tpms_temp
                val2 = checkTPMSsize(carData.car_tpms_temp, "temp")          // size check and fill up with "---" for temperatures
            }
            if (carData.stale_tpms_health != CarData.DataStale.NoValue && carData.car_tpms_health!!.isNotEmpty()) {
                if (stale1 == CarData.DataStale.NoValue) {
                    stale1 = carData.stale_tpms_health
                    val1 = checkTPMSsize(carData.car_tpms_health, "health") // size check and fill up with "---" for health
                }
            }
            if (carData.stale_tpms_alert != CarData.DataStale.NoValue && carData.car_tpms_alert!!.isNotEmpty()) {
                alert = carData.car_tpms_alert_raw
                if (alert == null) {
                    alert = intArrayOf(0, 0, 0, 0)
                } else if (alert.size < 4) {
                    while (alert.size < 4)
                        alert +=  0 // Fill with "0"
                }
                if (stale1 == CarData.DataStale.NoValue) {
                    stale1 = carData.stale_tpms_alert
                    val1 = checkTPMSsize(carData.car_tpms_alert, "alert") // size check and fill up with "0" for alert
                }
            } else {
                alert = intArrayOf(0, 0, 0, 0)
            }
        } else if (carData != null) {
            // Legacy data (msg code 'W'): only pressures & temperatures available
            val1 = arrayOf(
                carData.car_tpms_fl_p,
                carData.car_tpms_fr_p,
                carData.car_tpms_rl_p,
                carData.car_tpms_rr_p
            )
            stale1 = carData.stale_tpms
            val2 = arrayOf(
                carData.car_tpms_fl_t,
                carData.car_tpms_fr_t,
                carData.car_tpms_rl_t,
                carData.car_tpms_rr_t
            )
            // Legacy 'W' message had no distinct temperature meta data, guess availability from values:
            if (carData.car_tpms_fl_t_raw != 0.0 || carData.car_tpms_fr_t_raw != 0.0 || carData.car_tpms_rl_t_raw != 0.0 || carData.car_tpms_rr_t_raw != 0.0) {
                stale2 = carData.stale_tpms
            }
            checkTPMSsize(val1, "pressure")  // size check and fill up with "---" for pressure
            checkTPMSsize(val2, "temp")      // size check and fill up with "---" for temperatures
            alert = intArrayOf(0, 0, 0, 0)
        }

        val appwheelname = if (appPrefs.getData("tpms_wheelname_app", "off") == "on") {
            arrayOf(getString(R.string.fl_tpms),getString(R.string.fr_tpms),getString(R.string.rl_tpms),getString(R.string.rr_tpms))
        } else {
            checkTPMSsize(carData?.car_tpms_wheelname, "wheelname")  // size check and fill up with "---" for wheelnames
        }

        // Get TPMS data from firmware
        val carType = carData?.car_type?.lowercase() // config xsq = defined smart EQ config path in the Firmware
        var tpms_fl = getString(R.string.tpms_fl) + " " + val1?.get(0)
        var tpms_fr = getString(R.string.tpms_fr) + " " + val1?.get(1)
        var tpms_rl = getString(R.string.tpms_rl) + " " + val1?.get(2)
        var tpms_rr = getString(R.string.tpms_rr) + " " + val1?.get(3)
        if (stale2 != DataStale.NoValue) {
            tpms_fl += "/" + val2?.get(0)
            tpms_fr += "/" + val2?.get(1)
            tpms_rl += "/" + val2?.get(2)
            tpms_rr += "/" + val2?.get(3)
        }
        val options = arrayOf(tpms_fl,tpms_fr,tpms_rl,tpms_rr)

        flTPMS.setOnClickListener {
            var checkedItem = 0
            AlertDialog.Builder(requireActivity())
                .setTitle(R.string.fl_get_tpms)
                .setSingleChoiceItems(options, checkedItem) { _, which ->
                    checkedItem = which // Update the selected item index
                }
                .setNegativeButton(R.string.Close, null)
                .setPositiveButton(R.string.fl_set_tpms) { _, _ ->
                    if (appPrefs.getData("tmps_firmware_$vehicleId", "off") == "on") {
                        sendCommand(R.string.fl_set_tpms, "7,config set x$carType TPMS_FL $checkedItem", this@ControlsFragment)
                    } else {
                        appPrefs.saveData("tpms_fl_$vehicleId", "$checkedItem")
                    }
                    updateTPMSData(carData)
                }
                .show()
        }

        frTPMS.setOnClickListener {
            var checkedItem = 1
            AlertDialog.Builder(requireActivity())
                .setTitle(R.string.fr_get_tpms)
                .setSingleChoiceItems(options, checkedItem) { _, which ->
                    checkedItem = which // Update the selected item index
                }
                .setNegativeButton(R.string.Close, null)
                .setPositiveButton(R.string.fr_set_tpms) { _, _ ->
                    if (appPrefs.getData("tmps_firmware_$vehicleId", "off") == "on") {
                        sendCommand(R.string.fr_set_tpms, "7,config set x$carType TPMS_FR $checkedItem", this@ControlsFragment)
                    } else {
                        appPrefs.saveData("tpms_fr_$vehicleId", "$checkedItem")
                    }
                    updateTPMSData(carData)
                }
                .show()
        }

        rlTPMS.setOnClickListener {
            var checkedItem = 2
            AlertDialog.Builder(requireActivity())
                .setTitle(R.string.rl_get_tpms)
                .setSingleChoiceItems(options, checkedItem) { _, which ->
                    checkedItem = which // Update the selected item index
                }
                .setNegativeButton(R.string.Close, null)
                .setPositiveButton(R.string.rl_set_tpms) { _, _ ->
                    if (appPrefs.getData("tmps_firmware_$vehicleId", "off") == "on") {
                        sendCommand(R.string.rl_set_tpms, "7,config set x$carType TPMS_RL $checkedItem", this@ControlsFragment)
                    } else {
                        appPrefs.saveData("tpms_rl_$vehicleId", "$checkedItem")
                    }
                    updateTPMSData(carData)
                }
                .show()
        }

        rrTPMS.setOnClickListener {
            var checkedItem = 3
            AlertDialog.Builder(requireActivity())
                .setTitle(R.string.rr_get_tpms)
                .setSingleChoiceItems(options, checkedItem) { _, which ->
                    checkedItem = which // Update the selected item index
                }
                .setNegativeButton(R.string.Close, null)
                .setPositiveButton(R.string.rr_set_tpms) { _, _ ->
                    if (appPrefs.getData("tmps_firmware_$vehicleId", "off") == "on") {
                        sendCommand(R.string.rr_set_tpms, "7,config set x$carType TPMS_RR $checkedItem", this@ControlsFragment)
                    } else {
                        appPrefs.saveData("tpms_rr_$vehicleId", "$checkedItem")
                    }
                    updateTPMSData(carData)
                }
                .show()
        }

        // Disable TPMS for motorcycles, etc?
        if (carData?.car_type in listOf("EN", "NRJK")) {
            tpmsFAB.isEnabled = false
            return
        }

        // Display TPMS wheel values:
        if (stale2 == DataStale.NoValue) {
            // No secondary value available, show only primary value:
            flTPMS.text = String.format(
                "%s\n%s",
                appwheelname?.get(0),
                val1?.get((appPrefs.getData("tpms_fl_$vehicleId", "0")!!.toInt())) ?: "---"
            )
            frTPMS.text = String.format(
                "%s\n%s",
                appwheelname?.get(1),
                val1?.get((appPrefs.getData("tpms_fr_$vehicleId", "1")!!.toInt())) ?: "---"
            )
            rlTPMS.text = String.format(
                "%s\n%s",
                appwheelname?.get(2),
                val1?.get((appPrefs.getData("tpms_rl_$vehicleId", "2")!!.toInt())) ?: "---"
            )
            rrTPMS.text = String.format(
                "%s\n%s",
                appwheelname?.get(3),
                val1?.get((appPrefs.getData("tpms_rr_$vehicleId", "3")!!.toInt())) ?: "---"
            )
        } else {
            // Show primary and secondary values:
            flTPMS.text = String.format(
                "%s\n%s\n%s",
                appwheelname?.get(0),
                val1?.get((appPrefs.getData("tpms_fl_$vehicleId", "0")!!.toInt())) ?: "---",
                val2?.get((appPrefs.getData("tpms_fl_$vehicleId", "0")!!.toInt())) ?: "---"
            )
            frTPMS.text = String.format(
                "%s\n%s\n%s",
                appwheelname?.get(1),
                val1?.get((appPrefs.getData("tpms_fr_$vehicleId", "1")!!.toInt())) ?: "---",
                val2?.get((appPrefs.getData("tpms_fr_$vehicleId", "1")!!.toInt())) ?: "---"
            )
            rlTPMS.text = String.format(
                "%s\n%s\n%s",
                appwheelname?.get(2),
                val1?.get((appPrefs.getData("tpms_rl_$vehicleId", "2")!!.toInt())) ?: "---",
                val2?.get((appPrefs.getData("tpms_rl_$vehicleId", "2")!!.toInt())) ?: "---"
            )
            rrTPMS.text = String.format(
                "%s\n%s\n%s",
                appwheelname?.get(3),
                val1?.get((appPrefs.getData("tpms_rr_$vehicleId", "3")!!.toInt())) ?: "---",
                val2?.get((appPrefs.getData("tpms_rr_$vehicleId", "3")!!.toInt())) ?: "---"
            )
        }

        // set alert state for each TPMS by App pressure value
        fun setTpmsAlert(alertindex: Int) {
            if (!isTpmsAlertsEnabled) {
                return
            }
            val TirePos = arrayOf("tpms_fl_","tpms_fr_","tpms_rl_","tpms_rr_")
            val tpms_pos = TirePos[alertindex]
            val redvalue = appPrefs.getData("tpms_alert_red_$vehicleId", "10")!!.toInt().times(0.01)

            val TirePressureIndex = appPrefs.getData("${tpms_pos}$vehicleId", "0")!!.toInt()
            val TirePressure = if (appPrefs.getData("showtpmsbar") == "on") {
                floor(
                    carData!!.car_tpms_pressure_raw?.get(TirePressureIndex)
                        ?.div(10)!!
                ) / 10
            } else {
                floor(
                    carData!!.car_tpms_pressure_raw?.get(TirePressureIndex)?.times(1.450377)!!
                ) / 10
            }

            val TirePressureThreshold = if("$tpms_pos" in listOf("tpms_fl_","tpms_fr_")) {
                appPrefs.getData("tpms_alert_front_$vehicleId", "0")!!.toFloat()
            } else {
                appPrefs.getData("tpms_alert_rear_$vehicleId", "0")!!.toFloat()
            }
            var alertState = if (TirePressure < TirePressureThreshold) 1 else 0
            alertState = if (TirePressure < (TirePressureThreshold.minus(TirePressureThreshold.times(redvalue)))) 2 else alertState

            alert?.set(alertindex,alertState)
        }

        if (isTpmsAlertsEnabled) {
            for (i in val1?.indices!!) {
                setTpmsAlert(i)
            }
        }

        val alertcol = intArrayOf(Color.WHITE, Color.YELLOW, Color.RED)
        if ((alert?.get(0) ?: 0) != 0)
            flTPMS.setTextColor(alertcol[alert!![(appPrefs.getData("tpms_fl_$vehicleId", "0")!!.toInt())]])
        if ((alert?.get(1) ?: 0) != 0)
            frTPMS.setTextColor(alertcol[alert!![(appPrefs.getData("tpms_fr_$vehicleId", "1")!!.toInt())]])
        if ((alert?.get(2) ?: 0) != 0)
            rlTPMS.setTextColor(alertcol[alert!![(appPrefs.getData("tpms_rl_$vehicleId", "2")!!.toInt())]])
        if ((alert?.get(3) ?: 0) != 0)
            rrTPMS.setTextColor(alertcol[alert!![(appPrefs.getData("tpms_rr_$vehicleId", "3")!!.toInt())]])

        if (stale1 == CarData.DataStale.NoValue) {
            staleTPMS.setTextColor(Color.RED)
            staleTPMS.text = getString(R.string.no_data).lowercase()
        }

        if (stale1 == CarData.DataStale.Stale) {
            staleTPMS.setTextColor(Color.YELLOW)
            staleTPMS.text = getString(R.string.stale_data).lowercase()
        }

        if (stale1 == CarData.DataStale.Good) {
            context?.resources?.getColor(R.color.colorAccent)?.let { staleTPMS.setTextColor(it) }
            staleTPMS.text = getString(R.string.latest_data).lowercase()
        }

        val now = System.currentTimeMillis()
        var seconds = (now - (carData?.car_lastupdated?.time ?: 0)) / 1000
        var minutes = seconds / 60
        var hours = minutes / 60
        var days = minutes / (60 * 24)


        if (minutes > 0L) {
            staleTPMS.setTextColor(Color.YELLOW)
            var periodText: String
            if (minutes == 1L) {
                periodText = getText(R.string.min1).toString()
            } else if (days > 1) {
                periodText = String.format(getText(R.string.ndays).toString(), days)
            } else if (hours > 1) {
                periodText = String.format(getText(R.string.nhours).toString(), hours)
            } else if (minutes > 60) {
                periodText = String.format(
                    getText(R.string.nmins).toString(),
                    minutes
                )
            } else {
                periodText = String.format(
                    getText(R.string.nmins).toString(),
                    minutes
                )
            }
            staleTPMS.text = periodText
        }

        if ((alert?.find { it > 0 } ?: 0) > 0) {
            tpmsFAB.toggle()
        }
    }

    private fun initialiseCarRendering(carData: CarData?) {
        val carImageView = findViewById(R.id.battIndicatorImg) as ImageView
        val layers = carData?.let { CarRenderingUtils.getTopDownCarLayers(it, requireContext()) }

        if (layers != null) {
            val newDrawable = LayerDrawable(layers.toTypedArray())
            if (carImageView.drawable == null) {
                carImageView.setImageDrawable(
                    newDrawable
                )
                return
            }
            if ((carImageView.drawable as LayerDrawable?)?.numberOfLayers != newDrawable.numberOfLayers) {
                val anim_in: Animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
                carImageView.setImageDrawable(
                    newDrawable
                )
                anim_in.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationRepeat(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {}
                })
                carImageView.startAnimation(anim_in)
            }
        }
    }

    private fun initialiseSideActions(carData: CarData?) {
        sideActionsAdapter.mData.clear()
        centerActionsAdapter.setCarData(carData)
        if (carData?.car_type == "RT") {
            // Renault Twizy: use Homelink for profile switching:
            sideActionsAdapter.mData += TwizyDriveModeDefaultQuickAction({getService()})
            sideActionsAdapter.mData += TwizyDriveMode1QuickAction({getService()})
            sideActionsAdapter.mData += TwizyDriveMode2QuickAction({getService()})
            sideActionsAdapter.mData += TwizyDriveMode3QuickAction({getService()})
        } else {
            sideActionsAdapter.mData += Homelink1QuickAction({getService()})
            sideActionsAdapter.mData += Homelink2QuickAction({getService()})
            sideActionsAdapter.mData += Homelink3QuickAction({getService()})
        }
        sideActionsAdapter.notifyDataSetChanged()
    }

    private fun initialiseMainActions(carData: CarData?) {
        centerActionsAdapter.mData.clear()
        centerActionsAdapter.setCarData(carData)
        centerActionsAdapter.mData += LockQuickAction({getService()})
        if (carData?.car_type != "SQ") centerActionsAdapter.mData += ValetQuickAction({getService()})
        centerActionsAdapter.mData += WakeupQuickAction({getService()})
        if (carData?.car_type != "SQ") centerActionsAdapter.mData += ChargingQuickAction({getService()})
        if (carData?.car_type in listOf("SQ")) centerActionsAdapter.mData += CarInfoQuickAction({getService()})
        centerActionsAdapter.notifyDataSetChanged()
    }

    override fun update(carData: CarData?) {
        this.carData = carData
        updateTPMSData(carData)
        sideActionsAdapter.setCarData(carData)
        centerActionsAdapter.setCarData(carData)
        sideActionsAdapter.notifyDataSetChanged()
        centerActionsAdapter.notifyDataSetChanged()
        initialiseCarRendering(carData)
    }

    override fun onResultCommand(result: Array<String>) {
        if (result.size <= 1) return
        val resCode = result[1].toInt()
        val resText = if (result.size > 2) result[2] else ""
        val cmdMessage = getSentCommandMessage(result[0])
        val context: Context? = activity
        if (context != null) {
            when (resCode) {
                0 -> Toast.makeText(
                    context, cmdMessage + " " + getString(R.string.msg_ok),
                    Toast.LENGTH_SHORT
                ).show()

                1 -> Toast.makeText(
                    context, cmdMessage + " " + getString(R.string.err_failed, resText),
                    Toast.LENGTH_SHORT
                ).show()

                2 -> Toast.makeText(
                    context, cmdMessage + " " + getString(R.string.err_unsupported_operation),
                    Toast.LENGTH_SHORT
                ).show()

                3 -> Toast.makeText(
                    context, cmdMessage + " " + getString(R.string.err_unimplemented_operation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        cancelCommand()
    }
}