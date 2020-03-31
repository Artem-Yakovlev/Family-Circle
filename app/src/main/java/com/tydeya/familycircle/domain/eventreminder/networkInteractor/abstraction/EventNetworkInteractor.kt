package com.tydeya.familycircle.domain.eventreminder.networkInteractor.abstraction

import com.tydeya.familycircle.data.eventreminder.FamilyEvent

interface EventNetworkInteractor {

    fun requireEventDataFromServer()

    fun createEvent(familyEvent: FamilyEvent)

    fun editEvent(familyEvent: FamilyEvent)

    fun deleteEvent(familyEventId: String)
}