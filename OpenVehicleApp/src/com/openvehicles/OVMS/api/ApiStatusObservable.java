package com.openvehicles.OVMS.api;

import java.util.ArrayList;
import java.util.List;

import com.openvehicles.OVMS.entities.CarData;

public class ApiStatusObservable {
	private static ApiStatusObservable sInstance;
	private final List<ApiStstusObserver> mObservers = new ArrayList<ApiStstusObserver>();
	
	public static ApiStatusObservable get() {
		if (sInstance == null) {
			sInstance = new ApiStatusObservable();
		}
		return sInstance; 
	}

    public void addObserver(ApiStstusObserver pObserver) {
        if (pObserver == null) {
            throw new NullPointerException();
        }
        synchronized (this) {
            if (!mObservers.contains(pObserver))
                mObservers.add(pObserver);
        }
    }

    public int countObservers() {
        return mObservers.size();
    }

    public synchronized void deleteObserver(ApiStstusObserver pObserver) {
        mObservers.remove(pObserver);
    }

    public synchronized void deleteObservers() {
        mObservers.clear();
    }

    public void notifyObservers(CarData pCarData) {
        int size = 0;
        ApiStstusObserver[] arrays = null;
        synchronized (this) {
            size = mObservers.size();
            arrays = new ApiStstusObserver[size];
            mObservers.toArray(arrays);
        }
        if (arrays != null) {
            for (ApiStstusObserver observer : arrays) {
                observer.update(pCarData);
            }
        }
    }

}
