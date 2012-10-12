// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.android.maps.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// Referenced classes of package com.openvehicles.OVMS:
//            CarData, Utilities, OVMSActivity, ServerCommands

public class TabInfo_xlarge extends MapActivity
{

    public TabInfo_xlarge()
    {
        carMarkerAnimationTimerHandler = new Handler();
        carMarkerAnimationFrame = 0;
        lastUpdateTimerHandler = new Handler();
        lastUpdateTimer = new Runnable() {

            public void run()
            {
                updateLastUpdatedView();
                lastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000L);
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
;
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                updateLastUpdatedView();
                updateInfoUI();
                updateCarLayoutUI();
                updateMapUI();
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
;
        animateCarMarker = new Runnable() {

            public void run()
            {
                String s = "-";
                if(data.Data_LastCarUpdate != null)
                    s = (new SimpleDateFormat("MMM d, K:mm:ss a")).format(data.Data_LastCarUpdate);
                GeoPoint geopoint = Utilities.GetCarGeopoint(data);
                int i = (geopoint.getLatitudeE6() - lastCarGeoPoint.getLatitudeE6()) / 40;
                int j = (geopoint.getLongitudeE6() - lastCarGeoPoint.getLongitudeE6()) / 40;
                GeoPoint geopoint1;
                String s1;
                Object aobj[];
                Utilities.CarMarker carmarker;
                TabInfo_xlarge tabinfo_xlarge;
                int k;
                if(carMarkerAnimationFrame == 39)
                    geopoint1 = geopoint;
                else
                    geopoint1 = new GeoPoint(lastCarGeoPoint.getLatitudeE6() + i * carMarkerAnimationFrame, lastCarGeoPoint.getLongitudeE6() + j * carMarkerAnimationFrame);
                s1 = data.VehicleID;
                aobj = new Object[1];
                aobj[0] = s;
                carmarker = new Utilities.CarMarker(geopoint1, s1, String.format("Last reported: %s", aobj), (int)data.Data_Direction);
                carMarkers.setOverlay(0, carmarker);
                mapView.invalidate();
                tabinfo_xlarge = TabInfo_xlarge.this;
                k = 1 + tabinfo_xlarge.carMarkerAnimationFrame;
                tabinfo_xlarge.carMarkerAnimationFrame = k;
                if(k < 40)
                    carMarkerAnimationTimerHandler.postDelayed(animateCarMarker, 50L);
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
;
        orientationChangedHandler = new Handler() {

            public void handleMessage(Message message)
            {
                Log.d("Tab", "Relayout TabInfo_xlarge activity");
                CurrentScreenOrientation = getResources().getConfiguration().orientation;
                TableLayout tablelayout = (TableLayout)findViewById(0x7f090059);
                LinearLayout linearlayout;
                byte byte0;
                if(CurrentScreenOrientation == 2)
                    tablelayout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-2, -2));
                else
                    tablelayout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(240, -2));
                tablelayout.invalidate();
                linearlayout = (LinearLayout)findViewById(0x7f09007c);
                if(CurrentScreenOrientation == 1)
                    byte0 = 8;
                else
                    byte0 = 0;
                linearlayout.setVisibility(byte0);
                linearlayout.invalidate();
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
;
    }

    private void downloadLayout()
    {
        downloadProgress = new ProgressDialog(this);
        downloadProgress.setMessage("Downloading Hi-Res Graphics");
        downloadProgress.setIndeterminate(true);
        downloadProgress.setMax(100);
        downloadProgress.setCancelable(true);
        downloadProgress.setProgressStyle(1);
        downloadProgress.show();
        downloadProgress.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {

            public void onDismiss(DialogInterface dialoginterface)
            {
                StringBuilder stringbuilder = new StringBuilder(String.valueOf(getCacheDir().getAbsolutePath()));
                Object aobj[] = new Object[1];
                aobj[0] = data.VehicleImageDrawable;
                Bitmap bitmap = BitmapFactory.decodeFile(stringbuilder.append(String.format("/%s.png", aobj)).toString());
                if(bitmap != null)
                {
                    ((ImageView)findViewById(0x7f09005b)).setImageBitmap(bitmap);
                    StringBuilder stringbuilder1 = new StringBuilder(String.valueOf(getCacheDir().getAbsolutePath()));
                    Object aobj1[] = new Object[1];
                    aobj1[0] = data.VehicleImageDrawable;
                    Bitmap bitmap1 = BitmapFactory.decodeFile(stringbuilder1.append(String.format("/ol_%s.png", aobj1)).toString());
                    if(bitmap1 != null)
                        ((ImageView)findViewById(0x7f090067)).setImageBitmap(bitmap1);
                    Toast.makeText(TabInfo_xlarge.this, "Graphics Downloaded", 0).show();
                } else
                {
                    Toast.makeText(TabInfo_xlarge.this, "Download Failed", 0).show();
                }
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
);
        downloadTask = new ServerCommands.CarLayoutDownloader(downloadProgress);
        ServerCommands.CarLayoutDownloader carlayoutdownloader = downloadTask;
        String as[] = new String[2];
        as[0] = data.VehicleImageDrawable;
        as[1] = getCacheDir().getAbsolutePath();
        carlayoutdownloader.execute(as);
    }

    private void initCarLayoutUI()
    {
        ((TextView)findViewById(0x7f090057)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                String s = "-";
                if(data != null && data.Data_LastCarUpdate != null)
                    s = (new SimpleDateFormat("MMM d, K:mm:ss a")).format(data.Data_LastCarUpdate);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TabInfo_xlarge.this);
                builder.setMessage((new StringBuilder("Last update: ")).append(s).toString()).setCancelable(true).setPositiveButton("Close", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        dialoginterface.dismiss();
                    }

                    final _cls10 this$1;

                    
                    {
                        this$1 = _cls10.this;
                        super();
                    }
                }
).setTitle(data.VehicleID);
                lastUpdatedDialog = builder.create();
                lastUpdatedDialog.show();
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
);
        ((FrameLayout)findViewById(0x7f09007a)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(!isFinishing())
                {
                    TabInfo_xlarge tabinfo_xlarge = TabInfo_xlarge.this;
                    OVMSActivity ovmsactivity = (OVMSActivity)getParent();
                    boolean flag;
                    if(data.Data_CarLocked)
                        flag = false;
                    else
                        flag = true;
                    ServerCommands.LockUnlockCar(tabinfo_xlarge, ovmsactivity, null, flag);
                }
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
);
        ((FrameLayout)findViewById(0x7f09007b)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(!isFinishing())
                {
                    TabInfo_xlarge tabinfo_xlarge = TabInfo_xlarge.this;
                    OVMSActivity ovmsactivity = (OVMSActivity)getParent();
                    boolean flag;
                    if(data.Data_ValetON)
                        flag = false;
                    else
                        flag = true;
                    ServerCommands.ValetModeOnOff(tabinfo_xlarge, ovmsactivity, null, flag);
                }
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
);
        ((LinearLayout)findViewById(0x7f09007c)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(!isFinishing() && !data.Data_CoolingPumpON_DoorState3)
                    ServerCommands.WakeUp(TabInfo_xlarge.this, (OVMSActivity)getParent(), null, true);
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
);
    }

