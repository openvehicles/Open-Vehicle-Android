// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine;

import android.content.Context;
import android.content.Intent;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.BubbleChart;
import org.achartengine.chart.CombinedXYChart;
import org.achartengine.chart.CubicLineChart;
import org.achartengine.chart.DialChart;
import org.achartengine.chart.DoughnutChart;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PieChart;
import org.achartengine.chart.RangeBarChart;
import org.achartengine.chart.ScatterChart;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

// Referenced classes of package org.achartengine:
//            GraphicalActivity, GraphicalView

public class ChartFactory
{

    private ChartFactory()
    {
    }

    private static boolean checkMultipleSeriesItems(MultipleCategorySeries multiplecategoryseries, int i)
    {
        int j = multiplecategoryseries.getCategoriesCount();
        int k = 0;
        boolean flag = true;
        while(k < j && flag) 
        {
            if(multiplecategoryseries.getValues(k).length == multiplecategoryseries.getTitles(k).length)
                flag = true;
            else
                flag = false;
            k++;
        }
        return flag;
    }

    private static void checkParameters(CategorySeries categoryseries, DefaultRenderer defaultrenderer)
    {
        if(categoryseries == null || defaultrenderer == null || categoryseries.getItemCount() != defaultrenderer.getSeriesRendererCount())
            throw new IllegalArgumentException("Dataset and renderer should be not null and the dataset number of items should be equal to the number of series renderers");
        else
            return;
    }

    private static void checkParameters(MultipleCategorySeries multiplecategoryseries, DefaultRenderer defaultrenderer)
    {
        if(multiplecategoryseries == null || defaultrenderer == null || !checkMultipleSeriesItems(multiplecategoryseries, defaultrenderer.getSeriesRendererCount()))
            throw new IllegalArgumentException("Titles and values should be not null and the dataset number of items should be equal to the number of series renderers");
        else
            return;
    }

    private static void checkParameters(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        if(xymultipleseriesdataset == null || xymultipleseriesrenderer == null || xymultipleseriesdataset.getSeriesCount() != xymultipleseriesrenderer.getSeriesRendererCount())
            throw new IllegalArgumentException("Dataset and renderer should be not null and should have the same number of series");
        else
            return;
    }

