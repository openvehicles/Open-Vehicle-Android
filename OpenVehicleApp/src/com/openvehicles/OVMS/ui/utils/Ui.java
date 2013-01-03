package com.openvehicles.OVMS.ui.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
					EditText etxtPin = (EditText) ((Dialog) dialog).findViewById(R.id.etxt_input_value);
					if (pListener != null) pListener.onAction(etxtPin.getText().toString());
				}
			})
			.create();
		
        dialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
        		EditText et = (EditText)((Dialog) dialog).findViewById(R.id.etxt_input_value);
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
					EditText etxtPin = (EditText) ((Dialog) dialog).findViewById(R.id.etxt_input_value);
					if (pListener != null) pListener.onAction(etxtPin.getText().toString());
				}
			})
			.create();
		
        dialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
        		EditText et = (EditText)((Dialog) dialog).findViewById(R.id.etxt_input_value);
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
	
	public static void setOnClick(View pRootView, int pId, OnClickListener pListener) {
		try {
			pRootView.findViewById(pId).setOnClickListener(pListener);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	public static void setValue(View pRootView, int pId, String pVal) {
		try {
			TextView tv = (TextView) pRootView.findViewById(pId);
			tv.setText(pVal);
		} catch (Exception e) {
			Log.e(TAG, "string: " + e);
		}
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
	
	
}
