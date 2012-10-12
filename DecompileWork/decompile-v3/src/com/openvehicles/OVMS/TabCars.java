// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.io.File;
import java.util.ArrayList;

// Referenced classes of package com.openvehicles.OVMS:
//            OVMSActivity, CarEditor, CarData

public class TabCars extends ListActivity
{
    private class ItemsAdapter extends ArrayAdapter
    {

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            View view1 = view;
            if(view1 == null)
                view1 = ((LayoutInflater)getSystemService("layout_inflater")).inflate(0x7f03000d, null);
            CarData cardata = items[i];
            if(cardata != null)
            {
                ImageView imageview = (ImageView)view1.findViewById(0x7f09003a);
                StringBuilder stringbuilder = new StringBuilder(String.valueOf(getCacheDir().getAbsolutePath()));
                Object aobj[] = new Object[1];
                aobj[0] = cardata.VehicleImageDrawable;
                android.graphics.Bitmap bitmap = BitmapFactory.decodeFile(stringbuilder.append(String.format("/%s.png", aobj)).toString());
                TextView textview;
                Object aobj2[];
                ImageButton imagebutton;
                if(bitmap != null)
                {
                    imageview.setImageBitmap(bitmap);
                } else
                {
                    Resources resources = getResources();
                    Object aobj1[] = new Object[1];
                    aobj1[0] = cardata.VehicleImageDrawable;
                    imageview.setImageResource(resources.getIdentifier(String.format("%s96x44", aobj1), "drawable", "com.openvehicles.OVMS"));
                }
                ((TextView)view1.findViewById(0x7f09003b)).setText(cardata.VehicleID);
                textview = (TextView)view1.findViewById(0x7f09003c);
                aobj2 = new Object[1];
                aobj2[0] = cardata.ServerNameOrIP;
                textview.setText(String.format("@%s", aobj2));
                imagebutton = (ImageButton)view1.findViewById(0x7f09003d);
                if(cardata.VehicleID.equals("DEMO"))
                {
                    imagebutton.setVisibility(4);
                } else
                {
                    imagebutton.setClickable(true);
                    imagebutton.setTag(cardata);
                    imagebutton.setOnClickListener(new android.view.View.OnClickListener() {

                        public void onClick(View view2)
                        {
                            CarData cardata1 = (CarData)view2.getTag();
                            Log.d("OVMS", (new StringBuilder("Editing car: ")).append(cardata1.VehicleID).toString());
                            editCar(cardata1);
                        }

                        final ItemsAdapter this$1;

                
                {
                    this$1 = ItemsAdapter.this;
                    super();
                }
                    }
);
                }
            }
            return view1;
        }

        private CarData items[];
        final TabCars this$0;


        public ItemsAdapter(Context context, int i, CarData acardata[])
        {
            this$0 = TabCars.this;
            super(context, i, acardata);
            items = acardata;
        }
    }


    public TabCars()
    {
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                adapter = new ItemsAdapter(mContext, 0x7f03000d, carsList);
                setListAdapter(adapter);
            }

            final TabCars this$0;

            
            {
                this$0 = TabCars.this;
                super();
            }
        }
