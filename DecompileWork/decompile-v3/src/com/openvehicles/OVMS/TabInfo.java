// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.*;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package com.openvehicles.OVMS:
//            CarData, OVMSActivity, ServerCommands

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
                ImageView imageview3;
                int k;
                updateLastUpdatedView();
                ((TextView)findViewById(0x7f090044)).setText(data.VehicleID);
                TextView textview = (TextView)findViewById(0x7f09004e);
                String s = getString(0x7f060004);
                Object aobj[] = new Object[1];
                aobj[0] = Integer.valueOf(data.Data_SOC);
                textview.setText(String.format(s, aobj));
                SeekBar seekbar;
                TextView textview1;
                TextView textview2;
                ImageView imageview;
                byte byte0;
                int j;
                if(findViewById(0x7f090046) != null)
                {
                    TableRow tablerow = (TableRow)findViewById(0x7f090046);
                    String s1;
                    ImageView imageview1;
                    ImageView imageview2;
                    ImageView imageview4;
                    StringBuilder stringbuilder;
                    Object aobj3[];
                    android.graphics.Bitmap bitmap;
                    Object aobj5[];
                    Object aobj10[];
                    int l;
                    if(data.Data_ChargePortOpen)
                        l = 0;
                    else
                        l = 8;
                    tablerow.setVisibility(l);
                } else
                if(findViewById(0x7f090051) != null)
                {
                    RelativeLayout relativelayout = (RelativeLayout)findViewById(0x7f090051);
                    int i;
                    if(data.Data_ChargePortOpen)
                        i = 0;
                    else
                        i = 8;
                    relativelayout.setVisibility(i);
                }
                seekbar = (SeekBar)findViewById(0x7f09004a);
                textview1 = (TextView)findViewById(0x7f090049);
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
                textview2 = (TextView)findViewById(0x7f090048);
                imageview = (ImageView)findViewById(0x7f09004c);
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
                    imageview.setVisibility(8);
                    Object aobj2[] = new Object[1];
                    aobj2[0] = Integer.valueOf(data.Data_ChargeAmpsLimit);
                    textview2.setText(String.format("%sA MAX", aobj2));
                }
                s1 = " km";
                if(data.Data_DistanceUnit != null && !data.Data_DistanceUnit.equals("K"))
                    s1 = " miles";
                ((TextView)findViewById(0x7f090050)).setText((new StringBuilder(String.valueOf(data.Data_IdealRange))).append(s1).toString());
                ((TextView)findViewById(0x7f09004f)).setText((new StringBuilder(String.valueOf(data.Data_EstimatedRange))).append(s1).toString());
                imageview1 = (ImageView)findViewById(0x7f090041);
                if(isLoggedIn)
                    byte0 = 8;
                else
                    byte0 = 0;
                imageview1.setVisibility(byte0);
                imageview2 = (ImageView)findViewById(0x7f090042);
                if(data.ParanoidMode)
                    j = 0;
                else
                    j = 8;
                imageview2.setVisibility(j);
                imageview3 = (ImageView)findViewById(0x7f09001d);
                k = Integer.parseInt(data.Data_CarModuleGSMSignalLevel);
                if(k >= 1) goto _L2; else goto _L1
_L1:
                imageview3.setImageResource(0x7f020068);
_L4:
                ((ImageView)findViewById(0x7f09004d)).getLayoutParams().width = (268 * data.Data_SOC) / 100;
                imageview4 = (ImageView)findViewById(0x7f090045);
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
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TabInfo.this);
                        builder.setMessage("Would you like to download a set of high resolution car images specifically drawn for your car?\n\nThe download is approx. 300KB.\n\nNote: a manual download button is available in the car commands and settings tab.").setTitle("Download Graphics").setCancelable(true).setPositiveButton("Download Now", new android.content.DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialoginterface, int i1)
                            {
                                downloadLayout();
                                dialoginterface.dismiss();
                            }

                            final _cls2 this$1;

                    
                    {
                        this$1 = _cls2.this;
                        super();
                    }
                        }
).setNegativeButton("Later", new android.content.DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialoginterface, int i1)
                            {
                                dialoginterface.dismiss();
                            }

                            final _cls2 this$1;

                    
                    {
                        this$1 = _cls2.this;
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
                        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(_fld0);
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

                            public void onClick(DialogInterface dialoginterface, int i1)
                            {
                                dialoginterface.dismiss();
                            }

                            final _cls3 this$2;

                        
                        {
                            this$2 = _cls3.this;
                            super();
                        }
                        }
);
                        softwareInformation = builder1.create();
                        softwareInformation.show();
                    }

                    final _cls2 this$1;

                    
                    {
                        this$1 = _cls2.this;
                        super();
                    }
                }
);
                return;
_L2:
                if(k >= 7)
                    break MISSING_BLOCK_LABEL_1127;
                imageview3.setImageResource(0x7f020069);
                continue; /* Loop/switch isn't completed */
                if(k < 14)
                    imageview3.setImageResource(0x7f02006a);
                else
                if(k < 21)
                    imageview3.setImageResource(0x7f02006b);
                else
                if(k < 28)
                    imageview3.setImageResource(0x7f02006c);
                else
                    imageview3.setImageResource(0x7f02006d);
                continue; /* Loop/switch isn't completed */
                Exception exception;
                exception;
                if(true) goto _L4; else goto _L3
