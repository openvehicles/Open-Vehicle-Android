package com.openvehicles.OVMS.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.openvehicles.OVMS.luttu.AppPrefes;
import com.openvehicles.OVMS.luttu.Main;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.ui.utils.Database;

public class ConnectionList {
	private static final String TAG = "ConnectionList";
	Context context;
	ConnectionsListener listener;
	Main main;
	ArrayList<HashMap<String, String>> hList = new ArrayList<HashMap<String, String>>();
	ArrayList<String> al = new ArrayList<String>();
	ArrayList<String> al_check = new ArrayList<String>();
	ArrayList<String> al_Id = new ArrayList<String>();
	Database database;
	AppPrefes appPrefes;
	ConnectionListLoader connectionListLoader;

	public ConnectionList(Context context, ConnectionsListener listener, Boolean mayFetch) {
		appPrefes = new AppPrefes(context, "ovms");
		this.context = context;
		this.listener = listener;
		main = new Main(context);
		database = new Database(context);
		if (database.get_ConnectionTypes_Main().getCount() == 0 && mayFetch) {
			connectionListLoader = new ConnectionListLoader();
			connectionListLoader.execute();
		} else {
			connectionListLoader = null;
			getlist();
		}
	}

	private class ConnectionListLoader extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				String url = "https://api.openchargemap.io/v2/referencedata/";
				JSONObject jsonObject = main.getJSONObject(url);
				JSONArray connectionTypes = jsonObject.getJSONArray("ConnectionTypes");
				for (int i = 0; i < connectionTypes.length(); i++) {
					JSONObject detail = connectionTypes.getJSONObject(i);
					HashMap<String, String> hmap = new HashMap<String, String>();
					hmap.put("ID", detail.getString("ID"));
					hmap.put("Title", detail.getString("Title"));
					hmap.put("check", "false");
					al.add(detail.getString("Title"));
					al_Id.add(detail.getString("ID"));
					al_check.add("false");
					hList.add(hmap);
				}
			} catch (Exception e) {
				Log.e(TAG, "ConnectionListLoader:" + e.getMessage());
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			database.beginWrite();
			for (int i = 0; i < al.size(); i++) {
				database.addConnectionTypes_Main("" + i, al_Id.get(i), al.get(i));
			}
			database.endWrite(true);
			database.close();
		};
	}

	public interface ConnectionsListener {
		public void onConnectionChanged(String conId, String conName);
	}

	public void sublist() {
		getlist();
		AppCompatDialog dialog = new AppCompatDialog(context);
		dialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_list);
		ArrayAdapter<String> adpt = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_checked, al);
		final ListView listView = (ListView) dialog
				.findViewById(android.R.id.list);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setAdapter(adpt);
		for (int i = 0; i < al_check.size(); i++) {
			if (al_check.get(i).equals("true")) {
				listView.setItemChecked(i, true);
			}
		}
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String Title = "", Tid = "";
				String selVehicleLabel = appPrefes.getData("sel_vehicle_label");

				int len = listView.getCount();
				SparseBooleanArray checked = listView.getCheckedItemPositions();

				database.beginWrite();
				database.resetConnectionTypesDetail(selVehicleLabel);

				for (int i = 0; i < len; i++) {
					if (checked.get(i)) {

						database.updateConnectionTypesDetail(
								hList.get(i).get("ID"), "true",
								selVehicleLabel);

						if (Tid.equals("")) {
							Tid = al_Id.get(i);
							Title = hList.get(i).get("Title");
						} else {
							Tid = Tid + "," + al_Id.get(i);
							Title = Title + "," + hList.get(i).get("Title");
						}
					}
				}

				database.endWrite(true);
				database.close();

				appPrefes.SaveData("Id", Tid);

				listener.onConnectionChanged(Tid, Title);
			}
		});
		dialog.show();
	}

	// TODO: Remove "@SuppressLint("Range")" and handle this properly
	@SuppressLint("Range")
	private void getlist() {
		al.clear();
		al_Id.clear();
		al_check.clear();
		hList.clear();

		Log.d("ConnectionList", "getlist: sel_vehicle_label="
				+ appPrefes.getData("sel_vehicle_label"));

		Cursor cursor = database.get_ConnectionTypesdetails(
				appPrefes.getData("sel_vehicle_label"));

		if (cursor.getCount() != 0) {
			while (cursor.moveToNext()) {
				al.add(cursor.getString(cursor.getColumnIndex("title")));
				al_check.add(cursor.getString(cursor
						.getColumnIndex("chec")));
				al_Id.add(cursor.getString(cursor.getColumnIndex("tId")));
				HashMap<String, String> hmap = new HashMap<String, String>();
				hmap.put("ID",
						cursor.getString(cursor.getColumnIndex("Id")));
				hmap.put("Title", cursor.getString(cursor
						.getColumnIndex("title")));
				hmap.put("check",
						cursor.getString(cursor.getColumnIndex("chec")));
				hList.add(hmap);
			}

		} else {
			cursor.close();

			cursor = database.get_ConnectionTypes_Main();
			while (cursor.moveToNext()) {
				al.add(cursor.getString(cursor.getColumnIndex("title")));
				al_Id.add(cursor.getString(cursor.getColumnIndex("tId")));
			}

			database.beginWrite();
			for (int i = 0; i < al.size(); i++) {
				database.addConnectionTypesDetail(al_Id.get(i), al.get(i),
						"false", appPrefes.getData("sel_vehicle_label"));
			}
			database.endWrite(true);

			al.clear();
			al_Id.clear();
			al_check.clear();
			hList.clear();
			Cursor cursor1 = database.get_ConnectionTypesdetails(appPrefes
					.getData("sel_vehicle_label"));
			while (cursor1.moveToNext()) {
				al.add(cursor1.getString(cursor1.getColumnIndex("title")));
				al_check.add(cursor1.getString(cursor1
						.getColumnIndex("chec")));
				al_Id.add(cursor1.getString(cursor1.getColumnIndex("tId")));
				HashMap<String, String> hmap = new HashMap<String, String>();
				hmap.put("ID",
						cursor1.getString(cursor1.getColumnIndex("Id")));
				hmap.put("Title", cursor1.getString(cursor1
						.getColumnIndex("title")));
				hmap.put("check",
						cursor1.getString(cursor1.getColumnIndex("chec")));
				hList.add(hmap);
			}
			cursor1.close();
		}

		cursor.close();
		database.close();
	}
}
