package com.tydeya.familycircle.domain.eventreminder.interactor.details

import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.domain.eventreminder.interactor.abstraction.EventInteractorCallback
import com.tydeya.familycircle.domain.eventreminder.interactor.abstraction.EventInteractorObservable
import com.tydeya.familycircle.domain.eventreminder.networkInteractor.abstraction.EventNetworkInteractor
import com.tydeya.familycircle.domain.eventreminder.networkInteractor.abstraction.EventNetworkInteractorCallback
import com.tydeya.familycircle.domain.eventreminder.networkInteractor.details.EventNetworkInteractorImpl

class EventInteractor : EventNetworkInteractorCallback, EventInteractorObservable {

    val familySingleEvents: ArrayList<FamilyEvent> = ArrayList()

    val familyAnnualEvents: ArrayList<FamilyEvent> = ArrayList()

    private val networkInteractor: EventNetworkInteractor = EventNetworkInteractorImpl(this)

    private val observers: ArrayList<EventInteractorCallback> = ArrayList()

    init {
        networkInteractor.requireEventDataFromServer()
    }

    override fun eventDataUpdate(familySingleEvents: ArrayList<FamilyEvent>,
                                 familyAnnualEvents: ArrayList<FamilyEvent>) {
        this.familySingleEvents.clear()
        this.familySingleEvents.addAll(familySingleEvents)

        this.familyAnnualEvents.clear()
        this.familyAnnualEvents.addAll(familyAnnualEvents)

        notifyObserversEventsDataUpdated()
    }

    /**
     * Utils
     * */

    fun getEventById(id: String): FamilyEvent? {
        return searchEventById(id, familySingleEvents) ?: searchEventById(id, familyAnnualEvents)
    }

    private fun searchEventById(id: String, events: ArrayList<FamilyEvent>): FamilyEvent? {
        for (event in events) {
            if (event.id == id) {
                return event
            }
        }
        return null
    }

    /**
     * Callbacks
     * */

    private fun notifyObserversEventsDataUpdated() {
        for (callback in observers) {
            callback.eventDataFromServerUpdated()
        }
    }

    override fun subscribe(eventInteractorCallback: EventInteractorCallback) {
        if (!observers.contains(eventInteractorCallback)) {
            observers.add(eventInteractorCallback)
            eventInteractorCallback.eventDataFromServerUpdated()
        }
    }

    override fun unsubscribe(eventInteractorCallback: EventInteractorCallback) {
        observers.remove(eventInteractorCallback)
    }

}