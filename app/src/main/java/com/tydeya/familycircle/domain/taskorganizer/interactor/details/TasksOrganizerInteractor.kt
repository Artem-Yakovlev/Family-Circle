package com.tydeya.familycircle.domain.taskorganizer.interactor.details

import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.FamilyTaskStatus
import com.tydeya.familycircle.domain.taskorganizer.interactor.abstraction.TasksOrganizerInteractorCallback
import com.tydeya.familycircle.domain.taskorganizer.interactor.abstraction.TasksOrganizerInteractorObservable
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractorCallback
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.details.TasksOrganizerNetworkInteractorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TasksOrganizerInteractor : TasksOrganizerNetworkInteractorCallback, TasksOrganizerInteractorObservable {

    var tasksForUser: ArrayList<FamilyTask> = ArrayList()
    private var historyTasksForUser: ArrayList<FamilyTask> = ArrayList()

    var tasksByUser: ArrayList<FamilyTask> = ArrayList()
    private var historyTasksByUser: ArrayList<FamilyTask> = ArrayList()

    val sortedHistoryTasks: ArrayList<FamilyTask>
        get() {
            val sortedHistoryTasks = ArrayList<FamilyTask>()
            sortedHistoryTasks.addAll(historyTasksForUser)
            sortedHistoryTasks.addAll(historyTasksByUser)
            sortedHistoryTasks.sortWith(kotlin.Comparator { o1, o2 -> -compareValues(o1.time, o2.time) })
            return sortedHistoryTasks
        }

    private val observers: ArrayList<TasksOrganizerInteractorCallback> = ArrayList()
    private val networkInteractor: TasksOrganizerNetworkInteractor =
            TasksOrganizerNetworkInteractorImpl(this)

    init {
        networkInteractor.requireTasksData()
    }

    /**
     * Data reception
     * */

    override suspend fun tasksForUserDataFromServerUpdate(tasksForUser: ArrayList<FamilyTask>) {

        convertToTasksContainers(tasksForUser, false)

        withContext(Dispatchers.Main) {
            notifyObserversKitchenDataUpdated()
        }
    }

    override suspend fun tasksByUserDataFromServerUpdate(tasksByUser: ArrayList<FamilyTask>) {
        convertToTasksContainers(tasksByUser, true)

        withContext(Dispatchers.Main) {
            notifyObserversKitchenDataUpdated()
        }

    }

    private fun convertToTasksContainers(rawTasks: ArrayList<FamilyTask>, byUser: Boolean) {
        val awaitingTasks = ArrayList<FamilyTask>()
        val historyTasks = ArrayList<FamilyTask>()

        rawTasks.forEach {
            if (it.status == FamilyTaskStatus.AWAITING_COMPLETION) {
                awaitingTasks.add(it)
            } else {
                historyTasks.add(it)
            }
        }

        if (byUser) {
            this.tasksByUser = awaitingTasks
            this.historyTasksByUser = historyTasks
        } else {
            this.tasksForUser = awaitingTasks
            this.historyTasksForUser = historyTasks
        }
    }

    /**
     * Data editing
     * */

    fun performTask(familyTask: FamilyTask) {
        networkInteractor.setTaskStatus(familyTask.id, FamilyTaskStatus.ACCEPTED)
        familyTask.status = FamilyTaskStatus.ACCEPTED
        historyTasksForUser.add(familyTask)
        tasksForUser.remove(familyTask)
        notifyObserversKitchenDataUpdated()
    }

    fun refuseTask(familyTask: FamilyTask) {
        networkInteractor.setTaskStatus(familyTask.id, FamilyTaskStatus.REJECTED)
        familyTask.status = FamilyTaskStatus.REJECTED
        historyTasksForUser.add(familyTask)
        tasksForUser.remove(familyTask)
        notifyObserversKitchenDataUpdated()
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