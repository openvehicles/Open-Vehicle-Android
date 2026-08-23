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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.slider.Slider
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

    /**
     * Right-hand button column. Used only where the card carries the target
     * temperature slider (VW e-Golf): the start button sits there because that
     * is where the thumb falls with the phone in the right hand. Everywhere
     * else it stays empty and gone, so those vehicles keep the layout they had.
     */
    private lateinit var climateActionsRightAdapter: QuickActionsAdapter




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

        val rightRecyclerView = findViewById(R.id.climateActionsRight) as RecyclerView
        climateActionsRightAdapter = QuickActionsAdapter(context)
        rightRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rightRecyclerView.adapter = climateActionsRightAdapter

        initialiseTargetTempSlider()
        initialiseCarRendering(carData)
        initialiseClimateControls(carData)
    }

    /**
     * Target temperature for pre-conditioning.
     *
     * Only shown for vehicles whose module can set it — currently the VW e-Golf,
     * where the value lives in the car's stored BatteryControl profile and the
     * module writes it back with `xvg cctemp`. Set up once here rather than in
     * [initialiseClimateControls], which runs again on every data update and
     * would otherwise stack listeners.
     */
    private fun initialiseTargetTempSlider() {
        val group = findViewById(R.id.ccTempGroup) as LinearLayout
        if (carData?.car_type != "VWEG") {
            group.visibility = View.GONE
            return
        }
        group.visibility = View.VISIBLE

        val slider = findViewById(R.id.ccTempSlider) as Slider
        slider.addOnChangeListener { _, value, _ -> showTargetTemp(value) }
        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(s: Slider) {}
            override fun onStopTrackingTouch(s: Slider) {
                // Send on release only — sending while dragging would put a write
                // on the car's comfort bus for every step.
                sendCommand(
                    getString(R.string.climate_target_temp, formatTemp(s.value)),
                    "7,xvg cctemp " + formatTemp(s.value),
                    this@ClimateFragment
                )
            }
        })
        showTargetTemp(slider.value)

        // The target temperature is not carried by the v2 protocol, so it cannot
        // come in with the metrics — ask the module for it when the tab opens.
        sendCommand("", "7,xvg ccstatus", this)
    }

    private fun formatTemp(value: Float): String = DecimalFormat("0.0").format(value)

    private fun showTargetTemp(value: Float) {
        val label = findViewById(R.id.ccTempLabel) as TextView
        // Just the value — the slider directly beneath it makes clear what it is.
        label.text = formatTemp(value) + " °C"
    }

    /**
     * Applies `cctemp=22.0 current=32 valid=1` as reported by the module.
     *
     * `valid=0` means the module has not read the car's profile yet, so the
     * value carries no information — leave the slider where it is rather than
     * snapping it to a placeholder.
     */
    private fun applyClimateStatus(text: String) {
        if (Regex("valid=0").containsMatchIn(text)) return
        Regex("cctemp=([0-9.]+)").find(text)?.groupValues?.get(1)?.toFloatOrNull()?.let {
            val slider = findViewById(R.id.ccTempSlider) as Slider
            if (it >= slider.valueFrom && it <= slider.valueTo) {
                slider.value = it
                showTargetTemp(it)
            }
        }
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

        val errorColor = ContextCompat.getColor(requireContext(), R.color.colorTextError)
        val warningColor = ContextCompat.getColor(requireContext(), R.color.chargeOtherColor)

        if (dataStale == CarData.DataStale.NoValue) {
            staleLabel.setTextColor(errorColor)
            staleLabel.text = getString(R.string.no_data).lowercase()
            staleLabel.visibility = View.VISIBLE
        }

        if (dataStale == CarData.DataStale.Stale) {
            staleLabel.setTextColor(warningColor)
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
            staleLabel.setTextColor(warningColor)
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
        climateActionsRightAdapter.mData.clear()
        climateActionsRightAdapter.setCarData(carData)

        val leftColumn = findViewById(R.id.climateActions) as RecyclerView
        val rightColumn = findViewById(R.id.climateActionsRight) as RecyclerView
        if (carData?.car_type == "VWEG") {
            // Start on the right, where the thumb falls with the phone in the right
            // hand. There is deliberately no "climatise without the cable" button:
            // that profile bit is owned by the module, which sets it for a climate
            // command and clears it for a charge — a user-facing toggle would fight
            // the firmware and show a state that changes under the user's hands.
            climateActionsRightAdapter.mData += ClimateQuickAction({getService()})
            leftColumn.visibility = View.GONE
            rightColumn.visibility = View.VISIBLE
        } else {
            climateActionsAdapter.mData += ClimateQuickAction({getService()})
            leftColumn.visibility = View.VISIBLE
            rightColumn.visibility = View.GONE
        }
        if (carData?.car_type in listOf("NL","SE","SQ","VWUP","VWUP.T26","RZ","RZ2")
            || carData?.car_type.orEmpty().startsWith("VA")
            || carData?.car_type.orEmpty().startsWith("VB")
            || carData?.car_type.orEmpty().startsWith("OAE"))
            climateActionsAdapter.mData += ClimateScheduleQuickAction({getService()})
        climateActionsAdapter.notifyDataSetChanged()
        climateActionsRightAdapter.notifyDataSetChanged()
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
        // Status reply from `xvg ccstatus`: sync slider and button.
        if (resCode == 0 && resText.contains("cctemp=")) {
            applyClimateStatus(resText)
            cancelCommand()
            return
        }
        // Anything else we sent for this car changes the stored profile, and the
        // module may well have refused it — a sleeping car cannot be written to.
        // Never leave the slider showing a value the car does not hold: ask what
        // it actually is now. The reply lands in the branch above.
        // result[0] is the command *code*, not the text we sent — BaseFragment
        // keys its message map on command.split(",")[0]. 7 is "execute command",
        // and for this vehicle the only ones this tab sends are the xvg writes.
        if (carData?.car_type == "VWEG" && result[0] == "7") {
            if (resText.isNotEmpty())
                Toast.makeText(activity, resText, Toast.LENGTH_LONG).show()
            sendCommand("", "7,xvg ccstatus", this)
            return
        }
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