package com.openvehicles.OVMS.ui.witdet

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.openvehicles.OVMS.R
import kotlin.math.max
import kotlin.math.min

class ScaleLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

    private var contentWidth = 0f
    private var contentHeigth = 0f
    private var scale = 0f
    private var isScale = false
    private var onScale: Runnable? = null

    private val padding: Int = TypedValue
        .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, resources.displayMetrics)
        .toInt()

    fun getScale(): Float {
        return scale
    }

    fun setOnScale(pOnScale: Runnable?) {
        onScale = pOnScale
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        val lp = child.layoutParams as LayoutParams
        contentWidth = max(contentWidth.toDouble(), (lp.x + lp.width).toDouble()).toFloat()
        contentHeigth = max(contentHeigth.toDouble(), (lp.y + lp.height).toDouble()).toFloat()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                val lp = child.layoutParams as LayoutParams
                child.layout(
                    lp.x, lp.y,
                    lp.x + child.measuredWidth,
                    lp.y + child.measuredHeight
                )
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isInEditMode) {
            onDesignMeasure(widthMeasureSpec, heightMeasureSpec)
        } else {
            onRuntimeMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    private fun onRuntimeMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        val scaleW = (w - padding * 2) / contentWidth
        val scaleH = (h - padding * 2) / contentHeigth
        scale = min(scaleW.toDouble(), scaleH.toDouble()).toFloat()
        if (!isScale) {
            val offsetX = Math.round((w - padding * 2 - contentWidth * scale) * 0.5f)
            val offsetY = Math.round((h - padding * 2 - contentHeigth * scale) * 0.5f)
            val count = childCount
            for (i in 0 until count) {
                val child = getChildAt(i)
                val lp = child.layoutParams as LayoutParams
                lp.x = padding + offsetX + (lp.x * scale).toInt()
                lp.y = padding + offsetY + (lp.y * scale).toInt()
                lp.width = (lp.width * scale).toInt()
                lp.height = (lp.height * scale).toInt()
                if (child is TextView) {
                    val tv = child
                    tv.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        (tv.textSize * scale).toInt().toFloat()
                    )
                }
            }
            isScale = true
            if (onScale != null) onScale!!.run()
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(resolveSize(w, widthMeasureSpec), resolveSize(h, heightMeasureSpec))
    }

    private fun onDesignMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = childCount
        var maxHeight = 0
        var maxWidth = 0
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                var childRight: Int
                var childBottom: Int
                val lp = child.layoutParams as LayoutParams
                childRight = lp.x + child.measuredWidth
                childBottom = lp.y + child.measuredHeight
                maxWidth = max(maxWidth.toDouble(), childRight.toDouble()).toInt()
                maxHeight = max(maxHeight.toDouble(), childBottom.toDouble()).toInt()
            }
        }
        maxHeight = max(maxHeight.toDouble(), suggestedMinimumHeight.toDouble()).toInt()
        maxWidth = max(maxWidth.toDouble(), suggestedMinimumWidth.toDouble()).toInt()
        setMeasuredDimension(
            resolveSize(maxWidth, widthMeasureSpec),
            resolveSize(maxHeight, heightMeasureSpec)
        )
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return LayoutParams(p)
    }

    /*
     * Inner types
     */

    class LayoutParams : ViewGroup.LayoutParams {

        var x = 0
        var y = 0

        constructor(width: Int, height: Int, x: Int, y: Int) : super(width, height) {
            this.x = x
            this.y = y
        }

        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
            val a = c.obtainStyledAttributes(attrs, R.styleable.ScaleLayout_Layout)
            x = a.getDimensionPixelOffset(R.styleable.ScaleLayout_Layout_android_layout_x, 0)
            y = a.getDimensionPixelOffset(R.styleable.ScaleLayout_Layout_android_layout_y, 0)
            a.recycle()
        }

        constructor(source: ViewGroup.LayoutParams?) : super(source)
    }
}
