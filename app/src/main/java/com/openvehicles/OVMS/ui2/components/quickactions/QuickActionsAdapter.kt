package com.openvehicles.OVMS.ui2.components.quickactions

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.CarData

class QuickActionsAdapter internal constructor(
    context: Context?,
    var mData: List<QuickAction> = emptyList()
) : RecyclerView.Adapter<QuickActionsAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater
    private var carData: CarData? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.quick_action_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action = mData[position]
        action.setCarData(carData)
        action.initAction(holder.itemView)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setCarData(carData: CarData?) {
        this.carData = carData
    }

    inner class ViewHolder internal constructor(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    )

    fun getItem(id: Int): QuickAction {
        return mData[id]
    }
}