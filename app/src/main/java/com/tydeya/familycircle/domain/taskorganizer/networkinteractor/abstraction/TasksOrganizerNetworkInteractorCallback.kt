package com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction

import com.tydeya.familycircle.data.taskorganizer.FamilyTask

interface TasksOrganizerNetworkInteractorCallback {

    suspend fun tasksForUserDataFromServerUpdate(tasksForUser: ArrayList<FamilyTask>)

    suspend fun tasksByUserDataFromServerUpdate(tasksByUser: ArrayList<FamilyTask>)


}