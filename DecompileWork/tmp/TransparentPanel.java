package com.openvehicles.OVMS;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TransparentPanel extends LinearLayout {
	private Paint borderPaint;
	private Paint innerPaint;

	public TransparentPanel(Context paramContext) {
		super(paramContext);
		init();
	}

	public TransparentPanel(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init();
	}

	private void init() {
		this.innerPaint = new Paint();
		this.innerPaint.setARGB(225, 75, 75, 75);
		this.innerPaint.setAntiAlias(true);
		this.borderPaint = new Paint();
		this.borderPaint.setARGB(255, 255, 255, 255);
		this.borderPaint.setAntiAlias(true);
		this.borderPaint.setStyle(Paint.Style.STROKE);
		this.borderPaint.setStrokeWidth(2.0F);
	}

	protected void dispatchDraw(Canvas paramCanvas) {
		RectF localRectF = new RectF();
		localRectF.set(0.0F, 0.0F, getMeasuredWidth(), getMeasuredHeight());
		paramCanvas.drawRoundRect(localRectF, 5.0F, 5.0F, this.innerPaint);
		paramCanvas.drawRoundRect(localRectF, 5.0F, 5.0F, this.borderPaint);
		super.dispatchDraw(paramCanvas);
	}

	public void setBorderPaint(Paint paramPaint) {
		this.borderPaint = paramPaint;
	}

	public void setInnerPaint(Paint paramPaint) {
		this.innerPaint = paramPaint;
	}
}