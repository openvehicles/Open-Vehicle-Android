package com.openvehicles.OVMS.ui2.pages

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui2.components.energymetrics.EnergyMetric
import com.openvehicles.OVMS.ui2.components.energymetrics.EnergyMetricsAdapter
import com.openvehicles.OVMS.utils.CarsStorage
import java.util.Locale


class EnergyFragment : BaseFragment(), OnResultCommandListener, EnergyMetricsAdapter.ItemClickListener {

    private var carData: CarData? = null
    private lateinit var energyMetricsAdapter: EnergyMetricsAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_energy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        carData = CarsStorage.getSelectedCarData()
        energyMetricsAdapter = EnergyMetricsAdapter(context)
        energyMetricsAdapter.setClickListener(this)
        val metricsRecyclerView = findViewById(R.id.metricsRecyclerView) as RecyclerView
        metricsRecyclerView.adapter = energyMetricsAdapter
        metricsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        initialiseEnergyStats(carData)
    }


    private fun initialiseEnergyStats(carData: CarData?) {
        energyMetricsAdapter.mData = emptyList()
        // Battery content

        val battSoh = findViewById(R.id.battSoh) as TextView

        battSoh.text = String.format("%2.2f", carData?.car_soh)


        // SOC icon and label
        val socText: TextView = findViewById(R.id.battSoc) as TextView
        val socBattIcon = findViewById(R.id.battIndicatorImg) as ImageView

        var socBattLayers = emptyList<Drawable>()

        socBattLayers += ContextCompat.getDrawable(requireContext(), R.drawable.ic_batt_l0)!!

        val icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_batt_l1)!!.toBitmap()

        val soc = carData?.car_soc_raw ?: 0f
        val iconWidth = icon.height.minus(22).times(((soc / 100.0))).plus(7.5)
        if (iconWidth > 0) {
            val matrix = Matrix()
            matrix.postRotate(180f)
            val mBitmap =
                Bitmap.createBitmap(icon, 0, 0, icon.width, iconWidth.toInt(), matrix, true)
            val layer1Drawable = BitmapDrawable(resources, mBitmap)
            layer1Drawable.gravity = Gravity.BOTTOM

            if (carData?.car_charging == true) {
                layer1Drawable.setTint(
                    context?.resources?.getColor(R.color.colorAccent) ?: Color.GREEN
                )
            } else if (soc <= 10) {
                layer1Drawable.setTint(Color.RED)
            } else if (soc <= 20) {
                layer1Drawable.setTint(Color.YELLOW)
            } else {
                layer1Drawable.setTint(Color.WHITE)
            }
            socBattLayers += layer1Drawable
        }

        val socBattLayer = LayerDrawable(socBattLayers.toTypedArray())
        socBattIcon.setImageDrawable(socBattLayer)
        socText.text = String.format("%2.2f", carData?.car_soc_raw)
        var showSoc = true
        socText.setOnClickListener {
            showSoc = !showSoc
            socText.text = if (!showSoc) carData?.car_range_estimated else carData?.car_soc
        }

        // Battery temp

        val batteryTemp = findViewById(R.id.battTemp) as TextView
        batteryTemp.text = carData?.car_temp_battery

        // Battery power

        val battVolt = findViewById(R.id.battVolt) as TextView
        val battAmp = findViewById(R.id.battAmp) as TextView
        val battkW = findViewById(R.id.battkW) as TextView
        battVolt.text = String.format("%2.1f V", carData?.car_battery_voltage)
        battAmp.text = String.format("%2.1f A", carData?.car_battery_current_raw)
        battkW.text = String.format("%2.2f kW", carData?.car_power)

        // Metrics

        energyMetricsAdapter.mData += EnergyMetric("${getString(R.string.textMOTOR).lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) 
            else it.toString() }} ${getString(R.string.temp)}",
            carData?.car_temp_motor)

        energyMetricsAdapter.mData += EnergyMetric(getString(R.string.lb_motor_power),
            String.format("%2.1f kW", carData?.car_inv_power_motor_kw))

        energyMetricsAdapter.mData += EnergyMetric(getString(R.string.last_trip),
            String.format("%.1f %s", carData?.car_tripmeter_raw?.div(10), carData?.car_distance_units))

        var consumption = (carData?.car_energyused?.minus(carData.car_energyrecd))?.times(100)?.div(carData.car_tripmeter_raw.div(10))
        if (consumption?.isNaN() == true)
            consumption = 0f
        energyMetricsAdapter.mData += EnergyMetric(getString(R.string.consumption),
            String.format("%.1f Wh/%s", consumption, carData?.car_distance_units))

        energyMetricsAdapter.mData += EnergyMetric(getString(R.string.consumed_amount_label),
            String.format("%2.2f kWh", carData?.car_energyused))

        energyMetricsAdapter.mData += EnergyMetric(getString(R.string.regen_amount_label),
            String.format("%2.2f kWh", carData?.car_energyrecd))

        energyMetricsAdapter.mData += EnergyMetric(getString(R.string.drive_mode),
            when (carData?.car_drivemode) {
                0 -> getString(R.string.normal)
                1 -> getString(R.string.drive_mode_eco)
                else -> getString(R.string.unknown)
            })

        energyMetricsAdapter.mData += EnergyMetric(getString(R.string.text12VBATT),
            String.format("%2.2f V", carData?.car_12vline_voltage))

        energyMetricsAdapter.notifyDataSetChanged()

        // Power & Energy history (currently only supported for Renault Twizy)
        val powerHistoryLink = findViewById(R.id.power_history)
        powerHistoryLink.visibility = if (carData?.car_type == "RT") View.VISIBLE else View.GONE
        powerHistoryLink.findViewById<TextView>(R.id.tabName).setText(R.string.power_title)
        powerHistoryLink.findViewById<ImageView>(R.id.tabIcon).setImageResource(R.drawable.ic_action_chart)
        powerHistoryLink.setOnClickListener {
            findNavController().navigate(R.id.action_energyFragment_to_powerFragment)
        }

        // Main battery history (currently only supported for Renault Twizy)
        val batteryHistoryLink = findViewById(R.id.battery_history)
        batteryHistoryLink.visibility = if (carData?.car_type == "RT") View.VISIBLE else View.GONE
        batteryHistoryLink.findViewById<TextView>(R.id.tabName).setText(R.string.battery_title)
        batteryHistoryLink.findViewById<ImageView>(R.id.tabIcon).setImageResource(R.drawable.ic_action_chart)
        batteryHistoryLink.setOnClickListener {
            findNavController().navigate(R.id.action_energyFragment_to_batteryFragment)
        }

        // 12V battery history
        val history12VLink = findViewById(R.id.history_12v)
        history12VLink.findViewById<TextView>(R.id.tabName).setText(R.string.aux_battery_title)
        history12VLink.findViewById<ImageView>(R.id.tabIcon).setImageResource(R.drawable.ic_12vbatt)
        history12VLink.setOnClickListener {
            findNavController().navigate(R.id.action_energyFragment_to_auxBatteryFragment)
        }
    }


    override fun update(carData: CarData?) {
        this.carData = carData
        initialiseEnergyStats(carData)
    }

    override fun onResultCommand(result: Array<String>) {
        if (result.size <= 1) return
        val resCode = result[1].toInt()
        val resText = if (result.size > 2) result[2] else ""
        val cmdMessage = getSentCommandMessage(result[0])
        val context: Context? = activity
        if (context != null) {
            when (resCode) {
                1 -> Toast.makeText(
                    context, cmdMessage + " " + getString(R.string.err_failed, resText),
                    Toast.LENGTH_SHORT
                ).show()

                2 -> Toast.makeText(
                    context, cmdMessage + " " + getString(R.string.err_unsupported_operation),
                    Toast.LENGTH_SHORT
                ).show()

                3 -> Toast.makeText(
                    context, cmdMessage + " " + getString(R.string.err_unimplemented_operation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        cancelCommand()
    }

    override fun onItemClick(view: View?, position: Int) {

    }

}