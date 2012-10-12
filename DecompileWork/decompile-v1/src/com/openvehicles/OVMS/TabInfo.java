// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Date;

// Referenced classes of package com.openvehicles.OVMS:
//            CarData

public class TabInfo extends Activity
{

    public TabInfo()
    {
        lastUpdateTimerHandler = new Handler();
        lastUpdateTimer = new Runnable() {

            public void run()
            {
                updateLastUpdatedView();
                lastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000L);
            }

            final TabInfo this$0;

            
            {
                this$0 = TabInfo.this;
                super();
            }
        }
;
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                int i = 0;
                updateLastUpdatedView();
                ((TextView)findViewById(0x7f060030)).setText(data.VehicleID);
                TextView textview = (TextView)findViewById(0x7f060036);
                String s = getString(0x7f040005);
                Object aobj[] = new Object[1];
                aobj[i] = Integer.valueOf(data.Data_SOC);
                textview.setText(String.format(s, aobj));
                TextView textview1 = (TextView)findViewById(0x7f060038);
                TextView textview2;
                String s1;
                String s2;
                Object aobj1[];
                ImageView imageview;
                ImageView imageview1;
                if(data.Data_ChargeState.equals("charging"))
                {
                    Object aobj2[] = new Object[3];
                    aobj2[i] = data.Data_ChargeMode;
                    aobj2[1] = Integer.valueOf(data.Data_LineVoltage);
                    aobj2[2] = Integer.valueOf(data.Data_ChargeCurrent);
                    textview1.setText(String.format("Charging - %s (%sV %sA)", aobj2));
                } else
                {
                    textview1.setText("");
                }
                textview2 = (TextView)findViewById(0x7f06003a);
                s1 = " km";
                if(data.Data_DistanceUnit != null && !data.Data_DistanceUnit.equals("K"))
                    s1 = " miles";
                s2 = getString(0x7f040003);
                aobj1 = new Object[2];
                aobj1[i] = (new StringBuilder()).append(data.Data_IdealRange).append(s1).toString();
                aobj1[1] = (new StringBuilder()).append(data.Data_EstimatedRange).append(s1).toString();
                textview2.setText(String.format(s2, aobj1));
                ((ImageView)findViewById(0x7f06002c)).setVisibility(4);
                imageview = (ImageView)findViewById(0x7f06002d);
                if(!data.ParanoidMode)
                    i = 4;
                imageview.setVisibility(i);
                ((ImageView)findViewById(0x7f060035)).getLayoutParams().width = (268 * data.Data_SOC) / 100;
                imageview1 = (ImageView)findViewById(0x7f060032);
                imageview1.setImageResource(getResources().getIdentifier(data.VehicleImageDrawable, "drawable", "com.openvehicles.OVMS"));
                imageview1.setOnClickListener(new android.view.View.OnClickListener() {

                    public void onClick(View view)
                    {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(_fld0);
                        Object aobj3[] = new Object[6];
                        String s3;
                        String s4;
                        if(data.Data_CarPoweredON)
                            s3 = "ON";
                        else
                            s3 = "OFF";
                        aobj3[0] = s3;
                        aobj3[1] = data.Data_VIN;
                        aobj3[2] = data.Data_CarModuleGSMSignalLevel;
                        if(data.Data_HandBrakeApplied)
                            s4 = "ENGAGED";
                        else
                            s4 = "DISENGAGED";
                        aobj3[3] = s4;
                        aobj3[4] = data.Data_CarModuleFirmwareVersion;
                        aobj3[5] = data.Data_OVMSServerFirmwareVersion;
                        builder.setMessage(String.format("Power: %s\nVIN: %s\nGSM Signal: %s\nHandbrake: %s\n\nCar Module: %s\nOVMS Server: %s", aobj3)).setTitle("Software Information").setCancelable(false).setPositiveButton("Close", new android.content.DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialoginterface, int j)
                            {
                                dialoginterface.dismiss();
                            }

                            final _cls1 this$2;

                        
                        {
                            this$2 = _cls1.this;
                            super();
                        }
                        }
);
                        softwareInformation = builder.create();
                        softwareInformation.show();
                    }

                    final _cls2 this$1;

                    
                    {
                        this$1 = _cls2.this;
                        super();
                    }
                }
);
            }

            final TabInfo this$0;

            
            {
                this$0 = TabInfo.this;
                super();
            }
        }
;
    }

    private void updateLastUpdatedView()
    {
        if(data != null && data.Data_LastCarUpdate != null)
        {
            TextView textview = (TextView)findViewById(0x7f06002e);
            long l = ((new Date()).getDate() - data.Data_LastCarUpdate.getDate()) / 1000;
            Log.d("OVMS", (new StringBuilder()).append("Last updated: ").append(l).append(" secs ago").toString());
            if(l < 60L)
                textview.setText("live");
            else
            if(l < 3600L)
            {
                int k = (int)Math.ceil(l / 60L);
                Object aobj3[] = new Object[2];
                aobj3[0] = Integer.valueOf(k);
                String s3;
                if(k > 1)
                    s3 = "s";
                else
                    s3 = "";
                aobj3[1] = s3;
                textview.setText(String.format("Updated: %d minute%s ago", aobj3));
            } else
            if(l < 0x15180L)
            {
                int j = (int)Math.ceil(l / 3600L);
                Object aobj2[] = new Object[2];
                aobj2[0] = Integer.valueOf(j);
                String s2;
                if(j > 1)
                    s2 = "s";
                else
                    s2 = "";
                aobj2[1] = s2;
                textview.setText(String.format("Updated: %d hour%s ago", aobj2));
            } else
            if(l < 0xd2f00L)
            {
                int i = (int)Math.ceil(l / 0x15180L);
                Object aobj1[] = new Object[2];
                aobj1[0] = Integer.valueOf(i);
                String s1;
                if(i > 1)
                    s1 = "s";
                else
                    s1 = "";
                aobj1[1] = s1;
                textview.setText(String.format("Updated: %d day%s ago", aobj1));
            } else
            {
                String s = getString(0x7f040004);
                Object aobj[] = new Object[1];
                aobj[0] = data.Data_LastCarUpdate;
                textview.setText(String.format(s, aobj));
            }
        }
    }

    public void RefreshStatus(CarData cardata)
    {
        data = cardata;
        handler.sendEmptyMessage(0);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030008);
    }

    protected void onPause()
    {
        super.onPause();
        if(softwareInformation != null && softwareInformation.isShowing())
            softwareInformation.dismiss();
        lastUpdateTimerHandler.removeCallbacks(lastUpdateTimer);
    }

    protected void onResume()
    {
        super.onResume();
        lastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000L);
    }

    private CarData data;
    private Handler handler;
    private Runnable lastUpdateTimer;
    private Handler lastUpdateTimerHandler;
    private AlertDialog softwareInformation;







/*
    static AlertDialog access$402(TabInfo tabinfo, AlertDialog alertdialog)
    {
        tabinfo.softwareInformation = alertdialog;
        return alertdialog;
    }

*/
}
