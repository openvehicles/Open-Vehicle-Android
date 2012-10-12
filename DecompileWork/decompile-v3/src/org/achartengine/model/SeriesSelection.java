// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.model;


public class SeriesSelection
{

    public SeriesSelection(int i, int j, double d, double d1)
    {
        mSeriesIndex = i;
        mPointIndex = j;
        mXValue = d;
        mValue = d1;
    }

    public int getPointIndex()
    {
        return mPointIndex;
    }

    public int getSeriesIndex()
    {
        return mSeriesIndex;
    }

    public double getValue()
    {
        return mValue;
    }

    public double getXValue()
    {
        return mXValue;
    }

    private int mPointIndex;
    private int mSeriesIndex;
    private double mValue;
    private double mXValue;
}
