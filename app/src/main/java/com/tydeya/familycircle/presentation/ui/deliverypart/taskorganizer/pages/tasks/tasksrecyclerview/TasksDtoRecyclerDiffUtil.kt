package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.tasksrecyclerview

import androidx.recyclerview.widget.DiffUtil
import com.tydeya.familycircle.data.taskorganizer.FamilyTaskDto

class TasksDtoRecyclerDiffUtil(
        private val oldList: List<FamilyTaskDto>,
        private val newList: List<FamilyTaskDto>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].familyTask.id == newList[newItemPosition].familyTask.id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}