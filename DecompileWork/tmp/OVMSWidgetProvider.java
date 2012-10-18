package com.openvehicles.OVMS;

import java.util.Arrays;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;

public class OVMSWidgetProvider extends AppWidgetProvider {
	public void onUpdate(Context paramContext,
			AppWidgetManager paramAppWidgetManager, int[] paramArrayOfInt) {
		int i = paramArrayOfInt.length;
		StringBuilder localStringBuilder = new StringBuilder(
				"Updating widgets ");
		int[][] arrayOfInt = new int[1][];
		arrayOfInt[0] = paramArrayOfInt;
		Log.i("OVMSWidget", Arrays.asList(arrayOfInt));
		for (int j = 0;; j++) {
			if (j >= i)
				return;
			OVMSWidgets.UpdateWidget(paramContext, paramAppWidgetManager,
					paramArrayOfInt[j]);
		}
	}
}