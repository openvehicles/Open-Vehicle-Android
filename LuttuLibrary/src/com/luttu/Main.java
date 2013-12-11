package com.luttu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.luttulibrary.R;

@SuppressLint("NewApi")
public class Main {
	Context context;
	Dialog dialog;
	JSONObject json;
	JSONArray jsonarray;
	Animation rotation;
	ImageView img;

	public Main(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
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

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	private void strictmaode() {
		// TODO Auto-generated method stub
		int SDK_INT = android.os.Build.VERSION.SDK_INT;
		if (SDK_INT > 8) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	public void Diashow() {
		// TODO Auto-generated method stub
		dialog.show();
		img.startAnimation(rotation);
	}

	public void Diacancel() {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}

	public JSONObject getJSONObject(final String url) {
		// TODO Auto-generated method stub
		try {
			URL obj_URL = new URL(url);
			InputStream in;
			in = obj_URL.openStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String line,line1 = "";
			while ((line = reader.readLine()) != null) {
				line1=line1+line;
			}
			try {
				json = new JSONObject(line1);
			} catch (JSONException e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println(e);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(json);
		return json;
	}

	public JSONArray getJSONArray(final String url) {
		// TODO Auto-generated method stub
		try {
			URL obj_URL = new URL(url);
			InputStream in;
			in = obj_URL.openStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String line,line1 = "";
			while ((line = reader.readLine()) != null) {
				line1=line1+line;
			}
			try {
				jsonarray = new JSONArray(line1);
			} catch (JSONException e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println(e);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(jsonarray);
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
