package com.tydeya.familycircle.domain.eventmanager.networkInteractor.abstraction

import com.tydeya.familycircle.data.eventmanager.FamilyEvent

interface EventNetworkInteractorCallback {

    fun eventDataUpdate(familySingleEvents: ArrayList<FamilyEvent>,
                        familyAnnualEvents: ArrayList<FamilyEvent>)
}