    private void initInfoUI()
    {
        ((TextView)findViewById(0x7f090057)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(data.Data_LastCarUpdate != null)
                {
                    String s = (new SimpleDateFormat("MMM d, K:mm:ss a")).format(data.Data_LastCarUpdate);
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TabInfo_xlarge.this);
                    builder.setMessage((new StringBuilder("Last update: ")).append(s).toString()).setCancelable(true).setPositiveButton("Close", new android.content.DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                            dialoginterface.dismiss();
                        }

                        final _cls5 this$1;

                    
                    {
                        this$1 = _cls5.this;
                        super();
                    }
                    }
).setTitle(data.VehicleID);
                    lastUpdatedDialog = builder.create();
                    lastUpdatedDialog.show();
                }
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
);
        ((TextView)findViewById(0x7f090054)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(data.Data_ParkedTime != null)
                {
                    String s = (new SimpleDateFormat("MMM d, K:mm:ss a")).format(data.Data_ParkedTime);
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TabInfo_xlarge.this);
                    builder.setMessage((new StringBuilder("Parked since: ")).append(s).toString()).setCancelable(true).setPositiveButton("Close", new android.content.DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                            dialoginterface.dismiss();
                        }

                        final _cls6 this$1;

                    
                    {
                        this$1 = _cls6.this;
                        super();
                    }
                    }
).setTitle(data.VehicleID);
                    lastUpdatedDialog = builder.create();
                    lastUpdatedDialog.show();
                }
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
);
        ((TextView)findViewById(0x7f09005f)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                ServerCommands.SetChargeMode(TabInfo_xlarge.this, (OVMSActivity)getParent(), null);
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
);
        ((TextView)findViewById(0x7f090064)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                ServerCommands.SetChargeCurrent(TabInfo_xlarge.this, (OVMSActivity)getParent(), null, data.Data_ChargeAmpsLimit);
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
);
        ((SeekBar)findViewById(0x7f090060)).setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekbar, int i, boolean flag)
            {
                seekbar.setProgress(i);
            }

            public void onStartTrackingTouch(SeekBar seekbar)
            {
            }

            public void onStopTrackingTouch(final SeekBar seekBar)
            {
                int i = 0;
                if(seekBar.getProgress() < 25)
                {
                    seekBar.setProgress(0);
                    if(data.Data_Charging)
                        Toast.makeText(TabInfo_xlarge.this, "Already charging...", 0).show();
                    else
                        ServerCommands.StartCharge(TabInfo_xlarge.this, (OVMSActivity)getParent(), null).setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {

                            public void onCancel(DialogInterface dialoginterface)
                            {
                                seekBar.setProgress(seekBar.getMax());
                            }

                            final _cls9 this$1;
                            private final SeekBar val$seekBar;

                    
                    {
                        this$1 = _cls9.this;
                        seekBar = seekbar;
                        super();
                    }
                        }
);
                } else
                if(seekBar.getProgress() > -25 + seekBar.getMax())
                {
                    seekBar.setProgress(seekBar.getMax());
                    if(!data.Data_Charging)
                        Toast.makeText(TabInfo_xlarge.this, "Already stopped...", 0).show();
                    else
                        ServerCommands.StopCharge(TabInfo_xlarge.this, (OVMSActivity)getParent(), null).setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {

                            public void onCancel(DialogInterface dialoginterface)
                            {
                                seekBar.setProgress(0);
                            }

                            final _cls9 this$1;
                            private final SeekBar val$seekBar;

                    
                    {
                        this$1 = _cls9.this;
                        seekBar = seekbar;
                        super();
                    }
                        }
);
                } else
                {
                    if(!data.Data_Charging)
                        i = 100;
                    seekBar.setProgress(i);
                }
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
);
    }

    private void initMapUI()
    {
        mapView = (MapView)findViewById(0x7f090081);
        mc = mapView.getController();
        mapView.setBuiltInZoomControls(true);
        DirectionalMarker = BitmapFactory.decodeResource(getResources(), 0x7f020008);
        mapOverlays = mapView.getOverlays();
        carMarkers = new Utilities.CarMarkerOverlay(getResources().getDrawable(0x7f02001e), 20, this, DirectionalMarker, 1);
        mapOverlays.add(0, carMarkers);
    }

    private void updateCarLayoutUI()
    {
        ImageView imageview8;
        int j6;
        TextView textview = (TextView)findViewById(0x7f090074);
        int i;
        TextView textview1;
        int j;
        TextView textview2;
        int k;
        TextView textview3;
        int l;
        TextView textview4;
        int i1;
        TextView textview5;
        String s;
        int j1;
        int k1;
        int l1;
        int i2;
        int j2;
        int k2;
        int l2;
        int i3;
        int j3;
        int k3;
        int l3;
        int i4;
        int j4;
        int k4;
        int l4;
        int i5;
        int j5;
        int k5;
        int l5;
        int i6;
        if(data.Data_LeftDoorOpen)
            i = 0;
        else
            i = 4;
        textview.setVisibility(i);
        textview1 = (TextView)findViewById(0x7f090075);
        if(data.Data_RightDoorOpen)
            j = 0;
        else
            j = 4;
        textview1.setVisibility(j);
        textview2 = (TextView)findViewById(0x7f090076);
        if(data.Data_ChargePortOpen)
            k = 0;
        else
            k = 4;
        textview2.setVisibility(k);
        textview3 = (TextView)findViewById(0x7f090078);
        if(data.Data_BonnetOpen)
            l = 0;
        else
            l = 4;
        textview3.setVisibility(l);
        textview4 = (TextView)findViewById(0x7f090077);
        if(data.Data_TrunkOpen)
            i1 = 0;
        else
            i1 = 4;
        textview4.setVisibility(i1);
        textview5 = (TextView)findViewById(0x7f090079);
        if(data.Data_Speed > 0.0D)
        {
            Object aobj10[] = new Object[2];
            aobj10[0] = Integer.valueOf((int)data.Data_Speed);
            TextView textview6;
            Object aobj[];
            TextView textview7;
            Object aobj1[];
            TextView textview8;
            Object aobj2[];
            TextView textview9;
            Object aobj3[];
            TextView textview10;
            Object aobj4[];
            TextView textview11;
            Object aobj5[];
            TextView textview12;
            Object aobj6[];
            TextView textview13;
            Object aobj7[];
            ImageView imageview;
            StringBuilder stringbuilder;
            Object aobj8[];
            Bitmap bitmap;
            ImageView imageview1;
            ImageView imageview2;
            ImageView imageview3;
            ImageView imageview4;
            ImageView imageview5;
            ImageView imageview6;
            ImageView imageview7;
            String s1;
            if(data.Data_DistanceUnit.equals("K"))
                s1 = "kph";
            else
                s1 = "mph";
            aobj10[1] = s1;
            s = String.format("%d %s", aobj10);
        } else
        {
            s = "";
        }
        textview5.setText(s);
        textview6 = (TextView)findViewById(0x7f09007d);
        if(data.Data_PEM_Motor_Battery_TemperaturesDataStale || !data.Data_CarPoweredON && !data.Data_CoolingPumpON_DoorState3)
            j1 = 0xff444444;
        else
            j1 = -1;
        textview6.setTextColor(j1);
        aobj = new Object[1];
        aobj[0] = Integer.valueOf((int)data.Data_TemperaturePEM);
        textview6.setText(String.format("%d\260C", aobj));
        textview7 = (TextView)findViewById(0x7f09007e);
        if(data.Data_PEM_Motor_Battery_TemperaturesDataStale || !data.Data_CarPoweredON && !data.Data_CoolingPumpON_DoorState3)
            k1 = 0xff444444;
        else
            k1 = -1;
        textview7.setTextColor(k1);
        aobj1 = new Object[1];
        aobj1[0] = Integer.valueOf((int)data.Data_TemperatureMotor);
        textview7.setText(String.format("%d\260C", aobj1));
        textview8 = (TextView)findViewById(0x7f09007f);
        if(data.Data_PEM_Motor_Battery_TemperaturesDataStale || !data.Data_CarPoweredON && !data.Data_CoolingPumpON_DoorState3)
            l1 = 0xff444444;
        else
            l1 = -1;
        textview8.setTextColor(l1);
        aobj2 = new Object[1];
        aobj2[0] = Integer.valueOf((int)data.Data_TemperatureBattery);
        textview8.setText(String.format("%d\260C", aobj2));
        textview9 = (TextView)findViewById(0x7f090080);
        if(data.Data_AmbientTemperatureDataStale || !data.Data_CarPoweredON && !data.Data_CoolingPumpON_DoorState3)
            i2 = 0xff444444;
        else
            i2 = -1;
        textview9.setTextColor(i2);
        aobj3 = new Object[1];
        aobj3[0] = Integer.valueOf((int)data.Data_TemperatureAmbient);
        textview9.setText(String.format("%d\260C", aobj3));
        textview10 = (TextView)findViewById(0x7f090070);
        if(data.Data_TPMSDataStale)
            j2 = 0xff444444;
        else
            j2 = -1;
        textview10.setTextColor(j2);
        if(data.Data_FLWheelPressure != 0.0D || data.Data_FLWheelTemperature != 0.0D)
            k2 = 0;
        else
            k2 = 4;
        textview10.setVisibility(k2);
        aobj4 = new Object[2];
        aobj4[0] = Double.valueOf(data.Data_FLWheelPressure);
        aobj4[1] = Double.valueOf(data.Data_FLWheelTemperature);
        textview10.setText(String.format("%.1fpsi\n%.0f\260C", aobj4));
        textview11 = (TextView)findViewById(0x7f090071);
        if(data.Data_TPMSDataStale)
            l2 = 0xff444444;
        else
            l2 = -1;
        textview11.setTextColor(l2);
        if(data.Data_FRWheelPressure != 0.0D || data.Data_FRWheelTemperature != 0.0D)
            i3 = 0;
        else
            i3 = 4;
        textview11.setVisibility(i3);
        aobj5 = new Object[2];
        aobj5[0] = Double.valueOf(data.Data_FRWheelPressure);
        aobj5[1] = Double.valueOf(data.Data_FRWheelTemperature);
        textview11.setText(String.format("%.1fpsi\n%.0f\260C", aobj5));
        textview12 = (TextView)findViewById(0x7f090072);
        if(data.Data_TPMSDataStale)
            j3 = 0xff444444;
        else
            j3 = -1;
        textview12.setTextColor(j3);
        if(data.Data_RLWheelPressure != 0.0D || data.Data_RLWheelTemperature != 0.0D)
            k3 = 0;
        else
            k3 = 4;
        textview12.setVisibility(k3);
        aobj6 = new Object[2];
        aobj6[0] = Double.valueOf(data.Data_RLWheelPressure);
        aobj6[1] = Double.valueOf(data.Data_RLWheelTemperature);
        textview12.setText(String.format("%.1fpsi\n%.0f\260C", aobj6));
        textview13 = (TextView)findViewById(0x7f090073);
        if(data.Data_TPMSDataStale)
            l3 = 0xff444444;
        else
            l3 = -1;
        textview13.setTextColor(l3);
        if(data.Data_RRWheelPressure != 0.0D || data.Data_RRWheelTemperature != 0.0D)
            i4 = 0;
        else
            i4 = 4;
        textview13.setVisibility(i4);
        aobj7 = new Object[2];
        aobj7[0] = Double.valueOf(data.Data_RRWheelPressure);
        aobj7[1] = Double.valueOf(data.Data_RRWheelTemperature);
        textview13.setText(String.format("%.1fpsi\n%.0f\260C", aobj7));
        imageview = (ImageView)findViewById(0x7f090067);
        stringbuilder = new StringBuilder(String.valueOf(getCacheDir().getAbsolutePath()));
        aobj8 = new Object[1];
        aobj8[0] = data.VehicleImageDrawable;
        bitmap = BitmapFactory.decodeFile(stringbuilder.append(String.format("/ol_%s.png", aobj8)).toString());
        if(bitmap != null)
        {
            imageview.setImageBitmap(bitmap);
        } else
        {
            StringBuilder stringbuilder1 = (new StringBuilder("** File Not Found: ")).append(getCacheDir().getAbsolutePath());
            Object aobj9[] = new Object[1];
            aobj9[0] = data.VehicleImageDrawable;
            Log.d("OVMS", stringbuilder1.append(String.format("/ol_%s.png", aobj9)).toString());
            if(!data.DontAskLayoutDownload && (lastUpdatedDialog == null || !lastUpdatedDialog.isShowing()))
            {
                data.DontAskLayoutDownload = true;
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setMessage("Would you like to download a set of high resolution car images specifically drawn for your car?\n\nThe download is approx. 300KB.\n\nNote: a manual download button is available in the car commands and settings tab.").setTitle("Download Graphics").setCancelable(true).setPositiveButton("Download Now", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int k6)
                    {
                        downloadLayout();
                        dialoginterface.dismiss();
                    }

                    final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
                }
).setNegativeButton("Later", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int k6)
                    {
                        dialoginterface.dismiss();
                    }

                    final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
                }
);
                lastUpdatedDialog = builder.create();
                lastUpdatedDialog.show();
            }
        }
        imageview1 = (ImageView)findViewById(0x7f090068);
        if(data.Data_ChargePortOpen)
            j4 = 0;
        else
            j4 = 8;
        imageview1.setVisibility(j4);
        imageview2 = (ImageView)findViewById(0x7f09006b);
        if(data.Data_BonnetOpen)
            k4 = 0;
        else
            k4 = 8;
        imageview2.setVisibility(k4);
        imageview3 = (ImageView)findViewById(0x7f09006c);
        if(data.Data_LeftDoorOpen)
            l4 = 0;
        else
            l4 = 8;
        imageview3.setVisibility(l4);
        imageview4 = (ImageView)findViewById(0x7f09006a);
        if(data.Data_RightDoorOpen)
            i5 = 0;
        else
            i5 = 8;
        imageview4.setVisibility(i5);
        imageview5 = (ImageView)findViewById(0x7f090069);
        if(data.Data_TrunkOpen)
            j5 = 0;
        else
            j5 = 8;
        imageview5.setVisibility(j5);
        imageview6 = (ImageView)findViewById(0x7f09006d);
        if(data.Data_CarLocked)
            k5 = 0x7f02003b;
        else
            k5 = 0x7f02003c;
        imageview6.setImageResource(k5);
        imageview7 = (ImageView)findViewById(0x7f09006e);
        if(data.Data_ValetON)
            l5 = 0x7f02003e;
        else
            l5 = 0x7f02003d;
        imageview7.setImageResource(l5);
        imageview8 = (ImageView)findViewById(0x7f09006f);
        if(data.Data_HeadlightsON)
            i6 = 0;
        else
            i6 = 8;
        imageview8.setVisibility(i6);
        j6 = Integer.parseInt(data.Data_CarModuleGSMSignalLevel);
        if(j6 >= 1) goto _L2; else goto _L1
