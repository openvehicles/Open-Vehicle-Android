// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.*;
import android.content.*;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.TabHost;
import android.widget.Toast;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// Referenced classes of package com.openvehicles.OVMS:
//            CarData, ServerCommands, GPRSUtilization, TabInfo_xlarge, 
//            TabMap, Tab_SubTabNotifications, Tab_SubTabDataUtilizations, Tab_SubTabCarSettings, 
//            TabCars, TabInfo, TabCar, TabMiscFeatures, 
//            OVMSNotifications, OVMSWidgets, HMAC, Base64, 
//            RC4, DataLog, CarData_Group

public class OVMSActivity extends TabActivity
    implements android.widget.TabHost.OnTabChangeListener
{
    private class ServerCommandResponseHandler
        implements Runnable
    {

        public void run()
        {
            Toast.makeText(OVMSActivity.this, message, 0).show();
        }

        String message;
        final OVMSActivity this$0;

        ServerCommandResponseHandler(String s)
        {
            this$0 = OVMSActivity.this;
            super();
            message = s;
        }
    }

    private class TCPTask extends AsyncTask
    {

        private void ConnInit()
        {
            String s;
            String s1;
            char ac[];
            Random random;
            String s2;
            int i;
            s = carData.NetPass;
            s1 = carData.VehicleID;
            ac = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
            random = new Random();
            s2 = "";
            i = 0;
_L7:
            if(i < 22) goto _L2; else goto _L1
_L1:
            byte abyte0[] = s2.getBytes();
            HMAC hmac;
            hmac = new HMAC("MD5", s.getBytes());
            hmac.update(abyte0);
            String s3 = Base64.encodeBytes(hmac.sign());
            Log.d("TCP", (new StringBuilder("Connecting ")).append(carData.ServerNameOrIP).toString());
            Sock = new Socket();
            Sock.setSoTimeout(10000);
            Sock.connect(new InetSocketAddress(carData.ServerNameOrIP, 6867), 5000);
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
            String as[] = Inputstream.readLine().trim().split("[ ]+");
            String s4;
            byte abyte2[];
            Object aobj2[] = new Object[4];
            aobj2[0] = as[0];
            aobj2[1] = as[1];
            aobj2[2] = as[2];
            aobj2[3] = as[3];
            Log.d("OVMS", String.format("RX: %s %s %s %s", aobj2));
            s4 = as[2];
            byte abyte1[] = s4.getBytes();
            abyte2 = Base64.decode(as[3]);
            hmac.clear();
            hmac.update(abyte1);
            if(Arrays.equals(hmac.sign(), abyte2)) goto _L4; else goto _L3
_L3:
            Object aobj5[] = new Object[2];
            aobj5[0] = Base64.encodeBytes(hmac.sign());
            aobj5[1] = as[3];
            Log.d("OVMS", String.format("Server authentication failed. Expected %s Got %s", aobj5));
_L9:
            String s6;
            int j;
            hmac.clear();
            String s5 = (new StringBuilder(String.valueOf(s4))).append(s2).toString();
            hmac.update(s5.getBytes());
            byte abyte3[] = hmac.sign();
            Object aobj3[] = new Object[3];
            aobj3[0] = s5;
            aobj3[1] = toHex(abyte3).toLowerCase();
            aobj3[2] = Base64.encodeBytes(abyte3);
            Log.d("OVMS", String.format("Client version of the shared key is %s - (%s) %s", aobj3));
            RC4 rc4 = new RC4(abyte3);
            rxcipher = rc4;
            RC4 rc4_1 = new RC4(abyte3);
            txcipher = rc4_1;
            s6 = "";
            j = 0;
_L10:
            if(j < 1024) goto _L6; else goto _L5
_L5:
            rxcipher.rc4(s6.getBytes());
            txcipher.rc4(s6.getBytes());
            Object aobj4[] = new Object[1];
            aobj4[0] = carData.ServerNameOrIP;
            Log.d("OVMS", String.format("Connected to %s. Ciphers initialized. Listening...", aobj4));
            loginComplete();
_L8:
            return;
_L2:
            s2 = (new StringBuilder(String.valueOf(s2))).append(ac[random.nextInt(-1 + ac.length)]).toString();
            i++;
              goto _L7
            Exception exception1;
            exception1;
            String s7;
            try
            {
                notifyServerSocketError(exception1);
            }
            catch(UnknownHostException unknownhostexception)
            {
                notifyServerSocketError(unknownhostexception);
            }
            catch(SocketTimeoutException sockettimeoutexception)
            {
                notifyServerSocketError(sockettimeoutexception);
            }
            catch(Exception exception)
            {
                notifyServerSocketError(exception);
            }
              goto _L8
_L4:
            Log.d("OVMS", "Server authentication OK.");
              goto _L9
_L6:
            s7 = (new StringBuilder(String.valueOf(s6))).append("0").toString();
            s6 = s7;
            j++;
              goto _L10
        }

        private void notifyCommandResponse(String s)
        {
            if(OVMSActivity.this != null)
            {
                mCommandResponse = new ServerCommandResponseHandler(s);
                UIHandler.post(mCommandResponse);
            }
        }

        private void processMessage(String s)
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
            Log.d("TCP", (new StringBuilder("ET MSG Received: ")).append(s).toString());
            try
            {
                String s11 = s.substring(2);
                HMAC hmac = new HMAC("MD5", carData.RegPass.getBytes());
                hmac.update(s11.getBytes());
                pmDigest = hmac.sign();
                Log.d("OVMS", (new StringBuilder("Paranoid Mode Token Accepted. Entering Privacy Mode. (pmDigest = ")).append(Base64.encodeBytes(pmDigest)).append(")").toString());
            }
            catch(Exception exception2)
            {
                Log.d("ERR", exception2.getMessage());
                exception2.printStackTrace();
            }
_L2:
            Log.d("TCP", (new StringBuilder(String.valueOf(c))).append(" MSG Received: ").append(s1).toString());
            com.openvehicles.OVMS.DataLog.Log((new StringBuilder("[RX] ")).append(c).append(s1).toString());
            c;
            JVM INSTR lookupswitch 12: default 300
        //                       67: 2370
        //                       68: 1045
        //                       70: 1755
        //                       76: 909
        //                       83: 542
        //                       84: 787
        //                       87: 1943
        //                       90: 524
        //                       97: 2358
        //                       99: 2370
        //                       102: 1897
        //                       103: 2119;
               goto _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14 _L6 _L15 _L16
_L5:
            return;
_L4:
            if(c1 != 'M') goto _L2; else goto _L17
_L17:
            Log.d("TCP", (new StringBuilder("EM MSG Received: ")).append(s).toString());
            c = s.charAt(2);
            s1 = s.substring(3);
            byte abyte0[];
            String s8;
            int j2;
            abyte0 = Base64.decode(s1);
            pmcipher = new RC4(pmDigest);
            s8 = "";
            j2 = 0;
_L20:
            if(j2 < 1024) goto _L19; else goto _L18
_L18:
            String s10;
            pmcipher.rc4(s8.getBytes());
            s10 = new String(pmcipher.rc4(abyte0));
            s1 = s10;
_L21:
            if(!carData.ParanoidMode)
            {
                Log.d("OVMS", "Paranoid Mode Detected");
                carData.ParanoidMode = true;
                refreshUI();
            }
              goto _L2
_L19:
            String s9 = (new StringBuilder(String.valueOf(s8))).append("0").toString();
            s8 = s9;
            j2++;
              goto _L20
            Exception exception1;
            exception1;
            Log.d("ERR", exception1.getMessage());
            exception1.printStackTrace();
              goto _L21
_L13:
            carData.Data_CarsConnected = Integer.parseInt(s1);
            refreshUI();
              goto _L5
_L10:
            String as10[] = s1.split(",\\s*");
            if(as10.length >= 8)
            {
                Log.d("TCP", "S MSG Validated");
                carData.Data_SOC = Integer.parseInt(as10[0]);
                carData.Data_DistanceUnit = as10[1].toString();
                carData.Data_LineVoltage = Integer.parseInt(as10[2]);
                carData.Data_ChargeCurrent = Integer.parseInt(as10[3]);
                carData.Data_ChargeState = as10[4].toString();
                carData.Data_ChargeMode = as10[5].toString();
                carData.Data_IdealRange = Integer.parseInt(as10[6]);
                carData.Data_EstimatedRange = Integer.parseInt(as10[7]);
            }
            if(as10.length >= 14)
            {
                carData.Data_ChargeAmpsLimit = Integer.parseInt(as10[8]);
                carData.Data_ChargerB4State = Integer.parseInt(as10[9]);
                carData.Data_ChargerKWHConsumed = Double.parseDouble(as10[10]);
                carData.Data_ChargeSubstate = Integer.parseInt(as10[11]);
                carData.Data_ChargeState_raw = Integer.parseInt(as10[12]);
                carData.Data_ChargeMode_raw = Integer.parseInt(as10[13]);
            }
            refreshUI();
              goto _L5
_L11:
            if(s1.length() > 0)
            {
                carData.Data_LastCarUpdate_raw = Long.parseLong(s1);
                carData.Data_LastCarUpdate = new Date((new Date()).getTime() - 1000L * carData.Data_LastCarUpdate_raw);
                if(carData.Data_ParkedTime_raw > 0.0D)
                    carData.Data_ParkedTime = new Date(carData.Data_LastCarUpdate.getTime() - 1000L * (long)carData.Data_ParkedTime_raw);
                refreshUI();
            } else
            {
                Log.d("TCP", "T MSG Invalid");
            }
              goto _L5
_L9:
            String as9[] = s1.split(",\\s*");
            if(as9.length >= 2)
            {
                Log.d("TCP", "L MSG Validated");
                carData.Data_Latitude = Double.parseDouble(as9[0]);
                carData.Data_Longitude = Double.parseDouble(as9[1]);
            }
            if(as9.length >= 6)
            {
                carData.Data_Direction = Double.parseDouble(as9[2]);
                carData.Data_Altitude = Double.parseDouble(as9[3]);
                carData.Data_GPSLocked = as9[4].trim().equals("1");
                carData.Data_GPSDataStale = as9[5].trim().equals("0");
            }
            refreshUI();
              goto _L5
_L7:
            String as8[] = s1.split(",\\s*");
            if(as8.length >= 9)
            {
                Log.d("TCP", "D MSG Validated");
                int k1 = Integer.parseInt(as8[0]);
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
                int l1;
                CarData cardata7;
                boolean flag7;
                CarData cardata8;
                boolean flag8;
                CarData cardata9;
                boolean flag9;
                CarData cardata10;
                boolean flag10;
                CarData cardata11;
                boolean flag11;
                int i2;
                CarData cardata12;
                boolean flag12;
                if((k1 & 1) > 0)
                    flag = true;
                else
                    flag = false;
                cardata.Data_LeftDoorOpen = flag;
                cardata1 = carData;
                if((k1 & 2) > 0)
                    flag1 = true;
                else
                    flag1 = false;
                cardata1.Data_RightDoorOpen = flag1;
                cardata2 = carData;
                if((k1 & 4) > 0)
                    flag2 = true;
                else
                    flag2 = false;
                cardata2.Data_ChargePortOpen = flag2;
                cardata3 = carData;
                if((k1 & 8) > 0)
                    flag3 = true;
                else
                    flag3 = false;
                cardata3.Data_PilotPresent = flag3;
                cardata4 = carData;
                if((k1 & 0x10) > 0)
                    flag4 = true;
                else
                    flag4 = false;
                cardata4.Data_Charging = flag4;
                cardata5 = carData;
                if((k1 & 0x40) > 0)
                    flag5 = true;
                else
                    flag5 = false;
                cardata5.Data_HandBrakeApplied = flag5;
                cardata6 = carData;
                if((k1 & 0x80) > 1)
                    flag6 = true;
                else
                    flag6 = false;
                cardata6.Data_CarPoweredON = flag6;
                l1 = Integer.parseInt(as8[1]);
                cardata7 = carData;
                if((l1 & 8) > 0)
                    flag7 = true;
                else
                    flag7 = false;
                cardata7.Data_PINLocked = flag7;
                cardata8 = carData;
                if((l1 & 0x10) > 0)
                    flag8 = true;
                else
                    flag8 = false;
                cardata8.Data_ValetON = flag8;
                cardata9 = carData;
                if((l1 & 0x20) > 0)
                    flag9 = true;
                else
                    flag9 = false;
                cardata9.Data_HeadlightsON = flag9;
                cardata10 = carData;
                if((l1 & 0x40) > 0)
                    flag10 = true;
                else
                    flag10 = false;
                cardata10.Data_BonnetOpen = flag10;
                cardata11 = carData;
                if((l1 & 0x80) > 0)
                    flag11 = true;
                else
                    flag11 = false;
                cardata11.Data_TrunkOpen = flag11;
                i2 = Integer.parseInt(as8[2]);
                cardata12 = carData;
                if(i2 == 4)
                    flag12 = true;
                else
                    flag12 = false;
                cardata12.Data_CarLocked = flag12;
                carData.Data_TemperaturePEM = Double.parseDouble(as8[3]);
                carData.Data_TemperatureMotor = Double.parseDouble(as8[4]);
                carData.Data_TemperatureBattery = Double.parseDouble(as8[5]);
                carData.Data_TripMeter = Double.parseDouble(as8[6]);
                carData.Data_Odometer = Double.parseDouble(as8[7]);
                carData.Data_Speed = Double.parseDouble(as8[8]);
                if(as8.length >= 10)
                {
                    carData.Data_ParkedTime_raw = Double.parseDouble(as8[9]);
                    if(carData.Data_LastCarUpdate == null)
                        carData.Data_ParkedTime = null;
                    else
                        carData.Data_ParkedTime = new Date(carData.Data_LastCarUpdate.getTime() - 1000L * (long)carData.Data_ParkedTime_raw);
                }
                if(as8.length >= 11)
                    carData.Data_TemperatureAmbient = Double.parseDouble(as8[10]);
                if(as8.length >= 14)
                {
                    carData.Data_CoolingPumpON_DoorState3 = as8[11].trim().equals("1");
                    carData.Data_PEM_Motor_Battery_TemperaturesDataStale = as8[12].trim().equals("0");
                    carData.Data_AmbientTemperatureDataStale = as8[13].trim().equals("0");
                }
                refreshUI();
            }
              goto _L5
_L8:
            String as7[] = s1.split(",\\s*");
            if(as7.length >= 3)
            {
                Log.d("TCP", "F MSG Validated");
                carData.Data_CarModuleFirmwareVersion = as7[0].toString();
                carData.Data_VIN = as7[1].toString();
                carData.Data_CarModuleGSMSignalLevel = as7[2].toString();
                if(as7.length >= 4)
                {
                    carData.Data_Features.put(Integer.valueOf(15), as7[3].toString());
                    carData.Data_CANWriteEnabled = as7[3].trim().equals("1");
                }
                if(as7.length >= 5)
                    carData.Data_CarType = as7[4].toString();
                refreshUI();
            }
_L15:
            String as6[] = s1.split(",\\s*");
            if(as6.length >= 1)
            {
                Log.d("TCP", "f MSG Validated");
                carData.Data_OVMSServerFirmwareVersion = as6[0].toString();
                refreshUI();
            }
              goto _L5
_L12:
            String as5[] = s1.split(",\\s*");
            if(as5.length >= 8)
            {
                Log.d("TCP", "W MSG Validated");
                carData.Data_FRWheelPressure = Double.parseDouble(as5[0]);
                carData.Data_FRWheelTemperature = Double.parseDouble(as5[1]);
                carData.Data_RRWheelPressure = Double.parseDouble(as5[2]);
                carData.Data_RRWheelTemperature = Double.parseDouble(as5[3]);
                carData.Data_FLWheelPressure = Double.parseDouble(as5[4]);
                carData.Data_FLWheelTemperature = Double.parseDouble(as5[5]);
                carData.Data_RLWheelPressure = Double.parseDouble(as5[6]);
                carData.Data_RLWheelTemperature = Double.parseDouble(as5[7]);
                if(as5.length >= 9)
                    carData.Data_TPMSDataStale = as5[8].trim().equals("0");
                refreshUI();
            }
              goto _L5
_L16:
            String as4[] = s1.split(",\\s*");
            if(as4.length >= 9)
            {
                Log.d("TCP", "g MSG Validated");
                CarData_Group cardata_group = new CarData_Group();
                cardata_group.VehicleID = as4[0];
                cardata_group.SOC = Double.parseDouble(as4[2]);
                cardata_group.Speed = Double.parseDouble(as4[3]);
                cardata_group.Direction = Double.parseDouble(as4[4]);
                cardata_group.Altitude = Double.parseDouble(as4[5]);
                cardata_group.GPSLocked = as4[6].trim().equals("1");
                cardata_group.GPSDataStale = as4[7].trim().equals("0");
                cardata_group.Latitude = Double.parseDouble(as4[8]);
                cardata_group.Longitude = Double.parseDouble(as4[9]);
                if(carData.Group == null)
                    carData.Group = new HashMap();
                CarData_Group cardata_group1 = (CarData_Group)carData.Group.get(cardata_group.VehicleID);
                if(cardata_group1 != null)
                    cardata_group.VehicleImageDrawable = cardata_group1.VehicleImageDrawable;
                carData.Group.put(as4[0], cardata_group);
                refreshUI();
            }
              goto _L5
_L14:
            Log.d("TCP", "Server acknowleged ping");
              goto _L5
_L6:
label0:
            {
                if(s1.length() != 0)
                    break label0;
                Log.d("TCP", (new StringBuilder(String.valueOf(c))).append(" MSG Code Invalid").toString());
            }
              goto _L5
            String s2 = "";
            if(s1.indexOf(',') <= 0) goto _L23; else goto _L22
_L22:
            int j;
            String s7;
            j = Integer.parseInt(s1.substring(0, s1.indexOf(',')));
            s7 = s1.substring(1 + s1.indexOf(','));
            s2 = s7;
_L28:
            j;
            JVM INSTR lookupswitch 3: default 2488
        //                       1: 2588
        //                       3: 2914
        //                       30: 3240;
               goto _L24 _L25 _L26 _L27
_L24:
            String as3[] = s2.split(",\\s*");
            Exception exception;
            int i;
            String as[];
            SimpleDateFormat simpledateformat;
            ParseException parseexception;
            NumberFormatException numberformatexception;
            Object aobj[];
            String as1[];
            Object aobj1[];
            Object aobj2[];
            String s3;
            int k;
            int l;
            StringBuilder stringbuilder;
            String s4;
            Object aobj3[];
            String as2[];
            Object aobj4[];
            Object aobj5[];
            String s5;
            int i1;
            int j1;
            StringBuilder stringbuilder1;
            String s6;
            Object aobj6[];
            if(as3[0].equals("0"))
            {
                Object aobj9[] = new Object[1];
                aobj9[0] = ServerCommands.toString(j);
                notifyCommandResponse(String.format("Server Acknowledged %s", aobj9));
            } else
            if(as3[0].equals("1"))
                if(as3.length > 1)
                {
                    Object aobj8[] = new Object[2];
                    aobj8[0] = ServerCommands.toString(j);
                    aobj8[1] = as3[1];
                    notifyCommandResponse(String.format("[ERROR] %s\n%s\nTry turning on CAN_WRITE in the settings tab.", aobj8));
                } else
                {
                    Object aobj7[] = new Object[1];
                    aobj7[0] = ServerCommands.toString(j);
                    notifyCommandResponse(String.format("[ERROR] %s\nTry turning on CAN_WRITE in the settings tab.", aobj7));
                }
              goto _L5
_L23:
            i = Integer.parseInt(s1);
            j = i;
              goto _L28
            exception;
            Log.d("TCP", (new StringBuilder("!!! ")).append(c).append(" message is invalid.").toString());
              goto _L5
_L25:
            as2 = s2.split(",");
            if(as2.length <= 4) goto _L30; else goto _L29
_L29:
            s5 = "";
            i1 = 3;
_L33:
            j1 = as2.length;
            if(i1 < j1) goto _L32; else goto _L31
_L31:
            aobj6 = new Object[2];
            aobj6[0] = as2[1];
            aobj6[1] = s5;
            Log.d("TCP", String.format("FEATURE %s = %s", aobj6));
            carData.Data_Features.put(Integer.valueOf(Integer.parseInt(as2[1])), s5);
_L34:
            if(Integer.parseInt(as2[1]) == -1 + Integer.parseInt(as2[2]))
            {
                carData.Data_Features_LastRefreshed = new Date();
                refreshUI();
            }
              goto _L5
_L32:
            stringbuilder1 = new StringBuilder(String.valueOf(s5));
            if(s5.length() > 0)
                s6 = ",";
            else
                s6 = "";
            s5 = stringbuilder1.append(s6).append(as2[i1]).toString();
            i1++;
              goto _L33
_L30:
            if(as2.length == 4)
            {
                aobj5 = new Object[2];
                aobj5[0] = as2[1];
                aobj5[1] = as2[3];
                Log.d("TCP", String.format("FEATURE %s = %s", aobj5));
                carData.Data_Features.put(Integer.valueOf(Integer.parseInt(as2[1])), as2[3]);
            } else
            if(as2.length >= 2)
            {
                aobj4 = new Object[1];
                aobj4[0] = as2[1];
                Log.d("TCP", String.format("FEATURE %s = EMPTY", aobj4));
                carData.Data_Features.put(Integer.valueOf(Integer.parseInt(as2[1])), "");
            }
              goto _L34
_L26:
            as1 = s2.split(",");
            if(as1.length <= 4) goto _L36; else goto _L35
_L35:
            s3 = "";
            k = 3;
_L39:
            l = as1.length;
            if(k < l) goto _L38; else goto _L37
_L37:
            aobj3 = new Object[2];
            aobj3[0] = as1[1];
            aobj3[1] = s3;
            Log.d("TCP", String.format("PARAMETER %s = %s", aobj3));
            carData.Data_Parameters.put(Integer.valueOf(Integer.parseInt(as1[1])), s3);
_L40:
            if(Integer.parseInt(as1[1]) == -1 + Integer.parseInt(as1[2]))
            {
                carData.Data_Parameters_LastRefreshed = new Date();
                refreshUI();
            }
              goto _L5
_L38:
            stringbuilder = new StringBuilder(String.valueOf(s3));
            if(s3.length() > 0)
                s4 = ",";
            else
                s4 = "";
            s3 = stringbuilder.append(s4).append(as1[k]).toString();
            k++;
              goto _L39
_L36:
            if(as1.length == 4)
            {
                aobj2 = new Object[2];
                aobj2[0] = as1[1];
                aobj2[1] = as1[3];
                Log.d("TCP", String.format("PARAMETER %s = %s", aobj2));
                carData.Data_Parameters.put(Integer.valueOf(Integer.parseInt(as1[1])), as1[3]);
            } else
            if(as1.length >= 2)
            {
                aobj1 = new Object[1];
                aobj1[0] = as1[1];
                Log.d("TCP", String.format("PARAMETER %s = EMPTY", aobj1));
                carData.Data_Parameters.put(Integer.valueOf(Integer.parseInt(as1[1])), "");
            }
              goto _L40
_L27:
            as = s2.split(",\\s*");
            if(as.length >= 3)
            {
                if(carData.Data_GPRSUtilization == null)
                    carData.Data_GPRSUtilization = new GPRSUtilization(OVMSActivity.this);
                if(as[1].equals("1"))
                    carData.Data_GPRSUtilization.Clear();
                simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
                try
                {
                    carData.Data_GPRSUtilization.AddData(simpledateformat.parse(as[3]), Long.parseLong(as[4]), Long.parseLong(as[5]), Long.parseLong(as[6]), Long.parseLong(as[7]));
                    aobj = new Object[7];
                    aobj[0] = as[1];
                    aobj[1] = as[2];
                    aobj[2] = simpledateformat.parse(as[3]).toLocaleString();
                    aobj[3] = as[4];
                    aobj[4] = as[5];
                    aobj[5] = as[6];
                    aobj[6] = as[7];
                    Log.d("TCP", String.format("GPRS UTIL [%s/%s] %s: car_rx %s car_tx %s app_rx %s app_tx %s", aobj));
                }
                // Misplaced declaration of an exception variable
                catch(NumberFormatException numberformatexception)
                {
                    numberformatexception.printStackTrace();
                }
                // Misplaced declaration of an exception variable
                catch(ParseException parseexception)
                {
                    parseexception.printStackTrace();
                }
                if(as[1].equals(as[2]))
                {
                    carData.Data_GPRSUtilization.LastDataRefresh = new Date();
                    carData.Data_GPRSUtilization.Save(OVMSActivity.this);
                    refreshUI();
                }
            }
              goto _L5
        }

        private void refreshUI()
        {
            if(OVMSActivity.this != null)
                UIHandler.post(mRefresh);
        }

        private String toHex(byte abyte0[])
        {
            BigInteger biginteger = new BigInteger(1, abyte0);
            String s = (new StringBuilder("%0")).append(abyte0.length << 1).append("X").toString();
            Object aobj[] = new Object[1];
            aobj[0] = biginteger;
            return String.format(s, aobj);
        }

        public void ConnClose()
        {
            socketMarkedClosed = true;
            SuppressServerErrorDialog = true;
            isLoggedIn = false;
            if(Sock != null)
                Sock.close();
            IOException ioexception;
            try
            {
                Thread.sleep(200L);
            }
            catch(InterruptedException interruptedexception) { }
            Sock = null;
            SuppressServerErrorDialog = false;
_L1:
            return;
            ioexception;
            ioexception.printStackTrace();
              goto _L1
        }

        public void Ping()
        {
            SendCommand("A");
        }

        public boolean SendCommand(String s)
        {
            if(isLoggedIn) goto _L2; else goto _L1
_L1:
            boolean flag;
            Log.d("TCP", "Server not ready. TX aborted.");
            flag = false;
_L10:
            return flag;
_L2:
            com.openvehicles.OVMS.DataLog.Log((new StringBuilder("[TX] ")).append(s).toString());
            if(!carData.ParanoidMode || s.startsWith("A") || s.startsWith("C") || s.startsWith("C30") || s.startsWith("p")) goto _L4; else goto _L3
_L3:
            String s3;
            int i;
            pmcipher = new RC4(pmDigest);
            s3 = "";
            i = 0;
_L7:
            if(i < 1024) goto _L6; else goto _L5
_L5:
            String s2;
            pmcipher.rc4(s3.getBytes());
            String s4 = Base64.encodeBytes(pmcipher.rc4(s.getBytes()));
            s2 = (new StringBuilder("MP-0 EM")).append(s4).toString();
            Log.d("TCP", (new StringBuilder("TX (Paranoid-Mode Command): ")).append(s2).append(" (using pmDigest: ").append(Base64.encodeBytes(pmDigest)).append(")").toString());
_L8:
            byte abyte0[] = txcipher.rc4(s2.getBytes());
            Log.d("TCP", (new StringBuilder("TX (Encrypted): ")).append(Base64.encodeBytes(abyte0)).toString());
            Outputstream.println(Base64.encodeBytes(abyte0));
            break MISSING_BLOCK_LABEL_342;
_L6:
            s3 = (new StringBuilder(String.valueOf(s3))).append("0").toString();
            i++;
              goto _L7
_L4:
            String s1 = (new StringBuilder("MP-0 ")).append(s).toString();
            s2 = s1;
              goto _L8
            Exception exception;
            exception;
            exception.printStackTrace();
            notifyServerSocketError(exception);
            flag = true;
            if(true) goto _L10; else goto _L9
_L9:
        }

        protected volatile transient Object doInBackground(Object aobj[])
        {
            return doInBackground((Void[])aobj);
        }

        protected transient Void doInBackground(Void avoid[])
        {
            Log.d("TCP", "Starting background TCP thread");
            SuppressServerErrorDialog = false;
            socketMarkedClosed = false;
            ConnInit();
            if(!isLoggedIn) goto _L2; else goto _L1
_L1:
            Log.d("TCP", "Background TCP ready");
            Sock.setSoTimeout(5000);
_L7:
            boolean flag = Sock.isConnected();
            if(flag) goto _L3; else goto _L2
_L2:
            if(Outputstream != null)
                Outputstream.close();
            Exception exception;
            String s;
            IOException ioexception;
            String s1;
            String s2;
            String s3;
            Object aobj[];
            boolean flag1;
            Exception exception3;
            String s4;
            try
            {
                if(Inputstream != null)
                    Inputstream.close();
            }
            catch(Exception exception1) { }
            try
            {
                if(Sock != null)
                    Sock.close();
            }
            catch(Exception exception2) { }
            Sock = null;
            Log.d("TCP", "TCP thread ending");
            return null;
_L3:
            s = "";
_L10:
            s4 = Inputstream.readLine();
            s = s4;
            if(s == null) goto _L5; else goto _L4
_L4:
            if(s == null) goto _L7; else goto _L6
_L6:
            if(s.length() <= 5) goto _L7; else goto _L8
_L8:
            s1 = s.trim();
            s2 = new String(rxcipher.rc4(Base64.decode(s1)));
            if(s2 == null || s2.length() <= 5) goto _L7; else goto _L9
_L9:
            s3 = s2.trim();
            aobj = new Object[2];
            aobj[0] = s3;
            aobj[1] = s1;
            Log.d("OVMS", String.format("RX: %s (%s)", aobj));
            flag1 = s3.substring(0, 5).equals("MP-0 ");
            if(!flag1)
                break MISSING_BLOCK_LABEL_355;
            processMessage(s3.substring(5));
              goto _L7
            exception3;
            com.openvehicles.OVMS.DataLog.Log((new StringBuilder("##ERROR## ")).append(exception3.getMessage()).append(" - ").append(s3).toString());
            exception3.printStackTrace();
              goto _L7
            exception;
            if(!socketMarkedClosed)
                notifyServerSocketError(exception);
              goto _L2
_L5:
            Thread.sleep(100L);
              goto _L10
            ioexception;
              goto _L4
            Log.d("OVMS", "Unknown protection scheme");
              goto _L7
        }

        protected transient void onProgressUpdate(Integer ainteger[])
        {
        }

        protected volatile transient void onProgressUpdate(Object aobj[])
        {
            onProgressUpdate((Integer[])aobj);
        }

        private BufferedReader Inputstream;
        private PrintWriter Outputstream;
        public Socket Sock;
        private CarData carData;
        private byte pmDigest[];
        private RC4 pmcipher;
        private RC4 rxcipher;
        private boolean socketMarkedClosed;
        final OVMSActivity this$0;
        private RC4 txcipher;

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
        UIHandler = new Handler();
        delayedRequest = new Handler();
        SuppressServerErrorDialog = false;
        progressLogin = null;
        mRecreateChildTabLayout = new Runnable() {

            public void run()
            {
                String s;
                s = getLocalActivityManager().getCurrentId().trim();
                Log.d("Tab", (new StringBuilder("Tab recreate: ")).append(s).toString());
                if(s != null && getLocalActivityManager().getActivity(s) != null) goto _L2; else goto _L1
_L1:
                return;
_L2:
                if(s.equals("tabInfo_xlarge"))
                {
                    TabInfo_xlarge tabinfo_xlarge = (TabInfo_xlarge)getLocalActivityManager().getActivity(s);
                    tabinfo_xlarge.OrientationChanged();
                    tabinfo_xlarge.Refresh(carData, isLoggedIn);
                } else
                if(s.equals("tabInfo"))
                {
                    TabInfo tabinfo = (TabInfo)getLocalActivityManager().getActivity(s);
                    tabinfo.OrientationChanged();
                    tabinfo.Refresh(carData, isLoggedIn);
                } else
                if(s.equals("tabCar"))
                {
                    TabCar tabcar = (TabCar)getLocalActivityManager().getActivity(s);
                    tabcar.OrientationChanged();
                    tabcar.Refresh(carData, isLoggedIn);
                }
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
        mRefresh = new Runnable() {

            private void notifyTabRefresh(String s)
            {
                Log.d("Tab", (new StringBuilder("Tab refresh: ")).append(s).toString());
                if(s != null && getLocalActivityManager().getActivity(s) != null)
                {
                    if(DeviceScreenSize == 4)
                    {
                        if(s.equals("tabInfo_xlarge"))
                        {
                            TabInfo_xlarge tabinfo_xlarge = (TabInfo_xlarge)getLocalActivityManager().getActivity(s);
                            if(tabinfo_xlarge.CurrentScreenOrientation != getResources().getConfiguration().orientation)
                                tabinfo_xlarge.OrientationChanged();
                            tabinfo_xlarge.Refresh(carData, isLoggedIn);
                        } else
                        if(s.equals("tabMap"))
                            ((TabMap)getLocalActivityManager().getActivity(s)).Refresh(carData, isLoggedIn);
                        else
                        if(s.equals("tabNotifications"))
                            ((Tab_SubTabNotifications)getLocalActivityManager().getActivity(s)).Refresh(carData, isLoggedIn);
                        else
                        if(s.equals("tabDataUtilizations"))
                            ((Tab_SubTabDataUtilizations)getLocalActivityManager().getActivity(s)).Refresh(carData, isLoggedIn);
                        else
                        if(s.equals("tabCarSettings"))
                            ((Tab_SubTabCarSettings)getLocalActivityManager().getActivity(s)).Refresh(carData, isLoggedIn);
                        else
                        if(s.equals("tabCars"))
                            ((TabCars)getLocalActivityManager().getActivity(s)).LoadCars(allSavedCars);
                    } else
                    if(s.equals("tabInfo"))
                    {
                        TabInfo tabinfo = (TabInfo)getLocalActivityManager().getActivity(s);
                        if(tabinfo.CurrentScreenOrientation != getResources().getConfiguration().orientation)
                            tabinfo.OrientationChanged();
                        tabinfo.Refresh(carData, isLoggedIn);
                    } else
                    if(s.equals("tabCar"))
                    {
                        TabCar tabcar = (TabCar)getLocalActivityManager().getActivity(s);
                        if(tabcar.CurrentScreenOrientation != getResources().getConfiguration().orientation)
                            tabcar.OrientationChanged();
                        tabcar.Refresh(carData, isLoggedIn);
                    } else
                    if(s.equals("tabMap"))
                        ((TabMap)getLocalActivityManager().getActivity(s)).Refresh(carData, isLoggedIn);
                    else
                    if(s.equals("tabMiscFeatures"))
                        ((TabMiscFeatures)getLocalActivityManager().getActivity(s)).Refresh(carData, isLoggedIn);
                    else
                    if(s.equals("tabCars"))
                        ((TabCars)getLocalActivityManager().getActivity(s)).LoadCars(allSavedCars);
                    else
                        getTabHost().setCurrentTab(0);
                    getTabHost().invalidate();
                }
            }

            public void run()
            {
                if(isLoggedIn)
                {
                    if(progressLogin != null && progressLogin.isShowing())
                        progressLogin.dismiss();
                    if(alertDialog != null && alertDialog.isShowing())
                        alertDialog.dismiss();
                }
                notifyTabRefresh(getLocalActivityManager().getCurrentId().trim());
            }

            final OVMSActivity this$0;

            
            {
                this$0 = OVMSActivity.this;
                super();
            }
        }
;
        progressLoginCloseDialog = new Runnable() {

            public void run()
            {
                if(progressLogin != null)
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
                Exception exception2;
                try
                {
                    if(progressLogin != null)
                        progressLogin.dismiss();
                }
                catch(Exception exception) { }
                try
                {
                    if(alertDialog != null)
                        alertDialog.dismiss();
                }
                catch(Exception exception1) { }
                progressLogin = new ProgressDialog(OVMSActivity.this);
                progressLogin.setIndeterminate(true);
                progressLogin.setMessage("Connecting to OVMS Server...");
                progressLogin.getWindow().clearFlags(2);
                progressLogin.show();
_L2:
                return;
                exception2;
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
        serverSocketErrorDialog = new Runnable() {

            public void run()
            {
                if(!SuppressServerErrorDialog && (alertDialog == null || !alertDialog.isShowing()))
                {
                    android.app.AlertDialog.Builder builder;
                    String s;
                    try
                    {
                        if(progressLogin != null)
                            progressLogin.dismiss();
                    }
                    catch(Exception exception) { }
                    builder = new android.app.AlertDialog.Builder(OVMSActivity.this);
                    if(isLoggedIn)
                        s = String.format("OVMS Server Connection Lost", new Object[0]);
                    else
                        s = String.format("Please check the following:\n1. OVMS Server address\n2. Your vehicle ID and passwords", new Object[0]);
                    builder.setMessage(s).setTitle("Connection Problem").setCancelable(false).setPositiveButton("Retry", new android.content.DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                            ChangeCar(carData);
                            dialoginterface.dismiss();
                        }

                        final _cls5 this$1;

                    
                    {
                        this$1 = _cls5.this;
                        super();
                    }
                    }
).setNegativeButton("Open Settings", new android.content.DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                            getTabHost().setCurrentTabByTag("tabCars");
                            dialoginterface.dismiss();
                        }

                        final _cls5 this$1;

                    
                    {
                        this$1 = _cls5.this;
                        super();
                    }
                    }
);
                    alertDialog = builder.create();
                    try
                    {
                        alertDialog.show();
                    }
                    catch(Exception exception1) { }
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
                    Log.d("OVMS", (new StringBuilder("Generated New App ID: ")).append(s1).toString());
                } else
                {
                    s1 = sharedpreferences.getString("UUID", "");
                    Log.d("OVMS", (new StringBuilder("Loaded Saved App ID: ")).append(s1).toString());
                }
                if(s.length() == 0)
                {
                    Log.d("C2DM", "C2DM registration ID not found. Rescheduling.");
                    c2dmReportTimerHandler.postDelayed(reportC2DMRegistrationID, 15000L);
                } else
                if(!SendServerCommand(ServerCommands.SUBSCRIBE_PUSH_NOTIFICATIONS(s1, carData.VehicleID, carData.NetPass, s.trim())))
                {
                    Log.d("OVMS", "Reporting C2DM ID failed. Rescheduling.");
                    c2dmReportTimerHandler.postDelayed(reportC2DMRegistrationID, 5000L);
                }
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

    private void initializeSavedCars()
    {
        Log.d("OVMS", "Invalid save file. Initializing with demo car.");
        allSavedCars = new ArrayList();
        CarData cardata = new CarData();
        cardata.VehicleID = "DEMO";
        cardata.RegPass = "DEMO";
        cardata.NetPass = "DEMO";
        cardata.ServerNameOrIP = "tmc.openvehicles.com";
        cardata.VehicleImageDrawable = "car_models_signaturered";
        cardata.lastResetVersion = 1;
        allSavedCars.add(cardata);
        carData = cardata;
        saveCars();
    }

    private void loadCars()
    {
        Iterator iterator;
        Log.d("OVMS", "Loading saved cars from internal storage file: OVMSSavedCars.obj");
        ObjectInputStream objectinputstream = new ObjectInputStream(openFileInput("OVMSSavedCars.obj"));
        allSavedCars = (ArrayList)objectinputstream.readObject();
        objectinputstream.close();
        iterator = allSavedCars.iterator();
_L3:
        CarData cardata;
        String s;
        if(!iterator.hasNext())
        {
            s = getSharedPreferences("OVMS", 0).getString("LastVehicleID", "").trim();
            if(s.length() == 0)
            {
                carData = (CarData)allSavedCars.get(0);
                break MISSING_BLOCK_LABEL_399;
            }
            break MISSING_BLOCK_LABEL_271;
        }
        cardata = (CarData)iterator.next();
        if(cardata.VehicleID != null && cardata.RegPass != null && cardata.NetPass != null && cardata.ServerNameOrIP != null && cardata.VehicleImageDrawable != null) goto _L2; else goto _L1
_L1:
        initializeSavedCars();
          goto _L3
        Exception exception;
        exception;
        exception.printStackTrace();
        initializeSavedCars();
        break MISSING_BLOCK_LABEL_399;
_L2:
        if(cardata.lastResetVersion == 1) goto _L3; else goto _L4
_L4:
        CarData cardata1 = new CarData();
        cardata1.VehicleID = cardata.VehicleID;
        cardata1.RegPass = cardata.RegPass;
        cardata1.NetPass = cardata.NetPass;
        cardata1.ServerNameOrIP = cardata.ServerNameOrIP;
        cardata1.VehicleImageDrawable = cardata.VehicleImageDrawable;
        cardata1.lastResetVersion = 1;
        allSavedCars.set(allSavedCars.indexOf(cardata), cardata1);
          goto _L3
        int i;
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(allSavedCars.size());
        aobj[1] = s;
        Log.d("OVMS", String.format("Loaded %s cars. Last used car is %s", aobj));
        i = 0;
_L8:
        if(i < allSavedCars.size()) goto _L6; else goto _L5
_L5:
        if(carData == null)
            carData = (CarData)allSavedCars.get(0);
        break MISSING_BLOCK_LABEL_399;
_L6:
        if(!((CarData)allSavedCars.get(i)).VehicleID.equals(s))
            break; /* Loop/switch isn't completed */
        carData = (CarData)allSavedCars.get(i);
        if(true) goto _L5; else goto _L7
_L7:
        i++;
          goto _L8
    }

    private void loginComplete()
    {
        isLoggedIn = true;
        UIHandler.post(progressLoginCloseDialog);
        ReportC2DMRegistrationID();
        if(((String)carData.Data_Parameters.get(Integer.valueOf(11))).length() > 0)
        {
            SendServerCommand(ServerCommands.SUBSCRIBE_GROUP((String)carData.Data_Parameters.get(Integer.valueOf(11))));
        } else
        {
            SendServerCommand("C3");
            Runnable runnable = new Runnable() {

                public void run()
                {
                    SendServerCommand("C1");
                }

                final OVMSActivity this$0;

            
            {
                this$0 = OVMSActivity.this;
                super();
            }
            }
;
            delayedRequest.postDelayed(runnable, 200L);
        }
    }

    private void notifyServerSocketError(Exception exception)
    {
        lastServerException = exception;
        if(exception != null)
            exception.printStackTrace();
        if(!SuppressServerErrorDialog)
            UIHandler.post(serverSocketErrorDialog);
    }

    private void notifyTabRefresh()
    {
        UIHandler.post(mRefresh);
    }

    public void ChangeCar(CarData cardata)
    {
        ChangeCar(cardata, "tabInfo");
    }

    public void ChangeCar(CarData cardata, String s)
    {
        UIHandler.post(progressLoginShowDialog);
        Log.d("OVMS", (new StringBuilder("Changed car to: ")).append(cardata.VehicleID).toString());
        isLoggedIn = false;
        if(tcpTask != null)
        {
            Log.d("TCP", "Shutting down pervious TCP connection (ChangeCar())");
            tcpTask.ConnClose();
            tcpTask.cancel(true);
            tcpTask = null;
        }
        carData = cardata;
        if(carData.Data_GPRSUtilization == null)
            carData.Data_GPRSUtilization = new GPRSUtilization(this);
        notifyTabRefresh();
        cardata.ParanoidMode = false;
        tcpTask = new TCPTask(carData);
        Log.d("TCP", "Starting TCP Connection (ChangeCar())");
        tcpTask.execute(new Void[0]);
        getTabHost().setCurrentTabByTag(s);
    }

    public void ReportC2DMRegistrationID()
    {
        c2dmReportTimerHandler.postDelayed(reportC2DMRegistrationID, 500L);
    }

    public boolean SendServerCommand(String s)
    {
        return tcpTask.SendCommand(s);
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        super.onConfigurationChanged(configuration);
        UIHandler.post(mRecreateChildTabLayout);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030002);
        loadCars();
        String s = getSharedPreferences("C2DM", 0).getString("RegID", "");
        Display display;
        Object aobj[];
        TabHost tabhost;
        if(s.length() == 0)
        {
            Log.d("C2DM", "Doing first time registration.");
            ServerCommands.RequestC2DMRegistrationID(this);
        } else
        {
            Log.d("C2DM", (new StringBuilder("Loaded Saved C2DM registration ID: ")).append(s).toString());
        }
        DeviceScreenSize = 0xf & getResources().getConfiguration().screenLayout;
        display = ((WindowManager)getSystemService("window")).getDefaultDisplay();
        aobj = new Object[2];
        aobj[0] = Integer.valueOf(display.getWidth());
        aobj[1] = Integer.valueOf(display.getHeight());
        Log.d("INIT", String.format("Screen size: %d x %d", aobj));
        if(display.getWidth() >= 976 || display.getHeight() >= 976)
            DeviceScreenSize = 4;
        tabhost = getTabHost();
        if(DeviceScreenSize == 4)
        {
            Intent intent5 = (new Intent()).setClass(this, com/openvehicles/OVMS/TabInfo_xlarge);
            android.widget.TabHost.TabSpec tabspec5 = tabhost.newTabSpec("tabInfo_xlarge");
            tabspec5.setContent(intent5);
            tabspec5.setIndicator("", getResources().getDrawable(0x7f020045));
            tabhost.addTab(tabspec5);
            Intent intent6 = (new Intent()).setClass(this, com/openvehicles/OVMS/TabMap);
            android.widget.TabHost.TabSpec tabspec6 = tabhost.newTabSpec("tabMap");
            tabspec6.setContent(intent6);
            tabspec6.setIndicator("", getResources().getDrawable(0x7f02004b));
            tabhost.addTab(tabspec6);
            Intent intent7 = (new Intent()).setClass(this, com/openvehicles/OVMS/Tab_SubTabNotifications);
            android.widget.TabHost.TabSpec tabspec7 = tabhost.newTabSpec("tabNotifications");
            tabspec7.setContent(intent7);
            tabspec7.setIndicator("", getResources().getDrawable(0x7f020055));
            tabhost.addTab(tabspec7);
            Intent intent8 = (new Intent()).setClass(this, com/openvehicles/OVMS/Tab_SubTabDataUtilizations);
            android.widget.TabHost.TabSpec tabspec8 = tabhost.newTabSpec("tabDataUtilizations");
            tabspec8.setContent(intent8);
            tabspec8.setIndicator("", getResources().getDrawable(0x7f020049));
            tabhost.addTab(tabspec8);
            Intent intent9 = (new Intent()).setClass(this, com/openvehicles/OVMS/Tab_SubTabCarSettings);
            android.widget.TabHost.TabSpec tabspec9 = tabhost.newTabSpec("tabCarSettings");
            tabspec9.setContent(intent9);
            tabspec9.setIndicator("", getResources().getDrawable(0x7f02004e));
            tabhost.addTab(tabspec9);
            Intent intent10 = (new Intent()).setClass(this, com/openvehicles/OVMS/TabCars);
            android.widget.TabHost.TabSpec tabspec10 = tabhost.newTabSpec("tabCars");
            tabspec10.setContent(intent10);
            tabspec10.setIndicator("", getResources().getDrawable(0x7f02005a));
            tabhost.addTab(tabspec10);
        } else
        {
            Intent intent = (new Intent()).setClass(this, com/openvehicles/OVMS/TabInfo);
            android.widget.TabHost.TabSpec tabspec = tabhost.newTabSpec("tabInfo");
            tabspec.setContent(intent);
            tabspec.setIndicator("", getResources().getDrawable(0x7f020045));
            tabhost.addTab(tabspec);
            Intent intent1 = (new Intent()).setClass(this, com/openvehicles/OVMS/TabCar);
            android.widget.TabHost.TabSpec tabspec1 = tabhost.newTabSpec("tabCar");
            tabspec1.setContent(intent1);
            tabspec1.setIndicator("", getResources().getDrawable(0x7f020046));
            tabhost.addTab(tabspec1);
            Intent intent2 = (new Intent()).setClass(this, com/openvehicles/OVMS/TabMap);
            android.widget.TabHost.TabSpec tabspec2 = tabhost.newTabSpec("tabMap");
            tabspec2.setContent(intent2);
            tabspec2.setIndicator("", getResources().getDrawable(0x7f02004b));
            tabhost.addTab(tabspec2);
            Intent intent3 = (new Intent()).setClass(this, com/openvehicles/OVMS/TabMiscFeatures);
            android.widget.TabHost.TabSpec tabspec3 = tabhost.newTabSpec("tabMiscFeatures");
            tabspec3.setContent(intent3);
            tabspec3.setIndicator("", getResources().getDrawable(0x7f020054));
            tabhost.addTab(tabspec3);
            Intent intent4 = (new Intent()).setClass(this, com/openvehicles/OVMS/TabCars);
            android.widget.TabHost.TabSpec tabspec4 = tabhost.newTabSpec("tabCars");
            tabspec4.setContent(intent4);
            tabspec4.setIndicator("", getResources().getDrawable(0x7f02005a));
            tabhost.addTab(tabspec4);
        }
        getTabHost().setOnTabChangedListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f030003, menu);
        return true;
    }

    protected void onDestory()
    {
        if(tcpTask != null)
        {
            Log.d("TCP", "Shutting down TCP connection (OnDestroy())");
            tcpTask.ConnClose();
            tcpTask = null;
        }
    }

    public void onNewIntent(Intent intent)
    {
        TabHost tabhost;
        Log.d("EVENT", "onNewIntent");
        tabhost = getTabHost();
        if(intent == null) goto _L2; else goto _L1
_L1:
        CarData cardata;
        Iterator iterator;
        if(!intent.hasExtra("VehicleID"))
            break MISSING_BLOCK_LABEL_154;
        cardata = null;
        iterator = allSavedCars.iterator();
_L6:
        if(iterator.hasNext()) goto _L4; else goto _L3
_L3:
        if(cardata != null)
        {
            Log.d("EVENT", (new StringBuilder("Launching with default car set to: ")).append(cardata.VehicleID).toString());
            CarData cardata1;
            if(intent.hasExtra("SetTab"))
                ChangeCar(cardata, intent.getStringExtra("SetTab"));
            else
                ChangeCar(cardata);
        }
_L2:
        return;
_L4:
        cardata1 = (CarData)iterator.next();
        if(!cardata1.VehicleID.equals(intent.getStringExtra("VehicleID"))) goto _L6; else goto _L5
_L5:
        cardata = cardata1;
          goto _L3
        if(intent.hasExtra("SetTab"))
            tabhost.setCurrentTabByTag(intent.getStringExtra("SetTab"));
        else
            tabhost.setCurrentTabByTag("tabInfo");
          goto _L2
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        boolean flag = true;
        menuitem.getItemId();
        JVM INSTR tableswitch 2131296266 2131296267: default 32
    //                   2131296266 40
    //                   2131296267 47;
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
        ovmsnotifications.Clear();
        ovmsnotifications.Save();
        notifyTabRefresh();
        if(true) goto _L5; else goto _L4
_L4:
    }

    protected void onPause()
    {
        super.onPause();
        if(tcpTask != null)
        {
            Log.d("TCP", "Shutting down TCP connection (OnPause())");
            tcpTask.ConnClose();
            tcpTask.cancel(true);
            tcpTask = null;
        }
        saveCars();
        OVMSWidgets.UpdateWidgets(this);
    }

    protected void onResume()
    {
        super.onResume();
        if(tcpTask == null)
        {
            UIHandler.post(progressLoginCloseDialog);
            String s = getTabHost().getCurrentTabTag();
            ChangeCar(carData, s);
        }
    }

    public void onTabChanged(String s)
    {
        UIHandler.post(mRefresh);
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
          goto _L1
    }

    public int DeviceScreenSize;
    public final int OVMS_CONFIG_FILE_VERSION = 1;
    public final int SCREENLAYOUT_SIZE_LARGE = 3;
    public final int SCREENLAYOUT_SIZE_XLARGE = 4;
    public boolean SuppressServerErrorDialog;
    private Handler UIHandler;
    private AlertDialog alertDialog;
    private ArrayList allSavedCars;
    private Handler c2dmReportTimerHandler;
    private CarData carData;
    private Handler delayedRequest;
    private boolean isLoggedIn;
    private Exception lastServerException;
    private ServerCommandResponseHandler mCommandResponse;
    private Runnable mRecreateChildTabLayout;
    private Runnable mRefresh;
    private Runnable pingServer;
    private Handler pingServerTimerHandler;
    private ProgressDialog progressLogin;
    private Runnable progressLoginCloseDialog;
    private Runnable progressLoginShowDialog;
    private Runnable reportC2DMRegistrationID;
    private Runnable serverSocketErrorDialog;
    private final String settingsFileName = "OVMSSavedCars.obj";
    private TCPTask tcpTask;



















}
