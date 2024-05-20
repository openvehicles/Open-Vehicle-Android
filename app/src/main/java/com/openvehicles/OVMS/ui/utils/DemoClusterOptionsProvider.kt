/*
 * Copyright (C) 2013 Maciej Górski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.openvehicles.OVMS.ui.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.Rect
import androidx.collection.LruCache
import com.androidmapsextensions.ClusterOptions
import com.androidmapsextensions.ClusterOptionsProvider
import com.androidmapsextensions.Marker
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.openvehicles.OVMS.R

class DemoClusterOptionsProvider(
    resources: Resources
) : ClusterOptionsProvider {

    private val baseBitmaps: Array<Bitmap?> = arrayOfNulls(res.size)
    private val cache = LruCache<Int, BitmapDescriptor>(128)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds = Rect()
    private val clusterOptions = ClusterOptions().anchor(0.5f, 0.5f)

    init {
        for (i in res.indices) {
            baseBitmaps[i] = BitmapFactory.decodeResource(resources, res[i])
        }
        paint.setColor(Color.WHITE)
        paint.textAlign = Align.CENTER
        paint.textSize = resources.getDimension(R.dimen.text_size)
    }

    override fun getClusterOptions(markers: List<Marker>): ClusterOptions {
        val markersCount = markers.size
        val cachedIcon = cache[markersCount]
        if (cachedIcon != null) {
            return clusterOptions.icon(cachedIcon)
        }
        var base: Bitmap?
        var i = 0
        do {
            base = baseBitmaps[i]
        } while (markersCount >= forCounts[i++])
        val bitmap = base!!.copy(Bitmap.Config.ARGB_8888, true)
        val text = markersCount.toString()
        paint.getTextBounds(text, 0, text.length, bounds)
        val x = bitmap.getWidth() / 2.0f
        val y = (bitmap.getHeight() - bounds.height()) / 2.0f - bounds.top
        val canvas = Canvas(bitmap)
        canvas.drawText(text, x, y, paint)
        val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
        cache.put(markersCount, icon)
        return clusterOptions.icon(icon)
    }

    /*
     * Inner types
     */

    companion object {
        private val res = intArrayOf(R.drawable.m1, R.drawable.m2, R.drawable.m3, R.drawable.m4, R.drawable.m5)
        private val forCounts = intArrayOf(10, 100, 1000, 10000, Int.MAX_VALUE)
    }
}
