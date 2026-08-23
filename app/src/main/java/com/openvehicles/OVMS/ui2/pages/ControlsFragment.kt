package com.openvehicles.OVMS.ui2.pages

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.color.MaterialColors
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.TextInputEditText
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.CommandActivity
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.entities.CarData.DataStale
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.utils.Ui
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
import java.text.DateFormat
import java.util.Date


class ControlsFragment : BaseFragment(), OnResultCommandListener {

    private var carData: CarData? = null

    private lateinit var sideActionsAdapter: QuickActionsAdapter
    private lateinit var bottomActionsAdapter: QuickActionsAdapter
    private lateinit var centerActionsAdapter: QuickActionsAdapter
    private lateinit var appPrefs: AppPrefs
    private var tpmsConfig: MutableMap<String, String> = mutableMapOf()
    private var lastConfigVid: String? = null
    private var isLoadingConfig = false


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

        mainActionsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mainActionsRecyclerView.adapter = centerActionsAdapter

        updateServiceInfo(carData)
        updateTPMSData(carData)
        initialiseSideActions(carData)
        initialiseMainActions(carData)
        initialiseCarRendering(carData)
        loadTPMSConfig()

        findViewById(R.id.btnEditTPMSConfig).setOnClickListener {
            showEditTPMSConfigDialog()
        }

        // Setup collapsible TPMS Config header
        val tpmsConfigHeader = findViewById(R.id.tpmsConfigHeader)
        val tpmsConfigBody = findViewById(R.id.tpmsConfigBody)
        val tpmsConfigChevron = findViewById(R.id.tpmsConfigChevron)

