package com.openvehicles.OVMS;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public final class Utilities {
	public static GeoPoint GetCarGeopoint(double paramDouble1,
			double paramDouble2) {
		return new GeoPoint((int) (paramDouble1 * 1000000.0D),
				(int) (paramDouble2 * 1000000.0D));
	}

	public static GeoPoint GetCarGeopoint(CarData paramCarData) {
		return new GeoPoint((int) (1000000.0D * paramCarData.Data_Latitude),
				(int) (1000000.0D * paramCarData.Data_Longitude));
	}

	public static double GetDistanceBetweenCoordinatesKM(double paramDouble1,
			double paramDouble2, double paramDouble3, double paramDouble4) {
		double d1 = Math.toRadians(paramDouble3 - paramDouble1);
		double d2 = Math.toRadians(paramDouble4 - paramDouble2);
		double d3 = Math.toRadians(paramDouble1);
		double d4 = Math.toRadians(paramDouble3);
		double d5 = Math.sin(d1 / 2.0D) * Math.sin(d1 / 2.0D)
				+ Math.sin(d2 / 2.0D) * Math.sin(d2 / 2.0D) * Math.cos(d3)
				* Math.cos(d4);
		return 2.0D * Math.atan2(Math.sqrt(d5), Math.sqrt(1.0D - d5)) * 6371;
	}

	public static Bitmap GetRotatedDirectionalMarker(Bitmap paramBitmap,
			float paramFloat) {
		Bitmap localBitmap = paramBitmap.copy(Bitmap.Config.ARGB_8888, true);
		localBitmap.eraseColor(0);
		Canvas localCanvas = new Canvas(localBitmap);
		Matrix localMatrix = new Matrix();
		localMatrix.setRotate(paramFloat, localCanvas.getWidth() / 2,
				localCanvas.getHeight() / 2);
		localCanvas.drawBitmap(paramBitmap, localMatrix, null);
		return localBitmap;
	}

	public static Bitmap GetScaledBatteryOverlay(int paramInt,
			Bitmap paramBitmap) {
		Bitmap localBitmap = null;
		if (paramBitmap == null)
			Log.d("OVMS", "!!! Battery overlay resource not found !!!");
		while (true) {
			return localBitmap;
			if (paramInt > 0) {
				Matrix localMatrix = new Matrix();
				localMatrix.postScale(paramInt / 100.0F, 1.0F);
				localBitmap = Bitmap.createBitmap(paramBitmap, 0, 0,
						paramBitmap.getWidth(), paramBitmap.getHeight(),
						localMatrix, false);
			}
		}
	}

	public static class CarMarker extends OverlayItem {
		public int Direction;

		public CarMarker(GeoPoint paramGeoPoint, String paramString1,
				String paramString2, int paramInt) {
			super(paramString1, paramString2);
			this.Direction = paramInt;
		}
	}

	public static class CarMarkerOverlay extends ItemizedOverlay<OverlayItem> {
		private Bitmap DirectionalMarker;
		private int LABEL_SHADOW_XY;
		private ArrayList<Utilities.OnGroupCarTappedListener> _listeners = new ArrayList();
		private Context mContext;
		private int mLabelTextSize;
		private ArrayList<OverlayItem> mOverlays = new ArrayList();

		public CarMarkerOverlay(Drawable paramDrawable, int paramInt1,
				Context paramContext, Bitmap paramBitmap, int paramInt2) {
			super();
			this.mContext = paramContext;
			this.mLabelTextSize = paramInt1;
			this.DirectionalMarker = paramBitmap;
			this.LABEL_SHADOW_XY = paramInt2;
		}

		/** @deprecated */
		private void fireGroupCarTappedEvent(String paramString) {
			try {
				Iterator localIterator = this._listeners.iterator();
				while (true) {
					boolean bool = localIterator.hasNext();
					if (!bool)
						return;
					((Utilities.OnGroupCarTappedListener) localIterator.next())
					.OnGroupCarTapped(paramString);
				}
			} finally {
			}
		}

		/** @deprecated */
		public void addOnGroupCarTappedListener(Utilities.OnGroupCarTappedListener paramOnGroupCarTappedListener)
		{
			try
			{
				this._listeners.add(paramOnGroupCarTappedListener);
				return;
			}
			finally
			{
				localObject = finally;
				throw localObject;
			}
		}

		public void addOverlay(OverlayItem paramOverlayItem) {
			this.mOverlays.add(paramOverlayItem);
			populate();
		}

		public void clearItems() {
			this.mOverlays.clear();
		}

		protected OverlayItem createItem(int paramInt) {
			return (OverlayItem) this.mOverlays.get(paramInt);
		}

		public void draw(Canvas paramCanvas, MapView paramMapView,
				boolean paramBoolean) {
			super.draw(paramCanvas, paramMapView, paramBoolean);
			int i = 0;
			if (i >= this.mOverlays.size())
				return;
			Utilities.CarMarker localCarMarker = (Utilities.CarMarker) this.mOverlays
					.get(i);
			GeoPoint localGeoPoint = localCarMarker.getPoint();
			Point localPoint = new Point();
			paramMapView.getProjection().toPixels(localGeoPoint, localPoint);
			Paint localPaint = new Paint();
			localPaint.setAntiAlias(true);
			localPaint.setTextAlign(Paint.Align.CENTER);
			localPaint.setTextSize(this.mLabelTextSize);
			if (paramBoolean) {
				localPaint.setARGB(100, 0, 0, 0);
				paramCanvas.drawText(localCarMarker.getTitle(), localPoint.x
						+ this.LABEL_SHADOW_XY, -32 + localPoint.y
						+ this.LABEL_SHADOW_XY, localPaint);
			}
			while (true) {
				i++;
				break;
				localPaint.setARGB(255, 0, 0, 0);
				paramCanvas.drawText(localCarMarker.getTitle(), localPoint.x,
						-32 + localPoint.y, localPaint);
				paramCanvas.drawBitmap(Utilities.GetRotatedDirectionalMarker(
						this.DirectionalMarker, localCarMarker.Direction), -55
						+ localPoint.x, -75 + localPoint.y, localPaint);
			}
		}

		protected boolean onTap(int paramInt) {
			OverlayItem localOverlayItem = (OverlayItem) this.mOverlays
					.get(paramInt);
			if (localOverlayItem.getSnippet().length() > 0) {
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(
						this.mContext);
				localBuilder.setTitle(localOverlayItem.getTitle());
				localBuilder.setMessage(localOverlayItem.getSnippet());
				localBuilder.show();
			}
			while (true) {
				return true;
				fireGroupCarTappedEvent(localOverlayItem.getTitle());
			}
		}

		/** @deprecated */
		public void removeOnGroupCarTappedListener(Utilities.OnGroupCarTappedListener paramOnGroupCarTappedListener)
		{
			try
			{
				this._listeners.remove(paramOnGroupCarTappedListener);
				return;
			}
			finally
			{
				localObject = finally;
				throw localObject;
			}
		}

		public void removeOverlayAt(int paramInt) {
			this.mOverlays.remove(paramInt);
		}

		public void setOverlay(int paramInt, OverlayItem paramOverlayItem) {
			this.mOverlays.set(paramInt, paramOverlayItem);
			populate();
		}

		public int size() {
			return this.mOverlays.size();
		}
	}

	public static abstract interface OnGroupCarTappedListener {
		public abstract void OnGroupCarTapped(String paramString);
	}
}