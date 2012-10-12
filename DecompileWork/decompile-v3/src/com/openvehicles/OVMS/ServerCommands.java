// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.*;
import android.content.*;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

// Referenced classes of package com.openvehicles.OVMS:
//            OVMSActivity

public final class ServerCommands
{
    public static class CarLayoutDownloader extends AsyncTask
    {

        protected transient Boolean doInBackground(String as[])
        {
            if(as.length >= 2) goto _L2; else goto _L1
_L1:
            Boolean boolean1;
            Log.d("TCP", "!!! Car Layout Download Error: params incorrect !!!");
            boolean1 = Boolean.valueOf(false);
_L10:
            return boolean1;
_L2:
            int i;
            BufferedInputStream bufferedinputstream;
            FileOutputStream fileoutputstream;
            byte abyte0[];
            long l;
            Object aobj[] = new Object[2];
            aobj[0] = "http://www.openvehicles.com/resources";
            aobj[1] = as[0];
            URL url = new URL(String.format("%s/ol_%s.png", aobj));
            URLConnection urlconnection = url.openConnection();
            urlconnection.setConnectTimeout(5000);
            urlconnection.setReadTimeout(5000);
            urlconnection.connect();
            i = urlconnection.getContentLength();
            bufferedinputstream = new BufferedInputStream(url.openStream());
            Object aobj1[] = new Object[2];
            aobj1[0] = as[1];
            aobj1[1] = as[0];
            fileoutputstream = new FileOutputStream(String.format("%s/ol_%s.png", aobj1), false);
            abyte0 = new byte[1024];
            l = 0L;
_L7:
            int j = bufferedinputstream.read(abyte0);
            if(j != -1) goto _L4; else goto _L3
_L3:
            fileoutputstream.flush();
            fileoutputstream.close();
            bufferedinputstream.close();
            URL url1;
            Object aobj2[] = new Object[2];
            aobj2[0] = "http://www.openvehicles.com/resources";
            aobj2[1] = as[0];
            url1 = new URL(String.format("%s/%s.png", aobj2));
            int k;
            BufferedInputStream bufferedinputstream1;
            URLConnection urlconnection1 = url1.openConnection();
            urlconnection1.setConnectTimeout(5000);
            urlconnection1.setReadTimeout(5000);
            urlconnection1.connect();
            k = urlconnection1.getContentLength();
            bufferedinputstream1 = new BufferedInputStream(url1.openStream());
            FileOutputStream fileoutputstream1;
            Object aobj3[] = new Object[2];
            aobj3[0] = as[1];
            aobj3[1] = as[0];
            fileoutputstream1 = new FileOutputStream(String.format("%s/%s.png", aobj3), false);
            byte abyte1[];
            long l1;
            abyte1 = new byte[1024];
            l1 = 0L;
_L8:
            int i1 = bufferedinputstream1.read(abyte1);
            if(i1 != -1) goto _L6; else goto _L5
_L5:
            fileoutputstream1.flush();
            fileoutputstream1.close();
            bufferedinputstream1.close();
            boolean1 = Boolean.valueOf(true);
            continue; /* Loop/switch isn't completed */
_L4:
            l += j;
            try
            {
                Integer ainteger[] = new Integer[1];
                ainteger[0] = Integer.valueOf((int)((100D * (double)l) / (double)i));
                publishProgress(ainteger);
                fileoutputstream.write(abyte0, 0, j);
            }
            catch(Exception exception)
            {
                Log.d("TCP", "!!! Car Layout Download Error !!!");
                exception.printStackTrace();
                boolean1 = Boolean.valueOf(false);
                continue; /* Loop/switch isn't completed */
            }
              goto _L7
_L6:
            l1 += i1;
            Integer ainteger1[] = new Integer[1];
            ainteger1[0] = Integer.valueOf((int)((100D * (double)l1) / (double)k));
            publishProgress(ainteger1);
            fileoutputstream1.write(abyte1, 0, i1);
              goto _L8
            Exception exception1;
            exception1;
_L11:
            Log.d("TCP", "!!! Car Layout Download Error !!!");
            exception1.printStackTrace();
            boolean1 = Boolean.valueOf(false);
            if(true) goto _L10; else goto _L9
_L9:
            exception1;
              goto _L11
            exception1;
              goto _L11
            exception1;
              goto _L11
        }

