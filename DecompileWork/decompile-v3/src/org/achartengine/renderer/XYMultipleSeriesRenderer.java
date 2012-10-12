// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.renderer;

import android.graphics.Color;
import java.util.*;

// Referenced classes of package org.achartengine.renderer:
//            DefaultRenderer, SimpleSeriesRenderer

public class XYMultipleSeriesRenderer extends DefaultRenderer
{
    public static final class Orientation extends Enum
    {

        public static Orientation valueOf(String s)
        {
            return (Orientation)Enum.valueOf(org/achartengine/renderer/XYMultipleSeriesRenderer$Orientation, s);
        }

        public static Orientation[] values()
        {
            return (Orientation[])$VALUES.clone();
        }

        public int getAngle()
        {
            return mAngle;
        }

        private static final Orientation $VALUES[];
        public static final Orientation HORIZONTAL;
        public static final Orientation VERTICAL;
        private int mAngle;

        static 
        {
            HORIZONTAL = new Orientation("HORIZONTAL", 0, 0);
            VERTICAL = new Orientation("VERTICAL", 1, 90);
            Orientation aorientation[] = new Orientation[2];
            aorientation[0] = HORIZONTAL;
            aorientation[1] = VERTICAL;
            $VALUES = aorientation;
        }

        private Orientation(String s, int i, int j)
        {
            super(s, i);
            mAngle = 0;
            mAngle = j;
        }
    }


    public XYMultipleSeriesRenderer()
    {
        this(1);
    }

    public XYMultipleSeriesRenderer(int i)
    {
        mXTitle = "";
        mAxisTitleTextSize = 12F;
        mXLabels = 5;
        mYLabels = 5;
        mOrientation = Orientation.HORIZONTAL;
        mXTextLabels = new HashMap();
        mYTextLabels = new LinkedHashMap();
        mPanXEnabled = true;
        mPanYEnabled = true;
        mZoomXEnabled = true;
        mZoomYEnabled = true;
        mBarSpacing = 0.0D;
        mMarginsColor = 0;
        initialRange = new LinkedHashMap();
        mPointSize = 3F;
        mGridColor = Color.argb(75, 200, 200, 200);
        xLabelsAlign = android.graphics.Paint.Align.CENTER;
        scalesCount = i;
        initAxesRange(i);
    }

    public void addTextLabel(double d, String s)
    {
        addXTextLabel(d, s);
    }

    public void addXTextLabel(double d, String s)
    {
        mXTextLabels.put(Double.valueOf(d), s);
    }

    public void addYTextLabel(double d, String s)
    {
        addYTextLabel(d, s, 0);
    }

    public void addYTextLabel(double d, String s, int i)
    {
        ((Map)mYTextLabels.get(Integer.valueOf(i))).put(Double.valueOf(d), s);
    }

    public void clearTextLabels()
    {
        clearXTextLabels();
    }

    public void clearXTextLabels()
    {
        mXTextLabels.clear();
    }

    public void clearYTextLabels()
    {
        mYTextLabels.clear();
    }

    public float getAxisTitleTextSize()
    {
        return mAxisTitleTextSize;
    }

    public double getBarSpacing()
    {
        return mBarSpacing;
    }

    public double getBarsSpacing()
    {
        return getBarSpacing();
    }

    public int getGridColor()
    {
        return mGridColor;
    }

    public double[] getInitialRange()
    {
        return getInitialRange(0);
    }

    public double[] getInitialRange(int i)
    {
        return (double[])initialRange.get(Integer.valueOf(i));
    }

    public int getMarginsColor()
    {
        return mMarginsColor;
    }

    public Orientation getOrientation()
    {
        return mOrientation;
    }

    public double[] getPanLimits()
    {
        return mPanLimits;
    }

    public float getPointSize()
    {
        return mPointSize;
    }

    public int getScalesCount()
    {
        return scalesCount;
    }

    public double getXAxisMax()
    {
        return getXAxisMax(0);
    }

    public double getXAxisMax(int i)
    {
        return mMaxX[i];
    }

    public double getXAxisMin()
    {
        return getXAxisMin(0);
    }

    public double getXAxisMin(int i)
    {
        return mMinX[i];
    }

    public int getXLabels()
    {
        return mXLabels;
    }

    public android.graphics.Paint.Align getXLabelsAlign()
    {
        return xLabelsAlign;
    }

    public float getXLabelsAngle()
    {
        return mXLabelsAngle;
    }

    public String getXTextLabel(Double double1)
    {
        return (String)mXTextLabels.get(double1);
    }

    public Double[] getXTextLabelLocations()
    {
        return (Double[])mXTextLabels.keySet().toArray(new Double[0]);
    }

    public String getXTitle()
    {
        return mXTitle;
    }

    public android.graphics.Paint.Align getYAxisAlign(int i)
    {
        return yAxisAlign[i];
    }

