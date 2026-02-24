package com.openvehicles.OVMS.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.ui.utils.Database
import com.openvehicles.OVMS.ui.utils.Ui.setOnClick
import com.openvehicles.OVMS.ui.utils.Ui.setValue
import com.openvehicles.OVMS.utils.AppPrefs

class DetailFragment : Fragment() {

    private var cpId: String? = null
    private var slat: String? = null
    private var slng: String? = null
    private lateinit var appPrefs: AppPrefs
    private lateinit var database: Database

    // TODO: Remove "@SuppressLint("Range")" and handle this properly
    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appPrefs = AppPrefs(requireActivity(), "ovms")
        database = Database(requireActivity())

        // get chargepoint id to display:
        cpId = arguments?.getString("cpId")
        if (cpId == null) {
            return null
        }

        // load chargepoint data:
        var row = database.getChargePoint(cpId!!)
        if (!row.moveToFirst()) {
            row.close()
            return null
        }

        slat = row.getString(row.getColumnIndex("Latitude"))
        slng = row.getString(row.getColumnIndex("Longitude"))
        val title: String = row.getString(row.getColumnIndex("Title"))
        val operatorInfo = row.getString(row.getColumnIndex("OperatorInfo"))
        val statusType = row.getString(row.getColumnIndex("StatusType"))
        val usageType = row.getString(row.getColumnIndex("UsageType"))
        val usageCost = row.getString(row.getColumnIndex("UsageCost"))
        val accessComments = row.getString(row.getColumnIndex("AccessComments"))
        val numberOfPoints = row.getString(row.getColumnIndex("NumberOfPoints"))
        val addressLine1 = row.getString(row.getColumnIndex("AddressLine1"))
        val relatedURL = row.getString(row.getColumnIndex("RelatedURL"))
        val generalComments = row.getString(row.getColumnIndex("GeneralComments"))
        row.close()

        // create and fill view:
        val detail = inflater.inflate(R.layout.detail, null)
        setValue(detail, R.id.value_Title, "" + title)
        setValue(detail, R.id.value_OperatorInfo, "" + operatorInfo)
        setValue(detail, R.id.value_StatusType, "" + statusType)
        setValue(detail, R.id.value_UsageType, "" + usageType)
        setValue(detail, R.id.value_UsageCost, "" + usageCost)
        setValue(detail, R.id.value_AccessComments, "" + accessComments)
        setValue(detail, R.id.value_NumberOfPoints, "" + numberOfPoints)
        setValue(detail, R.id.value_AddressLine1, "" + addressLine1)
        setValue(detail, R.id.value_RelatedURL, "" + relatedURL)
        setValue(detail, R.id.value_GeneralComments, "" + generalComments)

        // add connections:
        val layout = detail.findViewById<View>(R.id.DetailContentGroup) as LinearLayout
        var itemConn: View?
        row = database.getChargePointConnections(cpId!!)
        while (row.moveToNext()) {
            try {
                itemConn = inflater.inflate(R.layout.item_connection, null)
                setValue(
                    itemConn,
                    R.id.heading_level,
                    getString(
                        R.string.lb_chargepoint_conn_level,
                        row.position + 1
                    )
                )
                setValue(
                    itemConn,
                    R.id.value_level,
                    row.getString(row.getColumnIndex("conLevelTitle"))
                )
                setValue(
                    itemConn,
                    R.id.heading_conn_type,
                    getString(
                        R.string.lb_chargepoint_conn_type,
                        row.position + 1
                    )
                )
                setValue(
                    itemConn,
                    R.id.value_conn_type,
                    row.getString(row.getColumnIndex("conTypeTitle"))
                )
                layout.addView(itemConn)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        row.close()

        // routing button:
        setOnClick(detail, R.id.btnGetRoute) { direction() }

        // OCM button:
        setOnClick(detail, R.id.btnViewInOCM) {
            openURL("https://openchargemap.org/site/poi/details/$cpId")
        }

        // click on RelatedURL => open browser:
        setOnClick(detail, R.id.value_RelatedURL) { openURL(relatedURL) }

        return detail
    }

    override fun onDestroyView() {
        database.close()
        super.onDestroyView()
    }

    private fun direction() {
        // Create Google Maps intent from current location to target location
        val directions = ("https://maps.google.com/maps?saddr=${appPrefs.getData("lat_main")},${appPrefs.getData("lng_main")}&daddr=$slat,$slng")
        val intent = Intent(ACTION_VIEW, Uri.parse(directions)).apply {
            setClassName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"
            )
        }
        startActivity(intent)
    }

    private fun openURL(url: String) {
        startActivity(Intent(ACTION_VIEW, Uri.parse(url)))
    }
}
