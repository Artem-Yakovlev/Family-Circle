package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.taskshistory.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import javax.inject.Inject

class HistoryTasksRecyclerViewAdapter(val context: Context, var historyTasks: ArrayList<FamilyTask>)
    :
        RecyclerView.Adapter<HistoryTasksViewHolder>() {

    @Inject
    lateinit var familyInteractor: FamilyInteractor

    init {
        App.getComponent().injectRecyclerViewAdapter(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            HistoryTasksViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.cardview_tasks_organizer_history_errand, parent, false))

    override fun getItemCount() = historyTasks.size

    override fun onBindViewHolder(holder: HistoryTasksViewHolder, position: Int) {

        val authorUser = FamilyAssistantImpl(familyInteractor.actualFamily)
                .getUserByPhone(historyTasks[position].author)

        val workerUser = FamilyAssistantImpl(familyInteractor.actualFamily)
                .getUserByPhone(historyTasks[position].worker)

        holder.bindData(context, historyTasks[position],
                authorUser?.description?.name ?: context.resources.getString(R.string.unknown_text),
                workerUser?.description?.name ?: context.resources.getString(R.string.unknown_text))
    }

    fun refreshData(historyTasks: ArrayList<FamilyTask>) {
        this.historyTasks = historyTasks
        notifyDataSetChanged()
    }
}