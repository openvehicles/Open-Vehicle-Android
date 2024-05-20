/**
 *
 */
package com.openvehicles.OVMS.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/**
 * @author android
 */
class AppPrefs(
    context: Context,
    prefName: String?
) {

    private val appSharedPrefs: SharedPreferences = context.getSharedPreferences(prefName, Activity.MODE_PRIVATE)
    private val prefsEditor: SharedPreferences.Editor = appSharedPrefs.edit()

    /**
     * Returns the value from the preference that matches [key].
     */
    fun getData(key: String?): String {
        return appSharedPrefs.getString(key, "")!!
    }

    fun getData(key: String?, defaultValue: String?): String? {
        return appSharedPrefs.getString(key, defaultValue)
    }

    /**
     * Saves the value of the preference.
     */
    fun saveData(key: String?, text: String?) {
        prefsEditor.putString(key, text)
        prefsEditor.commit()
    }
}
