// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.chart;

import android.graphics.*;
import java.util.*;
import org.achartengine.model.*;
import org.achartengine.renderer.*;
import org.achartengine.util.MathHelper;

// Referenced classes of package org.achartengine.chart:
//            AbstractChart, ScatterChart

public abstract class XYChart extends AbstractChart
{

    protected XYChart()
    {
        mCalcRange = new HashMap();
        clickableAreas = new HashMap();
    }

    public XYChart(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        mCalcRange = new HashMap();
        clickableAreas = new HashMap();
        mDataset = xymultipleseriesdataset;
        mRenderer = xymultipleseriesrenderer;
    }

    private int getLabelLinePos(android.graphics.Paint.Align align)
    {
        int i = 4;
        if(align == android.graphics.Paint.Align.LEFT)
            i = -i;
        return i;
    }

    private List getValidLabels(List list)
    {
        ArrayList arraylist = new ArrayList(list);
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Double double1 = (Double)iterator.next();
            if(double1.isNaN())
                arraylist.remove(double1);
        } while(true);
        return arraylist;
    }

    private void setStroke(android.graphics.Paint.Cap cap, android.graphics.Paint.Join join, float f, android.graphics.Paint.Style style, PathEffect patheffect, Paint paint)
    {
        paint.setStrokeCap(cap);
        paint.setStrokeJoin(join);
        paint.setStrokeMiter(f);
        paint.setPathEffect(patheffect);
        paint.setStyle(style);
    }

    private void transform(Canvas canvas, float f, boolean flag)
    {
        if(flag)
        {
            canvas.scale(1.0F / mScale, mScale);
            canvas.translate(mTranslate, -mTranslate);
            canvas.rotate(-f, mCenter.getX(), mCenter.getY());
        } else
        {
            canvas.rotate(f, mCenter.getX(), mCenter.getY());
            canvas.translate(-mTranslate, mTranslate);
            canvas.scale(mScale, 1.0F / mScale);
        }
    }

    protected abstract RectF[] clickableAreasForPoints(float af[], float f, int i);

    public void draw(Canvas canvas, int i, int j, int k, int l, Paint paint)
    {
        paint.setAntiAlias(mRenderer.isAntialiasing());
        int i1 = getLegendSize(mRenderer, l / 5, mRenderer.getAxisTitleTextSize());
        int ai[] = mRenderer.getMargins();
        int j1 = i + ai[1];
        int k1 = j + ai[0];
        int l1 = (i + k) - ai[3];
        int i2 = mDataset.getSeriesCount();
        String as[] = new String[i2];
        for(int j2 = 0; j2 < i2; j2++)
            as[j2] = mDataset.getSeriesAt(j2).getTitle();

        int k2;
        int l2;
        org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation orientation;
        int i3;
        int j3;
        boolean flag;
        int k3;
        int i4;
        double ad[];
        double ad1[];
        double ad2[];
        double ad3[];
        boolean aflag[];
        boolean aflag1[];
        boolean aflag2[];
        boolean aflag3[];
        int j4;
        double ad4[];
        double ad5[];
        int k4;
        int l4;
        boolean flag1;
        int i5;
        XYMultipleSeriesRenderer xymultipleseriesrenderer;
        int j5;
        int k5;
        int l5;
        boolean flag2;
        boolean flag3;
        boolean flag4;
        List list;
        HashMap hashmap;
        int i6;
        int j6;
        Double adouble[];
        double d;
        double d1;
        double d2;
        int k6;
        boolean flag5;
        int l6;
        boolean flag6;
        float f;
        int i7;
        int j7;
        android.graphics.Paint.Align align;
        Double adouble1[];
        int k7;
        int l7;
        Double double1;
        float f1;
        String s;
        List list1;
        int i8;
        int j8;
        double d3;
        android.graphics.Paint.Align align1;
        boolean flag7;
        float f2;
        XYMultipleSeriesRenderer xymultipleseriesrenderer1;
        int k8;
        int l8;
        int i9;
        XYSeries xyseries;
        int j9;
        SimpleSeriesRenderer simpleseriesrenderer;
        int k9;
        ArrayList arraylist;
        float f3;
        LinkedList linkedlist;
        int l9;
        int i10;
        double d4;
        XYSeries xyseries1;
        int j10;
        double d5;
        double d6;
        double d7;
        double d8;
        if(mRenderer.isFitLegend() && mRenderer.isShowLegend())
            k2 = drawLegend(canvas, mRenderer, as, j1, l1, j, k, l, i1, paint, true);
        else
            k2 = i1;
        l2 = (j + l) - ai[2] - k2;
        if(mScreenR == null)
            mScreenR = new Rect();
        mScreenR.set(j1, k1, l1, l2);
        drawBackground(mRenderer, canvas, i, j, k, l, paint, false, 0);
        if(paint.getTypeface() == null || !paint.getTypeface().toString().equals(mRenderer.getTextTypefaceName()) || paint.getTypeface().getStyle() != mRenderer.getTextTypefaceStyle())
            paint.setTypeface(Typeface.create(mRenderer.getTextTypefaceName(), mRenderer.getTextTypefaceStyle()));
        orientation = mRenderer.getOrientation();
        if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.VERTICAL)
        {
            int k10 = l1 - k2;
            l2 += k2 - 20;
            i3 = k10;
        } else
        {
            i3 = l1;
        }
        j3 = orientation.getAngle();
        if(j3 == 90)
            flag = true;
        else
            flag = false;
        mScale = (float)l / (float)k;
        mTranslate = Math.abs(k - l) / 2;
        if(mScale < 1.0F)
            mTranslate = -1F * mTranslate;
        mCenter = new Point((i + k) / 2, (j + l) / 2);
        if(flag)
            transform(canvas, j3, false);
        k3 = 0x80000001;
        for(int l3 = 0; l3 < i2; l3++)
            k3 = Math.max(k3, mDataset.getSeriesAt(l3).getScaleNumber());

        i4 = k3 + 1;
        if(i4 >= 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        ad = new double[i4];
        ad1 = new double[i4];
        ad2 = new double[i4];
        ad3 = new double[i4];
        aflag = new boolean[i4];
        aflag1 = new boolean[i4];
        aflag2 = new boolean[i4];
        aflag3 = new boolean[i4];
        for(j4 = 0; j4 < i4; j4++)
        {
            ad[j4] = mRenderer.getXAxisMin(j4);
            ad1[j4] = mRenderer.getXAxisMax(j4);
            ad2[j4] = mRenderer.getYAxisMin(j4);
            ad3[j4] = mRenderer.getYAxisMax(j4);
            aflag[j4] = mRenderer.isMinXSet(j4);
            aflag1[j4] = mRenderer.isMaxXSet(j4);
            aflag2[j4] = mRenderer.isMinYSet(j4);
            aflag3[j4] = mRenderer.isMaxYSet(j4);
            if(mCalcRange.get(Integer.valueOf(j4)) == null)
                mCalcRange.put(Integer.valueOf(j4), new double[4]);
        }

        ad4 = new double[i4];
        ad5 = new double[i4];
        k4 = 0;
        while(k4 < i2) 
        {
            xyseries1 = mDataset.getSeriesAt(k4);
            j10 = xyseries1.getScaleNumber();
            if(xyseries1.getItemCount() != 0)
            {
                if(!aflag[j10])
                {
                    d8 = xyseries1.getMinX();
                    ad[j10] = Math.min(ad[j10], d8);
                    ((double[])mCalcRange.get(Integer.valueOf(j10)))[0] = ad[j10];
                }
                if(!aflag1[j10])
                {
                    d7 = xyseries1.getMaxX();
                    ad1[j10] = Math.max(ad1[j10], d7);
                    ((double[])mCalcRange.get(Integer.valueOf(j10)))[1] = ad1[j10];
                }
                if(!aflag2[j10])
                {
                    d6 = xyseries1.getMinY();
                    ad2[j10] = Math.min(ad2[j10], (float)d6);
                    ((double[])mCalcRange.get(Integer.valueOf(j10)))[2] = ad2[j10];
                }
                if(!aflag3[j10])
                {
                    d5 = xyseries1.getMaxY();
                    ad3[j10] = Math.max(ad3[j10], (float)d5);
                    ((double[])mCalcRange.get(Integer.valueOf(j10)))[3] = ad3[j10];
                }
            }
            k4++;
        }
        for(l4 = 0; l4 < i4; l4++)
        {
            if(ad1[l4] - ad[l4] != 0.0D)
                ad4[l4] = (double)(i3 - j1) / (ad1[l4] - ad[l4]);
            if(ad3[l4] - ad2[l4] != 0.0D)
                ad5[l4] = (float)((double)(l2 - k1) / (ad3[l4] - ad2[l4]));
        }

        flag1 = false;
        clickableAreas = new HashMap();
        i5 = 0;
        while(i5 < i2) 
        {
            xyseries = mDataset.getSeriesAt(i5);
            j9 = xyseries.getScaleNumber();
            if(xyseries.getItemCount() != 0)
            {
                flag1 = true;
                simpleseriesrenderer = mRenderer.getSeriesRendererAt(i5);
                k9 = 2 * xyseries.getItemCount();
                arraylist = new ArrayList();
                f3 = Math.min(l2, (float)((double)l2 + ad5[j9] * ad2[j9]));
                linkedlist = new LinkedList();
                clickableAreas.put(Integer.valueOf(i5), linkedlist);
                l9 = 0;
                while(l9 < k9) 
                {
                    i10 = l9 / 2;
                    d4 = xyseries.getY(i10);
                    if(d4 != 1.7976931348623157E+308D)
                    {
                        arraylist.add(Float.valueOf((float)((double)j1 + ad4[j9] * (xyseries.getX(i10) - ad[j9]))));
                        arraylist.add(Float.valueOf((float)((double)l2 - ad5[j9] * (d4 - ad2[j9]))));
                    } else
                    if(isRenderNullValues())
                    {
                        arraylist.add(Float.valueOf((float)((double)j1 + ad4[j9] * (xyseries.getX(i10) - ad[j9]))));
                        arraylist.add(Float.valueOf((float)((double)l2 - ad5[j9] * -ad2[j9])));
                    } else
                    {
                        if(arraylist.size() > 0)
                        {
                            drawSeries(xyseries, canvas, paint, arraylist, simpleseriesrenderer, f3, i5, orientation);
                            linkedlist.addAll(Arrays.asList(clickableAreasForPoints(MathHelper.getFloats(arraylist), f3, i5)));
                            arraylist.clear();
                        }
                        linkedlist.add(null);
                    }
                    l9 += 2;
                }
                if(arraylist.size() > 0)
                {
                    drawSeries(xyseries, canvas, paint, arraylist, simpleseriesrenderer, f3, i5, orientation);
                    linkedlist.addAll(Arrays.asList(clickableAreasForPoints(MathHelper.getFloats(arraylist), f3, i5)));
                }
            }
            i5++;
        }
        drawBackground(mRenderer, canvas, i, l2, k, l - l2, paint, true, mRenderer.getMarginsColor());
        drawBackground(mRenderer, canvas, i, j, k, ai[0], paint, true, mRenderer.getMarginsColor());
        if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.HORIZONTAL)
        {
            drawBackground(mRenderer, canvas, i, j, j1 - i, l - j, paint, true, mRenderer.getMarginsColor());
            xymultipleseriesrenderer1 = mRenderer;
            k8 = ai[3];
            l8 = l - j;
            i9 = mRenderer.getMarginsColor();
            drawBackground(xymultipleseriesrenderer1, canvas, i3, j, k8, l8, paint, true, i9);
        } else
        if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.VERTICAL)
        {
            xymultipleseriesrenderer = mRenderer;
            j5 = k - i3;
            k5 = l - j;
            l5 = mRenderer.getMarginsColor();
            drawBackground(xymultipleseriesrenderer, canvas, i3, j, j5, k5, paint, true, l5);
            drawBackground(mRenderer, canvas, i, j, j1 - i, l - j, paint, true, mRenderer.getMarginsColor());
        }
        if(mRenderer.isShowLabels() && flag1)
            flag2 = true;
        else
            flag2 = false;
        flag3 = mRenderer.isShowGrid();
        flag4 = mRenderer.isShowCustomTextGrid();
        if(!flag2 && !flag3) goto _L4; else goto _L3
