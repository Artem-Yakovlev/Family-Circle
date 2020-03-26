package com.tydeya.familycircle.domain.eventreminder.networkInteractor.abstraction

import com.tydeya.familycircle.data.eventreminder.FamilyEvent

interface EventNetworkInteractorCallback {

    fun eventDataUpdate(familyEvent: ArrayList<FamilyEvent>)
}