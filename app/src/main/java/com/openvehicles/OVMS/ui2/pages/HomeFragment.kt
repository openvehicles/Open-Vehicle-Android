package com.openvehicles.OVMS.ui2.pages

import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.AdapterView.VISIBLE
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.slider.RangeSlider
import com.google.gson.Gson
import com.maltaisn.icondialog.IconDialog
import com.maltaisn.icondialog.IconDialogSettings
import com.maltaisn.icondialog.data.Icon
import com.maltaisn.icondialog.pack.IconPack
import com.openvehicles.OVMS.BaseApp
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.entities.StoredCommand
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.utils.Ui
import com.openvehicles.OVMS.ui2.MainActivityUI2
import com.openvehicles.OVMS.ui2.components.hometabs.HomeTab
import com.openvehicles.OVMS.ui2.components.hometabs.HomeTabsAdapter
import com.openvehicles.OVMS.ui2.components.quickactions.ChargingQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.ClimateQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.CustomCommandQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.Homelink1QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.Homelink2QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.Homelink3QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.LockQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.TwizyDriveMode1QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.TwizyDriveMode2QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.TwizyDriveMode3QuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.TwizyDriveModeDefaultQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.ValetQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.WakeupQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.adapters.QuickActionsAdapter
import com.openvehicles.OVMS.ui2.components.quickactions.adapters.QuickActionsEditorAdapter
import com.openvehicles.OVMS.ui2.misc.CarAnimationDrawable
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.CarsStorage
import com.openvehicles.OVMS.utils.CarsStorage.getStoredCars
import java.io.IOException
import java.util.Locale
import kotlin.math.floor
import kotlin.properties.Delegates


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment(), OnResultCommandListener, HomeTabsAdapter.ItemClickListener,  ActionBar.OnNavigationListener, IconDialog.Callback {

    private var carData: CarData? = null
    override var iconDialogIconPack: IconPack? = null
    private lateinit var quickActionsAdapter: QuickActionsAdapter
    private lateinit var quickActionsEditorAdapter: QuickActionsEditorAdapter
    private lateinit var tabsAdapter: HomeTabsAdapter
    private var textColor by Delegates.notNull<Int>()

    private lateinit var appPrefs: AppPrefs

    private val CAR_RENDER_TEST_MODE_T = false
    private val CAR_RENDER_TEST_MODE_D = false
    private val CAR_RENDER_TEST_MODE_HD = false
    private val CAR_RENDER_TEST_MODE_CHG = false

    private var lastQuickActionConfig: String? = null
    private var socState = 0
    private var showEditAction = false

        set(value) {
            quickActionsAdapter.editMode = value
            findViewById(R.id.modifyQuickActions).visibility = if (value) View.VISIBLE else View.GONE
        }

    companion object {
        private val TAB_CONTROLS = 1
        private val TAB_LOCATION = 2
        private val TAB_CHARGING = 3
        private val TAB_CLIMATE = 4
        private val TAB_SETTINGS = 5
        private val TAB_ENERGY = 6
        private val ICON_DIALOG_TAG = "add-quickaction-icon-dialog"

        private fun getEditorQuickActions(context: Context): List<QuickAction> {
            return listOf(
                ChargingQuickAction({null}, context),
                ClimateQuickAction({null}, context),
                LockQuickAction({null}, context),
                WakeupQuickAction({null}, context),
                ValetQuickAction({null}, context),
                Homelink1QuickAction({null}, context),
                Homelink2QuickAction({null}, context),
                Homelink3QuickAction({null}, context),
                TwizyDriveModeDefaultQuickAction({null}, context),
                TwizyDriveMode1QuickAction({null}, context),
                TwizyDriveMode2QuickAction({null}, context),
                TwizyDriveMode3QuickAction({null}, context),
                CustomCommandQuickAction("custom", AppCompatResources.getDrawable(context, R.drawable.ic_custom_command)!!, "", {null}, context.getString(R.string.custom_command)),
                )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iconDialogIconPack = (requireActivity().application as BaseApp).iconPack
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appPrefs = AppPrefs(requireContext(), "ovms")

        val menuHost: MenuHost = requireActivity()



        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    R.id.notifications -> findNavController().navigate(R.id.action_navigation_home_to_notificationsFragment)
                }
                return true
            }
        }, viewLifecycleOwner)

        val quickActionsRecyclerView = findViewById(R.id.quickActonBar) as RecyclerView
        val modifyQuickActionsRecyclerView = findViewById(R.id.modifyQuickActionsRecyclerView) as RecyclerView
        quickActionsAdapter = QuickActionsAdapter(context, {_ ->
            saveQuickActions(quickActionsAdapter.mData.toList())
            updateQuickActionEditorItems()
        })

        val callback: ItemTouchHelper.Callback = object : ItemTouchHelper.Callback() {

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == 2 && !quickActionsAdapter.editMode) {
                    showEditAction = true
                    quickActionsAdapter.notifyDataSetChanged()
                    (activity as MainActivityUI2?)?.startSupportActionMode(object : androidx.appcompat.view.ActionMode.Callback {

                        override fun onCreateActionMode(
                            mode: androidx.appcompat.view.ActionMode?,
                            menu: Menu?
                        ): Boolean {
                            return true
                        }

                        override fun onPrepareActionMode(
                            mode: androidx.appcompat.view.ActionMode?,
                            menu: Menu?
                        ): Boolean {
                            mode?.title = getString(R.string.modify_quick_actions)
                            return true
                        }

                        override fun onActionItemClicked(
                            mode: androidx.appcompat.view.ActionMode?,
                            item: MenuItem?
                        ): Boolean {
                            return true
                        }

                        override fun onDestroyActionMode(mode: androidx.appcompat.view.ActionMode?) {
                            showEditAction = false
                            quickActionsAdapter.notifyDataSetChanged()
                        }
                    })
                }
            }

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                quickActionsAdapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                saveQuickActions(quickActionsAdapter.mData)
                return true
            }

        }

        val quickActionsManager = FlexboxLayoutManager(requireContext())
        quickActionsManager.flexDirection = FlexDirection.ROW
        quickActionsManager.justifyContent = JustifyContent.SPACE_EVENLY
        quickActionsRecyclerView.layoutManager = quickActionsManager
        quickActionsRecyclerView.adapter = quickActionsAdapter

        // Quick Actions editor
        quickActionsEditorAdapter = QuickActionsEditorAdapter(requireContext(), {onQuickActionAddRequest(it)})
        quickActionsEditorAdapter.mData += getEditorQuickActions(requireContext())
        modifyQuickActionsRecyclerView.adapter = quickActionsEditorAdapter
        val quickActionsEditorManager = FlexboxLayoutManager(requireContext())
        quickActionsEditorManager.flexDirection = FlexDirection.ROW
        quickActionsEditorManager.justifyContent = JustifyContent.CENTER
        modifyQuickActionsRecyclerView.layoutManager = quickActionsEditorManager

        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(quickActionsRecyclerView)

        val homeTabsRecyclerView = findViewById(R.id.menuItems) as RecyclerView
        tabsAdapter = HomeTabsAdapter(context)
        tabsAdapter.setClickListener(this)
        homeTabsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        homeTabsRecyclerView.adapter = tabsAdapter

        val swipeRefresh = findViewById(R.id.swipeRefreshHome) as SwipeRefreshLayout
        val statusProgressBar = findViewById(R.id.carUpdatingProgress) as CircularProgressIndicator

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            statusProgressBar.visibility = VISIBLE
            triggerCarDataUpdate()
        }

        val typedValue = TypedValue()
        val theme = requireContext().theme
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)
        textColor = typedValue.data

        carData = CarsStorage.getSelectedCarData()
        setupVisualisation(carData)
        initialiseChargingCard(carData)
        initialiseQuickActions(carData)
        initialiseTabs(carData)
        initialiseBottomInfo(carData)
        initialiseCarDropDown()

    }

    private fun initialiseCarDropDown() {
        val cars = getStoredCars()
        val spinner = (activity as MainActivityUI2).findViewById(R.id.spinner_toolbar) as Spinner?
        val mArrayAdapter =
            NavAdapter(requireContext(), cars.map { CarPickerItem(it, it.sel_vehicle_label) }.toTypedArray())
        if (spinner != null) {
            spinner.adapter = mArrayAdapter
            if (carData != null)
                spinner.setSelection(cars.indexOfFirst { cD -> cD.hashCode() == carData.hashCode()  })
        }
        spinner?.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCarData = mArrayAdapter.getItem(position)?.carData
                if (selectedCarData != null && selectedCarData.hashCode() != carData.hashCode()) {
                    changeCar(selectedCarData)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private class NavAdapter(
        context: Context,
        objects: Array<CarPickerItem>
    ) : ArrayAdapter<CarPickerItem?>(
        context,
        android.R.layout.simple_spinner_item,
        objects
    ) {
        init {
            setDropDownViewResource(R.layout.menu_car)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val textView =
                View.inflate(context, android.R.layout.simple_spinner_item, null) as TextView
            textView.setText(getItem(position)?.carName)
            textView.textSize = 20F
            return textView
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            var row: View? = null
            if (convertView == null) {
                row = LayoutInflater.from(context)
                    .inflate(
                        R.layout.menu_car, parent,
                        false
                    )
            } else {
                row = convertView
            }
            row?.findViewById<TextView>(R.id.txt_title)?.text = getItem(position)?.carName
            row?.findViewById<ImageView>(R.id.img_car)?.setImageResource(Ui.getDrawableIdentifier(context, getItem(position)?.carData?.sel_vehicle_image))
            row?.findViewById<TextView>(R.id.menuRange)?.text = String.format("%s, %s: %s, %s: %s",  getItem(position)?.carData?.car_soc, context.getString(R.string.IdealShort).split(" ").first(), getItem(position)?.carData?.car_range_ideal, context.getString(R.string.EstimatedShort).split(" ").first(), getItem(position)?.carData?.car_range_estimated)
            return row!!
        }
    }

    private class CarPickerItem(val carData: CarData?, val carName: String) {
        override fun toString(): String {
            return carName
        }

    }

    /**
     * setupVisualisation: Set selected car title and initialise top part of UI with car rendering
     *
     * @param carData
     */
    private fun setupVisualisation(carData: CarData?) {
        // Car name
        (activity as MainActivityUI2?)?.supportActionBar?.title = carData?.sel_vehicle_label

        // SOC icon and label
        val socText: TextView = findViewById(R.id.battPercent) as TextView
        val rangeText: TextView = findViewById(R.id.battRange) as TextView
        val socBattIcon = findViewById(R.id.batteryIndicatorView) as ImageView

        var socBattLayers = emptyList<Drawable>()

        socBattLayers += ContextCompat.getDrawable(requireContext(), R.drawable.ic_batt_l0)!!

        val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_batt_l1)!!.toBitmap()

        val soc = carData?.car_soc_raw ?: 0f
        val iconWidth = icon.height.minus(22).times(((soc / 100.0))).plus(7.5)
        if (iconWidth > 0) {
            val matrix = Matrix()
            matrix.postRotate(180f)
            val mBitmap =
                Bitmap.createBitmap(icon, 0, 0, icon.width, iconWidth.toInt(), matrix, true)
            val layer1Drawable = BitmapDrawable(resources, mBitmap)
            layer1Drawable.gravity = Gravity.BOTTOM

            if (carData?.car_charging == true) {
                layer1Drawable.setTint(
                    context?.resources?.getColor(R.color.colorAccent) ?: Color.GREEN
                )
            } else if (soc <= 10) {
                layer1Drawable.setTint(Color.RED)
            } else if (soc <= 20) {
                layer1Drawable.setTint(Color.YELLOW)
            } else {
                layer1Drawable.setTint(Color.WHITE)
            }
            socBattLayers += layer1Drawable
        }
        val socBattLayer = LayerDrawable(socBattLayers.toTypedArray())
        socBattIcon.setImageDrawable(socBattLayer)
        socText.text = carData?.car_soc

        val rangeDisplay = appPrefs.appUIPrefs.getStringSet("home_range_display_mode", HashSet<String>())
        val idealRange = rangeDisplay?.contains("ideal") == true
        val estimatedRange = rangeDisplay?.contains("estimated") == true

        if (idealRange && estimatedRange) {
            rangeText.text = "I: ${carData?.car_range_ideal}, E: ${carData?.car_range_estimated}"
        } else if (idealRange || estimatedRange) {
            rangeText.text = if (idealRange) carData?.car_range_ideal else carData?.car_range_estimated
        } else {
            val socToggleListener = {
                socState += 1
                if (socState > 2)
                    socState = 0
                socText.text = when (socState) {
                    1 -> "I: ${carData?.car_range_ideal}"
                    2 -> "E: ${carData?.car_range_estimated}"
                    else -> carData?.car_soc
                }
            }
            socText.setOnClickListener { socToggleListener() }
            socBattIcon.setOnClickListener { socToggleListener() }
        }

        // Status label

        val statusText: TextView = findViewById(R.id.carStatus) as TextView
        val statusProgressBar = findViewById(R.id.carUpdatingProgress) as CircularProgressIndicator

        val now = System.currentTimeMillis()
        var seconds = (now - (carData?.car_lastupdated?.time ?: 0)) / 1000
        var minutes = seconds / 60
        var hours = minutes / 60
        var days = minutes / (60 * 24)

        if (minutes == 0L) {
            statusProgressBar.visibility = View.GONE
            // Car is online
            statusText.setText(R.string.parked)
            if (carData?.car_started == true) {
                statusText.text = carData.car_speed
            }
            if (carData?.car_charging == true || carData?.car_charge_state_i_raw == 14) {
                statusText.setText(R.string.state_charging_label)

                val etrFull = carData.car_chargefull_minsremaining
                val suffSOC = carData.car_chargelimit_soclimit
                val etrSuffSOC = carData.car_chargelimit_minsremaining_soc
                val suffRange = carData.car_chargelimit_rangelimit_raw
                val etrSuffRange = carData.car_chargelimit_minsremaining_range

                if (suffSOC > 0 && etrSuffSOC > 0) {
                    statusText.text = String.format(getString(R.string.charging_estimation_soc), String.format("%02d:%02d", etrSuffSOC / 60, etrSuffSOC % 60))
                } else if (suffRange > 0 && etrSuffRange > 0) {
                    statusText.text = String.format(getString(R.string.charging_estimation_range), String.format("%02d:%02d", etrSuffRange / 60, etrSuffRange % 60))
                } else if (etrFull > 0) {
                    statusText.text = String.format(getString(R.string.charging_estimation_full), String.format("%02d:%02d", etrFull / 60, etrFull % 60))
                }

                var chargeStateInfo = 0

                when (carData.car_charge_state_i_raw) {
                    2 -> chargeStateInfo = R.string.state_topping_off_label
                    4 -> chargeStateInfo = R.string.state_done_label
                    14 -> chargeStateInfo = R.string.timedcharge
                    21 -> chargeStateInfo = R.string.state_stopped_label
                }

                if (chargeStateInfo != 0) {
                    statusText.setText(chargeStateInfo)
                }

            }
        } else {
            statusProgressBar.visibility = View.VISIBLE
            val periodText: CharSequence?
            if (minutes == 1L) {
                periodText = getText(R.string.min1)
            } else if (days > 1) {
                periodText = String.format(getText(R.string.ndays).toString(), days)
            } else if (hours > 1) {
                periodText = String.format(getText(R.string.nhours).toString(), hours)
            } else if (minutes > 60) {
                periodText = String.format(
                    getText(R.string.nmins).toString(),
                    minutes
                )
            } else {
                periodText = String.format(
                    getText(R.string.nmins).toString(),
                    minutes
                )
            }
            statusText.text = String.format(getString(R.string.last_seen), periodText)
            if (carData?.car_lastupdated?.time == null) {
                statusText.text = getString(R.string.loading)
            }
            if (getService()?.isOnline() == false) {
                statusText.text = getString(R.string.connecting)
            }
        }

        // Car image
        val image: ImageView = findViewById(R.id.carStatusImage) as ImageView

        val name_splitted = carData?.sel_vehicle_image?.split("_");
        val car_tire_image1 = Ui.getDrawableIdentifier(
            context,
            name_splitted?.minus(name_splitted.last())?.joinToString("_") +"_tireanim"
        )

        var layers = emptyList<Drawable>()

        if (carData?.car_rearleftdoor_open == true || CAR_RENDER_TEST_MODE_D) {
            val modeResource = Ui.getDrawableIdentifier(
                context,
                carData?.sel_vehicle_image+"_rld"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }

        if (carData?.car_frontleftdoor_open == true || CAR_RENDER_TEST_MODE_D) {
            val modeResource = Ui.getDrawableIdentifier(
                context,
                carData?.sel_vehicle_image+"_fld"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }

        layers = layers.plus(ContextCompat.getDrawable(requireContext(), Ui.getDrawableIdentifier(
            context,
            carData?.sel_vehicle_image
        ))!!)

        val speedShownInUI = car_tire_image1 > 0 && (carData?.car_started == true||CAR_RENDER_TEST_MODE_D)

        if (speedShownInUI) {
            val animationDrawable = context?.getDrawable(car_tire_image1) as AnimationDrawable
            val carAnim = CarAnimationDrawable()
            var drawables = emptyList<Drawable>()
            for (i in 0..<animationDrawable.numberOfFrames) {
                drawables = drawables.plus(animationDrawable.getFrame(i))
            }
            drawables.forEach { carAnim.addFrame(it, 35) }
            carAnim.isOneShot = false
            layers = layers.plus(carAnim)




            if ((carData?.car_speed_raw ?: 0f) > 0) {
                // Adjust animaion speed
                carAnim.start()
                carAnim.setDuration((320 / (carData?.car_speed_raw!!/3.5)).toInt())
            }

        }

        if (carData?.car_headlights_on == true || CAR_RENDER_TEST_MODE_HD) {
            val modeResource = Ui.getDrawableIdentifier(
                context,
                name_splitted?.minus(name_splitted.last())?.joinToString("_") +"_hd"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }

        if (carData?.car_rearrightdoor_open == true || CAR_RENDER_TEST_MODE_D) {
            val modeResource = Ui.getDrawableIdentifier(
                context,
                carData?.sel_vehicle_image+"_rrd"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }

        if (carData?.car_frontrightdoor_open == true || CAR_RENDER_TEST_MODE_D) {
            val modeResource = Ui.getDrawableIdentifier(
                context,
                carData?.sel_vehicle_image+"_frd"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }


        if (carData?.car_trunk_open == true || CAR_RENDER_TEST_MODE_T) {
            val modeResource = Ui.getDrawableIdentifier(
                context,
                carData?.sel_vehicle_image+"_t"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
            else {
                val modeResource = Ui.getDrawableIdentifier(
                    context,
                    name_splitted?.minus(name_splitted.last())?.joinToString("_")+"_t"
                )
                if (modeResource > 0)
                    layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
            }
        }

        if (carData?.car_bonnet_open == true || CAR_RENDER_TEST_MODE_T) {
            val modeResource = Ui.getDrawableIdentifier(
                context,
                carData?.sel_vehicle_image+"_b"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
            else {
                val modeResource = Ui.getDrawableIdentifier(
                    context,
                    name_splitted?.minus(name_splitted.last())?.joinToString("_")+"_b"
                )
                if (modeResource > 0)
                    layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
            }
        }

        var chargingResName = name_splitted?.minus(name_splitted.last())?.joinToString("_")

        if (carData?.car_charge_mode == "performance" || (carData?.car_charge_current_raw ?: 0f) > 48) {
            chargingResName += "_q"
        }

        val chargePortOpen = if (carData?.car_type == "NL") (carData.car_charging || carData.car_charge_timer || carData.car_charge_state_i_raw == 14) else carData?.car_chargeport_open == true


        if (chargePortOpen || CAR_RENDER_TEST_MODE_CHG) {
            val modeResource = Ui.getDrawableIdentifier(
                context,
                name_splitted?.minus(name_splitted.last())?.joinToString("_") +"_cp"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }


        // For some reason some cars do not show timed charge as car_charge_timer
        val rawTimerStatus =
                    carData?.car_chargeport_open == true &&
                    (carData.car_charge_state_i_raw == 0x0d ||
                     carData.car_charge_state_i_raw == 0x0e ||
                     carData.car_charge_state_i_raw == 0x101)


        if (carData?.car_charge_timer == true || rawTimerStatus) {
            val modeResource = Ui.getDrawableIdentifier(
                context,
                chargingResName +"_cw"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }


        if (carData?.car_charge_state_i_raw == 0x01 || carData?.car_charge_state_i_raw == 0x02 || carData?.car_charge_state_i_raw == 0x0f || carData?.car_charging == true || CAR_RENDER_TEST_MODE_CHG) {
            val modeResource = Ui.getDrawableIdentifier(
                context,
                chargingResName +"_chg"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }


        val newDrawable = LayerDrawable(layers.toTypedArray())
        if ((image.drawable as LayerDrawable?)?.numberOfLayers !== newDrawable.numberOfLayers) {
            val anim_in: Animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
            image.setImageDrawable(
                newDrawable
            )
            anim_in.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {}
            })
            image.startAnimation(anim_in)
        } else if (speedShownInUI) {
            image.setImageDrawable(
                newDrawable
            )
        }
    }

    /**
     * initialiseChargingCard: Initialise charging card data
     */
    private fun initialiseChargingCard(carData: CarData?) {
        val chargingCard = findViewById(R.id.chargingCard) as MaterialCardView
        // Nissan Leaf shows charging port as always open when parked
        val chargePortOpen = if (carData?.car_type == "NL") (carData.car_charging || carData.car_charge_timer) else carData?.car_chargeport_open == true
        chargingCard.visibility = if (chargePortOpen == true) View.VISIBLE else View.GONE

        val ampLimitSlider = findViewById(R.id.seekBar) as RangeSlider

        //ampLimitSlider.valueFrom = 1.0f
        ampLimitSlider.setValues(1.0f)

        if (chargePortOpen != true) return

        val chargingCardTitle = findViewById(R.id.chargingStatus) as TextView
        val chargingCardSubtitle = findViewById(R.id.chargingStats) as TextView

        chargingCardTitle.text = carData?.car_charge_mode?.uppercase()

        var chargingPower = 0.0
        if ((carData?.car_charge_power_input_kw_raw ?: 0f) > 0) {
            chargingPower = carData!!.car_charge_power_input_kw_raw.toDouble()
        }
        if ((carData?.car_charge_power_kw_raw ?: 0.0) > 0) {
            chargingPower = carData!!.car_charge_power_kw_raw
        }

        if (carData?.car_charge_linevoltage_raw != null) {
            // Divide by -1000, because current is negative when charging
            chargingPower =
                (carData.car_charge_linevoltage_raw.toDouble() * carData.car_charge_current_raw.toDouble()) / -1000.0
        }

        chargingCardSubtitle.text = String.format("%2.2f kW, %s %s, Battery: %s", chargingPower, carData?.car_charge_linevoltage, carData?.car_charge_current, carData?.car_temp_battery)

        // Amp limit and slider
        val ampLimit = findViewById(R.id.ampLimit) as TextView

        ampLimit.text = carData?.car_charge_currentlimit
        ampLimitSlider.setValues(carData?.car_charge_currentlimit_raw)
        val touchListener: RangeSlider.OnSliderTouchListener = object :
            RangeSlider.OnSliderTouchListener {


            override fun onStartTrackingTouch(slider: RangeSlider) {
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle(R.string.lb_charger_confirm_amp_change)
                    .setNegativeButton(R.string.Cancel) {_, _ ->}
                    .setPositiveButton(android.R.string.ok) { dlg, which ->                     sendCommand(
                        R.string.msg_setting_charge_m, String.format("10,%d", ampLimitSlider.values.first().toInt()),
                        this@HomeFragment
                    );dlg.dismiss()}
                    .show()
            }
        }

        ampLimitSlider.clearOnSliderTouchListeners()
        ampLimitSlider.addOnSliderTouchListener(touchListener)

        ampLimitSlider.clearOnChangeListeners()
        ampLimitSlider.addOnChangeListener { slider, value, fromUser ->
            ampLimit.text = "${value.toInt()}A"
        }

        // Action buttons
        val action1 = findViewById(R.id.charging_action1) as Button
        val action2 = findViewById(R.id.charging_action2) as Button

        action1.isEnabled = carData?.car_charging == false && carData.car_charge_state_i_raw != 0x101 && carData.car_charge_state_i_raw != 0x115
        action2.isEnabled = carData?.car_charging == true && carData.car_charge_state_i_raw != 0x101 && carData.car_charge_state_i_raw != 0x115

        action1.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle(R.string.lb_charger_confirm_start)
                .setNegativeButton(R.string.Cancel) {_, _ ->}
                .setPositiveButton(android.R.string.ok) { dlg, which -> startCharge() }
                .show()
        }

        action2.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle(R.string.lb_charger_confirm_stop)
                .setNegativeButton(R.string.Cancel) {_, _ ->}
                .setPositiveButton(android.R.string.ok) { dlg, which -> stopCharge() }
                .show()
        }

    }

    private fun startCharge() {
        sendCommand(R.string.msg_starting_charge, "11", this)
        carData!!.car_charge_linevoltage_raw = 0f
        carData!!.car_charge_current_raw = 0f
        carData!!.car_charge_state_s_raw = "starting"
        carData!!.car_charge_state_i_raw = 0x101
        update(carData)
    }

    private fun stopCharge() {
        sendCommand(R.string.msg_stopping_charge, "12", this)
        carData!!.car_charge_linevoltage_raw = 0f
        carData!!.car_charge_current_raw = 0f
        carData!!.car_charge_state_s_raw = "stopping"
        carData!!.car_charge_state_i_raw = 0x115
        update(carData)
    }

    override fun onResultCommand(result: Array<String>) {
        if (result.size <= 1) return
        val resCode = result[1].toInt()
        val resText = if (result.size > 2) result[2] else ""
        val cmdMessage = getSentCommandMessage(result[0])
        val context: Context? = activity
        if (context != null) {
            when (resCode) {
                0 -> Toast.makeText(
                    context, cmdMessage + " " + getString(R.string.msg_ok),
                    Toast.LENGTH_SHORT
                ).show()

                1 -> Toast.makeText(
                    context, cmdMessage + " " + getString(R.string.err_failed, resText),
                    Toast.LENGTH_SHORT
                ).show()

                2 -> Toast.makeText(
                    context, cmdMessage + " " + getString(R.string.err_unsupported_operation),
                    Toast.LENGTH_SHORT
                ).show()

                3 -> Toast.makeText(
                    context, cmdMessage + " " + getString(R.string.err_unimplemented_operation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        cancelCommand()
    }


    private fun initialiseQuickActions(carData: CarData?) {
        if (lastQuickActionConfig == null || quickActionsAdapter.mData.isEmpty() || lastQuickActionConfig != carData?.sel_vehicleid) {
            // TODO Load config from settings for selected vehicle
            var savedQuickActionConfig: Array<String> = Gson().fromJson(
                appPrefs.getData("quick_actions_"+carData?.sel_vehicleid, "[]"),
                Array<String>::class.java
            )
            if (savedQuickActionConfig.isEmpty()) {
                // Get default config and save most optimised version for the vehicle
                val defaultIdConfig: Array<String> = when (carData?.sel_vehicleid) {
                    "RT" -> arrayOf("charging", "valet", "rt_profile_0", "rt_profile_1", "rt_profile_2", "rt_profile_3")
                    else -> arrayOf(
                        LockQuickAction({null}),
                        ChargingQuickAction({null}),
                        ValetQuickAction({null}),
                        WakeupQuickAction({null}),
                        Homelink1QuickAction({null}),
                        Homelink2QuickAction({null}),
                        Homelink3QuickAction({null})
                    ).filter { it.commandsAvailable() }.map { it.id }.take(6).toTypedArray()
                }
                saveQuickActions(null, defaultIdConfig)
                savedQuickActionConfig = defaultIdConfig
            }
            lastQuickActionConfig = carData?.sel_vehicleid

            quickActionsAdapter.mData.clear()

            for (action in savedQuickActionConfig) {
                getActionFromId(action) { getService() }?.let {
                    quickActionsAdapter.mData.add(it)
                }
            }
            updateQuickActionEditorItems()
        }
        updateQuickActions(carData)
    }

    private fun getActionFromId(id: String, apiServiceGetter: () -> ApiService?): QuickAction? {
        return when (id) {
            ChargingQuickAction.ACTION_ID -> ChargingQuickAction(apiServiceGetter)
            ClimateQuickAction.ACTION_ID -> ClimateQuickAction(apiServiceGetter)
            LockQuickAction.ACTION_ID -> LockQuickAction(apiServiceGetter)
            ValetQuickAction.ACTION_ID -> ValetQuickAction(apiServiceGetter)
            WakeupQuickAction.ACTION_ID -> WakeupQuickAction(apiServiceGetter)
            else -> {
                if (id.startsWith("rt_profile_")) {
                    return id.replace("rt_profile_", "").toIntOrNull().let {profileNum ->
                        when (profileNum) {
                            0 -> TwizyDriveModeDefaultQuickAction(apiServiceGetter)
                            1 -> TwizyDriveMode1QuickAction(apiServiceGetter)
                            2 -> TwizyDriveMode2QuickAction(apiServiceGetter)
                            3 -> TwizyDriveMode3QuickAction(apiServiceGetter)
                            else -> null
                        }
                    }
                }
                if (id.startsWith("hl_")) {
                    return id.replace("hl_", "").toIntOrNull().let {profileNum ->
                        when (profileNum) {
                            1 -> Homelink1QuickAction(apiServiceGetter)
                            2 -> Homelink2QuickAction(apiServiceGetter)
                            3 -> Homelink3QuickAction(apiServiceGetter)
                            else -> null
                        }
                    }
                }
                if (id.startsWith("custom_")) {
                    try {
                        val customCommand: StoredCommand? = com.openvehicles.OVMS.utils.Base64.decodeToObject(id.removePrefix("custom_"), 0, StoredCommand::class.java.classLoader) as StoredCommand?
                        return iconDialogIconPack?.getIcon(customCommand?.title?.toInt() ?: -1)?.drawable?.let {icon ->
                            return CustomCommandQuickAction(id, icon, customCommand!!.command, {getService()})
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return null
            }
        }
    }

    /*
       Update available editor items based on currently shown items
     */
    private fun updateQuickActionEditorItems() {
        quickActionsEditorAdapter.mData.clear()
        for (itm in getEditorQuickActions(requireContext())) {
            if (quickActionsAdapter.mData.find { it.id == itm.id } == null && (!itm.id.startsWith("rt_") || carData?.car_type == "RT")) {
                quickActionsEditorAdapter.mData.add(itm)
            }
        }
        quickActionsEditorAdapter.notifyDataSetChanged()

    }

    private fun saveQuickActions(actions: List<QuickAction>?, ids: Array<String>? = null) {
        carData?.sel_vehicleid.let {
            appPrefs.saveData("quick_actions_$it", Gson().toJson(ids ?: (actions?.map { itm -> itm.id}?.toTypedArray() ?: emptyArray<String>())))
        }
    }

    private fun onQuickActionAddRequest(id: String) {
        if (quickActionsAdapter.mData.size > 5) {
            Toast.makeText(context, getString(R.string.max_quick_actions_reached), Toast.LENGTH_SHORT).show()
            return
        }
        if (quickActionsAdapter.mData.find { it.id == id } == null) {
            if (id == "custom") {
                // handle custom separetely
                val dialogSettings = IconDialogSettings.Builder()
                dialogSettings.dialogTitle = R.string.dlg_choose_icon_action_title
                dialogSettings.maxSelection = 1
                val iconDialog = childFragmentManager.findFragmentByTag(ICON_DIALOG_TAG) as IconDialog?
                    ?: IconDialog.newInstance(dialogSettings.build())
                iconDialog.show(childFragmentManager, ICON_DIALOG_TAG)
                return
            }
            getActionFromId(id) { getService() }?.let {
                quickActionsAdapter.mData.add(it)
                quickActionsAdapter.notifyItemInserted(quickActionsAdapter.mData.size-1)
                updateQuickActionEditorItems()
                saveQuickActions(quickActionsAdapter.mData)
            }
        }
    }

    override fun onIconDialogIconsSelected(dialog: IconDialog, icons: List<Icon>) {
        if (icons.isEmpty()) {
            return
        }
        val view = LayoutInflater.from(context).inflate(R.layout.dlg_stored_command, null)
        view.findViewById<View>(R.id.etxt_input_title).visibility = View.GONE
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.stored_commands_add)
            .setView(view)
            .setNegativeButton(R.string.Cancel, null)
            .setPositiveButton(R.string.Save) { dialog1: DialogInterface?, which: Int ->
                val cmd = Ui.getValue(view, R.id.etxt_input_command)
                if (cmd.isEmpty()) {
                    Toast.makeText(context, R.string.invalid_command, Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                val iconId = icons.first().id.toString()
                val customCommand = StoredCommand(iconId, cmd)
                getActionFromId("custom_${com.openvehicles.OVMS.utils.Base64.encodeObject(customCommand)}") { getService() }?.let {
                    quickActionsAdapter.mData.add(it)
                    quickActionsAdapter.notifyItemInserted(quickActionsAdapter.mData.size-1)
                    updateQuickActionEditorItems()
                    saveQuickActions(quickActionsAdapter.mData)
                }
            }
            .create().show()
    }

    private fun initialiseTabs(carData: CarData?) {
        tabsAdapter.mData = emptyList()

        tabsAdapter.mData += HomeTab(TAB_CONTROLS, R.drawable.ic_controls_tab, getString(R.string.controls_tab_label), null)

        var climateData = ""
        if (carData?.car_temp_cabin != null && carData.car_temp_cabin.isNotEmpty()) {
            climateData += String.format("%s: %s", getString(R.string.textCABIN), carData.car_temp_cabin)
        }
        if (carData?.car_temp_ambient != null && carData.car_temp_ambient.isNotEmpty()) {
            if (climateData != "")
                climateData+= ", "
            climateData += String.format("%s: %s", getString(R.string.textAMBIENT), carData.car_temp_ambient)
        }

        tabsAdapter.mData += HomeTab(TAB_CLIMATE, R.drawable.ic_ac,
            getString(R.string.textAC).lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }, climateData)

        var consumption = (carData?.car_energyused?.minus(carData.car_energyrecd))?.times(100)?.div(carData.car_tripmeter_raw.div(10))
        if (consumption?.isNaN() == true)
            consumption = 0f
        val st = String.format(
            "%.1f Wh/%s, Regen %.1f kWh, Trip %s",
            consumption,
            carData?.car_distance_units,
            carData?.car_energyrecd?.times(10)?.let { floor(it.toDouble()) }?.div(10) ?: 0,
            carData?.car_tripmeter
        )


        var geocodedLocation = ""
        if (context != null) {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(carData!!.car_latitude, carData!!.car_longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val returnedAddress = addresses[0]
                    if (returnedAddress.thoroughfare != null)
                        geocodedLocation = "${returnedAddress.thoroughfare} ${returnedAddress.subThoroughfare ?: returnedAddress.premises ?: ""}"
                    else
                        geocodedLocation = returnedAddress.getAddressLine(0)
                }
            } catch (ignored: IOException) {
                ignored.printStackTrace()
            }
        }

        tabsAdapter.mData += HomeTab(TAB_LOCATION, R.drawable.ic_navigation, getString(R.string.Location), geocodedLocation)


        val etrFull = carData?.car_chargefull_minsremaining ?: 0
        val suffSOC = carData?.car_chargelimit_soclimit ?: 0
        val etrSuffSOC = carData?.car_chargelimit_minsremaining_soc ?: 0
        val suffRange = carData?.car_chargelimit_rangelimit_raw ?: 0
        val etrSuffRange = carData?.car_chargelimit_minsremaining_range ?: 0

        var chargingNote = emptyList<String>()
        if (suffSOC > 0 && etrSuffSOC > 0) {
            chargingNote += String.format("~%s: %d%%", String.format("%02d:%02d", etrSuffSOC / 60, etrSuffSOC % 60), suffSOC)
        }
        if (suffRange > 0 && etrSuffRange > 0) {
            chargingNote += String.format("~%s: %d%%", String.format("%02d:%02d", etrSuffRange / 60, etrSuffRange % 60), suffRange)
        }
        if (etrFull > 0) {
            chargingNote += String.format("~%s: 100%%", String.format("%02d:%02d", etrFull / 60, etrFull % 60))
        }

        tabsAdapter.mData += HomeTab(TAB_CHARGING, R.drawable.ic_charging, getString(R.string.state_charging_label), chargingNote.joinToString(separator = ", "))
        tabsAdapter.mData += HomeTab(TAB_ENERGY, R.drawable.ic_energy, getString(R.string.power_energy_description), st)

        tabsAdapter.mData += HomeTab(TAB_SETTINGS, R.drawable.ic_settings, getString(R.string.Settings), null)


        tabsAdapter.notifyDataSetChanged()
    }

    private fun initialiseBottomInfo(carData: CarData?) {
        val carModelText = findViewById(R.id.carModel) as TextView
        val carDetails = findViewById(R.id.carInformation) as TextView

        val carModel = carData?.car_type

        val carNameResource = Ui.getStringIdentifier(
            context,
            "cartype_$carModel"
        )
        carModelText.setText(if (carNameResource > 0) carNameResource else R.string.cartype_unknown)

        var carInfo = ""
        if (carData?.car_odometer?.isNotEmpty() == true) {
            carInfo += carData.car_odometer
        }
        if (carData?.car_vin?.isNotEmpty() == true) {
            carInfo += "\nVIN: ${carData.car_vin}"
        }
        if (carData?.car_firmware?.isNotEmpty() == true) {
            carInfo += "\n${getString(R.string.lb_ovms_firmware)} ${carData.car_firmware}"
        }

        if (carData?.server_firmware?.isNotEmpty() == true) {
            carInfo += "\n${getString(R.string.lb_ovms_firmware).replace("OVMS", "OVMS ${getString(R.string.Server)}")} ${carData.server_firmware}"
        }

        if (carData?.sel_server?.isNotEmpty() == true) {
            carInfo += "\n${getString(R.string.Server)}: ${carData.sel_server}"
        }

        if ((carData?.car_soh ?: 0f) > 0) {
            carInfo += "\nSOH: ${carData?.car_soh ?: 0f}%"
        }


        carDetails.text = carInfo

    }

    private fun updateQuickActions(carData: CarData?) {
        quickActionsAdapter.setCarData(carData)
        quickActionsAdapter.notifyDataSetChanged()
    }

    override fun update(carData: CarData?) {
        this.carData = carData
        setupVisualisation(carData)
        initialiseChargingCard(carData)
        initialiseQuickActions(carData)
        initialiseTabs(carData)
        initialiseBottomInfo(carData)
        initialiseCarDropDown()
    }

    override fun onServiceLoggedIn(service: ApiService?, isLoggedIn: Boolean) {
        val statusText: TextView = findViewById(R.id.carStatus) as TextView
        statusText.text = getString(if (isLoggedIn) R.string.loading else R.string.connecting)
        val statusProgressBar = findViewById(R.id.carUpdatingProgress) as CircularProgressIndicator
        statusProgressBar.visibility = View.VISIBLE
    }

    override fun onItemClick(view: View?, position: Int) {
        val tabItem = tabsAdapter.getItem(position)

        when (tabItem.tabId) {
            TAB_CONTROLS -> findNavController().navigate(R.id.action_navigation_home_to_controlsFragment)
            TAB_CLIMATE -> findNavController().navigate(R.id.action_navigation_home_to_climateFragment)
            TAB_LOCATION -> findNavController().navigate(R.id.action_navigation_home_to_mapFragment)
            TAB_CHARGING -> findNavController().navigate(R.id.action_navigation_home_to_chargingFragment)
            TAB_SETTINGS -> findNavController().navigate(R.id.action_navigation_home_to_settingsFragment)
            TAB_ENERGY -> findNavController().navigate(R.id.action_navigation_home_to_powerFragment)
        }
    }

    override fun onNavigationItemSelected(itemPosition: Int, itemId: Long): Boolean {
        return true
    }
}