package com.openvehicles.OVMS.ui.utils;

import com.google.android.gms.maps.model.LatLng;

import pl.mg6.android.maps.extensions.GoogleMap;
import pl.mg6.android.maps.extensions.MarkerOptions;

public class MarkerGenerator {
	public static void addMarkers(GoogleMap map, String title, String snippet, LatLng latlng, String cpid) {
		MarkerOptions options = new MarkerOptions();
		map.addMarker(options.title(title).snippet(snippet).position(latlng).data(cpid));
	}
}
