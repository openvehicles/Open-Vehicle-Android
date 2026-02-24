package com.openvehicles.OVMS.ui

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Gallery
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragmentActivity.Companion.show
import com.openvehicles.OVMS.ui.settings.CarInfoFragment
import com.openvehicles.OVMS.ui.settings.CellularStatsFragment
import com.openvehicles.OVMS.ui.settings.FeaturesFragment
import com.openvehicles.OVMS.ui.settings.GlobalOptionsFragment
import com.openvehicles.OVMS.ui.utils.Ui
import com.openvehicles.OVMS.ui.utils.Ui.getDrawableIdentifier
import com.openvehicles.OVMS.ui.witdet.ReversedSeekBar
import com.openvehicles.OVMS.ui.witdet.ScaleLayout
import com.openvehicles.OVMS.ui.witdet.SlideNumericView
import com.openvehicles.OVMS.ui.witdet.SwitcherView
import com.openvehicles.OVMS.utils.CarsStorage
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

class InfoFragment : BaseFragment(), View.OnClickListener, OnResultCommandListener {

    private lateinit var carsStorage: CarsStorage
    private var carData: CarData? = null
    private lateinit var carSelect: Gallery
    private var carSelectPos = 0
    private lateinit var handler: Handler
    private var carChanger: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // init car data:
        carsStorage = CarsStorage
        carData = carsStorage.getSelectedCarData()

        // inflate layout:
        val rootView = inflater.inflate(R.layout.fragment_info, null)

