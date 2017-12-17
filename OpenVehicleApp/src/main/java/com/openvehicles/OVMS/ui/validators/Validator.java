package com.openvehicles.OVMS.ui.validators;

import android.widget.EditText;

public interface Validator {
	public String getErrorMessage();
	public boolean valid(EditText pEditText, Object pValue);	
}
