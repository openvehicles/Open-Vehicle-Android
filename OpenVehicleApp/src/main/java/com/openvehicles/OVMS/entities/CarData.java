package com.openvehicles.OVMS.entities;

import java.io.Serializable;
import java.util.Date;

public class CarData implements Serializable {
	private static final long serialVersionUID = 9069218298370983442L;

	public enum DataStale {
		NoValue, Stale, Good
	}

	// //////////////////////////////////////////////////////////////////////
	// Selected Vehicle

	public String sel_server = "tmc.openvehicles.com"; // ServerNameOrIP
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
	public boolean car_coolingpump_on = false;
	public boolean car_alarm_sounding = false;
	public boolean car_charge_timer = false;
	public Date car_parked_time = null;
	public DataStale stale_environment = DataStale.NoValue;

	// Temperatures
	public String car_temp_pem = "";
	public String car_temp_motor = "";
	public String car_temp_battery = "";
	public String car_temp_ambient = "";
	public DataStale stale_car_temps = DataStale.NoValue;
	public DataStale stale_ambient_temp = DataStale.NoValue;

	// Firmware
	public String car_firmware = "";
	public String car_vin = "";
	public String car_type = "";
	public boolean car_canwrite = false;
	public String car_gsmlock = "";
	public String car_gsm_signal = "";
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
	public int car_temp_ambient_raw = 0;
	public int car_tripmeter_raw = 0;
	public int car_odometer_raw = 0;
	public int car_speed_raw = 0;
	public long car_parking_timer_raw = 0;
	public int car_stale_car_temps_raw = -1;
	public int car_stale_ambient_temp_raw = -1;

	// Car Firmware Message "F"
	public int car_gps_signal_raw = 0;

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
}
