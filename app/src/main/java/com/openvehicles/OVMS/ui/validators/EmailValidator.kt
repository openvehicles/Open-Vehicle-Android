package com.openvehicles.OVMS.ui.validators

import android.widget.EditText
import java.util.regex.Pattern

class EmailValidator : StringValidator() {

    override fun valid(editText: EditText?, value: Any?): Boolean {
        if (!super.valid(editText, value)) {
            return false
        }
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(value as String?)
        return matcher.matches()
    }

    /*
     * Inner types
     */

    private companion object {
        private const val EMAIL_PATTERN =
            "\\b([a-zA-Z0-9%_.+\\-]+)@([a-zA-Z0-9.\\-]+?\\.[a-zA-Z]{2,6})\\b"
    }
}
