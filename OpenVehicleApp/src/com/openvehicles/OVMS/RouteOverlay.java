// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.graphics.*;
import com.google.android.maps.*;

public class RouteOverlay extends Overlay
{

    public RouteOverlay(GeoPoint geopoint, GeoPoint geopoint1, int i)
    {
        gp1 = geopoint;
        gp2 = geopoint1;
        color = i;
    }

    public void draw(Canvas canvas, MapView mapview, boolean flag)
    {
        Projection projection = mapview.getProjection();
        Paint paint = new Paint();
        Point point = new Point();
        projection.toPixels(gp1, point);
        paint.setColor(color);
        Point point1 = new Point();
        projection.toPixels(gp2, point1);
        paint.setStrokeWidth(5F);
        paint.setAlpha(120);
        canvas.drawLine(point.x, point.y, point1.x, point1.y, paint);
        super.draw(canvas, mapview, flag);
    }

    private int color;
    public GeoPoint gp1;
    public GeoPoint gp2;
}
