// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.*;
import android.content.*;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.*;
import android.preference.*;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

// Referenced classes of package com.openvehicles.OVMS:
//            CarData, OVMSActivity, ServerCommands, DataLog

public class Tab_SubTabCarSettings extends PreferenceActivity
{

    public Tab_SubTabCarSettings()
    {
        isLoggedIn = false;
        lastVehicleID = "";
        lastDataRefreshed = null;
        toastDisplayed = null;
        delayedRequest = new Handler();
        preferenceStorageMapping = new LinkedHashMap();
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                if(lastVehicleID.equals(data.VehicleID)) goto _L2; else goto _L1
_L1:
                lastVehicleID = data.VehicleID;
                if(data.Data_Parameters.containsKey(Integer.valueOf(8)) && data.Data_Features.containsKey(Integer.valueOf(15))) goto _L4; else goto _L3
_L3:
                Log.d("SETTINGS", "No cached data found. Requesting data from module.");
                requestSettings();
_L7:
                return;
_L2:
                if(lastDataRefreshed == data.Data_Features_LastRefreshed)
                    continue; /* Loop/switch isn't completed */
_L4:
                if(toastDisplayed != null)
                {
                    toastDisplayed.cancel();
                    toastDisplayed = null;
                }
                lastDataRefreshed = data.Data_Features_LastRefreshed;
                TextView textview = (TextView)findViewById(0x7f090010);
                Iterator iterator;
                if(data.Data_Parameters_LastRefreshed != null)
                    textview.setText((new SimpleDateFormat("MMM d, K:mm:ss a")).format(data.Data_Parameters_LastRefreshed));
                else
                    textview.setText("Never");
                iterator = preferenceStorageMapping.keySet().iterator();
_L5:
                while(iterator.hasNext()) 
                {
                    String s = (String)iterator.next();
                    Preference preference = getPreferenceManager().findPreference(s);
                    LinkedHashMap linkedhashmap;
                    if(s.startsWith("PARAM"))
                    {
                        linkedhashmap = data.Data_Parameters;
                    } else
                    {
label0:
                        {
                            if(!s.startsWith("FEATURE"))
                                break label0;
                            linkedhashmap = data.Data_Features;
                        }
                    }
                    if(((Object[])preferenceStorageMapping.get(s))[1].equals("String"))
                    {
                        if(linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0]) == null)
                            ((EditTextPreference)preference).setText("");
                        else
                            ((EditTextPreference)preference).setText((String)linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0]));
                    } else
                    if(((Object[])preferenceStorageMapping.get(s))[1].equals("bool"))
                    {
                        Object obj = linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0]);
                        CheckBoxPreference checkboxpreference = (CheckBoxPreference)preference;
                        boolean flag;
                        if(obj != null && obj.toString().length() > 0 && !obj.equals("0"))
                            flag = true;
                        else
                            flag = false;
                        checkboxpreference.setChecked(flag);
                    } else
                    if(Integer.parseInt(((Object[])preferenceStorageMapping.get(s))[0].toString()) == 3)
                    {
                        if(linkedhashmap.get(Integer.valueOf(3)) == null || ((String)linkedhashmap.get(Integer.valueOf(3))).contains("IP") && ((String)linkedhashmap.get(Integer.valueOf(3))).contains("SMS"))
                            ((ListPreference)preference).setValue("SMS,IP");
                        else
                        if(((String)linkedhashmap.get(Integer.valueOf(3))).contains("SMS"))
                            ((ListPreference)preference).setValue("SMS");
                        else
                            ((ListPreference)preference).setValue("IP");
                    } else
                    if(Integer.parseInt(((Object[])preferenceStorageMapping.get(s))[0].toString()) == 2)
                    {
                        if(linkedhashmap.get(Integer.valueOf(2)) == null || ((String)linkedhashmap.get(Integer.valueOf(2))).equals("K"))
                            ((ListPreference)preference).setValue("K");
                        else
                            ((ListPreference)preference).setValue("M");
                    } else
                    if(Integer.parseInt(((Object[])preferenceStorageMapping.get(s))[0].toString()) == 9)
                        if(linkedhashmap.get(Integer.valueOf(9)) != null && ((String)linkedhashmap.get(Integer.valueOf(9))).length() > 0)
                            ((ListPreference)preference).setValue((String)linkedhashmap.get(Integer.valueOf(9)));
                        else
                            ((ListPreference)preference).setValue("30");
                }
                continue; /* Loop/switch isn't completed */
                Log.d("ERROR", (new StringBuilder("Unrecognized settings key: ")).append(s).toString());
                  goto _L5
                if(true) goto _L7; else goto _L6
