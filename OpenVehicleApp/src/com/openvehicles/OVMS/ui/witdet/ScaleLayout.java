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
	private float mScale;
	private int mSide;
	
	public ScaleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (isInEditMode()) return;
		
		mSide = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		
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
		float scale = Math.min(scale_w, scale_h);
		
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			LayoutParams lp = (LayoutParams) child.getLayoutParams();
			
            int childLeft = mSide + (int) (lp.x * scale);
            int childTop = mSide + (int) (lp.y * scale);

            child.layout(childLeft, childTop,
                    (int) (childLeft + child.getMeasuredWidth() * scale),
                    (int) (childTop + child.getMeasuredHeight() * scale));
            
//			if (changed && child instanceof TextView) {
//				TextView tv = (TextView) child;
//				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (tv.getTextSize() * scale));
//			}            
			
//			if (child instanceof TextView) {
//				TextView tv = (TextView) child;
//				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (tv.getTextSize() * scale));
//			} else 
//			if (child.getId() == R.id.tabInfoSliderChargerControl) {
//				SeekBar sb = (SeekBar)child;
//				Bitmap srcBmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.charger_button);
//				int th = child.getMeasuredHeight();
//				int tw = (int) (srcBmp.getWidth() * ((float)th / srcBmp.getHeight()));  
//				
//				Bitmap dstBmp = Bitmap.createScaledBitmap(srcBmp, tw, th, true);
//				srcBmp.recycle();
//
//				BitmapDrawable drw = new BitmapDrawable(getContext().getResources(), dstBmp);
//				
//				sb.setThumb(drw);
//				sb.setThumbOffset(dstBmp.getWidth() / 50);
//			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (isInEditMode()) return;

		Log.e("DEBUG", "onSizeChanged: " + w + 'x' + h);
		
		float scale_w = (w - mSide * 2) / mContentWidth;
		float scale_h = (h - mSide * 2) / mContentHeigth;
		mScale = Math.min(scale_w, scale_h);
		
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child instanceof TextView) {
				TextView tv = (TextView) child;
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (tv.getTextSize() * mScale));
			} else
			if (child.getId() == R.id.tabInfoSliderChargerControl) {
				SeekBar sb = (SeekBar)child;
				Bitmap srcBmp = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.charger_button);
				int th = (int) (child.getMeasuredHeight() * mScale);
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
