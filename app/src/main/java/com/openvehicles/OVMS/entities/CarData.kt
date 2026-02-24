package com.openvehicles.OVMS.entities

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.openvehicles.OVMS.BaseApp
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.utils.AppPrefs
import java.io.Serializable
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.collections.contains

class CarData : Serializable {

    // ================================================================================
    // START OF PUBLIC DATA
    // ================================================================================
    // NOTE:
    // 	The list of cars is now saved in JSON format, see CarsStorage.saveStoredCars(),
    //	so you can now add new fields where appropriate.
    // //////////////////////////////////////////////////////////////////////
    // Selected Vehicle
    @JvmField
    var sel_server = "" // ServerNameOrIP
    @JvmField
    var sel_tls = false // Use TLS for server connection
    @JvmField
    var sel_tls_trust_all = false // Trust all certificates for TLS
    @JvmField
    var sel_gcm_senderid = "" // GCM sender ID (empty = default OVMS ID)
    @JvmField
    var sel_vehicleid = "" // VehicleID
    @JvmField
    var sel_vehicle_label = "" // VehicleLabel
    @JvmField
    var sel_server_password = "" // NetPass
    @JvmField
    var sel_module_password = "" // RegPass
    var sel_paranoid = false // ParanoidMode
    @JvmField
    var sel_vehicle_image = "" // The vehicle image (on disk)
    @JvmField
    var car_lastupdated: Date? = null
    var server_carsconnected = 0

    // //////////////////////////////////////////////////////////////////////
    // Vehicle Sanitized Data
    // Environment
    @JvmField
    var car_frontleftdoor_open = false
    @JvmField
    var car_frontrightdoor_open = false
    @JvmField
    var car_rearleftdoor_open = false
    @JvmField
    var car_rearrightdoor_open = false
    @JvmField
    var car_chargeport_open = false
    var car_pilot_present = false
    @JvmField
    var car_charging = false
    @JvmField
    var car_charging_12v = false
    var car_auxiliary_12v = false
    var car_handbrake_on = false
    @JvmField
    var car_started = false
    @JvmField
    var car_locked = false
    @JvmField
    var car_valetmode = false
    @JvmField
    var car_headlights_on = false
    @JvmField
    var car_bonnet_open = false
    @JvmField
    var car_trunk_open = false
    var car_awake = false
    var car_alarm_sounding = false
    var car_charge_timer = false
    @JvmField
    var car_hvac_on = false
    @JvmField
    var car_parked_time: Date? = null
    var stale_environment = DataStale.NoValue

    // Temperatures
    @JvmField
    var car_temp_pem = ""
    @JvmField
    var car_temp_motor = ""
    @JvmField
    var car_temp_battery = ""
    @JvmField
    var car_temp_charger = ""
    @JvmField
    var car_temp_ambient = ""
    @JvmField
    var car_temp_cabin = ""
    @JvmField
    var stale_car_temps = DataStale.NoValue
    @JvmField
    var stale_ambient_temp = DataStale.NoValue

    // Firmware
    @JvmField
    var car_firmware = ""
    @JvmField
    var car_hardware = ""
    @JvmField
    var car_vin = ""
    @JvmField
    var car_type = ""
    var car_canwrite_raw = 0
    var car_canwrite = false
    var car_gsmlock = ""
    var car_mdm_mode = ""
    @JvmField
    var car_gsm_signal = ""
    var car_gsm_dbm = 0
    @JvmField
    var car_gsm_bars = 0
    @JvmField
    var server_firmware = ""
    @JvmField
    var car_12vline_voltage = 0.0
    @JvmField
    var car_12vline_ref = 0.0
    @JvmField
    var car_12v_current = 0.0
    var stale_firmware = DataStale.NoValue
    @JvmField
    var car_CAC = 0.0
    var car_CAC_ref = 0.0
    @JvmField
    var car_CAC_percent = 0.0

    // Status
    @JvmField
    var car_soc = ""
    @JvmField
    var car_charge_linevoltage = ""
    @JvmField
    var car_charge_current = ""
    var car_charge_voltagecurrent = ""
    @JvmField
    var car_charge_currentlimit = ""
    @JvmField
    var car_charge_mode = ""
    @JvmField
    var car_charge_state = ""
    @JvmField
    var car_range_ideal = ""
    @JvmField
    var car_range_estimated = ""
    var car_charge_time = ""
    @JvmField
    var car_distance_units = ""
    @JvmField
    var car_speed_units = ""
    var car_chargelimit_rangelimit = ""
    var car_max_idealrange = ""
    var car_charge_timestamp = ""
    var car_charge_timestamp_sec = 0
    var stale_chargetimer = DataStale.NoValue
    var stale_status = DataStale.NoValue

    // Position
    @JvmField
    var car_latitude = 0.0
    @JvmField
    var car_longitude = 0.0
    @JvmField
    var car_direction = 0.0
    var car_altitude = 0.0
    var car_speed = ""
    var car_tripmeter = ""
    var car_odometer = ""
    var car_gpslock = false
    var stale_gps = DataStale.NoValue

    // TPMS
    @JvmField
    var car_tpms_fl_p = ""
    @JvmField
    var car_tpms_fl_t = ""
    @JvmField
    var car_tpms_fr_p = ""
    @JvmField
    var car_tpms_fr_t = ""
    @JvmField
    var car_tpms_rl_p = ""
    @JvmField
    var car_tpms_rl_t = ""
    @JvmField
    var car_tpms_rr_p = ""
    @JvmField
    var car_tpms_rr_t = ""
    @JvmField
    var stale_tpms = DataStale.NoValue

    // //////////////////////////////////////////////////////////////////////
    // RAW values from the vehicle
    // Car Environment Message "D"
    var car_doors1_raw = 0
    var car_doors2_raw = 0
    var car_doors3_raw = 0
    var car_doors4_raw = 0
    var car_doors5_raw = 0
    var car_lockunlock_raw = 0
    var car_temp_pem_raw = 0f
    var car_temp_motor_raw = 0f
    var car_temp_battery_raw = 0f
    var car_temp_charger_raw = 0f
    var car_temp_ambient_raw = 0f
    var car_temp_cabin_raw = 0f
    @JvmField
    var car_tripmeter_raw = 0f
    @JvmField
    var car_odometer_raw = 0f
    @JvmField
    var car_speed_raw = 0f
    var car_parking_timer_raw: Long = 0
    var car_stale_car_temps_raw = -1
    var car_stale_ambient_temp_raw = -1f

    // Car Firmware Message "F"
    var car_gsm_signal_raw = 0
    @JvmField
    var car_servicerange = -1
    @JvmField
    var car_servicetime = -1

