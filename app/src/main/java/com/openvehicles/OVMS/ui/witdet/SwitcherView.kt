@file:Suppress("DEPRECATION")

package com.openvehicles.OVMS.ui.witdet

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.ui.utils.Ui
import androidx.core.view.isEmpty

class SwitcherView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), View.OnClickListener {

    private var listener: Ui.OnChangeListener<SwitcherView>? = null

    private var _selected = -1
    var selected: Int
        get() = if (_selected < 0) 0 else _selected
        set(pSelected) {
            if (pSelected < 0 || pSelected > childCount - 1) {
                throw IndexOutOfBoundsException("Item out of range")
            }
            onClick(getChildAt(pSelected))
        }

    init {
        orientation = HORIZONTAL
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SwitcherView)
        var values: Array<CharSequence>? = try {
            attributes.getTextArray(R.styleable.SwitcherView_android_entries)
        } finally {
            attributes.recycle()
        }
        if (values == null) {
            values = arrayOf("Off", "On")
        }
        setValues(*values)
    }

    fun setOnChangeListener(listener: Ui.OnChangeListener<SwitcherView>?) {
        this.listener = listener
    }

    private fun setValues(vararg pValues: CharSequence?) {
        val side = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6f, resources.displayMetrics)
            .toInt()
        val top = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics)
            .toInt()

        removeAllViews()

        for (text in pValues) {
            val button = SwitcherButton(context)
            if (_selected < 0 && isEmpty()) {
                button.setChecked(true)
            }
            button.setSingleLine()
            button.text = text
            if (!isInEditMode) {
                button.setTextAppearance(context, android.R.style.TextAppearance_Small)
            }
            button.setTextColor(-0x1)
            button.setBackgroundResource(R.drawable.item_choice)
            button.setPadding(side, top, side, top)
            button.setOnClickListener(this)
            addView(button)
        }
    }

    override fun onClick(v: View) {
        val count = childCount
        var button: SwitcherButton
        for (i in 0 until count) {
            button = getChildAt(i) as SwitcherButton
            button.setChecked(button === v)
            if (button === v) {
                _selected = i
            }
        }
        if (listener != null) {
            listener!!.onAction(this)
        }
    }

    /*
     * Inner types
     */

    class SwitcherButton(context: Context?) : CompoundButton(context) {
        init {
            isFocusable = false
        }
    }
}
