package com.openvehicles.OVMS.ui.settings

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.IconCompat
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.CommandActivity
import com.openvehicles.OVMS.entities.StoredCommand
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.BaseFragmentActivity
import com.openvehicles.OVMS.ui.utils.Database
import com.openvehicles.OVMS.ui.utils.Ui.getValue
import com.openvehicles.OVMS.ui.utils.Ui.setButtonImage
import com.openvehicles.OVMS.ui.utils.Ui.setOnClick
import com.openvehicles.OVMS.ui.utils.Ui.setValue
import com.openvehicles.OVMS.utils.AppPrefs

/**
 * StoredCommand editor & selector
 *
 * This fragment is called from the NotificationsFragment and from the CreateShortcutActivity
 * for management & selection of stored commands for execution or bookmark creation.
 */
class StoredCommandFragment : BaseFragment(), OnItemClickListener {

    private var appPrefs: AppPrefs? = null
    private lateinit var database: Database
    private lateinit var storedCommands: ArrayList<StoredCommand>
    private lateinit var listView: ListView
    private lateinit var adapter: StoredCommandAdapter
    private var requestCode = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container == null) {
            return null
        }
        val context = context
            ?: return null

        // Get our operation mode:
        val args = arguments
        if (args != null) {
            requestCode = args.getInt(BaseFragmentActivity.EXT_REQUEST_CODE, 0)
        }

        // Load stored commands from database:
        appPrefs = AppPrefs(context, "ovms")
        database = Database(context)
        storedCommands = database.getStoredCommands()

        // Create user interface:
        activity?.setTitle(R.string.stored_commands_title)
        setHasOptionsMenu(true)
        listView = ListView(container.context)
        listView.fitsSystemWindows = true
        listView.onItemClickListener = this
        adapter = StoredCommandAdapter(context, R.layout.item_stored_command, storedCommands, inflater)
        listView.setAdapter(adapter)
        return listView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.stored_command_list_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        if (menuId == R.id.mi_add) {
            showItemEditor(null)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (id) {
            R.id.btn_select.toLong() -> {
                selectItem(position)
            }
            R.id.btn_pin.toLong() -> {
                val cmd = adapter.getItem(position)
                cmd?.let { pinCommand(it) }
            }
            R.id.btn_delete.toLong() -> {
                val cmd = adapter.getItem(position)
                cmd?.let { confirmDeleteCommand(it) }
            }
            else -> {
                val cmd = adapter.getItem(position)
                cmd?.let { showItemEditor(it) }
            }
        }
    }

    private fun confirmDeleteCommand(cmd: StoredCommand) {
        val context = context ?: return
        
        AlertDialog.Builder(context)
            .setTitle(R.string.Delete)
            .setMessage(getString(R.string.confirm_delete_stored_command, cmd.title))
            .setNegativeButton(R.string.Cancel, null)
            .setPositiveButton(R.string.Delete) { _, _ ->
                deleteCommand(cmd)
            }
            .show()
    }

    private fun showItemEditor(cmd: StoredCommand?) {
        val context = context
            ?: return

        val view = LayoutInflater.from(context).inflate(R.layout.dlg_stored_command, null)
        if (cmd == null) {
            // Create new command:
            val dialog = AlertDialog.Builder(context)
                .setMessage(R.string.stored_commands_add)
                .setView(view)
                .setNegativeButton(R.string.Cancel, null)
                .setPositiveButton(R.string.Save) { dialog1: DialogInterface?, which: Int ->
                    val cmd1 = StoredCommand(
                        getValue(view, R.id.etxt_input_title),
                        getValue(view, R.id.etxt_input_command)
                    )
                    saveCommand(cmd1)
                }
                .create()
            dialog.show()
        } else {
            // Edit existing command:
            view.tag = cmd
            setValue(view, R.id.etxt_input_title, cmd.title)
            setValue(view, R.id.etxt_input_command, cmd.command)
            val dialog = AlertDialog.Builder(context)
                .setMessage(R.string.stored_commands_edit)
                .setView(view)
                .setNegativeButton(R.string.Cancel, null)
                .setNeutralButton(R.string.Delete) { dialog12: DialogInterface?, which: Int ->
                    val cmd12 = view.tag as StoredCommand
                    deleteCommand(cmd12)
                }
                .setPositiveButton(R.string.Save) { dialog13: DialogInterface?, which: Int ->
                    val cmd13 = view.tag as StoredCommand
                    cmd13.title = getValue(view, R.id.etxt_input_title)
                    cmd13.command = getValue(view, R.id.etxt_input_command)
                    saveCommand(cmd13)
                }
                .create()
            dialog.show()
        }
    }

    private fun saveCommand(cmd: StoredCommand) {
        if (database.saveStoredCommand(cmd)) {
            val index = storedCommands.indexOf(cmd)
            if (index < 0) storedCommands.add(cmd) else storedCommands[index] = cmd
            storedCommands.sort()
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(context, R.string.DatabaseError, Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteCommand(cmd: StoredCommand) {
        if (database.deleteStoredCommand(cmd)) {
            storedCommands.remove(cmd)
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(context, R.string.DatabaseError, Toast.LENGTH_LONG).show()
        }
    }

    private fun pinCommand(cmd: StoredCommand) {
        val shortcut: ShortcutInfoCompat
        val context = context ?: return
        val intent = Intent(context, CommandActivity::class.java)
        intent.setAction("com.openvehicles.OVMS.action.COMMAND")
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NO_USER_ACTION)
        intent.putExtra("apikey", appPrefs!!.getData("APIKey"))
        intent.putExtra("key", cmd.key)
        intent.putExtra("title", cmd.title)
        intent.putExtra("command", cmd.command)
        shortcut = ShortcutInfoCompat.Builder(context, "StoredCommand_" + cmd.key)
            .setShortLabel(cmd.title)
            .setIcon(IconCompat.createWithResource(context, R.drawable.ic_remote_control))
            .setIntent(intent)
            .build()
        val ok = ShortcutManagerCompat.requestPinShortcut(context, shortcut, null)
        if (ok) {
            // Beginning with API 26 (Android 8), the system will ask the user for permission
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                Toast.makeText(context, R.string.PinRequestSuccess, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, R.string.PinRequestFailure, Toast.LENGTH_LONG).show()
        }
    }

    private fun selectItem(position: Int) {
        val activity = activity
            ?: return

        // Check if we're expected to return a result:
        if (requestCode == 0) {
            return
        }

        // OK, return the command selected:
        val cmd = adapter.getItem(position)
            ?: return

        Log.d(TAG, "selectItem: reqCode=" + requestCode + " returning command: " + cmd.command)
        val result = Intent()
        result.putExtra("key", cmd.key)
        result.putExtra("title", cmd.title)
        result.putExtra("command", cmd.command)
        if (activity.parent != null) activity.parent.setResult(
            Activity.RESULT_OK,
            result
        ) else {
            activity.setResult(Activity.RESULT_OK, result)
        }
        activity.finish()
    }

    /*
     * Inner types
     */

    companion object {
        private const val TAG = "StoredCommandFragment"

        const val REQUEST_SELECT_EXECUTE = 1 // select a command for immediate execution
        const val REQUEST_SELECT_SHORTCUT = 2 // select a command for building a shortcut
    }

    private inner class StoredCommandAdapter(
        context: Context,
        resource: Int,
        objects: List<StoredCommand?>,
        private val inflater: LayoutInflater
    ) : ArrayAdapter<StoredCommand?>(context, resource, objects), View.OnClickListener {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (view == null) {
                view = inflater.inflate(R.layout.item_stored_command, null)
            }
            setOnClick(view!!, R.id.linearLayout1, position, this, null)
            setOnClick(view, R.id.btn_select, position, this, null)
            setOnClick(view, R.id.btn_delete, position, this, null)
            val btnPin = setOnClick(view, R.id.btn_pin, position, this, null)
            if (requestCode == REQUEST_SELECT_SHORTCUT) {
                val icon = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_arrow_forward, null
                )
                setButtonImage(view, R.id.btn_select, icon)
                btnPin.visibility = View.GONE
            }
            val cmd = getItem(position)
            if (cmd != null) {
                setValue(view, R.id.title, cmd.title)
                setValue(view, R.id.text, cmd.command)
            }
            return view
        }

        override fun onClick(view: View) {
            onItemClick(listView, view, view.tag as Int, view.id.toLong())
        }
    }
}
