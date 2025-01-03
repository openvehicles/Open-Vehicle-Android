package com.openvehicles.OVMS.ui2.components.energymetrics

import android.text.SpannableString

class EnergyMetric {
    var metricName: String
    var metricValue: String? = null

    constructor(metricName: String, metricValue: String?) {
        this.metricName = metricName
        this.metricValue = metricValue
    }
}