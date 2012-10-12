// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import java.io.Serializable;
import java.util.Date;

public class GPRSUtilizationData
    implements Serializable
{

    public GPRSUtilizationData()
    {
        DataDate = null;
        Car_tx = 0L;
        Car_rx = 0L;
        App_tx = 0L;
        App_rx = 0L;
    }

    public GPRSUtilizationData(Date date, long l, long l1, long l2, 
            long l3)
    {
        DataDate = date;
        Car_tx = l1;
        Car_rx = l;
        App_tx = l3;
        App_rx = l2;
    }

    private static final long serialVersionUID = 0x74db1108L;
    public long App_rx;
    public long App_tx;
    public long Car_rx;
    public long Car_tx;
    public Date DataDate;
}
