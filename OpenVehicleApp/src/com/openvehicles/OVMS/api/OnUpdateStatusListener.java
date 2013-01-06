package com.openvehicles.OVMS.api;

public interface OnUpdateStatusListener {
	public void onUpdateStatus();
	public void onServerSocketError(Throwable e);
	public void onResultCommand(String pCmd);
	public void onLoginBegin();
	public void onLoginComplete();
}
