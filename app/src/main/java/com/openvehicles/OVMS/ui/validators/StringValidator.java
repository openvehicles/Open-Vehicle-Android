package com.openvehicles.OVMS.ui.validators;

import android.text.TextUtils;
import android.widget.EditText;

import com.openvehicles.OVMS.R;

public class StringValidator extends BaseValidator {

	public StringValidator() {
		super();
	}
	
	public StringValidator(String string) {
		super(string);
	}

	@Override
	public boolean valid(EditText pEditText, Object pValue) {
		if (mErrorMessage == null) setErrorMessage(pEditText.getContext().getString(R.string.msg_please_enter_value));
		return !TextUtils.isEmpty((String)pValue);
	}
	
}
