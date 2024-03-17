package com.openvehicles.OVMS.appwidgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.utils.AppPrefes

/**
 * ApiWidget: use this as the base class for an application widget
 * processing vehicle data updates.
 *
 * ApiWidget takes care of enabling the ApiService and listening to ApiEvent broadcasts.
 *
 * The subclass only needs to define the actual widget rendering by implementing the
 * updateWidget() method. See InfoWidget for an example.
 *
 * @param <T> The actual widget class.
 */
open class ApiWidget<T>(

    // We need to know our specific subtype to be able to query our widget instances:
    private val widgetClass: Class<T>

) : AppWidgetProvider() {

    // First widget instance added: should we enable the ApiService for the user?
    override fun onEnabled(context: Context) {
        Log.d(TAG, "onEnabled")
        super.onEnabled(context)

        val appPrefes = AppPrefes(context, "ovms")
        if (appPrefes.getData("option_service_enabled", "0") == "0") {
            Log.i(TAG, "Enabling & starting ApiService")
            appPrefes.saveData("option_service_enabled", "1")
            try {
                context.startService(Intent(context, ApiService::class.java))
                context.sendBroadcast(Intent(ApiService.ACTION_ENABLE))
                serviceEnabledByUs = true
            } catch (e: Exception) {
                Log.e(TAG, "Can't start ApiService: ERROR", e)
            }
        }
    }

    // Last widget instance removed: should we disable the ApiService for the user?
    override fun onDisabled(context: Context) {
        Log.d(TAG, "onDisabled")
        if (serviceEnabledByUs) {
            Log.i(TAG, "Disabling & stopping ApiService")
            val appPrefes = AppPrefes(context, "ovms")
            appPrefes.saveData("option_service_enabled", "0")
            try {
                context.sendBroadcast(Intent(ApiService.ACTION_DISABLE))
                context.stopService(Intent(context, ApiService::class.java))
                serviceEnabledByUs = false
            } catch (e: Exception) {
                Log.e(TAG, "Can't stop ApiService: ERROR", e)
            }
        }

        super.onDisabled(context)
    }

    override fun onRestored(context: Context, oldWidgetIds: IntArray, newWidgetIds: IntArray) {
        Log.d(TAG, "onRestored: " + newWidgetIds.size + " instances")
        super.onRestored(context, oldWidgetIds, newWidgetIds)
    }

    // Update widget instances:
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d(TAG, "onUpdate: " + appWidgetIds.size + " instances")
        for (appWidgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }

    // Widget instance layout has been changed by user:
    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: Bundle
    ) {
        Log.d(TAG, "onAppWidgetOptionsChanged: widget id=$appWidgetId")
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        updateWidget(context, appWidgetManager, appWidgetId)
    }

    /**
     * onReceive: handle ApiService events, delegate system events
     */
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (ApiService.ACTION_APIEVENT == action) {
            val event = intent.getStringExtra("event")
            Log.v(TAG, "onReceive: ApiEvent $event")
            isServiceOnline = intent.getBooleanExtra("isOnline", false)
            updateAllWidgets(context)
        } else {
            super.onReceive(context, intent)
        }
    }

    // Update all widget instances:
    fun updateAllWidgets(context: Context?) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(context!!, widgetClass)
        )
        Log.v(TAG, "updateAllWidgets: " + appWidgetIds.size + " instances")
        for (appWidgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }

    /**
     * updateWidget: Update specific widget instance
     *
     * @param context - the current execution Context
     * @param appWidgetManager - the AppWidgetManager instance
     * @param appWidgetId - the ID of the widget to update
     */
    open fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int
    ) {
        // override me
    }

    companion object {

        private const val TAG = "ApiWidget"

        // mOnline reflects both the ApiService and the connection status:
        var isServiceOnline = false
            private set

        // Remember if we enabled the service for the user:
        private var serviceEnabledByUs = false
    }
}
