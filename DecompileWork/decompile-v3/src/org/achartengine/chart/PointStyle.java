// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;


public final class PointStyle extends Enum
{

    private PointStyle(String s, int i, String s1)
    {
        super(s, i);
        mName = s1;
    }

    public static int getIndexForName(String s)
    {
        int i = -1;
        PointStyle apointstyle[] = values();
        int j = apointstyle.length;
        for(int k = 0; k < j && i < 0; k++)
            if(apointstyle[k].mName.equals(s))
                i = k;

        return Math.max(0, i);
    }

    public static PointStyle getPointStyleForName(String s)
    {
        PointStyle apointstyle[] = values();
        int i = apointstyle.length;
        PointStyle pointstyle = null;
        for(int j = 0; j < i && pointstyle == null; j++)
            if(apointstyle[j].mName.equals(s))
                pointstyle = apointstyle[j];

        return pointstyle;
    }

    public static PointStyle valueOf(String s)
    {
        return (PointStyle)Enum.valueOf(org/achartengine/chart/PointStyle, s);
    }

    public static PointStyle[] values()
    {
        return (PointStyle[])$VALUES.clone();
    }

    public String getName()
    {
        return mName;
    }

    public String toString()
    {
        return getName();
    }

    private static final PointStyle $VALUES[];
    public static final PointStyle CIRCLE;
    public static final PointStyle DIAMOND;
    public static final PointStyle POINT;
    public static final PointStyle SQUARE;
    public static final PointStyle TRIANGLE;
    public static final PointStyle X;
    private String mName;

    static 
    {
        X = new PointStyle("X", 0, "x");
        CIRCLE = new PointStyle("CIRCLE", 1, "circle");
        TRIANGLE = new PointStyle("TRIANGLE", 2, "triangle");
        SQUARE = new PointStyle("SQUARE", 3, "square");
        DIAMOND = new PointStyle("DIAMOND", 4, "diamond");
        POINT = new PointStyle("POINT", 5, "point");
        PointStyle apointstyle[] = new PointStyle[6];
        apointstyle[0] = X;
        apointstyle[1] = CIRCLE;
        apointstyle[2] = TRIANGLE;
        apointstyle[3] = SQUARE;
        apointstyle[4] = DIAMOND;
        apointstyle[5] = POINT;
        $VALUES = apointstyle;
    }
}
