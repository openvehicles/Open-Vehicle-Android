package com.openvehicles.OVMS.ui.settings

import android.content.Context
import android.os.Bundle
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
import com.openvehicles.OVMS.utils.CarsStorage.getStoredCars

class ControlParametersFragment : BaseFragment(), OnResultCommandListener, OnItemClickListener {

    private var adapter: ControlParametersAdapter? = null
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
        if (editPosition >= 0) {
            carData = getStoredCars()[editPosition]
        }
        listView = ListView(container!!.context)
        listView!!.fitsSystemWindows = true
        listView!!.onItemClickListener = this

        // create storage adapter:
        adapter = ControlParametersAdapter()
        listView!!.setAdapter(adapter)
        val frameLayout = FrameLayout(container!!.context)
        frameLayout.addView(listView)
        frameLayout.addView(inflater.inflate(R.layout.progress_layer, null, false))

        createProgressOverlay(frameLayout, true)
        return frameLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.Parameters)
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
        service!!.sendCommand("3", this)
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val context = parent.context
        val item = adapter!!.getItem(position) as String
        val isPasswd = position == PARAM_REGPASS
                || position == PARAM_NETPASS1
                || position == PARAM_GPRSPASS

        // Check content type:
        var isBinary = false
        if (carData!!.car_type == "RT"
            && position >= PARAM_PROFILE1
            && position <= PARAM_PROFILE3 + 1) {
            isBinary = true
        } else if (position in PARAM_ACC1..PARAM_ACC4) {
            isBinary = true
        }
        if (isBinary) {
            Toast.makeText(
                activity, getString(R.string.err_param_not_editable),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            var baseActivity: BaseFragmentActivity? = null
            try {
                baseActivity = activity as BaseFragmentActivity?
            } catch (ignored: Exception) {}
            showEditDialog(
                context = context,
                title = adapter!!.getTitleRow(context, position),
                value = item,
                buttonResId = R.string.Set,
                isPassword = isPasswd,
                listener = object : Ui.OnChangeListener<String?> {
                    override fun onAction(data: String?) {
                        sendCommand(
                            String.format("4,%d,%s", position, data),
                            this@ControlParametersFragment
                        )
                        adapter!!.setParam(position, data)
                    }
                }, newUi = baseActivity == null)
        }
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
        if (command == 4) {
            // Set parameter (single) response:
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
        if (command != 3) {
            // Not for us
            return
        }
        when (resCode) {
            0 -> if (result.size >= 4) {
                val fn = result[2].toInt()
                val fm = result[3].toInt()
                val fv = if (result.size > 4) result[4] else ""
                stepProgressOverlay(fn + 1, fm)
                if (fn < PARAM_FEATURE_S) {
                    adapter!!.setParam(fn, fv)
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

        //		private static final int PARAM_MAX		= 32;
        //		private static final int PARAM_MAX_LENGTH = 32;
        //		private static final int PARAM_BANKSIZE	= 8;
        // Standard:
        private const val PARAM_REGPHONE = 0x00
        private const val PARAM_REGPASS = 0x01
        private const val PARAM_MILESKM = 0x02
        private const val PARAM_NOTIFIES = 0x03
        private const val PARAM_SERVERIP = 0x04
        private const val PARAM_GPRSAPN = 0x05
        private const val PARAM_GPRSUSER = 0x06
        private const val PARAM_GPRSPASS = 0x07
        private const val PARAM_MYID = 0x08
        private const val PARAM_NETPASS1 = 0x09
        private const val PARAM_PARANOID = 0x0A
        private const val PARAM_S_GROUP1 = 0x0B
        private const val PARAM_S_GROUP2 = 0x0C
        private const val PARAM_GSMLOCK = 0x0D
        private const val PARAM_VEHICLETYPE = 0x0E
        private const val PARAM_COOLDOWN = 0x0F
        private const val PARAM_ACC1 = 0x10 // base64
        private const val PARAM_ACC2 = 0x11 // base64
        private const val PARAM_ACC3 = 0x12 // base64
        private const val PARAM_ACC4 = 0x13 // base64
        private const val PARAM_GPRSDNS = 0x16
        private const val PARAM_TIMEZONE = 0x17

        // Renault Twizy:
        private const val PARAM_PROFILE = 0x0F // current cfg profile nr (0..3)
        private const val PARAM_PROFILE1 = 0x10 // custom profile #1 (binary, 2 slots)
        private const val PARAM_PROFILE2 = 0x12 // custom profile #2 (binary, 2 slots)
        private const val PARAM_PROFILE3 = 0x14 // custom profile #3 (binary, 2 slots)
        private const val PARAM_FEATURE_S = 0x18

        //		private static final int PARAM_FEATURE8  = 0x18;
        //		private static final int PARAM_FEATURE9  = 0x19;
        //		private static final int PARAM_FEATURE10 = 0x1A;
        //		private static final int PARAM_FEATURE11 = 0x1B;
        //		private static final int PARAM_FEATURE12 = 0x1C;
        //		private static final int PARAM_FEATURE13 = 0x1D;
        //		private static final int PARAM_FEATURE14 = 0x1E;
        //		private static final int PARAM_FEATURE15 = 0x1F;
    }

    private inner class ControlParametersAdapter : BaseAdapter() {

        private var inflater: LayoutInflater? = null
        private val params = arrayOfNulls<String>(PARAM_FEATURE_S)

        override fun getCount(): Int {
            return PARAM_FEATURE_S
        }

        override fun getItem(position: Int): Any {
            return params[position] ?: ""
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        fun setParam(key: Int, value: String?) {
            if (key > PARAM_FEATURE_S - 1) {
                return
            }
            params[key] = value
            notifyDataSetChanged()
        }

        fun getTitleRow(context: Context, position: Int): String {
            // Renault Twizy:
            if (carData!!.car_type == "RT") {
                when (position) {
                    PARAM_PROFILE -> return context.getString(
                        R.string.lb_cp_rt_profile,
                        position
                    )
                    PARAM_PROFILE1, PARAM_PROFILE1 + 1 -> return context.getString(
                        R.string.lb_cp_rt_profile1,
                        position
                    )
                    PARAM_PROFILE2, PARAM_PROFILE2 + 1 -> return context.getString(
                        R.string.lb_cp_rt_profile2,
                        position
                    )
                    PARAM_PROFILE3, PARAM_PROFILE3 + 1 -> return context.getString(
                        R.string.lb_cp_rt_profile3,
                        position
                    )
                    else -> {}
                }
            }
            return when (position) {
                PARAM_REGPHONE -> context.getString(
                    R.string.lb_cp_registered_telephone,
                    position
                )
                PARAM_REGPASS -> context.getString(
                    R.string.lb_cp_module_password,
                    position
                )
                PARAM_MILESKM -> context.getString(
                    R.string.lb_cp_miles_km,
                    position
                )
                PARAM_NOTIFIES -> context.getString(
                    R.string.lb_cp_notifications,
                    position
                )
                PARAM_SERVERIP -> context.getString(
                    R.string.lb_cp_ovms_server_ip,
                    position
                )
                PARAM_GPRSAPN -> context.getString(
                    R.string.lb_cp_cellular_network_apn,
                    position
                )
                PARAM_GPRSUSER -> context.getString(
                    R.string.lb_cp_cellular_network_user,
                    position
                )
                PARAM_GPRSPASS -> context.getString(
                    R.string.lb_cp_cellular_network_password,
                    position
                )
                PARAM_MYID -> context.getString(
                    R.string.lb_cp_vehicle_id,
                    position
                )
                PARAM_NETPASS1 -> context.getString(
                    R.string.lb_cp_server_password,
                    position
                )
                PARAM_PARANOID -> context.getString(
                    R.string.lb_cp_paranoid_mode,
                    position
                )
                PARAM_S_GROUP1 -> context.getString(
                    R.string.lb_cp_social_group_1,
                    position
                )
                PARAM_S_GROUP2 -> context.getString(
                    R.string.lb_cp_social_group_2,
                    position
                )
                PARAM_GSMLOCK -> context.getString(
                    R.string.lb_cp_gsmlock,
                    position
                )
                PARAM_VEHICLETYPE -> context.getString(
                    R.string.lb_cp_vehicle_type,
                    position
                )
                PARAM_COOLDOWN -> context.getString(
                    R.string.lb_cp_cooldown,
                    position
                )
                PARAM_GPRSDNS -> context.getString(
                    R.string.lb_cp_gprsdns,
                    position
                )
                PARAM_TIMEZONE -> context.getString(
                    R.string.lb_cp_timezone,
                    position
                )
                PARAM_ACC1 -> context.getString(
                    R.string.lb_cp_acc1,
                    position
                )
                PARAM_ACC2 -> context.getString(
                    R.string.lb_cp_acc2,
                    position
                )
                PARAM_ACC3 -> context.getString(
                    R.string.lb_cp_acc3,
                    position
                )
                PARAM_ACC4 -> context.getString(
                    R.string.lb_cp_acc4,
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
            val isPasswd = position == PARAM_REGPASS
                    || position == Companion.PARAM_NETPASS1
                    || position == PARAM_GPRSPASS
            var tv = view!!.findViewById<View>(android.R.id.text1) as TextView
            tv.text = getTitleRow(context, position)
            tv = view.findViewById<View>(android.R.id.text2) as TextView
            tv.text = if (isPasswd) "*****" else params[position]
            return view
        }
    }
}
