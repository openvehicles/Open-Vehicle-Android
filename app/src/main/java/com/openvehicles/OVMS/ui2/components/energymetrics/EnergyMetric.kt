package com.openvehicles.OVMS.ui2.components.energymetrics

class EnergyMetric {
    var metricName: String
    var metricValue: String? = null

    constructor(metricName: String, metricValue: String?) {
        this.metricName = metricName
        this.metricValue = metricValue
    }
}