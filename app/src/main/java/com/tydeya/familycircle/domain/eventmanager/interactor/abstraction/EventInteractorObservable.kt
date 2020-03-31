package com.tydeya.familycircle.domain.eventmanager.interactor.abstraction

interface EventInteractorObservable {

    fun subscribe(eventInteractorCallback: EventInteractorCallback)

    fun unsubscribe(eventInteractorCallback: EventInteractorCallback)

}