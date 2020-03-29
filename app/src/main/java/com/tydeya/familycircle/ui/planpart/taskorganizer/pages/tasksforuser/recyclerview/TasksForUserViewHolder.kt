package com.tydeya.familycircle.ui.planpart.taskorganizer.pages.tasksforuser.recyclerview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import kotlinx.android.synthetic.main.cardview_tasks_organizer_errand_for_you.view.*

class TasksForUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(context: Context, familyTask: FamilyTask, authorName: String) {
        itemView.errand_for_you_cardview_author.text = context.resources.
        getString(R.string.errand_for_you_cardview_author_placeholder, authorName)

        itemView.errand_for_you_cardview_text.text = familyTask.text

        itemView.errand_for_you_cardview_status.text = context.resources
                .getStringArray(R.array.tasks_status)[familyTask.status.ordinal]
    }

}