_L3:
        list = getValidLabels(MathHelper.getLabels(ad[0], ad1[0], mRenderer.getXLabels()));
        hashmap = new HashMap();
        for(i6 = 0; i6 < i4; i6++)
            hashmap.put(Integer.valueOf(i6), getValidLabels(MathHelper.getLabels(ad2[i6], ad3[i6], mRenderer.getYLabels())));

        if(!flag2)
            break MISSING_BLOCK_LABEL_3455;
        paint.setColor(mRenderer.getLabelsColor());
        paint.setTextSize(mRenderer.getLabelsTextSize());
        paint.setTextAlign(mRenderer.getXLabelsAlign());
        if(mRenderer.getXLabelsAlign() != android.graphics.Paint.Align.LEFT)
            break MISSING_BLOCK_LABEL_3455;
        j6 = (int)((float)j1 + mRenderer.getLabelsTextSize() / 4F);
_L5:
        adouble = mRenderer.getXTextLabelLocations();
        d = ad4[0];
        d1 = ad[0];
        d2 = ad1[0];
        drawXLabels(list, adouble, canvas, paint, j6, k1, l2, d, d1, d2);
        k6 = 0;
        do
        {
            if(k6 >= i4)
                break;
            paint.setTextAlign(mRenderer.getYLabelsAlign(k6));
            list1 = (List)hashmap.get(Integer.valueOf(k6));
            i8 = list1.size();
            j8 = 0;
            while(j8 < i8) 
            {
                d3 = ((Double)list1.get(j8)).doubleValue();
                align1 = mRenderer.getYAxisAlign(k6);
                if(mRenderer.getYTextLabel(Double.valueOf(d3), k6) != null)
                    flag7 = true;
                else
                    flag7 = false;
                f2 = (float)((double)l2 - ad5[k6] * (d3 - ad2[k6]));
                if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.HORIZONTAL)
                {
                    if(flag2 && !flag7)
                    {
                        paint.setColor(mRenderer.getLabelsColor());
                        if(align1 == android.graphics.Paint.Align.LEFT)
                        {
                            canvas.drawLine(j1 + getLabelLinePos(align1), f2, j1, f2, paint);
                            drawText(canvas, getLabel(d3), j1, f2 - 2.0F, paint, mRenderer.getYLabelsAngle());
                        } else
                        {
                            canvas.drawLine(i3, f2, i3 + getLabelLinePos(align1), f2, paint);
                            drawText(canvas, getLabel(d3), i3, f2 - 2.0F, paint, mRenderer.getYLabelsAngle());
                        }
                    }
                    if(flag3)
                    {
                        paint.setColor(mRenderer.getGridColor());
                        canvas.drawLine(j1, f2, i3, f2, paint);
                    }
                } else
                if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.VERTICAL)
                {
                    if(flag2 && !flag7)
                    {
                        paint.setColor(mRenderer.getLabelsColor());
                        canvas.drawLine(i3 - getLabelLinePos(align1), f2, i3, f2, paint);
                        drawText(canvas, getLabel(d3), i3 + 10, f2 - 2.0F, paint, mRenderer.getYLabelsAngle());
                    }
                    if(flag3)
                    {
                        paint.setColor(mRenderer.getGridColor());
                        canvas.drawLine(i3, f2, j1, f2, paint);
                    }
                }
                j8++;
            }
            k6++;
        } while(true);
        if(flag2)
        {
            paint.setColor(mRenderer.getLabelsColor());
            j7 = 0;
            do
            {
                if(j7 >= i4)
                    break;
                align = mRenderer.getYAxisAlign(j7);
                adouble1 = mRenderer.getYTextLabelLocations(j7);
                k7 = adouble1.length;
                l7 = 0;
                while(l7 < k7) 
                {
                    double1 = adouble1[l7];
                    if(ad2[j7] <= double1.doubleValue() && double1.doubleValue() <= ad3[j7])
                    {
                        f1 = (float)((double)l2 - ad5[j7] * (double1.doubleValue() - ad2[j7]));
                        s = mRenderer.getYTextLabel(double1, j7);
                        paint.setColor(mRenderer.getLabelsColor());
                        if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.HORIZONTAL)
                        {
                            if(align == android.graphics.Paint.Align.LEFT)
                            {
                                canvas.drawLine(j1 + getLabelLinePos(align), f1, j1, f1, paint);
                                drawText(canvas, s, j1, f1 - 2.0F, paint, mRenderer.getYLabelsAngle());
                            } else
                            {
                                canvas.drawLine(i3, f1, i3 + getLabelLinePos(align), f1, paint);
                                drawText(canvas, s, i3, f1 - 2.0F, paint, mRenderer.getYLabelsAngle());
                            }
                            if(flag4)
                            {
                                paint.setColor(mRenderer.getGridColor());
                                canvas.drawLine(j1, f1, i3, f1, paint);
                            }
                        } else
                        {
                            canvas.drawLine(i3 - getLabelLinePos(align), f1, i3, f1, paint);
                            drawText(canvas, s, i3 + 10, f1 - 2.0F, paint, mRenderer.getYLabelsAngle());
                            if(flag4)
                            {
                                paint.setColor(mRenderer.getGridColor());
                                canvas.drawLine(i3, f1, j1, f1, paint);
                            }
                        }
                    }
                    l7++;
                }
                j7++;
            } while(true);
        }
        if(flag2)
        {
            paint.setColor(mRenderer.getLabelsColor());
            f = mRenderer.getAxisTitleTextSize();
            paint.setTextSize(f);
            paint.setTextAlign(android.graphics.Paint.Align.CENTER);
            if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.HORIZONTAL)
            {
                drawText(canvas, mRenderer.getXTitle(), i + k / 2, f + ((float)l2 + (4F * mRenderer.getLabelsTextSize()) / 3F), paint, 0.0F);
                i7 = 0;
                while(i7 < i4) 
                {
                    if(mRenderer.getYAxisAlign(i7) == android.graphics.Paint.Align.LEFT)
                        drawText(canvas, mRenderer.getYTitle(i7), f + (float)i, j + l / 2, paint, -90F);
                    else
                        drawText(canvas, mRenderer.getYTitle(i7), i + k, j + l / 2, paint, -90F);
                    i7++;
                }
                paint.setTextSize(mRenderer.getChartTitleTextSize());
                drawText(canvas, mRenderer.getChartTitle(), i + k / 2, (float)j + mRenderer.getChartTitleTextSize(), paint, 0.0F);
            } else
            if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.VERTICAL)
            {
                drawText(canvas, mRenderer.getXTitle(), i + k / 2, (float)(j + l) - f, paint, -90F);
                drawText(canvas, mRenderer.getYTitle(), i3 + 20, j + l / 2, paint, 0.0F);
                paint.setTextSize(mRenderer.getChartTitleTextSize());
                drawText(canvas, mRenderer.getChartTitle(), f + (float)i, k1 + l / 2, paint, 0.0F);
            }
        }
