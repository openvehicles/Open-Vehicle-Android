// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.model;

import java.util.Date;

// Referenced classes of package org.achartengine.model:
//            XYSeries

public class TimeSeries extends XYSeries
{

    public TimeSeries(String s)
    {
        super(s);
    }

    /**
     * @deprecated Method add is deprecated
     */

    public void add(Date date, double d)
    {
        this;
        JVM INSTR monitorenter ;
        super.add(date.getTime(), d);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }
}
