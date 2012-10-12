// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;

import android.graphics.*;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.*;

// Referenced classes of package org.achartengine.chart:
//            XYChart

public class BubbleChart extends XYChart
{

    BubbleChart()
    {
    }

    public BubbleChart(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        super(xymultipleseriesdataset, xymultipleseriesrenderer);
    }

    private void drawCircle(Canvas canvas, Paint paint, float f, float f1, float f2)
    {
        canvas.drawCircle(f, f1, f2, paint);
    }

    protected RectF[] clickableAreasForPoints(float af[], float f, int i)
    {
        int j = af.length;
        XYValueSeries xyvalueseries = (XYValueSeries)mDataset.getSeriesAt(i);
        double d = 20D / xyvalueseries.getMaxValue();
        RectF arectf[] = new RectF[j / 2];
        for(int k = 0; k < j; k += 2)
        {
            double d1 = 2D + d * xyvalueseries.getValue(k / 2);
            arectf[k / 2] = new RectF(af[k] - (float)d1, af[k + 1] - (float)d1, af[k] + (float)d1, af[k + 1] + (float)d1);
        }

        return arectf;
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleseriesrenderer, float f, float f1, int i, Paint paint)
    {
        paint.setStyle(android.graphics.Paint.Style.FILL);
        drawCircle(canvas, paint, f + 10F, f1, 3F);
    }

    public void drawSeries(Canvas canvas, Paint paint, float af[], SimpleSeriesRenderer simpleseriesrenderer, float f, int i)
    {
        paint.setColor(((XYSeriesRenderer)simpleseriesrenderer).getColor());
        paint.setStyle(android.graphics.Paint.Style.FILL);
        int j = af.length;
        XYValueSeries xyvalueseries = (XYValueSeries)mDataset.getSeriesAt(i);
        double d = 20D / xyvalueseries.getMaxValue();
        for(int k = 0; k < j; k += 2)
        {
            double d1 = 2D + d * xyvalueseries.getValue(k / 2);
            drawCircle(canvas, paint, af[k], af[k + 1], (float)d1);
        }

    }

    public String getChartType()
    {
        return "Bubble";
    }

    public int getLegendShapeWidth(int i)
    {
        return 10;
    }

    private static final int MAX_BUBBLE_SIZE = 20;
    private static final int MIN_BUBBLE_SIZE = 2;
    private static final int SHAPE_WIDTH = 10;
    public static final String TYPE = "Bubble";
}
