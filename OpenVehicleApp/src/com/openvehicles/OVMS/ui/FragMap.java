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
	String url;
	private GoogleMap map;
	ArrayList<String> al_lat = new ArrayList<String>();
	ArrayList<String> al_lng = new ArrayList<String>();
	ArrayList<String> al_title = new ArrayList<String>();
	ArrayList<String> al_address = new ArrayList<String>();
	ArrayList<String> al_level1 = new ArrayList<String>();
	ArrayList<String> al_level2 = new ArrayList<String>();
	ArrayList<String> al_connction1 = new ArrayList<String>();
	ArrayList<String> al_connction_id = new ArrayList<String>();
	ArrayList<String> al_optr = new ArrayList<String>();
	ArrayList<String> al_usage = new ArrayList<String>();
	ArrayList<String> al_status = new ArrayList<String>();
	ArrayList<String> al_numberofpoints = new ArrayList<String>();
	Database database;
	String slat, slng;
	private Menu menu;
	AppPrefes appPrefes;
	static boolean flag = true;
	static boolean flag1 = true;
	private static final double[] CLUSTER_SIZES = new double[] { 360, 180, 90, 45, 22 };
	View rootView;
	boolean autotrack = true;
	static Updateclust updateclust;

	public interface UpdateLocation {
		public void updatelocation(String url);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.mmap, null);
		appPrefes = new AppPrefes(getActivity(), "ovms");
		updateclust = this;
		url = "http://api.openchargemap.io/v2/poi/?output=json&countrycode=US&maxresults=100";
		url = "http://www.openchargemap.org/api/?output=json&latitude=37.410866&longitude=-122.001946&distance=285&distanceunit=KM&maxresults=500";
		System.out.println("url" + url);
		database = new Database(getActivity());
		FragmentManager fm = getActivity().getSupportFragmentManager();
		SupportMapFragment f = (SupportMapFragment) fm
				.findFragmentById(R.id.mmap);
		map = f.getExtendedMap();
		map.setClustering(new ClusteringSettings().clusterOptionsProvider(
				new DemoClusterOptionsProvider(getResources()))
				.addMarkersDynamically(true));
		map.setOnInfoWindowClickListener(this);
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
			// if (lis != null) {
			// for (int i = 0; i < lis.size(); i++) {
			// MarkerGenerator.addMarkers(map, al_title.get(i), lis.get(i)
			// .getPosition());
			// }
			if (appPrefes.getData("filter").equals("off")) {
				after(false);
			} else {
				filter(false);
			}
			clusteringSettings
					.clusterOptionsProvider(new DemoClusterOptionsProvider(
							getResources()));

			double clusterSize = CLUSTER_SIZES[clusterSizeIndex];
			clusteringSettings.clusterSize(clusterSize);
			// }
		} else {
			// clusteringSettings.addMarkersDynamically(false);
			// map.getMarkers().clear();
			// map.clear();
			// clusteringSettings.enabled(false);
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
			// map.clear();
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
		sub.add(0, 3, 2, "Only show Stations in range OFF");
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
			url = "http://www.openchargemap.org/api/?output=json&latitude="
					+ lat
					+ "&longitude="
					+ lng
					+ "&distance=285&distanceunit=KM&maxresults=500&connectiontypeid="
					+ appPrefes.getData("Id");
			System.out.println("menu" + url);
			// getdata();
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
				filter(false);
			} else {
				appPrefes.SaveData("filter", "off");
				turnoff.setTitle("Filtered Stations ON");
				after(false);
			}
		} else if (value == 3) {
			if (turnoff.getTitle().toString()
					.equals("Only show Stations in range OFF")) {
				turnoff.setTitle("Only show Stations in range ON");
			} else
				turnoff.setTitle("Only show Stations in range OFF");
		}

	}

	public void onDestroyView() {
		try {
			flag = true;
			SupportMapFragment fragment = ((SupportMapFragment) getFragmentManager()
					.findFragmentById(R.id.mmap));
			FragmentTransaction ft = getActivity().getSupportFragmentManager()
					.beginTransaction();
			ft.remove(fragment);
			ft.commit();
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

	// fetch data from server
	// private void getdata() {
	// GetMapDetails getMapDetails = new GetMapDetails(getActivity(), url,
	// this);
	// getMapDetails.execute();
	// System.out.println("url" + url);
	// }

	// marker click event
	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		int j = marker.getClusterGroup();
		System.out.println("iii" + j);
		if (j == 0) {
			dialog(marker);
		}
	}

	// after fetch value from server
	@Override
	public void after(boolean flag) {
		// TODO Auto-generated method stub
		System.out.println("aaaaaaaaaaaaaaaa");
		al_lat.clear();
		al_lng.clear();
		al_title.clear();
		al_address.clear();
		al_numberofpoints.clear();
		al_level1.clear();
		al_level2.clear();
		al_connction1.clear();
		al_connction_id.clear();
		al_optr.clear();
		al_usage.clear();
		al_status.clear();
		if (flag) {
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
		Cursor cursor = database.get_mapdetails();
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					double Latitude = Double.parseDouble(cursor
							.getString(cursor.getColumnIndex("lat")));
					double Longitude = Double.parseDouble(cursor
							.getString(cursor.getColumnIndex("lng")));
					MarkerGenerator.addMarkers(map,
							cursor.getString(cursor.getColumnIndex("title")),
							new LatLng(Latitude, Longitude));
					al_lat.add(cursor.getString(cursor.getColumnIndex("lat")));
					al_lng.add(cursor.getString(cursor.getColumnIndex("lng")));
					al_title.add(cursor.getString(cursor
							.getColumnIndex("title")));
					al_address.add(cursor.getString(cursor
							.getColumnIndex("AddressLine1")));
					al_numberofpoints.add(cursor.getString(cursor
							.getColumnIndex("numberofpoint")));
					al_level1.add(cursor.getString(cursor
							.getColumnIndex("level1")));
					al_level2.add(cursor.getString(cursor
							.getColumnIndex("level2")));
					al_connction1.add(cursor.getString(cursor
							.getColumnIndex("connction1")));
					al_connction_id.add(cursor.getString(cursor
							.getColumnIndex("connction_id")));
					al_optr.add(cursor.getString(cursor.getColumnIndex("optr")));
					al_usage.add(cursor.getString(cursor
							.getColumnIndex("usage")));
					al_status.add(cursor.getString(cursor
							.getColumnIndex("status")));
				} while (cursor.moveToNext());
			}
		}
	}

	public void filter(boolean flag) {
		// TODO Auto-generated method stub
		if (flag) {
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
		System.out.println("aaaaaaaaaaaaaaaa");
		al_title.clear();
		al_lat.clear();
		al_lng.clear();
		al_address.clear();
		al_numberofpoints.clear();
		al_level1.clear();
		al_level2.clear();
		al_connction1.clear();
		al_connction_id.clear();
		al_optr.clear();
		al_usage.clear();
		al_status.clear();
		Cursor cursor = database.get_mapdetails(appPrefes.getData("Id"));
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					double Latitude = Double.parseDouble(cursor
							.getString(cursor.getColumnIndex("lat")));
					double Longitude = Double.parseDouble(cursor
							.getString(cursor.getColumnIndex("lng")));
					MarkerGenerator.addMarkers(map,
							cursor.getString(cursor.getColumnIndex("title")),
							new LatLng(Latitude, Longitude));
					al_lat.add(cursor.getString(cursor.getColumnIndex("lat")));
					al_lng.add(cursor.getString(cursor.getColumnIndex("lng")));
					al_title.add(cursor.getString(cursor
							.getColumnIndex("title")));
					al_address.add(cursor.getString(cursor
							.getColumnIndex("AddressLine1")));
					al_numberofpoints.add(cursor.getString(cursor
							.getColumnIndex("numberofpoint")));
					al_level1.add(cursor.getString(cursor
							.getColumnIndex("level1")));
					al_level2.add(cursor.getString(cursor
							.getColumnIndex("level2")));
					al_connction1.add(cursor.getString(cursor
							.getColumnIndex("connction1")));
					al_connction_id.add(cursor.getString(cursor
							.getColumnIndex("connction_id")));
					al_optr.add(cursor.getString(cursor.getColumnIndex("optr")));
					al_usage.add(cursor.getString(cursor
							.getColumnIndex("usage")));
					al_status.add(cursor.getString(cursor
							.getColumnIndex("status")));
				} while (cursor.moveToNext());
			}
		}
	}

	double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		System.out.println("lattt" + twoDForm.format(d));
		return Double.valueOf(twoDForm.format(d));
	}

	// click marker event
	private void dialog(Marker marker) {
		// TODO Auto-generated method stub
		Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.mapdialog);
		dialog.setTitle("Information");
		Button bt_route = (Button) dialog.findViewById(R.id.bt_route);
		TextView tv_title = (TextView) dialog.findViewById(R.id.tvtitle);
		TextView tv_lat = (TextView) dialog.findViewById(R.id.tvlat);
		TextView tv_lng = (TextView) dialog.findViewById(R.id.tvlng);
		TextView tv_address = (TextView) dialog.findViewById(R.id.tv_address);
		TextView tv_level1 = (TextView) dialog.findViewById(R.id.tv_level1);
		TextView tv_level2 = (TextView) dialog.findViewById(R.id.tv_level2);
		TextView tv_connction1 = (TextView) dialog
				.findViewById(R.id.tv_connction1);
		bt_route.setOnClickListener(this);
		for (int i = 0; i < al_title.size(); i++) {
			if (marker.getTitle().endsWith(al_title.get(i))) {
				DetailFragment.address = al_address.get(i) + "";
				DetailFragment.number = al_numberofpoints.get(i) + "";
				DetailFragment.title = al_optr.get(i) + "";
				DetailFragment.Usage = al_usage.get(i) + "";
				DetailFragment.level1 = al_level1.get(i) + "";
				DetailFragment.level2 = al_level2.get(i) + "";
				DetailFragment.connection = al_connction1.get(i) + "";
				DetailFragment.status = al_status.get(i) + "";
				tv_title.setText(al_optr.get(i));
				tv_lat.setText(al_usage.get(i));
				tv_lng.setText(al_status.get(i));
				tv_address.setText(al_address.get(i));
				tv_level1.setText(al_level1.get(i));
				tv_level2.setText(al_level2.get(i));
				tv_connction1.setText(al_connction1.get(i));
				slat = al_lat.get(i);
				slng = al_lng.get(i);
				DetailFragment.slat = slat;
				DetailFragment.slng = slng;
			}
		}
		Bundle args = new Bundle();
		BaseFragmentActivity.show(getActivity(), DetailFragment.class, args,
				Configuration.ORIENTATION_UNDEFINED);
		// dialog.show();
	}

	static double lat = 0, lng;

	// fetch latitude and longitude
	@Override
	public void update(CarData pCarData) {
		Log.d("OVMS", "Car on map: " + pCarData.car_latitude + " lng"
				+ pCarData.car_longitude);
		System.out.println("latitude" + lat);
		lat = pCarData.car_latitude;
		lng = pCarData.car_longitude;
//		appPrefes.SaveData("sel_vehicle_label", pCarData.sel_vehicle_label);   
		if (appPrefes.getData("filter").equals("on")) {
			filter(true);
		} else {
			after(true);
		}
		final LatLng MELBOURNE = new LatLng(lat, lng);
		Drawable drawable = getResources().getDrawable(
				Ui.getDrawableIdentifier(getActivity(), "map_"
						+ pCarData.sel_vehicle_image));
		Bitmap myLogo = ((BitmapDrawable) drawable).getBitmap();
		MarkerOptions marker = new MarkerOptions().position(MELBOURNE)
				.title(pCarData.sel_vehicle_label)
				.rotation((float) pCarData.car_direction)
				.icon(BitmapDescriptorFactory.fromBitmap(myLogo));
		// .fromResource(R.drawable.map_car_roadster_lightninggreen));
		Marker carmarker = map.addMarker(marker);
		carmarker.setClusterGroup(ClusterGroup.NOT_CLUSTERED);
		if (flag) {
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(MELBOURNE, 18));
			flag = false;
		} else if (autotrack)
			map.moveCamera(CameraUpdateFactory.newLatLng(MELBOURNE));

		// database.insert_mapdetails("" + lat, "" + lng, "Melbourne", " ",
		// " ", " ", " ");
		al_lat.add("" + lat);
		al_lng.add("" + lng);
		al_title.add("DEMO");
		al_address.add("");
		al_numberofpoints.add("");
		al_level1.add("");
		al_level2.add("");
		al_connction1.add("");
		al_connction_id.add("");
		al_optr.add("");
		al_usage.add("");
		al_status.add("");
		System.out.println("first" + pCarData.car_range_ideal_raw + "  second"
				+ pCarData.car_range_estimated_raw);
		addCircles(pCarData.car_range_ideal_raw,
				pCarData.car_range_estimated_raw);
		url = "http://www.openchargemap.org/api/?output=json&latitude="
				+ pCarData.car_latitude + "&longitude="
				+ pCarData.car_longitude
				+ "&distance=285&distanceunit=KM&maxresults=500";
		String slat = "" + pCarData.car_latitude;
		String slng = "" + pCarData.car_longitude;
		String[] strings = slat.split("\\.");
		String[] strings2 = slng.split("\\.");
		int latitude = Integer.parseInt(strings[0]);
		int longitude = Integer.parseInt(strings2[0]);
		appPrefes.SaveData("lat_main", "" + pCarData.car_latitude);
		appPrefes.SaveData("lng_main", "" + pCarData.car_longitude);
		Cursor cursor = database.getlatlngdetail(latitude, longitude);
		if (cursor.moveToFirst()) {
		} else {
			database.addlatlngdetail(latitude, longitude);
			System.out.println("lat" + strings[0] + " app"
					+ appPrefes.getData("lat_main"));
			// getdata();
			MainActivity.updateLocation.updatelocation(url);
		}
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

	private void bounds() {
		// TODO Auto-generated method stub
		// LatLngBounds.Builder builder = new LatLngBounds.Builder();
		// builder.include(Latlng1);
		// builder.include(Latlng2);
		// LatLngBounds bounds = builder.build();
		// map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
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
		// TODO Auto-generated method stub
		updateClustering(clusterSizeIndex, enabled);
	}
}
