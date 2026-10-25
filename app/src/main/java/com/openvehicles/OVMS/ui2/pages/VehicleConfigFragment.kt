package com.openvehicles.OVMS.ui2.pages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.CarsStorage
import com.openvehicles.OVMS.utils.VehicleConfigHelper
import java.util.Locale

class VehicleConfigFragment : BaseFragment(), OnResultCommandListener {

    companion object {
        private const val TAG = "VehicleConfigFragment"

        private val STANDARD_RUBRIKEN = listOf(
            "vehicle",
            "auto",
            "system.adc",
            "modem",
            "network",
            "ota",
            "power",
            "server.v2",
            "server.v3"
        )
    }

    data class ConfigRubrik(
        val id: String,
        val displayName: String,
        val isVehicleSpecific: Boolean = false
    )

    private var carData: CarData? = null
    private lateinit var appPrefs: AppPrefs
    private lateinit var adapter: VehicleConfigAdapter

    private lateinit var tvVehicleTitle: TextView
    private lateinit var tvPrefixDisplay: TextView
    private lateinit var btnChangePrefix: MaterialButton
    private lateinit var etSearchConfig: TextInputEditText
    private lateinit var tabLayoutRubriken: TabLayout
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var rvConfigList: RecyclerView
    private lateinit var tvEmptyConfig: TextView
    private lateinit var pbLoading: ProgressBar
    private lateinit var fabAddConfig: ExtendedFloatingActionButton

    private var currentRubrikId: String = ""
    private val cachedConfigs = mutableMapOf<String, List<VehicleConfigParam>>()
    private val discoveredRubriken = mutableSetOf<String>()
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vehicle_config, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carData = CarsStorage.getSelectedCarData()
        appPrefs = AppPrefs(requireContext(), "ovms")

        tvVehicleTitle = findViewById(R.id.tvVehicleTitle) as TextView
        tvPrefixDisplay = findViewById(R.id.tvPrefixDisplay) as TextView
        btnChangePrefix = findViewById(R.id.btnChangePrefix) as MaterialButton
        etSearchConfig = findViewById(R.id.etSearchConfig) as TextInputEditText
        tabLayoutRubriken = findViewById(R.id.tabLayoutRubriken) as TabLayout
        swipeRefresh = findViewById(R.id.swipeRefreshConfig) as SwipeRefreshLayout
        rvConfigList = findViewById(R.id.rvConfigList) as RecyclerView
        tvEmptyConfig = findViewById(R.id.tvEmptyConfig) as TextView
        pbLoading = findViewById(R.id.pbConfigLoading) as ProgressBar
        fabAddConfig = findViewById(R.id.fabAddConfig) as ExtendedFloatingActionButton

        adapter = VehicleConfigAdapter(
            onItemClick = { param -> showEditParamDialog(param) },
            onDeleteClick = { param -> confirmDeleteParam(param) }
        )

