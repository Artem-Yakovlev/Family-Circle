package com.tydeya.familycircle.utils.extensions

import java.util.*

fun GregorianCalendar.setDateAndGet(date: Date) = apply {
    time = date
}