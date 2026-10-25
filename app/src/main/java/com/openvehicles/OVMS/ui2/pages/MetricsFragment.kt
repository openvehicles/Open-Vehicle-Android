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
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.utils.CarsStorage

class MetricsFragment : BaseFragment(), OnResultCommandListener {

    companion object {
        private const val TAG = "MetricsFragment"
    }

    private var carData: CarData? = null
    private lateinit var adapter: MetricsAdapter

    private lateinit var etSearchMetrics: TextInputEditText
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var rvMetricsList: RecyclerView
    private lateinit var tvEmptyMetrics: TextView
    private lateinit var pbLoading: ProgressBar

    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_metrics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carData = CarsStorage.getSelectedCarData()

        etSearchMetrics = findViewById(R.id.etSearchMetrics) as TextInputEditText
        swipeRefresh = findViewById(R.id.swipeRefreshMetrics) as SwipeRefreshLayout
        rvMetricsList = findViewById(R.id.rvMetricsList) as RecyclerView
        tvEmptyMetrics = findViewById(R.id.tvEmptyMetrics) as TextView
        pbLoading = findViewById(R.id.pbMetricsLoading) as ProgressBar

        adapter = MetricsAdapter(
            onItemClick = { metric -> showEditMetricDialog(metric) }
        )

        rvMetricsList.layoutManager = LinearLayoutManager(requireContext())
        rvMetricsList.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            loadMetrics(showProgress = false)
        }

        etSearchMetrics.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s?.toString() ?: "")
                updateEmptyState()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.setTitle(R.string.metrics_title)
    }

    override fun onServiceAvailable(service: ApiService) {
        if (adapter.getAllCount() == 0 && !isLoading) {
            loadMetrics(showProgress = true)
        }
    }

    override fun onResultCommand(
        commandCode: Int,
        returnCode: Int,
        returnData: String,
        result: Array<String>
    ) {
        // Default callback for commands
    }

    override fun update(carData: CarData?) {
        val carChanged = this.carData?.sel_vehicleid != carData?.sel_vehicleid
        this.carData = carData
        if (carChanged) {
            adapter.setItems(emptyList())
            updateEmptyState()
            if (isAdded) {
                loadMetrics(showProgress = true)
            }
        }
    }

    private fun loadMetrics(showProgress: Boolean) {
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

        Log.d(TAG, "loadMetrics: sending '7,metrics list'")

        sendCommand("", "7,metrics list", object : OnResultCommandListener {
            override fun onResultCommand(
                commandCode: Int,
                returnCode: Int,
                returnData: String,
                result: Array<String>
            ) {
                isLoading = false
                swipeRefresh.isRefreshing = false
                pbLoading.visibility = View.GONE

                Log.d(TAG, "loadMetrics response: returnCode=$returnCode, dataLength=${returnData.length}")

                if (returnCode == 0 && returnData.isNotEmpty()) {
                    val items = parseMetricsList(returnData)
                    adapter.setItems(items)
                    adapter.filter(etSearchMetrics.text?.toString() ?: "")
                    updateEmptyState()
                } else {
                    Log.w(TAG, "Metrics list load failed with returnCode: $returnCode")
                    updateEmptyState()
                }
            }
        })
    }

    private fun parseMetricsList(output: String): List<MetricItem> {
        val items = mutableListOf<MetricItem>()
        val lines = output.split("\r\n", "\n", "\r")

        Log.d(TAG, "parseMetricsList: output line count=${lines.size}, sample=${output.take(300)}")

        for (line in lines) {
            val rawLine = line.trim()
            if (rawLine.isEmpty()) continue

            val key: String
            val value: String

            when {
                rawLine.contains(":") -> {
                    val parts = rawLine.split(":", limit = 2)
                    key = parts[0].trim()
                    value = parts[1].trim()
                }
                rawLine.contains("=") -> {
                    val parts = rawLine.split("=", limit = 2)
                    key = parts[0].trim()
                    value = parts[1].trim()
                }
                else -> {
                    val parts = rawLine.split("\\s{2,}".toRegex(), limit = 2)
                    if (parts.size == 2) {
                        key = parts[0].trim()
                        value = parts[1].trim()
                    } else {
                        val singleSpaceParts = rawLine.split("\\s+".toRegex(), limit = 2)
                        key = singleSpaceParts[0].trim()
                        value = singleSpaceParts.getOrNull(1)?.trim() ?: ""
                    }
                }
            }

            if (key.isNotEmpty() && !key.startsWith("Metric") && !key.startsWith("---")) {
                items.add(MetricItem(key, value))
            }
        }

        Log.d(TAG, "parseMetricsList: parsed ${items.size} metric items")
        return items
    }

    private fun formatMetricsSetCommand(key: String, value: String): String {
        val escapedValue = "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\""
        return "7,metrics set $key $escapedValue"
    }

    private fun updateEmptyState() {
        if (adapter.getItemsCount() == 0) {
            val query = etSearchMetrics.text?.toString() ?: ""
            tvEmptyMetrics.text = if (query.isNotBlank()) {
                getString(R.string.metrics_no_entries, query)
            } else {
                getString(R.string.no_data)
            }
            tvEmptyMetrics.visibility = View.VISIBLE
        } else {
            tvEmptyMetrics.visibility = View.GONE
        }
    }

    private fun showEditMetricDialog(metric: MetricItem) {
        if (!metric.isEditable()) {
            return
        }
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_vehicle_config_param, null)
        val tilKey = dialogView.findViewById<TextInputLayout>(R.id.tilParamKey)
        val etKey = dialogView.findViewById<TextInputEditText>(R.id.etParamKey)
        val etValue = dialogView.findViewById<TextInputEditText>(R.id.etParamValue)

        tilKey.hint = getString(R.string.metrics_key_label)
        etKey.setText(metric.key)
        tilKey.isEnabled = false

        dialogView.findViewById<TextInputLayout>(R.id.tilParamValue)?.hint = getString(R.string.metrics_value_label)
        etValue.setText(metric.value)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.metrics_edit_title)
            .setMessage(getString(R.string.metrics_edit_hint, metric.key))
            .setView(dialogView)
            .setPositiveButton(R.string.Save, null)
            .setNegativeButton(R.string.Cancel, null)
            .create()

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val newValue = etValue.text?.toString() ?: ""
            sendCommand(
                "Saving...",
                formatMetricsSetCommand(metric.key, newValue),
                object : OnResultCommandListener {
                    override fun onResultCommand(
                        commandCode: Int,
                        returnCode: Int,
                        returnData: String,
                        result: Array<String>
                    ) {
                        view?.postDelayed({
                            if (isAdded && view != null) {
                                loadMetrics(showProgress = false)
                            }
                        }, 1000)
                    }
                }
            )
            dialog.dismiss()
        }
    }
}