;
    }

    private void carClicked(CarData cardata)
    {
        ((OVMSActivity)getParent()).ChangeCar(cardata);
    }

    private void editCar(CarData cardata)
    {
        ArrayList arraylist = new ArrayList();
        int i = 0;
        do
        {
            if(i >= _allSavedCars.size())
            {
                Object aobj[] = new Object[1];
                aobj[0] = Integer.valueOf(arraylist.size());
                Log.d("OVMS", String.format("Starting car editor (%s in existing cars list)", aobj));
                Intent intent = new Intent(mContext, com/openvehicles/OVMS/CarEditor);
                intent.putExtra("ExistingVehicleIDs", arraylist);
                if(cardata != null)
                {
                    arraylist.remove(cardata.VehicleID);
                    intent.putExtra("Car", cardata);
                }
                startActivityForResult(intent, 0);
                return;
            }
            arraylist.add(((CarData)_allSavedCars.get(i)).VehicleID);
            i++;
        } while(true);
    }

    public void LoadCars(ArrayList arraylist)
    {
        CarData acardata[] = new CarData[arraylist.size()];
        arraylist.toArray(acardata);
        _allSavedCars = arraylist;
        carsList = acardata;
        handler.sendEmptyMessage(0);
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        CarData cardata;
        String s;
        String s1;
        super.onActivityResult(i, j, intent);
        if(intent == null)
        {
            Log.d("OVMS", "Editor cancelled.");
        } else
        {
label0:
            {
                cardata = (CarData)intent.getExtras().getSerializable("Car");
                s = intent.getExtras().getString("ActionCode");
                s1 = intent.getExtras().getString("OriginalVehicleID");
                Object aobj[] = new Object[2];
                aobj[0] = s;
                aobj[1] = s1;
                Log.d("OVMS", String.format("Editor closed with result action: %s %s", aobj));
                if(!s.equals("CANCEL"))
                    break label0;
                Toast.makeText(getBaseContext(), "Cancelled", 1000).show();
            }
        }
_L8:
        return;
        if(!s.equals("SAVE")) goto _L2; else goto _L1
_L1:
        if(cardata.VehicleID.equals(cardata.VehicleID)) goto _L4; else goto _L3
_L3:
        int i1 = 0;
_L9:
        if(i1 < _allSavedCars.size()) goto _L5; else goto _L4
_L4:
        if(s1.length() <= 0) goto _L7; else goto _L6
_L6:
        int l = 0;
_L10:
        CarData acardata[];
        if(l < _allSavedCars.size())
        {
label1:
            {
                if(!((CarData)_allSavedCars.get(l)).VehicleID.equals(s1))
                    break label1;
                Object aobj5[] = new Object[1];
                aobj5[0] = s1;
                Log.d("OVMS", String.format("Saved: %s", aobj5));
                _allSavedCars.set(l, cardata);
                Context context2 = getBaseContext();
                Object aobj6[] = new Object[1];
                aobj6[0] = cardata.VehicleID;
                Toast.makeText(context2, String.format("%s saved", aobj6), 1000).show();
            }
        }
_L11:
        ((OVMSActivity)getParent()).saveCars();
        acardata = new CarData[_allSavedCars.size()];
        _allSavedCars.toArray(acardata);
        carsList = acardata;
        adapter = new ItemsAdapter(mContext, 0x7f03000d, carsList);
        setListAdapter(adapter);
          goto _L8
_L5:
label2:
        {
            if(!((CarData)_allSavedCars.get(i1)).VehicleID.equals(cardata.VehicleID))
                break label2;
            Log.d("OVMS", String.format("Vehicle ID duplicated. Aborting save.", new Object[0]));
            Toast.makeText(getBaseContext(), "Duplicated vehicle ID - Changes not saved", 1000).show();
        }
          goto _L8
        i1++;
          goto _L9
        l++;
          goto _L10
_L7:
        Object aobj3[] = new Object[1];
        aobj3[0] = cardata.VehicleID;
        Log.d("OVMS", String.format("Added: %s", aobj3));
        _allSavedCars.add(cardata);
        Context context1 = getBaseContext();
        Object aobj4[] = new Object[1];
        aobj4[0] = cardata.VehicleID;
        Toast.makeText(context1, String.format("%s added", aobj4), 1000).show();
          goto _L11
_L2:
        if(!s.equals("DELETE")) goto _L11; else goto _L12
_L12:
        int k = 0;
_L13:
        if(k < _allSavedCars.size())
        {
label3:
            {
                if(!((CarData)_allSavedCars.get(k)).VehicleID.equals(s1))
                    break label3;
                Object aobj1[] = new Object[1];
                aobj1[0] = s1;
                Log.d("OVMS", String.format("Deleted: %s", aobj1));
                _allSavedCars.remove(k);
                Context context = getBaseContext();
                Object aobj2[] = new Object[1];
                aobj2[0] = cardata.VehicleID;
                Toast.makeText(context, String.format("%s deleted", aobj2), 1000).show();
            }
        }
          goto _L11
        k++;
          goto _L13
    }

    public boolean onContextItemSelected(MenuItem menuitem)
    {
        boolean flag = true;
        android.widget.AdapterView.AdapterContextMenuInfo _tmp = (android.widget.AdapterView.AdapterContextMenuInfo)menuitem.getMenuInfo();
        switch(menuitem.getItemId())
        {
        default:
            flag = super.onContextItemSelected(menuitem);
            // fall through

        case 2131296264: 
        case 2131296265: 
            return flag;
        }
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03000c);
        mContext = this;
        ((ImageButton)findViewById(0x7f090039)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                editCar(null);
            }

            final TabCars this$0;

            
            {
                this$0 = TabCars.this;
                super();
            }
        }
);
    }

    public void onCreateContextMenu(ContextMenu contextmenu, View view, android.view.ContextMenu.ContextMenuInfo contextmenuinfo)
    {
        super.onCreateContextMenu(contextmenu, view, contextmenuinfo);
        getMenuInflater().inflate(0x7f030001, contextmenu);
    }

    protected void onListItemClick(ListView listview, View view, int i, long l)
    {
        Log.d("OVMS", (new StringBuilder("Changing car to: ")).append(carsList[i].VehicleID).toString());
        carClicked(carsList[i]);
    }

    private ArrayList _allSavedCars;
    private ItemsAdapter adapter;
    private CarData carsList[];
    private Handler handler;
    private Context mContext;





}
