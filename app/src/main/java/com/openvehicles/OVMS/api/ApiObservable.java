package com.openvehicles.OVMS.api;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;

import com.openvehicles.OVMS.entities.CarData;

public class ApiObservable {
	private static ApiObservable sInstance;
	
	private final List<ApiObserver> mObservers = new ArrayList<ApiObserver>();
	private final NotifyOneHandler mHandler;
	private boolean mIsLoggedIn = false;
	
	public static ApiObservable get() {
		if (sInstance == null) {
			sInstance = new ApiObservable();
		}
		return sInstance; 
	}
	
	public ApiObservable() {
		mHandler = new NotifyOneHandler(this);		
	}

    public void addObserver(ApiObserver pObserver) {
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

    public synchronized void deleteObserver(ApiObserver pObserver) {
        mObservers.remove(pObserver);
    }

    public synchronized void deleteObservers() {
        mObservers.clear();
    }
    
    public void notifyOnBind(ApiService pService) {
        mIsLoggedIn = pService.isLoggedIn();
		int size = 0;
        ApiObserver[] arrays = null;
        synchronized (this) {
            size = mObservers.size();
            arrays = new ApiObserver[size];
            mObservers.toArray(arrays);
        }
		for (ApiObserver observer : arrays) {
			observer.onServiceAvailable(pService);
			observer.onServiceLoggedIn(pService, mIsLoggedIn);
		}
    }

	public void notifyLoggedIn(ApiService pService, boolean pIsLoggedIn) {
		// Only notify on state changes:
		if (mIsLoggedIn == pIsLoggedIn) return;
		mIsLoggedIn = pIsLoggedIn;

		// OK, notify observers:
		int size = 0;
		ApiObserver[] arrays = null;
		synchronized (this) {
			size = mObservers.size();
			arrays = new ApiObserver[size];
			mObservers.toArray(arrays);
		}
		for (ApiObserver observer : arrays) {
			observer.onServiceLoggedIn(pService, pIsLoggedIn);
		}
	}

    public void notifyUpdate(CarData pCarData) {
    	mHandler.notifyUpdate(pCarData);
    }
    
    private void notifyOneUpdate(CarData pCarData) {
        int size = 0;
        ApiObserver[] arrays = null;
        synchronized (this) {
            size = mObservers.size();
            arrays = new ApiObserver[size];
            mObservers.toArray(arrays);
        }
        if (arrays != null) {
            for (ApiObserver observer : arrays) {
                observer.update(pCarData);
            }
        }
    }
    
    private static class NotifyOneHandler extends Handler {
		private static final int WHAT = 100; 
    	private final WeakReference<ApiObservable> mParent;
		
		public NotifyOneHandler(ApiObservable pParent) {
			mParent = new WeakReference<ApiObservable>(pParent);
		}
		
		public void notifyUpdate(CarData pCarData) {
			Message msg = new Message();
			msg.what = WHAT;
			msg.obj = pCarData;
			
			removeMessages(WHAT, pCarData);
			sendMessageDelayed(msg, 300);
		}
		
		@Override
		public void handleMessage(Message msg) {
			mParent.get().notifyOneUpdate((CarData) msg.obj);
		}
	}
    
}
