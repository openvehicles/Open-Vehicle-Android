package com.openvehicles.OVMS.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.Mac;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.openvehicles.OVMS.BaseApp;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.entities.CarData.DataStale;

public class ApiTask extends AsyncTask<Void, Object, Void> {
	private static final String TAG = "ApiTask";
	private Socket mSocket;
	private Cipher mTxCipher, mRxCipher, mPmCipher;
	private byte[] mPmDigestBuf;
	private PrintWriter mOutputstream;
	private BufferedReader mInputstream;
	private boolean isLoggedIn = false;
	private final CarData mCarData;
	private final OnUpdateStatusListener mListener;
	private final Random sRnd = new Random();
	private boolean isShuttingDown = false;

	private enum MsgType {
		msgUpdate, msgError, msgCommand, msgLoginBegin, msgLoginComplete
	}
	
	public ApiTask(CarData pCarData, OnUpdateStatusListener pListener) {
		mCarData = pCarData;
		mListener = pListener;
		Log.v(TAG, "Create TCPTask");
	}
	
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	
	@Override
	protected void onProgressUpdate(Object... pParam) {
		if (mListener == null) return;
		
		MsgType state = (MsgType) pParam[0];
		switch (state) {
		case msgUpdate:
			mListener.onUpdateStatus();
			break;
		case msgLoginBegin:
			mListener.onLoginBegin();
			break;
		case msgLoginComplete:
			mListener.onLoginComplete();
			break;
		case msgError:
			mListener.onServerSocketError((Throwable)pParam[1]);
			break;
		case msgCommand:
			mListener.onResultCommand((String)pParam[1]);
			break;
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		String rx, msg;

		while (!isShuttingDown) {
			try {
				// (re-)open socket connection
				connInit();

				while (mSocket.isConnected()) {
					// read & decrypt message
					rx = mInputstream.readLine().trim();
					msg = new String(mRxCipher.update(Base64.decode(rx, 0))).trim();
					Log.d(TAG, String.format("RX: %s (%s)", msg, rx));

					if (msg.substring(0, 5).equals("MP-0 ")) {
						// is valid message (for protocol version 0):
						handleMessage(msg.substring(5));
					} else {
						Log.d(TAG, "Unknown protection scheme");
						// sleep for 100 ms to prevent DoS by invalid data
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// ignore
						}
					}
				}

				// reconnect if not shutting down:
				continue;

			} catch (SocketException e) {
				// connection lost, attempt to reconnect
				try {
					mSocket.close();
					mSocket = null;
				} catch (Exception ex) {
					// ignore
				}
				continue;

			} catch (IOException e) {
				// reader closed or I/O error
				e.printStackTrace();
				publishProgress(MsgType.msgError, e);
				break;

			} catch (Exception e) {
				// other error
				e.printStackTrace();
				publishProgress(MsgType.msgError, e);
				break;

			}
		}

		Log.d(TAG, "Terminating AsyncTask");
		return null;
	}
	
	public void ping() {
		String msg = "TX: MP-0 A";
		mOutputstream.println(Base64.encodeToString(mTxCipher.update(msg.getBytes()), Base64.NO_WRAP));
		Log.d(TAG, msg);
	}

