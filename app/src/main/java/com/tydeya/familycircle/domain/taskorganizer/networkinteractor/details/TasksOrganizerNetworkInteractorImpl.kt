package com.tydeya.familycircle.domain.taskorganizer.networkinteractor.details

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.tydeya.familycircle.data.constants.FireStore.TASKS_AUTHOR
import com.tydeya.familycircle.data.constants.FireStore.TASKS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.TASKS_STATUS
import com.tydeya.familycircle.data.constants.FireStore.TASKS_TEXT
import com.tydeya.familycircle.data.constants.FireStore.TASKS_TIME
import com.tydeya.familycircle.data.constants.FireStore.TASKS_WORKER
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.FamilyTaskStatus
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractorCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class TasksOrganizerNetworkInteractorImpl(val callback: TasksOrganizerNetworkInteractorCallback)
    :
        TasksOrganizerNetworkInteractor {

    /**
     * Data listeners
     * */

    override fun requireTasksData() {
        requireTasksForUser()
        requireTasksByUser()
    }

    private fun requireTasksForUser() {
        FirebaseFirestore.getInstance().collection(TASKS_COLLECTION)
                .whereEqualTo(TASKS_WORKER,
                        FirebaseAuth.getInstance().currentUser!!.phoneNumber)
                .addSnapshotListener { querySnapshot, _ ->
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
        FirebaseFirestore.getInstance().collection(TASKS_COLLECTION)
                .whereEqualTo(TASKS_AUTHOR,
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
            document.getString(TASKS_AUTHOR) ?: "+0",
            document.getString(TASKS_WORKER) ?: "+0",
            document.getString(TASKS_TEXT) ?: "",
            document.getDate(TASKS_TIME)?.time ?: 0,
            when (document.getLong(TASKS_STATUS)) {
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
            FirebaseFirestore.getInstance().collection(TASKS_COLLECTION)
                    .document(taskId)
                    .update(mapOf(TASKS_STATUS to familyTaskStatus.ordinal))
        }
    }

    override fun deleteTask(taskId: String) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(TASKS_COLLECTION)
                    .document(taskId).delete()
        }
    }

    override fun editTaskText(taskId: String, actualText: String) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(TASKS_COLLECTION)
                    .document(taskId)
                    .update(mapOf(TASKS_TEXT to actualText))
        }
    }

    override fun createTask(familyTask: FamilyTask) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(TASKS_COLLECTION).add(hashMapOf(
                    TASKS_AUTHOR to familyTask.author,
                    TASKS_WORKER to familyTask.worker,
                    TASKS_STATUS to 1,
                    TASKS_TEXT to familyTask.text,
                    TASKS_TIME to Date(familyTask.time)
            ) as Map<String, Any>)
        }
    }
}