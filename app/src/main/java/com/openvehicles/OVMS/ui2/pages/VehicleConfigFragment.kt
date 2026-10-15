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
            cachedConfigs.remove(currentRubrikId)
            loadVehicleConfig(showProgress = false, forceReload = true)
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
            loadVehicleConfig(showProgress = true)
        }
    }

    override fun onResultCommand(result: Array<String>) {
        // Default callback for commands sent without dedicated listener
    }

    override fun update(carData: CarData?) {
        val carChanged = this.carData?.sel_vehicleid != carData?.sel_vehicleid
        this.carData = carData
        if (carChanged) {
            cachedConfigs.clear()
            setupRubrikenTabs()
        }
    }

    private fun getRubrikenList(): List<ConfigRubrik> {
        val car = carData
        val vehiclePrefix = VehicleConfigHelper.getConfigPrefix(appPrefs, car)
        val list = mutableListOf<ConfigRubrik>()

        // 1. Fahrzeugspezifische Rubrik (first in list)
        list.add(
            ConfigRubrik(
                id = vehiclePrefix,
                displayName = getString(R.string.vehicle_config_rubrik_vehicle, vehiclePrefix),
                isVehicleSpecific = true
            )
        )

        // 2. Standard Rubriken
        for (name in STANDARD_RUBRIKEN) {
            list.add(
                ConfigRubrik(
                    id = name,
                    displayName = name,
                    isVehicleSpecific = false
                )
            )
        }

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

        tabLayoutRubriken.selectTab(tabLayoutRubriken.getTabAt(selectedIndex))
        tabLayoutRubriken.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val rubrikId = tab?.tag as? String ?: return
                onRubrikSelected(rubrikId)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {
                loadVehicleConfig(showProgress = false, forceReload = true)
            }
        })

        onRubrikSelected(currentRubrikId)
    }

    private fun onRubrikSelected(rubrikId: String) {
        currentRubrikId = rubrikId
        etSearchConfig.text?.clear()
        updateHeaderUI()

        if (cachedConfigs.containsKey(rubrikId)) {
            adapter.setItems(cachedConfigs[rubrikId] ?: emptyList())
            updateEmptyState()
        } else {
            loadVehicleConfig(showProgress = true, forceReload = false)
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

    private fun loadVehicleConfig(showProgress: Boolean, forceReload: Boolean = false) {
        val car = carData
        val vid = car?.sel_vehicleid
        if (vid == null) {
            swipeRefresh.isRefreshing = false
            pbLoading.visibility = View.GONE
            updateEmptyState()
            return
        }

        val rubrik = currentRubrikId
        if (rubrik.isBlank()) {
            swipeRefresh.isRefreshing = false
            pbLoading.visibility = View.GONE
            updateEmptyState()
            return
        }

        if (showProgress) {
            pbLoading.visibility = View.VISIBLE
        }
        isLoading = true

        sendCommand("", "7,config list $rubrik", object : OnResultCommandListener {
            override fun onResultCommand(result: Array<String>) {
                isLoading = false
                swipeRefresh.isRefreshing = false
                pbLoading.visibility = View.GONE

                if (result.size > 2 && result[1] == "0") {
                    val items = parseConfigList(result[2])
                    cachedConfigs[rubrik] = items
                    if (currentRubrikId == rubrik) {
                        adapter.setItems(items)
                        updateEmptyState()
                    }
                } else {
                    Log.w(TAG, "Config load for '$rubrik' failed with code: ${result.getOrNull(1)}")
                    if (currentRubrikId == rubrik) {
                        cachedConfigs[rubrik] = emptyList()
                        adapter.setItems(emptyList())
                        updateEmptyState()
                    }
                }
            }
        })
    }

    private fun parseConfigList(output: String): List<VehicleConfigParam> {
        val items = mutableListOf<VehicleConfigParam>()
        val lines = output.lines()
        for (line in lines) {
            val parts = line.trim().split(":", limit = 2)
            if (parts.size == 2) {
                val key = parts[0].trim()
                val value = parts[1].trim()
                if (key.isNotEmpty()) {
                    items.add(VehicleConfigParam(key, value))
                }
            }
        }
        return items
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
                val newValue = etValue.text?.toString()?.trim() ?: ""
                sendCommand(
                    "Saving...",
                    "7,config set $targetRubrik ${param.key} $newValue",
                    object : OnResultCommandListener {
                        override fun onResultCommand(result: Array<String>) {
                            cachedConfigs.remove(targetRubrik)
                            view?.postDelayed({ loadVehicleConfig(showProgress = false, forceReload = true) }, 1000)
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

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.vehicle_config_add_param)
            .setMessage(getString(R.string.vehicle_config_current_rubrik, targetRubrik))
            .setView(dialogView)
            .setPositiveButton(R.string.Save) { _, _ ->
                val newKey = etKey.text?.toString()?.trim() ?: ""
                val newValue = etValue.text?.toString()?.trim() ?: ""
                if (newKey.isBlank()) {
                    Toast.makeText(requireContext(), "Key cannot be empty", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                sendCommand(
                    "Saving...",
                    "7,config set $targetRubrik $newKey $newValue",
                    object : OnResultCommandListener {
                        override fun onResultCommand(result: Array<String>) {
                            cachedConfigs.remove(targetRubrik)
                            view?.postDelayed({ loadVehicleConfig(showProgress = false, forceReload = true) }, 1000)
                        }
                    }
                )
            }
            .setNegativeButton(R.string.Cancel, null)
            .show()
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
                        override fun onResultCommand(result: Array<String>) {
                            cachedConfigs.remove(targetRubrik)
                            view?.postDelayed({ loadVehicleConfig(showProgress = false, forceReload = true) }, 1000)
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

                cachedConfigs.remove(currentPrefix)
                currentRubrikId = newPrefix
                setupRubrikenTabs()
            }
            .setNegativeButton(R.string.Cancel, null)
            .show()
    }
}
