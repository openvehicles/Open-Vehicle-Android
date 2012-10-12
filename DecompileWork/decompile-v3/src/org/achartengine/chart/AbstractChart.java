// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;

import android.graphics.*;
import java.io.Serializable;
import java.util.List;
import org.achartengine.model.Point;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.*;

public abstract class AbstractChart
    implements Serializable
{

    public AbstractChart()
    {
    }

    private String getFitText(String s, float f, Paint paint)
    {
        int i = s.length();
        int j = 0;
        String s1;
        for(s1 = s; paint.measureText(s1) > f && j < i; s1 = (new StringBuilder()).append(s.substring(0, i - j)).append("...").toString())
            j++;

        if(j == i)
            s1 = "...";
        return s1;
    }

    public abstract void draw(Canvas canvas, int i, int j, int k, int l, Paint paint);

    protected void drawBackground(DefaultRenderer defaultrenderer, Canvas canvas, int i, int j, int k, int l, Paint paint, 
            boolean flag, int i1)
    {
        if(defaultrenderer.isApplyBackgroundColor() || flag)
        {
            if(flag)
                paint.setColor(i1);
            else
                paint.setColor(defaultrenderer.getBackgroundColor());
            paint.setStyle(android.graphics.Paint.Style.FILL);
            canvas.drawRect(i, j, i + k, j + l, paint);
        }
    }

    protected void drawLabel(Canvas canvas, String s, DefaultRenderer defaultrenderer, List list, int i, int j, float f, 
            float f1, float f2, float f3, int k, int l, Paint paint)
    {
        if(defaultrenderer.isShowLabels())
        {
            paint.setColor(defaultrenderer.getLabelsColor());
            double d = Math.toRadians(90F - (f2 + f3 / 2.0F));
            double d1 = Math.sin(d);
            double d2 = Math.cos(d);
            int i1 = Math.round((float)i + (float)(d1 * (double)f));
            int j1 = Math.round((float)j + (float)(d2 * (double)f));
            int k1 = Math.round((float)i + (float)(d1 * (double)f1));
            int l1 = Math.round((float)j + (float)(d2 * (double)f1));
            float f4 = defaultrenderer.getLabelsTextSize();
            float f5 = Math.max(f4 / 2.0F, 10F);
            paint.setTextAlign(android.graphics.Paint.Align.LEFT);
            if(i1 > k1)
            {
                f5 = -f5;
                paint.setTextAlign(android.graphics.Paint.Align.RIGHT);
            }
            float f6 = f5;
            float f7 = f6 + (float)k1;
            float f8 = l1;
            float f9 = (float)l - f7;
            if(i1 > k1)
                f9 = f7 - (float)k;
            String s1 = getFitText(s, f9, paint);
            float f10 = paint.measureText(s1);
            boolean flag = false;
            while(!flag) 
            {
                boolean flag1 = false;
                int j2 = list.size();
                int k2 = 0;
                float f11 = f8;
                while(k2 < j2 && !flag1) 
                {
                    RectF rectf = (RectF)list.get(k2);
                    int i2;
                    boolean flag2;
                    float f12;
                    if(rectf.intersects(f7, f11, f7 + f10, f11 + f4))
                    {
                        f12 = Math.max(f11, rectf.bottom);
                        flag2 = true;
                    } else
                    {
                        flag2 = flag1;
                        f12 = f11;
                    }
                    k2++;
                    f11 = f12;
                    flag1 = flag2;
                }
                if(!flag1)
                    flag = true;
                else
                    flag = false;
                f8 = f11;
            }
            i2 = (int)(f8 - f4 / 2.0F);
            canvas.drawLine(i1, j1, k1, i2, paint);
            canvas.drawLine(k1, i2, f6 + (float)k1, i2, paint);
            canvas.drawText(s1, f7, f8, paint);
            list.add(new RectF(f7, f8, f7 + f10, f8 + f4));
        }
    }

    protected int drawLegend(Canvas canvas, DefaultRenderer defaultrenderer, String as[], int i, int j, int k, int l, 
            int i1, int j1, Paint paint, boolean flag)
    {
        float f = 32F;
        if(defaultrenderer.isShowLegend())
        {
            float f1 = i;
            float f2 = f + (float)((k + i1) - j1);
            paint.setTextAlign(android.graphics.Paint.Align.LEFT);
            paint.setTextSize(defaultrenderer.getLegendTextSize());
            int k1 = Math.min(as.length, defaultrenderer.getSeriesRendererCount());
            int l1 = 0;
            while(l1 < k1) 
            {
                float f3 = getLegendShapeWidth(l1);
                String s = as[l1];
                float af[];
                float f4;
                int i2;
                if(as.length == defaultrenderer.getSeriesRendererCount())
                    paint.setColor(defaultrenderer.getSeriesRendererAt(l1).getColor());
                else
                    paint.setColor(0xffcccccc);
                af = new float[s.length()];
                paint.getTextWidths(s, af);
                f4 = 0.0F;
                i2 = af.length;
                for(int j2 = 0; j2 < i2; j2++)
                    f4 += af[j2];

                float f5 = f4 + (10F + f3);
                float f6 = f1 + f5;
                float f7;
                String s1;
                if(l1 > 0 && getExceed(f6, defaultrenderer, j, l))
                {
                    f1 = i;
                    f2 += defaultrenderer.getLegendTextSize();
                    float f9 = f + defaultrenderer.getLegendTextSize();
                    f6 = f1 + f5;
                    f7 = f9;
                } else
                {
                    f7 = f;
                }
                if(getExceed(f6, defaultrenderer, j, l))
                {
                    float f8 = (float)j - f1 - f3 - 10F;
                    if(isVertical(defaultrenderer))
                        f8 = (float)l - f1 - f3 - 10F;
                    int k2 = paint.breakText(s, true, f8, af);
                    s1 = (new StringBuilder()).append(s.substring(0, k2)).append("...").toString();
                } else
                {
                    s1 = s;
                }
                if(!flag)
                {
                    drawLegendShape(canvas, defaultrenderer.getSeriesRendererAt(l1), f1, f2, l1, paint);
                    canvas.drawText(s1, 5F + (f1 + f3), 5F + f2, paint);
                }
                f1 += f5;
                l1++;
                f = f7;
            }
        }
        return Math.round(f + defaultrenderer.getLegendTextSize());
    }

    public abstract void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleseriesrenderer, float f, float f1, int i, Paint paint);

    protected void drawPath(Canvas canvas, float af[], Paint paint, boolean flag)
    {
        Path path = new Path();
        path.moveTo(af[0], af[1]);
        for(int i = 2; i < af.length; i += 2)
            path.lineTo(af[i], af[i + 1]);

        if(flag)
            path.lineTo(af[0], af[1]);
        canvas.drawPath(path, paint);
    }

    protected boolean getExceed(float f, DefaultRenderer defaultrenderer, int i, int j)
    {
        boolean flag = true;
        boolean flag1;
        if(f > (float)i)
            flag1 = flag;
        else
            flag1 = false;
        if(isVertical(defaultrenderer))
        {
            if(f <= (float)j)
                flag = false;
        } else
        {
            flag = flag1;
        }
        return flag;
    }

    public abstract int getLegendShapeWidth(int i);

    protected int getLegendSize(DefaultRenderer defaultrenderer, int i, float f)
    {
        int j = defaultrenderer.getLegendHeight();
        if(!defaultrenderer.isShowLegend() || j != 0)
            i = j;
        if(!defaultrenderer.isShowLegend() && defaultrenderer.isShowLabels())
            i = (int)(f + (4F * defaultrenderer.getLabelsTextSize()) / 3F);
        return i;
    }

    public SeriesSelection getSeriesAndPointForScreenCoordinate(Point point)
    {
        return null;
    }

    protected boolean isVertical(DefaultRenderer defaultrenderer)
    {
        boolean flag;
        if((defaultrenderer instanceof XYMultipleSeriesRenderer) && ((XYMultipleSeriesRenderer)defaultrenderer).getOrientation() == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.VERTICAL)
            flag = true;
        else
            flag = false;
        return flag;
    }
}
