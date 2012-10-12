// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.model;

import java.io.Serializable;
import java.util.*;

// Referenced classes of package org.achartengine.model:
//            XYSeries

public class CategorySeries
    implements Serializable
{

    public CategorySeries(String s)
    {
        mCategories = new ArrayList();
        mValues = new ArrayList();
        mTitle = s;
    }

    /**
     * @deprecated Method add is deprecated
     */

    public void add(double d)
    {
        this;
        JVM INSTR monitorenter ;
        add((new StringBuilder()).append(mCategories.size()).append("").toString(), d);
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

    public void add(String s, double d)
    {
        this;
        JVM INSTR monitorenter ;
        mCategories.add(s);
        mValues.add(Double.valueOf(d));
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
        mCategories.clear();
        mValues.clear();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method getCategory is deprecated
     */

    public String getCategory(int i)
    {
        this;
        JVM INSTR monitorenter ;
        String s = (String)mCategories.get(i);
        this;
        JVM INSTR monitorexit ;
        return s;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method getItemCount is deprecated
     */

    public int getItemCount()
    {
        this;
        JVM INSTR monitorenter ;
        int i = mCategories.size();
        this;
        JVM INSTR monitorexit ;
        return i;
        Exception exception;
        exception;
        throw exception;
    }

    public String getTitle()
    {
        return mTitle;
    }

    /**
     * @deprecated Method getValue is deprecated
     */

    public double getValue(int i)
    {
        this;
        JVM INSTR monitorenter ;
        double d = ((Double)mValues.get(i)).doubleValue();
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
        mCategories.remove(i);
        mValues.remove(i);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    /**
     * @deprecated Method set is deprecated
     */

    public void set(int i, String s, double d)
    {
        this;
        JVM INSTR monitorenter ;
        mCategories.set(i, s);
        mValues.set(i, Double.valueOf(d));
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public XYSeries toXYSeries()
    {
        XYSeries xyseries = new XYSeries(mTitle);
        Iterator iterator = mValues.iterator();
        int j;
        for(int i = 0; iterator.hasNext(); i = j)
        {
            double d = ((Double)iterator.next()).doubleValue();
            j = i + 1;
            xyseries.add(j, d);
        }

        return xyseries;
    }

    private List mCategories;
    private String mTitle;
    private List mValues;
}
