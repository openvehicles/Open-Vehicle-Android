package com.openvehicles.OVMS.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.luttu.Main;
import com.openvehicles.OVMS.ui.utils.Database;

public class GetMapDetails extends AsyncTask<Void, Void, Void> {
	Main main;
	ArrayList<String> al_lat = new ArrayList<String>();
	ArrayList<String> al_lng = new ArrayList<String>();
	ArrayList<String> al_title = new ArrayList<String>();
	ArrayList<String> al_optr = new ArrayList<String>();
	ArrayList<String> al_usage = new ArrayList<String>();
	ArrayList<String> al_status = new ArrayList<String>();
	ArrayList<String> al_address = new ArrayList<String>();
	ArrayList<String> al_level1 = new ArrayList<String>();
	ArrayList<String> al_level2 = new ArrayList<String>();
	ArrayList<String> al_connction1 = new ArrayList<String>();
	ArrayList<String> al_connction_id = new ArrayList<String>();
	ArrayList<String> al_numberofpoints = new ArrayList<String>();
	String url;
	Database database;
	afterasytask name;

	public interface afterasytask {
		public void after(boolean flBoolean);
	}

	public GetMapDetails(Context context, String url, afterasytask name) {
		// TODO Auto-generated constructor stub
		main = new Main(context);
		this.url = url;
		database = new Database(context);
		this.name = name;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		System.out.println("pre");
		// main.Diashow();
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		System.out.println("doing" + al_lat.size());
		getdata();
		for (int i = 0; i < al_lat.size(); i++) {
			database.insert_mapdetails(al_lat.get(i), al_lng.get(i),
					al_title.get(i), al_optr.get(i), al_status.get(i),
					al_usage.get(i), al_address.get(i), al_level1.get(i),
					al_level2.get(i), al_connction_id.get(i),
					al_connction1.get(i), al_numberofpoints.get(i));
		}
		System.out.println("do" + al_lat.size());
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		// main.Diacancel();
		System.out.println("post");
		name.after(true);
	}

	private void getdata() {
		// TODO Auto-generated method stub
		JSONArray arr = main.getJSONArray(url);
		for (int i = 0; i < arr.length(); i++) {
			try {
				JSONObject AddressInfo = arr.getJSONObject(i);
				JSONObject jAddressInfo = AddressInfo
						.getJSONObject("AddressInfo");
				String Latitude = jAddressInfo.getString("Latitude");
				String Longitude = jAddressInfo.getString("Longitude");
				String Title = jAddressInfo.getString("Title");
				String AddressLine1 = jAddressInfo.getString("AddressLine1");
				al_lat.add(Latitude);
				al_lng.add(Longitude);
				al_title.add(Title);
				al_address.add(AddressLine1);
				try {
					try {
						JSONObject OperatorInfo = AddressInfo
								.getJSONObject("OperatorInfo");
						al_optr.add(OperatorInfo.getString("Title"));
						// System.out.println("jj OperatorInfo "+OperatorInfo.getString("Title"));
					} catch (Exception e) {
						// TODO: handle exception
						al_optr.add("(Unknown Operator)");
					}
					try {
						JSONObject StatusType = AddressInfo
								.getJSONObject("StatusType");
						al_status.add(StatusType.getString("Title"));
						// System.out.println("jj StatusType "+StatusType.getString("Title"));
					} catch (Exception e) {
						// TODO: handle exception
						al_status.add(" ");
					}
					try {
						JSONObject UsageType = AddressInfo
								.getJSONObject("UsageType");
						al_usage.add(UsageType.getString("Title"));
					} catch (Exception e) {
						// TODO: handle exception
						al_usage.add(" ");
					}
					try {
						al_numberofpoints.add(AddressInfo.getString("NumberOfPoints"));
//						System.out.println("NumberOfPoints"+AddressInfo.getString("NumberOfPoints"));
					} catch (Exception e) {
						// TODO: handle exception
						al_numberofpoints.add(" ");
					}
					try {
						JSONArray jConnections = AddressInfo
								.getJSONArray("Connections");
						JSONObject jcon = jConnections.getJSONObject(0);
						JSONObject Level = jcon.getJSONObject("Level");
						String jTitle = Level.getString("Title");
						al_level1.add(jTitle);
						al_level2.add(jTitle);
						JSONObject ConnectionType = jcon
								.getJSONObject("ConnectionType");
						String cTitle = ConnectionType.getString("Title");
						String cId = ConnectionType.getString("ID");
						al_connction1.add(cTitle);
						al_connction_id.add(cId);
					} catch (Exception e) {
						// TODO: handle exception
						al_level1.add(" ");
						al_level2.add(" ");
						al_connction1.add("");
						al_connction_id.add("0");
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				// System.out.println("lat  " + Latitude + " lon" + Longitude);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
