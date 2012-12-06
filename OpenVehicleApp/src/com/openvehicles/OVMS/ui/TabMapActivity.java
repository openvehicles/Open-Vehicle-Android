package com.openvehicles.OVMS.ui;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
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
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.utils.RefreshStatusCallBack;

public class TabMapActivity extends MapActivity implements RefreshStatusCallBack {

	// Define the debug signature hash (Android default debug cert). Code from sigs[i].hashCode()
	protected final static int DEBUG_SIGNATURE_HASH = -1119983641;
	private Boolean _isDebugBuild = null;
	
	// Checks if this apk was built using the debug certificate
	// Used e.g. for Google Maps API key determination (from: http://whereblogger.klaki.net/2009/10/choosing-android-maps-api-key-at-run.html)
	public Boolean isDebugBuild(Context context) {
	    if (_isDebugBuild == null) {
	        try {
	            _isDebugBuild = false;
	            Signature[] sigs = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures;
	            for (int i = 0; i < sigs.length; i++) {
	                if (sigs[i].hashCode() == DEBUG_SIGNATURE_HASH) {
	                    Log.d("OVMS", "This is a debug build!");
	                    _isDebugBuild = true;
	                    break;
	                }
	            }
	        } catch (NameNotFoundException e) {
	            e.printStackTrace();
	        }      
	    }
	    return _isDebugBuild;
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (isDebugBuild(this)) {
			setContentView(R.layout.tabmap_debug);
		}
		else {
			setContentView(R.layout.tabmap_release);			
		}

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
	private Handler handler = new TabMapHandler(this);

	private class CarMarkerOverlay extends ItemizedOverlay<OverlayItem> {
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

	// Called from HandleMessage() when we need to be displayed
	public void CentreMap() {
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

	// New car data has been received from the server
	// We store our local copy, the refresh the display
	public void RefreshStatus(CarData carData) {
		data = carData;

		// add markers overlay
		mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(this.getResources().getIdentifier("map_"+data.sel_vehicle_image, "drawable", "com.openvehicles.OVMS"));
//		Bitmap bm = ((BitmapDrawable)drawable).getBitmap();
//		Bitmap bitmapOrig = Bitmap.createScaledBitmap(bm, 64, 64, false);
//		carMarkers = new CarMarkerOverlay(new BitmapDrawable(bitmapOrig), this);
		carMarkers = new CarMarkerOverlay(drawable, this);
		mapOverlays.clear();
		mapOverlays.add(carMarkers);
		handler.sendEmptyMessage(0);
	}

}

class TabMapHandler extends Handler {
	private final WeakReference<TabMapActivity> m_tabMap; 

	TabMapHandler(TabMapActivity tabMap) {
		m_tabMap = new WeakReference<TabMapActivity>(tabMap);
	}
	@Override
	public void handleMessage(Message msg) {
		TabMapActivity tabMap = m_tabMap.get();
		tabMap.CentreMap();
	}
}