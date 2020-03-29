package com.tydeya.familycircle.domain.eventreminder.interactor.abstraction

interface EventInteractorObservable {

    fun subscribe(eventInteractorCallback: EventInteractorCallback)

    fun unsubscribe(eventInteractorCallback: EventInteractorCallback)

}