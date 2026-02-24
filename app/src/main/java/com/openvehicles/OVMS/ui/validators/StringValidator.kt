package com.openvehicles.OVMS.ui.validators

import android.text.TextUtils
import android.widget.EditText
import com.openvehicles.OVMS.R

open class StringValidator(
    override var errorMessage: String? = null
) : Validator {

    override fun valid(editText: EditText?, value: Any?): Boolean {
        if (errorMessage == null) {
            errorMessage = editText!!.context.getString(R.string.msg_please_enter_value)
        }
        return !TextUtils.isEmpty(value as String?)
    }
}
