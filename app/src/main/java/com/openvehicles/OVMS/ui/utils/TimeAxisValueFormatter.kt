package com.openvehicles.OVMS.ui.utils

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date

/**
 * TimeAxisValueFormatter: format chart axis time values
 * Expects values to be in seconds since startOffset
 */
class TimeAxisValueFormatter : ValueFormatter {

    private var offset: Long
    private var timeFmt: SimpleDateFormat
    private var date: Date

    constructor() {
        offset = 0
        timeFmt = SimpleDateFormat("HH:mm")
        date = Date()
    }

    constructor(offset: Long, timeFmtPattern: String?) {
        this.offset = offset
        timeFmt = SimpleDateFormat(timeFmtPattern)
        date = Date()
    }

    override fun getFormattedValue(value: Float): String {
        date.setTime((offset + value.toLong()) * 1000)
        return timeFmt.format(date)
    }
}
