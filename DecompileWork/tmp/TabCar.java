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
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TabCar extends Activity
{
	public int CurrentScreenOrientation;
	private CarData data;
	private ProgressDialog downloadProgress;
	private ServerCommands.CarLayoutDownloader downloadTask;
	private Handler handler = new Handler()
	{
		public void handleMessage(Message paramAnonymousMessage)
		{
			TabCar.this.updateLastUpdatedView();
			((TextView)TabCar.this.findViewById(2131296281)).setText(TabCar.this.data.VehicleID);
			TextView localTextView1 = (TextView)TabCar.this.findViewById(2131296299);
			int i;
			if (TabCar.this.data.Data_LeftDoorOpen)
				i = 0;
			label132: label528: label2067: 
				while (true)
				{
					localTextView1.setVisibility(i);
					TextView localTextView2 = (TextView)TabCar.this.findViewById(2131296300);
					int j;
					label95: int k;
					int m;
					label169: int n;
					label206: String str2;
					label288: String str1;
					label303: int i1;
					label354: int i2;
					label441: int i3;
					int i4;
					label628: int i5;
					label702: int i6;
					label742: int i7;
					label832: int i8;
					label872: int i9;
					label962: int i10;
					label1002: int i11;
					label1092: int i12;
					label1132: label1279: int i13;
					label1309: int i14;
					label1346: int i15;
					label1383: int i16;
					int i17;
					label1457: int i18;
					label1495: int i19;
					label1533: int i20;
					label1570: int i21;
					label1607: ImageView localImageView11;
					if (TabCar.this.data.Data_RightDoorOpen)
					{
						j = 0;
						localTextView2.setVisibility(j);
						TextView localTextView3 = (TextView)TabCar.this.findViewById(2131296301);
						if (!TabCar.this.data.Data_ChargePortOpen)
							break label1703;
						k = 0;
						localTextView3.setVisibility(k);
						TextView localTextView4 = (TextView)TabCar.this.findViewById(2131296303);
						if (!TabCar.this.data.Data_BonnetOpen)
							break label1709;
						m = 0;
						localTextView4.setVisibility(m);
						TextView localTextView5 = (TextView)TabCar.this.findViewById(2131296302);
						if (!TabCar.this.data.Data_TrunkOpen)
							break label1715;
						n = 0;
						localTextView5.setVisibility(n);
						TextView localTextView6 = (TextView)TabCar.this.findViewById(2131296304);
						if (TabCar.this.data.Data_Speed <= 0.0D)
							break label1729;
						Object[] arrayOfObject11 = new Object[2];
						arrayOfObject11[0] = Integer.valueOf((int)TabCar.this.data.Data_Speed);
						if (!TabCar.this.data.Data_DistanceUnit.equals("K"))
							break label1721;
						str2 = "kph";
						arrayOfObject11[1] = str2;
						str1 = String.format("%d %s", arrayOfObject11);
						localTextView6.setText(str1);
						TextView localTextView7 = (TextView)TabCar.this.findViewById(2131296308);
						if ((TabCar.this.data.Data_CarPoweredON) || (TabCar.this.data.Data_CoolingPumpON_DoorState3))
							break label1737;
						i1 = -12303292;
						localTextView7.setTextColor(i1);
						Object[] arrayOfObject1 = new Object[1];
						arrayOfObject1[0] = Integer.valueOf((int)TabCar.this.data.Data_TemperaturePEM);
						localTextView7.setText(String.format("%d¡C", arrayOfObject1));
						TextView localTextView8 = (TextView)TabCar.this.findViewById(2131296309);
						if ((TabCar.this.data.Data_CarPoweredON) || (TabCar.this.data.Data_CoolingPumpON_DoorState3))
							break label1744;
						i2 = -12303292;
						localTextView8.setTextColor(i2);
						Object[] arrayOfObject2 = new Object[1];
						arrayOfObject2[0] = Integer.valueOf((int)TabCar.this.data.Data_TemperatureMotor);
						localTextView8.setText(String.format("%d¡C", arrayOfObject2));
						TextView localTextView9 = (TextView)TabCar.this.findViewById(2131296310);
						if ((TabCar.this.data.Data_CarPoweredON) || (TabCar.this.data.Data_CoolingPumpON_DoorState3))
							break label1751;
						i3 = -12303292;
						localTextView9.setTextColor(i3);
						Object[] arrayOfObject3 = new Object[1];
						arrayOfObject3[0] = Integer.valueOf((int)TabCar.this.data.Data_TemperatureBattery);
						localTextView9.setText(String.format("%d¡C", arrayOfObject3));
						TextView localTextView10 = (TextView)TabCar.this.findViewById(2131296311);
						if ((!TabCar.this.data.Data_AmbientTemperatureDataStale) && ((TabCar.this.data.Data_CarPoweredON) || (TabCar.this.data.Data_CoolingPumpON_DoorState3)))
							break label1758;
						i4 = -12303292;
						localTextView10.setTextColor(i4);
						Object[] arrayOfObject4 = new Object[1];
						arrayOfObject4[0] = Integer.valueOf((int)TabCar.this.data.Data_TemperatureAmbient);
						localTextView10.setText(String.format("%d¡C", arrayOfObject4));
						TextView localTextView11 = (TextView)TabCar.this.findViewById(2131296295);
						if (!TabCar.this.data.Data_TPMSDataStale)
							break label1765;
						i5 = -12303292;
						localTextView11.setTextColor(i5);
						if ((TabCar.this.data.Data_FLWheelPressure == 0.0D) && (TabCar.this.data.Data_FLWheelTemperature == 0.0D))
							break label1772;
						i6 = 0;
						localTextView11.setVisibility(i6);
						Object[] arrayOfObject5 = new Object[2];
						arrayOfObject5[0] = Double.valueOf(TabCar.this.data.Data_FLWheelPressure);
						arrayOfObject5[1] = Double.valueOf(TabCar.this.data.Data_FLWheelTemperature);
						localTextView11.setText(String.format("%.1fpsi\n%.0f¡C", arrayOfObject5));
						TextView localTextView12 = (TextView)TabCar.this.findViewById(2131296296);
						if (!TabCar.this.data.Data_TPMSDataStale)
							break label1778;
						i7 = -12303292;
						localTextView12.setTextColor(i7);
						if ((TabCar.this.data.Data_FRWheelPressure == 0.0D) && (TabCar.this.data.Data_FRWheelTemperature == 0.0D))
							break label1785;
						i8 = 0;
						localTextView12.setVisibility(i8);
						Object[] arrayOfObject6 = new Object[2];
						arrayOfObject6[0] = Double.valueOf(TabCar.this.data.Data_FRWheelPressure);
						arrayOfObject6[1] = Double.valueOf(TabCar.this.data.Data_FRWheelTemperature);
						localTextView12.setText(String.format("%.1fpsi\n%.0f¡C", arrayOfObject6));
						TextView localTextView13 = (TextView)TabCar.this.findViewById(2131296297);
						if (!TabCar.this.data.Data_TPMSDataStale)
							break label1791;
						i9 = -12303292;
						localTextView13.setTextColor(i9);
						if ((TabCar.this.data.Data_RLWheelPressure == 0.0D) && (TabCar.this.data.Data_RLWheelTemperature == 0.0D))
							break label1798;
						i10 = 0;
						localTextView13.setVisibility(i10);
						Object[] arrayOfObject7 = new Object[2];
						arrayOfObject7[0] = Double.valueOf(TabCar.this.data.Data_RLWheelPressure);
						arrayOfObject7[1] = Double.valueOf(TabCar.this.data.Data_RLWheelTemperature);
						localTextView13.setText(String.format("%.1fpsi\n%.0f¡C", arrayOfObject7));
						TextView localTextView14 = (TextView)TabCar.this.findViewById(2131296298);
						if (!TabCar.this.data.Data_TPMSDataStale)
							break label1804;
						i11 = -12303292;
						localTextView14.setTextColor(i11);
						if ((TabCar.this.data.Data_RRWheelPressure == 0.0D) && (TabCar.this.data.Data_RRWheelTemperature == 0.0D))
							break label1811;
						i12 = 0;
						localTextView14.setVisibility(i12);
						Object[] arrayOfObject8 = new Object[2];
						arrayOfObject8[0] = Double.valueOf(TabCar.this.data.Data_RRWheelPressure);
						arrayOfObject8[1] = Double.valueOf(TabCar.this.data.Data_RRWheelTemperature);
						localTextView14.setText(String.format("%.1fpsi\n%.0f¡C", arrayOfObject8));
						ImageView localImageView1 = (ImageView)TabCar.this.findViewById(2131296286);
						StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(TabCar.this.getCacheDir().getAbsolutePath()));
						Object[] arrayOfObject9 = new Object[1];
						arrayOfObject9[0] = TabCar.this.data.VehicleImageDrawable;
						Bitmap localBitmap = BitmapFactory.decodeFile(String.format("/ol_%s.png", arrayOfObject9));
						if (localBitmap == null)
							break label1817;
						localImageView1.setImageBitmap(localBitmap);
						ImageView localImageView2 = (ImageView)TabCar.this.findViewById(2131296287);
						if (!TabCar.this.data.Data_ChargePortOpen)
							break label2016;
						i13 = 0;
						localImageView2.setVisibility(i13);
						ImageView localImageView3 = (ImageView)TabCar.this.findViewById(2131296290);
						if (!TabCar.this.data.Data_BonnetOpen)
							break label2023;
						i14 = 0;
						localImageView3.setVisibility(i14);
						ImageView localImageView4 = (ImageView)TabCar.this.findViewById(2131296291);
						if (!TabCar.this.data.Data_LeftDoorOpen)
							break label2030;
						i15 = 0;
						localImageView4.setVisibility(i15);
						ImageView localImageView5 = (ImageView)TabCar.this.findViewById(2131296289);
						if (!TabCar.this.data.Data_RightDoorOpen)
							break label2037;
						i16 = 0;
						localImageView5.setVisibility(i16);
						ImageView localImageView6 = (ImageView)TabCar.this.findViewById(2131296288);
						if (!TabCar.this.data.Data_TrunkOpen)
							break label2044;
						i17 = 0;
						localImageView6.setVisibility(i17);
						ImageView localImageView7 = (ImageView)TabCar.this.findViewById(2131296292);
						if (!TabCar.this.data.Data_CarLocked)
							break label2051;
						i18 = 2130837563;
						localImageView7.setImageResource(i18);
						ImageView localImageView8 = (ImageView)TabCar.this.findViewById(2131296293);
						if (!TabCar.this.data.Data_ValetON)
							break label2059;
						i19 = 2130837566;
						localImageView8.setImageResource(i19);
						ImageView localImageView9 = (ImageView)TabCar.this.findViewById(2131296294);
						if (!TabCar.this.data.Data_HeadlightsON)
							break label2067;
						i20 = 0;
						localImageView9.setVisibility(i20);
						ImageView localImageView10 = (ImageView)TabCar.this.findViewById(2131296283);
						if (!TabCar.this.data.ParanoidMode)
							break label2074;
						i21 = 0;
						localImageView10.setVisibility(i21);
						localImageView11 = (ImageView)TabCar.this.findViewById(2131296285);
					}
					label1811: label1817: label2074: 
						try
					{
							int i23 = Integer.parseInt(TabCar.this.data.Data_CarModuleGSMSignalLevel);
							label1656: ImageView localImageView12;
							if (i23 < 1)
							{
								localImageView11.setImageResource(2130837608);
								localImageView12 = (ImageView)TabCar.this.findViewById(2131296282);
								if (!TabCar.this.isLoggedIn)
									break label2164;
							}
							label1703: label1709: label1715: label1721: label1729: label1737: label1744: label1751: label1758: label1765: label1772: label1778: label2164: for (int i22 = 8; ; i22 = 0)
							{
								localImageView12.setVisibility(i22);
								return;
								i = 4;
								break;
								j = 4;
								break label95;
								k = 4;
								break label132;
								m = 4;
								break label169;
								n = 4;
								break label206;
								str2 = "mph";
								break label288;
								str1 = "";
								break label303;
								i1 = -1;
								break label354;
								i2 = -1;
								break label441;
								i3 = -1;
								break label528;
								i4 = -1;
								break label628;
								i5 = -1;
								break label702;
								i6 = 4;
								break label742;
								i7 = -1;
								break label832;
								label1785: i8 = 4;
								break label872;
								label1791: i9 = -1;
								break label962;
								i10 = 4;
								break label1002;
								i11 = -1;
								break label1092;
								i12 = 4;
								break label1132;
								StringBuilder localStringBuilder2 = new StringBuilder("** File Not Found: ").append(TabCar.this.getCacheDir().getAbsolutePath());
								Object[] arrayOfObject10 = new Object[1];
								arrayOfObject10[0] = TabCar.this.data.VehicleImageDrawable;
								Log.d("OVMS", String.format("/ol_%s.png", arrayOfObject10));
								if ((TabCar.this.data.DontAskLayoutDownload) || ((TabCar.this.lastUpdatedDialog != null) && (TabCar.this.lastUpdatedDialog.isShowing())))
									break label1279;
								TabCar.this.data.DontAskLayoutDownload = true;
								AlertDialog.Builder localBuilder = new AlertDialog.Builder(TabCar.this);
								localBuilder.setMessage("Would you like to download a set of high resolution car images specifically drawn for your car?\n\nThe download is approx. 300KB.\n\nNote: a manual download button is available in the car commands and settings tab.").setTitle("Download Graphics").setCancelable(true).setPositiveButton("Download Now", new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
									{
										TabCar.this.downloadLayout();
										paramAnonymous2DialogInterface.dismiss();
									}
								}).setNegativeButton("Later", new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
									{
										paramAnonymous2DialogInterface.dismiss();
									}
								});
								TabCar.this.lastUpdatedDialog = localBuilder.create();
								TabCar.this.lastUpdatedDialog.show();
								break label1279;
								i13 = 8;
								break label1309;
								i14 = 8;
								break label1346;
								i15 = 8;
								break label1383;
								label2037: i16 = 8;
								break label1420;
								label2044: i17 = 8;
								break label1457;
								i18 = 2130837564;
								break label1495;
								i19 = 2130837565;
								break label1533;
								i20 = 8;
								break label1570;
								i21 = 8;
								break label1607;
								if (i23 < 7)
								{
									localImageView11.setImageResource(2130837609);
									break label1656;
								}
								if (i23 < 14)
								{
									localImageView11.setImageResource(2130837610);
									break label1656;
								}
								if (i23 < 21)
								{
									localImageView11.setImageResource(2130837611);
									break label1656;
								}
								if (i23 < 28)
								{
									localImageView11.setImageResource(2130837612);
									break label1656;
								}
								localImageView11.setImageResource(2130837613);
								break label1656;
							}
					}
					catch (Exception localException)
					{
						label2016: label2023: label2030: break label1656;
					}
				}
		}
	};
	private boolean isLoggedIn;
	private Runnable lastUpdateTimer = new Runnable()
	{
		public void run()
		{
			TabCar.this.updateLastUpdatedView();
			TabCar.this.lastUpdateTimerHandler.postDelayed(TabCar.this.lastUpdateTimer, 5000L);
		}
	};
	private Handler lastUpdateTimerHandler = new Handler();
	private AlertDialog lastUpdatedDialog;
	private Handler orientationChangedHandler = new Handler()
	{
		public void handleMessage(Message paramAnonymousMessage)
		{
			TabCar.this.setContentView(2130903050);
			TabCar.this.CurrentScreenOrientation = TabCar.this.getResources().getConfiguration().orientation;
			TabCar.this.initUI();
		}
	};

	private void downloadLayout()
	{
		this.downloadProgress = new ProgressDialog(this);
		this.downloadProgress.setMessage("Downloading Hi-Res Graphics");
		this.downloadProgress.setIndeterminate(true);
		this.downloadProgress.setMax(100);
		this.downloadProgress.setCancelable(true);
		this.downloadProgress.setProgressStyle(1);
		this.downloadProgress.show();
		this.downloadProgress.setOnDismissListener(new DialogInterface.OnDismissListener()
		{
			public void onDismiss(DialogInterface paramAnonymousDialogInterface)
			{
				StringBuilder localStringBuilder = new StringBuilder(String.valueOf(TabCar.this.getCacheDir().getAbsolutePath()));
				Object[] arrayOfObject = new Object[1];
				arrayOfObject[0] = TabCar.this.data.VehicleImageDrawable;
				Bitmap localBitmap = BitmapFactory.decodeFile(String.format("/ol_%s.png", arrayOfObject));
				if (localBitmap != null)
				{
					((ImageView)TabCar.this.findViewById(2131296286)).setImageBitmap(localBitmap);
					Toast.makeText(TabCar.this, "Graphics Downloaded", 0).show();
				}
				while (true)
				{
					return;
					Toast.makeText(TabCar.this, "Download Failed", 0).show();
				}
			}
		});
		this.downloadTask = new ServerCommands.CarLayoutDownloader(this.downloadProgress);
		ServerCommands.CarLayoutDownloader localCarLayoutDownloader = this.downloadTask;
		String[] arrayOfString = new String[2];
		arrayOfString[0] = this.data.VehicleImageDrawable;
		arrayOfString[1] = getCacheDir().getAbsolutePath();
		localCarLayoutDownloader.execute(arrayOfString);
	}

	private void initUI()
	{
		((TextView)findViewById(2131296284)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				String str = "-";
				if ((TabCar.this.data != null) && (TabCar.this.data.Data_LastCarUpdate != null))
					str = new SimpleDateFormat("MMM d, K:mm:ss a").format(TabCar.this.data.Data_LastCarUpdate);
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(TabCar.this);
				localBuilder.setMessage("Last update: " + str).setCancelable(true).setPositiveButton("Close", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
					{
						paramAnonymous2DialogInterface.dismiss();
					}
				}).setTitle(TabCar.this.data.VehicleID);
				TabCar.this.lastUpdatedDialog = localBuilder.create();
				TabCar.this.lastUpdatedDialog.show();
			}
		});
		((FrameLayout)findViewById(2131296305)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if (TabCar.this.isFinishing())
					return;
				TabCar localTabCar = TabCar.this;
				OVMSActivity localOVMSActivity = (OVMSActivity)TabCar.this.getParent();
				if (TabCar.this.data.Data_CarLocked);
				for (boolean bool = false; ; bool = true)
				{
					ServerCommands.LockUnlockCar(localTabCar, localOVMSActivity, null, bool);
					break;
				}
			}
		});
		((FrameLayout)findViewById(2131296306)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if (TabCar.this.isFinishing())
					return;
				TabCar localTabCar = TabCar.this;
				OVMSActivity localOVMSActivity = (OVMSActivity)TabCar.this.getParent();
				if (TabCar.this.data.Data_ValetON);
				for (boolean bool = false; ; bool = true)
				{
					ServerCommands.ValetModeOnOff(localTabCar, localOVMSActivity, null, bool);
					break;
				}
			}
		});
		((LinearLayout)findViewById(2131296307)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if ((TabCar.this.isFinishing()) || (TabCar.this.data.Data_CoolingPumpON_DoorState3));
				while (true)
				{
					return;
					ServerCommands.WakeUp(TabCar.this, (OVMSActivity)TabCar.this.getParent(), null, true);
				}
			}
		});
	}

	private void updateLastUpdatedView()
	{
		if ((this.data == null) || (this.data.Data_LastCarUpdate == null));
		while (true)
		{
			return;
			TextView localTextView = (TextView)findViewById(2131296284);
			long l = (new Date().getTime() - this.data.Data_LastCarUpdate.getTime()) / 1000L;
			if (l < 60L)
			{
				localTextView.setText("live");
			}
			else
			{
				if (l < 3600L)
				{
					int k = (int)Math.ceil(l / 60L);
					Object[] arrayOfObject4 = new Object[2];
					arrayOfObject4[0] = Integer.valueOf(k);
					if (k > 1);
					for (String str4 = "s"; ; str4 = "")
					{
						arrayOfObject4[1] = str4;
						localTextView.setText(String.format("Updated: %d min%s ago", arrayOfObject4));
						break;
					}
				}
				if (l < 86400L)
				{
					int j = (int)Math.ceil(l / 3600L);
					Object[] arrayOfObject3 = new Object[2];
					arrayOfObject3[0] = Integer.valueOf(j);
					if (j > 1);
					for (String str3 = "s"; ; str3 = "")
					{
						arrayOfObject3[1] = str3;
						localTextView.setText(String.format("Updated: %d hr%s ago", arrayOfObject3));
						break;
					}
				}
				if (l < 864000L)
				{
					int i = (int)Math.ceil(l / 86400L);
					Object[] arrayOfObject2 = new Object[2];
					arrayOfObject2[0] = Integer.valueOf(i);
					if (i > 1);
					for (String str2 = "s"; ; str2 = "")
					{
						arrayOfObject2[1] = str2;
						localTextView.setText(String.format("Updated: %d day%s ago", arrayOfObject2));
						break;
					}
				}
				String str1 = getString(2131099651);
				Object[] arrayOfObject1 = new Object[1];
				arrayOfObject1[0] = this.data.Data_LastCarUpdate;
				localTextView.setText(String.format(str1, arrayOfObject1));
			}
		}
	}

	public void OrientationChanged()
	{
		this.orientationChangedHandler.sendEmptyMessage(0);
	}

	public void Refresh(CarData paramCarData, boolean paramBoolean)
	{
		Log.d("Tab", "TabCar Refresh");
		this.data = paramCarData;
		this.isLoggedIn = paramBoolean;
		this.handler.sendEmptyMessage(0);
	}

	public void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(2130903050);
		initUI();
	}

	protected void onPause()
	{
		super.onPause();
		try
		{
			if ((this.lastUpdatedDialog != null) && (this.lastUpdatedDialog.isShowing()))
				this.lastUpdatedDialog.dismiss();
			label28: this.lastUpdateTimerHandler.removeCallbacks(this.lastUpdateTimer);
			return;
		}
		catch (Exception localException)
		{
			break label28;
		}
	}

	protected void onResume()
	{
		super.onResume();
		this.lastUpdateTimerHandler.postDelayed(this.lastUpdateTimer, 5000L);
	}
}