// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class ReversedSeekBar extends SeekBar
{

    public ReversedSeekBar(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        isReversed = true;
    }

    protected void onDraw(Canvas canvas)
    {
        if(isReversed)
            canvas.scale(-1F, 1.0F, (float)getWidth() / 2.0F, (float)getHeight() / 2.0F);
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        if(isReversed)
            motionevent.setLocation((float)getWidth() - motionevent.getX(), motionevent.getY());
        return super.onTouchEvent(motionevent);
    }

    public boolean isReversed;
}
