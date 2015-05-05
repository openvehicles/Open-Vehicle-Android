package com.openvehicles.OVMS.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyDate extends Date {
	
	public MyDate(Date date)
	{ setTime(date.getTime()); }
	public MyDate(long milliseconds)
	{ setTime(milliseconds); }
	
    /*
	* Parses a date from the specified string using the rules of this date
    * format.
    */
	public MyDate(
			String strFormat//See http://www.tutorialspoint.com/java/java_date_time.htm for details
							// Example: "yyyy-MM-dd"
			, String input //the string to parse. Example: "2014-10-02 00:18:01" 
		)
	{
		//http://www.tutorialspoint.com/java/java_date_time.htm
		
		SimpleDateFormat ft;
		try { 
			ft = new SimpleDateFormat (strFormat); 
		} catch (IllegalArgumentException e) { 
			throw new MyException(MyElement.getName() + " Illegal argument exception: " + e.getLocalizedMessage());
//			return;
		}
		
//		System.out.print(input + " Parses as "); 
		
//		Date t; 
		
		try { 
			setTime(ft.parse(input).getTime()); 
//			System.out.println(t); 
		} catch (ParseException e) { 
//			System.out.println("Unparseable using " + ft); 
			throw new MyException(MyElement.getName() + " Unparseable using " + ft + ". " + e.getLocalizedMessage());
		}
	}
	
	public MyDate(int year, int month, int day, int hour, int minute, int second)
	{
		super(year, month, day, hour, minute, second);
	}
	
	//I have detected very strange problem:
	//
	//new Date(0).getHours() = 19
	//
	//I have added this method for resolving this problem
//	@Override
	public int getMyHours()
	{
		//http://www.mkyong.com/java/java-time-elapsed-in-days-hours-minutes-seconds/
		
		//1 minute = 60 seconds
		//1 hour = 60 x 60 = 3600
		//1 day = 3600 x 24 = 86400
	 
		//milliseconds
		long different = getTime();
/* 
		System.out.println("startDate : " + startDate);
		System.out.println("endDate : "+ endDate);
*/		
//		System.out.println("different : " + different);
 
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;
 
		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;
 
		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;
/* 
		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;
 
		long elapsedSeconds = different / secondsInMilli;
 
		System.out.printf(
		    "%d days, %d hours, %d minutes, %d seconds%n", 
		    elapsedDays,
		    elapsedHours, elapsedMinutes, elapsedSeconds);
*/		    
		return (int)(elapsedHours + elapsedDays * 24);
	}
}
