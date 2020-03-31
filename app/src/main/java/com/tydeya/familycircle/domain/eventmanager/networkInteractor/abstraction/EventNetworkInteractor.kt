package com.tydeya.familycircle.domain.eventmanager.networkInteractor.abstraction

import com.tydeya.familycircle.data.eventmanager.FamilyEvent

interface EventNetworkInteractor {

    fun requireEventDataFromServer()

    fun createEvent(familyEvent: FamilyEvent)

    fun editEvent(familyEvent: FamilyEvent)

    fun deleteEvent(familyEventId: String)
}