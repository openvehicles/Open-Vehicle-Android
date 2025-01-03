package com.openvehicles.OVMS.ui2.pages

import android.app.NotificationManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.text.util.Linkify
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService.Companion.makeMsgCommand
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.BaseFragmentActivity.Companion.showForResult
import com.openvehicles.OVMS.ui.settings.StoredCommandFragment
import com.openvehicles.OVMS.ui.utils.Ui
import com.openvehicles.OVMS.ui.utils.Ui.showPinDialog
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.CarsStorage.getLastSelectedCarId
import com.openvehicles.OVMS.utils.NotificationData
import com.openvehicles.OVMS.utils.OVMSNotifications
import java.text.SimpleDateFormat

class NotificationsFragment : BaseFragment(), OnItemClickListener, OnItemLongClickListener,
    OnEditorActionListener, OnResultCommandListener {

    private lateinit var listView: ListView
    private var itemsAdapter: ItemsAdapter? = null
    private var notifications: OVMSNotifications? = null
    private lateinit var cmdInput: EditText
    private lateinit var appPrefs: AppPrefs
    private var fontMonospace = false
    private var filterList = false
    private var filterInfo = false
    private var filterAlert = false
    private var fontSize = 10f
    private var vehicleId: String? = null
    private var lastCommandSent = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Load prefs:
        appPrefs = AppPrefs(requireActivity(), "ovms")
        fontMonospace = appPrefs.getData("notifications_font_monospace") == "on"
        fontSize = try {
            appPrefs.getData("notifications_font_size").toFloat()
        } catch (e: Exception) {
            10f
        }
        filterList = appPrefs.getData("notifications_filter_list") == "on"
        filterInfo = appPrefs.getData("notifications_filter_info") == "on"
        filterAlert = appPrefs.getData("notifications_filter_alert") == "on"

        // Create UI:
        val layout = inflater.inflate(R.layout.fragment_notifications_v2, null) as RelativeLayout
        listView = layout.findViewById<View>(R.id.listView) as ListView
        listView.onItemClickListener = this
        listView.setOnItemLongClickListener(this)
        cmdInput = layout.findViewById<View>(R.id.cmdInput) as EditText
        cmdInput.setOnEditorActionListener(this)
        cmdInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize * 1.2f)
        setHasOptionsMenu(true)
        return layout
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notifications_options, menu)
        menu.findItem(R.id.mi_chk_monospace).setChecked(fontMonospace)
        menu.findItem(R.id.mi_chk_filter_list).setChecked(filterList)
        menu.findItem(R.id.mi_chk_filter_info).setChecked(filterInfo)
        menu.findItem(R.id.mi_chk_filter_alert).setChecked(filterAlert)
    }

    override fun onResume() {
        super.onResume()

        // cancel Android system notification:
        val mNotificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.cancelAll()

        // update list:
        vehicleId = getLastSelectedCarId()
        update()
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val data = parent.adapter.getItem(position) as NotificationData
        if (data.Type == NotificationData.TYPE_COMMAND) {
            // use as history:
            cmdInput.setText(data.Message)
            cmdInput.requestFocus()
            cmdInput.postDelayed({
                val keyboard =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.showSoftInput(cmdInput, 0)
            }, 200)
        } else {
            // display:
            Log.d(TAG, "Displaying notification: #$position")
            val dialog = MaterialAlertDialogBuilder(parent.context)
                .setIcon(data.icon)
                .setTitle(data.Title)
                .setMessage(data.messageFormatted)
                .setCancelable(false)
                .setPositiveButton(R.string.Close) { dialog, which -> dialog.dismiss() }
                .show()
            val textView = dialog.findViewById<View>(android.R.id.message) as TextView?
            if (textView != null) {
                if (fontMonospace) {
                    textView.setTypeface(Typeface.MONOSPACE)
                }
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize * 1.2f)
                Linkify.addLinks(textView, Linkify.WEB_URLS)
            }
        }
    }

    override fun onItemLongClick(
        parent: AdapterView<*>,
        view: View,
        position: Int,
        id: Long
    ): Boolean {
        Log.d(TAG, "Long click on notification: #$position")

        // copy message text to clipboard:
        val data = parent.adapter.getItem(position) as NotificationData
        val message = data.messageFormatted
        val clipboard =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", message)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(
            context,
            R.string.notifications_toast_copied,
            Toast.LENGTH_SHORT
        ).show()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        val newState = !item.isChecked
        when (menuId) {
            R.id.mi_help -> {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.notifications_btn_help)
                    .setMessage(Html.fromHtml(getString(R.string.notifications_help)))
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
                return true
            }
            R.id.mi_chk_filter_list -> {
                filterList = newState
                appPrefs.saveData("notifications_filter_list", if (newState) "on" else "off")
                item.setChecked(newState)
                initList()
                return true
            }
            R.id.mi_chk_filter_info -> {
                filterInfo = newState
                appPrefs.saveData("notifications_filter_info", if (newState) "on" else "off")
                item.setChecked(newState)
                return true
            }
            R.id.mi_chk_filter_alert -> {
                filterAlert = newState
                appPrefs.saveData("notifications_filter_alert", if (newState) "on" else "off")
                item.setChecked(newState)
                return true
            }
            R.id.mi_chk_monospace -> {
                fontMonospace = newState
                appPrefs.saveData("notifications_font_monospace", if (newState) "on" else "off")
                item.setChecked(newState)
                itemsAdapter!!.notifyDataSetChanged()
                return true
            }
            R.id.mi_set_fontsize -> {
                showPinDialog(
                    requireActivity(),
                    getString(R.string.notifications_set_fontsize),
                    fontSize.toString(),
                    R.string.Set,
                    false,
                    object : Ui.OnChangeListener<String?> {
                        override fun onAction(data: String?) {
                            val value = try {
                                data?.toFloat()
                            } catch (e: Exception) {
                                10f
                            }
                            fontSize = value ?: 10f
                            appPrefs.saveData("notifications_font_size", fontSize.toString())
                            itemsAdapter!!.notifyDataSetChanged()
                            cmdInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize * 1.2f)
                        }
                    }, true)
                return true
            }
            R.id.mi_stored_commands -> {
                showForResult(
                    this, StoredCommandFragment::class.java,
                    StoredCommandFragment.REQUEST_SELECT_EXECUTE, null,
                    Configuration.ORIENTATION_UNDEFINED
                )
                return false
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var command: String? = null
        if (data != null) {
            command = data.getStringExtra("command")
        }
        Log.d(TAG, "onActivityResult: reqCode=$requestCode, resCode=$resultCode, command: $command")
        sendUserCommand(command)
    }

    fun update() {
        initList()
    }

    override fun update(carData: CarData?) {
        super.update(carData)

        // check if the car filter needs to be reapplied:
        if (filterList && carData!!.sel_vehicleid != vehicleId) {
            val vehicleId = getLastSelectedCarId()
            if (vehicleId != null && vehicleId != this.vehicleId) {
                Log.d(TAG, "update: vehicle changed to '$vehicleId' => filter reload")
                this.vehicleId = vehicleId
                initList()
            }
        }
    }

    private fun initList() {
        val context = activity
            ?: return
        Log.d(TAG, "initUi: (re-)loading notifications, filter=$filterInfo, vehicle=$vehicleId")

        // (re-)load notifications:
        // TODO: this scheme of recreating the OVMSNotifications object on every change is a PITA,
        //       it needs to be replaced by a singleton or service
        notifications = OVMSNotifications(context)
        val data = notifications!!.getArray(if (filterList) vehicleId else "")

        // attach array to ListView:
        itemsAdapter = ItemsAdapter(context, this, data)
        listView.setAdapter(itemsAdapter)
    }

    // ATTENTION: use this only to display local updates to mNotifications!
    //            (will not show changes from other OVMSNotifications instances)
    private fun updateList() {
        val context = activity
            ?: return
        if (notifications == null || itemsAdapter == null) {
            initList()
        } else {
            val data = notifications!!.getArray(if (filterList) vehicleId else "")
            itemsAdapter = ItemsAdapter(context, this, data)
            listView.setAdapter(itemsAdapter)
        }
    }

    override fun onEditorAction(textView: TextView, actionId: Int, keyEvent: KeyEvent?): Boolean {
        var handled = false
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            val userCmd = textView.getText().toString()
            handled = sendUserCommand(userCmd)
        }
        return handled
    }

    private fun sendUserCommand(userCmd: String?): Boolean {
        if (userCmd.isNullOrEmpty()) {
            return false
        }
        if (notifications == null) {
            initList()
        }

        // Add command to history:
        val vehicleId = getLastSelectedCarId()
        notifications!!.addNotification(
            NotificationData.TYPE_COMMAND, "$vehicleId: $userCmd", userCmd
        )
        updateList()

        // Send command:
        val mpCmd = makeMsgCommand(userCmd)
        val cp = mpCmd.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return try {
            lastCommandSent = cp[0].toInt()
            sendCommand(userCmd, mpCmd, this)
            true
        } catch (e: Exception) {
            Toast.makeText(
                activity,
                getString(R.string.err_unimplemented_operation),
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    override fun onResultCommand(result: Array<String>) {
        if (result.size <= 1) {
            return
        }
        if (notifications == null) {
            initList()
        }
        val command = result[0].toInt()
        val cmdMessage = getSentCommandMessage(result[0])
        val resCode = result[1].toInt()
        if (command != 7 && command != 41 && command != 49 && command != lastCommandSent) {
            // not for us
            return
        }
        var cmdOutput: String? = null
        if (result.size >= 3 && result[2] != null) {
            cmdOutput = result[2]
            for (i in 3 until result.size) {
                cmdOutput += "," + result[i]
            }
        }
        val vehicleId = getLastSelectedCarId()
        when (resCode) {
            0 -> {
                val type =
                    if (command == 41) NotificationData.TYPE_USSD else NotificationData.TYPE_RESULT_SUCCESS
                // suppress first (empty) OK result for cmd 41:
                if (command == 7 || cmdOutput != null) {
                    cancelCommand()
                    notifications!!.addNotification(
                        type,
                        "$vehicleId: $cmdMessage",
                        cmdOutput ?: getString(R.string.msg_ok)
                    )
                    updateList()
                }
            }

            1 -> {
                cancelCommand()
                notifications!!.addNotification(
                    NotificationData.TYPE_RESULT_ERROR,
                    "$vehicleId: $cmdMessage",
                    getString(R.string.err_failed_smscmd)
                )
                updateList()
            }

            2 -> {
                cancelCommand()
                if (activity != null) {
                    Toast.makeText(
                        activity,
                        cmdMessage + " => " + getString(R.string.err_unsupported_operation),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            3 -> {
                cancelCommand()
                if (activity != null) {
                    Toast.makeText(
                        activity,
                        cmdMessage + " => " + getString(R.string.err_unimplemented_operation),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /*
     * Inner types
     */

    private class ItemsAdapter(
        context: Context?,
        private val fragment: NotificationsFragment,
        items: Array<NotificationData?>?
    ) : ArrayAdapter<NotificationData?>(context!!, R.layout.item_notifications, items!!) {

        private val inflater: LayoutInflater
        private val dateFormat = SimpleDateFormat("MMM d, HH:mm")

        init {
            inflater = LayoutInflater.from(context)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var v = convertView
            val it = getItem(position)
            if (it != null) {
                Log.e("NF", "TYP"+it.Type+"_"+it.Title+"_"+it.Message)
                v = inflater.inflate(if (it.msgType == 1) R.layout.item_notifications_received else (if(it.msgType == 0) R.layout.item_notifications_sent else R.layout.item_notifications_v2) , null)
                // set icon according to notification type:
                if (it.msgType == -1) {
                    val iv = v!!.findViewById<View>(R.id.textNotificationsIcon) as ImageView
                    iv.setImageResource(it.icon)
                    var tv = v.findViewById<View>(R.id.textNotificationsListMessage) as TextView
                    if (fragment.fontMonospace) {
                        tv.setTypeface(Typeface.MONOSPACE)
                    } else {
                        tv.setTypeface(Typeface.DEFAULT)
                    }
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fragment.fontSize)
                    tv.text = it.messageFormatted

                    // set title & timestamp:
                    tv = v!!.findViewById<View>(R.id.textNotificationsListTitle) as TextView
                    tv.text = it.Title

                    val tstamp = v.findViewById<View>(R.id.textNotificationsListTimestamp) as TextView
                    tstamp.text = dateFormat.format(it.Timestamp)
                } else {
                    val tv = v!!.findViewById<View>(R.id.textNotificationsListTitle) as TextView
                    tv.text = it.Title
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fragment.fontSize)
                    if (fragment.fontMonospace) {
                        tv.setTypeface(Typeface.MONOSPACE)
                    } else {
                        tv.setTypeface(Typeface.DEFAULT)
                    }
                    if (it.msgType == 1) {
                        tv.text = it.messageFormatted
                    }
                    val tstamp = v.findViewById<View>(R.id.textNotificationsListTimestamp) as TextView
                    tstamp.text = dateFormat.format(it.Timestamp)
                }
            }
            return v!!
        }
    }

    companion object {
        private const val TAG = "NotificationsFragment"
    }
}
