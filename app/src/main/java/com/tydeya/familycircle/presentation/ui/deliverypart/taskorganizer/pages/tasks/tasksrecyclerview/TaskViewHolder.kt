package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.tasksrecyclerview

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.taskorganizer.FamilyTaskDto
import com.tydeya.familycircle.data.taskorganizer.TaskStatus
import com.tydeya.familycircle.databinding.RecyclerItemTaskBinding

class TaskViewHolder(
        private val binding: RecyclerItemTaskBinding,
        private val listener: TasksRecyclerViewClickListener,
        private val mainTaskStatus: TaskStatus
) :
        RecyclerView.ViewHolder(binding.root) {

    fun bindData(familyTaskDto: FamilyTaskDto) {
        binding.taskTitle.text = familyTaskDto.familyTask.title
        binding.taskText.text = familyTaskDto.familyTask.text
        binding.taskCheckbox.isChecked = familyTaskDto.familyTask.status == TaskStatus.COMPLETED

        if (mainTaskStatus != TaskStatus.COMPLETED) {
            binding.taskCheckbox.setOnCheckedChangeListener { _, _ ->
                listener.taskIsChecked(familyTaskDto.familyTask)
            }
            binding.taskEditButton.setOnClickListener {
                listener.editTask(familyTaskDto.familyTask)
            }
            binding.taskEditButton.visibility = View.VISIBLE
        } else {
            binding.taskCheckbox.isEnabled = false
            binding.taskCheckbox.isChecked = true
            binding.taskEditButton.visibility = View.INVISIBLE
        }

        binding.taskWorkersRecyclerView.adapter = WorkersRecyclerViewAdapter(
                familyTaskDto.workersNames
        )
        binding.taskWorkersRecyclerView.layoutManager = LinearLayoutManager(
                itemView.context, LinearLayoutManager.HORIZONTAL, false
        )
    }

}