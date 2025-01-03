package com.openvehicles.OVMS.ui2.pages.settings

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceViewHolder
import androidx.preference.SwitchPreferenceCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.ui.settings.GlobalOptionsFragment
import com.openvehicles.OVMS.ui.utils.Ui
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.Sys

/**
 * UI Settings
 */
class AppUISettingsFragment: PreferenceFragmentCompat() {

    companion object {
        const val TAG = "AppUISettingsFragment"
    }

    fun MultiSelectListPreference.setSummaryFromValues(values: Array<String>) {
        summary = if (values.isEmpty())
            arrayOf(getString(R.string.none), getString(R.string.home_soc_hint)).joinToString(separator = "\n")
        else
            values.joinToString(", ") { entries[findIndexOfValue(it)] }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey)

        val appPrefs = AppPrefs(requireContext(), "ovms")

        val home_range_display_mode_preference = findPreference<MultiSelectListPreference>("home_range_display_mode")
        home_range_display_mode_preference?.setSummaryFromValues(home_range_display_mode_preference.values.toTypedArray())
        home_range_display_mode_preference?.onPreferenceChangeListener = object : OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
                home_range_display_mode_preference?.setSummaryFromValues((newValue as Set<String>).toTypedArray())
                return true
            }
        }

        // Communication settings

        val apiKeyPreference = findPreference<Preference>("api_key")
        apiKeyPreference?.title = getString(R.string.lb_options_apikey).dropLast(1)
        apiKeyPreference?.summary = appPrefs.getData("APIKey")
        apiKeyPreference?.setOnPreferenceClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.lb_options_apikey_renew_confirm)
                .setNegativeButton(R.string.Cancel, null)
                .setPositiveButton(R.string.Yes) { dialog1: DialogInterface?, which: Int ->
                    val apiKey = Sys.getRandomString(25)
                    appPrefs.saveData("APIKey", apiKey)
                    Log.d(TAG, "onClick: generated new APIKey: $apiKey")
                    Ui.setValue(requireView(), R.id.tv_options_apikey, apiKey)
                }
                .create().show()
            false
        }

        val backgroundConnectionPreference = findPreference<SwitchPreferenceCompat>("communication_bg_connection")
        backgroundConnectionPreference?.isChecked = appPrefs.getData("option_service_enabled", "0") == "1"
        backgroundConnectionPreference?.onPreferenceChangeListener =
            OnPreferenceChangeListener { preference, newValue ->
                appPrefs.saveData("option_service_enabled", if (newValue as Boolean) "1" else "0")
                val intent =
                    Intent(if (newValue) ApiService.ACTION_ENABLE else ApiService.ACTION_DISABLE)
                requireContext().sendBroadcast(intent)
                true
            }

        val broadcastPreference = findPreference<SwitchPreferenceCompat>("communication_broadcast")
        broadcastPreference?.isChecked = appPrefs.getData("option_broadcast_enabled", "0") == "1"
        broadcastPreference?.onPreferenceChangeListener =
            OnPreferenceChangeListener { preference, newValue ->
                appPrefs.saveData("option_broadcast_enabled", if (newValue as Boolean) "1" else "0")
                true
            }

        val commandPreference = findPreference<SwitchPreferenceCompat>("communication_commands")
        commandPreference?.isChecked = appPrefs.getData("option_commands_enabled", "0") == "1"
        commandPreference?.onPreferenceChangeListener =
            OnPreferenceChangeListener { preference, newValue ->
                appPrefs.saveData("option_commands_enabled", if (newValue as Boolean) "1" else "0")
                true
            }

        val oldUIPreference = findPreference<SwitchPreferenceCompat>("other_oldui")
        oldUIPreference?.isChecked = appPrefs.getData("option_oldui_enabled", "0") == "1"
        oldUIPreference?.onPreferenceChangeListener =
            OnPreferenceChangeListener { preference, newValue ->
                appPrefs.saveData("option_oldui_enabled", if (newValue as Boolean) "1" else "0")
                val intent =
                    Intent(if (newValue) ApiService.ACTION_ENABLE else ApiService.ACTION_DISABLE)
                requireContext().sendBroadcast(intent)
                true
            }

    }

}