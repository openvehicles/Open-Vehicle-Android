// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;

import android.graphics.*;
import java.util.ArrayList;
import java.util.List;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

// Referenced classes of package org.achartengine.chart:
//            RoundChart

public class DoughnutChart extends RoundChart
{

    public DoughnutChart(MultipleCategorySeries multiplecategoryseries, DefaultRenderer defaultrenderer)
    {
        super(null, defaultrenderer);
        mDataset = multiplecategoryseries;
    }

    public void draw(Canvas canvas, int i, int j, int k, int l, Paint paint)
    {
        paint.setAntiAlias(mRenderer.isAntialiasing());
        paint.setStyle(android.graphics.Paint.Style.FILL);
        paint.setTextSize(mRenderer.getLabelsTextSize());
        int i1 = getLegendSize(mRenderer, l / 5, 0.0F);
        int j1 = i + k;
        int k1 = mDataset.getCategoriesCount();
        String as[] = new String[k1];
        for(int l1 = 0; l1 < k1; l1++)
            as[l1] = mDataset.getCategory(l1);

        int i2;
        int j2;
        int k2;
        double d;
        double d1;
        int l2;
        float f;
        float f1;
        ArrayList arraylist;
        int i3;
        int j3;
        if(mRenderer.isFitLegend())
            i2 = drawLegend(canvas, mRenderer, as, i, j1, j, k, l, i1, paint, true);
        else
            i2 = i1;
        j2 = (j + l) - i2;
        drawBackground(mRenderer, canvas, i, j, k, l, paint, false, 0);
        mStep = 7;
        k2 = Math.min(Math.abs(j1 - i), Math.abs(j2 - j));
        d = 0.34999999999999998D * (double)mRenderer.getScale();
        d1 = 0.20000000000000001D / (double)k1;
        l2 = (int)(d * (double)k2);
        if(mCenterX == 0x7fffffff)
            mCenterX = (i + j1) / 2;
        if(mCenterY == 0x7fffffff)
            mCenterY = (j2 + j) / 2;
        f = 0.9F * (float)l2;
        f1 = 1.1F * (float)l2;
        arraylist = new ArrayList();
        i3 = 0;
        j3 = l2;
        while(i3 < k1) 
        {
            int k3 = mDataset.getItemCount(i3);
            double d2 = 0.0D;
            String as1[] = new String[k3];
            for(int l3 = 0; l3 < k3; l3++)
            {
                d2 += mDataset.getValues(i3)[l3];
                as1[l3] = mDataset.getTitles(i3)[l3];
            }

            float f2 = 0.0F;
            RectF rectf = new RectF(mCenterX - j3, mCenterY - j3, j3 + mCenterX, j3 + mCenterY);
            for(int i4 = 0; i4 < k3; i4++)
            {
                paint.setColor(mRenderer.getSeriesRendererAt(i4).getColor());
                float f3 = (float)(360D * ((double)(float)mDataset.getValues(i3)[i4] / d2));
                canvas.drawArc(rectf, f2, f3, true, paint);
                drawLabel(canvas, mDataset.getTitles(i3)[i4], mRenderer, arraylist, mCenterX, mCenterY, f, f1, f2, f3, i, j1, paint);
                f2 += f3;
            }

            int j4 = (int)((double)j3 - d1 * (double)k2);
            f = (float)((double)f - (d1 * (double)k2 - 2D));
            int k4;
            if(mRenderer.getBackgroundColor() != 0)
                paint.setColor(mRenderer.getBackgroundColor());
            else
                paint.setColor(-1);
            paint.setStyle(android.graphics.Paint.Style.FILL);
            canvas.drawArc(new RectF(mCenterX - j4, mCenterY - j4, j4 + mCenterX, j4 + mCenterY), 0.0F, 360F, true, paint);
            k4 = j4 - 1;
            i3++;
            j3 = k4;
        }
        arraylist.clear();
        drawLegend(canvas, mRenderer, as, i, j1, j, k, l, i2, paint, false);
        drawTitle(canvas, i, j, k, paint);
    }

    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer simpleseriesrenderer, float f, float f1, int i, Paint paint)
    {
        mStep = -1 + mStep;
        canvas.drawCircle((10F + f) - (float)mStep, f1, mStep, paint);
    }

    public int getLegendShapeWidth(int i)
    {
        return 10;
    }

    private MultipleCategorySeries mDataset;
    private int mStep;
}
