package com.openvehicles.OVMS.ui2.rendering

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.Animatable
import android.graphics.drawable.VectorDrawable
import androidx.core.content.ContextCompat
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.ui.utils.Ui
import android.util.Log
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.toColorInt


object CarRenderingUtils {
    fun getTopDownCarLayers(carData: CarData, context: Context, climate: Boolean = false, heat: Boolean = false): List<Drawable> {
        var layers = emptyList<Drawable>()


        var overlayResource: String = carData.sel_vehicle_image


        val name_splitted = carData.sel_vehicle_image.split("_");

        when {
            carData.sel_vehicle_image.startsWith("car_imiev_")
            || carData.sel_vehicle_image.startsWith("car_i3_")
            || carData.sel_vehicle_image.startsWith("car_ampera_")
            || carData.sel_vehicle_image.startsWith("car_twizy_")
            || carData.sel_vehicle_image.startsWith("car_kangoo_") -> {
                // one ol image for all colors:
                overlayResource = name_splitted.minus(name_splitted.last())
                    .joinToString("_")
            }
            carData.sel_vehicle_image.startsWith("car_holdenvolt_") -> overlayResource = "car_ampera" // Holdenvolt: one ol image for all colors (same as ampera)
            carData.sel_vehicle_image.startsWith("car_kianiro_") -> overlayResource = "car_kianiro_grey"
            carData.sel_vehicle_image.startsWith("car_smart_44") -> overlayResource = "car_vwup_silver" // Smart forfour share a single image
            carData.sel_vehicle_image.startsWith("car_smart_") -> overlayResource = "car_smart" // Smart fortwo share a single image
            carData.sel_vehicle_image.startsWith("car_nrjkexperia") -> overlayResource = "car_nrjkexperia"
            carData.sel_vehicle_image.startsWith("car_nrjk") -> overlayResource = "car_nrjk"
            carData.sel_vehicle_image.startsWith("car_niu_mqi_gt") -> overlayResource = "car_niu_mqi_gt"
        }

        var otherResName = overlayResource.split("_").minus(name_splitted.last())
            .joinToString("_").replace("car_", "")
        val otherBodyResName = overlayResource.replace("car_", "")

        if (carData.car_type.startsWith("VA"))
            otherResName = "voltampera"

        // Base image:
        layers = layers.plus(
            ContextCompat.getDrawable(context, Ui.getDrawableIdentifier(
                context,
                "ol_"+overlayResource
            ))!!)

        // Mode overlays:
        if (climate) {
            val modeResource = Ui.getDrawableIdentifier(context,
                otherResName + "_topview_int"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
        }

        if (carData.car_headlights_on == true) {
            var modeResource = Ui.getDrawableIdentifier(context,
                otherResName + "_topview_hd"
            )
            if (modeResource == 0) {
                modeResource = Ui.getDrawableIdentifier(context,
                    otherResName + "_carlights")
            }
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
        }

        if (carData.car_frontrightdoor_open == true) {
            var modeResource = Ui.getDrawableIdentifier(context,
                otherBodyResName + "_topview_frd"
            )
            if (modeResource == 0) {
                modeResource = Ui.getDrawableIdentifier(context,
                    otherResName + "_outline_rd")
            }
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
        }

        if (carData.car_frontleftdoor_open == true) {
            var modeResource = Ui.getDrawableIdentifier(context,
                otherBodyResName + "_topview_fld"
            )
            if (modeResource == 0) {
                modeResource = Ui.getDrawableIdentifier(context,
                    otherResName + "_outline_ld")
            }
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
        }

        if (carData.car_rearrightdoor_open == true) {
            var modeResource = Ui.getDrawableIdentifier(context,
                otherBodyResName + "_topview_rrd"
            )
            if (modeResource == 0) {
                modeResource = Ui.getDrawableIdentifier(context,
                    otherResName + "_outline_rrd")
            }
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
        }

        if (carData.car_rearleftdoor_open == true) {
            var modeResource = Ui.getDrawableIdentifier(context,
                otherBodyResName + "_topview_rld"
            )
            if (modeResource == 0) {
                modeResource = Ui.getDrawableIdentifier(context,
                    otherResName + "_outline_rld")
            }
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
        }


        if (carData.car_trunk_open == true) {
            var modeResource = Ui.getDrawableIdentifier(context,
                otherBodyResName + "_topview_t"
            )
            if (modeResource == 0) {
                modeResource = Ui.getDrawableIdentifier(context,
                    otherResName + "_topview_t")
            }
            if (modeResource == 0) {
                modeResource = Ui.getDrawableIdentifier(context,
                    otherResName + "_outline_tr")
            }
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
        }

        if (carData.car_bonnet_open == true) {
            var modeResource = Ui.getDrawableIdentifier(context,
                otherBodyResName + "_topview_b"
            )
            if (modeResource == 0) {
                modeResource = Ui.getDrawableIdentifier(context,
                    otherResName + "_topview_b")
            }
            if (modeResource == 0) {
                modeResource = Ui.getDrawableIdentifier(context,
                    otherResName + "_outline_hd")
            }
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
        }

        if (carData.car_chargeport_open) {
            val modeResource = Ui.getDrawableIdentifier(context,
                otherResName + "_topview_cp"
            )
            if (modeResource > 0)
                layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
            else {
                if (carData.sel_vehicle_image.startsWith("car_twizy_")) {
                    // Renault Twizy:
                    layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.ol_car_twizy_chargeport)!!)
                } else if (carData.sel_vehicle_image.startsWith("car_imiev_")) {
                    // Mitsubishi i-MiEV:
                    layers = layers.plus(ContextCompat.getDrawable(context, if (carData.car_charge_currentlimit_raw > 16) R.drawable.ol_car_imiev_charge_quick else
                        R.drawable.ol_car_imiev_charge
                    )!!)
                } else if (carData.sel_vehicle_image.startsWith("car_kianiro_")) {
                    // Kia Niro: use i-MiEV charge overlays
                    layers = layers.plus(ContextCompat.getDrawable(context, if (carData.car_charge_mode == "performance") R.drawable.ol_car_imiev_charge_quick else
                        R.drawable.ol_car_imiev_charge)!!)
                } else if (carData.sel_vehicle_image.startsWith("car_mgzs_")) {
                    // MG ZS: use i-MiEV charge overlays
                    layers = layers.plus(ContextCompat.getDrawable(context, if (carData.car_charge_mode == "performance") R.drawable.ol_car_imiev_charge_quick else
                        R.drawable.ol_car_imiev_charge
                    )!!)
                } else if (carData.sel_vehicle_image.startsWith("car_leaf")) {
                    // Nissan Leaf: use Leaf charge overlay
                    layers = layers.plus(ContextCompat.getDrawable(context, if (carData.car_charge_state == "charging") R.drawable.ol_car_leaf_charge else
                        R.drawable.ol_car_leaf_nopower
                    )!!)
                } else if (carData.sel_vehicle_image.startsWith("car_vwup_")) {
                    // VW e-Up:
                    layers = layers.plus(ContextCompat.getDrawable(context, if (carData.car_charge_mode == "performance") R.drawable.ol_car_vwup_chargeport_redflash else if (carData.car_charge_state == "charging" || carData.car_charge_state == "topoff") //				else if (pCarData.car_charge_mode.equals("standard") || pCarData.car_charge_mode.equals("range"))
                        R.drawable.ol_car_vwup_chargeport_green else
                        R.drawable.ol_car_vwup_chargeport_orange
                    )!!)

                } else if (carData.sel_vehicle_image.startsWith("car_zoe_") ||
                    carData.sel_vehicle_image.startsWith("car_kangoo_") ||
                    carData.sel_vehicle_image.startsWith("car_smart_")
                ) {
                    // Renault ZOE/Kangoo/Smart EQ
                    when (carData.car_charge_state) {
                        "charging" -> layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.ol_car_zoe_chargeport_orange)!!)
                        "stopped" -> layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.ol_car_zoe_chargeport_red)!!)
                        "prepare" ->layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.ol_car_zoe_chargeport_yellow)!!)
                        else -> layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.ol_car_zoe_chargeport_green)!!)
                    }
                } else if (carData.sel_vehicle_image.startsWith("car_boltev_")) {
                    // Chevy Bolt EV
                    if (carData.car_charge_mode == "performance") layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.ol_car_boltev_dcfc)!!) else if (carData.car_charge_state == "charging") layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.ol_car_boltev_ac)!!) else layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.ol_car_boltev_portopen)!!)
                } else if (carData.sel_vehicle_image.startsWith("car_ampera_") ||
                    carData.sel_vehicle_image.startsWith("car_holdenvolt_")
                ) {
                    // Volt, Ampera
                    when (carData.car_charge_state) {
                        "charging" -> layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.ol_car_voltampera_chargeport_orange)!!)
                        "done" -> layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.ol_car_voltampera_chargeport_green)!!)
                        else -> layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.ol_car_voltampera_chargeport_red)!!)
                    }
                } else {
                    // Tesla Roadster:
                    if (carData.car_charge_substate_i_raw == 0x07) {
                        // We need to connect the power cable
                        layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.roadster_outline_cu)!!)
                    } else if (carData.car_charge_state_i_raw == 0x0d || carData.car_charge_state_i_raw == 0x0e || carData.car_charge_state_i_raw == 0x101) {
                        // Preparing to charge, timer wait, or fake 'starting' state
                        layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.roadster_outline_ce)!!)
                    } else if (carData.car_charge_state_i_raw == 0x01 || carData.car_charge_state_i_raw == 0x02 || carData.car_charge_state_i_raw == 0x0f ||
                        carData.car_charging
                    ) {
                        // Charging
                        layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.roadster_outline_cp)!!)
                    } else if (carData.car_charge_state_i_raw == 0x04) {
                        // Charging done
                        layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.roadster_outline_cd)!!)
                    } else if (carData.car_charge_state_i_raw >= 0x15 && carData.car_charge_state_i_raw <= 0x19) {
                        // Stopped
                        layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.roadster_outline_cs)!!)
                    } else {
                        // Fake 0x115 'stopping' state, or something else not understood
                        layers = layers.plus(ContextCompat.getDrawable(context, R.drawable.roadster_outline_cp)!!)
                    }
                }
            }


            var chargingResName = otherResName+"_topview"
            if (carData.car_charge_mode == "performance" || carData.car_charge_current_raw > 48) {
                chargingResName += "_q_"
            }

            if (carData.car_charge_state_i_raw == 0x01 || carData.car_charge_state_i_raw == 0x02 || carData.car_charge_state_i_raw == 0x0f || carData.car_charging) {
                val modeResource = Ui.getDrawableIdentifier(
                    context,
                    chargingResName +"_chg"
                )
                if (modeResource > 0)
                    layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
            } else if (carData.car_charge_state_i_raw == 0x0d || carData.car_charge_state_i_raw == 0x0e || carData.car_charge_state_i_raw == 0x101 || carData.car_charge_timer) {

                val modeResource = Ui.getDrawableIdentifier(
                    context,
                    chargingResName +"_tim"
                )
                if (modeResource > 0)
                    layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
            } else if (carData.car_charge_state_i_raw == 0x04) {
                // Charging done
                val modeResource = Ui.getDrawableIdentifier(
                    context,
                    chargingResName +"_chgf"
                )
                if (modeResource > 0)
                    layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
            } else if (carData.car_charge_state_i_raw >= 0x15 && carData.car_charge_state_i_raw <= 0x19) {
                // Charge stopped
                val modeResource = Ui.getDrawableIdentifier(
                    context,
                    chargingResName +"_chgs"
                )
                if (modeResource > 0)
                    layers = layers.plus(ContextCompat.getDrawable(context, modeResource)!!)
            }

        }

        //TODO: maybe something needs to be adjusted because of Fahrenheit
        if (heat) {
            val animatedAcArrowsDrawable = ContextCompat.getDrawable(context, R.drawable.avd_animated_ac_arrows)
            val tempCabin = carData?.car_temp_cabin_raw ?: 0f
            val warmColor = "#7d1506".toColorInt()
            val coolColor = "#5d7f9b".toColorInt()
            val temperature_threshold = 20f

            val tintColor = when {
                tempCabin > temperature_threshold -> coolColor
                else -> warmColor
            }

            if (animatedAcArrowsDrawable != null) {
                val finalDrawable = animatedAcArrowsDrawable.mutate()
                DrawableCompat.setTint(finalDrawable, tintColor)

                if (finalDrawable is Animatable) {
                   finalDrawable.start()
                }

                layers = layers.plus(finalDrawable)
            } else {
                Log.e("DrawableError", "Could not load R.drawable.avd_animated_ac_arrows")
                val staticArrows = ContextCompat.getDrawable(context, R.drawable.topview_ac_arrows)
                val vectorDrawable = staticArrows.mutate() as VectorDrawable
                vectorDrawable.setTint(tintColor)
                if (staticArrows != null) {
                    layers = layers.plus(vectorDrawable)
                }
            }
        }

        return layers
    }
}