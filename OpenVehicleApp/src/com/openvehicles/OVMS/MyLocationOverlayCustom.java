// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Handler;
import com.google.android.maps.*;

public class MyLocationOverlayCustom extends MyLocationOverlay
{

    public MyLocationOverlayCustom(Context context, MapView mapview)
    {
        super(context, mapview);
        savedFix = null;
        point = new Point();
        rect = new Rect();
        handler = new Handler();
        mapView = mapview;
        locationMarker = context.getResources().getDrawable(0x7f020044);
        overlayAnimationTask = new Runnable() {

            public void run()
            {
                mapView.invalidate();
                handler.removeCallbacks(overlayAnimationTask);
                handler.postDelayed(overlayAnimationTask, 1000L);
            }

            final MyLocationOverlayCustom this$0;

            
            {
                this$0 = MyLocationOverlayCustom.this;
                super();
            }
        }
;
        handler.removeCallbacks(overlayAnimationTask);
        handler.postAtTime(overlayAnimationTask, 100L);
    }

    protected void drawMyLocation(Canvas canvas, MapView mapview, Location location, GeoPoint geopoint, long l)
    {
        mapview.getProjection().toPixels(geopoint, point);
        rect.left = point.x - locationMarker.getIntrinsicWidth() / 2;
        rect.top = point.y - locationMarker.getIntrinsicHeight() / 2;
        rect.right = point.x + locationMarker.getIntrinsicWidth() / 2;
        rect.bottom = point.y + locationMarker.getIntrinsicHeight() / 2;
        locationMarker.setBounds(rect);
        locationMarker.draw(canvas);
    }

    private Handler handler;
    private Drawable locationMarker;
    private final MapView mapView;
    private Runnable overlayAnimationTask;
    private Point point;
    private Rect rect;
    private Location savedFix;



}
