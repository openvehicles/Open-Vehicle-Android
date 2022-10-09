package com.openvehicles.OVMS.appwidgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.MainActivity;
import com.openvehicles.OVMS.utils.CarsStorage;
import com.openvehicles.OVMS.utils.Sys;

import java.text.SimpleDateFormat;


/**
 * InfoWidget: Application Widget providing a SOC gauge.
 *
 *  Displays SOC, estimated range, battery temperature and charge time estimation.
 *  Charge SOC limit is shown if set & current SOC is below, charge time estimation
 *  changes to 100% estimation when above SOC limit. While charging, the energy
 *  gained (kWh) is shown above the SOC.
 *
 *  The vehicle name is shown in the upper left corner. The upper right corner
 *  displays the last update time if older than 2 minutes or "OFFLINE" if the
 *  ApiService currently isn't connected to the server.
 */
public class InfoWidget extends ApiWidget<InfoWidget> {
    private static final String TAG = "InfoWidget";

    public InfoWidget() {
        super(InfoWidget.class);
    }

    /**
     * updateWidget: Update specific widget instance
     *
     * @param context - the current execution Context
     * @param appWidgetManager - the AppWidgetManager instance
     * @param appWidgetId - the ID of the widget to update
     */
    @Override
    public void updateWidget(Context context, AppWidgetManager appWidgetManager,
                             int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.info_widget);

