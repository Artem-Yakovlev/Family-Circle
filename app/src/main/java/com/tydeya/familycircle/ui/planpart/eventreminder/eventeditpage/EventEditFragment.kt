package com.tydeya.familycircle.ui.planpart.eventreminder.eventeditpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.NavHostFragment
import com.tydeya.familycircle.App

import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.Application.EVENT_EDIT_PAGE_WORKING_MODE
import com.tydeya.familycircle.data.eventreminder.WorkingMode
import com.tydeya.familycircle.domain.eventreminder.interactor.details.EventInteractor
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerPresenter
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerUsable
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import com.tydeya.familycircle.utils.value
import kotlinx.android.synthetic.main.fragment_event_edit.*
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject


class EventEditFragment : Fragment(R.layout.fragment_event_edit), DatePickerUsable {

    @Inject
    lateinit var eventnteractor: EventInteractor

    private lateinit var workMode: WorkingMode
    private lateinit var eventId: String

    private var editableDate: Long = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectFragment(this)
        prepareForWork()
        choiceMode()
    }

    /**
     * Prepare for work
     * */

    private fun choiceMode() {
        if (WorkingMode.getModeFromInt(arguments!!.getInt(EVENT_EDIT_PAGE_WORKING_MODE)) ==
                WorkingMode.EDIT) {
            workMode = WorkingMode.EDIT
            event_reminder_edit_toolbar.title =
                    context!!.resources.getString(R.string.event_reminder_edit_toolbar_title_edit)

            eventId = arguments!!.getString("eventId", "")
            setCurrentData()
        } else {
            workMode = WorkingMode.CREATE
            event_reminder_edit_toolbar.title =
                    context!!.resources.getString(R.string.event_reminder_edit_toolbar_title_edit)
        }
    }

    private fun prepareForWork() {
        event_reminder_edit_toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
        setSpinnerData(R.array.events_type, event_reminder_edit_page_type_spinner)
        setSpinnerData(R.array.events_priority, event_reminder_edit_page_priority_spinner)

        event_reminder_edit_date_picker.setOnClickListener {
            val upperLimit = GregorianCalendar()
            upperLimit.set(Calendar.YEAR, upperLimit.get(Calendar.YEAR) + 5)

            DatePickerPresenter(WeakReference(this), upperLimit)
                    .onClick(event_reminder_edit_date_picker)
        }
    }

    private fun setSpinnerData(stringArrayID: Int, spinnerView: Spinner) {
        ArrayAdapter.createFromResource(context!!,
                stringArrayID,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerView.adapter = adapter
        }
    }

    /**
     * Set and edit current data
     * */

    private fun setCurrentData() {
        eventnteractor.getEventById(eventId)?.let {
            event_reminder_edit_title.value = it.title
            event_reminder_edit_description_text.value = it.description

            val calendar = GregorianCalendar()
            calendar.timeInMillis = it.timestamp

            event_reminder_edit_date_output.text = DateRefactoring.getDateLocaleText(calendar)

            event_reminder_edit_page_type_spinner.setSelection(it.type.ordinal)
            event_reminder_edit_page_priority_spinner.setSelection(it.priority.ordinal)
        }

    }

    override fun dateChanged(selectedDateYear: Int, selectedDateMonth: Int, selectedDateDay: Int) {
        val dateChanged = GregorianCalendar(selectedDateYear, selectedDateMonth, selectedDateDay)
        event_reminder_edit_date_output.text = DateRefactoring.getDateLocaleText(dateChanged)
        editableDate = dateChanged.timeInMillis
    }

}
