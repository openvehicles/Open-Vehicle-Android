package com.openvehicles.OVMS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class TabMap extends MapActivity implements RefreshStatusCallBack {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tabmap);

		ImageButton btn = (ImageButton) findViewById(R.id.btnCenterMap);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				handler.sendEmptyMessage(0);
			}
		});

		mapView = (MapView) findViewById(R.id.mapview);
		mc = mapView.getController();
		mapView.setBuiltInZoomControls(true);
	}

	private List<Overlay> mapOverlays;
	private CarMarkerOverlay carMarkers; 
	private MapView mapView;
	private MapController mc;
	private CarData data;

	private class CarMarkerOverlay extends ItemizedOverlay {
		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		private Context mContext;

		public void clearItems()
		{
			mOverlays.clear();
		}

		public void addOverlay(OverlayItem overlay) {
			mOverlays.add(overlay);
			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			// TODO Auto-generated method stub
			return mOverlays.get(i);
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return mOverlays.size();
		}

		public CarMarkerOverlay(Drawable defaultMarker, Context context) {
			super(boundCenterBottom(defaultMarker));
			mContext = context;
		}

		@Override
		protected boolean onTap(int index) {
			OverlayItem item = mOverlays.get(index);
			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
			dialog.setTitle(item.getTitle());
			dialog.setMessage(item.getSnippet());
			dialog.show();
			return true;
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// MyLocationOverlay myLocationOverlay = new MyLocationOverlay(this,
			// mapView);
			// mapView.getOverlays().add(myLocationOverlay);
			// myLocationOverlay.enableCompass(); // if you want to display a
			// compass also
			// myLocationOverlay.enableMyLocation();

			Log.d("OVMS", "Centering Map");
			int lat = (int) (data.car_latitude * 1E6);
			int lng = (int) (data.car_longitude * 1E6);
			GeoPoint point = new GeoPoint(lat, lng);

			carMarkers.clearItems();
			String lastReportDate = "-";
			if (data.car_lastupdated != null)
				lastReportDate = (new SimpleDateFormat("MMM d, k:mm:ss").format(data.car_lastupdated));
			OverlayItem overlayitem = new OverlayItem(point, data.sel_vehicleid, String.format("Last reported: %s", lastReportDate));
			carMarkers.addOverlay(overlayitem);

			mc.setCenter(point);
			mc.setZoom(18);
			mapView.invalidate();
		}
	};

	public void RefreshStatus(CarData carData) {
		data = carData;

		// add markers overlay
		mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(this.getResources().getIdentifier(data.sel_vehicle_image, "drawable", "com.openvehicles.OVMS"));
		Bitmap bm = ((BitmapDrawable)drawable).getBitmap();
		Bitmap bitmapOrig = Bitmap.createScaledBitmap(bm, 96, 44, false);
		carMarkers = new CarMarkerOverlay(new BitmapDrawable(bitmapOrig), this);
		mapOverlays.clear();
		mapOverlays.add(carMarkers);
		handler.sendEmptyMessage(0);
	}

}
