package com.openvehicles.OVMS.entities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.openvehicles.OVMS.BaseApp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by Michael Balzer <dexter@dexters-web.de> on 2019-12-13.
 */

public class AuxBatteryData {
	private static final transient String TAG = "AuxBatteryData";

	static public class PackStatus {
		public Date timeStamp;
		public float volt, voltRef;
		public float current;
		public float tempAmbient, tempCharger;
		public boolean isCharging12V;
		public boolean isCarAwake;

		public boolean isNewSection(PackStatus previous) {
			return ((previous != null)
					&& ((isCharging12V && !previous.isCharging12V)
					 || (isCarAwake && !previous.isCarAwake)));
		}
	}

	//
	// System environment:
	//

	private transient static final Context context = BaseApp.getApp();
	private transient static final Gson gson = new Gson();


	//
	// Storage data:
	//

	public String vehicleId;
	public ArrayList<PackStatus> packHistory;


	//
	// Methods
	//

	public AuxBatteryData() {
		vehicleId = "";
		packHistory = new ArrayList<PackStatus>(24*60);
	}


	public static AuxBatteryData loadFile(String vehicleId) {

		FileInputStream inputStream;
		String filename = "auxbatterydata-" + vehicleId + "-default.json";
		Log.v(TAG, "loading from file: " + filename);

		try {
			inputStream = context.openFileInput(filename);
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
			String json = sb.toString();
			return gson.fromJson(json, AuxBatteryData.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public boolean saveFile(String vehicleId) {

		FileOutputStream outputStream;
		String filename = "auxbatterydata-" + vehicleId + "-default.json";
		Log.v(TAG, "saving to file: " + filename);

		this.vehicleId = vehicleId;
		String json = gson.toJson(this);

		try {
			outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(json.getBytes());
			outputStream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public void processCmdResults(CmdSeries cmdSeries) {

		int recNr, recCnt;
		String recType;
		Date timeStamp;
		SimpleDateFormat serverTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		serverTime.setTimeZone(TimeZone.getTimeZone("UTC"));

		PackStatus packStatus = null;
		int doors;

		for (int i=0; i < cmdSeries.size(); i++) {

			CmdSeries.Cmd cmd = cmdSeries.get(i);

			if (cmd.commandCode != 32)
				continue;

			// init:
			if (cmd.command.equals("32,D")) {
				packHistory.clear();
			}

			for (int resNr=0; resNr < cmd.results.size(); resNr++) {

				String[] result = cmd.results.get(resNr);

				if (result[2].equals("No historical data available"))
					continue;

				try {

					recNr = Integer.parseInt(result[2]);
					recCnt = Integer.parseInt(result[3]);
					recType = result[4];
					timeStamp = serverTime.parse(result[5]);

					Log.v(TAG, "processing recType " + recType + " entryNr " + recNr + "/" + recCnt);

					if (recType.equals("D")) {
						try {
							// create record:
							packStatus = new PackStatus();
							packStatus.timeStamp = timeStamp;

							packStatus.volt = Float.parseFloat(result[21]);
							packStatus.voltRef = Float.parseFloat(result[23]);
							packStatus.current = Float.parseFloat(result[26]);
							packStatus.tempAmbient = Float.parseFloat(result[17]);
							packStatus.tempCharger = Float.parseFloat(result[25]);

							doors = Integer.parseInt(result[18]); // doors3
							packStatus.isCarAwake = ((doors & 0x01) != 0);
							doors = Integer.parseInt(result[24]); // doors5
							packStatus.isCharging12V = ((doors & 0x10) != 0);

							// add to history:
							packHistory.add(packStatus);

						} catch (Exception e) {
							// invalid record: skip
							Log.e(TAG, "skip: " + e.getMessage());
						}
					}

				} catch (Exception e) {
					// most probably parse error, skip row
					e.printStackTrace();
				}
			}

		}

		Log.v(TAG, "processCmdResults done");

	}

}
