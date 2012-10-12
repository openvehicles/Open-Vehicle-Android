// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.renderer;

import java.io.Serializable;

// Referenced classes of package org.achartengine.renderer:
//            BasicStroke

public class SimpleSeriesRenderer
    implements Serializable
{

    public SimpleSeriesRenderer()
    {
        mColor = 0xff0000ff;
        mChartValuesTextSize = 10F;
        mChartValuesTextAlign = android.graphics.Paint.Align.CENTER;
        mChartValuesSpacing = 5F;
        mGradientEnabled = false;
    }

    public float getChartValuesSpacing()
    {
        return mChartValuesSpacing;
    }

    public android.graphics.Paint.Align getChartValuesTextAlign()
    {
        return mChartValuesTextAlign;
    }

    public float getChartValuesTextSize()
    {
        return mChartValuesTextSize;
    }

    public int getColor()
    {
        return mColor;
    }

    public int getGradientStartColor()
    {
        return mGradientStartColor;
    }

    public double getGradientStartValue()
    {
        return mGradientStartValue;
    }

    public int getGradientStopColor()
    {
        return mGradientStopColor;
    }

    public double getGradientStopValue()
    {
        return mGradientStopValue;
    }

    public BasicStroke getStroke()
    {
        return mStroke;
    }

    public boolean isDisplayChartValues()
    {
        return mDisplayChartValues;
    }

    public boolean isGradientEnabled()
    {
        return mGradientEnabled;
    }

    public void setChartValuesSpacing(float f)
    {
        mChartValuesSpacing = f;
    }

    public void setChartValuesTextAlign(android.graphics.Paint.Align align)
    {
        mChartValuesTextAlign = align;
    }

    public void setChartValuesTextSize(float f)
    {
        mChartValuesTextSize = f;
    }

    public void setColor(int i)
    {
        mColor = i;
    }

    public void setDisplayChartValues(boolean flag)
    {
        mDisplayChartValues = flag;
    }

    public void setGradientEnabled(boolean flag)
    {
        mGradientEnabled = flag;
    }

    public void setGradientStart(double d, int i)
    {
        mGradientStartValue = d;
        mGradientStartColor = i;
    }

    public void setGradientStop(double d, int i)
    {
        mGradientStopValue = d;
        mGradientStopColor = i;
    }

    public void setStroke(BasicStroke basicstroke)
    {
        mStroke = basicstroke;
    }

    private float mChartValuesSpacing;
    private android.graphics.Paint.Align mChartValuesTextAlign;
    private float mChartValuesTextSize;
    private int mColor;
    private boolean mDisplayChartValues;
    private boolean mGradientEnabled;
    private int mGradientStartColor;
    private double mGradientStartValue;
    private int mGradientStopColor;
    private double mGradientStopValue;
    private BasicStroke mStroke;
}
