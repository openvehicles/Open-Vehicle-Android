// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.model;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package org.achartengine.model:
//            XYSeries

public class XYValueSeries extends XYSeries
{

    public XYValueSeries(String s)
    {
        super(s);
        mValue = new ArrayList();
        mMinValue = 1.7976931348623157E+308D;
        mMaxValue = -1.7976931348623157E+308D;
    }

    private void initRange()
    {
        mMinValue = 1.7976931348623157E+308D;
        mMaxValue = 1.7976931348623157E+308D;
        int i = getItemCount();
        for(int j = 0; j < i; j++)
            updateRange(getValue(j));

    }

    private void updateRange(double d)
    {
        mMinValue = Math.min(mMinValue, d);
        mMaxValue = Math.max(mMaxValue, d);
    }

    /**
     * @deprecated Method add is deprecated
     */

    public void add(double d, double d1)
    {
        this;
        JVM INSTR monitorenter ;
        add(d, d1, 0.0D);
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

    public void add(double d, double d1, double d2)
    {
        this;
        JVM INSTR monitorenter ;
        super.add(d, d1);
        mValue.add(Double.valueOf(d2));
        updateRange(d2);
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
        mValue.clear();
        initRange();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public double getMaxValue()
    {
        return mMaxValue;
    }

    public double getMinValue()
    {
        return mMinValue;
    }

    /**
     * @deprecated Method getValue is deprecated
     */

    public double getValue(int i)
    {
        this;
        JVM INSTR monitorenter ;
        double d = ((Double)mValue.get(i)).doubleValue();
        this;
        JVM INSTR monitorexit ;
        return d;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method remove is deprecated
     */

    public void remove(int i)
    {
        this;
        JVM INSTR monitorenter ;
        super.remove(i);
        double d = ((Double)mValue.remove(i)).doubleValue();
        if(d == mMinValue || d == mMaxValue)
            initRange();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private double mMaxValue;
    private double mMinValue;
    private List mValue;
}
