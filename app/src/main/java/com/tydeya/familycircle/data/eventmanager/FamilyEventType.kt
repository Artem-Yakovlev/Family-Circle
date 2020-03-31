package com.tydeya.familycircle.data.eventmanager

enum class FamilyEventType {
    ANNUAL_EVENT, IMPORTANT_EVENT, ROUTINE;

    companion object {
        fun fromInt(number: Int) = when (number) {
            0 -> ANNUAL_EVENT
            1 -> IMPORTANT_EVENT
            else -> ROUTINE
        }
    }
}