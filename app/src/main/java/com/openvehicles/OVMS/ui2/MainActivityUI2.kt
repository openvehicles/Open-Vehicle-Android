package com.openvehicles.OVMS.ui2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
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

        uuid = appPrefs.getData("UUID")
        if (uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString()
            appPrefs.saveData("UUID", uuid)
            Log.d(TAG, "onCreate: generated new UUID: $uuid")
        } else {
            Log.d(TAG, "onCreate: using UUID: $uuid")
        }
        setContentView(R.layout.activity_main2)
        val toolbar: MaterialToolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar)
        registerReceiver(apiEventReceiver, IntentFilter(ApiService.ACTION_APIEVENT))



        val abc = AppBarConfiguration.Builder(R.id.navigation_home).build();
        val navHostFragment = supportFragmentManager.fragments.first() as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController, abc)

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
            val cmd = String.format(
                "MP-0 p%s,gcm,production,%s,%s,%s",
                uuid, carData.sel_vehicleid, carData.sel_server_password, token
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
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(listener)
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (apiEventReceiver != null)
            unregisterReceiver(apiEventReceiver)
    }
}