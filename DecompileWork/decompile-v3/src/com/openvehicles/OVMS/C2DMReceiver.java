// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.*;
import android.content.*;
import android.content.res.Resources;
import android.util.Log;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.openvehicles.OVMS:
//            OVMSNotifications, OVMSActivity, CarData

public class C2DMReceiver extends BroadcastReceiver
{

    public C2DMReceiver()
    {
    }

    private void handleMessage(Context context, Intent intent)
    {
        if(intent.hasExtra("title") && intent.hasExtra("message")) goto _L2; else goto _L1
_L1:
        Log.d("ERR", "An invalid C2DM message was received.");
_L6:
        return;
_L2:
        String s;
        int i;
        Iterator iterator;
        Log.d("C2DM", "C2DM Message Received");
        ArrayList arraylist = null;
        NotificationManager notificationmanager;
        String s1;
        OVMSNotifications ovmsnotifications;
        Notification notification;
        Intent intent1;
        try
        {
            Log.d("OVMS", "Loading saved cars from internal storage file: OVMSSavedCars.obj");
            ObjectInputStream objectinputstream = new ObjectInputStream(context.openFileInput("OVMSSavedCars.obj"));
            arraylist = (ArrayList)objectinputstream.readObject();
            objectinputstream.close();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        notificationmanager = (NotificationManager)context.getSystemService("notification");
        s = intent.getStringExtra("title");
        s1 = intent.getStringExtra("message");
        i = 0x1080077;
        ovmsnotifications = new OVMSNotifications(context);
        ovmsnotifications.AddNotification(s.toString(), s1.toString());
        ovmsnotifications.Save();
        if(arraylist == null) goto _L4; else goto _L3
_L3:
        iterator = arraylist.iterator();
_L8:
        if(iterator.hasNext()) goto _L5; else goto _L4
_L4:
        notification = new Notification(i, s1, System.currentTimeMillis());
        notification.flags = 16;
        notification.defaults = 7;
        intent1 = new Intent(context, com/openvehicles/OVMS/OVMSActivity);
        intent1.putExtra("SetTab", "tabInfo");
        intent1.setAction("com.openvehicles.OVMS.NOTIFICATIONS_CLICK");
        intent1.putExtra("VehicleID", s);
        intent1.setFlags(0x24000000);
        notification.setLatestEventInfo(context, s, s1, PendingIntent.getActivity(context, 0, intent1, 0x8000000));
        notificationmanager.notify(1, notification);
          goto _L6
_L5:
        CarData cardata = (CarData)iterator.next();
        if(!cardata.VehicleID.equals(s)) goto _L8; else goto _L7
_L7:
        i = context.getResources().getIdentifier((new StringBuilder(String.valueOf(cardata.VehicleImageDrawable))).append("32x32").toString(), "drawable", "com.openvehicles.OVMS");
          goto _L4
    }

    private void handleRegistration(Context context, Intent intent)
    {
        String s = intent.getStringExtra("registration_id");
        if(intent.getStringExtra("error") == null) goto _L2; else goto _L1
_L1:
        String s1;
        Log.d("C2DM", "registration failed");
        s1 = intent.getStringExtra("error");
        if(s1 != "SERVICE_NOT_AVAILABLE") goto _L4; else goto _L3
_L3:
        Log.d("C2DM", "SERVICE_NOT_AVAILABLE");
_L6:
        return;
_L4:
        if(s1 == "ACCOUNT_MISSING")
            Log.d("C2DM", "ACCOUNT_MISSING");
        else
        if(s1 == "AUTHENTICATION_FAILED")
            Log.d("C2DM", "AUTHENTICATION_FAILED");
        else
        if(s1 == "TOO_MANY_REGISTRATIONS")
            Log.d("C2DM", "TOO_MANY_REGISTRATIONS");
        else
        if(s1 == "INVALID_SENDER")
            Log.d("C2DM", "INVALID_SENDER");
        else
        if(s1 == "PHONE_REGISTRATION_ERROR")
            Log.d("C2DM", "PHONE_REGISTRATION_ERROR");
        continue; /* Loop/switch isn't completed */
_L2:
        if(intent.getStringExtra("unregistered") != null)
            Log.d("C2DM", "unregistered");
        else
        if(s != null)
        {
            Log.d("C2DM", (new StringBuilder("New C2DM ID: ")).append(s).toString());
            android.content.SharedPreferences.Editor editor = context.getSharedPreferences("C2DM", 0).edit();
            editor.putString("RegID", s);
            editor.commit();
        }
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void onReceive(Context context, Intent intent)
    {
        if(!intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) goto _L2; else goto _L1
_L1:
        handleRegistration(context, intent);
_L4:
        return;
_L2:
        if(intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE"))
            handleMessage(context, intent);
        if(true) goto _L4; else goto _L3
_L3:
    }
}
