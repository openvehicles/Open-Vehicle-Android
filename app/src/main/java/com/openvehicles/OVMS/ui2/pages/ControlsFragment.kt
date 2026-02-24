package com.openvehicles.OVMS.ui2.pages

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.CommandActivity
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.entities.CarData.DataStale
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.ui2.components.quickactions.ChargingQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.CarInfoQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.DDT4allQuickAction
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
            // Toggle visibility
            serviceTextView.visibility =
                if (serviceTextView.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }

        if(carData.car_type == "SQ") {
            // Long click to open service details via CommandActivity
            serviceBtn.setOnLongClickListener {
                val intent = Intent(requireContext(), CommandActivity::class.java).apply {
                    action = "com.openvehicles.OVMS.action.COMMAND"
                    putExtra("apikey", appPrefs.getData("APIKey"))
                    putExtra("command", "xsq mtdata")
                    putExtra("title", getString(R.string.Service))
                }
                startActivity(intent)
                true
            }
        }
    }

    private fun updateTPMSData(carData: CarData?) {
        val frTPMS = findViewById(R.id.tpmsFR) as TextView
        val flTPMS = findViewById(R.id.tpmsFL) as TextView
        val rrTPMS = findViewById(R.id.tpmsRR) as TextView
        val rlTPMS = findViewById(R.id.tpmsRL) as TextView
        val staleTPMS = findViewById(R.id.tpmsStale) as TextView
        val tpmsFAB = findViewById(R.id.tpmsToggle) as ExtendedFloatingActionButton

        // Disable TPMS for vehicles not supporting any:
        if (carData?.car_type in listOf("RT", "EN", "NRJK")) {
            tpmsFAB.isEnabled = false
            tpmsFAB.visibility = View.INVISIBLE
            return
        }

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

        // Long click to open TPMS details via CommandActivity
        tpmsFAB.setOnLongClickListener {
            val intent = Intent(requireContext(), CommandActivity::class.java).apply {
                action = "com.openvehicles.OVMS.action.COMMAND"
                putExtra("apikey", appPrefs.getData("APIKey"))
                putExtra("command", "tpms map status")
                putExtra("title", getString(R.string.Service))
            }
            startActivity(intent)
            true
        }

        val vehicleId = getLastSelectedCarId()
        
        // Check if new TPMS data (msg code 'Y') is available
        val hasNewTPMS = carData?.car_tpms_wheelname != null && carData.car_tpms_wheelname!!.isNotEmpty()
        
        // Get wheel count from available data
        val wheelCount = carData?.car_tpms_wheelname?.size ?: 4
        
        // Check if firmware mapping is available and valid
        val hasMapping = carData?.stale_tpms_mapping != DataStale.NoValue && 
                         carData?.car_tpms_mapping_raw != null && 
                         carData.car_tpms_mapping_raw!!.isNotEmpty()
        
        // Use firmware mapping if available, otherwise use app prefs or defaults
        val byfirmware = appPrefs.getData("tpms_firmware_$vehicleId", "off") == "on" || hasMapping
        
        // Get mapping indices for each wheel position (FL=0, FR=1, RL=2, RR=3)
        fun getMapping(wheelPos: Int): Int {
            return when {
                hasMapping -> carData?.car_tpms_mapping_raw?.getOrNull(wheelPos) ?: wheelPos
                else -> appPrefs.getData("tpms_${arrayOf("fl","fr","rl","rr")[wheelPos]}_$vehicleId", "$wheelPos")?.toIntOrNull() ?: wheelPos
            }
        }
        
        // Default wheel names
        val defaultWheelNames = arrayOf(
            getString(R.string.fl_tpms), 
            getString(R.string.fr_tpms), 
            getString(R.string.rl_tpms), 
            getString(R.string.rr_tpms)
        )
        
        // Get wheel name for display position
        val useAppWheelnames = appPrefs.getData("tpms_wheelname_app", "off") == "on"
        fun getWheelName(displayPos: Int): String {
            return if (useAppWheelnames) {
                defaultWheelNames.getOrNull(displayPos) ?: "Wheel $displayPos"
            } else {
                carData?.car_tpms_wheelname?.getOrNull(displayPos) ?: defaultWheelNames.getOrNull(displayPos) ?: "Wheel $displayPos"
            }
        }
        
        // Build TPMS display string for a wheel position
        fun buildTPMSDisplay(displayPos: Int): String {
            val parts = mutableListOf<String>()
            
            // Wheel name
            parts.add(getWheelName(displayPos))
            
            if (hasNewTPMS) {
                // New TPMS data (msg code 'Y') - show all available values
                // Pressure
                if (carData?.stale_tpms_pressure != DataStale.NoValue) {
                    val pressure = carData?.car_tpms_pressure?.getOrNull(displayPos) ?: "---"
                    parts.add(pressure)
                }
                // Temperature
                if (carData?.stale_tpms_temp != DataStale.NoValue) {
                    val temp = carData?.car_tpms_temp?.getOrNull(displayPos) ?: "---"
                    parts.add(temp)
                }
                // Health
                if (carData?.stale_tpms_health != DataStale.NoValue) {
                    val health = carData?.car_tpms_health?.getOrNull(displayPos) ?: "---"
                    parts.add(health)
                }
                // Alert symbol
                if (carData?.stale_tpms_alert != DataStale.NoValue) {
                    val alert = carData?.car_tpms_alert?.getOrNull(displayPos) ?: "✔"
                    parts.add(alert)
                }
            } else if (carData != null) {
                // Legacy TPMS data (msg code 'W') - only pressure and temperature
                val legacyPressure = arrayOf(
                    carData.car_tpms_fl_p,
                    carData.car_tpms_fr_p,
                    carData.car_tpms_rl_p,
                    carData.car_tpms_rr_p
                )
                val legacyTemp = arrayOf(
                    carData.car_tpms_fl_t,
                    carData.car_tpms_fr_t,
                    carData.car_tpms_rl_t,
                    carData.car_tpms_rr_t
                )
                parts.add(legacyPressure.getOrNull(displayPos) ?: "---")
                // Check if temperature data is available
                val hasTemp = carData.car_tpms_fl_t_raw != 0.0 || carData.car_tpms_fr_t_raw != 0.0 || 
                              carData.car_tpms_rl_t_raw != 0.0 || carData.car_tpms_rr_t_raw != 0.0
                if (hasTemp) {
                    parts.add(legacyTemp.getOrNull(displayPos) ?: "---")
                }
            } else {
                parts.add("---")
            }
            
            return parts.joinToString("\n")
        }
        
        // Get alert level for a wheel position (0=ok, 1=warning, 2=alert)
        fun getAlertLevel(displayPos: Int): Int {
            return if (hasNewTPMS && carData?.stale_tpms_alert != DataStale.NoValue) {
                carData?.car_tpms_alert_raw?.getOrNull(displayPos) ?: 0
            } else {
                0
            }
        }
        
        // Build options list for mapping dialog (show all sensors with their values)
        fun buildSensorOptions(): Array<String> {
            val options = mutableListOf<String>()
            val sensorCount = carData?.car_tpms_wheelname?.size ?: 4
            
            for (i in 0 until sensorCount) {
                val parts = mutableListOf<String>()
                
                // Sensor wheel name from firmware
                val wheelName = carData?.car_tpms_wheelname?.getOrNull(i) ?: "Sensor $i"
                parts.add(wheelName)
                
                if (hasNewTPMS) {
                    // Add pressure if available
                    if (carData?.stale_tpms_pressure != DataStale.NoValue) {
                        parts.add(carData?.car_tpms_pressure?.getOrNull(i) ?: "---")
                    }
                    // Add temperature if available
                    if (carData?.stale_tpms_temp != DataStale.NoValue) {
                        parts.add(carData?.car_tpms_temp?.getOrNull(i) ?: "---")
                    }
                } else if (carData != null) {
                    // Legacy data
                    val legacyPressure = arrayOf(carData.car_tpms_fl_p, carData.car_tpms_fr_p, carData.car_tpms_rl_p, carData.car_tpms_rr_p)
                    val legacyTemp = arrayOf(carData.car_tpms_fl_t, carData.car_tpms_fr_t, carData.car_tpms_rl_t, carData.car_tpms_rr_t)
                    parts.add(legacyPressure.getOrNull(i) ?: "---")
                    parts.add(legacyTemp.getOrNull(i) ?: "---")
                }
                
                options.add(parts.joinToString(" "))
            }
            return options.toTypedArray()
        }
        
        val options = buildSensorOptions()
        val wheelKeys = arrayOf("fl", "fr", "rl", "rr")
        val wheelTitles = arrayOf(R.string.fl_get_tpms, R.string.fr_get_tpms, R.string.rl_get_tpms, R.string.rr_get_tpms)
        val wheelSetTitles = arrayOf(R.string.fl_set_tpms, R.string.fr_set_tpms, R.string.rl_set_tpms, R.string.rr_set_tpms)
        
        // Get sensor wheel names for mapping command (e.g., "fl", "fr", "rl", "rr")
        fun getSensorWheelKey(sensorIdx: Int): String {
            // Get the wheel name from firmware and convert to key (fl, fr, rl, rr)
            val wheelName = carData?.car_tpms_wheelname?.getOrNull(sensorIdx)?.lowercase() ?: ""
            return when {
                wheelName.contains("fl") || wheelName.contains("front") && wheelName.contains("left") -> "fl"
                wheelName.contains("fr") || wheelName.contains("front") && wheelName.contains("right") -> "fr"
                wheelName.contains("rl") || wheelName.contains("rear") && wheelName.contains("left") -> "rl"
                wheelName.contains("rr") || wheelName.contains("rear") && wheelName.contains("right") -> "rr"
                else -> wheelKeys.getOrNull(sensorIdx) ?: "fl"
            }
        }
        
        // Setup click listeners for wheel mapping dialogs
        fun setupWheelClickListener(textView: TextView, wheelPos: Int) {
            textView.setOnClickListener {
                var checkedItem = getMapping(wheelPos)
                AlertDialog.Builder(requireActivity())
                    .setTitle(wheelTitles[wheelPos])
                    .setSingleChoiceItems(options, checkedItem) { _, which ->
                        checkedItem = which
                    }
                    .setNeutralButton(R.string.reset) { _, _ ->
                        if (byfirmware) {
                            // Reset all mappings in firmware
                            sendCommand(R.string.reset, "7,tpms map reset", this@ControlsFragment)
                        } else {
                            // Reset all app preferences to defaults
                            for (i in 0..3) {
                                appPrefs.saveData("tpms_${wheelKeys[i]}_$vehicleId", "$i")
                            }
                        }
                        updateTPMSData(carData)
                    }
                    .setNegativeButton(R.string.Close, null)
                    .setPositiveButton(wheelSetTitles[wheelPos]) { _, _ ->
                        if (byfirmware && hasMapping) {
                            // Send command to firmware: tpms map set source=target (e.g., fl=fr)
                            val sourceWheel = getSensorWheelKey(checkedItem)
                            val targetWheel = wheelKeys[wheelPos]
                            sendCommand(wheelSetTitles[wheelPos], 
                                "7,tpms map set $targetWheel=$sourceWheel",
                                this@ControlsFragment)
                        } else {
                            // Save to app preferences
                            appPrefs.saveData("tpms_${wheelKeys[wheelPos]}_$vehicleId", "$checkedItem")
                        }
                        updateTPMSData(carData)
                    }
                    .show()
            }
        }
        
        setupWheelClickListener(flTPMS, 0)
        setupWheelClickListener(frTPMS, 1)
        setupWheelClickListener(rlTPMS, 2)
        setupWheelClickListener(rrTPMS, 3)

        // Display TPMS wheel values
        flTPMS.text = buildTPMSDisplay(0)
        frTPMS.text = buildTPMSDisplay(1)
        rlTPMS.text = buildTPMSDisplay(2)
        rrTPMS.text = buildTPMSDisplay(3)
        
        // Apply alert colors
        val alertColors = intArrayOf(Color.WHITE, Color.YELLOW, Color.RED)
        val defaultColor = context?.resources?.getColor(R.color.colorAccent) ?: Color.WHITE
        
        flTPMS.setTextColor(if (getAlertLevel(0) > 0) alertColors[getAlertLevel(0)] else defaultColor)
        frTPMS.setTextColor(if (getAlertLevel(1) > 0) alertColors[getAlertLevel(1)] else defaultColor)
        rlTPMS.setTextColor(if (getAlertLevel(2) > 0) alertColors[getAlertLevel(2)] else defaultColor)
        rrTPMS.setTextColor(if (getAlertLevel(3) > 0) alertColors[getAlertLevel(3)] else defaultColor)

        // Determine overall staleness (use best available)
        val stale1 = when {
            hasNewTPMS && carData?.stale_tpms_pressure != DataStale.NoValue -> carData!!.stale_tpms_pressure
            hasNewTPMS && carData?.stale_tpms_temp != DataStale.NoValue -> carData!!.stale_tpms_temp
            hasNewTPMS && carData?.stale_tpms_health != DataStale.NoValue -> carData!!.stale_tpms_health
            hasNewTPMS && carData?.stale_tpms_alert != DataStale.NoValue -> carData!!.stale_tpms_alert
            carData != null -> carData.stale_tpms
            else -> DataStale.NoValue
        }

        when (stale1) {
            DataStale.NoValue -> {
                staleTPMS.setTextColor(Color.RED)
                staleTPMS.text = getString(R.string.no_data).lowercase()
            }
            DataStale.Stale -> {
                staleTPMS.setTextColor(Color.YELLOW)
                staleTPMS.text = getString(R.string.stale_data).lowercase()
            }
            DataStale.Good -> {
                context?.resources?.getColor(R.color.colorAccent)?.let { staleTPMS.setTextColor(it) }
                staleTPMS.text = getString(R.string.latest_data).lowercase()
            }
        }

        // Show time since last update
        val now = System.currentTimeMillis()
        val seconds = (now - (carData?.car_lastupdated?.time ?: 0)) / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = minutes / (60 * 24)

        if (minutes > 0L) {
            staleTPMS.setTextColor(Color.YELLOW)
            val periodText = when {
                minutes == 1L -> getText(R.string.min1).toString()
                days > 1 -> String.format(getText(R.string.ndays).toString(), days)
                hours > 1 -> String.format(getText(R.string.nhours).toString(), hours)
                else -> String.format(getText(R.string.nmins).toString(), minutes)
            }
            staleTPMS.text = periodText
        }

        // Auto-expand TPMS display if any alert is active
        if ((0..3).any { getAlertLevel(it) > 0 }) {
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
        if (carData?.car_type in listOf("SQ")) {
            centerActionsAdapter.mData += CarInfoQuickAction({getService()})
            centerActionsAdapter.mData += DDT4allQuickAction({getService()})
        }
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