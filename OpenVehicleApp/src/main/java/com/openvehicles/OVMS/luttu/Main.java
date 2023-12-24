package com.openvehicles.OVMS.luttu;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.openvehicles.OVMS.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressLint("NewApi")
public class Main {
	private static final String TAG = "Main";
	Context context;
	PackageInfo packageInfo;
	Bundle metaData;
	Dialog dialog;
	JSONObject json;
	JSONArray jsonarray;
	Animation rotation;
	ImageView img;

	public Main(Context context) {
		this.context = context;
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			metaData = ai.metaData;
			packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dialog = new Dialog(context, android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.loadingicon);
		img = (ImageView) dialog.findViewById(R.id.progressBar1);
		img.setBackgroundResource(R.drawable.progress_img);
		rotation = AnimationUtils.loadAnimation(context,
				R.anim.clockwise_rotation);
		rotation.setRepeatCount(Animation.INFINITE);
		dialog.setCanceledOnTouchOutside(false);
		strictmaode();
	}

	public String getUserAgent() {
		return "OVMS/" + packageInfo.versionName + "-" + packageInfo.versionCode
				+ " Android/" + Build.VERSION.RELEASE;
	}

	public HttpURLConnection getURLConnection(String url) throws IOException {
		// create connection:
		URL obj_URL = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) obj_URL.openConnection();

		// set standard props:
		connection.setRequestProperty("User-Agent", getUserAgent());
		connection.setAllowUserInteraction(false);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setConnectTimeout(30 * 1000);
		connection.setReadTimeout(120 * 1000);

		// auto specialize by URL:
		if (url.contains("api.openchargemap.io")) {
			String apiKey = metaData.getString("org.openchargemap.api.v2.API_KEY");
			Log.d(TAG, "getURLConnection: using API key for openchargemap: " + apiKey);
			if (apiKey != null)
				connection.setRequestProperty("X-API-Key", apiKey);
			else
				Log.e(TAG, "getURLConnection: missing API key for openchargemap");
		}

		return connection;
	}

	public InputStream getURLInputStream(String url) throws IOException {
		HttpURLConnection connection = getURLConnection(url);
		return connection.getInputStream();
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	private void strictmaode() {
		int SDK_INT = android.os.Build.VERSION.SDK_INT;
		if (SDK_INT > 8) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	public void Diashow() {
		dialog.show();
		img.startAnimation(rotation);
	}

	public void Diacancel() {
		dialog.dismiss();
	}

	public JSONObject getJSONObject(final String url) {
		try {
			InputStream in = getURLInputStream(url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder data = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				data.append(line);
			}
			try {
				json = new JSONObject(data.toString());
			} catch (JSONException e) {
				Log.e(TAG, "getJSONObject: url='" + url + "' → " + e.getMessage());
				e.printStackTrace();
			}
		} catch (Exception e) {
			Log.e(TAG, "getJSONObject: url='" + url + "' → " + e.getMessage());
			e.printStackTrace();
		}
		// System.out.println(json);
		return json;
	}

	public JSONArray getJSONArray(final String url) {
		try {
			InputStream in = getURLInputStream(url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder data = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				data.append(line);
			}
			try {
				jsonarray = new JSONArray(data.toString());
			} catch (JSONException e) {
				Log.e(TAG, "getJSONArray: url='" + url + "' → " + e.getMessage());
				e.printStackTrace();
			}
		} catch (IOException e) {
			Log.e(TAG, "getJSONArray: url='" + url + "' → " + e.getMessage());
			e.printStackTrace();
		}
        Log.d(TAG, "getJSONArray: got " + (jsonarray != null ? jsonarray.length() : -1) + " JSON elems from url=" + url);
        return jsonarray;
	}

	public void addFragment1(android.app.Fragment fragment,
			boolean addToBackStack, int transition, String name, int id) {
		android.app.FragmentTransaction ft = ((Activity) context)
				.getFragmentManager().beginTransaction();
		ft.replace(id, fragment);
		ft.setTransition(transition);
		if (addToBackStack)
			ft.addToBackStack(name);
		ft.commit();
	}
}