_L1:
        imageview8.setImageResource(0x7f020068);
_L4:
        return;
_L2:
        if(j6 >= 7)
            break MISSING_BLOCK_LABEL_1797;
        imageview8.setImageResource(0x7f020069);
        continue; /* Loop/switch isn't completed */
        if(j6 < 14)
            imageview8.setImageResource(0x7f02006a);
        else
        if(j6 < 21)
            imageview8.setImageResource(0x7f02006b);
        else
        if(j6 < 28)
            imageview8.setImageResource(0x7f02006c);
        else
            imageview8.setImageResource(0x7f02006d);
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void updateInfoUI()
    {
        ImageView imageview3;
        int i;
        byte byte0 = 8;
        ((TextView)findViewById(0x7f09005a)).setText(data.VehicleID);
        TextView textview = (TextView)findViewById(0x7f090064);
        String s = getString(0x7f060004);
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(data.Data_SOC);
        textview.setText(String.format(s, aobj));
        SeekBar seekbar;
        TextView textview1;
        TextView textview2;
        ImageView imageview;
        byte byte1;
        if(findViewById(0x7f09005c) != null)
        {
            TableRow tablerow = (TableRow)findViewById(0x7f09005c);
            String s1;
            ImageView imageview1;
            ImageView imageview2;
            ImageView imageview4;
            StringBuilder stringbuilder;
            Object aobj3[];
            Bitmap bitmap;
            Object aobj5[];
            Object aobj10[];
            int j;
            if(data.Data_ChargePortOpen)
                j = 0;
            else
                j = byte0;
            tablerow.setVisibility(j);
        }
        seekbar = (SeekBar)findViewById(0x7f090060);
        textview1 = (TextView)findViewById(0x7f09005f);
        if(data.Data_ChargeState.equals("charging"))
        {
            aobj10 = new Object[1];
            aobj10[0] = data.Data_ChargeMode.toUpperCase();
            textview1.setText(String.format("Charging - %s", aobj10));
        } else
        if(data.Data_ChargeState.equals("prepare"))
        {
            Object aobj9[] = new Object[1];
            aobj9[0] = data.Data_ChargeMode.toUpperCase();
            textview1.setText(String.format("Preparing to Charge - %s", aobj9));
        } else
        if(data.Data_ChargeState.equals("heating"))
        {
            Object aobj8[] = new Object[1];
            aobj8[0] = data.Data_ChargeMode.toUpperCase();
            textview1.setText(String.format("Pre-Charge Battery Heating - %s", aobj8));
        } else
        if(data.Data_ChargeState.equals("topoff"))
        {
            Object aobj7[] = new Object[1];
            aobj7[0] = data.Data_ChargeMode.toUpperCase();
            textview1.setText(String.format("Topping Off - %s", aobj7));
        } else
        if(data.Data_ChargeState.equals("stopped"))
        {
            Object aobj6[] = new Object[1];
            aobj6[0] = data.Data_ChargeMode.toUpperCase();
            textview1.setText(String.format("Charge Interrupted - %s", aobj6));
        } else
        if(data.Data_ChargeState.equals("done"))
        {
            Object aobj1[] = new Object[1];
            aobj1[0] = data.Data_ChargeMode.toUpperCase();
            textview1.setText(String.format("Charge Completed - %s", aobj1));
        }
        textview2 = (TextView)findViewById(0x7f09005e);
        imageview = (ImageView)findViewById(0x7f090062);
        if(data.Data_Charging)
        {
            seekbar.setProgress(0);
            imageview.setVisibility(0);
            aobj5 = new Object[2];
            aobj5[0] = Integer.valueOf(data.Data_ChargeCurrent);
            aobj5[1] = Integer.valueOf(data.Data_LineVoltage);
            textview2.setText(String.format("%sA|%sV", aobj5));
        } else
        {
            seekbar.setProgress(100);
            imageview.setVisibility(byte0);
            Object aobj2[] = new Object[1];
            aobj2[0] = Integer.valueOf(data.Data_ChargeAmpsLimit);
            textview2.setText(String.format("%sA MAX", aobj2));
        }
        s1 = " km";
        if(data.Data_DistanceUnit != null && !data.Data_DistanceUnit.equals("K"))
            s1 = " miles";
        ((TextView)findViewById(0x7f090066)).setText((new StringBuilder(String.valueOf(data.Data_IdealRange))).append(s1).toString());
        ((TextView)findViewById(0x7f090065)).setText((new StringBuilder(String.valueOf(data.Data_EstimatedRange))).append(s1).toString());
        imageview1 = (ImageView)findViewById(0x7f090055);
        if(isLoggedIn)
            byte1 = byte0;
        else
            byte1 = 0;
        imageview1.setVisibility(byte1);
        imageview2 = (ImageView)findViewById(0x7f090056);
        if(data.ParanoidMode)
            byte0 = 0;
        imageview2.setVisibility(byte0);
        imageview3 = (ImageView)findViewById(0x7f090058);
        i = Integer.parseInt(data.Data_CarModuleGSMSignalLevel);
        if(i >= 1) goto _L2; else goto _L1
_L1:
        imageview3.setImageResource(0x7f020068);
_L4:
        ((ImageView)findViewById(0x7f090063)).setImageBitmap(Utilities.GetScaledBatteryOverlay(data.Data_SOC, BitmapFactory.decodeResource(getResources(), 0x7f020004)));
        imageview4 = (ImageView)findViewById(0x7f09005b);
        stringbuilder = new StringBuilder(String.valueOf(getCacheDir().getAbsolutePath()));
        aobj3 = new Object[1];
        aobj3[0] = data.VehicleImageDrawable;
        bitmap = BitmapFactory.decodeFile(stringbuilder.append(String.format("/%s.png", aobj3)).toString());
        if(bitmap != null)
        {
            imageview4.setImageBitmap(bitmap);
        } else
        {
            StringBuilder stringbuilder1 = (new StringBuilder("** File Not Found: ")).append(getCacheDir().getAbsolutePath());
            Object aobj4[] = new Object[1];
            aobj4[0] = data.VehicleImageDrawable;
            Log.d("OVMS", stringbuilder1.append(String.format("/%s.png", aobj4)).toString());
            if(!data.DontAskLayoutDownload && (lastUpdatedDialog == null || !lastUpdatedDialog.isShowing()))
            {
                data.DontAskLayoutDownload = true;
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setMessage("Would you like to download a set of high resolution car images specifically drawn for your car?\n\nThe download is approx. 300KB.\n\nNote: a manual download button is available in the car commands and settings tab.").setTitle("Download Graphics").setCancelable(true).setPositiveButton("Download Now", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int k)
                    {
                        downloadLayout();
                        dialoginterface.dismiss();
                    }

                    final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
                }
).setNegativeButton("Later", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int k)
                    {
                        dialoginterface.dismiss();
                    }

                    final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
                }
);
                lastUpdatedDialog = builder.create();
                lastUpdatedDialog.show();
            }
        }
        imageview4.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(TabInfo_xlarge.this);
                Object aobj11[] = new Object[10];
                String s2;
                String s3;
                String s4;
                String s5;
                String s6;
                String s7;
                if(data.Data_CarPoweredON)
                    s2 = "ON";
                else
                    s2 = "OFF";
                aobj11[0] = s2;
                aobj11[1] = data.Data_VIN;
                aobj11[2] = data.Data_CarModuleGSMSignalLevel;
                if(data.Data_HandBrakeApplied)
                    s3 = "ENGAGED";
                else
                    s3 = "DISENGAGED";
                aobj11[3] = s3;
                if(data.Data_ValetON)
                    s4 = "ON";
                else
                    s4 = "OFF";
                aobj11[4] = s4;
                if(data.Data_PINLocked)
                    s5 = "ON";
                else
                    s5 = "OFF";
                aobj11[5] = s5;
                if(data.Data_CoolingPumpON_DoorState3)
                    s6 = "ON";
                else
                    s6 = "OFF";
                aobj11[6] = s6;
                if(data.Data_GPSLocked)
                    s7 = "LOCKED";
                else
                    s7 = "(searching...)";
                aobj11[7] = s7;
                aobj11[8] = data.Data_CarModuleFirmwareVersion;
                aobj11[9] = data.Data_OVMSServerFirmwareVersion;
                builder1.setMessage(String.format("Power: %s\nVIN: %s\nGSM Signal: %s\nHandbrake: %s\nValet: %s\nLock: %s\nCooling Pump: %s\nGPS: %s\n\nCar Module: %s\nOVMS Server: %s", aobj11)).setTitle("Vehicle Information").setCancelable(true).setPositiveButton("Close", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int k)
                    {
                        dialoginterface.dismiss();
                    }

                    final _cls18 this$1;

                    
                    {
                        this$1 = _cls18.this;
                        super();
                    }
                }
);
                softwareInformation = builder1.create();
                softwareInformation.show();
            }

            final TabInfo_xlarge this$0;

            
            {
                this$0 = TabInfo_xlarge.this;
                super();
            }
        }
);
        return;
