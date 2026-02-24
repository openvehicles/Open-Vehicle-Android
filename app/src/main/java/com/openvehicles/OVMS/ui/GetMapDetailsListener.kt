package com.openvehicles.OVMS.ui

import com.google.android.gms.maps.model.LatLng

interface GetMapDetailsListener {

    fun getMapDetailsDone(isSuccess: Boolean, center: LatLng?)

}