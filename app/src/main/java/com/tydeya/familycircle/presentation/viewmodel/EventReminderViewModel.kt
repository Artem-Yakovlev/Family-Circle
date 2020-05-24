package com.tydeya.familycircle.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.eventreminder.EventStyleTheme
import com.tydeya.familycircle.data.eventreminder.EventType
import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.utils.Resource
import java.util.*
import kotlin.collections.ArrayList

class EventReminderViewModel : ViewModel() {

    val eventsResource: MutableLiveData<Resource<ArrayList<FamilyEvent>>> =
            MutableLiveData(Resource.Success(ArrayList<FamilyEvent>().apply {
                add(FamilyEvent(
                        id = "",
                        information = FamilyEvent.FamilyEventInformation(
                                title = "Выгулять рэкса",
                                type = EventType.ROUTINE),
                        time = FamilyEvent.FamilyEventTime(firstCalendar = GregorianCalendar()),
                        audience = FamilyEvent.FamilyEventAudience(""),
                        style = FamilyEvent.FamilyEventStyle(theme = EventStyleTheme.COLOR_LIGHT_GREEN)
                ))

                add(FamilyEvent(
                        id = "",
                        information = FamilyEvent.FamilyEventInformation(
                                title = "Семейный завтрак",
                                description = "Не забудь про булочки",
                                type = EventType.ROUTINE),
                        time = FamilyEvent.FamilyEventTime(firstCalendar = GregorianCalendar()),
                        audience = FamilyEvent.FamilyEventAudience(""),
                        style = FamilyEvent.FamilyEventStyle(theme = EventStyleTheme.COLOR_DARK_GREEN)
                ))

                add(FamilyEvent(
                        id = "",
                        information = FamilyEvent.FamilyEventInformation(
                                title = "День рождение Марка",
                                type = EventType.BIRTHDATE),
                        time = FamilyEvent.FamilyEventTime(firstCalendar = GregorianCalendar()),
                        audience = FamilyEvent.FamilyEventAudience(""),
                        style = FamilyEvent.FamilyEventStyle(theme = EventStyleTheme.COLOR_LIGHT_BLUE)
                ))

            }))

}