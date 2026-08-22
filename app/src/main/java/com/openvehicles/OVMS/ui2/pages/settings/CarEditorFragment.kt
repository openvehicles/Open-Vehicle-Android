package com.openvehicles.OVMS.ui2.pages.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Gallery
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.MaterialAutoCompleteTextView
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
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.button.MaterialButton
import java.io.File
import java.io.FileOutputStream
import com.openvehicles.OVMS.ui.utils.Ui.getCarDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.graphics.Paint
import android.graphics.Color
import android.view.Gravity

class CarEditorFragment : BaseFragment() {

    private var carData: CarData? = null
    private var isSelectedCar = false
    private var editPosition = 0
    
    private var rvVehicleType: RecyclerView? = null
    private var selectCarColor: MaterialAutoCompleteTextView? = null
    
    private var selectServer: MaterialAutoCompleteTextView? = null
    private var selectServerPosition = 0
    private lateinit var servers: Array<String>
    private lateinit var gcmSenders: Array<String>
    private var server: EditText? = null
    private var gcmSender: EditText? = null
    
    private var customImagePath: String? = null
    private var btnCustomImage: MaterialButton? = null
    private var btnClearCustomImage: MaterialButton? = null
    private var imgCustomPreview: ImageView? = null
    
    private var customImagePathOl: String? = null
    private var btnCustomImageOl: MaterialButton? = null
    private var btnClearCustomImageOl: MaterialButton? = null
    private var imgCustomPreviewOl: ImageView? = null
    private var galleryCarOl: Gallery? = null

    private var customImagePathMap: String? = null
    private var btnCustomImageMap: MaterialButton? = null
    private var btnClearCustomImageMap: MaterialButton? = null
    private var imgCustomPreviewMap: ImageView? = null
    private var galleryCarMap: Gallery? = null

    private data class VehicleColor(val name: String, val resName: String)
    private data class VehicleType(val id: String, val name: String, val colors: List<String>)

    private val vehicleTypes = listOf(
        VehicleType("roadster", "Tesla Roadster", listOf("arcticwhite", "brilliantyellow", "electricblue", "fushionred", "glacierblue", "jetblack", "lightninggreen", "obsidianblack", "racinggreen", "radiantred", "sterlingsilver", "thundergray", "twilightblue", "veryorange")),
        VehicleType("i3", "BMW i3", listOf("grey", "white", "darkblue", "babyblue")),
        VehicleType("twizy", "Renault Twizy", listOf("diamondblackwithivygreen", "snowwhiteandflameorange", "snowwhiteandurbanblue", "snowwhitewithblack")),
        VehicleType("kiasoul", "Kia Soul", listOf("carribianblueclearwhite", "cherryblackinfernored", "clearwhite", "pearlwhiteelectronicblue", "titaniumsilver")),
        VehicleType("kianiro", "Kia Niro", listOf("black", "blue", "grey", "silver", "snowwhite")),
        VehicleType("kiaev6", "Kia EV6", listOf("white")),
        VehicleType("kona", "Hyundai Kona", listOf("grey", "white", "red", "blue", "yellow")),
        VehicleType("ioniq", "Hyundai Ioniq", listOf("polarwhite")),
        VehicleType("ioniq5", "Hyundai Ioniq 5", listOf("cybergray")),
        VehicleType("leaf", "Nissan Leaf", listOf("coulisred", "deepblue", "planetblue", "forgedbronze", "gunmetallic", "pearlwhite", "superblack")),
        VehicleType("leaf2", "Nissan Leaf (2018)", listOf("gunmetallic", "jadefrostmetallic", "pearlwhite", "superblack", "vividblue")),
        VehicleType("env200", "Nissan e-NV200", listOf("white")),
        VehicleType("smart_ed", "Smart ED (ForTwo)", listOf("white")),
        VehicleType("smart_eq", "Smart EQ (ForTwo)", listOf("red", "black", "white", "fl_black", "fl_white", "fl_red", "cabrio_black", "cabrio_crystalwhite", "cabrio_grey", "cabrio_lavaorange")),
        VehicleType("smart_44", "Smart ForFour", listOf("black", "white_silver", "fl_black")),
        VehicleType("zoe", "Renault Zoe", listOf("black", "brown", "grey", "hellblau", "lila", "red", "white", "ytriumgrau")),
        VehicleType("mgzs", "MG ZS EV", listOf("white", "blue", "lightblue", "red", "black")),
        VehicleType("edeliver3", "Maxus eDeliver 3", listOf("white")),
        VehicleType("ampera", "Opel Ampera", listOf("black", "crystalred", "cybergray", "lithiumwhite", "powerblue", "silvertopas", "sovereignsilver", "summitwhite")),
        VehicleType("boltev", "Chevy Bolt EV", listOf("summitwhite")),
        VehicleType("holdenvolt", "Holden Volt", listOf("black", "crystalclaret", "silvernitrate", "urbanfresh", "whitediamond")),
        VehicleType("imiev", "Mitsubishi i-MiEV", listOf("black", "blue", "cherrybrown", "coolsilver", "white", "whitered")),
        VehicleType("thinkcity", "Think City", listOf("brightred", "citrusyellow", "classicblack", "skyblue")),
        VehicleType("kangoo", "Renault Kangoo", listOf("white", "black", "grey", "red", "blue", "brown")),
        VehicleType("fiat500e", "Fiat 500e", listOf("black")),
        VehicleType("nrjk", "Energica", listOf("ego_black", "experia_black", "experia_graybags", "ribelle_red", "ss9_orange")),
        VehicleType("niu_mqi_gt", "Niu MQi GT", listOf("or", "silver", "white", "black"))
    )

