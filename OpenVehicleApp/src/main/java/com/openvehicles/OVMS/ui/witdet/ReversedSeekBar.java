package com.openvehicles.OVMS.ui.witdet;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.support.v7.widget.AppCompatSeekBar;

public class ReversedSeekBar extends AppCompatSeekBar {
	public boolean isReversed = true;

	public ReversedSeekBar(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	protected void onDraw(Canvas paramCanvas) {
		if (isReversed) {
			paramCanvas.scale(-1.0F, 1.0F, getWidth() / 2.0F, getHeight() / 2.0F);
		}
		try {
			super.onDraw(paramCanvas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		if (isReversed) {
			paramMotionEvent.setLocation(getWidth() - paramMotionEvent.getX(),
					paramMotionEvent.getY());
		}
		return super.onTouchEvent(paramMotionEvent);
	}
}