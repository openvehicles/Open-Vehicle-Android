package com.openvehicles.OVMS.ui2.pages

import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
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
import com.openvehicles.OVMS.ui.utils.Ui.getDrawableIdentifier
import com.openvehicles.OVMS.ui2.MainActivityUI2
import com.openvehicles.OVMS.ui2.components.hometabs.HomeTab
import com.openvehicles.OVMS.ui2.components.hometabs.HomeTabsAdapter
import com.openvehicles.OVMS.ui2.components.quickactions.ChargingQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.ClimateQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.ClimateScheduleQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.DDT4allQuickAction
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
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.properties.Delegates
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import android.location.Address
import androidx.appcompat.view.ActionMode
import com.openvehicles.OVMS.utils.Base64

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment(), OnResultCommandListener, HomeTabsAdapter.ItemClickListener,  ActionBar.OnNavigationListener, IconDialog.Callback {
    // Footer expanded state (persisted per vehicle via pref_show_footer_<vid>)
    private var footerExpanded = true

    private var carData: CarData? = null
    private var lastPresentCarId: String? = null
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
            findViewById(R.id.modifyQuickActions).visibility = if (value) VISIBLE else View.GONE
            field = value
        }
    private var tabsItemTouchHelper: ItemTouchHelper? = null
    private var longPressHintShown = false
    private var lastFullTabs: List<HomeTab> = emptyList()
    private var parkTimeRunnable: Runnable? = null
    // Material-like tonal palettes (10 tones per base hue)
    private val tonalPalettes: List<Pair<String, IntArray>> by lazy {
        listOf(
            "Blue" to intArrayOf(0xFFE3F2FD.toInt(),0xFFBBDEFB.toInt(),0xFF90CAF9.toInt(),0xFF64B5F6.toInt(),0xFF42A5F5.toInt(),0xFF2196F3.toInt(),0xFF1E88E5.toInt(),0xFF1976D2.toInt(),0xFF1565C0.toInt(),0xFF0D47A1.toInt()),
            "Green" to intArrayOf(0xFFE8F5E9.toInt(),0xFFC8E6C9.toInt(),0xFFA5D6A7.toInt(),0xFF81C784.toInt(),0xFF66BB6A.toInt(),0xFF4CAF50.toInt(),0xFF43A047.toInt(),0xFF388E3C.toInt(),0xFF2E7D32.toInt(),0xFF1B5E20.toInt()),
            "Amber" to intArrayOf(0xFFFFF8E1.toInt(),0xFFFFECB3.toInt(),0xFFFFE082.toInt(),0xFFFFD54F.toInt(),0xFFFFCA28.toInt(),0xFFFFC107.toInt(),0xFFFFB300.toInt(),0xFFFFA000.toInt(),0xFFFF8F00.toInt(),0xFFFF6F00.toInt()),
            "Red" to intArrayOf(0xFFFFEBEE.toInt(),0xFFFFCDD2.toInt(),0xFFEF9A9A.toInt(),0xFFE57373.toInt(),0xFFEF5350.toInt(),0xFFF44336.toInt(),0xFFE53935.toInt(),0xFFD32F2F.toInt(),0xFFC62828.toInt(),0xFFB71C1C.toInt()),
            "Purple" to intArrayOf(0xFFF3E5F5.toInt(),0xFFE1BEE7.toInt(),0xFFCE93D8.toInt(),0xFFBA68C8.toInt(),0xFFAB47BC.toInt(),0xFF9C27B0.toInt(),0xFF8E24AA.toInt(),0xFF7B1FA2.toInt(),0xFF6A1B9A.toInt(),0xFF4A148C.toInt()),
            "Teal" to intArrayOf(0xFFE0F2F1.toInt(),0xFFB2DFDB.toInt(),0xFF80CBC4.toInt(),0xFF4DB6AC.toInt(),0xFF26A69A.toInt(),0xFF009688.toInt(),0xFF00897B.toInt(),0xFF00796B.toInt(),0xFF00695C.toInt(),0xFF004D40.toInt()),
            "Indigo" to intArrayOf(0xFFE8EAF6.toInt(),0xFFC5CAE9.toInt(),0xFF9FA8DA.toInt(),0xFF7986CB.toInt(),0xFF5C6BC0.toInt(),0xFF3F51B5.toInt(),0xFF3949AB.toInt(),0xFF303F9F.toInt(),0xFF283593.toInt(),0xFF1A237E.toInt()),
            "Cyan" to intArrayOf(0xFFE0F7FA.toInt(),0xFFB2EBF2.toInt(),0xFF80DEEA.toInt(),0xFF4DD0E1.toInt(),0xFF26C6DA.toInt(),0xFF00BCD4.toInt(),0xFF00ACC1.toInt(),0xFF0097A7.toInt(),0xFF00838F.toInt(),0xFF006064.toInt()),
            "Pink" to intArrayOf(0xFFFCE4EC.toInt(),0xFFF8BBD0.toInt(),0xFFF48FB1.toInt(),0xFFF06292.toInt(),0xFFEC407A.toInt(),0xFFE91E63.toInt(),0xFFD81B60.toInt(),0xFFC2185B.toInt(),0xFFAD1457.toInt(),0xFF880E4F.toInt()),
            "Lime" to intArrayOf(0xFFF9FBE7.toInt(),0xFFF0F4C3.toInt(),0xFFE6EE9C.toInt(),0xFFDCE775.toInt(),0xFFD4E157.toInt(),0xFFCDDC39.toInt(),0xFFC0CA33.toInt(),0xFFAFB42B.toInt(),0xFF9E9D24.toInt(),0xFF827717.toInt())
        )
    }

    companion object {
        private const val TAG = "HomeFragment"

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
                ClimateScheduleQuickAction({null}, context),
                DDT4allQuickAction({null}, context),
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

    override fun onDestroyView() {
        // Ensure we remove any pending callbacks to avoid leaks when view is destroyed
        view?.findViewById<TextView>(R.id.carStatus)?.let { statusView ->
            parkTimeRunnable?.let { statusView.removeCallbacks(it) }
        }
        parkTimeRunnable = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appPrefs = AppPrefs(requireContext(), "ovms")

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
                updateHomeTabSettingsMenuVisibility(menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    R.id.notifications -> findNavController().navigate(R.id.action_navigation_home_to_notificationsFragment)
                    R.id.home_tab_settings -> {
                        showHiddenTabsDialog()
                    }
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
                    (activity as MainActivityUI2?)?.startSupportActionMode(object : ActionMode.Callback {

                        override fun onCreateActionMode(
                            mode: ActionMode?,
                            menu: Menu?
                        ): Boolean {
                            return true
                        }

                        override fun onPrepareActionMode(
                            mode: ActionMode?,
                            menu: Menu?
                        ): Boolean {
                            mode?.title = getString(R.string.modify_quick_actions)
                            return true
                        }

                        override fun onActionItemClicked(
                            mode: ActionMode?,
                            item: MenuItem?
                        ): Boolean {
                            return true
                        }

                        override fun onDestroyActionMode(mode: ActionMode?) {
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
                quickActionsAdapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition())
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
        tabsAdapter.setLongClickListener(object: HomeTabsAdapter.ItemLongClickListener {
            override fun onItemLongClick(position: Int) {
                val tab = tabsAdapter.getItem(position) ?: return
                if (!longPressHintShown) {
                    longPressHintShown = appPrefs.getData("home_tabs_hint_shown", "0") == "1"
                    if (!longPressHintShown) {
                        longPressHintShown = true
                        appPrefs.saveData("home_tabs_hint_shown", "1")
                        try {
                            // Use fragment root view if available, else fall back to the activity content view
                            val parentView = view ?: requireActivity().findViewById(android.R.id.content)
                            val snackbar = com.google.android.material.snackbar.Snackbar.make(
                                parentView,
                                getString(R.string.hint_long_press_hide),
                                com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
                            )
                            snackbar.setTextMaxLines(2)
                            snackbar.show()
                        } catch (e: Exception) {
                            // As a last resort fall back to a Toast if Snackbar construction fails
                            Toast.makeText(requireContext(), getString(R.string.hint_long_press_hide), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                showTabContextMenu(position, tab)
            }
        })
        homeTabsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        homeTabsRecyclerView.adapter = tabsAdapter
        // Enable drag & drop reordering for home tabs
        val tabsTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
        tabsAdapter.onRowMoved(viewHolder.adapterPosition, target.adapterPosition)
        // Persist order for current vehicle
        saveHomeTabsOrder(carData?.sel_vehicleid)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // no-op
            }

            override fun isLongPressDragEnabled(): Boolean = true
        })
        tabsTouchHelper.attachToRecyclerView(homeTabsRecyclerView)
        tabsItemTouchHelper = tabsTouchHelper

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

        // Provide color provider to tabs adapter
        tabsAdapter.setColorProvider { tab ->
            val vid = carData?.sel_vehicleid
            loadTabColor(vid, tab.tabId)
        }
    }

    private fun applyFooterVisibility() {
        val root = view ?: return
        val footerAppBlock = root.findViewById<View>(R.id.footerAppBlock)
        val restoreHint = root.findViewById<View>(R.id.footerRestoreHint)
        val vehicleId = carData?.sel_vehicleid
        val baseKey = "pref_show_footer"
        // Try vehicle specific key first, then legacy global key, default visible
        val stored = if (vehicleId != null) appPrefs.getData("${baseKey}_${vehicleId}", null) else null
        val legacy = appPrefs.getData(baseKey, null)
        val showFooter = (stored ?: legacy ?: "1") != "0"
        // We now treat showFooter as the expanded/collapsed state instead of removing the whole block.
        footerExpanded = showFooter
        footerAppBlock.post {
            setFooterExpanded(footerAppBlock, restoreHint, showFooter, animate = false)
        }

        val toggleClickListener = View.OnClickListener {
            val vId = carData?.sel_vehicleid
            footerExpanded = !footerExpanded
            if (vId != null) appPrefs.saveData("${baseKey}_${vId}", if (footerExpanded) "1" else "0") else appPrefs.saveData(baseKey, if (footerExpanded) "1" else "0")
            setFooterExpanded(footerAppBlock, restoreHint, footerExpanded, animate = false)
        }
        footerAppBlock.setOnClickListener(toggleClickListener)
        restoreHint?.setOnClickListener(toggleClickListener)
    }

    private fun setFooterExpanded(footerAppBlock: View, restoreHint: View?, expanded: Boolean, animate: Boolean) {
        // restoreHint acts as collapsed handle (↕); no animation version
        if (expanded) {
            footerAppBlock.visibility = View.VISIBLE
            footerAppBlock.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            restoreHint?.visibility = View.GONE
            populateFooterInfo(carData)
        } else {
            footerAppBlock.visibility = View.GONE
            restoreHint?.visibility = View.VISIBLE
        }
    }

    private fun populateFooterInfo(carData: CarData?) {
        if (!isAdded) return
        val root = view ?: return
        val footerBlock = root.findViewById<ViewGroup>(R.id.footerAppBlock) ?: return
        // Reuse existing TextView with tag footerInfo or create a new one
        var footerTextView: TextView? = (0 until footerBlock.childCount)
            .map { footerBlock.getChildAt(it) }
            .filterIsInstance<TextView>()
            .firstOrNull { it.tag == "footerInfo" }
        if (footerTextView == null) {
            footerTextView = TextView(requireContext())
            footerTextView.tag = "footerInfo"
            footerTextView.alpha = 0.6f
            footerTextView.setTextIsSelectable(true)
            footerBlock.addView(footerTextView, 0)
        }
        // Ensure clicking the footer info toggles expand/collapse as well
        footerTextView.setOnClickListener {
            root.findViewById<View>(R.id.footerAppBlock)?.performClick()
        }
        // Also allow toggling by clicking car model / base info above
        root.findViewById<View>(R.id.carModel)?.setOnClickListener {
            root.findViewById<View>(R.id.footerAppBlock)?.performClick()
        }
        root.findViewById<View>(R.id.carInformation)?.setOnClickListener {
            root.findViewById<View>(R.id.footerAppBlock)?.performClick()
        }
        val footerInfo = StringBuilder()
        try {
            val pi = context?.packageManager?.getPackageInfo(context?.packageName ?: "", 0)
            footerInfo.append("${getString(R.string.App)}: ${pi?.versionName} (${pi?.versionCode})")
        } catch (_: PackageManager.NameNotFoundException) { }
        if (carData?.car_firmware?.isNotEmpty() == true) {
            if (footerInfo.isNotEmpty()) footerInfo.append("\n")
            footerInfo.append("${getString(R.string.lb_ovms_firmware)} ${carData.car_firmware}")
        }
        if (carData?.server_firmware?.isNotEmpty() == true) {
            if (footerInfo.isNotEmpty()) footerInfo.append("\n")
            footerInfo.append("${getString(R.string.lb_ovms_firmware).replace("OVMS", "OVMS ${getString(R.string.Server)}")} ${carData.server_firmware}")
        }
        if (carData?.sel_server?.isNotEmpty() == true) {
            if (footerInfo.isNotEmpty()) footerInfo.append("\n")
            footerInfo.append("${getString(R.string.Server)}: ${carData.sel_server}")
        }
        if ((carData?.car_soh ?: 0f) > 0) {
            if (footerInfo.isNotEmpty()) footerInfo.append("\n")
            footerInfo.append("SOH: ${carData?.car_soh ?: 0f}%")
        }
        footerTextView.text = footerInfo.toString()
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
            row?.findViewById<ImageView>(R.id.img_car)?.setImageResource(getDrawableIdentifier(context, getItem(position)?.carData?.sel_vehicle_image))
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

        var socBattLayers = emptyList<Drawable>().toMutableList()

        val socBackground = ContextCompat.getDrawable(requireContext(), R.drawable.ic_batt_l0)
        socBackground!!.setTint(Color.GRAY)
        socBattLayers += socBackground

        // Get icon scaling offsets in display density:
        val iconBorders = TypedValue.applyDimension(COMPLEX_UNIT_DIP,6.0f, resources.displayMetrics)
        val iconOffset = TypedValue.applyDimension(COMPLEX_UNIT_DIP,2.1f, resources.displayMetrics)

        val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_batt_l1)!!.toBitmap()

        val soc = carData?.car_soc_raw ?: 0f
        val iconWidth = min(icon.height.minus(iconBorders).times(((soc / 100.0))).plus(iconOffset).roundToInt(), icon.height)
        if (iconWidth > 0) {
            val matrix = Matrix()
            matrix.postRotate(180f)
            val mBitmap =
                Bitmap.createBitmap(icon, 0, 0, icon.width, iconWidth, matrix, true)
            val layer1Drawable = mBitmap.toDrawable(resources)
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
        socText.text = when (socState) {
            1 -> getString(R.string.ideal_range_abbreviation, carData?.car_range_ideal)
            2 -> getString(R.string.estimated_range_abbreviation, carData?.car_range_estimated)
            else -> carData?.car_soc
        }

        val rangeDisplay = appPrefs.appUIPrefs.getStringSet("home_range_display_mode", HashSet<String>())
        val idealRange = rangeDisplay?.contains("ideal") == true
        val estimatedRange = rangeDisplay?.contains("estimated") == true

        if (idealRange && estimatedRange) {
            rangeText.text = "${getString(R.string.ideal_range_abbreviation, carData?.car_range_ideal)}, ${getString(R.string.estimated_range_abbreviation, carData?.car_range_estimated)}"
        } else if (idealRange || estimatedRange) {
            rangeText.text = if (idealRange) carData?.car_range_ideal else carData?.car_range_estimated
        } else {
            val socToggleListener = {
                socState += 1
                if (socState > 2)
                    socState = 0
                socText.text = when (socState) {
                    1 -> getString(R.string.ideal_range_abbreviation, carData?.car_range_ideal)
                    2 -> getString(R.string.estimated_range_abbreviation, carData?.car_range_estimated)
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
        val seconds = (now - (carData?.car_lastupdated?.time ?: 0)) / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = minutes / (60 * 24)

        if (minutes == 0L) {
            statusProgressBar.visibility = View.GONE
            // ONLINE state: differentiate Driving / Charging / Parked
            val isDriving = carData?.car_started == true
            val isCharging = carData?.car_charging == true || carData?.car_charge_state_i_raw == 14

            if (isDriving) {
                // Ensure any pending parking runnable is removed
                parkTimeRunnable?.let { statusText.removeCallbacks(it) }
                parkTimeRunnable = null
                statusText.text = carData?.car_speed
                statusText.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                statusText.setOnClickListener(null)
            } else if (isCharging) {
                parkTimeRunnable?.let { statusText.removeCallbacks(it) }
                parkTimeRunnable = null
                statusText.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
                // Charging Text is set below
            } else {
                // Parked -> apply display mode (immediate toggle on click)
                applyParkedStatus(statusText, carData)
            }

            if (isCharging) {
                statusText.setText(R.string.state_charging_label)

                val etrFull = carData?.car_chargefull_minsremaining ?: 0
                val suffSOC = carData?.car_chargelimit_soclimit ?: 0
                val etrSuffSOC = carData?.car_chargelimit_minsremaining_soc ?: 0
                val suffRange = carData?.car_chargelimit_rangelimit_raw ?: 0
                val etrSuffRange = carData?.car_chargelimit_minsremaining_range ?: 0

                var pastTime = 0L
                val chargeTime = carData!!.car_charge_duration_raw / 60
                val timestampSec = carData!!.car_charge_timestamp_sec
                val currentTimeSec = now / 1000

                if (timestampSec > 0) {
                    try {
                        pastTime = (currentTimeSec - timestampSec) / 60
                    } catch (e: Exception) {
                        pastTime = 0L
                    }
                }

                if (chargeTime > 0) {
                    if (suffSOC > 0 && etrSuffSOC > 0) {
                        statusText.text = String.format(getString(R.string.charging_estimation_soc_2), String.format("%02d:%02dh", chargeTime / 60, chargeTime % 60), String.format("%02d:%02dh", etrSuffSOC / 60, etrSuffSOC % 60))
                    } else if (suffRange > 0 && etrSuffRange > 0) {
                        statusText.text = String.format(getString(R.string.charging_estimation_range_2), String.format("%02d:%02dh", chargeTime / 60, chargeTime % 60), String.format("%02d:%02dh", etrSuffRange / 60, etrSuffRange % 60))
                    } else if (etrFull > 0) {
                        statusText.text = String.format(getString(R.string.charging_estimation_full_2), String.format("%02d:%02dh", chargeTime / 60, chargeTime % 60), String.format("%02d:%02dh", etrFull / 60, etrFull % 60))
                    } else if (etrFull == 0) {
                        statusText.text = String.format(getString(R.string.charging_estimation_full_3), String.format("%02d:%02dh", pastTime / 60L, pastTime % 60L))
                    }
                } else {
                    if (suffSOC > 0 && etrSuffSOC > 0) {
                        statusText.text = String.format(getString(R.string.charging_estimation_soc), String.format("%02d:%02dh", etrSuffSOC / 60, etrSuffSOC % 60))
                    } else if (suffRange > 0 && etrSuffRange > 0) {
                        statusText.text = String.format(getString(R.string.charging_estimation_range), String.format("%02d:%02dh", etrSuffRange / 60, etrSuffRange % 60))
                    } else if (etrFull > 0) {
                        statusText.text = String.format(getString(R.string.charging_estimation_full), String.format("%02d:%02dh", etrFull / 60, etrFull % 60))
                    }
                }

                var chargeStateInfo = 0

                when (carData!!.car_charge_state_i_raw) {
                    2 -> chargeStateInfo = R.string.state_topping_off_label
                    4 -> chargeStateInfo = R.string.state_done_label
                    14 -> chargeStateInfo = R.string.timedcharge
                    211 -> chargeStateInfo = R.string.state_stopped_label
                }

                if(carData.car_type in listOf("SQ") && (pastTime > 0L) && carData.car_charge_state_i_raw == 4) {
                    statusText.text = String.format(getString(R.string.charging_estimation_full_3), String.format("%02d:%02dh", pastTime / 60L, pastTime % 60L))
                } else if (chargeStateInfo != 0) {
                    statusText.setText(chargeStateInfo)
                }
            }
        } else {
            statusProgressBar.visibility = VISIBLE
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

        val name_splitted = carData?.sel_vehicle_image?.split("_")
        val car_tire_image1 = getDrawableIdentifier(
            context,
            name_splitted?.minus(name_splitted.last())?.joinToString("_") +"_tireanim"
        )

        var layers = emptyList<Drawable>()

        if (carData?.car_rearleftdoor_open == true || CAR_RENDER_TEST_MODE_D) {
            val modeResource = getDrawableIdentifier(
                context,
                carData?.sel_vehicle_image+"_rld"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }

        if (carData?.car_frontleftdoor_open == true || CAR_RENDER_TEST_MODE_D) {
            val modeResource = getDrawableIdentifier(
                context,
                carData?.sel_vehicle_image+"_fld"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }

        layers = layers.plus(ContextCompat.getDrawable(requireContext(), getDrawableIdentifier(
            context,
            carData?.sel_vehicle_image
        )
        )!!)

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
            val modeResource = getDrawableIdentifier(
                context,
                name_splitted?.minus(name_splitted.last())?.joinToString("_") +"_hd"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }

        if (carData?.car_rearrightdoor_open == true || CAR_RENDER_TEST_MODE_D) {
            val modeResource = getDrawableIdentifier(
                context,
                carData?.sel_vehicle_image+"_rrd"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }

        if (carData?.car_frontrightdoor_open == true || CAR_RENDER_TEST_MODE_D) {
            val modeResource = getDrawableIdentifier(
                context,
                carData?.sel_vehicle_image+"_frd"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }


        if (carData?.car_trunk_open == true || CAR_RENDER_TEST_MODE_T) {
            val modeResource = getDrawableIdentifier(
                context,
                carData?.sel_vehicle_image+"_t"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
            else {
                val modeResource = getDrawableIdentifier(
                    context,
                    name_splitted?.minus(name_splitted.last())?.joinToString("_")+"_t"
                )
                if (modeResource > 0)
                    layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
            }
        }

        if (carData?.car_bonnet_open == true || CAR_RENDER_TEST_MODE_T) {
            val modeResource = getDrawableIdentifier(
                context,
                carData?.sel_vehicle_image+"_b"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
            else {
                val modeResource = getDrawableIdentifier(
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
            val modeResource = getDrawableIdentifier(
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
            val modeResource = getDrawableIdentifier(
                context,
                chargingResName +"_cw"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }


        if (carData?.car_charge_state_i_raw == 0x01 || carData?.car_charge_state_i_raw == 0x02 || carData?.car_charge_state_i_raw == 0x0f || carData?.car_charging == true || CAR_RENDER_TEST_MODE_CHG) {
            val modeResource = getDrawableIdentifier(
                context,
                chargingResName +"_chg"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(requireContext(), modeResource)!!)
        }


        val newDrawable = LayerDrawable(layers.toTypedArray())
        if ((image.drawable as LayerDrawable?)?.numberOfLayers != newDrawable.numberOfLayers || lastPresentCarId != carData?.sel_vehicleid) {
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
            lastPresentCarId = carData?.sel_vehicleid
        } else if (speedShownInUI) {
            image.setImageDrawable(
                newDrawable
            )
        }
    }

    // Helper to show parked status & toggle on click between simple label and duration display
    private fun applyParkedStatus(statusText: TextView, carData: CarData?) {
        val showDuration = appPrefs.getData("pref_show_parktime", "0") == "1"

    // Remove any previous runnable if present
    parkTimeRunnable?.let { statusText.removeCallbacks(it) }

        if (!showDuration) {
            // Show plain "parked" label only
            statusText.setText(R.string.parked)
            statusText.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            statusText.compoundDrawablePadding = 0
            parkTimeRunnable = null
        } else {
            // Show icon + duration
            val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_p_framed)
            statusText.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
            // Space between icon and text = width of two spaces
            try {
                val twoSpacesWidth = statusText.paint.measureText("  ") // two spaces
                statusText.compoundDrawablePadding = twoSpacesWidth.toInt()
            } catch (_: Exception) {
                statusText.compoundDrawablePadding = (statusText.resources.displayMetrics.density * 8).toInt() // Fallback ~8dp
            }

            // Server-provided elapsed parking time in seconds (authoritative) -> convert to ms
            val baseElapsedMs = (carData?.car_parking_timer_raw ?: 0L)

            fun formatDuration(seconds: Long): String {
                val totalMin = seconds / 60
                val totalHours = totalMin / 60
                val days = (totalHours / 24).toInt()
                val hours = (totalHours % 24).toInt()
                val minutes = (totalMin % 60).toInt()

                return when {
                    days > 0 -> {
                        val dPart = resources.getQuantityString(R.plurals.duration_days, days, days)
                        val hPart = if (hours > 0) " " + resources.getQuantityString(R.plurals.duration_hours, hours, hours) else ""
                        val mPart = if (minutes > 0) " " + resources.getQuantityString(R.plurals.duration_minutes, minutes, minutes) else ""
                        dPart + hPart + mPart
                    }
                    hours > 0 -> {
                        val hPart = resources.getQuantityString(R.plurals.duration_hours, hours, hours)
                        val mPart = if (minutes > 0) " " + resources.getQuantityString(R.plurals.duration_minutes, minutes, minutes) else ""
                        hPart + mPart
                    }
                    else -> resources.getQuantityString(R.plurals.duration_minutes, minutes, minutes)
                }
            }

            // Single update (no live ticking) – display exactly what server reports
            val updateRunnable = Runnable {
                statusText.text = formatDuration(baseElapsedMs)
            }
            parkTimeRunnable = updateRunnable
            updateRunnable.run()
        }

    // Click toggles immediately & reapplies display
        statusText.setOnClickListener {
            val newVal = if (showDuration) "0" else "1"
            appPrefs.saveData("pref_show_parktime", newVal)
            // Reapply with new preference value
            applyParkedStatus(statusText, carData)
        }
    }

    /**
     * initialiseChargingCard: Initialise charging card data
     */
    private fun initialiseChargingCard(carData: CarData?) {
        val chargingCard = findViewById(R.id.chargingCard) as MaterialCardView
        // Nissan Leaf shows charging port as always open when parked
        val chargePortOpen = if (carData?.car_type == "NL") (carData.car_charging || carData.car_charge_timer) else carData?.car_chargeport_open == true
        chargingCard.visibility = if (chargePortOpen == true) VISIBLE else View.GONE

        val ampLimitSlider = findViewById(R.id.seekBar) as RangeSlider

        if ((carData?.car_charge_currentlimit_raw ?: 0f) > 31f) {
            // increase limit of seekbar
            ampLimitSlider.valueTo = carData?.car_charge_currentlimit_raw?.plus(12f) ?: 32f
        }

        if (chargePortOpen != true) return

        val chargingCardTitle = findViewById(R.id.chargingStatus) as TextView
        val chargingCardSubtitle = findViewById(R.id.chargingStats) as TextView

        chargingCardTitle.text = carData?.car_charge_mode?.uppercase()

        var chargingPower = 0.0
        if ((carData?.car_charge_power_input_kw_raw ?: 0f) > 0) {
            chargingPower = carData!!.car_charge_power_input_kw_raw.toDouble()
        }
        else if ((carData?.car_charge_power_kw_raw ?: 0.0) > 0) {
            chargingPower = carData!!.car_charge_power_kw_raw
        }
        else if (carData?.car_charge_linevoltage_raw != null) {
            // Divide by -1000, because current is negative when charging
            chargingPower =
                (carData.car_charge_linevoltage_raw.toDouble() * carData.car_charge_current_raw.toDouble()) / -1000.0
        }

        // Amp limit and slider
        val ampLimit = findViewById(R.id.ampLimit) as TextView
        var chargeLimitActionTitle= R.string.lb_charger_confirm_amp_change

        when (carData?.car_type) {
            "RT" -> {
                // Twizy charge power levels:
                val levelLabels = resources.getStringArray(R.array.twizy_charge_power_limits)
                val powerLevel = carData.car_charge_currentlimit_raw.div(5).toInt()
                if (powerLevel >= 0 && powerLevel < levelLabels.size)
                    ampLimit.text = levelLabels[powerLevel]
                else
                    ampLimit.text = levelLabels[0]
                ampLimit.minimumWidth = TypedValue.applyDimension(COMPLEX_UNIT_DIP,120f, resources.displayMetrics).toInt()
                ampLimitSlider.valueFrom = 0f
                ampLimitSlider.valueTo = 35f
                ampLimitSlider.stepSize = 5f
                ampLimitSlider.setValues(carData.car_charge_currentlimit_raw)
            }
            "SQ" -> {
                // EQ charge SoC limit
                ampLimitSlider.valueFrom = 0f
                ampLimitSlider.valueTo = 100f
                if ((carData?.car_chargelimit_soclimit ?: 0) > 0) {
                    ampLimit.text = String.format("%d%%",carData.car_chargelimit_soclimit)
                    ampLimitSlider.setValues((carData.car_chargelimit_soclimit).toFloat())
                } else {
                    ampLimit.text = "100%"
                    ampLimitSlider.setValues(100f)
                }
                chargeLimitActionTitle = R.string.lb_charger_confirm_soc_change
            }
            else -> {
                ampLimit.text = carData?.car_charge_currentlimit
                ampLimitSlider.valueFrom = 1.0f
                if ((carData?.car_charge_currentlimit_raw ?: 0f) > 31f) {
                    // increase limit of seekbar
                    ampLimitSlider.valueTo = carData?.car_charge_currentlimit_raw?.plus(12f) ?: 32f
                }
                ampLimitSlider.setValues(carData?.car_charge_currentlimit_raw ?: 1f)
                if (ampLimitSlider.values.first() < 1.0f)
                    ampLimitSlider.values = listOf(1.0f)
            }
        }

        val touchListener: RangeSlider.OnSliderTouchListener = object :
            RangeSlider.OnSliderTouchListener {

            override fun onStartTrackingTouch(slider: RangeSlider) {
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle(chargeLimitActionTitle)
                    .setNegativeButton(R.string.Cancel) {_, _ ->}
                    .setPositiveButton(android.R.string.ok, fun(dlg: DialogInterface, which: Int) {
                        if (carData?.car_type == "RT") {
                            // CMD_SetChargeAlerts(<range>,<soc>,<powerlevel>,<stopmode>)
                            sendCommand(
                                R.string.msg_setting_charge_c,
                                String.format("204,%d,%d,%d,%d",
                                    carData.car_chargelimit_rangelimit_raw,
                                    carData.car_chargelimit_soclimit,
                                    ampLimitSlider.values.first().div(5).toInt(),
                                    carData.car_charge_mode_i_raw),
                                this@HomeFragment
                            )
                        } else if (carData?.car_type == "SQ") {
                            sendCommand(
                                R.string.lb_sufficient_soc,
                                String.format(
                                    "204,%d",
                                    ampLimitSlider.values.first().toInt()),
                                this@HomeFragment
                            )
                        } else {
                            sendCommand(
                                R.string.msg_setting_charge_c,
                                String.format("15,%d", ampLimitSlider.values.first().toInt()),
                                this@HomeFragment
                            )
                        }
                        dlg.dismiss()
                    })
                    .show()
            }
        }

        ampLimitSlider.clearOnSliderTouchListeners()
        ampLimitSlider.addOnSliderTouchListener(touchListener)

        ampLimitSlider.clearOnChangeListeners()
        ampLimitSlider.addOnChangeListener { slider, value, fromUser ->
            when (carData?.car_type) {
                "RT" -> {
                    // Twizy charge power levels:
                    val levelLabels = resources.getStringArray(R.array.twizy_charge_power_limits)
                    ampLimit.text = levelLabels[value.div(5).toInt()]
                }

                "SQ" -> {
                    // EQ charge SoC limit
                    ampLimit.text = "${value.toInt()}%"
                }

                else -> {
                    ampLimit.text = "${value.toInt()}A"
                }
            }
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

        // adjust charging card according to car_type
        when (carData?.car_type) {
            "SQ" -> {
                // hide charge start/stop button
                action1.visibility = View.GONE
                action2.visibility = View.GONE
                // set Card title and subtitle
                val consumed = carData?.car_charge_kwhconsumed ?: 0f
                val powerInput = carData?.car_charge_power_input_kw_raw ?: 0f
                val lineVoltage = carData?.car_charge_linevoltage ?: "N/A"
                val current = carData?.car_charge_current ?: "N/A"
                val efficiency = carData?.car_charger_efficiency ?: 0f
                chargingCardTitle.text = "${getString(R.string.chargingpower)}:  ⚡${powerInput}kW"
                chargingCardSubtitle.text = "▾${consumed}kWh,  $lineVoltage,  $current,  ${getString(R.string.chargingeff)}: ⚡${efficiency}%"
            }
            else -> {
                // show charge start/stop button
                action1.visibility = VISIBLE
                action2.visibility = VISIBLE
                // set Card title and subtitle
                val lineVoltage = carData?.car_charge_linevoltage ?: "N/A"
                val current = carData?.car_charge_current ?: "N/A"
                val batteryTemp = carData?.car_temp_battery ?: "N/A"
                chargingCardSubtitle.text = "${"%.2f".format(chargingPower)} kW, $lineVoltage, $current, Battery: $batteryTemp"
            }
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
            Log.d(TAG, "initialiseQuickActions: loaded config for ${carData?.sel_vehicleid}: ${savedQuickActionConfig.contentDeepToString()}")
            if (savedQuickActionConfig.isEmpty()) {
                // Get default config and save most optimised version for the vehicle
                val defaultIdConfig: Array<String> = when (carData?.car_type) {
                    "RT" -> arrayOf("charging", "valet", "rt_profile_-1", "rt_profile_0", "rt_profile_1", "rt_profile_2")
                    "SQ" -> arrayOf("lock", "climate", "climateschedule", "wakeup", "ddt4all")
                    else -> arrayOf("lock", "charging", "valet", "wakeup", "hl_1", "hl_2", "hl_3")
                }
                Log.d(TAG, "initialiseQuickActions: init config for ${carData?.sel_vehicleid}: ${defaultIdConfig.contentDeepToString()}")
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
            ClimateScheduleQuickAction.ACTION_ID -> ClimateScheduleQuickAction(apiServiceGetter)
            DDT4allQuickAction.ACTION_ID -> DDT4allQuickAction(apiServiceGetter)
            else -> {
                if (id.startsWith("rt_profile_")) {
                    return when (id) {
                        "rt_profile_-1" -> TwizyDriveModeDefaultQuickAction(apiServiceGetter)
                        "rt_profile_0" -> TwizyDriveMode1QuickAction(apiServiceGetter)
                        "rt_profile_1" -> TwizyDriveMode2QuickAction(apiServiceGetter)
                        "rt_profile_2" -> TwizyDriveMode3QuickAction(apiServiceGetter)
                        else -> null
                    }
                }
                if (id.startsWith("hl_")) {
                    return when (id) {
                        "hl_0" -> Homelink1QuickAction(apiServiceGetter)
                        "hl_1" -> Homelink2QuickAction(apiServiceGetter)
                        "hl_2" -> Homelink3QuickAction(apiServiceGetter)
                        else -> null
                    }
                }
                if (id.startsWith("custom_")) {
                    try {
                        val customCommand: StoredCommand? = Base64.decodeToObject(id.removePrefix("custom_"), 0, StoredCommand::class.java.classLoader) as StoredCommand?
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
                getActionFromId("custom_${Base64.encodeObject(customCommand)}") { getService() }?.let {
                    quickActionsAdapter.mData.add(it)
                    quickActionsAdapter.notifyItemInserted(quickActionsAdapter.mData.size-1)
                    updateQuickActionEditorItems()
                    saveQuickActions(quickActionsAdapter.mData)
                }
            }
            .create().show()
    }

    private fun initialiseTabs(carData: CarData?) {
        val newTabsList = mutableListOf<HomeTab>()
        tabsAdapter.mData.clear()
        lifecycleScope.launch {
            // TPMS Tab (synchron)
            var tpms = ""
            if(appPrefs.getData("showtpmscontrol", "off") == "on"){
                var pressure = carData?.car_tpms_pressure
                if (pressure != null && pressure.size == 4) {
                    tpms = String.format(
                        "%s %s | %s %s\n%s %s | %s %s",
                        getString(R.string.fl_tpms),
                        pressure?.get(
                            (appPrefs.getData("tpms_fl_" + carData?.sel_vehicleid, "0")!!.toInt())
                        ) ?: "",
                        getString(R.string.fr_tpms),
                        pressure?.get(
                            (appPrefs.getData("tpms_fr_" + carData?.sel_vehicleid, "1")!!.toInt())
                        ) ?: "",
                        getString(R.string.rl_tpms),
                        pressure?.get(
                            (appPrefs.getData("tpms_rl_" + carData?.sel_vehicleid, "2")!!.toInt())
                        ) ?: "",
                        getString(R.string.rr_tpms),
                        pressure?.get(
                            (appPrefs.getData("tpms_rr_" + carData?.sel_vehicleid, "3")!!.toInt())
                        ) ?: ""
                    )
                }
                if (pressure != null && pressure.size == 2) {
                    tpms = String.format(
                        "%s %s | %s %s",
                        getString(R.string.front_tpms),
                        pressure?.get(
                            (appPrefs.getData("tpms_fl_" + carData?.sel_vehicleid, "0")!!.toInt())
                        ) ?: "",
                        getString(R.string.rear_tpms),
                        pressure?.get(
                            (appPrefs.getData("tpms_fr_" + carData?.sel_vehicleid, "1")!!.toInt())
                        ) ?: ""
                    )
                }
            }

            newTabsList.add(HomeTab(TAB_CONTROLS, R.drawable.ic_controls_tab, getString(R.string.controls_tab_label), tpms))

            // Skip Climate Control for vehicles not supporting any:
            if (carData?.car_type !in listOf("RT")) {
                var climateData = ""
                if (carData?.car_temp_cabin != null && carData.car_temp_cabin.isNotEmpty()) {
                    climateData += String.format(
                        "%s: %s",
                        getString(R.string.textCABIN).lowercase().replaceFirstChar { it.titlecase() },
                        carData?.car_temp_cabin
                    )
                }
                if (carData?.car_temp_ambient != null && carData.car_temp_ambient.isNotEmpty()) {
                    if (climateData != "")
                        climateData += ", "
                    climateData += String.format(
                        "%s: %s",
                        getString(R.string.textAMBIENT).lowercase().replaceFirstChar { it.titlecase() },
                        carData.car_temp_ambient
                    )
                }

                newTabsList.add(
                    HomeTab(
                    TAB_CLIMATE,
                    R.drawable.ic_ac,
                    getString(R.string.textAC).lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    climateData
                    )
                )
            }

        // Launch a coroutine to handle geocoding in the background
            val geocodedLocation = getGeocodedAddress(carData) // Call our new suspend function

            // This block will execute on the Main thread after getGeocodedAddress completes
            newTabsList.add(
                HomeTab(
                    TAB_LOCATION,
                    R.drawable.ic_navigation,
                    getString(R.string.Location),
                    geocodedLocation
                )
            )

            val etrFull = carData?.car_chargefull_minsremaining ?: 0
            val suffSOC = carData?.car_chargelimit_soclimit ?: 0
            val etrSuffSOC = carData?.car_chargelimit_minsremaining_soc ?: 0
            val suffRange = carData?.car_chargelimit_rangelimit_raw ?: 0
            val etrSuffRange = carData?.car_chargelimit_minsremaining_range ?: 0

            var chargingNote = emptyList<String>()
            if (suffSOC > 0 && etrSuffSOC > 0) {
                chargingNote += String.format("~%s: %d%%", String.format("%02d:%02dh", etrSuffSOC / 60, etrSuffSOC % 60), suffSOC)
            }
            if (suffRange > 0 && etrSuffRange > 0) {
                chargingNote += String.format("~%s: %d%s", String.format("%02d:%02dh", etrSuffRange / 60, etrSuffRange % 60), suffRange, carData?.car_distance_units)
            }
            if (etrFull > 0) {
                chargingNote += String.format("~%s: 100%%", String.format("%02d:%02dh", etrFull / 60, etrFull % 60))
            }
            newTabsList.add(HomeTab(TAB_CHARGING, R.drawable.ic_charging, getString(R.string.charging_tab_label), chargingNote.joinToString(separator = ", ")))

            // Re-calculate energyTabDesc here if needed, or ensure it's accessible
            var consumption = (carData?.car_energyused?.minus(carData.car_energyrecd))?.times(1000)?.div(carData.car_tripmeter_raw.div(10)) ?: 0f
            if (!consumption.isFinite())
                consumption = 0f
            val regenPercentage =
                if ((carData?.car_energyused ?: 0f) > 0f)
                    (carData?.car_energyrecd?.div(carData.car_energyused)?.times(100f)) ?: 0f
                else if ((carData?.car_energyrecd ?: 0f) > 0f) 100f
                else 0f
            val energyTabDesc = if(carData?.car_type in listOf("SQ")) {
                String.format(
                    "%.1f Wh/%s, Con %.1f kWh, Regen %.1f kWh\nTrip %s, 12V Batt %sV",
                    consumption,
                    carData?.car_distance_units,
                    carData?.car_energyused ?: 0.0f,
                    carData?.car_energyrecd ?: 0.0f,
                    carData?.car_tripmeter ?: "N/A",
                    carData?.car_12vline_voltage ?: 0.0f
                )
            } else {
                String.format(
                    "Trip %s, %.0f Wh/%s, Regen %.0f%%",
                    carData?.car_tripmeter ?: "N/A",
                    consumption, carData?.car_distance_units,
                    regenPercentage
                )
            }
            newTabsList.add(HomeTab(TAB_ENERGY, R.drawable.ic_energy, getString(R.string.power_energy_description), energyTabDesc))

            newTabsList.add(HomeTab(TAB_SETTINGS, R.drawable.ic_settings, getString(R.string.Settings), null))

            // Keep a snapshot of the complete (unfiltered) list for the More dialog
            lastFullTabs = newTabsList.toList()

            // hidden tabs filtering & More tab
            val vehicleId = carData?.sel_vehicleid
            val hiddenIds = (appPrefs.getData("home_tabs_hidden_$vehicleId", "") ?: "")
                .split(",").filter { it.isNotBlank() }.mapNotNull { it.toIntOrNull() }.toSet()
            val visibleList = newTabsList.filter { !hiddenIds.contains(it.tabId) }.toMutableList()
            // hidden count no longer shown as a special tab; managed via gear menu

            // Apply persisted order if available
            val ordered = loadHomeTabsOrder(carData?.sel_vehicleid, visibleList)
            tabsAdapter.mData.clear()
            tabsAdapter.mData.addAll(ordered)
            tabsAdapter.notifyDataSetChanged() // Notify adapter after all tabs are potentially added
            activity?.invalidateOptionsMenu()
        }
    }

    private fun saveHomeTabsOrder(vehicleId: String?) {
        if (vehicleId.isNullOrBlank()) return
        val ids = tabsAdapter.mData.map { it.tabId }.joinToString(",")
        appPrefs.saveData("home_tabs_order_$vehicleId", ids)
    }

    private fun loadHomeTabsOrder(vehicleId: String?, generated: List<HomeTab>): List<HomeTab> {
        if (vehicleId.isNullOrBlank()) return generated
        val stored = appPrefs.getData("home_tabs_order_$vehicleId", "")
        if (stored.isNullOrBlank()) return generated
        val idOrder = stored.split(",").mapNotNull { it.toIntOrNull() }
        if (idOrder.isEmpty()) return generated
        val map = generated.associateBy { it.tabId }
        val ordered = mutableListOf<HomeTab>()
        idOrder.forEach { id -> map[id]?.let { ordered.add(it) } }
        // Append any new tabs not yet stored
        generated.forEach { if (ordered.find { o -> o.tabId == it.tabId } == null) ordered.add(it) }
        return ordered
    }

    private fun updateHomeTabSettingsMenuVisibility(menu: Menu) {
        // Determine if there are hidden tabs for current vehicle
        val vehicleId = carData?.sel_vehicleid
        val hiddenIds = (appPrefs.getData("home_tabs_hidden_$vehicleId", "") ?: "")
            .split(",").filter { it.isNotBlank() }.mapNotNull { it.toIntOrNull() }
        val item = menu.findItem(R.id.home_tab_settings)
        item?.isVisible = hiddenIds.isNotEmpty()
    }

    private fun initialiseBottomInfo(carData: CarData?) {
        val carModelText = findViewById(R.id.carModel) as TextView
        val carDetails = findViewById(R.id.carInformation) as TextView

        val carModel = carData?.car_type ?: "UNKNOWN"

        // Model map from module command "vehicle list":
        val modelMap = mapOf(
            "ATTO3"           to "BYD Atto 3",
            "BMWI3"           to "BMW i3, i3s",
            "C2CTS"           to "Cadillac 2nd gen CTS",
            "C6CORVETTE"      to "Chevrolet C6 Corvette",
            "CBOLT"           to "Chevrolet Bolt EV/Ampera-e",
            "DBC"             to "DBC-based Vehicle",
            "DEMO"            to "Demonstration Vehicle",
            "FT5E"            to "Fiat 500e",
            "HIONVFL"         to "Hyundai Ioniq vFL",
            "I5"              to "Hyundai Ioniq 5 EV/KIA EV6",
            "JLRI"            to "Jaguar Ipace",
            "KN"              to "Kia Niro / Hyundai Kona EV",
            "KS"              to "Kia Soul EV",
            "MB"              to "Mercedes-Benz B250E, W242",
            "ME56"            to "Maxus Euniq 5 6-seats",
            "ME6"             to "Maxus Euniq 6",
            "MED3"            to "Maxus eDeliver3",
            "MG4"             to "MG 4",
            "MG5"             to "MG 5 (2020-2023)",
            "MGA"             to "MG ZS EV (UK/EU)",
            "MGB"             to "MG ZS EV (AU/TH)",
            "MGD"             to "MG ZS EV (SR) (2023-)",
            "MI"              to "Trio",
            "MINISE"          to "Mini Cooper SE",
            "MPL60S"          to "Maple 60S",
            "NEVO"            to "NIU MQi GT EVO/100",
            "NL"              to "Nissan Leaf",
            "NONE"            to "Empty Vehicle",
            "NR"              to "Energica",
            "O2"              to "OBDII",
            "RT"              to "Renault Twizy",
            "RZ"              to "Renault Zoe/Kangoo",
            "RZ2"             to "Renault Zoe Ph2",
            "SE"              to "Smart ED 3.Gen",
            "SQ"              to "smart 453 4.Gen",
            "T3"              to "Tesla Model 3",
            "TGTC"            to "Think City",
            "TR"              to "Tesla Roadster",
            "TS"              to "Tesla Model S",
            "TYR4"            to "Toyota RAV4 EV",
            "VA"              to "Chevrolet Volt/Ampera",
            "VWUP"            to "VW e-Up",
            "XX"              to "TRACK",
            "ZEVA"            to "ZEVA BMS",
            "ZOM"             to "ZOMBIE VCU",
            )
        // Find best type prefix fit:
        var matchLength = 0
        var modelName = ""
        modelMap.forEach{ (type, name) ->
            if (carModel.startsWith(type) && type.length > matchLength) {
                matchLength = type.length
                modelName = name
            }
        }
        if (matchLength == 0)
            modelName = String.format(getString(R.string.cartype_unknown), carModel)

        carModelText.text = modelName

        // Base car info (always visible above collapsible footer)
        val baseInfoBuilder = StringBuilder()
        if (carData?.car_odometer?.isNotEmpty() == true) baseInfoBuilder.append(carData.car_odometer)

        if (carData?.car_gsmlock?.isNotEmpty() == true) {
            if (baseInfoBuilder.isNotEmpty()) baseInfoBuilder.append("\n")
            baseInfoBuilder.append("GSM: ${carData.car_gsmlock} ${carData.car_mdm_mode}")
        }

        if (carData?.car_vin?.isNotEmpty() == true) {
            if (baseInfoBuilder.isNotEmpty()) baseInfoBuilder.append("\n")
            baseInfoBuilder.append("VIN: ${carData.car_vin}\n")
        }
        carDetails.text = baseInfoBuilder.toString()

        // Populate collapsible footer content (app / firmware / server / SOH)
        applyFooterVisibility()
    }

    private fun updateQuickActions(carData: CarData?) {
        quickActionsAdapter.setCarData(carData)
        quickActionsAdapter.notifyDataSetChanged()
    }

    private fun gsmicon(carData: CarData?) {
        // GSM Bar
        if (appPrefs.getData("gsm_icon", "off") == "on") {
            val gsmimg = findViewById(R.id.gsmView) as ImageView
            gsmimg.visibility = VISIBLE
            gsmimg.setImageResource(
                getDrawableIdentifier(
                    activity,
                    "signal_strength_" + carData?.car_gsm_bars
                )
            )
        }
    }

    private fun gpsicon(carData: CarData?) {
        // GPS Bar
        if (appPrefs.getData("gps_icon", "off") == "on") {
            val gpsimg = findViewById(R.id.gpsView) as ImageView
            gpsimg.visibility = if(carData?.car_gpslock == true) VISIBLE else View.INVISIBLE
        }
    }

    override fun update(carData: CarData?) {
        Log.d(TAG, "update: lastupdated=" + carData?.car_lastupdated)
        this.carData = carData
        setupVisualisation(carData)
        initialiseChargingCard(carData)
        initialiseQuickActions(carData)
        initialiseTabs(carData)
        initialiseBottomInfo(carData)
        initialiseCarDropDown()
        gsmicon(carData)
        gpsicon(carData)
    }

    override fun onServiceLoggedIn(service: ApiService?, isLoggedIn: Boolean) {
        Log.d(TAG, "onServiceLoggedIn: isLoggedIn=" + isLoggedIn)
        val statusText: TextView = findViewById(R.id.carStatus) as TextView
        val statusProgressBar = findViewById(R.id.carUpdatingProgress) as CircularProgressIndicator
        if (!isLoggedIn) {
            statusText.text = getString(R.string.connecting)
            statusProgressBar.visibility = VISIBLE
        } else {
            update(carData)
        }
    }

    override fun onItemClick(view: View?, position: Int) {
        if (showEditAction) {
            return
        }
        val tabItem = tabsAdapter.getItem(position)

        when (tabItem?.tabId) {
            TAB_CONTROLS -> findNavController().navigate(R.id.action_navigation_home_to_controlsFragment)
            TAB_CLIMATE -> findNavController().navigate(R.id.action_navigation_home_to_climateFragment)
            TAB_LOCATION -> findNavController().navigate(R.id.action_navigation_home_to_mapFragment)
            TAB_CHARGING -> findNavController().navigate(R.id.action_navigation_home_to_chargingFragment)
            TAB_SETTINGS -> findNavController().navigate(R.id.action_navigation_home_to_settingsFragment)
            TAB_ENERGY -> findNavController().navigate(R.id.action_navigation_home_to_energyFragment)
        }
    }

    private fun toggleTabHidden(tab: HomeTab) {
        val vehicleId = carData?.sel_vehicleid ?: return
        val key = "home_tabs_hidden_$vehicleId"
        val current = appPrefs.getData(key, "") ?: ""
        val set = current.split(",").filter { it.isNotBlank() }.toMutableSet()
        if (set.contains(tab.tabId.toString())) set.remove(tab.tabId.toString()) else set.add(tab.tabId.toString())
        appPrefs.saveData(key, set.joinToString(","))
        initialiseTabs(carData)
    }

    private fun showTabContextMenu(position: Int, tab: HomeTab) {
        val recycler = view?.findViewById<RecyclerView>(R.id.menuItems) ?: return
        val vh = recycler.findViewHolderForAdapterPosition(position) ?: return
        val anchor = vh.itemView
        val popup = android.widget.PopupMenu(requireContext(), anchor)
        val vehicleId = carData?.sel_vehicleid
        val hidden = isTabHidden(tab.tabId, vehicleId)
        popup.menu.add(0, 1, 0, if (hidden) R.string.action_unhide_tab else R.string.action_hide_tab)
        popup.menu.add(0, 2, 1, R.string.action_reset_tabs)
        popup.menu.add(0, 4, 3, R.string.action_set_tab_color)
        popup.menu.add(0, 5, 4, R.string.action_reset_tab_color)
        popup.menu.add(0, 3, 2, R.string.drag_handle_description)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                1 -> toggleTabHidden(tab)
                2 -> vehicleId?.let { vid -> resetTabsForVehicle(vid) }
                4 -> showColorChooser(tab)
                5 -> {
                    vehicleId?.let { vid -> saveTabColor(vid, tab.tabId, null) }
                    tabsAdapter.notifyItemChanged(position)
                }
                3 -> tabsItemTouchHelper?.startDrag(vh)
            }
            true
        }
        popup.show()
    }

    private fun showColorChooser(tab: HomeTab) {
        val context = requireContext()
        val density = resources.displayMetrics.density
        val scroll = android.widget.ScrollView(context).apply {
            isFillViewport = true
        }
        val container = android.widget.LinearLayout(context).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding((8*density).toInt(), (8*density).toInt(), (8*density).toInt(), (8*density).toInt())
        }
        scroll.addView(container)

        val corner = 12f
        val currentSelectedColor = loadTabColor(carData?.sel_vehicleid, tab.tabId)
    tonalPalettes.forEach { (name, tones) ->
            // Optional label
            val label = TextView(context).apply {
                text = name
                alpha = 0.8f
                setPadding((4*density).toInt(), (6*density).toInt(), 0, (2*density).toInt())
            }
            container.addView(label)
            val rowScroll = android.widget.HorizontalScrollView(context).apply {
                isHorizontalScrollBarEnabled = false
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
            val row = android.widget.LinearLayout(context).apply {
                orientation = android.widget.LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding((4*density).toInt(), 0, (4*density).toInt(), 0)
            }
            // Buttons will be wired after dialog is built to allow dismiss()
            tones.forEach { colorInt ->
                val v = View(context)
                val size = (40 * density).toInt()
                val lp = ViewGroup.MarginLayoutParams(size, size)
                lp.setMargins((8*density).toInt(), (8*density).toInt(), (8*density).toInt(), (8*density).toInt())
                v.layoutParams = lp
                v.background = com.google.android.material.shape.MaterialShapeDrawable().apply {
                    fillColor = android.content.res.ColorStateList.valueOf(colorInt)
                    setCornerSize(corner)
                    if (currentSelectedColor != null && currentSelectedColor == colorInt) {
                        val strokeColor = com.google.android.material.color.MaterialColors.getColor(v, com.google.android.material.R.attr.colorPrimary)
                        setStroke(2f * density, strokeColor)
                    }
                }
                v.tag = colorInt
                row.addView(v)
            }
            rowScroll.addView(row)
            container.addView(rowScroll)
        }
        val dialogRef = MaterialAlertDialogBuilder(context)
            .setTitle(R.string.dialog_choose_color)
            .setView(scroll)
            .setNeutralButton(R.string.reset) { dlg, _ ->
                val vid = carData?.sel_vehicleid
                if (vid != null) {
                    saveTabColor(vid, tab.tabId, null)
                    tabsAdapter.notifyDataSetChanged()
                }
                dlg.dismiss()
            }
            .setNegativeButton(R.string.Cancel, null)
            .create()
        dialogRef.show()
        // Now that dialog exists, wire click listeners to dismiss on pick
        for (i in 0 until container.childCount) {
            val child = container.getChildAt(i)
            val rowLayout: android.widget.LinearLayout? = when (child) {
                is android.widget.HorizontalScrollView -> child.getChildAt(0) as? android.widget.LinearLayout
                is android.widget.LinearLayout -> child
                else -> null
            }
            rowLayout?.let { rowLL ->
                for (j in 0 until rowLL.childCount) {
                    val swatch = rowLL.getChildAt(j)
                    swatch?.setOnClickListener { v ->
                        val colorInt = (v.tag as? Int) ?: return@setOnClickListener
                        val vid = carData?.sel_vehicleid ?: return@setOnClickListener
                        saveTabColor(vid, tab.tabId, colorInt)
                        tabsAdapter.notifyDataSetChanged()
                        dialogRef.dismiss()
                    }
                }
            }
        }
    }

    private fun saveTabColor(vehicleId: String, tabId: Int, color: Int?) {
        val key = "home_tab_color_${vehicleId}_$tabId"
        if (color == null) appPrefs.saveData(key, "") else appPrefs.saveData(key, String.format("#%08X", color))
    }

    private fun loadTabColor(vehicleId: String?, tabId: Int): Int? {
        if (vehicleId.isNullOrBlank()) return null
        val key = "home_tab_color_${vehicleId}_$tabId"
        val s = appPrefs.getData(key, "") ?: return null
        if (s.isBlank()) return null
        return try { Color.parseColor(s) } catch (e: Exception) { null }
    }

    private fun isTabHidden(tabId: Int, vehicleId: String?): Boolean {
        if (vehicleId.isNullOrBlank()) return false
        val current = appPrefs.getData("home_tabs_hidden_$vehicleId", "") ?: return false
        if (current.isBlank()) return false
        return current.split(",").any { it == tabId.toString() }
    }

    private fun showHiddenTabsDialog() {
        val vehicleId = carData?.sel_vehicleid ?: return
        val hiddenIds = (appPrefs.getData("home_tabs_hidden_$vehicleId", "") ?: "")
            .split(",").filter { it.isNotBlank() }.mapNotNull { it.toIntOrNull() }.toSet()
        val hiddenTabs = lastFullTabs.filter { hiddenIds.contains(it.tabId) }
        if (hiddenTabs.isEmpty()) return
        val items = hiddenTabs.map { it.tabName.ifBlank { getString(R.string.app_name) } }.toTypedArray()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.hidden_tabs)
            .setItems(items) { _, which ->
                val tab = hiddenTabs[which]
                when (tab.tabId) {
                    TAB_CONTROLS -> findNavController().navigate(R.id.action_navigation_home_to_controlsFragment)
                    TAB_CLIMATE -> findNavController().navigate(R.id.action_navigation_home_to_climateFragment)
                    TAB_LOCATION -> findNavController().navigate(R.id.action_navigation_home_to_mapFragment)
                    TAB_CHARGING -> findNavController().navigate(R.id.action_navigation_home_to_chargingFragment)
                    TAB_SETTINGS -> findNavController().navigate(R.id.action_navigation_home_to_settingsFragment)
                    TAB_ENERGY -> findNavController().navigate(R.id.action_navigation_home_to_energyFragment)
                }
            }
            .setNegativeButton(R.string.Close, null)
            .setNeutralButton(R.string.reset) { _, _ ->
                resetTabsForVehicle(vehicleId)
            }
            .show()
    }

    private fun resetTabsForVehicle(vehicleId: String) {
        appPrefs.saveData("home_tabs_order_$vehicleId", "")
        appPrefs.saveData("home_tabs_hidden_$vehicleId", "")
        initialiseTabs(carData)
    }

    private suspend fun getGeocodedAddress(carData: CarData?): String {
        if (carData == null || carData.car_latitude == 0.0 || carData.car_longitude == 0.0) { // Added check for 0.0 coords
            Log.w("Geocoding", "carData is null or coordinates are invalid.")
            return "Location data unavailable" // Or an empty string, or a specific "pending" message
        }

        // Use requireContext() as you were, assuming it's safe in this Fragment's lifecycle state
        val geocoder = Geocoder(requireContext(), Locale.getDefault())

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Asynchronous version for API 33+
            try {
                suspendCancellableCoroutine { continuation ->
                    val geocodeListener = object : Geocoder.GeocodeListener {
                        override fun onGeocode(addresses: List<Address>) {
                            if (continuation.isActive) {
                                if (addresses.isNotEmpty()) {
                                    val addr = addresses[0]
                                    val result = if (addr.thoroughfare != null) {
                                        val streetNum = addr.subThoroughfare ?: addr.premises ?: ""
                                        "${addr.thoroughfare} $streetNum".trim()
                                    } else {
                                        addr.getAddressLine(0) ?: ""
                                    }
                                    continuation.resume(result) {} // Use resume with a lambda for onCancellation
                                } else {
                                    Log.w("Geocoding", "No address found (API 33+)")
                                    continuation.resume("No address found") {}
                                }
                            }
                        }

                        override fun onError(errorMessage: String?) {
                            if (continuation.isActive) {
                                Log.e("Geocoding", "Geocoding error (API 33+): ${errorMessage ?: "Unknown error"}")
                                continuation.resume("Geocoding unavailable") {}
                            }
                        }
                    }
                    geocoder.getFromLocation(carData.car_latitude, carData.car_longitude, 1, geocodeListener)

                    continuation.invokeOnCancellation {
                        // Optional: Clean up if the coroutine is cancelled
                        // For Geocoder, there isn't a direct cancel method on the listener itself.
                    }
                }
            } catch (e: Exception) {
                Log.e("Geocoding", "Error setting up geocoding (API 33+)", e)
                "Geocoding setup error"
            }
        } else {
            // Synchronous version for pre-API 33 (run on IO dispatcher)
            withContext(Dispatchers.IO) {
                @Suppress("DEPRECATION")
                try {
                    val addresses: List<Address>? = geocoder.getFromLocation(carData.car_latitude, carData.car_longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val returnedAddress = addresses[0]
                        if (returnedAddress.thoroughfare != null) {
                            val streetNumber = returnedAddress.subThoroughfare ?: returnedAddress.premises ?: ""
                            "${returnedAddress.thoroughfare} $streetNumber".trim()
                        } else {
                            returnedAddress.getAddressLine(0) ?: "Address line unavailable"
                        }
                    } else {
                        Log.w("Geocoding", "No address found (pre-API 33)")
                        "No address found"
                    }
                } catch (e: IOException) {
                    Log.e("Geocoding", "Geocoding I/O error (pre-API 33)", e)
                    "Geocoding unavailable"
                } catch (e: Exception) {
                    Log.e("Geocoding", "Geocoding general error (pre-API 33)", e)
                    "Geocoding error"
                }
            }
        }
    }

    override fun onNavigationItemSelected(itemPosition: Int, itemId: Long): Boolean {
        return true
    }
}