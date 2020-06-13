package com.tydeya.familycircle.domain.tasks

import com.tydeya.familycircle.data.constants.FireStore.TASKS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.TASKS_STATUS
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.TaskStatus
import com.tydeya.familycircle.domain.tasks.utils.toFirestoreData
import com.tydeya.familycircle.utils.extensions.firestoreFamily

fun createTaskInFirestore(familyId: String, familyTask: FamilyTask) {
    firestoreFamily(familyId).collection(TASKS_COLLECTION)
            .add(familyTask.toFirestoreData())
}

fun completeTaskInFirestore(familyId: String, familyTask: FamilyTask) {
    firestoreFamily(familyId).collection(TASKS_COLLECTION).document(familyTask.id)
            .update(mutableMapOf<String, Any>(TASKS_STATUS to TaskStatus.COMPLETED.ordinal))
}