package com.openvehicles.OVMS.utils;

import java.io.Serializable;
import java.util.Date;

public class NotificationData implements Serializable {
	private static final long serialVersionUID = -3173247800500433809L;
	public Date Timestamp;
	public String Title;
	public String Message;
	
	public NotificationData(Date timestamp, String title, String message) {
		this.Timestamp = timestamp;
		this.Title = title;
		this.Message = message;
	}

	// equals operator: used to detect duplicates
	public boolean equals(NotificationData o) {
		return (o.Title.equals(Title) && o.Message.equals(Message));
	}

	// message formatter:
	public String getMessageFormatted() {
		// default: use line breaks as sent by the module:
		return Message.replace('\r', '\n');
	}
}
