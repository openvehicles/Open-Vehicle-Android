package com.openvehicles.OVMS.entities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.luttu.AppPrefes;
import com.openvehicles.OVMS.BaseApp;
import com.openvehicles.OVMS.R;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarData implements Serializable {
	private static final String TAG = "CarData";
	private static final long serialVersionUID = 9069218298370983442L;

	public enum DataStale {
		NoValue, Stale, Good
	}

	// //////////////////////////////////////////////////////////////////////
	// Selected Vehicle

	public String sel_server = "tmc.openvehicles.com"; // ServerNameOrIP
	public String sel_gcm_senderid = "";		// GCM sender ID (empty = default OVMS ID)
	public String sel_vehicleid = "";			// VehicleID
	public String sel_vehicle_label = "";		// VehicleLabel
	public String sel_server_password = "";		// NetPass
	public String sel_module_password = "";		// RegPass
	public boolean sel_paranoid = false;		// ParanoidMode
	public String sel_vehicle_image = "";		// The vehicle image (on disk)
	public Date car_lastupdated = null;
	public int server_carsconnected = 0;

	// //////////////////////////////////////////////////////////////////////
	// Vehicle Sanitized Data

	// Environment
	public boolean car_frontleftdoor_open = false;
	public boolean car_frontrightdoor_open = false;
	public boolean car_chargeport_open = false;
	public boolean car_pilot_present = false;
	public boolean car_charging = false;
	public boolean car_charging_12v = false;
	public boolean car_handbrake_on = false;
	public boolean car_started = false;
	public boolean car_locked = false;
	public boolean car_valetmode = false;
	public boolean car_headlights_on = false;
	public boolean car_bonnet_open = false;
	public boolean car_trunk_open = false;
	public boolean car_awake = false;
	public boolean car_alarm_sounding = false;
	public boolean car_charge_timer = false;
	public Date car_parked_time = null;
	public DataStale stale_environment = DataStale.NoValue;

	// Temperatures
	public String car_temp_pem = "";
	public String car_temp_motor = "";
	public String car_temp_battery = "";
	public String car_temp_charger = "";
	public String car_temp_ambient = "";
	public DataStale stale_car_temps = DataStale.NoValue;
	public DataStale stale_ambient_temp = DataStale.NoValue;

	// Firmware
	public String car_firmware = "";
	public String car_vin = "";
	public String car_type = "";
	public int car_canwrite_raw = 0;
	public boolean car_canwrite = false;
	public String car_gsmlock = "";
	public String car_gsm_signal = "";
	public int car_gsm_dbm = 0;
	public int car_gsm_bars = 0;
	public String server_firmware = "";

	public double car_12vline_voltage;
	public double car_12vline_ref;
	public double car_12v_current;

	public DataStale stale_firmware = DataStale.NoValue;
	public double car_CAC = 0.0;
	public double car_CAC_ref = 0.0;
	public double car_CAC_percent = 0.0;

	// Status
	public String car_soc = "";
	public String car_charge_linevoltage = "";
	public String car_charge_current = "";
	public String car_charge_voltagecurrent = "";
	public String car_charge_currentlimit = "";
	public String car_charge_mode = "";
	public String car_charge_state = "";
	public String car_range_ideal = "";
	public String car_range_estimated = "";
	public String car_charge_time = "";
	public String car_distance_units = "";
	public String car_speed_units = "";
	public String car_chargelimit_rangelimit = "";
	public String car_max_idealrange = "";

	public DataStale stale_chargetimer = DataStale.NoValue;
	public DataStale stale_status = DataStale.NoValue;

	// Position
	public double car_latitude = 0;
	public double car_longitude = 0;
	public double car_direction = 0;
	public double car_altitude = 0;
	public String car_speed = "";
	public String car_tripmeter = "";
	public String car_odometer = "";
	public boolean car_gpslock = false;
	public DataStale stale_gps = DataStale.NoValue;

	// TPMS
	public String car_tpms_fl_p = "";
	public String car_tpms_fl_t = "";
	public String car_tpms_fr_p = "";
	public String car_tpms_fr_t = "";
	public String car_tpms_rl_p = "";
	public String car_tpms_rl_t = "";
	public String car_tpms_rr_p = "";
	public String car_tpms_rr_t = "";
	public DataStale stale_tpms = DataStale.NoValue;

	// //////////////////////////////////////////////////////////////////////
	// RAW values from the vehicle

	// Car Environment Message "D"
	public int car_doors1_raw = 0;
	public int car_doors2_raw = 0;
	public int car_doors3_raw = 0;
	public int car_doors4_raw = 0;
	public int car_doors5_raw = 0;
	public int car_lockunlock_raw = 0;
	public float car_temp_pem_raw = 0;
	public float car_temp_motor_raw = 0;
	public float car_temp_battery_raw = 0;
	public float car_temp_charger_raw = 0;
	public float car_temp_ambient_raw = 0;
	public float car_tripmeter_raw = 0;
	public float car_odometer_raw = 0;
	public float car_speed_raw = 0;
	public long car_parking_timer_raw = 0;
	public int car_stale_car_temps_raw = -1;
	public float car_stale_ambient_temp_raw = -1;

	// Car Firmware Message "F"
	public int car_gsm_signal_raw = 0;

	// Car State Message "S"
	public float car_soc_raw = 0;
	public String car_distance_units_raw = "";
	public float car_charge_linevoltage_raw = 0;
	public float car_charge_current_raw = 0;
	public String car_charge_state_s_raw = "";
	public int car_charge_state_i_raw = 0;
	public int car_charge_substate_i_raw = 0;
	public String car_mode_s_raw = "";
	public int car_charge_mode_i_raw = 0;
	public float car_range_ideal_raw = 0;
	public float car_range_estimated_raw = 0;
	public float car_charge_currentlimit_raw = 0;
	public int car_charge_duration_raw = 0;
	public int car_charge_b4byte_raw = 0;
	public float car_charge_kwhconsumed = 0;
	public int car_charge_timermode_raw = 0;
	public int car_charge_timerstart_raw = 0;
	public int car_stale_chargetimer_raw = -1;
	public int car_chargefull_minsremaining = -1;
	public int car_chargelimit_minsremaining = -1;
	public int car_chargelimit_rangelimit_raw = 0;
	public int car_chargelimit_soclimit = 0;
	public int car_coolingdown = -1;
	public int car_cooldown_tbattery = 0;
	public int car_cooldown_timelimit = 0;
	public int car_chargeestimate = -1;
	public int car_chargelimit_minsremaining_range = -1;
	public int car_chargelimit_minsremaining_soc = -1;
	public int car_max_idealrange_raw = 0;
	public int car_charge_plugtype = 0;
	public double car_charge_power_kw = 0;
	public double car_battery_voltage = 0;
	public float car_soh = 0;

	// Car Update Time Message "T"
	public long car_lastupdate_raw = 0;

	// Car Location Message "L"
	public int car_gpslock_raw = -1;
	public int car_stale_gps_raw = -1;

	// Car TPMS Message "W"
	public double car_tpms_fl_p_raw = 0.0D;
	public double car_tpms_fl_t_raw = 0.0D;
	public double car_tpms_fr_p_raw = 0.0D;
	public double car_tpms_fr_t_raw = 0.0D;
	public double car_tpms_rl_p_raw = 0.0D;
	public double car_tpms_rl_t_raw = 0.0D;
	public double car_tpms_rr_p_raw = 0.0D;
	public double car_tpms_rr_t_raw = 0.0D;
	public int car_stale_tpms_raw = -1;

	// Car Capabilities Message "V"
	public String car_capabilities = "";
	public boolean[] car_command_support;

	// Car Location Message "L": Drive/Power/Energy status
	public int car_drivemode = 0;
	public double car_power = 0.0;
	public float car_energyused = 0;
	public float car_energyrecd = 0;

	
	//
	// Renault Twizy specific
	//
	public int rt_cfg_type = 0;					// CFG: 0=Twizy80, 1=Twizy45
	public int rt_cfg_profile_user = 0;			// CFG: user selected profile: 0=Default, 1..3=Custom
	public int rt_cfg_profile_cfgmode = 0;		// CFG: profile, cfgmode params were last loaded from
	public int rt_cfg_unsaved = 0;				// CFG: RAM profile changed & not yet saved to EEPROM
	public int rt_cfg_applied = 0;				// CFG: applyprofile success flag


	//
	// Private (non serialization) data
	//

	private transient Context mContext;
	private transient AppPrefes appPrefes;
	private transient DecimalFormat decimalFormat1;


	/**
	 * Default constructor
	 */
	public CarData() {
		Init();
		// Allocate memory:
		car_command_support = new boolean[256];
	}

	private void Init() {
		// get Application Context and Preferences access:
		if (mContext == null)
			mContext = BaseApp.getContext();
		if (appPrefes == null)
			appPrefes = new AppPrefes(mContext, "ovms");
		if (decimalFormat1 == null)
			decimalFormat1 = new DecimalFormat("0.#");
	}

	
	/**
	 * Process capabilities message ("V")
	 */
	public boolean processCapabilities(String msgdata) {

		Log.d(TAG, "processCapabilities: " + msgdata);

		car_capabilities = msgdata;
		car_command_support = new boolean[256];

		// msgdata format: C1,C3-6,...
		// translate to bool array:
		try {
			String[] parts = msgdata.split(",\\s*");
			int i, start, end;
			for (String part : parts) {
				// Command?
				if (part.startsWith("C")) {
					String[] caps = part.split("-");
					start = Integer.parseInt(caps[0].substring(1));
					if (caps.length > 1) {
						end = Integer.parseInt(caps[1]);
					} else {
						end = start;
					}
					for (i = start; i <= end; i++) {
						car_command_support[i] = true;
					}
				}
			}
		} catch(Exception e) {
			Log.e(TAG, "processCapabilities: ERROR", e);
			return false;
		}

		return true;
	}

	public boolean hasCommand(int cmd) {
		return (car_command_support != null) && car_command_support[cmd];
	}


	/**
	 * Process location message ("L")
	 */
	public boolean processLocation(String msgdata) {

		Log.d(TAG, "processLocation: " + msgdata);

		try {
			String[] dataParts = msgdata.split(",\\s*");

			if (dataParts.length >= 2) {
				car_latitude = Double.parseDouble(dataParts[0]);
				car_longitude = Double.parseDouble(dataParts[1]);
			}
			if (dataParts.length >= 6) {
				car_direction = Double.parseDouble(dataParts[2]);
				car_altitude = Double.parseDouble(dataParts[3]);
				car_gpslock_raw = Integer.parseInt(dataParts[4]);
				car_gpslock = (car_gpslock_raw > 0);
				car_stale_gps_raw = Integer.parseInt(dataParts[5]);
				if (car_stale_gps_raw < 0)
					stale_gps = DataStale.NoValue;
				else if (car_stale_gps_raw == 0)
					stale_gps = DataStale.Stale;
				else
					stale_gps = DataStale.Good;
			}
			if (dataParts.length >= 8) {
				car_speed_raw = Float.parseFloat(dataParts[6]);
				car_speed = String.format("%s%s", decimalFormat1.format(car_speed_raw), car_speed_units);
				car_tripmeter_raw = Float.parseFloat(dataParts[7]);
				car_tripmeter = String.format("%.1f%s", (float) car_tripmeter_raw / 10, car_distance_units);
			}
			if (dataParts.length >= 12) {
				car_drivemode = Integer.parseInt(dataParts[8], 16);
				if ("RT".equals(car_type)) {
					rt_cfg_type = (car_drivemode & 0x01);
					rt_cfg_profile_user = (car_drivemode & 0x06) >> 1;
					rt_cfg_profile_cfgmode = (car_drivemode & 0x18) >> 3;
					rt_cfg_unsaved = (car_drivemode & 0x20) >> 5;
					rt_cfg_applied = (car_drivemode & 0x80) >> 7;
				}
				car_power = Double.parseDouble(dataParts[9]);
				car_energyused = Float.parseFloat(dataParts[10]);
				car_energyrecd = Float.parseFloat(dataParts[11]);
			}

		} catch(Exception e) {
			Log.e(TAG, "processLocation: ERROR", e);
			return false;
		}

		return true;
	}


	/**
	 * Process status message ("S")
	 */
	public boolean processStatus(String msgdata) {
		Init();
		Log.d(TAG, "processStatus: " + msgdata);

		try {
			String[] dataParts = msgdata.split(",\\s*");

			if (dataParts.length >= 8) {
				Log.v(TAG, "S MSG Validated");
				car_soc_raw = Float.parseFloat(dataParts[0]);
				car_soc = String.format("%.1f%%", car_soc_raw);
				car_distance_units_raw = dataParts[1];
				car_distance_units = (car_distance_units_raw.equals("M")) ? "m" : "km";
				car_speed_units = (car_distance_units_raw.equals("M"))
						? mContext.getText(R.string.mph).toString()
						: mContext.getText(R.string.kph).toString();
				car_charge_linevoltage_raw = Float.parseFloat(dataParts[2]);
				car_charge_linevoltage = String.format("%.1f%s", car_charge_linevoltage_raw, "V");
				car_charge_current_raw = Float.parseFloat(dataParts[3]);
				car_charge_current = String.format("%.1f%s", car_charge_current_raw, "A");
				car_charge_voltagecurrent = String.format("%.1f%s %.1f%s",
						car_charge_linevoltage_raw, "V",
						car_charge_current_raw, "A");
				car_charge_state_s_raw = dataParts[4];
				car_charge_state = car_charge_state_s_raw;
				car_mode_s_raw = dataParts[5];
				car_charge_mode = car_mode_s_raw;
				car_range_ideal_raw = Float.parseFloat(dataParts[6]);
				car_range_ideal = String.format("%.1f%s", car_range_ideal_raw, car_distance_units);
				car_range_estimated_raw = Float.parseFloat(dataParts[7]);
				car_range_estimated = String.format("%.1f%s", car_range_estimated_raw, car_distance_units);
				stale_status = DataStale.Good;
			}
			if (dataParts.length >= 15) {
				car_charge_currentlimit_raw = Float.parseFloat(dataParts[8]);
				car_charge_currentlimit = String.format("%.1f%s", car_charge_currentlimit_raw, "A");
				car_charge_duration_raw = Integer.parseInt(dataParts[9]);
				car_charge_b4byte_raw = Integer.parseInt(dataParts[10]);
				car_charge_kwhconsumed = Float.parseFloat(dataParts[11]);
				car_charge_substate_i_raw = Integer.parseInt(dataParts[12]);
				car_charge_state_i_raw = Integer.parseInt(dataParts[13]);
				car_charge_mode_i_raw = Integer.parseInt(dataParts[14]);
			}
			if (dataParts.length >= 18) {
				car_charge_timermode_raw = Integer.parseInt(dataParts[15]);
				car_charge_timer = (car_charge_timermode_raw > 0);
				car_charge_timerstart_raw = Integer.parseInt(dataParts[16]);
				car_charge_time = ""; // TODO: Implement later
				car_stale_chargetimer_raw = Integer.parseInt(dataParts[17]);
				if (car_stale_chargetimer_raw < 0)
					stale_chargetimer = DataStale.NoValue;
				else if (car_stale_chargetimer_raw == 0)
					stale_chargetimer = DataStale.Stale;
				else
					stale_chargetimer = DataStale.Good;

			}
			if (dataParts.length >= 19) {
				car_CAC = Double.parseDouble(dataParts[18]);
				if ("RT".equals(car_type)) {
					car_CAC_ref = 108.0;
					car_CAC_percent = car_CAC / car_CAC_ref * 100;
				}
			}
			if (dataParts.length >= 27) {
				car_chargefull_minsremaining = Integer.parseInt(dataParts[19]);
				car_chargelimit_minsremaining = Integer.parseInt(dataParts[20]);
				car_chargelimit_rangelimit_raw = Integer.parseInt(dataParts[21]);
				car_chargelimit_rangelimit = String.format("%d%s",
						car_chargelimit_rangelimit_raw, car_distance_units);
				car_chargelimit_soclimit = Integer.parseInt(dataParts[22]);
				car_coolingdown = Integer.parseInt(dataParts[23]);
				car_cooldown_tbattery = Integer.parseInt(dataParts[24]);
				car_cooldown_timelimit = Integer.parseInt(dataParts[25]);
				car_chargeestimate = Integer.parseInt(dataParts[26]);
			}
			if (dataParts.length >= 30) {
				car_chargelimit_minsremaining_range = Integer.parseInt(dataParts[27]);
				car_chargelimit_minsremaining_soc = Integer.parseInt(dataParts[28]);
				car_max_idealrange_raw = Integer.parseInt(dataParts[29]);
				car_max_idealrange = String.format("%d%s",
						car_max_idealrange_raw, car_distance_units);
			}
			if (dataParts.length >= 33) {
				car_charge_plugtype = Integer.parseInt(dataParts[30]);
				car_charge_power_kw = Double.parseDouble(dataParts[31]);
				car_battery_voltage = Double.parseDouble(dataParts[32]);
			}
			if (dataParts.length >= 34) {
				car_soh = Float.parseFloat(dataParts[33]);
			}

		} catch(Exception e) {
			Log.e(TAG, "processStatus: ERROR", e);
			return false;
		}

		return true;
	}


	/**
	 * Process doors & environment message ("D")
	 */
	public boolean processEnvironment(String msgdata) {
		Init();

		Log.d(TAG, "processEnvironment: " + msgdata);

		try {
			String[] dataParts = msgdata.split(",\\s*");
			int dataField;

			if (dataParts.length >= 9) {
				dataField = Integer.parseInt(dataParts[0]);
				car_doors1_raw = dataField;
				car_frontleftdoor_open = ((dataField & 0x1) == 0x1);
				car_frontrightdoor_open = ((dataField & 0x2) == 0x2);
				car_chargeport_open = ((dataField & 0x4) == 0x4);
				car_pilot_present = ((dataField & 0x8) == 0x8);
				car_charging = ((dataField & 0x10) == 0x10);
				// bit 5 is always 1
				car_handbrake_on = ((dataField & 0x40) == 0x40);
				car_started = ((dataField & 0x80) == 0x80);

				dataField = Integer.parseInt(dataParts[1]);
				car_doors2_raw = dataField;
				car_bonnet_open = ((dataField & 0x40) == 0x40);
				car_trunk_open = ((dataField & 0x80) == 0x80);
				car_headlights_on = ((dataField & 0x20) == 0x20);
				car_valetmode = ((dataField & 0x10) == 0x10);
				car_locked = ((dataField & 0x08) == 0x08);

				car_lockunlock_raw = Integer.parseInt(dataParts[2]);

				car_temp_pem_raw = Float.parseFloat(dataParts[3]);
				car_temp_motor_raw = Float.parseFloat(dataParts[4]);
				car_temp_battery_raw = Float.parseFloat(dataParts[5]);
				if (appPrefes.getData("showfahrenheit").equals("on")) {
					car_temp_pem = String.format("%.1f\u00B0F", (car_temp_pem_raw * (9.0 / 5.0)) + 32.0);
					car_temp_motor = String.format("%.1f\u00B0F", (car_temp_motor_raw * (9.0 / 5.0)) + 32.0);
					car_temp_battery = String.format("%.1f\u00B0F", (car_temp_battery_raw * (9.0 / 5.0)) + 32.0);
				} else {
					car_temp_pem = String.format("%.1f\u00B0C", car_temp_pem_raw);
					car_temp_motor = String.format("%.1f\u00B0C", car_temp_motor_raw);
					car_temp_battery = String.format("%.1f\u00B0C", car_temp_battery_raw);
				}

				car_tripmeter_raw = Float.parseFloat(dataParts[6]);
				car_tripmeter = String.format("%.1f%s", (float) car_tripmeter_raw / 10, car_distance_units);
				car_odometer_raw = Float.parseFloat(dataParts[7]);
				car_odometer = String.format("%.1f%s", (float) car_odometer_raw / 10, car_distance_units);
				car_speed_raw = Float.parseFloat(dataParts[8]);
				car_speed = String.format("%.1f%s", car_speed_raw, car_speed_units);

				stale_environment = DataStale.Good;
			}
			if (dataParts.length >= 14) {
				car_parking_timer_raw = Long.parseLong(dataParts[9]);
				car_parked_time = new Date((new Date()).getTime() - car_parking_timer_raw * 1000);

				car_temp_ambient_raw = Float.parseFloat(dataParts[10]);
				if (appPrefes.getData("showfahrenheit").equals("on"))
					car_temp_ambient = String.format("%.1f\u00B0F", (car_temp_ambient_raw * (9.0 / 5.0)) + 32.0);
				else
					car_temp_ambient = String.format("%.1f\u00B0C", car_temp_ambient_raw);

				dataField = Integer.parseInt(dataParts[11]);
				car_doors3_raw = dataField;
				car_awake = ((dataField & 0x02) == 0x02);

				car_stale_car_temps_raw = Integer.parseInt(dataParts[12]);
				if (car_stale_car_temps_raw < 0)
					stale_car_temps = DataStale.NoValue;
				else if (car_stale_car_temps_raw == 0)
					stale_car_temps = DataStale.Stale;
				else
					stale_car_temps = DataStale.Good;

				car_stale_ambient_temp_raw = Float.parseFloat(dataParts[13]);
				if (car_stale_ambient_temp_raw < 0)
					stale_ambient_temp = DataStale.NoValue;
				else if (car_stale_ambient_temp_raw == 0)
					stale_ambient_temp = DataStale.Stale;
				else
					stale_ambient_temp = DataStale.Good;
			}
			if (dataParts.length >= 16) {
				car_12vline_voltage = Double.parseDouble(dataParts[14]);
				dataField = Integer.parseInt(dataParts[15]);
				car_doors4_raw = dataField;
				car_alarm_sounding = ((dataField & 0x02) == 0x02);
			}
			if (dataParts.length >= 18) {
				car_12vline_ref = Double.parseDouble(dataParts[16]);
				dataField = Integer.parseInt(dataParts[17]);
				car_doors5_raw = dataField;
				car_charging_12v = ((dataField & 0x10) == 0x10);
			}
			if (dataParts.length >= 19) {
				car_temp_charger_raw = Float.parseFloat(dataParts[18]);
				if (appPrefes.getData("showfahrenheit").equals("on")) {
					car_temp_charger = String.format("%.1f\u00B0F", (car_temp_charger_raw * (9.0 / 5.0)) + 32.0);
				} else {
					car_temp_charger = String.format("%.1f\u00B0C", car_temp_charger_raw);
				}
			}
			if (dataParts.length >= 20) {
				car_12v_current = Double.parseDouble(dataParts[19]);
			}

		} catch(Exception e) {
			Log.e(TAG, "processEnvironment: ERROR", e);
			return false;
		}

		return true;
	}


	/**
	 * Process VIN and firmware message ("F")
	 */
	public boolean processFirmware(String msgdata) {

		Log.d(TAG, "processFirmware: " + msgdata);

		try {
			String[] dataParts = msgdata.split(",\\s*");

			if (dataParts.length >= 3) {
				car_firmware = dataParts[0];
				car_vin = dataParts[1];

				car_gsm_signal_raw = Integer.parseInt(dataParts[2]);
				car_gsm_dbm = 0;
				if (car_gsm_signal_raw <= 31)
					car_gsm_dbm = -113 + (car_gsm_signal_raw * 2);
				car_gsm_signal = String.format("%d%s", car_gsm_dbm, " dbm");
				if ((car_gsm_dbm < -121) || (car_gsm_dbm >= 0))
					car_gsm_bars = 0;
				else if (car_gsm_dbm < -107)
					car_gsm_bars = 1;
				else if (car_gsm_dbm < -98)
					car_gsm_bars = 2;
				else if (car_gsm_dbm < -87)
					car_gsm_bars = 3;
				else if (car_gsm_dbm < -76)
					car_gsm_bars = 4;
				else
					car_gsm_bars = 5;

				stale_firmware = DataStale.Good;
			}
			if (dataParts.length >= 5) {
				car_canwrite_raw = Integer.parseInt(dataParts[3]);
				car_canwrite = (car_canwrite_raw > 0);
				car_type = dataParts[4];
			}
			if (dataParts.length >= 6) {
				car_gsmlock = dataParts[5];
			}

		} catch(Exception e) {
			Log.e(TAG, "processFirmware: ERROR", e);
			return false;
		}

		return true;
	}


	/**
	 * Process TPMS message ("W")
	 */
	public boolean processTPMS(String msgdata) {
		Init();
		Log.d(TAG, "processTPMS: " + msgdata);

		try {
			String[] dataParts = msgdata.split(",\\s*");

			if (dataParts.length >= 9) {
				car_tpms_fr_p_raw = Double.parseDouble(dataParts[0]);
				car_tpms_fr_t_raw = Double.parseDouble(dataParts[1]);
				car_tpms_rr_p_raw = Double.parseDouble(dataParts[2]);
				car_tpms_rr_t_raw = Double.parseDouble(dataParts[3]);
				car_tpms_fl_p_raw = Double.parseDouble(dataParts[4]);
				car_tpms_fl_t_raw = Double.parseDouble(dataParts[5]);
				car_tpms_rl_p_raw = Double.parseDouble(dataParts[6]);
				car_tpms_rl_t_raw = Double.parseDouble(dataParts[7]);
				car_tpms_fl_p = String.format("%.1f%s", car_tpms_fl_p_raw, "psi");
				car_tpms_fr_p = String.format("%.1f%s", car_tpms_fr_p_raw, "psi");
				car_tpms_rl_p = String.format("%.1f%s", car_tpms_rl_p_raw, "psi");
				car_tpms_rr_p = String.format("%.1f%s", car_tpms_rr_p_raw, "psi");
				if (appPrefes.getData("showfahrenheit").equals("on")) {
					car_tpms_fl_t = String.format("%.0f%s", (car_tpms_fl_t_raw * (9.0 / 5.0)) + 32.0, "\u00B0F");
					car_tpms_fr_t = String.format("%.0f%s", (car_tpms_fr_t_raw * (9.0 / 5.0)) + 32.0, "\u00B0F");
					car_tpms_rl_t = String.format("%.0f%s", (car_tpms_rl_t_raw * (9.0 / 5.0)) + 32.0, "\u00B0F");
					car_tpms_rr_t = String.format("%.0f%s", (car_tpms_rr_t_raw * (9.0 / 5.0)) + 32.0, "\u00B0F");
				} else {
					car_tpms_fl_t = String.format("%.0f%s", car_tpms_fl_t_raw, "\u00B0C");
					car_tpms_fr_t = String.format("%.0f%s", car_tpms_fr_t_raw, "\u00B0C");
					car_tpms_rl_t = String.format("%.0f%s", car_tpms_rl_t_raw, "\u00B0C");
					car_tpms_rr_t = String.format("%.0f%s", car_tpms_rr_t_raw, "\u00B0C");
				}
				car_stale_tpms_raw = Integer.parseInt(dataParts[8]);
				if (car_stale_tpms_raw < 0)
					stale_tpms = DataStale.NoValue;
				else if (car_stale_tpms_raw == 0)
					stale_tpms = DataStale.Stale;
				else
					stale_tpms = DataStale.Good;
			}

		} catch(Exception e) {
			Log.e(TAG, "processTPMS: ERROR", e);
			return false;
		}

		return true;
	}



	/**
	 * Get data extract suitable for system broadcast.
	 *
	 * The intended receivers are automation Apps like Automagic & Tasker,
	 * so only public data shall be added, and data shall be prepared
	 * to be easily usable in scripts.
	 *
	 * @return the Bundle to be broadcasted in Intent
	 */
	public Bundle getBroadcastData() {

		SimpleDateFormat isoDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Bundle b = new Bundle();

		try {

			//
			// Time (msgCode 'T')
			//

			b.putLong("car_lastupdated_seconds", car_lastupdate_raw);
			b.putString("car_lastupdated_time",
					(car_lastupdated != null) ? isoDateTime.format(car_lastupdated) : "");


			//
			// Peers (msgCode 'Z')
			//

			b.putInt("server_carsconnected", server_carsconnected);


			//
			// Status (msgCode 'S')
			//

			b.putFloat("car_soc", car_soc_raw);
			b.putDouble("car_battery_voltage", car_battery_voltage);
			b.putDouble("car_battery_cac", car_CAC);
			b.putDouble("car_battery_cac_ref", car_CAC_ref);
			b.putDouble("car_battery_cac_percent", car_CAC_percent);
			b.putFloat("car_soh", car_soh);

			b.putString("car_distance_units", car_distance_units_raw);
			b.putFloat("car_range_ideal", car_range_ideal_raw);
			b.putInt("car_range_ideal_max", car_max_idealrange_raw);
			b.putFloat("car_range_estimated", car_range_estimated_raw);

			b.putInt("car_charge_state", car_charge_state_i_raw);
			b.putInt("car_charge_mode", car_charge_mode_i_raw);
			b.putString("car_charge_state_label", car_charge_state);
			b.putString("car_charge_mode_label", car_charge_mode);
			b.putFloat("car_charge_linevoltage", car_charge_linevoltage_raw);
			b.putFloat("car_charge_current", car_charge_current_raw);
			b.putFloat("car_charge_currentlimit", car_charge_currentlimit_raw);
			b.putInt("car_charge_duration", car_charge_duration_raw);
			b.putInt("car_charge_plugtype", car_charge_plugtype);
			b.putDouble("car_charge_power_kw", car_charge_power_kw);
			b.putFloat("car_charge_kwhconsumed", car_charge_kwhconsumed / 10f);
			b.putBoolean("car_charge_timer", car_charge_timer);

			b.putInt("car_chargeestimate", car_chargeestimate);
			b.putInt("car_chargefull_minsremaining", car_chargefull_minsremaining);
			b.putInt("car_chargelimit_minsremaining", car_chargelimit_minsremaining);
			b.putInt("car_chargelimit_rangelimit", car_chargelimit_rangelimit_raw);
			b.putInt("car_chargelimit_minsremaining_range", car_chargelimit_minsremaining_range);
			b.putInt("car_chargelimit_soclimit", car_chargelimit_soclimit);
			b.putInt("car_chargelimit_minsremaining_soc", car_chargelimit_minsremaining_soc);


			//
			// Location (msgCode 'L')
			//

			b.putDouble("car_latitude", car_latitude);
			b.putDouble("car_longitude", car_longitude);
			b.putDouble("car_direction", car_direction);
			b.putDouble("car_altitude", car_altitude);
			b.putInt("car_gps_lock", car_gpslock_raw);
			b.putInt("car_gps_stale", car_stale_gps_raw);

			b.putDouble("car_speed", car_speed_raw);
			b.putFloat("car_tripmeter", car_tripmeter_raw);

			b.putInt("car_drivemode", car_drivemode);
			if ("RT".equals(car_type)) {
				b.putInt("rt_cfg_type", rt_cfg_type);
				b.putInt("rt_cfg_profile_user", rt_cfg_profile_user);
				b.putInt("rt_cfg_profile_cfgmode", rt_cfg_profile_cfgmode);
				b.putInt("rt_cfg_unsaved", rt_cfg_unsaved);
				b.putInt("rt_cfg_applied", rt_cfg_applied);
			}

			b.putDouble("car_power", car_power);
			b.putFloat("car_energyused", car_energyused);
			b.putFloat("car_energyrecd", car_energyrecd);


			//
			// Environment, doors & switches (msgCode 'D')
			//

			b.putBoolean("car_frontleftdoor_open", car_frontleftdoor_open);
			b.putBoolean("car_frontrightdoor_open", car_frontrightdoor_open);
			b.putBoolean("car_chargeport_open", car_chargeport_open);
			b.putBoolean("car_pilot_present", car_pilot_present);
			b.putBoolean("car_charging", car_charging);
			b.putBoolean("car_handbrake_on", car_handbrake_on);
			b.putBoolean("car_started", car_started);
			b.putBoolean("car_locked", car_locked);
			b.putBoolean("car_valetmode", car_valetmode);
			b.putBoolean("car_headlights_on", car_headlights_on);
			b.putBoolean("car_bonnet_open", car_bonnet_open);
			b.putBoolean("car_trunk_open", car_trunk_open);
			b.putBoolean("car_awake", car_awake);
			b.putBoolean("car_alarm_sounding", car_alarm_sounding);

			b.putFloat("car_temp_pem", car_temp_pem_raw);
			b.putFloat("car_temp_motor", car_temp_motor_raw);
			b.putFloat("car_temp_battery", car_temp_battery_raw);
			b.putFloat("car_temp_ambient", car_temp_ambient_raw);
			b.putFloat("car_temp_charger", car_temp_charger_raw);

			b.putFloat("car_odometer", car_odometer_raw);

			if (car_parked_time != null) {
				b.putLong("car_parked_seconds", car_parking_timer_raw);
				b.putString("car_parked_time", isoDateTime.format(car_parked_time));
			} else {
				b.putLong("car_parked_seconds", 0);
				b.putString("car_parked_time", "");
			}

			b.putBoolean("car_charging_12v", car_charging_12v);
			b.putDouble("car_12vline_voltage", car_12vline_voltage);
			b.putDouble("car_12vline_ref", car_12vline_ref);
			b.putDouble("car_12v_current", car_12v_current);


			//
			// Firmware (msgCode 'F')
			//

			b.putString("car_firmware", car_firmware);
			b.putString("car_vin", car_vin);

			b.putInt("car_gsm_dbm", car_gsm_dbm);
			b.putInt("car_gsm_bars", car_gsm_bars);
			b.putString("car_gsm_lock", car_gsmlock);

			b.putInt("car_canwrite", car_canwrite_raw);


			//
			// TPMS (msgCode 'W')
			//

			b.putDouble("car_tpms_fr_p", car_tpms_fr_p_raw);
			b.putDouble("car_tpms_fr_t", car_tpms_fr_t_raw);
			b.putDouble("car_tpms_rr_p", car_tpms_rr_p_raw);
			b.putDouble("car_tpms_rr_t", car_tpms_rr_t_raw);
			b.putDouble("car_tpms_fl_p", car_tpms_fl_p_raw);
			b.putDouble("car_tpms_fl_t", car_tpms_fl_t_raw);
			b.putDouble("car_tpms_rl_p", car_tpms_rl_p_raw);
			b.putDouble("car_tpms_rl_t", car_tpms_rl_t_raw);


			//
			// Capabilities (msgCode 'V')
			//

			b.putString("car_capabilities", car_capabilities);


		} catch (Exception e) {
			e.printStackTrace();
		}

		return b;
	}

}
