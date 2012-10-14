package com.openvehicles.OVMS;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Handler;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MyLocationOverlayCustom extends MyLocationOverlay {
	private Handler handler = new Handler();
	private Drawable locationMarker;
	private final MapView mapView;
	private Runnable overlayAnimationTask;
	private Point point = new Point();
	private Rect rect = new Rect();
	private Location savedFix = null;

	public MyLocationOverlayCustom(Context paramContext, MapView paramMapView) {
		super(paramContext, paramMapView);
		this.mapView = paramMapView;
		this.locationMarker = paramContext.getResources().getDrawable(
				2130837572);
		this.overlayAnimationTask = new Runnable() {
			public void run() {
				MyLocationOverlayCustom.this.mapView.invalidate();
				MyLocationOverlayCustom.this.handler
				.removeCallbacks(MyLocationOverlayCustom.this.overlayAnimationTask);
				MyLocationOverlayCustom.this.handler.postDelayed(
						MyLocationOverlayCustom.this.overlayAnimationTask,
						1000L);
			}
		};
		this.handler.removeCallbacks(this.overlayAnimationTask);
		this.handler.postAtTime(this.overlayAnimationTask, 100L);
	}

	protected void drawMyLocation(Canvas paramCanvas, MapView paramMapView,
			Location paramLocation, GeoPoint paramGeoPoint, long paramLong) {
		paramMapView.getProjection().toPixels(paramGeoPoint, this.point);
		this.rect.left = (this.point.x - this.locationMarker
				.getIntrinsicWidth() / 2);
		this.rect.top = (this.point.y - this.locationMarker
				.getIntrinsicHeight() / 2);
		this.rect.right = (this.point.x + this.locationMarker
				.getIntrinsicWidth() / 2);
		this.rect.bottom = (this.point.y + this.locationMarker
				.getIntrinsicHeight() / 2);
		this.locationMarker.setBounds(this.rect);
		this.locationMarker.draw(paramCanvas);
	}
}