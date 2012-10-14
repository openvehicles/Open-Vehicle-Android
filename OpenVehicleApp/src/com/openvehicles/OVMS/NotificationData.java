package com.openvehicles.OVMS;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mark
 * 
 */
public class NotificationData implements Serializable {
	private static final long serialVersionUID = -3173247800500433809L;
	public String Message;
	public Date Timestamp;
	public String Title;

	public NotificationData() {
		this.Timestamp = new Date();
		this.Title = "";
		this.Message = "";
	}

	public NotificationData(Date paramDate, String paramString1,
			String paramString2) {
		this.Timestamp = paramDate;
		this.Title = paramString1;
		this.Message = paramString2;
	}
}