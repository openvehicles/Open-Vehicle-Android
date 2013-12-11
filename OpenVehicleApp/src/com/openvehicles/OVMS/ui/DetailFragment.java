package com.openvehicles.OVMS.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;

public class DetailFragment extends Fragment {
	RelativeLayout rel_route;
	static String title, Usage, status, address, level1, level2, connection,
			slat, slng, number;
	AppPrefes appPrefes;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.detail, null);
		appPrefes = new AppPrefes(getActivity(), "ovms");
		rel_route = (RelativeLayout) view.findViewById(R.id.rel_route);
		TextView value_opname = (TextView) view.findViewById(R.id.value_opname);
		TextView value_opstatus = (TextView) view
				.findViewById(R.id.value_opstatus);
		TextView value_addres = (TextView) view.findViewById(R.id.value_addres);
		TextView value_levelone = (TextView) view
				.findViewById(R.id.value_levelone);
		TextView value_conn_type_one = (TextView) view
				.findViewById(R.id.value_conn_type_one);
		TextView value_leveltwo = (TextView) view
				.findViewById(R.id.value_leveltwo);
		TextView value_conn_type_two = (TextView) view
				.findViewById(R.id.value_conn_type_two);
		TextView value_usage = (TextView) view.findViewById(R.id.value_opusage);
		TextView numberofpoint = (TextView) view
				.findViewById(R.id.value_points);
		value_opname.setText("" + title);
		value_opstatus.setText("" + status);
		value_addres.setText("" + address);
		value_levelone.setText("" + level1);
		value_conn_type_one.setText("" + connection);
		value_leveltwo.setText("" + level2);
		value_conn_type_two.setText("" + connection);
		value_usage.setText("" + Usage);
		numberofpoint.setText("" + number);
		rel_route.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				direction();
			}
		});
		return view;
	}

	private void direction() {
		// TODO Auto-generated method stub
		String directions = "https://maps.google.com/maps?saddr=Kanyakumari,+Tamil+Nadu,+India&daddr=Trivandrum,+Kerala,+India";
		directions = "https://maps.google.com/maps?saddr="
				+ appPrefes.getData("lat_main") + ","
				+ appPrefes.getData("lng_main") + "&daddr=" + slat + "," + slng;
		// Create Google Maps intent from current location to target location
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse(directions));
		intent.setClassName("com.google.android.apps.maps",
				"com.google.android.maps.MapsActivity");
		startActivity(intent);
	}
}
