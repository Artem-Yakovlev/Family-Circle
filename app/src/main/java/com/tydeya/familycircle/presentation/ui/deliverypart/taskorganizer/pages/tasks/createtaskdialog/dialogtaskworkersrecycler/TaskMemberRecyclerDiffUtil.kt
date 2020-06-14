package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.createtaskdialog.dialogtaskworkersrecycler

import androidx.recyclerview.widget.DiffUtil
import com.tydeya.familycircle.data.taskorganizer.TaskMember

class TaskMemberRecyclerDiffUtil(
        private val oldList: List<TaskMember>,
        private val newList: List<TaskMember>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].phoneNumber == newList[newItemPosition].phoneNumber

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}
