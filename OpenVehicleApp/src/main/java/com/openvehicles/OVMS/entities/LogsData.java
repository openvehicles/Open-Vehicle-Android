package com.openvehicles.OVMS.entities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.openvehicles.OVMS.BaseApp;
import com.openvehicles.OVMS.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by balzer on 07.03.15.
 *
 * This is the model for LogsFragment.
 *
 * It's currently designed to meet the storage requirements for the
 * SEVCON diagnostic logs as supported by the Twizy firmware,
 * but can be used as a container for other vehicles and/or logs.
 *
 */
public class LogsData {
	private static final transient String TAG = "LogsData";


	//
	// Storage classes:
	//

	static public class KeyTime {
		public int keyHour;
		public int keyMinSec;
		public Date timeStamp;

		public String fmtKeyTime() {
			int days = keyHour / 24;
			int hours = keyHour % 24;
			int mins = (keyMinSec * 15) / 60;
			int secs = (keyMinSec * 15) % 60;
			return context.getString(R.string.logs_fmt_keytime,
					days, hours, mins, secs);
		}

		public String fmtTimeStamp() {
			return (timeStamp != null)
					? DateFormat.getDateTimeInstance().format(timeStamp)
					: "-";
		}
	}

	static public class Alert {
		public String code;
		public String description;
	}

	static public class Event {
		public String code;
		public String description;
		public KeyTime time;
		public String data[];
	}

	static public class Counter {
		public String code;
		public String description;
		public KeyTime lastTime;
		public KeyTime firstTime;
		public int count;
	}

	static public class MinMax {
		public Double
				batteryVoltageMin,
				batteryVoltageMax,
				capacitorVoltageMin,
				capacitorVoltageMax;
		public int
				motorCurrentMin,
				motorCurrentMax,
				motorSpeedMin,
				motorSpeedMax,
				deviceTempMin,
				deviceTempMax;

