package com.openvehicles.OVMS.ui.witdet;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.openvehicles.OVMS.R;

@SuppressWarnings("deprecation")
public class ScaleLayout extends AbsoluteLayout {
	private float mContentWidth;
	private float mContentHeigth;
	
	public ScaleLayout(Context context, AttributeSet attrs) {
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
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (isInEditMode()) return;

		float scale_w = w / mContentWidth;
		float scale_h = h / mContentHeigth;
		float scale = Math.min(scale_w, scale_h);
		int side = (int) (8 * scale);
		
		int margin_l = Math.round((w - Math.round(mContentWidth * scale)) * 0.5f);
		int margin_t = Math.round((h - Math.round(mContentHeigth * scale)) * 0.5f);
		setPadding(Math.max(side, margin_l), Math.max(side, margin_t), side, side);
		
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
			} else 
			if (child.getId() == R.id.tabInfoSliderChargerControl) {
				SeekBar sb = (SeekBar)child;
				Bitmap srcBmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.charger_button);
				int th = lp.height;
				int tw = (int) (srcBmp.getWidth() * ((float)th / srcBmp.getHeight()));  
				
				Bitmap dstBmp = Bitmap.createScaledBitmap(srcBmp, tw, th, true);
				srcBmp.recycle();

				BitmapDrawable drw = new BitmapDrawable(getContext().getResources(), dstBmp);
				
				sb.setThumb(drw);
				sb.setThumbOffset(dstBmp.getWidth() / 50);
			}
				
		}
	}
}
