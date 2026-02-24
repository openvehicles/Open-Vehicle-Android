package com.openvehicles.OVMS.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.BaseFragmentActivity
import com.openvehicles.OVMS.ui.utils.Ui
import com.openvehicles.OVMS.ui.utils.Ui.showEditDialog
import com.openvehicles.OVMS.utils.CarsStorage.getSelectedCarData
import com.openvehicles.OVMS.utils.CarsStorage.getStoredCars

class FeaturesFragment : BaseFragment(), OnResultCommandListener, OnItemClickListener {

    private var adapter: FeaturesAdapter? = null
    private var listView: ListView? = null
    private var editPosition = 0
    private var carData: CarData? = null
    private var service: ApiService? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // get data of car to edit:
        editPosition = requireArguments().getInt("position", -1)
        carData = if (editPosition >= 0) {
            getStoredCars()[editPosition]
        } else {
            getSelectedCarData()
        }
        Log.d(TAG, "editPosition=$editPosition → carData=$carData")
        listView = ListView(container!!.context)
        listView!!.fitsSystemWindows = true
        listView!!.onItemClickListener = this

        // create storage adapter:
        adapter = FeaturesAdapter()
        listView!!.setAdapter(adapter)
        val frameLayout = FrameLayout(container!!.context)
        frameLayout.addView(listView)
        frameLayout.addView(inflater.inflate(R.layout.progress_layer, frameLayout, false))
        createProgressOverlay(frameLayout, true)

