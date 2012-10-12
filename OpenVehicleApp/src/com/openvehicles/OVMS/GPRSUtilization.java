// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.content.Context;
import android.util.Log;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

// Referenced classes of package com.openvehicles.OVMS:
//            GPRSUtilizationData

public class GPRSUtilization
    implements Serializable
{

    public GPRSUtilization(Context context)
    {
        settingsFileName = "OVMSSavedGPRSUtilization.obj";
        LastDataRefresh = null;
        mContext = context;
        Log.d("OVMS", "Loading saved GPRS utilizations from internal storage file: OVMSSavedGPRSUtilization.obj");
        ObjectInputStream objectinputstream = new ObjectInputStream(context.openFileInput("OVMSSavedGPRSUtilization.obj"));
        Utilizations = (ArrayList)objectinputstream.readObject();
        objectinputstream.close();
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(Utilizations.size());
        Log.d("OVMS", String.format("Loaded %s saved utilizations.", aobj));
_L1:
        return;
        Exception exception;
        exception;
        Log.d("ERR", exception.getMessage());
        Log.d("OVMS", "Initializing with utilization data.");
        Utilizations = new ArrayList();
        Save();
          goto _L1
    }

    public void AddData(GPRSUtilizationData gprsutilizationdata)
    {
        Utilizations.add(gprsutilizationdata);
    }

    public void AddData(Date date, long l, long l1, long l2, 
            long l3)
    {
        AddData(new GPRSUtilizationData(date, l, l1, l2, l3));
    }

    public void Clear()
    {
        Utilizations = new ArrayList();
    }

    public long GetUtilizationBytes(Date date, int i)
    {
        long l = 0L;
        int j = 0;
        do
        {
            if(j >= Utilizations.size())
                return l;
            GPRSUtilizationData gprsutilizationdata = (GPRSUtilizationData)Utilizations.get(j);
            if(gprsutilizationdata.DataDate.after(date) || gprsutilizationdata.DataDate.equals(date))
            {
                if((i & 1) == 1)
                    l += gprsutilizationdata.Car_rx;
                if((i & 2) == 2)
                    l += gprsutilizationdata.Car_tx;
                if((i & 4) == 4)
                    l += gprsutilizationdata.App_rx;
                if((i & 8) == 8)
                    l += gprsutilizationdata.App_tx;
            }
            j++;
        } while(true);
    }

    public void Save()
    {
        try
        {
            Log.d("OVMS", "Saving GPRS utilizations data to interal storage...");
            if(mContext == null)
            {
                Log.d("OVMS", "Context == null. Saving aborted.");
            } else
            {
                ObjectOutputStream objectoutputstream = new ObjectOutputStream(mContext.openFileOutput("OVMSSavedGPRSUtilization.obj", 0));
                objectoutputstream.writeObject(Utilizations);
                objectoutputstream.close();
                Object aobj[] = new Object[1];
                aobj[0] = Integer.valueOf(Utilizations.size());
                Log.d("OVMS", String.format("Saved %s records.", aobj));
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            Log.d("ERR", exception.getMessage());
        }
    }

    public void Save(Context context)
    {
        mContext = context;
        Save();
    }

    public static final transient int FLAG_APP_RX = 4;
    public static final transient int FLAG_APP_TX = 8;
    public static final transient int FLAG_CAR_RX = 1;
    public static final transient int FLAG_CAR_TX = 2;
    private static final long serialVersionUID = 0x651bbc51L;
    public Date LastDataRefresh;
    public ArrayList Utilizations;
    private transient Context mContext;
    private final String settingsFileName;
}
