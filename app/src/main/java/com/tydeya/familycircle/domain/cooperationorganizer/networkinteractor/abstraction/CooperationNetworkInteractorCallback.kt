package com.tydeya.familycircle.domain.cooperationorganizer.networkinteractor.abstraction

import com.tydeya.familycircle.data.cooperation.Cooperation

interface CooperationNetworkInteractorCallback {

    fun cooperationDataFromServerUpdate(cooperationData: ArrayList<Cooperation>)

}