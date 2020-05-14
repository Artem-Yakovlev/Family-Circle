package com.tydeya.familycircle.ui.planpart.eventmanager.eventeditpage

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.Application.EVENT_EDIT_PAGE_WORKING_MODE
import com.tydeya.familycircle.data.constants.NavigateConsts.BUNDLE_EVENT_ID
import com.tydeya.familycircle.data.eventmanager.FamilyEvent
import com.tydeya.familycircle.data.eventmanager.FamilyEventPriority
import com.tydeya.familycircle.data.eventmanager.FamilyEventType
import com.tydeya.familycircle.data.eventmanager.WorkingMode
import com.tydeya.familycircle.domain.eventmanager.interactor.details.EventInteractor
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerPresenter
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerUsable
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import com.tydeya.familycircle.framework.simplehelpers.DataConfirming
import com.tydeya.familycircle.utils.value
import kotlinx.android.synthetic.main.fragment_event_edit.*
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject


class EventEditFragment : Fragment(R.layout.fragment_event_edit), DatePickerUsable, EventAbleToActionCallback {

    @Inject
    lateinit var eventInteractor: EventInteractor

    private lateinit var workMode: WorkingMode
    private lateinit var eventId: String

    private var editableDate: Long = -1L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectFragment(this)
        prepareForWork()
        choiceMode()
        event_reminder_edit_done.setOnClickListener {
            if (workMode == WorkingMode.EDIT) {
                editIntent()
            } else {
                createIntent()
            }
        }
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

            eventId = arguments!!.getString(BUNDLE_EVENT_ID, "")
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
        eventInteractor.getEventById(eventId)?.let {
            event_reminder_edit_title.value = it.title
            event_reminder_edit_description_text.value = it.description

            val calendar = GregorianCalendar()
            calendar.timeInMillis = it.timestamp

            event_reminder_edit_date_output.text = DateRefactoring.getDateLocaleText(calendar)

            event_reminder_edit_page_type_spinner.setSelection(it.type.ordinal)
            event_reminder_edit_page_priority_spinner.setSelection(it.priority.ordinal)
            editableDate = it.timestamp
        }

    }

    override fun dateChanged(selectedDateYear: Int, selectedDateMonth: Int, selectedDateDay: Int) {
        val dateChanged = GregorianCalendar(selectedDateYear, selectedDateMonth, selectedDateDay)
        event_reminder_edit_date_output.text = DateRefactoring.getDateLocaleText(dateChanged)
        editableDate = dateChanged.timeInMillis
        event_reminder_edit_date_output
                .setTextColor(ContextCompat.getColor(context!!, R.color.colorGray))
    }

    /**
     * Perform intent of working mode
     * */

    private fun editIntent() {
        if (isDataCorrect()) {
            eventInteractor.checkExistEventWithData(event_reminder_edit_title.text.toString().trim(),
                    editableDate, eventId, this)
        }
    }

    private fun createIntent() {
        if (isDataCorrect()) {
            eventInteractor.checkExistEventWithData(event_reminder_edit_title.text.toString().trim(),
                    editableDate, "", this)
        }
    }

    private fun isDataCorrect(): Boolean {
        if (!DataConfirming.isEmptyNecessaryCheck(event_reminder_edit_title, true)) {
            if (editableDate != -1L) {
                return true
            } else {
                event_reminder_edit_date_output
                        .setTextColor(ContextCompat.getColor(context!!, R.color.design_default_color_error))
            }
        }
        return false
    }

    /**
     * Able to action callbacks
     * */

    override fun ableToPerformAction(title: String, timestamp: Long) {

        val familyEvent = FamilyEvent("", title, timestamp,
                FirebaseAuth.getInstance().currentUser!!.phoneNumber!!,
                event_reminder_edit_description_text.text.toString().trim(),
                FamilyEventPriority.fromInt(event_reminder_edit_page_priority_spinner.selectedItemPosition),
                FamilyEventType.fromInt(event_reminder_edit_page_type_spinner.selectedItemPosition))

        if (workMode == WorkingMode.CREATE) {
            eventInteractor.createEvent(familyEvent)
        } else {
            familyEvent.id = eventId
            eventInteractor.editEvent(familyEvent)
        }

        NavHostFragment.findNavController(this).popBackStack()
    }

    override fun notAbleToPerformAction(title: String, timestamp: Long) {
        Toast.makeText(context!!,
                context!!.resources.getString(R.string.event_reminder_edit_page_not_able_to_create),
                Toast.LENGTH_LONG).show()
    }
}
