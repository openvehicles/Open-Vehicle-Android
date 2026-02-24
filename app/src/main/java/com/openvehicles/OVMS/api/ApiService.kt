package com.openvehicles.OVMS.api

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.util.Log
import androidx.core.app.NotificationCompat
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.ApiObservable.addObserver
import com.openvehicles.OVMS.api.ApiObservable.deleteObserver
import com.openvehicles.OVMS.api.ApiObservable.notifyLoggedIn
import com.openvehicles.OVMS.api.ApiObservable.notifyUpdate
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.ui.MainActivity
import com.openvehicles.OVMS.ui.utils.Database
import com.openvehicles.OVMS.ui2.MainActivityUI2
import com.openvehicles.OVMS.utils.CarsStorage
import com.openvehicles.OVMS.utils.Sys
import java.io.Serializable
import java.lang.reflect.Array

class ApiService : Service(), ApiTask.ApiTaskListener, ApiObserver {

    private val binder: IBinder = ApiBinder()

    @Volatile
    private var carData: CarData? = null
    private var apiTask: ApiTask? = null
    private var onResultCommandListener: OnResultCommandListener? = null
    private var appPrefs: AppPrefs? = null
    private var database: Database? = null

    private var isEnabled = false // Service in "foreground" mode

    private var isStopped = false // Service stopped

    private var connectivityManager: ConnectivityManager? = null
    private var alarmManager: AlarmManager? = null

    @Volatile
    private var serviceLooper: Looper? = null

    @Volatile
    private var serviceHandler: ApiServiceHandler? = null

