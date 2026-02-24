package com.openvehicles.OVMS.ui.settings

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
import androidx.appcompat.app.AlertDialog
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.utils.Ui.setOnClick
import com.openvehicles.OVMS.ui.utils.Ui.setValue
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.Sys.getRandomString

/**
 * Created by balzer on 03.12.16.
 */
class GlobalOptionsFragment : BaseFragment(), View.OnClickListener, OnFocusChangeListener {

    private var appPrefs: AppPrefs? = null
    private var txtCodes: EditText? = null
    private var btnRevert: ImageButton? = null
    private var serviceEnabled = false
    private var oldUiEnabled = false
    private var broadcastEnabled = false
    // Currently unused, may be reused if single messages shall be sent
    private var broadcastCodes: String? = null
    private var commandsEnabled = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        compatActivity?.setTitle(R.string.Options)
        appPrefs = AppPrefs(compatActivity!!, "ovms")
        serviceEnabled = appPrefs!!.getData("option_service_enabled", "0") == "1"
        oldUiEnabled = appPrefs!!.getData("option_oldui_enabled", "0") == "1"
        broadcastEnabled = appPrefs!!.getData("option_broadcast_enabled", "0") == "1"
        broadcastCodes = appPrefs!!.getData("option_broadcast_codes", DEFAULT_BROADCAST_CODES)
        commandsEnabled = appPrefs!!.getData("option_commands_enabled", "0") == "1"
        var checkBox: CheckBox = findViewById(R.id.cb_options_service) as CheckBox
        checkBox.setChecked(serviceEnabled)
        checkBox.setOnClickListener(this)
        var oldUiCheckbox: CheckBox = findViewById(R.id.cb_options_oldui) as CheckBox
        oldUiCheckbox.setChecked(oldUiEnabled)
        oldUiCheckbox.setOnClickListener(this)
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
        when (id) {
            R.id.cb_options_oldui -> {
                oldUiEnabled = (v as CheckBox).isChecked
                appPrefs!!.saveData("option_oldui_enabled", if (oldUiEnabled) "1" else "0")
            }
            R.id.cb_options_service -> {
                serviceEnabled = (v as CheckBox).isChecked
                appPrefs!!.saveData("option_service_enabled", if (serviceEnabled) "1" else "0")
                // Request ApiService foreground mode change:
                val intent =
                    Intent(if (serviceEnabled) ApiService.ACTION_ENABLE else ApiService.ACTION_DISABLE)
                Log.d(TAG, "option_service_enabled: sending intent $intent")
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
            R.id.cb_options_commands -> {
                commandsEnabled = (v as CheckBox).isChecked
                appPrefs!!.saveData("option_commands_enabled", if (commandsEnabled) "1" else "0")
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
