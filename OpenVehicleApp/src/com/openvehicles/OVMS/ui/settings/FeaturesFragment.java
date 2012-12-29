package com.openvehicles.OVMS.ui.settings;

import java.util.Arrays;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openvehicles.OVMS.api.OnResultCommandListenner;
import com.openvehicles.OVMS.ui.BaseFragment;

public class FeaturesFragment extends BaseFragment implements OnResultCommandListenner {
	TextView dbugTextView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		dbugTextView = new TextView(container.getContext());
		return dbugTextView;
	}

	@Override
	public void onResultCommand(String[] data) {
		Log.e("DEBUG", "onResultCommand: " + Arrays.toString(data));
		dbugTextView.setText(Arrays.toString(data));
	}
	
	@Override
	public void onServiceBind() {
		Log.e("DEBUG", "onServiceConnected");
		sendCommand("18", this);
	}
}
