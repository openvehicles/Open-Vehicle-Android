package com.openvehicles.OVMS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class TabInfo_xlarge extends MapActivity {
	private final int CAR_MARKER_ANIMATION_DURATION_MS = 2000;
	private final int CAR_MARKER_ANIMATION_FRAMES = 40;
	public int CurrentScreenOrientation;
	private Bitmap DirectionalMarker;
	private final int LABEL_SHADOW_XY = 1;
	private final int LABEL_TEXT_SIZE = 20;
	private Runnable animateCarMarker = new Runnable() {
		public void run() {
			String str1 = "-";
			if (TabInfo_xlarge.this.data.Data_LastCarUpdate != null)
				str1 = new SimpleDateFormat("MMM d, K:mm:ss a")
			.format(TabInfo_xlarge.this.data.Data_LastCarUpdate);
			GeoPoint localGeoPoint1 = Utilities
					.GetCarGeopoint(TabInfo_xlarge.this.data);
			int i = (localGeoPoint1.getLatitudeE6() - TabInfo_xlarge.this.lastCarGeoPoint
					.getLatitudeE6()) / 40;
			int j = (localGeoPoint1.getLongitudeE6() - TabInfo_xlarge.this.lastCarGeoPoint
					.getLongitudeE6()) / 40;
			if (TabInfo_xlarge.this.carMarkerAnimationFrame == 39)
				;
			for (GeoPoint localGeoPoint2 = localGeoPoint1;; localGeoPoint2 = new GeoPoint(
					TabInfo_xlarge.this.lastCarGeoPoint.getLatitudeE6() + i
					* TabInfo_xlarge.this.carMarkerAnimationFrame,
					TabInfo_xlarge.this.lastCarGeoPoint.getLongitudeE6() + j
					* TabInfo_xlarge.this.carMarkerAnimationFrame)) {
				String str2 = TabInfo_xlarge.this.data.VehicleID;
				Object[] arrayOfObject = new Object[1];
				arrayOfObject[0] = str1;
				Utilities.CarMarker localCarMarker = new Utilities.CarMarker(
						localGeoPoint2, str2, String.format(
								"Last reported: %s", arrayOfObject),
								(int) TabInfo_xlarge.this.data.Data_Direction);
				TabInfo_xlarge.this.carMarkers.setOverlay(0, localCarMarker);
				TabInfo_xlarge.this.mapView.invalidate();
				TabInfo_xlarge localTabInfo_xlarge = TabInfo_xlarge.this;
				int k = 1 + localTabInfo_xlarge.carMarkerAnimationFrame;
				localTabInfo_xlarge.carMarkerAnimationFrame = k;
				if (k < 40)
					TabInfo_xlarge.this.carMarkerAnimationTimerHandler
					.postDelayed(TabInfo_xlarge.this.animateCarMarker,
							50L);
				return;
			}
		}
	};
	private int carMarkerAnimationFrame = 0;
	private Handler carMarkerAnimationTimerHandler = new Handler();
	private Utilities.CarMarkerOverlay carMarkers;
	private String currentVehicleID;
	private CarData data;
	private ProgressDialog downloadProgress;
	private ServerCommands.CarLayoutDownloader downloadTask;
	private Handler handler = new Handler() {
		public void handleMessage(Message paramAnonymousMessage) {
			TabInfo_xlarge.this.updateLastUpdatedView();
			TabInfo_xlarge.this.updateInfoUI();
			TabInfo_xlarge.this.updateCarLayoutUI();
			TabInfo_xlarge.this.updateMapUI();
		}
	};
	private boolean isLoggedIn;
	private GeoPoint lastCarGeoPoint;
	private Runnable lastUpdateTimer = new Runnable() {
		public void run() {
			TabInfo_xlarge.this.updateLastUpdatedView();
			TabInfo_xlarge.this.lastUpdateTimerHandler.postDelayed(
					TabInfo_xlarge.this.lastUpdateTimer, 5000L);
		}
	};
	private Handler lastUpdateTimerHandler = new Handler();
	private AlertDialog lastUpdatedDialog;
	private List<Overlay> mapOverlays;
	private MapView mapView;
	private MapController mc;
	private Handler orientationChangedHandler = new Handler() {
		public void handleMessage(Message paramAnonymousMessage) {
			Log.d("Tab", "Relayout TabInfo_xlarge activity");
			TabInfo_xlarge.this.CurrentScreenOrientation = TabInfo_xlarge.this
					.getResources().getConfiguration().orientation;
			TableLayout localTableLayout = (TableLayout) TabInfo_xlarge.this
					.findViewById(2131296345);
			LinearLayout localLinearLayout;
			if (TabInfo_xlarge.this.CurrentScreenOrientation == 2) {
				localTableLayout.setLayoutParams(new LinearLayout.LayoutParams(
						-2, -2));
				localTableLayout.invalidate();
				localLinearLayout = (LinearLayout) TabInfo_xlarge.this
						.findViewById(2131296380);
				if (TabInfo_xlarge.this.CurrentScreenOrientation != 1)
					break label132;
			}
			label132: for (int i = 8;; i = 0) {
				localLinearLayout.setVisibility(i);
				localLinearLayout.invalidate();
				return;
				localTableLayout.setLayoutParams(new LinearLayout.LayoutParams(
						240, -2));
				break;
			}
		}
	};
	private AlertDialog softwareInformation;

	private void downloadLayout() {
		this.downloadProgress = new ProgressDialog(this);
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
				StringBuilder localStringBuilder1 = new StringBuilder(
						String.valueOf(TabInfo_xlarge.this
								.getCacheDir().getAbsolutePath()));
				Object[] arrayOfObject1 = new Object[1];
				arrayOfObject1[0] = TabInfo_xlarge.this.data.VehicleImageDrawable;
				Bitmap localBitmap1 = BitmapFactory.decodeFile(String
						.format("/%s.png", arrayOfObject1));
				if (localBitmap1 != null) {
					((ImageView) TabInfo_xlarge.this
							.findViewById(2131296347))
							.setImageBitmap(localBitmap1);
					StringBuilder localStringBuilder2 = new StringBuilder(
							String.valueOf(TabInfo_xlarge.this
									.getCacheDir().getAbsolutePath()));
					Object[] arrayOfObject2 = new Object[1];
					arrayOfObject2[0] = TabInfo_xlarge.this.data.VehicleImageDrawable;
					Bitmap localBitmap2 = BitmapFactory
							.decodeFile(String.format("/ol_%s.png",
									arrayOfObject2));
					if (localBitmap2 != null)
						((ImageView) TabInfo_xlarge.this
								.findViewById(2131296359))
								.setImageBitmap(localBitmap2);
					Toast.makeText(TabInfo_xlarge.this,
							"Graphics Downloaded", 0).show();
				}
				while (true) {
					return;
					Toast.makeText(TabInfo_xlarge.this,
							"Download Failed", 0).show();
				}
			}
		});
		this.downloadTask = new ServerCommands.CarLayoutDownloader(
				this.downloadProgress);
		ServerCommands.CarLayoutDownloader localCarLayoutDownloader = this.downloadTask;
		String[] arrayOfString = new String[2];
		arrayOfString[0] = this.data.VehicleImageDrawable;
		arrayOfString[1] = getCacheDir().getAbsolutePath();
		localCarLayoutDownloader.execute(arrayOfString);
	}

	private void initCarLayoutUI() {
		((TextView) findViewById(2131296343))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				String str = "-";
				if ((TabInfo_xlarge.this.data != null)
						&& (TabInfo_xlarge.this.data.Data_LastCarUpdate != null))
					str = new SimpleDateFormat("MMM d, K:mm:ss a")
				.format(TabInfo_xlarge.this.data.Data_LastCarUpdate);
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(
						TabInfo_xlarge.this);
				localBuilder
				.setMessage("Last update: " + str)
				.setCancelable(true)
				.setPositiveButton("Close",
						new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymous2DialogInterface,
							int paramAnonymous2Int) {
						paramAnonymous2DialogInterface
						.dismiss();
					}
				})
				.setTitle(TabInfo_xlarge.this.data.VehicleID);
				TabInfo_xlarge.this.lastUpdatedDialog = localBuilder
						.create();
				TabInfo_xlarge.this.lastUpdatedDialog.show();
			}
		});
		((FrameLayout) findViewById(2131296378))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (TabInfo_xlarge.this.isFinishing())
					return;
				TabInfo_xlarge localTabInfo_xlarge = TabInfo_xlarge.this;
				OVMSActivity localOVMSActivity = (OVMSActivity) TabInfo_xlarge.this
						.getParent();
				if (TabInfo_xlarge.this.data.Data_CarLocked)
					;
				for (boolean bool = false;; bool = true) {
					ServerCommands.LockUnlockCar(localTabInfo_xlarge,
							localOVMSActivity, null, bool);
					break;
				}
			}
		});
		((FrameLayout) findViewById(2131296379))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (TabInfo_xlarge.this.isFinishing())
					return;
				TabInfo_xlarge localTabInfo_xlarge = TabInfo_xlarge.this;
				OVMSActivity localOVMSActivity = (OVMSActivity) TabInfo_xlarge.this
						.getParent();
				if (TabInfo_xlarge.this.data.Data_ValetON)
					;
				for (boolean bool = false;; bool = true) {
					ServerCommands.ValetModeOnOff(localTabInfo_xlarge,
							localOVMSActivity, null, bool);
					break;
				}
			}
		});
		((LinearLayout) findViewById(2131296380))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if ((TabInfo_xlarge.this.isFinishing())
						|| (TabInfo_xlarge.this.data.Data_CoolingPumpON_DoorState3))
					;
				while (true) {
					return;
					ServerCommands.WakeUp(TabInfo_xlarge.this,
							(OVMSActivity) TabInfo_xlarge.this
							.getParent(), null, true);
				}
			}
		});
	}

	private void initInfoUI() {
		((TextView) findViewById(2131296343))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (TabInfo_xlarge.this.data.Data_LastCarUpdate != null) {
					String str = new SimpleDateFormat(
							"MMM d, K:mm:ss a")
					.format(TabInfo_xlarge.this.data.Data_LastCarUpdate);
					AlertDialog.Builder localBuilder = new AlertDialog.Builder(
							TabInfo_xlarge.this);
					localBuilder
					.setMessage("Last update: " + str)
					.setCancelable(true)
					.setPositiveButton(
							"Close",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramAnonymous2DialogInterface,
										int paramAnonymous2Int) {
									paramAnonymous2DialogInterface
									.dismiss();
								}
							})
							.setTitle(
									TabInfo_xlarge.this.data.VehicleID);
					TabInfo_xlarge.this.lastUpdatedDialog = localBuilder
							.create();
					TabInfo_xlarge.this.lastUpdatedDialog.show();
				}
			}
		});
		((TextView) findViewById(2131296340))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (TabInfo_xlarge.this.data.Data_ParkedTime != null) {
					String str = new SimpleDateFormat(
							"MMM d, K:mm:ss a")
					.format(TabInfo_xlarge.this.data.Data_ParkedTime);
					AlertDialog.Builder localBuilder = new AlertDialog.Builder(
							TabInfo_xlarge.this);
					localBuilder
					.setMessage("Parked since: " + str)
					.setCancelable(true)
					.setPositiveButton(
							"Close",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramAnonymous2DialogInterface,
										int paramAnonymous2Int) {
									paramAnonymous2DialogInterface
									.dismiss();
								}
							})
							.setTitle(
									TabInfo_xlarge.this.data.VehicleID);
					TabInfo_xlarge.this.lastUpdatedDialog = localBuilder
							.create();
					TabInfo_xlarge.this.lastUpdatedDialog.show();
				}
			}
		});
		((TextView) findViewById(2131296351))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				ServerCommands.SetChargeMode(TabInfo_xlarge.this,
						(OVMSActivity) TabInfo_xlarge.this.getParent(),
						null);
			}
		});
		((TextView) findViewById(2131296356))
		.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				ServerCommands.SetChargeCurrent(TabInfo_xlarge.this,
						(OVMSActivity) TabInfo_xlarge.this.getParent(),
						null,
						TabInfo_xlarge.this.data.Data_ChargeAmpsLimit);
			}
		});
		((SeekBar) findViewById(2131296352))
		.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			public void onProgressChanged(
					SeekBar paramAnonymousSeekBar,
					int paramAnonymousInt, boolean paramAnonymousBoolean) {
				paramAnonymousSeekBar.setProgress(paramAnonymousInt);
			}

			public void onStartTrackingTouch(
					SeekBar paramAnonymousSeekBar) {
			}

			public void onStopTrackingTouch(
					final SeekBar paramAnonymousSeekBar) {
				int i = 0;
				if (paramAnonymousSeekBar.getProgress() < 25) {
					paramAnonymousSeekBar.setProgress(0);
					if (TabInfo_xlarge.this.data.Data_Charging)
						Toast.makeText(TabInfo_xlarge.this,
								"Already charging...", 0).show();
				}
				while (true) {
					return;
					ServerCommands
					.StartCharge(
							TabInfo_xlarge.this,
							(OVMSActivity) TabInfo_xlarge.this
							.getParent(), null)
							.setOnCancelListener(
									new DialogInterface.OnCancelListener() {
										public void onCancel(
												DialogInterface paramAnonymous2DialogInterface) {
											paramAnonymousSeekBar
											.setProgress(paramAnonymousSeekBar
													.getMax());
										}
									});
					continue;
					if (paramAnonymousSeekBar.getProgress() <= -25
							+ paramAnonymousSeekBar.getMax())
						break;
					paramAnonymousSeekBar
					.setProgress(paramAnonymousSeekBar.getMax());
					if (!TabInfo_xlarge.this.data.Data_Charging)
						Toast.makeText(TabInfo_xlarge.this,
								"Already stopped...", 0).show();
					else
						ServerCommands
						.StopCharge(
								TabInfo_xlarge.this,
								(OVMSActivity) TabInfo_xlarge.this
								.getParent(), null)
								.setOnCancelListener(
										new DialogInterface.OnCancelListener() {
											public void onCancel(
													DialogInterface paramAnonymous2DialogInterface) {
												paramAnonymousSeekBar
												.setProgress(0);
											}
										});
				}
				if (TabInfo_xlarge.this.data.Data_Charging)
					;
				while (true) {
					paramAnonymousSeekBar.setProgress(i);
					break;
					i = 100;
				}
			}
		});
	}

	private void initMapUI() {
		this.mapView = ((MapView) findViewById(2131296385));
		this.mc = this.mapView.getController();
		this.mapView.setBuiltInZoomControls(true);
		this.DirectionalMarker = BitmapFactory.decodeResource(getResources(),
				2130837512);
		this.mapOverlays = this.mapView.getOverlays();
		this.carMarkers = new Utilities.CarMarkerOverlay(getResources()
				.getDrawable(2130837534), 20, this, this.DirectionalMarker, 1);
		this.mapOverlays.add(0, this.carMarkers);
	}

	private void updateCarLayoutUI()
	{
		TextView localTextView1 = (TextView)findViewById(2131296372);
		int i;
		if (this.data.Data_LeftDoorOpen)
			i = 0;
		while (true)
		{
			localTextView1.setVisibility(i);
			TextView localTextView2 = (TextView)findViewById(2131296373);
			int j;
			label52: int k;
			label83: int m;
			label115: int n;
			label147: String str2;
			label220: String str1;
			label236: int i1;
			label290: int i2;
			label378: int i3;
			label466: int i4;
			label554: int i5;
			label622: int i6;
			label656: int i7;
			label737: int i8;
			label771: int i9;
			label852: int i10;
			label886: int i11;
			label967: int i12;
			label1001: label1136: int i13;
			label1161: int i14;
			label1193: int i15;
			label1225: int i16;
			label1257: int i17;
			label1289: int i18;
			label1323: int i19;
			label1357: ImageView localImageView9;
			int i20;
			if (this.data.Data_RightDoorOpen)
			{
				j = 0;
				localTextView2.setVisibility(j);
				TextView localTextView3 = (TextView)findViewById(2131296374);
				if (!this.data.Data_ChargePortOpen)
					break label1434;
				k = 0;
				localTextView3.setVisibility(k);
				TextView localTextView4 = (TextView)findViewById(2131296376);
				if (!this.data.Data_BonnetOpen)
					break label1440;
				m = 0;
				localTextView4.setVisibility(m);
				TextView localTextView5 = (TextView)findViewById(2131296375);
				if (!this.data.Data_TrunkOpen)
					break label1446;
				n = 0;
				localTextView5.setVisibility(n);
				TextView localTextView6 = (TextView)findViewById(2131296377);
				if (this.data.Data_Speed <= 0.0D)
					break label1460;
				Object[] arrayOfObject11 = new Object[2];
				arrayOfObject11[0] = Integer.valueOf((int)this.data.Data_Speed);
				if (!this.data.Data_DistanceUnit.equals("K"))
					break label1452;
				str2 = "kph";
				arrayOfObject11[1] = str2;
				str1 = String.format("%d %s", arrayOfObject11);
				localTextView6.setText(str1);
				TextView localTextView7 = (TextView)findViewById(2131296381);
				if ((!this.data.Data_PEM_Motor_Battery_TemperaturesDataStale) && ((this.data.Data_CarPoweredON) || (this.data.Data_CoolingPumpON_DoorState3)))
					break label1468;
				i1 = -12303292;
				localTextView7.setTextColor(i1);
				Object[] arrayOfObject1 = new Object[1];
				arrayOfObject1[0] = Integer.valueOf((int)this.data.Data_TemperaturePEM);
				localTextView7.setText(String.format("%d¡C", arrayOfObject1));
				TextView localTextView8 = (TextView)findViewById(2131296382);
				if ((!this.data.Data_PEM_Motor_Battery_TemperaturesDataStale) && ((this.data.Data_CarPoweredON) || (this.data.Data_CoolingPumpON_DoorState3)))
					break label1475;
				i2 = -12303292;
				localTextView8.setTextColor(i2);
				Object[] arrayOfObject2 = new Object[1];
				arrayOfObject2[0] = Integer.valueOf((int)this.data.Data_TemperatureMotor);
				localTextView8.setText(String.format("%d¡C", arrayOfObject2));
				TextView localTextView9 = (TextView)findViewById(2131296383);
				if ((!this.data.Data_PEM_Motor_Battery_TemperaturesDataStale) && ((this.data.Data_CarPoweredON) || (this.data.Data_CoolingPumpON_DoorState3)))
					break label1482;
				i3 = -12303292;
				localTextView9.setTextColor(i3);
				Object[] arrayOfObject3 = new Object[1];
				arrayOfObject3[0] = Integer.valueOf((int)this.data.Data_TemperatureBattery);
				localTextView9.setText(String.format("%d¡C", arrayOfObject3));
				TextView localTextView10 = (TextView)findViewById(2131296384);
				if ((!this.data.Data_AmbientTemperatureDataStale) && ((this.data.Data_CarPoweredON) || (this.data.Data_CoolingPumpON_DoorState3)))
					break label1489;
				i4 = -12303292;
				localTextView10.setTextColor(i4);
				Object[] arrayOfObject4 = new Object[1];
				arrayOfObject4[0] = Integer.valueOf((int)this.data.Data_TemperatureAmbient);
				localTextView10.setText(String.format("%d¡C", arrayOfObject4));
				TextView localTextView11 = (TextView)findViewById(2131296368);
				if (!this.data.Data_TPMSDataStale)
					break label1496;
				i5 = -12303292;
				localTextView11.setTextColor(i5);
				if ((this.data.Data_FLWheelPressure == 0.0D) && (this.data.Data_FLWheelTemperature == 0.0D))
					break label1503;
				i6 = 0;
				localTextView11.setVisibility(i6);
				Object[] arrayOfObject5 = new Object[2];
				arrayOfObject5[0] = Double.valueOf(this.data.Data_FLWheelPressure);
				arrayOfObject5[1] = Double.valueOf(this.data.Data_FLWheelTemperature);
				localTextView11.setText(String.format("%.1fpsi\n%.0f¡C", arrayOfObject5));
				TextView localTextView12 = (TextView)findViewById(2131296369);
				if (!this.data.Data_TPMSDataStale)
					break label1509;
				i7 = -12303292;
				localTextView12.setTextColor(i7);
				if ((this.data.Data_FRWheelPressure == 0.0D) && (this.data.Data_FRWheelTemperature == 0.0D))
					break label1516;
				i8 = 0;
				localTextView12.setVisibility(i8);
				Object[] arrayOfObject6 = new Object[2];
				arrayOfObject6[0] = Double.valueOf(this.data.Data_FRWheelPressure);
				arrayOfObject6[1] = Double.valueOf(this.data.Data_FRWheelTemperature);
				localTextView12.setText(String.format("%.1fpsi\n%.0f¡C", arrayOfObject6));
				TextView localTextView13 = (TextView)findViewById(2131296370);
				if (!this.data.Data_TPMSDataStale)
					break label1522;
				i9 = -12303292;
				localTextView13.setTextColor(i9);
				if ((this.data.Data_RLWheelPressure == 0.0D) && (this.data.Data_RLWheelTemperature == 0.0D))
					break label1529;
				i10 = 0;
				localTextView13.setVisibility(i10);
				Object[] arrayOfObject7 = new Object[2];
				arrayOfObject7[0] = Double.valueOf(this.data.Data_RLWheelPressure);
				arrayOfObject7[1] = Double.valueOf(this.data.Data_RLWheelTemperature);
				localTextView13.setText(String.format("%.1fpsi\n%.0f¡C", arrayOfObject7));
				TextView localTextView14 = (TextView)findViewById(2131296371);
				if (!this.data.Data_TPMSDataStale)
					break label1535;
				i11 = -12303292;
				localTextView14.setTextColor(i11);
				if ((this.data.Data_RRWheelPressure == 0.0D) && (this.data.Data_RRWheelTemperature == 0.0D))
					break label1542;
				i12 = 0;
				localTextView14.setVisibility(i12);
				Object[] arrayOfObject8 = new Object[2];
				arrayOfObject8[0] = Double.valueOf(this.data.Data_RRWheelPressure);
				arrayOfObject8[1] = Double.valueOf(this.data.Data_RRWheelTemperature);
				localTextView14.setText(String.format("%.1fpsi\n%.0f¡C", arrayOfObject8));
				ImageView localImageView1 = (ImageView)findViewById(2131296359);
				StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(getCacheDir().getAbsolutePath()));
				Object[] arrayOfObject9 = new Object[1];
				arrayOfObject9[0] = this.data.VehicleImageDrawable;
				Bitmap localBitmap = BitmapFactory.decodeFile(String.format("/ol_%s.png", arrayOfObject9));
				if (localBitmap == null)
					break label1548;
				localImageView1.setImageBitmap(localBitmap);
				ImageView localImageView2 = (ImageView)findViewById(2131296360);
				if (!this.data.Data_ChargePortOpen)
					break label1721;
				i13 = 0;
				localImageView2.setVisibility(i13);
				ImageView localImageView3 = (ImageView)findViewById(2131296363);
				if (!this.data.Data_BonnetOpen)
					break label1728;
				i14 = 0;
				localImageView3.setVisibility(i14);
				ImageView localImageView4 = (ImageView)findViewById(2131296364);
				if (!this.data.Data_LeftDoorOpen)
					break label1735;
				i15 = 0;
				localImageView4.setVisibility(i15);
				ImageView localImageView5 = (ImageView)findViewById(2131296362);
				if (!this.data.Data_RightDoorOpen)
					break label1742;
				i16 = 0;
				localImageView5.setVisibility(i16);
				ImageView localImageView6 = (ImageView)findViewById(2131296361);
				if (!this.data.Data_TrunkOpen)
					break label1749;
				i17 = 0;
				localImageView6.setVisibility(i17);
				ImageView localImageView7 = (ImageView)findViewById(2131296365);
				if (!this.data.Data_CarLocked)
					break label1756;
				i18 = 2130837563;
				localImageView7.setImageResource(i18);
				ImageView localImageView8 = (ImageView)findViewById(2131296366);
				if (!this.data.Data_ValetON)
					break label1764;
				i19 = 2130837566;
				localImageView8.setImageResource(i19);
				localImageView9 = (ImageView)findViewById(2131296367);
				if (!this.data.Data_HeadlightsON)
					break label1772;
				i20 = 0;
				label1389: localImageView9.setVisibility(i20);
			}
			try
			{
				int i21 = Integer.parseInt(this.data.Data_CarModuleGSMSignalLevel);
				if (i21 < 1)
					localImageView9.setImageResource(2130837608);
				while (true)
				{
					label1422: return;
				i = 4;
				break;
				j = 4;
				break label52;
				label1434: k = 4;
				break label83;
				label1440: m = 4;
				break label115;
				label1446: n = 4;
				break label147;
				label1452: str2 = "mph";
				break label220;
				label1460: str1 = "";
				break label236;
				label1468: i1 = -1;
				break label290;
				label1475: i2 = -1;
				break label378;
				label1482: i3 = -1;
				break label466;
				label1489: i4 = -1;
				break label554;
				label1496: i5 = -1;
				break label622;
				label1503: i6 = 4;
				break label656;
				label1509: i7 = -1;
				break label737;
				label1516: i8 = 4;
				break label771;
				label1522: i9 = -1;
				break label852;
				label1529: i10 = 4;
				break label886;
				label1535: i11 = -1;
				break label967;
				label1542: i12 = 4;
				break label1001;
				label1548: StringBuilder localStringBuilder2 = new StringBuilder("** File Not Found: ").append(getCacheDir().getAbsolutePath());
				Object[] arrayOfObject10 = new Object[1];
				arrayOfObject10[0] = this.data.VehicleImageDrawable;
				Log.d("OVMS", String.format("/ol_%s.png", arrayOfObject10));
				if ((this.data.DontAskLayoutDownload) || ((this.lastUpdatedDialog != null) && (this.lastUpdatedDialog.isShowing())))
					break label1136;
				this.data.DontAskLayoutDownload = true;
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
				localBuilder.setMessage("Would you like to download a set of high resolution car images specifically drawn for your car?\n\nThe download is approx. 300KB.\n\nNote: a manual download button is available in the car commands and settings tab.").setTitle("Download Graphics").setCancelable(true).setPositiveButton("Download Now", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
					{
						TabInfo_xlarge.this.downloadLayout();
						paramAnonymousDialogInterface.dismiss();
					}
				}).setNegativeButton("Later", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
					{
						paramAnonymousDialogInterface.dismiss();
					}
				});
				this.lastUpdatedDialog = localBuilder.create();
				this.lastUpdatedDialog.show();
				break label1136;
				label1721: i13 = 8;
				break label1161;
				label1728: i14 = 8;
				break label1193;
				label1735: i15 = 8;
				break label1225;
				label1742: i16 = 8;
				break label1257;
				label1749: i17 = 8;
				break label1289;
				label1756: i18 = 2130837564;
				break label1323;
				label1764: i19 = 2130837565;
				break label1357;
				label1772: i20 = 8;
				break label1389;
				if (i21 < 7)
					localImageView9.setImageResource(2130837609);
				else if (i21 < 14)
					localImageView9.setImageResource(2130837610);
				else if (i21 < 21)
					localImageView9.setImageResource(2130837611);
				else if (i21 < 28)
					localImageView9.setImageResource(2130837612);
				else
					localImageView9.setImageResource(2130837613);
				}
			}
			catch (Exception localException)
			{
				break label1422;
			}
		}
	}

	private void updateInfoUI()
	{
		int i = 8;
		((TextView)findViewById(2131296346)).setText(this.data.VehicleID);
		TextView localTextView1 = (TextView)findViewById(2131296356);
		String str1 = getString(2131099652);
		Object[] arrayOfObject1 = new Object[1];
		arrayOfObject1[0] = Integer.valueOf(this.data.Data_SOC);
		localTextView1.setText(String.format(str1, arrayOfObject1));
		TableRow localTableRow;
		int m;
		if (findViewById(2131296348) != null)
		{
			localTableRow = (TableRow)findViewById(2131296348);
			if (!this.data.Data_ChargePortOpen)
				break label617;
			m = 0;
		}
		while (true)
		{
			localTableRow.setVisibility(m);
			SeekBar localSeekBar = (SeekBar)findViewById(2131296352);
			TextView localTextView2 = (TextView)findViewById(2131296351);
			label187: TextView localTextView3;
			ImageView localImageView1;
			label280: int j;
			label414: ImageView localImageView4;
			if (this.data.Data_ChargeState.equals("charging"))
			{
				Object[] arrayOfObject11 = new Object[1];
				arrayOfObject11[0] = this.data.Data_ChargeMode.toUpperCase();
				localTextView2.setText(String.format("Charging - %s", arrayOfObject11));
				localTextView3 = (TextView)findViewById(2131296350);
				localImageView1 = (ImageView)findViewById(2131296354);
				if (!this.data.Data_Charging)
					break label883;
				localSeekBar.setProgress(0);
				localImageView1.setVisibility(0);
				Object[] arrayOfObject6 = new Object[2];
				arrayOfObject6[0] = Integer.valueOf(this.data.Data_ChargeCurrent);
				arrayOfObject6[1] = Integer.valueOf(this.data.Data_LineVoltage);
				localTextView3.setText(String.format("%sA|%sV", arrayOfObject6));
				String str2 = " km";
				if ((this.data.Data_DistanceUnit != null) && (!this.data.Data_DistanceUnit.equals("K")))
					str2 = " miles";
				((TextView)findViewById(2131296358)).setText(this.data.Data_IdealRange + str2);
				((TextView)findViewById(2131296357)).setText(this.data.Data_EstimatedRange + str2);
				ImageView localImageView2 = (ImageView)findViewById(2131296341);
				if (!this.isLoggedIn)
					break label932;
				j = i;
				localImageView2.setVisibility(j);
				ImageView localImageView3 = (ImageView)findViewById(2131296342);
				if (this.data.ParanoidMode)
					i = 0;
				localImageView3.setVisibility(i);
				localImageView4 = (ImageView)findViewById(2131296344);
			}
			try
			{
				int k = Integer.parseInt(this.data.Data_CarModuleGSMSignalLevel);
				label489: ImageView localImageView5;
				if (k < 1)
				{
					localImageView4.setImageResource(2130837608);
					((ImageView)findViewById(2131296355)).setImageBitmap(Utilities.GetScaledBatteryOverlay(this.data.Data_SOC, BitmapFactory.decodeResource(getResources(), 2130837508)));
					localImageView5 = (ImageView)findViewById(2131296347);
					StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(getCacheDir().getAbsolutePath()));
					Object[] arrayOfObject4 = new Object[1];
					arrayOfObject4[0] = this.data.VehicleImageDrawable;
					Bitmap localBitmap = BitmapFactory.decodeFile(String.format("/%s.png", arrayOfObject4));
					if (localBitmap == null)
						break label1021;
					localImageView5.setImageBitmap(localBitmap);
				}
				while (true)
				{
					localImageView5.setOnClickListener(new View.OnClickListener()
					{
						public void onClick(View paramAnonymousView)
						{
							AlertDialog.Builder localBuilder = new AlertDialog.Builder(TabInfo_xlarge.this);
							Object[] arrayOfObject = new Object[10];
							String str1;
							String str2;
							label83: String str3;
							label105: String str4;
							label127: String str5;
							if (TabInfo_xlarge.this.data.Data_CarPoweredON)
							{
								str1 = "ON";
								arrayOfObject[0] = str1;
								arrayOfObject[1] = TabInfo_xlarge.this.data.Data_VIN;
								arrayOfObject[2] = TabInfo_xlarge.this.data.Data_CarModuleGSMSignalLevel;
								if (!TabInfo_xlarge.this.data.Data_HandBrakeApplied)
									break label268;
								str2 = "ENGAGED";
								arrayOfObject[3] = str2;
								if (!TabInfo_xlarge.this.data.Data_ValetON)
									break label275;
								str3 = "ON";
								arrayOfObject[4] = str3;
								if (!TabInfo_xlarge.this.data.Data_PINLocked)
									break label282;
								str4 = "ON";
								arrayOfObject[5] = str4;
								if (!TabInfo_xlarge.this.data.Data_CoolingPumpON_DoorState3)
									break label289;
								str5 = "ON";
								label149: arrayOfObject[6] = str5;
								if (!TabInfo_xlarge.this.data.Data_GPSLocked)
									break label296;
							}
							label268: label275: label282: label289: label296: for (String str6 = "LOCKED"; ; str6 = "(searching...)")
							{
								arrayOfObject[7] = str6;
								arrayOfObject[8] = TabInfo_xlarge.this.data.Data_CarModuleFirmwareVersion;
								arrayOfObject[9] = TabInfo_xlarge.this.data.Data_OVMSServerFirmwareVersion;
								localBuilder.setMessage(String.format("Power: %s\nVIN: %s\nGSM Signal: %s\nHandbrake: %s\nValet: %s\nLock: %s\nCooling Pump: %s\nGPS: %s\n\nCar Module: %s\nOVMS Server: %s", arrayOfObject)).setTitle("Vehicle Information").setCancelable(true).setPositiveButton("Close", new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
									{
										paramAnonymous2DialogInterface.dismiss();
									}
								});
								TabInfo_xlarge.this.softwareInformation = localBuilder.create();
								TabInfo_xlarge.this.softwareInformation.show();
								return;
								str1 = "OFF";
								break;
								str2 = "DISENGAGED";
								break label83;
								str3 = "OFF";
								break label105;
								str4 = "OFF";
								break label127;
								str5 = "OFF";
								break label149;
							}
						}
					});
					return;
					label617: m = i;
					break;
					if (this.data.Data_ChargeState.equals("prepare"))
					{
						Object[] arrayOfObject10 = new Object[1];
						arrayOfObject10[0] = this.data.Data_ChargeMode.toUpperCase();
						localTextView2.setText(String.format("Preparing to Charge - %s", arrayOfObject10));
						break label187;
					}
					if (this.data.Data_ChargeState.equals("heating"))
					{
						Object[] arrayOfObject9 = new Object[1];
						arrayOfObject9[0] = this.data.Data_ChargeMode.toUpperCase();
						localTextView2.setText(String.format("Pre-Charge Battery Heating - %s", arrayOfObject9));
						break label187;
					}
					if (this.data.Data_ChargeState.equals("topoff"))
					{
						Object[] arrayOfObject8 = new Object[1];
						arrayOfObject8[0] = this.data.Data_ChargeMode.toUpperCase();
						localTextView2.setText(String.format("Topping Off - %s", arrayOfObject8));
						break label187;
					}
					if (this.data.Data_ChargeState.equals("stopped"))
					{
						Object[] arrayOfObject7 = new Object[1];
						arrayOfObject7[0] = this.data.Data_ChargeMode.toUpperCase();
						localTextView2.setText(String.format("Charge Interrupted - %s", arrayOfObject7));
						break label187;
					}
					if (!this.data.Data_ChargeState.equals("done"))
						break label187;
					Object[] arrayOfObject2 = new Object[1];
					arrayOfObject2[0] = this.data.Data_ChargeMode.toUpperCase();
					localTextView2.setText(String.format("Charge Completed - %s", arrayOfObject2));
					break label187;
					label883: localSeekBar.setProgress(100);
					localImageView1.setVisibility(i);
					Object[] arrayOfObject3 = new Object[1];
					arrayOfObject3[0] = Integer.valueOf(this.data.Data_ChargeAmpsLimit);
					localTextView3.setText(String.format("%sA MAX", arrayOfObject3));
					break label280;
					label932: j = 0;
					break label414;
					if (k < 7)
					{
						localImageView4.setImageResource(2130837609);
						break label489;
					}
					if (k < 14)
					{
						localImageView4.setImageResource(2130837610);
						break label489;
					}
					if (k < 21)
					{
						localImageView4.setImageResource(2130837611);
						break label489;
					}
					if (k < 28)
					{
						localImageView4.setImageResource(2130837612);
						break label489;
					}
					localImageView4.setImageResource(2130837613);
					break label489;
					label1021: StringBuilder localStringBuilder2 = new StringBuilder("** File Not Found: ").append(getCacheDir().getAbsolutePath());
					Object[] arrayOfObject5 = new Object[1];
					arrayOfObject5[0] = this.data.VehicleImageDrawable;
					Log.d("OVMS", String.format("/%s.png", arrayOfObject5));
					if ((!this.data.DontAskLayoutDownload) && ((this.lastUpdatedDialog == null) || (!this.lastUpdatedDialog.isShowing())))
					{
						this.data.DontAskLayoutDownload = true;
						AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
						localBuilder.setMessage("Would you like to download a set of high resolution car images specifically drawn for your car?\n\nThe download is approx. 300KB.\n\nNote: a manual download button is available in the car commands and settings tab.").setTitle("Download Graphics").setCancelable(true).setPositiveButton("Download Now", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
							{
								TabInfo_xlarge.this.downloadLayout();
								paramAnonymousDialogInterface.dismiss();
							}
						}).setNegativeButton("Later", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
							{
								paramAnonymousDialogInterface.dismiss();
							}
						});
						this.lastUpdatedDialog = localBuilder.create();
						this.lastUpdatedDialog.show();
					}
				}
			}
			catch (Exception localException)
			{
				break label489;
			}
		}
	}

	private void updateLastUpdatedView()
	{
		if ((this.data == null) || (this.data.Data_LastCarUpdate == null));
		while (true)
		{
			return;
			TextView localTextView1 = (TextView)findViewById(2131296343);
			long l1 = (new Date().getTime() - this.data.Data_LastCarUpdate.getTime()) / 1000L;
			label69: TextView localTextView2;
			LinearLayout localLinearLayout;
			long l2;
			if (l1 < 60L)
			{
				localTextView1.setText("live");
				localTextView2 = (TextView)findViewById(2131296340);
				localLinearLayout = (LinearLayout)findViewById(2131296338);
				if ((this.data.Data_CarPoweredON) || (this.data.Data_ParkedTime_raw == 0.0D))
					break label832;
				l2 = l1 + ()this.data.Data_ParkedTime_raw;
				this.data.Data_ParkedTime = new Date(new Date().getTime() - 1000L * l2);
				if (l2 >= 60L)
					break label449;
				localTextView2.setText("just now");
			}
			while (true)
			{
				localLinearLayout.setVisibility(0);
				break;
				if (l1 < 3600L)
				{
					int i3 = (int)Math.ceil(l1 / 60L);
					Object[] arrayOfObject8 = new Object[2];
					arrayOfObject8[0] = Integer.valueOf(i3);
					if (i3 > 1);
					for (String str9 = "s"; ; str9 = "")
					{
						arrayOfObject8[1] = str9;
						localTextView1.setText(String.format("Updated: %d min%s ago", arrayOfObject8));
						break;
					}
				}
				if (l1 < 86400L)
				{
					int i2 = (int)Math.ceil(l1 / 3600L);
					Object[] arrayOfObject7 = new Object[2];
					arrayOfObject7[0] = Integer.valueOf(i2);
					if (i2 > 1);
					for (String str8 = "s"; ; str8 = "")
					{
						arrayOfObject7[1] = str8;
						localTextView1.setText(String.format("Updated: %d hr%s ago", arrayOfObject7));
						break;
					}
				}
				if (l1 < 864000L)
				{
					int i1 = (int)Math.ceil(l1 / 86400L);
					Object[] arrayOfObject6 = new Object[2];
					arrayOfObject6[0] = Integer.valueOf(i1);
					if (i1 > 1);
					for (String str7 = "s"; ; str7 = "")
					{
						arrayOfObject6[1] = str7;
						localTextView1.setText(String.format("Updated: %d day%s ago", arrayOfObject6));
						break;
					}
				}
				String str1 = getString(2131099651);
				Object[] arrayOfObject1 = new Object[1];
				arrayOfObject1[0] = this.data.Data_LastCarUpdate;
				localTextView1.setText(String.format(str1, arrayOfObject1));
				break label69;
				label449: if (l2 < 3600L)
				{
					int n = (int)Math.ceil(l2 / 60L);
					Object[] arrayOfObject5 = new Object[2];
					arrayOfObject5[0] = Integer.valueOf(n);
					if (n > 1);
					for (String str6 = "s"; ; str6 = "")
					{
						arrayOfObject5[1] = str6;
						localTextView2.setText(String.format("%d min%s", arrayOfObject5));
						break;
					}
				}
				if (l2 < 86400L)
				{
					int k = (int)Math.floor(l2 / 3600L);
					int m = (int)Math.ceil(Math.abs((l2 - k * 3600) / 60L));
					Object[] arrayOfObject4 = new Object[4];
					arrayOfObject4[0] = Integer.valueOf(k);
					String str4;
					if (k > 1)
					{
						str4 = "s";
						label599: arrayOfObject4[1] = str4;
						arrayOfObject4[2] = Integer.valueOf(m);
						if (m <= 1)
							break label655;
					}
					label655: for (String str5 = "s"; ; str5 = "")
					{
						arrayOfObject4[3] = str5;
						localTextView2.setText(String.format("%d hr%s %d min%s", arrayOfObject4));
						break;
						str4 = "";
						break label599;
					}
				}
				if (l2 < 864000L)
				{
					int i = (int)Math.floor(l2 / 86400L);
					int j = (int)Math.ceil(Math.abs((l2 - 86400 * i) / 3600L));
					Object[] arrayOfObject3 = new Object[4];
					arrayOfObject3[0] = Integer.valueOf(i);
					String str2;
					if (i > 1)
					{
						str2 = "s";
						label735: arrayOfObject3[1] = str2;
						arrayOfObject3[2] = Integer.valueOf(j);
						if (j <= 1)
							break label791;
					}
					label791: for (String str3 = "s"; ; str3 = "")
					{
						arrayOfObject3[3] = str3;
						localTextView2.setText(String.format("%d day%s %d hr%s", arrayOfObject3));
						break;
						str2 = "";
						break label735;
					}
				}
				Object[] arrayOfObject2 = new Object[1];
				arrayOfObject2[0] = this.data.Data_ParkedTime;
				localTextView2.setText(String.format("%1$tD %1$tT", arrayOfObject2));
			}
			label832: localLinearLayout.setVisibility(8);
		}
	}

	private void updateMapUI()
	{
		Log.d("OVMS", "Refreshing Map");
		try
		{
			if (!this.currentVehicleID.equals(this.data.VehicleID))
			{
				this.currentVehicleID = this.data.VehicleID;
				this.carMarkers = new Utilities.CarMarkerOverlay(getResources().getDrawable(getResources().getIdentifier(this.data.VehicleImageDrawable + "32x32", "drawable", "com.openvehicles.OVMS")), 20, this, this.DirectionalMarker, 1);
				this.mapOverlays.set(0, this.carMarkers);
			}
			label118: GeoPoint localGeoPoint = Utilities.GetCarGeopoint(this.data);
			if ((this.carMarkers.size() == 0) || (!this.carMarkers.getItem(0).getPoint().equals(localGeoPoint)))
			{
				if (this.carMarkers.size() <= 0)
					break label234;
				this.lastCarGeoPoint = this.carMarkers.getItem(0).getPoint();
				this.carMarkerAnimationTimerHandler.removeCallbacks(this.animateCarMarker);
				this.carMarkerAnimationFrame = 0;
				this.carMarkerAnimationTimerHandler.postDelayed(this.animateCarMarker, 0L);
			}
			while (true)
			{
				this.mc.animateTo(localGeoPoint);
				this.mc.setZoom(17);
				this.mapView.invalidate();
				return;
				label234: String str1 = "-";
				if (this.data.Data_LastCarUpdate != null)
					str1 = new SimpleDateFormat("MMM d, K:mm:ss a").format(this.data.Data_LastCarUpdate);
				String str2 = this.data.VehicleID;
				Object[] arrayOfObject = new Object[1];
				arrayOfObject[0] = str1;
				Utilities.CarMarker localCarMarker = new Utilities.CarMarker(localGeoPoint, str2, String.format("Last reported: %s", arrayOfObject), (int)this.data.Data_Direction);
				this.carMarkers.addOverlay(localCarMarker);
			}
		}
		catch (Exception localException)
		{
			break label118;
		}
	}

	public void OrientationChanged() {
		this.orientationChangedHandler.sendEmptyMessage(0);
	}

	public void Refresh(CarData paramCarData, boolean paramBoolean) {
		this.data = paramCarData;
		this.isLoggedIn = paramBoolean;
		this.handler.sendEmptyMessage(0);
	}

	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(2130903055);
		initInfoUI();
		initCarLayoutUI();
		initMapUI();
	}

	protected void onPause() {
		super.onPause();
		try {
			if ((this.softwareInformation != null)
					&& (this.softwareInformation.isShowing()))
				this.softwareInformation.dismiss();
			try {
				label28: if ((this.lastUpdatedDialog != null)
						&& (this.lastUpdatedDialog.isShowing()))
					this.lastUpdatedDialog.dismiss();
			label52: this.lastUpdateTimerHandler
			.removeCallbacks(this.lastUpdateTimer);
			return;
			} catch (Exception localException2) {
				break label52;
			}
		} catch (Exception localException1) {
			break label28;
		}
	}

	protected void onResume() {
		super.onResume();
		this.lastUpdateTimerHandler.postDelayed(this.lastUpdateTimer, 5000L);
	}
}