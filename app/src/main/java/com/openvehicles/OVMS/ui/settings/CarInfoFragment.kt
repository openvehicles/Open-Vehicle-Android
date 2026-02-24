package com.openvehicles.OVMS.ui.settings

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui.utils.Ui.getDrawableIdentifier
import com.openvehicles.OVMS.ui.utils.Ui.setValue
import com.openvehicles.OVMS.utils.CarsStorage.getSelectedCarData
import com.openvehicles.OVMS.utils.CarsStorage.getStoredCars

class CarInfoFragment : BaseFragment() {

    private var carData: CarData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_info, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        compatActivity?.setTitle(R.string.lb_vehicle_info)
        val editPosition = requireArguments().getInt("position", -1)
        carData = if (editPosition >= 0) {
            getStoredCars()[editPosition]
        } else {
            getSelectedCarData()
        }
        if (carData != null) {
            approveCarData()
        }
    }

    private fun approveCarData() {
        val rootView = view
        val context = rootView!!.context
        setValue(rootView, R.id.txt_vehicle_id, carData!!.sel_vehicleid)
        setValue(rootView, R.id.txt_win, carData!!.car_vin)
        setValue(rootView, R.id.txt_type, carData!!.car_type)
        setValue(rootView, R.id.txt_server, carData!!.server_firmware)
        setValue(rootView, R.id.txt_car, carData!!.car_firmware)
        setValue(rootView, R.id.txt_hardware, carData!!.car_hardware)
        setValue(rootView,
            R.id.txt_gsm,
            String.format(
                "%s  %s  %s",carData!!.car_gsm_signal, carData!!.car_gsmlock, carData!!.car_mdm_mode)
        )
        setValue(
            rootView,
            R.id.txt_cac,
            if (carData!!.car_CAC_percent > 0) String.format(
                "%.1f Ah = %.1f%%",
                carData!!.car_CAC,
                carData!!.car_CAC_percent
            ) else if (carData!!.car_type == "SQ") String.format(
                "%.1f Ah   SoC: %.0f%%  %.1fkWh",
                carData!!.car_CAC,
                carData!!.car_soc_raw,
                carData!!.car_battery_capacity
            ) else String.format("%.1f Ah", carData!!.car_CAC)
        )
        setValue(rootView, R.id.txt_soh, String.format("%.1f%%", carData!!.car_soh))
        setValue(
            rootView, R.id.txt_12v_info, String.format(
                "%.1fV (%s) %.1fA",
                carData!!.car_12vline_voltage,
                if (carData!!.car_charging_12v) "charging" else if (carData!!.car_12vline_ref <= 1.5) String.format(
                    "calmdown, %d min left",
                    15 - (carData!!.car_12vline_ref * 10).toInt()
                ) else String.format("ref=%.1fV", carData!!.car_12vline_ref),
                carData!!.car_12v_current
            )
        )
        setValue(
            rootView,
            R.id.txt_charge_info,
            String.format("%.1f kWh", carData!!.car_charge_kwhconsumed)
        )

        // Show known car service interval info:
        var serviceInfo = ""
        if (carData!!.car_servicerange >= 0) {
            serviceInfo += String.format("%d km", carData!!.car_servicerange)
        }
        if (carData!!.car_servicetime >= 0) {
            if (serviceInfo != "") {
                serviceInfo += " / "
            }
            val now = System.currentTimeMillis() / 1000
            val serviceDays = (carData!!.car_servicetime - now) / 86400
            serviceInfo += String.format("%d days", serviceDays)
        }
        val serviceTextView = rootView.findViewById<View>(R.id.txt_service_info) as TextView
        val serviceView = rootView.findViewById<View>(R.id.service_info) as TextView
        if (serviceInfo == "") {
            serviceView.visibility = View.GONE
            serviceTextView.visibility = View.GONE
        } else {
            serviceView.visibility = View.VISIBLE
            serviceTextView.visibility = View.VISIBLE
            serviceTextView.text = serviceInfo
        }
        val iv = rootView.findViewById<View>(R.id.img_signal_rssi) as ImageView
        iv.setImageResource(
            getDrawableIdentifier(
                context,
                "signal_strength_" + carData!!.car_gsm_bars
            )
        )

        try {
            val pi = context.packageManager.getPackageInfo(context.packageName, 0)
            setValue(
                rootView,
                R.id.txt_app,
                String.format("%s (%s)", pi.versionName, pi.versionCode)
            )
        } catch (e: PackageManager.NameNotFoundException) {
        }
    }

    override fun update(carData: CarData?) {
        this.carData = carData
        approveCarData()
    }
}
