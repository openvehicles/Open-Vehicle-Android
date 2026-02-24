package com.openvehicles.OVMS.api

import android.os.Handler
import android.os.Message
import com.openvehicles.OVMS.entities.CarData
import java.lang.ref.WeakReference

object ApiObservable {

    private val observers = mutableListOf<ApiObserver>()
    private val handler = NotifyOneHandler(this)
    private var isLoggedIn = false

    fun addObserver(observer: ApiObserver) {
        synchronized(this) {
            if (!observers.contains(observer)) observers.add(observer)
        }
    }

    fun countObservers(): Int {
        return observers.size
    }

    @Synchronized
    fun deleteObserver(observer: ApiObserver) {
        observers.remove(observer)
    }

    @Synchronized
    fun deleteObservers() {
        observers.clear()
    }

    fun notifyOnBind(service: ApiService) {
        isLoggedIn = service.isLoggedIn()
        var arrays: Array<ApiObserver>
        synchronized(this) {
            arrays = observers.toTypedArray()
        }
        for (observer in arrays) {
            observer.onServiceAvailable(service)
            observer.onServiceLoggedIn(service, isLoggedIn)
        }
    }

    fun notifyLoggedIn(service: ApiService?, isLoggedIn: Boolean) {
        // Only notify on state changes:
        if (this.isLoggedIn == isLoggedIn) {
            return
        }
        this.isLoggedIn = isLoggedIn

        // OK, notify observers:
        var arrays: Array<ApiObserver>
        synchronized(this) {
            arrays = observers.toTypedArray()
        }
        for (observer in arrays) {
            observer.onServiceLoggedIn(service, isLoggedIn)
        }
    }

    fun notifyUpdate(pCarData: CarData?) {
        handler.notifyUpdate(pCarData)
    }

    private fun notifyOneUpdate(carData: CarData) {
        var arrays: Array<ApiObserver>
        synchronized(this) {
            arrays = observers.toTypedArray()
        }
        for (observer in arrays) {
            observer.update(carData)
        }
    }


    private class NotifyOneHandler(parent: ApiObservable) : Handler() {

        private val parent = WeakReference(parent)

        fun notifyUpdate(pCarData: CarData?) {
            val msg = Message()
            msg.what = WHAT
            msg.obj = pCarData
            removeMessages(WHAT, pCarData)
            sendMessageDelayed(msg, 300)
        }

        override fun handleMessage(msg: Message) {
            parent.get()!!.notifyOneUpdate(msg.obj as CarData)
        }

        companion object {

            private const val WHAT = 100

        }
    }

}