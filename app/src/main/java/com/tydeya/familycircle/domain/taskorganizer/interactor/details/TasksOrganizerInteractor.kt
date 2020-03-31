package com.tydeya.familycircle.domain.taskorganizer.interactor.details

import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.App
import com.tydeya.familycircle.data.cooperation.Cooperation
import com.tydeya.familycircle.data.cooperation.CooperationType
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.FamilyTaskStatus
import com.tydeya.familycircle.domain.cooperationorganizer.interactor.details.CooperationInteractor
import com.tydeya.familycircle.domain.taskorganizer.interactor.abstraction.TasksOrganizerInteractorCallback
import com.tydeya.familycircle.domain.taskorganizer.interactor.abstraction.TasksOrganizerInteractorObservable
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractorCallback
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.details.TasksOrganizerNetworkInteractorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

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

    @Inject
    lateinit var cooperationInteractor: CooperationInteractor

    init {
        App.getComponent().injectInteractor(this)
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

        cooperationInteractor.registerCooperation(
                Cooperation("", FirebaseAuth.getInstance().currentUser!!.phoneNumber!!,
                        familyTask.author, CooperationType.PERFORM_TASK, Date()))

        notifyObserversKitchenDataUpdated()
    }

    fun refuseTask(familyTask: FamilyTask) {
        networkInteractor.setTaskStatus(familyTask.id, FamilyTaskStatus.REJECTED)
        familyTask.status = FamilyTaskStatus.REJECTED
        historyTasksForUser.add(familyTask)
        tasksForUser.remove(familyTask)

        cooperationInteractor.registerCooperation(
                Cooperation("", FirebaseAuth.getInstance().currentUser!!.phoneNumber!!,
                        familyTask.author, CooperationType.REFUSE_TASK, Date()))

        notifyObserversKitchenDataUpdated()
    }

    fun deleteTask(familyTask: FamilyTask) {
        networkInteractor.deleteTask(familyTask.id)
        tasksByUser.remove(familyTask)
        notifyObserversKitchenDataUpdated()
    }

    fun editTaskText(familyTask: FamilyTask, actualText: String) {
        networkInteractor.editTaskText(familyTask.id, actualText)
        familyTask.text = actualText
        notifyObserversKitchenDataUpdated()
    }

    fun createTask(familyTask: FamilyTask) {
        networkInteractor.createTask(familyTask)
        tasksByUser.add(familyTask)

        cooperationInteractor.registerCooperation(
                Cooperation("", FirebaseAuth.getInstance().currentUser!!.phoneNumber!!,
                        familyTask.worker, CooperationType.GIVE_TASK, Date()))

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