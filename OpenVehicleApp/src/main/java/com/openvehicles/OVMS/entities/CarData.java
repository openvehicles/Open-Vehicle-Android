package com.openvehicles.OVMS.entities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.openvehicles.OVMS.luttu.AppPrefes;
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

	// ================================================================================
	// START OF PUBLIC DATA
	// ================================================================================
	// NOTE:
	// 	The list of cars is now saved in JSON format, see CarsStorage.saveStoredCars(),
	//	so you can now add new fields where appropriate.

	// //////////////////////////////////////////////////////////////////////
	// Selected Vehicle

	public String sel_server = ""; 				// ServerNameOrIP
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
	public boolean car_rearleftdoor_open = false;
	public boolean car_rearrightdoor_open = false;
	public boolean car_chargeport_open = false;
	public boolean car_pilot_present = false;
	public boolean car_charging = false;
	public boolean car_charging_12v = false;
	public boolean car_auxiliary_12v = false;
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
	public boolean car_hvac_on = false;
	public Date car_parked_time = null;
	public DataStale stale_environment = DataStale.NoValue;

	// Temperatures
	public String car_temp_pem = "";
	public String car_temp_motor = "";
	public String car_temp_battery = "";
	public String car_temp_charger = "";
	public String car_temp_ambient = "";
	public String car_temp_cabin = "";
	public DataStale stale_car_temps = DataStale.NoValue;
	public DataStale stale_ambient_temp = DataStale.NoValue;

	// Firmware
	public String car_firmware = "";
	public String car_hardware = "";
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
	public float car_temp_cabin_raw = 0;
	public float car_tripmeter_raw = 0;
	public float car_odometer_raw = 0;
	public float car_speed_raw = 0;
	public long car_parking_timer_raw = 0;
	public int car_stale_car_temps_raw = -1;
	public float car_stale_ambient_temp_raw = -1;

	// Car Firmware Message "F"
	public int car_gsm_signal_raw = 0;
	public int car_servicerange = -1;
	public int car_servicetime = -1;

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
	public double car_charge_power_kw_raw = 0;
	public String car_charge_power_kw = "";
	public double car_battery_voltage = 0;
	public double car_battery_current_raw = 0;
	public double car_battery_rangespeed_raw = 0;
	public String car_battery_rangespeed = "";
	public float car_soh = 0;
	public float car_charge_power_input_kw_raw = 0;
	public String car_charge_power_input_kw = "";
	public float car_charger_efficiency = 0;
	public double car_charge_power_loss_kw_raw = 0;
	public String car_charge_power_loss_kw = "";

	// Car Update Time Message "T"
	public long car_lastupdate_raw = 0;

	// Car Location Message "L"
	public int car_gpslock_raw = -1;
	public int car_stale_gps_raw = -1;
	public float car_inv_power_motor_kw = 0;
	public float car_inv_efficiency = 0;

	// Car TPMS Message "W" (obsolete)
	public double car_tpms_fl_p_raw = 0.0D;
	public double car_tpms_fl_t_raw = 0.0D;
	public double car_tpms_fr_p_raw = 0.0D;
	public double car_tpms_fr_t_raw = 0.0D;
	public double car_tpms_rl_p_raw = 0.0D;
	public double car_tpms_rl_t_raw = 0.0D;
	public double car_tpms_rr_p_raw = 0.0D;
	public double car_tpms_rr_t_raw = 0.0D;
	public int car_stale_tpms_raw = -1;

	// Car TPMS Message "Y"
	public String[] car_tpms_wheelname = null;
	public double[] car_tpms_pressure_raw = null;
	public String[] car_tpms_pressure = null;
	public DataStale stale_tpms_pressure = DataStale.NoValue;
	public double[] car_tpms_temp_raw = null;
	public String[] car_tpms_temp = null;
	public DataStale stale_tpms_temp = DataStale.NoValue;
	public double[] car_tpms_health_raw = null;
	public String[] car_tpms_health = null;
	public DataStale stale_tpms_health = DataStale.NoValue;
	public int[] car_tpms_alert_raw = null;
	public String[] car_tpms_alert = null;
	public DataStale stale_tpms_alert = DataStale.NoValue;

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


	// ================================================================================
	// END OF PUBLIC DATA
	// ================================================================================

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
	 * Calculate derived variables including prefs dependencies
	 */
	public void recalc() {
		boolean showTpmsBar = (appPrefes.getData("showtpmsbar").equals("on"));
		boolean showFahrenheit = (appPrefes.getData("showfahrenheit").equals("on"));

		if (showFahrenheit) {
			car_temp_pem = String.format("%.1f\u00B0F", (car_temp_pem_raw * (9.0 / 5.0)) + 32.0);
			car_temp_motor = String.format("%.1f\u00B0F", (car_temp_motor_raw * (9.0 / 5.0)) + 32.0);
			car_temp_battery = String.format("%.1f\u00B0F", (car_temp_battery_raw * (9.0 / 5.0)) + 32.0);
			car_temp_ambient = String.format("%.1f\u00B0F", (car_temp_ambient_raw * (9.0 / 5.0)) + 32.0);
			car_temp_charger = String.format("%.1f\u00B0F", (car_temp_charger_raw * (9.0 / 5.0)) + 32.0);
			car_temp_cabin = String.format("%.1f\u00B0F", (car_temp_cabin_raw * (9.0 / 5.0)) + 32.0);
		} else {
			car_temp_pem = String.format("%.1f\u00B0C", car_temp_pem_raw);
			car_temp_motor = String.format("%.1f\u00B0C", car_temp_motor_raw);
			car_temp_battery = String.format("%.1f\u00B0C", car_temp_battery_raw);
			car_temp_ambient = String.format("%.1f\u00B0C", car_temp_ambient_raw);
			car_temp_charger = String.format("%.1f\u00B0C", car_temp_charger_raw);
			car_temp_cabin = String.format("%.1f\u00B0C", car_temp_cabin_raw);
		}

		if (showTpmsBar) {
			car_tpms_fl_p = String.format("%.1f%s", car_tpms_fl_p_raw / 14.504, "bar");
			car_tpms_fr_p = String.format("%.1f%s", car_tpms_fr_p_raw / 14.504, "bar");
			car_tpms_rl_p = String.format("%.1f%s", car_tpms_rl_p_raw / 14.504, "bar");
			car_tpms_rr_p = String.format("%.1f%s", car_tpms_rr_p_raw / 14.504, "bar");
		} else {
			car_tpms_fl_p = String.format("%.1f%s", car_tpms_fl_p_raw, "psi");
			car_tpms_fr_p = String.format("%.1f%s", car_tpms_fr_p_raw, "psi");
			car_tpms_rl_p = String.format("%.1f%s", car_tpms_rl_p_raw, "psi");
			car_tpms_rr_p = String.format("%.1f%s", car_tpms_rr_p_raw, "psi");
		}

		if (showFahrenheit) {
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

		String sval;
		double dval;

		if (car_tpms_pressure_raw != null && car_tpms_pressure != null &&
				car_tpms_pressure_raw.length == car_tpms_pressure.length) {
			for (int j = 0; j < car_tpms_pressure_raw.length; j++) {
				dval = car_tpms_pressure_raw[j];
				if (showTpmsBar)
					sval = String.format("%.1f%s", Math.floor(dval / 10) / 10, "bar");
				else
					sval = String.format("%.1f%s", Math.floor(dval * 1.450377) / 10, "psi");
				car_tpms_pressure[j] = sval;
			}
		}

		if (car_tpms_temp_raw != null && car_tpms_temp != null &&
				car_tpms_temp_raw.length == car_tpms_temp.length) {
			for (int j = 0; j < car_tpms_temp_raw.length; j++) {
				dval = car_tpms_temp_raw[j];
				if (showFahrenheit)
					sval = String.format("%.0f\u00B0F", Math.ceil((dval * (9.0 / 5.0)) + 32.0));
				else
					sval = String.format("%.0f\u00B0C", Math.ceil(dval));
				car_tpms_temp[j] = sval;
			}
		}
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
			if (dataParts.length >= 14) {
				car_inv_power_motor_kw = Float.parseFloat(dataParts[12]);
				car_inv_efficiency = Float.parseFloat(dataParts[13]);
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
				car_distance_units = (car_distance_units_raw.startsWith("M")) ? "mi" : "km";
				car_speed_units = (car_distance_units_raw.startsWith("M"))
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
				car_charge_kwhconsumed = Float.parseFloat(dataParts[11]) / 10f;
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
				car_charge_power_kw_raw = Double.parseDouble(dataParts[31]);
				car_charge_power_kw = String.format("%.1fkW", car_charge_power_kw_raw);
				car_battery_voltage = Double.parseDouble(dataParts[32]);
			}
			if (dataParts.length >= 34) {
				car_soh = Float.parseFloat(dataParts[33]);
			}
			if (dataParts.length >= 36) {
				car_charge_power_input_kw_raw = Float.parseFloat(dataParts[34]);
				car_charge_power_input_kw = String.format("%.1fkW", car_charge_power_input_kw_raw);
				if (car_charge_power_kw_raw != 0) {
					car_charge_power_loss_kw_raw = car_charge_power_input_kw_raw - car_charge_power_kw_raw;
					car_charge_power_loss_kw = String.format("➘ %.1fkW", car_charge_power_loss_kw_raw);
				} else {
					car_charge_power_loss_kw_raw = 0;
					car_charge_power_loss_kw = "";
				}
				car_charger_efficiency = Float.parseFloat(dataParts[35]);
			}
			if (dataParts.length >= 38) {
				car_battery_current_raw = Double.parseDouble(dataParts[36]);
				car_battery_rangespeed_raw = Double.parseDouble(dataParts[37]);
				if (car_battery_rangespeed_raw != 0) {
					car_battery_rangespeed = String.format("%.1f%s",
							car_battery_rangespeed_raw, car_speed_units);
				} else {
					car_battery_rangespeed = "";
				}
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
				car_auxiliary_12v = ((dataField & 0x20) == 0x20);
				car_rearleftdoor_open = ((dataField & 0x1) == 0x1);
				car_rearrightdoor_open = ((dataField & 0x2) == 0x2);
				car_hvac_on = ((dataField & 0x80) == 0x80);
			}
			if (dataParts.length >= 19) {
				car_temp_charger_raw = Float.parseFloat(dataParts[18]);
			}
			if (dataParts.length >= 20) {
				car_12v_current = Double.parseDouble(dataParts[19]);
			}
			if (dataParts.length >= 21) {
				car_temp_cabin_raw = Float.parseFloat(dataParts[20]);
			}

		} catch(Exception e) {
			Log.e(TAG, "processEnvironment: ERROR", e);
			return false;
		}

		// Car specific handling:
		if (car_type.equals("RT")) {
			stale_ambient_temp = DataStale.NoValue;
		}

		recalc();
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
				car_gsm_signal = String.format("%d%s", car_gsm_dbm, " dBm");
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
			if (dataParts.length >= 8) {
				if (!dataParts[6].equals("")) {
					car_servicerange = Integer.parseInt(dataParts[6]);
				} else {
					car_servicerange = -1;
				}
				if (!dataParts[7].equals("")) {
					car_servicetime = Integer.parseInt(dataParts[7]);
				} else {
					car_servicetime = -1;
				}
			}
			if (dataParts.length >= 9) {
				car_hardware = dataParts[8];
			}

		} catch(Exception e) {
			Log.e(TAG, "processFirmware: ERROR", e);
			return false;
		}

		return true;
	}


	/**
	 * Process old TPMS message ("W")
	 */
	public boolean processOldTPMS(String msgdata) {
		Init();
		Log.d(TAG, "processOldTPMS: " + msgdata);

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
				car_stale_tpms_raw = Integer.parseInt(dataParts[8]);
				if (car_stale_tpms_raw < 0)
					stale_tpms = DataStale.NoValue;
				else if (car_stale_tpms_raw == 0)
					stale_tpms = DataStale.Stale;
				else
					stale_tpms = DataStale.Good;
			}

		} catch(Exception e) {
			Log.e(TAG, "processOldTPMS: ERROR", e);
			return false;
		}

		// Car specific handling:
		if (car_type.equals("RT") || car_type.equals("SE")) {
			stale_tpms = DataStale.NoValue;
		}

		recalc();
		return true;
	}


	/**
	 * Process new TPMS message ("Y")
	 */
	public boolean processNewTPMS(String msgdata) {
		Init();
		Log.d(TAG, "processNewTPMS: " + msgdata);

		try {
			String[] dataParts = msgdata.split(",\\s*");
			int i = 0, j = 0;
			int cnt = 0, end = 0, valid = 0;
			double dval;
			int ival;
			String sval;

			// Process wheel names:
			if (i < dataParts.length) {
				cnt = Integer.parseInt(dataParts[i++]);
				if (car_tpms_wheelname == null || car_tpms_wheelname.length != cnt) {
					car_tpms_wheelname = new String[cnt];
				}
				for (j = 0, end = i+cnt; i < end; i++, j++) {
					car_tpms_wheelname[j] = dataParts[i];
				}
			}

			// Process pressures:
			if (i < dataParts.length) {
				cnt = Integer.parseInt(dataParts[i++]);
				if (car_tpms_pressure == null || car_tpms_pressure.length != cnt) {
					car_tpms_pressure_raw = new double[cnt];
					car_tpms_pressure = new String[cnt];
				}
				for (j = 0, end = i+cnt; i < end; i++, j++) {
					dval = Double.parseDouble(dataParts[i]);
					car_tpms_pressure_raw[j] = dval;
					car_tpms_pressure[j] = "";
				}
				valid = Integer.parseInt(dataParts[i++]);
				stale_tpms_pressure = (valid < 0) ? DataStale.NoValue
						: (valid == 0) ? DataStale.Stale : DataStale.Good;
			}

			// Process temperatures:
			if (i < dataParts.length) {
				cnt = Integer.parseInt(dataParts[i++]);
				if (car_tpms_temp == null || car_tpms_temp.length != cnt) {
					car_tpms_temp_raw = new double[cnt];
					car_tpms_temp = new String[cnt];
				}
				for (j = 0, end = i+cnt; i < end; i++, j++) {
					dval = Double.parseDouble(dataParts[i]);
					car_tpms_temp_raw[j] = dval;
					car_tpms_temp[j] = "";
				}
				valid = Integer.parseInt(dataParts[i++]);
				stale_tpms_temp = (valid < 0) ? DataStale.NoValue
						: (valid == 0) ? DataStale.Stale : DataStale.Good;
			}

			// Process health levels:
			if (i < dataParts.length) {
				cnt = Integer.parseInt(dataParts[i++]);
				if (car_tpms_health == null || car_tpms_health.length != cnt) {
					car_tpms_health_raw = new double[cnt];
					car_tpms_health = new String[cnt];
				}
				for (j = 0, end = i+cnt; i < end; i++, j++) {
					dval = Double.parseDouble(dataParts[i]);
					sval = String.format("%.0f%%", Math.floor(dval));
					car_tpms_health_raw[j] = dval;
					car_tpms_health[j] = sval;
				}
				valid = Integer.parseInt(dataParts[i++]);
				stale_tpms_health = (valid < 0) ? DataStale.NoValue
						: (valid == 0) ? DataStale.Stale : DataStale.Good;
			}

			// Process alerts:
			if (i < dataParts.length) {
				cnt = Integer.parseInt(dataParts[i++]);
				if (car_tpms_alert == null || car_tpms_alert.length != cnt) {
					car_tpms_alert_raw = new int[cnt];
					car_tpms_alert = new String[cnt];
				}
				for (j = 0, end = i+cnt; i < end; i++, j++) {
					ival = Integer.parseInt(dataParts[i]);
					if (ival > 2)
						ival = 2;
					else if (ival < 0)
						ival = 0;
					if (ival == 0)
						sval = "✔";
					else if (ival == 1)
						sval = "⛛";
					else
						sval = "⚠";
					car_tpms_alert_raw[j] = ival;
					car_tpms_alert[j] = sval;
				}
				valid = Integer.parseInt(dataParts[i++]);
				stale_tpms_alert = (valid < 0) ? DataStale.NoValue
						: (valid == 0) ? DataStale.Stale : DataStale.Good;
			}

			Log.d(TAG, "processNewTPMS: processed " + i + " parts");

		} catch(Exception e) {
			Log.e(TAG, "processNewTPMS: ERROR", e);
			return false;
		}

		// Car specific handling:
		if (car_type.equals("RT") || car_type.equals("SE")) {
			stale_tpms_pressure = DataStale.NoValue;
			stale_tpms_temp = DataStale.NoValue;
			stale_tpms_health = DataStale.NoValue;
			stale_tpms_alert = DataStale.NoValue;
		}

		recalc();
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
			b.putDouble("car_charge_power_kw", car_charge_power_kw_raw);
			b.putDouble("car_battery_current", car_battery_current_raw);
			b.putDouble("car_battery_rangespeed", car_battery_rangespeed_raw);
			b.putFloat("car_charge_kwhconsumed", car_charge_kwhconsumed);
			b.putBoolean("car_charge_timer", car_charge_timer);
			b.putFloat("car_charge_power_input_kw", car_charge_power_input_kw_raw);
			b.putFloat("car_charger_efficiency", car_charger_efficiency);
			b.putDouble("car_charge_power_loss_kw", car_charge_power_loss_kw_raw);

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

			b.putFloat("car_inv_power_motor", car_inv_power_motor_kw);
			b.putFloat("car_inv_efficiency", car_inv_efficiency);


			//
			// Environment, doors & switches (msgCode 'D')
			//

			b.putBoolean("car_frontleftdoor_open", car_frontleftdoor_open);
			b.putBoolean("car_frontrightdoor_open", car_frontrightdoor_open);
			b.putBoolean("car_rearleftdoor_open", car_rearleftdoor_open);
			b.putBoolean("car_rearrightdoor_open", car_rearrightdoor_open);
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
			b.putBoolean("car_hvac_on", car_hvac_on);

			b.putFloat("car_temp_pem", car_temp_pem_raw);
			b.putFloat("car_temp_motor", car_temp_motor_raw);
			b.putFloat("car_temp_battery", car_temp_battery_raw);
			b.putFloat("car_temp_ambient", car_temp_ambient_raw);
			b.putFloat("car_temp_charger", car_temp_charger_raw);
			b.putFloat("car_temp_cabin", car_temp_cabin_raw);

			b.putFloat("car_odometer", car_odometer_raw);

			if (car_parked_time != null) {
				b.putLong("car_parked_seconds", car_parking_timer_raw);
				b.putString("car_parked_time", isoDateTime.format(car_parked_time));
			} else {
				b.putLong("car_parked_seconds", 0);
				b.putString("car_parked_time", "");
			}

			b.putBoolean("car_charging_12v", car_charging_12v);
			b.putBoolean("car_auxiliary_12v", car_auxiliary_12v);
			b.putDouble("car_12vline_voltage", car_12vline_voltage);
			b.putDouble("car_12vline_ref", car_12vline_ref);
			b.putDouble("car_12v_current", car_12v_current);


			//
			// Firmware (msgCode 'F')
			//

			b.putString("car_firmware", car_firmware);
			b.putString("car_hardware", car_hardware);
			b.putString("car_vin", car_vin);

			b.putInt("car_gsm_dbm", car_gsm_dbm);
			b.putInt("car_gsm_bars", car_gsm_bars);
			b.putString("car_gsm_lock", car_gsmlock);

			b.putInt("car_canwrite", car_canwrite_raw);

			b.putInt("car_servicedays", car_servicetime);
			b.putInt("car_servicedist", car_servicerange);

			//
			// TPMS new flexible wheel layout (msgCode 'Y')
			//

			b.putStringArray("car_tpms_wheelname", car_tpms_wheelname);
			b.putDoubleArray("car_tpms_pressure", car_tpms_pressure_raw);
			b.putDoubleArray("car_tpms_temp", car_tpms_temp_raw);
			b.putDoubleArray("car_tpms_health", car_tpms_health_raw);
			b.putIntArray("car_tpms_alert", car_tpms_alert_raw);

			//
			// TPMS old fixed four wheel layout values
			//

			if (car_tpms_pressure_raw != null && car_tpms_pressure_raw.length >= 4 &&
					car_tpms_temp_raw != null && car_tpms_temp_raw.length >= 4) {
				// Values from msgCode 'Y':
				b.putDouble("car_tpms_fl_p", car_tpms_pressure_raw[0] * 0.1450377);
				b.putDouble("car_tpms_fl_t", car_tpms_temp_raw[0]);
				b.putDouble("car_tpms_fr_p", car_tpms_pressure_raw[1] * 0.1450377);
				b.putDouble("car_tpms_fr_t", car_tpms_temp_raw[1]);
				b.putDouble("car_tpms_rl_p", car_tpms_pressure_raw[2] * 0.1450377);
				b.putDouble("car_tpms_rl_t", car_tpms_temp_raw[2]);
				b.putDouble("car_tpms_rr_p", car_tpms_pressure_raw[3] * 0.1450377);
				b.putDouble("car_tpms_rr_t", car_tpms_temp_raw[3]);
			} else {
				// Legacy, values from msgCode 'W':
				b.putDouble("car_tpms_fl_p", car_tpms_fl_p_raw);
				b.putDouble("car_tpms_fl_t", car_tpms_fl_t_raw);
				b.putDouble("car_tpms_fr_p", car_tpms_fr_p_raw);
				b.putDouble("car_tpms_fr_t", car_tpms_fr_t_raw);
				b.putDouble("car_tpms_rl_p", car_tpms_rl_p_raw);
				b.putDouble("car_tpms_rl_t", car_tpms_rl_t_raw);
				b.putDouble("car_tpms_rr_p", car_tpms_rr_p_raw);
				b.putDouble("car_tpms_rr_t", car_tpms_rr_t_raw);
			}

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
