// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;

import android.graphics.*;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.*;

// Referenced classes of package org.achartengine.chart:
//            XYChart, PointStyle

public class ScatterChart extends XYChart
{

    ScatterChart()
    {
        size = 3F;
    }

    public ScatterChart(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        super(xymultipleseriesdataset, xymultipleseriesrenderer);
        size = 3F;
        size = xymultipleseriesrenderer.getPointSize();
    }

    private void drawCircle(Canvas canvas, Paint paint, float f, float f1)
    {
        canvas.drawCircle(f, f1, size, paint);
    }

    private void drawDiamond(Canvas canvas, Paint paint, float af[], float f, float f1)
    {
        af[0] = f;
        af[1] = f1 - size;
        af[2] = f - size;
        af[3] = f1;
        af[4] = f;
        af[5] = f1 + size;
        af[6] = f + size;
        af[7] = f1;
        drawPath(canvas, af, paint, true);
    }

    private void drawSquare(Canvas canvas, Paint paint, float f, float f1)
    {
        canvas.drawRect(f - size, f1 - size, f + size, f1 + size, paint);
    }

    private void drawTriangle(Canvas canvas, Paint paint, float af[], float f, float f1)
    {
        af[0] = f;
        af[1] = f1 - size - size / 2.0F;
        af[2] = f - size;
        af[3] = f1 + size;
        af[4] = f + size;
        af[5] = af[3];
        drawPath(canvas, af, paint, true);
    }

    private void drawX(Canvas canvas, Paint paint, float f, float f1)
    {
        canvas.drawLine(f - size, f1 - size, f + size, f1 + size, paint);
        canvas.drawLine(f + size, f1 - size, f - size, f1 + size, paint);
    }

    protected RectF[] clickableAreasForPoints(float af[], float f, int i)
    {
        int j = af.length;
        RectF arectf[] = new RectF[j / 2];
        for(int k = 0; k < j; k += 2)
        {
            int l = mRenderer.getSelectableBuffer();
            arectf[k / 2] = new RectF(af[k] - (float)l, af[k + 1] - (float)l, af[k] + (float)l, af[k + 1] + (float)l);
        }

        return arectf;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleseriesrenderer, float f, float f1, int i, Paint paint)
    {
        class _cls1
        {

            static final int $SwitchMap$org$achartengine$chart$PointStyle[];

            static 
            {
                $SwitchMap$org$achartengine$chart$PointStyle = new int[PointStyle.values().length];
                NoSuchFieldError nosuchfielderror5;
                try
                {
                    $SwitchMap$org$achartengine$chart$PointStyle[PointStyle.X.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$org$achartengine$chart$PointStyle[PointStyle.CIRCLE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$org$achartengine$chart$PointStyle[PointStyle.TRIANGLE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$org$achartengine$chart$PointStyle[PointStyle.SQUARE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$org$achartengine$chart$PointStyle[PointStyle.DIAMOND.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                $SwitchMap$org$achartengine$chart$PointStyle[PointStyle.POINT.ordinal()] = 6;
_L2:
                return;
                nosuchfielderror5;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        if(((XYSeriesRenderer)simpleseriesrenderer).isFillPoints())
            paint.setStyle(android.graphics.Paint.Style.FILL);
        else
            paint.setStyle(android.graphics.Paint.Style.STROKE);
        _cls1..SwitchMap.org.achartengine.chart.PointStyle[((XYSeriesRenderer)simpleseriesrenderer).getPointStyle().ordinal()];
        JVM INSTR tableswitch 1 6: default 72
    //                   1 84
    //                   2 100
    //                   3 116
    //                   4 136
    //                   5 152
    //                   6 172;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L1:
        return;
_L2:
        drawX(canvas, paint, f + 10F, f1);
        continue; /* Loop/switch isn't completed */
_L3:
        drawCircle(canvas, paint, f + 10F, f1);
        continue; /* Loop/switch isn't completed */
_L4:
        drawTriangle(canvas, paint, new float[6], f + 10F, f1);
        continue; /* Loop/switch isn't completed */
_L5:
        drawSquare(canvas, paint, f + 10F, f1);
        continue; /* Loop/switch isn't completed */
_L6:
        drawDiamond(canvas, paint, new float[8], f + 10F, f1);
        continue; /* Loop/switch isn't completed */
_L7:
        canvas.drawPoint(f + 10F, f1, paint);
        if(true) goto _L1; else goto _L8
_L8:
    }

    public void drawSeries(Canvas canvas, Paint paint, float af[], SimpleSeriesRenderer simpleseriesrenderer, float f, int i)
    {
        int j;
        int k;
        j = 0;
        XYSeriesRenderer xyseriesrenderer = (XYSeriesRenderer)simpleseriesrenderer;
        paint.setColor(xyseriesrenderer.getColor());
        if(xyseriesrenderer.isFillPoints())
            paint.setStyle(android.graphics.Paint.Style.FILL);
        else
            paint.setStyle(android.graphics.Paint.Style.STROKE);
        k = af.length;
        _cls1..SwitchMap.org.achartengine.chart.PointStyle[xyseriesrenderer.getPointStyle().ordinal()];
        JVM INSTR tableswitch 1 6: default 88
    //                   1 99
    //                   2 128
    //                   3 157
    //                   4 197
    //                   5 226
    //                   6 266;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L1:
        return;
_L2:
        while(j < k) 
        {
            drawX(canvas, paint, af[j], af[j + 1]);
            j += 2;
        }
        continue; /* Loop/switch isn't completed */
_L3:
        while(j < k) 
        {
            drawCircle(canvas, paint, af[j], af[j + 1]);
            j += 2;
        }
        continue; /* Loop/switch isn't completed */
_L4:
        float af2[] = new float[6];
        int i1 = 0;
        while(i1 < k) 
        {
            drawTriangle(canvas, paint, af2, af[i1], af[i1 + 1]);
            i1 += 2;
        }
        continue; /* Loop/switch isn't completed */
_L5:
        while(j < k) 
        {
            drawSquare(canvas, paint, af[j], af[j + 1]);
            j += 2;
        }
        continue; /* Loop/switch isn't completed */
_L6:
        float af1[] = new float[8];
        int l = 0;
        while(l < k) 
        {
            drawDiamond(canvas, paint, af1, af[l], af[l + 1]);
            l += 2;
        }
        continue; /* Loop/switch isn't completed */
_L7:
        canvas.drawPoints(af, paint);
        if(true) goto _L1; else goto _L8
_L8:
    }

    public String getChartType()
    {
        return "Scatter";
    }

    public int getLegendShapeWidth(int i)
    {
        return 10;
    }

    protected void setDatasetRenderer(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        super.setDatasetRenderer(xymultipleseriesdataset, xymultipleseriesrenderer);
        size = xymultipleseriesrenderer.getPointSize();
    }

    private static final int SHAPE_WIDTH = 10;
    private static final float SIZE = 3F;
    public static final String TYPE = "Scatter";
    private float size;
}
