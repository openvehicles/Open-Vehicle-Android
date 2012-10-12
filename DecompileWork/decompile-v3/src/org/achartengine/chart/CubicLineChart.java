// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;

import android.graphics.*;
import org.achartengine.model.Point;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

// Referenced classes of package org.achartengine.chart:
//            LineChart

public class CubicLineChart extends LineChart
{

    public CubicLineChart()
    {
        p1 = new Point();
        p2 = new Point();
        p3 = new Point();
        firstMultiplier = 0.33F;
        secondMultiplier = 1.0F - firstMultiplier;
    }

    public CubicLineChart(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer, float f)
    {
        super(xymultipleseriesdataset, xymultipleseriesrenderer);
        p1 = new Point();
        p2 = new Point();
        p3 = new Point();
        firstMultiplier = f;
        secondMultiplier = 1.0F - firstMultiplier;
    }

    private void calc(float af[], Point point, int i, int j, float f)
    {
        float f1 = af[i];
        float f2 = af[i + 1];
        float f3 = af[j];
        float f4 = af[j + 1];
        float f5 = f3 - f1;
        float f6 = f4 - f2;
        point.setX(f1 + f5 * f);
        point.setY(f2 + f6 * f);
    }

    protected void drawPath(Canvas canvas, float af[], Paint paint, boolean flag)
    {
        Path path = new Path();
        path.moveTo(af[0], af[1]);
        int i = 0;
        while(i < af.length) 
        {
            int j;
            int k;
            Point point;
            float f;
            if(i + 2 < af.length)
                j = i + 2;
            else
                j = i;
            if(i + 4 < af.length)
                k = i + 4;
            else
                k = j;
            calc(af, p1, i, j, secondMultiplier);
            p2.setX(af[j]);
            p2.setY(af[j + 1]);
            point = p3;
            f = firstMultiplier;
            calc(af, point, j, k, f);
            path.cubicTo(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
            i += 2;
        }
        canvas.drawPath(path, paint);
    }

    public String getChartType()
    {
        return "Cubic";
    }

    public static final String TYPE = "Cubic";
    private float firstMultiplier;
    private Point p1;
    private Point p2;
    private Point p3;
    private float secondMultiplier;
}
