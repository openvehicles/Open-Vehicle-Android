// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.*;
import android.util.Log;
import android.widget.TabHost;

// Referenced classes of package com.openvehicles.OVMS:
//            Tab_SubTabNotifications, Tab_SubTabDataUtilizations, Tab_SubTabCarSettings, CarData

public class TabMiscFeatures extends TabActivity
    implements android.widget.TabHost.OnTabChangeListener
{

    public TabMiscFeatures()
    {
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                String s = getLocalActivityManager().getCurrentId().trim();
                notifyTabRefresh(s);
            }

            final TabMiscFeatures this$0;

            
            {
                this$0 = TabMiscFeatures.this;
                super();
            }
        }
;
    }

    private void notifyTabRefresh(String s)
    {
        Log.d("Tab", (new StringBuilder("SubTab refresh: ")).append(s).toString());
        if(s != null && getLocalActivityManager().getActivity(s) != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(!s.equals("tabNotifications"))
            break; /* Loop/switch isn't completed */
        ((Tab_SubTabNotifications)getLocalActivityManager().getActivity(s)).Refresh(data, isLoggedIn);
_L4:
        getTabHost().invalidate();
        if(true) goto _L1; else goto _L3
_L3:
        if(s.equals("tabDataUtilizations"))
            ((Tab_SubTabDataUtilizations)getLocalActivityManager().getActivity(s)).Refresh(data, isLoggedIn);
        else
        if(s.equals("tabCarSettings"))
        {
            ((Tab_SubTabCarSettings)getLocalActivityManager().getActivity(s)).Refresh(data, isLoggedIn);
        } else
        {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage((new StringBuilder("(!) TAB NOT FOUND ERROR: ")).append(s).toString()).setCancelable(false).setPositiveButton("Close", new android.content.DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialoginterface, int i)
                {
                    dialoginterface.dismiss();
                }

                final TabMiscFeatures this$0;

            
            {
                this$0 = TabMiscFeatures.this;
                super();
            }
            }
);
            alertDialog = builder.create();
            alertDialog.show();
            getTabHost().setCurrentTabByTag("tabInfo");
        }
          goto _L4
        if(true) goto _L1; else goto _L5
_L5:
    }

    public void Refresh(CarData cardata, boolean flag)
    {
        data = cardata;
        handler.sendEmptyMessage(0);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030013);
        TabHost tabhost = getTabHost();
        Intent intent = (new Intent()).setClass(this, com/openvehicles/OVMS/Tab_SubTabNotifications);
        android.widget.TabHost.TabSpec tabspec = tabhost.newTabSpec("tabNotifications");
        tabspec.setContent(intent);
        tabspec.setIndicator("", getResources().getDrawable(0x7f020055));
        tabhost.addTab(tabspec);
        Intent intent1 = (new Intent()).setClass(this, com/openvehicles/OVMS/Tab_SubTabDataUtilizations);
        android.widget.TabHost.TabSpec tabspec1 = tabhost.newTabSpec("tabDataUtilizations");
        tabspec1.setContent(intent1);
        tabspec1.setIndicator("", getResources().getDrawable(0x7f020049));
        tabhost.addTab(tabspec1);
        Intent intent2 = (new Intent()).setClass(this, com/openvehicles/OVMS/Tab_SubTabCarSettings);
        android.widget.TabHost.TabSpec tabspec2 = tabhost.newTabSpec("tabCarSettings");
        tabspec2.setContent(intent2);
        tabspec2.setIndicator("", getResources().getDrawable(0x7f02004e));
        tabhost.addTab(tabspec2);
        tabhost.setOnTabChangedListener(this);
    }

    public void onTabChanged(String s)
    {
        handler.sendEmptyMessage(0);
    }

    AlertDialog alertDialog;
    private CarData data;
    private Handler handler;
    private boolean isLoggedIn;

}
