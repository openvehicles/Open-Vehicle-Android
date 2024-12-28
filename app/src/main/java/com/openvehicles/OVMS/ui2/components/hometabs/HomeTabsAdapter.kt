package com.openvehicles.OVMS.ui2.components.hometabs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openvehicles.OVMS.R


class HomeTabsAdapter internal constructor(
    context: Context?,
    var mData: List<HomeTab> = emptyList()
) : RecyclerView.Adapter<HomeTabsAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.home_tab_item, parent, false)
        return ViewHolder(view, mClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action = mData[position]
        holder.tabName.text = action.tabName
        holder.tabIcon.setImageResource(action.tabIcon)
        holder.tabSubTitle.visibility = if (action.tabDesc == null || action.tabDesc!!.isEmpty()) View.GONE else View.VISIBLE
        holder.tabSubTitle.text = action.tabDesc
        holder.clickListener = mClickListener
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder internal constructor(itemView: View, var clickListener: ItemClickListener?) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val tabName: TextView = itemView.findViewById(R.id.tabName)
        val tabSubTitle: TextView = itemView.findViewById(R.id.tabExtraInfo)
        val tabIcon: ImageView = itemView.findViewById(R.id.tabIcon)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            clickListener?.onItemClick(view, adapterPosition)
        }
    }

    fun getItem(id: Int): HomeTab {
        return mData[id]
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}