// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.*;
import android.content.*;
import android.content.res.Resources;
import android.os.*;
import android.util.Base64;
import android.util.Log;
import android.view.*;
import android.widget.TabHost;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

// Referenced classes of package com.openvehicles.OVMS:
//            CarData, TabInfo, TabCar, TabMap, 
//            TabNotifications, TabCars, OVMSNotifications

public class OVMSActivity extends TabActivity
    implements android.widget.TabHost.OnTabChangeListener
{
    private class TCPTask extends AsyncTask
    {

        private void ConnInit()
        {
            String s;
            String s1;
            String s2;
            byte abyte0[];
            s = carData.CarPass;
            s1 = carData.VehicleID;
            char ac[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
            Random random = new Random();
            s2 = "";
            for(int i = 0; i < 22; i++)
                s2 = (new StringBuilder()).append(s2).append(ac[random.nextInt(-1 + ac.length)]).toString();

            abyte0 = s2.getBytes();
            Mac mac;
            mac = Mac.getInstance("HmacMD5");
            SecretKeySpec secretkeyspec = new SecretKeySpec(s.getBytes(), "HmacMD5");
            mac.init(secretkeyspec);
            String s3 = Base64.encodeToString(mac.doFinal(abyte0), 2);
            Sock = new Socket(carData.ServerNameOrIP, 6867);
            Outputstream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(Sock.getOutputStream())), true);
            Object aobj[] = new Object[3];
            aobj[0] = s2;
            aobj[1] = s3;
            aobj[2] = s1;
            Log.d("OVMS", String.format("TX: MP-A 0 %s %s %s", aobj));
            PrintWriter printwriter = Outputstream;
            Object aobj1[] = new Object[3];
            aobj1[0] = s2;
            aobj1[1] = s3;
            aobj1[2] = s1;
            printwriter.println(String.format("MP-A 0 %s %s %s", aobj1));
            Inputstream = new BufferedReader(new InputStreamReader(Sock.getInputStream()));
            String as[];
            Object aobj2[];
            String s4;
            byte abyte1[];
            byte abyte2[];
            String s5;
            byte abyte3[];
            Object aobj3[];
            Cipher cipher;
            SecretKeySpec secretkeyspec1;
            Cipher cipher1;
            SecretKeySpec secretkeyspec2;
            String s6;
            try
            {
                as = Inputstream.readLine().trim().split("[ ]+");
            }
            catch(Exception exception1)
            {
                if(!SuppressServerErrorDialog)
                    notifyServerSocketError(exception1);
                break MISSING_BLOCK_LABEL_794;
            }
            UnknownHostException unknownhostexception;
            aobj2 = new Object[4];
            aobj2[0] = as[0];
            aobj2[1] = as[1];
            aobj2[2] = as[2];
            aobj2[3] = as[3];
            Log.d("OVMS", String.format("RX: %s %s %s %s", aobj2));
            s4 = as[2];
            abyte1 = s4.getBytes();
            abyte2 = Base64.decode(as[3], 0);
            if(!Arrays.equals(mac.doFinal(abyte1), abyte2))
            {
                Object aobj5[] = new Object[2];
                aobj5[0] = Base64.encodeToString(mac.doFinal(as[2].getBytes()), 2);
                aobj5[1] = as[3];
                Log.d("OVMS", String.format("Server authentication failed. Expected %s Got %s", aobj5));
            } else
            {
                Log.d("OVMS", "Server authentication OK.");
            }
            s5 = (new StringBuilder()).append(s4).append(s2).toString();
            abyte3 = mac.doFinal(s5.getBytes());
            aobj3 = new Object[3];
            aobj3[0] = s5;
            aobj3[1] = toHex(abyte3).toLowerCase();
            aobj3[2] = Base64.encodeToString(abyte3, 2);
            Log.d("OVMS", String.format("Client version of the shared key is %s - (%s) %s", aobj3));
            rxcipher = Cipher.getInstance("RC4");
            cipher = rxcipher;
            secretkeyspec1 = new SecretKeySpec(abyte3, "RC4");
            cipher.init(2, secretkeyspec1);
            txcipher = Cipher.getInstance("RC4");
            cipher1 = txcipher;
            secretkeyspec2 = new SecretKeySpec(abyte3, "RC4");
            cipher1.init(1, secretkeyspec2);
            s6 = "";
            for(int j = 0; j < 1024; j++)
                s6 = (new StringBuilder()).append(s6).append("0").toString();

            try
            {
                rxcipher.update(s6.getBytes());
                txcipher.update(s6.getBytes());
                Object aobj4[] = new Object[1];
                aobj4[0] = carData.ServerNameOrIP;
                Log.d("OVMS", String.format("Connected to %s. Ciphers initialized. Listening...", aobj4));
                loginComplete();
            }
            // Misplaced declaration of an exception variable
            catch(UnknownHostException unknownhostexception)
            {
                notifyServerSocketError(unknownhostexception);
                unknownhostexception.printStackTrace();
            }
            catch(IOException ioexception)
            {
                notifyServerSocketError(ioexception);
                ioexception.printStackTrace();
            }
            catch(NullPointerException nullpointerexception)
            {
                nullpointerexception.printStackTrace();
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        }

        private void HandleMessage(String s)
        {
            char c;
            String s1;
            c = s.charAt(0);
            s1 = s.substring(1);
            if(c != 'E') goto _L2; else goto _L1
_L1:
            char c1 = s.charAt(1);
            if(c1 != 'T') goto _L4; else goto _L3
_L3:
            Log.d("TCP", (new StringBuilder()).append("ET MSG Received: ").append(s).toString());
            try
            {
                String s4 = s.substring(2);
                Mac mac = Mac.getInstance("HmacMD5");
                mac.init(new SecretKeySpec(carData.UserPass.getBytes(), "HmacMD5"));
                pmDigest = mac.doFinal(s4.getBytes());
                Log.d("OVMS", "Paranoid Mode Token Accepted. Entering Privacy Mode.");
            }
            catch(Exception exception1)
            {
                Log.d("ERR", exception1.getMessage());
                exception1.printStackTrace();
            }
_L2:
            Log.d("TCP", (new StringBuilder()).append(c).append(" MSG Received: ").append(s1).toString());
            c;
            JVM INSTR lookupswitch 9: default 236
        //                       68: 828
        //                       70: 1293
        //                       76: 764
        //                       83: 499
        //                       84: 690
        //                       87: 1418
        //                       90: 478
        //                       97: 1569
        //                       102: 1368;
               goto _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14
_L5:
            break; /* Loop/switch isn't completed */
_L13:
            break MISSING_BLOCK_LABEL_1569;
_L17:
            return;
_L4:
            if(c1 != 'M') goto _L2; else goto _L15
_L15:
            byte abyte0[];
            Log.d("TCP", (new StringBuilder()).append("EM MSG Received: ").append(s).toString());
            c = s.charAt(2);
            s1 = s.substring(3);
            abyte0 = Base64.decode(s1, 2);
            String s3;
            pmcipher = Cipher.getInstance("RC4");
            pmcipher.init(2, new SecretKeySpec(pmDigest, "RC4"));
            String s2 = "";
            for(int l = 0; l < 1024; l++)
                s2 = (new StringBuilder()).append(s2).append("0").toString();

            pmcipher.update(s2.getBytes());
            s3 = new String(pmcipher.update(abyte0));
            s1 = s3;
_L16:
            if(!carData.ParanoidMode)
            {
                Log.d("OVMS", "Paranoid Mode Detected");
                carData.ParanoidMode = true;
                UpdateStatus();
            }
              goto _L2
            Exception exception;
            exception;
            Log.d("ERR", exception.getMessage());
            exception.printStackTrace();
              goto _L16
_L12:
            carData.Data_CarsConnected = Integer.parseInt(s1);
            UpdateStatus();
              goto _L17
_L9:
            String as5[] = s1.split(",\\s*");
            if(as5.length >= 8)
            {
                Log.d("TCP", "S MSG Validated");
                carData.Data_SOC = Integer.parseInt(as5[0]);
                carData.Data_DistanceUnit = as5[1].toString();
                carData.Data_LineVoltage = Integer.parseInt(as5[2]);
                carData.Data_ChargeCurrent = Integer.parseInt(as5[3]);
                carData.Data_ChargeState = as5[4].toString();
                carData.Data_ChargeMode = as5[5].toString();
                carData.Data_IdealRange = Integer.parseInt(as5[6]);
                carData.Data_EstimatedRange = Integer.parseInt(as5[7]);
            }
            Log.d("TCP", (new StringBuilder()).append("Notify Vehicle Status Update: ").append(carData.VehicleID).toString());
            if(OVMSActivity.this != null)
                UpdateStatus();
              goto _L17
_L10:
            if(s1.length() > 0)
            {
                carData.Data_LastCarUpdate = new Date((new Date()).getTime() - 1000L * Long.parseLong(s1));
                carData.Data_LastCarUpdate_raw = Long.parseLong(s1);
                UpdateStatus();
            } else
            {
                Log.d("TCP", "T MSG Invalid");
            }
              goto _L17
_L8:
            String as4[] = s1.split(",\\s*");
            if(as4.length >= 2)
            {
                Log.d("TCP", "L MSG Validated");
                carData.Data_Latitude = Double.parseDouble(as4[0]);
                carData.Data_Longitude = Double.parseDouble(as4[1]);
                UpdateStatus();
            }
              goto _L17
_L6:
            String as3[] = s1.split(",\\s*");
            if(as3.length >= 9)
            {
                Log.d("TCP", "D MSG Validated");
                int i = Integer.parseInt(as3[0]);
                CarData cardata = carData;
                boolean flag;
                CarData cardata1;
                boolean flag1;
                CarData cardata2;
                boolean flag2;
                CarData cardata3;
                boolean flag3;
                CarData cardata4;
                boolean flag4;
                CarData cardata5;
                boolean flag5;
                CarData cardata6;
                boolean flag6;
                int j;
                CarData cardata7;
                boolean flag7;
                CarData cardata8;
                boolean flag8;
                int k;
                CarData cardata9;
                boolean flag9;
                if((i & 1) == 1)
                    flag = true;
                else
                    flag = false;
                cardata.Data_LeftDoorOpen = flag;
                cardata1 = carData;
                if((i & 2) == 2)
                    flag1 = true;
                else
                    flag1 = false;
                cardata1.Data_RightDoorOpen = flag1;
                cardata2 = carData;
                if((i & 4) == 4)
                    flag2 = true;
                else
                    flag2 = false;
                cardata2.Data_ChargePortOpen = flag2;
                cardata3 = carData;
                if((i & 8) == 8)
                    flag3 = true;
                else
                    flag3 = false;
                cardata3.Data_PilotPresent = flag3;
                cardata4 = carData;
                if((i & 0x10) == 16)
                    flag4 = true;
                else
                    flag4 = false;
                cardata4.Data_Charging = flag4;
                cardata5 = carData;
                if((i & 0x40) == 64)
                    flag5 = true;
                else
                    flag5 = false;
                cardata5.Data_HandBrakeApplied = flag5;
                cardata6 = carData;
                if((i & 0x80) == 128)
                    flag6 = true;
                else
                    flag6 = false;
                cardata6.Data_CarPoweredON = flag6;
                j = Integer.parseInt(as3[1]);
                cardata7 = carData;
                if((j & 0x40) == 64)
                    flag7 = true;
                else
                    flag7 = false;
                cardata7.Data_BonnetOpen = flag7;
                cardata8 = carData;
                if((j & 0x80) == 128)
                    flag8 = true;
                else
                    flag8 = false;
                cardata8.Data_TrunkOpen = flag8;
                k = Integer.parseInt(as3[2]);
                cardata9 = carData;
                if(k == 4)
                    flag9 = true;
                else
                    flag9 = false;
                cardata9.Data_CarLocked = flag9;
                carData.Data_TemperaturePEM = Double.parseDouble(as3[3]);
                carData.Data_TemperatureMotor = Double.parseDouble(as3[4]);
                carData.Data_TemperatureBattery = Double.parseDouble(as3[5]);
                carData.Data_TripMeter = Double.parseDouble(as3[6]);
                carData.Data_Odometer = Double.parseDouble(as3[7]);
                carData.Data_Speed = Double.parseDouble(as3[8]);
                UpdateStatus();
            }
              goto _L17
_L7:
            String as2[] = s1.split(",\\s*");
            if(as2.length >= 3)
            {
                Log.d("TCP", "F MSG Validated");
                carData.Data_CarModuleFirmwareVersion = as2[0].toString();
                carData.Data_VIN = as2[1].toString();
                carData.Data_CarModuleGSMSignalLevel = as2[2].toString();
                UpdateStatus();
            }
_L14:
            String as1[] = s1.split(",\\s*");
            if(as1.length >= 1)
            {
                Log.d("TCP", "f MSG Validated");
                carData.Data_OVMSServerFirmwareVersion = as1[0].toString();
                UpdateStatus();
            }
              goto _L17
_L11:
            String as[] = s1.split(",\\s*");
            if(as.length >= 8)
            {
                Log.d("TCP", "W MSG Validated");
                carData.Data_FRWheelPressure = Double.parseDouble(as[0]);
                carData.Data_FRWheelTemperature = Double.parseDouble(as[1]);
                carData.Data_RRWheelPressure = Double.parseDouble(as[2]);
                carData.Data_RRWheelTemperature = Double.parseDouble(as[3]);
                carData.Data_FLWheelPressure = Double.parseDouble(as[4]);
                carData.Data_FLWheelTemperature = Double.parseDouble(as[5]);
                carData.Data_RLWheelPressure = Double.parseDouble(as[6]);
                carData.Data_RLWheelTemperature = Double.parseDouble(as[7]);
                UpdateStatus();
            }
              goto _L17
            Log.d("TCP", "Server acknowleged ping");
              goto _L17
        }

        private String toHex(byte abyte0[])
        {
            BigInteger biginteger = new BigInteger(1, abyte0);
            String s = (new StringBuilder()).append("%0").append(abyte0.length << 1).append("X").toString();
            Object aobj[] = new Object[1];
            aobj[0] = biginteger;
            return String.format(s, aobj);
        }

        public void ConnClose()
        {
            if(Sock != null && Sock.isConnected())
                Sock.close();
_L1:
            return;
            Exception exception;
            exception;
            exception.printStackTrace();
              goto _L1
        }

        public void Ping()
        {
            Outputstream.println(Base64.encodeToString(txcipher.update("TX: MP-0 A".getBytes()), 2));
            Log.d("OVMS", "TX: MP-0 A");
        }

        public boolean SendCommand(String s)
        {
            Log.d("OVMS", (new StringBuilder()).append("TX: ").append(s).toString());
            boolean flag;
            if(!isLoggedIn)
            {
                Log.d("OVMS", "Server not ready. TX aborted.");
                flag = false;
            } else
            {
                try
                {
                    Outputstream.println(Base64.encodeToString(txcipher.update(s.getBytes()), 2));
                }
                catch(Exception exception)
                {
                    notifyServerSocketError(exception);
                }
                flag = true;
            }
            return flag;
        }

        protected volatile Object doInBackground(Object aobj[])
        {
            return doInBackground((Void[])aobj);
        }

        protected transient Void doInBackground(Void avoid[])
        {
            ConnInit();
_L1:
            if(!Sock.isConnected())
                break MISSING_BLOCK_LABEL_154;
            String s = Inputstream.readLine().trim();
            String s1 = (new String(rxcipher.update(Base64.decode(s, 0)))).trim();
            Object aobj[] = new Object[2];
            aobj[0] = s1;
            aobj[1] = s;
            Log.d("OVMS", String.format("RX: %s (%s)", aobj));
            if(!s1.substring(0, 5).equals("MP-0 "))
                break MISSING_BLOCK_LABEL_124;
            HandleMessage(s1.substring(5));
_L2:
            try
            {
                Thread.sleep(100L, 0);
            }
            catch(InterruptedException interruptedexception) { }
              goto _L1
            Log.d("OVMS", "Unknown protection scheme");
              goto _L2
            SocketException socketexception;
            socketexception;
            Exception exception;
            IOException ioexception;
            try
            {
                Sock.close();
                Sock = null;
            }
            catch(Exception exception1) { }
            ConnInit();
            return null;
            ioexception;
            if(!SuppressServerErrorDialog)
                notifyServerSocketError(ioexception);
            ioexception.printStackTrace();
            continue; /* Loop/switch isn't completed */
            exception;
            exception.printStackTrace();
            if(true) goto _L4; else goto _L3
_L3:
            break MISSING_BLOCK_LABEL_189;
_L4:
            break MISSING_BLOCK_LABEL_154;
        }

        protected transient void onProgressUpdate(Integer ainteger[])
        {
        }

        protected volatile void onProgressUpdate(Object aobj[])
        {
            onProgressUpdate((Integer[])aobj);
        }

        private BufferedReader Inputstream;
        private PrintWriter Outputstream;
        public Socket Sock;
        private CarData carData;
        private byte pmDigest[];
        private Cipher pmcipher;
        private Cipher rxcipher;
        final OVMSActivity this$0;
        private Cipher txcipher;

        public TCPTask(CarData cardata)
        {
            this$0 = OVMSActivity.this;
            super();
            carData = OVMSActivity.this.carData;
        }
    }


    public OVMSActivity()
    {
        c2dmReportTimerHandler = new Handler();
        pingServerTimerHandler = new Handler();
        SuppressServerErrorDialog = false;
        progressLogin = null;
        progressLoginCloseDialog = new Runnable() {

            public void run()
            {
                progressLogin.dismiss();
_L2:
                return;
                Exception exception;
                exception;
                if(true) goto _L2; else goto _L1
_L1:
            }

            final OVMSActivity this$0;

            
            {
                this$0 = OVMSActivity.this;
                super();
            }
        }
;
        progressLoginShowDialog = new Runnable() {

            public void run()
            {
                progressLogin = ProgressDialog.show(OVMSActivity.this, "", "Connecting to OVMS Server...");
            }

            final OVMSActivity this$0;

            
            {
                this$0 = OVMSActivity.this;
                super();
            }
        }
;
        serverSocketErrorDialog = new Runnable() {

            public void run()
            {
                if(!SuppressServerErrorDialog && (alertDialog == null || !alertDialog.isShowing()))
                {
                    if(progressLogin != null)
                        progressLogin.hide();
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OVMSActivity.this);
                    String s;
                    if(isLoggedIn)
                        s = String.format("OVMS Server Connection Lost", new Object[0]);
                    else
                        s = String.format("Please check the following:\n1. OVMS Server address\n2. Your vehicle ID and passwords", new Object[0]);
                    builder.setMessage(s).setTitle("Communications Problem").setCancelable(false).setPositiveButton("Open Settings", new android.content.DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                            getTabHost().setCurrentTabByTag("tabCars");
                        }

                        final _cls3 this$1;

                    
                    {
                        this$1 = _cls3.this;
                        super();
                    }
                    }
);
                    alertDialog = builder.create();
                    alertDialog.show();
                }
            }

            final OVMSActivity this$0;

            
            {
                this$0 = OVMSActivity.this;
                super();
            }
        }
