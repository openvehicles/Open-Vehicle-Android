/**
 * 
 */
package com.openvehicles.OVMS.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.openvehicles.OVMS.R;

/**
 * @author Andrej
 *
 */
public class MyAlertDialog {

	private static String TAG = "Common";
	private OnClickPositiveButton monClickPositiveButton;//The onClick() function of this interface is called if user clicks the positive button
	private OnCancelEvent monCancelEvent;//The OnCancel() function of this interface is called if user closes the alert dialog
	private AlertDialog mAlert;
	
	public MyAlertDialog(Activity activity, String strMessage
			, OnClickPositiveButton onClickPositiveButton//The onClick() function of this interface is called if user clicks the positive button
			)
	{
		MyAlertDialog(activity, strMessage, onClickPositiveButton, null);
	}
	public MyAlertDialog(Activity activity, String strMessage
			, OnClickPositiveButton onClickPositiveButton//The onClick() function of this interface is called if user clicks the positive button
			, OnCancelEvent onCancelEvent//The OnCancel() function of this interface is called if user closes the alert dialog
			)
	{
		MyAlertDialog(activity, strMessage, onClickPositiveButton, onCancelEvent);
	}
	private void MyAlertDialog(Activity activity, String strMessage,
			OnClickPositiveButton onClickPositiveButton,
			OnCancelEvent onCancelEvent)
	{
		monClickPositiveButton = onClickPositiveButton;
		monCancelEvent = onCancelEvent;
		try
		{
			//http://developer.android.com/reference/android/app/AlertDialog.html
			//http://developer.android.com/reference/android/app/AlertDialog.Builder.html
			//http://developer.alexanderklimov.ru/android/alertdialog.php
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle(R.string.Error)
					.setMessage(strMessage)
					.setIcon(android.R.drawable.ic_dialog_alert)//.stat_notify_error)
//									.setCancelable(false)
					.setNegativeButton(android.R.string.no,//.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
						})
					.setPositiveButton(android.R.string.yes,//.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
									monClickPositiveButton.onClick();
							}
						})
						
					//http://stackoverflow.com/questions/18267916/setoncancellistener-and-setondismisslistener-is-not-called-for-alertdialog-for-b
					.setOnCancelListener(new DialogInterface.OnCancelListener() {

			            @Override
			            public void onCancel(DialogInterface dialog) {
			            	if(monCancelEvent != null)
			            		monCancelEvent.OnCancel();
			            }
			        })
/*	
Call requires API level 17 (current min is 9)
					
					//http://stackoverflow.com/questions/18267916/setoncancellistener-and-setondismisslistener-is-not-called-for-alertdialog-for-b
					.setOnDismissListener(new DialogInterface.OnDismissListener() {

			            @Override
			            public void onDismiss(DialogInterface dialog) {
			                int i = 0;
			            }
			        })
*/			        
//					.show()
					;
			mAlert = builder.create();
			mAlert.show();
						
			/*
			Toast toast = Toast.makeText(getMainActivity(), 
					mstrMessage, Toast.LENGTH_LONG); 
			toast.show();
			*/
		}
		catch (Exception e)
		{
			Log.e(TAG, MyElement.getName() + " failed! " + e);
		}
	}
	public void cancel()
	{
		if(mAlert == null)
			return;
		mAlert.cancel();
		mAlert = null;
	}
}
