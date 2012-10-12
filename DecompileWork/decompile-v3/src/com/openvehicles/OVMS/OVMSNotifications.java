// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.content.Context;
import android.util.Log;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

// Referenced classes of package com.openvehicles.OVMS:
//            NotificationData

public class OVMSNotifications
{

    public OVMSNotifications(Context context)
    {
        this(context, null);
    }

    public OVMSNotifications(Context context, String s)
    {
        settingsFileName = "OVMSSavedNotifications.obj";
        mContext = context;
        Log.d("OVMS", "Loading saved notifications list from internal storage file: OVMSSavedNotifications.obj");
        ObjectInputStream objectinputstream = new ObjectInputStream(context.openFileInput("OVMSSavedNotifications.obj"));
        Notifications = (ArrayList)objectinputstream.readObject();
        objectinputstream.close();
        if(s == null) goto _L2; else goto _L1
_L1:
        int i = -1 + Notifications.size();
          goto _L3
_L2:
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(Notifications.size());
        Log.d("OVMS", String.format("Loaded %s saved notifications.", aobj));
          goto _L4
_L5:
        if(!((NotificationData)Notifications.get(i)).Title.equals(s))
            Notifications.remove(i);
        i--;
          goto _L3
        Exception exception;
        exception;
        Log.d("ERR", exception.getMessage());
        Log.d("OVMS", "Initializing with save notifications list.");
        Notifications = new ArrayList();
        AddNotification("Push Notifications", "Push notifications received for your registered vehicles are archived here.");
        Save();
          goto _L4
_L3:
        if(i >= 0) goto _L5; else goto _L2
_L4:
    }

    public void AddNotification(NotificationData notificationdata)
    {
        Notifications.add(notificationdata);
    }

    public void AddNotification(String s, String s1)
    {
        Date date = new Date();
        Notifications.add(new NotificationData(date, s, s1));
    }

    public void Clear()
    {
        Notifications = new ArrayList();
    }

    public void Save()
    {
        Log.d("OVMS", "Saving notifications list to interal storage...");
        ObjectOutputStream objectoutputstream = new ObjectOutputStream(mContext.openFileOutput("OVMSSavedNotifications.obj", 0));
        objectoutputstream.writeObject(Notifications);
        objectoutputstream.close();
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(Notifications.size());
        Log.d("OVMS", String.format("Saved %s notifications.", aobj));
_L1:
        return;
        Exception exception;
        exception;
        exception.printStackTrace();
        Log.d("ERR", exception.getMessage());
          goto _L1
    }

    public ArrayList Notifications;
    private Context mContext;
    private final String settingsFileName;
}
