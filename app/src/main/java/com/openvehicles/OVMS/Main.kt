package com.openvehicles.OVMS

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@SuppressLint("NewApi")
class Main(
    var context: Context
) {

    private var packageInfo: PackageInfo? = null
    private var metaData: Bundle? = null
    private var rotation: Animation

    private val userAgent: String
        get() = ("OVMS/${packageInfo!!.versionName}-${packageInfo!!.versionCode} Android/${Build.VERSION.RELEASE}")

    init {
        try {
            val ai = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            metaData = ai.metaData
            packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        rotation = AnimationUtils.loadAnimation(
            context,
            R.anim.clockwise_rotation
        )
        rotation.repeatCount = Animation.INFINITE
        setStrictMode()
    }

    @Throws(IOException::class)
    fun getURLConnection(url: String): HttpURLConnection {
        // create connection:
        val urlObj = URL(url)
        val connection = urlObj.openConnection() as HttpURLConnection

        // set standard props:
        connection.setRequestProperty("User-Agent", userAgent)
        connection.allowUserInteraction = false
        connection.useCaches = false
        connection.instanceFollowRedirects = true
        connection.connectTimeout = 30 * 1000
        connection.readTimeout = 120 * 1000

        // auto specialize by URL:
        if (url.contains("api.openchargemap.io")) {
            val apiKey = metaData!!.getString("org.openchargemap.api.v2.API_KEY")
            Log.d(TAG, "getURLConnection: using API key for openchargemap: $apiKey")
            if (apiKey != null) connection.setRequestProperty("X-API-Key", apiKey) else Log.e(
                TAG,
                "getURLConnection: missing API key for openchargemap"
            )
        }
        return connection
    }

    @Throws(IOException::class)
    fun getURLInputStream(url: String): InputStream {
        val connection = getURLConnection(url)
        return connection.inputStream
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    private fun setStrictMode() {
        val sdkInt = Build.VERSION.SDK_INT
        if (sdkInt > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
    }

    fun getJSONObject(url: String): JSONObject? {
        var json: JSONObject? = null
        try {
            val `in` = getURLInputStream(url)
            val reader = BufferedReader(InputStreamReader(`in`))
            val data = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                data.append(line)
            }
            try {
                json = JSONObject(data.toString())
            } catch (e: JSONException) {
                Log.e(TAG, "getJSONObject: url='" + url + "' → " + e.message)
                e.printStackTrace()
            }
        } catch (e: Exception) {
            Log.e(TAG, "getJSONObject: url='" + url + "' → " + e.message)
            e.printStackTrace()
        }

        return json
    }

    /*
     * Inner types
     */

    companion object {

        private const val TAG = "Main"
    }
}
