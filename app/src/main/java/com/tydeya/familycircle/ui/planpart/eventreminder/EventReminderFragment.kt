package com.tydeya.familycircle.ui.planpart.eventreminder

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.github.sundeepk.compactcalendarview.domain.Event
import com.melnykov.fab.ObservableScrollView
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.domain.eventreminder.interactor.abstraction.EventInteractorCallback
import com.tydeya.familycircle.domain.eventreminder.interactor.details.EventInteractor
import com.tydeya.familycircle.ui.planpart.eventreminder.recyclerview.EventReminderRecyclerViewAdapter
import com.tydeya.familycircle.ui.planpart.eventreminder.recyclerview.EventReminderRecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_event_reminder.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectEventReminderFragment(this)
        setCalendar()
        fillCalendarFromData(GregorianCalendar().get(Calendar.YEAR))

        event_reminder_main_calendar_reset_button.setOnClickListener {
            event_reminder_main_calendar.setCurrentDate(GregorianCalendar().time)
            event_reminder_main_calendar_reset_button.visibility = View.INVISIBLE
            adapter.refreshData(getEventForDisplay(GregorianCalendar().timeInMillis))
        }

        adapter = EventReminderRecyclerViewAdapter(context!!,
                getEventForDisplay(GregorianCalendar().timeInMillis), this)

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
        val calendarMonths = context!!.resources.getStringArray(R.array.calendar_months)

        event_reminder_main_calendar_year.text =
                Calendar.getInstance().get(Calendar.YEAR).toString()
        event_reminder_main_calendar_month.text =
                calendarMonths[Calendar.getInstance().get(Calendar.MONTH)]

        event_reminder_main_calendar.setListener(object : CompactCalendarViewListener {

            override fun onDayClick(dateClicked: Date?) {
                showData(getEventForDisplay(dateClicked!!.time))
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date?) {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = firstDayOfNewMonth!!.time
                event_reminder_main_calendar_year.text = calendar.get(Calendar.YEAR).toString()
                event_reminder_main_calendar_month.text = calendarMonths[calendar.get(Calendar.MONTH)]

                if (calendar.get(Calendar.MONTH) == 0 || calendar.get(Calendar.MONTH) == 11) {
                    fillCalendarFromData(calendar.get(Calendar.YEAR))
                }

                val actualData = GregorianCalendar()

                if (calendar.get(Calendar.MONTH) == actualData.get(Calendar.MONTH)
                        && calendar.get(Calendar.YEAR) == actualData.get(Calendar.YEAR)) {
                    event_reminder_main_calendar_reset_button.visibility = View.INVISIBLE
                } else {
                    event_reminder_main_calendar_reset_button.visibility = View.VISIBLE
                }
                showData(getEventForDisplay(firstDayOfNewMonth.time))
            }

        })
    }

    private fun fillCalendarFromData(year: Int) {
        event_reminder_main_calendar.removeAllEvents()
        eventInteractor.familySingleEvents.forEach {
            event_reminder_main_calendar.addEvent(Event(resources.getColor(R.color.colorPrimary), it.timestamp))
        }
        eventInteractor.familyAnnualEvents.forEach {
            val calendar = GregorianCalendar()
            calendar.timeInMillis = it.timestamp

            if (calendar.get(Calendar.YEAR) <= year && year <= calendar.get(Calendar.YEAR) + 5) {

                event_reminder_main_calendar.addEvent(Event(resources.getColor(R.color.colorPrimary),
                        getEventOfYear(it.timestamp, year)))
            }
        }
    }

    private fun getEventOfYear(originalTimestamp: Long, year: Int): Long {
        val calendar = GregorianCalendar()
        calendar.timeInMillis = originalTimestamp
        calendar.set(Calendar.YEAR, year)
        return calendar.timeInMillis
    }

    /**
     * Recycler view
     * */

    private fun getEventForDisplay(timestamp: Long): ArrayList<FamilyEvent> {
        val displayedEvents = ArrayList<FamilyEvent>()

        val calendar = GregorianCalendar()
        calendar.timeInMillis = timestamp

        eventInteractor.familyAnnualEvents.forEach {
            val eventCalendar = GregorianCalendar()
            eventCalendar.timeInMillis = it.timestamp

            if (eventCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
                    && eventCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
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

    private fun showData(events: ArrayList<FamilyEvent>) {
        adapter.refreshData(events)
    }

    /**
     * Callbacks
     * */

    override fun eventDataFromServerUpdated() {
        fillCalendarFromData(event_reminder_main_calendar_year.text.toString().toInt())
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
                })
    }


}
