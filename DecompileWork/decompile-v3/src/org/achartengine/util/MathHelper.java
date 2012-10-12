// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MathHelper
{

    private MathHelper()
    {
    }

    private static double[] computeLabels(double d, double d1, int i)
    {
        double ad[];
        if(Math.abs(d - d1) < 1.0000000116860974E-07D)
        {
            ad = new double[3];
            ad[0] = d;
            ad[1] = d;
            ad[2] = 0.0D;
        } else
        {
            boolean flag = false;
            double d3;
            double d4;
            double d5;
            if(d > d1)
            {
                flag = true;
            } else
            {
                double d2 = d1;
                d1 = d;
                d = d2;
            }
            d3 = roundUp(Math.abs(d1 - d) / (double)i);
            d4 = d3 * Math.ceil(d1 / d3);
            d5 = d3 * Math.floor(d / d3);
            if(flag)
            {
                ad = new double[3];
                ad[0] = d5;
                ad[1] = d4;
                ad[2] = d3 * -1D;
            } else
            {
                ad = new double[3];
                ad[0] = d4;
                ad[1] = d5;
                ad[2] = d3;
            }
        }
        return ad;
    }

    public static float[] getFloats(List list)
    {
        int i = list.size();
        float af[] = new float[i];
        for(int j = 0; j < i; j++)
            af[j] = ((Float)list.get(j)).floatValue();

        return af;
    }

    public static List getLabels(double d, double d1, int i)
    {
        ArrayList arraylist;
        double ad[];
        int j;
        int k;
        FORMAT.setMaximumFractionDigits(5);
        arraylist = new ArrayList();
        ad = computeLabels(d, d1, i);
        j = 1 + (int)((ad[1] - ad[0]) / ad[2]);
        k = 0;
_L2:
        double d2;
        if(k >= j)
            break; /* Loop/switch isn't completed */
        d2 = ad[0] + (double)k * ad[2];
        double d3 = FORMAT.parse(FORMAT.format(d2)).doubleValue();
        d2 = d3;
_L3:
        arraylist.add(Double.valueOf(d2));
        k++;
        if(true) goto _L2; else goto _L1
_L1:
        return arraylist;
        ParseException parseexception;
        parseexception;
          goto _L3
    }

    public static double[] minmax(List list)
    {
        double ad[];
        if(list.size() == 0)
        {
            ad = new double[2];
        } else
        {
            double d = ((Double)list.get(0)).doubleValue();
            int i = list.size();
            double d1 = d;
            double d2 = d;
            for(int j = 1; j < i; j++)
            {
                double d3 = ((Double)list.get(j)).doubleValue();
                d2 = Math.min(d2, d3);
                d1 = Math.max(d1, d3);
            }

            ad = new double[2];
            ad[0] = d2;
            ad[1] = d1;
        }
        return ad;
    }

    private static double roundUp(double d)
    {
        double d1;
        int i;
        double d2;
        d1 = 5D;
        i = (int)Math.floor(Math.log10(d));
        d2 = d * Math.pow(10D, -i);
        if(d2 <= d1) goto _L2; else goto _L1
_L1:
        d1 = 10D;
_L4:
        return d1 * Math.pow(10D, i);
_L2:
        if(d2 <= 2D)
            if(d2 > 1.0D)
                d1 = 2D;
            else
                d1 = d2;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static final NumberFormat FORMAT = NumberFormat.getNumberInstance();
    public static final double NULL_VALUE = 1.7976931348623157E+308D;

}
