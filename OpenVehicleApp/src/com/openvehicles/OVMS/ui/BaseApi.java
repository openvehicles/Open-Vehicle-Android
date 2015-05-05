package com.openvehicles.OVMS.ui;

import com.openvehicles.OVMS.api.ApiService;
import com.openvehicles.OVMS.api.OnResultCommandListener;
import com.openvehicles.OVMS.utils.MyElement;
import com.openvehicles.OVMS.utils.MyException;

import android.app.Activity;
class BaseApi
{
	private static Activity mMainActivity;
	
	public static String mKeyChargingStations = "check";
	public static String mKeyProgress = "progress";
	public static String mKeyTurnLoggedPath = "TurnLoggedPath";
	public static String mKeyAveragedPointsCircleRadius = "AveragedPointsCircleRadius";
	
	
	public static void SetMainActivity(Activity activity)
	{
		if (activity instanceof ApiActivity)
		{
			mMainActivity = activity;
			return;
		}
        throw new MyException(MyElement.getName() + " failed! " + activity.getLocalClassName() + " activity is not instanceof ApiActivity");
	}
	public static Activity getActivity()
	{
		if(mMainActivity == null)
			throw new MyException(MyElement.getName() + " mMainActivity == null");
		return mMainActivity;
	}
	
	// Commands and expected responses for message types "C" and "c".
	// See "Commands and Expected Responses" header of the
	// https://github.com/openvehicles/Open-Vehicle-Monitoring-System/blob/master/docs/OVMS_Protocol.docx
	// for details.
	//
	// See "commands and responses.txt" document also
	
	protected static final int mnCR_Retrieve_24_Hour_Log_Data = 33;//Retrieve 24-hour log data
	//For details see email:
	//Date: Tue, 30 Sep 2014 14:14:01 -0700
	//From: Lee Howard <lee.howard@mainpine.com>
	//Subject: Re: Samsung Galaxy S5

	protected static final int mnCR_My_Tracks = 50;//Get car's track list
	protected static final int mnCR_My_Track = 51;//Get a track, saved before

	protected void sendCommand(int pResIdMessage, String pCommand,
			OnResultCommandListener pOnResultCommandListener) {
		ApiService service = getService();
		if (service == null)
		{
//			throw new IllegalStateException(MyElement.getName() + " failed!");
			return;
		}
		
		//////////////////////////////////////////////
		// server's response simulator. For debugging
		
		String[] arrayCommand = pCommand.split(",\\s*");
		switch(Integer.parseInt(arrayCommand[0]))
		{
		case mnCR_My_Tracks://50
//String[] data = "111,222,\"333\"".split(",\\s*");
			{
				String[] data = "Track 1".split(",\\s*");
				pOnResultCommandListener.onResultCommand(data);
			}
			{
				String[] data = "Track 2".split(",\\s*");
				pOnResultCommandListener.onResultCommand(data);
			}
			return;
		}
		
		// server's response simulator
		/////////////////////////////////////////////////
		
		service.sendCommand(pResIdMessage, pCommand, pOnResultCommandListener);
	}
	
	protected void sendCommandRetrieve24hourLogData(int pResIdMessage, String pCommand,
			OnResultCommandListener pOnResultCommandListener) {
		ApiService service = getService();
		if (service == null)
			return;
		service.sendCommandRetrieve24hourLogData(pResIdMessage, pCommand, pOnResultCommandListener);
	}
	
	protected void cancelCommand() {
		ApiService service = getService();
		if (service == null)
			return;
		service.cancelCommand();
	}
	
	private ApiService getService() {
		Activity activity = getActivity();
		if (activity instanceof ApiActivity) {
			ApiService apiService = ((ApiActivity) activity).getService();
			if(apiService == null)
		        throw new MyException(MyElement.getName() + " failed! apiService == null");
			return apiService;
		}
        throw new MyException(MyElement.getName() + ".getService() failed!");
	}
}
