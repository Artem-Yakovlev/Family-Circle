package com.tydeya.familycircle.ui.planpart.eventmanager.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.eventmanager.FamilyEvent
import com.tydeya.familycircle.data.eventmanager.FamilyEventType
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import kotlinx.android.synthetic.main.cardview_event_reminder_view_holder.view.*
import java.util.*

class EventReminderViewHolder(itemView: View, val listener: EventReminderRecyclerViewClickListener)
    :
        RecyclerView.ViewHolder(itemView) {

    fun bindData(familyEvent: FamilyEvent, date: Calendar) {

        itemView.event_reminder_cardview_title.text = familyEvent.title

        itemView.event_reminder_cardview_date.text = DateRefactoring.getDateLocaleText(date)

        itemView.event_reminder_cardview_time.text =
                if (familyEvent.type == FamilyEventType.ANNUAL_EVENT) {
                    itemView.context.getString(R.string.annually)
                } else {
                    itemView.context.getString(R.string.this_year)
                }

        itemView.setOnClickListener {
            listener.onEventClickListener(familyEvent.id)
        }
    }

}