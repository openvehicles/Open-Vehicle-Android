package com.openvehicles.OVMS.utils;

import java.io.Serializable;
import java.util.Date;

public class NotificationData implements Serializable {
	private static final long serialVersionUID = -3173247800500433809L;

	public static final int TYPE_INFO = 0;
	public static final int TYPE_ALERT = 1;
	public static final int TYPE_COMMAND = 2;
	public static final int TYPE_RESULT_SUCCESS = 3;
	public static final int TYPE_RESULT_ERROR = 4;
	public static final int TYPE_USSD = 5;

	public long ID;
	public int Type;
	public Date Timestamp;
	public String Title;
	public String Message;

	public NotificationData(long ID, int type, Date timestamp, String title, String message) {
		this.ID = ID;
		Type = type;
		Timestamp = timestamp;
		Title = title;
		Message = message;
	}

	public NotificationData(int type, Date timestamp, String title, String message) {
		this.ID = 0;
		Type = type;
		Timestamp = timestamp;
		Title = title;
		Message = message;
	}

	public NotificationData(Date timestamp, String title, String message) {
		this.ID = 0;
		this.Type = TYPE_INFO;
		this.Timestamp = timestamp;
		this.Title = title;
		this.Message = message;
	}

	// equals operator: used to detect duplicates
	public boolean equals(NotificationData o) {
		return (o.Type == Type && o.Title.equals(Title) && o.Message.equals(Message));
	}

	// message formatter:
	public String getMessageFormatted() {
		// default: use line breaks as sent by the module:
		return Message.replace('\r', '\n');
	}

	public int getIcon() {
		switch (Type) {
			case NotificationData.TYPE_ALERT:
				return android.R.drawable.ic_dialog_alert;
			case NotificationData.TYPE_USSD:
				return android.R.drawable.ic_menu_call;
			case NotificationData.TYPE_COMMAND:
				return android.R.drawable.ic_menu_send;
			case NotificationData.TYPE_RESULT_SUCCESS:
				return android.R.drawable.ic_menu_revert;
			case NotificationData.TYPE_RESULT_ERROR:
				return android.R.drawable.ic_menu_help;
			default:
				return android.R.drawable.ic_menu_info_details;
		}
	}

}