_L6:
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
;
    }

    private void commitSettings()
    {
        final LinkedHashMap changedSettings;
        Iterator iterator;
        changedSettings = new LinkedHashMap();
        iterator = preferenceStorageMapping.keySet().iterator();
_L5:
        if(iterator.hasNext()) goto _L2; else goto _L1
_L1:
        if(changedSettings.size() != 0) goto _L4; else goto _L3
_L3:
        makeToast("Nothing has changed", 0);
        toastDisplayed.show();
_L6:
        return;
_L2:
        String s = (String)iterator.next();
        Preference preference = getPreferenceManager().findPreference(s);
        LinkedHashMap linkedhashmap;
        if(s.startsWith("PARAM"))
        {
            linkedhashmap = data.Data_Parameters;
        } else
        {
label0:
            {
                if(!s.startsWith("FEATURE"))
                    break label0;
                linkedhashmap = data.Data_Features;
            }
        }
        if(linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0]) == null)
            linkedhashmap.put(Integer.valueOf(Integer.parseInt(((Object[])preferenceStorageMapping.get(s))[0].toString())), "");
        if(((Object[])preferenceStorageMapping.get(s))[1].equals("String"))
        {
            String s4;
            String s5;
            if(!((EditTextPreference)preference).getText().equals(linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0])))
            {
                Integer integer3 = (Integer)((Object[])preferenceStorageMapping.get(s))[0];
                String as3[] = new String[3];
                as3[0] = s;
                Object aobj2[];
                String s6;
                String s7;
                if(((String)linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0])).trim().length() == 0)
                    s6 = "<empty>";
                else
                    s6 = (String)linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0]);
                as3[1] = s6;
                if(((EditTextPreference)preference).getText().trim().length() == 0)
                    s7 = "<empty>";
                else
                    s7 = ((EditTextPreference)preference).getText().trim();
                as3[2] = s7;
                changedSettings.put(integer3, as3);
            }
            aobj2 = new Object[3];
            aobj2[0] = s;
            if(((String)linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0])).trim().length() == 0)
                s4 = "<empty>";
            else
                s4 = (String)linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0]);
            aobj2[1] = s4;
            if(((EditTextPreference)preference).getText().trim().length() == 0)
                s5 = "<empty>";
            else
                s5 = ((EditTextPreference)preference).getText().trim();
            aobj2[2] = s5;
            Log.d("SETTINGS", String.format("%s : %s -> %s", aobj2));
        } else
        if(((Object[])preferenceStorageMapping.get(s))[1].equals("bool"))
        {
            Object obj = linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0]);
            boolean flag;
            String s1;
            if(obj != null && obj.toString().length() > 0 && !obj.equals("0"))
                flag = true;
            else
                flag = false;
            if(((CheckBoxPreference)preference).isChecked() != flag)
                if(Integer.parseInt(((Object[])preferenceStorageMapping.get(s))[0].toString()) == 10)
                {
                    Integer integer2 = (Integer)((Object[])preferenceStorageMapping.get(s))[0];
                    String as2[] = new String[3];
                    as2[0] = s;
                    as2[1] = (String)linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0]);
                    Object aobj1[];
                    String s3;
                    if(((CheckBoxPreference)preference).isChecked())
                        s3 = "P";
                    else
                        s3 = "";
                    as2[2] = s3;
                    changedSettings.put(integer2, as2);
                } else
                {
                    Integer integer1 = (Integer)((Object[])preferenceStorageMapping.get(s))[0];
                    String as1[] = new String[3];
                    as1[0] = s;
                    as1[1] = (String)linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0]);
                    String s2;
                    if(((CheckBoxPreference)preference).isChecked())
                        s2 = "1";
                    else
                        s2 = "0";
                    as1[2] = s2;
                    changedSettings.put(integer1, as1);
                }
            aobj1 = new Object[3];
            aobj1[0] = s;
            aobj1[1] = linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0]);
            if(((CheckBoxPreference)preference).isChecked())
                s1 = "1";
            else
                s1 = "0";
            aobj1[2] = s1;
            Log.d("SETTINGS", String.format("%s : %s -> %s", aobj1));
        } else
        if(((Object[])preferenceStorageMapping.get(s))[1].equals("List"))
        {
            if(!((ListPreference)preference).getValue().equals(linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0])))
            {
                Integer integer = (Integer)((Object[])preferenceStorageMapping.get(s))[0];
                String as[] = new String[3];
                as[0] = s;
                as[1] = (String)linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0]);
                as[2] = ((ListPreference)preference).getValue();
                changedSettings.put(integer, as);
            }
            Object aobj[] = new Object[3];
            aobj[0] = s;
            aobj[1] = linkedhashmap.get(((Object[])preferenceStorageMapping.get(s))[0]);
            aobj[2] = ((ListPreference)preference).getValue();
            Log.d("SETTINGS", String.format("%s : %s -> %s", aobj));
        }
          goto _L5
        Log.d("ERROR", (new StringBuilder("Unrecognized settings key: ")).append(s).toString());
          goto _L5
