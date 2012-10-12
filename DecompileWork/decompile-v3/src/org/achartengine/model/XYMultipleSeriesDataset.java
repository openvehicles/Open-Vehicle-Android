// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package org.achartengine.model:
//            XYSeries

public class XYMultipleSeriesDataset
    implements Serializable
{

    public XYMultipleSeriesDataset()
    {
        mSeries = new ArrayList();
    }

    /**
     * @deprecated Method addSeries is deprecated
     */

    public void addSeries(int i, XYSeries xyseries)
    {
        this;
        JVM INSTR monitorenter ;
        mSeries.add(i, xyseries);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method addSeries is deprecated
     */

    public void addSeries(XYSeries xyseries)
    {
        this;
        JVM INSTR monitorenter ;
        mSeries.add(xyseries);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method getSeries is deprecated
     */

    public XYSeries[] getSeries()
    {
        this;
        JVM INSTR monitorenter ;
        XYSeries axyseries[] = (XYSeries[])mSeries.toArray(new XYSeries[0]);
        this;
        JVM INSTR monitorexit ;
        return axyseries;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method getSeriesAt is deprecated
     */

    public XYSeries getSeriesAt(int i)
    {
        this;
        JVM INSTR monitorenter ;
        XYSeries xyseries = (XYSeries)mSeries.get(i);
        this;
        JVM INSTR monitorexit ;
        return xyseries;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method getSeriesCount is deprecated
     */

    public int getSeriesCount()
    {
        this;
        JVM INSTR monitorenter ;
        int i = mSeries.size();
        this;
        JVM INSTR monitorexit ;
        return i;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method removeSeries is deprecated
     */

    public void removeSeries(int i)
    {
        this;
        JVM INSTR monitorenter ;
        mSeries.remove(i);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method removeSeries is deprecated
     */

    public void removeSeries(XYSeries xyseries)
    {
        this;
        JVM INSTR monitorenter ;
        mSeries.remove(xyseries);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private List mSeries;
}
