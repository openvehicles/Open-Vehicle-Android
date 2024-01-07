package com.openvehicles.OVMS.ui.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeAxisValueFormatter: format chart axis time values
 * 	Expects values to be in seconds since startOffset
 */
public class TimeAxisValueFormatter extends ValueFormatter {
	private long offset;
	private SimpleDateFormat timeFmt;
	private Date date;

	public TimeAxisValueFormatter() {
		offset = 0;
		timeFmt = new SimpleDateFormat("HH:mm");
		date = new Date();
	}
	public TimeAxisValueFormatter(long offset, String timeFmtPattern) {
		this.offset = offset;
		timeFmt = new SimpleDateFormat(timeFmtPattern);
		date = new Date();
	}

	@Override
	public String getFormattedValue(float value) {
		date.setTime((offset + (long)value) * 1000);
		return timeFmt.format(date);
	}
}