    public double getYAxisMax()
    {
        return getYAxisMax(0);
    }

    public double getYAxisMax(int i)
    {
        return mMaxY[i];
    }

    public double getYAxisMin()
    {
        return getYAxisMin(0);
    }

    public double getYAxisMin(int i)
    {
        return mMinY[i];
    }

    public int getYLabels()
    {
        return mYLabels;
    }

    public android.graphics.Paint.Align getYLabelsAlign(int i)
    {
        return yLabelsAlign[i];
    }

    public float getYLabelsAngle()
    {
        return mYLabelsAngle;
    }

    public String getYTextLabel(Double double1)
    {
        return getYTextLabel(double1, 0);
    }

    public String getYTextLabel(Double double1, int i)
    {
        return (String)((Map)mYTextLabels.get(Integer.valueOf(i))).get(double1);
    }

    public Double[] getYTextLabelLocations()
    {
        return getYTextLabelLocations(0);
    }

    public Double[] getYTextLabelLocations(int i)
    {
        return (Double[])((Map)mYTextLabels.get(Integer.valueOf(i))).keySet().toArray(new Double[0]);
    }

    public String getYTitle()
    {
        return getYTitle(0);
    }

    public String getYTitle(int i)
    {
        return mYTitle[i];
    }

    public double[] getZoomLimits()
    {
        return mZoomLimits;
    }

    public void initAxesRange(int i)
    {
        mYTitle = new String[i];
        yLabelsAlign = new android.graphics.Paint.Align[i];
        yAxisAlign = new android.graphics.Paint.Align[i];
        mMinX = new double[i];
        mMaxX = new double[i];
        mMinY = new double[i];
        mMaxY = new double[i];
        for(int j = 0; j < i; j++)
            initAxesRangeForScale(j);

    }

    public void initAxesRangeForScale(int i)
    {
        mMinX[i] = 1.7976931348623157E+308D;
        mMaxX[i] = -1.7976931348623157E+308D;
        mMinY[i] = 1.7976931348623157E+308D;
        mMaxY[i] = -1.7976931348623157E+308D;
        double ad[] = new double[4];
        ad[0] = mMinX[i];
        ad[1] = mMaxX[i];
        ad[2] = mMinY[i];
        ad[3] = mMaxY[i];
        initialRange.put(Integer.valueOf(i), ad);
        mYTitle[i] = "";
        mYTextLabels.put(Integer.valueOf(i), new HashMap());
        yLabelsAlign[i] = android.graphics.Paint.Align.CENTER;
        yAxisAlign[i] = android.graphics.Paint.Align.LEFT;
    }

    public boolean isInitialRangeSet()
    {
        return isInitialRangeSet(0);
    }