        return frameLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().setTitle(R.string.Features)
    }

    override fun onServiceAvailable(service: ApiService) {
        this.service = service
        requestData()
    }

    override fun update(carData: CarData?) {
    }

    private fun requestData() {
        // send request:
        showProgressOverlay()
        service!!.sendCommand("1", this)
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val context = parent.context
        var baseActivity: BaseFragmentActivity? = null
        try {
            baseActivity = activity as BaseFragmentActivity?
        } catch (ignored: Exception) {}
        showEditDialog(context,
            title = adapter!!.getTitleRow(context, position),
            value = adapter!!.getItem(position) as String,
            buttonResId = R.string.Set,
            isPassword = false,
            listener = object : Ui.OnChangeListener<String?> {
                override fun onAction(data: String?) {
                    sendCommand(String.format("2,%d,%s", position, data), this@FeaturesFragment)
                    adapter!!.setFeature(position, data)
                }
            }, newUi = baseActivity == null)

        /*
		Ui.showPinDialog(context, mAdapter.getTitleRow(context, position), Long.toString(id),
				R.string.Set, false, new Ui.OnChangeListener<String>() {
			@Override
			public void onAction(String pData) {
				int val = TextUtils.isEmpty(pData) ? 0 : Integer.parseInt(pData);
				sendCommand(String.format("2,%d,%d", fn, val), FeaturesFragment.this);
				mAdapter.setFeature(fn, val);
			}
		});
		*/
    }

    override fun onResultCommand(result: Array<String>) {
        if (result.size < 2) {
            return
        }
        if (context == null) {
            return
        }
        val command = result[0].toInt()
        val resCode = result[1].toInt()
        val resText = if (result.size > 2) result[2] else ""
        if (command == 2) {
            // Set feature (single) response:
            cancelCommand()
            when (resCode) {
                0 -> Toast.makeText(
                    activity, getString(R.string.msg_ok),
                    Toast.LENGTH_SHORT
                ).show()
                1 -> Toast.makeText(
                    activity, getString(R.string.err_failed, resText),
                    Toast.LENGTH_SHORT
                ).show()
                2 -> Toast.makeText(
                    activity, getString(R.string.err_unsupported_operation),
                    Toast.LENGTH_SHORT
                ).show()
                3 -> Toast.makeText(
                    activity, getString(R.string.err_unimplemented_operation),
                    Toast.LENGTH_SHORT
                ).show()
            }
            return
        }
        if (command != 1) {
            // Not for us
            return
        }
        when (resCode) {
            0 -> if (result.size > 4) {
                val fn = result[2].toInt()
                val fm = result[3].toInt()
                val fv = result[4]
                stepProgressOverlay(fn + 1, fm)
                if (fm != adapter!!.featuresMax) {
                    adapter!!.featuresMax = fm
                }
                if (fn < adapter!!.featuresMax) {
                    adapter!!.setFeature(fn, fv)
                }
                if (fn == fm - 1) {
                    // got all, cancel listening:
                    cancelCommand()
                }
            }
            1 -> {
                Toast.makeText(
                    activity, getString(R.string.err_failed, resText),
                    Toast.LENGTH_SHORT
                ).show()
                cancelCommand()
            }
            2 -> {
                Toast.makeText(
                    activity, getString(R.string.err_unsupported_operation),
                    Toast.LENGTH_SHORT
                ).show()
                cancelCommand()
            }
            3 -> {
                Toast.makeText(
                    activity, getString(R.string.err_unimplemented_operation),
                    Toast.LENGTH_SHORT
                ).show()
                cancelCommand()
            }
        }
    }

    /*
     * Inner types
     */

    private companion object {

        private const val TAG = "FeaturesFragment"

        // Standard features:
        private const val FEATURE_SPEEDO = 0x00 // Speedometer feature
        private const val FEATURE_STREAM = 0x08 // Location streaming feature
        private const val FEATURE_MINSOC = 0x09 // Minimum SOC feature
        private const val FEATURE_CARBITS = 0x0E // Various ON/OFF features (bitmap)
        private const val FEATURE_CANWRITE = 0x0F // CAN bus can be written to

        // Renault Twizy:
        private const val FEATURE_GPSLOGINT = 0x00 // GPS log interval [seconds]
        private const val FEATURE_KICKDOWN_THRESHOLD = 0x01 // Kickdown threshold (pedal change)
        private const val FEATURE_KICKDOWN_COMPZERO =
            0x02 // Kickdown pedal compensation zero point
        private const val FEATURE_CHARGEMODE = 0x06 // Charge mode (0-1)
        private const val FEATURE_CHARGEPOWER = 0x07 // Charge power level (0-7)
        private const val FEATURE_SUFFSOC = 0x0A // Charge alert: sufficient SOC
        private const val FEATURE_SUFFRANGE = 0x0B // Charge alert: sufficient range
        private const val FEATURE_MAXRANGE = 0x0C // Max ideal range (100% SOC)
        private const val FEATURE_CAPACITY = 0x0D // Battery capacity average (SOH%)
        private const val FEATURE_CAPACITY_NOM_AH = 0x10 // Battery nominal capacity (Ah)

        // VW e-Up:
        private const val FEATURE_MODELYEAR = 0x14 // model year
        private const val FEATURE_REMOTE_AC_TEMP = 0x15 // temperature for remote AC
        private const val FEATURE_REMOTE_AC_ON_BAT = 0x16 // allow remote AC on battery power?

        // The FEATURE_CARBITS feature is a set of ON/OFF bits to control different
        // miscelaneous aspects of the system. The following bits are defined:
        //private static final int FEATURE_CB_2008		= 0x01; // Set to 1 to mark the car as 2008/2009
        //private static final int FEATURE_CB_SAD_SMS		= 0x02; // Set to 1 to suppress "Access Denied" SMS
        //private static final int FEATURE_CB_SOUT_SMS	= 0x04; // Set to 1 to suppress all outbound SMS

        // smart EQ
        private const val FEATURE_LED_STATE = 0x01 // LED Online State
        private const val FEATURE_TPMS_TEMP = 0x02 // TPMS temperatures
        private const val FEATURE_RESET_TRIP_CHARGE = 0x03 // reset trip at charge
        private const val FEATURE_RESET_KWH100 = 0x04 // reset kWh/100km at start
        private const val FEATURE_CHARGE12V = 0x05 // charge 12V
        private const val FEATURE_DOORLOCK = 0x06 // # 6 activate Doorlock warning
        private const val FEATURE_DOOROPEN = 0x07 // # 7 activate Dooropen warning
        private const val FEATURE_CALCADC = 0x0B // # 11 calc adc on charging
        private const val FEATURE_BC_KWH100 = 0x0C // # 12 OCS kWh/100km value
        private const val FEATURE_FULL_KM = 0x0D // # 13 OCS kWh/100km value

    }

    private inner class FeaturesAdapter : BaseAdapter() {

        var featuresMax = 16
            set(value) {
                field = value
                feature = arrayOfNulls(featuresMax)
            }

        private var inflater: LayoutInflater? = null
        private var feature = arrayOfNulls<String>(featuresMax)

        override fun getCount(): Int {
            return featuresMax
        }

        override fun getItem(position: Int): Any {
            return feature[position] ?: ""
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        fun setFeature(key: Int, value: String?) {
            if (key > featuresMax - 1) {
                return
            }
            feature[key] = value
            notifyDataSetChanged()
        }

        fun getTitleRow(context: Context, position: Int): String {
            // Renault Twizy:
            if (carData!!.car_type == "RT") {
                when (position) {
                    FEATURE_GPSLOGINT -> return context.getString(
                        R.string.lb_ft_rt_gpslogint,
                        position
                    )
                    FEATURE_KICKDOWN_THRESHOLD -> return context.getString(
                        R.string.lb_ft_rt_kickdown_threshold,
                        position
                    )
                    FEATURE_KICKDOWN_COMPZERO -> return context.getString(
                        R.string.lb_ft_rt_kickdown_compzero,
                        position
                    )
                    FEATURE_CHARGEMODE -> return context.getString(
                        R.string.lb_ft_rt_chargemode,
                        position
                    )
                    FEATURE_CHARGEPOWER -> return context.getString(
                        R.string.lb_ft_rt_chargepower,
                        position
                    )
                    FEATURE_SUFFSOC -> return context.getString(
                        R.string.lb_ft_rt_suffsoc,
                        position
                    )
                    FEATURE_SUFFRANGE -> return context.getString(
                        R.string.lb_ft_rt_suffrange,
                        position
                    )
                    FEATURE_MAXRANGE -> return context.getString(
                        R.string.lb_ft_rt_maxrange,
                        position
                    )
                    FEATURE_CAPACITY -> return context.getString(
                        R.string.lb_ft_rt_capacity,
                        position
                    )
                    FEATURE_CAPACITY_NOM_AH -> return context.getString(
                        R.string.lb_ft_rt_capacity_nom_ah,
                        position
                    )
                    else -> {}
                }
            }
            // Nissan Leaf:
            if (carData!!.car_type == "NL") {
                when (position) {
                    FEATURE_SUFFSOC -> return context.getString(
                        R.string.lb_ft_rt_suffsoc,
                        position
                    )
                    FEATURE_SUFFRANGE -> return context.getString(
                        R.string.lb_ft_rt_suffrange,
                        position
                    )
                    else -> {}
                }
            }
            // VW e-Up:
            if (carData!!.car_type == "VWUP") {
                when (position) {
                    FEATURE_CHARGEMODE -> return context.getString(
                        R.string.lb_ft_rt_chargemode,
                        position
                    )
                    FEATURE_SUFFSOC -> return context.getString(
                        R.string.lb_ft_rt_suffsoc,
                        position
                    )
                    FEATURE_MODELYEAR -> return context.getString(
                        R.string.lb_ft_modelyear,
                        position
                    )
                    FEATURE_REMOTE_AC_TEMP -> return context.getString(
                        R.string.lb_ft_remote_ac_temp,
                        position
                    )
                    FEATURE_REMOTE_AC_ON_BAT -> return context.getString(
                        R.string.lb_ft_remote_ac_on_bat,
                        position
                    )
                    else -> {}
                }
            }

            // Smart EQ:
            if (carData!!.car_type == "SQ") {
                when (position) {
                    FEATURE_LED_STATE -> return context.getString(
                        R.string.lb_ft_sq_led_state,
                        position
                    )
                    FEATURE_TPMS_TEMP -> return context.getString(
                        R.string.lb_ft_sq_tpms_temp,
                        position
                    )
                    FEATURE_RESET_TRIP_CHARGE -> return context.getString(
                        R.string.lb_ft_sq_reset_trip_charge,
                        position
                    )
                    FEATURE_SUFFSOC -> return context.getString(
                        R.string.lb_ft_rt_suffsoc,
                        position
                    )
                    FEATURE_CHARGE12V -> return context.getString(
                        R.string.lb_ft_sq_charge12v,
                        position
                    )
                    FEATURE_RESET_KWH100 -> return context.getString(
                        R.string.lb_ft_sq_reset_kwh100,
                        position
                    )
                    FEATURE_BC_KWH100 -> return context.getString(
                        R.string.lb_ft_sq_bc_kwh100,
                        position
                    )
                    FEATURE_FULL_KM -> return context.getString(
                        R.string.lb_ft_sq_full_km,
                        position
                    )
                    FEATURE_DOORLOCK -> return context.getString(
                        R.string.lb_ft_sq_doorlock,
                        position
                    )
                    FEATURE_DOOROPEN -> return context.getString(
                        R.string.lb_ft_sq_dooropen,
                        position
                    )
                    FEATURE_CALCADC -> return context.getString(
                        R.string.lb_ft_sq_calcadc,
                        position
                    )
                    else -> {}
                }
            }

            return when (position) {
                FEATURE_SPEEDO -> context.getString(
                    R.string.lb_ft_digital_speedo,
                    position
                )
                FEATURE_STREAM -> context.getString(
                    R.string.lb_ft_gps_stream,
                    position
                )
                FEATURE_MINSOC -> context.getString(
                    R.string.lb_ft_minimum_soc,
                    position
                )
                FEATURE_CARBITS -> context.getString(
                    R.string.lb_ft_car_bits,
                    position
                )
                FEATURE_CANWRITE -> context.getString(
                    R.string.lb_ft_can_write,
                    position
                )
                else -> String.format("#%d:", position)
            }
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            val context = parent.context
            if (view == null) {
                if (inflater == null) {
                    inflater = LayoutInflater.from(context)
                }
                view = inflater!!.inflate(R.layout.item_keyvalue, null)
            }
            var tv = view!!.findViewById<View>(android.R.id.text1) as TextView
            tv.text = getTitleRow(context, position)
            tv = view.findViewById<View>(android.R.id.text2) as TextView
            tv.text = feature[position]
            return view
        }
    }
}
