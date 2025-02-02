package com.openvehicles.OVMS.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Spinner
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.ConnectionList
import com.openvehicles.OVMS.utils.ConnectionList.ConnectionsListener

class MapSettingsFragment : Fragment(), ConnectionsListener {

    private lateinit var view: View
    private lateinit var appPrefs: AppPrefs
    private lateinit var connectionList: ConnectionList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.map_settings, null)
        appPrefs = AppPrefs(requireActivity(), "ovms")
        connectionList = ConnectionList(requireActivity(), this, false)
        return view
    }

    override fun onConnectionChanged(conId: String?, conName: String?) {
        MapFragment.updateMap.updateFilter(conId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClusteringViews()
    }

    private fun setUpClusteringViews() {
        val view = requireView()
        val ocmEnableCheckbox = view.findViewById<View>(R.id.checkbox_ocm_enable) as CheckBox
        val clusterCheckbox = view.findViewById<View>(R.id.checkbox_cluster) as CheckBox
        val clusterSizeSeekbar = view.findViewById<View>(R.id.seekbar_cluster_size) as SeekBar
        val maxResultsSpinner = view.findViewById<View>(R.id.ocm_maxresults) as Spinner
        val btnConnections = view.findViewById<View>(R.id.btn_connections) as Button
        val btnClearCache = view.findViewById<View>(R.id.btn_clearcache) as Button
        val ocmEnabled = appPrefs.getData("option_ocm_enabled", "1") == "1"
        ocmEnableCheckbox.setChecked(ocmEnabled)
        if (appPrefs.getData("check") == "false") {
            clusterCheckbox.setChecked(false)
            clusterSizeSeekbar.setEnabled(false)
        }
        try {
            // Note: probably due to cargo culting, the prefs name for the cluster size is "progress"
            clusterSizeSeekbar.progress = appPrefs.getData("progress").toInt()
        } catch (e: Exception) {
            // TODO: handle exception
        }

        // set maxresults spinner to current value:
        try {
            val ocmMaxResultsValue = appPrefs.getData("maxresults")
            val ocmMaxResultsOptions = resources.getStringArray(R.array.ocm_options_maxresults)
            var ocmMaxResultsIndex = 0
            for (i in ocmMaxResultsOptions.indices) {
                if (ocmMaxResultsOptions[i] == ocmMaxResultsValue) {
                    ocmMaxResultsIndex = i
                    break
                }
            }
            maxResultsSpinner.setSelection(ocmMaxResultsIndex)
        } catch (e: Exception) {
            // TODO: handle exception
        }
        ocmEnableCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            appPrefs.saveData("option_ocm_enabled", if (isChecked) "1" else "0")
            if (!isChecked) {
                MapFragment.updateMap.clearCache()
            }
        }
        clusterCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            clusterSizeSeekbar.setEnabled(isChecked)
            appPrefs.saveData("check", "" + isChecked)
            MapFragment.updateMap.updateClustering(clusterSizeSeekbar.progress, isChecked)
        }
        clusterSizeSeekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val progress = seekBar.progress
                val enabled = clusterCheckbox.isChecked
                appPrefs.saveData("progress", "" + progress)
                MapFragment.updateMap.updateClustering(progress, enabled)
            }
        })
        maxResultsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val selected = adapterView.getItemAtPosition(i).toString()
                if (appPrefs.getData("maxresults") != selected) {
                    appPrefs.saveData("maxresults", "" + selected)
                    MapFragment.updateMap.clearCache()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        btnConnections.setOnClickListener { connectionList.sublist() }
        btnClearCache.setOnClickListener {
            MapFragment.updateMap.clearCache()
            Toast.makeText(activity, getString(R.string.msg_cache_cleared), LENGTH_SHORT).show()
        }
    }

    /*
     * Inner types
     */

    companion object {
        private const val TAG = "FragMapSettings(OCM)"
    }

    interface UpdateMap {

        fun updateClustering(clusterSizeIndex: Int, isEnabled: Boolean)

        fun clearCache()

        fun updateFilter(connectionList: String?)
    }
}
