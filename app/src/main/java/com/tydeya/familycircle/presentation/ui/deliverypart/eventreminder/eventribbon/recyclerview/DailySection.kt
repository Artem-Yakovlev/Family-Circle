package com.tydeya.familycircle.presentation.ui.deliverypart.eventreminder.eventribbon.recyclerview

import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.xwray.groupie.Section
import java.text.SimpleDateFormat
import java.util.*

class DailySection() : Section() {

    constructor(events: List<FamilyEvent>) : this() {
        if (events.isNotEmpty()) {
            setHeader(EventRibbonGroup(SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
                    .format(events[0].time.firstCalendar.timeInMillis).toUpperCase()))
        }
        addAll(events.toEventRibbonItems())
    }

    private fun List<FamilyEvent>.toEventRibbonItems(): List<EventRibbonItem> {
        return this.map { text ->
            EventRibbonItem(text)
        }
    }
}