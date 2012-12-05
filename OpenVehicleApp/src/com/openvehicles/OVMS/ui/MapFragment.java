package com.openvehicles.OVMS.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends BaseFragment {
	private View mMapViewContainer;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mMapViewContainer == null) {
			mMapViewContainer = ((MainActivity)getActivity()).mapview_container;
		}
		return mMapViewContainer;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ViewGroup parentViewGroup = (ViewGroup) mMapViewContainer.getParent();
		if( null != parentViewGroup ) {
			parentViewGroup.removeView(mMapViewContainer);
		}
	}
	
}
