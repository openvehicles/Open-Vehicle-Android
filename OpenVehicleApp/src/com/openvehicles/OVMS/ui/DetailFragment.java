package com.openvehicles.OVMS.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.ui.utils.Ui;

public class DetailFragment extends Fragment {

	private String cpId, slat, slng;

	RelativeLayout rel_route;
	AppPrefes appPrefes;
	Database database;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		appPrefes = new AppPrefes(getActivity(), "ovms");
		database = new Database(getActivity());


		// get chargepoint id to display:

		Bundle args = getArguments();
		if (args != null)
			cpId = args.getString("cpId");
		if (cpId == null)
			return null;


		// load chargepoint data:

		Cursor row = database.getChargePoint(cpId);
		if (!row.moveToFirst())
			return null;

		slat = row.getString(row.getColumnIndex("lat"));
		slng = row.getString(row.getColumnIndex("lng"));

		String title, usage, status, address, number;
		title = row.getString(row.getColumnIndex("title"));
		status = row.getString(row.getColumnIndex("status"));
		address = row.getString(row.getColumnIndex("AddressLine1"));
		usage = row.getString(row.getColumnIndex("usage"));
		number = row.getString(row.getColumnIndex("numberofpoint"));

		row.close();


		// create and fill view:

		View detail = inflater.inflate(R.layout.detail, null);

		Ui.setValue(detail, R.id.value_opname, "" + title);
		Ui.setValue(detail, R.id.value_opstatus, "" + status);
		Ui.setValue(detail, R.id.value_addres, "" + address);
		Ui.setValue(detail, R.id.value_opusage, "" + usage);
		Ui.setValue(detail, R.id.value_points, "" + number);


		// add connections:

		LinearLayout layout = (LinearLayout) detail.findViewById(R.id.LinearLayout1);
		View itemConn;

		row = database.getChargePointConnections(cpId);
		while (row.moveToNext()) {
			try {

				itemConn = inflater.inflate(R.layout.item_connection, null);

				Ui.setValue(itemConn, R.id.heading_level,
						getString(R.string.lb_chargepoint_conn_level,
								row.getPosition() + 1));
				Ui.setValue(itemConn, R.id.value_level,
						row.getString(row.getColumnIndex("conLevelTitle")));

				Ui.setValue(itemConn, R.id.heading_conn_type,
						getString(R.string.lb_chargepoint_conn_type,
								row.getPosition() + 1));
				Ui.setValue(itemConn, R.id.value_conn_type,
						row.getString(row.getColumnIndex("conTypeTitle")));

				layout.addView(itemConn);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		row.close();


		// routing button:

		rel_route = (RelativeLayout) detail.findViewById(R.id.rel_route);
		rel_route.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				direction();
			}
		});


		return detail;
	}


	@Override
	public void onDestroyView() {
		database.close();
		super.onDestroyView();
	}


	private void direction() {

		// Create Google Maps intent from current location to target location

		String directions = "https://maps.google.com/maps?saddr="
				+ appPrefes.getData("lat_main") + ","
				+ appPrefes.getData("lng_main") + "&daddr=" + slat + "," + slng;

		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse(directions));
		intent.setClassName("com.google.android.apps.maps",
				"com.google.android.maps.MapsActivity");

		startActivity(intent);
	}
}
