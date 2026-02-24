package com.openvehicles.OVMS.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.androidmapsextensions.CircleOptions
import com.androidmapsextensions.ClusterGroup
import com.androidmapsextensions.ClusteringSettings
import com.androidmapsextensions.GoogleMap
import com.androidmapsextensions.Marker
import com.androidmapsextensions.MarkerOptions
import com.androidmapsextensions.OnMapReadyCallback
import com.androidmapsextensions.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PatternItem
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragmentActivity.Companion.show
import com.openvehicles.OVMS.ui.MapSettingsFragment.UpdateMap
import com.openvehicles.OVMS.ui.utils.Database
import com.openvehicles.OVMS.ui.utils.DemoClusterOptionsProvider
import com.openvehicles.OVMS.ui.utils.MarkerGenerator.addMarkers
import com.openvehicles.OVMS.ui.utils.Ui.getDrawableIdentifier
import com.openvehicles.OVMS.ui2.MainActivityUI2
import com.openvehicles.OVMS.utils.AppPrefs
import java.util.Arrays
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class MapFragment : BaseFragment(), GoogleMap.OnInfoWindowClickListener, GetMapDetailsListener,
    View.OnClickListener, UpdateMap, OnMapReadyCallback {

    private var map: GoogleMap? = null
    private lateinit var database: Database
    private var latitude: String? = null
    private var longitude: String? = null
    private lateinit var appPrefs: AppPrefs
    private var userInteraction = false
    private lateinit var optionsMenu: Menu
    private var autoTrackMenuItem: MenuItem? = null
    private var autoTrack = true
    private var mapZoomLevel = 15f
    private var carData: CarData? = null
    private var carPosition = LatLng(0.0, 0.0)
    private var markerList: List<Marker> = listOf()
    private val clusterSizes = doubleArrayOf(360.0, 180.0, 90.0, 45.0, 22.0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.mmap, null)
        setHasOptionsMenu(true)
        updateMap = this
        appPrefs = AppPrefs(requireActivity(), "ovms")
        database = Database(requireActivity())
        carPosition = LatLng(37.410866, -122.001946)
        maxRange = 285f
        distanceUnits = "KM"
        autoTrack = appPrefs.getData("autotrack") != "off"
        mapInitState = true
        return rootView
    }

    // getMap: Initialize the map fragment
    // 	see: http://developer.android.com/about/versions/android-4.2.html#NestedFragments
    // 	and: https://developers.google.com/android/reference/com/google/android/gms/maps/SupportMapFragment
    private fun getMap() {
        try {
            val fm = childFragmentManager
            var fragment = fm.findFragmentById(R.id.mmap) as SupportMapFragment?
            if (fragment == null) {
                Log.d(TAG, "getMap: create newInstance()")
                fragment = SupportMapFragment.newInstance()
                fm.beginTransaction().replace(R.id.mmap, fragment).commit()
            }
            Log.d(TAG, "getMap: fragment=$fragment")
            fragment!!.getExtendedMapAsync(this)
        } catch (e: Exception) {
            Log.e(TAG, "getMap:$e")
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        Log.i(TAG, "getMap/onMapReady: map=$map")
        var clusterEnabled = true
        var clusterSizeIndex = 0
        try {
            clusterEnabled = appPrefs.getData("check") != "false"
            clusterSizeIndex = appPrefs.getData("progress").toInt()
            mapZoomLevel = appPrefs.getData("mapZoomLevel").toFloat()
            if (mapZoomLevel == 0f) mapZoomLevel = 15f
        } catch (e: Exception) {
            // ignore
        }
        mapInitState = true
        updateClustering(clusterSizeIndex, clusterEnabled)
        map!!.setOnInfoWindowClickListener(this)
        map!!.uiSettings.isRotateGesturesEnabled = false // disable two-finger rotation gesture
        map!!.uiSettings.isZoomControlsEnabled = true // enable zoom +/- buttons
        map!!.uiSettings.isMapToolbarEnabled = true // enable Google Maps shortcuts
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            map!!.isMyLocationEnabled = true
            map!!.uiSettings.isMyLocationButtonEnabled = !autoTrack
            Log.i(TAG, "getMap/onMapReady: MyLocation enabled, button = " + !autoTrack)
        } else {
            Log.w(
                TAG,
                "getMap/onMapReady: MyLocation unavailable, permission FINE_LOCATION not granted"
            )
        }
        map!!.setOnMyLocationButtonClickListener(GoogleMap.OnMyLocationButtonClickListener { // move camera to car position if available:
            if (carPosition.latitude == 0.0 || carPosition.longitude == 0.0) {
                return@OnMyLocationButtonClickListener false
            }
            map!!.moveCamera(CameraUpdateFactory.newLatLng(carPosition))
            Log.i(TAG, "getMap/onMyLocationButtonClick: enabling autotrack")
            autoTrack = true
            appPrefs.saveData("autotrack", "on")
            if (autoTrackMenuItem != null) {
                autoTrackMenuItem!!.setChecked(autoTrack)
            }
            map!!.uiSettings.isMyLocationButtonEnabled = !autoTrack
            showMapToast(getString(R.string.ocm_toast_autotrack_on))
            true
        })
        Log.i(TAG, "getMap/onMapReady: mapZoomLevel=$mapZoomLevel")
        map!!.moveCamera(CameraUpdateFactory.zoomTo(mapZoomLevel)) // init zoom level
        map!!.setOnCameraChangeListener(GoogleMap.OnCameraChangeListener { cameraPosition ->
            if (mapInitState) {
                return@OnCameraChangeListener
            }
            // save zoom:
            if (cameraPosition.zoom != 0f && cameraPosition.zoom != mapZoomLevel) {
                userInteraction = true
                mapZoomLevel = cameraPosition.zoom
                appPrefs.saveData("mapZoomLevel", "" + mapZoomLevel)
                Log.i(TAG, "getMap/onCameraChange: new mapZoomLevel=" + cameraPosition.zoom)
            }
            // disable autotrack?
            if (cameraPosition.target.latitude != 0.0 && cameraPosition.target.longitude != 0.0) {
                if (autoTrack) {
                    val moved = distance(carPosition, cameraPosition.target).toInt()
                    if (moved > 10) {
                        userInteraction = true
                    }
                    if (moved > 300 * 2f.pow(15 - mapZoomLevel)) {
                        Log.i(
                            TAG,
                            "getMap/onCameraChange: moved " + moved + "m, disabling autotrack"
                        )
                        autoTrack = false
                        appPrefs.saveData("autotrack", "off")
                        if (autoTrackMenuItem != null) {
                            autoTrackMenuItem!!.setChecked(autoTrack)
                        }
                        if (map!!.isMyLocationEnabled) {
                            map!!.uiSettings.isMyLocationButtonEnabled = !autoTrack
                        }
                        showMapToast(getString(R.string.ocm_toast_autotrack_off))
                    }
                }
            }
        })
        map!!.setOnCameraIdleListener(GoogleMap.OnCameraIdleListener {
            userInteraction = false
            if (mapInitState) {
                return@OnCameraIdleListener
            }
            // fetch chargepoints for view:
            val cameraPosition = map!!.cameraPosition
            Log.i(TAG, "getMap/onCameraIdle: get charge points for " + cameraPosition.target)
            //mainActivity.startGetMapDetails(cameraPosition.target)
        })
        update()
    }

    private fun showMapToast(msg: String) {
        val text = SpannableStringBuilder(msg)
        text.setSpan(RelativeSizeSpan(1.15f), 0, text.length, 0)
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onResume() {
        super.onResume()
        getMap()
    }

    override fun onDestroyView() {
        try {
            mapInitState = true
            database.close()
        } catch (e: Exception) {
            // nop
        }
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
        optionsMenu = menu

        // set checkboxes:
        autoTrackMenuItem = optionsMenu.findItem(R.id.mi_map_autotrack)
        autoTrackMenuItem!!.setChecked(autoTrack)
        if (appPrefs.getData("option_ocm_enabled", "1") == "1") {
            optionsMenu.findItem(R.id.mi_map_filter_connections)
                .setChecked(appPrefs.getData("filter") == "on")
                .setVisible(true)
            optionsMenu.findItem(R.id.mi_map_filter_range)
                .setChecked(appPrefs.getData("inrange") == "on")
                .setVisible(true)
        } else {
            optionsMenu.findItem(R.id.mi_map_filter_connections)
                .setVisible(false)
            optionsMenu.findItem(R.id.mi_map_filter_range)
                .setVisible(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuId = item.itemId
        val newState = !item.isChecked
        if (menuId == R.id.mi_map_autotrack) {
            appPrefs.saveData("autotrack", if (newState) "on" else "off")
            item.setChecked(newState)
            autoTrack = newState
            if (autoTrack) {
                update()
            }
            if (map != null && map!!.isMyLocationEnabled) {
                Log.d(TAG, "onOptionsItemSelected: MyLocation button = " + !autoTrack)
                map!!.uiSettings.isMyLocationButtonEnabled = !autoTrack
            }
        } else if (menuId == R.id.mi_map_filter_connections) {
            appPrefs.saveData("filter", if (newState) "on" else "off")
            item.setChecked(newState)
            updateMapDetails(false)
        } else if (menuId == R.id.mi_map_filter_range) {
            appPrefs.saveData("inrange", if (newState) "on" else "off")
            item.setChecked(newState)
            updateMapDetails(false)
        } else if (menuId == R.id.mi_map_settings) {
            var baseActivity: MainActivity? = null
            try {
                baseActivity = activity as MainActivity?
            } catch (ignored: Exception) {}
            if (baseActivity == null) {
                findNavController().navigate(R.id.action_mapFragment_to_mapSettingsFragment)
                return false
            }
            show(
                requireActivity(),
                MapSettingsFragment::class.java,
                Bundle(),
                Configuration.ORIENTATION_UNDEFINED
            )
        }
        return false
    }

    override fun updateClustering(clusterSizeIndex: Int, isEnabled: Boolean) {
        Log.d(
            TAG, "getMap/updateClustering(" + clusterSizeIndex
                    + "," + isEnabled + "): map=" + map
        )
        if (map == null) {
            return
        }
        map!!.setClustering(
            ClusteringSettings()
                .clusterOptionsProvider(DemoClusterOptionsProvider(resources))
                .addMarkersDynamically(true)
                .clusterSize(clusterSizes[clusterSizeIndex])
                .enabled(isEnabled)
        )
    }

    private fun direction() {
        // TODO Auto-generated method stub
        var directions =
            "https://maps.google.com/maps?saddr=Kanyakumari,+Tamil+Nadu,+India&daddr=Trivandrum,+Kerala,+India"
        directions = ("https://maps.google.com/maps?saddr=37.410866,-122.001946&daddr="
                + latitude + "," + longitude)
        // Create Google Maps intent from current location to target location
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(directions)
        )
        intent.setClassName(
            "com.google.android.apps.maps",
            "com.google.android.maps.MapsActivity"
        )
        startActivity(intent)
    }

    // marker click event
    override fun onInfoWindowClick(marker: Marker) {
        // TODO Auto-generated method stub
        val j = marker.clusterGroup
        Log.d(TAG, "click: ClusterGroup=$j")
        if (j == 0) {
            dialog(marker)
        }
    }

    // after fetch value from server
    override fun getMapDetailsDone(isSuccess: Boolean, center: LatLng?) {
        updateMapDetails(isSuccess)
    }

    private fun updateMapDetails(clearMap: Boolean) {
        if (map == null) {
            return
        }
        Log.d(TAG, "updateMapDetails: clearmap=$clearMap")
        if (clearMap) {
            map!!.clear()
        } else {
            markerList = map!!.markers.mapNotNull { carMarker ->
                if (carMarker.clusterGroup == -1) {
                    null
                } else {
                    carMarker.remove()
                    carMarker
                }
            }
        }


        // Load charge points from database:
        var checkRange = false
        var maxRangeM = 0.0
        val cursor: Cursor = if (appPrefs.getData("filter") == "on") {
            // check if filter is defined, else fallback to all stations:
            val connectionList = appPrefs.getData("Id")
            Log.d(TAG, "updateMapDetails: connectionList=($connectionList)")
            if (connectionList != "") {
                database.getMapDetails(connectionList)
            } else {
                database.getMapDetails()
            }
        } else {
            // filter off:
            database.getMapDetails()
        }
        if (appPrefs.getData("inrange") == "on") {
            checkRange = true
            maxRangeM = if (distanceUnits == "Miles") {
                maxRange * 1.609344 * 1000
            } else {
                (maxRange * 1000).toDouble()
            }
        }
        Log.d(TAG, "updateMapDetails: addMarkers avail=" + cursor.count)
        if (cursor.count != 0) {
            val columnLatitude = cursor.getColumnIndex("Latitude")
            val columnLongitude = cursor.getColumnIndex("Longitude")
            val columnCPID = cursor.getColumnIndex("cpid")
            val columnTitle = cursor.getColumnIndex("Title")
            val columnInfo = cursor.getColumnIndex("OperatorInfo")
            var cntAdded = 0
            if (cursor.moveToFirst()) {
                do {
                    // check position:
                    try {
                        val lat = cursor.getString(columnLatitude).toDouble()
                        val lng = cursor.getString(columnLongitude).toDouble()
                        if (checkRange) {
                            if (distance(carPosition.latitude, carPosition.longitude, lat, lng) > maxRangeM) {
                                continue
                            }
                        }

                        // add marker:
                        val cpid = cursor.getString(columnCPID)
                        val title = cursor.getString(columnTitle)
                        val snippet = cursor.getString(columnInfo)
                        addMarkers(
                            map!!,
                            title, snippet,
                            LatLng(lat, lng),
                            cpid
                        )
                        cntAdded++
                    } catch (e: Exception) {
                        Log.e(TAG, "skipped charge point: ERROR", e)
                    }
                } while (cursor.moveToNext())
            }
            Log.d(TAG, "updateMapDetails: addMarkers added=$cntAdded")
        }
        cursor.close()
    }

    // click marker event
    private fun dialog(marker: Marker) {
        // open dialog:
        val args = Bundle()
        args.putString("cpId", marker.getData<Any>() as String)
        show(
            requireActivity(),
            DetailFragment::class.java,
            args,
            Configuration.ORIENTATION_UNDEFINED
        )
    }

    override fun update(carData: CarData?) {
        this.carData = carData
        update()
    }

    fun update() {
        if (carData == null || map == null || context == null) {
            return
        }

        // get last known car position:
        carPosition = LatLng(carData!!.car_latitude, carData!!.car_longitude)
        maxRange = max(
            carData!!.car_range_estimated_raw.toDouble(),
            carData!!.car_range_ideal_raw.toDouble()
        ).toFloat()
        distanceUnits = if (carData!!.car_distance_units_raw == "M") "Miles" else "KM"
        Log.i(
            TAG, "update: Car on map: " + carPosition
                    + " maxrange=" + maxRange + distanceUnits
        )

        // update charge point markers:
        updateMapDetails(true)

        // update car position marker:

        // determine icon for this car:
        val icon: Int = if (carData!!.sel_vehicle_image.startsWith("car_imiev_")) R.drawable.map_car_imiev // one map icon for all colors
        else if (carData!!.sel_vehicle_image.startsWith("car_i3_")) R.drawable.map_car_i3 // one map icon for all colors
        else if (carData!!.sel_vehicle_image.startsWith("car_smart_")) R.drawable.map_car_smart // one map icon for all colors
        else if (carData!!.sel_vehicle_image.startsWith("car_kianiro_")) R.drawable.map_car_kianiro_grey // one map icon for all colors
        else if (carData!!.sel_vehicle_image.startsWith("car_kangoo_")) R.drawable.map_car_kangoo // one map icon for all colors
        else if (carData!!.sel_vehicle_image.startsWith("car_nrjk")) R.drawable.map_car_nrjk
        else if (carData!!.sel_vehicle_image.startsWith("car_niu_mqi_gt_")) R.drawable.map_car_nrjk
        else getDrawableIdentifier(activity, "map_" + carData!!.sel_vehicle_image)
        val drawable = ResourcesCompat.getDrawable(
            resources,
            if (icon != 0) icon else R.drawable.map_car_default, null
        )
        val myLogo = (drawable as BitmapDrawable?)!!.bitmap
        val marker = MarkerOptions().position(carPosition)
            .title(carData!!.sel_vehicle_label)
            .rotation(carData!!.car_direction.toFloat())
            .icon(BitmapDescriptorFactory.fromBitmap(myLogo))
        val carMarker = map!!.addMarker(marker)
        carMarker.clusterGroup = ClusterGroup.NOT_CLUSTERED

        // move camera if tracking enabled and user not currently interacting with the map:
        if (mapInitState || autoTrack && !userInteraction) {
            map!!.moveCamera(CameraUpdateFactory.newLatLng(carPosition))
            mapInitState = false
        }

        // update range circles:
        Log.i(
            TAG, "update: adding range circles:"
                    + " ideal=" + carData!!.car_range_ideal_raw
                    + " estimated=" + carData!!.car_range_estimated_raw
        )
        addCircles(
            carData!!.car_range_ideal_raw,
            carData!!.car_range_estimated_raw
        )

        // start chargepoint data update:
        appPrefs.saveData("lat_main", "" + carData!!.car_latitude)
        appPrefs.saveData("lng_main", "" + carData!!.car_longitude)
        //MainActivity.updateLocation!!.updateLocation()
    }

    // draw circle in a map
    private fun addCircles(rd1: Float, rd2: Float) {
        var rd1 = rd1
        var rd2 = rd2
        if (map == null) {
            return
        }

        // fix for issue #79 by @Timopen:
        if (rd1 < 0) {
            rd1 = 0f
        }
        if (rd2 < 0) {
            rd2 = 0f
        }
        val strokeWidth = resources.getDimension(
            R.dimen.circle_stroke_width
        )
        val pattern = listOf<PatternItem>(Dot())

        // full range circles:
        map!!.addCircle(
            CircleOptions()
                .data("first circle")
                .center(carPosition)
                .radius((rd1 * 1000).toDouble())
                .strokeWidth(strokeWidth)
                .strokeColor(Color.BLUE)
        )
        map!!.addCircle(
            CircleOptions()
                .data("second circle")
                .center(carPosition)
                .radius((rd2 * 1000).toDouble())
                .strokeWidth(strokeWidth)
                .strokeColor(Color.RED)
        )

        // half range ("point of no return") circles:
        map!!.addCircle(
            CircleOptions()
                .data("first ponr")
                .center(carPosition)
                .radius((rd1 * 1000 / 2).toDouble())
                .strokeWidth(strokeWidth / 2)
                .strokePattern(pattern)
                .strokeColor(Color.parseColor("#A04455FF"))
        )
        map!!.addCircle(
            CircleOptions()
                .data("second ponr")
                .center(carPosition)
                .radius((rd2 * 1000 / 2).toDouble())
                .strokeWidth(strokeWidth / 2)
                .strokePattern(pattern)
                .strokeColor(Color.parseColor("#A0FF5544"))
        )
    }

    override fun onClick(view: View) {
        if (view.id == R.id.bt_route) {
            direction()
        }
    }

    override fun clearCache() {
        database.clearMapDetails()
    }

    override fun updateFilter(connectionList: String?) {
        // update markers:
        updateMapDetails(false)
    }

    // calculate distance in meters:
    private fun distance(pos1: LatLng, pos2: LatLng): Double {
        return distance(pos1.latitude, pos1.longitude, pos2.latitude, pos2.longitude)
    }

    private fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a =
            sin(dLat / 2) * sin(dLat / 2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(
                dLon / 2
            ) * sin(dLon / 2)
        val c = 2 * asin(sqrt(a))
        return 6371000 * c
    }

    /*
     * Inner types
     */

    companion object {

        private const val TAG = "FragMap"

        lateinit var updateMap: UpdateMap
        private var mapInitState = true
        private var maxRange = 160f
        private var distanceUnits = "KM"
    }

    interface UpdateLocation {
        fun updateLocation()
    }
}
