package com.tydeya.familycircle.domain.eventreminder.interactor.details

import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.domain.eventreminder.interactor.abstraction.EventInteractorCallback
import com.tydeya.familycircle.domain.eventreminder.interactor.abstraction.EventInteractorObservable
import com.tydeya.familycircle.domain.eventreminder.networkInteractor.abstraction.EventNetworkInteractor
import com.tydeya.familycircle.domain.eventreminder.networkInteractor.abstraction.EventNetworkInteractorCallback
import com.tydeya.familycircle.domain.eventreminder.networkInteractor.details.EventNetworkInteractorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        GlobalScope.launch(Dispatchers.Default) {
            this@EventInteractor.familySingleEvents.clear()
            this@EventInteractor.familySingleEvents.addAll(familySingleEvents)

            this@EventInteractor.familyAnnualEvents.clear()
            this@EventInteractor.familyAnnualEvents.addAll(familyAnnualEvents)

            withContext(Dispatchers.Main) {
                notifyObserversEventsDataUpdated()
            }
        }
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