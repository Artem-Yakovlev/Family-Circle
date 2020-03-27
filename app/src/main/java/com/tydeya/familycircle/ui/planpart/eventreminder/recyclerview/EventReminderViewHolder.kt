package com.tydeya.familycircle.ui.planpart.eventreminder.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import kotlinx.android.synthetic.main.cardview_event_reminder_view_holder.view.*

class EventReminderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindData(familyEvent: FamilyEvent) {
        itemView.event_reminder_cardview_title.text = familyEvent.title
        itemView.event_reminder_cardview_date.text = familyEvent.timestamp.toString()
    }

}