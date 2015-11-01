package com.openvehicles.OVMS.ui.validators;

import android.widget.EditText;

import com.openvehicles.OVMS.R;

public class PasswdValidator extends StringValidator {
	private final int mMinLength, mMaxLength;
	
	public PasswdValidator(int pMinLength, int pMaxLength) {
		mMinLength = pMinLength;
		mMaxLength = pMaxLength;
	}
	
	@Override
	public boolean valid(EditText pEditText, Object pValue) {
		if (!super.valid(pEditText, pValue)) return false;
		
		setErrorMessage(pEditText.getContext().getString(R.string.msg_invalid_passwd, mMinLength, mMaxLength));
		String name = (String) pValue;
		return name.length() >= mMinLength && name.length() <= mMaxLength;		
	}
	
}
