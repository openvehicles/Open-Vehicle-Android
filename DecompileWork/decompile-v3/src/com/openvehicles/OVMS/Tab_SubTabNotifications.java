// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// Referenced classes of package com.openvehicles.OVMS:
//            OVMSNotifications, CarData, OVMSActivity, NotificationData

public class Tab_SubTabNotifications extends ListActivity
{
    private class ItemsAdapter extends ArrayAdapter
    {

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            View view1 = view;
            if(view1 == null)
                view1 = ((LayoutInflater)getSystemService("layout_inflater")).inflate(0x7f030009, null);
            NotificationData notificationdata = items[i];
            if(notificationdata != null)
            {
                TextView textview = (TextView)view1.findViewById(0x7f090016);
                if(i == 0 || notificationdata.Timestamp.getDate() != items[i - 1].Timestamp.getDate())
                {
                    textview.setText(dateFormatter.format(notificationdata.Timestamp));
                    textview.setVisibility(0);
                } else
                {
                    textview.setVisibility(4);
                }
                ((TextView)view1.findViewById(0x7f090018)).setText(notificationdata.Message);
                ((TextView)view1.findViewById(0x7f090017)).setText(timeFormatter.format(notificationdata.Timestamp));
            }
            return view1;
        }

        private SimpleDateFormat dateFormatter;
        private NotificationData items[];
        final Tab_SubTabNotifications this$0;
        private SimpleDateFormat timeFormatter;

        public ItemsAdapter(Context context, int i, NotificationData anotificationdata[])
        {
            this$0 = Tab_SubTabNotifications.this;
            super(context, i, anotificationdata);
            items = anotificationdata;
            dateFormatter = new SimpleDateFormat("MMMMM d");
            timeFormatter = new SimpleDateFormat("h:mm a");
        }
    }


    public Tab_SubTabNotifications()
    {
        lastVehicleID = "";
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                if(!lastVehicleID.equals(data.VehicleID) && notifications.Notifications.size() == 0)
                    Toast.makeText(Tab_SubTabNotifications.this, "No notifications received", 0).show();
                if(!lastVehicleID.equals(data.VehicleID) || notifications.Notifications.size() != cachedData.length) goto _L2; else goto _L1
_L1:
                return;
_L2:
                lastVehicleID = data.VehicleID;
                NotificationData anotificationdata[] = new NotificationData[notifications.Notifications.size()];
                notifications.Notifications.toArray(anotificationdata);
                cachedData = new NotificationData[anotificationdata.length];
                int i = 0;
                do
                {
label0:
                    {
                        if(i < cachedData.length)
                            break label0;
                        adapter = new ItemsAdapter(mContext, 0x7f030009, cachedData);
                        setListAdapter(adapter);
                    }
                    if(true)
                        continue;
                    cachedData[i] = anotificationdata[(-1 + anotificationdata.length) - i];
                    i++;
                } while(true);
                if(true) goto _L1; else goto _L3
_L3:
            }

            final Tab_SubTabNotifications this$0;

            
            {
                this$0 = Tab_SubTabNotifications.this;
                super();
            }
        }
;
    }

    public void Refresh(CarData cardata, boolean flag)
    {
        data = cardata;
        notifications = new OVMSNotifications(this, data.VehicleID);
        handler.sendEmptyMessage(0);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030008);
        mOVMSActivity = (OVMSActivity)getParent().getParent();
        if(mOVMSActivity == null)
            mOVMSActivity = (OVMSActivity)getParent();
        if(mOVMSActivity == null)
            Toast.makeText(this, "Unknown Layout Error", 1).show();
        notifications = new OVMSNotifications(this);
        mContext = this;
    }

    protected void onListItemClick(ListView listview, View view, int i, long l)
    {
        Log.d("OVMS", (new StringBuilder("Displaying notification: #")).append(i).toString());
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getParent());
        builder.setMessage(cachedData[i].Message).setTitle(cachedData[i].Title).setCancelable(false).setPositiveButton("Close", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.dismiss();
            }

            final Tab_SubTabNotifications this$0;

            
            {
                this$0 = Tab_SubTabNotifications.this;
                super();
            }
        }
);
        builder.create().show();
    }

    private ItemsAdapter adapter;
    private NotificationData cachedData[];
    private CarData data;
    private Handler handler;
    private boolean isLoggedIn;
    private String lastVehicleID;
    private Context mContext;
    private OVMSActivity mOVMSActivity;
    private OVMSNotifications notifications;









}