        rvConfigList.layoutManager = LinearLayoutManager(requireContext())
        rvConfigList.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            loadAllConfigs(showProgress = false)
        }

        btnChangePrefix.setOnClickListener {
            showChangePrefixDialog()
        }

        fabAddConfig.setOnClickListener {
            showAddParamDialog()
        }

        etSearchConfig.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s?.toString() ?: "")
                updateEmptyState()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        setupRubrikenTabs()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.vehicle_config_title)
    }

    override fun onServiceAvailable(service: ApiService) {
        if (adapter.getAllCount() == 0 && !isLoading) {
            loadAllConfigs(showProgress = true)
        }
    }

    override fun onResultCommand(
        commandCode: Int,
        returnCode: Int,
        returnData: String,
        result: Array<String>
    ) {
        // Default callback for commands sent without dedicated listener
    }

    override fun update(carData: CarData?) {
        val carChanged = this.carData?.sel_vehicleid != carData?.sel_vehicleid
        this.carData = carData
        if (carChanged) {
            cachedConfigs.clear()
            discoveredRubriken.clear()
            currentRubrikId = ""
            setupRubrikenTabs()
            if (isAdded) {
                loadAllConfigs(showProgress = true)
            }
        }
    }

    private fun getRubrikenList(): List<ConfigRubrik> {
        val car = carData
        val vehiclePrefix = VehicleConfigHelper.getConfigPrefix(appPrefs, car)
        val list = mutableListOf<ConfigRubrik>()

        // 1. Vehicle-specific Rubrik (first in list)
        if (vehiclePrefix.isNotBlank()) {
            list.add(
                ConfigRubrik(
                    id = vehiclePrefix,
                    displayName = getString(R.string.vehicle_config_rubrik_vehicle, vehiclePrefix),
                    isVehicleSpecific = true
                )
            )
        }

        // 2. Discovered or fallback Standard Rubriken (filtering out vehiclePrefix)
        val baseRubriken = if (discoveredRubriken.isNotEmpty()) {
            discoveredRubriken
        } else {
            STANDARD_RUBRIKEN
        }

        val filteredRubriken = baseRubriken
            .filter { !it.equals(vehiclePrefix, ignoreCase = true) }
            .sortedWith(String.CASE_INSENSITIVE_ORDER)

        for (name in filteredRubriken) {
            list.add(
                ConfigRubrik(
                    id = name,
                    displayName = name,
                    isVehicleSpecific = false
                )
            )
        }

        Log.d(TAG, "getRubrikenList: vehiclePrefix=$vehiclePrefix, totalRubriken=${list.size}")
        return list
    }

    private fun setupRubrikenTabs() {
        tabLayoutRubriken.clearOnTabSelectedListeners()
        tabLayoutRubriken.removeAllTabs()

        val rubriken = getRubrikenList()
        var selectedIndex = 0

        for ((index, rubrik) in rubriken.withIndex()) {
            val tab = tabLayoutRubriken.newTab()
                .setText(rubrik.displayName)
                .setTag(rubrik.id)
            tabLayoutRubriken.addTab(tab)
            if (rubrik.id == currentRubrikId || (currentRubrikId.isEmpty() && rubrik.isVehicleSpecific)) {
                selectedIndex = index
                currentRubrikId = rubrik.id
            }
        }

        Log.d(TAG, "setupRubrikenTabs: created ${tabLayoutRubriken.tabCount} tabs, selectedIndex=$selectedIndex, currentRubrikId=$currentRubrikId")

        if (tabLayoutRubriken.tabCount > 0) {
            tabLayoutRubriken.selectTab(tabLayoutRubriken.getTabAt(selectedIndex))
        }

        tabLayoutRubriken.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val rubrikId = tab?.tag as? String ?: return
                onRubrikSelected(rubrikId)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {
                loadVehicleConfig(rubrikId = currentRubrikId, showProgress = false)
            }
        })
    }

    private fun onRubrikSelected(rubrikId: String) {
        currentRubrikId = rubrikId
        etSearchConfig.text?.clear()
        updateHeaderUI()

        Log.d(TAG, "onRubrikSelected: rubrikId=$rubrikId, cached=${cachedConfigs.containsKey(rubrikId)}")

        if (cachedConfigs.containsKey(rubrikId)) {
            adapter.setItems(cachedConfigs[rubrikId] ?: emptyList())
            updateEmptyState()
        } else {
            loadVehicleConfig(rubrikId = rubrikId, showProgress = true)
        }
    }

    private fun updateHeaderUI() {
        val car = carData
        val vehicleName = car?.sel_vehicleid ?: getString(R.string.cartype_unknown, "")
        val carType = car?.car_type ?: ""
        tvVehicleTitle.text = if (carType.isNotEmpty()) "$vehicleName ($carType)" else vehicleName

        val currentPrefix = VehicleConfigHelper.getConfigPrefix(appPrefs, car)
        val defaultPrefix = VehicleConfigHelper.getDefaultPrefix(car)

        val isVehicleSpecific = currentRubrikId == currentPrefix
        if (isVehicleSpecific) {
            btnChangePrefix.visibility = View.VISIBLE
            tvPrefixDisplay.text = "Prefix: $currentPrefix (Default: $defaultPrefix)"
        } else {
            btnChangePrefix.visibility = View.GONE
            tvPrefixDisplay.text = getString(R.string.vehicle_config_current_rubrik, currentRubrikId)
        }
    }

    private fun loadAllConfigs(showProgress: Boolean) {
        val car = carData
        val vid = car?.sel_vehicleid
        if (vid == null) {
            swipeRefresh.isRefreshing = false
            pbLoading.visibility = View.GONE
            updateEmptyState()
            return
        }

        if (showProgress) {
            pbLoading.visibility = View.VISIBLE
        }
        isLoading = true

        Log.d(TAG, "loadAllConfigs: sending '7,config list'")

        sendCommand("", "7,config list", object : OnResultCommandListener {
            override fun onResultCommand(
                commandCode: Int,
                returnCode: Int,
                returnData: String,
                result: Array<String>
            ) {
                isLoading = false
                swipeRefresh.isRefreshing = false
                pbLoading.visibility = View.GONE

                Log.d(TAG, "loadAllConfigs response: returnCode=$returnCode, dataLength=${returnData.length}")

                if (returnCode == 0 && returnData.isNotEmpty()) {
                    parseAndStoreRubriken(returnData)
                    setupRubrikenTabs()
                    if (currentRubrikId.isNotEmpty()) {
                        onRubrikSelected(currentRubrikId)
                    }
                } else {
                    Log.w(TAG, "Rubriken list load failed with returnCode: $returnCode")
                    updateEmptyState()
                }
            }
        })
    }

    private fun parseAndStoreRubriken(output: String) {
        discoveredRubriken.clear()
        val lines = output.split("\r\n", "\n", "\r")

        for (line in lines) {
            val rawLine = line.trim()
            if (rawLine.isEmpty()) continue
            val parts = rawLine.split("\\s+".toRegex(), limit = 2)
            if (parts.isNotEmpty()) {
                val rubrikId = parts[0].trim().lowercase(Locale.ROOT)
                if (rubrikId.isNotEmpty()) {
                    discoveredRubriken.add(rubrikId)
                }
            }
        }

        Log.d(TAG, "parseAndStoreRubriken: discovered ${discoveredRubriken.size} rubriken: $discoveredRubriken")
    }

    private fun loadVehicleConfig(rubrikId: String, showProgress: Boolean) {
        val car = carData
        val vid = car?.sel_vehicleid
        if (vid == null || rubrikId.isBlank()) {
            swipeRefresh.isRefreshing = false
            pbLoading.visibility = View.GONE
            updateEmptyState()
            return
        }

        if (showProgress) {
            pbLoading.visibility = View.VISIBLE
        }
        isLoading = true

        Log.d(TAG, "loadVehicleConfig: sending '7,config list $rubrikId'")

        sendCommand("", "7,config list $rubrikId", object : OnResultCommandListener {
            override fun onResultCommand(
                commandCode: Int,
                returnCode: Int,
                returnData: String,
                result: Array<String>
            ) {
                isLoading = false
                swipeRefresh.isRefreshing = false
                pbLoading.visibility = View.GONE

                Log.d(TAG, "loadVehicleConfig response for $rubrikId: returnCode=$returnCode, dataLength=${returnData.length}")

                if (returnCode == 0 && returnData.isNotEmpty()) {
                    val items = parseConfigList(returnData)
                    cachedConfigs[rubrikId] = items
                    if (currentRubrikId == rubrikId) {
                        adapter.setItems(items)
                        updateEmptyState()
                    }
                } else {
                    Log.w(TAG, "Config load for '$rubrikId' failed with returnCode: $returnCode")
                    if (currentRubrikId == rubrikId) {
                        cachedConfigs[rubrikId] = emptyList()
                        adapter.setItems(emptyList())
                        updateEmptyState()
                    }
                }
            }
        })
    }

    private fun parseConfigList(output: String): List<VehicleConfigParam> {
        val items = mutableListOf<VehicleConfigParam>()
        val lines = output.split("\r\n", "\n", "\r")
        for (line in lines) {
            val rawLine = line.trim()
            if (rawLine.isEmpty()) continue
            val parts = rawLine.split(":", limit = 2)
            if (parts.size == 2) {
                val key = parts[0].trim()
                val value = parts[1].removePrefix(" ")
                if (key.isNotEmpty()) {
                    items.add(VehicleConfigParam(key, value))
                }
            }
        }
        return items
    }

    private fun formatConfigSetCommand(rubrik: String, key: String, value: String): String {
        val escapedValue = "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\""
        return "7,config set $rubrik $key $escapedValue"
    }

    private fun updateEmptyState() {
        if (adapter.getItemsCount() == 0) {
            tvEmptyConfig.text = getString(R.string.vehicle_config_no_entries, currentRubrikId)
            tvEmptyConfig.visibility = View.VISIBLE
        } else {
            tvEmptyConfig.visibility = View.GONE
        }
    }

    private fun showEditParamDialog(param: VehicleConfigParam) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_vehicle_config_param, null)
        val tilKey = dialogView.findViewById<TextInputLayout>(R.id.tilParamKey)
        val etKey = dialogView.findViewById<TextInputEditText>(R.id.etParamKey)
        val etValue = dialogView.findViewById<TextInputEditText>(R.id.etParamValue)

        etKey.setText(param.key)
        tilKey.isEnabled = false
        etValue.setText(param.value)

        val targetRubrik = currentRubrikId

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.vehicle_config_edit_param)
            .setMessage(getString(R.string.vehicle_config_current_rubrik, targetRubrik))
            .setView(dialogView)
            .setPositiveButton(R.string.Save) { _, _ ->
                val newValue = etValue.text?.toString() ?: ""
                sendCommand(
                    "Saving...",
                    formatConfigSetCommand(targetRubrik, param.key, newValue),
                    object : OnResultCommandListener {
                        override fun onResultCommand(
                            commandCode: Int,
                            returnCode: Int,
                            returnData: String,
                            result: Array<String>
                        ) {
                            cachedConfigs.remove(targetRubrik)
                            view?.postDelayed({
                                if (isAdded && view != null) {
                                    loadVehicleConfig(targetRubrik, showProgress = false)
                                }
                            }, 1000)
                        }
                    }
                )
            }
            .setNeutralButton(R.string.vehicle_config_delete) { _, _ ->
                confirmDeleteParam(param)
            }
            .setNegativeButton(R.string.Cancel, null)
            .show()
    }

    private fun showAddParamDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_vehicle_config_param, null)
        val etKey = dialogView.findViewById<TextInputEditText>(R.id.etParamKey)
        val etValue = dialogView.findViewById<TextInputEditText>(R.id.etParamValue)

        val targetRubrik = currentRubrikId

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.vehicle_config_add_param)
            .setMessage(getString(R.string.vehicle_config_current_rubrik, targetRubrik))
            .setView(dialogView)
            .setPositiveButton(R.string.Save, null)
            .setNegativeButton(R.string.Cancel, null)
            .create()

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val newKey = etKey.text?.toString()?.trim() ?: ""
            val newValue = etValue.text?.toString() ?: ""
            if (newKey.isBlank()) {
                Toast.makeText(requireContext(), "Key cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            sendCommand(
                "Saving...",
                formatConfigSetCommand(targetRubrik, newKey, newValue),
                object : OnResultCommandListener {
                    override fun onResultCommand(
                        commandCode: Int,
                        returnCode: Int,
                        returnData: String,
                        result: Array<String>
                    ) {
                        cachedConfigs.remove(targetRubrik)
                        view?.postDelayed({
                            if (isAdded && view != null) {
                                loadVehicleConfig(targetRubrik, showProgress = false)
                            }
                        }, 1000)
                    }
                }
            )
            dialog.dismiss()
        }
    }

    private fun confirmDeleteParam(param: VehicleConfigParam) {
        val targetRubrik = currentRubrikId
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.vehicle_config_delete)
            .setMessage(getString(R.string.vehicle_config_delete_confirm, param.key))
            .setPositiveButton(R.string.vehicle_config_delete) { _, _ ->
                sendCommand(
                    "Deleting...",
                    "7,config rm $targetRubrik ${param.key}",
                    object : OnResultCommandListener {
                        override fun onResultCommand(
                            commandCode: Int,
                            returnCode: Int,
                            returnData: String,
                            result: Array<String>
                        ) {
                            cachedConfigs.remove(targetRubrik)
                            view?.postDelayed({
                                if (isAdded && view != null) {
                                    loadVehicleConfig(targetRubrik, showProgress = false)
                                }
                            }, 1000)
                        }
                    }
                )
            }
            .setNegativeButton(R.string.Cancel, null)
            .show()
    }

    private fun showChangePrefixDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_vehicle_config_prefix, null)
        val etPrefix = dialogView.findViewById<TextInputEditText>(R.id.etPrefixInput)
        val btnReset = dialogView.findViewById<MaterialButton>(R.id.btnResetPrefix)

        val currentPrefix = VehicleConfigHelper.getConfigPrefix(appPrefs, carData)
        val defaultPrefix = VehicleConfigHelper.getDefaultPrefix(carData)
        etPrefix.setText(currentPrefix)

        btnReset.setOnClickListener {
            etPrefix.setText(defaultPrefix)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.vehicle_config_prefix_dialog_title)
            .setView(dialogView)
            .setPositiveButton(R.string.Save) { _, _ ->
                val entered = etPrefix.text?.toString()?.trim()?.lowercase(Locale.ROOT) ?: ""
                val newPrefix = if (entered.isNotBlank()) entered else defaultPrefix
                val vid = carData?.sel_vehicleid
                VehicleConfigHelper.setConfigPrefix(appPrefs, vid, newPrefix)

                currentRubrikId = newPrefix
                setupRubrikenTabs()
            }
            .setNegativeButton(R.string.Cancel, null)
            .show()
    }
}
