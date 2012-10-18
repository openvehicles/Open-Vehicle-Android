package com.openvehicles.OVMS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.Toast;

public class OVMSActivity extends TabActivity
implements TabHost.OnTabChangeListener
{
	public int DeviceScreenSize;
	public final int OVMS_CONFIG_FILE_VERSION = 1;
	public final int SCREENLAYOUT_SIZE_LARGE = 3;
	public final int SCREENLAYOUT_SIZE_XLARGE = 4;
	public boolean SuppressServerErrorDialog = false;
	private Handler UIHandler = new Handler();
	private AlertDialog alertDialog;
	private ArrayList<CarData> allSavedCars;
	private Handler c2dmReportTimerHandler = new Handler();
	private CarData carData;
	private Handler delayedRequest = new Handler();
	private boolean isLoggedIn;
	private Exception lastServerException;
	private ServerCommandResponseHandler mCommandResponse;
	private Runnable mRecreateChildTabLayout = new Runnable()
	{
		public void run()
		{
			String str = OVMSActivity.this.getLocalActivityManager().getCurrentId().trim();
			Log.d("Tab", "Tab recreate: " + str);
			if ((str == null) || (OVMSActivity.this.getLocalActivityManager().getActivity(str) == null));
			while (true)
			{
				return;
				if (str.equals("tabInfo_xlarge"))
				{
					TabInfo_xlarge localTabInfo_xlarge = (TabInfo_xlarge)OVMSActivity.this.getLocalActivityManager().getActivity(str);
					localTabInfo_xlarge.OrientationChanged();
					localTabInfo_xlarge.Refresh(OVMSActivity.this.carData, OVMSActivity.this.isLoggedIn);
				}
				else if (str.equals("tabInfo"))
				{
					TabInfo localTabInfo = (TabInfo)OVMSActivity.this.getLocalActivityManager().getActivity(str);
					localTabInfo.OrientationChanged();
					localTabInfo.Refresh(OVMSActivity.this.carData, OVMSActivity.this.isLoggedIn);
				}
				else if (str.equals("tabCar"))
				{
					TabCar localTabCar = (TabCar)OVMSActivity.this.getLocalActivityManager().getActivity(str);
					localTabCar.OrientationChanged();
					localTabCar.Refresh(OVMSActivity.this.carData, OVMSActivity.this.isLoggedIn);
				}
			}
		}
	};
	private Runnable mRefresh = new Runnable()
	{
		private void notifyTabRefresh(String paramAnonymousString)
		{
			Log.d("Tab", "Tab refresh: " + paramAnonymousString);
			if ((paramAnonymousString == null) || (OVMSActivity.this.getLocalActivityManager().getActivity(paramAnonymousString) == null))
				return;
			if (OVMSActivity.this.DeviceScreenSize == 4)
				if (paramAnonymousString.equals("tabInfo_xlarge"))
				{
					TabInfo_xlarge localTabInfo_xlarge = (TabInfo_xlarge)OVMSActivity.this.getLocalActivityManager().getActivity(paramAnonymousString);
					if (localTabInfo_xlarge.CurrentScreenOrientation != OVMSActivity.this.getResources().getConfiguration().orientation)
						localTabInfo_xlarge.OrientationChanged();
					localTabInfo_xlarge.Refresh(OVMSActivity.this.carData, OVMSActivity.this.isLoggedIn);
				}
			while (true)
			{
				OVMSActivity.this.getTabHost().invalidate();
				break;
				if (paramAnonymousString.equals("tabMap"))
				{
					((TabMap)OVMSActivity.this.getLocalActivityManager().getActivity(paramAnonymousString)).Refresh(OVMSActivity.this.carData, OVMSActivity.this.isLoggedIn);
				}
				else if (paramAnonymousString.equals("tabNotifications"))
				{
					((Tab_SubTabNotifications)OVMSActivity.this.getLocalActivityManager().getActivity(paramAnonymousString)).Refresh(OVMSActivity.this.carData, OVMSActivity.this.isLoggedIn);
				}
				else if (paramAnonymousString.equals("tabDataUtilizations"))
				{
					((Tab_SubTabDataUtilizations)OVMSActivity.this.getLocalActivityManager().getActivity(paramAnonymousString)).Refresh(OVMSActivity.this.carData, OVMSActivity.this.isLoggedIn);
				}
				else if (paramAnonymousString.equals("tabCarSettings"))
				{
					((Tab_SubTabCarSettings)OVMSActivity.this.getLocalActivityManager().getActivity(paramAnonymousString)).Refresh(OVMSActivity.this.carData, OVMSActivity.this.isLoggedIn);
				}
				else if (paramAnonymousString.equals("tabCars"))
				{
					((TabCars)OVMSActivity.this.getLocalActivityManager().getActivity(paramAnonymousString)).LoadCars(OVMSActivity.this.allSavedCars);
					continue;
					if (paramAnonymousString.equals("tabInfo"))
					{
						TabInfo localTabInfo = (TabInfo)OVMSActivity.this.getLocalActivityManager().getActivity(paramAnonymousString);
						if (localTabInfo.CurrentScreenOrientation != OVMSActivity.this.getResources().getConfiguration().orientation)
							localTabInfo.OrientationChanged();
						localTabInfo.Refresh(OVMSActivity.this.carData, OVMSActivity.this.isLoggedIn);
					}
					else if (paramAnonymousString.equals("tabCar"))
					{
						TabCar localTabCar = (TabCar)OVMSActivity.this.getLocalActivityManager().getActivity(paramAnonymousString);
						if (localTabCar.CurrentScreenOrientation != OVMSActivity.this.getResources().getConfiguration().orientation)
							localTabCar.OrientationChanged();
						localTabCar.Refresh(OVMSActivity.this.carData, OVMSActivity.this.isLoggedIn);
					}
					else if (paramAnonymousString.equals("tabMap"))
					{
						((TabMap)OVMSActivity.this.getLocalActivityManager().getActivity(paramAnonymousString)).Refresh(OVMSActivity.this.carData, OVMSActivity.this.isLoggedIn);
					}
					else if (paramAnonymousString.equals("tabMiscFeatures"))
					{
						((TabMiscFeatures)OVMSActivity.this.getLocalActivityManager().getActivity(paramAnonymousString)).Refresh(OVMSActivity.this.carData, OVMSActivity.this.isLoggedIn);
					}
					else if (paramAnonymousString.equals("tabCars"))
					{
						((TabCars)OVMSActivity.this.getLocalActivityManager().getActivity(paramAnonymousString)).LoadCars(OVMSActivity.this.allSavedCars);
					}
					else
					{
						OVMSActivity.this.getTabHost().setCurrentTab(0);
					}
				}
			}
		}

		public void run()
		{
			if (OVMSActivity.this.isLoggedIn)
			{
				if ((OVMSActivity.this.progressLogin != null) && (OVMSActivity.this.progressLogin.isShowing()))
					OVMSActivity.this.progressLogin.dismiss();
				if ((OVMSActivity.this.alertDialog != null) && (OVMSActivity.this.alertDialog.isShowing()))
					OVMSActivity.this.alertDialog.dismiss();
			}
			notifyTabRefresh(OVMSActivity.this.getLocalActivityManager().getCurrentId().trim());
		}
	};
	private Runnable pingServer = new Runnable()
	{
		public void run()
		{
			if (OVMSActivity.this.isLoggedIn)
			{
				Log.d("OVMS", "Pinging server...");
				OVMSActivity.this.tcpTask.Ping();
			}
			OVMSActivity.this.pingServerTimerHandler.postDelayed(OVMSActivity.this.pingServer, 60000L);
		}
	};
	private Handler pingServerTimerHandler = new Handler();
	private ProgressDialog progressLogin = null;
	private Runnable progressLoginCloseDialog = new Runnable()
	{
		public void run()
		{
			try
			{
				if (OVMSActivity.this.progressLogin != null)
					OVMSActivity.this.progressLogin.dismiss();
				label20: return;
			}
			catch (Exception localException)
			{
				break label20;
			}
		}
	};
	private Runnable progressLoginShowDialog = new Runnable()
	{
		public void run()
		{
			try
			{
				if (OVMSActivity.this.progressLogin != null)
					OVMSActivity.this.progressLogin.dismiss();
				try
				{
					label20: if (OVMSActivity.this.alertDialog != null)
						OVMSActivity.this.alertDialog.dismiss();
				try
				{
					label40: OVMSActivity.this.progressLogin = new ProgressDialog(OVMSActivity.this);
				OVMSActivity.this.progressLogin.setIndeterminate(true);
				OVMSActivity.this.progressLogin.setMessage("Connecting to OVMS Server...");
				OVMSActivity.this.progressLogin.getWindow().clearFlags(2);
				OVMSActivity.this.progressLogin.show();
				label105: return;
				}
				catch (Exception localException3)
				{
					break label105;
				}
				}
				catch (Exception localException2)
				{
					break label40;
				}
			}
			catch (Exception localException1)
			{
				break label20;
			}
		}
	};
	private Runnable reportC2DMRegistrationID = new Runnable()
	{
		public void run()
		{
			if (OVMSActivity.this.tcpTask == null);
			while (true)
			{
				return;
				SharedPreferences localSharedPreferences = OVMSActivity.this.getSharedPreferences("C2DM", 0);
				String str1 = localSharedPreferences.getString("RegID", "");
				String str2;
				if (!localSharedPreferences.contains("UUID"))
				{
					str2 = UUID.randomUUID().toString();
					SharedPreferences.Editor localEditor = OVMSActivity.this.getSharedPreferences("C2DM", 0).edit();
					localEditor.putString("UUID", str2);
					localEditor.commit();
					Log.d("OVMS", "Generated New App ID: " + str2);
				}
				while (true)
				{
					if (str1.length() != 0)
						break label184;
					Log.d("C2DM", "C2DM registration ID not found. Rescheduling.");
					OVMSActivity.this.c2dmReportTimerHandler.postDelayed(OVMSActivity.this.reportC2DMRegistrationID, 15000L);
					break;
					str2 = localSharedPreferences.getString("UUID", "");
					Log.d("OVMS", "Loaded Saved App ID: " + str2);
				}
				label184: if (!OVMSActivity.this.SendServerCommand(ServerCommands.SUBSCRIBE_PUSH_NOTIFICATIONS(str2, OVMSActivity.this.carData.VehicleID, OVMSActivity.this.carData.NetPass, str1.trim())))
				{
					Log.d("OVMS", "Reporting C2DM ID failed. Rescheduling.");
					OVMSActivity.this.c2dmReportTimerHandler.postDelayed(OVMSActivity.this.reportC2DMRegistrationID, 5000L);
				}
			}
		}
	};
	private Runnable serverSocketErrorDialog = new Runnable()
	{
		public void run()
		{
			if (OVMSActivity.this.SuppressServerErrorDialog);
			while (true)
			{
				return;
				if ((OVMSActivity.this.alertDialog != null) && (OVMSActivity.this.alertDialog.isShowing()))
					continue;
				try
				{
					if (OVMSActivity.this.progressLogin != null)
						OVMSActivity.this.progressLogin.dismiss();
					label54: AlertDialog.Builder localBuilder = new AlertDialog.Builder(OVMSActivity.this);
					if (OVMSActivity.this.isLoggedIn);
					for (String str = String.format("OVMS Server Connection Lost", new Object[0]); ; str = String.format("Please check the following:\n1. OVMS Server address\n2. Your vehicle ID and passwords", new Object[0]))
					{
						while (true)
						{
							localBuilder.setMessage(str).setTitle("Connection Problem").setCancelable(false).setPositiveButton("Retry", new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
								{
									OVMSActivity.this.ChangeCar(OVMSActivity.this.carData);
									paramAnonymous2DialogInterface.dismiss();
								}
							}).setNegativeButton("Open Settings", new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
								{
									OVMSActivity.this.getTabHost().setCurrentTabByTag("tabCars");
									paramAnonymous2DialogInterface.dismiss();
								}
							});
							OVMSActivity.this.alertDialog = localBuilder.create();
							try
							{
								OVMSActivity.this.alertDialog.show();
							}
							catch (Exception localException2)
							{
							}
						}
						break;
					}
				}
				catch (Exception localException1)
				{
					break label54;
				}
			}
		}
	};
	private final String settingsFileName = "OVMSSavedCars.obj";
	private TCPTask tcpTask;

	private void initializeSavedCars()
	{
		Log.d("OVMS", "Invalid save file. Initializing with demo car.");
		this.allSavedCars = new ArrayList();
		CarData localCarData = new CarData();
		localCarData.VehicleID = "DEMO";
		localCarData.RegPass = "DEMO";
		localCarData.NetPass = "DEMO";
		localCarData.ServerNameOrIP = "tmc.openvehicles.com";
		localCarData.VehicleImageDrawable = "car_models_signaturered";
		localCarData.lastResetVersion = 1;
		this.allSavedCars.add(localCarData);
		this.carData = localCarData;
		saveCars();
	}

	private void loadCars()
	{
		try
		{
			Log.d("OVMS", "Loading saved cars from internal storage file: OVMSSavedCars.obj");
			ObjectInputStream localObjectInputStream = new ObjectInputStream(openFileInput("OVMSSavedCars.obj"));
			this.allSavedCars = ((ArrayList)localObjectInputStream.readObject());
			localObjectInputStream.close();
			Iterator localIterator = this.allSavedCars.iterator();
			while (true)
			{
				if (!localIterator.hasNext())
				{
					str = getSharedPreferences("OVMS", 0).getString("LastVehicleID", "").trim();
					if (str.length() != 0)
						break label271;
					this.carData = ((CarData)this.allSavedCars.get(0));
					return;
				}
				localCarData1 = (CarData)localIterator.next();
				if ((localCarData1.VehicleID != null) && (localCarData1.RegPass != null) && (localCarData1.NetPass != null) && (localCarData1.ServerNameOrIP != null) && (localCarData1.VehicleImageDrawable != null))
					break;
				initializeSavedCars();
			}
		}
		catch (Exception localException)
		{
			String str;
			while (true)
			{
				CarData localCarData1;
				localException.printStackTrace();
				initializeSavedCars();
				break;
				if (localCarData1.lastResetVersion != 1)
				{
					CarData localCarData2 = new CarData();
					localCarData2.VehicleID = localCarData1.VehicleID;
					localCarData2.RegPass = localCarData1.RegPass;
					localCarData2.NetPass = localCarData1.NetPass;
					localCarData2.ServerNameOrIP = localCarData1.ServerNameOrIP;
					localCarData2.VehicleImageDrawable = localCarData1.VehicleImageDrawable;
					localCarData2.lastResetVersion = 1;
					this.allSavedCars.set(this.allSavedCars.indexOf(localCarData1), localCarData2);
				}
			}
			label271: Object[] arrayOfObject = new Object[2];
			arrayOfObject[0] = Integer.valueOf(this.allSavedCars.size());
			arrayOfObject[1] = str;
			Log.d("OVMS", String.format("Loaded %s cars. Last used car is %s", arrayOfObject));
			for (int i = 0; ; i++)
			{
				if (i >= this.allSavedCars.size());
				while (true)
				{
					if (this.carData != null)
						return;
					this.carData = ((CarData)this.allSavedCars.get(0));
					return;
					if (!((CarData)this.allSavedCars.get(i)).VehicleID.equals(str))
						break;
					this.carData = ((CarData)this.allSavedCars.get(i));
				}
			}
		}
	}

	private void loginComplete()
	{
		this.isLoggedIn = true;
		this.UIHandler.post(this.progressLoginCloseDialog);
		ReportC2DMRegistrationID();
		if (((String)this.carData.Data_Parameters.get(Integer.valueOf(11))).length() > 0)
			SendServerCommand(ServerCommands.SUBSCRIBE_GROUP((String)this.carData.Data_Parameters.get(Integer.valueOf(11))));
		while (true)
		{
			return;
			SendServerCommand("C3");
			Runnable local8 = new Runnable()
			{
				public void run()
				{
					OVMSActivity.this.SendServerCommand("C1");
				}
			};
			this.delayedRequest.postDelayed(local8, 200L);
		}
	}

	private void notifyServerSocketError(Exception paramException)
	{
		this.lastServerException = paramException;
		if (paramException != null)
			paramException.printStackTrace();
		if (!this.SuppressServerErrorDialog)
			this.UIHandler.post(this.serverSocketErrorDialog);
	}

	private void notifyTabRefresh()
	{
		this.UIHandler.post(this.mRefresh);
	}

	public void ChangeCar(CarData paramCarData)
	{
		ChangeCar(paramCarData, "tabInfo");
	}

	public void ChangeCar(CarData paramCarData, String paramString)
	{
		this.UIHandler.post(this.progressLoginShowDialog);
		Log.d("OVMS", "Changed car to: " + paramCarData.VehicleID);
		this.isLoggedIn = false;
		if (this.tcpTask != null)
		{
			Log.d("TCP", "Shutting down pervious TCP connection (ChangeCar())");
			this.tcpTask.ConnClose();
			this.tcpTask.cancel(true);
			this.tcpTask = null;
		}
		this.carData = paramCarData;
		if (this.carData.Data_GPRSUtilization == null)
			this.carData.Data_GPRSUtilization = new GPRSUtilization(this);
		notifyTabRefresh();
		paramCarData.ParanoidMode = false;
		this.tcpTask = new TCPTask(this.carData);
		Log.d("TCP", "Starting TCP Connection (ChangeCar())");
		this.tcpTask.execute(new Void[0]);
		getTabHost().setCurrentTabByTag(paramString);
	}

	public void ReportC2DMRegistrationID()
	{
		this.c2dmReportTimerHandler.postDelayed(this.reportC2DMRegistrationID, 500L);
	}

	public boolean SendServerCommand(String paramString)
	{
		return this.tcpTask.SendCommand(paramString);
	}

	public void onConfigurationChanged(Configuration paramConfiguration)
	{
		super.onConfigurationChanged(paramConfiguration);
		this.UIHandler.post(this.mRecreateChildTabLayout);
	}

	public void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(2130903042);
		loadCars();
		String str = getSharedPreferences("C2DM", 0).getString("RegID", "");
		TabHost localTabHost;
		if (str.length() == 0)
		{
			Log.d("C2DM", "Doing first time registration.");
			ServerCommands.RequestC2DMRegistrationID(this);
			this.DeviceScreenSize = (0xF & getResources().getConfiguration().screenLayout);
			Display localDisplay = ((WindowManager)getSystemService("window")).getDefaultDisplay();
			Object[] arrayOfObject = new Object[2];
			arrayOfObject[0] = Integer.valueOf(localDisplay.getWidth());
			arrayOfObject[1] = Integer.valueOf(localDisplay.getHeight());
			Log.d("INIT", String.format("Screen size: %d x %d", arrayOfObject));
			if ((localDisplay.getWidth() >= 976) || (localDisplay.getHeight() >= 976))
				this.DeviceScreenSize = 4;
			localTabHost = getTabHost();
			if (this.DeviceScreenSize != 4)
				break label573;
			Intent localIntent6 = new Intent().setClass(this, TabInfo_xlarge.class);
			TabHost.TabSpec localTabSpec6 = localTabHost.newTabSpec("tabInfo_xlarge");
			localTabSpec6.setContent(localIntent6);
			localTabSpec6.setIndicator("", getResources().getDrawable(2130837573));
			localTabHost.addTab(localTabSpec6);
			Intent localIntent7 = new Intent().setClass(this, TabMap.class);
			TabHost.TabSpec localTabSpec7 = localTabHost.newTabSpec("tabMap");
			localTabSpec7.setContent(localIntent7);
			localTabSpec7.setIndicator("", getResources().getDrawable(2130837579));
			localTabHost.addTab(localTabSpec7);
			Intent localIntent8 = new Intent().setClass(this, Tab_SubTabNotifications.class);
			TabHost.TabSpec localTabSpec8 = localTabHost.newTabSpec("tabNotifications");
			localTabSpec8.setContent(localIntent8);
			localTabSpec8.setIndicator("", getResources().getDrawable(2130837589));
			localTabHost.addTab(localTabSpec8);
			Intent localIntent9 = new Intent().setClass(this, Tab_SubTabDataUtilizations.class);
			TabHost.TabSpec localTabSpec9 = localTabHost.newTabSpec("tabDataUtilizations");
			localTabSpec9.setContent(localIntent9);
			localTabSpec9.setIndicator("", getResources().getDrawable(2130837577));
			localTabHost.addTab(localTabSpec9);
			Intent localIntent10 = new Intent().setClass(this, Tab_SubTabCarSettings.class);
			TabHost.TabSpec localTabSpec10 = localTabHost.newTabSpec("tabCarSettings");
			localTabSpec10.setContent(localIntent10);
			localTabSpec10.setIndicator("", getResources().getDrawable(2130837582));
			localTabHost.addTab(localTabSpec10);
			Intent localIntent11 = new Intent().setClass(this, TabCars.class);
			TabHost.TabSpec localTabSpec11 = localTabHost.newTabSpec("tabCars");
			localTabSpec11.setContent(localIntent11);
			localTabSpec11.setIndicator("", getResources().getDrawable(2130837594));
			localTabHost.addTab(localTabSpec11);
		}
		while (true)
		{
			getTabHost().setOnTabChangedListener(this);
			return;
			Log.d("C2DM", "Loaded Saved C2DM registration ID: " + str);
			break;
			label573: Intent localIntent1 = new Intent().setClass(this, TabInfo.class);
			TabHost.TabSpec localTabSpec1 = localTabHost.newTabSpec("tabInfo");
			localTabSpec1.setContent(localIntent1);
			localTabSpec1.setIndicator("", getResources().getDrawable(2130837573));
			localTabHost.addTab(localTabSpec1);
			Intent localIntent2 = new Intent().setClass(this, TabCar.class);
			TabHost.TabSpec localTabSpec2 = localTabHost.newTabSpec("tabCar");
			localTabSpec2.setContent(localIntent2);
			localTabSpec2.setIndicator("", getResources().getDrawable(2130837574));
			localTabHost.addTab(localTabSpec2);
			Intent localIntent3 = new Intent().setClass(this, TabMap.class);
			TabHost.TabSpec localTabSpec3 = localTabHost.newTabSpec("tabMap");
			localTabSpec3.setContent(localIntent3);
			localTabSpec3.setIndicator("", getResources().getDrawable(2130837579));
			localTabHost.addTab(localTabSpec3);
			Intent localIntent4 = new Intent().setClass(this, TabMiscFeatures.class);
			TabHost.TabSpec localTabSpec4 = localTabHost.newTabSpec("tabMiscFeatures");
			localTabSpec4.setContent(localIntent4);
			localTabSpec4.setIndicator("", getResources().getDrawable(2130837588));
			localTabHost.addTab(localTabSpec4);
			Intent localIntent5 = new Intent().setClass(this, TabCars.class);
			TabHost.TabSpec localTabSpec5 = localTabHost.newTabSpec("tabCars");
			localTabSpec5.setContent(localIntent5);
			localTabSpec5.setIndicator("", getResources().getDrawable(2130837594));
			localTabHost.addTab(localTabSpec5);
		}
	}

	public boolean onCreateOptionsMenu(Menu paramMenu)
	{
		getMenuInflater().inflate(2130903043, paramMenu);
		return true;
	}

	protected void onDestory()
	{
		if (this.tcpTask != null)
		{
			Log.d("TCP", "Shutting down TCP connection (OnDestroy())");
			this.tcpTask.ConnClose();
			this.tcpTask = null;
		}
	}

	public void onNewIntent(Intent paramIntent)
	{
		Log.d("EVENT", "onNewIntent");
		TabHost localTabHost = getTabHost();
		Object localObject;
		Iterator localIterator;
		if (paramIntent != null)
		{
			if (!paramIntent.hasExtra("VehicleID"))
				break label154;
			localObject = null;
			localIterator = this.allSavedCars.iterator();
			if (localIterator.hasNext())
				break label108;
			label51: if (localObject != null)
			{
				Log.d("EVENT", "Launching with default car set to: " + ((CarData)localObject).VehicleID);
				if (!paramIntent.hasExtra("SetTab"))
					break label145;
				ChangeCar((CarData)localObject, paramIntent.getStringExtra("SetTab"));
			}
		}
		while (true)
		{
			return;
			label108: CarData localCarData = (CarData)localIterator.next();
			if (!localCarData.VehicleID.equals(paramIntent.getStringExtra("VehicleID")))
				break;
			localObject = localCarData;
			break label51;
			label145: ChangeCar((CarData)localObject);
			continue;
			label154: if (paramIntent.hasExtra("SetTab"))
				localTabHost.setCurrentTabByTag(paramIntent.getStringExtra("SetTab"));
			else
				localTabHost.setCurrentTabByTag("tabInfo");
		}
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem)
	{
		boolean bool = true;
		switch (paramMenuItem.getItemId())
		{
		default:
			bool = super.onOptionsItemSelected(paramMenuItem);
		case 2131296266:
		case 2131296267:
		}
		while (true)
		{
			return bool;
			finish();
			continue;
			OVMSNotifications localOVMSNotifications = new OVMSNotifications(this);
			localOVMSNotifications.Clear();
			localOVMSNotifications.Save();
			notifyTabRefresh();
		}
	}

	protected void onPause()
	{
		super.onPause();
		if (this.tcpTask != null)
		{
			Log.d("TCP", "Shutting down TCP connection (OnPause())");
			this.tcpTask.ConnClose();
			this.tcpTask.cancel(true);
			this.tcpTask = null;
		}
		saveCars();
		OVMSWidgets.UpdateWidgets(this);
	}

	protected void onResume()
	{
		super.onResume();
		if (this.tcpTask == null)
		{
			this.UIHandler.post(this.progressLoginCloseDialog);
			String str = getTabHost().getCurrentTabTag();
			ChangeCar(this.carData, str);
		}
	}

	public void onTabChanged(String paramString)
	{
		this.UIHandler.post(this.mRefresh);
	}

	public void saveCars()
	{
		try
		{
			Log.d("OVMS", "Saving cars to interal storage...");
			SharedPreferences.Editor localEditor = getSharedPreferences("OVMS", 0).edit();
			localEditor.putString("LastVehicleID", this.carData.VehicleID);
			localEditor.commit();
			ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(openFileOutput("OVMSSavedCars.obj", 0));
			localObjectOutputStream.writeObject(this.allSavedCars);
			localObjectOutputStream.close();
			return;
		}
		catch (Exception localException)
		{
			while (true)
				localException.printStackTrace();
		}
	}

	private class ServerCommandResponseHandler
	implements Runnable
	{
		String message;

		ServerCommandResponseHandler(String arg2)
		{
			Object localObject;
			this.message = localObject;
		}

		public void run()
		{
			Toast.makeText(OVMSActivity.this, this.message, 0).show();
		}
	}

	private class TCPTask extends AsyncTask<Void, Integer, Void>
	{
		private BufferedReader Inputstream;
		private PrintWriter Outputstream;
		public Socket Sock;
		private CarData carData = OVMSActivity.this.carData;
		private byte[] pmDigest;
		private RC4 pmcipher;
		private RC4 rxcipher;
		private boolean socketMarkedClosed;
		private RC4 txcipher;

		public TCPTask(CarData arg2)
		{
		}

		private void ConnInit()
		{
			String str1 = this.carData.NetPass;
			String str2 = this.carData.VehicleID;
			char[] arrayOfChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
			Random localRandom = new Random();
			String str3 = "";
			int i = 0;
			while (true)
			{
				byte[] arrayOfByte1;
				if (i >= 22)
					arrayOfByte1 = str3.getBytes();
				try
				{
					HMAC localHMAC = new HMAC("MD5", str1.getBytes());
					localHMAC.update(arrayOfByte1);
					String str4 = Base64.encodeBytes(localHMAC.sign());
					Log.d("TCP", "Connecting " + this.carData.ServerNameOrIP);
					this.Sock = new Socket();
					this.Sock.setSoTimeout(10000);
					this.Sock.connect(new InetSocketAddress(this.carData.ServerNameOrIP, 6867), 5000);
					this.Outputstream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.Sock.getOutputStream())), true);
					Object[] arrayOfObject1 = new Object[3];
					arrayOfObject1[0] = str3;
					arrayOfObject1[1] = str4;
					arrayOfObject1[2] = str2;
					Log.d("OVMS", String.format("TX: MP-A 0 %s %s %s", arrayOfObject1));
					PrintWriter localPrintWriter = this.Outputstream;
					Object[] arrayOfObject2 = new Object[3];
					arrayOfObject2[0] = str3;
					arrayOfObject2[1] = str4;
					arrayOfObject2[2] = str2;
					localPrintWriter.println(String.format("MP-A 0 %s %s %s", arrayOfObject2));
					this.Inputstream = new BufferedReader(new InputStreamReader(this.Sock.getInputStream()));
					try
					{
						String[] arrayOfString = this.Inputstream.readLine().trim().split("[ ]+");
						Object[] arrayOfObject3 = new Object[4];
						arrayOfObject3[0] = arrayOfString[0];
						arrayOfObject3[1] = arrayOfString[1];
						arrayOfObject3[2] = arrayOfString[2];
						arrayOfObject3[3] = arrayOfString[3];
						Log.d("OVMS", String.format("RX: %s %s %s %s", arrayOfObject3));
						String str5 = arrayOfString[2];
						byte[] arrayOfByte2 = str5.getBytes();
						byte[] arrayOfByte3 = Base64.decode(arrayOfString[3]);
						localHMAC.clear();
						localHMAC.update(arrayOfByte2);
						if (!Arrays.equals(localHMAC.sign(), arrayOfByte3))
						{
							Object[] arrayOfObject6 = new Object[2];
							arrayOfObject6[0] = Base64.encodeBytes(localHMAC.sign());
							arrayOfObject6[1] = arrayOfString[3];
							Log.d("OVMS", String.format("Server authentication failed. Expected %s Got %s", arrayOfObject6));
							localHMAC.clear();
							String str6 = str5 + str3;
							localHMAC.update(str6.getBytes());
							byte[] arrayOfByte4 = localHMAC.sign();
							Object[] arrayOfObject4 = new Object[3];
							arrayOfObject4[0] = str6;
							arrayOfObject4[1] = toHex(arrayOfByte4).toLowerCase();
							arrayOfObject4[2] = Base64.encodeBytes(arrayOfByte4);
							Log.d("OVMS", String.format("Client version of the shared key is %s - (%s) %s", arrayOfObject4));
							RC4 localRC41 = new RC4(arrayOfByte4);
							this.rxcipher = localRC41;
							RC4 localRC42 = new RC4(arrayOfByte4);
							this.txcipher = localRC42;
							localObject = "";
							j = 0;
							if (j < 1024)
								break label745;
							this.rxcipher.rc4(((String)localObject).getBytes());
							this.txcipher.rc4(((String)localObject).getBytes());
							Object[] arrayOfObject5 = new Object[1];
							arrayOfObject5[0] = this.carData.ServerNameOrIP;
							Log.d("OVMS", String.format("Connected to %s. Ciphers initialized. Listening...", arrayOfObject5));
							OVMSActivity.this.loginComplete();
							return;
							str3 = str3 + arrayOfChar[localRandom.nextInt(-1 + arrayOfChar.length)];
							i++;
						}
					}
					catch (Exception localException2)
					{
						while (true)
							OVMSActivity.this.notifyServerSocketError(localException2);
					}
				}
				catch (UnknownHostException localUnknownHostException)
				{
					while (true)
					{
						OVMSActivity.this.notifyServerSocketError(localUnknownHostException);
						continue;
						Log.d("OVMS", "Server authentication OK.");
					}
				}
				catch (SocketTimeoutException localSocketTimeoutException)
				{
					while (true)
					{
						int j;
						OVMSActivity.this.notifyServerSocketError(localSocketTimeoutException);
						continue;
						String str7 = localObject + "0";
						Object localObject = str7;
						j++;
					}
				}
				catch (Exception localException1)
				{
					while (true)
						label745: OVMSActivity.this.notifyServerSocketError(localException1);
				}
			}
		}

		private void notifyCommandResponse(String paramString)
		{
			if (OVMSActivity.this != null)
			{
				OVMSActivity.this.mCommandResponse = new OVMSActivity.ServerCommandResponseHandler(OVMSActivity.this, paramString);
				OVMSActivity.this.UIHandler.post(OVMSActivity.this.mCommandResponse);
			}
		}

		private void processMessage(String paramString)
		{
			char c = paramString.charAt(0);
			Object localObject1 = paramString.substring(1);
			int i5;
			if (c == 'E')
			{
				i5 = paramString.charAt(1);
				if (i5 != 84)
					break label323;
				Log.d("TCP", "ET MSG Received: " + paramString);
			}
			label300: label1214: label1345: 
				while (true)
				{
					try
					{
						String str8 = paramString.substring(2);
						HMAC localHMAC = new HMAC("MD5", this.carData.RegPass.getBytes());
						localHMAC.update(str8.getBytes());
						this.pmDigest = localHMAC.sign();
						Log.d("OVMS", "Paranoid Mode Token Accepted. Entering Privacy Mode. (pmDigest = " + Base64.encodeBytes(this.pmDigest) + ")");
						Log.d("TCP", c + " MSG Received: " + (String)localObject1);
						DataLog.Log("[RX] " + c + (String)localObject1);
						switch (c)
						{
						default:
							return;
						case 'Z':
						case 'S':
						case 'T':
						case 'L':
						case 'D':
						case 'F':
						case 'f':
						case 'W':
						case 'g':
						case 'a':
						case 'C':
						case 'c':
						}
					}
					catch (Exception localException3)
					{
						Log.d("ERR", localException3.getMessage());
						localException3.printStackTrace();
						continue;
					}
					label323: if (i5 == 77)
					{
						Log.d("TCP", "EM MSG Received: " + paramString);
						c = paramString.charAt(2);
						localObject1 = paramString.substring(3);
						try
						{
							byte[] arrayOfByte = Base64.decode((String)localObject1);
							this.pmcipher = new RC4(this.pmDigest);
							Object localObject3 = "";
							for (int i6 = 0; ; i6++)
							{
								if (i6 >= 1024)
								{
									this.pmcipher.rc4(((String)localObject3).getBytes());
									String str7 = new String(this.pmcipher.rc4(arrayOfByte));
									localObject1 = str7;
									if (this.carData.ParanoidMode)
										break;
									Log.d("OVMS", "Paranoid Mode Detected");
									this.carData.ParanoidMode = true;
									refreshUI();
									break;
								}
								String str6 = localObject3 + "0";
								localObject3 = str6;
							}
						}
						catch (Exception localException2)
						{
							while (true)
							{
								Log.d("ERR", localException2.getMessage());
								localException2.printStackTrace();
							}
						}
						this.carData.Data_CarsConnected = Integer.parseInt((String)localObject1);
						refreshUI();
						continue;
						String[] arrayOfString11 = ((String)localObject1).split(",\\s*");
						if (arrayOfString11.length >= 8)
						{
							Log.d("TCP", "S MSG Validated");
							this.carData.Data_SOC = Integer.parseInt(arrayOfString11[0]);
							this.carData.Data_DistanceUnit = arrayOfString11[1].toString();
							this.carData.Data_LineVoltage = Integer.parseInt(arrayOfString11[2]);
							this.carData.Data_ChargeCurrent = Integer.parseInt(arrayOfString11[3]);
							this.carData.Data_ChargeState = arrayOfString11[4].toString();
							this.carData.Data_ChargeMode = arrayOfString11[5].toString();
							this.carData.Data_IdealRange = Integer.parseInt(arrayOfString11[6]);
							this.carData.Data_EstimatedRange = Integer.parseInt(arrayOfString11[7]);
						}
						if (arrayOfString11.length >= 14)
						{
							this.carData.Data_ChargeAmpsLimit = Integer.parseInt(arrayOfString11[8]);
							this.carData.Data_ChargerB4State = Integer.parseInt(arrayOfString11[9]);
							this.carData.Data_ChargerKWHConsumed = Double.parseDouble(arrayOfString11[10]);
							this.carData.Data_ChargeSubstate = Integer.parseInt(arrayOfString11[11]);
							this.carData.Data_ChargeState_raw = Integer.parseInt(arrayOfString11[12]);
							this.carData.Data_ChargeMode_raw = Integer.parseInt(arrayOfString11[13]);
						}
						refreshUI();
						continue;
						if (((String)localObject1).length() > 0)
						{
							this.carData.Data_LastCarUpdate_raw = Long.parseLong((String)localObject1);
							this.carData.Data_LastCarUpdate = new Date(new Date().getTime() - 1000L * this.carData.Data_LastCarUpdate_raw);
							if (this.carData.Data_ParkedTime_raw > 0.0D)
								this.carData.Data_ParkedTime = new Date(this.carData.Data_LastCarUpdate.getTime() - 1000L * ()this.carData.Data_ParkedTime_raw);
							refreshUI();
						}
						else
						{
							Log.d("TCP", "T MSG Invalid");
							continue;
							String[] arrayOfString10 = ((String)localObject1).split(",\\s*");
							if (arrayOfString10.length >= 2)
							{
								Log.d("TCP", "L MSG Validated");
								this.carData.Data_Latitude = Double.parseDouble(arrayOfString10[0]);
								this.carData.Data_Longitude = Double.parseDouble(arrayOfString10[1]);
							}
							if (arrayOfString10.length >= 6)
							{
								this.carData.Data_Direction = Double.parseDouble(arrayOfString10[2]);
								this.carData.Data_Altitude = Double.parseDouble(arrayOfString10[3]);
								this.carData.Data_GPSLocked = arrayOfString10[4].trim().equals("1");
								this.carData.Data_GPSDataStale = arrayOfString10[5].trim().equals("0");
							}
							refreshUI();
							continue;
							String[] arrayOfString9 = ((String)localObject1).split(",\\s*");
							if (arrayOfString9.length >= 9)
							{
								Log.d("TCP", "D MSG Validated");
								int i2 = Integer.parseInt(arrayOfString9[0]);
								CarData localCarData1 = this.carData;
								boolean bool1;
								label1096: boolean bool2;
								label1119: boolean bool3;
								label1142: boolean bool4;
								boolean bool5;
								boolean bool6;
								boolean bool7;
								label1240: boolean bool8;
								label1273: boolean bool9;
								boolean bool10;
								boolean bool11;
								boolean bool12;
								label1370: boolean bool13;
								if ((i2 & 0x1) > 0)
								{
									bool1 = true;
									localCarData1.Data_LeftDoorOpen = bool1;
									CarData localCarData2 = this.carData;
									if ((i2 & 0x2) <= 0)
										break label1643;
									bool2 = true;
									localCarData2.Data_RightDoorOpen = bool2;
									CarData localCarData3 = this.carData;
									if ((i2 & 0x4) <= 0)
										break label1649;
									bool3 = true;
									localCarData3.Data_ChargePortOpen = bool3;
									CarData localCarData4 = this.carData;
									if ((i2 & 0x8) <= 0)
										break label1655;
									bool4 = true;
									localCarData4.Data_PilotPresent = bool4;
									CarData localCarData5 = this.carData;
									if ((i2 & 0x10) <= 0)
										break label1661;
									bool5 = true;
									localCarData5.Data_Charging = bool5;
									CarData localCarData6 = this.carData;
									if ((i2 & 0x40) <= 0)
										break label1667;
									bool6 = true;
									localCarData6.Data_HandBrakeApplied = bool6;
									CarData localCarData7 = this.carData;
									if ((i2 & 0x80) <= 1)
										break label1673;
									bool7 = true;
									localCarData7.Data_CarPoweredON = bool7;
									int i3 = Integer.parseInt(arrayOfString9[1]);
									CarData localCarData8 = this.carData;
									if ((i3 & 0x8) <= 0)
										break label1679;
									bool8 = true;
									localCarData8.Data_PINLocked = bool8;
									CarData localCarData9 = this.carData;
									if ((i3 & 0x10) <= 0)
										break label1685;
									bool9 = true;
									localCarData9.Data_ValetON = bool9;
									CarData localCarData10 = this.carData;
									if ((i3 & 0x20) <= 0)
										break label1691;
									bool10 = true;
									localCarData10.Data_HeadlightsON = bool10;
									CarData localCarData11 = this.carData;
									if ((i3 & 0x40) <= 0)
										break label1697;
									bool11 = true;
									localCarData11.Data_BonnetOpen = bool11;
									CarData localCarData12 = this.carData;
									if ((i3 & 0x80) <= 0)
										break label1703;
									bool12 = true;
									localCarData12.Data_TrunkOpen = bool12;
									int i4 = Integer.parseInt(arrayOfString9[2]);
									CarData localCarData13 = this.carData;
									if (i4 != 4)
										break label1709;
									bool13 = true;
									label1401: localCarData13.Data_CarLocked = bool13;
									this.carData.Data_TemperaturePEM = Double.parseDouble(arrayOfString9[3]);
									this.carData.Data_TemperatureMotor = Double.parseDouble(arrayOfString9[4]);
									this.carData.Data_TemperatureBattery = Double.parseDouble(arrayOfString9[5]);
									this.carData.Data_TripMeter = Double.parseDouble(arrayOfString9[6]);
									this.carData.Data_Odometer = Double.parseDouble(arrayOfString9[7]);
									this.carData.Data_Speed = Double.parseDouble(arrayOfString9[8]);
									if (arrayOfString9.length >= 10)
									{
										this.carData.Data_ParkedTime_raw = Double.parseDouble(arrayOfString9[9]);
										if (this.carData.Data_LastCarUpdate != null)
											break label1715;
									}
								}
								for (this.carData.Data_ParkedTime = null; ; this.carData.Data_ParkedTime = new Date(this.carData.Data_LastCarUpdate.getTime() - 1000L * ()this.carData.Data_ParkedTime_raw))
								{
									if (arrayOfString9.length >= 11)
										this.carData.Data_TemperatureAmbient = Double.parseDouble(arrayOfString9[10]);
									if (arrayOfString9.length >= 14)
									{
										this.carData.Data_CoolingPumpON_DoorState3 = arrayOfString9[11].trim().equals("1");
										this.carData.Data_PEM_Motor_Battery_TemperaturesDataStale = arrayOfString9[12].trim().equals("0");
										this.carData.Data_AmbientTemperatureDataStale = arrayOfString9[13].trim().equals("0");
									}
									refreshUI();
									break;
									bool1 = false;
									break label1096;
									label1643: bool2 = false;
									break label1119;
									label1649: bool3 = false;
									break label1142;
									label1655: bool4 = false;
									break label1166;
									label1661: bool5 = false;
									break label1190;
									bool6 = false;
									break label1214;
									bool7 = false;
									break label1240;
									bool8 = false;
									break label1273;
									bool9 = false;
									break label1297;
									bool10 = false;
									break label1321;
									bool11 = false;
									break label1345;
									bool12 = false;
									break label1370;
									bool13 = false;
									break label1401;
								}
								String[] arrayOfString8 = ((String)localObject1).split(",\\s*");
								if (arrayOfString8.length >= 3)
								{
									Log.d("TCP", "F MSG Validated");
									this.carData.Data_CarModuleFirmwareVersion = arrayOfString8[0].toString();
									this.carData.Data_VIN = arrayOfString8[1].toString();
									this.carData.Data_CarModuleGSMSignalLevel = arrayOfString8[2].toString();
									if (arrayOfString8.length >= 4)
									{
										this.carData.Data_Features.put(Integer.valueOf(15), arrayOfString8[3].toString());
										this.carData.Data_CANWriteEnabled = arrayOfString8[3].trim().equals("1");
									}
									if (arrayOfString8.length >= 5)
										this.carData.Data_CarType = arrayOfString8[4].toString();
									refreshUI();
								}
								String[] arrayOfString7 = ((String)localObject1).split(",\\s*");
								if (arrayOfString7.length >= 1)
								{
									Log.d("TCP", "f MSG Validated");
									this.carData.Data_OVMSServerFirmwareVersion = arrayOfString7[0].toString();
									refreshUI();
									continue;
									String[] arrayOfString6 = ((String)localObject1).split(",\\s*");
									if (arrayOfString6.length >= 8)
									{
										Log.d("TCP", "W MSG Validated");
										this.carData.Data_FRWheelPressure = Double.parseDouble(arrayOfString6[0]);
										this.carData.Data_FRWheelTemperature = Double.parseDouble(arrayOfString6[1]);
										this.carData.Data_RRWheelPressure = Double.parseDouble(arrayOfString6[2]);
										this.carData.Data_RRWheelTemperature = Double.parseDouble(arrayOfString6[3]);
										this.carData.Data_FLWheelPressure = Double.parseDouble(arrayOfString6[4]);
										this.carData.Data_FLWheelTemperature = Double.parseDouble(arrayOfString6[5]);
										this.carData.Data_RLWheelPressure = Double.parseDouble(arrayOfString6[6]);
										this.carData.Data_RLWheelTemperature = Double.parseDouble(arrayOfString6[7]);
										if (arrayOfString6.length >= 9)
											this.carData.Data_TPMSDataStale = arrayOfString6[8].trim().equals("0");
										refreshUI();
										continue;
										String[] arrayOfString5 = ((String)localObject1).split(",\\s*");
										if (arrayOfString5.length >= 9)
										{
											Log.d("TCP", "g MSG Validated");
											CarData_Group localCarData_Group1 = new CarData_Group();
											localCarData_Group1.VehicleID = arrayOfString5[0];
											localCarData_Group1.SOC = Double.parseDouble(arrayOfString5[2]);
											localCarData_Group1.Speed = Double.parseDouble(arrayOfString5[3]);
											localCarData_Group1.Direction = Double.parseDouble(arrayOfString5[4]);
											localCarData_Group1.Altitude = Double.parseDouble(arrayOfString5[5]);
											localCarData_Group1.GPSLocked = arrayOfString5[6].trim().equals("1");
											localCarData_Group1.GPSDataStale = arrayOfString5[7].trim().equals("0");
											localCarData_Group1.Latitude = Double.parseDouble(arrayOfString5[8]);
											localCarData_Group1.Longitude = Double.parseDouble(arrayOfString5[9]);
											if (this.carData.Group == null)
												this.carData.Group = new HashMap();
											CarData_Group localCarData_Group2 = (CarData_Group)this.carData.Group.get(localCarData_Group1.VehicleID);
											if (localCarData_Group2 != null)
												localCarData_Group1.VehicleImageDrawable = localCarData_Group2.VehicleImageDrawable;
											this.carData.Group.put(arrayOfString5[0], localCarData_Group1);
											refreshUI();
											continue;
											Log.d("TCP", "Server acknowleged ping");
											continue;
											if (((String)localObject1).length() == 0)
											{
												Log.d("TCP", c + " MSG Code Invalid");
											}
											else
											{
												Object localObject2 = "";
												int j;
												String[] arrayOfString4;
												try
												{
													if (((String)localObject1).indexOf(',') > 0)
													{
														j = Integer.parseInt(((String)localObject1).substring(0, ((String)localObject1).indexOf(',')));
														String str5 = ((String)localObject1).substring(1 + ((String)localObject1).indexOf(','));
														localObject2 = str5;
													}
													while (true)
														switch (j)
														{
														default:
															arrayOfString4 = ((String)localObject2).split(",\\s*");
															if (!arrayOfString4[0].equals("0"))
																break label3529;
															Object[] arrayOfObject10 = new Object[1];
															arrayOfObject10[0] = ServerCommands.toString(j);
															notifyCommandResponse(String.format("Server Acknowledged %s", arrayOfObject10));
															break label300;
															int i = Integer.parseInt((String)localObject1);
															j = i;
														case 1:
														case 3:
														case 30:
														}
												}
												catch (Exception localException1)
												{
													Log.d("TCP", "!!! " + c + " message is invalid.");
												}
												continue;
												String[] arrayOfString3 = ((String)localObject2).split(",");
												String str3;
												int n;
												if (arrayOfString3.length > 4)
												{
													str3 = "";
													n = 3;
													int i1 = arrayOfString3.length;
													if (n >= i1)
													{
														Object[] arrayOfObject7 = new Object[2];
														arrayOfObject7[0] = arrayOfString3[1];
														arrayOfObject7[1] = str3;
														Log.d("TCP", String.format("FEATURE %s = %s", arrayOfObject7));
														this.carData.Data_Features.put(Integer.valueOf(Integer.parseInt(arrayOfString3[1])), str3);
													}
												}
												while (true)
												{
													if (Integer.parseInt(arrayOfString3[1]) != -1 + Integer.parseInt(arrayOfString3[2]))
														break label2912;
													this.carData.Data_Features_LastRefreshed = new Date();
													refreshUI();
													break;
													StringBuilder localStringBuilder2 = new StringBuilder(String.valueOf(str3));
													if (str3.length() > 0);
													for (String str4 = ","; ; str4 = "")
													{
														str3 = str4 + arrayOfString3[n];
														n++;
														break;
													}
													if (arrayOfString3.length == 4)
													{
														Object[] arrayOfObject6 = new Object[2];
														arrayOfObject6[0] = arrayOfString3[1];
														arrayOfObject6[1] = arrayOfString3[3];
														Log.d("TCP", String.format("FEATURE %s = %s", arrayOfObject6));
														this.carData.Data_Features.put(Integer.valueOf(Integer.parseInt(arrayOfString3[1])), arrayOfString3[3]);
													}
													else if (arrayOfString3.length >= 2)
													{
														Object[] arrayOfObject5 = new Object[1];
														arrayOfObject5[0] = arrayOfString3[1];
														Log.d("TCP", String.format("FEATURE %s = EMPTY", arrayOfObject5));
														this.carData.Data_Features.put(Integer.valueOf(Integer.parseInt(arrayOfString3[1])), "");
													}
												}
												label2912: continue;
												String[] arrayOfString2 = ((String)localObject2).split(",");
												String str1;
												int k;
												if (arrayOfString2.length > 4)
												{
													str1 = "";
													k = 3;
													int m = arrayOfString2.length;
													if (k >= m)
													{
														Object[] arrayOfObject4 = new Object[2];
														arrayOfObject4[0] = arrayOfString2[1];
														arrayOfObject4[1] = str1;
														Log.d("TCP", String.format("PARAMETER %s = %s", arrayOfObject4));
														this.carData.Data_Parameters.put(Integer.valueOf(Integer.parseInt(arrayOfString2[1])), str1);
													}
												}
												while (true)
												{
													if (Integer.parseInt(arrayOfString2[1]) != -1 + Integer.parseInt(arrayOfString2[2]))
														break label3238;
													this.carData.Data_Parameters_LastRefreshed = new Date();
													refreshUI();
													break;
													StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(str1));
													if (str1.length() > 0);
													for (String str2 = ","; ; str2 = "")
													{
														str1 = str2 + arrayOfString2[k];
														k++;
														break;
													}
													if (arrayOfString2.length == 4)
													{
														Object[] arrayOfObject3 = new Object[2];
														arrayOfObject3[0] = arrayOfString2[1];
														arrayOfObject3[1] = arrayOfString2[3];
														Log.d("TCP", String.format("PARAMETER %s = %s", arrayOfObject3));
														this.carData.Data_Parameters.put(Integer.valueOf(Integer.parseInt(arrayOfString2[1])), arrayOfString2[3]);
													}
													else if (arrayOfString2.length >= 2)
													{
														Object[] arrayOfObject2 = new Object[1];
														arrayOfObject2[0] = arrayOfString2[1];
														Log.d("TCP", String.format("PARAMETER %s = EMPTY", arrayOfObject2));
														this.carData.Data_Parameters.put(Integer.valueOf(Integer.parseInt(arrayOfString2[1])), "");
													}
												}
												continue;
												String[] arrayOfString1 = ((String)localObject2).split(",\\s*");
												if (arrayOfString1.length >= 3)
												{
													if (this.carData.Data_GPRSUtilization == null)
														this.carData.Data_GPRSUtilization = new GPRSUtilization(OVMSActivity.this);
													if (arrayOfString1[1].equals("1"))
														this.carData.Data_GPRSUtilization.Clear();
													SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
													try
													{
														this.carData.Data_GPRSUtilization.AddData(localSimpleDateFormat.parse(arrayOfString1[3]), Long.parseLong(arrayOfString1[4]), Long.parseLong(arrayOfString1[5]), Long.parseLong(arrayOfString1[6]), Long.parseLong(arrayOfString1[7]));
														Object[] arrayOfObject1 = new Object[7];
														arrayOfObject1[0] = arrayOfString1[1];
														arrayOfObject1[1] = arrayOfString1[2];
														arrayOfObject1[2] = localSimpleDateFormat.parse(arrayOfString1[3]).toLocaleString();
														arrayOfObject1[3] = arrayOfString1[4];
														arrayOfObject1[4] = arrayOfString1[5];
														arrayOfObject1[5] = arrayOfString1[6];
														arrayOfObject1[6] = arrayOfString1[7];
														Log.d("TCP", String.format("GPRS UTIL [%s/%s] %s: car_rx %s car_tx %s app_rx %s app_tx %s", arrayOfObject1));
														if (!arrayOfString1[1].equals(arrayOfString1[2]))
															continue;
														this.carData.Data_GPRSUtilization.LastDataRefresh = new Date();
														this.carData.Data_GPRSUtilization.Save(OVMSActivity.this);
														refreshUI();
													}
													catch (NumberFormatException localNumberFormatException)
													{
														while (true)
															localNumberFormatException.printStackTrace();
													}
													catch (ParseException localParseException)
													{
														while (true)
															localParseException.printStackTrace();
													}
													label3529: if (arrayOfString4[0].equals("1"))
														if (arrayOfString4.length > 1)
														{
															Object[] arrayOfObject9 = new Object[2];
															arrayOfObject9[0] = ServerCommands.toString(j);
															arrayOfObject9[1] = arrayOfString4[1];
															notifyCommandResponse(String.format("[ERROR] %s\n%s\nTry turning on CAN_WRITE in the settings tab.", arrayOfObject9));
														}
														else
														{
															Object[] arrayOfObject8 = new Object[1];
															arrayOfObject8[0] = ServerCommands.toString(j);
															notifyCommandResponse(String.format("[ERROR] %s\nTry turning on CAN_WRITE in the settings tab.", arrayOfObject8));
														}
												}
											}
										}
									}
								}
							}
						}
					}
				}
		}

		private void refreshUI()
		{
			if (OVMSActivity.this != null)
				OVMSActivity.this.UIHandler.post(OVMSActivity.this.mRefresh);
		}

		private String toHex(byte[] paramArrayOfByte)
		{
			BigInteger localBigInteger = new BigInteger(1, paramArrayOfByte);
			String str = "%0" + (paramArrayOfByte.length << 1) + "X";
			Object[] arrayOfObject = new Object[1];
			arrayOfObject[0] = localBigInteger;
			return String.format(str, arrayOfObject);
		}

		public void ConnClose()
		{
			try
			{
				this.socketMarkedClosed = true;
				OVMSActivity.this.SuppressServerErrorDialog = true;
				OVMSActivity.this.isLoggedIn = false;
				if (this.Sock != null)
					this.Sock.close();
			}
			catch (IOException localIOException)
			{
				try
				{
					Thread.sleep(200L);
					label41: this.Sock = null;
					OVMSActivity.this.SuppressServerErrorDialog = false;
					while (true)
					{
						return;
						localIOException = localIOException;
						localIOException.printStackTrace();
					}
				}
				catch (InterruptedException localInterruptedException)
				{
					break label41;
				}
			}
		}

		public void Ping()
		{
			SendCommand("A");
		}

		public boolean SendCommand(String paramString)
		{
			boolean bool;
			if (!OVMSActivity.this.isLoggedIn)
			{
				Log.d("TCP", "Server not ready. TX aborted.");
				bool = false;
			}
			while (true)
			{
				return bool;
				DataLog.Log("[TX] " + paramString);
				try
				{
					String str2;
					int i;
					Object localObject;
					if ((this.carData.ParanoidMode) && (!paramString.startsWith("A")) && (!paramString.startsWith("C")) && (!paramString.startsWith("C30")) && (!paramString.startsWith("p")))
					{
						this.pmcipher = new RC4(this.pmDigest);
						str2 = "";
						i = 0;
						if (i >= 1024)
						{
							this.pmcipher.rc4(str2.getBytes());
							String str3 = Base64.encodeBytes(this.pmcipher.rc4(paramString.getBytes()));
							localObject = "MP-0 EM" + str3;
							Log.d("TCP", "TX (Paranoid-Mode Command): " + (String)localObject + " (using pmDigest: " + Base64.encodeBytes(this.pmDigest) + ")");
						}
					}
					while (true)
					{
						byte[] arrayOfByte = this.txcipher.rc4(((String)localObject).getBytes());
						Log.d("TCP", "TX (Encrypted): " + Base64.encodeBytes(arrayOfByte));
						this.Outputstream.println(Base64.encodeBytes(arrayOfByte));
						break label342;
						str2 = str2 + "0";
						i++;
						break;
						String str1 = "MP-0 " + paramString;
						localObject = str1;
					}
				}
				catch (Exception localException)
				{
					localException.printStackTrace();
					OVMSActivity.this.notifyServerSocketError(localException);
					label342: bool = true;
				}
			}
		}

		protected Void doInBackground(Void[] paramArrayOfVoid)
		{
			Log.d("TCP", "Starting background TCP thread");
			OVMSActivity.this.SuppressServerErrorDialog = false;
			this.socketMarkedClosed = false;
			try
			{
				ConnInit();
				if (OVMSActivity.this.isLoggedIn)
				{
					Log.d("TCP", "Background TCP ready");
					this.Sock.setSoTimeout(5000);
					boolean bool1 = this.Sock.isConnected();
					if (bool1);
				}
				else
				{
					label69: if (this.Outputstream != null)
						this.Outputstream.close();
				}
			}
			catch (Exception localException1)
			{
				try
				{
					while (true)
					{
						if (this.Inputstream != null)
							this.Inputstream.close();
						try
						{
							label97: if (this.Sock != null)
								this.Sock.close();
						label111: this.Sock = null;
						Log.d("TCP", "TCP thread ending");
						return null;
						Object localObject = "";
						try
						{
							while (true)
							{
								while (true)
								{
									String str4 = this.Inputstream.readLine();
									localObject = str4;
									if (localObject == null)
										break label341;
									label149: if ((localObject == null) || (((String)localObject).length() <= 5))
										break;
									String str1 = ((String)localObject).trim();
									String str2 = new String(this.rxcipher.rc4(Base64.decode(str1)));
									if ((str2 == null) || (str2.length() <= 5))
										break;
									String str3 = str2.trim();
									Object[] arrayOfObject = new Object[2];
									arrayOfObject[0] = str3;
									arrayOfObject[1] = str1;
									Log.d("OVMS", String.format("RX: %s (%s)", arrayOfObject));
									boolean bool2 = str3.substring(0, 5).equals("MP-0 ");
									if (!bool2)
										break label355;
									try
									{
										processMessage(str3.substring(5));
									}
									catch (Exception localException4)
									{
										DataLog.Log("##ERROR## " + localException4.getMessage() + " - " + str3);
										localException4.printStackTrace();
									}
								}
								break;
								localException1 = localException1;
								if (this.socketMarkedClosed)
									break label69;
								OVMSActivity.this.notifyServerSocketError(localException1);
								break label69;
								label341: Thread.sleep(100L);
							}
						}
						catch (IOException localIOException)
						{
							break label149;
							label355: Log.d("OVMS", "Unknown protection scheme");
						}
						}
						catch (Exception localException3)
						{
							break label111;
						}
					}
				}
				catch (Exception localException2)
				{
					break label97;
				}
			}
		}

		protected void onProgressUpdate(Integer[] paramArrayOfInteger)
		{
		}
	}
}