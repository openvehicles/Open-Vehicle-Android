// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine;

import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.RoundChart;
import org.achartengine.chart.XYChart;
import org.achartengine.model.Point;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.tools.FitZoom;
import org.achartengine.tools.PanListener;
import org.achartengine.tools.Zoom;
import org.achartengine.tools.ZoomListener;

// Referenced classes of package org.achartengine:
//            TouchHandlerOld, TouchHandler, ITouchHandler

public class GraphicalView extends View
{

    public GraphicalView(Context context, AbstractChart abstractchart)
    {
        int i;
        super(context);
        mRect = new Rect();
        mZoomR = new RectF();
        zoomSize = 50;
        mPaint = new Paint();
        mChart = abstractchart;
        mHandler = new Handler();
        int j;
        if(mChart instanceof XYChart)
            mRenderer = ((XYChart)mChart).getRenderer();
        else
            mRenderer = ((RoundChart)mChart).getRenderer();
        if(mRenderer.isZoomButtonsVisible())
        {
            zoomInImage = BitmapFactory.decodeStream(org/achartengine/GraphicalView.getResourceAsStream("image/zoom_in.png"));
            zoomOutImage = BitmapFactory.decodeStream(org/achartengine/GraphicalView.getResourceAsStream("image/zoom_out.png"));
            fitZoomImage = BitmapFactory.decodeStream(org/achartengine/GraphicalView.getResourceAsStream("image/zoom-1.png"));
        }
        if((mRenderer instanceof XYMultipleSeriesRenderer) && ((XYMultipleSeriesRenderer)mRenderer).getMarginsColor() == 0)
            ((XYMultipleSeriesRenderer)mRenderer).setMarginsColor(mPaint.getColor());
        if(mRenderer.isZoomEnabled() && mRenderer.isZoomButtonsVisible() || mRenderer.isExternalZoomEnabled())
        {
            mZoomIn = new Zoom(mChart, true, mRenderer.getZoomRate());
            mZoomOut = new Zoom(mChart, false, mRenderer.getZoomRate());
            mFitZoom = new FitZoom(mChart);
        }
        j = Integer.valueOf(android.os.Build.VERSION.SDK).intValue();
        i = j;
_L1:
        Exception exception;
        if(i < 7)
            mTouchHandler = new TouchHandlerOld(this, mChart);
        else
            mTouchHandler = new TouchHandler(this, mChart);
        return;
        exception;
        i = 7;
          goto _L1
    }

    public void addPanListener(PanListener panlistener)
    {
        mTouchHandler.addPanListener(panlistener);
    }

    public void addZoomListener(ZoomListener zoomlistener, boolean flag, boolean flag1)
    {
        if(flag)
        {
            if(mZoomIn != null)
            {
                mZoomIn.addZoomListener(zoomlistener);
                mZoomOut.addZoomListener(zoomlistener);
            }
            if(flag1)
                mTouchHandler.addZoomListener(zoomlistener);
        }
    }

    public SeriesSelection getCurrentSeriesAndPoint()
    {
        return mChart.getSeriesAndPointForScreenCoordinate(new Point(oldX, oldY));
    }

    protected RectF getZoomRectangle()
    {
        return mZoomR;
    }