        // init car selector:
        handler = Handler(Looper.getMainLooper())
        carSelect = rootView.findViewById<View>(R.id.tabInfoImageCar) as Gallery
        carSelectPos = 0
        carChanger = null
        carSelect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == carSelectPos) {
                    // no user action
                    return
                }
                try {
                    carSelectPos = position
                    // while mCarChanger is defined, updates are inhibited to avoid
                    // interference from scripts or incoming updates during interaction
                    if (carChanger != null) {
                        handler.removeCallbacks(carChanger!!)
                    }
                    carChanger = Runnable {
                        try {
                            val carData = carsStorage.getStoredCars()[carSelectPos]
                            if (carData.sel_vehicleid != this@InfoFragment.carData?.sel_vehicleid) {
                                Log.d(
                                    TAG,
                                    "onItemSelected: pos=" + carSelectPos + ", id=" + carData.sel_vehicleid
                                )
                                changeCar(carData)
                                carChanger = null // car is changed, allow updates
                                update(carData) // …and do a first update
                            }
                        } catch (error : Exception) {
                            Log.e(TAG, "carChanger: selecting pos $carSelectPos failed: $error")
                        }
                    }
                    handler.postDelayed(carChanger!!, 750)
                } catch (e: Exception) {
                    Log.e(TAG, "Car selection: position invalid: $position")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // nop
            }
        }

        // init ScaleLayout:
        val scaleLayout = rootView.findViewById<View>(R.id.scaleLayout) as ScaleLayout
        scaleLayout.setOnScale {
            val sb = scaleLayout.findViewById<View>(R.id.tabInfoSliderChargerControl) as SeekBar
            val lp = sb.layoutParams as ScaleLayout.LayoutParams
            val srcBmp = BitmapFactory.decodeResource(
                scaleLayout.context.resources,
                R.drawable.charger_button
            )
            var tw = (srcBmp.getWidth() * (lp.height / srcBmp.getHeight()))
            var th = lp.height
            if (tw < 40) {
                tw = 61
            } // Sane lower limit
            if (th < 10) {
                th = 22
            } // Sane lower limit
            val dstBmp = Bitmap.createScaledBitmap(
                srcBmp, tw,
                lp.height, true
            )
            srcBmp.recycle()
            val drw = BitmapDrawable(
                scaleLayout
                    .context.resources, dstBmp
            )
            sb.setThumb(drw)
            // sb.setThumbOffset(dstBmp.getWidth() / 9);
        }
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.battery_options, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (carData != null) {
            menu.findItem(R.id.mi_battery_stats).setVisible(carData!!.car_type == "RT")
            menu.findItem(R.id.mi_power_stats).setVisible(carData!!.car_type == "RT")
        }
    }

    override fun onDestroyView() {
        cancelCommand()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        carSelect.setAdapter(CarSelectAdapter())
        carSelectPos = carsStorage.getSelectedCarIndex()
        carSelect.setSelection(carSelectPos)
        Log.d(TAG, "onResume: pos=" + carSelectPos + ", id=" + carData!!.sel_vehicleid)
    }

    override fun update(carData: CarData?) {
        // while mCarChanger is defined, updates are inhibited to avoid
        // interference from scripts or incoming updates during interaction
        if (carChanger != null) {
            Log.d(TAG, "update: inhibited, UI car change is in progress")
            return
        }

        // store pointer to new car:
        this.carData = carData

        // update UI:
        compatActivity!!.invalidateOptionsMenu()
        updateLastUpdatedView(carData)
        updateCarInfoView(carData!!)
        updateChargeAlerts()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        findViewById(R.id.tabInfoTextSOC).setOnClickListener(this)
        findViewById(R.id.tabInfoTextChargeMode).setOnClickListener(this)
        findViewById(R.id.tabInfoImageBatteryChargingOverlay).setOnClickListener(this)
        findViewById(R.id.tabInfoImageBatteryOverlay).setOnClickListener(this)

        val bar = findViewById(R.id.tabInfoSliderChargerControl) as ReversedSeekBar
        bar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

            private var startProgress = 0

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                var progress = seekBar.progress
                if (progress > 50) {
                    seekBar.progress = 100
                    progress = 100
                } else {
                    seekBar.progress = 0
                    progress = 0
                }
                if (startProgress == progress) return
                if (progress == 0) chargerConfirmStart() else chargerConfirmStop()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                startProgress = seekBar.progress
            }

            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
            }
        })

    }

    private fun chargerConfirmStart() {
        val bar = findViewById(R.id.tabInfoSliderChargerControl) as ReversedSeekBar
        // create & open dialog:
        val dialogView = LayoutInflater.from(activity).inflate(
            R.layout.dlg_charger_confirm, null
        )
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.lb_charger_confirm_start)
            .setView(dialogView)
            .setNegativeButton(R.string.Cancel) { dlg, which -> bar.progress = 100 }
            .setPositiveButton(android.R.string.ok) { dlg, which -> startCharge() }
            .show()
    }

    private fun chargerConfirmStop() {
        val bar = findViewById(R.id.tabInfoSliderChargerControl) as ReversedSeekBar
        // create & open dialog:
        val dialogView = LayoutInflater.from(activity).inflate(
            R.layout.dlg_charger_confirm, null
        )
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.lb_charger_confirm_stop)
            .setView(dialogView)
            .setNegativeButton(R.string.Cancel) { dlg, which -> bar.progress = 0 }
            .setPositiveButton(android.R.string.ok) { dlg, which -> stopCharge() }
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            R.id.mi_battery_stats -> {
                show(
                    requireActivity(), BatteryFragment::class.java, null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_power_stats -> {
                show(
                    requireActivity(), PowerFragment::class.java, null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_show_carinfo -> {
                show(
                    requireActivity(), CarInfoFragment::class.java, null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_aux_battery_stats -> {
                show(
                    requireActivity(), AuxBatteryFragment::class.java, null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_show_features -> {
                show(
                    requireActivity(), FeaturesFragment::class.java, null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_globaloptions -> {
                show(
                    requireActivity(), GlobalOptionsFragment::class.java, null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_cellular_usage -> {
                show(
                    requireActivity(), CellularStatsFragment::class.java, null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return true
            }
            R.id.mi_app_about -> {
                (activity as MainActivity).showVersion()
                return true
            }
            else -> return false
        }
    }

    override fun onClick(v: View) {
        chargerSetting()
    }

    override fun onResultCommand(result: Array<String>) {
        if (result.size <= 1) return
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

    private fun startCharge() {
        sendCommand(R.string.msg_starting_charge, "11", this)
        carData!!.car_charge_linevoltage_raw = 0f
        carData!!.car_charge_current_raw = 0f
        carData!!.car_charge_state_s_raw = "starting"
        carData!!.car_charge_state_i_raw = 0x101
        updateCarInfoView(carData!!)
    }

    private fun stopCharge() {
        sendCommand(R.string.msg_stopping_charge, "12", this)
        carData!!.car_charge_linevoltage_raw = 0f
        carData!!.car_charge_current_raw = 0f
        carData!!.car_charge_state_s_raw = "stopping"
        carData!!.car_charge_state_i_raw = 0x115
        updateCarInfoView(carData!!)
    }

    private fun chargerSetting() {
        when (carData!!.car_type) {
            "RT" -> {
                chargerSettingRenaultTwizy()
            }
            "VWUP" -> {
                chargerSettingVWUP()
            }
            "SQ" -> {
                // nothing to do
            }
            else -> {
                chargerSettingDefault()
            }
        }
    }

    private fun chargerSettingDefault() {
        val content = LayoutInflater.from(activity).inflate(
            R.layout.dlg_charger, null
        )
        val sw = content.findViewById<View>(R.id.sv_state) as SwitcherView
        sw.setOnChangeListener(object : Ui.OnChangeListener<SwitcherView> {
            override fun onAction(data: SwitcherView) {
                val txtInfo = (data.parent as View)
                    .findViewById<View>(R.id.txt_info) as TextView
                when (data.selected) {
                    2 -> txtInfo.setText(R.string.msg_charger_range)
                    3 -> txtInfo.setText(R.string.msg_charger_perform)
                    else -> txtInfo.text = null
                }
            }
        })
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.lb_charger_setting)
            .setView(content)
            .setNegativeButton(R.string.Cancel, null)
            .setPositiveButton(
                android.R.string.ok
            ) { dlg, which ->
                val appCompatDialog = dlg as AppCompatDialog
                val switcherView = appCompatDialog
                    .findViewById<View>(R.id.sv_state) as SwitcherView?
                val snv = appCompatDialog
                    .findViewById<View>(R.id.snv_amps) as SlideNumericView?
                var ncm = switcherView!!.selected
                if (ncm >= 2) {
                    ncm++
                }
                val ncl = snv!!.value
                if (ncm != carData!!.car_charge_mode_i_raw
                    && ncl.toFloat() != carData!!.car_charge_currentlimit_raw
                ) {
                    sendCommand(
                        R.string.msg_setting_charge_mc, String.format("16,%d,%d", ncm, ncl),
                        this@InfoFragment
                    )
                } else if (ncm != carData!!.car_charge_mode_i_raw) {
                    sendCommand(
                        R.string.msg_setting_charge_m, String.format("10,%d", ncm),
                        this@InfoFragment
                    )
                } else if (ncl.toFloat() != carData!!.car_charge_currentlimit_raw) {
                    sendCommand(
                        R.string.msg_setting_charge_c, String.format("15,%d", ncl),
                        this@InfoFragment
                    )
                }
            }
            .show()
    }

    // Charger settings for Renault Twizy:
    // 	(charge alert setup)
    private fun chargerSettingRenaultTwizy() {
        // create & open dialog:
        val dialogView = LayoutInflater.from(activity).inflate(
            R.layout.dlg_charger_twizy, null
        )

        // add distance units to range label:
        val lbRange = dialogView.findViewById<View>(R.id.lb_sufficient_range) as TextView
        lbRange.text = getString(R.string.lb_sufficient_range, carData!!.car_distance_units)

        // set range:
        val snvRange = dialogView.findViewById<View>(R.id.snv_sufficient_range) as SlideNumericView?
        if (snvRange != null) {
            snvRange.init(
                0, max((carData!!.car_max_idealrange_raw + 25).toDouble(), 50.0)
                    .toInt(), 1
            )
            snvRange.value = carData!!.car_chargelimit_rangelimit_raw
        }

        // set SOC:
        val snvSOC = dialogView.findViewById<View>(R.id.snv_sufficient_soc) as SlideNumericView?
        if (snvSOC != null) {
            snvSOC.value = carData!!.car_chargelimit_soclimit
        }

        // set charge power limit:
        val spnChargePower = dialogView.findViewById<View>(R.id.spn_charge_power_limit) as Spinner
        spnChargePower.setSelection(carData!!.car_charge_currentlimit_raw.toInt() / 5)
        val svChargeMode = dialogView.findViewById<View>(R.id.sv_twizy_charge_mode) as SwitcherView?
        if (svChargeMode != null && carData!!.car_charge_mode_i_raw >= 0 && carData!!.car_charge_mode_i_raw <= 1) {
            svChargeMode.selected = carData!!.car_charge_mode_i_raw
        }
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.lb_charger_setting_twizy)
            .setView(dialogView)
            .setNegativeButton(R.string.Cancel, null)
            .setPositiveButton(
                android.R.string.ok
            ) { dlg, which ->
                val appCompatDialog = dlg as AppCompatDialog
                val snvRange = appCompatDialog
                    .findViewById<View>(R.id.snv_sufficient_range) as SlideNumericView?
                val snvSOC = appCompatDialog
                    .findViewById<View>(R.id.snv_sufficient_soc) as SlideNumericView?
                val spnChargePower = appCompatDialog
                    .findViewById<View>(R.id.spn_charge_power_limit) as Spinner?
                val svChargeMode = appCompatDialog
                    .findViewById<View>(R.id.sv_twizy_charge_mode) as SwitcherView?
                val suffRange = snvRange!!.value
                val suffSOC = snvSOC!!.value
                val chgPower = spnChargePower!!.selectedItemPosition
                val chgMode = svChargeMode!!.selected

                // SetChargeAlerts (204):
                sendCommand(
                    R.string.msg_setting_charge_alerts, String.format(
                        "204,%d,%d,%d,%d",
                        suffRange, suffSOC, chgPower, chgMode
                    ),
                    this@InfoFragment
                )
            }
            .show()
    }

    // Charger settings for VW e-Up:
    // 	(charge alert setup)
    private fun chargerSettingVWUP() {
        // create & open dialog:
        val dialogView = LayoutInflater.from(activity).inflate(
            R.layout.dlg_charger_vwup, null
        )

        // set SOC:
        val snvSOC = dialogView.findViewById<View>(R.id.snv_sufficient_soc) as SlideNumericView?
        if (snvSOC != null) {
            snvSOC.value = carData!!.car_chargelimit_soclimit
        }

        // set charge current:
        val snv = dialogView.findViewById<View>(R.id.snv_amps) as SlideNumericView?
        if (snv != null) {
            snv.value = carData!!.car_charge_currentlimit_raw.toInt()
        }

        // set charge power limit:
        /*		Spinner spnChargePower = (Spinner) dialogView.findViewById(R.id.spn_charge_power_limit);
		if (spnChargePower != null) {
			spnChargePower.setSelection((int) mCarData.car_charge_currentlimit_raw / 5);
		}*/
        val svChargeMode = dialogView.findViewById<View>(R.id.sv_twizy_charge_mode) as SwitcherView?
        if (svChargeMode != null) {
            if (carData!!.car_charge_mode_i_raw == 0)
                svChargeMode.selected = 1 // hack to map charge mode keys 0 ("standard") and 3 ("range") to this element
            else svChargeMode.selected = 0
        }
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.lb_charger_setting_twizy)
            .setView(dialogView)
            .setNegativeButton(R.string.Cancel, null)
            .setPositiveButton(
                android.R.string.ok
            ) { dlg, which ->
                val appCompatDialog = dlg as AppCompatDialog
                val snvSOC = appCompatDialog
                    .findViewById<View>(R.id.snv_sufficient_soc) as SlideNumericView?
                /*								Spinner spnChargePower = (Spinner) dlg
                                                .findViewById(R.id.spn_charge_power_limit);*/
                val snv = appCompatDialog
                    .findViewById<View>(R.id.snv_amps) as SlideNumericView?
                val svChargeMode = appCompatDialog
                    .findViewById<View>(R.id.sv_twizy_charge_mode) as SwitcherView?
                val suffSOC = snvSOC!!.value
                //								int chgPower = spnChargePower.getSelectedItemPosition();
                val chgMode = svChargeMode!!.selected
                val ncl = snv!!.value

                // SetChargeAlerts (204):
                sendCommand(
                    R.string.msg_setting_charge_alerts, String.format(
                        "204,%d,%d,%d",
                        suffSOC, ncl, chgMode
                    ),
                    this@InfoFragment
                )
            }
            .show()
    }

    // load ChargeAlerts / ETR data into UI:
    private fun updateChargeAlerts() {
        var infoEtr = ""
        var etrVisible = false
        val etrFull = carData!!.car_chargefull_minsremaining
        val suffSOC = carData!!.car_chargelimit_soclimit
        val etrSuffSOC = carData!!.car_chargelimit_minsremaining_soc
        val suffRange = carData!!.car_chargelimit_rangelimit_raw
        val etrSuffRange = carData!!.car_chargelimit_minsremaining_range
        if (etrSuffRange > 0) {
            val infoEtrRange = getString(
                R.string.info_etr_suffrange,
                suffRange,
                carData!!.car_distance_units,
                String.format("%02d:%02d", etrSuffRange / 60, etrSuffRange % 60)
            )
            if (infoEtr.isNotEmpty()) {
                infoEtr += "\n"
            }
            infoEtr += infoEtrRange
        }
        if (etrSuffSOC > 0) {
            val infoEtrSOC = getString(
                R.string.info_etr_suffsoc,
                suffSOC, String.format("%02d:%02d", etrSuffSOC / 60, etrSuffSOC % 60)
            )
            if (infoEtr.isNotEmpty()) {
                infoEtr += "\n"
            }
            infoEtr += infoEtrSOC
        }
        var textView = findViewById(R.id.tabInfoTextChargeEtrSuff) as TextView?
        if (textView != null) {
            textView.text = infoEtr
            if (infoEtr != "") {
                etrVisible = true
                textView.visibility = View.VISIBLE
            } else {
                textView.visibility = View.INVISIBLE
            }
        }
        infoEtr = ""
        if (etrFull > 0) {
            val infoEtrFull = getString(
                R.string.info_etr_full,
                String.format("%02d:%02d", etrFull / 60, etrFull % 60)
            )
            if (infoEtr.isNotEmpty()) {
                infoEtr += "\n"
            }
            infoEtr += infoEtrFull
        }
        textView = findViewById(R.id.tabInfoTextChargeEtrFull) as TextView?
        if (textView != null) {
            textView.text = infoEtr
            if (infoEtr != "") {
                etrVisible = true
                textView.visibility = View.VISIBLE
            } else {
                textView.visibility = View.INVISIBLE
            }
        }

        // display background if any ETR visible:
        val bgImg = findViewById(R.id.tabInfoImageChargeEtr) as ImageView?
        bgImg?.setVisibility(if (etrVisible) View.VISIBLE else View.INVISIBLE)
    }

    // This updates the part of the view with times shown.
    // It is called by a periodic timer so it gets updated every few seconds.
    private fun updateLastUpdatedView(carData: CarData?) {
        // Quick exit if the car data is not there yet...
        if (carData?.car_lastupdated == null) {
            return
        }

        // Let's update the Info tab view...

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
            tv.text = String.format(
                getText(R.string.nmins).toString(),
                minutes
            )
            tv.setTextColor(-0x10000)
        } else {
            tv.text = String.format(
                getText(R.string.nmins).toString(),
                minutes
            )
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
            if (minutes == 0L) tv.text = getText(R.string.justnow) else if (minutes == 1L) tv.text =
                "1 min" else if (days > 1) tv.text = String.format(
                getText(R.string.ndays).toString(),
                days
            ) else if (hours > 1) tv.text = String.format(
                getText(R.string.nhours).toString(),
                hours
            ) else if (minutes > 60) tv.text = String.format(
                getText(R.string.nmins).toString(),
                minutes
            ) else tv.text = String.format(
                getText(R.string.nmins).toString(),
                minutes
            )
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
    private fun updateCarInfoView(carData: CarData) {
        var tv = findViewById(R.id.txt_title) as TextView
        tv.text = carData.sel_vehicle_label
        val carPos = carsStorage.getStoredCars().indexOf(carData)
        if (carPos != carSelectPos) {
            Log.d(TAG, "updateCarInfoView: id=" + carData.sel_vehicleid + " pos=" + carPos)
            carSelectPos = carPos
            carSelect.setSelection(carSelectPos)
        }
        tv = findViewById(R.id.tabInfoTextSOC) as TextView
        tv.text = carData.car_soc
        val cmtv = findViewById(R.id.tabInfoTextChargeMode) as TextView
        val coiv = findViewById(R.id.tabInfoImageBatteryChargingOverlay) as ImageView
        val bar = findViewById(R.id.tabInfoSliderChargerControl) as ReversedSeekBar
        val tvl = findViewById(R.id.tabInfoTextChargeStatusLeft) as TextView
        val tvr = findViewById(R.id.tabInfoTextChargeStatusRight) as TextView
        val tvf = findViewById(R.id.tabInfoTextChargeStatus) as TextView
        val tvPowerInput = findViewById(R.id.tabInfoTextChargePowerInput) as TextView
        val tvPowerLoss = findViewById(R.id.tabInfoTextChargePowerLoss) as TextView
        if (!carData.car_chargeport_open || carData.car_charge_substate_i_raw == 0x07) {
            // Charge port is closed or car is not plugged in
            findViewById(R.id.tabInfoImageCharger).visibility = View.INVISIBLE
            bar.visibility = View.INVISIBLE
            cmtv.visibility = View.INVISIBLE
            coiv.setVisibility(View.INVISIBLE)
            tvl.visibility = View.INVISIBLE
            tvr.visibility = View.INVISIBLE
            tvf.visibility = View.INVISIBLE
            tvPowerInput.visibility = View.INVISIBLE
            tvPowerLoss.visibility = View.INVISIBLE
        } else {
            // Car is plugged in
            val cmst = if (carData.car_battery_rangespeed != "") {
                String.format(
                    "%s ≤%s ⏲%s ▾%.1fkWh",
                    carData.car_charge_mode.uppercase(Locale.getDefault()),
                    carData.car_charge_currentlimit,
                    carData.car_battery_rangespeed,
                    carData.car_charge_kwhconsumed
                )
            } else {
                if(carData!!.car_type == "SQ") {
                    String.format(
                        "SoH %.1f",
                        carData.car_soh
                    )
                }else{
                    String.format(
                        "%s ≤%s ▾%.1fkWh",
                        carData.car_charge_mode.uppercase(Locale.getDefault()),
                        carData.car_charge_currentlimit,
                        carData.car_charge_kwhconsumed
                    )
                }
            }
            cmtv.text = cmst
            cmtv.visibility = View.VISIBLE
            if ((this.carData!!.car_type == "RT") || (this.carData!!.car_type == "SQ")) {
                // Renault Twizy: no charge control
                findViewById(R.id.tabInfoImageCharger).visibility = View.VISIBLE
                bar.visibility = View.INVISIBLE
                tvl.visibility = View.INVISIBLE
                tvr.visibility = View.INVISIBLE
                tvPowerInput.visibility = View.INVISIBLE
                tvPowerLoss.visibility = View.INVISIBLE
                var chargeStateInfo = 0
                when (carData.car_charge_state_i_raw) {
                    1 -> chargeStateInfo = R.string.state_charging
                    2 -> chargeStateInfo = R.string.state_topping_off
                    4 -> chargeStateInfo = R.string.state_done
                    21 -> chargeStateInfo = R.string.state_stopped
                }
                if (chargeStateInfo != 0) {
                    tvf.text = String.format(
                        getText(chargeStateInfo).toString(),
                        carData.car_charge_linevoltage,
                        carData.car_charge_current
                    )
                    tvf.visibility = View.VISIBLE
                }
                coiv.setVisibility(View.VISIBLE)
            } else {
                // Standard car:
                findViewById(R.id.tabInfoImageCharger).visibility = View.VISIBLE
                bar.visibility = View.VISIBLE
                tvl.visibility = View.VISIBLE
                tvr.visibility = View.VISIBLE
                when (carData.car_charge_state_i_raw) {
                    0x04, 0x115, 0x15, 0x16, 0x17, 0x18, 0x19 -> {
                        // Slider on the left, message is "Slide to charge"
                        bar.progress = 100
                        tvl.text = null
                        tvr.text = getText(R.string.slidetocharge)
                        coiv.setVisibility(View.INVISIBLE)
                        tvPowerInput.visibility = View.INVISIBLE
                        tvPowerLoss.visibility = View.INVISIBLE
                    }

                    0x0e -> {
                        // Slider on the left, message is "Timed Charge"
                        bar.progress = 100
                        tvl.text = null
                        tvr.text = getText(R.string.timedcharge)
                        coiv.setVisibility(View.INVISIBLE)
                        tvPowerInput.visibility = View.INVISIBLE
                        tvPowerLoss.visibility = View.INVISIBLE
                    }

                    0x01, 0x101, 0x02, 0x0d, 0x0f -> {
                        // Slider on the right, message blank
                        bar.progress = 0
                        tvl.text = String.format(
                            getText(R.string.state_charging).toString(),
                            carData.car_charge_linevoltage,
                            carData.car_charge_current
                        )
                        tvr.text = ""
                        coiv.setVisibility(View.VISIBLE)
                        if (carData.car_charge_power_input_kw_raw > 0) {
                            tvPowerInput.text = carData.car_charge_power_input_kw
                            tvPowerInput.visibility = View.VISIBLE
                        } else {
                            tvPowerInput.visibility = View.INVISIBLE
                        }
                        if (carData.car_charge_power_loss_kw_raw > 0) {
                            tvPowerLoss.text = carData.car_charge_power_loss_kw
                            tvPowerLoss.visibility = View.VISIBLE
                        } else {
                            tvPowerLoss.visibility = View.INVISIBLE
                        }
                    }

                    else -> {
                        // Slider on the right, message blank
                        bar.progress = 100
                        tvl.text = null
                        tvr.text = null
                        coiv.setVisibility(View.INVISIBLE)
                        tvPowerInput.visibility = View.INVISIBLE
                        tvPowerLoss.visibility = View.INVISIBLE
                    }
                }
            }
        }
        tv = findViewById(R.id.tabInfoTextIdealRange) as TextView
        tv.text = carData.car_range_ideal
        tv = findViewById(R.id.tabInfoTextEstimatedRange) as TextView
        tv.text = carData.car_range_estimated
        val maxWeight = (findViewById(R.id.tabInfoTextSOC) as TextView).layoutParams.width
        val realWeight = Math
            .round(maxWeight * carData.car_soc_raw / 100 * 1.1f)
        val v = findViewById(R.id.tabInfoImageBatteryOverlay)
        v.layoutParams.width = min(maxWeight.toDouble(), realWeight.toDouble()).toInt()
        v.requestLayout()
        val iv = findViewById(R.id.img_signal_rssi) as ImageView
        iv.setImageResource(
            getDrawableIdentifier(
                activity,
                "signal_strength_" + carData.car_gsm_bars
            )
        )
    }

    /*
     * Inner types
     */

    companion object {
        private const val TAG = "InfoFragment"
    }

    private inner class CarSelectAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return carsStorage.getStoredCars().size
        }

        override fun getItem(position: Int): Any {
            return carsStorage.getStoredCars()[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            return try {
                val carData = carsStorage.getStoredCars()[position]
                val iv = if (convertView != null) {
                    convertView as ImageView
                } else {
                    ImageView(parent.context)
                }
                iv.setLayoutParams(
                    Gallery.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
                iv.setScaleType(ImageView.ScaleType.FIT_START)
                iv.setAdjustViewBounds(true)
                iv.setImageResource(
                    getDrawableIdentifier(
                        parent.context,
                        carData.sel_vehicle_image
                    )
                )
                iv
            } catch (e: Exception) {
                null
            }
        }
    }
}
