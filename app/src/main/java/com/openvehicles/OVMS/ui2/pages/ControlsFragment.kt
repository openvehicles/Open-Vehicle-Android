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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.api.OnResultCommandListener
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.BaseFragment
import com.openvehicles.OVMS.ui2.components.quickactions.ChargingQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.HomeLinkQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.LockQuickAction
import com.openvehicles.OVMS.ui2.components.quickactions.QuickActionsAdapter
import com.openvehicles.OVMS.ui2.components.quickactions.ValetQuickAction
import com.openvehicles.OVMS.ui2.rendering.CarRenderingUtils
import com.openvehicles.OVMS.utils.CarsStorage


class ControlsFragment : BaseFragment(), OnResultCommandListener {

    private var carData: CarData? = null

    private lateinit var sideActionsAdapter: QuickActionsAdapter
    private lateinit var bottomActionsAdapter: QuickActionsAdapter
    private lateinit var centerActionsAdapter: QuickActionsAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_controls, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        carData = CarsStorage.getSelectedCarData()
        val sideActionsRecyclerView = findViewById(R.id.sideActions) as RecyclerView
        val bottomActionsRecyclerView = findViewById(R.id.bottomActions) as RecyclerView
        val mainActionsRecyclerView = findViewById(R.id.mainActions) as RecyclerView

        sideActionsAdapter = QuickActionsAdapter(context)
        bottomActionsAdapter = QuickActionsAdapter(context)
        centerActionsAdapter = QuickActionsAdapter(context)

        sideActionsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        sideActionsRecyclerView.adapter = sideActionsAdapter

        bottomActionsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bottomActionsRecyclerView.adapter = bottomActionsAdapter

        mainActionsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, VERTICAL, false)
        mainActionsRecyclerView.adapter = centerActionsAdapter

        updateTPMSData(carData)
        initialiseSideActions(carData)
        initialiseMainActions(carData)
        initialiseCarRendering(carData)
    }

    private fun updateTPMSData(carData: CarData?) {
        val frTPMS = findViewById(R.id.tpmsFR) as TextView
        val flTPMS = findViewById(R.id.tpmsFL) as TextView
        val rrTPMS = findViewById(R.id.tpmsRR) as TextView
        val rlTPMS = findViewById(R.id.tpmsRL) as TextView
        val staleTPMS = findViewById(R.id.tpmsStale) as TextView
        val tpmsFAB = findViewById(R.id.tpmsToggle) as ExtendedFloatingActionButton

        tpmsFAB.setOnClickListener {
            frTPMS.visibility =
                if (frTPMS.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
            flTPMS.visibility =
                if (flTPMS.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
            rrTPMS.visibility =
                if (rrTPMS.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
            rlTPMS.visibility =
                if (rlTPMS.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
            staleTPMS.visibility =
                if (staleTPMS.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }

        // Disable TPMS for motorcycles, etc?
        if (arrayOf("EN", "NRJK").contains(carData?.car_type)) {
            tpmsFAB.isEnabled = false
            return
        }

        var stale1 = CarData.DataStale.NoValue
        var val1 = carData?.car_tpms_wheelname
        var val2: Array<String?>? = null
        var alert: IntArray? = intArrayOf(0, 0, 0, 0)

        if (carData?.car_tpms_wheelname != null && carData.car_tpms_wheelname!!.size >= 4) {
            // New data (msg code 'Y'):
            if (carData.stale_tpms_pressure != CarData.DataStale.NoValue && carData.car_tpms_pressure!!.size >= 4) {
                stale1 = carData.stale_tpms_pressure
                val1 = carData.car_tpms_pressure
            }
            if (carData.stale_tpms_temp != CarData.DataStale.NoValue && carData.car_tpms_temp!!.size >= 4) {
                val2 = carData.car_tpms_temp
            }
            if (carData.stale_tpms_health != CarData.DataStale.NoValue && carData.car_tpms_health!!.size >= 4) {
                if (stale1 == CarData.DataStale.NoValue) {
                    stale1 = carData.stale_tpms_health
                    val1 = carData.car_tpms_health
                }
            }
            if (carData.stale_tpms_alert != CarData.DataStale.NoValue && carData.car_tpms_alert!!.size >= 4) {
                alert = carData.car_tpms_alert_raw
                if (stale1 == CarData.DataStale.NoValue) {
                    stale1 = carData.stale_tpms_alert
                    val1 = carData.car_tpms_alert
                }
            } else {
                alert = intArrayOf(0, 0, 0, 0)
            }
            // TODO display single value in the bottom field:
            /*if (stale2 == CarData.DataStale.NoValue && stale1 != CarData.DataStale.NoValue) {
                stale2 = stale1
                val2 = val1
                val1 = carData.car_tpms_wheelname
            }*/
        } else if (carData != null) {
            // Legacy data (msg code 'W'): only pressures & temperatures available
            val1 = arrayOf(
                carData.car_tpms_fl_p,
                carData.car_tpms_fr_p,
                carData.car_tpms_rl_p,
                carData.car_tpms_rr_p
            )
            stale1 = carData.stale_tpms
            val2 = arrayOf(
                carData.car_tpms_fl_t,
                carData.car_tpms_fr_t,
                carData.car_tpms_rl_t,
                carData.car_tpms_rr_t
            )
            alert = intArrayOf(0, 0, 0, 0)
        }

        flTPMS.text = String.format("%s\n%s", val1?.get(0) ?: "---", val2?.get(0) ?:  "---")
        frTPMS.text = String.format("%s\n%s", val1?.get(1) ?: "---", val2?.get(1) ?:  "---")
        rlTPMS.text = String.format("%s\n%s", val1?.get(2) ?: "---", val2?.get(2) ?:  "---")
        rrTPMS.text = String.format("%s\n%s", val1?.get(3) ?: "---", val2?.get(3) ?:  "---")

        val alertcol = intArrayOf(0xFFFFFF, Color.YELLOW, Color.RED)
        if ((alert?.get(0) ?: 0) != 0)
            flTPMS.setTextColor(alertcol[alert!![0]])
        if ((alert?.get(1) ?: 0) != 0)
            frTPMS.setTextColor(alertcol[1])
        if ((alert?.get(2) ?: 0) != 0)
            rlTPMS.setTextColor(alertcol[alert!![2]])
        if ((alert?.get(3) ?: 0) != 0)
            rrTPMS.setTextColor(alertcol[alert!![3]])


        val now = System.currentTimeMillis()
        var seconds = (now - (carData?.car_lastupdated?.time ?: 0)) / 1000
        var minutes = seconds / 60
        var hours = minutes / 60
        var days = minutes / (60 * 24)

        if (minutes == 0L) {
            staleTPMS.text = getString(R.string.live)
        } else {
            var periodText = ""
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
            staleTPMS.text = periodText
        }

        if (stale1 == CarData.DataStale.Stale) {
            staleTPMS.setTextColor(Color.YELLOW)
        }
        if (stale1 == CarData.DataStale.Good) {
            context?.resources?.getColor(R.color.colorAccent)?.let { staleTPMS.setTextColor(it) }
        }

        if ((alert?.find { it > 0 } ?: 0) > 0) {
            tpmsFAB.toggle()
        }
    }

    private fun initialiseCarRendering(carData: CarData?) {
        val carImageView = findViewById(R.id.battIndicatorImg) as ImageView
        val layers = carData?.let { CarRenderingUtils.getTopDownCarLayers(it, requireContext()) }

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

    private fun initialiseSideActions(carData: CarData?) {
        sideActionsAdapter.mData = emptyList()
        centerActionsAdapter.setCarData(carData)
        if (carData?.car_type == "RT") {
            // Renault Twizy: use Homelink for profile switching:
            sideActionsAdapter.mData += HomeLinkQuickAction("hl_default", R.drawable.ic_drive_profile, "24", {getService()})
        } else {
            sideActionsAdapter.mData += HomeLinkQuickAction("hl_1", R.drawable.ic_homelink_1, "24,0", {getService()})
            sideActionsAdapter.mData += HomeLinkQuickAction("hl_2", R.drawable.ic_homelink_2, "24,1", {getService()})
            sideActionsAdapter.mData += HomeLinkQuickAction("hl_3", R.drawable.ic_homelink_3, "24,2", {getService()})
        }
        sideActionsAdapter.notifyDataSetChanged()
    }

    private fun initialiseMainActions(carData: CarData?) {
        centerActionsAdapter.mData = emptyList()
        centerActionsAdapter.setCarData(carData)
        centerActionsAdapter.mData += LockQuickAction {getService()}
        centerActionsAdapter.mData += ValetQuickAction {getService()}
        centerActionsAdapter.mData += HomeLinkQuickAction("wakeup", R.drawable.ic_wakeup, "18", {getService()})
        centerActionsAdapter.mData += ChargingQuickAction {getService()}
        centerActionsAdapter.notifyDataSetChanged()
    }

    override fun update(carData: CarData?) {
        this.carData = carData
        updateTPMSData(carData)
        initialiseSideActions(carData)
        initialiseMainActions(carData)
        initialiseCarRendering(carData)
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