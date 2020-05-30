package com.tydeya.familycircle.domain.familyinteractor.details

import com.tydeya.familycircle.data.family.Family
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyInteractorCallback
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyInteractorObservable
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyNetworkInteractor
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyNetworkInteractorCallback
import java.util.*

class FamilyInteractor : FamilyNetworkInteractorCallback, FamilyInteractorObservable {

    private val networkInteractor: FamilyNetworkInteractor = FamilyNetworkInteractorImpl(this)

    private val observers: ArrayList<FamilyInteractorCallback> = ArrayList()

    val actualFamily = Family(0, "Test family", ArrayList())

    init {
        networkInteractor.requireMembersDataFromServer()
    }

    override fun memberDataFromServerUpdate(members: ArrayList<FamilyMember>) {
        actualFamily.familyMembers = members
        notifyObserversMemberDataUpdated()
    }

    private fun notifyObserversMemberDataUpdated() {
        for (callback in observers) {
            callback.memberDataUpdated()
        }
    }

    override fun subscribe(callback: FamilyInteractorCallback) {
        if (!observers.contains(callback)) {
            observers.add(callback)
        }
    }

    override fun unsubscribe(callback: FamilyInteractorCallback) {
        observers.remove(callback)
    }
}