package com.openvehicles.OVMS.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatDialog
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.Main
import com.openvehicles.OVMS.ui.utils.Database
import org.json.JSONArray

class ConnectionList(
    context: Context,
    listener: ConnectionsListener,
    mayFetch: Boolean
) {

    var context: Context
    var listener: ConnectionsListener
    var main: Main
    var hList = ArrayList<HashMap<String, String>>()
    var al = ArrayList<String>()
    var alCheck = ArrayList<String>()
    var alId = ArrayList<String>()
    var database: Database
    var appPrefes: AppPrefes
    private var connectionListLoader: ConnectionListLoader? = null

    init {
        appPrefes = AppPrefes(context, "ovms")
        this.context = context
        this.listener = listener
        main = Main(context)
        database = Database(context)
        if (database._ConnectionTypes_Main.count == 0 && mayFetch) {
            connectionListLoader = ConnectionListLoader()
            connectionListLoader!!.execute()
        } else {
            connectionListLoader = null
            getList()
        }
    }

    fun sublist() {
        getList()
        val dialog = AppCompatDialog(context)
        dialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.connection_list)
        val adpt = ArrayAdapter(
            context,
            android.R.layout.simple_list_item_checked, al
        )
        val listView = dialog.findViewById<View>(android.R.id.list) as ListView?
        listView!!.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        listView.adapter = adpt
        for (i in alCheck.indices) {
            if (alCheck[i] == "true") {
                listView.setItemChecked(i, true)
            }
        }
        listView.onItemClickListener = OnItemClickListener { arg0, arg1, arg2, arg3 ->
            var title: String? = ""
            var tid = ""
            val selVehicleLabel = appPrefes.getData("sel_vehicle_label")
            val len = listView.count
            val checked = listView.checkedItemPositions
            database.beginWrite()
            database.resetConnectionTypesDetail(selVehicleLabel)
            for (i in 0 until len) {
                if (checked[i]) {
                    database.updateConnectionTypesDetail(
                        hList[i]["ID"], "true",
                        selVehicleLabel
                    )
                    if (tid == "") {
                        tid = alId[i]
                        title = hList[i]["Title"]
                    } else {
                        tid = tid + "," + alId[i]
                        title = title + "," + hList[i]["Title"]
                    }
                }
            }
            database.endWrite(true)
            database.close()
            appPrefes.saveData("Id", tid)
            listener.onConnectionChanged(tid, title)
        }
        dialog.show()
    }

    // TODO: Remove "@SuppressLint("Range")" and handle this properly
    @SuppressLint("Range")
    private fun getList() {
        al.clear()
        alId.clear()
        alCheck.clear()
        hList.clear()
        Log.d(
            "ConnectionList", "getlist: sel_vehicle_label="
                    + appPrefes.getData("sel_vehicle_label")
        )
        var cursor = database.get_ConnectionTypesdetails(
            appPrefes.getData("sel_vehicle_label")
        )
        if (cursor.count != 0) {
            while (cursor.moveToNext()) {
                al.add(cursor.getString(cursor.getColumnIndex("title")))
                alCheck.add(
                    cursor.getString(
                        cursor
                            .getColumnIndex("chec")
                    )
                )
                alId.add(cursor.getString(cursor.getColumnIndex("tId")))
                val hmap = HashMap<String, String>()
                hmap["ID"] = cursor.getString(cursor.getColumnIndex("Id"))
                hmap["Title"] = cursor.getString(
                    cursor
                        .getColumnIndex("title")
                )
                hmap["check"] = cursor.getString(cursor.getColumnIndex("chec"))
                hList.add(hmap)
            }
        } else {
            cursor.close()
            cursor = database._ConnectionTypes_Main
            while (cursor.moveToNext()) {
                al.add(cursor.getString(cursor.getColumnIndex("title")))
                alId.add(cursor.getString(cursor.getColumnIndex("tId")))
            }
            database.beginWrite()
            for (i in al.indices) {
                database.addConnectionTypesDetail(
                    alId[i], al[i],
                    "false", appPrefes.getData("sel_vehicle_label")
                )
            }
            database.endWrite(true)
            al.clear()
            alId.clear()
            alCheck.clear()
            hList.clear()
            val cursor1 = database.get_ConnectionTypesdetails(
                appPrefes
                    .getData("sel_vehicle_label")
            )
            while (cursor1.moveToNext()) {
                al.add(cursor1.getString(cursor1.getColumnIndex("title")))
                alCheck.add(
                    cursor1.getString(
                        cursor1
                            .getColumnIndex("chec")
                    )
                )
                alId.add(cursor1.getString(cursor1.getColumnIndex("tId")))
                val hmap = HashMap<String, String>()
                hmap["ID"] = cursor1.getString(cursor1.getColumnIndex("Id"))
                hmap["Title"] = cursor1.getString(
                    cursor1
                        .getColumnIndex("title")
                )
                hmap["check"] = cursor1.getString(cursor1.getColumnIndex("chec"))
                hList.add(hmap)
            }
            cursor1.close()
        }
        cursor.close()
        database.close()
    }

    /*
     * Inner types
     */

    companion object {
        private const val TAG = "ConnectionList"
    }

    inner class ConnectionListLoader : AsyncTask<Void?, Void?, Void?>() {

        override fun doInBackground(vararg params: Void?): Void? {
            try {
                val url = "https://api.openchargemap.io/v2/referencedata/"
                val jsonObject = main.getJSONObject(url)
                val connectionTypes = jsonObject?.getJSONArray("ConnectionTypes")
                    ?: JSONArray()
                for (i in 0 until connectionTypes.length()) {
                    val detail = connectionTypes.getJSONObject(i)
                    val hmap = HashMap<String, String>()
                    hmap["ID"] = detail.getString("ID")
                    hmap["Title"] = detail.getString("Title")
                    hmap["check"] = "false"
                    al.add(detail.getString("Title"))
                    alId.add(detail.getString("ID"))
                    alCheck.add("false")
                    hList.add(hmap)
                }
            } catch (e: Exception) {
                Log.e(TAG, "ConnectionListLoader:" + e.message)
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            database.beginWrite()
            for (i in al.indices) {
                database.addConnectionTypes_Main("" + i, alId[i], al[i])
            }
            database.endWrite(true)
            database.close()
        }
    }

    interface ConnectionsListener {
        fun onConnectionChanged(conId: String?, conName: String?)
    }
}