_L4:
        if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.HORIZONTAL)
            drawLegend(canvas, mRenderer, as, j1, i3, j, k, l, k2, paint, false);
        else
        if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.VERTICAL)
        {
            transform(canvas, j3, true);
            drawLegend(canvas, mRenderer, as, j1, i3, j, k, l, k2, paint, false);
            transform(canvas, j3, false);
        }
        if(mRenderer.isShowAxes())
        {
            paint.setColor(mRenderer.getAxesColor());
            canvas.drawLine(j1, l2, i3, l2, paint);
            flag5 = false;
            l6 = 0;
            while(l6 < i4 && !flag5) 
            {
                if(mRenderer.getYAxisAlign(l6) == android.graphics.Paint.Align.RIGHT)
                    flag6 = true;
                else
                    flag6 = false;
                l6++;
                flag5 = flag6;
            }
            if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.HORIZONTAL)
            {
                canvas.drawLine(j1, k1, j1, l2, paint);
                if(flag5)
                    canvas.drawLine(i3, k1, i3, l2, paint);
            } else
            if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.VERTICAL)
                canvas.drawLine(i3, k1, i3, l2, paint);
        }
        if(flag)
            transform(canvas, j3, true);
          goto _L1
        j6 = j1;
          goto _L5
    }

    protected void drawChartValuesText(Canvas canvas, XYSeries xyseries, SimpleSeriesRenderer simpleseriesrenderer, Paint paint, float af[], int i)
    {
        for(int j = 0; j < af.length; j += 2)
            drawText(canvas, getLabel(xyseries.getY(j / 2)), af[j], af[j + 1] - simpleseriesrenderer.getChartValuesSpacing(), paint, 0.0F);

    }

    public abstract void drawSeries(Canvas canvas, Paint paint, float af[], SimpleSeriesRenderer simpleseriesrenderer, float f, int i);

    protected void drawSeries(XYSeries xyseries, Canvas canvas, Paint paint, List list, SimpleSeriesRenderer simpleseriesrenderer, float f, int i, 
            org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation orientation)
    {
        BasicStroke basicstroke = simpleseriesrenderer.getStroke();
        android.graphics.Paint.Cap cap = paint.getStrokeCap();
        android.graphics.Paint.Join join = paint.getStrokeJoin();
        float f1 = paint.getStrokeMiter();
        PathEffect patheffect = paint.getPathEffect();
        android.graphics.Paint.Style style = paint.getStyle();
        if(basicstroke != null)
        {
            DashPathEffect dashpatheffect = null;
            if(basicstroke.getIntervals() != null)
                dashpatheffect = new DashPathEffect(basicstroke.getIntervals(), basicstroke.getPhase());
            setStroke(basicstroke.getCap(), basicstroke.getJoin(), basicstroke.getMiter(), android.graphics.Paint.Style.FILL_AND_STROKE, dashpatheffect, paint);
        }
        float af[] = MathHelper.getFloats(list);
        drawSeries(canvas, paint, af, simpleseriesrenderer, f, i);
        if(isRenderPoints(simpleseriesrenderer))
        {
            ScatterChart scatterchart = getPointsChart();
            if(scatterchart != null)
                scatterchart.drawSeries(canvas, paint, af, simpleseriesrenderer, f, i);
        }
        paint.setTextSize(simpleseriesrenderer.getChartValuesTextSize());
        if(orientation == org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation.HORIZONTAL)
            paint.setTextAlign(android.graphics.Paint.Align.CENTER);
        else
            paint.setTextAlign(android.graphics.Paint.Align.LEFT);
        if(simpleseriesrenderer.isDisplayChartValues())
        {
            paint.setTextAlign(simpleseriesrenderer.getChartValuesTextAlign());
            drawChartValuesText(canvas, xyseries, simpleseriesrenderer, paint, af, i);
        }
        if(basicstroke != null)
            setStroke(cap, join, f1, style, patheffect, paint);
    }

    protected void drawText(Canvas canvas, String s, float f, float f1, Paint paint, float f2)
    {
        float f3 = f2 + (float)(-mRenderer.getOrientation().getAngle());
        if(f3 != 0.0F)
            canvas.rotate(f3, f, f1);
        canvas.drawText(s, f, f1, paint);
        if(f3 != 0.0F)
            canvas.rotate(-f3, f, f1);
    }

    protected void drawXLabels(List list, Double adouble[], Canvas canvas, Paint paint, int i, int j, int k, 
            double d, double d1, double d2)
    {
        int l = list.size();
        boolean flag = mRenderer.isShowLabels();
        boolean flag1 = mRenderer.isShowGrid();
        for(int i1 = 0; i1 < l; i1++)
        {
            double d3 = ((Double)list.get(i1)).doubleValue();
            float f = (float)((double)i + d * (d3 - d1));
            if(flag)
            {
                paint.setColor(mRenderer.getLabelsColor());
                canvas.drawLine(f, k, f, (float)k + mRenderer.getLabelsTextSize() / 3F, paint);
                drawText(canvas, getLabel(d3), f, (float)k + (4F * mRenderer.getLabelsTextSize()) / 3F, paint, mRenderer.getXLabelsAngle());
            }
            if(flag1)
            {
                paint.setColor(mRenderer.getGridColor());
                canvas.drawLine(f, k, f, j, paint);
            }
        }

        drawXTextLabels(adouble, canvas, paint, flag, i, j, k, d, d1, d2);
    }

    protected void drawXTextLabels(Double adouble[], Canvas canvas, Paint paint, boolean flag, int i, int j, int k, 
            double d, double d1, double d2)
    {
        boolean flag1 = mRenderer.isShowCustomTextGrid();
        if(flag)
        {
            paint.setColor(mRenderer.getLabelsColor());
            int l = adouble.length;
            for(int i1 = 0; i1 < l; i1++)
            {
                Double double1 = adouble[i1];
                if(d1 > double1.doubleValue() || double1.doubleValue() > d2)
                    continue;
                float f = (float)((double)i + d * (double1.doubleValue() - d1));
                paint.setColor(mRenderer.getLabelsColor());
                canvas.drawLine(f, k, f, (float)k + mRenderer.getLabelsTextSize() / 3F, paint);
                drawText(canvas, mRenderer.getXTextLabel(double1), f, (float)k + (4F * mRenderer.getLabelsTextSize()) / 3F, paint, mRenderer.getXLabelsAngle());
                if(flag1)
                {
                    paint.setColor(mRenderer.getGridColor());
                    canvas.drawLine(f, k, f, j, paint);
                }
            }

        }
    }

    public double[] getCalcRange(int i)
    {
        return (double[])mCalcRange.get(Integer.valueOf(i));
    }

    public abstract String getChartType();

    public XYMultipleSeriesDataset getDataset()
    {
        return mDataset;
    }

    public double getDefaultMinimum()
    {
        return 1.7976931348623157E+308D;
    }

    protected String getLabel(double d)
    {
        String s;
        if(d == (double)Math.round(d))
            s = (new StringBuilder()).append(Math.round(d)).append("").toString();
        else
            s = (new StringBuilder()).append(d).append("").toString();
        return s;
    }

    public ScatterChart getPointsChart()
    {
        return null;
    }

    public XYMultipleSeriesRenderer getRenderer()
    {
        return mRenderer;
    }

    protected Rect getScreenR()
    {
        return mScreenR;
    }

    public SeriesSelection getSeriesAndPointForScreenCoordinate(Point point)
    {
        int i;
        if(clickableAreas == null)
            break MISSING_BLOCK_LABEL_158;
        i = -1 + clickableAreas.size();
_L6:
        if(i < 0) goto _L2; else goto _L1
_L1:
        int j;
        Iterator iterator;
        j = 0;
        if(clickableAreas.get(Integer.valueOf(i)) == null)
            continue; /* Loop/switch isn't completed */
        iterator = ((List)clickableAreas.get(Integer.valueOf(i))).iterator();
_L5:
        RectF rectf;
        if(!iterator.hasNext())
            continue; /* Loop/switch isn't completed */
        rectf = (RectF)iterator.next();
        if(rectf == null || !rectf.contains(point.getX(), point.getY())) goto _L4; else goto _L3
_L3:
        SeriesSelection seriesselection;
        XYSeries xyseries = mDataset.getSeriesAt(i);
        seriesselection = new SeriesSelection(i, j, xyseries.getX(j), xyseries.getY(j));
_L7:
        return seriesselection;
_L4:
        j++;
          goto _L5
        i--;
          goto _L6
_L2:
        seriesselection = super.getSeriesAndPointForScreenCoordinate(point);
          goto _L7
    }

    protected boolean isRenderNullValues()
    {
        return false;
    }

    public boolean isRenderPoints(SimpleSeriesRenderer simpleseriesrenderer)
    {
        return false;
    }

    public void setCalcRange(double ad[], int i)
    {
        mCalcRange.put(Integer.valueOf(i), ad);
    }

    protected void setDatasetRenderer(XYMultipleSeriesDataset xymultipleseriesdataset, XYMultipleSeriesRenderer xymultipleseriesrenderer)
    {
        mDataset = xymultipleseriesdataset;
        mRenderer = xymultipleseriesrenderer;
    }

    protected void setScreenR(Rect rect)
    {
        mScreenR = rect;
    }

    public double[] toRealPoint(float f, float f1)
    {
        return toRealPoint(f, f1, 0);
    }

    public double[] toRealPoint(float f, float f1, int i)
    {
        double d = mRenderer.getXAxisMin(i);
        double d1 = mRenderer.getXAxisMax(i);
        double d2 = mRenderer.getYAxisMin(i);
        double d3 = mRenderer.getYAxisMax(i);
        double ad[] = new double[2];
        ad[0] = d + ((double)(f - (float)mScreenR.left) * (d1 - d)) / (double)mScreenR.width();
        ad[1] = d2 + ((double)((float)(mScreenR.top + mScreenR.height()) - f1) * (d3 - d2)) / (double)mScreenR.height();
        return ad;
    }

    public double[] toScreenPoint(double ad[])
    {
        return toScreenPoint(ad, 0);
    }

    public double[] toScreenPoint(double ad[], int i)
    {
        double d = mRenderer.getXAxisMin(i);
        double d1 = mRenderer.getXAxisMax(i);
        double d2 = mRenderer.getYAxisMin(i);
        double d3 = mRenderer.getYAxisMax(i);
        if(!mRenderer.isMinXSet(i) || !mRenderer.isMaxXSet(i) || !mRenderer.isMinXSet(i) || !mRenderer.isMaxYSet(i))
        {
            double ad1[] = getCalcRange(i);
            d = ad1[0];
            d1 = ad1[1];
            d2 = ad1[2];
            d3 = ad1[3];
        }
        double ad2[] = new double[2];
        ad2[0] = ((ad[0] - d) * (double)mScreenR.width()) / (d1 - d) + (double)mScreenR.left;
        ad2[1] = ((d3 - ad[1]) * (double)mScreenR.height()) / (d3 - d2) + (double)mScreenR.top;
        return ad2;
    }

    private Map clickableAreas;
    private Map mCalcRange;
    private Point mCenter;
    protected XYMultipleSeriesDataset mDataset;
    protected XYMultipleSeriesRenderer mRenderer;
    private float mScale;
    private Rect mScreenR;
    private float mTranslate;
}
