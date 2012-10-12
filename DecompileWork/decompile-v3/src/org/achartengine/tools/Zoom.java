// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.tools;

import java.util.*;
import org.achartengine.chart.*;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

// Referenced classes of package org.achartengine.tools:
//            AbstractTool, ZoomListener, ZoomEvent

public class Zoom extends AbstractTool
{

    public Zoom(AbstractChart abstractchart, boolean flag, float f)
    {
        super(abstractchart);
        mZoomListeners = new ArrayList();
        mZoomIn = flag;
        setZoomRate(f);
    }

    /**
     * @deprecated Method notifyZoomListeners is deprecated
     */

    private void notifyZoomListeners(ZoomEvent zoomevent)
    {
        this;
        JVM INSTR monitorenter ;
        for(Iterator iterator = mZoomListeners.iterator(); iterator.hasNext(); ((ZoomListener)iterator.next()).zoomApplied(zoomevent));
        break MISSING_BLOCK_LABEL_44;
        Exception exception;
        exception;
        throw exception;
        this;
        JVM INSTR monitorexit ;
    }

    /**
     * @deprecated Method addZoomListener is deprecated
     */

    public void addZoomListener(ZoomListener zoomlistener)
    {
        this;
        JVM INSTR monitorenter ;
        mZoomListeners.add(zoomlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void apply()
    {
        if(!(mChart instanceof XYChart)) goto _L2; else goto _L1
_L1:
        int i;
        int j;
        i = mRenderer.getScalesCount();
        j = 0;
_L4:
        double d2;
        double d3;
        double d4;
        double d5;
        if(j >= i)
            break; /* Loop/switch isn't completed */
        double ad[] = getRange(j);
        checkRange(ad, j);
        double ad1[] = mRenderer.getZoomLimits();
        boolean flag;
        double d;
        double d1;
        double d6;
        double d7;
        double d8;
        double d9;
        if(ad1 != null && ad1.length == 4)
            flag = true;
        else
            flag = false;
        d = (ad[0] + ad[1]) / 2D;
        d1 = (ad[2] + ad[3]) / 2D;
        d2 = ad[1] - ad[0];
        d3 = ad[3] - ad[2];
        if(mZoomIn)
        {
            if(mRenderer.isZoomXEnabled())
                d2 /= mZoomRate;
            if(!mRenderer.isZoomYEnabled())
                break MISSING_BLOCK_LABEL_424;
            d4 = d3 / (double)mZoomRate;
            d5 = d2;
        } else
        {
            if(mRenderer.isZoomXEnabled())
                d2 *= mZoomRate;
            if(!mRenderer.isZoomYEnabled())
                break MISSING_BLOCK_LABEL_424;
            d4 = d3 * (double)mZoomRate;
            d5 = d2;
        }
_L5:
        if(mRenderer.isZoomXEnabled())
        {
            d8 = d - d5 / 2D;
            d9 = d + d5 / 2D;
            if(!flag || ad1[0] <= d8 && ad1[1] >= d9)
                setXRange(d8, d9, j);
        }
        if(mRenderer.isZoomYEnabled())
        {
            d6 = d1 - d4 / 2D;
            d7 = d1 + d4 / 2D;
            if(!flag || ad1[2] <= d6 && ad1[3] >= d7)
                setYRange(d6, d7, j);
        }
        j++;
        if(true) goto _L4; else goto _L3
_L2:
        DefaultRenderer defaultrenderer = ((RoundChart)mChart).getRenderer();
        if(mZoomIn)
            defaultrenderer.setScale(defaultrenderer.getScale() * mZoomRate);
        else
            defaultrenderer.setScale(defaultrenderer.getScale() / mZoomRate);
_L3:
        notifyZoomListeners(new ZoomEvent(mZoomIn, mZoomRate));
        return;
        d4 = d3;
        d5 = d2;
          goto _L5
    }

    /**
     * @deprecated Method notifyZoomResetListeners is deprecated
     */

    public void notifyZoomResetListeners()
    {
        this;
        JVM INSTR monitorenter ;
        for(Iterator iterator = mZoomListeners.iterator(); iterator.hasNext(); ((ZoomListener)iterator.next()).zoomReset());
        break MISSING_BLOCK_LABEL_43;
        Exception exception;
        exception;
        throw exception;
        this;
        JVM INSTR monitorexit ;
    }

    /**
     * @deprecated Method removeZoomListener is deprecated
     */

    public void removeZoomListener(ZoomListener zoomlistener)
    {
        this;
        JVM INSTR monitorenter ;
        mZoomListeners.add(zoomlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setZoomRate(float f)
    {
        mZoomRate = f;
    }

    private boolean mZoomIn;
    private List mZoomListeners;
    private float mZoomRate;
}
