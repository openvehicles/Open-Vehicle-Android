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
                ImageView imageview10;
                int k6;
                updateLastUpdatedView();
                ((TextView)findViewById(0x7f090019)).setText(data.VehicleID);
                TextView textview = (TextView)findViewById(0x7f09002b);
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
                int j6;
                if(data.Data_LeftDoorOpen)
                    i = 0;
                else
                    i = 4;
                textview.setVisibility(i);
                textview1 = (TextView)findViewById(0x7f09002c);
                if(data.Data_RightDoorOpen)
                    j = 0;
                else
                    j = 4;
                textview1.setVisibility(j);
                textview2 = (TextView)findViewById(0x7f09002d);
                if(data.Data_ChargePortOpen)
                    k = 0;
                else
                    k = 4;
                textview2.setVisibility(k);
                textview3 = (TextView)findViewById(0x7f09002f);
                if(data.Data_BonnetOpen)
                    l = 0;
                else
                    l = 4;
                textview3.setVisibility(l);
                textview4 = (TextView)findViewById(0x7f09002e);
                if(data.Data_TrunkOpen)
                    i1 = 0;
                else
                    i1 = 4;
                textview4.setVisibility(i1);
                textview5 = (TextView)findViewById(0x7f090030);
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
                    android.graphics.Bitmap bitmap;
                    ImageView imageview1;
                    ImageView imageview2;
                    ImageView imageview3;
                    ImageView imageview4;
                    ImageView imageview5;
                    ImageView imageview6;
                    ImageView imageview7;
                    ImageView imageview8;
                    ImageView imageview9;
                    ImageView imageview11;
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
                textview6 = (TextView)findViewById(0x7f090034);
                if(!data.Data_CarPoweredON && !data.Data_CoolingPumpON_DoorState3)
                    j1 = 0xff444444;
                else
                    j1 = -1;
                textview6.setTextColor(j1);
                aobj = new Object[1];
                aobj[0] = Integer.valueOf((int)data.Data_TemperaturePEM);
                textview6.setText(String.format("%d\260C", aobj));
                textview7 = (TextView)findViewById(0x7f090035);
                if(!data.Data_CarPoweredON && !data.Data_CoolingPumpON_DoorState3)
                    k1 = 0xff444444;
                else
                    k1 = -1;
                textview7.setTextColor(k1);
                aobj1 = new Object[1];
                aobj1[0] = Integer.valueOf((int)data.Data_TemperatureMotor);
                textview7.setText(String.format("%d\260C", aobj1));
                textview8 = (TextView)findViewById(0x7f090036);
                if(!data.Data_CarPoweredON && !data.Data_CoolingPumpON_DoorState3)
                    l1 = 0xff444444;
                else
                    l1 = -1;
                textview8.setTextColor(l1);
                aobj2 = new Object[1];
                aobj2[0] = Integer.valueOf((int)data.Data_TemperatureBattery);
                textview8.setText(String.format("%d\260C", aobj2));
                textview9 = (TextView)findViewById(0x7f090037);
                if(data.Data_AmbientTemperatureDataStale || !data.Data_CarPoweredON && !data.Data_CoolingPumpON_DoorState3)
                    i2 = 0xff444444;
                else
                    i2 = -1;
                textview9.setTextColor(i2);
                aobj3 = new Object[1];
                aobj3[0] = Integer.valueOf((int)data.Data_TemperatureAmbient);
                textview9.setText(String.format("%d\260C", aobj3));
                textview10 = (TextView)findViewById(0x7f090027);
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
                textview11 = (TextView)findViewById(0x7f090028);
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
                textview12 = (TextView)findViewById(0x7f090029);
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
                textview13 = (TextView)findViewById(0x7f09002a);
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
                imageview = (ImageView)findViewById(0x7f09001e);
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
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TabCar.this);
                        builder.setMessage("Would you like to download a set of high resolution car images specifically drawn for your car?\n\nThe download is approx. 300KB.\n\nNote: a manual download button is available in the car commands and settings tab.").setTitle("Download Graphics").setCancelable(true).setPositiveButton("Download Now", new android.content.DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialoginterface, int l6)
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

                            public void onClick(DialogInterface dialoginterface, int l6)
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
                imageview1 = (ImageView)findViewById(0x7f09001f);
                if(data.Data_ChargePortOpen)
                    j4 = 0;
                else
                    j4 = 8;
                imageview1.setVisibility(j4);
                imageview2 = (ImageView)findViewById(0x7f090022);
                if(data.Data_BonnetOpen)
                    k4 = 0;
                else
                    k4 = 8;
                imageview2.setVisibility(k4);
                imageview3 = (ImageView)findViewById(0x7f090023);
                if(data.Data_LeftDoorOpen)
                    l4 = 0;
                else
                    l4 = 8;
                imageview3.setVisibility(l4);
                imageview4 = (ImageView)findViewById(0x7f090021);
                if(data.Data_RightDoorOpen)
                    i5 = 0;
                else
                    i5 = 8;
                imageview4.setVisibility(i5);
                imageview5 = (ImageView)findViewById(0x7f090020);
                if(data.Data_TrunkOpen)
                    j5 = 0;
                else
                    j5 = 8;
                imageview5.setVisibility(j5);
                imageview6 = (ImageView)findViewById(0x7f090024);
                if(data.Data_CarLocked)
                    k5 = 0x7f02003b;
                else
                    k5 = 0x7f02003c;
                imageview6.setImageResource(k5);
                imageview7 = (ImageView)findViewById(0x7f090025);
                if(data.Data_ValetON)
                    l5 = 0x7f02003e;
                else
                    l5 = 0x7f02003d;
                imageview7.setImageResource(l5);
                imageview8 = (ImageView)findViewById(0x7f090026);
                if(data.Data_HeadlightsON)
                    i6 = 0;
                else
                    i6 = 8;
                imageview8.setVisibility(i6);
                imageview9 = (ImageView)findViewById(0x7f09001b);
                if(data.ParanoidMode)
                    j6 = 0;
                else
                    j6 = 8;
                imageview9.setVisibility(j6);
                imageview10 = (ImageView)findViewById(0x7f09001d);
                k6 = Integer.parseInt(data.Data_CarModuleGSMSignalLevel);
                if(k6 >= 1) goto _L2; else goto _L1
