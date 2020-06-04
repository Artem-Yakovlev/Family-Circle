package com.tydeya.familycircle.utils.extensions

import android.widget.EditText
import com.tydeya.familycircle.R

var EditText.value
    get() = text.toString()
    set(value) {
        setText(value)
    }

fun EditText.isNotEmptyCheck(attention: Boolean): Boolean {
    if (text.toString().trim() == "") {
        if (attention) {
            error = resources.getString(R.string.empty_necessary_field_warning)
        }
        return false
    }
    return true
}