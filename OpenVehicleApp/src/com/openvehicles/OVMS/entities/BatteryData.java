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
 * Created by balzer on 28.03.15.
 */

public class BatteryData {
	private static final transient String TAG = "BatteryData";

	/**
	 * 	Note: this is currently tailored to the Twizy battery
	 * 	which consists of only one pack. For other pack structures
	 * 	this should be extended as needed.
	 */

	static public class PackStatus {
		public Date timeStamp;
		public int voltAlert, tempAlert;
		public float soc, socMin, socMax;
		public float volt, voltMin, voltMax, voltDevMax;
		public float temp, tempMin, tempMax, tempDevMax;
		public long maxDrivePwr, maxRecupPwr;
		public ArrayList<CellStatus> cells;

		public boolean isNewSection(PackStatus previous) {
			return ((previous != null) &&
					(voltMin > previous.voltMin
							|| tempMin > previous.tempMin
							|| socMax < previous.socMax));
		}
	}

	static public class CellStatus {
		public int voltAlert, tempAlert;
		public float volt, voltMin, voltMax, voltDevMax;
		public float temp, tempMin, tempMax, tempDevMax;

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

	public BatteryData() {
		vehicleId = "";
		packHistory = new ArrayList<PackStatus>(24*60);
	}


	public static BatteryData loadFile(String vehicleId) {

		FileInputStream inputStream;
		String filename = "batterydata-" + vehicleId + "-default.json";
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
			return gson.fromJson(json, BatteryData.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public boolean saveFile(String vehicleId) {

		FileOutputStream outputStream;
		String filename = "batterydata-" + vehicleId + "-default.json";
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
		ArrayList<CellStatus> cells = null;
		CellStatus cellStatus;
		int nrCell, nrPack = 0;

		for (int i=0; i < cmdSeries.size(); i++) {

			CmdSeries.Cmd cmd = cmdSeries.get(i);

			if (cmd.commandCode != 32)
				continue;

			// init:
			if (cmd.command.equals("32,RT-PWR-BattPack")) {
				packHistory.clear();
			} else if (cmd.command.equals("32,RT-PWR-BattCell")) {
				// check BattPack result:
				if (packHistory.size() == 0)
					continue;

				// "BattCell" only transmits changed cells, so create complete group storage:
				cells = new ArrayList<CellStatus>(14);
				for (int j=0; j < 14; j++) {
					cellStatus = new CellStatus();
					cells.add(cellStatus);
				}

				// get first pack status:
				nrPack = 0;
				packStatus = packHistory.get(0);
			}


			for (int resNr=0; resNr < cmd.results.size(); resNr++) {

				String result[] = cmd.results.get(resNr);

				if (result[2].equals("No historical data available"))
					continue;

				try {

					recNr = Integer.parseInt(result[2]);
					recCnt = Integer.parseInt(result[3]);
					recType = result[4];
					timeStamp = serverTime.parse(result[5]);

					Log.v(TAG, "processing recType " + recType + " entryNr " + recNr + "/" + recCnt);

					if (recType.equals("RT-PWR-BattPack")) {
						try {
							// create record:
							packStatus = new PackStatus();
							packStatus.timeStamp = timeStamp;
							packStatus.voltAlert = Integer.parseInt(result[9]);
							packStatus.tempAlert = Integer.parseInt(result[10]);
							packStatus.soc = (float) Integer.parseInt(result[11]) / 100;
							packStatus.socMin = (float) Integer.parseInt(result[12]) / 100;
							packStatus.socMax = (float) Integer.parseInt(result[13]) / 100;
							packStatus.volt = (float) Integer.parseInt(result[14]) / 100;
							packStatus.voltMin = (float) Integer.parseInt(result[16]) / 100;
							packStatus.voltMax = (float) Integer.parseInt(result[18]) / 100;
							packStatus.temp = Integer.parseInt(result[20]);
							packStatus.tempMin = Integer.parseInt(result[21]);
							packStatus.tempMax = Integer.parseInt(result[22]);
							packStatus.voltDevMax = (float) Integer.parseInt(result[23]) / 100;
							packStatus.tempDevMax = Integer.parseInt(result[24]);
							packStatus.maxDrivePwr = Integer.parseInt(result[25]);
							packStatus.maxRecupPwr = Integer.parseInt(result[26]);

							// store record:
							packHistory.add(packStatus);

						} catch (Exception e) {
							// invalid record: skip
							Log.e(TAG, "BattPack skip: " + e.getMessage());
						}
					} else if (recType.equals("RT-PWR-BattCell")) {
						try {

							nrCell = Integer.parseInt(result[6]);

							// Pack record(s) complete?
							// (while handles Pack records without Cell records)
							while ((timeStamp.getTime() - packStatus.timeStamp.getTime()) > 3000) {
								// set pack cells:
								packStatus.cells = new ArrayList<CellStatus>(cells);

								// get next pack:
								packStatus = packHistory.get(++nrPack);
								if (packStatus.timeStamp.compareTo(timeStamp) != 0) {
									Log.w(TAG, "timeStamp differ: pack=" + packStatus.timeStamp
											+ " / cell=" + timeStamp);
								}
							}

							// create new record:
							cellStatus = new CellStatus();
							cellStatus.voltAlert = Integer.parseInt(result[8]);
							cellStatus.tempAlert = Integer.parseInt(result[9]);
							cellStatus.volt = (float) Integer.parseInt(result[10]) / 1000;
							cellStatus.voltMin = (float) Integer.parseInt(result[11]) / 1000;
							cellStatus.voltMax = (float) Integer.parseInt(result[12]) / 1000;
							cellStatus.voltDevMax = (float) Integer.parseInt(result[13]) / 1000;
							cellStatus.temp = Integer.parseInt(result[14]);
							cellStatus.tempMin = Integer.parseInt(result[15]);
							cellStatus.tempMax = Integer.parseInt(result[16]);
							cellStatus.tempDevMax = Integer.parseInt(result[17]);

							// store record:
							cells.set(nrCell-1, cellStatus);

						} catch (Exception e) {
							// invalid record: skip
							Log.e(TAG, "BattCell skip: " + e.getMessage());
						}
					}

				} catch (Exception e) {
					// most probably parse error, skip row
					e.printStackTrace();
				}
			}

			// no more results: finish
			if (cmd.command.equals("32,RT-PWR-BattCell")) {
				try {
					// set last cell collection into remaining pack records:
					while (nrPack < packHistory.size()) {
						packStatus = packHistory.get(nrPack++);
						packStatus.cells = new ArrayList<CellStatus>(cells);
					}
				} catch (Exception e) {
					Log.e(TAG, "BattCell finish error: " + e.getMessage());
				}
			}

		}

		Log.v(TAG, "processCmdResults done");

	}

}
