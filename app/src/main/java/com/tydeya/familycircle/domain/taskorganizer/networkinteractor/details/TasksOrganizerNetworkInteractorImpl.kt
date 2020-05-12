package com.tydeya.familycircle.domain.taskorganizer.networkinteractor.details

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.tydeya.familycircle.App
import com.tydeya.familycircle.data.constants.FireStore.*
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.FamilyTaskStatus
import com.tydeya.familycircle.domain.onlinemanager.details.OnlineInteractorImpl
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractorCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class TasksOrganizerNetworkInteractorImpl(val callback: TasksOrganizerNetworkInteractorCallback)
    :
        TasksOrganizerNetworkInteractor {

    /**
     * Data listeners
     * */

    @Inject
    lateinit var onlineManager: OnlineInteractorImpl

    override fun requireTasksData() {
        App.getComponent().injectInteractor(this)
        requireTasksForUser()
        requireTasksByUser()
    }

    private fun requireTasksForUser() {
        FirebaseFirestore.getInstance().collection(FIRESTORE_TASKS_COLLECTION)
                .whereEqualTo(FIRESTORE_TASKS_WORKER,
                        FirebaseAuth.getInstance().currentUser!!.phoneNumber)
                .addSnapshotListener { querySnapshot, _ ->
                    onlineManager.registerUserActivity()
                    GlobalScope.launch(Dispatchers.Default) {
                        querySnapshot?.let {
                            val tasksForUser = ArrayList<FamilyTask>()
                            for (document in querySnapshot) {
                                tasksForUser.add(convertServerDataToFamilyTask(document))
                            }
                            callback.tasksForUserDataFromServerUpdate(tasksForUser)
                        }
                    }
                }
    }

    private fun requireTasksByUser() {
        FirebaseFirestore.getInstance().collection(FIRESTORE_TASKS_COLLECTION)
                .whereEqualTo(FIRESTORE_TASKS_AUTHOR,
                        FirebaseAuth.getInstance().currentUser!!.phoneNumber)
                .addSnapshotListener { querySnapshot, _ ->
                    GlobalScope.launch(Dispatchers.Default) {
                        querySnapshot?.let {
                            val tasksByUser = ArrayList<FamilyTask>()
                            for (document in querySnapshot) {
                                tasksByUser.add(convertServerDataToFamilyTask(document))
                            }
                            callback.tasksByUserDataFromServerUpdate(tasksByUser)
                        }
                    }
                }
    }

    private fun convertServerDataToFamilyTask(document: QueryDocumentSnapshot) = FamilyTask(
            document.id,
            document.getString(FIRESTORE_TASKS_AUTHOR) ?: "+0",
            document.getString(FIRESTORE_TASKS_WORKER) ?: "+0",
            document.getString(FIRESTORE_TASKS_TEXT) ?: "",
            document.getDate(FIRESTORE_TASKS_TIME)?.time ?: 0,
            when (document.getLong(FIRESTORE_TASKS_STATUS)) {
                0L -> FamilyTaskStatus.REJECTED
                1L -> FamilyTaskStatus.AWAITING_COMPLETION
                else -> FamilyTaskStatus.ACCEPTED
            }
    )

    /**
     * Data editing
     * */

    override fun setTaskStatus(taskId: String, familyTaskStatus: FamilyTaskStatus) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(FIRESTORE_TASKS_COLLECTION)
                    .document(taskId)
                    .update(mapOf(FIRESTORE_TASKS_STATUS to familyTaskStatus.ordinal))
        }
    }

    override fun deleteTask(taskId: String) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(FIRESTORE_TASKS_COLLECTION)
                    .document(taskId).delete()
        }
    }

    override fun editTaskText(taskId: String, actualText: String) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(FIRESTORE_TASKS_COLLECTION)
                    .document(taskId)
                    .update(mapOf(FIRESTORE_TASKS_TEXT to actualText))
        }
    }

    override fun createTask(familyTask: FamilyTask) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(FIRESTORE_TASKS_COLLECTION).add(hashMapOf(
                    FIRESTORE_TASKS_AUTHOR to familyTask.author,
                    FIRESTORE_TASKS_WORKER to familyTask.worker,
                    FIRESTORE_TASKS_STATUS to 1,
                    FIRESTORE_TASKS_TEXT to familyTask.text,
                    FIRESTORE_TASKS_TIME to Date(familyTask.time)
            ) as Map<String, Any>)
        }
    }
}