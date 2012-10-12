// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

// Referenced classes of package org.achartengine.chart:
//            LineChart

public class TimeChart extends LineChart
{

    TimeChart()
    {
    }

    public TimeChart(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        super(xymultipleseriesdataset, xymultipleseriesrenderer);
    }

    private DateFormat getDateFormat(double d, double d1)
    {
        if(mDateFormat == null) goto _L2; else goto _L1
_L1:
        Object obj = new SimpleDateFormat(mDateFormat);
_L4:
        return ((DateFormat) (obj));
        Exception exception;
        exception;
_L2:
        obj = SimpleDateFormat.getDateInstance(2);
        double d2 = d1 - d;
        if(d2 > 86400000D && d2 < 432000000D)
            obj = SimpleDateFormat.getDateTimeInstance(3, 3);
        else
        if(d2 < 86400000D)
            obj = SimpleDateFormat.getTimeInstance(2);
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void drawXLabels(List list, Double adouble[], Canvas canvas, Paint paint, int i, int j, int k, 
            double d, double d1, double d2)
    {
        int l = list.size();
        if(l > 0)
        {
            boolean flag = mRenderer.isShowLabels();
            boolean flag1 = mRenderer.isShowGrid();
            DateFormat dateformat = getDateFormat(((Double)list.get(0)).doubleValue(), ((Double)list.get(l - 1)).doubleValue());
            for(int i1 = 0; i1 < l; i1++)
            {
                long l1 = Math.round(((Double)list.get(i1)).doubleValue());
                float f = (float)((double)i + d * ((double)l1 - d1));
                if(flag)
                {
                    paint.setColor(mRenderer.getLabelsColor());
                    canvas.drawLine(f, k, f, (float)k + mRenderer.getLabelsTextSize() / 3F, paint);
                    drawText(canvas, dateformat.format(new Date(l1)), f, (float)k + (4F * mRenderer.getLabelsTextSize()) / 3F, paint, mRenderer.getXLabelsAngle());
                }
                if(flag1)
                {
                    paint.setColor(mRenderer.getGridColor());
                    canvas.drawLine(f, k, f, j, paint);
                }
            }

        }
        drawXTextLabels(adouble, canvas, paint, true, i, j, k, d, d1, d2);
    }

    public String getChartType()
    {
        return "Time";
    }

    public String getDateFormat()
    {
        return mDateFormat;
    }

    public void setDateFormat(String s)
    {
        mDateFormat = s;
    }

    public static final long DAY = 0x5265c00L;
    public static final String TYPE = "Time";
    private String mDateFormat;
}