    // Car State Message "S"
    @JvmField
    var car_soc_raw = 0f
    @JvmField
    var car_distance_units_raw = ""
    @JvmField
    var car_charge_linevoltage_raw = 0f
    @JvmField
    var car_charge_current_raw = 0f
    @JvmField
    var car_charge_state_s_raw = ""
    @JvmField
    var car_charge_state_i_raw = 0
    @JvmField
    var car_charge_substate_i_raw = 0
    var car_mode_s_raw = ""
    @JvmField
    var car_charge_mode_i_raw = 0
    @JvmField
    var car_range_ideal_raw = 0f
    @JvmField
    var car_range_estimated_raw = 0f
    @JvmField
    var car_charge_currentlimit_raw = 0f
    var car_charge_duration_raw = 0
    var car_charge_b4byte_raw = 0
    @JvmField
    var car_charge_kwhconsumed = 0f
    var car_charge_timermode_raw = 0
    var car_charge_timerstart_raw = 0
    var car_stale_chargetimer_raw = -1
    @JvmField
    var car_chargefull_minsremaining = -1
    var car_chargelimit_minsremaining = -1
    @JvmField
    var car_chargelimit_rangelimit_raw = 0
    @JvmField
    var car_chargelimit_soclimit = 0
    var car_coolingdown = -1
    var car_cooldown_tbattery = 0
    var car_cooldown_timelimit = 0
    var car_chargeestimate = -1
    @JvmField
    var car_chargelimit_minsremaining_range = -1
    @JvmField
    var car_chargelimit_minsremaining_soc = -1
    @JvmField
    var car_max_idealrange_raw = 0
    var car_charge_plugtype = 0
    var car_charge_power_kw_raw = 0.0
    var car_charge_power_kw = ""
    var car_charge_kwh_grid = 0f
    var car_charge_kwh_grid_total = 0f
    var car_battery_capacity = 0f
    @JvmField
    var car_battery_voltage = 0.0
    var car_battery_current_raw = 0.0
    var car_battery_rangespeed_raw = 0.0
    @JvmField
    var car_battery_rangespeed = ""
    @JvmField
    var car_soh = 0f
    @JvmField
    var car_charge_power_input_kw_raw = 0f
    @JvmField
    var car_charge_power_input_kw = ""
    var car_charger_efficiency = 0f
    @JvmField
    var car_charge_power_loss_kw_raw = 0.0
    @JvmField
    var car_charge_power_loss_kw = ""

    // Car Update Time Message "T"
    var car_lastupdate_raw: Long = 0

    // Car Location Message "L"
    var car_gpslock_raw = -1
    var car_stale_gps_raw = -1
    var car_inv_power_motor_kw = 0f
    var car_inv_efficiency = 0f

    // Car TPMS Message "W" (obsolete)
    var car_tpms_fl_p_raw = 0.0
    var car_tpms_fl_t_raw = 0.0
    var car_tpms_fr_p_raw = 0.0
    var car_tpms_fr_t_raw = 0.0
    var car_tpms_rl_p_raw = 0.0
    var car_tpms_rl_t_raw = 0.0
    var car_tpms_rr_p_raw = 0.0
    var car_tpms_rr_t_raw = 0.0
    var car_stale_tpms_raw = -1

    // Car TPMS Message "Y"
    @JvmField
    var car_tpms_wheelname: Array<String?>? = null
    var car_tpms_pressure_raw: DoubleArray? = null
    @JvmField
    var car_tpms_pressure: Array<String?>? = null
    @JvmField
    var stale_tpms_pressure = DataStale.NoValue
    var car_tpms_temp_raw: DoubleArray? = null
    @JvmField
    var car_tpms_temp: Array<String?>? = null
    @JvmField
    var stale_tpms_temp = DataStale.NoValue
    var car_tpms_health_raw: DoubleArray? = null
    @JvmField
    var car_tpms_health: Array<String?>? = null
    @JvmField
    var stale_tpms_health = DataStale.NoValue
    @JvmField
    var car_tpms_alert_raw: IntArray? = null
    @JvmField
    var car_tpms_alert: Array<String?>? = null
    @JvmField
    var stale_tpms_alert = DataStale.NoValue
    
    // Car TPMS Mapping (sensor index for each wheel position)
    @JvmField
    var car_tpms_mapping_raw: IntArray? = null
    @JvmField
    var stale_tpms_mapping = DataStale.NoValue

    // Car Capabilities Message "V"
    var car_capabilities = ""
    var car_command_support: BooleanArray?

    // Car Location Message "L": Drive/Power/Energy status
    var car_drivemode = 0
    var car_power = 0.0
    @JvmField
    var car_energyused = 0f
    @JvmField
    var car_energyrecd = 0f

    // Car Gen Message "X"
    // not implemented yet?
    var car_gen_inprogress = false
    var car_gen_pilot = false
    var car_gen_voltage = 0
    var car_gen_current = 0f
    var car_gen_power = 0f
    var car_gen_efficiency = 0f
    var car_gen_type  = ""
    var car_gen_state  = ""
    var car_gen_substate  = ""
    var car_gen_mode  = ""
    var car_gen_climit = 0f
    var car_gen_limit_range = 0f
    var car_gen_limit_soc = 0
    var car_gen_kwh = 0f
    var car_gen_kwh_grid = 0f
    var car_gen_kwh_grid_total = 0f
    var car_gen_time = 0
    var car_gen_timermode = 0
    var car_gen_timerstart = 0
    var car_gen_duration_empty = 0
    var car_gen_duration_range = 0
    var car_gen_duration_soc = 0
    var car_gen_temp = 0f
    var car_gen_timestamp = ""

    //
    // Renault Twizy specific
    //
    var rt_cfg_type = 0 // CFG: 0=Twizy80, 1=Twizy45
    var rt_cfg_profile_user = 0 // CFG: user selected profile: 0=Default, 1..3=Custom
    var rt_cfg_profile_cfgmode = 0 // CFG: profile, cfgmode params were last loaded from
    var rt_cfg_unsaved = 0 // CFG: RAM profile changed & not yet saved to EEPROM
    var rt_cfg_applied = 0 // CFG: applyprofile success flag

    // ================================================================================
    // END OF PUBLIC DATA
    // ================================================================================
    //
    // Private (non serialization) data
    //
    @Transient
    private var context: Context? = null

    @Transient
    private var appPrefs: AppPrefs? = null

    @Transient
    private var decimalFormat1: DecimalFormat? = null

    /**
     * Default constructor
     */
    init {
        init()
        // Allocate memory:
        car_command_support = BooleanArray(256)
    }

    private fun init() {
        // get Application Context and Preferences access:
        if (context == null) {
            context = BaseApp.context
        }
        if (appPrefs == null) {
            appPrefs = AppPrefs(context!!, "ovms")
        }
        if (decimalFormat1 == null) {
            decimalFormat1 = DecimalFormat("0.#")
        }
    }

