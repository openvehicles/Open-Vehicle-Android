// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine;

import android.graphics.RectF;
import android.view.MotionEvent;
import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.RoundChart;
import org.achartengine.chart.XYChart;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.tools.Pan;
import org.achartengine.tools.PanListener;
import org.achartengine.tools.ZoomListener;

// Referenced classes of package org.achartengine:
//            ITouchHandler, GraphicalView

public class TouchHandlerOld
    implements ITouchHandler
{

    public TouchHandlerOld(GraphicalView graphicalview, AbstractChart abstractchart)
    {
        zoomR = new RectF();
        graphicalView = graphicalview;
        zoomR = graphicalView.getZoomRectangle();
        if(abstractchart instanceof XYChart)
            mRenderer = ((XYChart)abstractchart).getRenderer();
        else
            mRenderer = ((RoundChart)abstractchart).getRenderer();
        if(mRenderer.isPanEnabled())
            mPan = new Pan((XYChart)abstractchart);
    }

    public void addPanListener(PanListener panlistener)
    {
        if(mPan != null)
            mPan.addPanListener(panlistener);
    }

    public void addZoomListener(ZoomListener zoomlistener)
    {
    }

    public boolean handleTouch(MotionEvent motionevent)
    {
        boolean flag;
        int i;
        flag = true;
        i = motionevent.getAction();
        if(mRenderer == null || i != 2) goto _L2; else goto _L1
_L1:
        if(oldX < 0.0F && oldY < 0.0F) goto _L4; else goto _L3
_L3:
        float f = motionevent.getX();
        float f1 = motionevent.getY();
        if(mRenderer.isPanEnabled())
            mPan.apply(oldX, oldY, f, f1);
        oldX = f;
        oldY = f1;
        graphicalView.repaint();
_L6:
        return flag;
_L2:
        if(i == 0)
        {
            oldX = motionevent.getX();
            oldY = motionevent.getY();
            if(mRenderer != null && mRenderer.isZoomEnabled() && zoomR.contains(oldX, oldY))
            {
                if(oldX < zoomR.left + zoomR.width() / 3F)
                    graphicalView.zoomIn();
                else
                if(oldX < zoomR.left + (2.0F * zoomR.width()) / 3F)
                    graphicalView.zoomOut();
                else
                    graphicalView.zoomReset();
                continue; /* Loop/switch isn't completed */
            }
        } else
        if(i == flag)
        {
            oldX = 0.0F;
            oldY = 0.0F;
        }
_L4:
        if(mRenderer.isClickEnabled())
            flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void removePanListener(PanListener panlistener)
    {
        if(mPan != null)
            mPan.removePanListener(panlistener);
    }

    public void removeZoomListener(ZoomListener zoomlistener)
    {
    }

    private GraphicalView graphicalView;
    private Pan mPan;
    private DefaultRenderer mRenderer;
    private float oldX;
    private float oldY;
    private RectF zoomR;
}
