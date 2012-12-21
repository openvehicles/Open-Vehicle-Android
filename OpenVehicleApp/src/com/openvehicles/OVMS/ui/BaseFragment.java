package com.openvehicles.OVMS.ui;

import android.app.Activity;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragment;
import com.openvehicles.OVMS.api.ApiStatusObservable;
import com.openvehicles.OVMS.api.ApiStstusObserver;
import com.openvehicles.OVMS.entities.CarData;

public class BaseFragment extends SherlockFragment implements ApiStstusObserver {

	public void registerForUpdate() {
		ApiStatusObservable.get().addObserver(this);
	}

	public void unregisterForUpdate() {
		ApiStatusObservable.get().deleteObserver(this);
	}

	@Override
	public void update(CarData pCarData) {
		// TODO Auto-generated method stub
	}
	
	public View findViewById(int pResId) {
		return getView().findViewById(pResId);
	}
	
	public void sendCommand(int pResIdMessage, String pCommand) {
		Activity activity = getActivity();
		if (activity instanceof MainActivity) {
			((MainActivity)activity).sendCommand(pResIdMessage, pCommand);
		}
	}
	
	public void sendCommand(String pMessage, String pCommand) {
		Activity activity = getActivity();
		if (activity instanceof MainActivity) {
			((MainActivity)activity).sendCommand(pMessage, pCommand);
		}
	}
	
	public void changeCar(CarData pCarData) {
		Activity activity = getActivity();
		if (activity instanceof MainActivity) {
			MainActivity mainActivity = (MainActivity) activity;

			CarData currentCar = mainActivity.getCardata();
			if (currentCar == null || pCarData == null 
					|| pCarData.sel_vehicleid.equalsIgnoreCase(currentCar.sel_vehicleid)) return;
			mainActivity.changeCar(pCarData);
		}
	}
	
//	public ArrayList<CarData> getSavedCarData() {
//		Activity activity = getActivity();
//		if (activity instanceof MainActivity) {
//			return ((MainActivity)activity).getSavedCarData();
//		}
//		return null;
//	}
	
	
//	private void destroyView(View pView) {
//		if (!(pView instanceof ViewGroup)) return;
//		ViewGroup vg = (ViewGroup) pView;
//		int count = vg.getChildCount();
//		for (int i=0; i<count; i++) {
//			View v = vg.getChildAt(i);
//			if (v instanceof ImageView) onDestroyBitmap((ImageView)v);
//			if (v instanceof ViewGroup) destroyView(v);
//		}
//	}
//	
//	protected void onDestroyBitmap(ImageView pImageView) {
//		Drawable drawable = pImageView.getDrawable();
//		if (drawable instanceof BitmapDrawable) {
//			Log.d("DEBUG", "DestroyBitmap: " + drawable);
//		    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//		    Bitmap bitmap = bitmapDrawable.getBitmap();
//		    if (bitmap != null) bitmap.recycle();
//		    bitmap = null;
//		    pImageView.setImageBitmap(null);
//		}
//	}
}
