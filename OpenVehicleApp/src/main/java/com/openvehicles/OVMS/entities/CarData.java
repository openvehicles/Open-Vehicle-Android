package com.openvehicles.OVMS.entities;

import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
	public DataStale stale_firmware = DataStale.NoValue;
	public double car_CAC = 0.0;

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
	public int car_direction = 0;
	public int car_altitude = 0;
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
	public int car_temp_pem_raw = 0;
	public int car_temp_motor_raw = 0;
	public int car_temp_battery_raw = 0;
	public int car_temp_charger_raw = 0;
	public int car_temp_ambient_raw = 0;
	public int car_tripmeter_raw = 0;
	public int car_odometer_raw = 0;
	public int car_speed_raw = 0;
	public long car_parking_timer_raw = 0;
	public int car_stale_car_temps_raw = -1;
	public int car_stale_ambient_temp_raw = -1;

	// Car Firmware Message "F"
	public int car_gsm_signal_raw = 0;

	// Car State Message "S"
	public int car_soc_raw = 0;
	public String car_distance_units_raw = "";
	public int car_charge_linevoltage_raw = 0;
	public int car_charge_current_raw = 0;
	public String car_charge_state_s_raw = "";
	public int car_charge_state_i_raw = 0;
	public int car_charge_substate_i_raw = 0;
	public String car_mode_s_raw = "";
	public int car_charge_mode_i_raw = 0;
	public int car_range_ideal_raw = 0;
	public int car_range_estimated_raw = 0;
	public int car_charge_currentlimit_raw = 0;
	public int car_charge_duration_raw = 0;
	public int car_charge_b4byte_raw = 0;
	public int car_charge_kwhconsumed = 0;
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


	/**
	 * Default constructor
	 */
	public CarData() {
		car_command_support = new boolean[256];
	}

	/**
	 * Process capabilities message
	 *
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
			return false;
		}

		return true;
	}

	public boolean hasCommand(int cmd) {
		return (car_command_support != null) && car_command_support[cmd];
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

			b.putInt("car_soc", car_soc_raw);
			b.putDouble("car_battery_voltage", car_battery_voltage);
			b.putDouble("car_battery_cac", car_CAC);

			b.putString("car_distance_units", car_distance_units_raw);
			b.putInt("car_range_ideal", car_range_ideal_raw);
			b.putInt("car_range_ideal_max", car_max_idealrange_raw);
			b.putInt("car_range_estimated", car_range_estimated_raw);

			b.putInt("car_charge_state", car_charge_state_i_raw);
			b.putInt("car_charge_mode", car_charge_mode_i_raw);
			b.putString("car_charge_state_label", car_charge_state);
			b.putString("car_charge_mode_label", car_charge_mode);
			b.putInt("car_charge_linevoltage", car_charge_linevoltage_raw);
			b.putInt("car_charge_current", car_charge_current_raw);
			b.putInt("car_charge_currentlimit", car_charge_currentlimit_raw);
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
			b.putInt("car_direction", car_direction);
			b.putInt("car_altitude", car_altitude);
			b.putInt("car_gps_lock", car_gpslock_raw);
			b.putInt("car_gps_stale", car_stale_gps_raw);


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

			b.putInt("car_temp_pem", car_temp_pem_raw);
			b.putInt("car_temp_motor", car_temp_motor_raw);
			b.putInt("car_temp_battery", car_temp_battery_raw);
			b.putInt("car_temp_ambient", car_temp_ambient_raw);
			b.putInt("car_temp_charger", car_temp_charger_raw);

			b.putInt("car_tripmeter", car_tripmeter_raw);
			b.putInt("car_odometer", car_odometer_raw);
			b.putInt("car_speed", car_speed_raw);

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
