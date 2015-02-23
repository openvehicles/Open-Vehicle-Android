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

		slat = row.getString(row.getColumnIndex("Latitude"));
		slng = row.getString(row.getColumnIndex("Longitude"));

		final String Title, UsageType, StatusType, AddressLine1, NumberOfPoints,
				OperatorInfo, UsageCost, AccessComments, RelatedURL, GeneralComments;

		Title = row.getString(row.getColumnIndex("Title"));
		OperatorInfo = row.getString(row.getColumnIndex("OperatorInfo"));
		StatusType = row.getString(row.getColumnIndex("StatusType"));
		UsageType = row.getString(row.getColumnIndex("UsageType"));
		UsageCost = row.getString(row.getColumnIndex("UsageCost"));
		AccessComments = row.getString(row.getColumnIndex("AccessComments"));
		NumberOfPoints = row.getString(row.getColumnIndex("NumberOfPoints"));
		AddressLine1 = row.getString(row.getColumnIndex("AddressLine1"));
		RelatedURL = row.getString(row.getColumnIndex("RelatedURL"));
		GeneralComments = row.getString(row.getColumnIndex("GeneralComments"));

		row.close();


		// create and fill view:

		View detail = inflater.inflate(R.layout.detail, null);

		Ui.setValue(detail, R.id.value_Title, "" + Title);
		Ui.setValue(detail, R.id.value_OperatorInfo, "" + OperatorInfo);
		Ui.setValue(detail, R.id.value_StatusType, "" + StatusType);
		Ui.setValue(detail, R.id.value_UsageType, "" + UsageType);
		Ui.setValue(detail, R.id.value_UsageCost, "" + UsageCost);
		Ui.setValue(detail, R.id.value_AccessComments, "" + AccessComments);
		Ui.setValue(detail, R.id.value_NumberOfPoints, "" + NumberOfPoints);
		Ui.setValue(detail, R.id.value_AddressLine1, "" + AddressLine1);
		Ui.setValue(detail, R.id.value_RelatedURL, "" + RelatedURL);
		Ui.setValue(detail, R.id.value_GeneralComments, "" + GeneralComments);


		// add connections:

		LinearLayout layout = (LinearLayout) detail.findViewById(R.id.DetailContentGroup);
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
		Ui.setOnClick(detail, R.id.btnGetRoute, new OnClickListener() {
			@Override
			public void onClick(View v) {
				direction();
			}
		});

		// OCM button:
		Ui.setOnClick(detail, R.id.btnViewInOCM, new OnClickListener() {
			@Override
			public void onClick(View v) {
				openURL("http://openchargemap.org/site/poi/details/" + cpId);
			}
		});

		// click on RelatedURL => open browser:
		Ui.setOnClick(detail, R.id.value_RelatedURL, new OnClickListener() {
			@Override
			public void onClick(View view) {
				openURL(RelatedURL);
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

	private void openURL(String url) {
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse(url));
		startActivity(intent);
	}

}
