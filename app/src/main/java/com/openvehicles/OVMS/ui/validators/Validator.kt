package com.openvehicles.OVMS.ui.validators

import android.widget.EditText

interface Validator {

    var errorMessage: String?

    fun valid(editText: EditText?, value: Any?): Boolean

}
