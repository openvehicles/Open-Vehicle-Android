package com.openvehicles.OVMS.ui.utils;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.ui.validators.ValidationException;
import com.openvehicles.OVMS.ui.validators.Validator;

public final class Ui {
	private static final String TAG = "Ui"; 

	public interface OnChangeListener<T> {
		public void onAction(T pData);
	}

	public static void showPinDialog(Context pContext, int pMsgResId, OnChangeListener<String> pListene) {
		showPinDialog(pContext, pMsgResId, pMsgResId, pListene);
	}

	public static void showPinDialog(Context pContext, int pTitleResId, int pButtonResId,
			final OnChangeListener<String> pListene) {
		showPinDialog(pContext, pTitleResId, pButtonResId, true, pListene);
	}

	public static void showPinDialog(Context pContext, int pTitleResId, int pButtonResId, boolean isPin,
			final OnChangeListener<String> pListener) {
		showPinDialog(pContext, pContext.getString(pTitleResId), null, pButtonResId, isPin, pListener);
	}
	
	public static void showPinDialog(Context pContext, String pTitle, String pValue, int pButtonResId, boolean isPin,
			final OnChangeListener<String> pListener) {
		View view = LayoutInflater.from(pContext).inflate(R.layout.dlg_pin, null);
		EditText et = (EditText) view.findViewById(R.id.etxt_input_value);
		et.setText(pValue);
		if (isPin) {
			et.setHint(R.string.lb_enter_pin);
		} else {
			et.setTransformationMethod(null);
		}
		
		AlertDialog dialog = new AlertDialog.Builder(pContext)
			.setMessage(pTitle)
			.setView(view)
			.setNegativeButton(R.string.Cancel, null)
			.setPositiveButton(pButtonResId, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					EditText etxtPin = (EditText) ((AppCompatDialog) dialog).findViewById(R.id.etxt_input_value);
					if (pListener != null) pListener.onAction(etxtPin.getText().toString());
				}
			})
			.create();
		
        dialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
        		EditText et = (EditText)((AppCompatDialog) dialog).findViewById(R.id.etxt_input_value);
                InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                et.selectAll();
            }
        });
        dialog.show();
	}
	
	public static void showEditDialog(Context pContext, String pTitle, String pValue, int pButtonResId, boolean isPassvd,
			final OnChangeListener<String> pListener) {
		View view = LayoutInflater.from(pContext).inflate(R.layout.dlg_edit, null);
		EditText et = (EditText) view.findViewById(R.id.etxt_input_value);
		et.setText(pValue);
		if (isPassvd) {
			et.setHint(R.string.lb_enter_passwd);
			et.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			et.setTransformationMethod(PasswordTransformationMethod.getInstance());
		}
		
		AlertDialog dialog = new AlertDialog.Builder(pContext)
			.setMessage(pTitle)
			.setView(view)
			.setNegativeButton(R.string.Cancel, null)
			.setPositiveButton(pButtonResId, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					EditText etxtPin = (EditText) ((AppCompatDialog) dialog).findViewById(R.id.etxt_input_value);
					if (pListener != null) pListener.onAction(etxtPin.getText().toString());
				}
			})
			.create();
		
        dialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
        		EditText et = (EditText)((AppCompatDialog) dialog).findViewById(R.id.etxt_input_value);
                InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                et.selectAll();
            }
        });
        dialog.show();
	}
	
	public static int getDrawableIdentifier(Context pContext, String pName) {
		if (pName == null || pContext == null) return 0;
		return pContext.getResources().getIdentifier(pName, "drawable", pContext.getPackageName());
	}
	
	public static View setOnClick(View pRootView, int pId, OnClickListener pListener) {
		View view = pRootView.findViewById(pId);
		try {
			view.setOnClickListener(pListener);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return view;
	}

	public static View setOnClick(View pRootView, int pId, Object pTag,
								  OnClickListener pListener,
								  View.OnLongClickListener pLongListener) {
		View view = pRootView.findViewById(pId);
		try {
			view.setTag(pTag);
			view.setOnClickListener(pListener);
			if (pLongListener != null)
				view.setOnLongClickListener(pLongListener);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return view;
	}

	public static void setValue(View pRootView, int pId, String pVal) {
		try {
			TextView tv = (TextView) pRootView.findViewById(pId);
			tv.setText(pVal);
		} catch (Exception e) {
			Log.e(TAG, "setValue: " + e);
		}
	}

	public static String getValue(View pRootView, int pId) {
		String value = null;
		EditText et = (EditText) pRootView.findViewById(pId);
		value = et.getText().toString();
		return value;
	}

	public static String getValidValue(View pRootView, int pId, Validator pValidator) throws ValidationException {
		String value = null;
		EditText et = (EditText) pRootView.findViewById(pId);
		value = et.getText().toString();
		if (!pValidator.valid(et, value)) {
			et.requestFocus();
			et.setError(pValidator.getErrorMessage());
			throw new ValidationException(pValidator.getErrorMessage());
		}
		return value; 
	}

	public static void setButtonImage(View pRootView, int pId, Drawable pIcon) {
		try {
			ImageButton btn = (ImageButton) pRootView.findViewById(pId);
			btn.setImageDrawable(pIcon);
		} catch (Exception e) {
			Log.e(TAG, "setValue: " + e);
		}
	}


	/**
	 * Draw a text consisting of a value and a unit, applying a smaller text size to the
	 * unit part and separating the unit from the value by some space. The unit size is
	 * currently fixed to 70% of the value size. Alignment and value size is taken from
	 * paint.
	 *
	 * The default separator size is 1/6, but depending on the actual text size and unit
	 * text, this may not look good and need to be smaller or larger.
	 *
	 * @param canvas        Canvas to paint on
	 * @param paint         Paint template to use
	 * @param value         Value string
	 * @param unit          Unit string
	 * @param x             Position
	 * @param y             Position
	 * @param sepSizeFactor Relative to the unit text size
	 * @return complete width of the rendered text (value + separator + unit)
	 */
	public static float drawUnitText(Canvas canvas, Paint paint, String value, String unit,
							   float x, float y, float sepSizeFactor) {

		final float unitSizeFactor = 0.7f; // Relative unit text size

		Paint p1 = new Paint(paint);
		p1.setTextAlign(Paint.Align.LEFT);
		Paint p2 = new Paint(p1);
		float unitTextSize = p1.getTextSize() * unitSizeFactor;
		float sepSize = unitTextSize * sepSizeFactor;
		p2.setTextSize(unitTextSize);

		float w1 = p1.measureText(value) + sepSize;
		float w2 = p2.measureText(unit);

		float x0;
		if (paint.getTextAlign() == Paint.Align.LEFT) {
			x0 = x;
		} else if (paint.getTextAlign() == Paint.Align.RIGHT) {
			x0 = x - (w1 + w2);
		} else {
			x0 = x - (w1 + w2) / 2 + sepSize;
			// Due to the smaller text size for the unit, a mathematically centered
			// placement appears to be unbalanced. Shifting x0 to the right by the sepSize
			// works as a rebalancement (may need refinement for very small/large sizes).
		}

		canvas.drawText(value, x0, y, p1);
		canvas.drawText(unit,x0 + w1, y, p2);

		return w1 + w2;
	}

	public static float drawUnitText(Canvas canvas, Paint paint, String value, String unit,
							   float x, float y) {
		return drawUnitText(canvas, paint, value, unit, x, y, 1/6f);
	}
}
