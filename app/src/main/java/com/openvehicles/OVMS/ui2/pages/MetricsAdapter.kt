package com.openvehicles.OVMS.ui2.pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openvehicles.OVMS.R
import java.util.Locale

data class MetricItem(
    val key: String,
    var value: String
) {
    fun isEditable(): Boolean = !key.lowercase(Locale.ROOT).startsWith("m.")
}

class MetricsAdapter(
    private val onItemClick: (MetricItem) -> Unit
) : RecyclerView.Adapter<MetricsAdapter.ViewHolder>() {

    private val allItems = mutableListOf<MetricItem>()
    private val displayedItems = mutableListOf<MetricItem>()
    private var currentFilter: String = ""

    fun setItems(items: List<MetricItem>) {
        allItems.clear()
        allItems.addAll(items.sortedBy { it.key.lowercase(Locale.ROOT) })
        applyFilter()
    }

    fun filter(query: String) {
        currentFilter = query.trim().lowercase(Locale.ROOT)
        applyFilter()
    }

    private fun applyFilter() {
        displayedItems.clear()
        if (currentFilter.isEmpty()) {
            displayedItems.addAll(allItems)
        } else {
            displayedItems.addAll(allItems.filter {
                it.key.lowercase(Locale.ROOT).contains(currentFilter) ||
                        it.value.lowercase(Locale.ROOT).contains(currentFilter)
            })
        }
        notifyDataSetChanged()
    }

    fun getItemsCount(): Int = displayedItems.size

    fun getAllCount(): Int = allItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_metric, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = displayedItems[position]
        holder.tvKey.text = item.key
        holder.tvValue.text = item.value

        val isEditable = item.isEditable()
        if (isEditable) {
            holder.btnEdit.visibility = View.VISIBLE
            holder.btnEdit.setOnClickListener { onItemClick(item) }
            holder.itemView.setOnClickListener { onItemClick(item) }
            holder.itemView.isClickable = true
            holder.itemView.isFocusable = true
        } else {
            holder.btnEdit.visibility = View.GONE
            holder.btnEdit.setOnClickListener(null)
            holder.itemView.setOnClickListener(null)
            holder.itemView.isClickable = false
            holder.itemView.isFocusable = false
        }
    }

    override fun getItemCount(): Int = displayedItems.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvKey: TextView = itemView.findViewById(R.id.tvMetricKey)
        val tvValue: TextView = itemView.findViewById(R.id.tvMetricValue)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEditMetric)
    }
}
