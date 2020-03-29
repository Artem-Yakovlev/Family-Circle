package com.tydeya.familycircle.ui.planpart.taskorganizer.pages.tasksbyuser.recyclerview

import com.tydeya.familycircle.data.taskorganizer.FamilyTask

interface TasksByUserRecyclerViewOnClickListener {

    fun editEvent(familyTask: FamilyTask)

    fun deleteEvent(familyTask: FamilyTask)

}