_L2:
        if(i >= 7)
            break MISSING_BLOCK_LABEL_956;
        imageview3.setImageResource(0x7f020069);
        continue; /* Loop/switch isn't completed */
        if(i < 14)
            imageview3.setImageResource(0x7f02006a);
        else
        if(i < 21)
            imageview3.setImageResource(0x7f02006b);
        else
        if(i < 28)
            imageview3.setImageResource(0x7f02006c);
        else
            imageview3.setImageResource(0x7f02006d);
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void updateLastUpdatedView()
    {
        if(data != null && data.Data_LastCarUpdate != null)
        {
            TextView textview = (TextView)findViewById(0x7f090057);
            long l = ((new Date()).getTime() - data.Data_LastCarUpdate.getTime()) / 1000L;
            TextView textview1;
            LinearLayout linearlayout;
            if(l < 60L)
                textview.setText("live");
            else
            if(l < 3600L)
            {
                int j2 = (int)Math.ceil(l / 60L);
                Object aobj7[] = new Object[2];
                aobj7[0] = Integer.valueOf(j2);
                String s8;
                if(j2 > 1)
                    s8 = "s";
                else
                    s8 = "";
                aobj7[1] = s8;
                textview.setText(String.format("Updated: %d min%s ago", aobj7));
            } else
            if(l < 0x15180L)
            {
                int i2 = (int)Math.ceil(l / 3600L);
                Object aobj6[] = new Object[2];
                aobj6[0] = Integer.valueOf(i2);
                String s7;
                if(i2 > 1)
                    s7 = "s";
                else
                    s7 = "";
                aobj6[1] = s7;
                textview.setText(String.format("Updated: %d hr%s ago", aobj6));
            } else
            if(l < 0xd2f00L)
            {
                int k1 = (int)Math.ceil(l / 0x15180L);
                Object aobj5[] = new Object[2];
                aobj5[0] = Integer.valueOf(k1);
                String s6;
                if(k1 > 1)
                    s6 = "s";
                else
                    s6 = "";
                aobj5[1] = s6;
                textview.setText(String.format("Updated: %d day%s ago", aobj5));
            } else
            {
                String s = getString(0x7f060003);
                Object aobj[] = new Object[1];
                aobj[0] = data.Data_LastCarUpdate;
                textview.setText(String.format(s, aobj));
            }
            textview1 = (TextView)findViewById(0x7f090054);
            linearlayout = (LinearLayout)findViewById(0x7f090052);
            if(!data.Data_CarPoweredON && data.Data_ParkedTime_raw != 0.0D)
            {
                long l1 = l + (long)data.Data_ParkedTime_raw;
                data.Data_ParkedTime = new Date((new Date()).getTime() - 1000L * l1);
                if(l1 < 60L)
                    textview1.setText("just now");
                else
                if(l1 < 3600L)
                {
                    int j1 = (int)Math.ceil(l1 / 60L);
                    Object aobj4[] = new Object[2];
                    aobj4[0] = Integer.valueOf(j1);
                    String s5;
                    if(j1 > 1)
                        s5 = "s";
                    else
                        s5 = "";
                    aobj4[1] = s5;
                    textview1.setText(String.format("%d min%s", aobj4));
                } else
                if(l1 < 0x15180L)
                {
                    int k = (int)Math.floor(l1 / 3600L);
                    int i1 = (int)Math.ceil(Math.abs((l1 - (long)(k * 3600)) / 60L));
                    Object aobj3[] = new Object[4];
                    aobj3[0] = Integer.valueOf(k);
                    String s3;
                    String s4;
                    if(k > 1)
                        s3 = "s";
                    else
                        s3 = "";
                    aobj3[1] = s3;
                    aobj3[2] = Integer.valueOf(i1);
                    if(i1 > 1)
                        s4 = "s";
                    else
                        s4 = "";
                    aobj3[3] = s4;
                    textview1.setText(String.format("%d hr%s %d min%s", aobj3));
                } else
                if(l1 < 0xd2f00L)
                {
                    int i = (int)Math.floor(l1 / 0x15180L);
                    int j = (int)Math.ceil(Math.abs((l1 - (long)(0x15180 * i)) / 3600L));
                    Object aobj2[] = new Object[4];
                    aobj2[0] = Integer.valueOf(i);
                    String s1;
                    String s2;
                    if(i > 1)
                        s1 = "s";
                    else
                        s1 = "";
                    aobj2[1] = s1;
                    aobj2[2] = Integer.valueOf(j);
                    if(j > 1)
                        s2 = "s";
                    else
                        s2 = "";
                    aobj2[3] = s2;
                    textview1.setText(String.format("%d day%s %d hr%s", aobj2));
                } else
                {
                    Object aobj1[] = new Object[1];
                    aobj1[0] = data.Data_ParkedTime;
                    textview1.setText(String.format("%1$tD %1$tT", aobj1));
                }
                linearlayout.setVisibility(0);
            } else
            {
                linearlayout.setVisibility(8);
            }
        }
    }

    private void updateMapUI()
    {
        Log.d("OVMS", "Refreshing Map");
        GeoPoint geopoint;
        try
        {
            if(!currentVehicleID.equals(data.VehicleID))
            {
                currentVehicleID = data.VehicleID;
                carMarkers = new Utilities.CarMarkerOverlay(getResources().getDrawable(getResources().getIdentifier((new StringBuilder(String.valueOf(data.VehicleImageDrawable))).append("32x32").toString(), "drawable", "com.openvehicles.OVMS")), 20, this, DirectionalMarker, 1);
                mapOverlays.set(0, carMarkers);
            }
        }
        catch(Exception exception) { }
        geopoint = Utilities.GetCarGeopoint(data);
        if(carMarkers.size() == 0 || !carMarkers.getItem(0).getPoint().equals(geopoint))
            if(carMarkers.size() > 0)
            {
                lastCarGeoPoint = carMarkers.getItem(0).getPoint();
                carMarkerAnimationTimerHandler.removeCallbacks(animateCarMarker);
                carMarkerAnimationFrame = 0;
                carMarkerAnimationTimerHandler.postDelayed(animateCarMarker, 0L);
            } else
            {
                String s = "-";
                if(data.Data_LastCarUpdate != null)
                    s = (new SimpleDateFormat("MMM d, K:mm:ss a")).format(data.Data_LastCarUpdate);
                String s1 = data.VehicleID;
                Object aobj[] = new Object[1];
                aobj[0] = s;
                Utilities.CarMarker carmarker = new Utilities.CarMarker(geopoint, s1, String.format("Last reported: %s", aobj), (int)data.Data_Direction);
                carMarkers.addOverlay(carmarker);
            }
        mc.animateTo(geopoint);
        mc.setZoom(17);
        mapView.invalidate();
    }

    public void OrientationChanged()
    {
        orientationChangedHandler.sendEmptyMessage(0);
    }

    public void Refresh(CarData cardata, boolean flag)
    {
        data = cardata;
        isLoggedIn = flag;
        handler.sendEmptyMessage(0);
    }

    protected boolean isRouteDisplayed()
    {
        return false;
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03000f);
        initInfoUI();
        initCarLayoutUI();
        initMapUI();
    }

    protected void onPause()
    {
        super.onPause();
        try
        {
            if(softwareInformation != null && softwareInformation.isShowing())
                softwareInformation.dismiss();
        }
        catch(Exception exception) { }
        try
        {
            if(lastUpdatedDialog != null && lastUpdatedDialog.isShowing())
                lastUpdatedDialog.dismiss();
        }
        catch(Exception exception1) { }
        lastUpdateTimerHandler.removeCallbacks(lastUpdateTimer);
    }

    protected void onResume()
    {
        super.onResume();
        lastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000L);
    }

    private final int CAR_MARKER_ANIMATION_DURATION_MS = 2000;
    private final int CAR_MARKER_ANIMATION_FRAMES = 40;
    public int CurrentScreenOrientation;
    private Bitmap DirectionalMarker;
    private final int LABEL_SHADOW_XY = 1;
    private final int LABEL_TEXT_SIZE = 20;
    private Runnable animateCarMarker;
    private int carMarkerAnimationFrame;
    private Handler carMarkerAnimationTimerHandler;
    private Utilities.CarMarkerOverlay carMarkers;
    private String currentVehicleID;
    private CarData data;
    private ProgressDialog downloadProgress;
    private ServerCommands.CarLayoutDownloader downloadTask;
    private Handler handler;
    private boolean isLoggedIn;
    private GeoPoint lastCarGeoPoint;
    private Runnable lastUpdateTimer;
    private Handler lastUpdateTimerHandler;
    private AlertDialog lastUpdatedDialog;
    private List mapOverlays;
    private MapView mapView;
    private MapController mc;
    private Handler orientationChangedHandler;
    private AlertDialog softwareInformation;



















}
