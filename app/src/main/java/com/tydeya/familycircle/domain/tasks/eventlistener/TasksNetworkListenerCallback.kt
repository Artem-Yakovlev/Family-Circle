package com.tydeya.familycircle.domain.tasks.eventlistener

import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.utils.Resource

interface TasksNetworkListenerCallback {

    fun tasksDataUpdated(pendingTasks: Resource<ArrayList<FamilyTask>>,
                         completedTasks: Resource<ArrayList<FamilyTask>> = pendingTasks
    )
}
