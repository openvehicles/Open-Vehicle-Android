// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.tools;


public class ZoomEvent
{

    public ZoomEvent(boolean flag, float f)
    {
        mZoomIn = flag;
        mZoomRate = f;
    }

    public float getZoomRate()
    {
        return mZoomRate;
    }

    public boolean isZoomIn()
    {
        return mZoomIn;
    }

    private boolean mZoomIn;
    private float mZoomRate;
}
