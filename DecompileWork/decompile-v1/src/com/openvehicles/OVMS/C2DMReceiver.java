// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.*;
import android.content.*;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.openvehicles.OVMS:
//            OVMSNotifications, CarData, OVMSActivity

public class C2DMReceiver extends BroadcastReceiver
{

    public C2DMReceiver()
    {
    }

    private void handleMessage(Context context1, Intent intent)
    {
        if(intent.hasExtra("title") && intent.hasExtra("message")) goto _L2; else goto _L1
_L1:
        Log.d("ERR", "An invalid C2DM message was received.");
_L4:
        return;
_L2:
label0:
        {
            ArrayList arraylist = null;
            NotificationManager notificationmanager;
            String s;
            String s1;
            int i;
            OVMSNotifications ovmsnotifications;
            Notification notification;
            Intent intent1;
            Iterator iterator;
            CarData cardata;
            try
            {
                Log.d("OVMS", "Loading saved cars from internal storage file: OVMSSavedCars.obj");
                ObjectInputStream objectinputstream = new ObjectInputStream(context1.openFileInput("OVMSSavedCars.obj"));
                arraylist = (ArrayList)objectinputstream.readObject();
                objectinputstream.close();
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
            notificationmanager = (NotificationManager)context1.getSystemService("notification");
            s = intent.getStringExtra("title");
            s1 = intent.getStringExtra("message");
            i = 0x108002e;
            ovmsnotifications = new OVMSNotifications(context1);
            ovmsnotifications.AddNotification(s.toString(), s1.toString());
            ovmsnotifications.Save();
            if(arraylist == null)
                break label0;
            iterator = arraylist.iterator();
            do
            {
                if(!iterator.hasNext())
                    break label0;
                cardata = (CarData)iterator.next();
            } while(!cardata.VehicleID.equals(s));
            i = context1.getResources().getIdentifier((new StringBuilder()).append(cardata.VehicleImageDrawable).append("32x32").toString(), "drawable", "com.openvehicles.OVMS");
        }
        notification = new Notification(i, s1, System.currentTimeMillis());
        notification.flags = 16;
        notification.defaults = 7;
        intent1 = new Intent(context1, com/openvehicles/OVMS/OVMSActivity);
        intent1.putExtra("SetTab", "tabNotifications");
        notification.setLatestEventInfo(context1, s, s1, PendingIntent.getActivity(context1, 0, intent1, 0));
        notificationmanager.notify(1, notification);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void handleRegistration(Context context1, Intent intent)
    {
        String s = intent.getStringExtra("registration_id");
        if(intent.getStringExtra("error") == null) goto _L2; else goto _L1
_L1:
        String s1;
        Log.d("c2dm", "registration failed");
        s1 = intent.getStringExtra("error");
        if(s1 != "SERVICE_NOT_AVAILABLE") goto _L4; else goto _L3
_L3:
        Log.d("c2dm", "SERVICE_NOT_AVAILABLE");
_L6:
        return;
_L4:
        if(s1 == "ACCOUNT_MISSING")
            Log.d("c2dm", "ACCOUNT_MISSING");
        else
        if(s1 == "AUTHENTICATION_FAILED")
            Log.d("c2dm", "AUTHENTICATION_FAILED");
        else
        if(s1 == "TOO_MANY_REGISTRATIONS")
            Log.d("c2dm", "TOO_MANY_REGISTRATIONS");
        else
        if(s1 == "INVALID_SENDER")
            Log.d("c2dm", "INVALID_SENDER");
        else
        if(s1 == "PHONE_REGISTRATION_ERROR")
            Log.d("c2dm", "PHONE_REGISTRATION_ERROR");
        continue; /* Loop/switch isn't completed */
_L2:
        if(intent.getStringExtra("unregistered") != null)
            Log.d("c2dm", "unregistered");
        else
        if(s != null)
        {
            Log.d("c2dm", s);
            android.content.SharedPreferences.Editor editor = context1.getSharedPreferences(KEY, 0).edit();
            editor.putString(REGISTRATION_KEY, s);
            editor.commit();
            Toast.makeText(context1, "Push Notification Registered", 2000);
        }
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void onReceive(Context context1, Intent intent)
    {
        context = context1;
        if(!intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) goto _L2; else goto _L1
_L1:
        handleRegistration(context1, intent);
_L4:
        return;
_L2:
        if(intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE"))
            handleMessage(context1, intent);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static String KEY = "C2DM";
    private static String REGISTRATION_KEY = "RegID";
    private Context context;

}