    /**
     * Calculate derived variables including prefs dependencies
     */
    fun recalc() {
        val showTpmsBar = appPrefs!!.getData("showtpmsbar") == "on"
        val showFahrenheit = appPrefs!!.getData("showfahrenheit") == "on"
        if (showFahrenheit) {
            car_temp_pem = String.format("%.1f\u00B0F", car_temp_pem_raw * (9.0 / 5.0) + 32.0)
            car_temp_motor = String.format("%.1f\u00B0F", car_temp_motor_raw * (9.0 / 5.0) + 32.0)
            car_temp_battery =
                String.format("%.1f\u00B0F", car_temp_battery_raw * (9.0 / 5.0) + 32.0)
            car_temp_ambient =
                String.format("%.1f\u00B0F", car_temp_ambient_raw * (9.0 / 5.0) + 32.0)
            car_temp_charger =
                String.format("%.1f\u00B0F", car_temp_charger_raw * (9.0 / 5.0) + 32.0)
            car_temp_cabin = String.format("%.1f\u00B0F", car_temp_cabin_raw * (9.0 / 5.0) + 32.0)
        } else {
            car_temp_pem = String.format("%.1f\u00B0C", car_temp_pem_raw)
            car_temp_motor = String.format("%.1f\u00B0C", car_temp_motor_raw)
            car_temp_battery = String.format("%.1f\u00B0C", car_temp_battery_raw)
            car_temp_ambient = String.format("%.1f\u00B0C", car_temp_ambient_raw)
            car_temp_charger = String.format("%.1f\u00B0C", car_temp_charger_raw)
            car_temp_cabin = String.format("%.1f\u00B0C", car_temp_cabin_raw)
        }
        if (showTpmsBar) {
            car_tpms_fl_p = String.format("%.1f%s", car_tpms_fl_p_raw / 14.504, "bar")
            car_tpms_fr_p = String.format("%.1f%s", car_tpms_fr_p_raw / 14.504, "bar")
            car_tpms_rl_p = String.format("%.1f%s", car_tpms_rl_p_raw / 14.504, "bar")
            car_tpms_rr_p = String.format("%.1f%s", car_tpms_rr_p_raw / 14.504, "bar")
        } else {
            car_tpms_fl_p = String.format("%.1f%s", car_tpms_fl_p_raw, "psi")
            car_tpms_fr_p = String.format("%.1f%s", car_tpms_fr_p_raw, "psi")
            car_tpms_rl_p = String.format("%.1f%s", car_tpms_rl_p_raw, "psi")
            car_tpms_rr_p = String.format("%.1f%s", car_tpms_rr_p_raw, "psi")
        }
        if (showFahrenheit) {
            car_tpms_fl_t =
                String.format("%.0f%s", car_tpms_fl_t_raw * (9.0 / 5.0) + 32.0, "\u00B0F")
            car_tpms_fr_t =
                String.format("%.0f%s", car_tpms_fr_t_raw * (9.0 / 5.0) + 32.0, "\u00B0F")
            car_tpms_rl_t =
                String.format("%.0f%s", car_tpms_rl_t_raw * (9.0 / 5.0) + 32.0, "\u00B0F")
            car_tpms_rr_t =
                String.format("%.0f%s", car_tpms_rr_t_raw * (9.0 / 5.0) + 32.0, "\u00B0F")
        } else {
            car_tpms_fl_t = String.format("%.0f%s", car_tpms_fl_t_raw, "\u00B0C")
            car_tpms_fr_t = String.format("%.0f%s", car_tpms_fr_t_raw, "\u00B0C")
            car_tpms_rl_t = String.format("%.0f%s", car_tpms_rl_t_raw, "\u00B0C")
            car_tpms_rr_t = String.format("%.0f%s", car_tpms_rr_t_raw, "\u00B0C")
        }
        var sval: String
        var dval: Double
        if (car_tpms_pressure_raw != null && car_tpms_pressure != null && car_tpms_pressure_raw!!.size == car_tpms_pressure!!.size) {
            for (j in car_tpms_pressure_raw!!.indices) {
                dval = car_tpms_pressure_raw!![j]
                sval = if (showTpmsBar) String.format(
                    "%.1f%s",
                    Math.floor(dval / 10) / 10,
                    "bar"
                ) else String.format("%.1f%s", Math.floor(dval * 1.450377) / 10, "psi")
                car_tpms_pressure!![j] = sval
            }
        }
        if (car_tpms_temp_raw != null && car_tpms_temp != null && car_tpms_temp_raw!!.size == car_tpms_temp!!.size) {
            for (j in car_tpms_temp_raw!!.indices) {
                dval = car_tpms_temp_raw!![j]
                sval = if (showFahrenheit) String.format(
                    "%.0f\u00B0F",
                    Math.ceil(dval * (9.0 / 5.0) + 32.0)
                ) else String.format("%.0f\u00B0C", Math.ceil(dval))
                car_tpms_temp!![j] = sval
            }
        }
    }