    public boolean isInitialRangeSet(int i)
    {
        boolean flag;
        if(initialRange.get(Integer.valueOf(i)) != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isMaxXSet()
    {
        return isMaxXSet(0);
    }

    public boolean isMaxXSet(int i)
    {
        boolean flag;
        if(mMaxX[i] != -1.7976931348623157E+308D)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isMaxYSet()
    {
        return isMaxYSet(0);
    }

    public boolean isMaxYSet(int i)
    {
        boolean flag;
        if(mMaxY[i] != -1.7976931348623157E+308D)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isMinXSet()
    {
        return isMinXSet(0);
    }

    public boolean isMinXSet(int i)
    {
        boolean flag;
        if(mMinX[i] != 1.7976931348623157E+308D)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isMinYSet()
    {
        return isMinYSet(0);
    }

    public boolean isMinYSet(int i)
    {
        boolean flag;
        if(mMinY[i] != 1.7976931348623157E+308D)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isPanEnabled()
    {
        boolean flag;
        if(isPanXEnabled() || isPanYEnabled())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isPanXEnabled()
    {
        return mPanXEnabled;
    }

    public boolean isPanYEnabled()
    {
        return mPanYEnabled;
    }

    public boolean isZoomEnabled()
    {
        boolean flag;
        if(isZoomXEnabled() || isZoomYEnabled())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isZoomXEnabled()
    {
        return mZoomXEnabled;
    }

    public boolean isZoomYEnabled()
    {
        return mZoomYEnabled;
    }

    public void setAxisTitleTextSize(float f)
    {
        mAxisTitleTextSize = f;
    }

    public void setBarSpacing(double d)
    {
        mBarSpacing = d;
    }

    public void setChartValuesTextSize(float f)
    {
        SimpleSeriesRenderer asimpleseriesrenderer[] = getSeriesRenderers();
        int i = asimpleseriesrenderer.length;
        for(int j = 0; j < i; j++)
            asimpleseriesrenderer[j].setChartValuesTextSize(f);

    }

    public void setDisplayChartValues(boolean flag)
    {
        SimpleSeriesRenderer asimpleseriesrenderer[] = getSeriesRenderers();
        int i = asimpleseriesrenderer.length;
        for(int j = 0; j < i; j++)
            asimpleseriesrenderer[j].setDisplayChartValues(flag);

    }

    public void setGridColor(int i)
    {
        mGridColor = i;
    }

    public void setInitialRange(double ad[])
    {
        setInitialRange(ad, 0);
    }

    public void setInitialRange(double ad[], int i)
    {
        initialRange.put(Integer.valueOf(i), ad);
    }

    public void setMarginsColor(int i)
    {
        mMarginsColor = i;
    }

    public void setOrientation(Orientation orientation)
    {
        mOrientation = orientation;
    }

    public void setPanEnabled(boolean flag, boolean flag1)
    {
        mPanXEnabled = flag;
        mPanYEnabled = flag1;
    }

    public void setPanLimits(double ad[])
    {
        mPanLimits = ad;
    }

    public void setPointSize(float f)
    {
        mPointSize = f;
    }

    public void setRange(double ad[])
    {
        setRange(ad, 0);
    }

    public void setRange(double ad[], int i)
    {
        setXAxisMin(ad[0], i);
        setXAxisMax(ad[1], i);
        setYAxisMin(ad[2], i);
        setYAxisMax(ad[3], i);
    }

    public void setXAxisMax(double d)
    {
        setXAxisMax(d, 0);
    }

    public void setXAxisMax(double d, int i)
    {
        if(!isMaxXSet(i))
            ((double[])initialRange.get(Integer.valueOf(i)))[1] = d;
        mMaxX[i] = d;
    }

    public void setXAxisMin(double d)
    {
        setXAxisMin(d, 0);
    }

    public void setXAxisMin(double d, int i)
    {
        if(!isMinXSet(i))
            ((double[])initialRange.get(Integer.valueOf(i)))[0] = d;
        mMinX[i] = d;
    }

    public void setXLabels(int i)
    {
        mXLabels = i;
    }

    public void setXLabelsAlign(android.graphics.Paint.Align align)
    {
        xLabelsAlign = align;
    }

    public void setXLabelsAngle(float f)
    {
        mXLabelsAngle = f;
    }

    public void setXTitle(String s)
    {
        mXTitle = s;
    }

    public void setYAxisAlign(android.graphics.Paint.Align align, int i)
    {
        yAxisAlign[i] = align;
    }

    public void setYAxisMax(double d)
    {
        setYAxisMax(d, 0);
    }

    public void setYAxisMax(double d, int i)
    {
        if(!isMaxYSet(i))
            ((double[])initialRange.get(Integer.valueOf(i)))[3] = d;
        mMaxY[i] = d;
    }

    public void setYAxisMin(double d)
    {
        setYAxisMin(d, 0);
    }

    public void setYAxisMin(double d, int i)
    {
        if(!isMinYSet(i))
            ((double[])initialRange.get(Integer.valueOf(i)))[2] = d;
        mMinY[i] = d;
    }

    public void setYLabels(int i)
    {
        mYLabels = i;
    }

    public void setYLabelsAlign(android.graphics.Paint.Align align)
    {
        setYLabelsAlign(align, 0);
    }

    public void setYLabelsAlign(android.graphics.Paint.Align align, int i)
    {
        yLabelsAlign[i] = align;
    }

    public void setYLabelsAngle(float f)
    {
        mYLabelsAngle = f;
    }

    public void setYTitle(String s)
    {
        setYTitle(s, 0);
    }

    public void setYTitle(String s, int i)
    {
        mYTitle[i] = s;
    }

    public void setZoomEnabled(boolean flag, boolean flag1)
    {
        mZoomXEnabled = flag;
        mZoomYEnabled = flag1;
    }

    public void setZoomLimits(double ad[])
    {
        mZoomLimits = ad;
    }

    private Map initialRange;
    private float mAxisTitleTextSize;
    private double mBarSpacing;
    private int mGridColor;
    private int mMarginsColor;
    private double mMaxX[];
    private double mMaxY[];
    private double mMinX[];
    private double mMinY[];
    private Orientation mOrientation;
    private double mPanLimits[];
    private boolean mPanXEnabled;
    private boolean mPanYEnabled;
    private float mPointSize;
    private int mXLabels;
    private float mXLabelsAngle;
    private Map mXTextLabels;
    private String mXTitle;
    private int mYLabels;
    private float mYLabelsAngle;
    private Map mYTextLabels;
    private String mYTitle[];
    private double mZoomLimits[];
    private boolean mZoomXEnabled;
    private boolean mZoomYEnabled;
    private int scalesCount;
    private android.graphics.Paint.Align xLabelsAlign;
    private android.graphics.Paint.Align yAxisAlign[];
    private android.graphics.Paint.Align yLabelsAlign[];
}
