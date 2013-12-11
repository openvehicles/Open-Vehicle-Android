package com.openvehicles.OVMS.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openvehicles.OVMS.R;

public class MapPane extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		GoogleMap map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		LatLng mapCenter = new LatLng(41.889, -87.622);

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));

		// Flat markers will rotate when the map is rotated,
		// and change perspective when the map is tilted.
		map.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_launcher))
				.position(mapCenter).flat(true).rotation(245));

		CameraPosition cameraPosition = CameraPosition.builder()
				.target(mapCenter).zoom(13).bearing(90).build();

		// Animate the change in camera view over 2 seconds
		map.animateCamera(
				CameraUpdateFactory.newCameraPosition(cameraPosition), 2000,
				null);
	}
}
