package com.tydeya.familycircle.ui.planpart.taskorganizer.pages.taskshistory.recyclerview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.FamilyTaskStatus
import kotlinx.android.synthetic.main.cardview_tasks_organizer_history_errand.view.*

class HistoryTasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(context: Context, familyTask: FamilyTask, authorName: String, workerName: String) {

        itemView.history_errand_cardview_interaction_text.text = context.resources.getString(
                R.string.history_errand_cardview_interaction_placeholder, authorName, workerName)

        itemView.history_errand_cardview_text.text = familyTask.text

        Glide.with(context).load(when (familyTask.status) {
            FamilyTaskStatus.REJECTED -> R.drawable.ic_close_red_36dp
            else -> R.drawable.ic_done_green_36dp
        }).into(itemView.history_errand_cardview_status_image)
    }

}