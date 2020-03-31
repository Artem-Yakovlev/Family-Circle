package com.tydeya.familycircle.data.eventmanager

enum class FamilyEventPriority {
    LOW, MIDDLE, HIGH;

    companion object {
        fun fromInt(number: Int) = when (number) {
            0 -> LOW
            1 -> MIDDLE
            else -> HIGH
        }
    }
}