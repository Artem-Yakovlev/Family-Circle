package com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction

import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.FamilyTaskStatus

interface TasksOrganizerNetworkInteractor {

    fun requireTasksData()

    fun setTaskStatus(taskId: String, familyTaskStatus: FamilyTaskStatus)

    fun deleteTask(taskId: String)

    fun editTaskText(taskId: String, actualText: String)

    fun createTask(familyTask: FamilyTask)
}