package com.openvehicles.OVMS.ui2.misc

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.openvehicles.OVMS.utils.AppPrefs

/**
 * Light / dark / follow system.
 *
 * The choice is stored in the "ovms" preferences and applied through
 * [AppCompatDelegate], so it survives restarts and applies to every Activity without
 * each screen having to know about it.
 */
object ThemeMode {

    const val PREF_KEY = "app_theme_mode"

    const val LIGHT = "light"
    const val DARK = "dark"
    const val SYSTEM = "system"

    /** Default: follow the system theme. */
    const val DEFAULT = SYSTEM

    fun read(context: Context): String {
        val prefs = AppPrefs(context, "ovms")
        val value = prefs.getData(PREF_KEY, DEFAULT)
        return if (value.isNullOrBlank()) DEFAULT else value
    }

    fun write(context: Context, mode: String) {
        AppPrefs(context, "ovms").saveData(PREF_KEY, mode)
    }

    private fun toDelegateMode(mode: String): Int = when (mode) {
        LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        DARK -> AppCompatDelegate.MODE_NIGHT_YES
        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    /** Applies the stored choice. Call once on application start. */
    fun apply(context: Context) {
        val mode = read(context)
        Log.d("ThemeMode", "apply: mode=$mode")
        AppCompatDelegate.setDefaultNightMode(toDelegateMode(mode))
    }

    /**
     * Stores and applies a new choice. Activities recreate themselves, so the change
     * is visible immediately.
     */
    fun applyAndStore(context: Context, mode: String) {
        write(context, mode)
        AppCompatDelegate.setDefaultNightMode(toDelegateMode(mode))
    }
}
