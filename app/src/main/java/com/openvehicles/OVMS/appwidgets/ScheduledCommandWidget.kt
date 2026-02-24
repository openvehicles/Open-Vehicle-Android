package com.openvehicles.OVMS.appwidgets

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.utils.AppPrefs
import java.text.SimpleDateFormat
import java.util.*

/**
 * Widget for executing commands at a scheduled time.
 * Configuration per widget instance includes:
 * - Execution time (hour:minute)
 * - Command to execute
 * - Command title/name
 */
class ScheduledCommandWidget : AppWidgetProvider() {

    companion object {
        private const val TAG = "ScheduledCommandWidget"
        const val ACTION_EXECUTE_COMMAND = "com.openvehicles.OVMS.action.EXECUTE_SCHEDULED_COMMAND"
        const val ACTION_MANUAL_TRIGGER = "com.openvehicles.OVMS.action.MANUAL_TRIGGER_COMMAND"
        const val EXTRA_WIDGET_ID = "appWidgetId"

        private const val PREF_PREFIX = "scheduled_widget_"
        private const val PREF_COMMAND = "_command"
        private const val PREF_TITLE = "_title"
        private const val PREF_HOUR = "_hour"
        private const val PREF_MINUTE = "_minute"
        private const val PREF_DAYS = "_days"
        private const val PREF_ENABLED = "_enabled"

        fun saveWidgetConfig(
            context: Context,
            appWidgetId: Int,
            command: String,
            title: String,
            hour: Int,
            minute: Int,
            selectedDays: Int = 127 // Default: all days (1+2+4+8+16+32+64)
        ) {
            val prefs = AppPrefs(context, "ovms")
            prefs.saveData("$PREF_PREFIX$appWidgetId$PREF_COMMAND", command)
            prefs.saveData("$PREF_PREFIX$appWidgetId$PREF_TITLE", title)
            prefs.saveData("$PREF_PREFIX$appWidgetId$PREF_HOUR", hour.toString())
            prefs.saveData("$PREF_PREFIX$appWidgetId$PREF_MINUTE", minute.toString())
            prefs.saveData("$PREF_PREFIX$appWidgetId$PREF_DAYS", selectedDays.toString())
            prefs.saveData("$PREF_PREFIX$appWidgetId$PREF_ENABLED", "1")
        }

        fun deleteWidgetConfig(context: Context, appWidgetId: Int) {
            val prefs = AppPrefs(context, "ovms")
            prefs.saveData("$PREF_PREFIX$appWidgetId$PREF_COMMAND", "")
            prefs.saveData("$PREF_PREFIX$appWidgetId$PREF_TITLE", "")
            prefs.saveData("$PREF_PREFIX$appWidgetId$PREF_HOUR", "")
            prefs.saveData("$PREF_PREFIX$appWidgetId$PREF_MINUTE", "")
            prefs.saveData("$PREF_PREFIX$appWidgetId$PREF_DAYS", "")
            prefs.saveData("$PREF_PREFIX$appWidgetId$PREF_ENABLED", "0")
        }

        private fun getWidgetCommand(context: Context, appWidgetId: Int): String? {
            val prefs = AppPrefs(context, "ovms")
            return prefs.getData("$PREF_PREFIX$appWidgetId$PREF_COMMAND", null)
        }

        private fun getWidgetTitle(context: Context, appWidgetId: Int): String? {
            val prefs = AppPrefs(context, "ovms")
            return prefs.getData("$PREF_PREFIX$appWidgetId$PREF_TITLE", null)
        }

        private fun getWidgetHour(context: Context, appWidgetId: Int): Int {
            val prefs = AppPrefs(context, "ovms")
            return prefs.getData("$PREF_PREFIX$appWidgetId$PREF_HOUR", "12")?.toIntOrNull() ?: 12
        }

        private fun getWidgetMinute(context: Context, appWidgetId: Int): Int {
            val prefs = AppPrefs(context, "ovms")
            return prefs.getData("$PREF_PREFIX$appWidgetId$PREF_MINUTE", "0")?.toIntOrNull() ?: 0
        }

        private fun getWidgetDays(context: Context, appWidgetId: Int): Int {
            val prefs = AppPrefs(context, "ovms")
            return prefs.getData("$PREF_PREFIX$appWidgetId$PREF_DAYS", "127")?.toIntOrNull() ?: 127
        }

        private fun isWidgetEnabled(context: Context, appWidgetId: Int): Boolean {
            val prefs = AppPrefs(context, "ovms")
            return prefs.getData("$PREF_PREFIX$appWidgetId$PREF_ENABLED", "0") == "1"
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        try {
            Log.d(TAG, "onUpdate: ${appWidgetIds.size} widgets")
            for (appWidgetId in appWidgetIds) {
                try {
                    updateAppWidget(context, appWidgetManager, appWidgetId)
                } catch (e: Exception) {
                    Log.e(TAG, "onUpdate: Failed to update widget $appWidgetId", e)
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "onUpdate: FATAL ERROR", e)
            e.printStackTrace()
        }
    }

    override fun onEnabled(context: Context) {
        Log.d(TAG, "onEnabled: First widget instance added")
    }

    override fun onDisabled(context: Context) {
        Log.d(TAG, "onDisabled: Last widget instance removed")
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        Log.d(TAG, "onDeleted: ${appWidgetIds.size} widgets")
        for (appWidgetId in appWidgetIds) {
            cancelScheduledAlarm(context, appWidgetId)
            deleteWidgetConfig(context, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        
        when (intent.action) {
            ACTION_EXECUTE_COMMAND -> {
                val appWidgetId = intent.getIntExtra(EXTRA_WIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
                if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    executeCommand(context, appWidgetId)
                    // Reschedule for next day
                    scheduleNextAlarm(context, appWidgetId)
                    // Update widget to show last execution
                    val appWidgetManager = AppWidgetManager.getInstance(context)
                    updateAppWidget(context, appWidgetManager, appWidgetId)
                }
            }
            ACTION_MANUAL_TRIGGER -> {
                val appWidgetId = intent.getIntExtra(EXTRA_WIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
                if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    executeCommand(context, appWidgetId)
                    // Update widget to show execution
                    val appWidgetManager = AppWidgetManager.getInstance(context)
                    updateAppWidget(context, appWidgetManager, appWidgetId)
                }
            }
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        try {
            Log.d(TAG, "updateAppWidget: Starting update for widget $appWidgetId")
            
            // Create RemoteViews
            val views = RemoteViews(context.packageName, R.layout.widget_scheduled_command)
            
            // Always set default values first to prevent crashes
            views.setTextViewText(R.id.widget_title, "CMD")
            views.setTextViewText(R.id.widget_time, "--:--")
            views.setTextViewText(R.id.widget_status, "...")
            
            Log.d(TAG, "updateAppWidget: Default values set")

            try {
                val isConfigured = isWidgetEnabled(context, appWidgetId)
                Log.d(TAG, "updateAppWidget: Widget $appWidgetId configured: $isConfigured")

                if (!isConfigured) {
                    // Widget not configured yet - show simple placeholder
                    views.setTextViewText(R.id.widget_title, "---")
                    views.setTextViewText(R.id.widget_time, "--:--")
                    views.setTextViewText(R.id.widget_status, "Tap")
                    
                    // Set up configuration intent
                    try {
                        val configIntent = Intent(context, ScheduledCommandWidgetConfigActivity::class.java).apply {
                            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        val configPendingIntent = PendingIntent.getActivity(
                            context, 
                            appWidgetId, 
                            configIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                        views.setOnClickPendingIntent(R.id.widget_container, configPendingIntent)
                        Log.d(TAG, "updateAppWidget: Config intent set")
                    } catch (e: Exception) {
                        Log.e(TAG, "updateAppWidget: Failed to set config intent", e)
                    }
                    
                    Log.d(TAG, "updateAppWidget: Set unconfigured state for widget $appWidgetId")
                } else {
                    // Widget is configured
                    try {
                        val title = getWidgetTitle(context, appWidgetId) ?: "CMD"
                        val hour = getWidgetHour(context, appWidgetId)
                        val minute = getWidgetMinute(context, appWidgetId)
                        
                        Log.d(TAG, "updateAppWidget: Widget $appWidgetId config - title: $title, time: $hour:$minute")
                        
                        // Show abbreviated title (max 6 characters for 1x1 widget)
                        val shortTitle = if (title.length > 6) title.substring(0, 6) else title
                        views.setTextViewText(R.id.widget_title, shortTitle)
                        views.setTextViewText(R.id.widget_time, String.format(Locale.getDefault(), "%02d:%02d", hour, minute))
                        
                        // Calculate next execution
                        try {
                            val selectedDays = getWidgetDays(context, appWidgetId)
                            val nextExecution = getNextExecutionTime(hour, minute, selectedDays)
                            val now = Calendar.getInstance().timeInMillis
                            val totalMinutes = ((nextExecution - now) / (1000 * 60)).toInt()
                            val hoursUntil = totalMinutes / 60
                            val minutesUntil = totalMinutes % 60
                            
                            val statusText = if (hoursUntil > 0) {
                                "${hoursUntil}h"
                            } else if (minutesUntil > 0) {
                                "${minutesUntil}m"
                            } else {
                                "now"
                            }
                            views.setTextViewText(R.id.widget_status, statusText)
                            Log.d(TAG, "updateAppWidget: Status set to: $statusText")
                        } catch (e: Exception) {
                            Log.e(TAG, "updateAppWidget: Error calculating next execution", e)
                            views.setTextViewText(R.id.widget_status, "ok")
                        }
                        
                        // Set up click intent - open configuration for editing
                        try {
                            val configIntent = Intent(context, ScheduledCommandWidgetConfigActivity::class.java).apply {
                                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            val configPendingIntent = PendingIntent.getActivity(
                                context, 
                                appWidgetId, 
                                configIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                            )
                            views.setOnClickPendingIntent(R.id.widget_container, configPendingIntent)
                            Log.d(TAG, "updateAppWidget: Config intent set for editing")
                        } catch (e: Exception) {
                            Log.e(TAG, "updateAppWidget: Failed to set config intent", e)
                        }
                        
                        // Set up execute button - manual trigger
                        try {
                            val triggerIntent = Intent(context, ScheduledCommandWidget::class.java).apply {
                                action = ACTION_MANUAL_TRIGGER
                                putExtra(EXTRA_WIDGET_ID, appWidgetId)
                            }
                            val triggerPendingIntent = PendingIntent.getBroadcast(
                                context, 
                                appWidgetId + 10000, // Different request code
                                triggerIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                            )
                            views.setOnClickPendingIntent(R.id.widget_execute_btn, triggerPendingIntent)
                            Log.d(TAG, "updateAppWidget: Execute button intent set")
                        } catch (e: Exception) {
                            Log.e(TAG, "updateAppWidget: Failed to set execute button intent", e)
                        }
                        
                        // Schedule alarm if not already scheduled
                        try {
                            scheduleNextAlarm(context, appWidgetId)
                        } catch (e: Exception) {
                            Log.e(TAG, "updateAppWidget: Failed to schedule alarm", e)
                        }
                        
                        Log.d(TAG, "updateAppWidget: Configured widget $appWidgetId updated successfully")
                    } catch (e: Exception) {
                        Log.e(TAG, "updateAppWidget: Error loading config for widget $appWidgetId", e)
                        views.setTextViewText(R.id.widget_title, "ERR")
                        views.setTextViewText(R.id.widget_time, "ERR")
                        views.setTextViewText(R.id.widget_status, "err")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "updateAppWidget: Error checking config state", e)
                views.setTextViewText(R.id.widget_title, "ERR")
                views.setTextViewText(R.id.widget_time, "ERR")
                views.setTextViewText(R.id.widget_status, "log")
            }

            // Always try to update the widget, even if there were errors
            appWidgetManager.updateAppWidget(appWidgetId, views)
            Log.d(TAG, "updateAppWidget: Successfully updated widget $appWidgetId")
        } catch (e: Exception) {
            Log.e(TAG, "updateAppWidget: FATAL ERROR updating widget $appWidgetId", e)
            e.printStackTrace()
            
            // Last attempt - create minimal widget
            try {
                val views = RemoteViews(context.packageName, R.layout.widget_scheduled_command)
                views.setTextViewText(R.id.widget_title, "ERR")
                views.setTextViewText(R.id.widget_time, "XX:XX")
                views.setTextViewText(R.id.widget_status, "err")
                appWidgetManager.updateAppWidget(appWidgetId, views)
            } catch (e2: Exception) {
                Log.e(TAG, "updateAppWidget: Even minimal widget failed!", e2)
            }
        }
    }

    private fun executeCommand(context: Context, appWidgetId: Int) {
        val command = getWidgetCommand(context, appWidgetId)
        val title = getWidgetTitle(context, appWidgetId)
        
        if (command.isNullOrEmpty()) {
            Log.w(TAG, "executeCommand: No command configured for widget $appWidgetId")
            return
        }
        
        Log.i(TAG, "executeCommand: Executing command for widget $appWidgetId: $command")
        
        // Get API key for authentication
        val appPrefs = AppPrefs(context, "ovms")
        val apikey = appPrefs.getData("APIKey")
        
        // Send command directly to ApiService (works in background)
        val intent = Intent("com.openvehicles.OVMS.SendCommand").apply {
            setPackage(context.packageName)
            putExtra("command", command)
            putExtra("apikey", apikey)  // Required for authentication
        }
        
        try {
            context.sendBroadcast(intent)
            Log.i(TAG, "executeCommand: Command broadcast sent successfully with apikey")
        } catch (e: Exception) {
            Log.e(TAG, "executeCommand: Failed to send command broadcast", e)
        }
    }

    private fun getNextExecutionTime(hour: Int, minute: Int, selectedDays: Int): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        
        // If the time has passed today, start checking from tomorrow
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        
        // Find the next day that matches selectedDays
        // Monday=1, Tuesday=2, Wednesday=4, Thursday=8, Friday=16, Saturday=32, Sunday=64
        var daysChecked = 0
        while (daysChecked < 7) {
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            // Convert Calendar.DAY_OF_WEEK (1=Sunday, 2=Monday, ...) to our bitmask
            val dayBit = when (dayOfWeek) {
                Calendar.MONDAY -> 1
                Calendar.TUESDAY -> 2
                Calendar.WEDNESDAY -> 4
                Calendar.THURSDAY -> 8
                Calendar.FRIDAY -> 16
                Calendar.SATURDAY -> 32
                Calendar.SUNDAY -> 64
                else -> 0
            }
            
            if ((selectedDays and dayBit) != 0) {
                // This day is selected, use it
                return calendar.timeInMillis
            }
            
            // Try next day
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            daysChecked++
        }
        
        // Fallback: return next occurrence (shouldn't happen if at least one day is selected)
        return calendar.timeInMillis
    }

    private fun scheduleNextAlarm(context: Context, appWidgetId: Int) {
        if (!isWidgetEnabled(context, appWidgetId)) {
            return
        }
        
        val hour = getWidgetHour(context, appWidgetId)
        val minute = getWidgetMinute(context, appWidgetId)
        val selectedDays = getWidgetDays(context, appWidgetId)
        val nextExecutionTime = getNextExecutionTime(hour, minute, selectedDays)
        
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ScheduledCommandWidget::class.java).apply {
            action = ACTION_EXECUTE_COMMAND
            putExtra(EXTRA_WIDGET_ID, appWidgetId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            appWidgetId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Check if we can schedule exact alarms (Android 12+)
        val canScheduleExact = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true // Before Android 12, exact alarms don't need permission
        }
        
        try {
            if (canScheduleExact) {
                // Use exact alarm if permission granted
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    nextExecutionTime,
                    pendingIntent
                )
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                Log.i(TAG, "scheduleNextAlarm: Exact alarm for widget $appWidgetId scheduled for ${dateFormat.format(Date(nextExecutionTime))}")
            } else {
                // Use inexact alarm as fallback
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    nextExecutionTime,
                    pendingIntent
                )
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                Log.i(TAG, "scheduleNextAlarm: Inexact alarm for widget $appWidgetId scheduled for ~${dateFormat.format(Date(nextExecutionTime))}")
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "scheduleNextAlarm: Permission denied, using fallback", e)
            // Last fallback
            try {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    nextExecutionTime,
                    pendingIntent
                )
            } catch (e2: Exception) {
                Log.e(TAG, "scheduleNextAlarm: Even fallback failed", e2)
            }
        }
    }

    private fun cancelScheduledAlarm(context: Context, appWidgetId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ScheduledCommandWidget::class.java).apply {
            action = ACTION_EXECUTE_COMMAND
            putExtra(EXTRA_WIDGET_ID, appWidgetId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            appWidgetId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        alarmManager.cancel(pendingIntent)
        Log.i(TAG, "cancelScheduledAlarm: Cancelled alarm for widget $appWidgetId")
    }
}
