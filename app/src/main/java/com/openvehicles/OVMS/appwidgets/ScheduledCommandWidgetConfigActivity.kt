package com.openvehicles.OVMS.appwidgets

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.StoredCommand
import com.openvehicles.OVMS.ui.utils.Database

/**
 * Configuration activity for ScheduledCommandWidget.
 * Allows user to select:
 * - Time (hour:minute) for daily execution
 * - Command from stored commands
 */
class ScheduledCommandWidgetConfigActivity : AppCompatActivity() {

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var timePicker: TimePicker
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private lateinit var checkboxMonday: CheckBox
    private lateinit var checkboxTuesday: CheckBox
    private lateinit var checkboxWednesday: CheckBox
    private lateinit var checkboxThursday: CheckBox
    private lateinit var checkboxFriday: CheckBox
    private lateinit var checkboxSaturday: CheckBox
    private lateinit var checkboxSunday: CheckBox
    
    private lateinit var database: Database
    private var storedCommands: List<StoredCommand> = emptyList()
    private var selectedCommand: StoredCommand? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set result to CANCELED in case user backs out
        setResult(Activity.RESULT_CANCELED)
        
        setContentView(R.layout.activity_scheduled_command_widget_config)
        supportActionBar?.title = getString(R.string.configure_scheduled_widget)
        
