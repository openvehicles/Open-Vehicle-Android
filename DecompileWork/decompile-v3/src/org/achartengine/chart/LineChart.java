// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;

import android.graphics.*;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.*;

// Referenced classes of package org.achartengine.chart:
//            XYChart, ScatterChart, PointStyle

public class LineChart extends XYChart
{

    LineChart()
    {
    }

    public LineChart(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        super(xymultipleseriesdataset, xymultipleseriesrenderer);
        pointsChart = new ScatterChart(xymultipleseriesdataset, xymultipleseriesrenderer);
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
        canvas.drawLine(f, f1, f + 30F, f1, paint);
        if(isRenderPoints(simpleseriesrenderer))
            pointsChart.drawLegendShape(canvas, simpleseriesrenderer, f + 5F, f1, i, paint);
    }

    public void drawSeries(Canvas canvas, Paint paint, float af[], SimpleSeriesRenderer simpleseriesrenderer, float f, int i)
    {
        int j = af.length;
        XYSeriesRenderer xyseriesrenderer = (XYSeriesRenderer)simpleseriesrenderer;
        float f1 = paint.getStrokeWidth();
        paint.setStrokeWidth(xyseriesrenderer.getLineWidth());
        if(xyseriesrenderer.isFillBelowLine())
        {
            paint.setColor(xyseriesrenderer.getFillBelowLineColor());
            float af1[] = new float[4 + af.length];
            System.arraycopy(af, 0, af1, 0, j);
            af1[0] = 1.0F + af[0];
            af1[j] = af1[j - 2];
            af1[j + 1] = f;
            af1[j + 2] = af1[0];
            af1[j + 3] = af1[j + 1];
            paint.setStyle(android.graphics.Paint.Style.FILL);
            drawPath(canvas, af1, paint, true);
        }
        paint.setColor(simpleseriesrenderer.getColor());
        paint.setStyle(android.graphics.Paint.Style.STROKE);
        drawPath(canvas, af, paint, false);
        paint.setStrokeWidth(f1);
    }

    public String getChartType()
    {
        return "Line";
    }

    public int getLegendShapeWidth(int i)
    {
        return 30;
    }

    public ScatterChart getPointsChart()
    {
        return pointsChart;
    }

    public boolean isRenderPoints(SimpleSeriesRenderer simpleseriesrenderer)
    {
        boolean flag;
        if(((XYSeriesRenderer)simpleseriesrenderer).getPointStyle() != PointStyle.POINT)
            flag = true;
        else
            flag = false;
        return flag;
    }

    protected void setDatasetRenderer(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        super.setDatasetRenderer(xymultipleseriesdataset, xymultipleseriesrenderer);
        pointsChart = new ScatterChart(xymultipleseriesdataset, xymultipleseriesrenderer);
    }

    private static final int SHAPE_WIDTH = 30;
    public static final String TYPE = "Line";
    private ScatterChart pointsChart;
}
