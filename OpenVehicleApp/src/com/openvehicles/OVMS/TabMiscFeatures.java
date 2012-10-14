package com.openvehicles.OVMS;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TabHost;

public class TabMiscFeatures extends TabActivity implements
TabHost.OnTabChangeListener {
	AlertDialog alertDialog;
	private CarData data;
	private Handler handler = new Handler() {
		public void handleMessage(Message paramAnonymousMessage) {
			String str = TabMiscFeatures.this.getLocalActivityManager()
					.getCurrentId().trim();
			TabMiscFeatures.this.notifyTabRefresh(str);
		}
	};
	private boolean isLoggedIn;

	private void notifyTabRefresh(String paramString) {
		Log.d("Tab", "SubTab refresh: " + paramString);
		if ((paramString == null)
				|| (getLocalActivityManager().getActivity(paramString) == null))
			return;
		if (paramString.equals("tabNotifications"))
			((Tab_SubTabNotifications) getLocalActivityManager().getActivity(
					paramString)).Refresh(this.data, this.isLoggedIn);
		while (true) {
			getTabHost().invalidate();
			break;
			if (paramString.equals("tabDataUtilizations")) {
				((Tab_SubTabDataUtilizations) getLocalActivityManager()
						.getActivity(paramString)).Refresh(this.data,
								this.isLoggedIn);
			} else if (paramString.equals("tabCarSettings")) {
				((Tab_SubTabCarSettings) getLocalActivityManager().getActivity(
						paramString)).Refresh(this.data, this.isLoggedIn);
			} else {
				AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
				localBuilder
				.setMessage("(!) TAB NOT FOUND ERROR: " + paramString)
				.setCancelable(false)
				.setPositiveButton("Close",
						new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.dismiss();
					}
				});
				this.alertDialog = localBuilder.create();
				this.alertDialog.show();
				getTabHost().setCurrentTabByTag("tabInfo");
			}
		}
	}

	public void Refresh(CarData paramCarData, boolean paramBoolean) {
		this.data = paramCarData;
		this.handler.sendEmptyMessage(0);
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(2130903059);
		TabHost localTabHost = getTabHost();
		Intent localIntent1 = new Intent().setClass(this,
				Tab_SubTabNotifications.class);
		TabHost.TabSpec localTabSpec1 = localTabHost
				.newTabSpec("tabNotifications");
		localTabSpec1.setContent(localIntent1);
		localTabSpec1.setIndicator("", getResources().getDrawable(2130837589));
		localTabHost.addTab(localTabSpec1);
		Intent localIntent2 = new Intent().setClass(this,
				Tab_SubTabDataUtilizations.class);
		TabHost.TabSpec localTabSpec2 = localTabHost
				.newTabSpec("tabDataUtilizations");
		localTabSpec2.setContent(localIntent2);
		localTabSpec2.setIndicator("", getResources().getDrawable(2130837577));
		localTabHost.addTab(localTabSpec2);
		Intent localIntent3 = new Intent().setClass(this,
				Tab_SubTabCarSettings.class);
		TabHost.TabSpec localTabSpec3 = localTabHost
				.newTabSpec("tabCarSettings");
		localTabSpec3.setContent(localIntent3);
		localTabSpec3.setIndicator("", getResources().getDrawable(2130837582));
		localTabHost.addTab(localTabSpec3);
		localTabHost.setOnTabChangedListener(this);
	}

	public void onTabChanged(String paramString) {
		this.handler.sendEmptyMessage(0);
	}
}