_L1:
                imageview10.setImageResource(0x7f020068);
_L4:
                imageview11 = (ImageView)findViewById(0x7f09001a);
                byte byte0;
                if(isLoggedIn)
                    byte0 = 8;
                else
                    byte0 = 0;
                imageview11.setVisibility(byte0);
                return;
_L2:
                if(k6 >= 7)
                    break MISSING_BLOCK_LABEL_2099;
                imageview10.setImageResource(0x7f020069);
                continue; /* Loop/switch isn't completed */
                if(k6 < 14)
                    imageview10.setImageResource(0x7f02006a);
                else
                if(k6 < 21)
                    imageview10.setImageResource(0x7f02006b);
                else
                if(k6 < 28)
                    imageview10.setImageResource(0x7f02006c);
                else
                    imageview10.setImageResource(0x7f02006d);
                continue; /* Loop/switch isn't completed */
                Exception exception;
                exception;
                if(true) goto _L4; else goto _L3
_L3:
            }

            final TabCar this$0;


            
            {
                this$0 = TabCar.this;
                super();
            }
        }
;
        orientationChangedHandler = new Handler() {

            public void handleMessage(Message message)
            {
                setContentView(0x7f03000a);
                CurrentScreenOrientation = getResources().getConfiguration().orientation;
                initUI();
            }

            final TabCar this$0;

            
            {
                this$0 = TabCar.this;
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
                android.graphics.Bitmap bitmap = BitmapFactory.decodeFile(stringbuilder.append(String.format("/ol_%s.png", aobj)).toString());
                if(bitmap != null)
                {
                    ((ImageView)findViewById(0x7f09001e)).setImageBitmap(bitmap);
                    Toast.makeText(TabCar.this, "Graphics Downloaded", 0).show();
                } else
                {
                    Toast.makeText(TabCar.this, "Download Failed", 0).show();
                }
            }

            final TabCar this$0;

            
            {
                this$0 = TabCar.this;
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
        ((TextView)findViewById(0x7f09001c)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                String s = "-";
                if(data != null && data.Data_LastCarUpdate != null)
                    s = (new SimpleDateFormat("MMM d, K:mm:ss a")).format(data.Data_LastCarUpdate);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TabCar.this);
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

            final TabCar this$0;

            
            {
                this$0 = TabCar.this;
                super();
            }
        }
);
        ((FrameLayout)findViewById(0x7f090031)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(!isFinishing())
                {
                    TabCar tabcar = TabCar.this;
                    OVMSActivity ovmsactivity = (OVMSActivity)getParent();
                    boolean flag;
                    if(data.Data_CarLocked)
                        flag = false;
                    else
                        flag = true;
                    ServerCommands.LockUnlockCar(tabcar, ovmsactivity, null, flag);
                }
            }

            final TabCar this$0;

            
            {
                this$0 = TabCar.this;
                super();
            }
        }
);
        ((FrameLayout)findViewById(0x7f090032)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(!isFinishing())
                {
                    TabCar tabcar = TabCar.this;
                    OVMSActivity ovmsactivity = (OVMSActivity)getParent();
                    boolean flag;
                    if(data.Data_ValetON)
                        flag = false;
                    else
                        flag = true;
                    ServerCommands.ValetModeOnOff(tabcar, ovmsactivity, null, flag);
                }
            }

            final TabCar this$0;

            
            {
                this$0 = TabCar.this;
                super();
            }
        }
);
        ((LinearLayout)findViewById(0x7f090033)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(!isFinishing() && !data.Data_CoolingPumpON_DoorState3)
                    ServerCommands.WakeUp(TabCar.this, (OVMSActivity)getParent(), null, true);
            }

            final TabCar this$0;

            
            {
                this$0 = TabCar.this;
                super();
            }
        }
);
    }

    private void updateLastUpdatedView()
    {
        if(data != null && data.Data_LastCarUpdate != null)
        {
            TextView textview = (TextView)findViewById(0x7f09001c);
            long l = ((new Date()).getTime() - data.Data_LastCarUpdate.getTime()) / 1000L;
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
                textview.setText(String.format("Updated: %d min%s ago", aobj3));
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
                textview.setText(String.format("Updated: %d hr%s ago", aobj2));
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
                String s = getString(0x7f060003);
                Object aobj[] = new Object[1];
                aobj[0] = data.Data_LastCarUpdate;
                textview.setText(String.format(s, aobj));
            }
        }
    }

    public void OrientationChanged()
    {
        orientationChangedHandler.sendEmptyMessage(0);
    }

    public void Refresh(CarData cardata, boolean flag)
    {
        Log.d("Tab", "TabCar Refresh");
        data = cardata;
        isLoggedIn = flag;
        handler.sendEmptyMessage(0);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03000a);
        initUI();
    }

    protected void onPause()
    {
        super.onPause();
        try
        {
            if(lastUpdatedDialog != null && lastUpdatedDialog.isShowing())
                lastUpdatedDialog.dismiss();
        }
        catch(Exception exception) { }
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









}
