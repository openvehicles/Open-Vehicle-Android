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
 * Created by balzer on 06.04.15.
 */
public class GPSLogData {
	private static final transient String TAG = "GPSLogData";


	static public class Entry {

		public Date timeStamp;

		public float odometerMi;
		public double latitude, longitude;
		public float altitude, direction, speed;
		public int gspFix, gpsStaleCnt, gsmSignal;

		public int currentPower, powerUsedWh, powerRecdWh;
		public int powerDistance, minPower, maxPower;

		public int carStatus;
				// Twizy status flags:
				//  bit 0 = 0x01: 1 = Footbrake
				//  bit 1 = 0x02: 1 = Forward mode "D"
				//  bit 2 = 0x04: 1 = Reverse mode "R"
				//  bit 3 = 0x08: 1 = "GO" = Motor ON (Ignition)
				//  bit 4 = 0x10: 1 = Car awake (key turned)
				//  bit 5 = 0x20: 1 = Charging
				//  bit 6 = 0x40: 1 = Switch-ON/-OFF phase / 0 = normal operation
				//  bit 7 = 0x80: 1 = CAN-Bus online (test flag to detect offline)

		// V3.6.0: add BMS power limits [W] and autopower levels [%]
		public int bmsDriveLimit, bmsRecupLimit;
		public float autoDriveLevel, autoRecupLevel;

		// V3.6.0: add current min/max
		public float minCurrent, maxCurrent;


		/**
		 * Get operative status
		 *
		 * @return -- -1=charging / 0=off / 1=on
		 */
		public int getOpStatus() {
			if ((carStatus & 0x60) == 0x20)
				return -1;
			else if ((carStatus & 0x50) == 0x10)
				return 1;
			else
				return 0;
		}

		/**
		 * Get key status
		 *
		 * @return -- 0x10=on / 0x00=off
		 */
		public int getKeyStatus() {
			return carStatus & 0x10;
		}

		/**
		 * Check if this is a new data point to be displayed (= X axis entry):
		 *  - time delta >= 60 seconds
		 *
		 * @param ref -- last data point displayed
		 * @return
		 */
		public boolean isNewTimePoint(Entry ref) {
			return ((ref != null)
					&& ((timeStamp.getTime() - ref.timeStamp.getTime()) / 1000f >= 60)
					&& ((getOpStatus() != 0) || (ref.getOpStatus() != 0))
					);
		}

		/**
		 * Check if this data point is the beginning of a new section (drive/charge).
		 *
		 * @param ref
		 * @return
		 */
		public boolean isSectionStart(Entry ref) {
			return ((ref != null)
					&& (getKeyStatus() > ref.getKeyStatus()
							|| powerDistance < ref.powerDistance
							|| powerUsedWh < ref.powerUsedWh
							|| powerRecdWh < ref.powerRecdWh)
					);
		}

		public float getTimeDiff(Entry ref) {
			return (timeStamp.getTime() - ref.timeStamp.getTime()) / 1000f / 60f / 60f;
		}

		public float getOdometer(String unit) {
			return unit.equals("M") ? odometerMi : (odometerMi * 1.609344f);
		}

		public float getOdoDiff(Entry ref, String unit) {
			float odoDiff = (odometerMi - ref.odometerMi);
			return unit.equals("M") ? odoDiff : (odoDiff * 1.609344f);
		}

		public float getSpeed(Entry ref, String unit) {
			float calcSpeed = (odometerMi - ref.odometerMi) / getTimeDiff(ref);
			return unit.equals("M") ? calcSpeed : (calcSpeed * 1.609344f);
		}

		public int getSegUsedWh(Entry ref) {
			if (ref.powerUsedWh <= powerUsedWh)
				return powerUsedWh - ref.powerUsedWh;
			else
				return powerUsedWh;
		}

		public int getSegRecdWh(Entry ref) {
			if (ref.powerRecdWh <= powerRecdWh)
				return -(powerRecdWh - ref.powerRecdWh);
			else
				return -powerRecdWh;
		}

		public float getSegAvgPwr(Entry ref) {
			return (getSegUsedWh(ref) + getSegRecdWh(ref)) / getTimeDiff(ref);
		}

		public float getSegAvgEnergy(Entry ref, String unit) {
			float odoDiff = getOdoDiff(ref, unit);
			return (odoDiff > 0.050f)
					? ((getSegUsedWh(ref) + getSegRecdWh(ref)) / odoDiff)
					: 0f;
		}

		// minPower & maxPower are per GPS log entry instead of cumulative, thus need to be
		//  scanned for a segment spanning multiple entries:

		public int getMinPower(ArrayList<Entry> entries, Entry ref) {
			int min = 32767;
			Entry entry;
			for (int i = entries.indexOf(ref); i <= entries.indexOf(this); i++) {
				entry = entries.get(i);
				if (entry.minPower < min)
					min = entry.minPower;
			}
			return min;
		}

