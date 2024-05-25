package com.openvehicles.OVMS.ui

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.CommandActivity
import com.openvehicles.OVMS.ui.BaseFragmentActivity.Companion.showForResult
import com.openvehicles.OVMS.ui.settings.StoredCommandFragment
import com.openvehicles.OVMS.utils.AppPrefs

/**
 * This Activity receives the CREATE_SHORTCUT intent, it allows the user to create
 * a shortcut from a StoredCommand for external Apps like Automagic & KWGT.
 *
 * Note: the shortcut isn't added to the launcher by this Activity, see
 * StoredCommandFragment.pinCommand() for this.
 */
class CreateShortcutActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: intent $intent")

        showForResult(
            this,
            StoredCommandFragment::class.java,
            StoredCommandFragment.REQUEST_SELECT_SHORTCUT,
            null,
            Configuration.ORIENTATION_UNDEFINED
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult: reqCode=$requestCode, resCode=$resultCode")

        if (resultCode != RESULT_OK || data == null) {
            setResult(RESULT_CANCELED)
            finish()
            return
        }

        val appPrefs = AppPrefs(this, "ovms")
        val key = data.getLongExtra("key", 0)
        val title = data.getStringExtra("title")
        val command = data.getStringExtra("command")
        Log.d(TAG, "onActivityResult: command: $command")
        if (key == 0L || title == null || command == null) {
            setResult(RESULT_CANCELED)
            finish()
            return
        }

        val appContext = applicationContext
        val intent = Intent(appContext, CommandActivity::class.java).apply {
            setAction("com.openvehicles.OVMS.action.COMMAND")
            setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NO_USER_ACTION)
            putExtra("apikey", appPrefs.getData("APIKey"))
            putExtras(data)
        }
        val shortcut = ShortcutInfoCompat.Builder(appContext, "StoredCommand_$key")
            .setShortLabel(title)
            .setIcon(IconCompat.createWithResource(appContext, R.drawable.ic_remote_control))
            .setIntent(intent)
            .build()
        setResult(RESULT_OK, ShortcutManagerCompat.createShortcutResultIntent(this, shortcut))
        finish()
    }

    /*
     * Inner types
     */

    companion object {
        private const val TAG = "CreateShortcutActivity"
    }
}
