package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.CommandActivity
import com.openvehicles.OVMS.utils.AppPrefs
import java.util.*

/**
 * Quick action for managing climate control schedules
 * Uses climatecontrol schedule commands
 */
class ClimateScheduleQuickAction(
    apiServiceGetter: () -> ApiService?,
    context: Context? = null
) : QuickAction(
    ACTION_ID,
    R.drawable.heat_cool_w,
    apiServiceGetter,
    actionOnTint = R.attr.colorSecondaryContainer,
    actionOffTint = R.color.cardview_dark_background,
    label = context?.getString(R.string.lb_climate_schedule)
) {
    companion object {
        const val ACTION_ID = "climateschedule"
        val DAYS = arrayOf("mon", "tue", "wed", "thu", "fri", "sat", "sun")
        val DAY_LABELS_EN = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        
        // Map full day names to abbreviations
        private val DAY_NAME_MAP = mapOf(
            "monday" to "mon",
            "tuesday" to "tue",
            "wednesday" to "wed",
            "thursday" to "thu",
            "friday" to "fri",
            "saturday" to "sat",
            "sunday" to "sun"
        )
    }

    // Temporary references for status parsing
    private var dialogStatusText: TextView? = null
    private var dialogEnableSwitch: SwitchMaterial? = null
    private var dialogContext: Context? = null
    
    // Schedule data per day
    private var scheduleData: MutableMap<String, String> = mutableMapOf()
    private var dialogTimesInput: TextInputEditText? = null
    private var dialogDaySpinner: Spinner? = null

    override fun onAction() {
        val context = context ?: return
        showScheduleDialog(context)
    }

    override fun onCommandSuccess(command: String, details: String?) {
        super.onCommandSuccess(command, details)
        
        // Parse status response - command might include "7," prefix
        if (command.contains("schedule status", ignoreCase = true) && details != null) {
            parseStatusResponse(details)
        }
        
        // Parse schedule list response to populate existing schedules
        if (command.contains("schedule list", ignoreCase = true) && details != null) {
            parseScheduleListResponse(details)
        }
    }

    private fun parseScheduleListResponse(response: String) {
        Log.d("ClimateSchedule", "Received schedule list response: $response")
        
        val timesInput = dialogTimesInput ?: return
        val daySpinner = dialogDaySpinner ?: return
        
        // Clear existing data
        scheduleData.clear()
        
        // V2 protocol replaces commas with semicolons - convert back
        val normalizedResponse = response.replace(';', ',')
        
        // Parse response line by line
        // Expected format: "Monday    : 7:35/8" or "mon: 07:30, 17:00/15"
        normalizedResponse.lines().forEach { line ->
            val trimmed = line.trim()
            if (trimmed.contains(":") && !trimmed.startsWith("=")) {
                val parts = trimmed.split(":", limit = 2)
                if (parts.size == 2) {
                    val dayRaw = parts[0].trim().lowercase()
                    val times = parts[1].trim()
                    
                    // Convert full day name to abbreviation if needed
                    val day = DAY_NAME_MAP[dayRaw] ?: dayRaw
                    
                    Log.d("ClimateSchedule", "Parsed line - dayRaw: '$dayRaw', day: '$day', times: '$times'")
                    
                    // Only store if it's a valid day and has actual schedule data
                    if (DAYS.contains(day) && 
                        times.isNotEmpty() && 
                        !times.contains("(none)") && 
                        times != "-" &&
                        !times.contains("DISABLED", ignoreCase = true)) {
                        scheduleData[day] = times
                        Log.d("ClimateSchedule", "Stored schedule for $day: $times")
                    }
                }
            }
        }
        
        Log.d("ClimateSchedule", "Total schedules loaded: ${scheduleData.size}")
        
        // Update input field with current day's schedule on UI thread
        timesInput.post {
            val selectedPosition = daySpinner.selectedItemPosition
            if (selectedPosition >= 0 && selectedPosition < DAYS.size) {
                val currentDay = DAYS[selectedPosition]
                val existingSchedule = scheduleData[currentDay] ?: ""
                Log.d("ClimateSchedule", "Updating UI for day $currentDay with: '$existingSchedule'")
                if (timesInput.text.toString() != existingSchedule) {
                    timesInput.setText(existingSchedule)
                }
            }
        }
    }
    
    private fun parseStatusResponse(response: String) {
        val ctx = dialogContext ?: return
        val statusText = dialogStatusText ?: return
        val enableSwitch = dialogEnableSwitch ?: return
        
        // Parse response for "enabled" or "disabled"
        // Look for patterns like "enabled" or "Enabled: yes" etc.
        val responseLower = response.lowercase()
        val isEnabled = when {
            responseLower.contains("disabled") -> false
            responseLower.contains("enabled") -> true
            else -> return // Unknown response format, keep current state
        }
        
        // Update UI on main thread
        statusText.post {
            enableSwitch.setOnCheckedChangeListener(null) // Prevent triggering listener
            enableSwitch.isChecked = isEnabled
            updateEnableState(ctx, isEnabled)
            statusText.text = if (isEnabled) {
                ctx.getString(R.string.schedule_enabled_status)
            } else {
                ctx.getString(R.string.schedule_disabled_status)
            }
            // Re-attach listener
            setupEnableSwitchListener(ctx, statusText, enableSwitch)
        }
    }

    private fun setupEnableSwitchListener(context: Context, statusText: TextView, enableSwitch: SwitchMaterial) {
        enableSwitch.setOnCheckedChangeListener { _, isChecked ->
            statusText.text = if (isChecked) {
                context.getString(R.string.schedule_enabled_status)
            } else {
                context.getString(R.string.schedule_disabled_status)
            }
            updateEnableState(context, isChecked)
            val cmd = if (isChecked) {
                "7,climatecontrol schedule enable"
            } else {
                "7,climatecontrol schedule disable"
            }
            sendCommand(cmd)
        }
    }

    private fun updateEnableState(context: Context, enabled: Boolean) {
        val vehicleId = getCarData()?.sel_vehicleid ?: return
        val appPrefs = AppPrefs(context, "ovms")
        appPrefs.saveData("climate_schedule_enabled_$vehicleId", if (enabled) "yes" else "no")
        
        // Update button state
        setActionState(enabled)
    }

    private fun isScheduleEnabled(context: Context): Boolean {
        val vehicleId = getCarData()?.sel_vehicleid ?: return false
        val appPrefs = AppPrefs(context, "ovms")
        return appPrefs.getData("climate_schedule_enabled_$vehicleId", "no") == "yes"
    }

    private fun showScheduleDialog(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dlg_climate_schedule, null)
        
        val daySpinner = dialogView.findViewById<Spinner>(R.id.day_spinner)
        val timesInput = dialogView.findViewById<TextInputEditText>(R.id.times_input)
        val enableSwitch = dialogView.findViewById<SwitchMaterial>(R.id.enable_switch)
        val statusText = dialogView.findViewById<TextView>(R.id.status_text)
        
        // Store references for status parsing and schedule loading
        dialogContext = context
        dialogStatusText = statusText
        dialogEnableSwitch = enableSwitch
        dialogTimesInput = timesInput
        dialogDaySpinner = daySpinner
        
        // Initialize switch with saved state
        enableSwitch.isChecked = isScheduleEnabled(context)
        statusText.text = if (enableSwitch.isChecked) {
            context.getString(R.string.schedule_enabled_status)
        } else {
            context.getString(R.string.schedule_disabled_status)
        }
        
        // Setup day spinner
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, DAY_LABELS_EN)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = adapter
        
        // Add listener to update times input when day changes
        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val selectedDay = DAYS[position]
                val existingTimes = scheduleData[selectedDay] ?: ""
                timesInput.setText(existingTimes)
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
        
        // Set current day as default (after listener is set)
        val calendar = Calendar.getInstance()
        val currentDay = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7 // Convert to mon=0
        daySpinner.setSelection(currentDay)
        
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.lb_climate_schedule)
            .setView(dialogView)
            .setPositiveButton(R.string.Set) { _, _ ->
                val selectedDay = DAYS[daySpinner.selectedItemPosition]
                val times = timesInput.text.toString().trim()
                
                if (times.isNotEmpty()) {
                    val cmd = "7,climatecontrol schedule set $selectedDay $times"
                    sendCommand(cmd)
                }
            }
            .setNegativeButton(R.string.Cancel, null)
            .setNeutralButton(R.string.more) { _, _ ->
                showAdvancedOptions(context)
            }
            .show()
        
        // Setup enable/disable switch listener
        setupEnableSwitchListener(context, statusText, enableSwitch)
        
        // Request status and schedule list from vehicle
        sendCommand("7,climatecontrol schedule status")
        sendCommand("7,climatecontrol schedule list")
    }

    private fun showAdvancedOptions(context: Context) {
        val options = arrayOf(
            context.getString(R.string.schedule_list),
            context.getString(R.string.schedule_clear_day),
            context.getString(R.string.schedule_clear_all),
            context.getString(R.string.schedule_copy)
        )
        
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.schedule_advanced)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        // Use CommandActivity to show result in notification
                        val appPrefs = AppPrefs(context, "ovms")
                        val intent = Intent(context, CommandActivity::class.java).apply {
                            action = "com.openvehicles.OVMS.action.COMMAND"
                            putExtra("apikey", appPrefs.getData("APIKey"))
                            putExtra("command", "climatecontrol schedule list")
                            putExtra("title", context.getString(R.string.schedule_list))
                        }
                        context.startActivity(intent)
                    }
                    1 -> showClearDayDialog(context)
                    2 -> showClearAllDialog(context)
                    3 -> showCopyDialog(context)
                }
            }
            .setNegativeButton(R.string.Cancel, null)
            .show()
    }

    private fun showClearDayDialog(context: Context) {
        val daySpinner = Spinner(context)
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, DAY_LABELS_EN)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = adapter
        
        val padding = context.resources.getDimensionPixelSize(R.dimen.dialog_padding)
        daySpinner.setPadding(padding, padding, padding, padding)
        
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.schedule_clear_day)
            .setView(daySpinner)
            .setPositiveButton(R.string.Clear) { _, _ ->
                val selectedDay = DAYS[daySpinner.selectedItemPosition]
                sendCommand("7,climatecontrol schedule clear $selectedDay")
            }
            .setNegativeButton(R.string.Cancel, null)
            .show()
    }

    private fun showClearAllDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.schedule_clear_all)
            .setMessage(R.string.schedule_clear_all_confirm)
            .setPositiveButton(R.string.Clear) { _, _ ->
                sendCommand("7,climatecontrol schedule clear all")
            }
            .setNegativeButton(R.string.Cancel, null)
            .show()
    }

    private fun showCopyDialog(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dlg_schedule_copy, null)
        val sourceSpinner = dialogView.findViewById<Spinner>(R.id.source_day_spinner)
        val targetInput = dialogView.findViewById<TextInputEditText>(R.id.target_days_input)
        
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, DAY_LABELS_EN)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sourceSpinner.adapter = adapter
        
        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.schedule_copy)
            .setView(dialogView)
            .setPositiveButton(R.string.Copy) { _, _ ->
                val sourceDay = DAYS[sourceSpinner.selectedItemPosition]
                val targetDays = targetInput.text.toString().trim()
                
                if (targetDays.isNotEmpty()) {
                    sendCommand("7,climatecontrol schedule copy $sourceDay $targetDays")
                }
            }
            .setNegativeButton(R.string.Cancel, null)
            .show()
    }

    override fun getStateFromCarData(): Boolean {
        // Try to get context, fallback to false if not available
        val ctx = context ?: return false
        val vehicleId = getCarData()?.sel_vehicleid ?: return false
        
        return try {
            val appPrefs = AppPrefs(ctx, "ovms")
            appPrefs.getData("climate_schedule_enabled_$vehicleId", "no") == "yes"
        } catch (e: Exception) {
            false
        }
    }



    override fun commandsAvailable(): Boolean {
        return getCarData()?.car_type in listOf("NL","SE","SQ","VWUP","VWUP.T26","RZ","RZ2")
    }
}
