package com.tydeya.familycircle.utils.extensions

fun String?.ifNullToEmpty(): String {
    return this ?: ""
}