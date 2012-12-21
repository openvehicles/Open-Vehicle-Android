package com.openvehicles.OVMS.utils;

public class StorageCars {
	private static StorageCars sInstance;
	
	public static StorageCars getInstance() {
		if (sInstance == null) {
			sInstance = new StorageCars(); 
		}
		return sInstance;
	}

}
