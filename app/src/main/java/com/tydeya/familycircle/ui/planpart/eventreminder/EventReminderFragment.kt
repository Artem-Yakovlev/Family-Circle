package com.tydeya.familycircle.ui.planpart.eventreminder

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.github.sundeepk.compactcalendarview.domain.Event
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.eventreminder.interactor.abstraction.EventInteractorCallback
import com.tydeya.familycircle.domain.eventreminder.interactor.details.EventInteractor
import kotlinx.android.synthetic.main.fragment_event_reminder.*
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


class EventReminderFragment : Fragment(R.layout.fragment_event_reminder), EventInteractorCallback {

    @Inject
    lateinit var eventInteractor: EventInteractor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectEventReminderFragment(this)
        setCalendar()
        fillCalendarFromData(GregorianCalendar().get(Calendar.YEAR))

        event_reminder_main_calendar_reset_button.setOnClickListener {
            event_reminder_main_calendar.setCurrentDate(GregorianCalendar().time)
            event_reminder_main_calendar_reset_button.visibility = View.INVISIBLE
        }
    }

    private fun setCalendar() {
        val calendarMonths = context!!.resources.getStringArray(R.array.calendar_months)

        event_reminder_main_calendar_year.text =
                Calendar.getInstance().get(Calendar.YEAR).toString()
        event_reminder_main_calendar_month.text =
                calendarMonths[Calendar.getInstance().get(Calendar.MONTH)]

        event_reminder_main_calendar.setListener(object : CompactCalendarViewListener {

            override fun onDayClick(dateClicked: Date?) {

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
            }

        })
    }

    private fun fillCalendarFromData(year: Int) {
        event_reminder_main_calendar.removeAllEvents()
        eventInteractor.familySingleEvents.forEach {
            event_reminder_main_calendar.addEvent(Event(resources.getColor(R.color.colorAccent), it.timestamp))
        }
        eventInteractor.familyAnnualEvents.forEach {
            val calendar = GregorianCalendar()
            calendar.timeInMillis = it.timestamp

            if (calendar.get(Calendar.YEAR) <= year && year <= calendar.get(Calendar.YEAR) + 5) {

                event_reminder_main_calendar.addEvent(Event(resources.getColor(R.color.colorAccent),
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


}
