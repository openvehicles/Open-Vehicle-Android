package com.openvehicles.OVMS.entities

import android.content.Context
import android.util.Log
import com.openvehicles.OVMS.BaseApp.Companion.app
import com.openvehicles.OVMS.api.ApiService
import com.openvehicles.OVMS.api.ApiService.Companion.hasMultiRowResponse
import com.openvehicles.OVMS.api.OnResultCommandListener

/**
 * Created by balzer on 09.03.15.
 *
 * This class takes care of execution of a series of commands.
 * All results will be stored in the Cmd.results arrays.
 * Execution stops on command errors (except empty history records).
 *
 * The Listener can react to progress and finish events.
 * Return code -1 on finish means execution was cancelled.
 *
 * See LogsFragment and BatteryFragment for complete usage examples.
 *
 */
class CmdSeries(

    private val context: Context?,
    private val service: ApiService?,
    private val listener: Listener?

) : OnResultCommandListener {

    private val cmdList: ArrayList<Cmd> = ArrayList()
    private var current: Int = -1

    constructor(service: ApiService?, listener: Listener?) : this(app, service, listener)

    fun size(): Int {
        return cmdList.size
    }

    operator fun get(i: Int): Cmd {
        return cmdList[i]
    }

    /**
     * Add a command to be executed to the series.
     * The command will be added at the end.
     *
     * Example:
     * CmdSeries series = new CmdSeries(...)
     * .add("Setting feature #8 to 1...", "2,8,1")
     * .add("Setting param #11 to abc...", "4,11,abc");
     *
     * @param pMessage -- user message to display & log
     * @param pCommand -- command string
     * @return -- CmdSeries for daisy chaining
     */
    fun add(pMessage: String?, pCommand: String): CmdSeries {
        val cmd = Cmd(this)
        cmd.message = pMessage
        cmd.command = pCommand
        val code = pCommand.split(",".toRegex(), limit = 2).toTypedArray()[0]
        cmd.commandCode = code.toInt()
        cmdList.add(cmd)
        return this // for daisy chaining
    }

    fun add(pMessageId: Int, pCommand: String): CmdSeries {
        return add(context!!.getString(pMessageId), pCommand)
    }

    /**
     * Get current command.
     *
     * @return -- current command or null if series is not running
     */
    fun getCurrent(): Cmd? {
        return if (current >= 0 && current < cmdList.size) cmdList[current] else null
    }

    /**
     * Advance execution to next command in series.
     *
     * @return -- next command or null if end of series
     */
    fun getNext(): Cmd? {
        current += 1
        return getCurrent()
    }

    /**
     * Start execution of series at first command scheduled.
     *
     * @return -- this CmdSeries for daisy chaining
     */
    fun start(): CmdSeries {
        Log.v(TAG, "started")
        current = -1
        executeNext()
        return this
    }

    /**
     * Execution handler: sends the current command to the server.
     * The command position in the series is told to the listener as the main progress.
     */
    private fun executeNext() {
        if (service == null || !service.isLoggedIn()) return
        val cmd = getNext()
        if (cmd != null) {
            // send next command:
            Log.v(TAG, "executeNext: " + cmd.message + ": cmd=" + cmd.command)
            listener?.onCmdSeriesProgress(cmd.message, cmd.pos(), cmd.posCnt(), 0, 0)
            service.sendCommand(cmd.command!!, this)
        } else {
            // series finished:
            service.cancelCommand(this)
            listener?.onCmdSeriesFinish(this, 0)
        }
    }

    /**
     * Result handler. Adds a cmd result matching the current command
     * to the command results. On success, the next command will be executed,
     * on failure the series aborts.
     *
     * Commands 1,3,30-32: multiple results are handled by checking the
     * result records count and position. Error "no historical messages"
     * is handled as a normal result (no failure = no abort). The record
     * count/position are told to the listener as sub step progress.
     *
     * @param result -- return string received from server
     */
    override fun onResultCommand(result: Array<String>) {
        if (result.size < 2) return
        val commandCode = result[0].toInt()
        val returnCode = result[1].toInt()
        val returnText = if (result.size > 2) result[2] else ""

        // check command:
        val cmd = getCurrent()
        if (cmd == null) {
            // we're not active, cancel subscription:
            service!!.cancelCommand(this)
            return
        } else if (cmd.commandCode != commandCode) {
            // not for us:
            return
        }

        // store result:
        cmd.returnCode = returnCode
        cmd.results.add(result)
        Log.v(
            TAG, "onResult: " + cmd.message + " / key=" + cmd.commandCode
                    + " => returnCode=" + cmd.returnCode
        )
        if (hasMultiRowResponse(commandCode)) {
            // multiple result command:
            if (returnText == "No historical data available") {
                // no records: continue without error
                executeNext()
            } else if (returnCode != 0) {
                // error: stop execution
                Log.e(
                    TAG,
                    "ABORT: cmd failed: key=" + cmd.commandCode + " => returnCode=" + cmd.returnCode
                )
                service!!.cancelCommand(this)
                listener?.onCmdSeriesFinish(this, returnCode)
            } else {
                // success: check record count
                var recNr = result[2].toInt()
                val recCnt = result[3].toInt()
                if (commandCode <= 3) recNr++ // fix record numbering for feature & param lists
                if (recNr == recCnt) {
                    // got all records
                    executeNext()
                } else {
                    // update progress sub step:
                    listener?.onCmdSeriesProgress(
                        cmd.message,
                        cmd.pos(),
                        cmd.posCnt(),
                        recNr,
                        recCnt
                    )
                }
            }
        } else if (returnCode != 0) {
            // single result command error: stop execution
            Log.e(
                TAG,
                "ABORT: cmd failed: key=" + cmd.commandCode + " => returnCode=" + cmd.returnCode
            )
            service!!.cancelCommand(this)
            listener?.onCmdSeriesFinish(this, returnCode)
        } else if (commandCode == 41) {
            // ignore initial empty response
            if (!returnText.isEmpty()) executeNext()
        } else {
            // single result command success:
            executeNext()
        }
    }

    /**
     * Cancel series execution. Pending results will be ignored.
     * Triggers Listener callback onCmdSeriesFinish with result code -1.
     */
    fun cancel() {
        Log.v(TAG, "cancelled")
        service!!.cancelCommand(this)
        if (listener != null && current >= 0 && current < cmdList.size) {
            listener.onCmdSeriesFinish(this, -1)
        }
    }

    /**
     * Get return code of current command
     * @return -- command return code (0..3)
     */
    fun getReturnCode(): Int {
        val cmd = getCurrent()
        return cmd?.returnCode ?: 0
    }

    /**
     * Get user message for current command
     * @return -- message string
     */
    fun getMessage(): String? {
        val cmd = getCurrent()
        return if (cmd != null) cmd.message else ""
    }

    /**
     * Get optional error detail string returned by the module if available.
     * (On return code 1-3 the command may supply a detail message, see protocol)
     * @return -- error detail description or ""
     */
    fun getErrorDetail(): String {
        val cmd = getCurrent() ?: return ""
        val result = if (cmd.results.size > 0) cmd.results[cmd.results.size - 1] else null
        return if (result != null && result.size >= 3) result[2] else ""
    }

    /*
     * Inner types
     */

    companion object {

        @Transient
        private val TAG = "CmdSeries"
    }

    interface Listener {

        /**
         * Progress callback.
         *
         * @param message -- user message for current command
         * @param pos -- position of current command
         * @param posCnt -- size of command series
         * @param step -- on multiple results: record position (else 0)
         * @param stepCnt -- on multiple results: record count (else 0)
         */
        fun onCmdSeriesProgress(message: String?, pos: Int, posCnt: Int, step: Int, stepCnt: Int)

        /**
         * Success / abort callback.
         *
         * @param cmdSeries -- the series
         * @param returnCode -- last result code or -1 on abort
         */
        fun onCmdSeriesFinish(cmdSeries: CmdSeries, returnCode: Int)
    }

    /**
     * Single command entry of the series
     */
    inner class Cmd(private val series: CmdSeries) {

        /** User message & command specification  */
        var message: String? = null
        @JvmField
        var command: String? = null
        @JvmField
        var commandCode = 0

        /** Last return code  */
        var returnCode = 0

        /** All results collected  */
        @JvmField
        var results: ArrayList<Array<String>> = ArrayList()

        fun pos(): Int {
            return series.current
        }

        fun posCnt(): Int {
            return series.cmdList.size
        }
    }
}
