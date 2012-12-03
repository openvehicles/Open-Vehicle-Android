package com.openvehicles.OVMS.ui;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

public abstract class CommandReceiver extends BroadcastReceiver {
	private static final String EXT_DATA 			= "ext_data";
	private static final String ACTION_RECEIVE	= "com.openvehicles.OVMS.Receive";
	private static final String ACTION_SEND		= "com.openvehicles.OVMS.Send";
	
	private static final long TIMEOUT				= 60 * 1000;
	private static final int MSG_DISMISS_DLG		= 0xF0;
	
	protected Context mContext;
	private final TimeOutHandler mHandler;
	private ProgressDialog mDlg;
	
	public CommandReceiver() {
		mHandler = new TimeOutHandler(this);
	}
	
	public void registerAsSender(Activity pActivity) {
		mContext = pActivity;
		pActivity.registerReceiver(this, new IntentFilter(ACTION_SEND));
	}
	
	public void registerAsReceiver(Activity pActivity) {
		mContext = pActivity;
		pActivity.registerReceiver(this, new IntentFilter(ACTION_RECEIVE));
	}
	
	public void unregister(Activity pActivity) {
		pActivity.unregisterReceiver(this);
	}
	
	public void dismissDialog() {
		if (mDlg != null) mDlg.dismiss();
	}
	
	@Override
	public void onReceive(Context pContext, Intent pIntent) {
		String action = pIntent.getAction();
		String data = pIntent.getStringExtra(EXT_DATA);
		
		if (ACTION_SEND.equals(action)) {
			onCommand(data);
		} else
		if (ACTION_RECEIVE.equals(action)) {
			mHandler.removeMessages(MSG_DISMISS_DLG);
			mHandler.sendEmptyMessageDelayed(MSG_DISMISS_DLG, 1300);
			onResult(data.split(",\\s*"));
		}
	}
	
	public void sendCommand(int pResIdMessage, String pCommand) {
		if (mContext == null) return;
		sendCommand(mContext.getString(pResIdMessage), pCommand);
	}
	
	public void sendCommand(String pMessage, String pCommand) {
		if (mContext == null) return;
		
		if (mDlg == null) {
			mDlg = new ProgressDialog(mContext);
//			mDlg.setCancelable(false);
		}
		mDlg.setMessage(pMessage);
		mDlg.show();
		
		Intent intent = new Intent(ACTION_SEND);
		intent.putExtra(EXT_DATA, pCommand);
		mContext.sendBroadcast(intent);
		
		mHandler.sendEmptyMessageDelayed(MSG_DISMISS_DLG, TIMEOUT);
	}
	
	public void sendResult(String pMsg) {
		if (mContext == null) return;
		
		Intent intent = new Intent(ACTION_RECEIVE);
		intent.putExtra(EXT_DATA, pMsg);
		mContext.sendBroadcast(intent);
	}
	
	public abstract void onCommand(String pCommand);
	public abstract void onResult(String[] pData);
	
	private static class TimeOutHandler extends Handler {
		private final WeakReference<CommandReceiver> mParent;
		
		public TimeOutHandler(CommandReceiver pParent) {
			mParent = new WeakReference<CommandReceiver>(pParent);
		}
		
		@Override
		public void handleMessage(Message msg) {
			if (msg.what != MSG_DISMISS_DLG) return;
			mParent.get().dismissDialog();
		}
	}
}
