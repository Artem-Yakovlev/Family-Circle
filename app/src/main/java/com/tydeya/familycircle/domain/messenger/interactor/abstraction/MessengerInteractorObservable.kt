package com.tydeya.familycircle.domain.messenger.interactor.abstraction

interface MessengerInteractorObservable {

    fun subscribe(callback: MessengerInteractorCallback)

    fun unsubscribe(callback: MessengerInteractorCallback)

}