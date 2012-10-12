// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TransparentPanel extends LinearLayout
{

    public TransparentPanel(Context context)
    {
        super(context);
        init();
    }

    public TransparentPanel(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        init();
    }

    private void init()
    {
        innerPaint = new Paint();
        innerPaint.setARGB(225, 75, 75, 75);
        innerPaint.setAntiAlias(true);
        borderPaint = new Paint();
        borderPaint.setARGB(255, 255, 255, 255);
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(android.graphics.Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2.0F);
    }

    protected void dispatchDraw(Canvas canvas)
    {
        RectF rectf = new RectF();
        rectf.set(0.0F, 0.0F, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRoundRect(rectf, 5F, 5F, innerPaint);
        canvas.drawRoundRect(rectf, 5F, 5F, borderPaint);
        super.dispatchDraw(canvas);
    }

    public void setBorderPaint(Paint paint)
    {
        borderPaint = paint;
    }

    public void setInnerPaint(Paint paint)
    {
        innerPaint = paint;
    }

    private Paint borderPaint;
    private Paint innerPaint;
}
