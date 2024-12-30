package com.openvehicles.OVMS.ui2.pages.settings

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.openvehicles.OVMS.R

/**
 * UI Settings
 */
class AppUISettingsFragment: PreferenceFragmentCompat() {

    fun MultiSelectListPreference.setSummaryFromValues(values: Array<String>) {
        summary = values.map {entries[findIndexOfValue(it)]}.joinToString(", ")
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey)

        val home_range_display_mode_preference = findPreference<MultiSelectListPreference>("home_range_display_mode")
        home_range_display_mode_preference?.setSummaryFromValues(requireContext().resources.getStringArray(R.array.home_range_display_mode))

        findPreference<Preference>("legacy_settings")?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.action_appUISettingsFragment_to_globalOptionsFragment)
            false
        }

        findPreference<Preference>("home_quick_actions")?.setOnPreferenceClickListener {

            false
        }
    }

}