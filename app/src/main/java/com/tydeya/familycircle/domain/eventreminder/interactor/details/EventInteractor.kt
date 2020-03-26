package com.tydeya.familycircle.domain.eventreminder.interactor.details

import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.domain.eventreminder.networkInteractor.abstraction.EventNetworkInteractor
import com.tydeya.familycircle.domain.eventreminder.networkInteractor.abstraction.EventNetworkInteractorCallback
import com.tydeya.familycircle.domain.eventreminder.networkInteractor.details.EventNetworkInteractorImpl

class EventInteractor: EventNetworkInteractorCallback {

    private val familyEvents: ArrayList<FamilyEvent> = ArrayList()
    private val eventNetworkInteractor: EventNetworkInteractor = EventNetworkInteractorImpl()

    init {

    }

    override fun eventDataUpdate(familyEvent: ArrayList<FamilyEvent>) {
        this.familyEvents.clear()
        this.familyEvents.addAll(familyEvent)
    }

}