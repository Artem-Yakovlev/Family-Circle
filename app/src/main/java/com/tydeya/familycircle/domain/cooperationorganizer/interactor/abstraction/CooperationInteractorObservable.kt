package com.tydeya.familycircle.domain.cooperationorganizer.interactor.abstraction

interface CooperationInteractorObservable {

    fun subscribe(callback: CooperationInteractorCallback)

    fun unsubscribe(callback: CooperationInteractorCallback)

}