// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.Activity;
import android.os.*;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Date;

// Referenced classes of package com.openvehicles.OVMS:
//            CarData

public class TabCar extends Activity
{

    public TabCar()
    {
        lastUpdateTimerHandler = new Handler();
        lastUpdateTimer = new Runnable() {

            public void run()
            {
                updateLastUpdatedView();
                lastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000L);
            }

            final TabCar this$0;

            
            {
                this$0 = TabCar.this;
                super();
            }
        }
;
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                int i = 0;
                updateLastUpdatedView();
                ((TextView)findViewById(0x7f060013)).setText(data.VehicleID);
                TextView textview = (TextView)findViewById(0x7f06001f);
                int j;
                TextView textview1;
                int k;
                TextView textview2;
                int l;
                TextView textview3;
                int i1;
                TextView textview4;
                int j1;
                TextView textview5;
                Object aobj[];
                TextView textview6;
                Object aobj1[];
                TextView textview7;
                Object aobj2[];
                TextView textview8;
                Object aobj3[];
                TextView textview9;
                Object aobj4[];
                ImageView imageview;
                int k1;
                ImageView imageview1;
                int l1;
                ImageView imageview2;
                int i2;
                ImageView imageview3;
                int j2;
                ImageView imageview4;
                int k2;
                ImageView imageview5;
                int l2;
                ImageView imageview6;
                if(data.Data_LeftDoorOpen)
                    j = 0;
                else
                    j = 4;
                textview.setVisibility(j);
                textview1 = (TextView)findViewById(0x7f060020);
                if(data.Data_RightDoorOpen)
                    k = 0;
                else
                    k = 4;
                textview1.setVisibility(k);
                textview2 = (TextView)findViewById(0x7f060024);
                if(data.Data_ChargePortOpen)
                    l = 0;
                else
                    l = 4;
                textview2.setVisibility(l);
                textview3 = (TextView)findViewById(0x7f060021);
                if(data.Data_BonnetOpen)
                    i1 = 0;
                else
                    i1 = 4;
                textview3.setVisibility(i1);
                textview4 = (TextView)findViewById(0x7f060025);
                if(data.Data_TrunkOpen)
                    j1 = 0;
                else
                    j1 = 4;
                textview4.setVisibility(j1);
                textview5 = (TextView)findViewById(0x7f060023);
                aobj = new Object[4];
                aobj[i] = Integer.valueOf((int)data.Data_TemperaturePEM);
                aobj[1] = Integer.valueOf((int)data.Data_TemperatureMotor);
                aobj[2] = Integer.valueOf((int)data.Data_TemperatureBattery);
                aobj[3] = Integer.valueOf((int)data.Data_Speed);
                textview5.setText(String.format("PEM: %d\272C\nMotor: %d\272C\nBatt: %d\272C\nSpeed: %dkph", aobj));
                textview6 = (TextView)findViewById(0x7f06001b);
                aobj1 = new Object[2];
                aobj1[i] = Double.valueOf(data.Data_FLWheelPressure);
                aobj1[1] = Double.valueOf(data.Data_FLWheelTemperature);
                textview6.setText(String.format("%.1fpsi\n%.0f\272C", aobj1));
                textview7 = (TextView)findViewById(0x7f06001c);
                aobj2 = new Object[2];
                aobj2[i] = Double.valueOf(data.Data_FRWheelPressure);
                aobj2[1] = Double.valueOf(data.Data_FRWheelTemperature);
                textview7.setText(String.format("%.1fpsi\n%.0f\272C", aobj2));
                textview8 = (TextView)findViewById(0x7f06001d);
                aobj3 = new Object[2];
                aobj3[i] = Double.valueOf(data.Data_RLWheelPressure);
                aobj3[1] = Double.valueOf(data.Data_RLWheelTemperature);
                textview8.setText(String.format("%.1fpsi\n%.0f\272C", aobj3));
                textview9 = (TextView)findViewById(0x7f06001e);
                aobj4 = new Object[2];
                aobj4[i] = Double.valueOf(data.Data_RRWheelPressure);
                aobj4[1] = Double.valueOf(data.Data_RRWheelTemperature);
                textview9.setText(String.format("%.1fpsi\n%.0f\272C", aobj4));
                imageview = (ImageView)findViewById(0x7f060016);
                if(data.Data_ChargePortOpen)
                    k1 = 0;
                else
                    k1 = 4;
                imageview.setVisibility(k1);
                imageview1 = (ImageView)findViewById(0x7f060019);
                if(data.Data_BonnetOpen)
                    l1 = 0;
                else
                    l1 = 4;
                imageview1.setVisibility(l1);
                imageview2 = (ImageView)findViewById(0x7f06001a);
                if(data.Data_LeftDoorOpen)
                    i2 = 0;
                else
                    i2 = 4;
                imageview2.setVisibility(i2);
                imageview3 = (ImageView)findViewById(0x7f060018);
                if(data.Data_RightDoorOpen)
                    j2 = 0;
                else
                    j2 = 4;
                imageview3.setVisibility(j2);
                imageview4 = (ImageView)findViewById(0x7f060017);
                if(data.Data_TrunkOpen)
                    k2 = 0;
                else
                    k2 = 4;
                imageview4.setVisibility(k2);
                imageview5 = (ImageView)findViewById(0x7f060022);
                if(data.Data_CarLocked)
                    l2 = 0x7f020032;
                else
                    l2 = 0x7f020033;
                imageview5.setImageResource(l2);
                imageview6 = (ImageView)findViewById(0x7f060010);
                if(!data.ParanoidMode)
                    i = 4;
                imageview6.setVisibility(i);
                ((ImageView)findViewById(0x7f06000f)).setVisibility(4);
            }

            final TabCar this$0;

            
            {
                this$0 = TabCar.this;
                super();
            }
        }
;
    }

    private void updateLastUpdatedView()
    {
        if(data != null && data.Data_LastCarUpdate != null)
        {
            TextView textview = (TextView)findViewById(0x7f060011);
            long l = ((new Date()).getDate() - data.Data_LastCarUpdate.getDate()) / 1000;
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
        Log.d("Tab", "TabCar Refresh");
        data = cardata;
        handler.sendEmptyMessage(0);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030004);
    }

    protected void onPause()
    {
        super.onPause();
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




}
