// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.renderer;

import android.graphics.Color;
import org.achartengine.chart.PointStyle;

// Referenced classes of package org.achartengine.renderer:
//            SimpleSeriesRenderer

public class XYSeriesRenderer extends SimpleSeriesRenderer
{

    public XYSeriesRenderer()
    {
        mFillPoints = false;
        mFillBelowLine = false;
        mFillColor = Color.argb(125, 0, 0, 200);
        mPointStyle = PointStyle.POINT;
        mLineWidth = 1.0F;
    }

    public int getFillBelowLineColor()
    {
        return mFillColor;
    }

    public float getLineWidth()
    {
        return mLineWidth;
    }

    public PointStyle getPointStyle()
    {
        return mPointStyle;
    }

    public boolean isFillBelowLine()
    {
        return mFillBelowLine;
    }

    public boolean isFillPoints()
    {
        return mFillPoints;
    }

    public void setFillBelowLine(boolean flag)
    {
        mFillBelowLine = flag;
    }

    public void setFillBelowLineColor(int i)
    {
        mFillColor = i;
    }

    public void setFillPoints(boolean flag)
    {
        mFillPoints = flag;
    }

    public void setLineWidth(float f)
    {
        mLineWidth = f;
    }

    public void setPointStyle(PointStyle pointstyle)
    {
        mPointStyle = pointstyle;
    }

    private boolean mFillBelowLine;
    private int mFillColor;
    private boolean mFillPoints;
    private float mLineWidth;
    private PointStyle mPointStyle;
}
