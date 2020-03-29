package com.tydeya.familycircle.ui.planpart.taskorganizer.pages.tasksforuser.recyclerview

import com.tydeya.familycircle.data.taskorganizer.FamilyTask

interface TasksForUserRecyclerViewClickListener {

    fun completeTask(familyTask: FamilyTask)

    fun refuseTask(familyTask: FamilyTask)
}