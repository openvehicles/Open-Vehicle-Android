package com.openvehicles.OVMS.ui.witdet;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.ui.utils.Ui.OnChangeListener;

public class SwitcherView extends LinearLayout implements OnClickListener  {
	private int mSelected = -1;
	private OnChangeListener<SwitcherView> mListener;
	
	public SwitcherView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(HORIZONTAL);
		
		CharSequence[] values = null;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwitcherView);
		try {
			values = a.getTextArray(R.styleable.SwitcherView_android_entries);
		} finally {
			a.recycle();
		}
		
		if (values == null) values = new CharSequence[] {"Off", "On"};
		setValues(values);
	}
	
	public SwitcherView(Context context) {
		this(context, null);
	}
	
	public void setOnChangeListener(OnChangeListener<SwitcherView> pListener) {
		mListener = pListener;
	}
	
	public void setValues(CharSequence ...pValues) {
		int side = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());		
		int top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());		
		
		removeAllViews();
		for (CharSequence text: pValues) {
			SwitcherButton b = new SwitcherButton(getContext());
			if (mSelected < 0 && getChildCount() == 0) b.setChecked(true);
			b.setSingleLine();
			b.setText(text);
			if (!isInEditMode()) b.setTextAppearance(getContext(), android.R.style.TextAppearance_Small);
			b.setTextColor(0xFFFFFFFF);
			b.setBackgroundResource(R.drawable.item_choice);
			b.setPadding(side, top, side, top);
			b.setOnClickListener(this);
			addView(b);
		}
	}

	@Override
	public void onClick(View v) {
		int count = getChildCount();
		SwitcherButton b;
		for (int i=0; i<count; i++) {
			b = (SwitcherButton) getChildAt(i);
			b.setChecked(b == v);
			if (b == v) mSelected = i;
		}
		if (mListener != null) mListener.onAction(this);
	}
	
	public int getSelected() {
		return mSelected < 0 ? 0 : mSelected;
	}
	
	public void setSelected(int pSelected) {
		if (pSelected < 0 || pSelected > getChildCount()-1) 
			throw new IndexOutOfBoundsException("Item out of range");
		onClick(getChildAt(pSelected));
	}
	
	public static class SwitcherButton extends CompoundButton {
		public SwitcherButton(Context context) {
			super(context);
			setFocusable(false);
		}
	}
}
