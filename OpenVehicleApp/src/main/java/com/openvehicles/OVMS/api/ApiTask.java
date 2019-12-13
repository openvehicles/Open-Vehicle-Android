package com.openvehicles.OVMS.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Cipher;
import javax.crypto.Mac;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.openvehicles.OVMS.entities.CarData;

public class ApiTask extends AsyncTask<Void, Object, Void> {
	private static final String TAG = "ApiTask";

	private Context mContext;
	private final CarData mCarData;
	private final OnUpdateStatusListener mListener;

	private Socket mSocket;
	private Cipher mTxCipher, mRxCipher, mPmCipher;
	private byte[] mPmDigestBuf;
	private PrintWriter mOutputstream;
	private BufferedReader mInputstream;
	private boolean isLoggedIn = false;
	private final Random sRnd = new Random();
	private Timer pingTimer;


	/**
	 * Constructor.
	 *
	 * @param pContext -- ApplicationContext
	 * @param pCarData -- car model to use (= mCarData in ApiService)
	 * @param pListener -- message listener (= ApiService)
	 */
	public ApiTask(Context pContext, CarData pCarData, OnUpdateStatusListener pListener) {
		mContext = pContext;
		mCarData = pCarData;
		mListener = pListener;
		Log.v(TAG, "Create TCPTask");
	}


	/**
	 * Status check for ApiService
	 * @return true if connection to server is currently established
	 */
	public boolean isLoggedIn() {
		return (getStatus() == Status.RUNNING) && isLoggedIn && !isCancelled();
	}


	/**
	 * Progress Update messaging (communication with ApiService)
	 */

	private enum MsgType {
		msgUpdate,
		msgError,
		msgCommand,
		msgLoginBegin,
		msgLoginComplete
	}

	public interface OnUpdateStatusListener {
		public void onUpdateStatus(char msgCode, String msgText);
		public void onServerSocketError(Throwable e);
		public void onResultCommand(String pCmd);
		public void onLoginBegin();
		public void onLoginComplete();
	}

	@Override
	protected void onProgressUpdate(Object... pParam) {
		if (mListener == null) return;
		
		MsgType state = (MsgType) pParam[0];
		switch (state) {
			case msgUpdate:
				String msgData = (pParam.length >= 3)
						? (String) pParam[2] : "";
				mListener.onUpdateStatus((char) pParam[1], msgData);
				break;
			case msgLoginBegin:
				mListener.onLoginBegin();
				break;
			case msgLoginComplete:
				mListener.onLoginComplete();
				break;
			case msgError:
				mListener.onServerSocketError((Throwable) pParam[1]);
				break;
			case msgCommand:
				mListener.onResultCommand((String) pParam[1]);
				break;
		}
	}


