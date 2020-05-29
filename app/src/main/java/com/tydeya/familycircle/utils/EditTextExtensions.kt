package com.tydeya.familycircle.utils

import android.widget.EditText

var EditText.value
    get() = text.toString()
    set(value) {
        setText(value)
    }