;
        pingServer = new Runnable() {

            public void run()
            {
                if(isLoggedIn)
                {
                    Log.d("OVMS", "Pinging server...");
                    tcpTask.Ping();
                }
                pingServerTimerHandler.postDelayed(pingServer, 60000L);
            }

            final OVMSActivity this$0;

            
            {
                this$0 = OVMSActivity.this;
                super();
            }
        }
;
        reportC2DMRegistrationID = new Runnable() {

            public void run()
            {
                if(tcpTask != null) goto _L2; else goto _L1
_L1:
                return;
_L2:
                SharedPreferences sharedpreferences = getSharedPreferences("C2DM", 0);
                String s = sharedpreferences.getString("RegID", "");
                String s1;
                if(!sharedpreferences.contains("UUID"))
                {
                    s1 = UUID.randomUUID().toString();
                    android.content.SharedPreferences.Editor editor = getSharedPreferences("C2DM", 0).edit();
                    editor.putString("UUID", s1);
                    editor.commit();
                    Log.d("OVMS", (new StringBuilder()).append("Generated New C2DM UUID: ").append(s1).toString());
                } else
                {
                    s1 = sharedpreferences.getString("UUID", "");
                    Log.d("OVMS", (new StringBuilder()).append("Loaded Saved C2DM UUID: ").append(s1).toString());
                }
                if(s.length() != 0)
                {
                    TCPTask tcptask = tcpTask;
                    Object aobj[] = new Object[4];
                    aobj[0] = s1;
                    aobj[1] = carData.VehicleID;
                    aobj[2] = carData.CarPass;
                    aobj[3] = s;
                    if(tcptask.SendCommand(String.format("MP-0 p%s,c2dm,production,%s,%s,%s", aobj)))
                        continue; /* Loop/switch isn't completed */
                }
                Log.d("OVMS", "Reporting C2DM ID failed. Rescheduling.");
                c2dmReportTimerHandler.postDelayed(reportC2DMRegistrationID, 5000L);
                if(true) goto _L1; else goto _L3
_L3:
            }

            final OVMSActivity this$0;

            
            {
                this$0 = OVMSActivity.this;
                super();
            }
        }
