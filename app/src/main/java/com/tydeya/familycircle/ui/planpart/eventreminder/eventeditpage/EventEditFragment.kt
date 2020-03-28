package com.tydeya.familycircle.ui.planpart.eventreminder.eventeditpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import com.tydeya.familycircle.R
import kotlinx.android.synthetic.main.fragment_event_edit.*


class EventEditFragment : Fragment(R.layout.fragment_event_edit) {

    private lateinit var eventId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventId = arguments!!.getString("eventId", "")
        setCurrentData()
    }

    private fun setCurrentData() {
        setSpinnersData()
    }

    private fun setSpinnersData() {
        ArrayAdapter.createFromResource(context!!, R.array.events_type,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            event_reminder_edit_page_type_spinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(context!!, R.array.events_priority,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            event_reminder_edit_page_priority_spinner.adapter = adapter
        }
    }

}
