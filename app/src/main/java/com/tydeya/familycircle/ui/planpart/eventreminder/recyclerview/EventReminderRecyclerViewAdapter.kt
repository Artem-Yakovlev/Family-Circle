package com.tydeya.familycircle.ui.planpart.eventreminder.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.eventreminder.FamilyEvent

class EventReminderRecyclerViewAdapter(
        private val context: Context,
        private var events: ArrayList<FamilyEvent>)
    :
        RecyclerView.Adapter<EventReminderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventReminderViewHolder =
            EventReminderViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.cardview_event_reminder_view_holder,
                            parent, false))

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventReminderViewHolder, position: Int) {
        holder.bindData(events[position])
    }

    fun refreshData(events: ArrayList<FamilyEvent>) {
        this.events = events
        notifyDataSetChanged()
    }
}