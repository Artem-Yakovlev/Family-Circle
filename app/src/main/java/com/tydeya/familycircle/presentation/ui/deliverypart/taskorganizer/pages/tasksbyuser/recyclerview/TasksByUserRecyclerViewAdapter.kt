package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksbyuser.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.oldfamilyinteractor.details.FamilyInteractor
import javax.inject.Inject

class TasksByUserRecyclerViewAdapter(
        val context: Context,
        var tasksByUser: ArrayList<FamilyTask>,
        val clickListener: TasksByUserRecyclerViewOnClickListener)
    :
        RecyclerView.Adapter<TasksByUserViewHolder>() {

    @Inject
    lateinit var familyInteractor: FamilyInteractor

    init {
        App.getComponent().injectRecyclerViewAdapter(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TasksByUserViewHolder(
            LayoutInflater.from(context).inflate(R.layout.cardview_tasks_organizer_your_errand,
                    parent, false), clickListener)


    override fun getItemCount(): Int = tasksByUser.size

    override fun onBindViewHolder(holder: TasksByUserViewHolder, position: Int) {
        val workerUser = FamilyAssistantImpl(familyInteractor.actualFamily)
                .getUserByPhone(tasksByUser[position].worker)

        holder.bindData(context, tasksByUser[position], workerUser?.description?.name
                ?: context.resources.getString(R.string.unknown_text))
    }

    fun refreshData(tasksByUser: ArrayList<FamilyTask>) {
        this.tasksByUser = tasksByUser
        notifyDataSetChanged()
    }
}