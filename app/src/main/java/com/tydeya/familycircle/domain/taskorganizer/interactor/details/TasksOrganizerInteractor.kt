package com.tydeya.familycircle.domain.taskorganizer.interactor.details

import com.tydeya.familycircle.domain.taskorganizer.interactor.abstraction.TasksOrganizerInteractorCallback
import com.tydeya.familycircle.domain.taskorganizer.interactor.abstraction.TasksOrganizerInteractorObservable
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractorCallback
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.details.TasksOrganizerNetworkInteractorImpl

class TasksOrganizerInteractor : TasksOrganizerNetworkInteractorCallback, TasksOrganizerInteractorObservable {

    private val observers: ArrayList<TasksOrganizerInteractorCallback> = ArrayList()

    private val networkInteractor: TasksOrganizerNetworkInteractor =
            TasksOrganizerNetworkInteractorImpl(this)

    init {

    }

    /**
     * Callbacks
     * */

    private fun notifyObserversKitchenDataUpdated() {
        for (callback in observers) {
            callback.tasksDataFromServerUpdate()
        }
    }

    override fun subscribe(callback: TasksOrganizerInteractorCallback) {
        if (!observers.contains(callback)) {
            observers.add(callback)
            callback.tasksDataFromServerUpdate()
        }
    }

    override fun unsubscribe(callback: TasksOrganizerInteractorCallback) {
        observers.remove(callback)
    }


}