    protected void onDraw(Canvas canvas)
    {
        int i = 0;
        super.onDraw(canvas);
        canvas.getClipBounds(mRect);
        int j = mRect.top;
        int k = mRect.left;
        int l = mRect.width();
        int i1 = mRect.height();
        if(mRenderer.isInScroll())
        {
            l = getMeasuredWidth();
            i1 = getMeasuredHeight();
            j = 0;
        } else
        {
            i = k;
        }
        mChart.draw(canvas, i, j, l, i1, mPaint);
        if(mRenderer != null && mRenderer.isZoomEnabled() && mRenderer.isZoomButtonsVisible())
        {
            mPaint.setColor(ZOOM_BUTTONS_COLOR);
            zoomSize = Math.max(zoomSize, Math.min(l, i1) / 7);
            mZoomR.set((i + l) - 3 * zoomSize, (float)(j + i1) - 0.775F * (float)zoomSize, i + l, j + i1);
            canvas.drawRoundRect(mZoomR, zoomSize / 3, zoomSize / 3, mPaint);
            float f = (float)(j + i1) - 0.625F * (float)zoomSize;
            canvas.drawBitmap(zoomInImage, (float)(i + l) - 2.75F * (float)zoomSize, f, null);
            canvas.drawBitmap(zoomOutImage, (float)(i + l) - 1.75F * (float)zoomSize, f, null);
            canvas.drawBitmap(fitZoomImage, (float)(i + l) - 0.75F * (float)zoomSize, f, null);
        }
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        if(motionevent.getAction() == 0)
        {
            oldX = motionevent.getX();
            oldY = motionevent.getY();
        }
        boolean flag;
        if(mRenderer != null && (mRenderer.isPanEnabled() || mRenderer.isZoomEnabled()) && mTouchHandler.handleTouch(motionevent))
            flag = true;
        else
            flag = super.onTouchEvent(motionevent);
        return flag;
    }

    public void removePanListener(PanListener panlistener)
    {
        mTouchHandler.removePanListener(panlistener);
    }

    /**
     * @deprecated Method removeZoomListener is deprecated
     */

    public void removeZoomListener(ZoomListener zoomlistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(mZoomIn != null)
        {
            mZoomIn.removeZoomListener(zoomlistener);
            mZoomOut.removeZoomListener(zoomlistener);
        }
        mTouchHandler.removeZoomListener(zoomlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void repaint()
    {
        mHandler.post(new Runnable() {

            public void run()
            {
                invalidate();
            }

            final GraphicalView this$0;

            
            {
                this$0 = GraphicalView.this;
                super();
            }
        }
);
    }

    public void repaint(final int left, final int top, final int right, final int bottom)
    {
        mHandler.post(new Runnable() {

            public void run()
            {
                invalidate(left, top, right, bottom);
            }

            final GraphicalView this$0;
            final int val$bottom;
            final int val$left;
            final int val$right;
            final int val$top;

            
            {
                this$0 = GraphicalView.this;
                left = i;
                top = j;
                right = k;
                bottom = l;
                super();
            }
        }
);
    }

    public void setZoomRate(float f)
    {
        if(mZoomIn != null && mZoomOut != null)
        {
            mZoomIn.setZoomRate(f);
            mZoomOut.setZoomRate(f);
        }
    }

    public Bitmap toBitmap()
    {
        setDrawingCacheEnabled(false);
        if(!isDrawingCacheEnabled())
            setDrawingCacheEnabled(true);
        if(mRenderer.isApplyBackgroundColor())
            setDrawingCacheBackgroundColor(mRenderer.getBackgroundColor());
        setDrawingCacheQuality(0x100000);
        return getDrawingCache(true);
    }

    public double[] toRealPoint(int i)
    {
        double ad[];
        if(mChart instanceof XYChart)
            ad = ((XYChart)mChart).toRealPoint(oldX, oldY, i);
        else
            ad = null;
        return ad;
    }

    public void zoomIn()
    {
        if(mZoomIn != null)
        {
            mZoomIn.apply();
            repaint();
        }
    }

    public void zoomOut()
    {
        if(mZoomOut != null)
        {
            mZoomOut.apply();
            repaint();
        }
    }

    public void zoomReset()
    {
        if(mFitZoom != null)
        {
            mFitZoom.apply();
            mZoomIn.notifyZoomResetListeners();
            repaint();
        }
    }

    private static final int ZOOM_BUTTONS_COLOR = Color.argb(175, 150, 150, 150);
    private Bitmap fitZoomImage;
    private AbstractChart mChart;
    private FitZoom mFitZoom;
    private Handler mHandler;
    private Paint mPaint;
    private Rect mRect;
    private DefaultRenderer mRenderer;
    private ITouchHandler mTouchHandler;
    private Zoom mZoomIn;
    private Zoom mZoomOut;
    private RectF mZoomR;
    private float oldX;
    private float oldY;
    private Bitmap zoomInImage;
    private Bitmap zoomOutImage;
    private int zoomSize;

}