		public String getSensor(int nr) {
			switch (nr) {
				case 0:
					return batteryVoltageMin.toString();
				case 1:
					return batteryVoltageMax.toString();
				case 2:
					return capacitorVoltageMin.toString();
				case 3:
					return capacitorVoltageMax.toString();
				case 4:
					return "" + motorCurrentMin;
				case 5:
					return "" + motorCurrentMax;
				case 6:
					return "" + motorSpeedMin;
				case 7:
					return "" + motorSpeedMax;
				case 8:
					return "" + deviceTempMin;
				case 9:
					return "" + deviceTempMax;
				default:
					return "";
			}
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

	public KeyTime keyTime;

	public ArrayList<Alert> alerts;
	public KeyTime alertsTime;

	public ArrayList<Event> faultEvents;
	public KeyTime faultEventsTime;

	public ArrayList<Event> systemEvents;
	public KeyTime systemEventsTime;

	public ArrayList<Counter> counters;
	public KeyTime countersTime;

	public ArrayList<MinMax> minMaxes;
	public KeyTime minMaxesTime;


	//
	// Methods
	//


	public LogsData() {

		vehicleId = "";

		keyTime = new KeyTime();

		alerts = new ArrayList<Alert>();
		alertsTime = new KeyTime();

		faultEvents = new ArrayList<Event>();
		faultEventsTime = new KeyTime();

		systemEvents = new ArrayList<Event>();
		systemEventsTime = new KeyTime();

		counters = new ArrayList<Counter>();
		countersTime = new KeyTime();

		minMaxes = new ArrayList<MinMax>();
		minMaxesTime = new KeyTime();

	}


	public static LogsData loadFile(String vehicleId) {

		FileInputStream inputStream;
		String filename = "logsdata-" + vehicleId + "-default.json";
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
			return gson.fromJson(json, LogsData.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public boolean saveFile(String vehicleId) {

		FileOutputStream outputStream;
		String filename = "logsdata-" + vehicleId + "-default.json";
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
		String recType, timeStamp;
		int entryNr;
		SimpleDateFormat serverTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		serverTime.setTimeZone(TimeZone.getTimeZone("UTC"));

		for (int i=0; i < cmdSeries.size(); i++) {

			CmdSeries.Cmd cmd = cmdSeries.get(i);

			if (cmd.commandCode != 32)
				continue;
			
			for (int resNr=0; resNr < cmd.results.size(); resNr++) {
				
				String result[] = cmd.results.get(resNr);
				
				if (result[2].equals("No historical data available"))
					continue;

				try {

					recNr = Integer.parseInt(result[2]);
					recCnt = Integer.parseInt(result[3]);
					recType = result[4];
					timeStamp = result[5];
					entryNr = Integer.parseInt(result[6]);

					// [7++] = Payload
					Log.v(TAG, "processing recType " + recType + " entryNr " + entryNr);

					if (recType.equals("RT-ENG-LogKeyTime")) {
						keyTime = new KeyTime();
						keyTime.keyHour = Integer.parseInt(result[7]);
						keyTime.keyMinSec = Integer.parseInt(result[8]);
						try {
							keyTime.timeStamp = serverTime.parse(timeStamp);
						} catch (Exception e) {
							keyTime.timeStamp = new Date();
						}
					} else if (recType.equals("RT-ENG-LogAlerts")) {
						if (entryNr == 0) {
							alerts = new ArrayList<Alert>(20);
							alertsTime = keyTime;
						}
						Alert alert = new Alert();
						alert.code = result[7];
						alert.description = result[8];
						alerts.add(alert);
					} else if (recType.equals("RT-ENG-LogFaults")) {
						if (entryNr == 0) {
							faultEvents = new ArrayList<Event>(40);
							faultEventsTime = keyTime;
						}
						Event event = new Event();
						event.code = result[7];
						event.description = result[8];
						event.time = new KeyTime();
						event.time.keyHour = Integer.parseInt(result[9]);
						event.time.keyMinSec = Integer.parseInt(result[10]);
						event.data = new String[3];
						event.data[0] = result[11];
						event.data[1] = result[12];
						event.data[2] = result[13];
						faultEvents.add(event);
					} else if (recType.equals("RT-ENG-LogSystem")) {
						if (entryNr == 0) {
							systemEvents = new ArrayList<Event>(20);
							systemEventsTime = keyTime;
						}
						Event event = new Event();
						event.code = result[7];
						event.description = result[8];
						event.time = new KeyTime();
						event.time.keyHour = Integer.parseInt(result[9]);
						event.time.keyMinSec = Integer.parseInt(result[10]);
						event.data = new String[3];
						event.data[0] = result[11];
						event.data[1] = result[12];
						event.data[2] = result[13];
						systemEvents.add(event);
					} else if (recType.equals("RT-ENG-LogCounts")) {
						if (entryNr == 0) {
							counters = new ArrayList<Counter>(10);
							countersTime = keyTime;
						}
						Counter counter = new Counter();
						counter.code = result[7];
						counter.description = result[8];
						counter.lastTime = new KeyTime();
						counter.lastTime.keyHour = Integer.parseInt(result[9]);
						counter.lastTime.keyMinSec = Integer.parseInt(result[10]);
						counter.firstTime = new KeyTime();
						counter.firstTime.keyHour = Integer.parseInt(result[11]);
						counter.firstTime.keyMinSec = Integer.parseInt(result[12]);
						counter.count = Integer.parseInt(result[13]);
						counters.add(counter);
					} else if (recType.equals("RT-ENG-LogMinMax")) {
						if (entryNr == 0) {
							minMaxes = new ArrayList<MinMax>(2);
							minMaxesTime = keyTime;
						}
						MinMax minMax = new MinMax();
						minMax.batteryVoltageMin = Double.parseDouble(result[7]) / 16;
						minMax.batteryVoltageMax = Double.parseDouble(result[8]) / 16;
						minMax.capacitorVoltageMin = Double.parseDouble(result[9]) / 16;
						minMax.capacitorVoltageMax = Double.parseDouble(result[10]) / 16;
						minMax.motorCurrentMin = Integer.parseInt(result[11]);
						minMax.motorCurrentMax = Integer.parseInt(result[12]);
						minMax.motorSpeedMin = Integer.parseInt(result[13]);
						minMax.motorSpeedMax = Integer.parseInt(result[14]);
						minMax.deviceTempMin = Integer.parseInt(result[15]);
						minMax.deviceTempMax = Integer.parseInt(result[16]);
						minMaxes.add(minMax);
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
