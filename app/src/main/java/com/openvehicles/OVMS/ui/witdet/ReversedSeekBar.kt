package com.openvehicles.OVMS.ui.witdet

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar

class ReversedSeekBar(
    paramContext: Context?,
    paramAttributeSet: AttributeSet?
) : AppCompatSeekBar(paramContext!!, paramAttributeSet) {

    private var isReversed = true

    override fun onDraw(canvas: Canvas) {
        if (isReversed) {
            canvas.scale(-1.0f, 1.0f, width / 2.0f, height / 2.0f)
        }

        try {
            super.onDraw(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        if (isReversed) {
            motionEvent.setLocation(
                width - motionEvent.x,
                motionEvent.y
            )
        }

        return super.onTouchEvent(motionEvent)
    }
}