    /**
     * Process capabilities message ("V")
     */
    fun processCapabilities(msgdata: String): Boolean {
        Log.d(TAG, "processCapabilities: $msgdata")
        car_capabilities = msgdata
        car_command_support = BooleanArray(256)

        // msgdata format: C1,C3-6,...
        // translate to bool array:
        try {
            val parts =
                msgdata.split(",\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var i: Int
            var start: Int
            var end: Int
            for (part in parts) {
                // Command?
                if (part.startsWith("C")) {
                    val caps =
                        part.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    start = caps[0].substring(1).toInt()
                    end = if (caps.size > 1) {
                        caps[1].toInt()
                    } else {
                        start
                    }
                    i = start
                    while (i <= end) {
                        car_command_support!![i] = true
                        i++
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "processCapabilities: ERROR", e)
            return false
        }
        return true
    }

    fun hasCommand(cmd: Int): Boolean {
        return car_command_support != null && car_command_support!![cmd]
    }

    /**
     * Process location message ("L")
     */
    fun processLocation(msgdata: String): Boolean {
        Log.d(TAG, "processLocation: $msgdata")
        try {
            val dataParts =
                msgdata.split(",\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (dataParts.size >= 2) {
                car_latitude = dataParts[0].toDouble()
                car_longitude = dataParts[1].toDouble()
            }
            if (dataParts.size >= 6) {
                car_direction = dataParts[2].toDouble()
                car_altitude = dataParts[3].toDouble()
                car_gpslock_raw = dataParts[4].toInt()
                car_gpslock = car_gpslock_raw > 0
                car_stale_gps_raw = dataParts[5].toInt()
                stale_gps =
                    if (car_stale_gps_raw < 0) DataStale.NoValue else if (car_stale_gps_raw == 0) DataStale.Stale else DataStale.Good
            }
            if (dataParts.size >= 8) {
                car_speed_raw = dataParts[6].toFloat()
                car_speed = String.format(
                    "%s%s",
                    decimalFormat1!!.format(car_speed_raw.toDouble()),
                    car_speed_units
                )
                car_tripmeter_raw = dataParts[7].toFloat()
                car_tripmeter = String.format("%.1f%s", car_tripmeter_raw / 10, car_distance_units)
            }
            if (dataParts.size >= 12) {
                car_drivemode = dataParts[8].toInt(16)
                if ("RT" == car_type) {
                    rt_cfg_type = car_drivemode and 0x01
                    rt_cfg_profile_user = car_drivemode and 0x06 shr 1
                    rt_cfg_profile_cfgmode = car_drivemode and 0x18 shr 3
                    rt_cfg_unsaved = car_drivemode and 0x20 shr 5
                    rt_cfg_applied = car_drivemode and 0x80 shr 7
                }
                car_power = dataParts[9].toDouble()
                car_energyused = dataParts[10].toFloat()
                car_energyrecd = dataParts[11].toFloat()
            }
            if (dataParts.size >= 14) {
                car_inv_power_motor_kw = dataParts[12].toFloat()
                car_inv_efficiency = dataParts[13].toFloat()
            }
        } catch (e: Exception) {
            Log.e(TAG, "processLocation: ERROR", e)
            return false
        }
        return true
    }

    /**
     * Process status message ("S")
     */
    fun processStatus(msgdata: String): Boolean {
        init()
        Log.d(TAG, "processStatus: $msgdata")
        try {
            val dataParts =
                msgdata.split(",\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (dataParts.size >= 8) {
                Log.v(TAG, "S MSG Validated")
                car_soc_raw = dataParts[0].toFloat()
                car_soc = String.format("%.0f%%", car_soc_raw)
                car_distance_units_raw = dataParts[1]
                car_distance_units = if (car_distance_units_raw.startsWith("M")) "mi" else "km"
                car_speed_units =
                    if (car_distance_units_raw.startsWith("M")) context!!.getText(R.string.mph)
                        .toString() else context!!.getText(R.string.kph).toString()
                car_charge_linevoltage_raw = dataParts[2].toFloat()
                car_charge_linevoltage = String.format("%.0f%s", car_charge_linevoltage_raw, "V")
                car_charge_current_raw = dataParts[3].toFloat()
                car_charge_current = String.format("%.0f%s", car_charge_current_raw, "A")
                car_charge_voltagecurrent = String.format(
                    "%.0f%s %.0f%s",
                    car_charge_linevoltage_raw, "V",
                    car_charge_current_raw, "A"
                )
                car_charge_state_s_raw = dataParts[4]
                car_charge_state = car_charge_state_s_raw
                car_mode_s_raw = dataParts[5]
                car_charge_mode = car_mode_s_raw
                car_range_ideal_raw = dataParts[6].toFloat()
                car_range_ideal = String.format("%.0f%s", car_range_ideal_raw, car_distance_units)
                car_range_estimated_raw = dataParts[7].toFloat()
                car_range_estimated =
                    String.format("%.0f%s", car_range_estimated_raw, car_distance_units)
                stale_status = DataStale.Good
            }
            if (dataParts.size >= 15) {
                car_charge_currentlimit_raw = dataParts[8].toFloat()
                car_charge_currentlimit = String.format("%.0f%s", car_charge_currentlimit_raw, "A")
                car_charge_duration_raw = dataParts[9].toInt()
                car_charge_b4byte_raw = dataParts[10].toInt()
                car_charge_kwhconsumed = dataParts[11].toFloat() / 10f
                car_charge_substate_i_raw = dataParts[12].toInt()
                car_charge_state_i_raw = dataParts[13].toInt()
                car_charge_mode_i_raw = dataParts[14].toInt()
            }
            if (dataParts.size >= 18) {
                car_charge_timermode_raw = dataParts[15].toInt()
                car_charge_timer = car_charge_timermode_raw > 0
                car_charge_timerstart_raw = dataParts[16].toInt()
                car_charge_time = "" // TODO: Implement later
                car_stale_chargetimer_raw = dataParts[17].toInt()
                stale_chargetimer =
                    if (car_stale_chargetimer_raw < 0) DataStale.NoValue else if (car_stale_chargetimer_raw == 0) DataStale.Stale else DataStale.Good
            }
            if (dataParts.size >= 19) {
                car_CAC = dataParts[18].toDouble()
                if ("RT" == car_type) {
                    car_CAC_ref = 108.0
                    car_CAC_percent = car_CAC / car_CAC_ref * 100
                }
            }
            if (dataParts.size >= 27) {
                car_chargefull_minsremaining = dataParts[19].toInt()
                car_chargelimit_minsremaining = dataParts[20].toInt()
                car_chargelimit_rangelimit_raw = dataParts[21].toInt()
                car_chargelimit_rangelimit = String.format(
                    "%d%s",
                    car_chargelimit_rangelimit_raw, car_distance_units
                )
                car_chargelimit_soclimit = dataParts[22].toInt()
                car_coolingdown = dataParts[23].toInt()
                car_cooldown_tbattery = dataParts[24].toInt()
                car_cooldown_timelimit = dataParts[25].toInt()
                car_chargeestimate = dataParts[26].toInt()
            }
            if (dataParts.size >= 30) {
                car_chargelimit_minsremaining_range = dataParts[27].toInt()
                car_chargelimit_minsremaining_soc = dataParts[28].toInt()
                car_max_idealrange_raw = dataParts[29].toInt()
                car_max_idealrange = String.format(
                    "%d%s",
                    car_max_idealrange_raw, car_distance_units
                )
            }
            if (dataParts.size >= 33) {
                car_charge_plugtype = dataParts[30].toInt()
                car_charge_power_kw_raw = dataParts[31].toDouble()
                car_charge_power_kw = String.format("%.1fkW", car_charge_power_kw_raw)
                car_battery_voltage = dataParts[32].toDouble()
            }
            if (dataParts.size >= 34) {
                car_soh = dataParts[33].toFloat()
            }
            if (dataParts.size >= 36) {
                car_charge_power_input_kw_raw = dataParts[34].toFloat()
                car_charge_power_input_kw = String.format("%.1fkW", car_charge_power_input_kw_raw)
                if (car_charge_power_kw_raw != 0.0) {
                    car_charge_power_loss_kw_raw =
                        car_charge_power_input_kw_raw - car_charge_power_kw_raw
                    car_charge_power_loss_kw =
                        String.format("➘ %.1fkW", car_charge_power_loss_kw_raw)
                } else {
                    car_charge_power_loss_kw_raw = 0.0
                    car_charge_power_loss_kw = ""
                }
                car_charger_efficiency = dataParts[35].toFloat()
            }
            if (dataParts.size >= 38) {
                car_battery_current_raw = dataParts[36].toDouble()
                car_battery_rangespeed_raw = dataParts[37].toDouble()
                car_battery_rangespeed = if (car_battery_rangespeed_raw != 0.0) {
                    String.format(
                        "%.1f%s",
                        car_battery_rangespeed_raw, car_speed_units
                    )
                } else {
                    ""
                }
            }
            if (dataParts.size >= 42) {
                car_charge_kwh_grid = dataParts[38].toFloat()
                car_charge_kwh_grid_total = dataParts[39].toFloat()
                car_battery_capacity = dataParts[40].toFloat()

                val dateFormat = appPrefs!!.getData("showfahrenheit", "off") == "on"
                car_charge_timestamp_sec = dataParts[41].toInt()
                if (car_charge_timestamp_sec <= 0) {
                    car_charge_timestamp = ""
                    car_charge_timestamp_sec = 0
                } else {
                    val timestamp_formater = if (!dateFormat) {
                        SimpleDateFormat("dd.MM.yy  HH:mm", Locale.getDefault())
                    } else {
                        SimpleDateFormat("MM/dd/yy  HH:mm", Locale.getDefault())
                    }
                    val date = Date(car_charge_timestamp_sec.toLong() * 1000)
                    car_charge_timestamp = timestamp_formater.format(date)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "processStatus: ERROR", e)
            return false
        }
        return true
    }

    /**
     * Process doors & environment message ("D")
     */
    fun processEnvironment(msgdata: String): Boolean {
        init()
        Log.d(TAG, "processEnvironment: $msgdata")
        try {
            val dataParts =
                msgdata.split(",\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var dataField: Int
            if (dataParts.size >= 9) {
                dataField = dataParts[0].toInt()
                car_doors1_raw = dataField
                car_frontleftdoor_open = dataField and 0x1 == 0x1
                car_frontrightdoor_open = dataField and 0x2 == 0x2
                car_chargeport_open = dataField and 0x4 == 0x4
                car_pilot_present = dataField and 0x8 == 0x8
                car_charging = dataField and 0x10 == 0x10
                // bit 5 is always 1
                car_handbrake_on = dataField and 0x40 == 0x40
                car_started = dataField and 0x80 == 0x80
                dataField = dataParts[1].toInt()
                car_doors2_raw = dataField
                car_bonnet_open = dataField and 0x40 == 0x40
                car_trunk_open = dataField and 0x80 == 0x80
                car_headlights_on = dataField and 0x20 == 0x20
                car_valetmode = dataField and 0x10 == 0x10
                car_locked = dataField and 0x08 == 0x08
                car_lockunlock_raw = dataParts[2].toInt()
                car_temp_pem_raw = dataParts[3].toFloat()
                car_temp_motor_raw = dataParts[4].toFloat()
                car_temp_battery_raw = dataParts[5].toFloat()
                car_tripmeter_raw = dataParts[6].toFloat()
                car_tripmeter = String.format("%.1f%s", car_tripmeter_raw / 10, car_distance_units)
                car_odometer_raw = dataParts[7].toFloat()
                car_odometer = String.format("%.1f%s", car_odometer_raw / 10, car_distance_units)
                car_speed_raw = dataParts[8].toFloat()
                car_speed = String.format("%.1f%s", car_speed_raw, car_speed_units)
                stale_environment = DataStale.Good
            }
            if (dataParts.size >= 14) {
                car_parking_timer_raw = dataParts[9].toLong()
                car_parked_time = Date(Date().time - car_parking_timer_raw * 1000)
                car_temp_ambient_raw = dataParts[10].toFloat()
                dataField = dataParts[11].toInt()
                car_doors3_raw = dataField
                car_awake = dataField and 0x02 == 0x02
                car_stale_car_temps_raw = dataParts[12].toInt()
                stale_car_temps =
                    if (car_stale_car_temps_raw < 0) DataStale.NoValue else if (car_stale_car_temps_raw == 0) DataStale.Stale else DataStale.Good
                car_stale_ambient_temp_raw = dataParts[13].toFloat()
                stale_ambient_temp =
                    if (car_stale_ambient_temp_raw < 0) DataStale.NoValue else if (car_stale_ambient_temp_raw == 0f) DataStale.Stale else DataStale.Good
            }
            if (dataParts.size >= 16) {
                car_12vline_voltage = dataParts[14].toDouble()
                dataField = dataParts[15].toInt()
                car_doors4_raw = dataField
                car_alarm_sounding = dataField and 0x02 == 0x02
            }
            if (dataParts.size >= 18) {
                car_12vline_ref = dataParts[16].toDouble()
                dataField = dataParts[17].toInt()
                car_doors5_raw = dataField
                car_charging_12v = dataField and 0x10 == 0x10
                car_auxiliary_12v = dataField and 0x20 == 0x20
                car_rearleftdoor_open = dataField and 0x1 == 0x1
                car_rearrightdoor_open = dataField and 0x2 == 0x2
                car_hvac_on = dataField and 0x80 == 0x80
            }
            if (dataParts.size >= 19) {
                car_temp_charger_raw = dataParts[18].toFloat()
            }
            if (dataParts.size >= 20) {
                car_12v_current = dataParts[19].toDouble()
            }
            if (dataParts.size >= 21) {
                car_temp_cabin_raw = dataParts[20].toFloat()
            }
        } catch (e: Exception) {
            Log.e(TAG, "processEnvironment: ERROR", e)
            return false
        }

        // Car specific handling:
        if (car_type == "RT") {
            stale_ambient_temp = DataStale.NoValue
        }
        recalc()
        return true
    }

    /**
     * Process VIN and firmware message ("F")
     */
    fun processFirmware(msgdata: String): Boolean {
        Log.d(TAG, "processFirmware: $msgdata")
        try {
            val dataParts =
                msgdata.split(",\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (dataParts.size >= 3) {
                car_firmware = dataParts[0]
                car_vin = dataParts[1]
                car_gsm_signal_raw = dataParts[2].toInt()
                car_gsm_dbm = 0
                if (car_gsm_signal_raw <= 31) car_gsm_dbm = -113 + car_gsm_signal_raw * 2
                car_gsm_signal = String.format("%d%s", car_gsm_dbm, " dBm")
                car_gsm_bars =
                    if (car_gsm_dbm < -121 || car_gsm_dbm >= 0) 0 else if (car_gsm_dbm < -107) 1 else if (car_gsm_dbm < -98) 2 else if (car_gsm_dbm < -87) 3 else if (car_gsm_dbm < -76) 4 else 5
                stale_firmware = DataStale.Good
            }
            if (dataParts.size >= 5) {
                car_canwrite_raw = dataParts[3].toInt()
                car_canwrite = car_canwrite_raw > 0
                car_type = dataParts[4]
            }
            if (dataParts.size >= 6) {
                car_gsmlock = dataParts[5]
            }
            if (dataParts.size >= 8) {
                car_servicerange = if (dataParts[6] != "") {
                    dataParts[6].toInt()
                } else {
                    -1
                }
                car_servicetime = if (dataParts[7] != "") {
                    dataParts[7].toInt()
                } else {
                    -1
                }
            }
            if (dataParts.size >= 9) {
                car_hardware = dataParts[8]
            }
            if (dataParts.size >= 10) {
                car_mdm_mode = dataParts[9].split(";")[0].toString()
            }
        } catch (e: Exception) {
            Log.e(TAG, "processFirmware: ERROR", e)
            return false
        }
        return true
    }

    /**
     * Process old TPMS message ("W")
     */
    fun processOldTPMS(msgdata: String): Boolean {
        init()
        Log.d(TAG, "processOldTPMS: $msgdata")
        try {
            val dataParts =
                msgdata.split(",\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (dataParts.size >= 9) {
                car_tpms_fr_p_raw = dataParts[0].toDouble()
                car_tpms_fr_t_raw = dataParts[1].toDouble()
                car_tpms_rr_p_raw = dataParts[2].toDouble()
                car_tpms_rr_t_raw = dataParts[3].toDouble()
                car_tpms_fl_p_raw = dataParts[4].toDouble()
                car_tpms_fl_t_raw = dataParts[5].toDouble()
                car_tpms_rl_p_raw = dataParts[6].toDouble()
                car_tpms_rl_t_raw = dataParts[7].toDouble()
                car_stale_tpms_raw = dataParts[8].toInt()
                stale_tpms =
                    if (car_stale_tpms_raw < 0) DataStale.NoValue else if (car_stale_tpms_raw == 0) DataStale.Stale else DataStale.Good
            }
        } catch (e: Exception) {
            Log.e(TAG, "processOldTPMS: ERROR", e)
            return false
        }

        // Car specific handling:
        if (car_type == "RT" || car_type == "SE") {
            stale_tpms = DataStale.NoValue
        }
        recalc()
        return true
    }

    /**
     * Process new TPMS message ("Y")
     */
    fun processNewTPMS(msgdata: String): Boolean {
        init()
        Log.d(TAG, "processNewTPMS: $msgdata")
        try {
            val dataParts =
                msgdata.split(",\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var i = 0
            var j = 0
            var cnt = 0
            var end = 0
            var valid = 0
            var dval: Double
            var ival: Int
            var sval: String

            // Process wheel names:
            if (i < dataParts.size) {
                cnt = dataParts[i++].toInt()
                if (car_tpms_wheelname == null || car_tpms_wheelname!!.size != cnt) {
                    car_tpms_wheelname = arrayOfNulls(cnt)
                }
                j = 0
                end = i + cnt
                while (i < end) {
                    car_tpms_wheelname!![j] = dataParts[i]
                    i++
                    j++
                }
            }

            // Process pressures:
            if (i < dataParts.size) {
                cnt = dataParts[i++].toInt()
                if (car_tpms_pressure == null || car_tpms_pressure!!.size != cnt) {
                    car_tpms_pressure_raw = DoubleArray(cnt)
                    car_tpms_pressure = arrayOfNulls(cnt)
                }
                j = 0
                end = i + cnt
                while (i < end) {
                    dval = dataParts[i].toDouble()
                    car_tpms_pressure_raw!![j] = dval
                    car_tpms_pressure!![j] = ""
                    i++
                    j++
                }
                valid = dataParts[i++].toInt()
                stale_tpms_pressure =
                    if (valid < 0) DataStale.NoValue else if (valid == 0) DataStale.Stale else DataStale.Good
            }

            // Process temperatures:
            if (i < dataParts.size) {
                cnt = dataParts[i++].toInt()
                if (car_tpms_temp == null || car_tpms_temp!!.size != cnt) {
                    car_tpms_temp_raw = DoubleArray(cnt)
                    car_tpms_temp = arrayOfNulls(cnt)
                }
                j = 0
                end = i + cnt
                while (i < end) {
                    dval = dataParts[i].toDouble()
                    car_tpms_temp_raw!![j] = dval
                    car_tpms_temp!![j] = ""
                    i++
                    j++
                }
                valid = dataParts[i++].toInt()
                stale_tpms_temp =
                    if (valid < 0) DataStale.NoValue else if (valid == 0) DataStale.Stale else DataStale.Good
            }

            // Process health levels:
            if (i < dataParts.size) {
                cnt = dataParts[i++].toInt()
                if (car_tpms_health == null || car_tpms_health!!.size != cnt) {
                    car_tpms_health_raw = DoubleArray(cnt)
                    car_tpms_health = arrayOfNulls(cnt)
                }
                j = 0
                end = i + cnt
                while (i < end) {
                    dval = dataParts[i].toDouble()
                    sval = String.format("%.0f%%", Math.floor(dval))
                    car_tpms_health_raw!![j] = dval
                    car_tpms_health!![j] = sval
                    i++
                    j++
                }
                valid = dataParts[i++].toInt()
                stale_tpms_health =
                    if (valid < 0) DataStale.NoValue else if (valid == 0) DataStale.Stale else DataStale.Good
            }

            // Process alerts:
            if (i < dataParts.size) {
                cnt = dataParts[i++].toInt()
                if (car_tpms_alert == null || car_tpms_alert!!.size != cnt) {
                    car_tpms_alert_raw = IntArray(cnt)
                    car_tpms_alert = arrayOfNulls(cnt)
                }
                j = 0
                end = i + cnt
                while (i < end) {
                    ival = dataParts[i].toInt()
                    if (ival > 2) ival = 2 else if (ival < 0) ival = 0
                    sval = if (ival == 0) "✔" else if (ival == 1) "⛛" else "⚠"
                    car_tpms_alert_raw!![j] = ival
                    car_tpms_alert!![j] = sval
                    i++
                    j++
                }
                valid = dataParts[i++].toInt()
                stale_tpms_alert =
                    if (valid < 0) DataStale.NoValue else if (valid == 0) DataStale.Stale else DataStale.Good
            }

            // Process sensor mappings (sensor index for each wheel position):
            if (i < dataParts.size) {
                cnt = dataParts[i++].toInt()
                if (cnt > 0) {
                    if (car_tpms_mapping_raw == null || car_tpms_mapping_raw!!.size != cnt) {
                        car_tpms_mapping_raw = IntArray(cnt)
                    }
                    j = 0
                    end = i + cnt
                    while (i < end && i < dataParts.size) {
                        ival = dataParts[i].toInt()
                        car_tpms_mapping_raw!![j] = ival
                        i++
                        j++
                    }
                    if (i < dataParts.size) {
                        valid = dataParts[i++].toInt()
                        stale_tpms_mapping =
                            if (valid < 0) DataStale.NoValue else if (valid == 0) DataStale.Stale else DataStale.Good
                    }
                } else {
                    // Mapping count is 0 = not supported
                    car_tpms_mapping_raw = null
                    stale_tpms_mapping = DataStale.NoValue
                }
            }
            Log.d(TAG, "processNewTPMS: processed $i parts")
        } catch (e: Exception) {
            Log.e(TAG, "processNewTPMS: ERROR", e)
            return false
        }

        // Car specific handling:
        if (car_type == "RT" || car_type == "SE") {
            stale_tpms_pressure = DataStale.NoValue
            stale_tpms_temp = DataStale.NoValue
            stale_tpms_health = DataStale.NoValue
            stale_tpms_alert = DataStale.NoValue
            stale_tpms_mapping = DataStale.NoValue
        }
        recalc()
        return true
    }

    /**
     * Process GEN message ("X")
     * not implemented yet?
     */
    fun processGen(msgdata: String): Boolean {
        init()
        Log.d(TAG, "processGen: $msgdata")
        try {
            val dataParts =
                msgdata.split(",\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (dataParts.size >= 9) {
                car_gen_inprogress = dataParts[0].toBoolean()
                car_gen_pilot = dataParts[1].toBoolean()
                car_gen_voltage = dataParts[2].toInt()
                car_gen_current = dataParts[3].toFloat()
                car_gen_power = dataParts[4].toFloat()
                car_gen_efficiency = dataParts[5].toFloat()
                car_gen_type = dataParts[6]
                car_gen_state = dataParts[7]
                car_gen_substate = dataParts[8]
                car_gen_mode = dataParts[9]
                car_gen_climit = dataParts[10].toFloat()
                car_gen_limit_range = dataParts[11].toFloat()
                car_gen_limit_soc = dataParts[12].toInt()
                car_gen_kwh = dataParts[13].toFloat()
                car_gen_kwh_grid = dataParts[14].toFloat()
                car_gen_kwh_grid_total = dataParts[15].toFloat()
                car_gen_time = dataParts[16].toInt()
                car_gen_timermode = dataParts[17].toInt()
                car_gen_timerstart = dataParts[18].toInt()
                car_gen_duration_empty = dataParts[19].toInt()
                car_gen_duration_range = dataParts[20].toInt()
                car_gen_duration_soc = dataParts[21].toInt()
                car_gen_temp = dataParts[22].toFloat()
            }
        } catch (e: Exception) {
            Log.e(TAG, "processGen: ERROR", e)
            return false
        }
        recalc()
        return true
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
    fun getBroadcastData(): Bundle {
        val isoDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val b = Bundle()
        try {

            //
            // Time (msgCode 'T')
            //
            b.putLong("car_lastupdated_seconds", car_lastupdate_raw)
            b.putString(
                "car_lastupdated_time",
                if (car_lastupdated != null) isoDateTime.format(car_lastupdated) else ""
            )


            //
            // Peers (msgCode 'Z')
            //
            b.putInt("server_carsconnected", server_carsconnected)


            //
            // Status (msgCode 'S')
            //
            b.putFloat("car_soc", car_soc_raw)
            b.putDouble("car_battery_voltage", car_battery_voltage)
            b.putDouble("car_battery_cac", car_CAC)
            b.putDouble("car_battery_cac_ref", car_CAC_ref)
            b.putDouble("car_battery_cac_percent", car_CAC_percent)
            b.putFloat("car_soh", car_soh)
            b.putString("car_distance_units", car_distance_units_raw)
            b.putFloat("car_range_ideal", car_range_ideal_raw)
            b.putInt("car_range_ideal_max", car_max_idealrange_raw)
            b.putFloat("car_range_estimated", car_range_estimated_raw)
            b.putInt("car_charge_state", car_charge_state_i_raw)
            b.putInt("car_charge_mode", car_charge_mode_i_raw)
            b.putString("car_charge_state_label", car_charge_state)
            b.putString("car_charge_mode_label", car_charge_mode)
            b.putFloat("car_charge_linevoltage", car_charge_linevoltage_raw)
            b.putFloat("car_charge_current", car_charge_current_raw)
            b.putFloat("car_charge_currentlimit", car_charge_currentlimit_raw)
            b.putInt("car_charge_duration", car_charge_duration_raw)
            b.putString("car_charge_time", car_charge_time)
            b.putInt("car_charge_plugtype", car_charge_plugtype)
            b.putDouble("car_charge_power_kw", car_charge_power_kw_raw)
            b.putDouble("car_battery_current", car_battery_current_raw)
            b.putDouble("car_battery_rangespeed", car_battery_rangespeed_raw)
            b.putFloat("car_charge_kwhconsumed", car_charge_kwhconsumed)
            b.putBoolean("car_charge_timer", car_charge_timer)
            b.putFloat("car_charge_power_input_kw", car_charge_power_input_kw_raw)
            b.putFloat("car_charger_efficiency", car_charger_efficiency)
            b.putDouble("car_charge_power_loss_kw", car_charge_power_loss_kw_raw)
            b.putInt("car_chargeestimate", car_chargeestimate)
            b.putInt("car_chargefull_minsremaining", car_chargefull_minsremaining)
            b.putInt("car_chargelimit_minsremaining", car_chargelimit_minsremaining)
            b.putInt("car_chargelimit_rangelimit", car_chargelimit_rangelimit_raw)
            b.putInt("car_chargelimit_minsremaining_range", car_chargelimit_minsremaining_range)
            b.putInt("car_chargelimit_soclimit", car_chargelimit_soclimit)
            b.putInt("car_chargelimit_minsremaining_soc", car_chargelimit_minsremaining_soc)
            b.putFloat("car_charge_kwh_grid", car_charge_kwh_grid)
            b.putFloat("car_charge_kwh_grid_total", car_charge_kwh_grid_total)
            b.putFloat("car_battery_capacity", car_battery_capacity)
            b.putString("car_charge_timestamp", car_charge_timestamp)
            b.putInt("car_charge_timestamp_sec", car_charge_timestamp_sec)


            //
            // Location (msgCode 'L')
            //
            b.putDouble("car_latitude", car_latitude)
            b.putDouble("car_longitude", car_longitude)
            b.putDouble("car_direction", car_direction)
            b.putDouble("car_altitude", car_altitude)
            b.putInt("car_gps_lock", car_gpslock_raw)
            b.putInt("car_gps_stale", car_stale_gps_raw)
            b.putDouble("car_speed", car_speed_raw.toDouble())
            b.putFloat("car_tripmeter", car_tripmeter_raw)
            b.putInt("car_drivemode", car_drivemode)
            if ("RT" == car_type) {
                b.putInt("rt_cfg_type", rt_cfg_type)
                b.putInt("rt_cfg_profile_user", rt_cfg_profile_user)
                b.putInt("rt_cfg_profile_cfgmode", rt_cfg_profile_cfgmode)
                b.putInt("rt_cfg_unsaved", rt_cfg_unsaved)
                b.putInt("rt_cfg_applied", rt_cfg_applied)
            }
            b.putDouble("car_power", car_power)
            b.putFloat("car_energyused", car_energyused)
            b.putFloat("car_energyrecd", car_energyrecd)
            b.putFloat("car_inv_power_motor", car_inv_power_motor_kw)
            b.putFloat("car_inv_efficiency", car_inv_efficiency)


            //
            // Environment, doors & switches (msgCode 'D')
            //
            b.putBoolean("car_frontleftdoor_open", car_frontleftdoor_open)
            b.putBoolean("car_frontrightdoor_open", car_frontrightdoor_open)
            b.putBoolean("car_rearleftdoor_open", car_rearleftdoor_open)
            b.putBoolean("car_rearrightdoor_open", car_rearrightdoor_open)
            b.putBoolean("car_chargeport_open", car_chargeport_open)
            b.putBoolean("car_pilot_present", car_pilot_present)
            b.putBoolean("car_charging", car_charging)
            b.putBoolean("car_handbrake_on", car_handbrake_on)
            b.putBoolean("car_started", car_started)
            b.putBoolean("car_locked", car_locked)
            b.putBoolean("car_valetmode", car_valetmode)
            b.putBoolean("car_headlights_on", car_headlights_on)
            b.putBoolean("car_bonnet_open", car_bonnet_open)
            b.putBoolean("car_trunk_open", car_trunk_open)
            b.putBoolean("car_awake", car_awake)
            b.putBoolean("car_alarm_sounding", car_alarm_sounding)
            b.putBoolean("car_hvac_on", car_hvac_on)
            b.putFloat("car_temp_pem", car_temp_pem_raw)
            b.putFloat("car_temp_motor", car_temp_motor_raw)
            b.putFloat("car_temp_battery", car_temp_battery_raw)
            b.putFloat("car_temp_ambient", car_temp_ambient_raw)
            b.putFloat("car_temp_charger", car_temp_charger_raw)
            b.putFloat("car_temp_cabin", car_temp_cabin_raw)
            b.putFloat("car_odometer", car_odometer_raw)
            if (car_parked_time != null) {
                b.putLong("car_parked_seconds", car_parking_timer_raw)
                b.putString("car_parked_time", isoDateTime.format(car_parked_time))
            } else {
                b.putLong("car_parked_seconds", 0)
                b.putString("car_parked_time", "")
            }
            b.putBoolean("car_charging_12v", car_charging_12v)
            b.putBoolean("car_auxiliary_12v", car_auxiliary_12v)
            b.putDouble("car_12vline_voltage", car_12vline_voltage)
            b.putDouble("car_12vline_ref", car_12vline_ref)
            b.putDouble("car_12v_current", car_12v_current)

            //
            // Firmware (msgCode 'F')
            //
            b.putString("car_firmware", car_firmware)
            b.putString("car_hardware", car_hardware)
            b.putString("car_vin", car_vin)
            b.putInt("car_gsm_dbm", car_gsm_dbm)
            b.putInt("car_gsm_bars", car_gsm_bars)
            b.putString("car_gsm_lock", car_gsmlock)
            b.putInt("car_canwrite", car_canwrite_raw)
            b.putInt("car_servicedays", car_servicetime)
            b.putInt("car_servicedist", car_servicerange)
            b.putString("car_mdm_mode", car_mdm_mode)

            //
            // TPMS new flexible wheel layout (msgCode 'Y')
            //
            b.putStringArray("car_tpms_wheelname", car_tpms_wheelname)
            b.putDoubleArray("car_tpms_pressure", car_tpms_pressure_raw)
            b.putDoubleArray("car_tpms_temp", car_tpms_temp_raw)
            b.putDoubleArray("car_tpms_health", car_tpms_health_raw)
            b.putIntArray("car_tpms_alert", car_tpms_alert_raw)
            b.putIntArray("car_tpms_mapping", car_tpms_mapping_raw)

            //
            // TPMS old fixed four wheel layout values
            //
            if (car_tpms_pressure_raw != null && car_tpms_pressure_raw!!.size >= 4 && car_tpms_temp_raw != null && car_tpms_temp_raw!!.size >= 4) {
                // Values from msgCode 'Y':
                b.putDouble("car_tpms_fl_p", car_tpms_pressure_raw!![0] * 0.1450377)
                b.putDouble("car_tpms_fl_t", car_tpms_temp_raw!![0])
                b.putDouble("car_tpms_fr_p", car_tpms_pressure_raw!![1] * 0.1450377)
                b.putDouble("car_tpms_fr_t", car_tpms_temp_raw!![1])
                b.putDouble("car_tpms_rl_p", car_tpms_pressure_raw!![2] * 0.1450377)
                b.putDouble("car_tpms_rl_t", car_tpms_temp_raw!![2])
                b.putDouble("car_tpms_rr_p", car_tpms_pressure_raw!![3] * 0.1450377)
                b.putDouble("car_tpms_rr_t", car_tpms_temp_raw!![3])
            } else {
                // Legacy, values from msgCode 'W':
                b.putDouble("car_tpms_fl_p", car_tpms_fl_p_raw)
                b.putDouble("car_tpms_fl_t", car_tpms_fl_t_raw)
                b.putDouble("car_tpms_fr_p", car_tpms_fr_p_raw)
                b.putDouble("car_tpms_fr_t", car_tpms_fr_t_raw)
                b.putDouble("car_tpms_rl_p", car_tpms_rl_p_raw)
                b.putDouble("car_tpms_rl_t", car_tpms_rl_t_raw)
                b.putDouble("car_tpms_rr_p", car_tpms_rr_p_raw)
                b.putDouble("car_tpms_rr_t", car_tpms_rr_t_raw)
            }

            //
            // Capabilities (msgCode 'V')
            //
            b.putString("car_capabilities", car_capabilities)

            //
            //  Gen (msgCode 'X')
            //
            // not implemented yet?
            b.putBoolean("car_gen_inprogress", car_gen_inprogress)
            b.putBoolean("car_gen_pilot", car_gen_pilot)
            b.putInt("car_gen_voltage", car_gen_voltage)
            b.putFloat("car_gen_current", car_gen_current)
            b.putFloat("car_gen_power", car_gen_power)
            b.putFloat("car_gen_efficiency", car_gen_efficiency)
            b.putString("car_gen_type", car_gen_type)
            b.putString("car_gen_state", car_gen_state)
            b.putString("car_gen_substate", car_gen_substate)
            b.putString("car_gen_mode", car_gen_mode)
            b.putFloat("car_gen_climit", car_gen_climit)
            b.putFloat("car_gen_limit_range", car_gen_limit_range)
            b.putInt("car_gen_limit_soc", car_gen_limit_soc)
            b.putFloat("car_gen_kwh", car_gen_kwh)
            b.putFloat("car_gen_kwh_grid", car_gen_kwh_grid)
            b.putFloat("car_gen_kwh_grid_total", car_gen_kwh_grid_total)
            b.putInt("car_gen_time", car_gen_time)
            b.putInt("car_gen_timermode", car_gen_timermode)
            b.putInt("car_gen_timerstart", car_gen_timerstart)
            b.putInt("car_gen_duration_empty", car_gen_duration_empty)
            b.putInt("car_gen_duration_range", car_gen_duration_range)
            b.putInt("car_gen_duration_soc", car_gen_duration_soc)
            b.putFloat("car_gen_temp", car_gen_temp)
            b.putString("car_gen_timestamp", car_gen_timestamp)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return b
    }

    /*
     * Inner types
     */

    companion object {

        private const val TAG = "CarData"
        private const val serialVersionUID = 9069218298370983442L
    }

    enum class DataStale {
        NoValue,
        Stale,
        Good
    }
}