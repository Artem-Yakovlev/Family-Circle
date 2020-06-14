package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.createtaskdialog.dialogtaskworkersrecycler

import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.taskorganizer.TaskMember
import com.tydeya.familycircle.databinding.RecyclerItemCheckboxMemberInDialogBinding

class CreateTaskMemberViewHolder(
        private val binding: RecyclerItemCheckboxMemberInDialogBinding
) :
        RecyclerView.ViewHolder(binding.root) {

    fun bindData(taskMember: TaskMember) {
        binding.checkboxMemberInDialogName.text = taskMember.name
        binding.checkboxMemberInDialogCheckbox.setOnClickListener {
            taskMember.isAdded = binding.checkboxMemberInDialogCheckbox.isChecked
        }
        binding.checkboxMemberInDialogCheckbox.isChecked = taskMember.isAdded
    }
}
