package com.openvehicles.OVMS.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.entities.CarData.DataStale
import com.openvehicles.OVMS.ui.BaseFragmentActivity.Companion.show
import com.openvehicles.OVMS.ui.settings.CarInfoFragment
import com.openvehicles.OVMS.ui.settings.FeaturesFragment
import com.openvehicles.OVMS.ui.settings.GlobalOptionsFragment
import com.openvehicles.OVMS.ui.settings.LogsFragment
import com.openvehicles.OVMS.ui.utils.Ui
import com.openvehicles.OVMS.ui.utils.Ui.getDrawableIdentifier
import com.openvehicles.OVMS.ui.utils.Ui.showPinDialog
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.CarsStorage.getSelectedCarData
import kotlin.math.floor

class CarFragment : BaseFragment(), View.OnClickListener, OnResultCommandListener {

    private var carData: CarData? = null
    private var uiCarType = ""
    private lateinit var optionsMenu: Menu
    lateinit var appPrefs: AppPrefs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // init car data:
        carData = getSelectedCarData()
        appPrefs = AppPrefs(requireActivity(), "ovms")

        // inflate layout:
        val rootView = inflater.inflate(R.layout.fragment_car, null)
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        uiCarType = ""
    }

    override fun onResume() {
        super.onResume()
        setupCarType(carData)
    }

    /**
     * setupCarType: apply car specific UI changes
     *
     * @param carData
     */
    private fun setupCarType(carData: CarData?) {
        Log.d(TAG, "updateCarType: old=" + uiCarType + ", new=" + carData!!.car_type)
        if (uiCarType == carData.car_type) {
            return
        }

        // set the car background image:
        val iv = findViewById(R.id.tabCarImageCarOutline) as ImageView
        iv.setImageResource(getDrawableIdentifier(activity, "ol_" + this.carData!!.sel_vehicle_image))
        val tabCarImageHomeLink: ImageView = findViewById(R.id.tabCarImageHomelink) as ImageView
        when (carData.car_type) {
            "RT" -> {
                // UI changes for Renault Twizy:

                // exchange "Homelink" by "Profile":
                tabCarImageHomeLink.setImageResource(R.drawable.ic_drive_profile)
                val label = findViewById(R.id.txt_homelink) as TextView
                label.setText(R.string.textPROFILE)
            }
            "RZ" -> {
                // UI changes for Renault ZOE:

                // change "Homelink" image:
                tabCarImageHomeLink.setImageResource(R.drawable.homelinklogo_zoe)
            }
            "SQ" -> {
                // UI changes for Smart EQ:
                findViewById(R.id.btn_valet_mode).visibility = View.INVISIBLE
                findViewById(R.id.btn_lock_car).visibility = View.INVISIBLE
                //findViewById(R.id.tabCarImageCarLocked).visibility = View.INVISIBLE
                //findViewById(R.id.tabCarImageCarValetMode).visibility = View.INVISIBLE
                // change "Homelink" image:
                tabCarImageHomeLink.setImageResource(R.drawable.ic_home_link)
            }
            "EN", "NRJK" -> { // Also previous "NRJK" code
                // UI change for Energica:

                // TODO: No TPMS, only two wheels, etc.
                findViewById(R.id.btn_valet_mode).visibility = View.INVISIBLE
                findViewById(R.id.btn_lock_car).visibility = View.INVISIBLE
                findViewById(R.id.tabCarImageCarLocked).visibility = View.INVISIBLE
                findViewById(R.id.tabCarImageCarValetMode).visibility = View.INVISIBLE
                findViewById(R.id.tabCarImageCarTPMSBoxes).visibility = View.INVISIBLE
                findViewById(R.id.tabCarImageCarLeftDoorOpen).visibility = View.INVISIBLE
                findViewById(R.id.tabCarImageCarRightDoorOpen).visibility = View.INVISIBLE
                findViewById(R.id.tabCarImageCarTrunkOpen).visibility = View.INVISIBLE
            }
            else -> {
                tabCarImageHomeLink.setImageResource(R.drawable.ic_home_link)
            }
        }

        //
        // Configure A/C button:
        //
        val tabCarImageCarACBoxes = findViewById(R.id.tabCarImageCarACBoxes) as ImageView
        val tabCarImageAC: ImageView = findViewById(R.id.tabCarImageAC) as ImageView

        // The V3 framework does not support capabilities yet, but
        //	the Leaf, Smart, VW e-Up and Zoe Ph2 are the only cars providing command 26 up to now, so:
        if (carData.hasCommand(26)
            || carData.car_type == "NL"
            || carData.car_type == "SE"
            || carData.car_type == "SQ"
            || carData.car_type == "VWUP"
            || carData.car_type == "VWUP.T26"
            || carData.car_type == "RZ2") {
            // enable
            tabCarImageCarACBoxes.setVisibility(View.VISIBLE)
            tabCarImageAC.setVisibility(View.VISIBLE)
            if (carData.car_hvac_on) {
                tabCarImageAC.setImageResource(R.drawable.ic_ac_on)
            } else {
                tabCarImageAC.setImageResource(R.drawable.ic_ac_off)
            }
        } else {
            // disable
            tabCarImageCarACBoxes.setVisibility(View.INVISIBLE)
            tabCarImageAC.setVisibility(View.INVISIBLE)
        }
        uiCarType = carData.car_type

        // request menu recreation:
        compatActivity!!.invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.car_options, menu)
        optionsMenu = menu

        // set checkbox:
        optionsMenu.findItem(R.id.mi_show_fahrenheit)
            .setChecked(appPrefs.getData("showfahrenheit") == "on")
        optionsMenu.findItem(R.id.mi_show_tpms_bar)
            .setChecked(appPrefs.getData("showtpmsbar") == "on")

        if (uiCarType == "RT") {
            // Menu setup for Renault Twizy:
            optionsMenu.findItem(R.id.mi_power_stats).setVisible(true)
            optionsMenu.findItem(R.id.mi_battery_stats).setVisible(true)
            optionsMenu.findItem(R.id.mi_show_diag_logs).setVisible(true)
        } else {
            // defaults:
            optionsMenu.findItem(R.id.mi_power_stats).setVisible(false)
            optionsMenu.findItem(R.id.mi_battery_stats).setVisible(false)
            optionsMenu.findItem(R.id.mi_show_diag_logs).setVisible(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        val newState = !item.isChecked
        when (menuId) {
            R.id.mi_power_stats -> {
                show(
                    requireActivity(),
                    PowerFragment::class.java,
                    null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_battery_stats -> {
                show(
                    requireActivity(),
                    BatteryFragment::class.java,
                    null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_show_carinfo -> {
                show(
                    requireActivity(),
                    CarInfoFragment::class.java,
                    null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_aux_battery_stats -> {
                show(
                    requireActivity(),
                    AuxBatteryFragment::class.java,
                    null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_show_features -> {
                show(
                    requireActivity(),
                    FeaturesFragment::class.java,
                    null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_show_diag_logs -> {
                show(
                    requireActivity(),
                    LogsFragment::class.java,
                    null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_show_fahrenheit -> {
                appPrefs.saveData("showfahrenheit", if (newState) "on" else "off")
                item.setChecked(newState)
                triggerCarDataUpdate()
                return true
            }
            R.id.mi_show_tpms_bar -> {
                appPrefs.saveData("showtpmsbar", if (newState) "on" else "off")
                item.setChecked(newState)
                triggerCarDataUpdate()
                return true
            }
            R.id.mi_globaloptions -> {
                show(
                    requireActivity(),
                    GlobalOptionsFragment::class.java,
                    null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            else -> return false
        }
    }

    override fun update(carData: CarData?) {
        this.carData = carData
        setupCarType(carData)
        updateLastUpdatedView(carData)
        updateCarBodyView(carData)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerForContextMenu(findViewById(R.id.btn_wakeup))
        registerForContextMenu(findViewById(R.id.txt_homelink))
        registerForContextMenu(findViewById(R.id.tabCarImageHomelink))
        registerForContextMenu(findViewById(R.id.tabCarImageAC))
        findViewById(R.id.btn_lock_car).setOnClickListener(this)
        findViewById(R.id.btn_valet_mode).setOnClickListener(this)
        findViewById(R.id.tabCarText12V).setOnClickListener(this)
        findViewById(R.id.tabCarText12VLabel).setOnClickListener(this)
    }

    override fun registerForContextMenu(view: View) {
        super.registerForContextMenu(view)
        view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (carData == null) {
            return
        }
        val dialogTitle: Int
        val dialogButton: Int
        val isPinEntry: Boolean
        val id = v.id
        when (id) {
            R.id.btn_lock_car -> { // get dialog mode & labels:
                if (carData!!.car_type == "RT") {
                    dialogTitle = R.string.lb_lock_mode_twizy
                    dialogButton =
                        if (carData!!.car_locked) {
                            if (carData!!.car_valetmode) {
                                R.string.lb_valet_mode_extend_twizy
                            } else {
                                R.string.lb_unlock_car_twizy
                            }
                        } else {
                            R.string.lb_lock_car_twizy
                        }
                    isPinEntry = false
                } else {
                    dialogTitle = if (carData!!.car_locked) {
                        R.string.lb_unlock_car
                    } else {
                        R.string.lb_lock_car
                    }
                    dialogButton = dialogTitle
                    isPinEntry = true
                }

                // show dialog:
                showPinDialog(
                    requireActivity(),
                    dialogTitle,
                    dialogButton,
                    isPinEntry,
                    object : Ui.OnChangeListener<String?> {
                        override fun onAction(data: String?) {
                            val cmd: String
                            val resId: Int
                            if (carData!!.car_locked) {
                                resId = dialogButton
                                cmd = "22,$data"
                            } else {
                                resId = dialogButton
                                cmd = "20,$data"
                            }
                            sendCommand(resId, cmd, this@CarFragment)
                        }
                    })
            }
            R.id.btn_valet_mode -> { // get dialog mode & labels:
                when (carData!!.car_type) {
                    "RT" -> {
                        dialogTitle = R.string.lb_valet_mode_twizy
                        dialogButton =
                            if (carData!!.car_valetmode) {
                                if (carData!!.car_locked) {
                                    R.string.lb_unvalet_unlock_twizy
                                } else {
                                    R.string.lb_valet_mode_off_twizy
                                }
                            } else {
                                R.string.lb_valet_mode_on_twizy
                            }
                        isPinEntry = false
                    }
                    "SE" -> {
                        dialogTitle = R.string.lb_valet_mode_smart
                        dialogButton = if (carData!!.car_valetmode) {
                            R.string.lb_valet_mode_smart_off
                        } else {
                            R.string.lb_valet_mode_smart_on
                        }
                        isPinEntry = true
                    }
                    else -> {
                        dialogTitle = R.string.lb_valet_mode
                        dialogButton = if (carData!!.car_valetmode) {
                            R.string.lb_valet_mode_off
                        } else {
                            R.string.lb_valet_mode_on
                        }
                        isPinEntry = true
                    }
                }

                // show dialog:
                showPinDialog(
                    requireActivity(),
                    dialogTitle,
                    dialogButton,
                    isPinEntry,
                    object : Ui.OnChangeListener<String?> {
                        override fun onAction(data: String?) {
                            val cmd: String
                            val resId: Int
                            if (carData!!.car_valetmode) {
                                resId = dialogButton
                                cmd = "23,$data"
                            } else {
                                resId = dialogButton
                                cmd = "21,$data"
                            }
                            sendCommand(resId, cmd, this@CarFragment)
                        }
                    })
            }
            R.id.tabCarText12V, R.id.tabCarText12VLabel -> {
                show(
                    requireActivity(),
                    AuxBatteryFragment::class.java,
                    null,
                    Configuration.ORIENTATION_UNDEFINED
                )
            }
            else -> {
                v.performLongClick()
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val id = v.id
        when (id) {
            R.id.btn_wakeup -> {
                if (carData!!.car_type == "RT") {
                    // no wakeup support for Twizy
                    return
                }
                menu.setHeaderTitle(R.string.lb_wakeup_car)
                menu.add(0, MI_WAKEUP, 0, R.string.Wakeup)
                menu.add(R.string.Cancel)
            }
            R.id.tabCarImageHomelink, R.id.txt_homelink -> {
                if (carData!!.car_type == "RT") {
                    // Renault Twizy: use Homelink for profile switching:
                    menu.setHeaderTitle(R.string.textPROFILE)
                    menu.add(0, MI_HL_DEFAULT, 0, R.string.Default)
                } else {
                    menu.setHeaderTitle(R.string.textHOMELINK)
                }
                if (carData!!.car_type == "SQ") {
                    menu.add(0, MI_HL_01, 0, "Booster")
                    menu.add(0, MI_HL_02, 0, "2")
                    menu.add(0, MI_HL_03, 0, "3")
                    menu.add(R.string.Cancel)
                } else {
                    menu.add(0, MI_HL_01, 0, "1")
                    menu.add(0, MI_HL_02, 0, "2")
                    menu.add(0, MI_HL_03, 0, "3")
                    menu.add(R.string.Cancel)
                }
            }
            R.id.tabCarImageAC -> {
                menu.setHeaderTitle(R.string.textAC)
                menu.add(0, MI_AC_ON, 0, R.string.mi_ac_on)
                menu.add(0, MI_AC_OFF, 0, R.string.mi_ac_off)
                menu.add(R.string.Cancel)
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            MI_WAKEUP -> {
                sendCommand(R.string.msg_wakeup_car, "18", this)
                true
            }
            MI_HL_01 -> {
                sendCommand(R.string.msg_issuing_homelink, "24,0", this)
                true
            }
            MI_HL_02 -> {
                sendCommand(R.string.msg_issuing_homelink, "24,1", this)
                true
            }
            MI_HL_03 -> {
                sendCommand(R.string.msg_issuing_homelink, "24,2", this)
                true
            }
            MI_HL_DEFAULT -> {
                sendCommand(R.string.msg_issuing_homelink, "24", this)
                true
            }
            MI_AC_ON -> {
                sendCommand(R.string.msg_issuing_climatecontrol, "26,1", this)
                true
            }
            MI_AC_OFF -> {
                sendCommand(R.string.msg_issuing_climatecontrol, "26,0", this)
                true
            }
            else -> false
        }
    }

    override fun onResultCommand(result: Array<String>) {
        if (result.size <= 1) {
            return
        }

        val command = result[0].toInt()
        val resCode = result[1].toInt()
        val resText = if (result.size > 2) result[2] else ""
        val cmdMessage = getSentCommandMessage(result[0])
        val context: Context? = activity
        if (context != null) {
            when (resCode) {
                0 -> Toast.makeText(
                    context, cmdMessage + " => " + getString(R.string.msg_ok),
                    Toast.LENGTH_SHORT
                ).show()
                1 -> Toast.makeText(
                    context, cmdMessage + " => " + getString(R.string.err_failed, resText),
                    Toast.LENGTH_SHORT
                ).show()
                2 -> Toast.makeText(
                    context, cmdMessage + " => " + getString(R.string.err_unsupported_operation),
                    Toast.LENGTH_SHORT
                ).show()
                3 -> Toast.makeText(
                    context, cmdMessage + " => " + getString(R.string.err_unimplemented_operation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        cancelCommand()
    }

    // This updates the part of the view with times shown.
    // It is called by a periodic timer so it gets updated every few seconds.
    private fun updateLastUpdatedView(carData: CarData?) {
        if (carData?.car_lastupdated == null) {
            return
        }

        // First the last updated section...
        var tv = findViewById(R.id.txt_last_updated) as TextView
        val now = System.currentTimeMillis()
        var seconds = (now - carData.car_lastupdated!!.time) / 1000
        var minutes = seconds / 60
        var hours = minutes / 60
        var days = minutes / (60 * 24)
        Log.d(TAG, "Last updated: $seconds secs ago")
        if (carData.car_lastupdated == null) {
            tv.text = ""
            tv.setTextColor(-0x1)
        } else if (minutes == 0L) {
            tv.text = getText(R.string.live)
            tv.setTextColor(-0x1)
        } else if (minutes == 1L) {
            tv.text = getText(R.string.min1)
            tv.setTextColor(-0x1)
        } else if (days > 1) {
            tv.text = String.format(getText(R.string.ndays).toString(), days)
            tv.setTextColor(-0x10000)
        } else if (hours > 1) {
            tv.text = String.format(getText(R.string.nhours).toString(), hours)
            tv.setTextColor(-0x10000)
        } else if (minutes > 60) {
            tv.text = String.format(getText(R.string.nmins).toString(), minutes)
            tv.setTextColor(-0x10000)
        } else {
            tv.text = String.format(getText(R.string.nmins).toString(), minutes)
            tv.setTextColor(-0x1)
        }

        // Then the parking timer...
        tv = findViewById(R.id.txt_parked_time) as TextView
        if (!carData.car_started && carData.car_parked_time != null) {
            // Car is parked
            tv.visibility = View.VISIBLE
            seconds = (now - carData.car_parked_time!!.time) / 1000
            minutes = seconds / 60
            hours = minutes / 60
            days = minutes / (60 * 24)
            if (minutes == 0L) {
                tv.text = getText(R.string.justnow)
            } else if (minutes == 1L) {
                tv.text = "1 min"
            } else if (days > 1) {
                tv.text = String.format(getText(R.string.ndays).toString(), days)
            } else if (hours > 1) {
                tv.text = String.format(getText(R.string.nhours).toString(), hours)
            } else if (minutes > 60) {
                tv.text = String.format(getText(R.string.nmins).toString(), minutes)
            } else {
                tv.text = String.format(getText(R.string.nmins).toString(), minutes)
            }
        } else {
            tv.visibility = View.INVISIBLE
        }

        // The signal strength indicator
        val iv = findViewById(R.id.img_signal_rssi) as ImageView
        iv.setImageResource(
            getDrawableIdentifier(
                activity,
                "signal_strength_" + carData.car_gsm_bars
            )
        )
    }

    // This updates the main informational part of the view.
    // It is called when the server gets new data.
    private fun updateCarBodyView(carData: CarData?) {
        if (carData?.car_lastupdated == null) {
            return
        }

        // Now, the car background image
        var iv: ImageView = findViewById(R.id.tabCarImageCarOutline) as ImageView
        if (carData.sel_vehicle_image.startsWith("car_imiev_")) {
            // Mitsubishi i-MiEV: one ol image for all colors:
            iv.setImageResource(R.drawable.ol_car_imiev)
        } else if (carData.sel_vehicle_image.startsWith("car_i3_")) {
            // BMW i3: one ol image for all colors since roof is same:
            iv.setImageResource(R.drawable.ol_car_i3)
        } else if (carData.sel_vehicle_image.startsWith("car_smart_")) {
            // smart ED: one ol image for all colors:
            iv.setImageResource(R.drawable.ol_car_smart)
        } else if (carData.sel_vehicle_image.startsWith("car_ampera_")) {
            // Ampera: one ol image for all colors:
            iv.setImageResource(R.drawable.ol_car_ampera)
        } else if (carData.sel_vehicle_image.startsWith("car_holdenvolt_")) {
            // Holdenvolt: one ol image for all colors (same as ampera):
            iv.setImageResource(R.drawable.ol_car_ampera)
        } else if (carData.sel_vehicle_image.startsWith("car_twizy_")) {
            // Twizy: one ol image for all colors:
            iv.setImageResource(R.drawable.ol_car_twizy)
        } else if (carData.sel_vehicle_image.startsWith("car_kangoo_")) {
            // Kangoo: one ol image for all colors:
            iv.setImageResource(R.drawable.ol_car_kangoo)
        } else if (carData.sel_vehicle_image.startsWith("car_kianiro_")) {
            iv.setImageResource(R.drawable.ol_car_kianiro_grey)
        } else if (carData.sel_vehicle_image.startsWith("car_nrjkexperia")) {
            iv.setImageResource(R.drawable.ol_car_nrjkexperia)
        } else if (carData.sel_vehicle_image.startsWith("car_nrjk")) {
            iv.setImageResource(R.drawable.ol_car_nrjk) // TODO: Ribelle top view
        } else if (carData.sel_vehicle_image.startsWith("car_niu_mqi_gt")) {
            iv.setImageResource(R.drawable.ol_car_nrjk)
        } else {
            iv.setImageResource(getDrawableIdentifier(activity, "ol_" + carData.sel_vehicle_image))
        }

        // "12V" box:
        //label = (TextView) findViewById(R.id.tabCarText12VLabel);
        var tv: TextView = findViewById(R.id.tabCarText12V) as TextView
        tv.text = String.format("%.1fV", this.carData!!.car_12vline_voltage)
        if (this.carData!!.car_12vline_ref <= 1.5 || this.carData!!.car_charging_12v) {
            // charging / calmdown
            tv.setTextColor(-0x565601)
        } else {
            val diff = this.carData!!.car_12vline_ref - this.carData!!.car_12vline_voltage
            if (diff >= 1.6) {
                tv.setTextColor(-0x10000)
            } else if (diff >= 1.0) {
                tv.setTextColor(-0x9a00)
            } else {
                tv.setTextColor(-0x1)
            }
        }

        // "Ambient" box:
        iv = findViewById(R.id.tabCarImageCarAmbientBox) as ImageView
        val label: TextView = findViewById(R.id.tabCarTextAmbientLabel) as TextView
        tv = findViewById(R.id.tabCarTextAmbient) as TextView
        when (carData.stale_ambient_temp) {
            DataStale.NoValue -> {
                iv.setVisibility(View.INVISIBLE)
                label.visibility = View.INVISIBLE
                tv.text = null
            }
            DataStale.Stale -> {
                iv.setVisibility(View.VISIBLE)
                label.visibility = View.VISIBLE
                tv.text = carData.car_temp_ambient
                tv.setTextColor(-0x7f7f80)
            }
            else -> {
                iv.setVisibility(View.VISIBLE)
                label.visibility = View.VISIBLE
                tv.text = carData.car_temp_ambient
                tv.setTextColor(-0x1)
            }
        }

        // TPMS
        //	String tirePressureDisplayFormat = "%s\n%s";
        val fltv = findViewById(R.id.textFLWheel) as TextView
        val fltvv = findViewById(R.id.textFLWheelVal) as TextView
        val frtv = findViewById(R.id.textFRWheel) as TextView
        val frtvv = findViewById(R.id.textFRWheelVal) as TextView
        val rltv = findViewById(R.id.textRLWheel) as TextView
        val rltvv = findViewById(R.id.textRLWheelVal) as TextView
        val rrtv = findViewById(R.id.textRRWheel) as TextView
        val rrtvv = findViewById(R.id.textRRWheelVal) as TextView
        iv = findViewById(R.id.tabCarImageCarTPMSBoxes) as ImageView

        // Determine value layout:
        var stale1 = DataStale.NoValue
        var stale2 = DataStale.NoValue
        var val1 = carData.car_tpms_wheelname
        var val2: Array<String?>? = null
        val alert: IntArray?
        if (carData.car_tpms_wheelname != null && carData.car_tpms_wheelname!!.size >= 4) {
            // New data (msg code 'Y'):
            if (carData.stale_tpms_pressure != DataStale.NoValue && carData.car_tpms_pressure!!.size >= 4) {
                stale1 = carData.stale_tpms_pressure
                val1 = carData.car_tpms_pressure
            }
            if (carData.stale_tpms_temp != DataStale.NoValue && carData.car_tpms_temp!!.size >= 4) {
                stale2 = carData.stale_tpms_temp
                val2 = carData.car_tpms_temp
            }
            if (carData.stale_tpms_health != DataStale.NoValue && carData.car_tpms_health!!.size >= 4) {
                if (stale1 == DataStale.NoValue) {
                    stale1 = carData.stale_tpms_health
                    val1 = carData.car_tpms_health
                } else if (stale2 == DataStale.NoValue) {
                    stale2 = carData.stale_tpms_health
                    val2 = carData.car_tpms_health
                }
            }
            if (carData.stale_tpms_alert != DataStale.NoValue && carData.car_tpms_alert!!.size >= 4) {
                alert = carData.car_tpms_alert_raw
                if (stale1 == DataStale.NoValue) {
                    stale1 = carData.stale_tpms_alert
                    val1 = carData.car_tpms_alert
                } else if (stale2 == DataStale.NoValue) {
                    stale2 = carData.stale_tpms_alert
                    val2 = carData.car_tpms_alert
                }
            } else {
                alert = intArrayOf(0, 0, 0, 0)
            }
            // display single value in the bottom field:
            if (stale2 == DataStale.NoValue && stale1 != DataStale.NoValue) {
                stale2 = stale1
                val2 = val1
                val1 = carData.car_tpms_wheelname
            }
        } else {
            // Legacy data (msg code 'W'): only pressures & temperatures available
            val1 = arrayOf(
                carData.car_tpms_fl_p,
                carData.car_tpms_fr_p,
                carData.car_tpms_rl_p,
                carData.car_tpms_rr_p
            )
            stale1 = carData.stale_tpms
            val2 = arrayOf(
                carData.car_tpms_fl_t,
                carData.car_tpms_fr_t,
                carData.car_tpms_rl_t,
                carData.car_tpms_rr_t
            )
            stale2 = carData.stale_tpms
            alert = intArrayOf(0, 0, 0, 0)
        }

        // Update display:
        if (stale1 == DataStale.NoValue) {
            iv.setVisibility(View.INVISIBLE)
            fltv.text = null
            frtv.text = null
            rltv.text = null
            rrtv.text = null
            fltvv.text = null
            frtvv.text = null
            rltvv.text = null
            rrtvv.text = null
        } else {
            iv.setVisibility(View.VISIBLE)
            fltv.text = val1!![0]
            frtv.text = val1[1]
            rltv.text = val1[2]
            rrtv.text = val1[3]
            fltvv.text = val2!![0]
            frtvv.text = val2[1]
            rltvv.text = val2[2]
            rrtvv.text = val2[3]
            val trans1 = if (stale1 == DataStale.Stale) -0x80000000 else -0x1000000
            val trans2 = if (stale2 == DataStale.Stale) -0x80000000 else -0x1000000
            val alertcol = intArrayOf(0xFFFFFF, 0xFFAA44, 0xFF4444)
            fltv.setTextColor(trans1 or alertcol[alert!![0]])
            frtv.setTextColor(trans1 or alertcol[alert[1]])
            rltv.setTextColor(trans1 or alertcol[alert[2]])
            rrtv.setTextColor(trans1 or alertcol[alert[3]])

            // Display alert state "OK" icons in green:
            if (val2.contentEquals(carData.car_tpms_alert)) {
                alertcol[0] = 0x44FF44
            }
            fltvv.setTextColor(trans2 or alertcol[alert[0]])
            frtvv.setTextColor(trans2 or alertcol[alert[1]])
            rltvv.setTextColor(trans2 or alertcol[alert[2]])
            rrtvv.setTextColor(trans2 or alertcol[alert[3]])
        }

        // "Temp PEM" box:
        val pemtvl = findViewById(R.id.tabCarTextPEMLabel) as TextView
        val pemtv = findViewById(R.id.tabCarTextPEM) as TextView
        // Display of cabin temperature for all vehicles that support it: VWUP VWUP.T26 NL KS KN VA MI SE SQ
        if (carData.car_type == "VWUP"
            || carData.car_type == "KS"
            || carData.car_type == "KN"
            || carData.car_type.startsWith("VA")
            || carData.car_type == "MI"
            || carData.car_type == "SE"
            || carData.car_type == "SQ"
            || carData.car_type == "NL"
            || carData.car_type == "RZ2") {
            pemtvl.setText(R.string.textCAB)
            if (carData.stale_car_temps == DataStale.NoValue) {
                pemtv.text = ""
            } else {
                pemtv.text = carData.car_temp_cabin
                if (carData.stale_car_temps == DataStale.Stale) {
                    pemtv.setTextColor(-0x7f7f80)
                } else {
                    pemtv.setTextColor(-0x1)
                }
            }
        } else {
            pemtvl.setText(R.string.textPEM)
            if (carData.stale_car_temps == DataStale.NoValue) {
                pemtv.text = ""
            } else {
                pemtv.text = carData.car_temp_pem
                if (carData.stale_car_temps == DataStale.Stale) {
                    pemtv.setTextColor(-0x7f7f80)
                } else {
                    pemtv.setTextColor(-0x1)
                }
            }
        }

        // "Temp Motor" box:
        val motortvl = findViewById(R.id.tabCarTextMotorLabel) as TextView
        val motortv = findViewById(R.id.tabCarTextMotor) as TextView

        // Renault Zoe, Smart ED, Smart EQ, Nissan LEAF, MG ZS EV display HVBatt voltage instead of motor temp
        if (this.carData!!.car_type == "RZ"
            || this.carData!!.car_type == "SE"
            || this.carData!!.car_type == "SQ"
            || this.carData!!.car_type == "NL"
            || this.carData!!.car_type == "MGEV") {
            motortvl.setText(R.string.textHVBATT)
            motortv.text = String.format("%.1fV", this.carData!!.car_battery_voltage)
            if (carData.stale_car_temps == DataStale.Stale) {
                motortv.setTextColor(-0x7f7f80)
            } else {
                motortv.setTextColor(-0x1)
            }
        } else {
            // Standard car: display Motor temperature
            motortvl.setText(R.string.textMOTOR)
            if (carData.stale_car_temps == DataStale.NoValue) {
                motortv.text = ""
            } else {
                motortv.text = carData.car_temp_motor
                if (carData.stale_car_temps == DataStale.Stale) {
                    motortv.setTextColor(-0x7f7f80)
                } else {
                    motortv.setTextColor(-0x1)
                }
            }
        }

        // Temperatures
        val batterytv = findViewById(R.id.tabCarTextBattery) as TextView
        val chargertv = findViewById(R.id.tabCarTextCharger) as TextView
        if (carData.stale_car_temps == DataStale.NoValue) {
            batterytv.text = ""
            chargertv.text = ""
        } else {
            batterytv.text = carData.car_temp_battery
            chargertv.text = carData.car_temp_charger
            if (carData.stale_car_temps == DataStale.Stale) {
                batterytv.setTextColor(-0x7f7f80)
                chargertv.setTextColor(-0x7f7f80)
            } else {
                batterytv.setTextColor(-0x1)
                chargertv.setTextColor(-0x1)
            }
        }
        var st: String
        var ss: SpannableString

        // Odometer
        st = String.format("%.1f%s", carData.car_odometer_raw / 10, carData.car_distance_units)
        ss = SpannableString(st)
        ss.setSpan(RelativeSizeSpan(0.67f), st.indexOf(carData.car_distance_units), st.length, 0)
        tv = findViewById(R.id.tabCarTextOdometer) as TextView
        tv.text = ss

        // Speed
        tv = findViewById(R.id.tabCarTextSpeed) as TextView
        if (!carData.car_started) {
            tv.text = ""
        } else {
            st = String.format("%.1f%s", carData.car_speed_raw, carData.car_speed_units)
            ss = SpannableString(st)
            ss.setSpan(RelativeSizeSpan(0.67f), st.indexOf(carData.car_speed_units), st.length, 0)
            tv.text = ss
        }

        // Trip
        st = String.format("➟%.1f%s", carData.car_tripmeter_raw / 10, carData.car_distance_units)
        ss = SpannableString(st)
        ss.setSpan(RelativeSizeSpan(0.67f), st.indexOf(carData.car_distance_units), st.length, 0)
        tv = findViewById(R.id.tabCarTextTrip) as TextView
        tv.text = ss

        // Energy
        st = String.format(
            "▴%.1f ▾%.1f kWh",
            floor((carData.car_energyused * 10).toDouble()) / 10,
            floor((carData.car_energyrecd * 10).toDouble()) / 10
        )
        ss = SpannableString(st)
        ss.setSpan(RelativeSizeSpan(0.67f), st.indexOf("kWh"), st.length, 0)
        tv = findViewById(R.id.tabCarTextEnergy) as TextView
        tv.text = ss

        // Car Hood
        iv = findViewById(R.id.tabCarImageCarHoodOpen) as ImageView
        iv.setVisibility(if (carData.car_bonnet_open) View.VISIBLE else View.INVISIBLE)
        if (carData.car_type.startsWith("VA")) {
            // Volt, Ampera
            iv.setImageResource(R.drawable.voltampera_outline_hd)
        }

        // Doors, Trunks & Headlights:
        if (carData.sel_vehicle_image.startsWith("car_zoe_")) {
            // Left Door Zoe
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.zoe_outline_ld)

            // Right Door Zoe
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.zoe_outline_rd)

            // Rear Left Door Zoe
            iv = findViewById(R.id.tabCarImageCarRearLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_rearleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.zoe_outline_rld)

            // Rear Right Door Zoe
            iv = findViewById(R.id.tabCarImageCarRearRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_rearrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.zoe_outline_rrd)

            // Trunk Zoe
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.setVisibility(if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.zoe_outline_tr)

            // Headlights Zoe
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.setVisibility(if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.zoe_carlights)
        } else if (carData.sel_vehicle_image.startsWith("car_mgzs_")) {
            // Left Door MGZS
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.mgzs_outline_ld)

            // Right Door MGZS
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.mgzs_outline_rd)

            // Rear Left Door MGZS
            iv = findViewById(R.id.tabCarImageCarRearLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_rearleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.mgzs_outline_rld)

            // Rear Right Door MGZS
            iv = findViewById(R.id.tabCarImageCarRearRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_rearrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.mgzs_outline_rrd)

            // Trunk MGZS
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.setVisibility(if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.mgzs_outline_tr)

            // Headlights MGZS
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.setVisibility(if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.mgzs_carlights)
        } else if (carData.sel_vehicle_image.startsWith("car_smart_")) {
            // Left Door Smart
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.smart_outline_ld)

            // Right Door Smart
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.smart_outline_rd)

            // Trunk Smart
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.setVisibility(if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.smart_outline_tr)

            // Headlights Smart
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.setVisibility(if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.smart_carlights)
        } else if (carData.sel_vehicle_image.startsWith("car_leaf")) {
            // Left Door Leaf
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.leaf_outline_ld)

            // Right Door Leaf
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.leaf_outline_rd)

            // Rear Left Door Leaf
            iv = findViewById(R.id.tabCarImageCarRearLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_rearleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.leaf_outline_rld)

            // Rear Right Door Leaf
            iv = findViewById(R.id.tabCarImageCarRearRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_rearrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.leaf_outline_rrd)

            // Trunk Leaf
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.setVisibility(if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.leaf_outline_tr)

            // Headlights Leaf
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.setVisibility(if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE)
            if (carData.sel_vehicle_image.startsWith("car_leaf2")) {
                iv.setImageResource(R.drawable.leaf2_carlights)
            } else iv.setImageResource(R.drawable.leaf_carlights)
        } else if (carData.sel_vehicle_image.startsWith("car_vwup_")) {
            // Left Door VW e-Up
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.vwup_outline_ld)

            // Right Door VW e-Up
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.vwup_outline_rd)

            // Rear Left Door VW e-Up
            iv = findViewById(R.id.tabCarImageCarRearLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_rearleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.vwup_outline_rld)

            // Rear Right Door VW e-Up
            iv = findViewById(R.id.tabCarImageCarRearRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_rearrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.vwup_outline_rrd)

            // Trunk VW e-Up
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.setVisibility(if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.vwup_outline_tr)

            // Headlights VW e-Up
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.setVisibility(if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.vwup_carlights)
        } else if (carData.sel_vehicle_image.startsWith("car_ampera_")
            || carData.sel_vehicle_image.startsWith("car_holdenvolt_")) {
            // Left Door Volt, Ampera
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.voltampera_outline_ld)

            // Right Door Volt, Ampera
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.voltampera_outline_rd)

            // Rear Left Door Volt, Ampera
            iv = findViewById(R.id.tabCarImageCarRearLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_rearleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.voltampera_outline_rld)

            // Rear Right Door Volt, Ampera
            iv = findViewById(R.id.tabCarImageCarRearRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_rearrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.voltampera_outline_rrd)

            // Trunk Volt, Ampera
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.setVisibility(if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.voltampera_outline_tr)

            // Headlights Volt, Ampera
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.setVisibility(if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.voltampera_carlights)
        } else if (carData.sel_vehicle_image.startsWith("car_kangoo_")) {
            // Left Door Kangoo
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.kangoo_outline_ld)

            // Right Door Kangoo
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.kangoo_outline_rd)

            // Rear Left Door Kangoo
            iv = findViewById(R.id.tabCarImageCarRearLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_rearleftdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.kangoo_outline_rld)

            // Rear Right Door Kangoo
            iv = findViewById(R.id.tabCarImageCarRearRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_rearrightdoor_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.kangoo_outline_rrd)

            // Trunk Kangoo
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.setVisibility(if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.kangoo_outline_tr)

            // Headlights Kangoo
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.setVisibility(if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE)
            iv.setImageResource(R.drawable.kangoo_carlights)
        } else {
            // Left Door
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE)

            // Right Door
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.setVisibility(if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE)

            // Trunk
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.setVisibility(if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE)

            // Headlights
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.setVisibility(if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE)
        }

        // Car locked
        when (carData.car_type) {
            "TR" -> {
                // Lock status Tesla Roadster
                iv = findViewById(R.id.tabCarImageCarLocked) as ImageView
                iv.setImageResource(if (carData.car_locked) R.drawable.carlock_roadster else R.drawable.carunlock_roadster)
            }
            "SQ" -> {
                // Switch on/off Smart EQ 453
                iv = findViewById(R.id.tabCarImageCarLocked) as ImageView
                iv.setImageResource(if (carData.car_started) R.drawable.smart_on_l else R.drawable.smart_off_l)
            }
            else -> {
                // Lock status default
                iv = findViewById(R.id.tabCarImageCarLocked) as ImageView
                iv.setImageResource(if (carData.car_locked) R.drawable.carlock_clean else R.drawable.carunlock_clean)
            }
        }

        // Valet mode
        when (carData.car_type) {
            "TR" -> {
                // Valet mode Tesla Roadster
                iv = findViewById(R.id.tabCarImageCarValetMode) as ImageView
                iv.setImageResource(if (carData.car_valetmode) R.drawable.carvaleton_roadster else R.drawable.carvaletoff_roadster)
            }
            "SE" -> {
                // Valet mode Smart ED 451
                iv = findViewById(R.id.tabCarImageCarValetMode) as ImageView
                iv.setImageResource(if (carData.car_valetmode) R.drawable.smart_on else R.drawable.smart_off)
            }
            "SQ" -> {
                // Handbreak on/off Smart EQ 453
                iv = findViewById(R.id.tabCarImageCarValetMode) as ImageView
                iv.setImageResource(if (carData.car_handbrake_on) R.drawable.handbrake_on else R.drawable.handbrake_off)
            }
            else -> {
                // Valet mode default
                iv = findViewById(R.id.tabCarImageCarValetMode) as ImageView
                iv.setImageResource(if (carData.car_valetmode) R.drawable.carvaleton_clean else R.drawable.carvaletoff_clean)
            }
        }

        // Charge Port
        iv = findViewById(R.id.tabCarImageCarChargePortOpen) as ImageView
        if (!carData.car_chargeport_open) {
            iv.setVisibility(View.INVISIBLE)
        } else {
            iv.setVisibility(View.VISIBLE)
            if (carData.sel_vehicle_image.startsWith("car_twizy_")) {
                // Renault Twizy:
                iv.setImageResource(R.drawable.ol_car_twizy_chargeport)
            } else if (carData.sel_vehicle_image.startsWith("car_imiev_")) {
                // Mitsubishi i-MiEV:
                if (carData.car_charge_currentlimit_raw > 16) iv.setImageResource(R.drawable.ol_car_imiev_charge_quick) else iv.setImageResource(
                    R.drawable.ol_car_imiev_charge
                )
            } else if (carData.sel_vehicle_image.startsWith("car_kianiro_")) {
                // Kia Niro: use i-MiEV charge overlays
                if (carData.car_charge_mode == "performance") iv.setImageResource(R.drawable.ol_car_imiev_charge_quick) else iv.setImageResource(
                    R.drawable.ol_car_imiev_charge
                )
            } else if (carData.sel_vehicle_image.startsWith("car_mgzs_")) {
                // MG ZS: use i-MiEV charge overlays
                if (carData.car_charge_mode == "performance") iv.setImageResource(R.drawable.ol_car_imiev_charge_quick) else iv.setImageResource(
                    R.drawable.ol_car_imiev_charge
                )
            } else if (carData.sel_vehicle_image.startsWith("car_leaf")) {
                // Nissan Leaf: use Leaf charge overlay
                if (carData.car_charge_state == "charging") iv.setImageResource(R.drawable.ol_car_leaf_charge) else iv.setImageResource(
                    R.drawable.ol_car_leaf_nopower
                )
            } else if (carData.sel_vehicle_image.startsWith("car_vwup_")) {
                // VW e-Up:
                if (carData.car_charge_mode == "performance") iv.setImageResource(R.drawable.ol_car_vwup_chargeport_redflash) else if (carData.car_charge_state == "charging" || carData.car_charge_state == "topoff") //				else if (pCarData.car_charge_mode.equals("standard") || pCarData.car_charge_mode.equals("range"))
                    iv.setImageResource(R.drawable.ol_car_vwup_chargeport_green) else iv.setImageResource(
                    R.drawable.ol_car_vwup_chargeport_orange
                )
            } else if (carData.sel_vehicle_image.startsWith("car_zoe_") ||
                carData.sel_vehicle_image.startsWith("car_kangoo_") ||
                carData.sel_vehicle_image.startsWith("car_smart_") ||
                carData.sel_vehicle_image.startsWith("car_nrjk") ||
                carData.sel_vehicle_image.startsWith("car_niu_mqi_gt")
            ) {
                // Renault ZOE/Kangoo/Smart EQ
                when (carData.car_charge_state) {
                    "charging" -> iv.setImageResource(R.drawable.ol_car_zoe_chargeport_orange)
                    "stopped" -> iv.setImageResource(
                        R.drawable.ol_car_zoe_chargeport_red
                    )
                    "prepare" -> iv.setImageResource(R.drawable.ol_car_zoe_chargeport_yellow)
                    else -> iv.setImageResource(
                        R.drawable.ol_car_zoe_chargeport_green
                    )
                }
            } else if (carData.sel_vehicle_image.startsWith("car_boltev_")) {
                // Chevy Bolt EV
                if (carData.car_charge_mode == "performance") iv.setImageResource(R.drawable.ol_car_boltev_dcfc) else if (carData.car_charge_state == "charging") iv.setImageResource(
                    R.drawable.ol_car_boltev_ac
                ) else iv.setImageResource(R.drawable.ol_car_boltev_portopen)
            } else if (carData.sel_vehicle_image.startsWith("car_ampera_") ||
                carData.sel_vehicle_image.startsWith("car_holdenvolt_")
            ) {
                // Volt, Ampera
                when (carData.car_charge_state) {
                    "charging" -> iv.setImageResource(R.drawable.ol_car_voltampera_chargeport_orange)
                    "done" -> iv.setImageResource(
                        R.drawable.ol_car_voltampera_chargeport_green
                    )
                    else -> iv.setImageResource(R.drawable.ol_car_voltampera_chargeport_red)
                }
            } else {
                // Tesla Roadster:
                if (carData.car_charge_substate_i_raw == 0x07) {
                    // We need to connect the power cable
                    iv.setImageResource(R.drawable.roadster_outline_cu)
                } else if (carData.car_charge_state_i_raw == 0x0d || carData.car_charge_state_i_raw == 0x0e || carData.car_charge_state_i_raw == 0x101) {
                    // Preparing to charge, timer wait, or fake 'starting' state
                    iv.setImageResource(R.drawable.roadster_outline_ce)
                } else if (carData.car_charge_state_i_raw == 0x01 || carData.car_charge_state_i_raw == 0x02 || carData.car_charge_state_i_raw == 0x0f ||
                    carData.car_charging
                ) {
                    // Charging
                    iv.setImageResource(R.drawable.roadster_outline_cp)
                } else if (carData.car_charge_state_i_raw == 0x04) {
                    // Charging done
                    iv.setImageResource(R.drawable.roadster_outline_cd)
                } else if (carData.car_charge_state_i_raw >= 0x15 && carData.car_charge_state_i_raw <= 0x19) {
                    // Stopped
                    iv.setImageResource(R.drawable.roadster_outline_cs)
                } else {
                    // Fake 0x115 'stopping' state, or something else not understood
                    iv.setImageResource(R.drawable.roadster_outline_cp)
                }
            }
        }

        // A/C status:
        iv = findViewById(R.id.tabCarImageAC) as ImageView
        if (carData.car_hvac_on) {
            iv.setImageResource(R.drawable.ic_ac_on)
        } else {
            iv.setImageResource(R.drawable.ic_ac_off)
        }

        // Done.
    }

    companion object {
        private const val TAG = "CarFragment"
        private const val MI_WAKEUP = Menu.FIRST
        private const val MI_HL_01 = Menu.FIRST + 1
        private const val MI_HL_02 = Menu.FIRST + 2
        private const val MI_HL_03 = Menu.FIRST + 3
        private const val MI_HL_DEFAULT = Menu.FIRST + 4
        private const val MI_AC_ON = Menu.FIRST + 5
        private const val MI_AC_OFF = Menu.FIRST + 6
    }
}
