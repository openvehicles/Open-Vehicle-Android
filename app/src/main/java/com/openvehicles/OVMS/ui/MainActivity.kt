package com.openvehicles.OVMS.ui

import android.Manifest
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.ui.BaseFragmentActivity.Companion.finishCurrent
import com.openvehicles.OVMS.ui.MapFragment.UpdateLocation
import com.openvehicles.OVMS.ui.utils.Database
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.ConnectionList
import com.openvehicles.OVMS.utils.ConnectionList.ConnectionsListener
import com.openvehicles.OVMS.utils.Sys.getRandomString
import java.util.UUID

class MainActivity : ApiActivity(), ActionBar.OnNavigationListener, GetMapDetailsListener,
    ConnectionsListener, UpdateLocation {

    private var versionName = ""
    private var versionCode = 0
    private lateinit var appPrefs: AppPrefs
    private lateinit var database: Database
    private lateinit var uuid: String
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: MainPagerAdapter
    private var getMapDetails: GetMapDetails? = null
    private var getMapDetailList: MutableList<LatLng?> = mutableListOf()
    private var getMapDetailsBlockUntil: Long = 0
    private var getMapDetailsBlockSeconds: Long = 0
    private var tokenRequested = false

    private val getMapDetailsHandler = Handler(Looper.getMainLooper())

    private val gcmHandler = Handler(Looper.getMainLooper())
    private val gcmRegistrationBroadcastReceiver: BroadcastReceiver =
        object : BroadcastReceiver() {
            private val TAG = "mGcmRegReceiver"
            override fun onReceive(context: Context, intent: Intent) {
                Log.d(TAG, "onReceive intent: $intent")
                if (!tokenRequested) {
                    Log.i(TAG, "FCM token renewal detected => redo server registration")
                    gcmStartRegistration()
                }
            }
        }

    /**
     * ApiService / OVMS server communication:
     *
     */
    private var apiErrorDialog: AlertDialog? = null
    private var apiErrorMessage: String? = null
    private val apiEventReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val event = intent.getStringExtra("event")
            Log.v(TAG, "mApiEventReceiver: event=$event")
            when (event) {
                "LoginBegin" -> {
                    Log.d(TAG, "mApiEventReceiver: login process started")

                    // show progress indicator:
                    setSupportProgressBarIndeterminateVisibility(true)
                }
                "LoginComplete" -> {
                    Log.d(TAG, "mApiEventReceiver: login successful")

                    // hide progress indicator:
                    setSupportProgressBarIndeterminateVisibility(false)

                    // ...and hide error dialog:
                    if (apiErrorDialog != null && apiErrorDialog!!.isShowing) {
                        apiErrorDialog!!.hide()
                    }

                    // schedule GCM registration:
                    gcmStartRegistration()
                }
                "ServerSocketError" -> {
                    Log.d(TAG, "mApiEventReceiver: server/login error")

                    // hide progress indicator:
                    setSupportProgressBarIndeterminateVisibility(false)

                    // check if this message needs to be displayed:
                    val message = intent.getStringExtra("message") ?: return
                    if (message == apiErrorMessage && apiErrorDialog != null && apiErrorDialog!!.isShowing) {
                        return
                    }

                    // display message:
                    if (apiErrorDialog != null) {
                        apiErrorDialog!!.dismiss()
                    }
                    apiErrorMessage = message
                    apiErrorDialog = AlertDialog.Builder(this@MainActivity)
                        .setTitle(R.string.Error)
                        .setMessage(apiErrorMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show()
                }
            }
        }
    }

    // Make notification updates visible immediately:
    private val notificationReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d(TAG, "mNotificationReceiver: received $intent")

            // update messages list:
            val frg = pagerAdapter.getItemByTitle(R.string.Messages) as NotificationsFragment?
            if (frg != null) {
                Log.d(TAG, "mNotificationReceiver: update notifications fragment")
                frg.update()
                if (intent.getBooleanExtra("onNotification", false)) {
                    Log.d(TAG, "mNotificationReceiver: switch to notifications fragment")
                    finishCurrent()
                    onNavigationItemSelected(pagerAdapter.getPosition(R.string.Messages), 0)
                }
            }
        }
    }

    /**
     * App lifecycle management:
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        appPrefs = AppPrefs(this, "ovms")
        database = Database(this)

        // get/create App UUID:
        uuid = appPrefs.getData("UUID")
        if (uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString()
            appPrefs.saveData("UUID", uuid)
            Log.d(TAG, "onCreate: generated new UUID: $uuid")
        } else {
            Log.d(TAG, "onCreate: using UUID: $uuid")
        }

        // Check/create API key:
        var apiKey = appPrefs.getData("APIKey")
        if (apiKey.isEmpty()) {
            apiKey = getRandomString(25)
            appPrefs.saveData("APIKey", apiKey)
            Log.d(TAG, "onCreate: generated new APIKey: $apiKey")
        } else {
            Log.d(TAG, "onCreate: using APIKey: $apiKey")
        }

        // OCM init:
        getMapDetails = null
        updateLocation = this
        updateLocation()
        // update connection list if OCM is enabled:
        if (appPrefs.getData("option_ocm_enabled", "1") == "1") {
            ConnectionList(this, this, true)
        }

        // Start background ApiService:
        Log.i(TAG, "onCreate: starting ApiService")
        try {
            startService(Intent(this, ApiService::class.java))
        } catch (e: Exception) {
            Log.w(TAG, "onCreate: starting ApiService failed: $e")
        }

        // set up receiver for server communication service:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(apiEventReceiver, IntentFilter(ApiService.ACTION_APIEVENT),
                RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(apiEventReceiver, IntentFilter(ApiService.ACTION_APIEVENT))
        }

        // set up receiver for notifications:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(notificationReceiver, IntentFilter(ApiService.ACTION_NOTIFICATION),
                RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(notificationReceiver, IntentFilter(ApiService.ACTION_NOTIFICATION))
        }

        // init UI tabs:
        viewPager = ViewPager(this)
        viewPager.setId(android.R.id.tabhost)
        viewPager.fitsSystemWindows = true
        setContentView(viewPager)

        // check for update, Google Play Services & permissions:
        checkVersion()

        // configure ActionBar:
        val actionBar = supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(false)
        actionBar.setDisplayShowHomeEnabled(true)

        // Progress bar init:
        val progressBar = ProgressBar(this)
        progressBar.visibility = View.GONE
        progressBar.isIndeterminate = true
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.customView = progressBar
        pagerAdapter = MainPagerAdapter(
            arrayOf(
                TabInfo(
                    R.string.Messages,
                    R.drawable.ic_action_email,
                    NotificationsFragment::class.java
                ),
                TabInfo(R.string.Battery, R.drawable.ic_action_battery, InfoFragment::class.java),
                TabInfo(R.string.Car, R.drawable.ic_action_car, CarFragment::class.java), // TODO: R.string.Bike when the "car" is a bike
                TabInfo(R.string.Location, R.drawable.ic_action_location_map, MapFragment::class.java),
                TabInfo(R.string.Settings, R.drawable.ic_action_settings, SettingsFragment::class.java)
            )
        )
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = pagerAdapter.count - 1
        viewPager.addOnPageChangeListener(
            object : SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    actionBar.setSelectedNavigationItem(position)

                    // cancel system notifications on page "Messages":
                    if (pagerAdapter.getItemId(position) == R.string.Messages.toLong()) {
                        val mNotificationManager =
                            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                        mNotificationManager.cancelAll()
                    }
                }
            })
        actionBar.navigationMode = ActionBar.NAVIGATION_MODE_LIST
        actionBar.setListNavigationCallbacks(
            NavAdapter(this, pagerAdapter.tabInfoItems), this
        )

        // start on battery tab:
        onNavigationItemSelected(pagerAdapter.getPosition(R.string.Battery), 0)

        // process Activity startup intent:
        onNewIntent(intent)
    }

    public override fun onNewIntent(newIntent: Intent?) {
        if (newIntent == null) {
            return
        }
        Log.d(TAG, "onNewIntent: $newIntent")
        super.onNewIntent(newIntent)

        // if launched from notification, switch to messages tab:
        if (newIntent.getBooleanExtra("onNotification", false)) {
            onNavigationItemSelected(pagerAdapter.getPosition(R.string.Messages), 0)
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        unregisterReceiver(apiEventReceiver)
        unregisterReceiver(notificationReceiver)
        database.close()

        // Stop background ApiService?
        val serviceEnabled = appPrefs.getData("option_service_enabled", "0") == "1"
        if (!serviceEnabled) {
            Log.i(TAG, "onDestroy: stopping ApiService")
            stopService(Intent(this, ApiService::class.java))
        }
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        LocalBroadcastManager.getInstance(this).registerReceiver(
            gcmRegistrationBroadcastReceiver,
            IntentFilter(ACTION_FCM_NEW_TOKEN)
        )
        onNewIntent(intent)
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(gcmRegistrationBroadcastReceiver)
        super.onPause()
    }

    override fun setSupportProgressBarIndeterminateVisibility(visible: Boolean) {
        supportActionBar!!.customView.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * Check for update, show changes info
     */
    private fun checkVersion() {
        try {
            // get App version
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            versionName = pInfo.versionName.toString()
            versionCode = pInfo.versionCode
            if (appPrefs.getData("lastUsedVersionName", "") != versionName) {
                showVersion()
            } else {
                checkPlayServices()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            // ignore
            checkPlayServices()
        }
    }

    fun showVersion() {
        val msg = TextView(this)
        val scale = resources.displayMetrics.density
        val pad = (25 * scale + 0.5f).toInt()
        msg.setPadding(pad, pad, pad, pad)
        msg.text = Html.fromHtml(getString(R.string.about_message))
        msg.movementMethod = LinkMovementMethod.getInstance()
        msg.isClickable = true
        AlertDialog.Builder(this@MainActivity)
            .setTitle(getString(R.string.about_title, versionName, versionCode))
            .setView(msg)
            .setPositiveButton(R.string.msg_ok) { dialog1: DialogInterface?, which: Int ->
                appPrefs.saveData(
                    "lastUsedVersionName",
                    versionName
                )
            }
            .setOnDismissListener { dialog12: DialogInterface? -> checkPlayServices() }
            .show()
    }

    /**
     * Check the device for Google Play Services, tell user if missing.
     */
    private fun checkPlayServices() {
        if (appPrefs.getData("skipPlayServicesCheck", "0") == "1") {
            return
        }
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle(R.string.common_google_play_services_install_title)
                .setMessage(R.string.play_services_recommended)
                .setPositiveButton(R.string.remind, null)
                .setNegativeButton(R.string.dontremind) { dialog1: DialogInterface?, which: Int ->
                    appPrefs.saveData(
                        "skipPlayServicesCheck",
                        "1"
                    )
                }
                .setOnDismissListener { dialog12: DialogInterface? -> checkPermissions() }
                .show()
        } else {
            checkPermissions()
        }
    }

    /**
     * Check / request permissions
     */
    private fun checkPermissions() {
        val permissions = ArrayList<String>(2)
        var showRationale = false

        // ACCESS_FINE_LOCATION: needed for the "My location" map button
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            showRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

        // POST_NOTIFICATIONS: needed on Android >= 13 to create notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
            showRationale = showRationale or ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.POST_NOTIFICATIONS
            )
        }
        if (permissions.isNotEmpty()) {
            val permArray = arrayOfNulls<String>(permissions.size)
            permissions.toArray(permArray)
            if (showRationale) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.needed_permissions_title)
                    .setMessage(R.string.needed_permissions_message)
                    .setNegativeButton(R.string.later, null)
                    .setPositiveButton(R.string.msg_ok) { dialog1: DialogInterface?, which: Int ->
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            permArray,
                            0
                        )
                    }
                    .show()
            } else {
                ActivityCompat.requestPermissions(this@MainActivity, permArray, 0)
            }
        }
    }

    /**
     * FCM push notification registration:
     * - server login => gcmStartRegistration
     * - init gcmSenderId specific FirebaseApp instance as needed
     * - subscribe App instance to FCM broadcast channel (async)
     * - get the App instance FCM token (async)
     * - start GcmDoSubscribe for server push subscription (async, retries if necessary)
     */
    // onNewToken() callback also fires from getToken(), so we need a recursion inhibitor:
    private fun gcmStartRegistration() {
        val carData = loggedInCar
            ?: return
        val vehicleId = carData.sel_vehicleid

        // Initialize App for server/car specific GCM sender ID:
        var myApp = FirebaseApp.getInstance()
        val defaults = myApp.options
        val gcmSenderId = if (carData.sel_gcm_senderid.isNotEmpty()) {
            carData.sel_gcm_senderid
        } else {
            defaults.gcmSenderId
        }
        Log.d(
            TAG, "gcmStartRegistration: vehicleId=" + vehicleId
                    + ", gcmSenderId=" + gcmSenderId
        )
        if (gcmSenderId != null && gcmSenderId != defaults.gcmSenderId) {
            try {
                myApp = FirebaseApp.getInstance(gcmSenderId)
                Log.i(TAG, "gcmStartRegistration: reusing FirebaseApp " + myApp.name)
            } catch (ex1: Exception) {
                try {
                    // Note: we assume here we can simply replace the sender ID. This has been tested
                    //  successfully, but may need to be reconsidered & changed in the future.
                    // It works because FirebaseMessaging relies on Metadata.getDefaultSenderId(),
                    //  which prioritizes gcmSenderId if set. If gcmSenderId isn't set, it falls back
                    //  to extracting the project number from the applicationId.
                    // FCM token creation needs Project ID, Application ID and API key, but these
                    //  currently don't need to match additional sender ID projects, so we can
                    //  use the defaults. If/when this changes in the future, users will need to
                    //  supply these three instead of the sender ID (or build the App using their
                    //  "google-services.json" file).
                    val myOptions = FirebaseOptions.Builder(defaults) //.setProjectId("…")
                        //.setApplicationId("…")
                        //.setApiKey("…")
                        .setGcmSenderId(gcmSenderId)
                        .build()
                    myApp = FirebaseApp.initializeApp(this, myOptions, gcmSenderId)
                    Log.i(TAG, "gcmStartRegistration: initialized new FirebaseApp " + myApp.name)
                } catch (ex2: Exception) {
                    Log.e(
                        TAG,
                        "gcmStartRegistration: failed to initialize FirebaseApp, skipping token registration",
                        ex2
                    )
                    return
                }
            }
        }

        // Get messaging interface:
        val myMessaging = myApp.get(FirebaseMessaging::class.java)

        // Subscribe to broadcast channel:
        myMessaging.subscribeToTopic(FCM_BROADCAST_TOPIC)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e(TAG, "gcmStartRegistration: broadcast topic subscription failed")
                } else {
                    Log.i(TAG, "gcmStartRegistration: broadcast topic subscription done")
                }
            }

        // Start OVMS server push subscription:
        tokenRequested = true // inhibit onNewToken() callback
        myMessaging.token
            .addOnCompleteListener(OnCompleteListener<String> { task ->
                tokenRequested = false // allow onNewToken() callback
                if (!task.isSuccessful) {
                    Log.w(
                        TAG,
                        "gcmStartRegistration: fetching FCM registration token failed",
                        task.exception
                    )
                    return@OnCompleteListener
                }
                // as this is an async callback, verify we're still logged in as the initial vehicle:
                if (!isLoggedIn(vehicleId)) {
                    Log.d(
                        TAG,
                        "gcmStartRegistration: discard callback, logged in vehicle has changed"
                    )
                    return@OnCompleteListener
                }
                // Get FCM registration token
                val token = task.result
                Log.i(
                    TAG, "gcmStartRegistration: vehicleId=" + vehicleId
                            + ", gcmSenderId=" + gcmSenderId
                            + " => token=" + token
                )
                // Start push subscription at OVMS server
                gcmHandler.post(GcmDoSubscribe(vehicleId, token))
            })
    }

    override fun onNavigationItemSelected(itemPosition: Int, itemId: Long): Boolean {
        if (itemPosition < 0 || itemPosition >= pagerAdapter.count) return false
        val ti: TabInfo = pagerAdapter.tabInfoItems[itemPosition]
        supportActionBar!!.setIcon(ti.iconResId)
        viewPager.setCurrentItem(itemPosition, false)
        return true
    }

    override fun onConnectionChanged(al: String?, name: String?) {
        // TODO Auto-generated method stub
    }

    override fun updateLocation() {
        // get car location:
        var lat = "37.410866"
        var lng = "-122.001946"
        if (appPrefs.getData("lat_main") == "") {
            // init car position:
            appPrefs.saveData("lat_main", lat)
            appPrefs.saveData("lng_main", lng)
            Log.i(TAG, "updatelocation: init car position")
        } else {
            // get current car position:
            lat = appPrefs.getData("lat_main")
            lng = appPrefs.getData("lng_main")
        }

        // Start OpenChargeMap task:
        try {
            val dLat = lat.toDouble()
            val dLng = lng.toDouble()
            startGetMapDetails(LatLng(dLat, dLng))
        } catch (e: Exception) {
            // ignore
        }
    }

    private fun isMapCacheValid(center: LatLng): Boolean {
        // As OCM does not yet support incremental queries,
        // we're using a cache with key = int(lat/lng)
        // resulting in a tile size of max. 112 x 112 km
        // = diagonal max 159 km
        // The API call will fetch a fixed radius of 160 km
        // covering all adjacent tiles.

        // check OCM cache for key int(lat/lng):
        val latitude = center.latitude.toInt()
        val longitude = center.longitude.toInt()
        val cursor = database.getLatLngDetail(latitude, longitude)
        val colLastUpdate = cursor.getColumnIndex("last_update")
        if (cursor.count == 0) {
            cursor.close()
            return false
        } else if (cursor.moveToFirst()) {
            // check if last tile update was more than 24 hours ago:
            val lastUpdate = if (colLastUpdate >= 0) cursor.getLong(colLastUpdate) else 0
            val now = System.currentTimeMillis() / 1000
            if (now > lastUpdate + 3600 * 24) {
                cursor.close()
                return false
            }
        }
        Log.v(TAG, "isMapCacheValid: cache valid for lat/lng=$latitude/$longitude")
        cursor.close()
        return true
    }

    fun startGetMapDetails(center: LatLng) {
        if (!isMapCacheValid(center)) {
            getMapDetailList.add(center)
            startGetMapDetails()
        } else {
            Log.v(TAG, "StartGetMapDetails: map cache valid for center=$center")
        }
    }

    private fun startGetMapDetails() {
        // check if task is still running:
        if (getMapDetails != null && getMapDetails!!.status != AsyncTask.Status.FINISHED) {
            return
        }
        // check if error block period is active:
        if (System.currentTimeMillis() < getMapDetailsBlockUntil) {
            return
        }
        // check if OCM has been disabled:
        if (appPrefs.getData("option_ocm_enabled", "1") == "0") {
            return
        }
        do {
            // get next coordinates to fetch:
            if (getMapDetailList.isEmpty()) {
                Log.i(TAG, "StartGetMapDetails: done.")
                return
            }
            val center = getMapDetailList.removeAt(0)
            if (isMapCacheValid(center!!)) {
                continue
            }

            // create new fetcher task:
            Log.i(TAG, "StartGetMapDetails: starting task for $center")
            getMapDetails = GetMapDetails(this@MainActivity, center, this)
            getMapDetails!!.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            return
        } while (true)
    }

    override fun getMapDetailsDone(isSuccess: Boolean, center: LatLng?) {
        if (isSuccess) {
            // mark cache tile valid:
            Log.i(TAG, "getMapDetailsDone: OCM updates received for $center")
            database.addLatLngDetail(center!!.latitude.toInt(), center.longitude.toInt())
            // update map:
            val frg = pagerAdapter.getItemByTitle(R.string.Location) as MapFragment?
            if (frg != null) {
                Log.d(TAG, "getMapDetailsDone: OCM updates received => calling FragMap.update()")
                frg.update()
            }
            // clear blocking:
            getMapDetailsBlockUntil = 0
            getMapDetailsBlockSeconds = 0
            // check for next fetch job:
            startGetMapDetails()
        } else {
            Log.e(TAG, "getMapDetailsDone: OCM updates failed for $center")
            // increase blocking time up to 120 seconds:
            if (getMapDetailsBlockSeconds < 120) {
                getMapDetailsBlockSeconds += 10
            }
            Log.d(
                TAG,
                "getMapDetailsDone: blocking OCM API calls for $getMapDetailsBlockSeconds seconds"
            )
            getMapDetailsBlockUntil = System.currentTimeMillis() + 1000 * getMapDetailsBlockSeconds
            // schedule retry:
            if (getMapDetailsBlockSeconds < 120) {
                getMapDetailsHandler.postDelayed(
                    { startGetMapDetails(center!!) },
                    1000 * getMapDetailsBlockSeconds
                )
            } else {
                Log.w(TAG, "getMapDetailsDone: maximum block time reached, no further retries")
                // Note: retry blocking will be resolved by the next regular fetch from a vehicle movement
            }
        }
    }

    /*
     * Inner types
     */

    companion object {
        private const val TAG = "MainActivity"
        const val ACTION_FCM_NEW_TOKEN = "fcmNewToken"
        private const val FCM_BROADCAST_TOPIC = "global"
        @JvmField
        var updateLocation: UpdateLocation? = null
    }

    private inner class GcmDoSubscribe(
        private val vehicleId: String,
        private val token: String
    ) : Runnable {

        private val tag = "GcmDoSubscribe"

        override fun run() {
            val service = service
            if (service == null) {
                Log.d(tag, "ApiService terminated, cancelling")
                return
            } else if (!service.isLoggedIn()) {
                Log.d(tag, "ApiService not yet logged in, scheduling retry")
                gcmHandler.postDelayed(this, 5000)
                return
            }
            val carData = service.getCarData()
            if (carData?.sel_vehicleid == null || carData.sel_vehicleid.isEmpty()) {
                Log.d(tag, "ApiService not logged in / has no defined car, cancelling")
                return
            }

            // Async operation, verify we're still logged in to the same vehicle:
            if (carData.sel_vehicleid != vehicleId) {
                Log.d(tag, "ApiService logged in to different car, cancelling")
                return
            }

            // Subscribe at OVMS server:
            Log.d(tag, "subscribing vehicle ID $vehicleId to FCM token $token")
            // MP-0 p<appid>,<pushtype>,<pushkeytype>{,<vehicleid>,<netpass>,<pushkeyvalue>}
            // Note: Normalize vehicle ID to uppercase to ensure consistent matching with server notifications
            val cmd = String.format(
                "MP-0 p%s,gcm,production,%s,%s,%s",
                uuid, carData.sel_vehicleid.uppercase(), carData.sel_server_password, token
            )
            if (!service.sendCommand(cmd, null)) {
                Log.w(tag, "FCM server push subscription failed, scheduling retry")
                gcmHandler.postDelayed(this, 5000)
            } else {
                Log.i(tag, "FCM server push subscription done")
            }
        }
    }

    /**
     * User interface handling:
     *
     */
    public inner class TabInfo(
        val titleResId: Int,
        val iconResId: Int,
        val fragmentClass: Class<out BaseFragment?>
    ) {
        var fragment: Fragment? = null
        val fragmentName: String
            get() = fragmentClass.getName()

        override fun toString(): String {
            return getString(titleResId)
        }
    }


    public class NavAdapter(
        context: Context,
        objects: Array<TabInfo>
    ) : ArrayAdapter<TabInfo?>(
        context,
        android.R.layout.simple_spinner_item,
        objects
    ) {
        init {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val tv = super.getDropDownView(
                position,
                convertView, parent
            ) as TextView
            tv.setCompoundDrawablesWithIntrinsicBounds(
                getItem(position)!!.iconResId, 0, 0, 0
            )
            return tv
        }
    }

    public inner class MainPagerAdapter(
        tabInfoItems: Array<TabInfo>
    ) : FragmentPagerAdapter(supportFragmentManager) {

        val tabInfoItems: Array<TabInfo>

        init {
            this.tabInfoItems = tabInfoItems
        }

        override fun getItem(position: Int): Fragment {
            if (tabInfoItems[position].fragment == null) {
                // instantiate fragment:
                tabInfoItems[position].fragment = Fragment.instantiate(
                    this@MainActivity,
                    tabInfoItems[position].fragmentName
                )
            }
            Log.d(
                TAG,
                "MainPagerAdapter: pos=" + position + " => frg=" + tabInfoItems[position].fragment
            )
            return tabInfoItems[position].fragment!!
        }

        fun getItemByTitle(pTitle: Int): Fragment {
            return getItem(getPosition(pTitle))
        }

        override fun getCount(): Int {
            return tabInfoItems.size
        }

        override fun getItemId(position: Int): Long {
            // use Title String resource id as Item id:
            return tabInfoItems[position].titleResId.toLong()
        }

        fun getPosition(itemId: Int): Int {
            for (p in tabInfoItems.indices) {
                if (tabInfoItems[p].titleResId == itemId) {
                    return p
                }
            }
            return -1
        }

        override fun getPageTitle(position: Int): CharSequence {
            return getString(tabInfoItems[position].titleResId)
        }
    }
}
