package com.tydeya.familycircle.utils.extensions

import android.content.Context
import android.widget.Toast

fun Context.showToast(stringId: Int) {
    Toast.makeText(this, resources.getString(stringId), Toast.LENGTH_LONG).show()
}