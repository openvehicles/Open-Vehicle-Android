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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
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
import com.openvehicles.OVMS.ui.witdet.SlideNumericView
import com.openvehicles.OVMS.ui.witdet.SwitcherView
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
                val eqBoxes: ImageView = findViewById(R.id.tabCarImageCarTempsBoxes) as ImageView
                eqBoxes.setImageResource(R.drawable.motortemp_letterbox_2)

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
        //	the Leaf, Smart and VW e-Up are the only cars providing command 26 up to now, so:
        if (carData.hasCommand(26)
            || carData.car_type == "NL"
            || carData.car_type == "SE"
            || carData.car_type == "SQ"
            || carData.car_type == "VWUP"
            || carData.car_type == "VWUP.T26") {
            // enable
            tabCarImageCarACBoxes.visibility = View.VISIBLE
            tabCarImageAC.visibility = View.VISIBLE
            if (carData.car_hvac_on) {
                tabCarImageAC.setImageResource(R.drawable.ic_ac_on)
            } else {
                tabCarImageAC.setImageResource(R.drawable.ic_ac_off)
            }
        } else {
            // disable
            tabCarImageCarACBoxes.visibility = View.INVISIBLE
            tabCarImageAC.visibility = View.INVISIBLE
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
        registerForContextMenu(findViewById(R.id.tabCarImagePlugin))
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
        val app_Car_ID = carData!!.sel_vehicleid
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
                    menu.add(0, MI_HL_02, 0, "Booster Timer reset")
                } else {
                    menu.add(0, MI_HL_01, 0, "1")
                    menu.add(0, MI_HL_02, 0, "2")
                    menu.add(0, MI_HL_03, 0, "3")
                }
                if(appPrefs.getData("option_firmware_enabled_" + app_Car_ID) == "1") {
                    menu.add(0, MI_HL_FW, 0, R.string.lb_options_firmware_update)
                }
                menu.add(R.string.Cancel)
            }
            R.id.tabCarImageAC -> {
                if (carData!!.car_type == "SQ") {
                    menu.setHeaderTitle(R.string.textAC)
                    menu.add(0, MI_AC_ON, 0, R.string.mi_ac_on)
                    menu.add(0, MI_AC_BON, 0, if(appPrefs.getData("booster_on_" + app_Car_ID) == "on") R.string.lb_booster_off else R.string.lb_booster_on)
                    menu.add(0, MI_AC_BT, 0, R.string.lb_booster_time)
                    menu.add(0, MI_AC_BW, 0, if(appPrefs.getData("booster_weekly_on_" + app_Car_ID) == "on") R.string.lb_booster_weekly_off else R.string.lb_booster_weekly_on)
                    menu.add(0, MI_AC_BDS, 0, R.string.lb_booster_day_sel)
                    menu.add(0, MI_AC_BTD, 0, if(appPrefs.getData("booster_btd_" + app_Car_ID) == "1") R.string.lb_booster_doubler_off else R.string.lb_booster_doubler_on)
                    menu.add(R.string.Cancel)
                } else {
                    menu.setHeaderTitle(R.string.textAC)
                    menu.add(0, MI_AC_ON, 0, R.string.mi_ac_on)
                    menu.add(0, MI_AC_OFF, 0, R.string.mi_ac_off)
                    menu.add(R.string.Cancel)
                }
            }
            R.id.tabCarImagePlugin -> {
                menu.setHeaderTitle(R.string.lb_options_plugin_btn)
                val app_Car_ID = carData!!.sel_vehicleid
                menu.add(0,plugin_repo_smarteq,0,if (appPrefs.getData("plugin_repo_smarteq_" + app_Car_ID) == "on") R.string.lb_options_plugin_deinst else R.string.lb_options_plugin_inst)
                if (appPrefs.getData("plugin_repo_smarteq_" + app_Car_ID) == "on") {
                    if (carData!!.car_type == "SQ") {
                        if (appPrefs.getData("plugin_ovmsmain_" + app_Car_ID) != "on") {
                            menu.add(0,plugin_ovmsmain,0,R.string.lb_plugin_ovmsmain)
                        }else{
                            menu.add(0,plugin_ovmsmain,0,R.string.lb_plugin_update)
                        }
                        menu.add(0,plugin_1,0,if (appPrefs.getData("plugin_1_" + app_Car_ID) == "on") R.string.lb_plugin_1_off else R.string.lb_plugin_1_on)
                    }
                    menu.add(0,plugin_2,0,if (appPrefs.getData("plugin_2_" + app_Car_ID) == "on") R.string.lb_plugin_2_off else R.string.lb_plugin_2_on)
                    menu.add(0,plugin_3,0,if (appPrefs.getData("plugin_3_" + app_Car_ID) == "on") R.string.lb_plugin_3_off else R.string.lb_plugin_3_on)
                    menu.add(0,plugin_4,0,if (appPrefs.getData("plugin_4_" + app_Car_ID) == "on") R.string.lb_plugin_4_off else R.string.lb_plugin_4_on)
                }
                menu.add(R.string.Cancel)
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        // "Booster" box:
        val app_Car_ID = carData!!.sel_vehicleid
        val tabCarImageBooster = findViewById(R.id.tabCarImageBooster) as ImageView
        val tabCarImageCalendar = findViewById(R.id.tabCarImageCalendar) as ImageView
        val tabInfoTextBoostertime = findViewById(R.id.tabInfoTextBoostertime) as TextView
        if(appPrefs.getData("booster_btd_" + app_Car_ID) == "1") tabCarImageBooster.setImageResource(R.drawable.heat_cool_2) else tabCarImageBooster.setImageResource(R.drawable.heat_cool)
        tabCarImageBooster.visibility = if (appPrefs.getData("booster_on_" + app_Car_ID) == "on") View.VISIBLE else View.INVISIBLE
        tabInfoTextBoostertime.visibility = if (appPrefs.getData("booster_on_" + app_Car_ID) == "on") View.VISIBLE else View.INVISIBLE
        tabInfoTextBoostertime.text = appPrefs.getData("booster_time_" + app_Car_ID)
        tabCarImageCalendar.visibility = if (appPrefs.getData("booster_weekly_on_" + app_Car_ID) == "on") View.VISIBLE else View.INVISIBLE
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
                if (carData!!.car_type == "SQ") {
                    appPrefs.saveData("booster_on_" + app_Car_ID, "off")
                    appPrefs.saveData("booster_time_" + app_Car_ID, "05:15")
                    appPrefs.saveData("booster_weekly_on_" + app_Car_ID, "off")
                    appPrefs.saveData("booster_btd_" + app_Car_ID, "0")
                    appPrefs.saveData("booster_time_h_" + app_Car_ID, "5")
                    appPrefs.saveData("booster_time_m_" + app_Car_ID, "15")
                    tabCarImageBooster.visibility = View.INVISIBLE
                    tabInfoTextBoostertime.visibility = View.INVISIBLE
                    tabCarImageCalendar.visibility = View.INVISIBLE
                    sendCommand(R.string.msg_issuing_homelink, "7,config set usr b.init no", this)
                    sendCommand(R.string.msg_issuing_homelink, "7,module reset", this)
                    true
                } else {
                    sendCommand(R.string.msg_issuing_homelink, "24,1", this)
                    true
                }
            }
            MI_HL_03 -> {
                sendCommand(R.string.msg_issuing_homelink, "24,2", this)
                true
            }
            MI_HL_DEFAULT -> {
                sendCommand(R.string.msg_issuing_homelink, "24", this)
                true
            }
            MI_HL_FW -> {
                val options = arrayOf("Dimitrie78", "Edge", "Eap", "Main")
                var checkedItem = -1 // To store the index of the selected item
                AlertDialog.Builder(requireActivity())
                    .setTitle(R.string.lb_plugin_firmware)
                    .setSingleChoiceItems(options, checkedItem) { dialog, which ->
                        checkedItem = which // Update the selected item index
                    }
                    .setNegativeButton(R.string.Cancel, null)
                    .setPositiveButton(android.R.string.ok) { dialog, which ->
                        when (checkedItem) {
                            0 -> sendCommand(R.string.lb_plugin_firmware_update, "7,ota flash http ovms.dimitrie.eu/firmware/ota/v3.3/smarteq/ovms3.bin", this)
                            1 -> sendCommand(R.string.lb_plugin_firmware_update, "7,ota flash http ovms.dexters-web.de/firmware/ota/v3.1/edge/ovms3.bin", this)
                            2 -> sendCommand(R.string.lb_plugin_firmware_update, "7,ota flash http ovms.dexters-web.de/firmware/ota/v3.1/eap/ovms3.bin", this)
                            3 -> sendCommand(R.string.lb_plugin_firmware_update, "7,ota flash http ovms.dexters-web.de/firmware/ota/v3.1/main/ovms3.bin", this)
                            else -> false
                        }
                    }
                    .show()
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
            MI_AC_BON -> {
                val state = appPrefs.getData("booster_on_" + app_Car_ID)
                val newState = if (state == "on") "off" else "on"
                tabCarImageBooster.visibility = if (newState == "on") View.VISIBLE else View.INVISIBLE
                tabInfoTextBoostertime.visibility = if (newState == "on") View.VISIBLE else View.INVISIBLE
                appPrefs.saveData("booster_on_" + app_Car_ID, newState)
                if (newState == "off") {
                    tabCarImageCalendar.visibility = View.INVISIBLE
                    appPrefs.saveData("booster_on_" + app_Car_ID, "off")
                    appPrefs.saveData("booster_weekly_on_" + app_Car_ID, "off")
                    sendCommand(
                        R.string.msg_issuing_climatecontrol,
                        "7,config set usr b.data 1,2,2,0,-1,-1,-1",
                        this
                    )
                } else {
                    appPrefs.saveData("booster_on_" + app_Car_ID, "on")
                    sendCommand(
                        R.string.msg_issuing_climatecontrol,
                        "7,config set usr b.data 1,1,0,0,-1,-1,-1",
                        this
                    )
                }
                true
            }
            MI_AC_BT -> {
                // create booster timer dialog:
                val dialogView = LayoutInflater.from(activity).inflate(
                    R.layout.dlg_booster_time, null
                )

                val booster_h = dialogView.findViewById<View>(R.id.booster_time_hour) as SlideNumericView?
                val booster_m = dialogView.findViewById<View>(R.id.booster_time_min) as SlideNumericView?
                val booster_sel = dialogView.findViewById<View>(R.id.booster_SwitcherView) as SwitcherView?
                if(appPrefs.getData("booster_time_h_" + app_Car_ID) != "") {
                    booster_h!!.value = appPrefs.getData("booster_time_h_" + app_Car_ID).toInt()
                    booster_m!!.value = appPrefs.getData("booster_time_m_" + app_Car_ID).toInt()
                }
                if(appPrefs.getData("booster_btd_" + app_Car_ID) != "") booster_sel!!.selected = appPrefs.getData("booster_btd_" + app_Car_ID).toInt()

                AlertDialog.Builder(requireActivity())
                    .setTitle(R.string.lb_booster_time)
                    .setView(dialogView)
                    .setNegativeButton(R.string.Cancel, null)
                    .setPositiveButton(
                        android.R.string.ok
                    ) { dlg, which ->
                        appPrefs.saveData("booster_on_" + app_Car_ID, "on")
                        tabCarImageBooster.visibility = View.VISIBLE
                        tabInfoTextBoostertime.visibility = View.VISIBLE
                        val appCompatDialog = dlg as AppCompatDialog
                        val booster_hd = appCompatDialog
                            .findViewById<View>(R.id.booster_time_hour) as SlideNumericView?
                        val booster_md = appCompatDialog
                            .findViewById<View>(R.id.booster_time_min) as SlideNumericView?
                        val booster_h = if(booster_hd!!.value < 10) String.format("0%d", booster_hd.value) else booster_hd.value
                        val booster_m = if(booster_md!!.value < 10) String.format("0%d", booster_md.value) else booster_md.value
                        val booster_sel = appCompatDialog
                            .findViewById<View>(R.id.booster_SwitcherView) as SwitcherView?
                        val booster_btd = booster_sel!!.selected
                        val time = "$booster_h:$booster_m"
                        val cmd = "7,config set usr b.data 1,1,0,$booster_h$booster_m,-1,-1,$booster_btd"
                        appPrefs.saveData("booster_time_" + app_Car_ID, time)
                        appPrefs.saveData("booster_time_h_" + app_Car_ID, "$booster_h")
                        appPrefs.saveData("booster_time_m_" + app_Car_ID, "$booster_m")
                        appPrefs.saveData("booster_btd_" + app_Car_ID, "$booster_btd")
                        tabInfoTextBoostertime.text = time
                        if("$booster_btd" == "1") tabCarImageBooster.setImageResource(R.drawable.heat_cool_2) else tabCarImageBooster.setImageResource(R.drawable.heat_cool)
                        sendCommand(
                            R.string.msg_issuing_climatecontrol, cmd,
                            this@CarFragment)
                    }
                    .show()
                true
            }
            MI_AC_BW -> {
                val state_weekly = appPrefs.getData("booster_weekly_on_" + app_Car_ID)
                val newState = if (state_weekly == "on") "off" else "on"
                if (newState == "off") {
                    appPrefs.saveData("booster_on_" + app_Car_ID, "off")
                    appPrefs.saveData("booster_weekly_on_" + app_Car_ID, "off")
                    tabCarImageBooster.visibility = View.INVISIBLE
                    tabInfoTextBoostertime.visibility = View.INVISIBLE
                    tabCarImageCalendar.visibility = View.INVISIBLE
                    sendCommand(
                        R.string.msg_issuing_climatecontrol,
                        "7,config set usr b.data 1,2,2,0,-1,-1,-1",
                        this
                    )
                } else {
                    appPrefs.saveData("booster_on_" + app_Car_ID, "on")
                    appPrefs.saveData("booster_weekly_on_" + app_Car_ID, "on")
                    tabCarImageBooster.visibility = View.VISIBLE
                    tabInfoTextBoostertime.visibility = View.VISIBLE
                    tabCarImageCalendar.visibility = View.VISIBLE
                    sendCommand(
                        R.string.msg_issuing_climatecontrol,
                        "7,config set usr b.data 1,1,1,0,-1,-1,-1",
                        this
                    )
                }
                true
            }
            MI_AC_BDS -> {
                // show booster start Day dialog:
                val dialogView = LayoutInflater.from(activity).inflate(
                    R.layout.dlg_booster_days, null
                )

                val booster_sel_s = dialogView.findViewById<View>(R.id.booster_DayViewStart) as SwitcherView?
                val booster_sel_e = dialogView.findViewById<View>(R.id.booster_DayViewEnd) as SwitcherView?
                if(appPrefs.getData("booster_day_start_" + app_Car_ID) != "") booster_sel_s!!.selected = appPrefs.getData("booster_day_start_" + app_Car_ID).toInt()
                if(appPrefs.getData("booster_day_end_" + app_Car_ID) != "") booster_sel_e!!.selected = appPrefs.getData("booster_day_end_" + app_Car_ID).toInt()

                AlertDialog.Builder(requireActivity())
                    .setTitle(R.string.lb_booster_day_sel)
                    .setView(dialogView)
                    .setNegativeButton(R.string.Cancel, null)
                    .setPositiveButton(
                        android.R.string.ok
                    ) { dlg, which ->
                        appPrefs.saveData("booster_on_" + app_Car_ID, "on")
                        appPrefs.saveData("booster_weekly_on_" + app_Car_ID, "on")
                        tabCarImageBooster.visibility = View.VISIBLE
                        tabInfoTextBoostertime.visibility = View.VISIBLE
                        tabCarImageCalendar.visibility = View.VISIBLE
                        val appCompatDialog = dlg as AppCompatDialog
                        val booster_sel_s = appCompatDialog
                            .findViewById<View>(R.id.booster_DayViewStart) as SwitcherView?
                        val booster_sel_e = appCompatDialog
                            .findViewById<View>(R.id.booster_DayViewEnd) as SwitcherView?
                        val booster_start = booster_sel_s!!.selected
                        val booster_end = booster_sel_e!!.selected
                        val cmd = "7,config set usr b.data 1,1,1,0,$booster_start,$booster_end,-1"
                        appPrefs.saveData("booster_day_start_" + app_Car_ID, "$booster_start")
                        appPrefs.saveData("booster_day_end_" + app_Car_ID, "$booster_end")
                        sendCommand(
                            R.string.msg_issuing_climatecontrol, cmd,
                            this@CarFragment)
                    }
                    .show()
                true
            }
            MI_AC_BTD -> {
                val state = appPrefs.getData("booster_btd_" + app_Car_ID)
                val newState = if (state == "1") "0" else "1"
                if (newState == "0") {
                    appPrefs.saveData("booster_btd_" + app_Car_ID, "0")
                    tabCarImageBooster.setImageResource(R.drawable.heat_cool)
                    sendCommand(
                        R.string.msg_issuing_climatecontrol,
                        "7,config set usr b.data 1,0,0,0,-1,-1,-1",
                        this
                    )
                } else {
                    appPrefs.saveData("booster_btd_" + app_Car_ID, "1")
                    tabCarImageBooster.setImageResource(R.drawable.heat_cool_2)
                    sendCommand(
                        R.string.msg_issuing_climatecontrol,
                        "7,config set usr b.data 1,0,0,0,-1,-1,1",
                        this
                    )
                }
                true
            }
            plugin_repo_smarteq -> {
                if (appPrefs.getData("plugin_repo_smarteq_" + app_Car_ID) == "on") {
                    appPrefs.saveData("plugin_repo_smarteq_" + app_Car_ID, "off")
                    sendCommand(R.string.lb_options_plugin_deinst, "7,plugin repo remove SmartEQ", this)
                    true
                } else {
                    appPrefs.saveData("plugin_repo_smarteq_" + app_Car_ID, "on")
                    sendCommand(R.string.lb_options_plugin_inst, "7,plugin repo install SmartEQ http://s418145198.online.de/plugins/", this)
                    true
                }
            }
            plugin_ovmsmain -> {
                if (appPrefs.getData("plugin_ovmsmain_" + app_Car_ID) != "on") {
                    appPrefs.saveData("plugin_ovmsmain_" + app_Car_ID, "on")
                    sendCommand(R.string.lb_plugin_ovmsmain,"7,vfs rm /store/scripts/ovmsmain.js",this)
                    sendCommand(R.string.lb_plugin_ovmsmain, "7,config rm usr *", this)
                    sendCommand(R.string.lb_plugin_ovmsmain, "7,script reload", this)
                    true
                } else {
                    sendCommand(R.string.lb_plugin_update, "7,plugin update", this)
                    true
                }
            }
            plugin_1 -> {
                if (appPrefs.getData("plugin_1_" + app_Car_ID) == "on") {
                    appPrefs.saveData("plugin_1_" + app_Car_ID, "off")
                    sendCommand(R.string.lb_plugin_1_off, "7,plugin disable xsq_v2data", this)
                    true
                } else {
                    appPrefs.saveData("plugin_1_" + app_Car_ID, "on")
                    sendCommand(R.string.lb_plugin_1_on, "7,plugin install xsq_v2data", this)
                    true
                }
            }
            plugin_2 -> {
                if (appPrefs.getData("plugin_2_" + app_Car_ID) == "on") {
                    appPrefs.saveData("plugin_2_" + app_Car_ID, "off")
                    sendCommand(R.string.lb_plugin_2_off, "7,plugin disable scheduled_booster", this)
                    true
                } else {
                    appPrefs.saveData("plugin_2_" + app_Car_ID, "on")
                    sendCommand(R.string.lb_plugin_2_on, "7,plugin install scheduled_booster", this)
                    true
                }
            }
            plugin_3 -> {
                if (appPrefs.getData("plugin_3_" + app_Car_ID) == "on") {
                    appPrefs.saveData("plugin_3_" + app_Car_ID, "off")
                    sendCommand(R.string.lb_plugin_3_off, "7,plugin disable gps_onoff", this)
                    true
                } else {
                    appPrefs.saveData("plugin_3_" + app_Car_ID, "on")
                    sendCommand(R.string.lb_plugin_3_on, "7,plugin install gps_onoff", this)
                    true
                }
            }
            plugin_4 -> {
                if (appPrefs.getData("plugin_4_" + app_Car_ID) == "on") {
                    appPrefs.saveData("plugin_4_" + app_Car_ID, "off")
                    sendCommand(R.string.lb_plugin_4_off, "7,plugin disable booster_12V", this)
                    true
                } else {
                    appPrefs.saveData("plugin_4_" + app_Car_ID, "on")
                    sendCommand(R.string.lb_plugin_4_on, "7,plugin install booster_12V", this)
                    true
                }
            }
            else -> false
        }
    }

    override fun onResultCommand(result: Array<String>) {
        if (result.size <= 1) {
            return
        }

        //val command = result[0].toInt()
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
                tv.text = getText(R.string.min1)
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
        } else if (carData.sel_vehicle_image.startsWith("car_smart_44")) {
            // smart ED: one ol image for all colors:
            iv.setImageResource(R.drawable.ol_car_vwup_black)
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
            iv.setImageResource(R.drawable.ol_car_nrjkexperia) // TODO: Ribelle top view
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

        // plugin menu
        val btn_plugin_menu = findViewById(R.id.tabCarImagePlugin) as ImageView
        btn_plugin_menu.visibility = if(appPrefs.getData("option_plugin_enabled_" + carData.sel_vehicleid) == "1") View.VISIBLE else View.INVISIBLE

        // "Ambient" box:
        iv = findViewById(R.id.tabCarImageCarAmbientBox) as ImageView
        val label: TextView = findViewById(R.id.tabCarTextAmbientLabel) as TextView
        tv = findViewById(R.id.tabCarTextAmbient) as TextView
        when (carData.stale_ambient_temp) {
            DataStale.NoValue -> {
                iv.visibility = View.INVISIBLE
                label.visibility = View.INVISIBLE
                tv.text = null
            }
            DataStale.Stale -> {
                iv.visibility = View.VISIBLE
                label.visibility = View.VISIBLE
                tv.text = carData.car_temp_ambient
                tv.setTextColor(-0x7f7f80)
            }
            else -> {
                iv.visibility = View.VISIBLE
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
            iv.visibility = View.INVISIBLE
            fltv.text = null
            frtv.text = null
            rltv.text = null
            rrtv.text = null
            fltvv.text = null
            frtvv.text = null
            rltvv.text = null
            rrtvv.text = null
        } else {
            iv.visibility = View.VISIBLE

            if ((carData.car_type == "SQ")&&(val1!![0]=="FL")) {
                // fix the wrong side of the tires
                fltv.text = getString(R.string.fl_tpms)
                frtv.text = getString(R.string.fr_tpms)
                rltv.text = getString(R.string.rl_tpms)
                rrtv.text = getString(R.string.rr_tpms)
                frtvv.text = val2!![0]
                fltvv.text = val2[1]
                rrtvv.text = val2[2]
                rltvv.text = val2[3]
            }else if ((carData.car_type == "SQ")&&(val1!![0]!="FL")) {
                // fix the wrong side of the tires
                fltv.text = getString(R.string.fl_tpms)
                frtv.text = getString(R.string.fr_tpms)
                rltv.text = getString(R.string.rl_tpms)
                rrtv.text = getString(R.string.rr_tpms)
                frtvv.text = val1!![0]
                fltvv.text = val1[1]
                rrtvv.text = val1[2]
                rltvv.text = val1[3]
            }else {
                fltv.text = val1!![0]
                frtv.text = val1[1]
                rltv.text = val1[2]
                rrtv.text = val1[3]
                fltvv.text = val2!![0]
                frtvv.text = val2[1]
                rltvv.text = val2[2]
                rrtvv.text = val2[3]
            }

            val trans1 = if ((carData.car_type != "SQ")&&(stale1 == DataStale.Stale)) -0x80000000 else -0x1000000
            val trans2 = if ((carData.car_type != "SQ")&&(stale1 == DataStale.Stale)) -0x80000000 else -0x1000000
            val alertcol = intArrayOf(0xFFFFFF, 0xFFAA44, 0xFF4444)

            if (carData.car_type == "SQ") {
                // fix the wrong side of the tires
                frtv.setTextColor(trans1 or alertcol[alert!![0]])
                fltv.setTextColor(trans1 or alertcol[alert[1]])
                rrtv.setTextColor(trans1 or alertcol[alert[2]])
                rltv.setTextColor(trans1 or alertcol[alert[3]])
                if (val2.contentEquals(carData.car_tpms_alert)) {
                    alertcol[0] = 0x44FF44
                }
                frtvv.setTextColor(trans2 or alertcol[alert[0]])
                fltvv.setTextColor(trans2 or alertcol[alert[1]])
                rrtvv.setTextColor(trans2 or alertcol[alert[2]])
                rltvv.setTextColor(trans2 or alertcol[alert[3]])
            }else {
                fltv.setTextColor(trans1 or alertcol[alert!![0]])
                frtv.setTextColor(trans1 or alertcol[alert[1]])
                rltv.setTextColor(trans1 or alertcol[alert[2]])
                rrtv.setTextColor(trans1 or alertcol[alert[3]])
                if (val2.contentEquals(carData.car_tpms_alert)) {
                    alertcol[0] = 0x44FF44
                }
                fltvv.setTextColor(trans2 or alertcol[alert[0]])
                frtvv.setTextColor(trans2 or alertcol[alert[1]])
                rltvv.setTextColor(trans2 or alertcol[alert[2]])
                rrtvv.setTextColor(trans2 or alertcol[alert[3]])
            }
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
            || carData.car_type == "NL") {
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
        } else if (carData.car_type == "SQ"){
            // switches the box from PEM to Battery energy
            pemtvl.setText(R.string.textEnergy)
            if (carData.stale_car_temps == DataStale.NoValue) {
                pemtv.text = ""
            } else {
                pemtv.text = String.format("%.1fkWh",carData.car_charge_kwhconsumed)
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

        // "Booster" box:
        val tabCarImageBooster = findViewById(R.id.tabCarImageBooster) as ImageView
        val tabCarImageCalendar = findViewById(R.id.tabCarImageCalendar) as ImageView
        val tabInfoTextBoostertime = findViewById(R.id.tabInfoTextBoostertime) as TextView
        val app_Car_ID = carData.sel_vehicleid
        if(appPrefs.getData("booster_btd_" + carData.sel_vehicleid) == "1") tabCarImageBooster.setImageResource(R.drawable.heat_cool_2) else tabCarImageBooster.setImageResource(R.drawable.heat_cool)
        tabCarImageBooster.visibility = if (appPrefs.getData("booster_on_" + app_Car_ID) == "on") View.VISIBLE else View.INVISIBLE
        tabInfoTextBoostertime.visibility = if (appPrefs.getData("booster_on_" + app_Car_ID) == "on") View.VISIBLE else View.INVISIBLE
        tabInfoTextBoostertime.text = appPrefs.getData("booster_time_" + app_Car_ID)
        tabCarImageCalendar.visibility = if (appPrefs.getData("booster_weekly_on_" + app_Car_ID) == "on") View.VISIBLE else View.INVISIBLE


        // "SoC" box:
        val soctvl = findViewById(R.id.tabCarTextSoCLabel) as TextView
        val soctv =findViewById(R.id.tabCarTextSoC) as TextView
        if (carData.car_type == "SQ"){
            soctvl.visibility = View.VISIBLE
            soctv.visibility = View.VISIBLE

            if (carData.stale_status == DataStale.NoValue) {
                soctv.text = ""
            } else {
                soctv.text = String.format("%.1f%%", carData.car_soc_raw)
                if (carData.stale_car_temps == DataStale.Stale) {
                    soctv.setTextColor(-0x7f7f80)
                } else {
                    soctv.setTextColor(-0x1)
                }
            }
        }

        // "SoH" box:
        val sohtvl = findViewById(R.id.tabCarTextSoHLabel) as TextView
        val sohtv =findViewById(R.id.tabCarTextSoH) as TextView
        if (carData.car_type == "SQ"){
            sohtvl.visibility = View.VISIBLE
            sohtv.visibility = View.VISIBLE

            if (carData.stale_status == DataStale.NoValue) {
                sohtv.text = ""
            } else {
                sohtv.text = String.format("%.1f%%", carData.car_soh)
                if (carData.stale_car_temps == DataStale.Stale) {
                    sohtv.setTextColor(-0x7f7f80)
                } else {
                    sohtv.setTextColor(-0x1)
                }
            }
        }

        // Temperatures
        val batterytvl = findViewById(R.id.tabCarTextChargerLabel) as TextView
        val batterytv = findViewById(R.id.tabCarTextBattery) as TextView
        val chargertv = findViewById(R.id.tabCarTextCharger) as TextView
        if (carData.car_type == "SQ") {
            // switches the box from battery to cabin
            batterytvl.setText(R.string.textCAB)
            if (carData.stale_car_temps == DataStale.NoValue) {

                batterytv.text = ""
                chargertv.text = ""
            } else {
                batterytv.text = carData.car_temp_battery
                chargertv.text = carData.car_temp_cabin
                if (carData.stale_car_temps == DataStale.Stale) {
                    batterytv.setTextColor(-0x7f7f80)
                    chargertv.setTextColor(-0x7f7f80)
                } else {
                    batterytv.setTextColor(-0x1)
                    chargertv.setTextColor(-0x1)
                }
            }
        } else {
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
        }
        var st: String
        var ss: SpannableString

        // Odometer
        st = String.format("%.1f%s", carData.car_odometer_raw / 10, carData.car_distance_units)
        ss = SpannableString(st)
        ss.setSpan(RelativeSizeSpan(0.67f), st.indexOf(carData.car_distance_units), st.length, 0)
        tv = findViewById(R.id.tabCarTextOdometer) as TextView
        tv.text = ss
        // move the Odometer text to the right position
        if (carData.car_type == "SQ") {
            tv.translationY = "-165".toFloat()
        }

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
        iv.visibility = if (carData.car_bonnet_open) View.VISIBLE else View.INVISIBLE
        if (carData.car_type.startsWith("VA")) {
            // Volt, Ampera
            iv.setImageResource(R.drawable.voltampera_outline_hd)
        }

        // Doors, Trunks & Headlights:
        if (carData.sel_vehicle_image.startsWith("car_zoe_")) {
            // Left Door Zoe
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.zoe_outline_ld)

            // Right Door Zoe
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.zoe_outline_rd)

            // Rear Left Door Zoe
            iv = findViewById(R.id.tabCarImageCarRearLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_rearleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.zoe_outline_rld)

            // Rear Right Door Zoe
            iv = findViewById(R.id.tabCarImageCarRearRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_rearrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.zoe_outline_rrd)

            // Trunk Zoe
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.visibility = if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.zoe_outline_tr)

            // Headlights Zoe
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.visibility = if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.zoe_carlights)
        } else if (carData.sel_vehicle_image.startsWith("car_mgzs_")) {
            // Left Door MGZS
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.mgzs_outline_ld)

            // Right Door MGZS
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.mgzs_outline_rd)

            // Rear Left Door MGZS
            iv = findViewById(R.id.tabCarImageCarRearLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_rearleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.mgzs_outline_rld)

            // Rear Right Door MGZS
            iv = findViewById(R.id.tabCarImageCarRearRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_rearrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.mgzs_outline_rrd)

            // Trunk MGZS
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.visibility = if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.mgzs_outline_tr)

            // Headlights MGZS
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.visibility = if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.mgzs_carlights)
        } else if ((carData.sel_vehicle_image.startsWith("car_smart_"))||(!carData.sel_vehicle_image.startsWith("car_smart_44_")))  {
            // Left Door Smart
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.smart_outline_ld)

            // Right Door Smart
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.smart_outline_rd)

            // Trunk Smart
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.visibility = if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.smart_outline_tr)

            // Headlights Smart
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.visibility = if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.smart_carlights)
        } else if (carData.sel_vehicle_image.startsWith("car_leaf")) {
            // Left Door Leaf
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.leaf_outline_ld)

            // Right Door Leaf
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.leaf_outline_rd)

            // Rear Left Door Leaf
            iv = findViewById(R.id.tabCarImageCarRearLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_rearleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.leaf_outline_rld)

            // Rear Right Door Leaf
            iv = findViewById(R.id.tabCarImageCarRearRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_rearrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.leaf_outline_rrd)

            // Trunk Leaf
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.visibility = if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.leaf_outline_tr)

            // Headlights Leaf
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.visibility = if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE
            if (carData.sel_vehicle_image.startsWith("car_leaf2")) {
                iv.setImageResource(R.drawable.leaf2_carlights)
            } else iv.setImageResource(R.drawable.leaf_carlights)
        } else if ((carData.sel_vehicle_image.startsWith("car_vwup_"))||(carData.sel_vehicle_image.startsWith("car_smart_44_"))) {
            // Left Door VW e-Up
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.vwup_outline_ld)

            // Right Door VW e-Up
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.vwup_outline_rd)

            // Rear Left Door VW e-Up
            iv = findViewById(R.id.tabCarImageCarRearLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_rearleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.vwup_outline_rld)

            // Rear Right Door VW e-Up
            iv = findViewById(R.id.tabCarImageCarRearRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_rearrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.vwup_outline_rrd)

            // Trunk VW e-Up
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.visibility = if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.vwup_outline_tr)

            // Headlights VW e-Up
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.visibility = if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.vwup_carlights)
        } else if (carData.sel_vehicle_image.startsWith("car_ampera_")
            || carData.sel_vehicle_image.startsWith("car_holdenvolt_")) {
            // Left Door Volt, Ampera
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.voltampera_outline_ld)

            // Right Door Volt, Ampera
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.voltampera_outline_rd)

            // Rear Left Door Volt, Ampera
            iv = findViewById(R.id.tabCarImageCarRearLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_rearleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.voltampera_outline_rld)

            // Rear Right Door Volt, Ampera
            iv = findViewById(R.id.tabCarImageCarRearRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_rearrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.voltampera_outline_rrd)

            // Trunk Volt, Ampera
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.visibility = if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.voltampera_outline_tr)

            // Headlights Volt, Ampera
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.visibility = if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.voltampera_carlights)
        } else if (carData.sel_vehicle_image.startsWith("car_kangoo_")) {
            // Left Door Kangoo
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.kangoo_outline_ld)

            // Right Door Kangoo
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.kangoo_outline_rd)

            // Rear Left Door Kangoo
            iv = findViewById(R.id.tabCarImageCarRearLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_rearleftdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.kangoo_outline_rld)

            // Rear Right Door Kangoo
            iv = findViewById(R.id.tabCarImageCarRearRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_rearrightdoor_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.kangoo_outline_rrd)

            // Trunk Kangoo
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.visibility = if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.kangoo_outline_tr)

            // Headlights Kangoo
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.visibility = if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE
            iv.setImageResource(R.drawable.kangoo_carlights)
        } else {
            // Left Door
            iv = findViewById(R.id.tabCarImageCarLeftDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontleftdoor_open) View.VISIBLE else View.INVISIBLE

            // Right Door
            iv = findViewById(R.id.tabCarImageCarRightDoorOpen) as ImageView
            iv.visibility = if (carData.car_frontrightdoor_open) View.VISIBLE else View.INVISIBLE

            // Trunk
            iv = findViewById(R.id.tabCarImageCarTrunkOpen) as ImageView
            iv.visibility = if (carData.car_trunk_open) View.VISIBLE else View.INVISIBLE

            // Headlights
            iv = findViewById(R.id.tabCarImageCarHeadlightsON) as ImageView
            iv.visibility = if (carData.car_headlights_on) View.VISIBLE else View.INVISIBLE
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
            iv.visibility = View.INVISIBLE
        } else {
            iv.visibility = View.VISIBLE
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
                carData.sel_vehicle_image.startsWith("car_smart_")
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
            } else if (carData.sel_vehicle_image.startsWith("car_nrjk")) {
                // TODO
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
                } else if (carData.car_charge_state_i_raw in 0x15..0x19) {
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
        private const val MI_HL_FW = Menu.FIRST + 5
        private const val MI_AC_ON = Menu.FIRST + 6
        private const val MI_AC_OFF = Menu.FIRST + 7
        private const val MI_AC_BON = Menu.FIRST + 8
        private const val MI_AC_BT = Menu.FIRST + 9
        private const val MI_AC_BW = Menu.FIRST + 10
        private const val MI_AC_BDS = Menu.FIRST + 11
        private const val MI_AC_BTD = Menu.FIRST + 12
        private const val plugin_repo_smarteq = Menu.FIRST + 13
        private const val plugin_ovmsmain = Menu.FIRST +14
        private const val plugin_1 = Menu.FIRST + 15
        private const val plugin_2 = Menu.FIRST + 16
        private const val plugin_3 = Menu.FIRST + 17
        private const val plugin_4 = Menu.FIRST + 18
    }
}
