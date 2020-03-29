package com.tydeya.familycircle.domain.taskorganizer.networkinteractor.details

import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractorCallback

class TasksOrganizerNetworkInteractorImpl(val callback: TasksOrganizerNetworkInteractorCallback)
    :
        TasksOrganizerNetworkInteractor {

    override fun requireTasksData() {

    }
}