_L3:
            }

            final TabInfo this$0;


            
            {
                this$0 = TabInfo.this;
                super();
            }
        }
;
        orientationChangedHandler = new Handler() {

            public void handleMessage(Message message)
            {
                setContentView(0x7f03000e);
                CurrentScreenOrientation = getResources().getConfiguration().orientation;
                initUI();
            }

            final TabInfo this$0;

            
            {
                this$0 = TabInfo.this;
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
                android.graphics.Bitmap bitmap = BitmapFactory.decodeFile(stringbuilder.append(String.format("/%s.png", aobj)).toString());
                if(bitmap != null)
                {
                    ((ImageView)findViewById(0x7f090045)).setImageBitmap(bitmap);
                    Toast.makeText(TabInfo.this, "Graphics Downloaded", 0).show();
                } else
                {
                    Toast.makeText(TabInfo.this, "Download Failed", 0).show();
                }
            }

            final TabInfo this$0;

            
            {
                this$0 = TabInfo.this;
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

    private void initUI()
    {
        ((TextView)findViewById(0x7f090043)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(data.Data_LastCarUpdate != null)
                {
                    String s = (new SimpleDateFormat("MMM d, K:mm:ss a")).format(data.Data_LastCarUpdate);
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TabInfo.this);
                    builder.setMessage((new StringBuilder("Last update: ")).append(s).toString()).setCancelable(true).setPositiveButton("Close", new android.content.DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                            dialoginterface.dismiss();
                        }

                        final _cls4 this$1;

                    
                    {
                        this$1 = _cls4.this;
                        super();
                    }
                    }
).setTitle(data.VehicleID);
                    lastUpdatedDialog = builder.create();
                    lastUpdatedDialog.show();
                }
            }

            final TabInfo this$0;

            
            {
                this$0 = TabInfo.this;
                super();
            }
        }
);
        ((TextView)findViewById(0x7f090040)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(data.Data_ParkedTime != null)
                {
                    String s = (new SimpleDateFormat("MMM d, K:mm:ss a")).format(data.Data_ParkedTime);
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TabInfo.this);
                    builder.setMessage((new StringBuilder("Parked since: ")).append(s).toString()).setCancelable(true).setPositiveButton("Close", new android.content.DialogInterface.OnClickListener() {

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

            final TabInfo this$0;

            
            {
                this$0 = TabInfo.this;
                super();
            }
        }
);
        ((TextView)findViewById(0x7f090049)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                ServerCommands.SetChargeMode(TabInfo.this, (OVMSActivity)getParent(), null);
            }

            final TabInfo this$0;

            
            {
                this$0 = TabInfo.this;
                super();
            }
        }
);
        ((TextView)findViewById(0x7f09004e)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                ServerCommands.SetChargeCurrent(TabInfo.this, (OVMSActivity)getParent(), null, data.Data_ChargeAmpsLimit);
            }

            final TabInfo this$0;

            
            {
                this$0 = TabInfo.this;
                super();
            }
        }
);
        ((SeekBar)findViewById(0x7f09004a)).setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {

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
                        Toast.makeText(TabInfo.this, "Already charging...", 0).show();
                    else
                        ServerCommands.StartCharge(TabInfo.this, (OVMSActivity)getParent(), null).setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {

                            public void onCancel(DialogInterface dialoginterface)
                            {
                                seekBar.setProgress(seekBar.getMax());
                            }

                            final _cls8 this$1;
                            private final SeekBar val$seekBar;

                    
                    {
                        this$1 = _cls8.this;
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
                        Toast.makeText(TabInfo.this, "Already stopped...", 0).show();
                    else
                        ServerCommands.StopCharge(TabInfo.this, (OVMSActivity)getParent(), null).setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {

                            public void onCancel(DialogInterface dialoginterface)
                            {
                                seekBar.setProgress(0);
                            }

                            final _cls8 this$1;
                            private final SeekBar val$seekBar;

                    
                    {
                        this$1 = _cls8.this;
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

            final TabInfo this$0;

            
            {
                this$0 = TabInfo.this;
                super();
            }
        }
);
    }

    private void updateLastUpdatedView()
    {
        if(data != null && data.Data_LastCarUpdate != null)
        {
            TextView textview = (TextView)findViewById(0x7f090043);
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
            textview1 = (TextView)findViewById(0x7f090040);
            linearlayout = (LinearLayout)findViewById(0x7f09003e);
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

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03000e);
        initUI();
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

    public int CurrentScreenOrientation;
    private CarData data;
    private ProgressDialog downloadProgress;
    private ServerCommands.CarLayoutDownloader downloadTask;
    private Handler handler;
    private boolean isLoggedIn;
    private Runnable lastUpdateTimer;
    private Handler lastUpdateTimerHandler;
    private AlertDialog lastUpdatedDialog;
    private Handler orientationChangedHandler;
    private AlertDialog softwareInformation;











}
