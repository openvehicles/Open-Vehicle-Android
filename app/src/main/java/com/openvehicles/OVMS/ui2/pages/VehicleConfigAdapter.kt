package com.openvehicles.OVMS.ui2.pages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openvehicles.OVMS.R
import java.util.Locale

data class VehicleConfigParam(
    val key: String,
    var value: String
)

class VehicleConfigAdapter(
    private val onItemClick: (VehicleConfigParam) -> Unit,
    private val onDeleteClick: (VehicleConfigParam) -> Unit
) : RecyclerView.Adapter<VehicleConfigAdapter.ViewHolder>() {

    private val allItems = mutableListOf<VehicleConfigParam>()
    private val displayedItems = mutableListOf<VehicleConfigParam>()
    private var currentFilter: String = ""

    fun setItems(items: List<VehicleConfigParam>) {
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
            .inflate(R.layout.item_vehicle_config, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = displayedItems[position]
        holder.tvKey.text = item.key
        holder.tvValue.text = item.value
        holder.itemView.setOnClickListener { onItemClick(item) }
        holder.btnDelete.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = displayedItems.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvKey: TextView = itemView.findViewById(R.id.tvConfigKey)
        val tvValue: TextView = itemView.findViewById(R.id.tvConfigValue)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteConfig)
    }
}
