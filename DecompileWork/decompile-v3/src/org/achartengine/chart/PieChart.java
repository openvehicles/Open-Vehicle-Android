// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;

import android.graphics.*;
import java.util.ArrayList;
import java.util.List;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

// Referenced classes of package org.achartengine.chart:
//            RoundChart

public class PieChart extends RoundChart
{

    public PieChart(CategorySeries categoryseries, DefaultRenderer defaultrenderer)
    {
        super(categoryseries, defaultrenderer);
    }

    public void draw(Canvas canvas, int i, int j, int k, int l, Paint paint)
    {
        paint.setAntiAlias(mRenderer.isAntialiasing());
        paint.setStyle(android.graphics.Paint.Style.FILL);
        paint.setTextSize(mRenderer.getLabelsTextSize());
        int i1 = getLegendSize(mRenderer, l / 5, 0.0F);
        int j1 = i + k;
        int k1 = mDataset.getItemCount();
        String as[] = new String[k1];
        int l1 = 0;
        double d;
        double d1;
        for(d = 0.0D; l1 < k1; d = d1)
        {
            d1 = d + mDataset.getValue(l1);
            as[l1] = mDataset.getCategory(l1);
            l1++;
        }

        int i2;
        int j2;
        float f;
        int k2;
        float f1;
        float f2;
        RectF rectf;
        ArrayList arraylist;
        if(mRenderer.isFitLegend())
            i2 = drawLegend(canvas, mRenderer, as, i, j1, j, k, l, i1, paint, true);
        else
            i2 = i1;
        j2 = (j + l) - i2;
        drawBackground(mRenderer, canvas, i, j, k, l, paint, false, 0);
        f = 0.0F;
        k2 = (int)(0.34999999999999998D * (double)Math.min(Math.abs(j1 - i), Math.abs(j2 - j)) * (double)mRenderer.getScale());
        if(mCenterX == 0x7fffffff)
            mCenterX = (i + j1) / 2;
        if(mCenterY == 0x7fffffff)
            mCenterY = (j2 + j) / 2;
        f1 = 0.9F * (float)k2;
        f2 = 1.1F * (float)k2;
        rectf = new RectF(mCenterX - k2, mCenterY - k2, k2 + mCenterX, k2 + mCenterY);
        arraylist = new ArrayList();
        for(int l2 = 0; l2 < k1; l2++)
        {
            paint.setColor(mRenderer.getSeriesRendererAt(l2).getColor());
            float f3 = (float)(360D * ((double)(float)mDataset.getValue(l2) / d));
            canvas.drawArc(rectf, f, f3, true, paint);
            drawLabel(canvas, mDataset.getCategory(l2), mRenderer, arraylist, mCenterX, mCenterY, f1, f2, f, f3, i, j1, paint);
            f += f3;
        }

        arraylist.clear();
        drawLegend(canvas, mRenderer, as, i, j1, j, k, l, i2, paint, false);
        drawTitle(canvas, i, j, k, paint);
    }
}
