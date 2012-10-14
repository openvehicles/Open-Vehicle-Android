package com.openvehicles.OVMS;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Tab_SubTabCarSettings extends PreferenceActivity {
	private SharedPreferences cachedUIPreferences;
	private CarData data;
	private Handler delayedRequest = new Handler();
	private AlertDialog dialog;
	private ProgressDialog downloadProgress;
	private ServerCommands.CarLayoutDownloader downloadTask;
	private Handler handler = new Handler() {
		public void handleMessage(Message paramAnonymousMessage) {
			if (!Tab_SubTabCarSettings.this.lastVehicleID
					.equals(Tab_SubTabCarSettings.this.data.VehicleID)) {
				Tab_SubTabCarSettings.this.lastVehicleID = Tab_SubTabCarSettings.this.data.VehicleID;
				if ((Tab_SubTabCarSettings.this.data.Data_Parameters
						.containsKey(Integer.valueOf(8)))
						&& (Tab_SubTabCarSettings.this.data.Data_Features
								.containsKey(Integer.valueOf(15))))
					break label118;
				Log.d("SETTINGS",
						"No cached data found. Requesting data from module.");
				Tab_SubTabCarSettings.this.requestSettings();
			}
			label865: while (true) {
				return;
				if (Tab_SubTabCarSettings.this.lastDataRefreshed != Tab_SubTabCarSettings.this.data.Data_Features_LastRefreshed) {
					label118: if (Tab_SubTabCarSettings.this.toastDisplayed != null) {
						Tab_SubTabCarSettings.this.toastDisplayed.cancel();
						Tab_SubTabCarSettings.this.toastDisplayed = null;
					}
				Tab_SubTabCarSettings.this.lastDataRefreshed = Tab_SubTabCarSettings.this.data.Data_Features_LastRefreshed;
				TextView localTextView = (TextView) Tab_SubTabCarSettings.this
						.findViewById(2131296272);
				Iterator localIterator;
				if (Tab_SubTabCarSettings.this.data.Data_Parameters_LastRefreshed != null) {
					localTextView
					.setText(new SimpleDateFormat(
							"MMM d, K:mm:ss a")
					.format(Tab_SubTabCarSettings.this.data.Data_Parameters_LastRefreshed));
					localIterator = Tab_SubTabCarSettings.this.preferenceStorageMapping
							.keySet().iterator();
				}
				while (true) {
					label231: if (!localIterator.hasNext())
						break label865;
				String str = (String) localIterator.next();
				Preference localPreference = Tab_SubTabCarSettings.this
						.getPreferenceManager().findPreference(str);
				LinkedHashMap localLinkedHashMap;
				if (str.startsWith("PARAM"))
					localLinkedHashMap = Tab_SubTabCarSettings.this.data.Data_Parameters;
				while (true)
					if (((Object[]) Tab_SubTabCarSettings.this.preferenceStorageMapping
							.get(str))[1].equals("String")) {
						if (localLinkedHashMap
								.get(((Object[]) Tab_SubTabCarSettings.this.preferenceStorageMapping
										.get(str))[0]) == null) {
							((EditTextPreference) localPreference)
							.setText("");
							break label231;
							localTextView.setText("Never");
							break;
							if (str.startsWith("FEATURE")) {
								localLinkedHashMap = Tab_SubTabCarSettings.this.data.Data_Features;
								continue;
							}
							Log.d("ERROR",
									"Unrecognized settings key: " + str);
							break label231;
						}
						((EditTextPreference) localPreference)
						.setText((String) localLinkedHashMap
								.get(((Object[]) Tab_SubTabCarSettings.this.preferenceStorageMapping
										.get(str))[0]));
						break label231;
					}
				if (((Object[]) Tab_SubTabCarSettings.this.preferenceStorageMapping
						.get(str))[1].equals("bool")) {
					Object localObject = localLinkedHashMap
							.get(((Object[]) Tab_SubTabCarSettings.this.preferenceStorageMapping
									.get(str))[0]);
					CheckBoxPreference localCheckBoxPreference = (CheckBoxPreference) localPreference;
					if ((localObject != null)
							&& (localObject.toString().length() > 0)
							&& (!localObject.equals("0")))
						;
					for (boolean bool = true;; bool = false) {
						localCheckBoxPreference.setChecked(bool);
						break;
					}
				}
				if (Integer
						.parseInt(((Object[]) Tab_SubTabCarSettings.this.preferenceStorageMapping
								.get(str))[0].toString()) == 3) {
					if ((localLinkedHashMap.get(Integer.valueOf(3)) == null)
							|| ((((String) localLinkedHashMap
									.get(Integer.valueOf(3)))
									.contains("IP")) && (((String) localLinkedHashMap
											.get(Integer.valueOf(3)))
											.contains("SMS"))))
						((ListPreference) localPreference)
						.setValue("SMS,IP");
					else if (((String) localLinkedHashMap.get(Integer
							.valueOf(3))).contains("SMS"))
						((ListPreference) localPreference)
						.setValue("SMS");
					else
						((ListPreference) localPreference)
						.setValue("IP");
				} else if (Integer
						.parseInt(((Object[]) Tab_SubTabCarSettings.this.preferenceStorageMapping
								.get(str))[0].toString()) == 2) {
					if ((localLinkedHashMap.get(Integer.valueOf(2)) == null)
							|| (((String) localLinkedHashMap
									.get(Integer.valueOf(2)))
									.equals("K")))
						((ListPreference) localPreference)
						.setValue("K");
					else
						((ListPreference) localPreference)
						.setValue("M");
				} else if (Integer
						.parseInt(((Object[]) Tab_SubTabCarSettings.this.preferenceStorageMapping
								.get(str))[0].toString()) == 9)
					if ((localLinkedHashMap.get(Integer.valueOf(9)) != null)
							&& (((String) localLinkedHashMap
									.get(Integer.valueOf(9))).length() > 0))
						((ListPreference) localPreference)
						.setValue((String) localLinkedHashMap
								.get(Integer.valueOf(9)));
					else
						((ListPreference) localPreference)
						.setValue("30");
				}
				}
			}
		}
	};
	private boolean isLoggedIn = false;
	private Date lastDataRefreshed = null;
	private String lastVehicleID = "";
	private Context mContext;
	private OVMSActivity mOVMSActivity;
	private LinkedHashMap<String, Object[]> preferenceStorageMapping = new LinkedHashMap();
	private Toast toastDisplayed = null;

	private void commitSettings()
	{
		final LinkedHashMap localLinkedHashMap1 = new LinkedHashMap();
		Iterator localIterator1 = this.preferenceStorageMapping.keySet().iterator();
		while (true)
		{
			if (!localIterator1.hasNext())
			{
				if (localLinkedHashMap1.size() != 0)
					break;
				makeToast("Nothing has changed", 0);
				this.toastDisplayed.show();
				return;
			}
			String str1 = (String)localIterator1.next();
			Preference localPreference = getPreferenceManager().findPreference(str1);
			LinkedHashMap localLinkedHashMap2;
			label90: String str7;
			label258: String str8;
			label286: Object[] arrayOfObject3;
			String str5;
			if (str1.startsWith("PARAM"))
			{
				localLinkedHashMap2 = this.data.Data_Parameters;
				if (localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0]) == null)
					localLinkedHashMap2.put(Integer.valueOf(Integer.parseInt(((Object[])this.preferenceStorageMapping.get(str1))[0].toString())), "");
				if (!((Object[])this.preferenceStorageMapping.get(str1))[1].equals("String"))
					break label532;
				if (!((EditTextPreference)localPreference).getText().equals(localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0])))
				{
					Integer localInteger4 = (Integer)((Object[])this.preferenceStorageMapping.get(str1))[0];
					String[] arrayOfString4 = new String[3];
					arrayOfString4[0] = str1;
					if (((String)localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0])).trim().length() != 0)
						break label448;
					str7 = "<empty>";
					arrayOfString4[1] = str7;
					if (((EditTextPreference)localPreference).getText().trim().length() != 0)
						break label474;
					str8 = "<empty>";
					arrayOfString4[2] = str8;
					localLinkedHashMap1.put(localInteger4, arrayOfString4);
				}
				arrayOfObject3 = new Object[3];
				arrayOfObject3[0] = str1;
				if (((String)localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0])).trim().length() != 0)
					break label490;
				str5 = "<empty>";
				label347: arrayOfObject3[1] = str5;
				if (((EditTextPreference)localPreference).getText().trim().length() != 0)
					break label516;
			}
			label516: for (String str6 = "<empty>"; ; str6 = ((EditTextPreference)localPreference).getText().trim())
			{
				arrayOfObject3[2] = str6;
				Log.d("SETTINGS", String.format("%s : %s -> %s", arrayOfObject3));
				break;
				if (str1.startsWith("FEATURE"))
				{
					localLinkedHashMap2 = this.data.Data_Features;
					break label90;
				}
				Log.d("ERROR", "Unrecognized settings key: " + str1);
				break;
				label448: str7 = (String)localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0]);
				break label258;
				label474: str8 = ((EditTextPreference)localPreference).getText().trim();
				break label286;
				label490: str5 = (String)localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0]);
				break label347;
			}
			label532: if (((Object[])this.preferenceStorageMapping.get(str1))[1].equals("bool"))
			{
				Object localObject = localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0]);
				int i;
				label604: String str4;
				label711: Object[] arrayOfObject2;
				if ((localObject != null) && (localObject.toString().length() > 0) && (!localObject.equals("0")))
				{
					i = 1;
					if (((CheckBoxPreference)localPreference).isChecked() != i)
					{
						if (Integer.parseInt(((Object[])this.preferenceStorageMapping.get(str1))[0].toString()) != 10)
							break label812;
						Integer localInteger3 = (Integer)((Object[])this.preferenceStorageMapping.get(str1))[0];
						String[] arrayOfString3 = new String[3];
						arrayOfString3[0] = str1;
						arrayOfString3[1] = ((String)localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0]));
						if (!((CheckBoxPreference)localPreference).isChecked())
							break label805;
						str4 = "P";
						arrayOfString3[2] = str4;
						localLinkedHashMap1.put(localInteger3, arrayOfString3);
					}
					arrayOfObject2 = new Object[3];
					arrayOfObject2[0] = str1;
					arrayOfObject2[1] = localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0]);
					if (!((CheckBoxPreference)localPreference).isChecked())
						break label908;
				}
				for (String str2 = "1"; ; str2 = "0")
				{
					arrayOfObject2[2] = str2;
					Log.d("SETTINGS", String.format("%s : %s -> %s", arrayOfObject2));
					break;
					i = 0;
					break label604;
					label805: str4 = "";
					break label711;
					label812: Integer localInteger2 = (Integer)((Object[])this.preferenceStorageMapping.get(str1))[0];
					String[] arrayOfString2 = new String[3];
					arrayOfString2[0] = str1;
					arrayOfString2[1] = ((String)localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0]));
					if (((CheckBoxPreference)localPreference).isChecked());
					for (String str3 = "1"; ; str3 = "0")
					{
						arrayOfString2[2] = str3;
						localLinkedHashMap1.put(localInteger2, arrayOfString2);
						break;
					}
				}
			}
			label908: if (((Object[])this.preferenceStorageMapping.get(str1))[1].equals("List"))
			{
				if (!((ListPreference)localPreference).getValue().equals(localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0])))
				{
					Integer localInteger1 = (Integer)((Object[])this.preferenceStorageMapping.get(str1))[0];
					String[] arrayOfString1 = new String[3];
					arrayOfString1[0] = str1;
					arrayOfString1[1] = ((String)localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0]));
					arrayOfString1[2] = ((ListPreference)localPreference).getValue();
					localLinkedHashMap1.put(localInteger1, arrayOfString1);
				}
				Object[] arrayOfObject1 = new Object[3];
				arrayOfObject1[0] = str1;
				arrayOfObject1[1] = localLinkedHashMap2.get(((Object[])this.preferenceStorageMapping.get(str1))[0]);
				arrayOfObject1[2] = ((ListPreference)localPreference).getValue();
				Log.d("SETTINGS", String.format("%s : %s -> %s", arrayOfObject1));
			}
		}
		String str9 = "";
		Iterator localIterator2 = localLinkedHashMap1.keySet().iterator();
		while (true)
		{
			if (!localIterator2.hasNext())
			{
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.mContext);
				localBuilder.setMessage(str9).setTitle(getResources().getQuantityString(2131165184, localLinkedHashMap1.size())).setCancelable(true).setPositiveButton("Commit", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
					{
						paramAnonymousDialogInterface.dismiss();
						Tab_SubTabCarSettings.this.makeToast("Commiting - please wait...", 1);
						Iterator localIterator = localLinkedHashMap1.keySet().iterator();
						if (!localIterator.hasNext())
						{
							Tab_SubTabCarSettings.this.makeToast("Completed", 0);
							return;
						}
						int i = ((Integer)localIterator.next()).intValue();
						if (((String[])localLinkedHashMap1.get(Integer.valueOf(i)))[0].startsWith("PARAM"));
						for (String str = ServerCommands.SET_PARAMETER(i, ((String[])localLinkedHashMap1.get(Integer.valueOf(i)))[2]); ; str = ServerCommands.SET_FEATURE(i, ((String[])localLinkedHashMap1.get(Integer.valueOf(i)))[2]))
						{
							Tab_SubTabCarSettings.this.mOVMSActivity.SendServerCommand(str);
							break;
							if (!((String[])localLinkedHashMap1.get(Integer.valueOf(i)))[0].startsWith("FEATURE"))
								break;
						}
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
					{
						Tab_SubTabCarSettings.this.makeToast("Commit cancelled", 1);
						paramAnonymousDialogInterface.dismiss();
					}
				});
				AlertDialog localAlertDialog = localBuilder.create();
				if (isFinishing())
					break;
				localAlertDialog.show();
				break;
			}
			int j = ((Integer)localIterator2.next()).intValue();
			if (str9.length() > 0)
				str9 = str9 + "\n";
			StringBuilder localStringBuilder = new StringBuilder(String.valueOf(str9));
			Object[] arrayOfObject4 = new Object[3];
			arrayOfObject4[0] = ((String[])localLinkedHashMap1.get(Integer.valueOf(j)))[0].replace("PARAM_", "").replace("FEATURE_", "");
			arrayOfObject4[1] = ((String[])localLinkedHashMap1.get(Integer.valueOf(j)))[1];
			arrayOfObject4[2] = ((String[])localLinkedHashMap1.get(Integer.valueOf(j)))[2];
			str9 = String.format("%s: %s > %s", arrayOfObject4);
		}
	}

	private void downloadLayout() {
		this.downloadProgress = new ProgressDialog(this.mContext);
		this.downloadProgress.setMessage("Downloading Hi-Res Graphics");
		this.downloadProgress.setIndeterminate(true);
		this.downloadProgress.setMax(100);
		this.downloadProgress.setCancelable(true);
		this.downloadProgress.setProgressStyle(1);
		this.downloadProgress.show();
		this.downloadProgress
		.setOnDismissListener(new DialogInterface.OnDismissListener() {
			public void onDismiss(
					DialogInterface paramAnonymousDialogInterface) {
				StringBuilder localStringBuilder = new StringBuilder(
						String.valueOf(Tab_SubTabCarSettings.this.mContext
								.getCacheDir().getAbsolutePath()));
				Object[] arrayOfObject = new Object[1];
				arrayOfObject[0] = Tab_SubTabCarSettings.this.data.VehicleImageDrawable;
				if (BitmapFactory.decodeFile(String.format(
						"/ol_%s.png", arrayOfObject)) != null)
					Toast.makeText(Tab_SubTabCarSettings.this.mContext,
							"Graphics Downloaded", 0).show();
				while (true) {
					return;
					Toast.makeText(Tab_SubTabCarSettings.this.mContext,
							"Download Failed", 0).show();
				}
			}
		});
		this.downloadTask = new ServerCommands.CarLayoutDownloader(
				this.downloadProgress);
		ServerCommands.CarLayoutDownloader localCarLayoutDownloader = this.downloadTask;
		String[] arrayOfString = new String[2];
		arrayOfString[0] = this.data.VehicleImageDrawable;
		arrayOfString[1] = this.mContext.getCacheDir().getAbsolutePath();
		localCarLayoutDownloader.execute(arrayOfString);
	}

	private void forceContext(Context paramContext, Preference paramPreference) {
		try {
			Field localField = Preference.class.getDeclaredField("mContext");
			localField.setAccessible(true);
			localField.set(paramPreference, paramContext);
			return;
		} catch (Exception localException) {
			while (true)
				localException.printStackTrace();
		}
	}

	private String getSharedPreference(String paramString) {
		return this.cachedUIPreferences.getString(paramString, null);
	}

	private void makeToast(String paramString, int paramInt) {
		if (this.toastDisplayed != null) {
			this.toastDisplayed.cancel();
			this.toastDisplayed = null;
		}
		this.toastDisplayed = Toast.makeText(this.mContext, paramString,
				paramInt);
		this.toastDisplayed.show();
	}

	private void requestSettings() {
		int i = 0;
		String str;
		if (this.data.Data_CarModuleFirmwareVersion.length() >= 5)
			str = this.data.Data_CarModuleFirmwareVersion.substring(0, 5);
		try {
			Log.d("OVMS", "Current Firmware: " + str.replaceAll("\\.", ""));
			int j = Integer.parseInt(str.replaceAll("\\.", ""));
			i = j;
			label81: if (i < 119) {
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(
						this.mContext);
				localBuilder
				.setMessage(
						"Please upgrade vehicle module firmware to 1.1.9-exp3 or later.")
						.setTitle("Unsupported Firmware")
						.setCancelable(false)
						.setPositiveButton("Close",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymousDialogInterface,
									int paramAnonymousInt) {
								paramAnonymousDialogInterface.dismiss();
							}
						});
				AlertDialog localAlertDialog = localBuilder.create();
				if (!isFinishing())
					localAlertDialog.show();
			}
			while (true) {
				return;
				makeToast("Requesting data from car...", 1);
				this.mOVMSActivity.SendServerCommand("C3");
				Runnable local26 = new Runnable() {
					public void run() {
						Tab_SubTabCarSettings.this.mOVMSActivity
						.SendServerCommand("C1");
					}
				};
				this.delayedRequest.postDelayed(local26, 200L);
			}
		} catch (Exception localException) {
			break label81;
		}
	}

	private void setSharedPreference(String paramString1, String paramString2) {
		SharedPreferences.Editor localEditor = this.cachedUIPreferences.edit();
		localEditor.putString(paramString1, paramString2);
		localEditor.commit();
	}

	private void wireUpDynamicMessage(Preference paramPreference,
			String paramString) {
		paramPreference
		.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			public boolean onPreferenceChange(
					Preference paramAnonymousPreference,
					Object paramAnonymousObject) {
				paramAnonymousPreference
				.setSummary(paramAnonymousObject.toString());
				return true;
			}
		});
		if (paramString.equals("String"))
			paramPreference.setSummary(((EditTextPreference) paramPreference)
					.getText().toString());
		if (paramString.equals("List"))
			paramPreference.setSummary(((ListPreference) paramPreference)
					.getValue().toString());
	}

	private void wireUpPrefButtons() {
		findPreference("startcharge").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						if (Tab_SubTabCarSettings.this.isFinishing())
							;
						while (true) {
							return true;
							ServerCommands.StartCharge(
									Tab_SubTabCarSettings.this.mContext,
									Tab_SubTabCarSettings.this.mOVMSActivity,
									Tab_SubTabCarSettings.this.toastDisplayed);
						}
					}
				});
		findPreference("stopcharge").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						if (Tab_SubTabCarSettings.this.isFinishing())
							;
						while (true) {
							return true;
							ServerCommands.StopCharge(
									Tab_SubTabCarSettings.this.mContext,
									Tab_SubTabCarSettings.this.mOVMSActivity,
									Tab_SubTabCarSettings.this.toastDisplayed);
						}
					}
				});
		findPreference("chargemode").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						if (Tab_SubTabCarSettings.this.isFinishing())
							;
						while (true) {
							return true;
							ServerCommands.SetChargeMode(
									Tab_SubTabCarSettings.this.mContext,
									Tab_SubTabCarSettings.this.mOVMSActivity,
									Tab_SubTabCarSettings.this.toastDisplayed);
						}
					}
				});
		findPreference("lockcar").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						if (Tab_SubTabCarSettings.this.isFinishing())
							;
						while (true) {
							return true;
							ServerCommands.LockUnlockCar(
									Tab_SubTabCarSettings.this.mContext,
									Tab_SubTabCarSettings.this.mOVMSActivity,
									Tab_SubTabCarSettings.this.toastDisplayed,
									true);
						}
					}
				});
		findPreference("unlockcar").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						if (Tab_SubTabCarSettings.this.isFinishing())
							;
						while (true) {
							return true;
							ServerCommands.LockUnlockCar(
									Tab_SubTabCarSettings.this.mContext,
									Tab_SubTabCarSettings.this.mOVMSActivity,
									Tab_SubTabCarSettings.this.toastDisplayed,
									false);
						}
					}
				});
		findPreference("valeton").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						if (Tab_SubTabCarSettings.this.isFinishing())
							;
						while (true) {
							return true;
							ServerCommands.ValetModeOnOff(
									Tab_SubTabCarSettings.this.mContext,
									Tab_SubTabCarSettings.this.mOVMSActivity,
									Tab_SubTabCarSettings.this.toastDisplayed,
									true);
						}
					}
				});
		findPreference("valetoff").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						if (Tab_SubTabCarSettings.this.isFinishing())
							;
						while (true) {
							return true;
							ServerCommands.ValetModeOnOff(
									Tab_SubTabCarSettings.this.mContext,
									Tab_SubTabCarSettings.this.mOVMSActivity,
									Tab_SubTabCarSettings.this.toastDisplayed,
									false);
						}
					}
				});
		findPreference("setchargecurrent").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						if (Tab_SubTabCarSettings.this.isFinishing())
							;
						while (true) {
							return true;
							ServerCommands
							.SetChargeCurrent(
									Tab_SubTabCarSettings.this.mContext,
									Tab_SubTabCarSettings.this.mOVMSActivity,
									Tab_SubTabCarSettings.this.toastDisplayed,
									Tab_SubTabCarSettings.this.data.Data_ChargeAmpsLimit);
						}
					}
				});
		findPreference("wakeupcar").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						if (Tab_SubTabCarSettings.this.isFinishing())
							;
						while (true) {
							return true;
							ServerCommands.WakeUp(
									Tab_SubTabCarSettings.this.mContext,
									Tab_SubTabCarSettings.this.mOVMSActivity,
									Tab_SubTabCarSettings.this.toastDisplayed,
									false);
						}
					}
				});
		findPreference("wakeuptemps").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						if (Tab_SubTabCarSettings.this.isFinishing())
							;
						while (true) {
							return true;
							ServerCommands.WakeUp(
									Tab_SubTabCarSettings.this.mContext,
									Tab_SubTabCarSettings.this.mOVMSActivity,
									Tab_SubTabCarSettings.this.toastDisplayed,
									true);
						}
					}
				});
		findPreference("restartovms").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						AlertDialog.Builder localBuilder = new AlertDialog.Builder(
								Tab_SubTabCarSettings.this.mContext);
						localBuilder
						.setMessage(
								"A full reboot will be performed on the car module.")
								.setTitle("Reboot Car Module")
								.setCancelable(true)
								.setPositiveButton("Reboot",
										new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymous2DialogInterface,
											int paramAnonymous2Int) {
										Tab_SubTabCarSettings.this.mOVMSActivity
										.SendServerCommand("C5");
										Tab_SubTabCarSettings.this
										.makeToast(
												"Request sent",
												0);
										paramAnonymous2DialogInterface
										.dismiss();
									}
								})
								.setNegativeButton("Cancel",
										new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymous2DialogInterface,
											int paramAnonymous2Int) {
										paramAnonymous2DialogInterface
										.dismiss();
									}
								});
						AlertDialog localAlertDialog = localBuilder.create();
						if (!Tab_SubTabCarSettings.this.isFinishing())
							localAlertDialog.show();
						return true;
					}
				});
		findPreference("sendsms").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						View localView = LayoutInflater.from(
								Tab_SubTabCarSettings.this.mContext).inflate(
										2130903044, null);
						final EditText localEditText1 = (EditText) localView
								.findViewById(2131296268);
						final EditText localEditText2 = (EditText) localView
								.findViewById(2131296269);
						AlertDialog.Builder localBuilder = new AlertDialog.Builder(
								Tab_SubTabCarSettings.this.mContext);
						localBuilder
						.setTitle("Send SMS")
						.setView(localView)
						.setCancelable(true)
						.setPositiveButton("Send",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymous2DialogInterface,
									int paramAnonymous2Int) {
								String str1 = localEditText1
										.getText().toString()
										.trim();
								String str2 = localEditText2
										.getText().toString();
								if (str1.length() > 0) {
									Tab_SubTabCarSettings.this.mOVMSActivity
									.SendServerCommand(ServerCommands
											.SEND_SMS(
													str1,
													str2));
									Tab_SubTabCarSettings.this
									.makeToast(
											"Request sent",
											0);
									paramAnonymous2DialogInterface
									.dismiss();
								}
								while (true) {
									return;
									Tab_SubTabCarSettings.this
									.makeToast(
											"Invalid format",
											0);
								}
							}
						})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymous2DialogInterface,
									int paramAnonymous2Int) {
								paramAnonymous2DialogInterface
								.dismiss();
							}
						});
						AlertDialog localAlertDialog = localBuilder.create();
						if (!Tab_SubTabCarSettings.this.isFinishing())
							localAlertDialog.show();
						return true;
					}
				});
		findPreference("sendussd").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						final EditText localEditText = new EditText(
								Tab_SubTabCarSettings.this.mContext);
						AlertDialog.Builder localBuilder = new AlertDialog.Builder(
								Tab_SubTabCarSettings.this.mContext);
						localBuilder
						.setMessage("USSD (GSM feature code) to send:")
						.setTitle("Send USSD Code")
						.setCancelable(true)
						.setView(localEditText)
						.setPositiveButton("Send",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymous2DialogInterface,
									int paramAnonymous2Int) {
								Tab_SubTabCarSettings.this.mOVMSActivity
								.SendServerCommand(ServerCommands
										.SEND_USSD(localEditText
												.getText()
												.toString()));
								Tab_SubTabCarSettings.this
								.makeToast(
										"Request sent",
										0);
								paramAnonymous2DialogInterface
								.dismiss();
							}
						})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymous2DialogInterface,
									int paramAnonymous2Int) {
								paramAnonymous2DialogInterface
								.dismiss();
							}
						});
						AlertDialog localAlertDialog = localBuilder.create();
						if (!Tab_SubTabCarSettings.this.isFinishing())
							localAlertDialog.show();
						return true;
					}
				});
		findPreference("sendat").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						final EditText localEditText = new EditText(
								Tab_SubTabCarSettings.this.mContext);
						AlertDialog.Builder localBuilder = new AlertDialog.Builder(
								Tab_SubTabCarSettings.this.mContext);
						localBuilder
						.setMessage("AT command to send to the modem:")
						.setTitle("Send AT Modem")
						.setCancelable(true)
						.setView(localEditText)
						.setPositiveButton("Send",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymous2DialogInterface,
									int paramAnonymous2Int) {
								Tab_SubTabCarSettings.this.mOVMSActivity
								.SendServerCommand(ServerCommands
										.SEND_AT_COMMAND(localEditText
												.getText()
												.toString()));
								Tab_SubTabCarSettings.this
								.makeToast(
										"Request sent",
										0);
								paramAnonymous2DialogInterface
								.dismiss();
							}
						})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymous2DialogInterface,
									int paramAnonymous2Int) {
								paramAnonymous2DialogInterface
								.dismiss();
							}
						});
						AlertDialog localAlertDialog = localBuilder.create();
						if (!Tab_SubTabCarSettings.this.isFinishing())
							localAlertDialog.show();
						return true;
					}
				});
		findPreference("commslog").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						AlertDialog.Builder localBuilder = new AlertDialog.Builder(
								Tab_SubTabCarSettings.this.mContext);
						localBuilder
						.setMessage(DataLog.getLog())
						.setTitle("TCP Log")
						.setCancelable(true)
						.setPositiveButton("Close",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymous2DialogInterface,
									int paramAnonymous2Int) {
								paramAnonymous2DialogInterface
								.dismiss();
							}
						});
						AlertDialog localAlertDialog = localBuilder.create();
						if (!Tab_SubTabCarSettings.this.isFinishing())
							localAlertDialog.show();
						return true;
					}
				});
		findPreference("downloadgraphics").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						AlertDialog.Builder localBuilder = new AlertDialog.Builder(
								Tab_SubTabCarSettings.this.mContext);
						localBuilder
						.setMessage(
								"Re-download high resolution graphics now?\n\nThe download is approx. 300KB.")
								.setTitle("Download Graphics")
								.setCancelable(true)
								.setPositiveButton("Download",
										new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymous2DialogInterface,
											int paramAnonymous2Int) {
										Tab_SubTabCarSettings.this
										.downloadLayout();
										paramAnonymous2DialogInterface
										.dismiss();
									}
								})
								.setNegativeButton("Cancel",
										new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymous2DialogInterface,
											int paramAnonymous2Int) {
										Tab_SubTabCarSettings.this.data.DontAskLayoutDownload = true;
										paramAnonymous2DialogInterface
										.dismiss();
									}
								});
						Tab_SubTabCarSettings.this.dialog = localBuilder
								.create();
						Tab_SubTabCarSettings.this.dialog.show();
						return true;
					}
				});
		findPreference("reinitializec2dm").setOnPreferenceClickListener(
				new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(
							Preference paramAnonymousPreference) {
						AlertDialog.Builder localBuilder = new AlertDialog.Builder(
								Tab_SubTabCarSettings.this.mContext);
						localBuilder
						.setMessage(
								"Re-register the OVMS server with a new C2DM push notification ID.")
								.setTitle("Re-register Push Notifications")
								.setCancelable(true)
								.setPositiveButton("Re-register",
										new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymous2DialogInterface,
											int paramAnonymous2Int) {
										SharedPreferences.Editor localEditor = Tab_SubTabCarSettings.this.mOVMSActivity
												.getSharedPreferences(
														"C2DM", 0)
														.edit();
										localEditor.remove("RegID");
										localEditor.commit();
										ServerCommands
										.RequestC2DMRegistrationID(Tab_SubTabCarSettings.this.mOVMSActivity);
										Tab_SubTabCarSettings.this.mOVMSActivity
										.ReportC2DMRegistrationID();
										paramAnonymous2DialogInterface
										.dismiss();
									}
								})
								.setNegativeButton("Cancel",
										new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface paramAnonymous2DialogInterface,
											int paramAnonymous2Int) {
										Tab_SubTabCarSettings.this.data.DontAskLayoutDownload = true;
										paramAnonymous2DialogInterface
										.dismiss();
									}
								});
						Tab_SubTabCarSettings.this.dialog = localBuilder
								.create();
						Tab_SubTabCarSettings.this.dialog.show();
						return true;
					}
				});
		findPreference("FEATURE_DEBUGMODEM").setEnabled(false);
		Preference localPreference = findPreference("resetovms");
		localPreference.setEnabled(false);
		localPreference
		.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			public boolean onPreferenceClick(
					Preference paramAnonymousPreference) {
				final EditText localEditText = new EditText(
						Tab_SubTabCarSettings.this.mContext);
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(
						Tab_SubTabCarSettings.this.mContext);
				localBuilder
				.setMessage(
						"YOU ARE ABOUT TO FACTORY RESET YOUR OVMS CAR MODULE.\n\nAfter resetting, your module will remain offline and available from this app. You will need to manually send a SMS to restore the module's connection settings.\n\nTo reset, enter 12345678:")
						.setTitle("!! Factory Reset !!")
						.setCancelable(true)
						.setView(localEditText)
						.setPositiveButton("RESET",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymous2DialogInterface,
									int paramAnonymous2Int) {
								if (!localEditText.getText()
										.toString()
										.equals("12345678"))
									Tab_SubTabCarSettings.this
									.makeToast(
											"You must enter 12345678 to reset",
											0);
								paramAnonymous2DialogInterface
								.dismiss();
							}
						})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymous2DialogInterface,
									int paramAnonymous2Int) {
								paramAnonymous2DialogInterface
								.dismiss();
							}
						});
				AlertDialog localAlertDialog = localBuilder.create();
				if (!Tab_SubTabCarSettings.this.isFinishing())
					localAlertDialog.show();
				return true;
			}
		});
	}

	public void Refresh(CarData paramCarData, boolean paramBoolean) {
		this.data = paramCarData;
		this.handler.sendEmptyMessage(0);
	}

	public void onCreate(Bundle paramBundle) {
		this.mContext = getParent();
		super.onCreate(paramBundle);
		this.mOVMSActivity = ((OVMSActivity) getParent().getParent());
		if (this.mOVMSActivity == null)
			this.mOVMSActivity = ((OVMSActivity) getParent());
		if (this.mOVMSActivity == null)
			Toast.makeText(this, "Unknown Layout Error", 1).show();
		LinkedHashMap localLinkedHashMap1 = this.preferenceStorageMapping;
		Object[] arrayOfObject1 = new Object[2];
		arrayOfObject1[0] = Integer.valueOf(0);
		arrayOfObject1[1] = "String";
		localLinkedHashMap1.put("PARAM_REGPHONE", arrayOfObject1);
		LinkedHashMap localLinkedHashMap2 = this.preferenceStorageMapping;
		Object[] arrayOfObject2 = new Object[2];
		arrayOfObject2[0] = Integer.valueOf(1);
		arrayOfObject2[1] = "String";
		localLinkedHashMap2.put("PARAM_REGPASS", arrayOfObject2);
		LinkedHashMap localLinkedHashMap3 = this.preferenceStorageMapping;
		Object[] arrayOfObject3 = new Object[2];
		arrayOfObject3[0] = Integer.valueOf(2);
		arrayOfObject3[1] = "List";
		localLinkedHashMap3.put("PARAM_MILESKM", arrayOfObject3);
		LinkedHashMap localLinkedHashMap4 = this.preferenceStorageMapping;
		Object[] arrayOfObject4 = new Object[2];
		arrayOfObject4[0] = Integer.valueOf(3);
		arrayOfObject4[1] = "List";
		localLinkedHashMap4.put("PARAM_NOTIFIES", arrayOfObject4);
		LinkedHashMap localLinkedHashMap5 = this.preferenceStorageMapping;
		Object[] arrayOfObject5 = new Object[2];
		arrayOfObject5[0] = Integer.valueOf(4);
		arrayOfObject5[1] = "String";
		localLinkedHashMap5.put("PARAM_SERVERIP", arrayOfObject5);
		LinkedHashMap localLinkedHashMap6 = this.preferenceStorageMapping;
		Object[] arrayOfObject6 = new Object[2];
		arrayOfObject6[0] = Integer.valueOf(5);
		arrayOfObject6[1] = "String";
		localLinkedHashMap6.put("PARAM_GPRSAPN", arrayOfObject6);
		LinkedHashMap localLinkedHashMap7 = this.preferenceStorageMapping;
		Object[] arrayOfObject7 = new Object[2];
		arrayOfObject7[0] = Integer.valueOf(6);
		arrayOfObject7[1] = "String";
		localLinkedHashMap7.put("PARAM_GPRSUSER", arrayOfObject7);
		LinkedHashMap localLinkedHashMap8 = this.preferenceStorageMapping;
		Object[] arrayOfObject8 = new Object[2];
		arrayOfObject8[0] = Integer.valueOf(7);
		arrayOfObject8[1] = "String";
		localLinkedHashMap8.put("PARAM_GPRSPASS", arrayOfObject8);
		LinkedHashMap localLinkedHashMap9 = this.preferenceStorageMapping;
		Object[] arrayOfObject9 = new Object[2];
		arrayOfObject9[0] = Integer.valueOf(8);
		arrayOfObject9[1] = "String";
		localLinkedHashMap9.put("PARAM_MYID", arrayOfObject9);
		LinkedHashMap localLinkedHashMap10 = this.preferenceStorageMapping;
		Object[] arrayOfObject10 = new Object[2];
		arrayOfObject10[0] = Integer.valueOf(9);
		arrayOfObject10[1] = "String";
		localLinkedHashMap10.put("PARAM_NETPASS1", arrayOfObject10);
		LinkedHashMap localLinkedHashMap11 = this.preferenceStorageMapping;
		Object[] arrayOfObject11 = new Object[2];
		arrayOfObject11[0] = Integer.valueOf(10);
		arrayOfObject11[1] = "bool";
		localLinkedHashMap11.put("PARAM_PARANOID", arrayOfObject11);
		LinkedHashMap localLinkedHashMap12 = this.preferenceStorageMapping;
		Object[] arrayOfObject12 = new Object[2];
		arrayOfObject12[0] = Integer.valueOf(11);
		arrayOfObject12[1] = "String";
		localLinkedHashMap12.put("PARAM_S_GROUP", arrayOfObject12);
		LinkedHashMap localLinkedHashMap13 = this.preferenceStorageMapping;
		Object[] arrayOfObject13 = new Object[2];
		arrayOfObject13[0] = Integer.valueOf(0);
		arrayOfObject13[1] = "bool";
		localLinkedHashMap13.put("FEATURE_SPEEDO", arrayOfObject13);
		LinkedHashMap localLinkedHashMap14 = this.preferenceStorageMapping;
		Object[] arrayOfObject14 = new Object[2];
		arrayOfObject14[0] = Integer.valueOf(7);
		arrayOfObject14[1] = "bool";
		localLinkedHashMap14.put("FEATURE_DEBUGMODEM", arrayOfObject14);
		LinkedHashMap localLinkedHashMap15 = this.preferenceStorageMapping;
		Object[] arrayOfObject15 = new Object[2];
		arrayOfObject15[0] = Integer.valueOf(8);
		arrayOfObject15[1] = "bool";
		localLinkedHashMap15.put("FEATURE_STREAM", arrayOfObject15);
		LinkedHashMap localLinkedHashMap16 = this.preferenceStorageMapping;
		Object[] arrayOfObject16 = new Object[2];
		arrayOfObject16[0] = Integer.valueOf(9);
		arrayOfObject16[1] = "List";
		localLinkedHashMap16.put("FEATURE_MINSOC", arrayOfObject16);
		LinkedHashMap localLinkedHashMap17 = this.preferenceStorageMapping;
		Object[] arrayOfObject17 = new Object[2];
		arrayOfObject17[0] = Integer.valueOf(15);
		arrayOfObject17[1] = "bool";
		localLinkedHashMap17.put("FEATURE_CANWRITE", arrayOfObject17);
		this.cachedUIPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		addPreferencesFromResource(2130903046);
		setContentView(LayoutInflater.from(getParent()).inflate(2130903045,
				null));
		((Button) findViewById(2131296271))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				Tab_SubTabCarSettings.this.commitSettings();
			}
		});
		((Button) findViewById(2131296270))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(
						Tab_SubTabCarSettings.this.mContext);
				localBuilder
				.setMessage(
						"This will consume about 5KB of wireless data.")
						.setTitle("Retrieve data from car?")
						.setCancelable(true)
						.setPositiveButton("Retrieve",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymous2DialogInterface,
									int paramAnonymous2Int) {
								paramAnonymous2DialogInterface
								.dismiss();
								Tab_SubTabCarSettings.this
								.requestSettings();
							}
						})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface paramAnonymous2DialogInterface,
									int paramAnonymous2Int) {
								paramAnonymous2DialogInterface
								.dismiss();
							}
						});
				AlertDialog localAlertDialog = localBuilder.create();
				if (!Tab_SubTabCarSettings.this.isFinishing())
					localAlertDialog.show();
			}
		});
		Iterator localIterator = this.preferenceStorageMapping.keySet()
				.iterator();
		while (true) {
			if (!localIterator.hasNext()) {
				wireUpPrefButtons();
				return;
			}
			String str = (String) localIterator.next();
			Preference localPreference = getPreferenceManager().findPreference(
					str);
			forceContext(this.mContext, localPreference);
			if ((((Object[]) this.preferenceStorageMapping.get(str))[1]
					.equals("String"))
					|| (((Object[]) this.preferenceStorageMapping.get(str))[1]
							.equals("List")))
				wireUpDynamicMessage(localPreference,
						((Object[]) this.preferenceStorageMapping.get(str))[1]
								.toString());
		}
	}

	protected void onPause() {
		super.onPause();
		if (this.toastDisplayed != null) {
			this.toastDisplayed.cancel();
			this.toastDisplayed = null;
		}
	}
}