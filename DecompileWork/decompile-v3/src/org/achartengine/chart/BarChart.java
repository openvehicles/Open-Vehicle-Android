// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;

import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

// Referenced classes of package org.achartengine.chart:
//            XYChart

public class BarChart extends XYChart
{
    public static final class Type extends Enum
    {

        public static Type valueOf(String s)
        {
            return (Type)Enum.valueOf(org/achartengine/chart/BarChart$Type, s);
        }

        public static Type[] values()
        {
            return (Type[])$VALUES.clone();
        }

        private static final Type $VALUES[];
        public static final Type DEFAULT;
        public static final Type STACKED;

        static 
        {
            DEFAULT = new Type("DEFAULT", 0);
            STACKED = new Type("STACKED", 1);
            Type atype[] = new Type[2];
            atype[0] = DEFAULT;
            atype[1] = STACKED;
            $VALUES = atype;
        }

        private Type(String s, int i)
        {
            super(s, i);
        }
    }


    BarChart()
    {
        mType = Type.DEFAULT;
    }

    public BarChart(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, Type type)
    {
        super(xymultipleseriesdataset, xymultipleseriesrenderer);
        mType = Type.DEFAULT;
        mType = type;
    }

    private void drawBar(Canvas canvas, float f, float f1, float f2, float f3, int i, int j, 
            Paint paint)
    {
        SimpleSeriesRenderer simpleseriesrenderer = mRenderer.getSeriesRendererAt(j);
        if(simpleseriesrenderer.isGradientEnabled())
        {
            double ad[] = new double[2];
            ad[0] = 0.0D;
            ad[1] = simpleseriesrenderer.getGradientStopValue();
            float f4 = (float)toScreenPoint(ad, i)[1];
            double ad1[] = new double[2];
            ad1[0] = 0.0D;
            ad1[1] = simpleseriesrenderer.getGradientStartValue();
            float f5 = (float)toScreenPoint(ad1, i)[1];
            float f6 = Math.max(f4, f1);
            float f7 = Math.min(f5, f3);
            int k = simpleseriesrenderer.getGradientStopColor();
            int l = simpleseriesrenderer.getGradientStartColor();
            int i1;
            int j1;
            android.graphics.drawable.GradientDrawable.Orientation orientation;
            int ai[];
            GradientDrawable gradientdrawable;
            if(f1 < f4)
            {
                paint.setColor(k);
                canvas.drawRect(Math.round(f), Math.round(f1), Math.round(f2), Math.round(f6), paint);
                i1 = k;
            } else
            {
                i1 = getGradientPartialColor(k, l, (f5 - f6) / (f5 - f4));
            }
            if(f3 > f5)
            {
                paint.setColor(l);
                canvas.drawRect(Math.round(f), Math.round(f7), Math.round(f2), Math.round(f3), paint);
                j1 = l;
            } else
            {
                j1 = getGradientPartialColor(l, k, (f7 - f4) / (f5 - f4));
            }
            orientation = android.graphics.drawable.GradientDrawable.Orientation.BOTTOM_TOP;
            ai = new int[2];
            ai[0] = j1;
            ai[1] = i1;
            gradientdrawable = new GradientDrawable(orientation, ai);
            gradientdrawable.setBounds(Math.round(f), Math.round(f6), Math.round(f2), Math.round(f7));
            gradientdrawable.draw(canvas);
        } else
        {
            canvas.drawRect(Math.round(f), Math.round(f1), Math.round(f2), Math.round(f3), paint);
        }
    }

    private int getGradientPartialColor(int i, int j, float f)
    {
        return Color.argb(Math.round(f * (float)Color.alpha(i) + (1.0F - f) * (float)Color.alpha(j)), Math.round(f * (float)Color.red(i) + (1.0F - f) * (float)Color.red(j)), Math.round(f * (float)Color.green(i) + (1.0F - f) * (float)Color.green(j)), Math.round(f * (float)Color.blue(i) + (1.0F - f) * (float)Color.blue(j)));
    }

