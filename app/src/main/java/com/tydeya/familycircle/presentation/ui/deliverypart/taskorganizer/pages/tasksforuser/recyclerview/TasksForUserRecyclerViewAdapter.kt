package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksforuser.recyclerview

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

class TasksForUserRecyclerViewAdapter(val context: Context,
                                      var familyTasks: ArrayList<FamilyTask>,
                                      var clickListener: TasksForUserRecyclerViewClickListener)
    :
        RecyclerView.Adapter<TasksForUserViewHolder>() {

    @Inject
    lateinit var familyInteractor: FamilyInteractor

    init {
        App.getComponent().injectRecyclerViewAdapter(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksForUserViewHolder =
            TasksForUserViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.cardview_tasks_organizer_errand_for_you,
                            parent, false), clickListener)

    override fun onBindViewHolder(holder: TasksForUserViewHolder, position: Int) {

        val authorUser = FamilyAssistantImpl(familyInteractor.actualFamily)
                .getUserByPhone(familyTasks[position].author)

        holder.bindData(context, familyTasks[position], authorUser?.description?.name
                ?: context.resources.getString(R.string.unknown_text))
    }

    override fun getItemCount() = familyTasks.size

    fun refreshData(tasks: ArrayList<FamilyTask>) {
        this.familyTasks = tasks
        notifyDataSetChanged()
    }
}