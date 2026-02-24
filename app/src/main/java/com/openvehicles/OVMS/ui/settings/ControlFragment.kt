package com.openvehicles.OVMS.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.BaseFragmentActivity
import com.openvehicles.OVMS.ui.utils.Ui
import com.openvehicles.OVMS.ui.utils.Ui.setOnClick
import com.openvehicles.OVMS.ui.utils.Ui.showEditDialog
import com.openvehicles.OVMS.utils.CarsStorage.getStoredCars
import com.openvehicles.OVMS.utils.ConnectionList
import com.openvehicles.OVMS.utils.ConnectionList.ConnectionsListener
import com.openvehicles.OVMS.utils.NotificationData
import com.openvehicles.OVMS.utils.OVMSNotifications

class ControlFragment
    : BaseFragment(), View.OnClickListener, OnResultCommandListener, ConnectionsListener {

    private var connectionList: ConnectionList? = null
    private var editPosition = 0
    private var carData: CarData? = null
    private var ussdCmd: String? = null
    private var ovmsNotifications: OVMSNotifications? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // get data of car to edit:
        editPosition = requireArguments().getInt("position", -1)
        if (editPosition >= 0) {
            carData = getStoredCars()[editPosition]
        }
        connectionList = ConnectionList(requireActivity(), this, false)
        compatActivity?.setTitle(R.string.Control)
        val rootView = view
        setOnClick(rootView!!, R.id.btn_features, this)
        setOnClick(rootView, R.id.btn_parameters, this)
        setOnClick(rootView, R.id.btn_mmi_ussd_code, this)
        setOnClick(rootView, R.id.btn_connections, this)
        setOnClick(rootView, R.id.btn_cellular_usage, this)
        setOnClick(rootView, R.id.btn_reset_ovms_module, this)

        // diag logs only available on Renault Twizy (up to now):
        if (carData!!.car_type == "RT") {
            setOnClick(rootView, R.id.btn_diag_logs, this)
        } else {
            rootView.findViewById<View>(R.id.btn_diag_logs).visibility = View.GONE
        }
        ussdCmd = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_control, null)
    }

    override fun onDestroyView() {
        cancelCommand()
        super.onDestroyView()
    }

    override fun onClick(view: View) {
        var baseActivity: BaseFragmentActivity? = null
        try {
            baseActivity = activity as BaseFragmentActivity?
        } catch (ignored: Exception) {}
        val args: Bundle
        val id = view.id
        when (id) {
            R.id.btn_mmi_ussd_code -> {
                showEditDialog(
                    view.context,
                    getString(R.string.msg_mmi_ssd_code),
                    "*100#",
                    R.string.Send,
                    false,
                    object : Ui.OnChangeListener<String?> {

                        override fun onAction(data: String?) {
                            if (TextUtils.isEmpty(data)) {
                                return
                            }
                            ussdCmd = data
                            val context: Context? = getActivity()
                            if (ovmsNotifications == null) {
                                ovmsNotifications = OVMSNotifications(context!!)
                            }
                            val isNew = ovmsNotifications!!.addNotification(
                                NotificationData.TYPE_COMMAND,
                                carData!!.sel_vehicleid + ": " + ussdCmd,
                                ussdCmd
                            )
                            if (isNew) {
                                // signal App to reload notifications:
                                val uiNotify = Intent(context!!.packageName + ".Notification")
                                context.sendBroadcast(uiNotify)
                            }
                            sendCommand(R.string.lb_mmi_ussd_code, "41,$data", this@ControlFragment)
                        }
                    }, baseActivity == null)
            }
            R.id.btn_features -> {
                args = Bundle()
                args.putInt("position", editPosition)
                if (baseActivity == null) {
                    findNavController().navigate(R.id.action_controlFragment_to_featuresFragment, args)
                } else {
                    baseActivity.setNextFragment(FeaturesFragment::class.java, args)
                }
            }
            R.id.btn_parameters -> {
                args = Bundle()
                args.putInt("position", editPosition)
                if (baseActivity == null) {
                    findNavController().navigate(R.id.action_controlFragment_to_controlParametersFragment, args)
                } else {
                    baseActivity.setNextFragment(ControlParametersFragment::class.java, args)
                }
            }
            R.id.btn_reset_ovms_module -> {
                sendCommand(R.string.msg_rebooting_car_module, "5", this)
            }
            R.id.btn_connections -> {
                connectionList!!.sublist()
            }
            R.id.btn_cellular_usage -> {
                if (baseActivity == null) {
                    findNavController().navigate(R.id.action_controlFragment_to_cellularStatsFragment)
                } else {
                    baseActivity.setNextFragment(CellularStatsFragment::class.java)
                }
            }
            R.id.btn_diag_logs -> {
                args = Bundle()
                args.putInt("position", editPosition)
                if (baseActivity == null) {
                    findNavController().navigate(R.id.action_controlFragment_to_logsFragment, args)
                } else {
                    baseActivity.setNextFragment(LogsFragment::class.java, args)
                }
            }
        }
    }

    override fun onResultCommand(result: Array<String>) {
        if (result.size < 2) {
            return
        }
        if (context == null) {
            return
        }
        val context: Context? = activity
        val command = result[0].toInt()
        val resCode = result[1].toInt()
        val resText = if (result.size > 2) result[2] else ""
        val cmdMessage = getSentCommandMessage(result[0])

        when (resCode) {
            0 -> {
                if (command == 41) {
                    // only process second cmd result carrying data:
                    if (result.size >= 3) {
                        cancelCommand()

                        // add MMI/USSD result to Notifications:
                        if (ovmsNotifications == null) ovmsNotifications = OVMSNotifications(context!!)
                        val isNew = ovmsNotifications!!.addNotification(
                            NotificationData.TYPE_USSD,
                            carData!!.sel_vehicleid + ": " + ussdCmd,
                            result[2]
                        )
                        if (isNew) {
                            // signal App to reload notifications:
                            val uiNotify = Intent(context!!.packageName + ".Notification")
                            context.sendBroadcast(uiNotify)

                            // user info dialog:
                            AlertDialog.Builder(context)
                                .setTitle(cmdMessage)
                                .setMessage("$ussdCmd =>\n${result[2]}")
                                .setPositiveButton(android.R.string.ok, null)
                                .show()
                        }
                    }
                } else {
                    // default:
                    cancelCommand()
                    Toast.makeText(
                        context, cmdMessage + " => " + getString(R.string.msg_ok),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            1 -> {
                cancelCommand()
                Toast.makeText(
                    context, cmdMessage + " => " + getString(R.string.err_failed, resText),
                    Toast.LENGTH_SHORT
                ).show()
            }
            2 -> {
                cancelCommand()
                Toast.makeText(
                    context, cmdMessage + " => " + getString(R.string.err_unsupported_operation),
                    Toast.LENGTH_SHORT
                ).show()
            }
            3 -> {
                cancelCommand()
                Toast.makeText(
                    context, cmdMessage + " => " + getString(R.string.err_unimplemented_operation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onConnectionChanged(conId: String?, conName: String?) {
        Log.d("ControlFragment", "onConnectionChanged: conName=$conName")
    }
}
