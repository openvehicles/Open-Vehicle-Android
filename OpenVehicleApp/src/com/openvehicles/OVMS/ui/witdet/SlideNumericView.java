package com.openvehicles.OVMS.ui.witdet;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.TextView;

import com.openvehicles.OVMS.R;

public class SlideNumericView extends Gallery {
	private static final int[] SHADOWS_COLORS = new int[] {0xFF000000, 0x00AAAAAA};
	private static GradientDrawable sLeftShadow = new GradientDrawable(Orientation.LEFT_RIGHT, SHADOWS_COLORS);
	private static GradientDrawable sRightShadow = new GradientDrawable(Orientation.RIGHT_LEFT, SHADOWS_COLORS);
	
	public SlideNumericView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundResource(R.drawable.bg_wheel);

		setSpacing(6);
        setUnselectedAlpha(0.2f);
        
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlideNumericView);
		try {
			init(a.getInt(R.styleable.SlideNumericView_min, 0), 
					a.getInt(R.styleable.SlideNumericView_max, 100), 1);
		} finally {
			a.recycle();
		}
	}

	public SlideNumericView(Context context) {
		this(context, null);
	}
	
	public void init(int min, int max, int measure) {
		NumericAdapter adapter = new NumericAdapter(getContext(), min, max, measure); 
		setAdapter(adapter);
        setSelection(adapter.getStartPosition());
	}

	public void setValue(int value) {
		NumericAdapter adapter = (NumericAdapter) getAdapter();
		if (adapter == null) return;
		
		if (value < adapter.min) value = adapter.min;
		if (value > adapter.max) value = adapter.max;

		setSelection(adapter.getStartPosition() + adapter.calculatePosition(value));
	}
	
	public int getValue() {
		return (Integer) getSelectedItem();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawShadows(canvas);
	}
	
	private void drawShadows(Canvas canvas) {
		sLeftShadow.setBounds(0, 0, getWidth() / 4, getHeight());
		sLeftShadow.draw(canvas);

		sRightShadow.setBounds(getWidth() - (getWidth() / 4), 0, getWidth(), getHeight());
		sRightShadow.draw(canvas);
	}
	
	private static class NumericAdapter extends BaseAdapter {
		public final int min, max;
		private final int mMeasure, mPading;

	    public NumericAdapter(Context pContext, int pMin, int pMax, int pMeasure) {
	    	min = pMin;
	    	max = pMax;
	    	mMeasure =  pMeasure == 0 ? 1 : pMeasure;

	    	mPading = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, 
	    			pContext.getResources().getDisplayMetrics());
	    }
	    
		public int getCount() {
			return Integer.MAX_VALUE;
		}
		
		public int size() {
			return Math.round((max - min) / mMeasure + 1);
		}
		
		public int calculatePosition(int val) {
			if (val < min || val > max) {
				throw new ArithmeticException(String.format("min: %d, max: %d, val: %d", min, max, val));
			}
			return (val - min) / mMeasure;
		}
		
		public int getStartPosition() {
			return size() != 0 ? 
					(int)(Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2) % size() : 
					Integer.MAX_VALUE / 2;
		}

		public Object getItem(int position) {
			position = preparePosition(position);
			return ((min + mMeasure * position) * 100) / 100;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv;
			if (convertView != null) {
				tv = (TextView) convertView; 
			} else {
				tv = new TextView(parent.getContext());
				tv.setTypeface(Typeface.DEFAULT_BOLD);
				tv.setTextColor(0xFF000000);
				tv.setTextSize(28);
				tv.setPadding(mPading, mPading, mPading, mPading);
			}
			tv.setText(getItem(position).toString());
			return tv;
		}
		
	    protected int preparePosition(int position) {
	        if (position >= size()) {
	            position = position % size();
	        }
	        return position;
	    }
	}
}
