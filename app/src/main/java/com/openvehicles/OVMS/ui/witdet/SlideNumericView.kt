package com.openvehicles.OVMS.ui.witdet

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Gallery
import android.widget.TextView
import com.openvehicles.OVMS.R

class SlideNumericView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : Gallery(context, attrs) {

    var value: Int
        get() = getSelectedItem() as Int
        set(value) {
            val adapter = adapter as? NumericAdapter ?: return

            var newValue = value
            if (newValue < adapter.min) {
                newValue = adapter.min
            }
            if (newValue > adapter.max) {
                newValue = adapter.max
            }
            setSelection(adapter.startPosition + adapter.calculatePosition(newValue))
        }

    init {
        setBackgroundResource(R.drawable.bg_wheel)
        setSpacing(6)
        setUnselectedAlpha(0.2f)
        val a = context.obtainStyledAttributes(attrs, R.styleable.SlideNumericView)
        try {
            init(
                min = a.getInt(R.styleable.SlideNumericView_min, 0),
                max = a.getInt(R.styleable.SlideNumericView_max, 100),
                measure = 1
            )
        } finally {
            a.recycle()
        }
    }

    fun init(min: Int, max: Int, measure: Int) {
        val adapter = NumericAdapter(context, min, max, measure)
        setAdapter(adapter)
        setSelection(adapter.startPosition)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawShadows(canvas)
    }

    private fun drawShadows(canvas: Canvas) {
        leftShadow.setBounds(0, 0, width / 4, height)
        leftShadow.draw(canvas)
        rightShadow.setBounds(width - width / 4, 0, width, height)
        rightShadow.draw(canvas)
    }

    /*
     * Inner types
     */

    companion object {

        private val shadowColors = intArrayOf(-0x1000000, 0x00AAAAAA)
        private val leftShadow =
            GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, shadowColors)
        private val rightShadow =
            GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, shadowColors)

    }

    private class NumericAdapter(
        context: Context,
        val min: Int,
        val max: Int,
        measure: Int
    ) : BaseAdapter() {

        private val measure: Int
        private val padding: Int

        val startPosition: Int
            get() = if (size() != 0) {
                (Int.MAX_VALUE / 2) - Int.MAX_VALUE / 2 % size()
            } else {
                Int.MAX_VALUE / 2
            }

        init {
            this.measure = if (measure == 0) 1 else measure
            padding = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 6f,
                context.resources.displayMetrics
            ).toInt()
        }

        override fun getCount(): Int {
            return Int.MAX_VALUE
        }

        fun size(): Int {
            return Math.round(((max - min) / measure + 1).toFloat())
        }

        fun calculatePosition(value: Int): Int {
            if (value < min || value > max) {
                throw ArithmeticException(
                    String.format(
                        "min: %d, max: %d, val: %d",
                        min,
                        max,
                        value
                    )
                )
            }
            return (value - min) / measure
        }

        override fun getItem(position: Int): Any {
            val newPosition = preparePosition(position)
            return (min + measure * newPosition) * 100 / 100
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val textView = if (convertView != null) {
                convertView as TextView
            } else {
                TextView(parent.context).apply {
                    setTypeface(Typeface.DEFAULT_BOLD)
                    setTextColor(-0x1000000)
                    textSize = 28f
                    setPadding(padding, padding, padding, padding)
                }
            }
            textView.text = getItem(position).toString()

            return textView
        }

        private fun preparePosition(position: Int): Int {
            var newPosition = position
            if (position >= size()) {
                newPosition = position % size()
            }
            return newPosition
        }
    }
}