;
    }

    private void loadCars()
    {
        String s;
        int i;
        Log.d("OVMS", "Loading saved cars from internal storage file: OVMSSavedCars.obj");
        ObjectInputStream objectinputstream = new ObjectInputStream(openFileInput("OVMSSavedCars.obj"));
        allSavedCars = (ArrayList)objectinputstream.readObject();
        objectinputstream.close();
        s = getSharedPreferences("OVMS", 0).getString("LastVehicleID", "").trim();
        if(s.length() == 0)
        {
            carData = (CarData)allSavedCars.get(0);
            break MISSING_BLOCK_LABEL_312;
        }
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(allSavedCars.size());
        aobj[1] = s;
        Log.d("OVMS", String.format("Loaded %s cars. Last used car is ", aobj));
        i = 0;
_L2:
        if(i < allSavedCars.size())
        {
            if(!((CarData)allSavedCars.get(i)).VehicleID.equals(s))
                break MISSING_BLOCK_LABEL_306;
            carData = (CarData)allSavedCars.get(i);
        }
        if(carData == null)
            carData = (CarData)allSavedCars.get(0);
        break MISSING_BLOCK_LABEL_312;
        Exception exception;
        exception;
        exception.printStackTrace();
        Log.d("ERR", exception.getMessage());
        Log.d("OVMS", "Invalid save file. Initializing with demo car.");
        allSavedCars = new ArrayList();
        CarData cardata = new CarData();
        cardata.VehicleID = "DEMO";
        cardata.CarPass = "DEMO";
        cardata.UserPass = "DEMO";
        cardata.ServerNameOrIP = "www.openvehicles.com";
        cardata.VehicleImageDrawable = "car_models_signaturered";
        allSavedCars.add(cardata);
        carData = cardata;
        saveCars();
        break MISSING_BLOCK_LABEL_312;
        i++;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void loginComplete()
    {
        isLoggedIn = true;
        runOnUiThread(progressLoginCloseDialog);
    }

    private void notifyServerSocketError(Exception exception)
    {
        lastServerException = exception;
        runOnUiThread(serverSocketErrorDialog);
    }

    private void notifyTabUpdate(String s)
    {
        Log.d("Tab", (new StringBuilder()).append("Tab change to: ").append(s).toString());
        if(s == "tabInfo")
            ((TabInfo)getLocalActivityManager().getActivity(s)).RefreshStatus(carData);
        else
        if(s == "tabCar")
        {
            Log.d("Tab", "Telling tabCar to update");
            ((TabCar)getLocalActivityManager().getActivity(s)).RefreshStatus(carData);
        } else
        if(s == "tabMap")
            ((TabMap)getLocalActivityManager().getActivity(s)).RefreshStatus(carData);
        else
        if(s == "tabNotifications")
            ((TabNotifications)getLocalActivityManager().getActivity(s)).Refresh();
        else
        if(s == "tabCars")
            ((TabCars)getLocalActivityManager().getActivity(s)).LoadCars(allSavedCars);
        else
            getTabHost().setCurrentTabByTag("tabInfo");
    }

    public void ChangeCar(CarData cardata)
    {
        runOnUiThread(progressLoginShowDialog);
        Log.d("OVMS", (new StringBuilder()).append("Changed car to: ").append(cardata.VehicleID).toString());
        isLoggedIn = false;
        if(tcpTask != null)
        {
            Log.d("TCP", "Shutting down pervious TCP connection (ChangeCar())");
            SuppressServerErrorDialog = true;
            tcpTask.ConnClose();
            tcpTask.cancel(true);
            tcpTask = null;
            SuppressServerErrorDialog = false;
        }
        carData = cardata;
        cardata.ParanoidMode = false;
        tcpTask = new TCPTask(carData);
        Log.d("TCP", "Starting TCP Connection (ChangeCar())");
        tcpTask.execute(new Void[0]);
        getTabHost().setCurrentTabByTag("tabInfo");
        UpdateStatus();
    }

    public void UpdateStatus()
    {
        Log.d("OVMS", "Status Update");
        notifyTabUpdate(getLocalActivityManager().getCurrentId());
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030002);
        loadCars();
        String s = getSharedPreferences("C2DM", 0).getString("RegID", "");
        TabHost tabhost;
        Intent intent;
        android.widget.TabHost.TabSpec tabspec;
        Intent intent1;
        android.widget.TabHost.TabSpec tabspec1;
        Intent intent2;
        android.widget.TabHost.TabSpec tabspec2;
        Intent intent3;
        android.widget.TabHost.TabSpec tabspec3;
        Intent intent4;
        android.widget.TabHost.TabSpec tabspec4;
        if(s.length() == 0)
        {
            Log.d("C2DM", "Doing first time registration.");
            ProgressDialog progressdialog = ProgressDialog.show(this, "Push Notification Network", "Sending one-time registration...");
            Intent intent5 = new Intent("com.google.android.c2dm.intent.REGISTER");
            intent5.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
            intent5.putExtra("sender", "openvehicles@gmail.com");
            startService(intent5);
            progressdialog.dismiss();
            c2dmReportTimerHandler.postDelayed(reportC2DMRegistrationID, 2000L);
        } else
        {
            Log.d("C2DM", (new StringBuilder()).append("Loaded Saved C2DM registration ID: ").append(s).toString());
            c2dmReportTimerHandler.postDelayed(reportC2DMRegistrationID, 2000L);
        }
        tabhost = getTabHost();
        intent = (new Intent()).setClass(this, com/openvehicles/OVMS/TabInfo);
        tabspec = tabhost.newTabSpec("tabInfo");
        tabspec.setContent(intent);
        tabspec.setIndicator("", getResources().getDrawable(0x1080041));
        tabhost.addTab(tabspec);
        intent1 = (new Intent()).setClass(this, com/openvehicles/OVMS/TabCar);
        tabspec1 = tabhost.newTabSpec("tabCar");
        tabspec1.setContent(intent1);
        tabspec1.setIndicator("", getResources().getDrawable(0x1080049));
        tabhost.addTab(tabspec1);
        intent2 = (new Intent()).setClass(this, com/openvehicles/OVMS/TabMap);
        tabspec2 = tabhost.newTabSpec("tabMap");
        tabspec2.setContent(intent2);
        tabspec2.setIndicator("", getResources().getDrawable(0x1080039));
        tabhost.addTab(tabspec2);
        intent3 = (new Intent()).setClass(this, com/openvehicles/OVMS/TabNotifications);
        tabspec3 = tabhost.newTabSpec("tabNotifications");
        tabspec3.setContent(intent3);
        tabspec3.setIndicator("", getResources().getDrawable(0x1080034));
        tabhost.addTab(tabspec3);
        intent4 = (new Intent()).setClass(this, com/openvehicles/OVMS/TabCars);
        tabspec4 = tabhost.newTabSpec("tabCars");
        tabspec4.setContent(intent4);
        tabspec4.setIndicator("", getResources().getDrawable(0x1080042));
        tabhost.addTab(tabspec4);
        getTabHost().setOnTabChangedListener(this);
        if(tabhost.getCurrentTabTag() == "")
            getTabHost().setCurrentTabByTag("tabInfo");
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f030003, menu);
        return true;
    }

    protected void onDestory()
    {
    }

    public void onNewIntent(Intent intent)
    {
        TabHost tabhost = getTabHost();
        if(intent != null && intent.hasExtra("SetTab"))
            tabhost.setCurrentTabByTag(intent.getStringExtra("SetTab"));
        else
            tabhost.setCurrentTabByTag("tabInfo");
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        boolean flag = true;
        menuitem.getItemId();
        JVM INSTR tableswitch 2131099659 2131099660: default 32
    //                   2131099659 40
    //                   2131099660 47;
           goto _L1 _L2 _L3
_L1:
        flag = super.onOptionsItemSelected(menuitem);
_L5:
        return flag;
_L2:
        finish();
        continue; /* Loop/switch isn't completed */
_L3:
        OVMSNotifications ovmsnotifications = new OVMSNotifications(this);
        ovmsnotifications.Notifications = new ArrayList();
        ovmsnotifications.Save();
        UpdateStatus();
        if(true) goto _L5; else goto _L4
_L4:
    }

    protected void onPause()
    {
        super.onPause();
        try
        {
            if(tcpTask != null)
            {
                Log.d("TCP", "Shutting down TCP connection");
                tcpTask.ConnClose();
                tcpTask.cancel(true);
                tcpTask = null;
            }
        }
        catch(Exception exception) { }
        saveCars();
    }

    protected void onResume()
    {
        super.onResume();
        ChangeCar(carData);
    }

    public void onTabChanged(String s)
    {
        notifyTabUpdate(s);
    }

    public void saveCars()
    {
        Log.d("OVMS", "Saving cars to interal storage...");
        android.content.SharedPreferences.Editor editor = getSharedPreferences("OVMS", 0).edit();
        editor.putString("LastVehicleID", carData.VehicleID);
        editor.commit();
        ObjectOutputStream objectoutputstream = new ObjectOutputStream(openFileOutput("OVMSSavedCars.obj", 0));
        objectoutputstream.writeObject(allSavedCars);
        objectoutputstream.close();
_L1:
        return;
        Exception exception;
        exception;
        exception.printStackTrace();
        Log.d("ERR", exception.getMessage());
          goto _L1
    }

    public boolean SuppressServerErrorDialog;
    private AlertDialog alertDialog;
    private ArrayList allSavedCars;
    private Handler c2dmReportTimerHandler;
    private CarData carData;
    private boolean isLoggedIn;
    private Exception lastServerException;
    private Runnable pingServer;
    private Handler pingServerTimerHandler;
    ProgressDialog progressLogin;
    private Runnable progressLoginCloseDialog;
    private Runnable progressLoginShowDialog;
    private Runnable reportC2DMRegistrationID;
    private Runnable serverSocketErrorDialog;
    private final String settingsFileName = "OVMSSavedCars.obj";
    private TCPTask tcpTask;



/*
    static AlertDialog access$002(OVMSActivity ovmsactivity, AlertDialog alertdialog)
    {
        ovmsactivity.alertDialog = alertdialog;
        return alertdialog;
    }

*/









}
