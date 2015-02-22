package com.openvehicles.OVMS.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pl.mg6.android.maps.extensions.CircleOptions;
import pl.mg6.android.maps.extensions.ClusterGroup;
import pl.mg6.android.maps.extensions.ClusteringSettings;
import pl.mg6.android.maps.extensions.GoogleMap;
import pl.mg6.android.maps.extensions.GoogleMap.OnInfoWindowClickListener;
import pl.mg6.android.maps.extensions.Marker;
import pl.mg6.android.maps.extensions.MarkerOptions;
import pl.mg6.android.maps.extensions.SupportMapFragment;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.GetMapDetails.afterasytask;
import com.openvehicles.OVMS.ui.Settings.Updateclust;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.ui.utils.DemoClusterOptionsProvider;
import com.openvehicles.OVMS.ui.utils.MarkerGenerator;
import com.openvehicles.OVMS.ui.utils.Ui;

public class FragMap extends BaseFragment implements OnInfoWindowClickListener,
		afterasytask, OnClickListener, Updateclust {
	private static final String TAG = "FragMap";

	private GoogleMap map;
	Database database;
	String slat, slng;
	private Menu menu;
	AppPrefes appPrefes;
	static boolean flag = true;
	private static final double[] CLUSTER_SIZES = new double[] { 360, 180, 90, 45, 22 };
	View rootView;
	boolean autotrack = true;
	static Updateclust updateclust;

	static double lat = 0, lng = 0;
	static int maxrange = 160;
	static String distance_units = "KM";

	public interface UpdateLocation {
		public void updatelocation();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.mmap, null);
		appPrefes = new AppPrefes(getActivity(), "ovms");
		updateclust = this;

		lat = 37.410866;
		lng = -122.001946;
		maxrange = 285;
		distance_units = "KM";

		database = new Database(getActivity());
		FragmentManager fm = getActivity().getSupportFragmentManager();
		SupportMapFragment f = (SupportMapFragment) fm
				.findFragmentById(R.id.mmap);
		map = f.getExtendedMap();
		map.setClustering(new ClusteringSettings().clusterOptionsProvider(
				new DemoClusterOptionsProvider(getResources()))
				.addMarkersDynamically(true));
		map.setOnInfoWindowClickListener(this);
    	map.getUiSettings().setRotateGesturesEnabled(false); // Disable two-finger rotation gesture
		// setUpClusteringViews();
		setHasOptionsMenu(true);
		flag = true;
		// after();

		return rootView;
	}

	void updateClustering(int clusterSizeIndex, boolean enabled) {
		ClusteringSettings clusteringSettings = new ClusteringSettings();
		clusteringSettings.addMarkersDynamically(true);
		if (enabled) {
			after(false);
			clusteringSettings
					.clusterOptionsProvider(new DemoClusterOptionsProvider(
							getResources()));

			double clusterSize = CLUSTER_SIZES[clusterSizeIndex];
			clusteringSettings.clusterSize(clusterSize);
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
		map.setClustering(clusteringSettings);
	}

	List<Marker> lis;

	private void replacefragment(Fragment frag) {
		// TODO Auto-generated method stub
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		ft.replace(R.id.fm, frag);
		ft.commit();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// inflater.inflate(R.menu.option, menu);
		this.menu = menu;
		SubMenu sub = menu.addSubMenu("Options");
		sub.add(0, 1, 0, "Turn OFF autotrack");
		if (appPrefes.getData("filter").equals("off")) {
			sub.add(0, 2, 1, "Filtered Stations ON");
		} else {
			sub.add(0, 2, 1, "Filtered Stations OFF");
		}
		if (appPrefes.getData("inrange").equals("off")) {
			sub.add(0, 3, 2, "Only show Stations in range ON");
		} else {
			sub.add(0, 3, 2, "Only show Stations in range OFF");
		}
		sub.add(0, 4, 3, "Settings");
		sub.getItem().setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			changemenuitem(1);
		} else if (item.getItemId() == 2) {
			changemenuitem(2);
		} else if (item.getItemId() == 3) {
			changemenuitem(3);
		} else if (item.getItemId() == 4) {
			Bundle args = new Bundle();
			BaseFragmentActivity.show(getActivity(), Settings.class, args,
					Configuration.ORIENTATION_UNDEFINED);
		}
		return false;
	}

	private void changemenuitem(int value) {
		// TODO Auto-generated method stub
		MenuItem turnoff = menu.findItem(value);
		if (value == 1) {
			if (turnoff.getTitle().toString().equals("Turn OFF autotrack")) {
				turnoff.setTitle("Turn ON autotrack");
				autotrack = false;
			} else {
				turnoff.setTitle("Turn OFF autotrack");
				autotrack = true;
			}
		} else if (value == 2) {
			if (turnoff.getTitle().toString().equals("Filtered Stations ON")) {
				appPrefes.SaveData("filter", "on");
				turnoff.setTitle("Filtered Stations OFF");
			} else {
				appPrefes.SaveData("filter", "off");
				turnoff.setTitle("Filtered Stations ON");
			}
			after(false);
		} else if (value == 3) {
			if (turnoff.getTitle().toString().equals("Only show Stations in range OFF")) {
				appPrefes.SaveData("inrange", "off");
				turnoff.setTitle("Only show Stations in range ON");
			} else {
				appPrefes.SaveData("inrange", "on");
				turnoff.setTitle("Only show Stations in range OFF");
			}
			after(false);
		}

	}

	@Override
	public void onDestroyView() {
		try {
			flag = true;
			SupportMapFragment fragment = ((SupportMapFragment) getFragmentManager()
					.findFragmentById(R.id.mmap));
			FragmentTransaction ft = getActivity().getSupportFragmentManager()
					.beginTransaction();
			ft.remove(fragment);
			ft.commit();
			database.close();
		} catch (Exception e) {
		}
		super.onDestroyView();
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
			cursor = database.get_mapdetails(appPrefes.getData("Id"));
		} else {
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

					double Latitude = Double.parseDouble(cursor
							.getString(cursor.getColumnIndex("lat")));
					double Longitude = Double.parseDouble(cursor
							.getString(cursor.getColumnIndex("lng")));

					if (check_range) {
						if (distance(lat, lng, Latitude, Longitude) > maxrange_m)
							continue;
					}

					// add marker:

					String cpid = cursor.getString(cursor.getColumnIndex("cpid"));

					MarkerGenerator.addMarkers(map,
							cursor.getString(cursor.getColumnIndex("title")),
							new LatLng(Latitude, Longitude),
							cpid);

					cnt_added++;

				} while (cursor.moveToNext());
			}

			Log.d(TAG, "after: addMarkers added=" + cnt_added);
		}
	}


	double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		//Log.d(TAG, "roundTwoDecimals=" + twoDForm.format(d));
		return Double.valueOf(twoDForm.format(d));
	}


	// click marker event
	private void dialog(Marker marker) {
		// open dialog:
		Bundle args = new Bundle();
		args.putString("cpId", (String) marker.getData());
		BaseFragmentActivity.show(getActivity(), DetailFragment.class, args,
				Configuration.ORIENTATION_UNDEFINED);
	}


	// fetch latitude and longitude
	@Override
	public void update(CarData pCarData) {

		// get car position:

		lat = pCarData.car_latitude;
		lng = pCarData.car_longitude;
		maxrange = Math.max(pCarData.car_range_estimated_raw, pCarData.car_range_ideal_raw);
		distance_units = (pCarData.car_distance_units_raw.equals("M") ? "Miles" : "KM");

		Log.d(TAG, "update: Car on map: lat=" + lat + " lng=" + lng
				+ " maxrange=" + maxrange + distance_units);

		// update charge point markers:

		after(true);

		// update car position marker:

		final LatLng MELBOURNE = new LatLng(lat, lng);
		Drawable drawable = getResources().getDrawable(
				Ui.getDrawableIdentifier(getActivity(), "map_"
						+ pCarData.sel_vehicle_image));
		Bitmap myLogo = ((BitmapDrawable) drawable).getBitmap();
		MarkerOptions marker = new MarkerOptions().position(MELBOURNE)
				.title(pCarData.sel_vehicle_label)
				.rotation((float) pCarData.car_direction)
				.icon(BitmapDescriptorFactory.fromBitmap(myLogo));
		Marker carmarker = map.addMarker(marker);
		carmarker.setClusterGroup(ClusterGroup.NOT_CLUSTERED);

		if (flag) {
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(MELBOURNE, 18));
			flag = false;
		} else if (autotrack) {
			map.moveCamera(CameraUpdateFactory.newLatLng(MELBOURNE));
		}

		// update range circles:

		Log.i(TAG, "update: adding range circles:"
				+ " ideal=" + pCarData.car_range_ideal_raw
				+ " estimated=" + pCarData.car_range_estimated_raw);
		addCircles(pCarData.car_range_ideal_raw,
				pCarData.car_range_estimated_raw);


		// start chargepoint data update:

		appPrefes.SaveData("lat_main", "" + pCarData.car_latitude);
		appPrefes.SaveData("lng_main", "" + pCarData.car_longitude);

		MainActivity.updateLocation.updatelocation();

	}

	// draw circle in a map
	private void addCircles(int rd1, int rd2) {
		float strokeWidth = getResources().getDimension(
				R.dimen.circle_stroke_width);
		CircleOptions options = new CircleOptions().strokeWidth(strokeWidth)
				.strokeColor(Color.BLUE);
		rd1 = rd1 * 1000;
		rd2 = rd2 * 1000;
		map.addCircle(options.center(new LatLng(lat, lng)).data("first circle")
				.radius(rd1));
		options = new CircleOptions().strokeWidth(strokeWidth).strokeColor(
				Color.RED);
		map.addCircle(options.center(new LatLng(lat, lng))
				.data("second circle").radius(rd2));
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
	public void updateclust(int clusterSizeIndex, boolean enabled) {
		updateClustering(clusterSizeIndex, enabled);
	}

	@Override
	public void clearcache() {
		database.clear_latlngdetail();
		MainActivity.updateLocation.updatelocation();
	}
}
