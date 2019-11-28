package com.openvehicles.OVMS.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.*;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.stream.JsonReader;
import com.luttu.Main;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.ChargePoint;
import com.openvehicles.OVMS.ui.utils.Database;


// This is the OCM API fetch background job

public class GetMapDetails extends AsyncTask<Void, Void, Void> {
	private static final String TAG = "GetMapDetails";

	Context appContext;
	Main main;
	String url;
	Database database;
	afterasytask invoker;
	Gson gson;
	ArrayList<ChargePoint> chargePoints;
	Exception error;


	public interface afterasytask {
		public void after(boolean flBoolean);
	}

	public GetMapDetails(Context context, String url, afterasytask invoker) {
		appContext = context;
		main = new Main(context);
		this.url = url;
		database = new Database(context);
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

		Log.d(TAG, "reading from url=" + url);

		try {
			getdata();
		} catch (IOException e) {
			e.printStackTrace();
			error = e;
		}

		Log.d(TAG, "read " + chargePoints.size() + " chargepoints");

		// update database:

		database.beginWrite();

		int i;
		for (i = 0; !isCancelled() && (i < chargePoints.size()); i++) {
			database.insert_mapdetails(chargePoints.get(i));
		}

		database.endWrite(true);

		Log.d(TAG, "saved " + i + " chargepoints to database, lastUpdate=" + database.get_DateLastStatusUpdate());

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (error != null) {
			Toast.makeText(appContext,
					appContext.getString(R.string.ocm_read_failed, error.getLocalizedMessage()),
					Toast.LENGTH_LONG).show();
			invoker.after(false);
		} else {
			invoker.after(true);
		}
		database.close();
	}


	private void getdata() throws IOException {

		// open URL for JSON parser:

		URL obj_URL = new URL(url);
		HttpURLConnection connection = (HttpURLConnection)obj_URL.openConnection();
		connection.setAllowUserInteraction(false);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setConnectTimeout(30*1000);
		connection.setReadTimeout(120*1000);

		InputStream in = connection.getInputStream();
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

	}


}
