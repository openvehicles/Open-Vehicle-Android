package com.openvehicles.OVMS.ui.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.openvehicles.OVMS.R
import com.openvehicles.OVMS.ui.validators.ValidationException
import com.openvehicles.OVMS.ui.validators.Validator

object Ui {

    private const val TAG = "Ui"

    fun showPinDialog(context: Context, msgResId: Int, listener: OnChangeListener<String?>?, newUi: Boolean = false) {
        showPinDialog(context, msgResId, msgResId, listener, newUi)
    }

    fun showPinDialog(
        context: Context,
        titleResId: Int,
        buttonResId: Int,
        listener: OnChangeListener<String?>?,
        newUi: Boolean = false
    ) {
        showPinDialog(context, titleResId, buttonResId, true, listener, newUi)
    }

    @JvmStatic
    fun showPinDialog(
        context: Context,
        titleResId: Int,
        buttonResId: Int,
        isPin: Boolean,
        listener: OnChangeListener<String?>?,
        newUi: Boolean = false
    ) {
        showPinDialog(
            context,
            context.getString(titleResId),
            null,
            buttonResId,
            isPin,
            listener,
            newUi
        )
    }

    @JvmStatic
    fun showPinDialog(
        context: Context,
        title: String?,
        value: String?,
        buttonResId: Int,
        isPin: Boolean,
        listener: OnChangeListener<String?>?,
        newUi: Boolean = false
    ) {
        val view = LayoutInflater.from(context).inflate(if (newUi) R.layout.dlg_pin_v2 else R.layout.dlg_pin, null)
        val et = view.findViewById<View>(R.id.etxt_input_value) as EditText
        et.setText(value)
        if (isPin) {
            et.setHint(R.string.lb_enter_pin)
        } else {
            et.setTransformationMethod(null)
        }
        val dialog = (if (newUi) MaterialAlertDialogBuilder(context) else AlertDialog.Builder(context))
            .setMessage(title)
            .setView(view)
            .setNegativeButton(R.string.Cancel, null)
            .setPositiveButton(buttonResId) { dialog, which ->
                val etxtPin = (dialog as AppCompatDialog).findViewById<View>(R.id.etxt_input_value) as EditText?
                listener?.onAction(etxtPin!!.getText().toString())
            }
            .create()
        dialog.setOnShowListener { dlg ->
            val etxtPin = (dlg as AppCompatDialog).findViewById<View>(R.id.etxt_input_value) as EditText?
            val imm = etxtPin!!.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etxtPin, InputMethodManager.SHOW_IMPLICIT)
            etxtPin.selectAll()
            etxtPin.requestFocus()
        }
        dialog.show()
    }

    @JvmStatic
    fun showEditDialog(
        context: Context,
        title: String?,
        value: String?,
        buttonResId: Int,
        isPassword: Boolean,
        listener: OnChangeListener<String?>?,
        newUi: Boolean = false
    ) {
        val view = LayoutInflater.from(context).inflate(if (newUi) R.layout.dlg_edit_v2 else R.layout.dlg_edit, null)
        val et = view.findViewById<View>(R.id.etxt_input_value) as EditText
        et.setText(value)
        if (isPassword) {
            et.setHint(R.string.lb_enter_passwd)
            et.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            et.setTransformationMethod(PasswordTransformationMethod.getInstance())
        }
        val dialog = (if (newUi) MaterialAlertDialogBuilder(context) else AlertDialog.Builder(context))
            .setMessage(title)
            .setView(view)
            .setNegativeButton(R.string.Cancel, null)
            .setPositiveButton(buttonResId) { dialog, which ->
                val etxtPin = (dialog as AppCompatDialog).findViewById<View>(R.id.etxt_input_value) as EditText?
                listener?.onAction(etxtPin!!.getText().toString())
            }
            .create()
        dialog.setOnShowListener { dlg ->
            val etxtPin = (dlg as AppCompatDialog).findViewById<View>(R.id.etxt_input_value) as EditText?
            val imm = etxtPin!!.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etxtPin, InputMethodManager.SHOW_IMPLICIT)
            etxtPin.selectAll()
        }
        dialog.show()
    }

    @JvmStatic
    fun getDrawableIdentifier(context: Context?, name: String?): Int {
        return if (name == null || context == null) {
            0
        } else {
            context.resources.getIdentifier(
                name,
                "drawable",
                context.packageName
            )
        }
    }

    @JvmStatic
    fun getStringIdentifier(context: Context?, name: String?): Int {
        return if (name == null || context == null) {
            0
        } else {
            context.resources.getIdentifier(
                name,
                "string",
                context.packageName
            )
        }
    }

    @JvmStatic
    fun setOnClick(rootView: View, id: Int, listener: View.OnClickListener?): View {
        val view = rootView.findViewById<View>(id)
        try {
            view.setOnClickListener(listener)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
        return view
    }

    @JvmStatic
    fun setOnClick(
        rootView: View,
        id: Int,
        tag: Any?,
        listener: View.OnClickListener?,
        longListener: OnLongClickListener?
    ): View {
        val view = rootView.findViewById<View>(id)
        try {
            view.tag = tag
            view.setOnClickListener(listener)
            if (longListener != null) {
                view.setOnLongClickListener(longListener)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
        return view
    }

    @JvmStatic
    fun setValue(rootView: View, id: Int, value: String?) {
        try {
            val tv = rootView.findViewById<View>(id) as TextView
            tv.text = value
        } catch (e: Exception) {
            Log.e(TAG, "setValue: $e")
        }
    }

    @JvmStatic
    fun getValue(rootView: View, id: Int): String {
        return (rootView.findViewById<View>(id) as EditText)
            .getText()
            .toString()
    }

    @JvmStatic
    @Throws(ValidationException::class)
    fun getValidValue(rootView: View, id: Int, validator: Validator): String {
        val et = rootView.findViewById<View>(id) as EditText
        val value = et.getText().toString()
        if (!validator.valid(et, value)) {
            et.requestFocus()
            et.error = validator.errorMessage
            throw ValidationException(validator.errorMessage)
        }
        return value
    }

    @JvmStatic
    fun setButtonImage(rootView: View, id: Int, icon: Drawable?) {
        try {
            val btn = rootView.findViewById<View>(id) as ImageButton
            btn.setImageDrawable(icon)
        } catch (e: Exception) {
            Log.e(TAG, "setValue: $e")
        }
    }

    /**
     * Draw a text consisting of a value and a unit, applying a smaller text size to the
     * unit part and separating the unit from the value by some space. The unit size is
     * currently fixed to 70% of the value size. Alignment and value size is taken from
     * paint.
     *
     * The default separator size is 1/6, but depending on the actual text size and unit
     * text, this may not look good and need to be smaller or larger.
     *
     * @param canvas        Canvas to paint on
     * @param paint         Paint template to use
     * @param value         Value string
     * @param unit          Unit string
     * @param x             Position
     * @param y             Position
     * @param sepSizeFactor Relative to the unit text size
     * @return complete width of the rendered text (value + separator + unit)
     */
    @JvmOverloads
    fun drawUnitText(
        canvas: Canvas,
        paint: Paint,
        value: String?,
        unit: String?,
        x: Float,
        y: Float,
        sepSizeFactor: Float = 1 / 6f
    ): Float {
        val unitSizeFactor = 0.7f // Relative unit text size
        val p1 = Paint(paint)
        p1.textAlign = Paint.Align.LEFT
        val p2 = Paint(p1)
        val unitTextSize = p1.textSize * unitSizeFactor
        val sepSize = unitTextSize * sepSizeFactor
        p2.textSize = unitTextSize
        val w1 = p1.measureText(value) + sepSize
        val w2 = p2.measureText(unit)
        val x0: Float = when (paint.textAlign) {
            Paint.Align.LEFT -> x
            Paint.Align.RIGHT -> x - (w1 + w2)
            else -> {
                x - (w1 + w2) / 2 + sepSize
                // Due to the smaller text size for the unit, a mathematically centered
                // placement appears to be unbalanced. Shifting x0 to the right by the sepSize
                // works as a rebalancement (may need refinement for very small/large sizes).
            }
        }
        canvas.drawText(value!!, x0, y, p1)
        canvas.drawText(unit!!, x0 + w1, y, p2)
        return w1 + w2
    }

    /*
     * Inner types
     */

    interface OnChangeListener<T> {
        fun onAction(data: T)
    }
}
