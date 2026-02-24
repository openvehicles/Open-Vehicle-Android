package com.openvehicles.OVMS.ui

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.JsonReader
import com.openvehicles.OVMS.Main
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.ChargePoint
import com.openvehicles.OVMS.ui.utils.Database
import com.openvehicles.OVMS.utils.AppPrefs
import java.io.IOException
import java.io.InputStreamReader

// This is the OCM API fetch background job
class GetMapDetails(
    private var appContext: Context,
    center: LatLng,
    invoker: GetMapDetailsListener
) : AsyncTask<Void?, Void?, Void?>() {

    private var main: Main = Main(appContext)
    private var appPrefs: AppPrefs = AppPrefs(appContext, "ovms")
    private var database: Database = Database(appContext)
    private var center: LatLng
    private var invoker: GetMapDetailsListener
    private var gson: Gson
    private var chargePoints: ArrayList<ChargePoint>
    private var error: Exception?

    init {
        this.center = center
        this.invoker = invoker
        gson = Gson()
        chargePoints = ArrayList()
        error = null
    }

    override fun onCancelled(result: Void?) {
        Log.d(TAG, "cancelled")
    }

    override fun doInBackground(vararg params: Void?): Void? {
        if (isCancelled) {
            return null
        }

        // read from OCM:
        try {
            getdata()
        } catch (e: IOException) {
            e.printStackTrace()
            error = e
        }

        // update database:
        database.beginWrite()
        var i = 0
        while (!isCancelled && i < chargePoints.size) {
            database.insertMapDetails(chargePoints[i])
            i++
        }
        database.endWrite(true)
        Log.d(
            TAG,
            "saved $i chargepoints to database, lastUpdate=${database.getDateLastStatusUpdate()}"
        )
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)

        if (error != null) {
            Toast.makeText(
                appContext,
                appContext.getString(R.string.ocm_read_failed, error!!.localizedMessage),
                Toast.LENGTH_LONG
            ).show()
            invoker.getMapDetailsDone(false, center)
        } else {
            invoker.getMapDetailsDone(true, center)
        }
        database.close()
    }

    @Throws(IOException::class)
    private fun getdata() {
        // Make OCM API URL:
        // As OCM does not yet support incremental queries,
        // we're using a cache with key = int(lat/lng)
        // resulting in a tile size of max. 112 x 112 km
        // = diagonal max 159 km
        // The API call will fetch a fixed radius of 160 km
        // covering all adjacent tiles.
        val maxResults = appPrefs.getData("maxresults")
        val lastStatusUpdate = database.getDateLastStatusUpdate(center)
        val url = ("https://api.openchargemap.io/v2/poi/?output=json&verbose=false"
                + "&latitude=" + center.latitude
                + "&longitude=" + center.longitude
                + "&distance=160" // see above
                + "&distanceunit=KM"
                + "&maxresults=" + if (maxResults == "") "500" else (maxResults
                + "&modifiedsince=" + lastStatusUpdate.replace(' ', 'T')))
        Log.d(
            TAG, "getdata: fetching @" + center.latitude + "," + center.longitude
                    + " => url=" + url
        )

        // open URL for JSON parser:
        val urlInputStream = main.getURLInputStream(url)
        val reader = JsonReader(InputStreamReader(urlInputStream, "UTF-8"))

        // read charge points array:
        reader.beginArray()
        while (!isCancelled && reader.hasNext()) {
            try {
                val chargePoint = gson.fromJson<ChargePoint>(reader, ChargePoint::class.java)
                chargePoints.add(chargePoint)
            } catch (e: JsonIOException) {
                e.printStackTrace()
                break
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
            }
        }
        reader.endArray()
        reader.close()
        Log.d(TAG, "getdata: read " + chargePoints.size + " chargepoints")
    }

    /*
     * Inner types
     */

    companion object {
        private const val TAG = "GetMapDetails"
    }
}
