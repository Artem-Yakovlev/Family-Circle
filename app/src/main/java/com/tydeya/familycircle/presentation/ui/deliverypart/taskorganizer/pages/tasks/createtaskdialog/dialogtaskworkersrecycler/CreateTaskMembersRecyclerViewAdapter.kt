package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.createtaskdialog.dialogtaskworkersrecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.taskorganizer.TaskMember
import com.tydeya.familycircle.databinding.RecyclerItemCheckboxMemberInDialogBinding

class CreateTaskMembersRecyclerViewAdapter(
        val members: ArrayList<TaskMember> = ArrayList()
) :
        RecyclerView.Adapter<CreateTaskMemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CreateTaskMemberViewHolder(RecyclerItemCheckboxMemberInDialogBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
            )


    override fun onBindViewHolder(holder: CreateTaskMemberViewHolder, position: Int) {
        holder.bindData(members[position])
    }

    fun refreshData(members: ArrayList<TaskMember>) {
        val diffResult = DiffUtil.calculateDiff(TaskMemberRecyclerDiffUtil(
                this.members, members
        ))
        this.members.clear()
        this.members.addAll(members)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = members.size

}
