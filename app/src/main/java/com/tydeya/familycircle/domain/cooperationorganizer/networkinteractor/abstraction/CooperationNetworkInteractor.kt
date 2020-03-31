package com.tydeya.familycircle.domain.cooperationorganizer.networkinteractor.abstraction

import com.tydeya.familycircle.data.cooperation.Cooperation

interface CooperationNetworkInteractor {

    fun listenCooperationData()

    fun registerCooperation(cooperation: Cooperation)

}