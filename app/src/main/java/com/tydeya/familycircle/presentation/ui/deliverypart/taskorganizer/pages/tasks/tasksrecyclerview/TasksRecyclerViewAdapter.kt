package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.tasksrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.FamilyTaskDto
import com.tydeya.familycircle.data.taskorganizer.TaskStatus
import com.tydeya.familycircle.databinding.RecyclerItemTaskBinding
import com.tydeya.familycircle.utils.extensions.toArrayList
import com.tydeya.familycircle.utils.extensions.toFamilyTasksDTO

class TasksRecyclerViewAdapter(
        private val mainTaskStatus: TaskStatus,
        private val familyTasksDTO: ArrayList<FamilyTaskDto> = ArrayList(),
        private var listener: TasksRecyclerViewClickListener)
    :
        RecyclerView.Adapter<TasksForUserViewHolder>() {

    private val familyMembers: ArrayList<FamilyMember> = ArrayList()
    private val familyTasks: ArrayList<FamilyTask> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksForUserViewHolder =
            TasksForUserViewHolder(RecyclerItemTaskBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false),
                    listener,
                    mainTaskStatus
            )

    override fun onBindViewHolder(holder: TasksForUserViewHolder, position: Int) {
        holder.bindData(familyTasksDTO[position])
    }

    fun refreshTasks(familyTasks: ArrayList<FamilyTask>) {
        this.familyTasks.clear()
        this.familyTasks.addAll(familyTasks)
        refreshData()
    }

    fun refreshFamilyMembers(familyMembers: ArrayList<FamilyMember>) {
        this.familyMembers.clear()
        this.familyMembers.addAll(familyMembers)
        refreshData()
    }

    private fun refreshData() {
        val actualTasksDTO = familyTasks.toFamilyTasksDTO(this.familyMembers).toArrayList()

        val diffResult = DiffUtil.calculateDiff(TasksDtoRecyclerDiffUtil(
                this.familyTasksDTO, actualTasksDTO
        ))

        this.familyTasksDTO.clear()
        this.familyTasksDTO.addAll(actualTasksDTO)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = familyTasks.size
}
