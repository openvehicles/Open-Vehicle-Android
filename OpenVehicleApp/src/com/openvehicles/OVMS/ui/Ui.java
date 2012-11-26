package com.openvehicles.OVMS.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.openvehicles.OVMS.R;

public class Ui {

	public static void showPinDialog(Context pContext, int pMsgResId, OnChangeListener<String> pListene) {
		showPinDialog(pContext, pMsgResId, pMsgResId, pListene);
	}
	
	public static void showPinDialog(Context pContext, int pTitleResId, int pButtonResId,
			final OnChangeListener<String> pListene) {
		AlertDialog dialog = new AlertDialog.Builder(pContext)
			.setTitle(pTitleResId)
			.setView(LayoutInflater.from(pContext).inflate(R.layout.dlg_pin, null))
			.setNegativeButton(R.string.Cancel, null)
			.setPositiveButton(pButtonResId, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					EditText etxtPin = (EditText) ((Dialog) dialog).findViewById(R.id.etxt_pin);
					if (pListene != null) pListene.onAction(etxtPin.getText().toString());
				}
			})
			.create();
		
        dialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
            	View v = ((Dialog) dialog).findViewById(R.id.etxt_pin).findViewById(R.id.etxt_pin);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        dialog.show();
	}
	
	public interface OnChangeListener<T> {
		public void onAction(T pData);
	}
}
