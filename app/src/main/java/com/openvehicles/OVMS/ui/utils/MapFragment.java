//package com.openvehicles.OVMS.ui.utils;
//
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.actionbarsherlock.view.Menu;
//import com.actionbarsherlock.view.MenuInflater;
//import com.actionbarsherlock.view.MenuItem;
//import com.google.android.maps.GeoPoint;
//import com.google.android.maps.MapController;
//import com.google.android.maps.MapView;
//import com.google.android.maps.Overlay;
//import com.google.android.maps.OverlayItem;
//import com.openvehicles.OVMS.R;
//import com.openvehicles.OVMS.entities.CarData;
//import com.openvehicles.OVMS.ui.BaseFragment;
//import com.openvehicles.OVMS.ui.utils.BalloonItemizedOverlay;
//import com.openvehicles.OVMS.ui.utils.Ui;
//
//public class MapFragment extends BaseFragment {
//	private View mMapViewContainer;
//	private MapView mMapView;
//	private MapController mMapController;
//	private GeoPoint mLastGeoPoint;
//	private CarMarkerOverlay mCarMarkerOverlay;
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		if (mMapViewContainer == null) {
//			mMapViewContainer = ((MainActivity)getActivity()).mapview_container;
//		}
//		return mMapViewContainer;
//	}
//	
//	@Override
//	public void onDestroyView() {
//		super.onDestroyView();
//		ViewGroup parentViewGroup = (ViewGroup) mMapViewContainer.getParent();
//		if( null != parentViewGroup ) {
//			parentViewGroup.removeView(mMapViewContainer);
//		}
//	}
//	
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		mMapView = (MapView) mMapViewContainer.findViewById(R.id.mapview);
////		mMapView.setBuiltInZoomControls(true);
//		mMapController = mMapView.getController();
//		
//		List<Overlay> overlays = mMapView.getOverlays(); 
//		if (overlays.size() == 0) {
//			mCarMarkerOverlay = new CarMarkerOverlay(mMapView);
//			overlays.add(mCarMarkerOverlay);
//		} else {
//			mCarMarkerOverlay = (CarMarkerOverlay) overlays.get(0); 
//		}
//		setHasOptionsMenu(true);
//		
////		findViewById(R.id.btn_center_map).setOnClickListener(this);
//	}
//
////	@Override
////	public void onClick(View v) {
////		if (mLastGeoPoint == null) return;
////		
////		mMapController.animateTo(mLastGeoPoint);
////	}
//	
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		inflater.inflate(R.menu.center_map, menu);
//	}
//	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		if (item.getItemId() == R.id.mi_center_map) {
//			if (mLastGeoPoint != null) mMapController.animateTo(mLastGeoPoint); 
//			return true;
//		}
//		return false;
//	}
//	
//	@Override
//	public void update(CarData pCarData) {
//		mLastGeoPoint = new GeoPoint((int) (pCarData.car_latitude * 1E6),
//			(int) (pCarData.car_longitude * 1E6));
//		Log.d("OVMS", "Car on map: " + mLastGeoPoint);
//		
//		
//		String lastReportDate = pCarData.car_lastupdated == null ? "-" :
//			new SimpleDateFormat("MMM d, k:mm:ss").format(pCarData.car_lastupdated);
//		OverlayItem overlayItem = new OverlayItem(mLastGeoPoint, pCarData.sel_vehicleid,
//				String.format("Last reported: %s", lastReportDate));
//		
//		Context context = getActivity();
//		if (context != null) {
//			Drawable drawable = getResources().getDrawable(Ui.getDrawableIdentifier(context,
//					"map_" + pCarData.sel_vehicle_image));
//			mCarMarkerOverlay.setOverlayItem(overlayItem, drawable);
//			
//			mMapController.setZoom(18);
//			mMapController.animateTo(mLastGeoPoint);
//		}
//	}
//	
//	private static class CarMarkerOverlay extends BalloonItemizedOverlay<OverlayItem> {
//		private OverlayItem mOverlayItem; 
//		
//		public CarMarkerOverlay(MapView pMapView) {
//			super(boundCenter(pMapView.getContext().getResources().getDrawable(
//				R.drawable.map_car_default)), pMapView);
//			populate();
//		}
//
//		@Override
//		protected OverlayItem createItem(int i) {
//			return mOverlayItem;
//		}
//
//		@Override
//		public int size() {
//			return mOverlayItem == null ? 0 : 1;
//		}
//
//		public synchronized void setOverlayItem(OverlayItem pOverlayItem, Drawable pDrawableMarker) {
//			mOverlayItem = pOverlayItem;
//			mOverlayItem.setMarker(boundCenter(pDrawableMarker));
//			populate();
//		}
//	}
//}
