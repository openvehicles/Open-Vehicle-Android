package com.openvehicles.OVMS.ui.witdet;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openvehicles.OVMS.R;

public class ScaleLayout extends ViewGroup {
	private float mContentWidth = 0;
	private float mContentHeigth = 0;
	private final int mPadding;
	private float mScale;
	private boolean isScale = false;
	private Runnable mOnScale;

    public ScaleLayout(Context context) {
        this(context, null);
    }

    public ScaleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
		mPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
    }
    
    public float getScale() {
    	return mScale;
    }
    
    public void setOnScale(Runnable pOnScale) {
    	mOnScale = pOnScale;
    }
    
	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		super.addView(child, index, params);
		ScaleLayout.LayoutParams lp = (ScaleLayout.LayoutParams) child.getLayoutParams();
		
		mContentWidth = Math.max(mContentWidth, lp.x + lp.width);
		mContentHeigth = Math.max(mContentHeigth, lp.y + lp.height);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
            	ScaleLayout.LayoutParams lp = (ScaleLayout.LayoutParams) child.getLayoutParams();
                child.layout(lp.x, lp.y,
                		lp.x + child.getMeasuredWidth(),
                		lp.y + child.getMeasuredHeight());
            }
        }
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (isInEditMode()) {
			onDesignMeasure(widthMeasureSpec, heightMeasureSpec);
		} else {
			onRuntimeMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	
	private void onRuntimeMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int w = MeasureSpec.getSize(widthMeasureSpec);
		int h = MeasureSpec.getSize(heightMeasureSpec);
		
		float scale_w = (w - mPadding * 2) / mContentWidth;
		float scale_h = (h - mPadding * 2) / mContentHeigth;
		mScale = Math.min(scale_w, scale_h);

		if (!isScale) {
			int ofset_x = Math.round(((w - mPadding * 2) - mContentWidth * mScale) * 0.5f); 
			int ofset_y = Math.round(((h - mPadding * 2) - mContentHeigth * mScale) * 0.5f); 
			
	        int count = getChildCount();
	        for (int i = 0; i < count; i++) {
	            View child = getChildAt(i);
	            
	            ScaleLayout.LayoutParams lp = (ScaleLayout.LayoutParams) child.getLayoutParams();
	            lp.x = mPadding + ofset_x + (int) (lp.x * mScale);
	            lp.y = mPadding + ofset_y + (int) (lp.y * mScale);
	            
	            lp.width = (int) (lp.width * mScale);
	            lp.height = (int) (lp.height * mScale);
	            
				if (child instanceof TextView) {
					TextView tv = (TextView) child;
					tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (tv.getTextSize() * mScale));
				}
	        }
	        isScale = true;
			if (mOnScale != null) mOnScale.run();
		}
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resolveSize(w, widthMeasureSpec), resolveSize(h, heightMeasureSpec));
	}
	
	private void onDesignMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        int maxHeight = 0;
        int maxWidth = 0;

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int childRight;
                int childBottom;

                ScaleLayout.LayoutParams lp = (ScaleLayout.LayoutParams) child.getLayoutParams();

                childRight = lp.x + child.getMeasuredWidth();
                childBottom = lp.y + child.getMeasuredHeight();

                maxWidth = Math.max(maxWidth, childRight);
                maxHeight = Math.max(maxHeight, childBottom);
            }
        }

        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        
        setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec),
        		resolveSize(maxHeight, heightMeasureSpec));
	}
	
	
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ScaleLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof ScaleLayout.LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }
	

	public static class LayoutParams extends ViewGroup.LayoutParams {
        public int x, y;
        
        public LayoutParams(int width, int height, int x, int y) {
            super(width, height);
            this.x = x;
            this.y = y;
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
			TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.ScaleLayout_Layout);
			x = a.getDimensionPixelOffset(R.styleable.ScaleLayout_Layout_android_layout_x, 0);
			y = a.getDimensionPixelOffset(R.styleable.ScaleLayout_Layout_android_layout_y, 0);
            a.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

    }
}
