package com.openvehicles.OVMS.ui.utils

import com.androidmapsextensions.GoogleMap
import com.androidmapsextensions.MarkerOptions
import com.google.android.gms.maps.model.LatLng

object MarkerGenerator {

    @JvmStatic
    fun addMarkers(
        map: GoogleMap,
        title: String?,
        snippet: String?,
        latLng: LatLng?,
        cpId: String?
    ) {
        map.addMarker(
            MarkerOptions()
                .title(title)
                .snippet(snippet)
                .position(latLng)
                .data(cpId)
        )
    }
}