    private inner class VehicleTypeAdapter(private val onClick: (Int) -> Unit) : RecyclerView.Adapter<VehicleTypeAdapter.ViewHolder>() {
        var selectedIndex = 0

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val img: ImageView = view.findViewById(android.R.id.icon)
            init {
                view.setOnClickListener {
                    val pos = bindingAdapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        val old = selectedIndex
                        selectedIndex = pos
                        notifyItemChanged(old)
                        notifyItemChanged(selectedIndex)
                        onClick(selectedIndex)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = ImageView(parent.context).apply {
                id = android.R.id.icon
                val d = resources.displayMetrics.density
                layoutParams = ViewGroup.LayoutParams((120 * d).toInt(), (80 * d).toInt())
                scaleType = ImageView.ScaleType.FIT_CENTER
                setBackgroundResource(R.drawable.gallery_item_selector)
                setPadding((8 * d).toInt(), (8 * d).toInt(), (8 * d).toInt(), (8 * d).toInt())
            }
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val type = vehicleTypes[position]
            val currentColor = type.colors[0]
            val resName = if (type.id == "nrjk") "car_nrjk$currentColor" else "car_${type.id}_$currentColor"
            holder.img.setImageResource(getDrawableIdentifier(holder.itemView.context, resName))
            holder.itemView.isSelected = position == selectedIndex
        }

        override fun getItemCount() = vehicleTypes.size
    }

    private var selectedVehicleTypeIndex = 0
    private var selectedColorIndex = 0
    private var isAutoSyncEnabled = true
    
    private val vehicleTypeAdapter = VehicleTypeAdapter { index -> 
        selectedVehicleTypeIndex = index
        updateColorDropdown()
        if (isAutoSyncEnabled) autoSyncImages()
        filterSecondaryGalleries()
    }

    private fun filterSecondaryGalleries() {
        val currentType = vehicleTypes.getOrNull(selectedVehicleTypeIndex) ?: return
        val filterId = if (currentType.id.startsWith("smart_44")) "vwup" else if (currentType.id.startsWith("smart_e")) "smart" else if (currentType.id.startsWith("niu")) "nrjk" else  currentType.id
        
        // Filter OL images
        val filteredOl = availableOlImages.filter { it.contains(filterId) }.toTypedArray()
        val olList = if (filteredOl.isEmpty()) availableOlImages else filteredOl
        galleryCarOl?.adapter = CarImgAdapter(olList, true)
        
        // Filter Map icons
        val filteredMap = availableMapIcons.filter { it.contains(filterId) }.toTypedArray()
        val mapList = if (filteredMap.isEmpty()) availableMapIcons else filteredMap
        galleryCarMap?.adapter = CarImgAdapter(mapList, false)
    }

    private fun autoSyncImages() {
        val currentType = vehicleTypes.getOrNull(selectedVehicleTypeIndex) ?: return
        val currentColor = currentType.colors.getOrNull(selectedColorIndex) ?: currentType.colors[0]
        
        // Auto-select OL image
        val targetOl = "car_${currentType.id}_${currentColor}"
        val olAdapter = galleryCarOl?.adapter as? CarImgAdapter ?: return
        var olIndex = -1
        val baseId = if (currentType.id.startsWith("smart_44")) "vwup" else if (currentType.id.startsWith("smart_e")) "smart" else if (currentType.id.startsWith("niu")) "nrjk" else  currentType.id
        
        for (i in 0 until olAdapter.count) {
            val img = olAdapter.getItem(i) as String
            if (img == targetOl || img == "car_${currentType.id}" || img == currentType.id || img == "car_$baseId" || img == baseId) {
                olIndex = i
                break
            }
            // Special case for motorcycles/Experia
            if (img.contains("experia") && currentColor.contains("experia")) {
                olIndex = i
                break
            }
        }
        if (olIndex >= 0) galleryCarOl?.setSelection(olIndex)

        // Auto-select Map image
        val targetMap = "map_car_${currentType.id}_${currentColor}"
        val mapAdapter = galleryCarMap?.adapter as? CarImgAdapter ?: return
        var mapIndex = -1
        val altTargetMap = "map_car_${currentType.id}"
        val baseMapTarget = "map_car_$baseId"
        
        for (i in 0 until mapAdapter.count) {
            val img = mapAdapter.getItem(i) as String
            if (img == targetMap || img == altTargetMap || img == baseMapTarget) {
                mapIndex = i
                break
            }
        }
        if (mapIndex >= 0) galleryCarMap?.setSelection(mapIndex)
    }

    private fun updateColorDropdown() {
        val colors = vehicleTypes.getOrNull(selectedVehicleTypeIndex)?.colors ?: return
        val displayColors = colors.map { it.replace("_", " ").uppercase() }
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_popup_item, displayColors)
        selectCarColor?.setAdapter(adapter)
        if (selectedColorIndex >= displayColors.size) selectedColorIndex = 0
        selectCarColor?.setText(displayColors.getOrNull(selectedColorIndex) ?: "", false)
        updateCustomImageUI()
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            try {
                val inputStream = requireContext().contentResolver.openInputStream(it)
                val fileName = "car_custom_${System.currentTimeMillis()}.png"
                val file = File(requireContext().filesDir, fileName)
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
                
                customImagePath = "file://" + file.absolutePath
                updateCustomImageUI()
                Log.d(TAG, "PickImage: saved to $customImagePath")
            } catch (e: Exception) {
                Log.e(TAG, "PickImage failed", e)
            }
        }
    }

    private val pickOlImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            try {
                val inputStream = requireContext().contentResolver.openInputStream(it)
                val fileName = "car_custom_ol_${System.currentTimeMillis()}.png"
                val file = File(requireContext().filesDir, fileName)
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()

                customImagePathOl = "file://" + file.absolutePath
                updateCustomOlImageUI()
                Log.d(TAG, "PickOlImage: saved to $customImagePathOl")
            } catch (e: Exception) {
                Log.e(TAG, "PickOlImage failed", e)
            }
        }
    }

    private val pickMapImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            try {
                val inputStream = requireContext().contentResolver.openInputStream(it)
                val fileName = "car_custom_map_${System.currentTimeMillis()}.png"
                val file = File(requireContext().filesDir, fileName)
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()

                customImagePathMap = "file://" + file.absolutePath
                updateCustomMapImageUI()
                Log.d(TAG, "PickMapImage: saved to $customImagePathMap")
            } catch (e: Exception) {
                Log.e(TAG, "PickMapImage failed", e)
            }
        }
    }

    private fun updateCustomImageUI() {
        if (customImagePath != null) {
            btnClearCustomImage?.visibility = View.VISIBLE
            imgCustomPreview?.visibility = View.VISIBLE
            imgCustomPreview?.setImageDrawable(getCarDrawable(requireContext(), customImagePath))
            rvVehicleType?.alpha = 0.3f
            rvVehicleType?.isEnabled = false
            selectCarColor?.isEnabled = false
        } else {
            btnClearCustomImage?.visibility = View.GONE
            imgCustomPreview?.visibility = View.VISIBLE
            
            val currentType = vehicleTypes.getOrNull(selectedVehicleTypeIndex)
            val currentColor = currentType?.colors?.getOrNull(selectedColorIndex) ?: currentType?.colors?.getOrNull(0)
            if (currentType != null && currentColor != null) {
                val resName = if (currentType.id == "nrjk") "car_nrjk$currentColor" else "car_${currentType.id}_$currentColor"
                imgCustomPreview?.setImageResource(getDrawableIdentifier(requireContext(), resName))
            }
            
            rvVehicleType?.alpha = 1.0f
            rvVehicleType?.isEnabled = true
            selectCarColor?.isEnabled = true
        }
    }

    private fun updateCustomOlImageUI() {
        if (customImagePathOl != null) {
            btnClearCustomImageOl?.visibility = View.VISIBLE
            imgCustomPreviewOl?.visibility = View.VISIBLE
            imgCustomPreviewOl?.setImageDrawable(getCarDrawable(requireContext(), customImagePathOl))
            galleryCarOl?.alpha = 0.3f
            galleryCarOl?.isEnabled = false
        } else {
            btnClearCustomImageOl?.visibility = View.GONE
            imgCustomPreviewOl?.visibility = View.GONE
            galleryCarOl?.alpha = 1.0f
            galleryCarOl?.isEnabled = true
        }
    }

    private fun updateCustomMapImageUI() {
        if (customImagePathMap != null) {
            btnClearCustomImageMap?.visibility = View.VISIBLE
            imgCustomPreviewMap?.visibility = View.VISIBLE
            imgCustomPreviewMap?.setImageDrawable(getCarDrawable(requireContext(), customImagePathMap))
            galleryCarMap?.alpha = 0.3f
            galleryCarMap?.isEnabled = false
        } else {
            btnClearCustomImageMap?.visibility = View.GONE
            imgCustomPreviewMap?.visibility = View.GONE
            galleryCarMap?.alpha = 1.0f
            galleryCarMap?.isEnabled = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_careditor_v2, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        selectServer = requireView().findViewById<View>(R.id.select_server) as MaterialAutoCompleteTextView
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu_popup_item,
            requireContext().resources.getStringArray(R.array.select_server_options)
        )
        selectServer!!.setAdapter(adapter);
        selectServerPosition = -1
        servers = resources.getStringArray(R.array.select_server_options)
        gcmSenders = resources.getStringArray(R.array.select_server_gcm_senders)
        server = requireView().findViewById<View>(R.id.txt_server_address) as EditText
        gcmSender = requireView().findViewById<View>(R.id.txt_gcm_senderid) as EditText
        selectServer!!.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id -> setSelectedServer(position, true) }
        
        rvVehicleType = requireView().findViewById(R.id.rv_vehicle_type)
        rvVehicleType?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvVehicleType?.adapter = vehicleTypeAdapter

        selectCarColor = requireView().findViewById(R.id.select_car_color)
        selectCarColor?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedColorIndex = position
            updateCustomImageUI()
            if (isAutoSyncEnabled) autoSyncImages()
        }

        btnCustomImage = requireView().findViewById(R.id.btn_custom_image)
        btnClearCustomImage = requireView().findViewById(R.id.btn_clear_custom_image)
        imgCustomPreview = requireView().findViewById(R.id.img_custom_preview)

        btnCustomImage?.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnClearCustomImage?.setOnClickListener {
            customImagePath = null
            updateCustomImageUI()
        }

        galleryCarOl = requireView().findViewById<View>(R.id.ga_car_ol) as Gallery
        galleryCarOl!!.setAdapter(CarImgAdapter(availableOlImages, true))

        btnCustomImageOl = requireView().findViewById(R.id.btn_custom_image_ol)
        btnClearCustomImageOl = requireView().findViewById(R.id.btn_clear_custom_image_ol)
        imgCustomPreviewOl = requireView().findViewById(R.id.img_custom_preview_ol)

        btnCustomImageOl?.setOnClickListener {
            pickOlImageLauncher.launch("image/*")
        }

        btnClearCustomImageOl?.setOnClickListener {
            customImagePathOl = null
            updateCustomOlImageUI()
        }

        galleryCarMap = requireView().findViewById<View>(R.id.ga_car_map) as Gallery
        galleryCarMap!!.setAdapter(CarImgAdapter(availableMapIcons, false))

        btnCustomImageMap = requireView().findViewById(R.id.btn_custom_image_map)
        btnClearCustomImageMap = requireView().findViewById(R.id.btn_clear_custom_image_map)
        imgCustomPreviewMap = requireView().findViewById(R.id.img_custom_preview_map)

        btnCustomImageMap?.setOnClickListener {
            pickMapImageLauncher.launch("image/*")
        }

        btnClearCustomImageMap?.setOnClickListener {
            customImagePathMap = null
            updateCustomMapImageUI()
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.control_save_delete, menu)
            }

            override fun onPrepareMenu(menu: Menu) {
                Log.d("CarEditorFragment", "onPrepareMenu edit car: " + (getStoredCars().size > 1))
                menu.findItem(R.id.mi_delete).setVisible(carData != null && getStoredCars().size > 1)
                menu.findItem(R.id.mi_control).setVisible(false)
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.mi_save -> {
                        save()
                        true
                    }
                    R.id.mi_delete -> {
                        delete()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        load()
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
                var baseActivity: BaseFragmentActivity? = null
                try {
                    baseActivity = activity as BaseFragmentActivity?
                    baseActivity?.finish()
                } catch (ignored: Exception) {
                    findNavController().popBackStack()
                }
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
            carData!!.sel_tls = (rootView.findViewById<View>(R.id.chk_tls_enabled) as SwitchMaterial).isChecked
            carData!!.sel_tls_trust_all = (rootView.findViewById<View>(R.id.chk_tls_trust_all) as SwitchMaterial).isChecked
            
            if (customImagePath != null) {
                carData!!.sel_vehicle_image = customImagePath!!
            } else {
                val currentType = vehicleTypes[selectedVehicleTypeIndex]
                val currentColor = currentType.colors[selectedColorIndex]
                carData!!.sel_vehicle_image = if (currentType.id == "nrjk") "car_nrjk$currentColor" else "car_${currentType.id}_${currentColor}"
            }

            if (customImagePathOl != null) {
                carData!!.sel_vehicle_image_ol = customImagePathOl!!
            } else {
                val pos = galleryCarOl?.selectedItemPosition ?: -1
                val adapter = galleryCarOl?.adapter as? CarImgAdapter
                if (pos >= 0 && adapter != null) {
                    carData!!.sel_vehicle_image_ol = adapter.getItem(pos) as String
                } else {
                    carData!!.sel_vehicle_image_ol = ""
                }
            }

            if (customImagePathMap != null) {
                carData!!.sel_vehicle_image_map = customImagePathMap!!
            } else {
                val pos = galleryCarMap?.selectedItemPosition ?: -1
                val adapter = galleryCarMap?.adapter as? CarImgAdapter
                if (pos >= 0 && adapter != null) {
                    carData!!.sel_vehicle_image_map = adapter.getItem(pos) as String
                } else {
                    carData!!.sel_vehicle_image_map = ""
                }
            }
        } catch (e: ValidationException) {
            Log.e("Validation", e.message, e)
            return
        }
        if (editPosition < 0) {
            getStoredCars().add(carData!!)
        }
        saveStoredCars()
        var baseActivity: BaseFragmentActivity? = null
        try {
            baseActivity = activity as BaseFragmentActivity?
            baseActivity?.finish()
        } catch (ignored: Exception) {
            findNavController().popBackStack()
        }
    }

    private fun load() {
        try {
            val rootView = view
            if (carData == null) {
                // edit new car:
                setSelectedServer(0, false)
            } else {
                // edit existing car:
                compatActivity?.supportActionBar?.setTitle(carData!!.sel_vehicleid)
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
                val chkTlsEnabled = requireView().findViewById<View>(R.id.chk_tls_enabled) as SwitchMaterial
                val chkTlsTrustAll = requireView().findViewById<View>(R.id.chk_tls_trust_all) as SwitchMaterial
                chkTlsEnabled.isChecked = carData!!.sel_tls
                chkTlsTrustAll.isChecked = carData!!.sel_tls_trust_all
                chkTlsTrustAll.isEnabled = carData!!.sel_tls
                chkTlsEnabled.setOnClickListener {
                    chkTlsTrustAll.isEnabled = (it as SwitchMaterial).isChecked
                }

                // set car image:
                if (carData!!.sel_vehicle_image.startsWith("file://")) {
                    customImagePath = carData!!.sel_vehicle_image
                    updateCustomImageUI()
                } else {
                    customImagePath = null
                    updateCustomImageUI()
                    
                    // Match image to type and color
                for (typeIdx in vehicleTypes.indices) {
                    val type = vehicleTypes[typeIdx]
                    if (carData!!.sel_vehicle_image.startsWith("car_${type.id}")) {
                        selectedVehicleTypeIndex = typeIdx
                        vehicleTypeAdapter.selectedIndex = typeIdx
                        vehicleTypeAdapter.notifyDataSetChanged()
                        rvVehicleType?.scrollToPosition(typeIdx)
                        
                        val colorPart = if (type.id == "nrjk") {
                            carData!!.sel_vehicle_image.substringAfter("car_nrjk", "")
                        } else {
                            carData!!.sel_vehicle_image.substringAfter("car_${type.id}_", "")
                        }
                        selectedColorIndex = type.colors.indexOf(colorPart).coerceAtLeast(0)
                        
                        updateColorDropdown()
                        filterSecondaryGalleries()
                        break
                    }
                }
                }

                // set top-down image:
                if (carData!!.sel_vehicle_image_ol.isNotEmpty() && carData!!.sel_vehicle_image_ol.startsWith("file://")) {
                    customImagePathOl = carData!!.sel_vehicle_image_ol
                    updateCustomOlImageUI()
                } else {
                    customImagePathOl = null
                    updateCustomOlImageUI()
                    
                    val targetImg = if (carData!!.sel_vehicle_image_ol.isEmpty()) carData!!.sel_vehicle_image else carData!!.sel_vehicle_image_ol
                    val adapter = galleryCarOl?.adapter as? CarImgAdapter
                    if (adapter != null) {
                        var index = -1
                        for (i in 0 until adapter.count) {
                            if (adapter.getItem(i) == targetImg) {
                                index = i
                                break
                            }
                        }
                        if (index >= 0) {
                            galleryCarOl?.setSelection(index)
                        }
                    }
                }

                // set map image:
                if (!carData!!.sel_vehicle_image_map.isNullOrEmpty() && carData!!.sel_vehicle_image_map.startsWith("file://")) {
                    customImagePathMap = carData!!.sel_vehicle_image_map
                    updateCustomMapImageUI()
                } else {
                    customImagePathMap = null
                    updateCustomMapImageUI()
                    
                    val targetImg = if (carData!!.sel_vehicle_image_map.isNullOrEmpty()) "map_car_default" else carData!!.sel_vehicle_image_map
                    val adapter = galleryCarMap?.adapter as? CarImgAdapter
                    if (adapter != null) {
                        var index = -1
                        for (i in 0 until adapter.count) {
                            if (adapter.getItem(i) == targetImg) {
                                index = i
                                break
                            }
                        }
                        if (index >= 0) {
                            galleryCarMap?.setSelection(index)
                        }
                    }
                }

                // save selected vehicle label:
                val appPrefs = AppPrefs(requireActivity(), "ovms")
                Log.d(TAG, "load: sel_vehicle_label=" + carData!!.sel_vehicle_label)
                appPrefs.saveData("sel_vehicle_label", carData!!.sel_vehicle_label)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in load()", e)
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
                (server!!.parent as FrameLayout).visibility = View.GONE
                (gcmSender!!.parent as FrameLayout).visibility = View.GONE
            } else {
                if (userAction) {
                    server!!.setText("")
                    gcmSender!!.setText("")
                    server!!.requestFocus()
                } else {
                    server!!.setText(if (carData != null) carData!!.sel_server else "")
                    gcmSender!!.setText(if (carData != null) carData!!.sel_gcm_senderid else "")
                }
                (server!!.parent as FrameLayout).visibility = View.VISIBLE
                (gcmSender!!.parent as FrameLayout).visibility = View.VISIBLE
            }
            if (!userAction) {
                selectServer!!.setText(requireContext().resources.getStringArray(R.array.select_server_options)[position], false)
            }
        }
    }

    /*
     * Inner types
     */

    private companion object {

        private const val TAG = "CarEditorFragment"

        private val availableMapIcons = arrayOf(
            "map_car_default",
            "map_car_ampera_black",
            "map_car_ampera_crystalred",
            "map_car_ampera_cybergray",
            "map_car_ampera_lithiumwhite",
            "map_car_ampera_powerblue",
            "map_car_ampera_silvertopas",
            "map_car_ampera_sovereignsilver",
            "map_car_ampera_summitwhite",
            "map_car_boltev_summitwhite",
            "map_car_edeliver3_white",
            "map_car_env200_white",
            "map_car_fiat500e_black",
            "map_car_holdenvolt_black",
            "map_car_holdenvolt_crystalclaret",
            "map_car_holdenvolt_silvernitrate",
            "map_car_holdenvolt_urbanfresh",
            "map_car_holdenvolt_whitediamond",
            "map_car_i3",
            "map_car_imiev",
            "map_car_ioniq5_cybergray",
            "map_car_ioniq_polarwhite",
            "map_car_kangoo",
            "map_car_kiaev6_white",
            "map_car_kianiro_grey",
            "map_car_kiasoul_carribianblueclearwhite",
            "map_car_kiasoul_cherryblackinfernored",
            "map_car_kiasoul_clearwhite",
            "map_car_kiasoul_pearlwhiteelectronicblue",
            "map_car_kiasoul_titaniumsilver",
            "map_car_kona_blue",
            "map_car_kona_grey",
            "map_car_kona_red",
            "map_car_kona_white",
            "map_car_kona_yellow",
            "map_car_leaf2_gunmetallic",
            "map_car_leaf2_jadefrostmetallic",
            "map_car_leaf2_pearlwhite",
            "map_car_leaf2_superblack",
            "map_car_leaf2_vividblue",
            "map_car_leaf_coulisred",
            "map_car_leaf_deepblue",
            "map_car_leaf_forgedbronze",
            "map_car_leaf_gunmetallic",
            "map_car_leaf_pearlwhite",
            "map_car_leaf_planetblue",
            "map_car_leaf_superblack",
            "map_car_mgzs_black",
            "map_car_mgzs_blue",
            "map_car_mgzs_lightblue",
            "map_car_mgzs_red",
            "map_car_mgzs_white",
            "map_car_nrjk",
            "map_car_smart",
            "map_car_thinkcity_brightred",
            "map_car_thinkcity_citrusyellow",
            "map_car_thinkcity_classicblack",
            "map_car_thinkcity_skyblue",
            "map_car_twizy_diamondblackwithivygreen",
            "map_car_twizy_snowwhiteandflameorange",
            "map_car_twizy_snowwhiteandurbanblue",
            "map_car_twizy_snowwhitewithblack",
            "map_car_vwup_black",
            "map_car_vwup_blue",
            "map_car_vwup_red",
            "map_car_vwup_silver",
            "map_car_vwup_white",
            "map_car_vwup_yellow",
            "map_car_zoe_black",
            "map_car_zoe_brown",
            "map_car_zoe_grey",
            "map_car_zoe_hellblau",
            "map_car_zoe_lila",
            "map_car_zoe_red",
            "map_car_zoe_white",
            "map_car_zoe_ytriumgrau"
        )

        private val availableOlImages = arrayOf(
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
            "car_ampera",
            "car_boltev_summitwhite",
            "car_edeliver3_white",
            "car_env200_white",
            "car_fiat500e_black",
            "car_i3",
            "car_imiev",
            "car_ioniq5_cybergray",
            "car_ioniq_polarwhite",
            "car_kangoo",
            "car_kiaev6_white",
            "car_kianiro_grey",
            "car_kiasoul_carribianblueclearwhite",
            "car_kiasoul_cherryblackinfernored",
            "car_kiasoul_clearwhite",
            "car_kiasoul_pearlwhiteelectronicblue",
            "car_kiasoul_titaniumsilver",
            "car_kona_blue",
            "car_kona_grey",
            "car_kona_red",
            "car_kona_white",
            "car_kona_yellow",
            "car_leaf2_gunmetallic",
            "car_leaf2_jadefrostmetallic",
            "car_leaf2_pearlwhite",
            "car_leaf2_superblack",
            "car_leaf2_vividblue",
            "car_leaf_coulisred",
            "car_leaf_deepblue",
            "car_leaf_forgedbronze",
            "car_leaf_gunmetallic",
            "car_leaf_pearlwhite",
            "car_leaf_planetblue",
            "car_leaf_superblack",
            "car_mgzs_black",
            "car_mgzs_blue",
            "car_mgzs_lightblue",
            "car_mgzs_red",
            "car_mgzs_white",
            "car_niu_mqi_gt",
            "car_nrjk",
            "car_nrjkexperia",
            "car_smart",
            "car_thinkcity_brightred",
            "car_thinkcity_citrusyellow",
            "car_thinkcity_classicblack",
            "car_thinkcity_skyblue",
            "car_twizy",
            "car_vwup_black",
            "car_vwup_blue",
            "car_vwup_red",
            "car_vwup_silver",
            "car_vwup_white",
            "car_vwup_yellow",
            "car_zoe_black",
            "car_zoe_brown",
            "car_zoe_grey",
            "car_zoe_hellblau",
            "car_zoe_lila",
            "car_zoe_red",
            "car_zoe_white",
            "car_zoe_ytriumgrau"
        )

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
            "car_nrjkego_black",
            "car_nrjkexperia_black",
            "car_nrjkexperia_graybags",
            "car_nrjkribelle_red",
            "car_nrjkss9_orange",
            "car_niu_mqi_gt_or",
            "car_niu_mqi_gt_silver",
            "car_niu_mqi_gt_white",
            "car_niu_mqi_gt_black"
        )
    }

    private class CarImgAdapter(private val images: Array<String>, private val isOl: Boolean = false) : BaseAdapter() {

        override fun getCount(): Int {
            return images.size
        }

        override fun getItem(position: Int): Any {
            return images[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val iv = convertView as? ImageView ?: ImageView(parent.context)
            val density = parent.context.resources.displayMetrics.density
            iv.layoutParams = Gallery.LayoutParams((200 * density).toInt(), (120 * density).toInt())
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE)
            iv.setAdjustViewBounds(true)
            val p = (8 * density).toInt()
            iv.setPadding(p, p, p, p)
            iv.setBackgroundResource(R.drawable.gallery_item_selector)
            val resName = if (isOl) "ol_" + images[position] else images[position]
            iv.setImageResource(getDrawableIdentifier(parent.context, resName))
            return iv
        }
    }
}