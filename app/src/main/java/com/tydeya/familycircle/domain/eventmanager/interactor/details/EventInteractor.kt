package com.tydeya.familycircle.domain.eventmanager.interactor.details

import com.tydeya.familycircle.data.eventmanager.FamilyEvent
import com.tydeya.familycircle.data.eventmanager.FamilyEventType
import com.tydeya.familycircle.domain.eventmanager.interactor.abstraction.EventInteractorCallback
import com.tydeya.familycircle.domain.eventmanager.interactor.abstraction.EventInteractorObservable
import com.tydeya.familycircle.domain.eventmanager.networkInteractor.abstraction.EventNetworkInteractor
import com.tydeya.familycircle.domain.eventmanager.networkInteractor.abstraction.EventNetworkInteractorCallback
import com.tydeya.familycircle.domain.eventmanager.networkInteractor.details.EventNetworkInteractorImpl
import com.tydeya.familycircle.ui.planpart.eventmanager.eventeditpage.EventAbleToActionCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

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

    fun checkExistEventWithData(title: String, timestamp: Long, exceptId: String,
                                        callback: EventAbleToActionCallback) {
        GlobalScope.launch(Dispatchers.Default) {
            if (isExistEventWithData(title, timestamp, exceptId)) {
                withContext(Dispatchers.Main) {
                    callback.notAbleToPerformAction(title, timestamp)
                }
            } else {
                withContext(Dispatchers.Main) {
                    callback.ableToPerformAction(title, timestamp)
                }
            }
        }

    }

    private fun isExistEventWithData(title: String, timestamp: Long, exceptId: String): Boolean {
        for (event in familySingleEvents) {
            if (event.title == title && event.timestamp == timestamp && event.id != exceptId) {
                return true
            }
        }

        val searchCalendar = GregorianCalendar()
        searchCalendar.timeInMillis = timestamp

        for (event in familyAnnualEvents) {
            val eventCalendar = GregorianCalendar()
            eventCalendar.timeInMillis = event.timestamp
            if (event.title == title
                    && event.id != exceptId
                    && searchCalendar.get(Calendar.DAY_OF_MONTH) == eventCalendar.get(Calendar.DAY_OF_MONTH)
                    && searchCalendar.get(Calendar.MONTH) == eventCalendar.get(Calendar.MONTH)) {
                return true
            }
        }

        return false
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

    /**
     * Perform intents
     * */

    fun createEvent(familyEvent: FamilyEvent) {
        networkInteractor.createEvent(familyEvent)

        if (familyEvent.type == FamilyEventType.ANNUAL_EVENT) {
            familyAnnualEvents.add(familyEvent)
        } else {
            familySingleEvents.add(familyEvent)
        }
    }

    fun editEvent(familyEvent: FamilyEvent) {
        networkInteractor.editEvent(familyEvent)

        searchEventById(familyEvent.id, familySingleEvents)?.let {
            if (familyEvent.type == FamilyEventType.ANNUAL_EVENT) {
                familySingleEvents.remove(it)
                familyAnnualEvents.add(familyEvent)
            } else {
                familySingleEvents[familySingleEvents.indexOf(it)] = familyEvent
            }
            return
        }

        searchEventById(familyEvent.id, familyAnnualEvents)?.let {
            if (familyEvent.type != FamilyEventType.ANNUAL_EVENT) {
                familyAnnualEvents.remove(it)
                familySingleEvents.add(familyEvent)
            } else {
                familyAnnualEvents[familyAnnualEvents.indexOf(it)] = familyEvent
            }
        }
    }

    fun deleteEvent(familyEventId: String) {
        networkInteractor.deleteEvent(familyEventId)
    }



}