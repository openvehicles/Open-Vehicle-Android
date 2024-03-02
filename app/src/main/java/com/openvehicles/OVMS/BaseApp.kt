package com.openvehicles.OVMS

import android.app.Application
import android.content.Context

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        app = this
        context = applicationContext
    }

    companion object {

        // TODO: Do not place Android context classes in static fields, this is a memory leak
        var app: BaseApp? = null
        var context: Context? = null

    }

}