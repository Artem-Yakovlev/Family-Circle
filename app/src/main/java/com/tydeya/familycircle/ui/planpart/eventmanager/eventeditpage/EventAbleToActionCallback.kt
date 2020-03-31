package com.tydeya.familycircle.ui.planpart.eventmanager.eventeditpage

interface EventAbleToActionCallback {

    fun ableToPerformAction(title: String, timestamp: Long)

    fun notAbleToPerformAction(title: String, timestamp: Long)
}