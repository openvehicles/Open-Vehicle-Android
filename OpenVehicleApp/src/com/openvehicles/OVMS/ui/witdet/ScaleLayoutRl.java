package com.openvehicles.OVMS.ui.witdet;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.openvehicles.OVMS.R;

public class ScaleLayoutRl extends RelativeLayout {
	private float mContentWidth;
	private float mContentHeigth;
	private float mScale;
	private int mSide;
	
	public ScaleLayoutRl(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode()) return;
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScaleLayout);
		try {
			mContentWidth = (float) a.getInt(R.styleable.ScaleLayout_content_width, 0);
			mContentHeigth = (float) a.getInt(R.styleable.ScaleLayout_content_height, 0);
		} finally {
			a.recycle();
		}
		
		if ((mContentWidth + mContentHeigth) == 0) {
			throw new RuntimeException("Not set content_width or content_height");
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (isInEditMode()) {
			super.onLayout(changed, l, t, r, b);
			return;
		}

		int w = r-l;
		int h = b-t;

		Log.e("DEBUG", "onLayout: " + changed + " = " + w + 'x' + h);
		
		float scale_w = (w - mSide * 2) / mContentWidth;
		float scale_h = (h - mSide * 2) / mContentHeigth;
		mScale = Math.min(scale_w, scale_h);
	}
	

}
