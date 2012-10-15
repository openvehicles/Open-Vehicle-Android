package com.openvehicles.OVMS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CarEditor extends Activity {
	private ArrayList availableCarColors;
	private CarData car;
	private ArrayList<String> existingVehicleIDs;
	private String originalVehicleID;

	private void closeEditor(String paramString) {
		TextView localTextView1 = (TextView) findViewById(2131296256);
		this.car.VehicleID = localTextView1.getText().toString().trim();
		TextView localTextView2 = (TextView) findViewById(2131296259);
		this.car.RegPass = localTextView2.getText().toString().trim();
		TextView localTextView3 = (TextView) findViewById(2131296258);
		this.car.NetPass = localTextView3.getText().toString().trim();
		TextView localTextView4 = (TextView) findViewById(2131296257);
		this.car.ServerNameOrIP = localTextView4.getText().toString().trim();
		Spinner localSpinner = (Spinner) findViewById(2131296260);
		this.car.VehicleImageDrawable = ((HashMap) this.availableCarColors
				.get(localSpinner.getSelectedItemPosition())).get("Name")
				.toString();
		Log.d("Editor", "Closing editor: " + paramString);
		Intent localIntent = new Intent();
		localIntent.putExtra("Car", this.car);
		localIntent.putExtra("ActionCode", paramString);
		localIntent.putExtra("OriginalVehicleID", this.originalVehicleID);
		setResult(-1, localIntent);
		finish();
	}

	public void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(2130903040);
		this.existingVehicleIDs = ((ArrayList)getIntent().getExtras().getSerializable("ExistingVehicleIDs"));
		Button localButton;
		String[] arrayOfString1;
		int i;
		if (getIntent().getExtras().containsKey("Car"))
		{
			this.car = ((CarData)getIntent().getExtras().getSerializable("Car"));
			this.originalVehicleID = this.car.VehicleID;
			((TextView)findViewById(2131296259)).setText(this.car.RegPass);
			((TextView)findViewById(2131296258)).setText(this.car.NetPass);
			((TextView)findViewById(2131296257)).setText(this.car.ServerNameOrIP);
			((TextView)findViewById(2131296256)).setText(this.car.VehicleID);
			((Button)findViewById(2131296261)).setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View paramAnonymousView)
				{
					CarEditor.this.closeEditor("CANCEL");
				}
			});
			localButton = (Button)findViewById(2131296262);
			if (!this.originalVehicleID.equals(""))
				break label471;
			localButton.setVisibility(4);
			((Button)findViewById(2131296263)).setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View paramAnonymousView)
				{
					String str = ((TextView)CarEditor.this.findViewById(2131296256)).getText().toString().trim();
					Object[] arrayOfObject1 = new Object[3];
					arrayOfObject1[0] = str;
					arrayOfObject1[1] = CarEditor.this.originalVehicleID;
					arrayOfObject1[2] = Boolean.valueOf(CarEditor.this.existingVehicleIDs.contains(str));
					Log.d("OVMS", String.format("newVehicleID %s, originalVehicleID %s, duplicated %s", arrayOfObject1));
					if ((!str.equals(CarEditor.this.originalVehicleID)) && (CarEditor.this.existingVehicleIDs.contains(str)))
					{
						Context localContext = CarEditor.this.getBaseContext();
						Object[] arrayOfObject2 = new Object[1];
						arrayOfObject2[0] = str;
						Toast.makeText(localContext, String.format("Vehicle ID %s is already registered - Cancelling Save", arrayOfObject2), 1000).show();
					}
					else
					{
						CarEditor.this.closeEditor("SAVE");
					}
				}
			});
			arrayOfString1 = new String[23];
			arrayOfString1[0] = "car_roadster_arcticwhite";
			arrayOfString1[1] = "car_roadster_brilliantyellow";
			arrayOfString1[2] = "car_roadster_electricblue";
			arrayOfString1[3] = "car_roadster_fushionred";
			arrayOfString1[4] = "car_roadster_glacierblue";
			arrayOfString1[5] = "car_roadster_jetblack";
			arrayOfString1[6] = "car_roadster_lightninggreen";
			arrayOfString1[7] = "car_roadster_obsidianblack";
			arrayOfString1[8] = "car_roadster_racinggreen";
			arrayOfString1[9] = "car_roadster_radiantred";
			arrayOfString1[10] = "car_roadster_sterlingsilver";
			arrayOfString1[11] = "car_roadster_thundergray";
			arrayOfString1[12] = "car_roadster_twilightblue";
			arrayOfString1[13] = "car_roadster_veryorange";
			arrayOfString1[14] = "car_models_anzabrown";
			arrayOfString1[15] = "car_models_catalinawhite";
			arrayOfString1[16] = "car_models_montereyblue";
			arrayOfString1[17] = "car_models_sansimeonsilver";
			arrayOfString1[18] = "car_models_sequolagreen";
			arrayOfString1[19] = "car_models_shastapearlwhite";
			arrayOfString1[20] = "car_models_sierrablack";
			arrayOfString1[21] = "car_models_signaturered";
			arrayOfString1[22] = "car_models_tiburongrey";
			this.availableCarColors = new ArrayList();
			i = 0;
		}
		for (int j = 0; ; j++)
		{
			if (j >= arrayOfString1.length)
			{
				Spinner localSpinner = (Spinner)findViewById(2131296260);
				ArrayList localArrayList = this.availableCarColors;
				String[] arrayOfString2 = new String[1];
				arrayOfString2[0] = "Icon";
				int[] arrayOfInt = new int[1];
				arrayOfInt[0] = 2131296312;
				localSpinner.setAdapter(new CustomSpinnerAdapter(this, localArrayList, 2130903051, arrayOfString2, arrayOfInt));
				localSpinner.setSelection(i);
				return;
				this.car = new CarData();
				this.originalVehicleID = "";
				break;
				label471: localButton.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View paramAnonymousView)
					{
						AlertDialog.Builder localBuilder = new AlertDialog.Builder(CarEditor.this);
						localBuilder.setMessage("Delete this car?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
							{
								CarEditor.this.closeEditor("DELETE");
							}
						}).setNegativeButton("No", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
							{
								paramAnonymous2DialogInterface.cancel();
							}
						});
						localBuilder.create().show();
					}
				});
				break label198;
			}
			HashMap localHashMap = new HashMap();
			if (arrayOfString1[j].equals(this.car.VehicleImageDrawable))
				i = j;
			localHashMap.put("Name", arrayOfString1[j]);
			Resources localResources = getResources();
			Object[] arrayOfObject = new Object[1];
			arrayOfObject[0] = arrayOfString1[j];
			localHashMap.put("Icon", Integer.valueOf(localResources.getIdentifier(String.format("%s96x44", arrayOfObject), "drawable", "com.openvehicles.OVMS")));
			this.availableCarColors.add(localHashMap);
		}
	}

	class CustomSpinnerAdapter extends SimpleAdapter {
		private List<? extends Map<String, ?>> dataRecieved;
		LayoutInflater mInflater;

		public CustomSpinnerAdapter(List<? extends Map<String, ?>> paramInt,
				int paramArrayOfString, String[] paramArrayOfInt, int[] arg5) {
			super(paramArrayOfString, paramArrayOfInt, arrayOfString,
					arrayOfInt);
			this.dataRecieved = paramArrayOfString;
			this.mInflater = LayoutInflater.from(paramInt);
		}

		public View getView(int paramInt, View paramView,
				ViewGroup paramViewGroup) {
			if (paramView == null)
				paramView = this.mInflater.inflate(2130903051, null);
			HashMap localHashMap = (HashMap) getItem(paramInt);
			ImageView localImageView = (ImageView) paramView
					.findViewById(2131296312);
			StringBuilder localStringBuilder = new StringBuilder(
					String.valueOf(CarEditor.this.getCacheDir()
							.getAbsolutePath()));
			Object[] arrayOfObject = new Object[1];
			arrayOfObject[0] = localHashMap.get("Name").toString();
			Bitmap localBitmap = BitmapFactory.decodeFile(String.format(
					"/%s.png", arrayOfObject));
			if (localBitmap != null)
				localImageView.setImageBitmap(localBitmap);
			else {
				localImageView.setImageBitmap(null);
				localImageView.setBackgroundResource(((Integer) localHashMap
						.get("Icon")).intValue());
			}
			return paramView;
		}
	}
}