package com.tydeya.familycircle.utils

import android.content.Context

fun getDp(context: Context, number: Int): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (number * scale + 0.5f).toInt()
}