package com.openvehicles.OVMS.ui.witdet;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class Label extends TextView {

	public Label(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		Log.i("DEBUG", "onSizeChanged: " + w + 'x' + h);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		int w = r-l;
		int h = b-t;
		Log.i("DEBUG", "onLayout: " +  w + 'x' + h);
	}

}
