// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

// Referenced classes of package org.achartengine.chart:
//            BarChart

public class RangeBarChart extends BarChart
{

    RangeBarChart()
    {
    }

    public RangeBarChart(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, BarChart.Type type)
    {
        super(xymultipleseriesdataset, xymultipleseriesrenderer, type);
    }

    protected void drawChartValuesText(Canvas canvas, XYSeries xyseries, SimpleSeriesRenderer simpleseriesrenderer, Paint paint, float af[], int i)
    {
        int j = mDataset.getSeriesCount();
        float f = getHalfDiffX(af, af.length, j);
        for(int k = 0; k < af.length; k += 4)
        {
            int l = k / 2;
            float f1 = af[k];
            if(mType == BarChart.Type.DEFAULT)
                f1 += f * (float)(i * 2) - f * ((float)j - 1.5F);
            if(xyseries.getY(l + 1) != 1.7976931348623157E+308D)
                drawText(canvas, getLabel(xyseries.getY(l + 1)), f1, af[k + 3] - simpleseriesrenderer.getChartValuesSpacing(), paint, 0.0F);
            if(xyseries.getY(l) != 1.7976931348623157E+308D)
                drawText(canvas, getLabel(xyseries.getY(l)), f1, (af[k + 1] + simpleseriesrenderer.getChartValuesTextSize() + simpleseriesrenderer.getChartValuesSpacing()) - 3F, paint, 0.0F);
        }

    }

    public void drawSeries(Canvas canvas, Paint paint, float af[], SimpleSeriesRenderer simpleseriesrenderer, float f, int i)
    {
        int j = mDataset.getSeriesCount();
        int k = af.length;
        paint.setColor(simpleseriesrenderer.getColor());
        paint.setStyle(android.graphics.Paint.Style.FILL);
        float f1 = getHalfDiffX(af, k, j);
        for(int l = 0; l < k; l += 4)
            drawBar(canvas, af[l], af[l + 1], af[l + 2], af[l + 3], f1, j, i, paint);

        paint.setColor(simpleseriesrenderer.getColor());
    }

    public String getChartType()
    {
        return "RangeBar";
    }

    protected float getCoeficient()
    {
        return 0.5F;
    }

    public static final String TYPE = "RangeBar";
}
