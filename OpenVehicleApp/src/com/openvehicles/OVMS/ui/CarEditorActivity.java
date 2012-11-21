package com.openvehicles.OVMS.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;

public class CarEditorActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.careditor);
		
		existingVehicleIDs = (ArrayList<String>)getIntent().getExtras().getSerializable("ExistingVehicleIDs");
		if (getIntent().getExtras().containsKey("Car"))
		{
			// Edit Car
			car = (CarData)getIntent().getExtras().getSerializable("Car");
			originalVehicleID = car.sel_vehicleid;
		} else
		{
			// Create New Car
			car = new CarData();
			originalVehicleID = "";
		}
		
		TextView tv = (TextView) findViewById(R.id.textCarEditorNetPass);
		tv.setText(car.sel_server_password);
		tv = (TextView) findViewById(R.id.textCarEditorUserPass);
		tv.setText(car.sel_module_password);
		tv = (TextView) findViewById(R.id.textCarEditorServerNameOrIP);
		tv.setText(car.sel_server);
		tv = (TextView) findViewById(R.id.textCarEditorVehicleID);
		tv.setText(car.sel_vehicleid);
		
		Button btn = (Button) findViewById(R.id.btnCarEditorCancel);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				closeEditor("CANCEL");
			}
			
		});
		btn = (Button) findViewById(R.id.btnCarEditorDelete);
		if (originalVehicleID.equals(""))
		{
			// hide delete button when creating new vehicle
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					AlertDialog.Builder builder = new AlertDialog.Builder(CarEditorActivity.this);
					builder.setMessage("Delete this car?")
					       .setCancelable(false)
					       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
									closeEditor("DELETE");
					           }
					       })
					       .setNegativeButton("No", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					                dialog.cancel();
					           }
					       });
					builder.create().show();;
				}
				
			});
		}
		btn = (Button) findViewById(R.id.btnCarEditorSave);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				
				TextView tv = (TextView) findViewById(R.id.textCarEditorVehicleID);
				String newVehicleID = tv.getText().toString().trim();
				
				Log.d("OVMS", String.format("newVehicleID %s, originalVehicleID %s, duplicated %s", newVehicleID, originalVehicleID, existingVehicleIDs.contains(newVehicleID)));
				if (!newVehicleID.equals(originalVehicleID) && existingVehicleIDs.contains(newVehicleID))
				{
			    	Toast.makeText(getBaseContext(), String.format("Vehicle ID %s is already registered - Cancelling Save", newVehicleID), 1000).show();
			    	return;
				}

				closeEditor("SAVE");
			}
			
		});
		
        String[] availableColors = {
"car_roadster_arcticwhite",
"car_roadster_brilliantyellow",
"car_roadster_electricblue",
"car_roadster_fushionred",
"car_roadster_glacierblue",
"car_roadster_jetblack",
"car_roadster_lightninggreen",
"car_roadster_obsidianblack",
"car_roadster_racinggreen",
"car_roadster_radiantred",
"car_roadster_sterlingsilver",
"car_roadster_thundergray",
"car_roadster_twilightblue",
"car_roadster_veryorange",
"car_ampera_black",
"car_ampera_crystalred",
"car_ampera_cybergray",
"car_ampera_lithiumwhite",
"car_ampera_powerblue",
"car_ampera_silvertopas",
"car_ampera_sovereignsilver",
"car_ampera_summitwhite",
"car_holdenvolt_black",
"car_holdenvolt_crystalclaret",
"car_holdenvolt_silvernitrate",
"car_holdenvolt_urbanfresh",
"car_holdenvolt_whitediamond",
"car_twizy_diamondblackwithivygreen",
"car_twizy_snowwhiteandflameorange",
"car_twizy_snowwhiteandurbanblue",
"car_twizy_snowwhitewithblack"
 };
        //get redId's
        // build HashMap
        HashMap<String, Object> item;
        availableCarColors = new ArrayList();
        int currentCarDrawable = 0;
        for (int i = 0; i < availableColors.length; i++) {
        	item = new HashMap<String, Object>();

        	if (availableColors[i].equals(car.sel_vehicle_image))
        		currentCarDrawable = i;
        	
        	item.put("Name", availableColors[i]);
        	item.put("Icon", getResources().getIdentifier(availableColors[i], "drawable", "com.openvehicles.OVMS"));
        	availableCarColors.add(item);
        }
        
        Spinner spin = (Spinner) findViewById(R.id.spinnerCarEditorColor);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this,
        		availableCarColors, R.layout.tabcareditorcars_listitem, new String[] { "Icon" }, new int[] { R.id.imgCarEditorCarImage });
        spin.setAdapter(adapter);
        spin.setSelection(currentCarDrawable);
	}
	
	private ArrayList availableCarColors;
	
	@SuppressWarnings("unchecked")
	private void closeEditor(String actionCode)
	{
		TextView tv = (TextView) findViewById(R.id.textCarEditorVehicleID);
		car.sel_vehicleid = tv.getText().toString().trim();
		tv = (TextView) findViewById(R.id.textCarEditorNetPass);
		car.sel_server_password = tv.getText().toString().trim();
		tv = (TextView) findViewById(R.id.textCarEditorUserPass);
		car.sel_module_password = tv.getText().toString().trim();
		tv = (TextView) findViewById(R.id.textCarEditorServerNameOrIP);
		car.sel_server = tv.getText().toString().trim();

		Spinner spin = (Spinner) findViewById(R.id.spinnerCarEditorColor);
		car.sel_vehicle_image = ((HashMap<String, Object>)availableCarColors.get(spin.getSelectedItemPosition())).get("Name").toString();

		Log.d("Editor", "Closing editor: " + actionCode);
		
		Intent resultIntent = new Intent();
		resultIntent.putExtra("Car", car);
		resultIntent.putExtra("ActionCode", actionCode);
		resultIntent.putExtra("OriginalVehicleID", originalVehicleID);
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
	}
	
	private ArrayList<String> existingVehicleIDs;
	private CarData car;
	private String originalVehicleID;

	class CustomSpinnerAdapter extends SimpleAdapter {

	    LayoutInflater mInflater;
	    private List<? extends Map<String, ?>> dataRecieved;

	    public CustomSpinnerAdapter(Context context, List<? extends Map<String, ?>> data,
	            int resource, String[] from, int[] to) {
	        super(context, data, resource, from, to);
	        dataRecieved =data;
	        mInflater=LayoutInflater.from(context);
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        if (convertView == null) {
	            convertView = mInflater.inflate(R.layout.tabcareditorcars_listitem,
	                    null);
	        }
	        @SuppressWarnings("unchecked")
			HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);
        	Log.d("OVMS", "Loading drawable resId " + data.get("Icon").toString());

	        ImageView iv = ((ImageView) convertView.findViewById(R.id.imgCarEditorCarImage));
        	iv.setBackgroundResource((Integer)data.get("Icon"));
	        return convertView;
	    }
	}
}
