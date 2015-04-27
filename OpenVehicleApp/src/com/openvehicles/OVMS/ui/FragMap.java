package com.openvehicles.OVMS.ui;

import java.text.DecimalFormat;
import java.util.List;

import pl.mg6.android.maps.extensions.CircleOptions;
import pl.mg6.android.maps.extensions.ClusterGroup;
import pl.mg6.android.maps.extensions.ClusteringSettings;
import pl.mg6.android.maps.extensions.GoogleMap;
import pl.mg6.android.maps.extensions.GoogleMap.OnInfoWindowClickListener;
import pl.mg6.android.maps.extensions.Marker;
import pl.mg6.android.maps.extensions.MarkerOptions;
import pl.mg6.android.maps.extensions.SupportMapFragment;

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

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.GetMapDetails.afterasytask;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.ui.utils.DemoClusterOptionsProvider;
import com.openvehicles.OVMS.ui.utils.MarkerGenerator;
import com.openvehicles.OVMS.ui.utils.Ui;

public class FragMap extends BaseFragment implements OnInfoWindowClickListener,
		afterasytask, OnClickListener, Settings.UpdateMap,
		pl.mg6.android.maps.extensions.GoogleMap.OnMyLocationButtonClickListener {
	private static final String TAG = "FragMap";

	private GoogleMap map;
	Database database;
	String slat, slng;
	AppPrefes appPrefes;

	static boolean flag = true;
	private static final double[] CLUSTER_SIZES = new double[] { 360, 180, 90, 45, 22 };
	View rootView;
	Menu optionsMenu;
	boolean autotrack = true;
	static Settings.UpdateMap updateMap;

	private CarData mCarData;
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
		updateMap = this;

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
		map.moveCamera(CameraUpdateFactory.zoomTo(15));
        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
		// setUpClusteringViews();

		setHasOptionsMenu(true);
		flag = true;

		// get config:
		autotrack = !appPrefes.getData("autotrack").equals("off");

		return rootView;
	}

	@Override
	public void updateClustering(int clusterSizeIndex, boolean enabled) {
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

	private static LatLng mlatLng;

	@Override
	public void update(CarData pCarData) {
		mlatLng = new LatLng(pCarData.car_latitude, pCarData.car_longitude);
		mCarData = pCarData;
		update();
	}


	public void update() {

		if (mCarData == null)
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

		final LatLng MELBOURNE = new LatLng(lat, lng);
		Drawable drawable = getResources().getDrawable(
				Ui.getDrawableIdentifier(getActivity(), "map_"
						+ mCarData.sel_vehicle_image));
		Bitmap myLogo = ((BitmapDrawable) drawable).getBitmap();
		MarkerOptions marker = new MarkerOptions().position(MELBOURNE)
				.title(mCarData.sel_vehicle_label)
				.rotation((float) mCarData.car_direction)
				.icon(BitmapDescriptorFactory.fromBitmap(myLogo));
		Marker carmarker = map.addMarker(marker);
		carmarker.setClusterGroup(ClusterGroup.NOT_CLUSTERED);

		if (flag) {
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(MELBOURNE, 15));
			flag = false;
		}
/*		
		else if (autotrack) {
			map.moveCamera(CameraUpdateFactory.newLatLng(MELBOURNE));
		}
*/
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
	public void clearCache() {
		database.clear_latlngdetail();
		MainActivity.updateLocation.updatelocation();
	}

	@Override
	public void updateFilter(String connectionList) {
		// update markers:
		after(false);
	}
	
    @Override
    public boolean onMyLocationButtonClick() {
    	
		if(mlatLng != null)
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(mlatLng, 18));
//		else Toast.makeText(getActivity(), R.string.Undefined_location, Toast.LENGTH_SHORT).show();//Location of a vehicle is not defined
    	
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return true;//false;
    }

}
