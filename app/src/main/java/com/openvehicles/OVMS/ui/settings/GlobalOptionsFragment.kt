package com.openvehicles.OVMS.ui.settings

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.utils.Ui.setOnClick
import com.openvehicles.OVMS.ui.utils.Ui.setValue
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.CarsStorage.getSelectedCarData
import com.openvehicles.OVMS.utils.OVMSNotifications
import com.openvehicles.OVMS.utils.Sys.getRandomString

/**
 * Created by balzer on 03.12.16.
 */
class GlobalOptionsFragment : BaseFragment(), View.OnClickListener, OnFocusChangeListener, OnResultCommandListener {

    private val carData = getSelectedCarData()
    private var appPrefs: AppPrefs? = null
    private var txtCodes: EditText? = null
    private var btnRevert: ImageButton? = null
    private var serviceEnabled = false
    private var broadcastEnabled = false
    private var ovmsNotifications: OVMSNotifications? = null
    // Currently unused, may be reused if single messages shall be sent
    private var broadcastCodes: String? = null
    private var commandsEnabled = false
    private var notificationEnabled = false
    private var pluginEnabled = false
    private var firmwareEnabled = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        compatActivity?.supportActionBar!!.setIcon(R.drawable.ic_action_settings)
        compatActivity?.setTitle(R.string.Options)
        appPrefs = AppPrefs(compatActivity!!, "ovms")
        val app_Car_ID = carData!!.sel_vehicleid
        serviceEnabled = appPrefs!!.getData("option_service_enabled", "0") == "1"
        broadcastEnabled = appPrefs!!.getData("option_broadcast_enabled", "0") == "1"
        broadcastCodes = appPrefs!!.getData("option_broadcast_codes", DEFAULT_BROADCAST_CODES)
        commandsEnabled = appPrefs!!.getData("option_commands_enabled", "0") == "1"
        notificationEnabled = appPrefs!!.getData("option_notification_enabled_" + app_Car_ID, "0") == "1"
        pluginEnabled = appPrefs!!.getData("option_plugin_enabled_"  + app_Car_ID, "0") == "1"
        firmwareEnabled = appPrefs!!.getData("option_firmware_enabled_"  + app_Car_ID, "0") == "1"
        var checkBox: CheckBox = findViewById(R.id.cb_options_service) as CheckBox
        checkBox.setChecked(serviceEnabled)
        checkBox.setOnClickListener(this)
        checkBox = findViewById(R.id.cb_options_broadcast) as CheckBox
        checkBox.setChecked(broadcastEnabled)
        checkBox.setOnClickListener(this)
        txtCodes = findViewById(R.id.txt_options_broadcast_codes) as EditText
        txtCodes!!.setText(broadcastCodes)
        txtCodes!!.onFocusChangeListener = this
        btnRevert = findViewById(R.id.btn_options_broadcast_codes_revert) as ImageButton
        btnRevert!!.setOnClickListener(this)
        txtCodes!!.setEnabled(broadcastEnabled)
        btnRevert!!.setEnabled(broadcastEnabled)
        checkBox = findViewById(R.id.cb_options_commands) as CheckBox
        checkBox.setChecked(commandsEnabled)
        checkBox.setOnClickListener(this)
        checkBox = findViewById(R.id.cb_options_notification) as CheckBox
        checkBox.setChecked(notificationEnabled)
        checkBox.setOnClickListener(this)
        checkBox = findViewById(R.id.cb_options_plugin) as CheckBox
        checkBox.setChecked(pluginEnabled)
        checkBox.setOnClickListener(this)
        checkBox = findViewById(R.id.cb_options_firmware) as CheckBox
        checkBox.setChecked(firmwareEnabled)
        checkBox.setOnClickListener(this)
        setOnClick(requireView(), R.id.cb_options_reboot, this)
        setOnClick(requireView(), R.id.cb_options_apikey_renew, this)
        setValue(requireView(), R.id.tv_options_apikey, appPrefs!!.getData("APIKey"))
        val info = findViewById(R.id.txt_options_broadcast_info) as TextView
        info.text = Html.fromHtml(getString(R.string.lb_options_broadcast_info))
        info.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_globaloptions, null)
    }

    override fun onClick(v: View) {
        val context = context ?: return
        val id = v.id
        val app_Car_ID = carData!!.sel_vehicleid
        when (id) {
            R.id.cb_options_service -> {
                serviceEnabled = (v as CheckBox).isChecked
                appPrefs!!.saveData("option_service_enabled", if (serviceEnabled) "1" else "0")
                val intent =
                    Intent(if (serviceEnabled) ApiService.ACTION_ENABLE else ApiService.ACTION_DISABLE)
                context.sendBroadcast(intent)
            }
            R.id.cb_options_broadcast -> {
                broadcastEnabled = (v as CheckBox).isChecked
                txtCodes!!.setEnabled(broadcastEnabled)
                btnRevert!!.setEnabled(broadcastEnabled)
                appPrefs!!.saveData("option_broadcast_enabled", if (broadcastEnabled) "1" else "0")
            }
            R.id.btn_options_broadcast_codes_revert -> {
                broadcastCodes = DEFAULT_BROADCAST_CODES
                txtCodes!!.setText(broadcastCodes)
                appPrefs!!.saveData("option_broadcast_codes", broadcastCodes)
            }
            R.id.cb_options_notification -> {
                notificationEnabled = (v as CheckBox).isChecked
                appPrefs!!.saveData("option_notification_enabled_" + app_Car_ID, if (notificationEnabled) "1" else "0")
            }
            R.id.cb_options_commands -> {
                commandsEnabled = (v as CheckBox).isChecked
                appPrefs!!.saveData("option_commands_enabled", if (commandsEnabled) "1" else "0")
            }
            R.id.cb_options_plugin -> {
                pluginEnabled = (v as CheckBox).isChecked
                appPrefs!!.saveData("option_plugin_enabled_" + app_Car_ID, if (pluginEnabled) "1" else "0")
            }
            R.id.cb_options_firmware -> {
                firmwareEnabled = (v as CheckBox).isChecked
                appPrefs!!.saveData("option_firmware_enabled_" + app_Car_ID, if (firmwareEnabled) "1" else "0")
            }
            R.id.cb_options_reboot -> {
                AlertDialog.Builder(context)
                    .setMessage(R.string.lb_reset_ovms_module)
                    .setNegativeButton(R.string.Cancel, null)
                    .setPositiveButton(R.string.Yes) { dialog1: DialogInterface?, which: Int ->
                        sendCommand(
                            "reboot module activated",
                            "5",
                            this@GlobalOptionsFragment
                        )
                    }
                    .create().show()
            }
            R.id.cb_options_apikey_renew -> {
                AlertDialog.Builder(context)
                    .setMessage(R.string.lb_options_apikey_renew_confirm)
                    .setNegativeButton(R.string.Cancel, null)
                    .setPositiveButton(R.string.Yes) { dialog1: DialogInterface?, which: Int ->
                        val apiKey = getRandomString(25)
                        appPrefs!!.saveData("APIKey", apiKey)
                        Log.d(TAG, "onClick: generated new APIKey: $apiKey")
                        setValue(requireView(), R.id.tv_options_apikey, apiKey)
                    }
                    .create().show()
            }
        }
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

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (v.id == R.id.txt_options_broadcast_codes) {
            if (!hasFocus) {
                broadcastCodes = (v as EditText).getText().toString()
                appPrefs!!.saveData("option_broadcast_codes", broadcastCodes)
            }
        }
    }

    /*
     * Inner types
     */

    private companion object {
        private const val TAG = "GlobalOptionsFragment"
        private const val DEFAULT_BROADCAST_CODES = "DFLPSTVWZ"
    }
}