_L4:
        String s8;
        Iterator iterator1;
        s8 = "";
        iterator1 = changedSettings.keySet().iterator();
_L7:
label1:
        {
            if(iterator1.hasNext())
                break label1;
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
            builder.setMessage(s8).setTitle(getResources().getQuantityString(0x7f070000, changedSettings.size())).setCancelable(true).setPositiveButton("Commit", new android.content.DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialoginterface, int j)
                {
                    Iterator iterator2;
                    dialoginterface.dismiss();
                    makeToast("Commiting - please wait...", 1);
                    iterator2 = changedSettings.keySet().iterator();
_L2:
                    int k;
                    String s9;
                    if(!iterator2.hasNext())
                    {
                        makeToast("Completed", 0);
                        return;
                    }
                    k = ((Integer)iterator2.next()).intValue();
                    if(!((String[])changedSettings.get(Integer.valueOf(k)))[0].startsWith("PARAM"))
                        break; /* Loop/switch isn't completed */
                    s9 = ServerCommands.SET_PARAMETER(k, ((String[])changedSettings.get(Integer.valueOf(k)))[2]);
_L4:
                    mOVMSActivity.SendServerCommand(s9);
                    if(true) goto _L2; else goto _L1
_L1:
                    if(!((String[])changedSettings.get(Integer.valueOf(k)))[0].startsWith("FEATURE")) goto _L2; else goto _L3
_L3:
                    s9 = ServerCommands.SET_FEATURE(k, ((String[])changedSettings.get(Integer.valueOf(k)))[2]);
                      goto _L4
                }

                final Tab_SubTabCarSettings this$0;
                private final LinkedHashMap val$changedSettings;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                changedSettings = linkedhashmap;
                super();
            }
            }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialoginterface, int j)
                {
                    makeToast("Commit cancelled", 1);
                    dialoginterface.dismiss();
                }

                final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
            }
);
            AlertDialog alertdialog = builder.create();
            if(!isFinishing())
                alertdialog.show();
        }
          goto _L6
        int i = ((Integer)iterator1.next()).intValue();
        if(s8.length() > 0)
            s8 = (new StringBuilder(String.valueOf(s8))).append("\n").toString();
        StringBuilder stringbuilder = new StringBuilder(String.valueOf(s8));
        Object aobj3[] = new Object[3];
        aobj3[0] = ((String[])changedSettings.get(Integer.valueOf(i)))[0].replace("PARAM_", "").replace("FEATURE_", "");
        aobj3[1] = ((String[])changedSettings.get(Integer.valueOf(i)))[1];
        aobj3[2] = ((String[])changedSettings.get(Integer.valueOf(i)))[2];
        s8 = stringbuilder.append(String.format("%s: %s > %s", aobj3)).toString();
          goto _L7
    }

    private void downloadLayout()
    {
        downloadProgress = new ProgressDialog(mContext);
        downloadProgress.setMessage("Downloading Hi-Res Graphics");
        downloadProgress.setIndeterminate(true);
        downloadProgress.setMax(100);
        downloadProgress.setCancelable(true);
        downloadProgress.setProgressStyle(1);
        downloadProgress.show();
        downloadProgress.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {

            public void onDismiss(DialogInterface dialoginterface)
            {
                StringBuilder stringbuilder = new StringBuilder(String.valueOf(mContext.getCacheDir().getAbsolutePath()));
                Object aobj[] = new Object[1];
                aobj[0] = data.VehicleImageDrawable;
                if(BitmapFactory.decodeFile(stringbuilder.append(String.format("/ol_%s.png", aobj)).toString()) != null)
                    Toast.makeText(mContext, "Graphics Downloaded", 0).show();
                else
                    Toast.makeText(mContext, "Download Failed", 0).show();
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        downloadTask = new ServerCommands.CarLayoutDownloader(downloadProgress);
        ServerCommands.CarLayoutDownloader carlayoutdownloader = downloadTask;
        String as[] = new String[2];
        as[0] = data.VehicleImageDrawable;
        as[1] = mContext.getCacheDir().getAbsolutePath();
        carlayoutdownloader.execute(as);
    }

    private void forceContext(Context context, Preference preference)
    {
        Field field = android/preference/Preference.getDeclaredField("mContext");
        field.setAccessible(true);
        field.set(preference, context);
_L1:
        return;
        Exception exception;
        exception;
        exception.printStackTrace();
          goto _L1
    }

    private String getSharedPreference(String s)
    {
        return cachedUIPreferences.getString(s, null);
    }

    private void makeToast(String s, int i)
    {
        if(toastDisplayed != null)
        {
            toastDisplayed.cancel();
            toastDisplayed = null;
        }
        toastDisplayed = Toast.makeText(mContext, s, i);
        toastDisplayed.show();
    }

    private void requestSettings()
    {
        int i;
        String s;
        i = 0;
        if(data.Data_CarModuleFirmwareVersion.length() < 5)
            break MISSING_BLOCK_LABEL_81;
        s = data.Data_CarModuleFirmwareVersion.substring(0, 5);
        int j;
        Log.d("OVMS", (new StringBuilder("Current Firmware: ")).append(s.replaceAll("\\.", "")).toString());
        j = Integer.parseInt(s.replaceAll("\\.", ""));
        i = j;
_L2:
        if(i < 119)
        {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
            builder.setMessage("Please upgrade vehicle module firmware to 1.1.9-exp3 or later.").setTitle("Unsupported Firmware").setCancelable(false).setPositiveButton("Close", new android.content.DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialoginterface, int k)
                {
                    dialoginterface.dismiss();
                }

                final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
            }
);
            AlertDialog alertdialog = builder.create();
            if(!isFinishing())
                alertdialog.show();
        } else
        {
            makeToast("Requesting data from car...", 1);
            mOVMSActivity.SendServerCommand("C3");
            Runnable runnable = new Runnable() {

                public void run()
                {
                    mOVMSActivity.SendServerCommand("C1");
                }

                final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
            }
;
            delayedRequest.postDelayed(runnable, 200L);
        }
        return;
        Exception exception;
        exception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private void setSharedPreference(String s, String s1)
    {
        android.content.SharedPreferences.Editor editor = cachedUIPreferences.edit();
        editor.putString(s, s1);
        editor.commit();
    }

    private void wireUpDynamicMessage(Preference preference, String s)
    {
        preference.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {

            public boolean onPreferenceChange(Preference preference1, Object obj)
            {
                preference1.setSummary(obj.toString());
                return true;
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        if(s.equals("String"))
            preference.setSummary(((EditTextPreference)preference).getText().toString());
        if(s.equals("List"))
            preference.setSummary(((ListPreference)preference).getValue().toString());
    }

    private void wireUpPrefButtons()
    {
        findPreference("startcharge").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                if(!isFinishing())
                    ServerCommands.StartCharge(mContext, mOVMSActivity, toastDisplayed);
                return true;
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("stopcharge").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                if(!isFinishing())
                    ServerCommands.StopCharge(mContext, mOVMSActivity, toastDisplayed);
                return true;
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("chargemode").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                if(!isFinishing())
                    ServerCommands.SetChargeMode(mContext, mOVMSActivity, toastDisplayed);
                return true;
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("lockcar").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                if(!isFinishing())
                    ServerCommands.LockUnlockCar(mContext, mOVMSActivity, toastDisplayed, true);
                return true;
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("unlockcar").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                if(!isFinishing())
                    ServerCommands.LockUnlockCar(mContext, mOVMSActivity, toastDisplayed, false);
                return true;
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("valeton").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                if(!isFinishing())
                    ServerCommands.ValetModeOnOff(mContext, mOVMSActivity, toastDisplayed, true);
                return true;
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("valetoff").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                if(!isFinishing())
                    ServerCommands.ValetModeOnOff(mContext, mOVMSActivity, toastDisplayed, false);
                return true;
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("setchargecurrent").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                if(!isFinishing())
                    ServerCommands.SetChargeCurrent(mContext, mOVMSActivity, toastDisplayed, data.Data_ChargeAmpsLimit);
                return true;
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("wakeupcar").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                if(!isFinishing())
                    ServerCommands.WakeUp(mContext, mOVMSActivity, toastDisplayed, false);
                return true;
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("wakeuptemps").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                if(!isFinishing())
                    ServerCommands.WakeUp(mContext, mOVMSActivity, toastDisplayed, true);
                return true;
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("restartovms").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setMessage("A full reboot will be performed on the car module.").setTitle("Reboot Car Module").setCancelable(true).setPositiveButton("Reboot", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        mOVMSActivity.SendServerCommand("C5");
                        makeToast("Request sent", 0);
                        dialoginterface.dismiss();
                    }

                    final _cls15 this$1;

                    
                    {
                        this$1 = _cls15.this;
                        super();
                    }
                }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        dialoginterface.dismiss();
                    }

                    final _cls15 this$1;

                    
                    {
                        this$1 = _cls15.this;
                        super();
                    }
                }
);
                AlertDialog alertdialog = builder.create();
                if(!isFinishing())
                    alertdialog.show();
                return true;
            }

            final Tab_SubTabCarSettings this$0;


            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("sendsms").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                View view = LayoutInflater.from(mContext).inflate(0x7f030004, null);
                final EditText input1 = (EditText)view.findViewById(0x7f09000c);
                final EditText input2 = (EditText)view.findViewById(0x7f09000d);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setTitle("Send SMS").setView(view).setCancelable(true).setPositiveButton("Send", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        String s = input1.getText().toString().trim();
                        String s1 = input2.getText().toString();
                        if(s.length() > 0)
                        {
                            mOVMSActivity.SendServerCommand(ServerCommands.SEND_SMS(s, s1));
                            makeToast("Request sent", 0);
                            dialoginterface.dismiss();
                        } else
                        {
                            makeToast("Invalid format", 0);
                        }
                    }

                    final _cls16 this$1;
                    private final EditText val$input1;
                    private final EditText val$input2;

                    
                    {
                        this$1 = _cls16.this;
                        input1 = edittext;
                        input2 = edittext1;
                        super();
                    }
                }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        dialoginterface.dismiss();
                    }

                    final _cls16 this$1;

                    
                    {
                        this$1 = _cls16.this;
                        super();
                    }
                }
);
                AlertDialog alertdialog = builder.create();
                if(!isFinishing())
                    alertdialog.show();
                return true;
            }

            final Tab_SubTabCarSettings this$0;


            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("sendussd").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                final EditText input = new EditText(mContext);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setMessage("USSD (GSM feature code) to send:").setTitle("Send USSD Code").setCancelable(true).setView(input).setPositiveButton("Send", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        mOVMSActivity.SendServerCommand(ServerCommands.SEND_USSD(input.getText().toString()));
                        makeToast("Request sent", 0);
                        dialoginterface.dismiss();
                    }

                    final _cls17 this$1;
                    private final EditText val$input;

                    
                    {
                        this$1 = _cls17.this;
                        input = edittext;
                        super();
                    }
                }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        dialoginterface.dismiss();
                    }

                    final _cls17 this$1;

                    
                    {
                        this$1 = _cls17.this;
                        super();
                    }
                }
);
                AlertDialog alertdialog = builder.create();
                if(!isFinishing())
                    alertdialog.show();
                return true;
            }

            final Tab_SubTabCarSettings this$0;


            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("sendat").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                final EditText input = new EditText(mContext);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setMessage("AT command to send to the modem:").setTitle("Send AT Modem").setCancelable(true).setView(input).setPositiveButton("Send", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        mOVMSActivity.SendServerCommand(ServerCommands.SEND_AT_COMMAND(input.getText().toString()));
                        makeToast("Request sent", 0);
                        dialoginterface.dismiss();
                    }

                    final _cls18 this$1;
                    private final EditText val$input;

                    
                    {
                        this$1 = _cls18.this;
                        input = edittext;
                        super();
                    }
                }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
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
                AlertDialog alertdialog = builder.create();
                if(!isFinishing())
                    alertdialog.show();
                return true;
            }

            final Tab_SubTabCarSettings this$0;


            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("commslog").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setMessage(DataLog.getLog()).setTitle("TCP Log").setCancelable(true).setPositiveButton("Close", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        dialoginterface.dismiss();
                    }

                    final _cls19 this$1;

                    
                    {
                        this$1 = _cls19.this;
                        super();
                    }
                }
);
                AlertDialog alertdialog = builder.create();
                if(!isFinishing())
                    alertdialog.show();
                return true;
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("downloadgraphics").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setMessage("Re-download high resolution graphics now?\n\nThe download is approx. 300KB.").setTitle("Download Graphics").setCancelable(true).setPositiveButton("Download", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        downloadLayout();
                        dialoginterface.dismiss();
                    }

                    final _cls20 this$1;

                    
                    {
                        this$1 = _cls20.this;
                        super();
                    }
                }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        data.DontAskLayoutDownload = true;
                        dialoginterface.dismiss();
                    }

                    final _cls20 this$1;

                    
                    {
                        this$1 = _cls20.this;
                        super();
                    }
                }
);
                dialog = builder.create();
                dialog.show();
                return true;
            }

            final Tab_SubTabCarSettings this$0;


            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("reinitializec2dm").setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setMessage("Re-register the OVMS server with a new C2DM push notification ID.").setTitle("Re-register Push Notifications").setCancelable(true).setPositiveButton("Re-register", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        android.content.SharedPreferences.Editor editor = mOVMSActivity.getSharedPreferences("C2DM", 0).edit();
                        editor.remove("RegID");
                        editor.commit();
                        ServerCommands.RequestC2DMRegistrationID(mOVMSActivity);
                        mOVMSActivity.ReportC2DMRegistrationID();
                        dialoginterface.dismiss();
                    }

                    final _cls21 this$1;

                    
                    {
                        this$1 = _cls21.this;
                        super();
                    }
                }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        data.DontAskLayoutDownload = true;
                        dialoginterface.dismiss();
                    }

                    final _cls21 this$1;

                    
                    {
                        this$1 = _cls21.this;
                        super();
                    }
                }
);
                dialog = builder.create();
                dialog.show();
                return true;
            }

            final Tab_SubTabCarSettings this$0;


            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        findPreference("FEATURE_DEBUGMODEM").setEnabled(false);
        Preference preference = findPreference("resetovms");
        preference.setEnabled(false);
        preference.setOnPreferenceClickListener(new android.preference.Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference1)
            {
                final EditText input = new EditText(mContext);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setMessage("YOU ARE ABOUT TO FACTORY RESET YOUR OVMS CAR MODULE.\n\nAfter resetting, your module will remain offline and available from this app. You will need to manually send a SMS to restore the module's connection settings.\n\nTo reset, enter 12345678:").setTitle("!! Factory Reset !!").setCancelable(true).setView(input).setPositiveButton("RESET", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        if(!input.getText().toString().equals("12345678"))
                            makeToast("You must enter 12345678 to reset", 0);
                        dialoginterface.dismiss();
                    }

                    final _cls22 this$1;
                    private final EditText val$input;

                    
                    {
                        this$1 = _cls22.this;
                        input = edittext;
                        super();
                    }
                }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        dialoginterface.dismiss();
                    }

                    final _cls22 this$1;

                    
                    {
                        this$1 = _cls22.this;
                        super();
                    }
                }
);
                AlertDialog alertdialog = builder.create();
                if(!isFinishing())
                    alertdialog.show();
                return true;
            }

            final Tab_SubTabCarSettings this$0;


            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
    }

    public void Refresh(CarData cardata, boolean flag)
    {
        data = cardata;
        handler.sendEmptyMessage(0);
    }

    public void onCreate(Bundle bundle)
    {
        mContext = getParent();
        super.onCreate(bundle);
        mOVMSActivity = (OVMSActivity)getParent().getParent();
        if(mOVMSActivity == null)
            mOVMSActivity = (OVMSActivity)getParent();
        if(mOVMSActivity == null)
            Toast.makeText(this, "Unknown Layout Error", 1).show();
        LinkedHashMap linkedhashmap = preferenceStorageMapping;
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(0);
        aobj[1] = "String";
        linkedhashmap.put("PARAM_REGPHONE", ((Object) (aobj)));
        LinkedHashMap linkedhashmap1 = preferenceStorageMapping;
        Object aobj1[] = new Object[2];
        aobj1[0] = Integer.valueOf(1);
        aobj1[1] = "String";
        linkedhashmap1.put("PARAM_REGPASS", ((Object) (aobj1)));
        LinkedHashMap linkedhashmap2 = preferenceStorageMapping;
        Object aobj2[] = new Object[2];
        aobj2[0] = Integer.valueOf(2);
        aobj2[1] = "List";
        linkedhashmap2.put("PARAM_MILESKM", ((Object) (aobj2)));
        LinkedHashMap linkedhashmap3 = preferenceStorageMapping;
        Object aobj3[] = new Object[2];
        aobj3[0] = Integer.valueOf(3);
        aobj3[1] = "List";
        linkedhashmap3.put("PARAM_NOTIFIES", ((Object) (aobj3)));
        LinkedHashMap linkedhashmap4 = preferenceStorageMapping;
        Object aobj4[] = new Object[2];
        aobj4[0] = Integer.valueOf(4);
        aobj4[1] = "String";
        linkedhashmap4.put("PARAM_SERVERIP", ((Object) (aobj4)));
        LinkedHashMap linkedhashmap5 = preferenceStorageMapping;
        Object aobj5[] = new Object[2];
        aobj5[0] = Integer.valueOf(5);
        aobj5[1] = "String";
        linkedhashmap5.put("PARAM_GPRSAPN", ((Object) (aobj5)));
        LinkedHashMap linkedhashmap6 = preferenceStorageMapping;
        Object aobj6[] = new Object[2];
        aobj6[0] = Integer.valueOf(6);
        aobj6[1] = "String";
        linkedhashmap6.put("PARAM_GPRSUSER", ((Object) (aobj6)));
        LinkedHashMap linkedhashmap7 = preferenceStorageMapping;
        Object aobj7[] = new Object[2];
        aobj7[0] = Integer.valueOf(7);
        aobj7[1] = "String";
        linkedhashmap7.put("PARAM_GPRSPASS", ((Object) (aobj7)));
        LinkedHashMap linkedhashmap8 = preferenceStorageMapping;
        Object aobj8[] = new Object[2];
        aobj8[0] = Integer.valueOf(8);
        aobj8[1] = "String";
        linkedhashmap8.put("PARAM_MYID", ((Object) (aobj8)));
        LinkedHashMap linkedhashmap9 = preferenceStorageMapping;
        Object aobj9[] = new Object[2];
        aobj9[0] = Integer.valueOf(9);
        aobj9[1] = "String";
        linkedhashmap9.put("PARAM_NETPASS1", ((Object) (aobj9)));
        LinkedHashMap linkedhashmap10 = preferenceStorageMapping;
        Object aobj10[] = new Object[2];
        aobj10[0] = Integer.valueOf(10);
        aobj10[1] = "bool";
        linkedhashmap10.put("PARAM_PARANOID", ((Object) (aobj10)));
        LinkedHashMap linkedhashmap11 = preferenceStorageMapping;
        Object aobj11[] = new Object[2];
        aobj11[0] = Integer.valueOf(11);
        aobj11[1] = "String";
        linkedhashmap11.put("PARAM_S_GROUP", ((Object) (aobj11)));
        LinkedHashMap linkedhashmap12 = preferenceStorageMapping;
        Object aobj12[] = new Object[2];
        aobj12[0] = Integer.valueOf(0);
        aobj12[1] = "bool";
        linkedhashmap12.put("FEATURE_SPEEDO", ((Object) (aobj12)));
        LinkedHashMap linkedhashmap13 = preferenceStorageMapping;
        Object aobj13[] = new Object[2];
        aobj13[0] = Integer.valueOf(7);
        aobj13[1] = "bool";
        linkedhashmap13.put("FEATURE_DEBUGMODEM", ((Object) (aobj13)));
        LinkedHashMap linkedhashmap14 = preferenceStorageMapping;
        Object aobj14[] = new Object[2];
        aobj14[0] = Integer.valueOf(8);
        aobj14[1] = "bool";
        linkedhashmap14.put("FEATURE_STREAM", ((Object) (aobj14)));
        LinkedHashMap linkedhashmap15 = preferenceStorageMapping;
        Object aobj15[] = new Object[2];
        aobj15[0] = Integer.valueOf(9);
        aobj15[1] = "List";
        linkedhashmap15.put("FEATURE_MINSOC", ((Object) (aobj15)));
        LinkedHashMap linkedhashmap16 = preferenceStorageMapping;
        Object aobj16[] = new Object[2];
        aobj16[0] = Integer.valueOf(15);
        aobj16[1] = "bool";
        linkedhashmap16.put("FEATURE_CANWRITE", ((Object) (aobj16)));
        cachedUIPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        addPreferencesFromResource(0x7f030006);
        setContentView(LayoutInflater.from(getParent()).inflate(0x7f030005, null));
        ((Button)findViewById(0x7f09000f)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                commitSettings();
            }

            final Tab_SubTabCarSettings this$0;

            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        ((Button)findViewById(0x7f09000e)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setMessage("This will consume about 5KB of wireless data.").setTitle("Retrieve data from car?").setCancelable(true).setPositiveButton("Retrieve", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        dialoginterface.dismiss();
                        requestSettings();
                    }

                    final _cls3 this$1;

                    
                    {
                        this$1 = _cls3.this;
                        super();
                    }
                }
).setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int i)
                    {
                        dialoginterface.dismiss();
                    }

                    final _cls3 this$1;

                    
                    {
                        this$1 = _cls3.this;
                        super();
                    }
                }
);
                AlertDialog alertdialog = builder.create();
                if(!isFinishing())
                    alertdialog.show();
            }

            final Tab_SubTabCarSettings this$0;


            
            {
                this$0 = Tab_SubTabCarSettings.this;
                super();
            }
        }
);
        Iterator iterator = preferenceStorageMapping.keySet().iterator();
        do
        {
            String s;
            Preference preference;
            do
            {
                if(!iterator.hasNext())
                {
                    wireUpPrefButtons();
                    return;
                }
                s = (String)iterator.next();
                preference = getPreferenceManager().findPreference(s);
                forceContext(mContext, preference);
            } while(!((Object[])preferenceStorageMapping.get(s))[1].equals("String") && !((Object[])preferenceStorageMapping.get(s))[1].equals("List"));
            wireUpDynamicMessage(preference, ((Object[])preferenceStorageMapping.get(s))[1].toString());
        } while(true);
    }

    protected void onPause()
    {
        super.onPause();
        if(toastDisplayed != null)
        {
            toastDisplayed.cancel();
            toastDisplayed = null;
        }
    }

    private SharedPreferences cachedUIPreferences;
    private CarData data;
    private Handler delayedRequest;
    private AlertDialog dialog;
    private ProgressDialog downloadProgress;
    private ServerCommands.CarLayoutDownloader downloadTask;
    private Handler handler;
    private boolean isLoggedIn;
    private Date lastDataRefreshed;
    private String lastVehicleID;
    private Context mContext;
    private OVMSActivity mOVMSActivity;
    private LinkedHashMap preferenceStorageMapping;
    private Toast toastDisplayed;
















}
