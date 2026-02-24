package com.openvehicles.OVMS.appwidgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.Log
import android.widget.RemoteViews
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.entities.CarData
import com.openvehicles.OVMS.utils.AppPrefs
import com.openvehicles.OVMS.ui.MainActivity
import com.openvehicles.OVMS.ui.utils.Ui
import com.openvehicles.OVMS.ui2.MainActivityUI2
import com.openvehicles.OVMS.utils.CarsStorage
import com.openvehicles.OVMS.utils.Sys
import java.text.SimpleDateFormat
import kotlin.math.min

/**
 * InfoWidget: Application Widget providing a SOC gauge.
 *
 * Displays SOC, estimated range, battery temperature and charge time estimation.
 * Charge SOC limit is shown if set & current SOC is below, charge time estimation
 * changes to 100% estimation when above SOC limit. While charging, the energy
 * gained (kWh) is shown above the SOC.
 *
 * The vehicle name is shown in the upper left corner. The upper right corner
 * displays the last update time if older than 2 minutes or "OFFLINE" if the
 * ApiService currently isn't connected to the server.
 */
class InfoWidget : ApiWidget<InfoWidget>(InfoWidget::class.java) {

    private var appPrefs: AppPrefs? = null

    /**
     * updateWidget: Update specific widget instance
     *
     * @param context - the current execution Context
     * @param appWidgetManager - the AppWidgetManager instance
     * @param appWidgetId - the ID of the widget to update
     */
    override fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int
    ) {
        appPrefs = AppPrefs(context, "ovms")

        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.info_widget)

        // Register click intent:
        val configIntent = Intent(context, if (appPrefs?.getData("option_oldui_enabled", "0") == "1") MainActivity::class.java
        else MainActivityUI2::class.java)
        val configPendingIntent = PendingIntent.getActivity(
            context, 0,
            configIntent, Sys.getMutableFlags(0, false)
        )
        views.setOnClickPendingIntent(R.id.info_widget_image, configPendingIntent)

        // Get current widget size:
        val options = appWidgetManager!!.getAppWidgetOptions(appWidgetId)
        val width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, 100)
        val height = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, 100)

        // Update widget view:
        val carData = CarsStorage.getSelectedCarData()
        try {
            views.setImageViewBitmap(
                R.id.info_widget_image,
                renderWidget(context, carData, width, height)
            )
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        } catch (e : Exception) {
            Log.e(TAG, "updateWidget: unable to render widget: $e")
        }
    }

    private fun renderWidget(
        context: Context?,
        carData: CarData?,
        width: Int,
        height: Int
    ): Bitmap {
        val displayDensity = context!!.resources.displayMetrics.density
        val dim = (min(width, height) * displayDensity).toInt()
        val dps = (dim - 1) / 100f // scaling for coordinates normalized to range 0…100
        //Log.v(TAG, "renderWidget: dim " + width + "x" + height + ", density=" + displayDensity + " => dim=" + dim);
        val bitmap = Bitmap.createBitmap(dim, dim, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.isAntiAlias = true

        // Create background:
        val bg = RectF(0 * dps, 0 * dps, 100 * dps, 100 * dps)
        paint.color = context.resources.getColor(R.color.colorPrimary)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 0f
        canvas.drawRoundRect(bg, 5 * dps, 5 * dps, paint)

        // Check for car data availability:
        if (carData == null) {
            paint.textSize = 15 * dps
            paint.color = Color.WHITE
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText("No Data", 50 * dps, 58 * dps, paint)
            return bitmap
        }

        // Get charge parameters:
        val etrFull = carData.car_chargefull_minsremaining
        val etrSuffSOC = carData.car_chargelimit_minsremaining_soc
        val etr = if (etrSuffSOC > 0) etrSuffSOC else etrFull

        //
        // Render SOC gauge
        //
        val colorSOC = if (carData.car_charging) {
            Color.parseColor("#ffe43aff")
        } else if (carData.car_soc_raw > 25) {
            Color.parseColor("#ff10ff10")
        } else if (carData.car_soc_raw > 12.5) {
            Color.parseColor("#ffffff10")
        } else {
            Color.parseColor("#ffff1010")
        }
        val colorSOCLimit = Color.parseColor("#ffffaa54")
        val colorScale = Color.parseColor("#ff666688")
        paint.color = colorScale
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f

        // Gauge track:
        val rectGauge = RectF(10 * dps, 14 * dps, 90 * dps, 94 * dps)
        paint.strokeWidth = 6 * dps
        canvas.drawArc(rectGauge, 135f, 270f, false, paint)

        // Scale:
        val rectScale = RectF(14 * dps, 18 * dps, 86 * dps, 90 * dps)
        val tickWidth = 2f
        for (i in 0..10) {
            val startAngle = 135 + i * (270f / 10f) - tickWidth * (i / 10f)
            canvas.drawArc(rectScale, startAngle, tickWidth, false, paint)
        }

        // SOC charge limit:
        var socLimitAngle = 270f
        if (etrSuffSOC > 0) socLimitAngle *= carData.car_chargelimit_soclimit / 100f
        paint.color = colorSOCLimit
        paint.strokeWidth = 5.5f * dps
        canvas.drawArc(rectGauge, 135f, socLimitAngle, false, paint)
        paint.color = colorScale
        paint.strokeWidth = 4.5f * dps
        canvas.drawArc(rectGauge, 135f, socLimitAngle - 1f, false, paint)

        // SOC:
        paint.strokeWidth = 5 * dps
        paint.color = colorSOC
        paint.setShadowLayer(3 * dps, 0f, 0f, colorSOC)
        canvas.drawArc(
            rectGauge, 135f,
            270f * (carData.car_soc_raw / 100f), false, paint
        )

        //
        // Render text displays
        //
        val textColorMain = Color.WHITE
        val textColorAux = Color.parseColor("#ffffaa54")
        val textColorKwh = Color.parseColor("#ffffa9ff")
        val textColorUpdate = Color.parseColor("#ff00fff9")
        paint.reset()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.setTypeface(Typeface.DEFAULT_BOLD)

        // Vehicle name:
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = 7 * dps
        paint.color = textColorAux
        canvas.drawText(carData.sel_vehicle_label, 5 * dps, 8 * dps, paint)

        // Offline status / last update time if outdated:
        if (!isServiceOnline) {
            paint.textAlign = Paint.Align.RIGHT
            paint.textSize = 7 * dps
            paint.color = textColorUpdate
            canvas.drawText("OFFLINE", 95 * dps, 8 * dps, paint)
        } else if (carData.car_lastupdated != null && System.currentTimeMillis() - carData.car_lastupdated!!.time > 120 * 1000) {
            val isoTime = SimpleDateFormat("HH:mm")
            val textLastUpdate = isoTime.format(carData.car_lastupdated)
            paint.textAlign = Paint.Align.RIGHT
            paint.textSize = 7 * dps
            paint.color = textColorUpdate
            canvas.drawText(textLastUpdate, 95 * dps, 8 * dps, paint)
        }
        paint.textAlign = Paint.Align.CENTER

        // kWh charged:
        var shiftdown = 0f
        if (carData.car_chargeport_open) {
            val textKwh = String.format("%.1f", carData.car_charge_kwhconsumed)
            paint.textSize = 11 * dps
            paint.color = textColorKwh
            Ui.drawUnitText(canvas, paint, textKwh, "kWh", 50 * dps, 35 * dps)
            shiftdown = 1.5f
        }

        // SOC:
        val textSOC = String.format("%d", Math.floor(carData.car_soc_raw.toDouble()).toInt())
        paint.textSize = 25 * dps
        paint.color = textColorMain
        Ui.drawUnitText(canvas, paint, textSOC, "%", 50 * dps, (57 + 2 * shiftdown) * dps, 1 / 10f)

        // Estimated range:
        val textRange =
            String.format("%d", Math.floor(carData.car_range_estimated_raw.toDouble()).toInt())
        paint.textSize = 15 * dps
        Ui.drawUnitText(
            canvas,
            paint,
            textRange,
            carData.car_distance_units,
            50 * dps,
            (76.5f + shiftdown) * dps
        )

        // Battery temperature:
        paint.textSize = 12 * dps
        paint.color = textColorAux
        paint.textAlign = Paint.Align.LEFT
        if (appPrefs!!.getData("showfahrenheit") == "on") {
            val fahrenheit = carData.car_temp_battery_raw * (9.0 / 5.0) + 32.0
            Ui.drawUnitText(
                canvas,
                paint,
                String.format("%.1f", fahrenheit),
                "\u00B0F",
                5 * dps,
                96 * dps
            )
        } else {
            Ui.drawUnitText(
                canvas,
                paint,
                String.format("%.1f", carData.car_temp_battery_raw),
                "\u00B0C",
                5 * dps,
                96 * dps
            )
        }

        // Estimated charge time:
        if (etr > 0) {
            paint.textAlign = Paint.Align.RIGHT
            if (etr > 60) {
                val w = Ui.drawUnitText(
                    canvas,
                    paint,
                    String.format(" %d", etr % 60),
                    "m",
                    95 * dps,
                    96 * dps
                )
                Ui.drawUnitText(
                    canvas,
                    paint,
                    String.format("%d", etr / 60),
                    "h",
                    95 * dps - w,
                    96 * dps
                )
            } else {
                Ui.drawUnitText(canvas, paint, String.format("%d", etr), "m", 95 * dps, 96 * dps)
            }
        }
        return bitmap
    }

    companion object {

        private const val TAG = "InfoWidget"
    }
}