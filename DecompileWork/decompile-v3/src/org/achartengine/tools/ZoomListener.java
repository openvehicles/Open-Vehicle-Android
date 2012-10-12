// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.tools;


// Referenced classes of package org.achartengine.tools:
//            ZoomEvent

public interface ZoomListener
{

    public abstract void zoomApplied(ZoomEvent zoomevent);

    public abstract void zoomReset();
}