        protected volatile transient Object doInBackground(Object aobj[])
        {
            return doInBackground((String[])aobj);
        }

        protected void onCancelled()
        {
            mProgressDialog.dismiss();
        }

        protected void onPostExecute(Boolean boolean1)
        {
            mProgressDialog.dismiss();
        }

        protected volatile void onPostExecute(Object obj)
        {
            onPostExecute((Boolean)obj);
        }

        protected void onPreExecute()
        {
            mProgressDialog.setIndeterminate(false);
            if(!mProgressDialog.isShowing())
                mProgressDialog.show();
        }

        protected transient void onProgressUpdate(Integer ainteger[])
        {
            mProgressDialog.setProgress(ainteger[0].intValue());
        }

        protected volatile transient void onProgressUpdate(Object aobj[])
        {
            onProgressUpdate((Integer[])aobj);
        }

        private ProgressDialog mProgressDialog;

        public CarLayoutDownloader(ProgressDialog progressdialog)
        {
            mProgressDialog = progressdialog;
        }
    }


    public ServerCommands()
    {
    }

    public static String ACTIVATE_VALET_MODE(String s)
    {
        Object aobj[] = new Object[2];
        aobj[0] = "C21";
        aobj[1] = s;
        return String.format("%s,%s", aobj);
    }

    public static String DEACTIVATE_VALET_MODE(String s)
    {
        Object aobj[] = new Object[2];
        aobj[0] = "C23";
        aobj[1] = s;
        return String.format("%s,%s", aobj);
    }

    public static String LOCK_CAR(String s)
    {
        Object aobj[] = new Object[2];
        aobj[0] = "C20";
        aobj[1] = s;
        return String.format("%s,%s", aobj);
    }

