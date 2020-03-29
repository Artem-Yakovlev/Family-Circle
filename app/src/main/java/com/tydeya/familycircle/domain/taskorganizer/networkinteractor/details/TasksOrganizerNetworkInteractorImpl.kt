package com.tydeya.familycircle.domain.taskorganizer.networkinteractor.details

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.FamilyTaskStatus
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.abstraction.TasksOrganizerNetworkInteractorCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TasksOrganizerNetworkInteractorImpl(val callback: TasksOrganizerNetworkInteractorCallback)
    :
        TasksOrganizerNetworkInteractor {

    override fun requireTasksData() {
        requireTasksForUser()
        requireTasksByUser()
    }

    private fun requireTasksForUser() {
        FirebaseFirestore.getInstance().collection(FIRESTORE_TASKS_COLLECTION)
                .whereEqualTo(FIRESTORE_TASKS_WORKER,
                        FirebaseAuth.getInstance().currentUser!!.phoneNumber)
                .addSnapshotListener { querySnapshot, _ ->
                    GlobalScope.launch(Dispatchers.Default) {
                        val tasksForUser = ArrayList<FamilyTask>()
                        for (document in querySnapshot) {
                            tasksForUser.add(convertServerDataToFamilyTask(document))
                        }
                        callback.tasksForUserDataFromServerUpdate(tasksForUser)
                    }
                }
    }

    private fun requireTasksByUser() {
        FirebaseFirestore.getInstance().collection(FIRESTORE_TASKS_COLLECTION)
                .whereEqualTo(FIRESTORE_TASKS_AUTHOR,
                        FirebaseAuth.getInstance().currentUser!!.phoneNumber)
                .addSnapshotListener { querySnapshot, _ ->
                    GlobalScope.launch(Dispatchers.Default) {
                        val tasksByUser = ArrayList<FamilyTask>()
                        for (document in querySnapshot) {
                            tasksByUser.add(convertServerDataToFamilyTask(document))
                        }
                        callback.tasksByUserDataFromServerUpdate(tasksByUser)
                    }
                }
    }

    private fun convertServerDataToFamilyTask(document: QueryDocumentSnapshot) = FamilyTask(
            document.id,
            document.getString(FIRESTORE_TASKS_AUTHOR),
            document.getString(FIRESTORE_TASKS_WORKER),
            document.getString(FIRESTORE_TASKS_TEXT),
            document.getDate(FIRESTORE_TASKS_TIME).time,
            when(document.getLong(FIRESTORE_TASKS_STATUS)) {
                0L -> FamilyTaskStatus.REJECTED
                1L -> FamilyTaskStatus.AWAITING_COMPLETION
                else -> FamilyTaskStatus.ACCEPTED
            }
    )
}