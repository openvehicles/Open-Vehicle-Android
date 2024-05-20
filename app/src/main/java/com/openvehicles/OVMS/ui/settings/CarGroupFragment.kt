package com.openvehicles.OVMS.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.utils.Ui
import com.openvehicles.OVMS.ui.utils.Ui.showPinDialog

class CarGroupFragment
    : BaseFragment(), OnResultCommandListener, OnItemClickListener, OnItemLongClickListener {

    private var adapter: CarGroupAdapter? = null
    private var listView: ListView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listView = ListView(container!!.context).apply {
            onItemClickListener = this@CarGroupFragment
            setOnItemLongClickListener(this@CarGroupFragment)
        }
        return listView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        compatActivity?.setTitle(R.string.cargroup_title)
    }

    override fun onServiceAvailable(service: ApiService) {
        // nop
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val context = parent.context
        showPinDialog(
            context, adapter!!.getTitleRow(context, position),
            id.toString(),
            R.string.Set,
            false,
            object : Ui.OnChangeListener<String?> {

                override fun onAction(data: String?) {
                    val dataInt = if (data.isNullOrEmpty()) 0 else data.toInt()
                    sendCommand(String.format("2,%d,%d", position, dataInt), this@CarGroupFragment)
                    adapter!!.setFeature(position, dataInt)
                }
            })
    }

    override fun onItemLongClick(
        adapterView: AdapterView<*>?,
        view: View,
        position: Int,
        id: Long
    ): Boolean {
        return false
    }

    override fun onResultCommand(result: Array<String>) {
        if (result.size <= 1) {
            return
        }
        if (adapter == null) {
            adapter = CarGroupAdapter()
            listView!!.setAdapter(adapter)
        }
        val command = result[0].toInt()
        val resultCode = result[1].toInt()
        val resText = if (result.size > 2) result[2] else ""
        if (command == 2) {
            cancelCommand()
            when (resultCode) {
                0 -> {}
                1 -> Toast.makeText(
                    activity, getString(R.string.err_failed, resText),
                    Toast.LENGTH_SHORT
                ).show()
                2 -> Toast.makeText(
                    activity, getString(R.string.err_unsupported_operation),
                    Toast.LENGTH_SHORT
                ).show()
                3 -> Toast.makeText(
                    activity, getString(R.string.err_unimplemented_operation),
                    Toast.LENGTH_SHORT
                ).show()
            }
            return
        }
        if (command != 1) {
            // Not for us
            return
        }
        when (resultCode) {
            0 -> if (result.size > 4) {
                val fn = result[2].toInt()
                //				int fm = Integer.parseInt(result[3]);
                val fv = result[4].toInt()
                if (fn < CarGroupAdapter.FEATURES_MAX) {
                    adapter!!.setFeature(fn, fv)
                }

//				if (fn == (fm - 1)) {
//					cancelCommand();
//					listView.setAdapter(mAdapter);
//				}
            }
            1 -> {
                Toast.makeText(
                    activity, getString(R.string.err_failed, resText),
                    Toast.LENGTH_SHORT
                ).show()
                cancelCommand()
            }
            2 -> {
                Toast.makeText(
                    activity, getString(R.string.err_unsupported_operation),
                    Toast.LENGTH_SHORT
                ).show()
                cancelCommand()
            }
            3 -> {
                Toast.makeText(
                    activity, getString(R.string.err_unimplemented_operation),
                    Toast.LENGTH_SHORT
                ).show()
                cancelCommand()
            }
        }
    }

    /*
     * Inner types
     */

    private class CarGroupAdapter : BaseAdapter() {

        private var inflater: LayoutInflater? = null

        override fun getCount(): Int {
            return FEATURES_MAX
        }

        override fun getItem(position: Int): Any {
            return feature[position]
        }

        override fun getItemId(position: Int): Long {
            return feature[position].toLong()
        }

        fun setFeature(key: Int, value: Int) {
            if (key > FEATURES_MAX - 1) {
                return
            }
            feature[key] = value
            notifyDataSetChanged()
        }

        fun getTitleRow(context: Context?, position: Int): String {
            return String.format("#%d:", position)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            val context = parent.context
            if (view == null) {
                if (inflater == null) {
                    inflater = LayoutInflater.from(context)
                }
                view = inflater!!.inflate(R.layout.item_keyvalue, null)
            }
            var tv = view!!.findViewById<View>(android.R.id.text1) as TextView
            tv.text = getTitleRow(context, position)
            tv = view.findViewById<View>(android.R.id.text2) as TextView
            tv.text = feature[position].toString()
            return view
        }

        companion object {
            const val FEATURES_MAX = 16
            private val feature = IntArray(FEATURES_MAX)
        }
    }
}
