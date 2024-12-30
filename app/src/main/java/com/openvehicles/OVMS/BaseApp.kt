package com.openvehicles.OVMS

import android.app.Application
import android.content.Context
import com.maltaisn.icondialog.pack.IconPack
import com.maltaisn.icondialog.pack.IconPackLoader
import com.maltaisn.iconpack.defaultpack.createDefaultIconPack

class BaseApp : Application() {

    var iconPack: IconPack? = null

    override fun onCreate() {
        super.onCreate()

        app = this
        context = applicationContext

        loadIconPack()
    }

    private fun loadIconPack() {
        // Create an icon pack loader with application context.
        val loader = IconPackLoader(this)

        // Create an icon pack and load all drawables.
        val iconPack = createDefaultIconPack(loader)
        iconPack.loadDrawables(loader.drawableLoader)

        this.iconPack = iconPack
    }

    companion object {

        // TODO: Do not place Android context classes in static fields, this is a memory leak
        var app: BaseApp? = null
        var context: Context? = null

    }

}