/**
 * 
 */
package com.openvehicles.OVMS.utils;

import android.util.Log;

/**
 * @author Andrej
 *
 */
public class MyException extends IllegalStateException {
	public MyException(String detailMessage)
	{
		super(detailMessage);
		Log.e("MyLine", MyElement.getName() + detailMessage);
//		assert false : detailMessage;
	}

}
