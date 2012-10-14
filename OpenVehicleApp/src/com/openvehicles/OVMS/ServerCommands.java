package com.openvehicles.OVMS;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.Toast;

public final class ServerCommands {
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
	private static final CharSequence[] chargeModes = arrayOfCharSequence;

	static {
		CharSequence[] arrayOfCharSequence = new CharSequence[4];
		arrayOfCharSequence[0] = "Standard";
		arrayOfCharSequence[1] = "Storage";
		arrayOfCharSequence[2] = "Range";
		arrayOfCharSequence[3] = "Performance";
	}

	public static String ACTIVATE_VALET_MODE(String paramString) {
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = "C21";
		arrayOfObject[1] = paramString;
		return String.format("%s,%s", arrayOfObject);
	}

	public static String DEACTIVATE_VALET_MODE(String paramString) {
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = "C23";
		arrayOfObject[1] = paramString;
		return String.format("%s,%s", arrayOfObject);
	}

	public static String LOCK_CAR(String paramString) {
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = "C20";
		arrayOfObject[1] = paramString;
		return String.format("%s,%s", arrayOfObject);
	}

	public static AlertDialog LockUnlockCar(final Context paramContext,
			final OVMSActivity paramOVMSActivity, final Toast paramToast,
			boolean paramBoolean) {
		InputFilter[] arrayOfInputFilter = new InputFilter[1];
		arrayOfInputFilter[0] = new InputFilter.LengthFilter(8);
		DigitsKeyListener localDigitsKeyListener = new DigitsKeyListener(false,
				false);
		final EditText localEditText = new EditText(paramContext);
		localEditText.setFilters(arrayOfInputFilter);
		localEditText.setInputType(8192);
		localEditText.setTransformationMethod(PasswordTransformationMethod
				.getInstance());
		localEditText.setHint("Vehicle PIN");
		localEditText.setKeyListener(localDigitsKeyListener);
		AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(
				paramContext);
		AlertDialog.Builder localBuilder2 = localBuilder1
				.setMessage("Vehicle PIN:");
		String str1;
		AlertDialog.Builder localBuilder3;
		if (paramBoolean) {
			str1 = "Lock Car";
			localBuilder3 = localBuilder2.setTitle(str1).setCancelable(true)
					.setView(localEditText);
			if (!paramBoolean)
				break label188;
		}
		label188: for (String str2 = "Lock";; str2 = "Unlock") {
			localBuilder3.setPositiveButton(str2,
					new DialogInterface.OnClickListener() {
				public void onClick(
						DialogInterface paramAnonymousDialogInterface,
						int paramAnonymousInt) {
					Toast localToast;
					Context localContext;
					if (this.val$lock) {
						paramOVMSActivity
						.SendServerCommand(ServerCommands
								.LOCK_CAR(localEditText
										.getText().toString()));
						localToast = paramToast;
						localContext = paramContext;
						if (!this.val$lock)
							break label96;
					}
					label96: for (String str = "Locking...";; str = "Unlocking...") {
						ServerCommands.makeToast(localToast,
								localContext, str, 0);
						paramAnonymousDialogInterface.dismiss();
						return;
						paramOVMSActivity
						.SendServerCommand(ServerCommands
								.UNLOCK_CAR(localEditText
										.getText().toString()));
						break;
					}
				}
			}).setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
				public void onClick(
						DialogInterface paramAnonymousDialogInterface,
						int paramAnonymousInt) {
					paramAnonymousDialogInterface.cancel();
				}
			});
			AlertDialog localAlertDialog = localBuilder1.create();
			localAlertDialog.show();
			return localAlertDialog;
			str1 = "Unlock Car";
			break;
		}
	}

	public static void RequestC2DMRegistrationID(Context paramContext) {
		Toast.makeText(paramContext, "Initializing push notification", 0)
		.show();
		Intent localIntent = new Intent(
				"com.google.android.c2dm.intent.REGISTER");
		localIntent.putExtra("app",
				PendingIntent.getBroadcast(paramContext, 0, new Intent(), 0));
		localIntent.putExtra("sender", "openvehicles@gmail.com");
		paramContext.startService(localIntent);
	}

	public static String SEND_AT_COMMAND(String paramString) {
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = "C49";
		arrayOfObject[1] = paramString;
		return String.format("%s,%s", arrayOfObject);
	}

	public static String SEND_SMS(String paramString1, String paramString2) {
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = "C40";
		arrayOfObject[1] = paramString1;
		arrayOfObject[2] = paramString2;
		return String.format("%s,%s,%s", arrayOfObject);
	}

	public static String SEND_USSD(String paramString) {
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = "C41";
		arrayOfObject[1] = paramString;
		return String.format("%s,%s", arrayOfObject);
	}

	public static String SET_CHARGE_CURRENT(int paramInt) {
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = "C15";
		arrayOfObject[1] = Integer.valueOf(paramInt);
		return String.format("%s,%s", arrayOfObject);
	}

	public static String SET_CHARGE_MODE(int paramInt) {
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = "C10";
		arrayOfObject[1] = Integer.valueOf(paramInt);
		return String.format("%s,%s", arrayOfObject);
	}

	public static String SET_CHARGE_MODE_AND_CURRENT(int paramInt1,
			int paramInt2) {
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = "C16";
		arrayOfObject[1] = Integer.valueOf(paramInt1);
		arrayOfObject[2] = Integer.valueOf(paramInt2);
		return String.format("%s,%s,%s", arrayOfObject);
	}

	public static String SET_FEATURE(int paramInt, String paramString) {
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = "C2";
		arrayOfObject[1] = Integer.valueOf(paramInt);
		arrayOfObject[2] = paramString;
		return String.format("%s,%s,%s", arrayOfObject);
	}

	public static String SET_PARAMETER(int paramInt, String paramString) {
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = "C4";
		arrayOfObject[1] = Integer.valueOf(paramInt);
		arrayOfObject[2] = paramString;
		return String.format("%s,%s,%s", arrayOfObject);
	}

	public static String SUBSCRIBE_GROUP(String paramString) {
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = "G";
		arrayOfObject[1] = paramString;
		return String.format("%s%s,1", arrayOfObject);
	}

	public static String SUBSCRIBE_PUSH_NOTIFICATIONS(String paramString1,
			String paramString2, String paramString3, String paramString4) {
		Object[] arrayOfObject = new Object[5];
		arrayOfObject[0] = "p";
		arrayOfObject[1] = paramString1;
		arrayOfObject[2] = paramString2;
		arrayOfObject[3] = paramString3;
		arrayOfObject[4] = paramString4;
		return String.format("%s%s,c2dm,production,%s,%s,%s", arrayOfObject);
	}

	public static AlertDialog SetChargeCurrent(final Context paramContext,
			final OVMSActivity paramOVMSActivity, final Toast paramToast,
			int paramInt) {
		new InputFilter[1][0] = new InputFilter.LengthFilter(8);
		DigitsKeyListener localDigitsKeyListener = new DigitsKeyListener(false,
				false);
		InputFilter[] arrayOfInputFilter = new InputFilter[1];
		arrayOfInputFilter[0] = new InputFilter.LengthFilter(2);
		EditText localEditText = new EditText(paramContext);
		localEditText.setFilters(arrayOfInputFilter);
		localEditText.setInputType(8192);
		localEditText.setKeyListener(localDigitsKeyListener);
		localEditText.setHint("Charge Current (Amps)");
		Object[] arrayOfObject = new Object[1];
		arrayOfObject[0] = Integer.valueOf(paramInt);
		localEditText.setText(String.format("%s", arrayOfObject));
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
		localBuilder
		.setMessage("Enter desired amps (10 - 70):")
		.setTitle("Set Maximum Current")
		.setCancelable(true)
		.setView(localEditText)
		.setPositiveButton("Set",
				new DialogInterface.OnClickListener() {
			public void onClick(
					DialogInterface paramAnonymousDialogInterface,
					int paramAnonymousInt) {
				int i = Integer.parseInt(ServerCommands.this
						.getText().toString());
				if ((i >= 10) && (i <= 70)) {
					paramOVMSActivity
					.SendServerCommand(ServerCommands
							.SET_CHARGE_CURRENT(i));
					ServerCommands.makeToast(paramToast,
							paramContext,
							"Changing Max Current...", 0);
					paramAnonymousDialogInterface.dismiss();
				}
				while (true) {
					return;
					ServerCommands
					.makeToast(
							paramToast,
							paramContext,
							"Amps must be between 10 and 70",
							0);
				}
			}
		})
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(
					DialogInterface paramAnonymousDialogInterface,
					int paramAnonymousInt) {
				paramAnonymousDialogInterface.cancel();
			}
		});
		AlertDialog localAlertDialog = localBuilder.create();
		localAlertDialog.show();
		return localAlertDialog;
	}

	public static AlertDialog SetChargeMode(final Context paramContext,
			OVMSActivity paramOVMSActivity, final Toast paramToast) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
		localBuilder.setTitle("Set Charge Mode").setCancelable(true)
		.setItems(chargeModes, new DialogInterface.OnClickListener() {
			public void onClick(
					DialogInterface paramAnonymousDialogInterface,
					int paramAnonymousInt) {
				OVMSActivity localOVMSActivity = ServerCommands.this;
				if (paramAnonymousInt >= 2)
					paramAnonymousInt++;
				localOVMSActivity.SendServerCommand(ServerCommands
						.SET_CHARGE_MODE(paramAnonymousInt));
				ServerCommands.makeToast(paramToast, paramContext,
						"Changing Mode...", 0);
				paramAnonymousDialogInterface.dismiss();
			}
		});
		AlertDialog localAlertDialog = localBuilder.create();
		localAlertDialog.show();
		return localAlertDialog;
	}

	public static AlertDialog StartCharge(final Context paramContext,
			OVMSActivity paramOVMSActivity, final Toast paramToast) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
		localBuilder
		.setMessage("Do you want to start charging the car now?")
		.setTitle("Start Charging")
		.setCancelable(true)
		.setPositiveButton("Start",
				new DialogInterface.OnClickListener() {
			public void onClick(
					DialogInterface paramAnonymousDialogInterface,
					int paramAnonymousInt) {
				ServerCommands.this.SendServerCommand("C11");
				ServerCommands.makeToast(paramToast,
						paramContext, "Charge Starting...", 0);
				paramAnonymousDialogInterface.dismiss();
			}
		})
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(
					DialogInterface paramAnonymousDialogInterface,
					int paramAnonymousInt) {
				paramAnonymousDialogInterface.cancel();
			}
		});
		AlertDialog localAlertDialog = localBuilder.create();
		localAlertDialog.show();
		return localAlertDialog;
	}

	public static AlertDialog StopCharge(final Context paramContext,
			OVMSActivity paramOVMSActivity, final Toast paramToast) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
		localBuilder
		.setMessage("Do you want to stop the car from charging now?")
		.setTitle("Stop Charging")
		.setCancelable(true)
		.setPositiveButton("Stop",
				new DialogInterface.OnClickListener() {
			public void onClick(
					DialogInterface paramAnonymousDialogInterface,
					int paramAnonymousInt) {
				ServerCommands.this.SendServerCommand("C12");
				ServerCommands.makeToast(paramToast,
						paramContext, "Charge Stopping...", 0);
				paramAnonymousDialogInterface.dismiss();
			}
		})
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(
					DialogInterface paramAnonymousDialogInterface,
					int paramAnonymousInt) {
				paramAnonymousDialogInterface.cancel();
			}
		});
		AlertDialog localAlertDialog = localBuilder.create();
		localAlertDialog.show();
		return localAlertDialog;
	}

	public static String UNLOCK_CAR(String paramString) {
		Object[] arrayOfObject = new Object[2];
		arrayOfObject[0] = "C22";
		arrayOfObject[1] = paramString;
		return String.format("%s,%s", arrayOfObject);
	}

	public static AlertDialog ValetModeOnOff(final Context paramContext,
			final OVMSActivity paramOVMSActivity, final Toast paramToast,
			boolean paramBoolean) {
		InputFilter[] arrayOfInputFilter = new InputFilter[1];
		arrayOfInputFilter[0] = new InputFilter.LengthFilter(8);
		DigitsKeyListener localDigitsKeyListener = new DigitsKeyListener(false,
				false);
		final EditText localEditText = new EditText(paramContext);
		localEditText.setFilters(arrayOfInputFilter);
		localEditText.setInputType(8192);
		localEditText.setTransformationMethod(PasswordTransformationMethod
				.getInstance());
		localEditText.setHint("Vehicle PIN");
		localEditText.setKeyListener(localDigitsKeyListener);
		AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(
				paramContext);
		AlertDialog.Builder localBuilder2 = localBuilder1
				.setMessage("Vehicle PIN:");
		String str1;
		AlertDialog.Builder localBuilder3;
		if (paramBoolean) {
			str1 = "Activate Valet Mode";
			localBuilder3 = localBuilder2.setTitle(str1).setCancelable(true)
					.setView(localEditText);
			if (!paramBoolean)
				break label189;
		}
		label189: for (String str2 = "Activate";; str2 = "Deactivate") {
			localBuilder3.setPositiveButton(str2,
					new DialogInterface.OnClickListener() {
				public void onClick(
						DialogInterface paramAnonymousDialogInterface,
						int paramAnonymousInt) {
					Toast localToast;
					Context localContext;
					if (this.val$valetOn) {
						paramOVMSActivity.SendServerCommand(ServerCommands
								.ACTIVATE_VALET_MODE(localEditText
										.getText().toString()));
						localToast = paramToast;
						localContext = paramContext;
						if (!this.val$valetOn)
							break label96;
					}
					label96: for (String str = "Activating Valet...";; str = "Deactivating Valet...") {
						ServerCommands.makeToast(localToast,
								localContext, str, 0);
						paramAnonymousDialogInterface.dismiss();
						return;
						paramOVMSActivity.SendServerCommand(ServerCommands
								.DEACTIVATE_VALET_MODE(localEditText
										.getText().toString()));
						break;
					}
				}
			}).setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
				public void onClick(
						DialogInterface paramAnonymousDialogInterface,
						int paramAnonymousInt) {
					paramAnonymousDialogInterface.cancel();
				}
			});
			AlertDialog localAlertDialog = localBuilder1.create();
			localAlertDialog.show();
			return localAlertDialog;
			str1 = "Deactivate Valet Mode";
			break;
		}
	}

	public static AlertDialog WakeUp(final Context paramContext,
			final OVMSActivity paramOVMSActivity, final Toast paramToast,
			boolean paramBoolean) {
		AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(
				paramContext);
		String str1;
		AlertDialog.Builder localBuilder2;
		if (paramBoolean) {
			str1 = "Wake up the sensor systems now?";
			localBuilder2 = localBuilder1.setMessage(str1);
			if (!paramBoolean)
				break label102;
		}
		label102: for (String str2 = "Wake Up Sensors";; str2 = "Wake Up Car") {
			localBuilder2
			.setTitle(str2)
			.setCancelable(true)
			.setPositiveButton("Wake Up",
					new DialogInterface.OnClickListener() {
				public void onClick(
						DialogInterface paramAnonymousDialogInterface,
						int paramAnonymousInt) {
					if (this.val$wakeUpSensorsOnly)
						paramOVMSActivity
						.SendServerCommand("C19");
					while (true) {
						ServerCommands
						.makeToast(paramToast,
								paramContext,
								"Waking Up...", 0);
						paramAnonymousDialogInterface.dismiss();
						return;
						paramOVMSActivity
						.SendServerCommand("C18");
					}
				}
			})
			.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
				public void onClick(
						DialogInterface paramAnonymousDialogInterface,
						int paramAnonymousInt) {
					paramAnonymousDialogInterface.cancel();
				}
			});
			AlertDialog localAlertDialog = localBuilder1.create();
			localAlertDialog.show();
			return localAlertDialog;
			str1 = "Wake up the car and its sensor systems now?";
			break;
		}
	}

	public static void makeToast(Toast paramToast, Context paramContext,
			String paramString, int paramInt) {
		if (paramToast != null)
			paramToast.cancel();
		Toast.makeText(paramContext, paramString, paramInt).show();
	}

	public static String toString(int paramInt) {
		String str;
		switch (paramInt) {
		default:
			str = null;
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 10:
		case 11:
		case 12:
		case 20:
		case 21:
		case 22:
		case 23:
		case 30:
		case 40:
		case 41:
		case 49:
		}
		while (true) {
			return str;
			str = "Features Request";
			continue;
			str = "Set Feature";
			continue;
			str = "Parameters Request";
			continue;
			str = "Set Parameter";
			continue;
			str = "System Reboot";
			continue;
			str = "Set Charge Mode";
			continue;
			str = "Start Charge";
			continue;
			str = "Stop Charge";
			continue;
			str = "Lock Car";
			continue;
			str = "Activate Valet Mode";
			continue;
			str = "Unlock Car";
			continue;
			str = "Deactivate Valet Mode";
			continue;
			str = "GPRS Utilization Request";
			continue;
			str = "SMS Relay";
			continue;
			str = "USSD Command";
			continue;
			str = "AT Command";
		}
	}

	public static class CarLayoutDownloader extends
	AsyncTask<String, Integer, Boolean> {
		private ProgressDialog mProgressDialog;

		public CarLayoutDownloader(ProgressDialog paramProgressDialog) {
			this.mProgressDialog = paramProgressDialog;
		}

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

		protected void onCancelled() {
			this.mProgressDialog.dismiss();
		}

		protected void onPostExecute(Boolean paramBoolean) {
			this.mProgressDialog.dismiss();
		}

		protected void onPreExecute() {
			this.mProgressDialog.setIndeterminate(false);
			if (!this.mProgressDialog.isShowing())
				this.mProgressDialog.show();
		}

		protected void onProgressUpdate(Integer[] paramArrayOfInteger) {
			this.mProgressDialog.setProgress(paramArrayOfInteger[0].intValue());
		}
	}
}