// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;

import android.graphics.*;
import java.util.List;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

// Referenced classes of package org.achartengine.chart:
//            XYChart, TimeChart, LineChart, BarChart, 
//            BubbleChart, ScatterChart, RangeBarChart

public class CombinedXYChart extends XYChart
{

    public CombinedXYChart(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, String as[])
    {
        int i = 0;
        super(xymultipleseriesdataset, xymultipleseriesrenderer);
        Class aclass[] = new Class[7];
        aclass[i] = org/achartengine/chart/TimeChart;
        aclass[1] = org/achartengine/chart/LineChart;
        aclass[2] = org/achartengine/chart/BarChart;
        aclass[3] = org/achartengine/chart/BubbleChart;
        aclass[4] = org/achartengine/chart/LineChart;
        aclass[5] = org/achartengine/chart/ScatterChart;
        aclass[6] = org/achartengine/chart/RangeBarChart;
        xyChartTypes = aclass;
        int j = as.length;
        mCharts = new XYChart[j];
        while(i < j) 
        {
            XYMultipleSeriesDataset xymultipleseriesdataset1;
            XYMultipleSeriesRenderer xymultipleseriesrenderer1;
            int k;
            try
            {
                mCharts[i] = getXYChart(as[i]);
            }
            catch(Exception exception) { }
            if(mCharts[i] == null)
                throw new IllegalArgumentException((new StringBuilder()).append("Unknown chart type ").append(as[i]).toString());
            xymultipleseriesdataset1 = new XYMultipleSeriesDataset();
            xymultipleseriesdataset1.addSeries(xymultipleseriesdataset.getSeriesAt(i));
            xymultipleseriesrenderer1 = new XYMultipleSeriesRenderer();
            xymultipleseriesrenderer1.setBarSpacing(xymultipleseriesrenderer.getBarSpacing());
            xymultipleseriesrenderer1.setPointSize(xymultipleseriesrenderer.getPointSize());
            k = xymultipleseriesdataset.getSeriesAt(i).getScaleNumber();
            if(xymultipleseriesrenderer.isMinXSet(k))
                xymultipleseriesrenderer1.setXAxisMin(xymultipleseriesrenderer.getXAxisMin(k));
            if(xymultipleseriesrenderer.isMaxXSet(k))
                xymultipleseriesrenderer1.setXAxisMax(xymultipleseriesrenderer.getXAxisMax(k));
            if(xymultipleseriesrenderer.isMinYSet(k))
                xymultipleseriesrenderer1.setYAxisMin(xymultipleseriesrenderer.getYAxisMin(k));
            if(xymultipleseriesrenderer.isMaxYSet(k))
                xymultipleseriesrenderer1.setYAxisMax(xymultipleseriesrenderer.getYAxisMax(k));
            xymultipleseriesrenderer1.addSeriesRenderer(xymultipleseriesrenderer.getSeriesRendererAt(i));
            mCharts[i].setDatasetRenderer(xymultipleseriesdataset1, xymultipleseriesrenderer1);
            i++;
        }
    }

    private XYChart getXYChart(String s)
        throws IllegalAccessException, InstantiationException
    {
        XYChart xychart = null;
        int i = xyChartTypes.length;
        int j = 0;
        while(j < i && xychart == null) 
        {
            XYChart xychart1 = (XYChart)xyChartTypes[j].newInstance();
            if(!s.equals(xychart1.getChartType()))
                xychart1 = xychart;
            j++;
            xychart = xychart1;
        }
        return xychart;
    }

    protected RectF[] clickableAreasForPoints(float af[], float f, int i)
    {
        return mCharts[i].clickableAreasForPoints(af, f, 0);
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleseriesrenderer, float f, float f1, int i, Paint paint)
    {
        mCharts[i].drawLegendShape(canvas, simpleseriesrenderer, f, f1, 0, paint);
    }

    public void drawSeries(Canvas canvas, Paint paint, float af[], SimpleSeriesRenderer simpleseriesrenderer, float f, int i)
    {
        mCharts[i].setScreenR(getScreenR());
        mCharts[i].setCalcRange(getCalcRange(mDataset.getSeriesAt(i).getScaleNumber()), 0);
        mCharts[i].drawSeries(canvas, paint, af, simpleseriesrenderer, f, 0);
    }

    protected void drawSeries(XYSeries xyseries, Canvas canvas, Paint paint, List list, SimpleSeriesRenderer simpleseriesrenderer, float f, int i, 
            org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation orientation)
    {
        mCharts[i].setScreenR(getScreenR());
        mCharts[i].setCalcRange(getCalcRange(mDataset.getSeriesAt(i).getScaleNumber()), 0);
        mCharts[i].drawSeries(xyseries, canvas, paint, list, simpleseriesrenderer, f, 0, orientation);
    }

    public String getChartType()
    {
        return "Combined";
    }

    public int getLegendShapeWidth(int i)
    {
        return mCharts[i].getLegendShapeWidth(0);
    }

    private XYChart mCharts[];
    private Class xyChartTypes[];
}
