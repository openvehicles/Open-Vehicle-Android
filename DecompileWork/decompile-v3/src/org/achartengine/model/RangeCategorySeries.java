// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.model;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package org.achartengine.model:
//            CategorySeries, XYSeries

public class RangeCategorySeries extends CategorySeries
{

    public RangeCategorySeries(String s)
    {
        super(s);
        mMaxValues = new ArrayList();
    }

    /**
     * @deprecated Method add is deprecated
     */

    public void add(double d, double d1)
    {
        this;
        JVM INSTR monitorenter ;
        super.add(d);
        mMaxValues.add(Double.valueOf(d1));
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method add is deprecated
     */

    public void add(String s, double d, double d1)
    {
        this;
        JVM INSTR monitorenter ;
        super.add(s, d);
        mMaxValues.add(Double.valueOf(d1));
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method clear is deprecated
     */

    public void clear()
    {
        this;
        JVM INSTR monitorenter ;
        super.clear();
        mMaxValues.clear();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public double getMaximumValue(int i)
    {
        return ((Double)mMaxValues.get(i)).doubleValue();
    }

    public double getMinimumValue(int i)
    {
        return getValue(i);
    }

    /**
     * @deprecated Method remove is deprecated
     */

    public void remove(int i)
    {
        this;
        JVM INSTR monitorenter ;
        super.remove(i);
        mMaxValues.remove(i);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public XYSeries toXYSeries()
    {
        XYSeries xyseries = new XYSeries(getTitle());
        int i = getItemCount();
        for(int j = 0; j < i; j++)
        {
            xyseries.add(j + 1, getMinimumValue(j));
            xyseries.add(j + 1, getMaximumValue(j));
        }

        return xyseries;
    }

    private List mMaxValues;
}
