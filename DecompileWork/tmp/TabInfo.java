package com.openvehicles.OVMS;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TabInfo extends Activity
{
	public int CurrentScreenOrientation;
	private ServerCommands.CarLayoutDownloader downloadTask;
	private Handler handler = new Handler()
	{
		public void handleMessage(Message paramAnonymousMessage)
		{
			TabInfo.this.updateLastUpdatedView();
			((TextView)TabInfo.this.findViewById(2131296324)).setText(TabInfo.this.data.VehicleID);
			TextView localTextView1 = (TextView)TabInfo.this.findViewById(2131296334);
			String str1 = TabInfo.this.getString(2131099652);
			Object[] arrayOfObject1 = new Object[1];
			arrayOfObject1[0] = Integer.valueOf(TabInfo.this.data.Data_SOC);
			localTextView1.setText(String.format(str1, arrayOfObject1));
			TableRow localTableRow;
			int n;
			if (TabInfo.this.findViewById(2131296326) != null)
			{
				localTableRow = (TableRow)TabInfo.this.findViewById(2131296326);
				if (TabInfo.this.data.Data_ChargePortOpen)
					n = 0;
			}
			while (true)
			{
				localTableRow.setVisibility(n);
				label137: SeekBar localSeekBar = (SeekBar)TabInfo.this.findViewById(2131296330);
				TextView localTextView2 = (TextView)TabInfo.this.findViewById(2131296329);
				label218: TextView localTextView3;
				ImageView localImageView1;
				label323: int j;
				label476: int k;
				label513: ImageView localImageView4;
				if (TabInfo.this.data.Data_ChargeState.equals("charging"))
				{
					Object[] arrayOfObject11 = new Object[1];
					arrayOfObject11[0] = TabInfo.this.data.Data_ChargeMode.toUpperCase();
					localTextView2.setText(String.format("Charging - %s", arrayOfObject11));
					localTextView3 = (TextView)TabInfo.this.findViewById(2131296328);
					localImageView1 = (ImageView)TabInfo.this.findViewById(2131296332);
					if (!TabInfo.this.data.Data_Charging)
						break label1045;
					localSeekBar.setProgress(0);
					localImageView1.setVisibility(0);
					Object[] arrayOfObject6 = new Object[2];
					arrayOfObject6[0] = Integer.valueOf(TabInfo.this.data.Data_ChargeCurrent);
					arrayOfObject6[1] = Integer.valueOf(TabInfo.this.data.Data_LineVoltage);
					localTextView3.setText(String.format("%sA|%sV", arrayOfObject6));
					String str2 = " km";
					if ((TabInfo.this.data.Data_DistanceUnit != null) && (!TabInfo.this.data.Data_DistanceUnit.equals("K")))
						str2 = " miles";
					((TextView)TabInfo.this.findViewById(2131296336)).setText(TabInfo.this.data.Data_IdealRange + str2);
					((TextView)TabInfo.this.findViewById(2131296335)).setText(TabInfo.this.data.Data_EstimatedRange + str2);
					ImageView localImageView2 = (ImageView)TabInfo.this.findViewById(2131296321);
					if (!TabInfo.this.isLoggedIn)
						break label1097;
					j = 8;
					localImageView2.setVisibility(j);
					ImageView localImageView3 = (ImageView)TabInfo.this.findViewById(2131296322);
					if (!TabInfo.this.data.ParanoidMode)
						break label1103;
					k = 0;
					localImageView3.setVisibility(k);
					localImageView4 = (ImageView)TabInfo.this.findViewById(2131296285);
				}
				try
				{
					int m = Integer.parseInt(TabInfo.this.data.Data_CarModuleGSMSignalLevel);
					label562: ImageView localImageView5;
					if (m < 1)
					{
						localImageView4.setImageResource(2130837608);
						((ImageView)TabInfo.this.findViewById(2131296333)).getLayoutParams().width = (268 * TabInfo.this.data.Data_SOC / 100);
						localImageView5 = (ImageView)TabInfo.this.findViewById(2131296325);
						StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(TabInfo.this.getCacheDir().getAbsolutePath()));
						Object[] arrayOfObject4 = new Object[1];
						arrayOfObject4[0] = TabInfo.this.data.VehicleImageDrawable;
						Bitmap localBitmap = BitmapFactory.decodeFile(String.format("/%s.png", arrayOfObject4));
						if (localBitmap == null)
							break label1190;
						localImageView5.setImageBitmap(localBitmap);
					}
					while (true)
					{
						localImageView5.setOnClickListener(new View.OnClickListener()
						{
							public void onClick(View paramAnonymous2View)
							{
								AlertDialog.Builder localBuilder = new AlertDialog.Builder(TabInfo.this);
								Object[] arrayOfObject = new Object[10];
								String str1;
								String str2;
								label98: String str3;
								label123: String str4;
								label148: String str5;
								if (TabInfo.this.data.Data_CarPoweredON)
								{
									str1 = "ON";
									arrayOfObject[0] = str1;
									arrayOfObject[1] = TabInfo.this.data.Data_VIN;
									arrayOfObject[2] = TabInfo.this.data.Data_CarModuleGSMSignalLevel;
									if (!TabInfo.this.data.Data_HandBrakeApplied)
										break label307;
									str2 = "ENGAGED";
									arrayOfObject[3] = str2;
									if (!TabInfo.this.data.Data_ValetON)
										break label314;
									str3 = "ON";
									arrayOfObject[4] = str3;
									if (!TabInfo.this.data.Data_PINLocked)
										break label321;
									str4 = "ON";
									arrayOfObject[5] = str4;
									if (!TabInfo.this.data.Data_CoolingPumpON_DoorState3)
										break label328;
									str5 = "ON";
									label173: arrayOfObject[6] = str5;
									if (!TabInfo.this.data.Data_GPSLocked)
										break label335;
								}
								label307: label314: label321: label328: label335: for (String str6 = "LOCKED"; ; str6 = "(searching...)")
								{
									arrayOfObject[7] = str6;
									arrayOfObject[8] = TabInfo.this.data.Data_CarModuleFirmwareVersion;
									arrayOfObject[9] = TabInfo.this.data.Data_OVMSServerFirmwareVersion;
									localBuilder.setMessage(String.format("Power: %s\nVIN: %s\nGSM Signal: %s\nHandbrake: %s\nValet: %s\nLock: %s\nCooling Pump: %s\nGPS: %s\n\nCar Module: %s\nOVMS Server: %s", arrayOfObject)).setTitle("Vehicle Information").setCancelable(true).setPositiveButton("Close", new DialogInterface.OnClickListener()
									{
										public void onClick(DialogInterface paramAnonymous3DialogInterface, int paramAnonymous3Int)
										{
											paramAnonymous3DialogInterface.dismiss();
										}
									});
									TabInfo.this.softwareInformation = localBuilder.create();
									TabInfo.this.softwareInformation.show();
									return;
									str1 = "OFF";
									break;
									str2 = "DISENGAGED";
									break label98;
									str3 = "OFF";
									break label123;
									str4 = "OFF";
									break label148;
									str5 = "OFF";
									break label173;
								}
							}
						});
						return;
						n = 8;
						break;
						if (TabInfo.this.findViewById(2131296337) == null)
							break label137;
						RelativeLayout localRelativeLayout = (RelativeLayout)TabInfo.this.findViewById(2131296337);
						if (TabInfo.this.data.Data_ChargePortOpen);
						for (int i = 0; ; i = 8)
						{
							localRelativeLayout.setVisibility(i);
							break;
						}
						if (TabInfo.this.data.Data_ChargeState.equals("prepare"))
						{
							Object[] arrayOfObject10 = new Object[1];
							arrayOfObject10[0] = TabInfo.this.data.Data_ChargeMode.toUpperCase();
							localTextView2.setText(String.format("Preparing to Charge - %s", arrayOfObject10));
							break label218;
						}
						if (TabInfo.this.data.Data_ChargeState.equals("heating"))
						{
							Object[] arrayOfObject9 = new Object[1];
							arrayOfObject9[0] = TabInfo.this.data.Data_ChargeMode.toUpperCase();
							localTextView2.setText(String.format("Pre-Charge Battery Heating - %s", arrayOfObject9));
							break label218;
						}
						if (TabInfo.this.data.Data_ChargeState.equals("topoff"))
						{
							Object[] arrayOfObject8 = new Object[1];
							arrayOfObject8[0] = TabInfo.this.data.Data_ChargeMode.toUpperCase();
							localTextView2.setText(String.format("Topping Off - %s", arrayOfObject8));
							break label218;
						}
						if (TabInfo.this.data.Data_ChargeState.equals("stopped"))
						{
							Object[] arrayOfObject7 = new Object[1];
							arrayOfObject7[0] = TabInfo.this.data.Data_ChargeMode.toUpperCase();
							localTextView2.setText(String.format("Charge Interrupted - %s", arrayOfObject7));
							break label218;
						}
						if (!TabInfo.this.data.Data_ChargeState.equals("done"))
							break label218;
						Object[] arrayOfObject2 = new Object[1];
						arrayOfObject2[0] = TabInfo.this.data.Data_ChargeMode.toUpperCase();
						localTextView2.setText(String.format("Charge Completed - %s", arrayOfObject2));
						break label218;
						label1045: localSeekBar.setProgress(100);
						localImageView1.setVisibility(8);
						Object[] arrayOfObject3 = new Object[1];
						arrayOfObject3[0] = Integer.valueOf(TabInfo.this.data.Data_ChargeAmpsLimit);
						localTextView3.setText(String.format("%sA MAX", arrayOfObject3));
						break label323;
						label1097: j = 0;
						break label476;
						label1103: k = 8;
						break label513;
						if (m < 7)
						{
							localImageView4.setImageResource(2130837609);
							break label562;
						}
						if (m < 14)
						{
							localImageView4.setImageResource(2130837610);
							break label562;
						}
						if (m < 21)
						{
							localImageView4.setImageResource(2130837611);
							break label562;
						}
						if (m < 28)
						{
							localImageView4.setImageResource(2130837612);
							break label562;
						}
						localImageView4.setImageResource(2130837613);
						break label562;
						label1190: StringBuilder localStringBuilder2 = new StringBuilder("** File Not Found: ").append(TabInfo.this.getCacheDir().getAbsolutePath());
						Object[] arrayOfObject5 = new Object[1];
						arrayOfObject5[0] = TabInfo.this.data.VehicleImageDrawable;
						Log.d("OVMS", String.format("/%s.png", arrayOfObject5));
						if ((!TabInfo.this.data.DontAskLayoutDownload) && ((TabInfo.this.lastUpdatedDialog == null) || (!TabInfo.this.lastUpdatedDialog.isShowing())))
						{
							TabInfo.this.data.DontAskLayoutDownload = true;
							AlertDialog.Builder localBuilder = new AlertDialog.Builder(TabInfo.this);
							localBuilder.setMessage("Would you like to download a set of high resolution car images specifically drawn for your car?\n\nThe download is approx. 300KB.\n\nNote: a manual download button is available in the car commands and settings tab.").setTitle("Download Graphics").setCancelable(true).setPositiveButton("Download Now", new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
								{
									TabInfo.this.downloadLayout();
									paramAnonymous2DialogInterface.dismiss();
								}
							}).setNegativeButton("Later", new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
								{
									paramAnonymous2DialogInterface.dismiss();
								}
							});
							TabInfo.this.lastUpdatedDialog = localBuilder.create();
							TabInfo.this.lastUpdatedDialog.show();
						}
					}
				}
				catch (Exception localException)
				{
					break label562;
				}
			}
		}
	};
	private boolean isLoggedIn;
	private Handler orientationChangedHandler = new Handler()
	{
		public void handleMessage(Message paramAnonymousMessage)
		{
			TabInfo.this.setContentView(2130903054);
			TabInfo.this.CurrentScreenOrientation = TabInfo.this.getResources().getConfiguration().orientation;
			TabInfo.this.initUI();
		}
	};
	private AlertDialog softwareInformation;

	private void initUI()
	{
		((TextView)findViewById(2131296323)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if (TabInfo.this.data.Data_LastCarUpdate != null)
				{
					String str = new SimpleDateFormat("MMM d, K:mm:ss a").format(TabInfo.this.data.Data_LastCarUpdate);
					AlertDialog.Builder localBuilder = new AlertDialog.Builder(TabInfo.this);
					localBuilder.setMessage("Last update: " + str).setCancelable(true).setPositiveButton("Close", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
						{
							paramAnonymous2DialogInterface.dismiss();
						}
					}).setTitle(TabInfo.this.data.VehicleID);
					TabInfo.this.lastUpdatedDialog = localBuilder.create();
					TabInfo.this.lastUpdatedDialog.show();
				}
			}
		});
		((TextView)findViewById(2131296320)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if (TabInfo.this.data.Data_ParkedTime != null)
				{
					String str = new SimpleDateFormat("MMM d, K:mm:ss a").format(TabInfo.this.data.Data_ParkedTime);
					AlertDialog.Builder localBuilder = new AlertDialog.Builder(TabInfo.this);
					localBuilder.setMessage("Parked since: " + str).setCancelable(true).setPositiveButton("Close", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
						{
							paramAnonymous2DialogInterface.dismiss();
						}
					}).setTitle(TabInfo.this.data.VehicleID);
					TabInfo.this.lastUpdatedDialog = localBuilder.create();
					TabInfo.this.lastUpdatedDialog.show();
				}
			}
		});
		((TextView)findViewById(2131296329)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				ServerCommands.SetChargeMode(TabInfo.this, (OVMSActivity)TabInfo.this.getParent(), null);
			}
		});
		((TextView)findViewById(2131296334)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				ServerCommands.SetChargeCurrent(TabInfo.this, (OVMSActivity)TabInfo.this.getParent(), null, TabInfo.this.data.Data_ChargeAmpsLimit);
			}
		});
		((SeekBar)findViewById(2131296330)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
			{
				paramAnonymousSeekBar.setProgress(paramAnonymousInt);
			}

			public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar)
			{
			}

			public void onStopTrackingTouch(final SeekBar paramAnonymousSeekBar)
			{
				int i = 0;
				if (paramAnonymousSeekBar.getProgress() < 25)
				{
					paramAnonymousSeekBar.setProgress(0);
					if (TabInfo.this.data.Data_Charging)
						Toast.makeText(TabInfo.this, "Already charging...", 0).show();
				}
				while (true)
				{
					return;
					ServerCommands.StartCharge(TabInfo.this, (OVMSActivity)TabInfo.this.getParent(), null).setOnCancelListener(new DialogInterface.OnCancelListener()
					{
						public void onCancel(DialogInterface paramAnonymous2DialogInterface)
						{
							paramAnonymousSeekBar.setProgress(paramAnonymousSeekBar.getMax());
						}
					});
					continue;
					if (paramAnonymousSeekBar.getProgress() <= -25 + paramAnonymousSeekBar.getMax())
						break;
					paramAnonymousSeekBar.setProgress(paramAnonymousSeekBar.getMax());
					if (!TabInfo.this.data.Data_Charging)
						Toast.makeText(TabInfo.this, "Already stopped...", 0).show();
					else
						ServerCommands.StopCharge(TabInfo.this, (OVMSActivity)TabInfo.this.getParent(), null).setOnCancelListener(new DialogInterface.OnCancelListener()
						{
							public void onCancel(DialogInterface paramAnonymous2DialogInterface)
							{
								paramAnonymousSeekBar.setProgress(0);
							}
						});
				}
				if (TabInfo.this.data.Data_Charging);
				while (true)
				{
					paramAnonymousSeekBar.setProgress(i);
					break;
					i = 100;
				}
			}
		});
	}

}
