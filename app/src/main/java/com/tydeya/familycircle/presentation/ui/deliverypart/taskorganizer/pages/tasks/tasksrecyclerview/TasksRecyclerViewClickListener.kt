package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.tasksrecyclerview

import com.tydeya.familycircle.data.taskorganizer.FamilyTask

interface TasksRecyclerViewClickListener {

    fun taskIsChecked(familyTask: FamilyTask) {}

    fun editTask(familyTask: FamilyTask) {}

}