    public static AlertDialog LockUnlockCar(final Context mContext, final OVMSActivity mApp, final Toast toastDisplayed, final boolean lock)
    {
        InputFilter ainputfilter[] = new InputFilter[1];
        ainputfilter[0] = new android.text.InputFilter.LengthFilter(8);
        DigitsKeyListener digitskeylistener = new DigitsKeyListener(false, false);
        final EditText input = new EditText(mContext);
        input.setFilters(ainputfilter);
        input.setInputType(8192);
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        input.setHint("Vehicle PIN");
        input.setKeyListener(digitskeylistener);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        android.app.AlertDialog.Builder builder1 = builder.setMessage("Vehicle PIN:");
        String s;
        android.app.AlertDialog.Builder builder2;
        String s1;
        AlertDialog alertdialog;
        if(lock)
            s = "Lock Car";
        else
            s = "Unlock Car";
        builder2 = builder1.setTitle(s).setCancelable(true).setView(input);
        if(lock)
            s1 = "Lock";
        else
            s1 = "Unlock";
        builder2.setPositiveButton(s1, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                Toast toast;
                Context context;
                String s2;
                if(lock)
                    mApp.SendServerCommand(ServerCommands.LOCK_CAR(input.getText().toString()));
                else
                    mApp.SendServerCommand(ServerCommands.UNLOCK_CAR(input.getText().toString()));
                toast = toastDisplayed;
                context = mContext;
                if(lock)
                    s2 = "Locking...";
                else
                    s2 = "Unlocking...";
                ServerCommands.makeToast(toast, context, s2, 0);
                dialoginterface.dismiss();
            }

            private final EditText val$input;
            private final boolean val$lock;
            private final OVMSActivity val$mApp;
            private final Context val$mContext;
            private final Toast val$toastDisplayed;

            
            {
                lock = flag;
                mApp = ovmsactivity;
                input = edittext;
                toastDisplayed = toast;
                mContext = context;
                super();
            }
        }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                dialoginterface.cancel();
            }

        }
);
        alertdialog = builder.create();
        alertdialog.show();
        return alertdialog;
    }

    public static void RequestC2DMRegistrationID(Context context)
    {
        Toast.makeText(context, "Initializing push notification", 0).show();
        Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
        intent.putExtra("app", PendingIntent.getBroadcast(context, 0, new Intent(), 0));
        intent.putExtra("sender", "openvehicles@gmail.com");
        context.startService(intent);
    }

    public static String SEND_AT_COMMAND(String s)
    {
        Object aobj[] = new Object[2];
        aobj[0] = "C49";
        aobj[1] = s;
        return String.format("%s,%s", aobj);
    }

    public static String SEND_SMS(String s, String s1)
    {
        Object aobj[] = new Object[3];
        aobj[0] = "C40";
        aobj[1] = s;
        aobj[2] = s1;
        return String.format("%s,%s,%s", aobj);
    }

    public static String SEND_USSD(String s)
    {
        Object aobj[] = new Object[2];
        aobj[0] = "C41";
        aobj[1] = s;
        return String.format("%s,%s", aobj);
    }

    public static String SET_CHARGE_CURRENT(int i)
    {
        Object aobj[] = new Object[2];
        aobj[0] = "C15";
        aobj[1] = Integer.valueOf(i);
        return String.format("%s,%s", aobj);
    }

    public static String SET_CHARGE_MODE(int i)
    {
        Object aobj[] = new Object[2];
        aobj[0] = "C10";
        aobj[1] = Integer.valueOf(i);
        return String.format("%s,%s", aobj);
    }

    public static String SET_CHARGE_MODE_AND_CURRENT(int i, int j)
    {
        Object aobj[] = new Object[3];
        aobj[0] = "C16";
        aobj[1] = Integer.valueOf(i);
        aobj[2] = Integer.valueOf(j);
        return String.format("%s,%s,%s", aobj);
    }

    public static String SET_FEATURE(int i, String s)
    {
        Object aobj[] = new Object[3];
        aobj[0] = "C2";
        aobj[1] = Integer.valueOf(i);
        aobj[2] = s;
        return String.format("%s,%s,%s", aobj);
    }

    public static String SET_PARAMETER(int i, String s)
    {
        Object aobj[] = new Object[3];
        aobj[0] = "C4";
        aobj[1] = Integer.valueOf(i);
        aobj[2] = s;
        return String.format("%s,%s,%s", aobj);
    }

    public static String SUBSCRIBE_GROUP(String s)
    {
        Object aobj[] = new Object[2];
        aobj[0] = "G";
        aobj[1] = s;
        return String.format("%s%s,1", aobj);
    }

    public static String SUBSCRIBE_PUSH_NOTIFICATIONS(String s, String s1, String s2, String s3)
    {
        Object aobj[] = new Object[5];
        aobj[0] = "p";
        aobj[1] = s;
        aobj[2] = s1;
        aobj[3] = s2;
        aobj[4] = s3;
        return String.format("%s%s,c2dm,production,%s,%s,%s", aobj);
    }

    public static AlertDialog SetChargeCurrent(final Context mContext, final OVMSActivity mApp, final Toast toastDisplayed, int i)
    {
        (new InputFilter[1])[0] = new android.text.InputFilter.LengthFilter(8);
        DigitsKeyListener digitskeylistener = new DigitsKeyListener(false, false);
        InputFilter ainputfilter[] = new InputFilter[1];
        ainputfilter[0] = new android.text.InputFilter.LengthFilter(2);
        final EditText input = new EditText(mContext);
        input.setFilters(ainputfilter);
        input.setInputType(8192);
        input.setKeyListener(digitskeylistener);
        input.setHint("Charge Current (Amps)");
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(i);
        input.setText(String.format("%s", aobj));
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        builder.setMessage("Enter desired amps (10 - 70):").setTitle("Set Maximum Current").setCancelable(true).setView(input).setPositiveButton("Set", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                int k = Integer.parseInt(input.getText().toString());
                if(k >= 10 && k <= 70)
                {
                    mApp.SendServerCommand(ServerCommands.SET_CHARGE_CURRENT(k));
                    ServerCommands.makeToast(toastDisplayed, mContext, "Changing Max Current...", 0);
                    dialoginterface.dismiss();
                } else
                {
                    ServerCommands.makeToast(toastDisplayed, mContext, "Amps must be between 10 and 70", 0);
                }
            }

            private final EditText val$input;
            private final OVMSActivity val$mApp;
            private final Context val$mContext;
            private final Toast val$toastDisplayed;

            
            {
                input = edittext;
                mApp = ovmsactivity;
                toastDisplayed = toast;
                mContext = context;
                super();
            }
        }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.cancel();
            }

        }
);
        AlertDialog alertdialog = builder.create();
        alertdialog.show();
        return alertdialog;
    }

    public static AlertDialog SetChargeMode(final Context mContext, final OVMSActivity mApp, final Toast toastDisplayed)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        builder.setTitle("Set Charge Mode").setCancelable(true).setItems(chargeModes, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                OVMSActivity ovmsactivity = mApp;
                if(i >= 2)
                    i++;
                ovmsactivity.SendServerCommand(ServerCommands.SET_CHARGE_MODE(i));
                ServerCommands.makeToast(toastDisplayed, mContext, "Changing Mode...", 0);
                dialoginterface.dismiss();
            }

            private final OVMSActivity val$mApp;
            private final Context val$mContext;
            private final Toast val$toastDisplayed;

            
            {
                mApp = ovmsactivity;
                toastDisplayed = toast;
                mContext = context;
                super();
            }
        }
);
        AlertDialog alertdialog = builder.create();
        alertdialog.show();
        return alertdialog;
    }

    public static AlertDialog StartCharge(final Context mContext, final OVMSActivity mApp, final Toast toastDisplayed)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        builder.setMessage("Do you want to start charging the car now?").setTitle("Start Charging").setCancelable(true).setPositiveButton("Start", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                mApp.SendServerCommand("C11");
                ServerCommands.makeToast(toastDisplayed, mContext, "Charge Starting...", 0);
                dialoginterface.dismiss();
            }

            private final OVMSActivity val$mApp;
            private final Context val$mContext;
            private final Toast val$toastDisplayed;

            
            {
                mApp = ovmsactivity;
                toastDisplayed = toast;
                mContext = context;
                super();
            }
        }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                dialoginterface.cancel();
            }

        }
);
        AlertDialog alertdialog = builder.create();
        alertdialog.show();
        return alertdialog;
    }

    public static AlertDialog StopCharge(final Context mContext, final OVMSActivity mApp, final Toast toastDisplayed)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        builder.setMessage("Do you want to stop the car from charging now?").setTitle("Stop Charging").setCancelable(true).setPositiveButton("Stop", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                mApp.SendServerCommand("C12");
                ServerCommands.makeToast(toastDisplayed, mContext, "Charge Stopping...", 0);
                dialoginterface.dismiss();
            }

            private final OVMSActivity val$mApp;
            private final Context val$mContext;
            private final Toast val$toastDisplayed;

            
            {
                mApp = ovmsactivity;
                toastDisplayed = toast;
                mContext = context;
                super();
            }
        }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                dialoginterface.cancel();
            }

        }
);
        AlertDialog alertdialog = builder.create();
        alertdialog.show();
        return alertdialog;
    }

    public static String UNLOCK_CAR(String s)
    {
        Object aobj[] = new Object[2];
        aobj[0] = "C22";
        aobj[1] = s;
        return String.format("%s,%s", aobj);
    }

    public static AlertDialog ValetModeOnOff(final Context mContext, final OVMSActivity mApp, final Toast toastDisplayed, final boolean valetOn)
    {
        InputFilter ainputfilter[] = new InputFilter[1];
        ainputfilter[0] = new android.text.InputFilter.LengthFilter(8);
        DigitsKeyListener digitskeylistener = new DigitsKeyListener(false, false);
        final EditText input = new EditText(mContext);
        input.setFilters(ainputfilter);
        input.setInputType(8192);
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        input.setHint("Vehicle PIN");
        input.setKeyListener(digitskeylistener);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        android.app.AlertDialog.Builder builder1 = builder.setMessage("Vehicle PIN:");
        String s;
        android.app.AlertDialog.Builder builder2;
        String s1;
        AlertDialog alertdialog;
        if(valetOn)
            s = "Activate Valet Mode";
        else
            s = "Deactivate Valet Mode";
        builder2 = builder1.setTitle(s).setCancelable(true).setView(input);
        if(valetOn)
            s1 = "Activate";
        else
            s1 = "Deactivate";
        builder2.setPositiveButton(s1, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                Toast toast;
                Context context;
                String s2;
                if(valetOn)
                    mApp.SendServerCommand(ServerCommands.ACTIVATE_VALET_MODE(input.getText().toString()));
                else
                    mApp.SendServerCommand(ServerCommands.DEACTIVATE_VALET_MODE(input.getText().toString()));
                toast = toastDisplayed;
                context = mContext;
                if(valetOn)
                    s2 = "Activating Valet...";
                else
                    s2 = "Deactivating Valet...";
                ServerCommands.makeToast(toast, context, s2, 0);
                dialoginterface.dismiss();
            }

            private final EditText val$input;
            private final OVMSActivity val$mApp;
            private final Context val$mContext;
            private final Toast val$toastDisplayed;
            private final boolean val$valetOn;

            
            {
                valetOn = flag;
                mApp = ovmsactivity;
                input = edittext;
                toastDisplayed = toast;
                mContext = context;
                super();
            }
        }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                dialoginterface.cancel();
            }

        }
);
        alertdialog = builder.create();
        alertdialog.show();
        return alertdialog;
    }

    public static AlertDialog WakeUp(final Context mContext, final OVMSActivity mApp, final Toast toastDisplayed, final boolean wakeUpSensorsOnly)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        String s;
        android.app.AlertDialog.Builder builder1;
        String s1;
        AlertDialog alertdialog;
        if(wakeUpSensorsOnly)
            s = "Wake up the sensor systems now?";
        else
            s = "Wake up the car and its sensor systems now?";
        builder1 = builder.setMessage(s);
        if(wakeUpSensorsOnly)
            s1 = "Wake Up Sensors";
        else
            s1 = "Wake Up Car";
        builder1.setTitle(s1).setCancelable(true).setPositiveButton("Wake Up", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                if(wakeUpSensorsOnly)
                    mApp.SendServerCommand("C19");
                else
                    mApp.SendServerCommand("C18");
                ServerCommands.makeToast(toastDisplayed, mContext, "Waking Up...", 0);
                dialoginterface.dismiss();
            }

            private final OVMSActivity val$mApp;
            private final Context val$mContext;
            private final Toast val$toastDisplayed;
            private final boolean val$wakeUpSensorsOnly;

            
            {
                wakeUpSensorsOnly = flag;
                mApp = ovmsactivity;
                toastDisplayed = toast;
                mContext = context;
                super();
            }
        }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                dialoginterface.cancel();
            }

        }
);
        alertdialog = builder.create();
        alertdialog.show();
        return alertdialog;
    }

    public static void makeToast(Toast toast, Context context, String s, int i)
    {
        if(toast != null)
            toast.cancel();
        Toast.makeText(context, s, i).show();
    }

    public static String toString(int i)
    {
        i;
        JVM INSTR lookupswitch 16: default 140
    //                   1: 144
    //                   2: 151
    //                   3: 158
    //                   4: 165
    //                   5: 172
    //                   10: 179
    //                   11: 186
    //                   12: 193
    //                   20: 200
    //                   21: 206
    //                   22: 213
    //                   23: 220
    //                   30: 227
    //                   40: 234
    //                   41: 241
    //                   49: 248;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13 _L14 _L15 _L16 _L17
_L1:
        String s = null;
_L19:
        return s;
_L2:
        s = "Features Request";
        continue; /* Loop/switch isn't completed */
_L3:
        s = "Set Feature";
        continue; /* Loop/switch isn't completed */
_L4:
        s = "Parameters Request";
        continue; /* Loop/switch isn't completed */
_L5:
        s = "Set Parameter";
        continue; /* Loop/switch isn't completed */
_L6:
        s = "System Reboot";
        continue; /* Loop/switch isn't completed */
_L7:
        s = "Set Charge Mode";
        continue; /* Loop/switch isn't completed */
_L8:
        s = "Start Charge";
        continue; /* Loop/switch isn't completed */
_L9:
        s = "Stop Charge";
        continue; /* Loop/switch isn't completed */
_L10:
        s = "Lock Car";
        continue; /* Loop/switch isn't completed */
_L11:
        s = "Activate Valet Mode";
        continue; /* Loop/switch isn't completed */
_L12:
        s = "Unlock Car";
        continue; /* Loop/switch isn't completed */
_L13:
        s = "Deactivate Valet Mode";
        continue; /* Loop/switch isn't completed */
_L14:
        s = "GPRS Utilization Request";
        continue; /* Loop/switch isn't completed */
_L15:
        s = "SMS Relay";
        continue; /* Loop/switch isn't completed */
_L16:
        s = "USSD Command";
        continue; /* Loop/switch isn't completed */
_L17:
        s = "AT Command";
        if(true) goto _L19; else goto _L18
_L18:
    }

    public static final int DEBUG_MODEM_RESPONSE = 49;
    public static final int FEATURE_CANWRITE = 15;
    public static final int FEATURE_CARBITS = 14;
    public static final int FEATURE_DEBUGMODEM = 7;
    public static final String FEATURE_LIST_REQUEST = "C1";
    public static final int FEATURE_LIST_RESPONSE = 1;
    public static final int FEATURE_MINSOC = 9;
    public static final int FEATURE_SPEEDO = 0;
    public static final int FEATURE_STREAM = 8;
    public static final String GPRS_UTILIZATION_DATA_REQUEST = "C30";
    public static final int GPRS_UTILIZATION_DATA_RESPONSE = 30;
    public static final String PARAMETER_LIST_REQUEST = "C3";
    public static final int PARAMETER_LIST_RESPONSE = 3;
    public static final int PARAM_FEATURE10 = 26;
    public static final int PARAM_FEATURE11 = 27;
    public static final int PARAM_FEATURE12 = 28;
    public static final int PARAM_FEATURE13 = 29;
    public static final int PARAM_FEATURE14 = 30;
    public static final int PARAM_FEATURE15 = 31;
    public static final int PARAM_FEATURE8 = 24;
    public static final int PARAM_FEATURE9 = 25;
    public static final int PARAM_FEATURE_E = 31;
    public static final int PARAM_FEATURE_S = 24;
    public static final int PARAM_GPRSAPN = 5;
    public static final int PARAM_GPRSPASS = 7;
    public static final int PARAM_GPRSUSER = 6;
    public static final int PARAM_MILESKM = 2;
    public static final int PARAM_MYID = 8;
    public static final int PARAM_NETPASS1 = 9;
    public static final int PARAM_NOTIFIES = 3;
    public static final int PARAM_PARANOID = 10;
    public static final int PARAM_REGPASS = 1;
    public static final int PARAM_REGPHONE = 0;
    public static final int PARAM_SERVERIP = 4;
    public static final int PARAM_S_GROUP = 11;
    public static final String START_CHARGE = "C11";
    public static final String STOP_CHARGE = "C12";
    public static final String SYSTEM_REBOOT = "C5";
    public static final String WAKE_UP_CAR = "C18";
    public static final String WAKE_UP_TEMP_SUBSYSTEM = "C19";
    public static final String __ACTIVATE_VALET_MODE = "C21";
    public static final String __DEACTIVATE_VALET_MODE = "C23";
    public static final String __LAYOUT_IMAGE_URL_BASE = "http://www.openvehicles.com/resources";
    public static final String __LOCK_CAR = "C20";
    public static final String __SEND_AT_COMMAND = "C49";
    public static final String __SEND_SMS = "C40";
    public static final String __SEND_USSD = "C41";
    public static final String __SET_CHARGE_CURRENT = "C15";
    public static final String __SET_CHARGE_MODE = "C10";
    public static final String __SET_CHARGE_MODE_AND_CURRENT = "C16";
    public static final String __SET_FEATURE = "C2";
    public static final String __SET_PARAMETER = "C4";
    public static final String __SUBSCRIBE_GROUP = "G";
    public static final String __SUBSCRIBE_PUSH_NOTIFICATIONS = "p";
    public static final String __UNLOCK_CAR = "C22";
    private static final CharSequence chargeModes[];

    static 
    {
        CharSequence acharsequence[] = new CharSequence[4];
        acharsequence[0] = "Standard";
        acharsequence[1] = "Storage";
        acharsequence[2] = "Range";
        acharsequence[3] = "Performance";
        chargeModes = acharsequence;
    }
}
