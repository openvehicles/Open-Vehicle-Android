/**
 * 
 */
package com.openvehicles.OVMS.utils;

//import java.util.List;
import java.util.TimerTask;

import android.app.Activity;
//import android.content.Intent;
import android.util.Log;

/**
 * @author Andrej
 * Use Start(...) for start timer and Stop() for stop
 */
public class MyTimer {
	
	public MyTimer(Activity activity)
	{
		if(activity == null)
			throw new MyException(MyElement.getName() + " failed! activity == null");
		mactivity = activity;
	}

	private static String TAG = "Common";
	private String mstrMessage;//Displays after timeout
	private java.util.Timer mtimer;
	private MyTimerTimeout mMyTimerTimeout;//The onTimeout() function of this interface is called after timeout
	private OnClickPositiveButton monClickPositiveButton;//The onClick() function of this interface is called if user clicks the positive button of the MyAlertDialog
	private MyAlertDialog mMyAlertDialog;
	
	private Activity mactivity;
	private Activity getActivity()
	{
		if(mactivity == null)
			throw new MyException(MyElement.getName() + " failed! mactivity == null");
		return mactivity;
	}
	
	public void Stop()
	{
		
		if(mtimer == null)
			return;
		mtimer.cancel();
		mtimer = null;
		if(mMyAlertDialog != null)
			mMyAlertDialog.cancel();
	}
	
	//Start the mtimer and display the alert dialog after timeout
	//Please call Stop() for stop of the mtimer
	public void Start
		(
				long delay//amount of time in milliseconds before displaying of the timeout message
				, int MessageID//ID of message string, displays after timeout
				, MyTimerTimeout myTimerTimeout//The onTimeout() function of this interface is called after timeout
				, OnClickPositiveButton onClickPositiveButton//The onClick() function of this interface is called if user clicks the positive button
		)
	{
		try
		{
			mMyTimerTimeout = myTimerTimeout;
			monClickPositiveButton = onClickPositiveButton;
			mstrMessage = getActivity().getString(MessageID);
			
			if(mtimer != null)
			{
				Log.e(TAG,  MyElement.getName() + " failed! mtimer != null");
				return;
			}
			mtimer = new java.util.Timer();
			
			//http://stackoverflow.com/questions/18656813/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
			mtimer.schedule(new TimerTask() {
			    @Override
			    public void run() {
			        // Your logic here...
	
			        // When you need to modify a UI element, do so on the UI thread. 
			        // 'getActivity()' is required as this is being ran from a Fragment.
			        getActivity().runOnUiThread(new Runnable() {
			            @Override
			            public void run() {
			                // This code will always run on the UI thread, therefore is safe to modify UI elements.
	//Log.d(TAG, "BaseApi.Wait.TimerTask().run().Runnable().run()");
			            	
//							((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
			            	mMyTimerTimeout.onTimeout();
	
			            	mMyAlertDialog = new MyAlertDialog((Activity)getActivity(), mstrMessage, monClickPositiveButton);
			            }
			        });
			    }
			}, delay); // End of your timer code.
			
//			((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(true);
		}
		catch (Exception e)
		{
			String strMessage = MyElement.getName() + " " + e;
			Log.e(TAG,  strMessage);
			throw new MyException(strMessage);
		}
	}
}
