package com.openvehicles.OVMS.entities;

import androidx.annotation.Nullable;

public class StoredCommand
	implements Comparable<StoredCommand> {

	public long mKey;
	public String mTitle;
	public String mCommand;

	public StoredCommand(long pKey, String pTitle, String pCommand) {
		this.mKey = pKey;
		this.mTitle = pTitle;
		this.mCommand = pCommand;
	}
	public StoredCommand(String pTitle, String pCommand) {
		this(0, pTitle, pCommand);
	}

	@Override
	public int compareTo(StoredCommand o) {
		return mTitle.compareToIgnoreCase(o.mTitle);
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj instanceof StoredCommand) {
			return (((StoredCommand)obj).mKey == this.mKey);
		} else {
			return false;
		}
	}
}