		public int getMaxPower(ArrayList<Entry> entries, Entry ref) {
			int max = -32768;
			Entry entry;
			for (int i = entries.indexOf(ref); i <= entries.indexOf(this); i++) {
				entry = entries.get(i);
				if (entry.maxPower > max)
					max = entry.maxPower;
			}
			return max;
		}

		// bmsDriveLimit & bmsRecupLimit are per GPS log entry instead of cumulative, thus need to be
		//  scanned for a segment spanning multiple entries:

		public int getBmsRecupLimit(ArrayList<Entry> entries, Entry ref) {
			int min = 32767;
			Entry entry;
			for (int i = entries.indexOf(ref); i <= entries.indexOf(this); i++) {
				entry = entries.get(i);
				if (entry.bmsRecupLimit < min)
					min = entry.bmsRecupLimit;
			}
			return min;
		}

		public int getBmsDriveLimit(ArrayList<Entry> entries, Entry ref) {
			int max = -32768;
			Entry entry;
			for (int i = entries.indexOf(ref); i <= entries.indexOf(this); i++) {
				entry = entries.get(i);
				if (entry.bmsDriveLimit > max)
					max = entry.bmsDriveLimit;
			}
			return max;
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

	public ArrayList<Entry> entries;


	//
	// Methods
	//

	public GPSLogData() {
		vehicleId = "";
		entries = new ArrayList<Entry>(24*60);
	}


	public static GPSLogData loadFile(String vehicleId) {

		FileInputStream inputStream;
		String filename = "gpslog-" + vehicleId + "-default.json";
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
			return gson.fromJson(json, GPSLogData.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public boolean saveFile(String vehicleId) {

		FileOutputStream outputStream;
		String filename = "gpslog-" + vehicleId + "-default.json";
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

		Entry entry;


		for (int i=0; i < cmdSeries.size(); i++) {

			CmdSeries.Cmd cmd = cmdSeries.get(i);
			
			if (cmd.commandCode != 32)
				continue;

			// init:
			if (cmd.command.equals("32,RT-GPS-Log")) {
				entries.clear();
			}


			for (int resNr=0; resNr < cmd.results.size(); resNr++) {

				String result[] = cmd.results.get(resNr);

				if (result.length > 2 && result[2].equals("No historical data available"))
					continue;

				try {

					recNr = Integer.parseInt(result[2]);
					recCnt = Integer.parseInt(result[3]);
					recType = result[4];
					timeStamp = serverTime.parse(result[5]);

					Log.v(TAG, "processing recType " + recType + " entryNr " + recNr + "/" + recCnt);

					if (recType.equals("RT-GPS-Log")) {
						try {
							// create record:
							entry = new Entry();
							entry.timeStamp = timeStamp;
							int j = 6;
							entry.odometerMi = Integer.parseInt(result[j++]) / 10f;
							entry.latitude = Double.parseDouble(result[j++]);
							entry.longitude = Double.parseDouble(result[j++]);
							entry.altitude = Float.parseFloat(result[j++]);
							entry.direction = Float.parseFloat(result[j++]);
							entry.speed = Float.parseFloat(result[j++]);
							entry.gspFix = Integer.parseInt(result[j++]);
							entry.gpsStaleCnt = Integer.parseInt(result[j++]);
							entry.gsmSignal = Integer.parseInt(result[j++]);
							entry.currentPower = Integer.parseInt(result[j++]);
							entry.powerUsedWh = Integer.parseInt(result[j++]);
							entry.powerRecdWh = Integer.parseInt(result[j++]);
							entry.powerDistance = Integer.parseInt(result[j++]);
							entry.minPower = Integer.parseInt(result[j++]);
							entry.maxPower = Integer.parseInt(result[j++]);
							entry.carStatus = Integer.parseInt(result[j++], 16);

							// V3.6 extensions:
							if (result.length > j) {
								entry.bmsDriveLimit = Integer.parseInt(result[j++]);
								entry.bmsRecupLimit = Integer.parseInt(result[j++]);
								entry.autoDriveLevel = Float.parseFloat(result[j++]);
								entry.autoRecupLevel = Float.parseFloat(result[j++]);
							}
							if (result.length > j) {
								entry.minCurrent = Float.parseFloat(result[j++]);
								entry.maxCurrent = Float.parseFloat(result[j++]);
							}

							// store record:
							entries.add(entry);

						} catch (Exception e) {
							// invalid record: skip
							Log.e(TAG, "GPS-Log skip: " + e.getMessage());
						}
					}

				} catch (Exception e) {
					// most probably parse error, skip row
					e.printStackTrace();
				}
			}

			// no more results: finish
			if (cmd.command.equals("32,RT-GPS-Log")) {
				try {
					// ...?
				} catch (Exception e) {
					Log.e(TAG, "GPS-Log finish error: " + e.getMessage());
				}
			}

		}

		Log.v(TAG, "processCmdResults done");

	}


}
