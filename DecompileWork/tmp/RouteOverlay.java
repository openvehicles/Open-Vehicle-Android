package com.openvehicles.OVMS;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class RouteOverlay extends Overlay {
	private int color;
	public GeoPoint gp1;
	public GeoPoint gp2;

	public RouteOverlay(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2,
			int paramInt) {
		this.gp1 = paramGeoPoint1;
		this.gp2 = paramGeoPoint2;
		this.color = paramInt;
	}

	public void draw(Canvas paramCanvas, MapView paramMapView,
			boolean paramBoolean) {
		Projection localProjection = paramMapView.getProjection();
		Paint localPaint = new Paint();
		Point localPoint1 = new Point();
		localProjection.toPixels(this.gp1, localPoint1);
		localPaint.setColor(this.color);
		Point localPoint2 = new Point();
		localProjection.toPixels(this.gp2, localPoint2);
		localPaint.setStrokeWidth(5.0F);
		localPaint.setAlpha(120);
		paramCanvas.drawLine(localPoint1.x, localPoint1.y, localPoint2.x,
				localPoint2.y, localPaint);
		super.draw(paramCanvas, paramMapView, paramBoolean);
	}
}