package com.openvehicles.OVMS.ui.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.EditText;

public class EmailValidator extends StringValidator {
	private static final String EMAIL_PATTERN = "\\b([a-zA-Z0-9%_.+\\-]+)@([a-zA-Z0-9.\\-]+?\\.[a-zA-Z]{2,6})\\b";

	@Override
	public boolean valid(EditText pEditText, Object pValue) {
		if (!super.valid(pEditText, pValue)) return false;
		
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher((String) pValue);
		return matcher.matches();
	}
	
}
