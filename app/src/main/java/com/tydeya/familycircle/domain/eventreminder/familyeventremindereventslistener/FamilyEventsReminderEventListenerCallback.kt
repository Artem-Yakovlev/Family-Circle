package com.tydeya.familycircle.domain.eventreminder.familyeventremindereventslistener

import android.util.ArrayMap
import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.utils.Resource
import java.util.*

interface FamilyEventsReminderEventListenerCallback {

    fun allBuyCatalogsUpdated(familyEvents: Resource<ArrayMap<Calendar, ArrayList<FamilyEvent>>>)

}