    private val actionReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            handleIntent(intent)
        }

    }

    /**
     * Broadcast Receiver for Network Connectivity Changes
     */
    private val networkStatusReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            Log.d(TAG, "mNetworkStatusReceiver: " + intent + intent.extras)
            Log.d(
                TAG, "mNetworkStatusReceiver: new state: "
                        + if (isOnline()) "ONLINE" else "OFFLINE"
            )
            if (!isOnline() && apiTask != null) {
                closeConnection()
            } else if (isOnline() && apiTask == null) {
                openConnection()
            }
        }

    }

    /**
     * Broadcast Command Receiver for Automagic / Tasker / ...
     *
     * Intent extras:
     * - apikey or sel_vehicleid + sel_server_password
     * - msg_command or command
     *
     * msg_command: MP command syntax, e.g. "7,stat" / "20,1234"
     * command: user command syntax, e.g. "stat" / "#20,1234"
     *
     * Car will be changed as necessary (persistent change).
     * Fails silently on errors.
     * Results will be broadcasted & displayed by onResultCommand().
     */
    private val mCommandReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            Log.d(TAG, "CommandReceiver: received $intent")

            // Check API configuration:
            if (appPrefs!!.getData("option_commands_enabled", "0") == "0") {
                Log.e(TAG, "CommandReceiver: disabled")
                return
            } else if (!isLoggedIn()) {
                Log.e(TAG, "CommandReceiver: not logged in")
                return
            }

            // Get parameters:
            val apikey = intent.getStringExtra("apikey")
            val vehicleid = intent.getStringExtra("sel_vehicleid")
            val vehiclepass = intent.getStringExtra("sel_server_password")

            // Get vehicle config:
            val carApiKey = appPrefs!!.getData("APIKey")
            val carData = if (!vehicleid.isNullOrEmpty()) {
                CarsStorage.getCarById(vehicleid)
            } else {
                CarsStorage.getSelectedCarData()
            }

            // Check authorization:
            if (carData == null || apikey == null && vehiclepass == null || apikey != null && carApiKey != apikey || vehiclepass != null && carData.sel_server_password != vehiclepass) {
                Log.e(TAG, "CommandReceiver: vehicle/authorization invalid")
                return
            }

            // Get command parameters:
            var msgCommand = intent.getStringExtra("msg_command")
            val userCommand = intent.getStringExtra("command")
            if (msgCommand == null || msgCommand.isEmpty()) {
                msgCommand = makeMsgCommand(userCommand)
            }

            // Note: command may be empty/missing, to only change the current vehicle

            // Change car if necessary:
            if (this@ApiService.carData!!.sel_vehicleid != carData.sel_vehicleid) {
                Log.i(TAG, "CommandReceiver: changing car to: " + carData.sel_vehicleid)
                changeCar(carData)
                CarsStorage.setSelectedCarId(carData.sel_vehicleid)
            }

            // Send command:
            if (!msgCommand.isEmpty()) {
                Log.i(TAG, "CommandReceiver: sending command: $msgCommand")
                cancelCommand(null)
                if (!apiTask!!.sendMessage(String.format("MP-0 C%s", msgCommand))) {
                    Log.e(TAG, "CommandReceiver: sendCommand failed")
                }
            }
        }

    }

    override fun onCreate() {
        super.onCreate()

        isStopped = false
        connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        appPrefs = AppPrefs(this, "ovms")
        database = Database(applicationContext)
        createNotificationChannel()

        // check if the service shall run in foreground:
        val foreground = appPrefs!!.getData("option_service_enabled", "0") == "1"
        if (foreground) {
            enableService()
        }

        // Register command receiver:
        Log.d(TAG, "Registering command receiver for Intent: $ACTION_SENDCOMMAND")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(mCommandReceiver, IntentFilter(ACTION_SENDCOMMAND), RECEIVER_EXPORTED)
        } else {
            registerReceiver(mCommandReceiver, IntentFilter(ACTION_SENDCOMMAND))
        }

        // Register network status receiver:
        registerReceiver(
            networkStatusReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        // Start intent handler thread:
        val thread = HandlerThread("ApiServiceHandler")
        thread.start()
        serviceLooper = thread.looper
        serviceHandler = ApiServiceHandler(serviceLooper)

        // Register action receiver:
        val actionFilter = IntentFilter()
        actionFilter.addAction(ACTION_PING)
        actionFilter.addAction(ACTION_ENABLE)
        actionFilter.addAction(ACTION_DISABLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Note: this receiver needs to be "exported", it otherwise does not receive the
            //          broadcasts sent by the AppUISettingsFragment
            registerReceiver(actionReceiver, actionFilter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(actionReceiver, actionFilter)
        }

        // Register as an ApiObserver:
        addObserver(this)

        // Login for selected car:
        openConnection()

        // Schedule ping:
        val pi = PendingIntent.getService(
            this, 0,
            Intent(ACTION_PING), Sys.getMutableFlags(PendingIntent.FLAG_UPDATE_CURRENT, false)
        )
        val pingIntervalMs = (PING_INTERVAL * 60 * 1000).toLong()
        alarmManager!!.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + pingIntervalMs, pingIntervalMs, pi
        )
        sendApiEvent("ServiceCreated")
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: close")
        isStopped = true

        // Stop ping:
        val pi = PendingIntent.getService(
            this, 0,
            Intent(ACTION_PING), Sys.getMutableFlags(PendingIntent.FLAG_UPDATE_CURRENT, false)
        )
        alarmManager!!.cancel(pi)

        // Logout:
        closeConnection()
        unregisterReceiver(mCommandReceiver)
        unregisterReceiver(networkStatusReceiver)
        unregisterReceiver(actionReceiver)
        deleteObserver(this)
        database!!.close()
        sendApiEvent("ServiceDestroyed")
        Log.d(TAG, "onDestroy: done")
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            Log.d(TAG, "onStartCommand: no intent")
            return START_STICKY;
        }
        Log.d(TAG, "onStartCommand: $intent")

        // Forward intent to our handler thread:
        val msg = serviceHandler?.obtainMessage()
        if (msg != null) {
            msg.arg1 = startId
            msg.obj = intent
            serviceHandler?.sendMessage(msg)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind:$intent")
        checkConnection()
        return binder
    }

    fun onActivityStop() {
        if (!isEnabled && !isOnline()) {
            Log.d(TAG, "onActivityStop (without background connectivity)")
            isStopped = true
            sendApiEvent("ServiceStopped")
        }
    }

    fun onActivityStart() {
        if (isStopped) {
            Log.d(TAG, "onActivityStart")
            isStopped = false
            sendApiEvent("ServiceStarted")
        }
    }

    override fun onTimeout(startId: Int, fgsType: Int) {
        // see https://developer.android.com/develop/background-work/services/fgs/timeout
        Log.e(TAG, "onTimeout: Android requests stopping the service due to maximum run time limit reached")
        stopSelf()
    }

    private fun handleIntent(intent: Intent?) {
        Log.d(TAG, "handleIntent: $intent")
        if (intent == null) return
        val action = intent.action
        if (ACTION_PING == action) {
            checkConnection()
        } else if (ACTION_ENABLE == action) {
            enableService()
        } else if (ACTION_DISABLE == action) {
            disableService()
        }
    }

    /**
     * checkConnection: reconnect if necessary
     */
    fun checkConnection() {
        if (!isLoggedIn()) {
            if (!isOnline()) {
                Log.i(TAG, "checkConnection: no network, skipping reconnect")
            } else {
                Log.i(TAG, "checkConnection: doing reconnect")
                openConnection()
            }
        } else {
            Log.i(TAG, "checkConnection: connection OK")
        }
    }

    /**
     * closeConnection: terminate ApiTask
     */
    @Synchronized
    fun closeConnection() {
        try {
            if (apiTask != null) {
                Log.v(TAG, "closeConnection: shutting down TCP connection")
                apiTask!!.cancel(true)
                apiTask = null
                notifyLoggedIn(this, false)
                sendApiEvent("UpdateStatus")
            }
        } catch (e: Exception) {
            Log.e(TAG, "closeConnection: ERROR stopping ApiTask", e)
        }
    }

    /**
     * enableService: ask Android to keep this service running ("foreground") after stopping the MainActivity
     */
    private fun enableService() {
        Log.i(TAG, "enableService: starting foreground mode")
        val notificationIntent = Intent(this,
            if (appPrefs?.getData("option_oldui_enabled", "0") == "1") MainActivity::class.java
            else MainActivityUI2::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, Sys.getMutableFlags(0, false)
        )
        val notification: Notification = NotificationCompat.Builder(this, "status")
            .setContentTitle(getText(R.string.service_notification_title))
            .setContentText(getText(R.string.service_notification_text))
            .setTicker(getText(R.string.service_notification_ticker))
            .setSmallIcon(R.drawable.ic_service)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .build()
        try {
            startForeground(ONGOING_NOTIFICATION_ID, notification)
            isEnabled = true
            isStopped = false
            sendApiEvent("ServiceEnabled")
        } catch (e: Exception) {
            Log.e(TAG, "enableService: startForeground failed:", e)
        }
    }

    /**
     * disableService: ask Android to stop this service when the MainActivity is stopped
     */
    private fun disableService() {
        Log.i(TAG, "disableService: stopping foreground mode")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(Service.STOP_FOREGROUND_REMOVE)
        } else {
            stopForeground(true)
        }
        isEnabled = false
        sendApiEvent("ServiceDisabled")
    }


    /**
     * openConnection: start ApiTask
     */
    @Synchronized
    fun openConnection() {
        if (carData == null) {
            Log.v(TAG, "openConnection: getting CarData")
            carData = CarsStorage.getSelectedCarData()
        }
        if (apiTask != null) {
            Log.v(TAG, "openConnection: closing previous connection")
            closeConnection()
        }
        if (carData != null) {
            Log.v(TAG, "openConnection: starting TCP Connection")

            // reset the paranoid mode flag in car data
            // it will be set again when the TCP task detects paranoid mode messages
            carData!!.sel_paranoid = false

            // start the new ApiTask:
            apiTask = ApiTask(this, carData!!, this)
            apiTask!!.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }
    }

    fun isLoggedIn(): Boolean {
        return apiTask != null && apiTask!!.isLoggedIn()
    }

    fun getCarData(): CarData? {
        return carData
    }

    /**
     * Check for service / network availability.
     *
     * @return true if service is running and has network access
     */
    fun isOnline(): Boolean {
        if (isStopped) return false
        val info = connectivityManager!!.activeNetworkInfo
        return info != null && info.isConnected && info.detailedState != NetworkInfo.DetailedState.BLOCKED
    }

    /**
     * changeCar: terminate existing connection if any, connect to car
     *
     * @param carData
     */
    fun changeCar(carData: CarData) {
        Log.i(TAG, "changeCar: changing car to: " + carData.sel_vehicleid)
        closeConnection()
        this.carData = carData
        notifyUpdate(this.carData)
        openConnection()
    }

    fun sendCommand(
        resIdMessage: Int,
        command: String,
        onResultCommandListener: OnResultCommandListener?
    ): Boolean {
        return sendCommand(getString(resIdMessage), command, onResultCommandListener)
    }

    fun sendCommand(
        message: String?,
        command: String,
        onResultCommandListener: OnResultCommandListener?
    ): Boolean {
        return sendCommand(command, onResultCommandListener)
    }

    fun sendCommand(command: String, onResultCommandListener: OnResultCommandListener?): Boolean {
        if (apiTask == null || TextUtils.isEmpty(command)) return false
        // TODO: use command request queue, move result listener into command request
        this.onResultCommandListener = onResultCommandListener
        return apiTask!!.sendMessage(
            if (command.startsWith("MP-0")) command else String.format(
                "MP-0 C%s",
                command
            )
        )
    }

    fun cancelCommand(onResultCommandListener: OnResultCommandListener?) {
        // TODO: use command request queue, move result listener into command request
        if (this.onResultCommandListener === onResultCommandListener || onResultCommandListener == null) {
            this.onResultCommandListener = null
        }
    }

    fun sendApiEvent(event: String?) {
        ApiEvent(event).send()
    }

    /* sendBroadcast debug helper
	@Override
	public void sendBroadcast(Intent intent) {
		Log.v(TAG, "sendBroadcast: " + intent + ":" + intent.getExtras().toString());
		super.sendBroadcast(intent);
	}
	*/

    /* sendBroadcast debug helper
	@Override
	public void sendBroadcast(Intent intent) {
		Log.v(TAG, "sendBroadcast: " + intent + ":" + intent.getExtras().toString());
		super.sendBroadcast(intent);
	}
	*/
    override fun onUpdateStatus(msgCode: Char, msgData: String?) {
        Log.v(TAG, "onUpdateStatus $msgCode")
        // Route the update through the ApiObservable queue to merge multiple
        //  adjacent server messages into one broadcast:
        notifyUpdate(carData)
    }

    override fun onPushNotification(msgClass: Char, msgText: String?) {
        // This callback only receives MP push notifications for the currently selected vehicle.
        // See MyFirebaseMessagingService for system notification broadcasting.
    }

    // ApiObserver interface:
    override fun update(carData: CarData?) {
        // Update ApiEvent listeners (App Widgets):
        sendApiEvent("UpdateStatus")

        // Send system broadcast for Automagic / Tasker / ...
        if (appPrefs!!.getData("option_broadcast_enabled", "0") == "1") {
            Log.d(TAG, "update: sending system broadcast $ACTION_UPDATE")
            val intent = Intent(ACTION_UPDATE)
            intent.putExtra("sel_server", this.carData!!.sel_server)
            intent.putExtra("sel_vehicleid", this.carData!!.sel_vehicleid)
            intent.putExtra("sel_vehicle_label", this.carData!!.sel_vehicle_label)
            intent.putExtras(this.carData!!.getBroadcastData())
            sendBroadcast(intent)
            sendKustomBroadcast(this, intent)
        }
    }

    /**
     * triggerUpdate: request unconditional update of current car state for all listeners.
     * Use to forward prefs changes regarding car data interpretation.
     */
    fun triggerUpdate() {
        carData!!.recalc()
        notifyUpdate(carData)
    }

    // ApiObserver interface:
    override fun onServiceAvailable(service: ApiService) {
        // nop
    }

    override fun onServiceLoggedIn(service: ApiService?, isLoggedIn: Boolean) {
        // nop
    }

    override fun onServerSocketError(error: Throwable?) {
        notifyLoggedIn(this, isLoggedIn())
        val apiEvent = ApiEvent("ServerSocketError", error)
        apiEvent.putExtra(
            "message",
            getString(if (isLoggedIn()) R.string.err_connection_lost else R.string.err_check_following)
        )
        apiEvent.send()
    }

    override fun onResultCommand(cmdResponse: String?) {
        if (cmdResponse.isNullOrEmpty()) {
            Log.d(TAG, "onResultCommand: response: null")
            return
        }
        Log.d(TAG, "onResultCommand: response: " + cmdResponse.replace('\r', '|'))

        // Parse command response:
        val cmdCode: Int
        val cmdError: Int
        var cmdData: String? = ""
        val data = cmdResponse
            .split(",\\s*".toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()
        try {
            cmdCode = data[0].toInt()
            cmdError = data[1].toInt()
            val offset = data[0].length + data[1].length + 2
            if (cmdResponse.length > offset) cmdData = cmdResponse.substring(offset)
        } catch (e: Exception) {
            Log.e(TAG, "onResultCommand: invalid response: $cmdResponse")
            return
        }
        if (onResultCommandListener != null) {
            // TODO: use command request queue, move result listener into command request
            // Forward command response to listener:
            //Log.d(TAG, "onResultCommand: forward to listener");
            onResultCommandListener!!.onResultCommand(data)
        }

        // Check broadcast API configuration:
        if (appPrefs!!.getData("option_commands_enabled", "0") == "1") {
            Log.v(
                TAG,
                "onResultCommand: sending broadcast $ACTION_COMMANDRESULT: $cmdResponse"
            )
            val intent = Intent(ACTION_COMMANDRESULT)
            intent.putExtra("sel_server", carData!!.sel_server)
            intent.putExtra("sel_vehicleid", carData!!.sel_vehicleid)
            intent.putExtra("sel_vehicle_label", carData!!.sel_vehicle_label)
            intent.putExtra("cmd_vehicleid", carData!!.sel_vehicleid)
            intent.putExtra("cmd_code", cmdCode)
            intent.putExtra("cmd_error", cmdError)
            intent.putExtra("cmd_data", cmdData)
            intent.putExtra("cmd_result", data)
            sendBroadcast(intent)
            sendKustomBroadcast(this, intent)
        }
    }

    override fun onLoginBegin() {
        Log.d(TAG, "onLoginBegin")
        notifyLoggedIn(this, false)
        sendApiEvent("LoginBegin")
    }

    override fun onLoginComplete() {
        Log.d(TAG, "onLoginComplete")
        notifyLoggedIn(this, true)
        sendApiEvent("LoginComplete")
    }

    /**
     * Create notification channels for Android >= 8.0
     * This is done here in ApiService because the service may start
     * on boot, independant of the MainActivity.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )

            // Delete the obsolete "default" channel:
            notificationManager.deleteNotificationChannel("default")

            // Create channel "status":
            var name: CharSequence = getString(R.string.channel_status_name)
            var description = getString(R.string.channel_status_description)
            var importance = NotificationManager.IMPORTANCE_LOW
            var channel = NotificationChannel("status", name, importance)
            channel.description = description
            notificationManager.createNotificationChannel(channel)

            // Create channel "info":
            name = getString(R.string.channel_info_name)
            description = getString(R.string.channel_info_description)
            importance = NotificationManager.IMPORTANCE_HIGH // show as popup by default, user can change this
            channel = NotificationChannel("info", name, importance)
            channel.description = description
            notificationManager.createNotificationChannel(channel)

            // Create channel "alert":
            name = getString(R.string.channel_alert_name)
            description = getString(R.string.channel_alert_description)
            importance = NotificationManager.IMPORTANCE_HIGH
            channel = NotificationChannel("alert", name, importance)
            channel.description = description
            notificationManager.createNotificationChannel(channel)
        }
    }

    /*
     * Inner types
     */

    companion object {

        private const val TAG = "ApiService"
        private const val ONGOING_NOTIFICATION_ID = 0x4f564d53 // "OVMS"

        private const val PING_INTERVAL = 5 // Minutes

        // Internal broadcast actions:
        const val ACTION_APIEVENT = "com.openvehicles.OVMS.ApiEvent"
        const val ACTION_PING = "com.openvehicles.OVMS.service.intent.PING"
        const val ACTION_ENABLE = "com.openvehicles.OVMS.service.intent.ENABLE"
        const val ACTION_DISABLE = "com.openvehicles.OVMS.service.intent.DISABLE"

        // System broadcast actions for Tasker et al:
        const val ACTION_UPDATE = "com.openvehicles.OVMS.Update"
        const val ACTION_NOTIFICATION = "com.openvehicles.OVMS.Notification"
        const val ACTION_SENDCOMMAND = "com.openvehicles.OVMS.SendCommand"
        const val ACTION_COMMANDRESULT = "com.openvehicles.OVMS.CommandResult"

        // MP command response result codes:
        const val COMMAND_RESULT_OK = 0
        const val COMMAND_RESULT_FAILED = 1
        const val COMMAND_RESULT_UNSUPPORTED = 2
        const val COMMAND_RESULT_UNIMPLEMENTED = 3

        /**
         * Kustom support:
         * The KustomWidget App (KWGT) cannot read generic intent extras, data
         * needs to be sent as string arrays. See:
         * https://help.kustom.rocks/i195-send-variables-to-kustom-via-broadcast
         */
        const val KUSTOM_ACTION = "org.kustom.action.SEND_VAR"
        const val KUSTOM_ACTION_EXT_NAME = "org.kustom.action.EXT_NAME"
        const val KUSTOM_ACTION_VAR_NAME_ARRAY = "org.kustom.action.VAR_NAME_ARRAY"
        const val KUSTOM_ACTION_VAR_VALUE_ARRAY = "org.kustom.action.VAR_VALUE_ARRAY"

        /**
         * convertUserCommand: convert user command input to the corresponding MP command
         *
         * User input syntax support:
         * - MMI/USSD commands: prefix "*", example: "*100#"
         * - Modem commands:    prefix "@", example: "@ATI"
         * - MP MSG commands:   prefix "#", example: "#31"
         * - Text commands:     everything else, example: "stat"
         *
         * @param userCmd - user command input
         * @return MP command
         */
        fun makeMsgCommand(userCmd: String?): String {
            return if (userCmd == null || userCmd.isEmpty()) {
                ""
            } else if (userCmd.startsWith("*")) {
                // MMI/USSD command
                "41,$userCmd"
            } else if (userCmd.startsWith("@")) {
                // Modem command
                "49," + userCmd.substring(1)
            } else if (userCmd.startsWith("#")) {
                // MSG protocol command
                userCmd.substring(1)
            } else {
                // Text (former SMS) command
                "7,$userCmd"
            }
        }

        fun getCommandName(cmdCode: Int): String {
            // TODO: localize command names?
            return when (cmdCode) {
                1 -> "Get Features"
                2 -> "Set Feature"
                3 -> "Get Parameters"
                4 -> "Set Parameter"
                5 -> "Reboot"
                6 -> "Status"
                7 -> "Command"
                10 -> "Set Charge Mode"
                11 -> "Start Charge"
                12 -> "Stop Charge"
                15 -> "Set Charge Current"
                16 -> "Set Charge Parameters"
                17 -> "Set Charge Timer"
                18 -> "Wakeup Car"
                19 -> "Wakeup Subsystem"
                20 -> "Lock Car"
                21 -> "Set Valet Mode"
                22 -> "Unlock Car"
                23 -> "Clear Valet Mode"
                24 -> "Home Link"
                25 -> "Cooldown"
                30 -> "Get Usage"
                31 -> "Get Data Summary"
                32 -> "Get Data Records"
                40 -> "Send SMS"
                41 -> "Send MMI/USSD"
                49 -> "Modem Command"
                else -> "#$cmdCode"
            }
        }

        fun hasMultiRowResponse(cmdCode: Int): Boolean {
            return cmdCode == 1 || cmdCode == 3 || cmdCode == 30 || cmdCode == 31 || cmdCode == 32
        }

        fun sendKustomBroadcast(context: Context, srcIntent: Intent) {
            val extras = srcIntent.extras ?: return

            // create Kustom intent:
            val kIntent = Intent(KUSTOM_ACTION)
            kIntent.putExtra(KUSTOM_ACTION_EXT_NAME, "ovms")

            // create string arrays from extras:
            val names = ArrayList<String>(extras.size())
            val values = ArrayList<String>(extras.size())
            for (key in extras.keySet()) {
                val value = extras[key]
                if (value == null) {
                    names.add(key)
                    values.add("")
                } else if (value.javaClass.isArray) {
                    // unroll arrays by adding index suffix:
                    val cnt = Array.getLength(value)
                    names.add(key + "_cnt")
                    values.add("" + cnt)
                    for (i in 0 until cnt) {
                        names.add(key + "_" + (i + 1))
                        val elem = Array.get(value, i)
                        if (elem != null) values.add(elem.toString())
                    }
                } else {
                    names.add(key)
                    values.add(value.toString())
                }
            }

            // send to Kustom:
            val nameArray = names.toTypedArray<String>()
            val valueArray = values.toTypedArray<String>()
            kIntent.putExtra(KUSTOM_ACTION_VAR_NAME_ARRAY, nameArray)
            kIntent.putExtra(KUSTOM_ACTION_VAR_VALUE_ARRAY, valueArray)
            context.sendBroadcast(kIntent)
        }
    }

    inner class ApiBinder : Binder() {
        val service: ApiService
            get() = this@ApiService
    }

    private inner class ApiServiceHandler(looper: Looper?) : Handler(looper!!) {
        override fun handleMessage(msg: Message) {
            handleIntent(msg.obj as Intent)
        }
    }

    inner class ApiEvent(event: String?) : Intent(ACTION_APIEVENT) {

        init {
            setPackage(packageName)
            putExtra("event", event)
            putExtra("isOnline", isOnline())
            putExtra("isLoggedIn", isLoggedIn())
        }

        constructor(event: String?, detail: Serializable?) : this(event) {
            putExtra("detail", detail)
        }

        fun send() {
            sendBroadcast(this)
        }
    }

}