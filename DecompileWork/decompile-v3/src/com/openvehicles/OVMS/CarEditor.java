// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.io.File;
import java.util.*;

// Referenced classes of package com.openvehicles.OVMS:
//            CarData

public class CarEditor extends Activity
{
    class CustomSpinnerAdapter extends SimpleAdapter
    {

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            if(view == null)
                view = mInflater.inflate(0x7f03000b, null);
            HashMap hashmap = (HashMap)getItem(i);
            ImageView imageview = (ImageView)view.findViewById(0x7f090038);
            StringBuilder stringbuilder = new StringBuilder(String.valueOf(getCacheDir().getAbsolutePath()));
            Object aobj[] = new Object[1];
            aobj[0] = hashmap.get("Name").toString();
            android.graphics.Bitmap bitmap = BitmapFactory.decodeFile(stringbuilder.append(String.format("/%s.png", aobj)).toString());
            if(bitmap != null)
            {
                imageview.setImageBitmap(bitmap);
            } else
            {
                imageview.setImageBitmap(null);
                imageview.setBackgroundResource(((Integer)hashmap.get("Icon")).intValue());
            }
            return view;
        }

        private List dataRecieved;
        LayoutInflater mInflater;
        final CarEditor this$0;

        public CustomSpinnerAdapter(Context context, List list, int i, String as[], int ai[])
        {
            this$0 = CarEditor.this;
            super(context, list, i, as, ai);
            dataRecieved = list;
            mInflater = LayoutInflater.from(context);
        }
    }


    public CarEditor()
    {
    }

    private void closeEditor(String s)
    {
        TextView textview = (TextView)findViewById(0x7f090000);
        car.VehicleID = textview.getText().toString().trim();
        TextView textview1 = (TextView)findViewById(0x7f090003);
        car.RegPass = textview1.getText().toString().trim();
        TextView textview2 = (TextView)findViewById(0x7f090002);
        car.NetPass = textview2.getText().toString().trim();
        TextView textview3 = (TextView)findViewById(0x7f090001);
        car.ServerNameOrIP = textview3.getText().toString().trim();
        Spinner spinner = (Spinner)findViewById(0x7f090004);
        car.VehicleImageDrawable = ((HashMap)availableCarColors.get(spinner.getSelectedItemPosition())).get("Name").toString();
        Log.d("Editor", (new StringBuilder("Closing editor: ")).append(s).toString());
        Intent intent = new Intent();
        intent.putExtra("Car", car);
        intent.putExtra("ActionCode", s);
        intent.putExtra("OriginalVehicleID", originalVehicleID);
        setResult(-1, intent);
        finish();
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030000);
        existingVehicleIDs = (ArrayList)getIntent().getExtras().getSerializable("ExistingVehicleIDs");
        Button button;
        String as[];
        int i;
        int j;
        if(getIntent().getExtras().containsKey("Car"))
        {
            car = (CarData)getIntent().getExtras().getSerializable("Car");
            originalVehicleID = car.VehicleID;
        } else
        {
            car = new CarData();
            originalVehicleID = "";
        }
        ((TextView)findViewById(0x7f090003)).setText(car.RegPass);
        ((TextView)findViewById(0x7f090002)).setText(car.NetPass);
        ((TextView)findViewById(0x7f090001)).setText(car.ServerNameOrIP);
        ((TextView)findViewById(0x7f090000)).setText(car.VehicleID);
        ((Button)findViewById(0x7f090005)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                closeEditor("CANCEL");
            }

            final CarEditor this$0;

            
            {
                this$0 = CarEditor.this;
                super();
            }
        }
);
        button = (Button)findViewById(0x7f090006);
        if(originalVehicleID.equals(""))
            button.setVisibility(4);
        else
            button.setOnClickListener(new _cls2());
        ((Button)findViewById(0x7f090007)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                String s = ((TextView)findViewById(0x7f090000)).getText().toString().trim();
                Object aobj1[] = new Object[3];
                aobj1[0] = s;
                aobj1[1] = originalVehicleID;
                aobj1[2] = Boolean.valueOf(existingVehicleIDs.contains(s));
                Log.d("OVMS", String.format("newVehicleID %s, originalVehicleID %s, duplicated %s", aobj1));
                if(!s.equals(originalVehicleID) && existingVehicleIDs.contains(s))
                {
                    Context context = getBaseContext();
                    Object aobj2[] = new Object[1];
                    aobj2[0] = s;
                    Toast.makeText(context, String.format("Vehicle ID %s is already registered - Cancelling Save", aobj2), 1000).show();
                } else
                {
                    closeEditor("SAVE");
                }
            }

            final CarEditor this$0;

            
            {
                this$0 = CarEditor.this;
                super();
            }
        }
);
        as = new String[23];
        as[0] = "car_roadster_arcticwhite";
        as[1] = "car_roadster_brilliantyellow";
        as[2] = "car_roadster_electricblue";
        as[3] = "car_roadster_fushionred";
        as[4] = "car_roadster_glacierblue";
        as[5] = "car_roadster_jetblack";
        as[6] = "car_roadster_lightninggreen";
        as[7] = "car_roadster_obsidianblack";
        as[8] = "car_roadster_racinggreen";
        as[9] = "car_roadster_radiantred";
        as[10] = "car_roadster_sterlingsilver";
        as[11] = "car_roadster_thundergray";
        as[12] = "car_roadster_twilightblue";
        as[13] = "car_roadster_veryorange";
        as[14] = "car_models_anzabrown";
        as[15] = "car_models_catalinawhite";
        as[16] = "car_models_montereyblue";
        as[17] = "car_models_sansimeonsilver";
        as[18] = "car_models_sequolagreen";
        as[19] = "car_models_shastapearlwhite";
        as[20] = "car_models_sierrablack";
        as[21] = "car_models_signaturered";
        as[22] = "car_models_tiburongrey";
        availableCarColors = new ArrayList();
        i = 0;
        j = 0;
        do
        {
            class _cls2
                implements android.view.View.OnClickListener
            {

                public void onClick(View view)
                {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CarEditor.this);
                    builder.setMessage("Delete this car?").setCancelable(false).setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialoginterface, int k)
                        {
                            closeEditor("DELETE");
                        }

                        final _cls2 this$1;

                    
                    {
                        this$1 = _cls2.this;
                        super();
                    }
                    }
).setNegativeButton("No", new android.content.DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialoginterface, int k)
                        {
                            dialoginterface.cancel();
                        }

                        final _cls2 this$1;

                    
                    {
                        this$1 = _cls2.this;
                        super();
                    }
                    }
);
                    builder.create().show();
                }

                final CarEditor this$0;


            
            {
                this$0 = CarEditor.this;
                super();
            }
            }

            if(j >= as.length)
            {
                Spinner spinner = (Spinner)findViewById(0x7f090004);
                ArrayList arraylist = availableCarColors;
                String as1[] = new String[1];
                as1[0] = "Icon";
                int ai[] = new int[1];
                ai[0] = 0x7f090038;
                spinner.setAdapter(new CustomSpinnerAdapter(this, arraylist, 0x7f03000b, as1, ai));
                spinner.setSelection(i);
                return;
            }
            HashMap hashmap = new HashMap();
            if(as[j].equals(car.VehicleImageDrawable))
                i = j;
            hashmap.put("Name", as[j]);
            Resources resources = getResources();
            Object aobj[] = new Object[1];
            aobj[0] = as[j];
            hashmap.put("Icon", Integer.valueOf(resources.getIdentifier(String.format("%s96x44", aobj), "drawable", "com.openvehicles.OVMS")));
            availableCarColors.add(hashmap);
            j++;
        } while(true);
    }

    private ArrayList availableCarColors;
    private CarData car;
    private ArrayList existingVehicleIDs;
    private String originalVehicleID;



}
