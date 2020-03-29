package com.tydeya.familycircle.domain.taskorganizer.interactor.abstraction

interface TasksOrganizerInteractorObservable {

    fun subscribe(callback: TasksOrganizerInteractorCallback)

    fun unsubscribe(callback: TasksOrganizerInteractorCallback)
}