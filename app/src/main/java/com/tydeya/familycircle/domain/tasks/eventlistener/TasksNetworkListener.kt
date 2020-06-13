package com.tydeya.familycircle.domain.tasks.eventlistener

import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.tydeya.familycircle.data.constants.FireStore.TASKS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.TASKS_STATUS
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.TaskStatus
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.domain.tasks.utils.convertServerDataToFamilyTask
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.firestoreFamily
import com.tydeya.familycircle.utils.extensions.toArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksNetworkListener(
        private val familyId: String,
        private val callback: TasksNetworkListenerCallback
) :
        EventListenerObservable, EventListener<QuerySnapshot> {

    private val tasksRef = firestoreFamily(familyId)
            .collection(TASKS_COLLECTION)
            .whereEqualTo(TASKS_STATUS, TaskStatus.PENDING.ordinal)

    private lateinit var tasksRegistration: ListenerRegistration

    override fun register() {
        tasksRegistration = tasksRef.addSnapshotListener(this)
    }

    override fun unregister() {
        tasksRegistration.remove()
    }

    override fun onEvent(querySnapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
        if (exception == null) {
            querySnapshot?.let {
                GlobalScope.launch(Dispatchers.Default) {
                    val tasks = it.documents
                            .map(::convertServerDataToFamilyTask)
                            .sortedBy(FamilyTask::text)

                    val pendingTasks = Resource.Success(
                            tasks.filter { it.status == TaskStatus.PENDING }
                                    .toArrayList()
                    )

                    val completedTasks = Resource.Success(
                            tasks.filter { it.status == TaskStatus.COMPLETED }
                                    .toArrayList()
                    )

                    withContext(Dispatchers.Main) {
                        callback.tasksDataUpdated(
                                pendingTasks = pendingTasks,
                                completedTasks = completedTasks
                        )
                    }

                }

            }
        } else {
            callback.tasksDataUpdated(pendingTasks = Resource.Failure(exception))
        }
    }

}

