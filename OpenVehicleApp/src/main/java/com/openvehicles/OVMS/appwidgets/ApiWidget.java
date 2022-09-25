package com.openvehicles.OVMS.appwidgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.api.ApiService;

/**
 * ApiWidget: use this as the base class for an application widget
 * 	processing vehicle data updates.
 *
 *  ApiWidget takes care of enabling the ApiService and listening to ApiEvent broadcasts.
 *
 *  The subclass only needs to define the actual widget rendering by implementing the
 *  updateWidget() method. See InfoWidget for an example.
 *
 * @param <T> -- the actual widget class
 */
public class ApiWidget<T> extends AppWidgetProvider {
	private static final String TAG = "ApiWidget";

	// We need to know our specific subtype to be able to query our widget instances:
	private final Class<T> mWidgetClass;
	public ApiWidget(Class<T> widgetClass) {
		mWidgetClass = widgetClass;
	}

	// mOnline reflects both the ApiService and the connection status:
	private static boolean mServiceIsOnline = false;
	public boolean isServiceOnline() {
		return mServiceIsOnline;
	}

	// Remember if we enabled the service for the user:
	private static boolean mServiceEnabledByUs = false;

	// First widget instance added: should we enable the ApiService for the user?
	@Override
	public void onEnabled(Context context) {
		Log.d(TAG, "onEnabled");
		super.onEnabled(context);
		AppPrefes appPrefes = new AppPrefes(context, "ovms");
		if (appPrefes.getData("option_service_enabled", "0").equals("0")) {
			Log.i(TAG, "Enabling & starting ApiService");
			appPrefes.SaveData("option_service_enabled", "1");
			try {
				context.startService(new Intent(context, ApiService.class));
				context.sendBroadcast(new Intent(ApiService.ACTION_ENABLE));
				mServiceEnabledByUs = true;
			} catch (Exception e) {
				Log.e(TAG, "Can't start ApiService: ERROR", e);
			}
		}
	}

	// Last widget instance removed: should we disable the ApiService for the user?
	@Override
	public void onDisabled(Context context) {
		Log.d(TAG, "onDisabled");
		if (mServiceEnabledByUs) {
			Log.i(TAG, "Disabling & stopping ApiService");
			AppPrefes appPrefes = new AppPrefes(context, "ovms");
			appPrefes.SaveData("option_service_enabled", "0");
			try {
				context.sendBroadcast(new Intent(ApiService.ACTION_DISABLE));
				context.stopService(new Intent(context, ApiService.class));
				mServiceEnabledByUs = false;
			} catch (Exception e) {
				Log.e(TAG, "Can't stop ApiService: ERROR", e);
			}
		}
		super.onDisabled(context);
	}


	@Override
	public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
		Log.d(TAG, "onRestored: " + newWidgetIds.length + " instances");
		super.onRestored(context, oldWidgetIds, newWidgetIds);
	}

	// Update widget instances:
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Log.d(TAG, "onUpdate: " + appWidgetIds.length + " instances");
		for (int appWidgetId : appWidgetIds) {
			updateWidget(context, appWidgetManager, appWidgetId);
		}
	}

	// Widget instance layout has been changed by user:
	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
		Log.d(TAG, "onAppWidgetOptionsChanged: widget id=" + appWidgetId);
		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
		updateWidget(context, appWidgetManager, appWidgetId);
	}

	/**
	 * onReceive: handle ApiService events, delegate system events
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (ApiService.ACTION_APIEVENT.equals(action)) {
			String event = intent.getStringExtra("event");
			Log.v(TAG, "onReceive: ApiEvent " + event);
			mServiceIsOnline = intent.getBooleanExtra("isOnline", false);
			updateAllWidgets(context);
		} else {
			super.onReceive(context, intent);
		}
	}


	// Update all widget instances:
	public void updateAllWidgets(Context context) {
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
				new ComponentName(context, mWidgetClass));
		Log.v(TAG, "updateAllWidgets: " + appWidgetIds.length + " instances");
		for (int appWidgetId : appWidgetIds) {
			updateWidget(context, appWidgetManager, appWidgetId);
		}
	}

	/**
	 * updateWidget: Update specific widget instance
	 *
	 * @param context - the current execution Context
	 * @param appWidgetManager - the AppWidgetManager instance
	 * @param appWidgetId - the ID of the widget to update
	 */
	public void updateWidget(Context context, AppWidgetManager appWidgetManager,
							 int appWidgetId) {
		// override me
	}
}
