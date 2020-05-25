package com.tydeya.familycircle.presentation.viewmodel

import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.domain.eventreminder.familyeventremindereventslistener.FamilyEventsReminderEventListener
import com.tydeya.familycircle.domain.eventreminder.familyeventremindereventslistener.FamilyEventsReminderEventListenerCallback
import com.tydeya.familycircle.utils.Resource
import java.util.*

class EventReminderViewModel : ViewModel(), FamilyEventsReminderEventListenerCallback {

    val eventsResource: MutableLiveData<Resource<ArrayMap<Calendar, ArrayList<FamilyEvent>>>> =
            MutableLiveData(Resource.Loading())

    private val eventsListener = FamilyEventsReminderEventListener(this)

    init {
        eventsListener.register()
    }

    override fun allBuyCatalogsUpdated(
            familyEvents: Resource<ArrayMap<Calendar, ArrayList<FamilyEvent>>>
    ) {
        this.eventsResource.value = familyEvents
    }

    fun createFamilyEvent(familyEvent: FamilyEvent) {

    }

    override fun onCleared() {
        super.onCleared()
        eventsListener.unregister()
    }
}