	public void connClose() {
		try {
			Log.d(TAG, "connClose() requested");
			isShuttingDown = true;
			if ((mSocket != null) && mSocket.isConnected()) {
				mSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean sendCommand(String command) {
		Log.i(TAG, "TX: " + command);
		if (!isLoggedIn) {
			Log.w(TAG, "Server not ready. TX aborted.");
			return false;
		}

		try {
			mOutputstream.println(Base64.encodeToString(mTxCipher.update(command.getBytes()), Base64.NO_WRAP));
		} catch (Exception e) {
			publishProgress(MsgType.msgError, e);
		}
		return true;
	}
	
	private void connInit() {
		isLoggedIn = false;
		publishProgress(MsgType.msgLoginBegin);
		
		Log.d(TAG, "connInit() requested");
		isShuttingDown = false;

		String shared_secret = mCarData.sel_server_password;
		String vehicleID = mCarData.sel_vehicleid;
		String b64tabString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		char[] b64tab = b64tabString.toCharArray();

		// generate session client token
		String client_tokenString = "";
		for (int cnt = 0; cnt < 22; cnt++) {
			client_tokenString += b64tab[sRnd.nextInt(b64tab.length - 1)];
		}

		byte[] client_token = client_tokenString.getBytes();
		try {
			Mac client_hmac = Mac.getInstance("HmacMD5");
			javax.crypto.spec.SecretKeySpec sk = new javax.crypto.spec.SecretKeySpec(
					shared_secret.getBytes(), "HmacMD5");
			client_hmac.init(sk);
			byte[] hashedBytes = client_hmac.doFinal(client_token);
			String client_digest = Base64.encodeToString(hashedBytes, Base64.NO_WRAP);

			mSocket = new Socket(mCarData.sel_server, 6867);

			mOutputstream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())), true);
			Log.d(TAG, String.format("TX: MP-A 0 %s %s %s", client_tokenString, client_digest, vehicleID));

			mOutputstream.println(String.format("MP-A 0 %s %s %s", client_tokenString, client_digest, vehicleID));

			mInputstream = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

			// get server welcome message
			String[] serverWelcomeMsg = null;
			try {
				serverWelcomeMsg = mInputstream.readLine().trim().split("[ ]+");
			} catch (Exception e) {
				Log.e(TAG, "ERROR response server welcome message", e);
				publishProgress(MsgType.msgError, e);
				return;
			}
			Log.d(TAG, String.format("RX: %s %s %s %s", serverWelcomeMsg[0], serverWelcomeMsg[1],
					serverWelcomeMsg[2], serverWelcomeMsg[3]));

			String server_tokenString = serverWelcomeMsg[2];
			byte[] server_token = server_tokenString.getBytes();
			byte[] server_digest = Base64.decode(serverWelcomeMsg[3], 0);

			if (!Arrays.equals(client_hmac.doFinal(server_token), server_digest)) {
				// server hash failed
				Log.d(TAG, String.format(
						"Server authentication failed. Expected %s Got %s",
						Base64.encodeToString(client_hmac
								.doFinal(serverWelcomeMsg[2].getBytes()),
								Base64.NO_WRAP), serverWelcomeMsg[3]));
			} else {
				Log.d(TAG, "Server authentication OK.");
			}

			// generate client_key
			String server_client_token = server_tokenString + client_tokenString;
			byte[] client_key = client_hmac.doFinal(server_client_token.getBytes());

			Log.d(TAG, String.format("Client version of the shared key is %s - (%s) %s",
					server_client_token, toHex(client_key).toLowerCase(),
					Base64.encodeToString(client_key, Base64.NO_WRAP)));

			// generate ciphers
			mRxCipher = Cipher.getInstance("RC4");
			mRxCipher.init(Cipher.DECRYPT_MODE, new javax.crypto.spec.SecretKeySpec(client_key, "RC4"));

			mTxCipher = Cipher.getInstance("RC4");
			mTxCipher.init(Cipher.ENCRYPT_MODE, new javax.crypto.spec.SecretKeySpec(client_key, "RC4"));

			// prime ciphers
			String primeData = "";
			for (int cnt = 0; cnt < 1024; cnt++) primeData += "0";

			mRxCipher.update(primeData.getBytes());
			mTxCipher.update(primeData.getBytes());

			Log.i(TAG, String.format("Connected to %s. Ciphers initialized. Listening...", mCarData.sel_server));

			//OLD loginComplete();
			isLoggedIn = true;
			publishProgress(MsgType.msgLoginComplete);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			publishProgress(MsgType.msgError, e);
		} catch (IOException e) {
			e.printStackTrace();
			publishProgress(MsgType.msgError, e);
		} catch (NullPointerException e) {
			// notifyServerSocketError(e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void handleMessage(String msg) {
		Context mContext = BaseApp.getApp();

		Log.i(TAG, "handleMessage: " + msg);
		
		char code = msg.charAt(0);
		String cmd = msg.substring(1);

		if (code == 'E') {
			// We have a paranoid mode message
			char innercode = msg.charAt(1);
			if (innercode == 'T') {
				// Set the paranoid token
				Log.v(TAG, "ET MSG Received: " + msg);

				try {
					String pmToken = msg.substring(2);
					Mac pm_hmac = Mac.getInstance("HmacMD5");
					javax.crypto.spec.SecretKeySpec sk = new javax.crypto.spec.SecretKeySpec(
							mCarData.sel_server_password.getBytes(), "HmacMD5");
					pm_hmac.init(sk);
					mPmDigestBuf = pm_hmac.doFinal(pmToken.getBytes());
					Log.d(TAG, "Paranoid Mode Token Accepted. Entering Privacy Mode.");
				} catch (Exception e) {
					Log.e("ERR", e.getMessage());
					e.printStackTrace();
				}
			} else if (innercode == 'M') {
				// Decrypt the paranoid message
				Log.v(TAG, "EM MSG Received: " + msg);

				code = msg.charAt(2);
				cmd = msg.substring(3);

				byte[] decodedCmd = Base64.decode(cmd,
						Base64.NO_WRAP);

				try {
					mPmCipher = Cipher.getInstance("RC4");
					mPmCipher.init(Cipher.DECRYPT_MODE,
							new javax.crypto.spec.SecretKeySpec(mPmDigestBuf,
									"RC4"));

					// prime ciphers
					String primeData = "";
					for (int cnt = 0; cnt < 1024; cnt++)
						primeData += "0";

					mPmCipher.update(primeData.getBytes());
					cmd = new String(mPmCipher.update(decodedCmd));
				} catch (Exception e) {
					Log.d("ERR", e.getMessage());
					e.printStackTrace();
				}

				// notify main process of paranoid mode detection
				if (!mCarData.sel_paranoid) {
					Log.d(TAG, "Paranoid Mode Detected");
					mCarData.sel_paranoid = true;
					publishProgress(MsgType.msgUpdate);
				}
			}
		}

		Log.v(TAG, code + " MSG Received: " + cmd);
		switch (code) {
		case 'Z': // Number of connected cars
		{
			mCarData.server_carsconnected = Integer.parseInt(cmd);
			
			publishProgress(MsgType.msgUpdate);
			break;
		}
		case 'S': // STATUS
		{
			String[] dataParts = cmd.split(",\\s*");
			if (dataParts.length >= 8) {
				Log.v(TAG, "S MSG Validated");
				mCarData.car_soc_raw = Integer.parseInt(dataParts[0]);
				mCarData.car_soc = String.format("%d%%",mCarData.car_soc_raw);
				mCarData.car_distance_units_raw = dataParts[1].toString();
				mCarData.car_distance_units = (mCarData.car_distance_units_raw.equals("M"))?"m":"km";
				mCarData.car_speed_units = (mCarData.car_distance_units_raw.equals("M"))
						?mContext.getText(R.string.mph).toString()
						:mContext.getText(R.string.kph).toString();
				mCarData.car_charge_linevoltage_raw = Integer.parseInt(dataParts[2]);
				mCarData.car_charge_linevoltage = String.format("%d%s", mCarData.car_charge_linevoltage_raw,"V");
				mCarData.car_charge_current_raw = Integer.parseInt(dataParts[3]);
				mCarData.car_charge_current = String.format("%d%s",mCarData.car_charge_current_raw,"A");
				mCarData.car_charge_voltagecurrent = String.format("%d%s %d%s",
						mCarData.car_charge_linevoltage_raw,"V",
						mCarData.car_charge_current_raw,"A");
				mCarData.car_charge_state_s_raw = dataParts[4].toString();
				mCarData.car_charge_state = mCarData.car_charge_state_s_raw;
				mCarData.car_mode_s_raw = dataParts[5].toString();
				mCarData.car_charge_mode = mCarData.car_mode_s_raw;
				mCarData.car_range_ideal_raw = Integer.parseInt(dataParts[6]);
				mCarData.car_range_ideal = String.format("%d%s",mCarData.car_range_ideal_raw,mCarData.car_distance_units);
				mCarData.car_range_estimated_raw = Integer.parseInt(dataParts[7]);
				mCarData.car_range_estimated = String.format("%d%s",mCarData.car_range_estimated_raw,mCarData.car_distance_units);
				mCarData.stale_status = DataStale.Good;
			}
			if (dataParts.length >= 15) {
				mCarData.car_charge_currentlimit_raw = Integer.parseInt(dataParts[8]);
				mCarData.car_charge_currentlimit = String.format("%d%s",mCarData.car_charge_currentlimit_raw,"A");
				mCarData.car_charge_duration_raw = Integer.parseInt(dataParts[9]);
				mCarData.car_charge_b4byte_raw = Integer.parseInt(dataParts[10]);
				mCarData.car_charge_kwhconsumed = Integer.parseInt(dataParts[11]);
				mCarData.car_charge_substate_i_raw = Integer.parseInt(dataParts[12]);
				mCarData.car_charge_state_i_raw = Integer.parseInt(dataParts[13]);
				mCarData.car_charge_mode_i_raw = Integer.parseInt(dataParts[14]);
			}				
			if (dataParts.length >= 18) {
				mCarData.car_charge_timermode_raw = Integer.parseInt(dataParts[15]);
				mCarData.car_charge_timer = (mCarData.car_charge_timermode_raw > 0);
				mCarData.car_charge_timerstart_raw = Integer.parseInt(dataParts[16]);
				mCarData.car_charge_time = ""; // TODO: Implement later
				mCarData.car_stale_chargetimer_raw = Integer.parseInt(dataParts[17]);
				if (mCarData.car_stale_chargetimer_raw < 0)
					mCarData.stale_chargetimer = DataStale.NoValue;
				else if (mCarData.car_stale_chargetimer_raw == 0)
					mCarData.stale_chargetimer = DataStale.Stale;
				else
					mCarData.stale_chargetimer = DataStale.Good;

			}
			if (dataParts.length >= 19) {
				mCarData.car_CAC = Double.parseDouble(dataParts[18]);
			}
			if (dataParts.length >= 27) {
				mCarData.car_chargefull_minsremaining = Integer.parseInt(dataParts[19]);
				mCarData.car_chargelimit_minsremaining = Integer.parseInt(dataParts[20]);
				mCarData.car_chargelimit_rangelimit_raw = Integer.parseInt(dataParts[21]);
				mCarData.car_chargelimit_rangelimit = String.format("%d%s",
						mCarData.car_chargelimit_rangelimit_raw, mCarData.car_distance_units);
				mCarData.car_chargelimit_soclimit = Integer.parseInt(dataParts[22]);
				mCarData.car_coolingdown = Integer.parseInt(dataParts[23]);
				mCarData.car_cooldown_tbattery = Integer.parseInt(dataParts[24]);
				mCarData.car_cooldown_timelimit = Integer.parseInt(dataParts[25]);
				mCarData.car_chargeestimate = Integer.parseInt(dataParts[26]);
			}
			if (dataParts.length >= 30) {
				mCarData.car_chargelimit_minsremaining_range = Integer.parseInt(dataParts[27]);
				mCarData.car_chargelimit_minsremaining_soc = Integer.parseInt(dataParts[28]);
				mCarData.car_max_idealrange_raw = Integer.parseInt(dataParts[29]);
				mCarData.car_max_idealrange = String.format("%d%s",
						mCarData.car_max_idealrange_raw, mCarData.car_distance_units);
			}

			Log.v(TAG, "Notify Vehicle Status Update: " + mCarData.sel_vehicleid);
			
			publishProgress(MsgType.msgUpdate);
			break;
		}
		case 'T': // TIME
		{
			if (cmd.length() > 0) {
				mCarData.car_lastupdate_raw = Long.parseLong(cmd);
				mCarData.car_lastupdated = new Date(System.currentTimeMillis() - mCarData.car_lastupdate_raw * 1000);
				publishProgress(MsgType.msgUpdate);
			} else
				Log.w(TAG, "T MSG Invalid");
			break;
		}
		case 'L': // LOCATION
		{
			String[] dataParts = cmd.split(",\\s*");
			if (dataParts.length >= 2) {
				Log.v(TAG, "L MSG Validated");
				mCarData.car_latitude = Double.parseDouble(dataParts[0]);
				mCarData.car_longitude = Double.parseDouble(dataParts[1]);
			}
			if (dataParts.length >= 6) {
				mCarData.car_direction = Integer.parseInt(dataParts[2]);
				mCarData.car_altitude = Integer.parseInt(dataParts[3]);
				mCarData.car_gpslock_raw = Integer.parseInt(dataParts[4]);
				mCarData.car_gpslock = (mCarData.car_gpslock_raw > 0);
				mCarData.car_stale_gps_raw = Integer.parseInt(dataParts[5]);
				if (mCarData.car_stale_gps_raw < 0)
					mCarData.stale_gps = DataStale.NoValue;
				else if (mCarData.car_stale_gps_raw == 0)
					mCarData.stale_gps = DataStale.Stale;
				else
					mCarData.stale_gps = DataStale.Good;
			}

			publishProgress(MsgType.msgUpdate);
			break;
		}
		case 'D': // Doors and switches
		{
			String[] dataParts = cmd.split(",\\s*");
			if (dataParts.length >= 9) {
				Log.v(TAG, "D MSG Validated");
				int dataField = Integer.parseInt(dataParts[0]);
				mCarData.car_doors1_raw = dataField;
				mCarData.car_frontleftdoor_open = ((dataField & 0x1) == 0x1);
				mCarData.car_frontrightdoor_open = ((dataField & 0x2) == 0x2);
				mCarData.car_chargeport_open = ((dataField & 0x4) == 0x4);
				mCarData.car_pilot_present = ((dataField & 0x8) == 0x8);
				mCarData.car_charging = ((dataField & 0x10) == 0x10);
				// bit 5 is always 1
				mCarData.car_handbrake_on = ((dataField & 0x40) == 0x40);
				mCarData.car_started = ((dataField & 0x80) == 0x80);

				dataField = Integer.parseInt(dataParts[1]);
				mCarData.car_doors2_raw = dataField;
				mCarData.car_bonnet_open = ((dataField & 0x40) == 0x40);
				mCarData.car_trunk_open = ((dataField & 0x80) == 0x80);
				mCarData.car_headlights_on = ((dataField & 0x20) == 0x20);
				mCarData.car_valetmode = ((dataField & 0x10) == 0x10);
				mCarData.car_locked = ((dataField & 0x08) == 0x08);

				mCarData.car_lockunlock_raw = Integer.parseInt(dataParts[2]);

				mCarData.car_temp_pem_raw = Integer.parseInt(dataParts[3]);
				mCarData.car_temp_motor_raw = Integer.parseInt(dataParts[4]);
				mCarData.car_temp_battery_raw = Integer.parseInt(dataParts[5]);
				if (mCarData.car_distance_units_raw.equals("M"))
					{
					mCarData.car_temp_pem = String.format("%.0f\u00B0F",(mCarData.car_temp_pem_raw*(9.0/5.0))+32.0);
					mCarData.car_temp_motor = String.format("%.0f\u00B0F",(mCarData.car_temp_motor_raw*(9.0/5.0))+32.0);
					mCarData.car_temp_battery = String.format("%.0f\u00B0F",(mCarData.car_temp_battery_raw*(9.0/5.0))+32.0);
					}
				else
					{
					mCarData.car_temp_pem = String.format("%d\u00B0C",mCarData.car_temp_pem_raw);
					mCarData.car_temp_motor = String.format("%d\u00B0C",mCarData.car_temp_motor_raw);
					mCarData.car_temp_battery = String.format("%d\u00B0C",mCarData.car_temp_battery_raw);
					}
				
				mCarData.car_tripmeter_raw = Integer.parseInt(dataParts[6]);
				mCarData.car_tripmeter = String.format("%.1f%s",(float)mCarData.car_tripmeter_raw/10,mCarData.car_distance_units);
				mCarData.car_odometer_raw = Integer.parseInt(dataParts[7]);
				mCarData.car_odometer = String.format("%.1f%s",(float)mCarData.car_odometer_raw/10,mCarData.car_distance_units);
				mCarData.car_speed_raw = Integer.parseInt(dataParts[8]);
				mCarData.car_speed = String.format("%d%s", mCarData.car_speed_raw,mCarData.car_speed_units);
				
				mCarData.stale_environment = DataStale.Good;

				if (dataParts.length >= 14) {
					mCarData.car_parking_timer_raw = Long.parseLong(dataParts[9]);
					mCarData.car_parked_time = new Date((new Date()).getTime() - mCarData.car_parking_timer_raw * 1000);

					mCarData.car_temp_ambient_raw = Integer.parseInt(dataParts[10]);
					if (mCarData.car_distance_units_raw.equals("M"))
						mCarData.car_temp_ambient = String.format("%.0f\u00B0F",(mCarData.car_temp_ambient_raw*(9.0/5.0))+32.0);
					else
						mCarData.car_temp_ambient = String.format("%d\u00B0C",mCarData.car_temp_ambient_raw);
					
					dataField = Integer.parseInt(dataParts[11]);
					mCarData.car_doors3_raw =  dataField;
					mCarData.car_coolingpump_on =  ((dataField & 0x02) == 0x02);

					mCarData.car_stale_car_temps_raw = Integer.parseInt(dataParts[12]);
					if (mCarData.car_stale_car_temps_raw < 0)
						mCarData.stale_car_temps = DataStale.NoValue;
					else if (mCarData.car_stale_car_temps_raw == 0)
						mCarData.stale_car_temps = DataStale.Stale;
					else
						mCarData.stale_car_temps = DataStale.Good;

					mCarData.car_stale_ambient_temp_raw = Integer.parseInt(dataParts[13]);
					if (mCarData.car_stale_ambient_temp_raw < 0)
						mCarData.stale_ambient_temp = DataStale.NoValue;
					else if (mCarData.car_stale_ambient_temp_raw == 0)
						mCarData.stale_ambient_temp = DataStale.Stale;
					else
						mCarData.stale_ambient_temp = DataStale.Good;
				}
				if (dataParts.length >= 16) {
					mCarData.car_12vline_voltage = Double.parseDouble(dataParts[14]);
					dataField = Integer.parseInt(dataParts[15]);
					mCarData.car_doors4_raw = dataField;
					mCarData.car_alarm_sounding = ((dataField & 0x02) == 0x02);
				}
				if (dataParts.length >= 18) {
					mCarData.car_12vline_ref = Double.parseDouble(dataParts[16]);
					dataField = Integer.parseInt(dataParts[17]);
					mCarData.car_doors5_raw = dataField;
					mCarData.car_charging_12v = ((dataField & 0x10) == 0x10);
				}

				publishProgress(MsgType.msgUpdate);
			}
			break;
		}
		case 'F': // VIN and Firmware
		{
			String[] dataParts = cmd.split(",\\s*");
			if (dataParts.length >= 3) {
				Log.v(TAG, "F MSG Validated");
				mCarData.car_firmware = dataParts[0].toString();
				mCarData.car_vin = dataParts[1].toString();
				mCarData.car_gps_signal_raw = Integer.parseInt(dataParts[2]);

				int car_gsmdbm = 0;
				if (mCarData.car_gps_signal_raw <= 31)
					car_gsmdbm = -113 + (mCarData.car_gps_signal_raw*2);
				mCarData.car_gsm_signal = String.format("%d%s", car_gsmdbm," dbm");
				if ((car_gsmdbm < -121)||(car_gsmdbm >= 0))
					mCarData.car_gsm_bars = 0;
				else if (car_gsmdbm < -107)
					mCarData.car_gsm_bars = 1;
				else if (car_gsmdbm < -98)
					mCarData.car_gsm_bars = 2;
				else if (car_gsmdbm < -87)
					mCarData.car_gsm_bars = 3;
				else if (car_gsmdbm < -76)
					mCarData.car_gsm_bars = 4;
				else
					mCarData.car_gsm_bars = 5;

				mCarData.stale_firmware = DataStale.Good;
			}
			if (dataParts.length >= 5) {
				mCarData.car_canwrite = (Integer.parseInt(dataParts[3])>0);
				mCarData.car_type = dataParts[4].toString();
			}
			if (dataParts.length >= 6) {
				mCarData.car_gsmlock = dataParts[5].toString();
			}

			publishProgress(MsgType.msgUpdate);
		}
		case 'f': // OVMS Server Firmware
		{
			String[] dataParts = cmd.split(",\\s*");
			if (dataParts.length >= 1) {
				Log.v(TAG, "f MSG Validated");
				mCarData.server_firmware = dataParts[0].toString();

				publishProgress(MsgType.msgUpdate);
			}
			break;
		}
		case 'W': // TPMS
		{
			String[] dataParts = cmd.split(",\\s*");
			if (dataParts.length >= 9) {
				Log.v(TAG, "W MSG Validated");
				mCarData.car_tpms_fr_p_raw = Double.parseDouble(dataParts[0]);
				mCarData.car_tpms_fr_t_raw = Double.parseDouble(dataParts[1]);
				mCarData.car_tpms_rr_p_raw = Double.parseDouble(dataParts[2]);
				mCarData.car_tpms_rr_t_raw = Double.parseDouble(dataParts[3]);
				mCarData.car_tpms_fl_p_raw = Double.parseDouble(dataParts[4]);
				mCarData.car_tpms_fl_t_raw = Double.parseDouble(dataParts[5]);
				mCarData.car_tpms_rl_p_raw = Double.parseDouble(dataParts[6]);
				mCarData.car_tpms_rl_t_raw = Double.parseDouble(dataParts[7]);
				mCarData.car_tpms_fl_p = String.format("%.1f%s",mCarData.car_tpms_fl_p_raw, "psi"); 
				mCarData.car_tpms_fr_p = String.format("%.1f%s",mCarData.car_tpms_fr_p_raw, "psi"); 
				mCarData.car_tpms_rl_p = String.format("%.1f%s",mCarData.car_tpms_rl_p_raw, "psi"); 
				mCarData.car_tpms_rr_p = String.format("%.1f%s",mCarData.car_tpms_rr_p_raw, "psi"); 
				if (mCarData.car_distance_units_raw.equals("M"))
					{
					mCarData.car_tpms_fl_t = String.format("%.0f%s",(mCarData.car_tpms_fl_t_raw*(9.0/5.0))+32.0, "\u00B0F"); 
					mCarData.car_tpms_fr_t = String.format("%.0f%s",(mCarData.car_tpms_fr_t_raw*(9.0/5.0))+32.0, "\u00B0F"); 
					mCarData.car_tpms_rl_t = String.format("%.0f%s",(mCarData.car_tpms_rl_t_raw*(9.0/5.0))+32.0, "\u00B0F"); 
					mCarData.car_tpms_rr_t = String.format("%.0f%s",(mCarData.car_tpms_rr_t_raw*(9.0/5.0))+32.0, "\u00B0F"); 
					}
				else
					{
					mCarData.car_tpms_fl_t = String.format("%.0f%s",mCarData.car_tpms_fl_t_raw, "\u00B0C"); 
					mCarData.car_tpms_fr_t = String.format("%.0f%s",mCarData.car_tpms_fr_t_raw, "\u00B0C"); 
					mCarData.car_tpms_rl_t = String.format("%.0f%s",mCarData.car_tpms_rl_t_raw, "\u00B0C"); 
					mCarData.car_tpms_rr_t = String.format("%.0f%s",mCarData.car_tpms_rr_t_raw, "\u00B0C"); 
					}
				mCarData.car_stale_tpms_raw = Integer.parseInt(dataParts[8]);
				if (mCarData.car_stale_tpms_raw < 0)
					mCarData.stale_tpms = DataStale.NoValue;
				else if (mCarData.car_stale_tpms_raw == 0)
					mCarData.stale_tpms = DataStale.Stale;
				else
					mCarData.stale_tpms = DataStale.Good;

				publishProgress(MsgType.msgUpdate);
			}
			break;
		}
		case 'a': {
			Log.v(TAG, "Server acknowleged ping");
			break;
		}
		case 'c': {
			Log.i(TAG, "c MSG Validated");
			publishProgress(MsgType.msgCommand, cmd);
			break;
		}
		
		}
	}

	private String toHex(byte[] bytes) {
		BigInteger bi = new BigInteger(1, bytes);
		return String.format("%0" + (bytes.length << 1) + "X", bi);
	}
	
	public interface OnUpdateStatusListener {
		public void onUpdateStatus();
		public void onServerSocketError(Throwable e);
		public void onResultCommand(String pCmd);
		public void onLoginBegin();
		public void onLoginComplete();
	}
}