        tpmsConfigHeader.setOnClickListener {
            val vehicleId = carData?.sel_vehicleid
            val isExpanded = tpmsConfigBody.visibility == View.VISIBLE
            val nextVis = if (isExpanded) View.GONE else View.VISIBLE
            
            tpmsConfigBody.visibility = nextVis
            tpmsConfigChevron.rotation = if (isExpanded) 0f else 90f
            
            appPrefs.saveData("pref_controls_tpms_config_expanded_$vehicleId", if (nextVis == View.VISIBLE) "1" else "0")
        }
    }

    override fun update(carData: CarData?) {
        val carIdChanged = this.carData?.sel_vehicleid != carData?.sel_vehicleid
        this.carData = carData
        updateServiceInfo(carData)
        updateTPMSData(carData)
        if (carIdChanged) {
            tpmsConfig.clear()
            updateTPMSConfigUI()
            loadTPMSConfig()
        }
        sideActionsAdapter.setCarData(carData)
        bottomActionsAdapter.setCarData(carData)
        centerActionsAdapter.setCarData(carData)
        sideActionsAdapter.notifyDataSetChanged()
        bottomActionsAdapter.notifyDataSetChanged()
        centerActionsAdapter.notifyDataSetChanged()
        initialiseCarRendering(carData)
    }

    private fun loadTPMSConfig() {
        val vid = carData?.sel_vehicleid
        if (carData?.car_type != "SQ" || vid == null) {
            findViewById(R.id.cardTPMSConfig).visibility = View.GONE
            return
        }

        if (isLoadingConfig && lastConfigVid == vid) return
        
        isLoadingConfig = true
        lastConfigVid = vid
        
        // Use prefix 7 (command with output) instead of 1
        sendCommand("", "7,config list xsq", object : OnResultCommandListener {
            override fun onResultCommand(result: Array<String>) {
                isLoadingConfig = false
                if (result.size > 2 && result[1] == "0") {
                    parseTPMSConfig(result[2])
                    updateTPMSConfigUI()
                } else {
                    // Try without prefix if 7 fails, or maybe it's just slow
                    Log.w("ControlsFragment", "TPMS Config load failed with code ${result.getOrNull(1)}")
                }
            }
        })
    }

    private fun parseTPMSConfig(output: String) {
        tpmsConfig.clear()
        val lines = output.lines()
        for (line in lines) {
            // More robust parsing: split at first colon, trim both sides
            val parts = line.trim().split(":", limit = 2)
            if (parts.size == 2) {
                val key = parts[0].trim()
                val value = parts[1].trim()
                if (key.isNotEmpty()) {
                    tpmsConfig[key] = value
                }
            }
        }
    }

    private fun updateTPMSConfigUI() {
        if (view == null) return
        val summaryView = findViewById(R.id.tpmsConfigSummary) as TextView
        val card = findViewById(R.id.cardTPMSConfig)
        val body = findViewById(R.id.tpmsConfigBody)
        val chevron = findViewById(R.id.tpmsConfigChevron) as ImageView
        
        if (tpmsConfig.isEmpty()) {
            card.visibility = View.GONE
            return
        }

        val vehicleId = getLastSelectedCarId()
        val showTPMS = appPrefs.getData("pref_controls_show_tpms_$vehicleId", "0") == "1"
        card.visibility = if (showTPMS) View.VISIBLE else View.GONE
        
        // Restore expanded state
        val isExpanded = appPrefs.getData("pref_controls_tpms_config_expanded_$vehicleId", "0") == "1"
        body.visibility = if (isExpanded) View.VISIBLE else View.GONE
        chevron.rotation = if (isExpanded) 90f else 0f
        
        val alert = tpmsConfig["tpms.alert.enable"] ?: "no"
        val front = tpmsConfig["tpms.front.pressure"] ?: "0"
        val rear = tpmsConfig["tpms.rear.pressure"] ?: "0"
        val temp = tpmsConfig["tpms.temp"] ?: "no"
        val warnVal = tpmsConfig["tpms.value.warn"] ?: "0"
        val alertVal = tpmsConfig["tpms.value.alert"] ?: "0"
        
        summaryView.text = "\nAlert message: $alert \n\nShow temperatures: $temp \n\nTarget pressure:\n  Front: $front kPa • Rear: $rear kPa \n\nDifference from target pressure:\n  Warn: $warnVal kPa • Alert: $alertVal kPa"
    }

    private fun showEditTPMSConfigDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_tpms_config, null)
        val swAlert = dialogView.findViewById<MaterialSwitch>(R.id.swAlertEnable)
        val etFront = dialogView.findViewById<TextInputEditText>(R.id.etFrontPressure)
        val etRear = dialogView.findViewById<TextInputEditText>(R.id.etRearPressure)
        val swTemp = dialogView.findViewById<MaterialSwitch>(R.id.swTempEnable)
        val etWarn = dialogView.findViewById<TextInputEditText>(R.id.etWarnValue)
        val etAlert = dialogView.findViewById<TextInputEditText>(R.id.etAlertValue)

        swAlert.isChecked = tpmsConfig["tpms.alert.enable"] == "yes"
        etFront.setText(tpmsConfig["tpms.front.pressure"] ?: "0")
        etRear.setText(tpmsConfig["tpms.rear.pressure"] ?: "0")
        swTemp.isChecked = tpmsConfig["tpms.temp"] == "yes"
        etWarn.setText(tpmsConfig["tpms.value.warn"] ?: "0")
        etAlert.setText(tpmsConfig["tpms.value.alert"] ?: "0")

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("TPMS Config (XSQ)")
            .setView(dialogView)
            .setPositiveButton(R.string.Save) { _, _ ->
                val newConfig = mapOf(
                    "tpms.alert.enable" to if (swAlert.isChecked) "yes" else "no",
                    "tpms.front.pressure" to etFront.text.toString(),
                    "tpms.rear.pressure" to etRear.text.toString(),
                    "tpms.temp" to if (swTemp.isChecked) "yes" else "no",
                    "tpms.value.warn" to etWarn.text.toString(),
                    "tpms.value.alert" to etAlert.text.toString()
                )
                saveTPMSConfig(newConfig)
            }
            .setNegativeButton(R.string.Cancel, null)
            .show()
    }

    private fun saveTPMSConfig(newConfig: Map<String, String>) {
        val commands = mutableListOf<String>()
        for ((key, value) in newConfig) {
            if (tpmsConfig[key] != value) {
                commands.add("7,config set xsq $key $value")
            }
        }
        
        if (commands.isEmpty()) return
        
        // Send commands one by one
        for (cmd in commands) {
            sendCommand("Saving...", cmd, null)
        }
        
        // Give it a short delay and reload
        view?.postDelayed({ loadTPMSConfig() }, 1500)
    }

    private fun updateServiceInfo(carData: CarData?) {
        // Show known car service interval info:
        val serviceBtn= findViewById(R.id.serviceToggle) as ExtendedFloatingActionButton
        val serviceTextView = findViewById(R.id.serviceinfo) as TextView
        val serviceCard = findViewById(R.id.cardServiceInfo) as View?

        val vehicleId = carData?.sel_vehicleid

        val serviceInfoParts = mutableListOf<String>()
        if (carData!!.car_servicerange >= 0) {
            serviceInfoParts.add(String.format("%d km", carData.car_servicerange))
        }
        if (carData.car_servicetime >= 0) {
            val now = System.currentTimeMillis() / 1000
            val serviceDays = (carData.car_servicetime - now) / 86400
            
            var timeInfo = String.format(getString(R.string.ndays), serviceDays)
            if (serviceDays > 0) {
                val date = Date(carData.car_servicetime.toLong() * 1000)
                val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
                timeInfo += " (${dateFormat.format(date)})"
            }
            serviceInfoParts.add(timeInfo)
        }
        if (carData.car_gen_substate != "" && carData.car_type == "SQ") {
            serviceInfoParts.add(carData.car_gen_substate)
        }

        if (serviceInfoParts.isEmpty()) {
            serviceBtn.visibility = View.INVISIBLE
            serviceBtn.isEnabled = false
            serviceCard?.visibility = View.GONE
        } else {
            serviceBtn.visibility = View.VISIBLE
            // Join parts with a bullet or separator to keep them side by side
            serviceTextView.text = serviceInfoParts.joinToString("  •  ")

            // Apply persisted visibility
            val showService = appPrefs.getData("pref_controls_show_service_$vehicleId", "0") == "1"
            serviceCard?.isVisible = showService
        }

        serviceBtn.setOnClickListener {
            // Toggle visibility
            serviceCard?.let { card ->
                card.isVisible = !card.isVisible
                appPrefs.saveData("pref_controls_show_service_$vehicleId", if (card.isVisible) "1" else "0")
            }
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
        val cardFL = findViewById(R.id.cardTPMS_FL) as View?
        val cardFR = findViewById(R.id.cardTPMS_FR) as View?
        val cardRL = findViewById(R.id.cardTPMS_RL) as View?
        val cardRR = findViewById(R.id.cardTPMS_RR) as View?
        val cardConfig = findViewById(R.id.cardTPMSConfig) as View?
        val staleTPMS = findViewById(R.id.tpmsStale) as TextView
        val tpmsFAB = findViewById(R.id.tpmsToggle) as ExtendedFloatingActionButton

        val vehicleId = getLastSelectedCarId()

        // Disable TPMS for vehicles not supporting any:
        if (carData?.car_type in listOf("RT", "EN", "NRJK")) {
            tpmsFAB.isEnabled = false
            tpmsFAB.visibility = View.INVISIBLE
            return
        }

        // Apply persisted visibility
        val showTPMS = appPrefs.getData("pref_controls_show_tpms_$vehicleId", "0") == "1"
        val initialVis = if (showTPMS) View.VISIBLE else View.GONE
        cardFL?.visibility = initialVis
        cardFR?.visibility = initialVis
        cardRL?.visibility = initialVis
        cardRR?.visibility = initialVis
        staleTPMS.visibility = initialVis
        if (carData?.car_type == "SQ" && !tpmsConfig.isEmpty()) {
            cardConfig?.visibility = initialVis
        }

        tpmsFAB.setOnClickListener {
            val nextVis = if (cardFR?.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            cardFL?.visibility = nextVis
            cardFR?.visibility = nextVis
            cardRL?.visibility = nextVis
            cardRR?.visibility = nextVis
            staleTPMS.visibility = nextVis
            if (carData?.car_type == "SQ" && !tpmsConfig.isEmpty()) {
                cardConfig?.visibility = nextVis
            }
            appPrefs.saveData("pref_controls_show_tpms_$vehicleId", if (nextVis == View.VISIBLE) "1" else "0")
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
        val alertColors = intArrayOf(
            MaterialColors.getColor(requireContext(), android.R.attr.textColorPrimary, Color.BLACK),
            ContextCompat.getColor(requireContext(), R.color.chargeOtherColor),
            ContextCompat.getColor(requireContext(), R.color.colorTextError)
        )
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.colorAccent)
        
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

        val errorColor = ContextCompat.getColor(requireContext(), R.color.colorTextError)
        val warningColor = ContextCompat.getColor(requireContext(), R.color.chargeOtherColor)

        when (stale1) {
            DataStale.NoValue -> {
                staleTPMS.setTextColor(errorColor)
                staleTPMS.text = getString(R.string.no_data).lowercase()
            }
            DataStale.Stale -> {
                staleTPMS.setTextColor(warningColor)
                staleTPMS.text = getString(R.string.stale_data).lowercase()
            }
            DataStale.Good -> {
                staleTPMS.setTextColor(defaultColor)
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
            staleTPMS.setTextColor(warningColor)
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
            if (cardFR?.visibility != View.VISIBLE) {
                tpmsFAB.performClick()
            }
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
        sideActionsAdapter.setCarData(carData)
        val context = requireContext()
        if (carData?.car_type == "RT") {
            // Renault Twizy: use Homelink for profile switching:
            sideActionsAdapter.mData += TwizyDriveModeDefaultQuickAction({getService()}, context)
            sideActionsAdapter.mData += TwizyDriveMode1QuickAction({getService()}, context)
            sideActionsAdapter.mData += TwizyDriveMode2QuickAction({getService()}, context)
            sideActionsAdapter.mData += TwizyDriveMode3QuickAction({getService()}, context)
        } else {
            sideActionsAdapter.mData += Homelink1QuickAction({getService()}, context)
            sideActionsAdapter.mData += Homelink2QuickAction({getService()}, context)
            sideActionsAdapter.mData += Homelink3QuickAction({getService()}, context)
        }
        sideActionsAdapter.notifyDataSetChanged()
    }

    private fun initialiseMainActions(carData: CarData?) {
        centerActionsAdapter.mData.clear()
        centerActionsAdapter.setCarData(carData)
        val context = requireContext()
        centerActionsAdapter.mData += LockQuickAction({getService()}, context)
        if (carData?.car_type != "SQ") centerActionsAdapter.mData += ValetQuickAction({getService()}, context)
        centerActionsAdapter.mData += WakeupQuickAction({getService()}, context)
        if (carData?.car_type != "SQ") centerActionsAdapter.mData += ChargingQuickAction({getService()}, context)
        if (carData?.car_type in listOf("SQ")) {
            centerActionsAdapter.mData += CarInfoQuickAction({getService()}, context)
            centerActionsAdapter.mData += DDT4allQuickAction({getService()}, context)
        }
        centerActionsAdapter.notifyDataSetChanged()
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