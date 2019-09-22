package com.openvehicles.OVMS.ui;

import java.util.Arrays;
import java.util.List;

import com.androidmapsextensions.CircleOptions;
import com.androidmapsextensions.ClusterGroup;
import com.androidmapsextensions.ClusteringSettings;
import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.GoogleMap.OnInfoWindowClickListener;
import com.androidmapsextensions.Marker;
import com.androidmapsextensions.MarkerOptions;
import com.androidmapsextensions.OnMapReadyCallback;
import com.androidmapsextensions.SupportMapFragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.GetMapDetails.afterasytask;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.ui.utils.DemoClusterOptionsProvider;
import com.openvehicles.OVMS.ui.utils.MarkerGenerator;
import com.openvehicles.OVMS.ui.utils.Ui;

public class FragMap extends BaseFragment implements OnInfoWindowClickListener,
		afterasytask, OnClickListener, Settings.UpdateMap, OnMapReadyCallback {
	private static final String TAG = "FragMap";

	private SupportMapFragment fragment;
	private GoogleMap map;

	Database database;
	String slat, slng;
	AppPrefes appPrefes;

	static boolean mapInitState = true;
	private static final double[] CLUSTER_SIZES = new double[] { 360, 180, 90, 45, 22 };
	View rootView;
	Menu optionsMenu;
	boolean autotrack = true;
	static Settings.UpdateMap updateMap;

	private CarData mCarData;
	static double lat = 0, lng = 0;
	static float maxrange = 160;
	static String distance_units = "KM";

	List<Marker> lis;

	public interface UpdateLocation {
		public void updatelocation();
	}


	// getMap: Initialize the map fragment
	// 	see: http://developer.android.com/about/versions/android-4.2.html#NestedFragments
	// 	and: https://developers.google.com/android/reference/com/google/android/gms/maps/SupportMapFragment
	private void getMap() {

		FragmentManager fm = getChildFragmentManager();

		fragment = (SupportMapFragment) fm.findFragmentById(R.id.mmap);
		if (fragment == null) {
			Log.d(TAG, "getMap: create newInstance()");
			fragment = SupportMapFragment.newInstance();
			fm.beginTransaction().replace(R.id.mmap, fragment).commit();
		}
		Log.d(TAG, "getMap: fragment=" + fragment);

		if (fragment != null) {
			fragment.getExtendedMapAsync(this);
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {

		map = googleMap;
		Log.d(TAG, "getMap/onMapReady: map=" + map);

		boolean clusterEnabled = true;
		int clusterSizeIndex = 0;
		float mapZoomLevel = 15;

		try {
			clusterEnabled = !appPrefes.getData("check").equals("false");
			clusterSizeIndex = Integer.parseInt(appPrefes.getData("progress"));
			mapZoomLevel = Float.parseFloat(appPrefes.getData("mapZoomLevel"));
		} catch (Exception e) {
			// ignore
		}

		updateClustering(clusterSizeIndex, clusterEnabled);

		map.setOnInfoWindowClickListener(this);

		map.getUiSettings().setRotateGesturesEnabled(false); // disable two-finger rotation gesture
		map.getUiSettings().setZoomControlsEnabled(true); // enable zoom +/- buttons
		map.getUiSettings().setMapToolbarEnabled(true); // enable Google Maps shortcuts

		// map.setMyLocationEnabled(!autotrack); // crashing on SDK27 / maps 26
		map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
			@Override
			public boolean onMyLocationButtonClick() {
				// move camera to car position if available:
				if (lat == 0 && lng == 0)
					return false;
				LatLng carPosition = new LatLng(lat, lng);
				map.moveCamera(CameraUpdateFactory.newLatLng(carPosition));
				return true;
			}
		});

		Log.d(TAG, "getMap/onMapReady: mapZoomLevel=" + mapZoomLevel);
		map.moveCamera(CameraUpdateFactory.zoomTo(mapZoomLevel)); // init zoom level
		map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
			@Override
			public void onCameraChange(CameraPosition cameraPosition) {
				appPrefes.SaveData("mapZoomLevel", "" + cameraPosition.zoom);
				Log.d(TAG, "getMap/onCameraChange: new mapZoomLevel=" + cameraPosition.zoom);
			}
		});

		update();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.mmap, null);
		appPrefes = new AppPrefes(getActivity(), "ovms");
		updateMap = this;

		lat = 37.410866;
		lng = -122.001946;
		maxrange = 285;
		distance_units = "KM";

		database = new Database(getActivity());

		getMap();

		setHasOptionsMenu(true);

		mapInitState = true;

		// get config:
		autotrack = !appPrefes.getData("autotrack").equals("off");

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		getMap();
	}

	@Override
	public void onDestroyView() {
		try {
			mapInitState = true;

			database.close();

		} catch (Exception e) {
			// nop
		}
		super.onDestroyView();
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.map_options, menu);

		optionsMenu = menu;

		// set checkboxes:
		optionsMenu.findItem(R.id.mi_map_autotrack)
				.setChecked(autotrack);
		optionsMenu.findItem(R.id.mi_map_filter_connections)
				.setChecked(appPrefes.getData("filter").equals("on"));
		optionsMenu.findItem(R.id.mi_map_filter_range)
				.setChecked(appPrefes.getData("inrange").equals("on"));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int menuId = item.getItemId();
		boolean newState = !item.isChecked();

		switch(menuId) {

			case R.id.mi_map_autotrack:
				appPrefes.SaveData("autotrack", newState ? "on" : "off");
				item.setChecked(newState);
				autotrack = newState;
				if (autotrack)
					update();
				// map.setMyLocationEnabled(!autotrack); // crashing on SDK27 / maps 26
				break;

			case R.id.mi_map_filter_connections:
				appPrefes.SaveData("filter", newState ? "on" : "off");
				item.setChecked(newState);
				after(false);
				break;

			case R.id.mi_map_filter_range:
				appPrefes.SaveData("inrange", newState ? "on" : "off");
				item.setChecked(newState);
				after(false);
				break;

			case R.id.mi_map_settings:
				Bundle args = new Bundle();
				BaseFragmentActivity.show(getActivity(), Settings.class, args,
						Configuration.ORIENTATION_UNDEFINED);
				break;
		}

		return false;
	}


	@Override
	public void updateClustering(int clusterSizeIndex, boolean clusterEnabled) {

		Log.d(TAG, "getMap/updateClustering(" + clusterSizeIndex
				+ "," + clusterEnabled + "): map=" + map);

		if (map == null)
			return;

		map.setClustering(
				new ClusteringSettings()
						.clusterOptionsProvider(new DemoClusterOptionsProvider(getResources()))
						.addMarkersDynamically(true)
						.clusterSize(CLUSTER_SIZES[clusterSizeIndex])
						.enabled(clusterEnabled));
	}


	private void direction() {
		// TODO Auto-generated method stub
		String directions = "https://maps.google.com/maps?saddr=Kanyakumari,+Tamil+Nadu,+India&daddr=Trivandrum,+Kerala,+India";
		directions = "https://maps.google.com/maps?saddr=37.410866,-122.001946&daddr="
				+ slat + "," + slng;
		// Create Google Maps intent from current location to target location
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse(directions));
		intent.setClassName("com.google.android.apps.maps",
				"com.google.android.maps.MapsActivity");
		startActivity(intent);
	}


	// marker click event
	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		int j = marker.getClusterGroup();
		Log.d(TAG, "click: ClusterGroup=" + j);
		if (j == 0) {
			dialog(marker);
		}
	}

	// after fetch value from server
	@Override
	public void after(boolean clearmap) {

		if (map == null)
			return;

		Log.d(TAG, "after: clearmap=" + clearmap);

		if (clearmap) {
			map.clear();
		} else {
			lis = map.getMarkers();
			for (int i = 0; i < lis.size(); i++) {
				Marker carmarker = lis.get(i);
				int j = carmarker.getClusterGroup();
				if (j == -1) {
					lis.remove(i);
				} else {
					carmarker.remove();
				}
			}
		}


		// Load charge points from database:

		Cursor cursor;
		boolean check_range = false;
		double maxrange_m = 0;

		if (appPrefes.getData("filter").equals("on")) {
			// check if filter is defined, else fallback to all stations:
			String connectionList = appPrefes.getData("Id");
			Log.d(TAG, "after: connectionList=(" + connectionList + ")");
			if (!connectionList.equals(""))
				cursor = database.get_mapdetails(connectionList);
			else
				cursor = database.get_mapdetails();
		} else {
			// filter off:
			cursor = database.get_mapdetails();
		}

		if (appPrefes.getData("inrange").equals("on")) {
			check_range = true;
			if (distance_units.equals("Miles"))
				maxrange_m = maxrange * 1.609344 * 1000;
			else
				maxrange_m = maxrange * 1000;
		}

		Log.d(TAG, "after: addMarkers avail=" + cursor.getCount());

		if (cursor.getCount() != 0) {
			int cnt_added = 0;
			if (cursor.moveToFirst()) {
				do {
					// check position:

					try {
						double Latitude = Double.parseDouble(cursor
								.getString(cursor.getColumnIndex("Latitude")));
						double Longitude = Double.parseDouble(cursor
								.getString(cursor.getColumnIndex("Longitude")));

						if (check_range) {
							if (distance(lat, lng, Latitude, Longitude) > maxrange_m)
								continue;
						}

						// add marker:

						String cpid = cursor.getString(cursor.getColumnIndex("cpid"));
						String title = cursor.getString(cursor.getColumnIndex("Title"));
						String snippet = cursor.getString(cursor.getColumnIndex("OperatorInfo"));

						MarkerGenerator.addMarkers(map,
								title, snippet,
								new LatLng(Latitude, Longitude),
								cpid);

						cnt_added++;
					} catch (Exception e) {
						Log.e(TAG, "skipped charge point: ERROR", e);
					}

				} while (cursor.moveToNext());
			}

			Log.d(TAG, "after: addMarkers added=" + cnt_added);
		}
	}


	// click marker event
	private void dialog(Marker marker) {
		// open dialog:
		Bundle args = new Bundle();
		args.putString("cpId", (String) marker.getData());
		BaseFragmentActivity.show(getActivity(), DetailFragment.class, args,
				Configuration.ORIENTATION_UNDEFINED);
	}


	@Override
	public void update(CarData pCarData) {
		mCarData = pCarData;
		update();
	}


	public void update() {

		if (mCarData == null)
			return;
		if (map == null)
			return;
		if (getContext() == null)
			return;

		// get last known car position:

		lat = mCarData.car_latitude;
		lng = mCarData.car_longitude;
		maxrange = Math.max(mCarData.car_range_estimated_raw, mCarData.car_range_ideal_raw);
		distance_units = (mCarData.car_distance_units_raw.equals("M") ? "Miles" : "KM");

		Log.d(TAG, "update: Car on map: lat=" + lat + " lng=" + lng
				+ " maxrange=" + maxrange + distance_units);

		// update charge point markers:

		after(true);

		// update car position marker:

		LatLng carPosition = new LatLng(lat, lng);
		Drawable drawable;
		if (mCarData.sel_vehicle_image.startsWith("car_imiev_"))
			drawable = getResources().getDrawable(R.drawable.map_car_imiev); // one map icon for all colors
		else if (mCarData.sel_vehicle_image.startsWith("car_smart_"))
			drawable = getResources().getDrawable(R.drawable.map_car_smart); // one map icon for all colors
		else if (mCarData.sel_vehicle_image.startsWith("car_kianiro_"))
			drawable = getResources().getDrawable(R.drawable.map_car_kianiro_grey); // one map icon for all colors
		else
			drawable = getResources().getDrawable(Ui.getDrawableIdentifier(getActivity(),
					"map_" + mCarData.sel_vehicle_image));
		Bitmap myLogo = ((BitmapDrawable) drawable).getBitmap();
		MarkerOptions marker = new MarkerOptions().position(carPosition)
				.title(mCarData.sel_vehicle_label)
				.rotation((float) mCarData.car_direction)
				.icon(BitmapDescriptorFactory.fromBitmap(myLogo));
		Marker carmarker = map.addMarker(marker);
		carmarker.setClusterGroup(ClusterGroup.NOT_CLUSTERED);

		if (mapInitState || autotrack) {
			map.moveCamera(CameraUpdateFactory.newLatLng(carPosition));
			mapInitState = false;
		}

		// update range circles:

		Log.i(TAG, "update: adding range circles:"
				+ " ideal=" + mCarData.car_range_ideal_raw
				+ " estimated=" + mCarData.car_range_estimated_raw);
		addCircles(mCarData.car_range_ideal_raw,
				mCarData.car_range_estimated_raw);


		// start chargepoint data update:

		appPrefes.SaveData("lat_main", "" + mCarData.car_latitude);
		appPrefes.SaveData("lng_main", "" + mCarData.car_longitude);

		MainActivity.updateLocation.updatelocation();

	}

	// draw circle in a map
	private void addCircles(float rd1, float rd2) {
		if (map == null)
			return;

		// fix for issue #79 by @Timopen:
		if (rd1 < 0) rd1 = 0;
		if (rd2 < 0) rd2 = 0;

		float strokeWidth = getResources().getDimension(
				R.dimen.circle_stroke_width);
		List<PatternItem> pattern = Arrays.<PatternItem>asList(new Dot());

		// full range circles:

		map.addCircle(new CircleOptions()
				.data("first circle")
				.center(new LatLng(lat, lng))
				.radius(rd1 * 1000)
				.strokeWidth(strokeWidth)
				.strokeColor(Color.BLUE));

		map.addCircle(new CircleOptions()
				.data("second circle")
				.center(new LatLng(lat, lng))
				.radius(rd2 * 1000)
				.strokeWidth(strokeWidth)
				.strokeColor(Color.RED));

		// half range ("point of no return") circles:

		map.addCircle(new CircleOptions()
				.data("first ponr")
				.center(new LatLng(lat, lng))
				.radius(rd1 * 1000 / 2)
				.strokeWidth(strokeWidth / 2)
				.strokePattern(pattern)
				.strokeColor(Color.parseColor("#A04455FF")));

		map.addCircle(new CircleOptions()
				.data("second ponr")
				.center(new LatLng(lat, lng))
				.radius(rd2 * 1000 / 2)
				.strokeWidth(strokeWidth / 2)
				.strokePattern(pattern)
				.strokeColor(Color.parseColor("#A0FF5544")));
	}

	// calculate distance in meters:
	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		double dLat = Math.toRadians(lat2-lat1);
		double dLon = Math.toRadians(lon2-lon1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
						Math.sin(dLon/2) * Math.sin(dLon/2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return 6371000 * c;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_route:
			direction();
			break;

		default:
			break;
		}
	}

	@Override
	public void clearCache() {
		database.clear_mapdetails();
		MainActivity.updateLocation.updatelocation();
	}

	@Override
	public void updateFilter(String connectionList) {
		// update markers:
		after(false);
	}

}
