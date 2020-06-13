package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.tasksrecyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.taskorganizer.FamilyTaskDto
import com.tydeya.familycircle.data.taskorganizer.TaskStatus
import com.tydeya.familycircle.databinding.RecyclerItemTaskBinding

class TasksForUserViewHolder(
        private val binding: RecyclerItemTaskBinding,
        private val listener: TasksRecyclerViewClickListener
) :
        RecyclerView.ViewHolder(binding.root) {

    fun bindData(familyTaskDto: FamilyTaskDto) {
        binding.taskTitle.text = familyTaskDto.familyTask.title
        binding.taskText.text = familyTaskDto.familyTask.text
        binding.taskCheckbox.isChecked = familyTaskDto.familyTask.status == TaskStatus.COMPLETED
        binding.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
            listener.taskIsChecked(isChecked)
        }

        binding.taskWorkersRecyclerView.adapter = WorkersRecyclerViewAdapter(
                familyTaskDto.workersNames
        )
        binding.taskWorkersRecyclerView.layoutManager = LinearLayoutManager(
                itemView.context, LinearLayoutManager.HORIZONTAL, false
        )
    }

}
