package com.openvehicles.OVMS.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.*;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.stream.JsonReader;
import com.openvehicles.OVMS.utils.AppPrefes;
import com.openvehicles.OVMS.Main;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.ChargePoint;
import com.openvehicles.OVMS.ui.utils.Database;


// This is the OCM API fetch background job

public class GetMapDetails extends AsyncTask<Void, Void, Void> {
	private static final String TAG = "GetMapDetails";

	Context appContext;
	Main main;
	AppPrefes appPrefes;
	Database database;
	LatLng center;
	GetMapDetailsListener invoker;
	Gson gson;
	ArrayList<ChargePoint> chargePoints;
	Exception error;


	public interface GetMapDetailsListener {
		public void getMapDetailsDone(boolean success, LatLng center);
	}

	public GetMapDetails(Context context, LatLng center, GetMapDetailsListener invoker) {
		appContext = context;
		main = new Main(context);
		appPrefes = new AppPrefes(context, "ovms");
		database = new Database(context);
		this.center = center;
		this.invoker = invoker;
		gson = new Gson();
		chargePoints = new ArrayList<ChargePoint>();
		error = null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// main.Diashow();
	}

	@Override
	protected void onCancelled(Void result) {
		Log.d(TAG, "cancelled");
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (isCancelled())
			return null;

		// read from OCM:
		try {
			getdata();
		} catch (IOException e) {
			e.printStackTrace();
			error = e;
		}

		// update database:
		database.beginWrite();
		int i;
		for (i = 0; !isCancelled() && (i < chargePoints.size()); i++) {
			database.insert_mapdetails(chargePoints.get(i));
		}
		database.endWrite(true);
		Log.d(TAG, "saved " + i + " chargepoints to database, lastUpdate="
				+ database.get_DateLastStatusUpdate());

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (error != null) {
			Toast.makeText(appContext,
					appContext.getString(R.string.ocm_read_failed, error.getLocalizedMessage()),
					Toast.LENGTH_LONG).show();
			invoker.getMapDetailsDone(false, center);
		} else {
			invoker.getMapDetailsDone(true, center);
		}
		database.close();
	}


	private void getdata() throws IOException {

		// Make OCM API URL:
		// As OCM does not yet support incremental queries,
		// we're using a cache with key = int(lat/lng)
		// resulting in a tile size of max. 112 x 112 km
		// = diagonal max 159 km
		// The API call will fetch a fixed radius of 160 km
		// covering all adjacent tiles.
		String maxresults = appPrefes.getData("maxresults");
		String lastStatusUpdate = database.get_DateLastStatusUpdate(center);

		String url = "https://api.openchargemap.io/v2/poi/?output=json&verbose=false"
				+ "&latitude=" + center.latitude
				+ "&longitude=" + center.longitude
				+ "&distance=160" // see above
				+ "&distanceunit=KM"
				+ "&maxresults=" + (maxresults.equals("") ? "500" : maxresults
				+ "&modifiedsince=" + lastStatusUpdate.replace(' ', 'T'));

		Log.d(TAG, "getdata: fetching @" + center.latitude + "," + center.longitude
				+ " => url=" + url);

		// open URL for JSON parser:
		InputStream in = main.getURLInputStream(url);
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

		// read charge points array:
		reader.beginArray();
		while (!isCancelled() && reader.hasNext()) {
			try {
				ChargePoint chargePoint = gson.fromJson(reader, ChargePoint.class);
				chargePoints.add(chargePoint);
			} catch (JsonIOException e) {
				e.printStackTrace();
				break;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
		}
		reader.endArray();
		reader.close();

		Log.d(TAG, "getdata: read " + chargePoints.size() + " chargepoints");
	}


}
