package com.tydeya.familycircle.domain.tasks.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.tydeya.familycircle.data.constants.FireStore.TASKS_AUTHOR
import com.tydeya.familycircle.data.constants.FireStore.TASKS_STATUS
import com.tydeya.familycircle.data.constants.FireStore.TASKS_TEXT
import com.tydeya.familycircle.data.constants.FireStore.TASKS_TITLE
import com.tydeya.familycircle.data.constants.FireStore.TASKS_WORKER
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.TaskStatus
import com.tydeya.familycircle.domain.familyselection.getListByTag

fun convertServerDataToFamilyTask(document: DocumentSnapshot) = FamilyTask(
        id = document.id,
        author = document.getString(TASKS_AUTHOR) ?: "",
        workers = document.getListByTag(TASKS_WORKER),
        title = document.getString(TASKS_TITLE) ?: "",
        text = document.getString(TASKS_TEXT) ?: "",
        status = TaskStatus.values()[(document.getLong(TASKS_STATUS) ?: 0).toInt()]
)