        // Register click intent:
        Intent configIntent = new Intent(context, MainActivity.class);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0,
                configIntent, Sys.getMutableFlags(0, false));
        views.setOnClickPendingIntent(R.id.info_widget_image, configPendingIntent);

        // Get current widget size:
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, 100);
        int height = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, 100);

        // Update widget view:
        CarData carData = CarsStorage.get().getSelectedCarData();
        views.setImageViewBitmap(R.id.info_widget_image, renderWidget(context, carData, width, height));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private Bitmap renderWidget(Context context, CarData carData, int width, int height) {

        final float displayDensity = context.getResources().getDisplayMetrics().density;
        int dim = (int) (Math.min(width, height) * displayDensity);
        float dps = (dim-1)/100f; // scaling for coordinates normalized to range 0…100
        //Log.v(TAG, "renderWidget: dim " + width + "x" + height + ", density=" + displayDensity + " => dim=" + dim);

        Bitmap bitmap = Bitmap.createBitmap(dim, dim, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Create background:
        RectF bg = new RectF(0*dps,0*dps, 100*dps,100*dps);
        paint.setColor(context.getResources().getColor(R.color.colorPrimary));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        canvas.drawRoundRect(bg, 10*dps,10*dps, paint);

        // Check for car data availability:
        if (carData == null) {
            paint.setTextSize(15*dps);
            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("No Data", 50*dps,58*dps, paint);
            return bitmap;
        }

        // Get charge parameters:
        int etrFull = carData.car_chargefull_minsremaining;
        int etrSuffSOC = carData.car_chargelimit_minsremaining_soc;
        int etr = (etrSuffSOC > 0) ? etrSuffSOC : etrFull;

        //
        // Render SOC gauge
        //

        int colorSOC;
        if (carData.car_charging)
            colorSOC = Color.parseColor("#ffe43aff");
        else if (carData.car_soc_raw > 25)
            colorSOC = Color.parseColor("#ff10ff10");
        else if (carData.car_soc_raw > 12.5)
            colorSOC = Color.parseColor("#ffffff10");
        else
            colorSOC = Color.parseColor("#ffff1010");
        int colorSOCLimit = Color.parseColor("#ffffaa54");
        int colorScale = Color.parseColor("#ff666688");

        paint.setColor(colorScale);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        // Gauge track:
        RectF rectGauge = new RectF(10*dps,14*dps,90*dps,94*dps);
        paint.setStrokeWidth(6*dps);
        canvas.drawArc(rectGauge, 135, 270, false, paint);

        // Scale:
        RectF rectScale = new RectF(14*dps,18*dps,86*dps,90*dps);
        float tickWidth = 2f;
        for (int i = 0; i <= 10; i++) {
            float startAngle = 135 + i * (270f/10f) - tickWidth * (i/10f);
            canvas.drawArc(rectScale, startAngle, tickWidth, false, paint);
        }

        // SOC charge limit:
        float socLimitAngle = 270f;
        if (etrSuffSOC > 0)
            socLimitAngle *= carData.car_chargelimit_soclimit/100f;
        paint.setColor(colorSOCLimit);
        paint.setStrokeWidth(5.5f*dps);
        canvas.drawArc(rectGauge, 135, socLimitAngle, false, paint);
        paint.setColor(colorScale);
        paint.setStrokeWidth(4.5f*dps);
        canvas.drawArc(rectGauge, 135, socLimitAngle-1f, false, paint);

        // SOC:
        paint.setStrokeWidth(5*dps);
        paint.setColor(colorSOC);
        paint.setShadowLayer(3*dps,0,0, colorSOC);
        canvas.drawArc(rectGauge, 135,
                270f * (carData.car_soc_raw/100f), false, paint);

        //
        // Render text displays
        //

        int textColorMain = Color.WHITE;
        int textColorAux = Color.parseColor("#ffffaa54");
        int textColorKwh = Color.parseColor("#ffffa9ff");
        int textColorUpdate = Color.parseColor("#ff00fff9");

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        // Vehicle name:
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(7*dps);
        paint.setColor(textColorAux);
        canvas.drawText(carData.sel_vehicle_label, 5*dps,8*dps, paint);

        // Offline status / last update time if outdated:
        if (!isServiceOnline()) {
            paint.setTextAlign(Paint.Align.RIGHT);
            paint.setTextSize(7*dps);
            paint.setColor(textColorUpdate);
            canvas.drawText("OFFLINE", 95*dps,8*dps, paint);
        }
        else if (carData.car_lastupdated != null && System.currentTimeMillis() - carData.car_lastupdated.getTime() > 120 * 1000) {
            SimpleDateFormat isoTime = new SimpleDateFormat("HH:mm");
            String textLastUpdate = isoTime.format(carData.car_lastupdated);
            paint.setTextAlign(Paint.Align.RIGHT);
            paint.setTextSize(7*dps);
            paint.setColor(textColorUpdate);
            canvas.drawText(textLastUpdate, 95*dps,8*dps, paint);
        }

        paint.setTextAlign(Paint.Align.CENTER);

        // kWh charged:
        if (carData.car_charging) {
            String textKwh = String.format("%.1fkWh", carData.car_charge_kwhconsumed);
            paint.setTextSize(11 * dps);
            paint.setColor(textColorKwh);
            canvas.drawText(textKwh, 50 * dps, 34 * dps, paint);
        }

        // SOC:
        String textSOC = String.format("%d%%", (int)Math.floor(carData.car_soc_raw));
        paint.setTextSize(25*dps);
        paint.setColor(textColorMain);
        canvas.drawText(textSOC, 50*dps,58*dps, paint);

        // Estimated range:
        String textRange = String.format("%d%s", (int)Math.floor(carData.car_range_estimated_raw), carData.car_distance_units);
        paint.setTextSize(15*dps);
        canvas.drawText(textRange, 50*dps,75*dps, paint);

        // Battery temperature:
        paint.setTextSize(12*dps);
        paint.setColor(textColorAux);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(carData.car_temp_battery, 5*dps,96*dps, paint);

        // Estimated charge time:
        if (etr > 0) {
            String textEtr;
            if (etr > 60)
                textEtr = String.format("%dh %dm", etr / 60, etr % 60);
            else
                textEtr = String.format("%dm", etr);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(textEtr, 95*dps,96*dps, paint);
        }

        return bitmap;
    }

}