package com.openvehicles.OVMS.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragmentActivity.Companion.show
import com.openvehicles.OVMS.ui.settings.CarEditorFragment
import com.openvehicles.OVMS.ui.settings.CarInfoFragment
import com.openvehicles.OVMS.ui.settings.ControlFragment
import com.openvehicles.OVMS.ui.settings.GlobalOptionsFragment
import com.openvehicles.OVMS.ui.utils.Ui.getDrawableIdentifier
import com.openvehicles.OVMS.utils.CarsStorage.getStoredCars

class SettingsFragment : BaseFragment(), OnItemClickListener {

    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        listView = ListView(container!!.context)
        listView!!.fitsSystemWindows = true
        return listView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE)
        listView.onItemClickListener = this
        listView.setAdapter(SettingsAdapter(activity, getStoredCars()))
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.settings_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mi_add) {
            edit(-1)
            return true
        } else if (item.itemId == R.id.mi_globaloptions) {
            showGlobalOptions()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun update(carData: CarData?) {
        val count = listView.count
        for (i in 0 until count) {
            if (carData == listView.getItemAtPosition(i)) {
                listView.setItemChecked(i, true)
                break
            }
        }
        listView.invalidateViews()
    }

    override fun onServiceAvailable(service: ApiService) {
        if (service.isLoggedIn()) {
            update(service.getCarData())
        }
    }

    override fun onItemClick(
        parent: AdapterView<*>,
        view: View,
        position: Int,
        id: Long
    ) {
        when (view.id) {
            R.id.btn_edit -> {
                edit(position)
                return
            }
            R.id.btn_control -> {
                control(position)
                return
            }
            R.id.btn_info -> {
                info(position)
                return
            }
            else -> {
                val carData = parent.adapter.getItem(position) as CarData
                changeCar(carData)
            }
        }
    }

    private fun edit(position: Int) {
        val args = Bundle()
        args.putInt("position", position)
        show(
            requireActivity(),
            CarEditorFragment::class.java,
            args,
            Configuration.ORIENTATION_UNDEFINED
        )
    }

    private fun control(pPosition: Int) {
        val args = Bundle()
        args.putInt("position", pPosition)
        show(
            requireActivity(),
            ControlFragment::class.java,
            args,
            Configuration.ORIENTATION_UNDEFINED
        )
    }

    private fun info(pPosition: Int) {
        val args = Bundle()
        args.putInt("position", pPosition)
        show(
            requireActivity(),
            CarInfoFragment::class.java,
            args,
            Configuration.ORIENTATION_UNDEFINED
        )
    }

    private fun showGlobalOptions() {
        show(
            requireActivity(),
            GlobalOptionsFragment::class.java,
            null,
            Configuration.ORIENTATION_UNDEFINED
        )
    }

    /*
     * Inner types
     */

    private class SettingsAdapter(
        context: Context?,
        private val items: ArrayList<CarData>?
    ) : BaseAdapter(), View.OnClickListener {

        private val inflater: LayoutInflater
        private var listView: ListView? = null

        init {
            inflater = LayoutInflater.from(context)
        }

        override fun getCount(): Int {
            return items!!.size
        }

        override fun getItem(position: Int): Any? {
            return if (items == null || position >= items.size) null else items[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (view == null) {
                view = inflater.inflate(R.layout.item_car, null)
            }
            val btnEdit = view!!.findViewById<View>(R.id.btn_edit) as ImageButton
            val btnControl = view.findViewById<View>(R.id.btn_control) as ImageButton
            val btnInfo = view.findViewById<View>(R.id.btn_info) as ImageButton
            btnEdit.setOnClickListener(this)
            btnControl.setOnClickListener(this)
            btnInfo.setOnClickListener(this)
            btnEdit.tag = position
            btnControl.tag = position
            btnInfo.tag = position
            val it = items!![position]
            var iv = view.findViewById<View>(R.id.img_car) as ImageView
            iv.setImageResource(
                getDrawableIdentifier(
                    parent.context,
                    it.sel_vehicle_image
                )
            )
            (view.findViewById<View>(R.id.txt_title) as TextView).text =
                it.sel_vehicle_label
            if (listView == null && parent is ListView) {
                listView = parent
            }
            if (listView == null) {
                return view
            }
            iv = view.findViewById<View>(R.id.img_signal_rssi) as ImageView
            if (listView!!.isItemChecked(position)) {
                view.setBackgroundColor(-0x7fcc4a1b)
                btnInfo.setVisibility(View.VISIBLE)
                btnControl.setVisibility(View.VISIBLE)
                iv.setVisibility(View.VISIBLE)
                iv.setImageResource(
                    getDrawableIdentifier(
                        parent.context, "signal_strength_"
                                + it.car_gsm_bars
                    )
                )
            } else {
                view.setBackgroundColor(0)
                iv.setVisibility(View.INVISIBLE)
                btnInfo.setVisibility(View.INVISIBLE)
                btnControl.setVisibility(View.INVISIBLE)
            }
            return view
        }

        override fun onClick(view: View) {
            if (listView?.onItemClickListener == null) {
                return
            }
            listView!!.onItemClickListener!!.onItemClick(
                listView,
                view,
                (view.tag as Int),
                view.id.toLong()
            )
        }
    }
}
