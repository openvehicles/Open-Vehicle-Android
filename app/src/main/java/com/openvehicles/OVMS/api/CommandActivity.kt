package com.openvehicles.OVMS.api

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.LeadingMarginSpan
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.CmdSeries
import com.openvehicles.OVMS.utils.AppPrefes
import com.openvehicles.OVMS.ui.ApiActivity
import com.openvehicles.OVMS.ui.utils.Database
import com.openvehicles.OVMS.utils.CarsStorage
import com.openvehicles.OVMS.utils.NotificationData
import com.openvehicles.OVMS.utils.Sys
import java.util.Arrays
import java.util.Date
import java.util.LinkedList
import java.util.Queue

/**
 * CommandActivity is registered to receive command intents meant for interactive execution,
 * i.e. StoredCommand bookmark execution Intents or command broadcast from third party
 * Apps like Automagic or Tasker (Intent action com.openvehicles.OVMS.action.COMMAND).
 *
 * Intent extras expected/supported:
 * - vehicleid (optional)
 * - apikey or (with sel_vehicleid) password
 * - title (optional)
 * - command (optional, no command means only change the car)
 *
 * Commands need to be in user input syntax:
 * 	- MMI/USSD commands: prefix "*", example: "*100#"
 * 	- Modem commands:    prefix "@", example: "@ATI"
 * 	- MP MSG commands:   prefix "#", example: "#31"
 * 	- Text commands:     everything else, example: "stat"
 *
 * Command results are shown in an overlay (dialog style) window for DISPLAY_TIMEOUT_SECONDS,
 * or until the user clicks on the text. Scrolling or holding the display stops the timeout.
 * Result rows exceeding DISPLAY_MAXROWS+5 will be omitted.
 */
class CommandActivity : ApiActivity(), CmdSeries.Listener {

    private var appPrefes: AppPrefes? = null
    private var database: Database? = null

    private val commandQueue: Queue<Intent> = LinkedList()

    private var currentIntent: Intent? = null
    private var vehicleId: String? = null
    private var title: String? = null
    private var command: String? = null
    private var msgCommand: String? = null
    private var msgCommandCode = 0
    private var cmdSeries: CmdSeries? = null

    private var progressBar: ProgressBar? = null
    private var textViewMessage: TextView? = null

    private val timeoutHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        if (intent == null) {
            finish()
            return
        }
        Log.d(TAG, "onCreate " + Sys.toString(intent.extras))
        appPrefes = AppPrefes(this, "ovms")
        database = Database(applicationContext)

        // Create UI:
        progressBar = ProgressBar(this)
        progressBar!!.setPadding(20, 10, 20, 20)
        progressBar!!.isIndeterminate = true
        textViewMessage = TextView(this)
        textViewMessage!!.setPadding(20, 10, 20, 20)
        if (appPrefes!!.getData("notifications_font_monospace") == "on") {
            textViewMessage!!.setTypeface(Typeface.MONOSPACE)
        }
        try {
            val fontSize = appPrefes!!.getData("notifications_font_size").toFloat()
            textViewMessage!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        } catch (ignore: Exception) {
            // keep default font size
        }
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.addView(progressBar)
        linearLayout.addView(textViewMessage)
        val scrollView = ScrollView(this)
        scrollView.addView(linearLayout)
        setContentView(scrollView)
        queueCommand(intent)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")

