package com.openvehicles.OVMS.ui.witdet;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class ScaleLayout extends AbsoluteLayout {
	private static final float PAGE_W = 550f;
	private static final float PAGE_H = 701f;
	
	public ScaleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
//	@Override
//	public void addView(View child, int index, ViewGroup.LayoutParams params) {
//		child.setVisibility(INVISIBLE);
//		super.addView(child, index, params);
//	}
	

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (isInEditMode()) return;

		float scale_w = w / PAGE_W;
		float scale_h = h / PAGE_H;
		float scale = Math.min(scale_w, scale_h);
		int side = (int) (8 * scale);
		
		Log.i("DEBUG", "scale_w: " + scale_w);
		Log.i("DEBUG", "scale_h: " + scale_h);
		Log.i("DEBUG", "scale: " + scale);

		Log.i("DEBUG", "W: " + w);
		Log.i("DEBUG", "H: " + h);
		
		Log.i("DEBUG", "PAGE_W: " + PAGE_W);
		Log.i("DEBUG", "PAGE_H: " + PAGE_H);
		
		
//		if (scale_w >= scale_h) {
//			int margin = (int) ((w - PAGE_W * scale) * 0.5f);
//			setPadding(margin, side, side, side);
//		} else {
//			int margin = (int) ((h - PAGE_H * scale) * 0.5f);
//			setPadding(side, margin, side, side);
//		}
		
		int margin_l = Math.round((w - Math.round(PAGE_W * scale)) * 0.5f);
		int margin_t = Math.round((h - Math.round(PAGE_H * scale)) * 0.5f);
		setPadding(Math.max(side, margin_l), Math.max(side, margin_t), side, side);
		
		Log.i("DEBUG", "margin_l: " + margin_l);
		Log.i("DEBUG", "margin_t: " + margin_t);
		
		
		
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			LayoutParams lp = (LayoutParams) child.getLayoutParams();
			lp.x = (int) (lp.x * scale);
			lp.y = (int) (lp.y * scale);
			lp.width = (int) (lp.width * scale);
			lp.height = (int) (lp.height * scale);

			if (child instanceof TextView) {
				TextView tv = (TextView) child;
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (tv.getTextSize() * scale));
			}
		}
	}
}
