package com.openvehicles.OVMS.ui2.components.energymetrics

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openvehicles.OVMS.R
import androidx.core.graphics.toColorInt


class EnergyMetricsAdapter internal constructor(
    context: Context?,
    var mData: List<EnergyMetric> = emptyList()
) : RecyclerView.Adapter<EnergyMetricsAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.energy_metric, parent, false)
        return ViewHolder(view, mClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val metric = mData[position]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (position.and(1) == 0)
                holder.itemView.setBackgroundResource(R.color.material_dynamic_secondary20)
            else
                holder.itemView.setBackgroundResource(R.color.material_dynamic_secondary10)
        } else {
            if (position.and(1) == 0)
                holder.itemView.setBackgroundColor("#2A3042".toColorInt())
            else
                holder.itemView.setBackgroundColor("#151B2C".toColorInt())
        }
        holder.metricName.text = metric.metricName
        holder.metricValue.text = metric.metricValue
        holder.clickListener = mClickListener
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder internal constructor(itemView: View, var clickListener: ItemClickListener?) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val metricName: TextView = itemView.findViewById(R.id.metricName)
        val metricValue: TextView = itemView.findViewById(R.id.metricValue)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            clickListener?.onItemClick(view, adapterPosition)
        }
    }

    fun getItem(id: Int): EnergyMetric {
        return mData[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}