        // Stop command execution and clear queue:
        if (cmdSeries != null) {
            cmdSeries!!.cancel()
            cmdSeries = null
        }
        currentIntent = null
        commandQueue.clear()
        stopFinishTimeout()
        database!!.close()
        super.onDestroy()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent " + Sys.toString(intent.extras))
        queueCommand(intent)
    }

    private fun queueCommand(intent: Intent) {
        // Is there currently a command executing?
        if (currentIntent != null) {
            Log.d(TAG, "queueCommand: adding intent to queue")
            commandQueue.add(intent)
        } else {
            Log.d(TAG, "queueCommand: processing intent now")
            startCommand(intent)
        }
    }

    override fun onServiceAvailable(service: ApiService?) {
        Log.d(TAG, "onServiceAvailable $service")
        processCommand()
    }

    override fun onServiceLoggedIn(service: ApiService?, isLoggedIn: Boolean) {
        Log.d(TAG, "onServiceLoggedIn loggedin=$isLoggedIn")
        processCommand()
    }

    private fun processCommand() {
        if (currentIntent != null) {
            // Progress with current command:
            Log.d(TAG, "processCommand: progress with current command")
            startCommand(currentIntent!!)
        } else {
            Log.d(TAG, "processCommand: check for new command")
            checkQueue()
        }
    }

    private fun checkQueue() {
        val intent = commandQueue.poll()
        if (intent != null) {
            Log.d(TAG, "checkQueue: starting next command")
            startCommand(intent)
        } else {
            Log.d(TAG, "checkQueue: all commands done, finish activity")
            finish()
        }
    }

    private fun startCommand(intent: Intent) {
        if (currentIntent !== intent) {
            // Init new command:
            val apikey = intent.getStringExtra("apikey")
            val vehicleId = intent.getStringExtra("vehicleid")
            val vehiclePass = intent.getStringExtra("password")
            var title = intent.getStringExtra("title")
            val command = intent.getStringExtra("command")

            // Get vehicle config:
            val carApiKey = appPrefes!!.getData("APIKey")
            val carData = if (vehicleId != null && !vehicleId.isEmpty()) {
                CarsStorage.getCarById(vehicleId)
            } else {
                CarsStorage.getSelectedCarData()
            }

            // Configure job:
            currentIntent = intent
            if (cmdSeries != null) {
                Log.w(
                    TAG,
                    "startCommand: need to cancel previous command -- this should not happen"
                )
                cmdSeries!!.cancel()
            }
            cmdSeries = null
            this.vehicleId = if (carData != null) carData.sel_vehicleid else vehicleId
            title = title ?: ""
            this.command = command ?: ""
            msgCommand = ApiService.makeMsgCommand(this.command)
            msgCommandCode = if (!msgCommand!!.isEmpty()) {
                try {
                    msgCommand!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()[0].toInt()
                } catch (e: Exception) {
                    0
                }
            } else {
                0
            }
            if (title.isEmpty() && msgCommandCode > 0) {
                title = if (msgCommandCode == 7) {
                    this.command
                } else {
                    ApiService.getCommandName(msgCommandCode)
                }
            }

            // Set job title:
            val sb = StringBuilder()
            sb.append(this.vehicleId).append(": ").append(title)
            setTitle(sb.toString())
            Log.i(TAG, "startCommand: $sb → $msgCommand")

            // Check vehicle:
            if (carData == null) {
                Log.e(TAG, "startCommand: vehicle unknown: ${this.vehicleId}")
                showError(getString(R.string.err_vehicle_unknown))
                return
            }

            // Check authorization:
            if (apikey == null && vehiclePass == null || apikey != null && carApiKey != apikey || vehiclePass != null && carData.sel_server_password != vehiclePass) {
                Log.e(TAG, "startCommand: vehicle/authorization invalid")
                showError(getString(R.string.err_command_auth_failed))
                return
            }
        }

        // Check service & network:
        if (!hasService()) {
            Log.d(TAG, "startCommand: connecting service")
            showStatus(getString(R.string.msg_connecting_service))
            return
        }
        if (!isOnline) {
            Log.e(TAG, "startCommand: offline")
            showError(getString(R.string.err_offline))
            return
        }

        // Check selected car:
        val selectedCarId = CarsStorage.getLastSelectedCarId()
        if (!TextUtils.equals(selectedCarId, vehicleId)) {
            Log.d(TAG, "startCommand: changing car to $vehicleId")
            changeCar(vehicleId)
            showStatus(getString(R.string.msg_connecting_vehicle))
            return
        }

        // Check login status:
        if (!isLoggedIn) {
            Log.d(TAG, "startCommand: connecting vehicle")
            showStatus(getString(R.string.msg_connecting_vehicle))
            return
        }

        // Is there a command to run, or are we done after changing the car?
        if (command == null || command!!.isEmpty()) {
            Log.i(TAG, "startCommand: no command, done")
            showResult(getString(R.string.msg_ok))
            return
        }
        if (cmdSeries == null) {
            Log.i(TAG, "startCommand: connected, starting command execution")

            // Add command to notifications:
            database!!.addNotification(
                NotificationData(
                    NotificationData.TYPE_COMMAND,
                    Date(), "$vehicleId: $command", command!!
                )
            )
            sendBroadcast(
                Intent(ApiService.ACTION_NOTIFICATION)
                    .putExtra("onNotification", true)
            )

            // Start command:
            cmdSeries = CmdSeries(this, mApiService, this)
            cmdSeries!!.add(title, msgCommand!!)
            cmdSeries!!.start()
            val command = if (msgCommandCode == 7) {
                this.command
            } else {
                ApiService.getCommandName(msgCommandCode) + " (" + this.command + ")"
            }
            showStatus("> $command")
        }
    }

    override fun onCmdSeriesProgress(
        message: String?,
        pos: Int,
        posCnt: Int,
        step: Int,
        stepCnt: Int
    ) {
        // unused for now
    }

    override fun onCmdSeriesFinish(cmdSeries: CmdSeries, returnCode: Int) {
        Log.i(TAG, "onCmdSeriesFinish: command done returnCode=$returnCode")
        val title = "$vehicleId: $command"
        val text = SpannableStringBuilder()

        // Failure?
        if (returnCode != ApiService.COMMAND_RESULT_OK) {
            val errorDetail = this.cmdSeries!!.getErrorDetail()
            when (returnCode) {
                ApiService.COMMAND_RESULT_FAILED -> if (!errorDetail.isEmpty()) text.append(
                    getString(R.string.err_failed, errorDetail)
                ) else text.append(getString(R.string.err_command_failed))

                ApiService.COMMAND_RESULT_UNSUPPORTED -> text.append(getString(R.string.err_unsupported_operation))
                    .append('\n').append(errorDetail)

                ApiService.COMMAND_RESULT_UNIMPLEMENTED -> text.append(getString(R.string.err_unimplemented_operation))
                    .append('\n').append(errorDetail)

                else -> text.append(getString(R.string.err_command_cancelled)).append('\n')
                    .append(errorDetail)
            }
            val red = resources.getColor(R.color.colorTextError)
            text.setSpan(ForegroundColorSpan(red), 0, text.length, 0)

            // Add result to notifications list:
            if (returnCode != -1) {
                database!!.addNotification(
                    NotificationData(
                        NotificationData.TYPE_ERROR, Date(),
                        title, text.toString()
                    )
                )
                sendBroadcast(
                    Intent(ApiService.ACTION_NOTIFICATION)
                        .putExtra("onNotification", true)
                )
            }
            showError(text)
            return
        }

        // Success:
        val cmd = cmdSeries[0]
        val results = cmd.results

        // Collect results, skip empty lines:
        var rows = 0
        val maxRows = if (results.size <= DISPLAY_MAXROWS + 5) results.size else DISPLAY_MAXROWS
        var i = 0
        while (i < results.size) {
            val result = results[i]
            if (result.size < 3) {
                i++
                continue
            }
            val line = java.lang.String.join(",", *Arrays.copyOfRange(result, 2, result.size))
            if (line.isEmpty()) {
                i++
                continue
            }
            text.append(line.replace('\r', '\n')).append('\n')
            if (++rows == maxRows) {
                if (++i < results.size) text.append(
                    getString(
                        R.string.msg_more_rows_omitted,
                        results.size - i
                    )
                )
                break
            }
            i++
        }

        // If no text result was received, output "OK":
        if (rows == 0) {
            text.append(getString(R.string.msg_ok))
            text.setSpan(ForegroundColorSpan(Color.GREEN), 0, text.length, 0)
        } else {
            text.setSpan(LeadingMarginSpan.Standard(0, 50), 0, text.length, 0)
        }

        // Add result to notifications:
        val type =
            if (cmd.commandCode == 41) NotificationData.TYPE_USSD else NotificationData.TYPE_RESULT_SUCCESS
        database!!.addNotification(NotificationData(type, Date(), title, text.toString()))
        sendBroadcast(
            Intent(ApiService.ACTION_NOTIFICATION)
                .putExtra("onNotification", true)
        )

        // Display result:
        showResult(text)
    }

    private fun showStatus(text: CharSequence) {
        textViewMessage!!.text = text
        textViewMessage!!.setOnClickListener(null)
        textViewMessage!!.setOnLongClickListener(null)
        progressBar!!.visibility = View.VISIBLE
    }

    private fun showResult(text: CharSequence) {
        textViewMessage!!.text = text
        textViewMessage!!.setOnClickListener { v: View? -> finishCommand() }
        textViewMessage!!.setOnLongClickListener { v: View? -> true }
        progressBar!!.visibility = View.GONE
        startFinishTimeout()
    }

    private fun showError(text: CharSequence) {
        val sb = SpannableStringBuilder()
        sb.append(getString(R.string.Error))
        sb.append(": ")
        sb.append(text)
        val red = resources.getColor(R.color.colorTextError)
        sb.setSpan(ForegroundColorSpan(red), 0, sb.length, 0)
        showResult(text)
    }

    // Note: finishTimeout needs to be a Runnable for removeCallbacks() to work
    private val finishTimeout = Runnable { finishCommand() }

    private fun startFinishTimeout() {
        timeoutHandler.postDelayed(finishTimeout, (DISPLAY_TIMEOUT_SECONDS * 1000).toLong())
    }

    private fun stopFinishTimeout() {
        timeoutHandler.removeCallbacks(finishTimeout)
    }

    override fun onUserInteraction() {
        Log.d(TAG, "onUserInteraction")
        stopFinishTimeout()
    }

    override fun onUserLeaveHint() {
        Log.d(TAG, "onUserLeaveHint")
        stopFinishTimeout()
    }

    private fun finishCommand() {
        Log.d(TAG, "finishCommand")

        // Clear job:
        if (cmdSeries != null) {
            cmdSeries!!.cancel()
        }
        currentIntent = null
        cmdSeries = null
        vehicleId = null
        title = null
        command = null
        msgCommand = null
        msgCommandCode = 0

        // In case we've been called by user interaction:
        stopFinishTimeout()

        // See if there are more commands to run:
        checkQueue()
    }

    /*
     * Inner types
     */

    companion object {

        private const val TAG = "CommandActivity"

        private const val DISPLAY_MAXROWS = 10
        private const val DISPLAY_TIMEOUT_SECONDS = 5
    }

}