    public static final Intent getBarChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, org.achartengine.chart.BarChart.Type type)
    {
        return getBarChartIntent(context, xymultipleseriesdataset, xymultipleseriesrenderer, type, "");
    }

    public static final Intent getBarChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, org.achartengine.chart.BarChart.Type type, String s)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        Intent intent = new Intent(context, org/achartengine/GraphicalActivity);
        intent.putExtra("chart", new BarChart(xymultipleseriesdataset, xymultipleseriesrenderer, type));
        intent.putExtra("title", s);
        return intent;
    }

    public static final GraphicalView getBarChartView(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, org.achartengine.chart.BarChart.Type type)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        return new GraphicalView(context, new BarChart(xymultipleseriesdataset, xymultipleseriesrenderer, type));
    }

    public static final Intent getBubbleChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        return getBubbleChartIntent(context, xymultipleseriesdataset, xymultipleseriesrenderer, "");
    }

    public static final Intent getBubbleChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, String s)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        Intent intent = new Intent(context, org/achartengine/GraphicalActivity);
        intent.putExtra("chart", new BubbleChart(xymultipleseriesdataset, xymultipleseriesrenderer));
        intent.putExtra("title", s);
        return intent;
    }

    public static final GraphicalView getBubbleChartView(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        return new GraphicalView(context, new BubbleChart(xymultipleseriesdataset, xymultipleseriesrenderer));
    }

    public static final Intent getCombinedXYChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, String as[], String s)
    {
        if(xymultipleseriesdataset == null || xymultipleseriesrenderer == null || as == null || xymultipleseriesdataset.getSeriesCount() != as.length)
        {
            throw new IllegalArgumentException("Datasets, renderers and types should be not null and the datasets series count should be equal to the types length");
        } else
        {
            checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
            Intent intent = new Intent(context, org/achartengine/GraphicalActivity);
            intent.putExtra("chart", new CombinedXYChart(xymultipleseriesdataset, xymultipleseriesrenderer, as));
            intent.putExtra("title", s);
            return intent;
        }
    }

    public static final GraphicalView getCombinedXYChartView(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, String as[])
    {
        if(xymultipleseriesdataset == null || xymultipleseriesrenderer == null || as == null || xymultipleseriesdataset.getSeriesCount() != as.length)
        {
            throw new IllegalArgumentException("Dataset, renderer and types should be not null and the datasets series count should be equal to the types length");
        } else
        {
            checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
            return new GraphicalView(context, new CombinedXYChart(xymultipleseriesdataset, xymultipleseriesrenderer, as));
        }
    }

    public static final GraphicalView getCubeLineChartView(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, float f)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        return new GraphicalView(context, new CubicLineChart(xymultipleseriesdataset, xymultipleseriesrenderer, f));
    }

    public static final Intent getCubicLineChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, float f)
    {
        return getCubicLineChartIntent(context, xymultipleseriesdataset, xymultipleseriesrenderer, f, "");
    }

    public static final Intent getCubicLineChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, float f, String s)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        Intent intent = new Intent(context, org/achartengine/GraphicalActivity);
        intent.putExtra("chart", new CubicLineChart(xymultipleseriesdataset, xymultipleseriesrenderer, f));
        intent.putExtra("title", s);
        return intent;
    }

    public static final Intent getDialChartIntent(Context context, CategorySeries categoryseries, DialRenderer dialrenderer, String s)
    {
        checkParameters(categoryseries, dialrenderer);
        Intent intent = new Intent(context, org/achartengine/GraphicalActivity);
        intent.putExtra("chart", new DialChart(categoryseries, dialrenderer));
        intent.putExtra("title", s);
        return intent;
    }

    public static final GraphicalView getDialChartView(Context context, CategorySeries categoryseries, DialRenderer dialrenderer)
    {
        checkParameters(categoryseries, dialrenderer);
        return new GraphicalView(context, new DialChart(categoryseries, dialrenderer));
    }

    public static final Intent getDoughnutChartIntent(Context context, MultipleCategorySeries multiplecategoryseries, DefaultRenderer defaultrenderer, String s)
    {
        checkParameters(multiplecategoryseries, defaultrenderer);
        Intent intent = new Intent(context, org/achartengine/GraphicalActivity);
        intent.putExtra("chart", new DoughnutChart(multiplecategoryseries, defaultrenderer));
        intent.putExtra("title", s);
        return intent;
    }

    public static final GraphicalView getDoughnutChartView(Context context, MultipleCategorySeries multiplecategoryseries, DefaultRenderer defaultrenderer)
    {
        checkParameters(multiplecategoryseries, defaultrenderer);
        return new GraphicalView(context, new DoughnutChart(multiplecategoryseries, defaultrenderer));
    }

    public static final Intent getLineChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        return getLineChartIntent(context, xymultipleseriesdataset, xymultipleseriesrenderer, "");
    }

    public static final Intent getLineChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, String s)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        Intent intent = new Intent(context, org/achartengine/GraphicalActivity);
        intent.putExtra("chart", new LineChart(xymultipleseriesdataset, xymultipleseriesrenderer));
        intent.putExtra("title", s);
        return intent;
    }

    public static final GraphicalView getLineChartView(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        return new GraphicalView(context, new LineChart(xymultipleseriesdataset, xymultipleseriesrenderer));
    }

    public static final Intent getPieChartIntent(Context context, CategorySeries categoryseries, DefaultRenderer defaultrenderer, String s)
    {
        checkParameters(categoryseries, defaultrenderer);
        Intent intent = new Intent(context, org/achartengine/GraphicalActivity);
        intent.putExtra("chart", new PieChart(categoryseries, defaultrenderer));
        intent.putExtra("title", s);
        return intent;
    }

    public static final GraphicalView getPieChartView(Context context, CategorySeries categoryseries, DefaultRenderer defaultrenderer)
    {
        checkParameters(categoryseries, defaultrenderer);
        return new GraphicalView(context, new PieChart(categoryseries, defaultrenderer));
    }

    public static final Intent getRangeBarChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, org.achartengine.chart.BarChart.Type type, String s)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        Intent intent = new Intent(context, org/achartengine/GraphicalActivity);
        intent.putExtra("chart", new RangeBarChart(xymultipleseriesdataset, xymultipleseriesrenderer, type));
        intent.putExtra("title", s);
        return intent;
    }

    public static final GraphicalView getRangeBarChartView(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, org.achartengine.chart.BarChart.Type type)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        return new GraphicalView(context, new RangeBarChart(xymultipleseriesdataset, xymultipleseriesrenderer, type));
    }

    public static final Intent getScatterChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        return getScatterChartIntent(context, xymultipleseriesdataset, xymultipleseriesrenderer, "");
    }

    public static final Intent getScatterChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, String s)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        Intent intent = new Intent(context, org/achartengine/GraphicalActivity);
        intent.putExtra("chart", new ScatterChart(xymultipleseriesdataset, xymultipleseriesrenderer));
        intent.putExtra("title", s);
        return intent;
    }

    public static final GraphicalView getScatterChartView(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        return new GraphicalView(context, new ScatterChart(xymultipleseriesdataset, xymultipleseriesrenderer));
    }

    public static final Intent getTimeChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, String s)
    {
        return getTimeChartIntent(context, xymultipleseriesdataset, xymultipleseriesrenderer, s, "");
    }

    public static final Intent getTimeChartIntent(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, String s, String s1)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        Intent intent = new Intent(context, org/achartengine/GraphicalActivity);
        TimeChart timechart = new TimeChart(xymultipleseriesdataset, xymultipleseriesrenderer);
        timechart.setDateFormat(s);
        intent.putExtra("chart", timechart);
        intent.putExtra("title", s1);
        return intent;
    }

    public static final GraphicalView getTimeChartView(Context context, XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, String s)
    {
        checkParameters(xymultipleseriesdataset, xymultipleseriesrenderer);
        TimeChart timechart = new TimeChart(xymultipleseriesdataset, xymultipleseriesrenderer);
        timechart.setDateFormat(s);
        return new GraphicalView(context, timechart);
    }

    public static final String CHART = "chart";
    public static final String TITLE = "title";
}
