package com.openvehicles.OVMS.ui.validators

import android.widget.EditText
import com.openvehicles.OVMS.R

class PasswdValidator(
    private val minLength: Int,
    private val maxLength: Int
) : StringValidator() {

    override fun valid(editText: EditText?, value: Any?): Boolean {
        if (!super.valid(editText, value)) {
            return false
        }
        errorMessage =
            editText!!.context.getString(R.string.msg_invalid_passwd, minLength, maxLength)
        val name = value as String?
        return name!!.length >= minLength && name.length <= maxLength
    }
}