        // Get widget ID from intent
        appWidgetId = intent?.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
        
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }
        
        database = Database(this)
        
        timePicker = findViewById(R.id.time_picker)
        timePicker.setIs24HourView(true)
        
        // Initialize day checkboxes
        checkboxMonday = findViewById(R.id.checkbox_monday)
        checkboxTuesday = findViewById(R.id.checkbox_tuesday)
        checkboxWednesday = findViewById(R.id.checkbox_wednesday)
        checkboxThursday = findViewById(R.id.checkbox_thursday)
        checkboxFriday = findViewById(R.id.checkbox_friday)
        checkboxSaturday = findViewById(R.id.checkbox_saturday)
        checkboxSunday = findViewById(R.id.checkbox_sunday)
        
        recyclerView = findViewById(R.id.command_list)
        btnSave = findViewById(R.id.btn_save)
        btnCancel = findViewById(R.id.btn_cancel)
        
        loadStoredCommands()
        setupRecyclerView()
        setupButtons()
        loadExistingConfiguration()
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }

    private fun loadStoredCommands() {
        storedCommands = database.getStoredCommands()
    }
    
    private fun loadExistingConfiguration() {
        // Check if widget is already configured (editing mode)
        val prefs = com.openvehicles.OVMS.utils.AppPrefs(this, "ovms")
        val existingCommand = prefs.getData("scheduled_widget_${appWidgetId}_command", null)
        
        if (existingCommand != null) {
            // Widget is being edited - load existing values
            val existingTitle = prefs.getData("scheduled_widget_${appWidgetId}_title", null)
            val existingHour = prefs.getData("scheduled_widget_${appWidgetId}_hour", "12")?.toIntOrNull() ?: 12
            val existingMinute = prefs.getData("scheduled_widget_${appWidgetId}_minute", "0")?.toIntOrNull() ?: 0
            val existingDays = prefs.getData("scheduled_widget_${appWidgetId}_days", "127")?.toIntOrNull() ?: 127
            
            // Set time picker
            timePicker.hour = existingHour
            timePicker.minute = existingMinute
            
            // Set day checkboxes
            checkboxMonday.isChecked = (existingDays and 1) != 0
            checkboxTuesday.isChecked = (existingDays and 2) != 0
            checkboxWednesday.isChecked = (existingDays and 4) != 0
            checkboxThursday.isChecked = (existingDays and 8) != 0
            checkboxFriday.isChecked = (existingDays and 16) != 0
            checkboxSaturday.isChecked = (existingDays and 32) != 0
            checkboxSunday.isChecked = (existingDays and 64) != 0
            
            // Pre-select the command
            storedCommands.find { it.command == existingCommand }?.let { command ->
                selectedCommand = command
                // Scroll to and highlight the command in the RecyclerView
                val position = storedCommands.indexOf(command)
                if (position >= 0) {
                    recyclerView.scrollToPosition(position)
                }
            }
            
            // Change title to indicate editing
            supportActionBar?.title = getString(R.string.edit_scheduled_widget)
        }
    }

    private fun setupRecyclerView() {
        val adapter = CommandSelectionAdapter(storedCommands) { command ->
            selectedCommand = command
            Toast.makeText(
                this,
                getString(R.string.command_selected, command.title),
                Toast.LENGTH_SHORT
            ).show()
        }
        
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupButtons() {
        btnSave.setOnClickListener {
            saveConfiguration()
        }
        
        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveConfiguration() {
        if (selectedCommand == null) {
            Toast.makeText(
                this,
                R.string.please_select_command,
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        
        // Get selected days as bitmask (Monday=1, Tuesday=2, ..., Sunday=64)
        val selectedDays = getSelectedDays()
        if (selectedDays == 0) {
            Toast.makeText(
                this,
                R.string.please_select_at_least_one_day,
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        
        // Check if background commands are enabled
        val prefs = com.openvehicles.OVMS.utils.AppPrefs(this, "ovms")
        val commandsEnabled = prefs.getData("option_commands_enabled", "0") == "1"
        
        if (!commandsEnabled) {
            // Show warning and offer to enable
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(R.string.background_commands_disabled_title)
                .setMessage(R.string.background_commands_disabled_message)
                .setPositiveButton(R.string.enable_and_save) { _, _ ->
                    // Enable background commands
                    prefs.saveData("option_commands_enabled", "1")
                    // Continue with save
                    performSave()
                }
                .setNegativeButton(R.string.save_anyway) { _, _ ->
                    // Save without enabling
                    performSave()
                }
                .setNeutralButton(android.R.string.cancel, null)
                .show()
            return
        }
        
        performSave()
    }
    
    private fun performSave() {
        val hour = timePicker.hour
        val minute = timePicker.minute
        val selectedDays = getSelectedDays()
        
        try {
            // Save widget configuration
            ScheduledCommandWidget.saveWidgetConfig(
                this,
                appWidgetId,
                selectedCommand!!.command,
                selectedCommand!!.title,
                hour,
                minute,
                selectedDays
            )
            
            // Directly update the widget
            val appWidgetManager = AppWidgetManager.getInstance(this)
            ScheduledCommandWidget().apply {
                onUpdate(this@ScheduledCommandWidgetConfigActivity, appWidgetManager, intArrayOf(appWidgetId))
            }
            
            // Return success
            val resultValue = Intent().apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            setResult(Activity.RESULT_OK, resultValue)
            
            Toast.makeText(
                this,
                getString(R.string.widget_configured_successfully),
                Toast.LENGTH_SHORT
            ).show()
            
            finish()
        } catch (e: Exception) {
            android.util.Log.e("ScheduledWidget", "Error saving configuration", e)
            Toast.makeText(
                this,
                "Error: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    
    private fun getSelectedDays(): Int {
        var days = 0
        if (checkboxMonday.isChecked) days = days or 1
        if (checkboxTuesday.isChecked) days = days or 2
        if (checkboxWednesday.isChecked) days = days or 4
        if (checkboxThursday.isChecked) days = days or 8
        if (checkboxFriday.isChecked) days = days or 16
        if (checkboxSaturday.isChecked) days = days or 32
        if (checkboxSunday.isChecked) days = days or 64
        return days
    }

    /**
     * Adapter for displaying stored commands in RecyclerView
     */
    private inner class CommandSelectionAdapter(
        private val commands: List<StoredCommand>,
        private val onCommandSelected: (StoredCommand) -> Unit
    ) : RecyclerView.Adapter<CommandSelectionAdapter.ViewHolder>() {

        private var selectedPosition = RecyclerView.NO_POSITION

        inner class ViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
            val radioButton: android.widget.RadioButton = view.findViewById(R.id.radio_button)
            val textTitle: android.widget.TextView = view.findViewById(R.id.text_title)
            val textCommand: android.widget.TextView = view.findViewById(R.id.text_command)
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
            val view = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_command_selection, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val command = commands[position]
            holder.textTitle.text = command.title
            holder.textCommand.text = command.command
            holder.radioButton.isChecked = position == selectedPosition
            
            holder.itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = holder.adapterPosition
                
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                
                onCommandSelected(command)
            }
            
            holder.radioButton.setOnClickListener {
                holder.itemView.performClick()
            }
        }

        override fun getItemCount() = commands.size
    }
}
