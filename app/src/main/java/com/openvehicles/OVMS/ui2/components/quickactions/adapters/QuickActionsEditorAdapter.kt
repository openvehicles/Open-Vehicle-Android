package com.openvehicles.OVMS.ui2.components.quickactions.adapters

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.ui2.components.quickactions.QuickAction


class QuickActionsEditorAdapter internal constructor(
    context: Context?,
    val actionClickListener: (actionId: String) -> Unit,
    val mData: MutableList<QuickAction> = arrayListOf()
) : RecyclerView.Adapter<QuickActionsEditorAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater

    init {
        mInflater = LayoutInflater.from(context)
    }

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.quick_action_editor_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action = mData[position]
        val button = holder.itemView.findViewById(R.id.action_button) as FloatingActionButton
        val label = holder.itemView.findViewById<TextView>(R.id.quick_action_label) as TextView
        button.setImageDrawable(action.getLiveCarIcon(false, holder.itemView.context))
        button.setOnClickListener {
            actionClickListener(action.id)
        }
        label.text = action.label
        label.isSelected = true
    }

    override fun getItemCount(): Int {
        return mData.size
    }
    inner class ViewHolder internal constructor(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    )

    fun getItem(id: Int): QuickAction {
        return mData[id]
    }
}