package com.tydeya.familycircle.ui.planpart.eventreminder.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import java.util.*

class EventReminderRecyclerViewAdapter(
        private val context: Context,
        private var events: ArrayList<FamilyEvent>,
        private val listener: EventReminderRecyclerViewClickListener,
        private val date: Calendar)
    :
        RecyclerView.Adapter<EventReminderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventReminderViewHolder =
            EventReminderViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.cardview_event_reminder_view_holder,
                            parent, false), listener)

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventReminderViewHolder, position: Int) {
        holder.bindData(events[position], date)
    }

    fun refreshData(events: ArrayList<FamilyEvent>, date: Calendar) {
        this.events = events
        this.date.timeInMillis = date.timeInMillis
        notifyDataSetChanged()
    }
}