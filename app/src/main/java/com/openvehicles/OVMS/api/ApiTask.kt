package com.openvehicles.OVMS.api

import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.util.Base64
import android.util.Log
import com.openvehicles.OVMS.entities.CarData
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.math.BigInteger
import java.net.Socket
import java.net.UnknownHostException
import java.security.cert.X509Certificate
import java.util.Arrays
import java.util.Date
import java.util.Locale
import java.util.Random
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ApiTask(

    private var context: Context,
    private var carData: CarData,
    private var listener: ApiTaskListener

) : AsyncTask<Void, Any, Void>() {


    private var socket: Socket? = null
    private lateinit var txCipher: Cipher
    private lateinit var rxCipher: Cipher
    private lateinit var pmCipher: Cipher
    private lateinit var pmDigestBuf: ByteArray
    private var outputstream: PrintWriter? = null
    private var inputstream: BufferedReader? = null
    private var outputLock = Semaphore(1)
    private var isLoggedIn = false
    private val random = Random()
    private var pingTimer: Timer? = null


    /**
     * Status check for ApiService
     *
     * @return true if connection to server is currently established
     */
    fun isLoggedIn(): Boolean {
        return status == Status.RUNNING && isLoggedIn && !isCancelled
    }

    override fun onProgressUpdate(vararg param: Any) {
        val state = param[0] as MsgType
        when (state) {
            MsgType.UPDATE -> {
                val msgData = if (param.size >= 3) param[2] as String else ""
                listener.onUpdateStatus(param[1] as Char, msgData)
            }

            MsgType.LOGIN_BEGIN -> listener.onLoginBegin()
            MsgType.LOGIN_COMPLETE -> listener.onLoginComplete()
            MsgType.ERROR -> listener.onServerSocketError(param[1] as Throwable)
            MsgType.COMMAND -> listener.onResultCommand(param[1] as String)
            MsgType.PUSH_NOTIFICATION -> listener.onPushNotification(
                param[1] as Char,
                param[2] as String
            )
        }
    }

    /**
     * ApiTask main entry.
     *
     * @param params -- unused
     * @return null
     */
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Void?): Void? {
        var rx: String
        var msg: String
        var line: String?

        // Schedule server ping message every...
        val pingPeriod = 5 // ...minutes:
        pingTimer = Timer()
        pingTimer!!.schedule(object : TimerTask() {
            override fun run() {
                ping()
            }
        }, (pingPeriod * 60 * 1000).toLong(), (pingPeriod * 60 * 1000).toLong())

        // Main Task loop:
        while (!isCancelled) {
            try {

                // (re-)open socket connection
                if (!connInit()) {
                    // non recoverable error, terminate ApiTask:
                    break
                }

                // Enter main RX loop:
                while (!isCancelled && socket != null && socket!!.isConnected) {

                    // Read & decrypt message:
                    line = inputstream!!.readLine()
                    if (line == null) throw IOException("Connection lost")
                    rx = line.trim { it <= ' ' }
                    msg = String(rxCipher.update(Base64.decode(rx, 0))).trim { it <= ' ' }
                    Log.d(TAG, String.format("RX: %s (%s)", msg, rx))

                    // Process message:
                    if (msg.startsWith("MP-0 ")) {
                        // is valid message (for protocol version 0):
                        handleMessage(msg.substring(5))
                    } else {
                        Log.w(TAG, "Unknown protection scheme")
                        // sleep for 100 ms to prevent DoS by invalid data
                        try {
                            Thread.sleep(100)
                        } catch (e: InterruptedException) {
                            // ignore
                        }
                    }
                }
            } catch (e: IOException) {
                // Reader closed or I/O error
                publishProgress(MsgType.ERROR, e)
            }
            if (isCancelled) {
                break
            }

            //
            // Connection lost:
            //
            Log.d(TAG, "Lost connection")
            synchronized(this@ApiTask) {
                isLoggedIn = false

                // Cleanup Socket:
                if (socket != null && socket!!.isConnected) {
                    try {
                        socket!!.close()
                    } catch (ex: Exception) {
                        // ignore
                    }
                }
                socket = null
            }

            // Wait 3 seconds before reconnect:
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                // ignore
            }
        } // while (!isCancelled())

        // Shut down ApiTask:
        Log.d(TAG, "Terminating AsyncTask")

        // Terminate ping Thread:
        pingTimer!!.cancel()
        pingTimer = null
        return null
    }

    /**
     * Send connection keepalive ping to server.
     */
    fun ping() {
        if (isLoggedIn) {
            Log.d(TAG, "Sending ping")
            sendMessage("MP-0 A")
        }
    }

    /**
     * Send protocol message to server. See OVMS protocol PDF for details.
     * Asynchronous reply will be handled by handleMessage().
     * Example: request feature list: sendCommand("MP-0 C1")
     *
     * @param message -- complete protocol message with header
     * @return true if command has been sent, false on error
     */
    fun sendMessage(message: String): Boolean {
        if (!isLoggedIn) {
            Log.e(TAG, "TX: Server not ready. TX aborted.")
            return false
        }

        // Network activity shall not run on the UI thread, so we start a
        //	thread for the transmission and serialize concurrent transmissions
        //	using a semaphore:
        try {
            if (!outputLock.tryAcquire(1000, TimeUnit.MILLISECONDS)) throw TimeoutException()
        } catch (e: Exception) {
            Log.e(TAG, "TX: Socket unavailable:$e")
            return false
        }
        Thread {
            try {
                Log.i(TAG, "TX: $message")
                outputstream!!.println(
                    Base64.encodeToString(
                        txCipher.update(message.toByteArray()),
                        Base64.NO_WRAP
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "TX: $e")
                publishProgress(MsgType.ERROR, e)
            }
            outputLock.release()
        }.start()
        return true
    }

    /**
     * Try to connect to the OVMS server using the current car credentials.
     * Sets isLoggedIn accordingly.
     *
     * Publishes progress messages:
     * - msgLoginBegin
     * - msgLoginComplete
     * - msgError
     *
     * @return
     * false: a non recoverable error is detected (like wrong host or login)
     * true: connection established (isLoggedIn==true) or caller shall retry (isLoggedIn==false)
     */
    @Synchronized
    private fun connInit(): Boolean {
        Log.d(TAG, "connInit() requested")
        isLoggedIn = false

        // Check if some network is available:
        if (!isOnline()) {
            Log.i(TAG, "No network connection available")
            return true // temporary error, retry
        }
        publishProgress(MsgType.LOGIN_BEGIN)

        // Get car login credentials:
        val vehicleID = carData.sel_vehicleid
        val sharedSecret = carData.sel_server_password
        val useTls = carData.sel_tls
        val trustAllCerts = carData.sel_tls_trust_all
        val serverPort = if (useTls) 6870 else 6867

        // Generate session client token
        val b64tabString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
        val b64tab = b64tabString.toCharArray()
        var clientTokenString = ""
        for (cnt in 0..21) {
            clientTokenString += b64tab[random.nextInt(b64tab.size - 1)]
        }
        val clientToken = clientTokenString.toByteArray()
        try {
            // Open TCP connection to server port:
            if (useTls) {
                val factory = if (trustAllCerts) {
                    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                        override fun getAcceptedIssuers(): Array<X509Certificate>? = null
                        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
                        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
                    })
                    val sslContext = SSLContext.getInstance("TLS")
                    sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                    sslContext.socketFactory
                } else {
                    javax.net.ssl.SSLSocketFactory.getDefault()
                }
                val sslSocket = factory.createSocket(carData.sel_server, serverPort) as SSLSocket
                if (!trustAllCerts) {
                    // Explicitly enable hostname verification for standard TLS connections
                    val params = sslSocket.sslParameters
                    params.endpointIdentificationAlgorithm = "HTTPS"
                    sslSocket.sslParameters = params
                }
                socket = sslSocket
            } else {
                socket = Socket(carData.sel_server, serverPort)
            }

            outputstream = PrintWriter(
                BufferedWriter(
                    OutputStreamWriter(
                        socket!!.getOutputStream()
                    )
                ), true
            )
            inputstream = BufferedReader(InputStreamReader(socket!!.getInputStream()))
            outputLock = Semaphore(1)

            // Encrypt password:
            val clientHmac = Mac.getInstance("HmacMD5")
            val sk = SecretKeySpec(
                sharedSecret.toByteArray(), "HmacMD5"
            )
            clientHmac.init(sk)
            val hashedBytes = clientHmac.doFinal(clientToken)
            val clientDigest = Base64.encodeToString(hashedBytes, Base64.NO_WRAP)

            // Login to server:
            Log.d(
                TAG,
                String.format("TX: MP-A 0 %s %s %s", clientTokenString, clientDigest, vehicleID)
            )
            outputstream!!.println(
                String.format(
                    "MP-A 0 %s %s %s",
                    clientTokenString,
                    clientDigest,
                    vehicleID
                )
            )

            // Get server welcome message:
            val serverWelcomeMsg: Array<String> = try {
                inputstream!!.readLine().trim { it <= ' ' }.split("[ ]+".toRegex())
                    .dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            } catch (e: Exception) {
                Log.e(TAG, "ERROR response server welcome message", e)
                publishProgress(MsgType.ERROR, e)
                return false // non recoverable error: vehicleID or password wrong
            }

            Log.d(
                TAG, String.format(
                    "RX: %s %s %s %s",
                    serverWelcomeMsg[0],
                    serverWelcomeMsg[1],
                    serverWelcomeMsg[2],
                    serverWelcomeMsg[3]
                )
            )

            // Check hash:
            val serverTokenString = serverWelcomeMsg[2]
            val serverToken = serverTokenString.toByteArray()
            val serverDigest = Base64.decode(serverWelcomeMsg[3], 0)
            if (!Arrays.equals(clientHmac.doFinal(serverToken), serverDigest)) {
                // server hash failed
                Log.d(
                    TAG, String.format(
                        "Server authentication failed. Expected %s Got %s",
                        Base64.encodeToString(
                            clientHmac
                                .doFinal(serverWelcomeMsg[2].toByteArray()),
                            Base64.NO_WRAP
                        ), serverWelcomeMsg[3]
                    )
                )
                publishProgress(MsgType.ERROR, Exception("Server authentication failed"))
                return false // non recoverable error
            } else {
                Log.d(TAG, "Server authentication OK.")
            }

            // generate client_key
            val serverClientToken = serverTokenString + clientTokenString
            val clientKey = clientHmac.doFinal(serverClientToken.toByteArray())
            Log.d(
                TAG, String.format(
                    "Client version of the shared key is %s - (%s) %s",
                    serverClientToken, toHex(clientKey).lowercase(Locale.getDefault()),
                    Base64.encodeToString(clientKey, Base64.NO_WRAP)
                )
            )

            // generate ciphers
            rxCipher = Cipher.getInstance("RC4")
            rxCipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(clientKey, "RC4"))
            txCipher = Cipher.getInstance("RC4")
            txCipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(clientKey, "RC4"))

            // prime ciphers
            var primeData = ""
            for (cnt in 0..1023) primeData += "0"
            rxCipher.update(primeData.toByteArray())
            txCipher.update(primeData.toByteArray())

            // Connection established:
            Log.i(
                TAG,
                String.format(
                    "Connected to %s. Ciphers initialized. Listening...",
                    carData.sel_server
                )
            )
            isLoggedIn = true
            publishProgress(MsgType.LOGIN_COMPLETE)
            return true
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            publishProgress(MsgType.ERROR, e)
        } catch (e: IOException) {
            e.printStackTrace()
            publishProgress(MsgType.ERROR, e)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false // non recoverable error
    }

    /**
     * Process message received from server.
     * - update car model
     * - publish update message
     * See OVMS protocol PDF for details on message structure.
     *
     * @param msg -- the OVMS protocol message payload received (without header)
     */
    private fun handleMessage(msg: String) {
        Log.v(TAG, "handleMessage: $msg")
        var msgCode = msg[0]
        var msgData = msg.substring(1)
        if (msgCode == 'E') {
            // We have a paranoid mode message
            val innercode = msg[1]
            if (innercode == 'T') {
                // Set the paranoid token
                Log.v(TAG, "ET MSG Received: $msg")
                try {
                    val pmToken = msg.substring(2)
                    val pm_hmac = Mac.getInstance("HmacMD5")
                    val sk = SecretKeySpec(
                        carData.sel_server_password.toByteArray(), "HmacMD5"
                    )
                    pm_hmac.init(sk)
                    pmDigestBuf = pm_hmac.doFinal(pmToken.toByteArray())
                    Log.d(TAG, "Paranoid Mode Token Accepted. Entering Privacy Mode.")
                } catch (e: Exception) {
                    Log.e("ERR", e.message!!)
                    e.printStackTrace()
                }
            } else if (innercode == 'M') {
                // Decrypt the paranoid message
                Log.v(TAG, "EM MSG Received: $msg")
                msgCode = msg[2]
                msgData = msg.substring(3)
                val decodedCmd = Base64.decode(
                    msgData,
                    Base64.NO_WRAP
                )
                try {
                    pmCipher = Cipher.getInstance("RC4")
                    pmCipher.init(
                        Cipher.DECRYPT_MODE,
                        SecretKeySpec(
                            pmDigestBuf,
                            "RC4"
                        )
                    )

                    // prime ciphers
                    var primeData = ""
                    for (cnt in 0..1023) primeData += "0"
                    pmCipher.update(primeData.toByteArray())
                    msgData = String(pmCipher.update(decodedCmd))
                } catch (e: Exception) {
                    Log.d("ERR", e.message!!)
                    e.printStackTrace()
                }

                // notify main process of paranoid mode detection
                if (!carData.sel_paranoid) {
                    Log.d(TAG, "Paranoid Mode Detected")
                    carData.sel_paranoid = true
                    publishProgress(MsgType.UPDATE, msgCode, msgData)
                }
            }
        }
        Log.v(TAG, "$msgCode MSG Received: $msgData")
        when (msgCode) {
            'f' -> {
                val dataParts = msgData.split(",\\s*".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                if (dataParts.size >= 1) {
                    Log.v(TAG, "f MSG Validated")
                    carData.server_firmware = dataParts[0]
                    publishProgress(MsgType.UPDATE, msgCode, msgData)
                }
            }

            'Z' -> try {
                carData.server_carsconnected = msgData.toInt()
                publishProgress(MsgType.UPDATE, msgCode, msgData)
            } catch (e: Exception) {
                Log.w(TAG, "Z MSG Invalid")
            }

            'T' -> if (msgData.isNotEmpty()) {
                carData.car_lastupdate_raw = msgData.toLong()
                carData.car_lastupdated =
                    Date(System.currentTimeMillis() - carData.car_lastupdate_raw * 1000)
                publishProgress(MsgType.UPDATE, msgCode, msgData)
            } else Log.w(TAG, "T MSG Invalid")

            'a' -> {
                Log.d(TAG, "Server acknowledged ping")
                // No model change, but publish for "last updated" displays:
                publishProgress(MsgType.UPDATE, msgCode, msgData)
            }

            'c' -> {
                Log.d(TAG, "Command response received: $msgData")
                publishProgress(MsgType.COMMAND, msgData)
            }

            'P' -> {
                Log.i(TAG, "Push notification received: $msgData")
                publishProgress(MsgType.PUSH_NOTIFICATION, msgData[0], msgData.substring(1))
            }

            'F' -> if (carData.processFirmware(msgData)) publishProgress(
                MsgType.UPDATE,
                msgCode,
                msgData
            ) else Log.w(TAG, "F MSG Invalid")

            'V' -> if (carData.processCapabilities(msgData)) publishProgress(
                MsgType.UPDATE,
                msgCode,
                msgData
            ) else Log.w(TAG, "V MSG Invalid")

            'S' -> if (carData.processStatus(msgData)) publishProgress(
                MsgType.UPDATE,
                msgCode,
                msgData
            ) else Log.w(TAG, "S MSG Invalid")

            'L' -> if (carData.processLocation(msgData)) publishProgress(
                MsgType.UPDATE,
                msgCode,
                msgData
            ) else Log.w(TAG, "L MSG Invalid")

            'D' -> if (carData.processEnvironment(msgData)) publishProgress(
                MsgType.UPDATE,
                msgCode,
                msgData
            ) else Log.w(TAG, "D MSG Invalid")

            'W' -> if (carData.processOldTPMS(msgData)) publishProgress(
                MsgType.UPDATE,
                msgCode,
                msgData
            ) else Log.w(TAG, "W MSG Invalid")

            'X' -> if (carData.processGen(msgData)) publishProgress(
                MsgType.UPDATE,
                msgCode,
                msgData
            ) else Log.w(TAG, "X MSG Invalid")

            'Y' -> if (carData.processNewTPMS(msgData)) publishProgress(
                MsgType.UPDATE,
                msgCode,
                msgData
            ) else Log.w(TAG, "Y MSG Invalid")

            else -> {
                Log.w(TAG, "Unhandled message received: $msgCode$msgData")
                // forward to listeners, maybe this is a custom message:
                publishProgress(MsgType.UPDATE, msgCode, msgData)
            }
        }
    }

    private fun toHex(bytes: ByteArray): String {
        val bi = BigInteger(1, bytes)
        return String.format("%0" + (bytes.size shl 1) + "X", bi)
    }

    /**
     * Check for network availability.
     *
     * @return true if any network is available
     */
    fun isOnline(): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnectedOrConnecting
    }

    /*
     * Inner types
     */

    companion object {

        private const val TAG = "ApiTask"

    }

    /**
     * Progress Update messaging (communication with ApiService)
     */
    private enum class MsgType {
        UPDATE,
        ERROR,
        COMMAND,
        LOGIN_BEGIN,
        LOGIN_COMPLETE,
        PUSH_NOTIFICATION
    }

    // Interface to ApiService:
    interface ApiTaskListener {
        fun onUpdateStatus(msgCode: Char, msgText: String?)
        fun onServerSocketError(e: Throwable?)
        fun onResultCommand(cmd: String?)
        fun onLoginBegin()
        fun onLoginComplete()
        fun onPushNotification(msgClass: Char, msgText: String?)
    }

}