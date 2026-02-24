package com.openvehicles.OVMS.ui2.misc

import android.graphics.drawable.AnimationDrawable
import android.os.SystemClock

class CarAnimationDrawable : AnimationDrawable() {
    @Volatile
    private var duration = 0 //its volatile because another thread will update its value
    private var currentFrame = 0
    override fun run() {
        val n = numberOfFrames
        currentFrame++
        if (currentFrame >= n) {
            currentFrame = 0
        }
        selectDrawable(currentFrame)
        scheduleSelf(this, SystemClock.uptimeMillis() + duration)
    }

    fun setDuration(duration: Int) {
        this.duration = duration
        //we have to do the following or the next frame will be displayed after the old duration
        unscheduleSelf(this)
        selectDrawable(currentFrame)
        scheduleSelf(this, SystemClock.uptimeMillis() + duration)
    }
}