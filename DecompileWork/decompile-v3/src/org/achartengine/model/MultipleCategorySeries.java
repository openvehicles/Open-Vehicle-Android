// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package org.achartengine.model:
//            XYSeries

public class MultipleCategorySeries
    implements Serializable
{

    public MultipleCategorySeries(String s)
    {
        mCategories = new ArrayList();
        mTitles = new ArrayList();
        mValues = new ArrayList();
        mTitle = s;
    }

    public void add(String s, String as[], double ad[])
    {
        mCategories.add(s);
        mTitles.add(as);
        mValues.add(ad);
    }

    public void add(String as[], double ad[])
    {
        add((new StringBuilder()).append(mCategories.size()).append("").toString(), as, ad);
    }

    public void clear()
    {
        mCategories.clear();
        mTitles.clear();
        mValues.clear();
    }

    public int getCategoriesCount()
    {
        return mCategories.size();
    }

    public String getCategory(int i)
    {
        return (String)mCategories.get(i);
    }

    public int getItemCount(int i)
    {
        return ((double[])mValues.get(i)).length;
    }

    public String[] getTitles(int i)
    {
        return (String[])mTitles.get(i);
    }

    public double[] getValues(int i)
    {
        return (double[])mValues.get(i);
    }

    public void remove(int i)
    {
        mCategories.remove(i);
        mTitles.remove(i);
        mValues.remove(i);
    }

    public XYSeries toXYSeries()
    {
        return new XYSeries(mTitle);
    }

    private List mCategories;
    private String mTitle;
    private List mTitles;
    private List mValues;
}
