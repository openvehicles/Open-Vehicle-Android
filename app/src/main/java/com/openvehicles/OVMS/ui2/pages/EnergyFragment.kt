package com.openvehicles.OVMS.ui2.pages

import android.content.Context
import android.content.res.ColorStateList
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
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.utils.CarsStorage


class EnergyFragment : BaseFragment(), OnResultCommandListener {

    private var carData: CarData? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_energy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        carData = CarsStorage.getSelectedCarData()

        initialiseEnergyStats(carData)
    }


    private fun initialiseEnergyStats(carData: CarData?) {

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


        // regen and consumed kWh

        val consRegen = findViewById(R.id.regenAmount) as TextView
        val consSpent = findViewById(R.id.consumeAmount) as TextView
        val consTrip = findViewById(R.id.consumeTrip) as TextView
        val whTrip = findViewById(R.id.consumptionTrip) as TextView
        val consumption = (carData?.car_energyused?.minus(carData.car_energyrecd))?.times(100)?.div(carData.car_tripmeter_raw.div(10))
        whTrip.text = String.format("%.1f Wh/%s", consumption, carData?.car_distance_units)
        consRegen.text = String.format("%2.2f kWh", carData?.car_energyrecd)
        consSpent.text = String.format("%2.2f kWh", carData?.car_energyused)
        consTrip.text = String.format("%.1f %s", carData?.car_tripmeter_raw?.div(10), carData?.car_distance_units)


        // 12V batt

        val batt12v = findViewById(R.id.batt12v) as TextView
        batt12v.text = String.format("%2.2f V", carData?.car_12vline_voltage)

        // Energy stats
        val energyHistoryLink = findViewById(R.id.energy_history)
        energyHistoryLink.findViewById<TextView>(R.id.tabName).setText(R.string.power_title)
        energyHistoryLink.findViewById<ImageView>(R.id.tabIcon).setImageResource(R.drawable.ic_action_chart)
        energyHistoryLink.setOnClickListener {
            findNavController().navigate(R.id.action_powerFragment_to_batteryFragment)
        }

        // 12V stats
        val history12VLink = findViewById(R.id.history_12v)
        history12VLink.findViewById<TextView>(R.id.tabName).setText(R.string.aux_battery_title)
        history12VLink.findViewById<ImageView>(R.id.tabIcon).setImageResource(R.drawable.ic_12vbatt)
        history12VLink.setOnClickListener {
            findNavController().navigate(R.id.action_powerFragment_to_auxBatteryFragment)
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

}