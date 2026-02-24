package com.openvehicles.OVMS.ui2.components.quickactions.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui2.components.quickactions.QuickAction
import java.util.Collections


class QuickActionsAdapter internal constructor(
    context: Context?,
    val removeAction: ((action: QuickAction) -> Unit)? = null,
    val mData: MutableList<QuickAction> = arrayListOf()
) : RecyclerView.Adapter<QuickActionsAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater
    private var carData: CarData? = null
    var editMode = false

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
        action.initAction(holder.itemView, {
            if (editMode) {
                if (itemCount > 1) {
                    if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION) {
                        mData.removeAt(holder.bindingAdapterPosition)
                        notifyItemRemoved(holder.bindingAdapterPosition)
                    }
                    removeAction?.invoke(action)
                }
            }
            !editMode
        }, editMode)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (!editMode)
            return
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(mData, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(mData, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
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