	/**
	 * ApiTask main entry.
	 *
	 * @param params -- unused
	 * @return null
	 */
	@Override
	protected Void doInBackground(Void... params) {
		String rx, msg, line;

		// Schedule server ping message every...
		final int pingPeriod = 5; // ...minutes:
		pingTimer = new Timer();
		pingTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				ping();
			}
		}, pingPeriod * 60 * 1000, pingPeriod * 60 * 1000);

		// Main Task loop:
		while (!isCancelled()) {

			try {

				// (re-)open socket connection
				if (!connInit()) {
					// non recoverable error, terminate ApiTask:
					break;
				}

				// Enter main RX loop:
				while (!isCancelled() && mSocket != null && mSocket.isConnected()) {

					// Read & decrypt message:
					line = mInputstream.readLine();
					if (line == null)
						throw new IOException("Connection lost");

					rx = line.trim();
					msg = new String(mRxCipher.update(Base64.decode(rx, 0))).trim();
					Log.d(TAG, String.format("RX: %s (%s)", msg, rx));

					// Process message:
					if (msg.substring(0, 5).equals("MP-0 ")) {
						// is valid message (for protocol version 0):
						handleMessage(msg.substring(5));
					} else {
						Log.w(TAG, "Unknown protection scheme");
						// sleep for 100 ms to prevent DoS by invalid data
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// ignore
						}
					}
				}

			} catch (IOException e) {
				// Reader closed or I/O error
				publishProgress(MsgType.msgError, e);
			}

			if (isCancelled()) {
				break;
			}

			//
			// Connection lost:
			//

			Log.d(TAG, "Lost connection");

			synchronized (ApiTask.this) {

				isLoggedIn = false;

				// Cleanup Socket:
				if (mSocket != null && mSocket.isConnected()) {
					try {
						mSocket.close();
					} catch (Exception ex) {
						// ignore
					}
				}
				mSocket = null;
			}

			// Wait 3 seconds before reconnect:
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// ignore
			}

		} // while (!isCancelled())

		// Shut down ApiTask:
		Log.d(TAG, "Terminating AsyncTask");

		// Terminate ping Thread:
		pingTimer.cancel();
		pingTimer = null;

		return null;
	}


	/**
	 * Send connection keepalive ping to server.
	 */
	public void ping() {
		if (isLoggedIn) {
			Log.d(TAG, "Sending ping");
			sendMessage("MP-0 A");
		}
	}


	/**
	 * Send protocol message to server. See OVMS protocol PDF for details.
	 * Asynchronous reply will be handled by handleMessage().
	 * Example: request feature list: sendCommand("MP-0 C1")
	 *
	 * @param message -- complete protocol message with header
	 * @return true if command has been sent, false on error
	 */
	public synchronized boolean sendMessage(String message) {
		Log.i(TAG, "TX: " + message);
		if (!isLoggedIn) {
			Log.w(TAG, "Server not ready. TX aborted.");
			return false;
		}

		try {
			mOutputstream.println(Base64.encodeToString(mTxCipher.update(message.getBytes()), Base64.NO_WRAP));
		} catch (Exception e) {
			publishProgress(MsgType.msgError, e);
			return false;
		}

		return true;
	}


	/**
	 * Try to connect to the OVMS server using the current car credentials.
	 * Sets isLoggedIn accordingly.
	 *
	 * Publishes progress messages:
	 * 	- msgLoginBegin
	 * 	- msgLoginComplete
	 * 	- msgError
	 *
	 * @return
	 *  false: a non recoverable error is detected (like wrong host or login)
	 * 	true: connection established (isLoggedIn==true) or caller shall retry (isLoggedIn==false)
	 */
	private synchronized boolean connInit() {

		Log.d(TAG, "connInit() requested");

		isLoggedIn = false;

		// Check if some network is available:
		if (!isOnline()) {
			Log.i(TAG, "No network connection available");
			return true; // temporary error, retry
		}

		publishProgress(MsgType.msgLoginBegin);

		// Get car login credentials:
		String vehicleID = mCarData.sel_vehicleid;
		String shared_secret = mCarData.sel_server_password;

		// Generate session client token
		String b64tabString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		char[] b64tab = b64tabString.toCharArray();
		String client_tokenString = "";
		for (int cnt = 0; cnt < 22; cnt++) {
			client_tokenString += b64tab[sRnd.nextInt(b64tab.length - 1)];
		}
		byte[] client_token = client_tokenString.getBytes();

		try {
			// Open TCP connection to server port 6867 (OVMS main port):
			mSocket = new Socket(mCarData.sel_server, 6867);
			mOutputstream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())), true);
			mInputstream = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

			// Encrypt password:
			Mac client_hmac = Mac.getInstance("HmacMD5");
			javax.crypto.spec.SecretKeySpec sk = new javax.crypto.spec.SecretKeySpec(
					shared_secret.getBytes(), "HmacMD5");
			client_hmac.init(sk);
			byte[] hashedBytes = client_hmac.doFinal(client_token);
			String client_digest = Base64.encodeToString(hashedBytes, Base64.NO_WRAP);

			// Login to server:
			Log.d(TAG, String.format("TX: MP-A 0 %s %s %s", client_tokenString, client_digest, vehicleID));
			mOutputstream.println(String.format("MP-A 0 %s %s %s", client_tokenString, client_digest, vehicleID));

			// Get server welcome message:
			String[] serverWelcomeMsg = null;
			try {
				serverWelcomeMsg = mInputstream.readLine().trim().split("[ ]+");
			} catch (Exception e) {
				Log.e(TAG, "ERROR response server welcome message", e);
				publishProgress(MsgType.msgError, e);
				return false; // non recoverable error: vehicleID or password wrong
			}
			Log.d(TAG, String.format("RX: %s %s %s %s", serverWelcomeMsg[0], serverWelcomeMsg[1],
					serverWelcomeMsg[2], serverWelcomeMsg[3]));

			// Check hash:
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
				publishProgress(MsgType.msgError, new Exception("Server authentication failed"));
				return false; // non recoverable error
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

			// Connection established:
			Log.i(TAG, String.format("Connected to %s. Ciphers initialized. Listening...", mCarData.sel_server));
			isLoggedIn = true;
			publishProgress(MsgType.msgLoginComplete);

			return true;

		} catch (UnknownHostException e) {
			e.printStackTrace();
			publishProgress(MsgType.msgError, e);
		} catch (IOException e) {
			e.printStackTrace();
			publishProgress(MsgType.msgError, e);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false; // non recoverable error
	}


	/**
	 * Process message received from server.
	 *  - update car model
	 *  - publish update message
	 * See OVMS protocol PDF for details on message structure.
	 *
	 * @param msg -- the OVMS protocol message payload received (without header)
	 */
	private void handleMessage(String msg) {
		Log.i(TAG, "handleMessage: " + msg);
		
		char msgCode = msg.charAt(0);
		String msgData = msg.substring(1);
		
		if (msgCode == 'E') {
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

				msgCode = msg.charAt(2);
				msgData = msg.substring(3);

				byte[] decodedCmd = Base64.decode(msgData,
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
					msgData = new String(mPmCipher.update(decodedCmd));
				} catch (Exception e) {
					Log.d("ERR", e.getMessage());
					e.printStackTrace();
				}

				// notify main process of paranoid mode detection
				if (!mCarData.sel_paranoid) {
					Log.d(TAG, "Paranoid Mode Detected");
					mCarData.sel_paranoid = true;
					publishProgress(MsgType.msgUpdate, msgCode, msgData);
				}
			}
		}

		Log.v(TAG, msgCode + " MSG Received: " + msgData);

		switch (msgCode) {

			/**
			 * OVMS PROTOCOL
			 */

			case 'f': // OVMS Server version
				String[] dataParts = msgData.split(",\\s*");
				if (dataParts.length >= 1) {
					Log.v(TAG, "f MSG Validated");
					mCarData.server_firmware = dataParts[0].toString();
					publishProgress(MsgType.msgUpdate, msgCode, msgData);
				}
				break;

			case 'Z': // Number of connected cars
				try {
					mCarData.server_carsconnected = Integer.parseInt(msgData);
					publishProgress(MsgType.msgUpdate, msgCode, msgData);
				} catch(Exception e) {
					Log.w(TAG, "Z MSG Invalid");
				}
				break;

			case 'T': // TIMESTAMP of last update
				if (msgData.length() > 0) {
					mCarData.car_lastupdate_raw = Long.parseLong(msgData);
					mCarData.car_lastupdated = new Date(System.currentTimeMillis() - mCarData.car_lastupdate_raw * 1000);
					publishProgress(MsgType.msgUpdate, msgCode, msgData);
				} else
					Log.w(TAG, "T MSG Invalid");
				break;

			case 'a':
				Log.d(TAG, "Server acknowledged ping");
				break;

			case 'c':
				Log.i(TAG, "Command response received: " + msgData);
				publishProgress(MsgType.msgCommand, msgData);
				break;

			case 'P':
				Log.i(TAG, "Push notification received: " + msgData);
				publishProgress(MsgType.msgUpdate, msgCode, msgData);
				break;


			/**
			 * CAR VERSION AND CAPABILITIES
			 */

			case 'F': // CAR VIN and Firmware version
				if (mCarData.processFirmware(msgData))
					publishProgress(MsgType.msgUpdate, msgCode, msgData);
				else
					Log.w(TAG, "F MSG Invalid");
				break;

			case 'V': // CAR firmware capabilities
				if (mCarData.processCapabilities(msgData))
					publishProgress(MsgType.msgUpdate, msgCode, msgData);
				else
					Log.w(TAG, "V MSG Invalid");
				break;


			/**
			 * STANDARD CAR MODEL DATA
			 */

			case 'S': // STATUS
				if (mCarData.processStatus(msgData))
					publishProgress(MsgType.msgUpdate, msgCode, msgData);
				else
					Log.w(TAG, "S MSG Invalid");
				break;

			case 'L': // LOCATION
				if (mCarData.processLocation(msgData))
					publishProgress(MsgType.msgUpdate, msgCode, msgData);
				else
					Log.w(TAG, "L MSG Invalid");
				break;

			case 'D': // Doors / switches & environment
				if (mCarData.processEnvironment(msgData))
					publishProgress(MsgType.msgUpdate, msgCode, msgData);
				else
					Log.w(TAG, "D MSG Invalid");
				break;

			case 'W': // TPMS
				if (mCarData.processTPMS(msgData))
					publishProgress(MsgType.msgUpdate, msgCode, msgData);
				else
					Log.w(TAG, "W MSG Invalid");
				break;


			/**
			 * CUSTOM MESSAGES
			 */

			default:
				Log.i(TAG, "Unhandled message received: " + msgCode + msgData);
				// forward to listeners, maybe this is a custom message:
				publishProgress(MsgType.msgUpdate, msgCode, msgData);
				break;
		}
	}


	private String toHex(byte[] bytes) {
		BigInteger bi = new BigInteger(1, bytes);
		return String.format("%0" + (bytes.length << 1) + "X", bi);
	}


	/**
	 * Check for network availability.
	 *
	 * @return true if any network is available
	 */
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return (info != null && info.isConnectedOrConnecting());
	}


}

