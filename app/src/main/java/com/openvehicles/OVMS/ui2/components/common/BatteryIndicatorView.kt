package com.openvehicles.OVMS.ui2.components.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.ui2.components.common.limit

class BatteryIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_BORDER_WIDTH = 0.04f
        const val DEFAULT_INTERNAL_SPACING = 2.8f
    }

    private var batteryLevelPaint: Paint = Paint()
    private var borderThickness = 0f
    private var batteryLevelCornerRadius = 0f
    private val rect = RectF(0f, 0f, 0f, 0f)

    /**
     * Border coefficient. Border is part of icon, so this is just relative dimension to draw other
     * aspects
     */
    private val borderThicknessPercent: Float = DEFAULT_BORDER_WIDTH

    /**
     * Charging percent
     */
    var batteryLevel = 0
        set(value) {
            field = value.limit(0, 100)
            invalidate()
        }


    /**
     * View infill color.
     */
    @ColorInt
    var infillColor = Color.WHITE
        set(newColor) {
            field = newColor
            batteryLevelPaint = Paint().apply {
                color = newColor
                isAntiAlias = true
            }
            invalidate()
        }

    /**
     * View border color
     */
    @ColorInt
    var borderColor = Color.BLACK
        set(newBorderColor) {
            field = newBorderColor
            setColorFilter(newBorderColor)
        }

    /**
     * Spacing between border and internal charge-level rect. Relative as [borderThicknessPercent],
     * but relative to border thickness. Actually is percent of border thickness.
     */
    var internalSpacing: Float = DEFAULT_INTERNAL_SPACING
        set(value) {
            field = value
            invalidate()
        }

    init {
        //attrs?.also(::obtainAttributes)
        super.setScaleType(ScaleType.FIT_XY)




        super.setImageResource(R.drawable.ic_battery)

        setWillNotDraw(false)
    }

   /* private fun obtainAttributes(attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BatteryIndicatorView,
            0, 0
        )
        try {
            borderColor = a.getColor(R.styleable.BatteryIndicatorView_bat_borderColor, Color.BLACK)
            infillColor = a.getColor(R.styleable.BatteryIndicatorView_bat_infillColor, Color.WHITE)
            batteryLevel = a.getInteger(R.styleable.BatteryIndicatorView_bat_percent, 0)
            internalSpacing =
                a.getFloat(R.styleable.BatteryIndicatorView_bat_internalSpacing, DEFAULT_INTERNAL_SPACING)
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthSpec = widthMeasureSpec
        var heightSpec = heightMeasureSpec
        val min = kotlin.math.min(MeasureSpec.getSize(widthSpec), MeasureSpec.getSize(heightSpec))
        widthSpec =
            MeasureSpec.makeMeasureSpec((min.toFloat() / 24f * 18f).toInt(), MeasureSpec.EXACTLY)
        heightSpec
            MeasureSpec.makeMeasureSpec((min.toFloat() / 18f * 24f).toInt(), MeasureSpec.EXACTLY)
        borderThickness = min.toFloat() * borderThicknessPercent
        batteryLevelCornerRadius = borderThickness
        super.onMeasure(widthSpec, heightSpec)
    }

    fun setInfillColorRes(@ColorRes colorRes: Int) {
        infillColor = ContextCompat.getColor(context, colorRes)
    }

    fun setBorderColorRes(@ColorRes colorRes: Int) {
        borderColor = ContextCompat.getColor(context, colorRes)
    }


    override fun onDraw(canvas: Canvas) {
        val rectMarginBorderMultiplier = 3 + internalSpacing
        val width = getWidth().toFloat() - 2 * rectMarginBorderMultiplier * borderThickness
        val height =
            ((getHeight().toFloat()) - (rectMarginBorderMultiplier+4f) * borderThickness) * batteryLevel.toFloat() / 100f

        val left = rectMarginBorderMultiplier * borderThickness
        val top = (getHeight().toFloat() - borderThickness - 1f - height)-6f

        val right = left + width
        val bottom =  top + height
        rect[left, top, right] = bottom
        canvas.drawRoundRect(
            rect,
            batteryLevelCornerRadius,
            batteryLevelCornerRadius,
            batteryLevelPaint
        )
        super.onDraw(canvas)
    }*/
}

private fun Float.limit(min: Float, max: Float): Float {
    return if (this < min) min else if (this > max) max else this
}

private fun Int.limit(min: Int, max: Int): Int {
    return if (this < min) min else if (this > max) max else this
}