package com.openvehicles.OVMS.ui2.pages

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui2.components.quickactions.ClimateQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.ClimateScheduleQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.adapters.QuickActionsAdapter
import com.openvehicles.OVMS.ui2.rendering.CarRenderingUtils
import com.openvehicles.OVMS.utils.CarsStorage
import java.text.DecimalFormat


class ClimateFragment : BaseFragment(), OnResultCommandListener {

    private var carData: CarData? = null

    private lateinit var climateActionsAdapter: QuickActionsAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_climate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        carData = CarsStorage.getSelectedCarData()
        val climateActionsRecyclerView = findViewById(R.id.climateActions) as RecyclerView
        climateActionsAdapter = QuickActionsAdapter(context)

        climateActionsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        climateActionsRecyclerView.adapter = climateActionsAdapter

        initialiseCarRendering(carData)
        initialiseClimateControls(carData)
    }

    private fun initialiseCarRendering(carData: CarData?) {
        val carImageView = findViewById(R.id.battIndicatorImg) as ImageView
        val layers = carData?.let { CarRenderingUtils.getTopDownCarLayers(it, requireContext(), climate = true, heat = carData.car_hvac_on) }

        if (layers != null) {
            val newDrawable = LayerDrawable(layers.toTypedArray())
            if (carImageView.drawable == null) {
                carImageView.setImageDrawable(
                    newDrawable
                )
                return
            }
            if ((carImageView.drawable as LayerDrawable?)?.numberOfLayers != newDrawable.numberOfLayers) {
                val anim_in: Animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
                carImageView.setImageDrawable(
                    newDrawable
                )
                anim_in.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationRepeat(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {}
                })
                carImageView.startAnimation(anim_in)
            }
        }
    }

    private fun initialiseClimateControls(carData: CarData?) {
        val insideTempText = findViewById(R.id.cabinTemp) as TextView
        val insideTempUnitText = findViewById(R.id.tempUnit2) as TextView
        val outsideTempText = findViewById(R.id.ambientTemp) as TextView
        val outsideTempUnitText = findViewById(R.id.tempUnit1) as TextView
        val staleLabel = findViewById(R.id.staleDataLabel) as TextView

        outsideTempText.alpha = 1f
        insideTempText.alpha = 1f

        val tempCabin = carData?.car_temp_cabin?.split("°")?.first()
        val tempAmbient = carData?.car_temp_ambient?.split("°")?.first()
        val tempFormat = carData?.car_temp_cabin?.split("°")?.last()

        outsideTempText.text =
            if (carData?.stale_ambient_temp == CarData.DataStale.NoValue) "--" else tempAmbient
        insideTempText.text =
            if (carData?.stale_car_temps == CarData.DataStale.NoValue) "--" else tempCabin

        insideTempUnitText.text = "°"+tempFormat
        outsideTempUnitText.text = "°"+tempFormat

        var dataStale: CarData.DataStale = CarData.DataStale.Good

        staleLabel.visibility = View.GONE

        if (carData?.stale_ambient_temp != CarData.DataStale.Good) {
            dataStale = carData!!.stale_ambient_temp
            outsideTempText.alpha = 0.6f
        }

        if (carData.stale_car_temps != CarData.DataStale.Good) {
            dataStale = carData.stale_car_temps
            insideTempText.alpha = 0.6f
        }

        if (dataStale == CarData.DataStale.NoValue) {
            staleLabel.setTextColor(Color.RED)
            staleLabel.text = getString(R.string.no_data).lowercase()
            staleLabel.visibility = View.VISIBLE
        }

        if (dataStale == CarData.DataStale.Stale) {
            staleLabel.setTextColor(Color.YELLOW)
            staleLabel.text = getString(R.string.stale_data).lowercase()
            staleLabel.visibility = View.VISIBLE
        }

        val now = System.currentTimeMillis()
        var seconds = (now - (carData.car_lastupdated?.time ?: 0)) / 1000
        var minutes = seconds / 60
        var hours = minutes / 60
        var days = minutes / (60 * 24)


        if (minutes > 0L) {
            staleLabel.visibility = View.VISIBLE
            staleLabel.setTextColor(Color.YELLOW)
            val periodText: String
            if (minutes == 1L) {
                periodText = getText(R.string.min1).toString()
            } else if (days > 1) {
                periodText = String.format(getText(R.string.ndays).toString(), days)
            } else if (hours > 1) {
                periodText = String.format(getText(R.string.nhours).toString(), hours)
            } else if (minutes > 60) {
                periodText = String.format(
                    getText(R.string.nmins).toString(),
                    minutes
                )
            } else {
                periodText = String.format(
                    getText(R.string.nmins).toString(),
                    minutes
                )
            }
            staleLabel.text = periodText
        }

        climateActionsAdapter.mData.clear()
        climateActionsAdapter.setCarData(carData)
        climateActionsAdapter.mData += ClimateQuickAction({getService()})
        if (carData?.car_type in listOf("NL","SE","SQ","VWUP","VWUP.T26","RZ","RZ2")) climateActionsAdapter.mData += ClimateScheduleQuickAction({getService()})
        climateActionsAdapter.notifyDataSetChanged()
    }

    override fun update(carData: CarData?) {
        this.carData = carData
        initialiseCarRendering(carData)
        initialiseClimateControls(carData)
    }

    override fun onResultCommand(result: Array<String>) {
        if (result.size <= 1) return
        val resCode = result[1].toInt()
        val resText = if (result.size > 2) result[2] else ""
        val cmdMessage = getSentCommandMessage(result[0])
        val context: Context? = activity
        if (context != null) {
            when (resCode) {
                0 -> Toast.makeText(
                    context, cmdMessage + " " + getString(R.string.msg_ok),
                    Toast.LENGTH_SHORT
                ).show()

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