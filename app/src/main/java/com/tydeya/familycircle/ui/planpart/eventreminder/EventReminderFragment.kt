package com.tydeya.familycircle.ui.planpart.eventreminder

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.domain.eventreminder.interactor.abstraction.EventInteractorCallback
import com.tydeya.familycircle.domain.eventreminder.interactor.details.EventInteractor
import com.tydeya.familycircle.ui.planpart.eventreminder.recyclerview.EventReminderRecyclerViewAdapter
import com.tydeya.familycircle.ui.planpart.eventreminder.recyclerview.EventReminderRecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_event_reminder.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class EventReminderFragment
    :
        Fragment(R.layout.fragment_event_reminder), EventInteractorCallback,
        EventReminderRecyclerViewClickListener {

    @Inject
    lateinit var eventInteractor: EventInteractor

    private lateinit var adapter: EventReminderRecyclerViewAdapter

    private var pageIndex = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectEventReminderFragment(this)
        setAdapter()
        setCalendar()
        setBackToTodayButton()
    }

    /**
     * Date setters
     * */

    private fun setBackToTodayButton() {
        event_reminder_main_calendar_reset_button.setOnClickListener {
            event_reminder_main_calendar.setDate(getCleanTodayDate())
            event_reminder_main_calendar_reset_button.visibility = View.INVISIBLE
            showData(getCleanTodayDate())
        }
    }

    private fun setAdapter() {
        adapter = EventReminderRecyclerViewAdapter(context!!, ArrayList(), this, GregorianCalendar())

        event_reminder_main_recyclerview.adapter = adapter
        event_reminder_main_recyclerview.layoutManager =
                LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)

        event_reminder_main_nested_scroll.setOnScrollChangeListener(NestedScrollView
                .OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                    if (scrollY > oldScrollY) {
                        event_reminder_floating_button.hide();
                    } else {
                        event_reminder_floating_button.show();
                    }
                })
    }

    private fun setCalendar() {
        event_reminder_main_calendar.setOnDayClickListener { eventDay ->
            showData(eventDay.calendar)
        }

        fun isBackButtonReady() {
            if (pageIndex != 0) {
                event_reminder_main_calendar_reset_button.visibility = View.VISIBLE
            } else {
                event_reminder_main_calendar_reset_button.visibility = View.INVISIBLE
            }
        }

        event_reminder_main_calendar.setOnForwardPageChangeListener {
            pageIndex++
            isBackButtonReady()
        }

        event_reminder_main_calendar.setOnPreviousPageChangeListener {
            pageIndex--
            isBackButtonReady()
        }

        val calendar = GregorianCalendar()
        val minimumDate = GregorianCalendar(calendar.get(Calendar.YEAR) - 5,
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        val maxDate = GregorianCalendar(calendar.get(Calendar.YEAR) + 5,
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        event_reminder_main_calendar.setMinimumDate(minimumDate)
        event_reminder_main_calendar.setMaximumDate(maxDate)

        event_reminder_main_calendar.setDate(getCleanTodayDate())

    }

    private fun fillCalendarFromData() {
        GlobalScope.launch(Dispatchers.Default) {
            val events = ArrayList<EventDay>()
            val currentYear = GregorianCalendar().get(Calendar.YEAR)

            eventInteractor.familySingleEvents.forEach {
                val calendarEvent = GregorianCalendar()
                calendarEvent.timeInMillis = it.timestamp
                events.add(EventDay(calendarEvent, R.drawable.ic_lens_blue_4dp))
            }

            eventInteractor.familyAnnualEvents.forEach {
                for (year in (currentYear - 5)..(currentYear + 5)) {
                    val calendarEvent = GregorianCalendar()
                    calendarEvent.timeInMillis = it.timestamp
                    calendarEvent.set(Calendar.YEAR, year)
                    events.add(EventDay(calendarEvent, R.drawable.ic_lens_blue_4dp))
                }
            }
            withContext(Dispatchers.Main) {
                event_reminder_main_calendar.setEvents(events)
            }
        }
    }

    /**
     * Recycler view
     * */

    private fun getEventForDisplay(timestamp: Long): ArrayList<FamilyEvent> {
        val displayedEvents = ArrayList<FamilyEvent>()

        val selectedCalendar = GregorianCalendar()
        selectedCalendar.timeInMillis = timestamp

        eventInteractor.familyAnnualEvents.forEach {
            val eventCalendar = GregorianCalendar()
            eventCalendar.timeInMillis = it.timestamp

            if (eventCalendar.get(Calendar.DAY_OF_MONTH) == selectedCalendar.get(Calendar.DAY_OF_MONTH)
                    && eventCalendar.get(Calendar.MONTH) == selectedCalendar.get(Calendar.MONTH)) {
                displayedEvents.add(it)
            }
        }

        eventInteractor.familySingleEvents.forEach {
            if (it.timestamp == timestamp) {
                displayedEvents.add(it)
            }
        }
        return displayedEvents
    }

    private fun showData(date: Calendar) {
        adapter.refreshData(getEventForDisplay(date.timeInMillis), date)
    }

    /**
     * Callbacks
     * */

    override fun eventDataFromServerUpdated() {
        fillCalendarFromData()
        showData(event_reminder_main_calendar.firstSelectedDate)
    }

    override fun onPause() {
        super.onPause()
        eventInteractor.unsubscribe(this)
    }

    override fun onResume() {
        super.onResume()
        eventInteractor.subscribe(this)
    }

    /**
     * RecyclerView callbacks
     * */

    override fun onEventClickListener(id: String) {
        NavHostFragment.findNavController(this).navigate(R.id.eventViewPage,
                Bundle().apply {
                    putString("id", id)
                    putInt("year", event_reminder_main_calendar.firstSelectedDate.get(Calendar.YEAR))
                })
    }

    /**
     * Utils
     * */

    private fun getCleanTodayDate(): Calendar {
        val rawCalendar = GregorianCalendar()
        return GregorianCalendar(rawCalendar.get(Calendar.YEAR), rawCalendar.get(Calendar.MONTH),
                rawCalendar.get(Calendar.DAY_OF_MONTH))
    }
}
