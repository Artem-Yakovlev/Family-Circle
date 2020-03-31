package com.tydeya.familycircle.data.eventmanager

enum class WorkingMode {

    EDIT, CREATE;

    companion object {
        fun getModeFromInt(number: Int) = when (number) {
            0 -> EDIT
            else -> CREATE
        }
    }

}