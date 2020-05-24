package com.tydeya.familycircle.data.eventreminder

import java.util.*
import kotlin.collections.ArrayList

data class FamilyEvent(
        val id: String,
        val information: FamilyEventInformation,
        val time: FamilyEventTime,
        val audience: FamilyEventAudience,
        val style: FamilyEventStyle = FamilyEventStyle()) {

    data class FamilyEventInformation(
            val title: String,
            val description: String = "",
            val type: EventType
    )

    data class FamilyEventTime(
            val firstCalendar: Calendar,
            val secondCalendar: Calendar = firstCalendar.clone() as Calendar,
            val timeType: EventTimeType = EventTimeType.ONLY_DATE_WITHOUT_PERIOD,
            val repeatType: EventRepeatType = EventRepeatType.NOT_REPEATED
    )

    data class FamilyEventAudience(
            val author: String,
            val audienceType: EventAudienceType = EventAudienceType.ALL,
            val audience: List<String> = ArrayList()
    )

    data class FamilyEventStyle(
            val theme: EventStyleTheme = EventStyleTheme.COLOR_LIGHT_BLUE
    )

}

public enum class EventType {
    ROUTINE, BIRTHDATE, IMPORTANT
}

public enum class EventTimeType {
    ONLY_DATE_WITHOUT_PERIOD, ONLY_DATE_WITH_PERIOD, DATE_AND_TIME_WITHOUT_PERIOD,
    DATE_AND_TIME_WITH_PERIOD
}

public enum class EventAudienceType {
    ALL, GROUP
}

public enum class EventStyleTheme {
    COLOR_DARK_BLUE, COLOR_LIGHT_BLUE, COLOR_DARK_GREEN, COLOR_LIGHT_GREEN, COLOR_ORANGE,
    THEME_CUSTOM
}

public enum class EventRepeatType {
    NOT_REPEATED, EVERYDAY, EVERY_2_DAY, EVERY_WEEK, EVERY_MONTH, EVERY_YEAR
}
