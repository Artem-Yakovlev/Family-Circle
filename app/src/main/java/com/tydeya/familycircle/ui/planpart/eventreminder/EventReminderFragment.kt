package com.tydeya.familycircle.ui.planpart.eventreminder

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.tydeya.familycircle.R
import kotlinx.android.synthetic.main.fragment_event_reminder.*
import java.util.*


class EventReminderFragment : Fragment(R.layout.fragment_event_reminder) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCalendar()
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
                val calender = Calendar.getInstance()
                calender.timeInMillis = firstDayOfNewMonth!!.time
                event_reminder_main_calendar_year.text = calender.get(Calendar.YEAR).toString()
                event_reminder_main_calendar_month.text = calendarMonths[calender.get(Calendar.MONTH)]
            }

        })
    }

}
