// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.tools;

import java.util.*;
import org.achartengine.chart.*;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

// Referenced classes of package org.achartengine.tools:
//            AbstractTool, PanListener

public class Pan extends AbstractTool
{

    public Pan(AbstractChart abstractchart)
    {
        super(abstractchart);
        mPanListeners = new ArrayList();
    }

    /**
     * @deprecated Method notifyPanListeners is deprecated
     */

    private void notifyPanListeners()
    {
        this;
        JVM INSTR monitorenter ;
        for(Iterator iterator = mPanListeners.iterator(); iterator.hasNext(); ((PanListener)iterator.next()).panApplied());
        break MISSING_BLOCK_LABEL_43;
        Exception exception;
        exception;
        throw exception;
        this;
        JVM INSTR monitorexit ;
    }

    /**
     * @deprecated Method addPanListener is deprecated
     */

    public void addPanListener(PanListener panlistener)
    {
        this;
        JVM INSTR monitorenter ;
        mPanListeners.add(panlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void apply(float f, float f1, float f2, float f3)
    {
        if(!(mChart instanceof XYChart)) goto _L2; else goto _L1
_L1:
        double ad[];
        boolean flag;
        XYChart xychart;
        int j;
        double ad1[];
        int i = mRenderer.getScalesCount();
        ad = mRenderer.getPanLimits();
        double ad2[];
        if(ad != null && ad.length == 4)
            flag = true;
        else
            flag = false;
        xychart = (XYChart)mChart;
        j = 0;
_L5:
        if(j >= i)
            break MISSING_BLOCK_LABEL_465;
        ad1 = getRange(j);
        ad2 = xychart.getCalcRange(j);
        if((ad1[0] != ad1[1] || ad2[0] != ad2[1]) && (ad1[2] != ad1[3] || ad2[2] != ad2[3])) goto _L4; else goto _L3
_L3:
        return;
_L4:
        checkRange(ad1, j);
        double ad3[] = xychart.toRealPoint(f, f1, j);
        double ad4[] = xychart.toRealPoint(f2, f3, j);
        double d = ad3[0] - ad4[0];
        double d1 = ad3[1] - ad4[1];
        if(mRenderer.isPanXEnabled())
            if(flag)
            {
                boolean flag3;
                boolean flag4;
                if(ad[0] <= d + ad1[0])
                    flag3 = true;
                else
                    flag3 = false;
                if(ad[1] >= d + ad1[1])
                    flag4 = true;
                else
                    flag4 = false;
                if(flag3 && flag4)
                    setXRange(d + ad1[0], d + ad1[1], j);
            } else
            {
                setXRange(d + ad1[0], d + ad1[1], j);
            }
        if(mRenderer.isPanYEnabled())
            if(flag)
            {
                boolean flag1;
                boolean flag2;
                if(ad[2] <= d1 + ad1[2])
                    flag1 = true;
                else
                    flag1 = false;
                if(ad[3] < d1 + ad1[3])
                    flag2 = true;
                else
                    flag2 = false;
                if(flag1 && !flag2)
                    setYRange(d1 + ad1[2], d1 + ad1[3], j);
            } else
            {
                setYRange(d1 + ad1[2], d1 + ad1[3], j);
            }
        j++;
          goto _L5
_L2:
        RoundChart roundchart = (RoundChart)mChart;
        roundchart.setCenterX(roundchart.getCenterX() + (int)(f2 - f));
        roundchart.setCenterY(roundchart.getCenterY() + (int)(f3 - f1));
        notifyPanListeners();
          goto _L3
    }

    /**
     * @deprecated Method removePanListener is deprecated
     */

    public void removePanListener(PanListener panlistener)
    {
        this;
        JVM INSTR monitorenter ;
        mPanListeners.add(panlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private List mPanListeners;
}
