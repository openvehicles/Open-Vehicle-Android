// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.renderer;

import java.util.*;

// Referenced classes of package org.achartengine.renderer:
//            DefaultRenderer

public class DialRenderer extends DefaultRenderer
{
    public static final class Type extends Enum
    {

        public static Type valueOf(String s)
        {
            return (Type)Enum.valueOf(org/achartengine/renderer/DialRenderer$Type, s);
        }

        public static Type[] values()
        {
            return (Type[])$VALUES.clone();
        }

        private static final Type $VALUES[];
        public static final Type ARROW;
        public static final Type NEEDLE;

        static 
        {
            NEEDLE = new Type("NEEDLE", 0);
            ARROW = new Type("ARROW", 1);
            Type atype[] = new Type[2];
            atype[0] = NEEDLE;
            atype[1] = ARROW;
            $VALUES = atype;
        }

        private Type(String s, int i)
        {
            super(s, i);
        }
    }


    public DialRenderer()
    {
        mChartTitle = "";
        mChartTitleTextSize = 15F;
        mAngleMin = 330D;
        mAngleMax = 30D;
        mMinValue = 1.7976931348623157E+308D;
        mMaxValue = -1.7976931348623157E+308D;
        mMinorTickSpacing = 1.7976931348623157E+308D;
        mMajorTickSpacing = 1.7976931348623157E+308D;
        mVisualTypes = new ArrayList();
    }

    public double getAngleMax()
    {
        return mAngleMax;
    }

    public double getAngleMin()
    {
        return mAngleMin;
    }

    public String getChartTitle()
    {
        return mChartTitle;
    }

    public float getChartTitleTextSize()
    {
        return mChartTitleTextSize;
    }

    public double getMajorTicksSpacing()
    {
        return mMajorTickSpacing;
    }

    public double getMaxValue()
    {
        return mMaxValue;
    }

    public double getMinValue()
    {
        return mMinValue;
    }

    public double getMinorTicksSpacing()
    {
        return mMinorTickSpacing;
    }

    public Type getVisualTypeForIndex(int i)
    {
        Type type;
        if(i < mVisualTypes.size())
            type = (Type)mVisualTypes.get(i);
        else
            type = Type.NEEDLE;
        return type;
    }

    public boolean isMaxValueSet()
    {
        boolean flag;
        if(mMaxValue != -1.7976931348623157E+308D)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isMinValueSet()
    {
        boolean flag;
        if(mMinValue != 1.7976931348623157E+308D)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void setAngleMax(double d)
    {
        mAngleMax = d;
    }

    public void setAngleMin(double d)
    {
        mAngleMin = d;
    }

    public void setChartTitle(String s)
    {
        mChartTitle = s;
    }

    public void setChartTitleTextSize(float f)
    {
        mChartTitleTextSize = f;
    }

    public void setMajorTicksSpacing(double d)
    {
        mMajorTickSpacing = d;
    }

    public void setMaxValue(double d)
    {
        mMaxValue = d;
    }

    public void setMinValue(double d)
    {
        mMinValue = d;
    }

    public void setMinorTicksSpacing(double d)
    {
        mMinorTickSpacing = d;
    }

    public void setVisualTypes(Type atype[])
    {
        mVisualTypes.clear();
        mVisualTypes.addAll(Arrays.asList(atype));
    }

    private double mAngleMax;
    private double mAngleMin;
    private String mChartTitle;
    private float mChartTitleTextSize;
    private double mMajorTickSpacing;
    private double mMaxValue;
    private double mMinValue;
    private double mMinorTickSpacing;
    private List mVisualTypes;
}
