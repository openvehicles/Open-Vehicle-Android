package com.openvehicles.OVMS.ui.validators;


public abstract class BaseValidator implements Validator {
	protected String mErrorMessage = null;

	public BaseValidator(String pErrorMessage) {
		mErrorMessage = pErrorMessage;
	}
	
	public BaseValidator() {
		this(null);
	}
	
	public void setErrorMessage(String pErrorMessage) {
		mErrorMessage = pErrorMessage;
	}
	
	@Override
	public String getErrorMessage() {
		return mErrorMessage;
	}
}
