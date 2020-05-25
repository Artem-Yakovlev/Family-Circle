package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksbyuser.recyclerview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import kotlinx.android.synthetic.main.cardview_tasks_organizer_your_errand.view.*

class TasksByUserViewHolder(itemView: View, val clickListener: TasksByUserRecyclerViewOnClickListener)
    :
        RecyclerView.ViewHolder(itemView) {

    fun bindData(context: Context, familyTask: FamilyTask, workerName: String) {

        itemView.your_errand_cardview_text.text = familyTask.text
        itemView.your_errand_cardview_worker.text = context.resources
                .getString(R.string.your_errand_cardview_worker_placeholder, workerName)

        itemView.your_errand_cardview_edit_button.setOnClickListener {
            clickListener.editEvent(familyTask)
        }

        itemView.your_errand_cardview_delete_button.setOnClickListener {
            clickListener.deleteEvent(familyTask)
        }
    }

}