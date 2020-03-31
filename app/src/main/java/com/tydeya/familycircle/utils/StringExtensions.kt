package com.tydeya.familycircle.utils

fun String?.ifNullToEmpty(): String {
    return this ?: ""
}