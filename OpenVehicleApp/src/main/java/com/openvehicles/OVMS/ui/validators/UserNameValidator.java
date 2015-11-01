package com.openvehicles.OVMS.ui.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.EditText;

import com.openvehicles.OVMS.R;

public class UserNameValidator extends StringValidator {
	private static final String NAME_PATERN = "(\\w+\\s*)+";

	@Override
	public boolean valid(EditText pEditText, Object pValue) {
		if (!super.valid(pEditText, pValue)) return false;
		
		setErrorMessage(pEditText.getContext().getString(R.string.msg_invalid_name));
		String name = (String) pValue;
		
		if (name.length() < 3) return false;
		Pattern pattern = Pattern.compile(NAME_PATERN);
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}
	
}
