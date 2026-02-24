package com.openvehicles.OVMS.ui.validators

import android.widget.EditText
import com.openvehicles.OVMS.R
import java.util.regex.Pattern

class UserNameValidator : StringValidator() {

    override fun valid(editText: EditText?, value: Any?): Boolean {
        if (!super.valid(editText, value)) {
            return false
        }
        errorMessage = editText!!.context.getString(R.string.msg_invalid_name)
        val name = value as String?
        if (name!!.length < 3) {
            return false
        }
        val pattern = Pattern.compile(NAME_PATERN)
        val matcher = pattern.matcher(name)
        return matcher.matches()
    }

    /*
     * Inner types
     */

    private companion object {
        private const val NAME_PATERN = "(\\w+\\s*)+"
    }
}
