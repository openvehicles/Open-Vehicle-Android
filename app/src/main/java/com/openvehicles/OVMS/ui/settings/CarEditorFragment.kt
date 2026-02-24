package com.openvehicles.OVMS.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Gallery
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.BaseFragmentActivity
import com.openvehicles.OVMS.ui.utils.Ui.getDrawableIdentifier
import com.openvehicles.OVMS.ui.utils.Ui.getValidValue
import com.openvehicles.OVMS.ui.utils.Ui.getValue
import com.openvehicles.OVMS.ui.utils.Ui.setValue
import com.openvehicles.OVMS.ui.validators.PasswdValidator
import com.openvehicles.OVMS.ui.validators.StringValidator
import com.openvehicles.OVMS.ui.validators.ValidationException
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.CarsStorage.getSelectedCarData
import com.openvehicles.OVMS.utils.CarsStorage.getStoredCars
import com.openvehicles.OVMS.utils.CarsStorage.saveStoredCars

class CarEditorFragment : BaseFragment() {

    private var carData: CarData? = null
    private var isSelectedCar = false
    private var editPosition = 0
    private var galleryCar: Gallery? = null
    private var selectServer: Spinner? = null
    private var selectServerPosition = 0
    private lateinit var servers: Array<String>
    private lateinit var gcmSenders: Array<String>
    private var server: EditText? = null
    private var gcmSender: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_careditor, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        compatActivity?.supportActionBar!!.setIcon(R.drawable.ic_action_edit)
        editPosition = requireArguments().getInt("position", -1)
        if (editPosition >= 0) {
            try {
                carData = getStoredCars()[editPosition]
                val selectedCarData = getSelectedCarData()
                isSelectedCar = selectedCarData != null
                        && carData != null
                        && selectedCarData.sel_vehicleid == carData!!.sel_vehicleid
            } catch (e: Exception) {
                carData = null
                editPosition = -1
                isSelectedCar = false
            }
        }
        selectServer = requireView().findViewById<View>(R.id.select_server) as Spinner
        selectServerPosition = -1
        servers = resources.getStringArray(R.array.select_server_options)
        gcmSenders = resources.getStringArray(R.array.select_server_gcm_senders)
        server = requireView().findViewById<View>(R.id.txt_server_address) as EditText
        gcmSender = requireView().findViewById<View>(R.id.txt_gcm_senderid) as EditText
        selectServer!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                setSelectedServer(position, true)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // nop
            }
        }
        galleryCar = requireView().findViewById<View>(R.id.ga_car) as Gallery
        galleryCar!!.setAdapter(CarImgAdapter())
        setHasOptionsMenu(true)
        load()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.control_save_delete, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        Log.d("CarEditorFragment", "onPrepareOptionsMenu edit car: " + (getStoredCars().size > 1))
        menu.findItem(R.id.mi_delete).setVisible(carData != null && getStoredCars().size > 1)
        menu.findItem(R.id.mi_control).setVisible(isSelectedCar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_save -> {
                save()
                true
            }
            R.id.mi_delete -> {
                delete()
                true
            }
            R.id.mi_control -> {
                val args = Bundle()
                args.putInt("position", editPosition)
                val activity = activity as BaseFragmentActivity?
                activity!!.setNextFragment(ControlFragment::class.java, args)
                true
            }
            else -> false
        }
    }

    private fun delete() {
        AlertDialog.Builder(requireActivity())
            .setMessage(R.string.msg_delete_this_car)
            .setNegativeButton(R.string.No, null)
            .setPositiveButton(R.string.Yes) { dialog, which ->
                val carList = getStoredCars()
                // remove car:
                carList.removeAt(editPosition)
                saveStoredCars()
                // select closest remaining car:
                val selCar: CarData = if (editPosition < carList.size) {
                    carList[editPosition]
                } else {
                    carList[carList.size - 1]
                }
                changeCar(selCar)
                // back to previous fragment:
                requireActivity().finish()
            }
            .show()
    }
    private fun save() {
        val rootView = view
        if (carData == null) {
            carData = CarData()
        }
        try {
            carData!!.sel_vehicleid = getValidValue(
                rootView!!, R.id.txt_vehicle_id,
                object : StringValidator() {
                    override fun valid(editText: EditText?, value: Any?): Boolean {
                        if (!super.valid(editText, value)) {
                            return false
                        }
                        errorMessage = editText!!.context.getString(
                            R.string.msg_invalid_id_already_registered,
                            value
                        )
                        val mAllCars: List<CarData> = getStoredCars()
                        val count = mAllCars.size
                        val valueUpperCase = value.toString().uppercase()
                        for (i in 0 until count) {
                            if (mAllCars[i].sel_vehicleid.equals(valueUpperCase, ignoreCase = true) && i != editPosition) {
                                return false
                            }
                        }
                        return true
                    }
                }).uppercase()
            carData!!.sel_vehicle_label =
                getValidValue(rootView, R.id.txt_vehicle_label, StringValidator())
            carData!!.sel_server_password =
                getValidValue(rootView, R.id.txt_server_passwd, PasswdValidator(4, 255))
            carData!!.sel_module_password =
                getValidValue(rootView, R.id.txt_module_passwd, PasswdValidator(4, 255))
            carData!!.sel_server =
                getValidValue(rootView, R.id.txt_server_address, StringValidator())
            carData!!.sel_gcm_senderid = getValue(rootView, R.id.txt_gcm_senderid)
            carData!!.sel_tls = (rootView.findViewById<View>(R.id.chk_tls_enabled) as CheckBox).isChecked
            carData!!.sel_tls_trust_all = (rootView.findViewById<View>(R.id.chk_tls_trust_all) as CheckBox).isChecked
            carData!!.sel_vehicle_image = availableColors[galleryCar!!.selectedItemPosition]
        } catch (e: ValidationException) {
            Log.e("Validation", e.message, e)
            return
        }
        if (editPosition < 0) {
            getStoredCars().add(carData!!)
        }
        saveStoredCars()
        requireActivity().finish()
    }

    private fun load() {
        val rootView = view
        if (carData == null) {
            // edit new car:
            setSelectedServer(0, false)
        } else {
            // edit existing car:
            compatActivity?.setTitle(carData!!.sel_vehicleid)
            setValue(rootView!!, R.id.txt_vehicle_id, carData!!.sel_vehicleid)
            setValue(rootView, R.id.txt_vehicle_label, carData!!.sel_vehicle_label)
            setValue(rootView, R.id.txt_server_passwd, carData!!.sel_server_password)
            setValue(rootView, R.id.txt_module_passwd, carData!!.sel_module_password)

            // set server:
            var position = servers.size - 1
            for (i in servers.indices) {
                if (servers[i] == carData!!.sel_server && gcmSenders[i] == carData!!.sel_gcm_senderid) {
                    position = i
                    break
                }
            }
            Log.d(TAG, "load: server=" + carData!!.sel_server + " → position=" + position)
            setSelectedServer(position, false)

            // set TLS options:
            val chkTlsEnabled = requireView().findViewById<View>(R.id.chk_tls_enabled) as CheckBox
            val chkTlsTrustAll = requireView().findViewById<View>(R.id.chk_tls_trust_all) as CheckBox
            chkTlsEnabled.isChecked = carData!!.sel_tls
            chkTlsTrustAll.isChecked = carData!!.sel_tls_trust_all
            chkTlsTrustAll.isEnabled = carData!!.sel_tls
            chkTlsEnabled.setOnClickListener {
                chkTlsTrustAll.isEnabled = (it as CheckBox).isChecked
            }

            // set car image:
            var index = -1
            for (imgRes in availableColors) {
                index++
                if (imgRes == carData!!.sel_vehicle_image) {
                    break
                }
            }
            if (index >= 0) {
                galleryCar!!.setSelection(index)
            }

            // save selected vehicle label:
            val appPrefs = AppPrefs(requireActivity(), "ovms")
            Log.d(TAG, "load: sel_vehicle_label=" + carData!!.sel_vehicle_label)
            appPrefs.saveData("sel_vehicle_label", carData!!.sel_vehicle_label)
        }
    }

    private fun setSelectedServer(position: Int, userAction: Boolean) {
        if (position != selectServerPosition) {
            selectServerPosition = position
            Log.d(
                TAG, "setSelectedServer: new position=" + position
                        + " → server=" + servers[position]
            )
            if (position < servers.size - 1) {
                server!!.setText(servers[position])
                gcmSender!!.setText(gcmSenders[position])
                server!!.visibility = View.GONE
                gcmSender!!.visibility = View.GONE
            } else {
                if (userAction) {
                    server!!.setText("")
                    gcmSender!!.setText("")
                    server!!.requestFocus()
                } else {
                    server!!.setText(if (carData != null) carData!!.sel_server else "")
                    gcmSender!!.setText(if (carData != null) carData!!.sel_gcm_senderid else "")
                }
                server!!.visibility = View.VISIBLE
                gcmSender!!.visibility = View.VISIBLE
            }
            if (!userAction) {
                selectServer!!.setSelection(position)
            }
        }
    }

    /*
     * Inner types
     */

    private companion object {

        private const val TAG = "CarEditorFragment"

        private val availableColors = arrayOf(
            "car_roadster_arcticwhite",
            "car_roadster_brilliantyellow",
            "car_roadster_electricblue",
            "car_roadster_fushionred",
            "car_roadster_glacierblue",
            "car_roadster_jetblack",
            "car_roadster_lightninggreen",
            "car_roadster_obsidianblack",
            "car_roadster_racinggreen",
            "car_roadster_radiantred",
            "car_roadster_sterlingsilver",
            "car_roadster_thundergray",
            "car_roadster_twilightblue",
            "car_roadster_veryorange",
            "car_i3_grey",
            "car_i3_white",
            "car_i3_darkblue",
            "car_i3_babyblue",
            "car_twizy_diamondblackwithivygreen",
            "car_twizy_snowwhiteandflameorange",
            "car_twizy_snowwhiteandurbanblue",
            "car_twizy_snowwhitewithblack",
            "car_kiasoul_carribianblueclearwhite",
            "car_kiasoul_cherryblackinfernored",
            "car_kiasoul_clearwhite",
            "car_kiasoul_pearlwhiteelectronicblue",
            "car_kiasoul_titaniumsilver",
            "car_kianiro_black",
            "car_kianiro_blue",
            "car_kianiro_grey",
            "car_kianiro_silver",
            "car_kianiro_snowwhite",
            "car_kiaev6_white",
            "car_kona_grey",
            "car_kona_white",
            "car_kona_red",
            "car_kona_blue",
            "car_kona_yellow",
            "car_ioniq_polarwhite",
            "car_ioniq5_cybergray",
            "car_leaf_coulisred",
            "car_leaf_deepblue",
            "car_leaf_planetblue",
            "car_leaf_forgedbronze",
            "car_leaf_gunmetallic",
            "car_leaf_pearlwhite",
            "car_leaf_superblack",
            "car_leaf2_gunmetallic",
            "car_leaf2_jadefrostmetallic",
            "car_leaf2_pearlwhite",
            "car_leaf2_superblack",
            "car_leaf2_vividblue",
            "car_env200_white",
            "car_smart_ed_white",
            "car_smart_eq_red",
            "car_smart_eq_black",
            "car_smart_eq_white",
            "car_smart_eq_fl_black",
            "car_smart_eq_fl_white",
            "car_smart_eq_fl_red",
            "car_smart_eq_cabrio_black",
            "car_smart_eq_cabrio_crystalwhite",
            "car_smart_eq_cabrio_grey",
            "car_smart_eq_cabrio_lavaorange",
            "car_smart_44_black",
            "car_smart_44_white_silver",
            "car_smart_44_fl_black",
            "car_zoe_black",
            "car_vwup_black",
            "car_vwup_blue",
            "car_vwup_red",
            "car_vwup_silver",
            "car_vwup_white",
            "car_vwup_yellow",
            "car_zoe_brown",
            "car_zoe_grey",
            "car_zoe_hellblau",
            "car_zoe_lila",
            "car_zoe_red",
            "car_zoe_white",
            "car_zoe_ytriumgrau",
            "car_mgzs_white",
            "car_mgzs_blue",
            "car_mgzs_lightblue",
            "car_mgzs_red",
            "car_mgzs_black",
            "car_edeliver3_white",
            "car_ampera_black",
            "car_ampera_crystalred",
            "car_ampera_cybergray",
            "car_ampera_lithiumwhite",
            "car_ampera_powerblue",
            "car_ampera_silvertopas",
            "car_ampera_sovereignsilver",
            "car_ampera_summitwhite",
            "car_boltev_summitwhite",
            "car_holdenvolt_black",
            "car_holdenvolt_crystalclaret",
            "car_holdenvolt_silvernitrate",
            "car_holdenvolt_urbanfresh",
            "car_holdenvolt_whitediamond",
            "car_imiev_black",
            "car_imiev_blue",
            "car_imiev_cherrybrown",
            "car_imiev_coolsilver",
            "car_imiev_white",
            "car_imiev_whitered",
            "car_thinkcity_brightred",
            "car_thinkcity_citrusyellow",
            "car_thinkcity_classicblack",
            "car_thinkcity_skyblue",
            "car_kangoo_white",
            "car_kangoo_black",
            "car_kangoo_grey",
            "car_kangoo_red",
            "car_kangoo_blue",
            "car_kangoo_brown",
            "car_fiat500e_black",
            "car_nrjkss9_orange",
            "car_nrjkribelle_red",
            "car_nrjkexperia_black",
            "car_nrjkexperia_graybags",
            "car_nrjkego_black",
            "car_niu_mqi_gt_or",
            "car_niu_mqi_gt_silver",
            "car_niu_mqi_gt_white",
            "car_niu_mqi_gt_black"
        )
    }

    private class CarImgAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return availableColors.size
        }

        override fun getItem(position: Int): Any {
            return availableColors[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val iv = convertView as? ImageView ?: ImageView(parent.context)
            iv.setScaleType(ImageView.ScaleType.FIT_START)
            iv.setAdjustViewBounds(true)
            iv.setImageResource(getDrawableIdentifier(parent.context, availableColors[position]))
            return iv
        }
    }
}