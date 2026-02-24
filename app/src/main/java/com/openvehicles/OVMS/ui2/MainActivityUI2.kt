package com.openvehicles.OVMS.ui2

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.ui.ApiActivity
import com.openvehicles.OVMS.ui.MainActivity
import com.openvehicles.OVMS.ui.MapFragment
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.utils.Sys.getRandomString
import java.util.UUID

class MainActivityUI2 : ApiActivity() {

    companion object {
        var updateLocation: MapFragment.UpdateLocation? = null
        private const val TAG = "MainActivityUI2"
        private const val FCM_BROADCAST_TOPIC = "global"
    }

    private lateinit var navController: NavController
    /**
     * ApiService / OVMS server communication:
     *
     */
    private var apiErrorDialog: AlertDialog? = null
    private var apiErrorMessage: String? = null
    private lateinit var appPrefs: AppPrefs
    private lateinit var uuid: String
    private var versionName = ""
    private var versionCode = 0

    private var tokenRequested = false
    private var apiEventReceiver: BroadcastReceiver? = null

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

    private val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        findViewById<Spinner>(R.id.spinner_toolbar).visibility = if (destination.id == R.id.navigation_home) View.VISIBLE else View.GONE
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appPrefs = AppPrefs(this, "ovms")
        if (appPrefs.getData("option_oldui_enabled", "0") == "1") {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        apiEventReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val event = intent.getStringExtra("event")
                Log.v(TAG, "mApiEventReceiver: event=$event")
                when (event) {
                    "LoginBegin" -> {
                        Log.d(TAG, "mApiEventReceiver: login process started")

                        // show progress indicator:
                    }
                    "LoginComplete" -> {
                        Log.d(TAG, "mApiEventReceiver: login successful")

                        // hide progress indicator:
                        // ...and hide error dialog:
                        if (apiErrorDialog != null && apiErrorDialog!!.isShowing) {
                            apiErrorDialog!!.hide()
                        }

                        // schedule GCM registration:
                        gcmStartRegistration()
                    }
                    "ServerSocketError" -> {
                        Log.d(TAG, "mApiEventReceiver: server/login error")

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
                        apiErrorDialog = MaterialAlertDialogBuilder(this@MainActivityUI2)
                            .setTitle(R.string.Error)
                            .setMessage(apiErrorMessage)
                            .setPositiveButton(android.R.string.ok, null)
                            .show()
                    }
                }
            }
        }

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

        setContentView(R.layout.activity_main2)
        val toolbar: MaterialToolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar)

        // Start background ApiService:
        Log.i(TAG, "onCreate: starting ApiService")
        try {
            startService(Intent(this, ApiService::class.java))
        } catch (e: Exception) {
            Log.w(TAG, "onCreate: starting ApiService failed: $e")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(apiEventReceiver, IntentFilter(ApiService.ACTION_APIEVENT),
                RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(apiEventReceiver, IntentFilter(ApiService.ACTION_APIEVENT))
        }

        // check for update, Google Play Services & permissions:
        checkVersion()

        val abc = AppBarConfiguration.Builder(R.id.navigation_home).build();
        val navHostFragment = supportFragmentManager.fragments.first() as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController, abc)

        // process Activity startup intent:
        onNewIntent(intent)
    }

    public override fun onNewIntent(newIntent: Intent?) {
        Log.d(TAG, "onNewIntent: $newIntent")
        super.onNewIntent(newIntent)
        // we cannot always access the navController here, so store the intent for onResume():
        intent = newIntent
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
        MaterialAlertDialogBuilder(this@MainActivityUI2)
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
            AlertDialog.Builder(this@MainActivityUI2)
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
                AlertDialog.Builder(this@MainActivityUI2)
                    .setTitle(R.string.needed_permissions_title)
                    .setMessage(R.string.needed_permissions_message)
                    .setNegativeButton(R.string.later, null)
                    .setPositiveButton(R.string.msg_ok) { dialog1: DialogInterface?, which: Int ->
                        ActivityCompat.requestPermissions(
                            this@MainActivityUI2,
                            permArray,
                            0
                        )
                    }
                    .show()
            } else {
                ActivityCompat.requestPermissions(this@MainActivityUI2, permArray, 0)
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

    override fun onSupportNavigateUp(): Boolean {
        if (!(navController.navigateUp() || super.onSupportNavigateUp())) {
            onBackPressed()
        }
        return true
    }


    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)

        // if launched from notification, switch to messages tab:
        val startIntent = intent
        if (startIntent != null && startIntent.getBooleanExtra("onNotification", false) == true) {
            navController.navigate(R.id.action_global_notificationsFragment)
        }
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(listener)
        super.onPause()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")

        if (apiEventReceiver != null)
            unregisterReceiver(apiEventReceiver)

        // Stop background ApiService?
        val serviceEnabled = appPrefs.getData("option_service_enabled", "0") == "1"
        if (!serviceEnabled) {
            Log.i(TAG, "onDestroy: stopping ApiService")
            stopService(Intent(this, ApiService::class.java))
        }

        super.onDestroy()
    }
}