package com.openvehicles.OVMS;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

public final class OVMSWidgets {
	public static void UpdateWidget(Context paramContext,
			AppWidgetManager paramAppWidgetManager, int paramInt) {
		try {
			Log.d("OVMS",
					"Loading saved cars from internal storage file: OVMSSavedCars.obj");
			ObjectInputStream localObjectInputStream = new ObjectInputStream(
					paramContext.openFileInput("OVMSSavedCars.obj"));
			localArrayList = (ArrayList) localObjectInputStream.readObject();
			localObjectInputStream.close();
			localCarData = null;
			str = paramContext.getSharedPreferences("OVMS", 0)
					.getString("LastVehicleID", "").trim();
			if (str.length() == 0) {
				localCarData = (CarData) localArrayList.get(0);
				localRemoteViews = getUpdatedRemoteViews(paramContext,
						localCarData);
				if (localRemoteViews != null)
					break label214;
			}
		} catch (Exception localException) {
			while (true) {
				ArrayList localArrayList;
				CarData localCarData;
				String str;
				RemoteViews localRemoteViews;
				localException.printStackTrace();
				continue;
				Object[] arrayOfObject = new Object[2];
				arrayOfObject[0] = Integer.valueOf(localArrayList.size());
				arrayOfObject[1] = str;
				Log.d("OVMS", String.format(
						"Loaded %s cars. Last used car is %s", arrayOfObject));
				label208: for (int i = 0;; i++) {
					if (i >= localArrayList.size())
						;
					while (localCarData == null) {
						localCarData = (CarData) localArrayList.get(0);
						break;
						if (!((CarData) localArrayList.get(i)).VehicleID
								.equals(str))
							break label208;
						localCarData = (CarData) localArrayList.get(i);
					}
				}
				label214: paramAppWidgetManager.updateAppWidget(paramInt,
						localRemoteViews);
			}
		}
	}

	public static void UpdateWidgets(Context paramContext) {
		try {
			Log.d("OVMS",
					"Loading saved cars from internal storage file: OVMSSavedCars.obj");
			ObjectInputStream localObjectInputStream = new ObjectInputStream(
					paramContext.openFileInput("OVMSSavedCars.obj"));
			localArrayList = (ArrayList) localObjectInputStream.readObject();
			localObjectInputStream.close();
			localCarData = null;
			str = paramContext.getSharedPreferences("OVMS", 0)
					.getString("LastVehicleID", "").trim();
			if (str.length() == 0) {
				localCarData = (CarData) localArrayList.get(0);
				localAppWidgetManager = AppWidgetManager
						.getInstance(paramContext);
				localComponentName = new ComponentName(paramContext,
						OVMSWidgetProvider.class);
				localRemoteViews = getUpdatedRemoteViews(paramContext,
						localCarData);
				if (localRemoteViews != null)
					break label229;
			}
		} catch (Exception localException) {
			while (true) {
				ArrayList localArrayList;
				CarData localCarData;
				String str;
				AppWidgetManager localAppWidgetManager;
				ComponentName localComponentName;
				RemoteViews localRemoteViews;
				localException.printStackTrace();
				continue;
				Object[] arrayOfObject = new Object[2];
				arrayOfObject[0] = Integer.valueOf(localArrayList.size());
				arrayOfObject[1] = str;
				Log.d("OVMS", String.format(
						"Loaded %s cars. Last used car is %s", arrayOfObject));
				label223: for (int i = 0;; i++) {
					if (i >= localArrayList.size())
						;
					while (localCarData == null) {
						localCarData = (CarData) localArrayList.get(0);
						break;
						if (!((CarData) localArrayList.get(i)).VehicleID
								.equals(str))
							break label223;
						localCarData = (CarData) localArrayList.get(i);
					}
				}
				label229: localAppWidgetManager.updateAppWidget(
						localComponentName, localRemoteViews);
			}
		}
	}

	private static RemoteViews getUpdatedRemoteViews(Context paramContext, CarData paramCarData)
	{
		if (paramCarData == null)
		{
			localRemoteViews = null;
			return localRemoteViews;
		}
		RemoteViews localRemoteViews = new RemoteViews(paramContext.getPackageName(), 2130903062);
		Object[] arrayOfObject1 = new Object[1];
		arrayOfObject1[0] = Integer.valueOf(paramCarData.Data_EstimatedRange);
		localRemoteViews.setTextViewText(2131296412, String.format("%s - ", arrayOfObject1));
		Object[] arrayOfObject2 = new Object[1];
		arrayOfObject2[0] = Integer.valueOf(paramCarData.Data_IdealRange);
		localRemoteViews.setTextViewText(2131296413, String.format("%s", arrayOfObject2));
		String str;
		label95: int i;
		label162: int j;
		if (paramCarData.Data_DistanceUnit.equals("M"))
		{
			str = " mi";
			localRemoteViews.setTextViewText(2131296414, str);
			Object[] arrayOfObject3 = new Object[1];
			arrayOfObject3[0] = Integer.valueOf(paramCarData.Data_SOC);
			localRemoteViews.setTextViewText(2131296421, String.format("%d%%", arrayOfObject3));
			localRemoteViews.setTextViewText(2131296411, paramCarData.VehicleID);
			if ((paramCarData.Data_CarPoweredON) || (paramCarData.Data_ParkedTime_raw <= 0.0D))
				break label332;
			i = 0;
			localRemoteViews.setViewVisibility(2131296415, i);
			if (!paramCarData.Data_Charging)
				break label339;
			j = 0;
			label180: localRemoteViews.setViewVisibility(2131296419, j);
			localRemoteViews.setImageViewResource(2131296410, paramContext.getResources().getIdentifier(paramCarData.VehicleImageDrawable + "96x44", "drawable", "com.openvehicles.OVMS"));
			if (paramCarData.Data_SOC <= 0)
				break label346;
			localRemoteViews.setImageViewBitmap(2131296420, Utilities.GetScaledBatteryOverlay(paramCarData.Data_SOC, BitmapFactory.decodeResource(paramContext.getResources(), 2130837509)));
		}
		while (true)
		{
			if (paramCarData.Data_ParkedTime != null)
			{
				Date localDate = new Date();
				localRemoteViews.setChronometer(2131296416, SystemClock.elapsedRealtime() - (localDate.getTime() - paramCarData.Data_ParkedTime.getTime()), null, true);
			}
			localRemoteViews.setOnClickPendingIntent(2131296409, PendingIntent.getActivity(paramContext, 0, new Intent(paramContext, OVMSActivity.class), 0));
			break;
			str = " km";
			break label95;
			label332: i = 8;
			break label162;
			label339: j = 8;
			break label180;
			label346: localRemoteViews.setImageViewResource(2131296420, 2130837506);
		}
	}
}