    protected RectF[] clickableAreasForPoints(float af[], float f, int i)
    {
        int j = mDataset.getSeriesCount();
        int k = af.length;
        RectF arectf[] = new RectF[k / 2];
        float f1 = getHalfDiffX(af, k, j);
        int l = 0;
        while(l < k) 
        {
            float f2 = af[l];
            float f3 = af[l + 1];
            if(mType == Type.STACKED)
            {
                arectf[l / 2] = new RectF(f2 - f1, f3, f2 + f1, f);
            } else
            {
                float f4 = (f2 - f1 * (float)j) + f1 * (float)(i * 2);
                arectf[l / 2] = new RectF(f4, f3, f4 + 2.0F * f1, f);
            }
            l += 2;
        }
        return arectf;
    }

    protected void drawBar(Canvas canvas, float f, float f1, float f2, float f3, float f4, int i, 
            int j, Paint paint)
    {
        int k = mDataset.getSeriesAt(j).getScaleNumber();
        if(mType == Type.STACKED)
        {
            drawBar(canvas, f - f4, f3, f2 + f4, f1, k, j, paint);
        } else
        {
            float f5 = (f - f4 * (float)i) + f4 * (float)(j * 2);
            drawBar(canvas, f5, f3, f5 + 2.0F * f4, f1, k, j, paint);
        }
    }

    protected void drawChartValuesText(Canvas canvas, XYSeries xyseries, SimpleSeriesRenderer simpleseriesrenderer, Paint paint, float af[], int i)
    {
        int j = mDataset.getSeriesCount();
        float f = getHalfDiffX(af, af.length, j);
        for(int k = 0; k < af.length; k += 2)
        {
            int l = k / 2;
            if(xyseries.getY(l) == 1.7976931348623157E+308D)
                continue;
            float f1 = af[k];
            if(mType == Type.DEFAULT)
                f1 += f * (float)(i * 2) - f * ((float)j - 1.5F);
            drawText(canvas, getLabel(xyseries.getY(l)), f1, af[k + 1] - simpleseriesrenderer.getChartValuesSpacing(), paint, 0.0F);
        }

    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleseriesrenderer, float f, float f1, int i, Paint paint)
    {
        canvas.drawRect(f, f1 - 6F, f + 12F, f1 + 6F, paint);
    }

    public void drawSeries(Canvas canvas, Paint paint, float af[], SimpleSeriesRenderer simpleseriesrenderer, float f, int i)
    {
        int j = mDataset.getSeriesCount();
        int k = af.length;
        paint.setColor(simpleseriesrenderer.getColor());
        paint.setStyle(android.graphics.Paint.Style.FILL);
        float f1 = getHalfDiffX(af, k, j);
        for(int l = 0; l < k; l += 2)
        {
            float f2 = af[l];
            drawBar(canvas, f2, f, f2, af[l + 1], f1, j, i, paint);
        }

        paint.setColor(simpleseriesrenderer.getColor());
    }

    public String getChartType()
    {
        return "Bar";
    }

    protected float getCoeficient()
    {
        return 1.0F;
    }

    public double getDefaultMinimum()
    {
        return 0.0D;
    }

    protected float getHalfDiffX(float af[], int i, int j)
    {
        int k;
        float f;
        if(i > 2)
            k = i - 2;
        else
            k = i;
        f = (af[i - 2] - af[0]) / (float)k;
        if(f == 0.0F)
            f = 10F;
        if(mType != Type.STACKED)
            f /= j;
        return (float)((double)f / ((double)getCoeficient() * (1.0D + mRenderer.getBarSpacing())));
    }

    public int getLegendShapeWidth(int i)
    {
        return 12;
    }

    protected boolean isRenderNullValues()
    {
        return true;
    }

    private static final int SHAPE_WIDTH = 12;
    public static final String TYPE = "Bar";
    protected Type mType;
}
