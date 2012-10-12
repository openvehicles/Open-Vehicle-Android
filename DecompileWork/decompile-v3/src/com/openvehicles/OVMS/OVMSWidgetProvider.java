// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import java.util.Arrays;

// Referenced classes of package com.openvehicles.OVMS:
//            OVMSWidgets

public class OVMSWidgetProvider extends AppWidgetProvider
{

    public OVMSWidgetProvider()
    {
    }

    public void onUpdate(Context context, AppWidgetManager appwidgetmanager, int ai[])
    {
        int i = ai.length;
        StringBuilder stringbuilder = new StringBuilder("Updating widgets ");
        int ai1[][] = new int[1][];
        ai1[0] = ai;
        Log.i("OVMSWidget", stringbuilder.append(Arrays.asList(ai1)).toString());
        int j = 0;
        do
        {
            if(j >= i)
                return;
            OVMSWidgets.UpdateWidget(context, appwidgetmanager, ai[j]);
            j++;
        } while(true);
    }
}
