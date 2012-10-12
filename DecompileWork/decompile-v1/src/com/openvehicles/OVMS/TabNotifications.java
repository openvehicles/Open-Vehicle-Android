// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

// Referenced classes of package com.openvehicles.OVMS:
//            OVMSNotifications, NotificationData

public class TabNotifications extends ListActivity
{
    private class ItemsAdapter extends ArrayAdapter
    {

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            View view1 = view;
            if(view1 == null)
                view1 = ((LayoutInflater)getSystemService("layout_inflater")).inflate(0x7f03000b, null);
            NotificationData notificationdata = items[i];
            if(notificationdata != null)
            {
                ((TextView)view1.findViewById(0x7f06003d)).setText(notificationdata.Title);
                ((TextView)view1.findViewById(0x7f06003f)).setText(notificationdata.Message);
                SimpleDateFormat simpledateformat = new SimpleDateFormat("MMM d, k:mm");
                ((TextView)view1.findViewById(0x7f06003e)).setText(simpledateformat.format(notificationdata.Timestamp));
            }
            return view1;
        }

        private NotificationData items[];
        final TabNotifications this$0;

        public ItemsAdapter(Context context, int i, NotificationData anotificationdata[])
        {
            this$0 = TabNotifications.this;
            super(context, i, anotificationdata);
            items = anotificationdata;
        }
    }


    public TabNotifications()
    {
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                NotificationData anotificationdata[] = new NotificationData[notifications.Notifications.size()];
                notifications.Notifications.toArray(anotificationdata);
                cachedData = new NotificationData[anotificationdata.length];
                for(int i = 0; i < cachedData.length; i++)
                    cachedData[i] = anotificationdata[(-1 + anotificationdata.length) - i];

                adapter = new ItemsAdapter(mContext, 0x7f03000b, cachedData);
                setListAdapter(adapter);
            }

            final TabNotifications this$0;

            
            {
                this$0 = TabNotifications.this;
                super();
            }
        }
;
    }

    public void Refresh()
    {
        notifications = new OVMSNotifications(this);
        handler.sendEmptyMessage(0);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03000a);
        notifications = new OVMSNotifications(this);
        mContext = this;
    }

    protected void onListItemClick(ListView listview, View view, int i, long l)
    {
        Log.d("OVMS", (new StringBuilder()).append("Displaying notification: #").append(i).toString());
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(cachedData[i].Message).setTitle(cachedData[i].Title).setCancelable(false).setPositiveButton("Close", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.dismiss();
            }

            final TabNotifications this$0;

            
            {
                this$0 = TabNotifications.this;
                super();
            }
        }
);
        builder.create().show();
    }

    private ItemsAdapter adapter;
    private NotificationData cachedData[];
    private Handler handler;
    private Context mContext;
    private OVMSNotifications notifications;




/*
    static NotificationData[] access$102(TabNotifications tabnotifications, NotificationData anotificationdata[])
    {
        tabnotifications.cachedData = anotificationdata;
        return anotificationdata;
    }

*/



/*
    static ItemsAdapter access$202(TabNotifications tabnotifications, ItemsAdapter itemsadapter)
    {
        tabnotifications.adapter = itemsadapter;
        return itemsadapter;
    }

*/

}
