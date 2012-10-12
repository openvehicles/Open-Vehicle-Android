// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.*;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;

// Referenced classes of package com.openvehicles.OVMS:
//            CarData, OVMSWidgetProvider, Utilities, OVMSActivity

public final class OVMSWidgets
{

    public OVMSWidgets()
    {
    }

    public static void UpdateWidget(Context context, AppWidgetManager appwidgetmanager, int i)
    {
        ArrayList arraylist;
        Log.d("OVMS", "Loading saved cars from internal storage file: OVMSSavedCars.obj");
        ObjectInputStream objectinputstream = new ObjectInputStream(context.openFileInput("OVMSSavedCars.obj"));
        arraylist = (ArrayList)objectinputstream.readObject();
        objectinputstream.close();
        CarData cardata;
        String s;
        cardata = null;
        s = context.getSharedPreferences("OVMS", 0).getString("LastVehicleID", "").trim();
        if(s.length() != 0) goto _L2; else goto _L1
_L1:
        cardata = (CarData)arraylist.get(0);
_L4:
        RemoteViews remoteviews = getUpdatedRemoteViews(context, cardata);
        Exception exception;
        Object aobj[];
        int j;
        if(remoteviews != null)
            appwidgetmanager.updateAppWidget(i, remoteviews);
_L3:
        return;
        exception;
        exception.printStackTrace();
        if(true) goto _L3; else goto _L2
_L2:
        aobj = new Object[2];
        aobj[0] = Integer.valueOf(arraylist.size());
        aobj[1] = s;
        Log.d("OVMS", String.format("Loaded %s cars. Last used car is %s", aobj));
        j = 0;
_L5:
        if(j < arraylist.size())
        {
label0:
            {
                if(!((CarData)arraylist.get(j)).VehicleID.equals(s))
                    break label0;
                cardata = (CarData)arraylist.get(j);
            }
        }
        if(cardata == null)
            cardata = (CarData)arraylist.get(0);
          goto _L4
        j++;
          goto _L5
    }

    public static void UpdateWidgets(Context context)
    {
        ArrayList arraylist;
        Log.d("OVMS", "Loading saved cars from internal storage file: OVMSSavedCars.obj");
        ObjectInputStream objectinputstream = new ObjectInputStream(context.openFileInput("OVMSSavedCars.obj"));
        arraylist = (ArrayList)objectinputstream.readObject();
        objectinputstream.close();
        CarData cardata;
        String s;
        cardata = null;
        s = context.getSharedPreferences("OVMS", 0).getString("LastVehicleID", "").trim();
        if(s.length() != 0) goto _L2; else goto _L1
_L1:
        cardata = (CarData)arraylist.get(0);
_L4:
        AppWidgetManager appwidgetmanager = AppWidgetManager.getInstance(context);
        ComponentName componentname = new ComponentName(context, com/openvehicles/OVMS/OVMSWidgetProvider);
        RemoteViews remoteviews = getUpdatedRemoteViews(context, cardata);
        Exception exception;
        Object aobj[];
        int i;
        if(remoteviews != null)
            appwidgetmanager.updateAppWidget(componentname, remoteviews);
_L3:
        return;
        exception;
        exception.printStackTrace();
        if(true) goto _L3; else goto _L2
_L2:
        aobj = new Object[2];
        aobj[0] = Integer.valueOf(arraylist.size());
        aobj[1] = s;
        Log.d("OVMS", String.format("Loaded %s cars. Last used car is %s", aobj));
        i = 0;
_L5:
        if(i < arraylist.size())
        {
label0:
            {
                if(!((CarData)arraylist.get(i)).VehicleID.equals(s))
                    break label0;
                cardata = (CarData)arraylist.get(i);
            }
        }
        if(cardata == null)
            cardata = (CarData)arraylist.get(0);
          goto _L4
        i++;
          goto _L5
    }

    private static RemoteViews getUpdatedRemoteViews(Context context, CarData cardata)
    {
        RemoteViews remoteviews;
        if(cardata == null)
        {
            remoteviews = null;
        } else
        {
            remoteviews = new RemoteViews(context.getPackageName(), 0x7f030016);
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(cardata.Data_EstimatedRange);
            remoteviews.setTextViewText(0x7f09009c, String.format("%s - ", aobj));
            Object aobj1[] = new Object[1];
            aobj1[0] = Integer.valueOf(cardata.Data_IdealRange);
            remoteviews.setTextViewText(0x7f09009d, String.format("%s", aobj1));
            String s;
            Object aobj2[];
            int i;
            int j;
            if(cardata.Data_DistanceUnit.equals("M"))
                s = " mi";
            else
                s = " km";
            remoteviews.setTextViewText(0x7f09009e, s);
            aobj2 = new Object[1];
            aobj2[0] = Integer.valueOf(cardata.Data_SOC);
            remoteviews.setTextViewText(0x7f0900a5, String.format("%d%%", aobj2));
            remoteviews.setTextViewText(0x7f09009b, cardata.VehicleID);
            if(!cardata.Data_CarPoweredON && cardata.Data_ParkedTime_raw > 0.0D)
                i = 0;
            else
                i = 8;
            remoteviews.setViewVisibility(0x7f09009f, i);
            if(cardata.Data_Charging)
                j = 0;
            else
                j = 8;
            remoteviews.setViewVisibility(0x7f0900a3, j);
            remoteviews.setImageViewResource(0x7f09009a, context.getResources().getIdentifier((new StringBuilder(String.valueOf(cardata.VehicleImageDrawable))).append("96x44").toString(), "drawable", "com.openvehicles.OVMS"));
            if(cardata.Data_SOC > 0)
                remoteviews.setImageViewBitmap(0x7f0900a4, Utilities.GetScaledBatteryOverlay(cardata.Data_SOC, BitmapFactory.decodeResource(context.getResources(), 0x7f020005)));
            else
                remoteviews.setImageViewResource(0x7f0900a4, 0x7f020002);
            if(cardata.Data_ParkedTime != null)
            {
                Date date = new Date();
                remoteviews.setChronometer(0x7f0900a0, SystemClock.elapsedRealtime() - (date.getTime() - cardata.Data_ParkedTime.getTime()), null, true);
            }
            remoteviews.setOnClickPendingIntent(0x7f090099, PendingIntent.getActivity(context, 0, new Intent(context, com/openvehicles/OVMS/OVMSActivity), 0));
        }
        return remoteviews;
    }
}
