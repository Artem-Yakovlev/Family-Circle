package com.tydeya.familycircle.domain.cooperationorganizer.interactor.details

import com.tydeya.familycircle.data.cooperation.Cooperation
import com.tydeya.familycircle.domain.cooperationorganizer.interactor.abstraction.CooperationInteractorCallback
import com.tydeya.familycircle.domain.cooperationorganizer.interactor.abstraction.CooperationInteractorObservable
import com.tydeya.familycircle.domain.cooperationorganizer.networkinteractor.abstraction.CooperationNetworkInteractor
import com.tydeya.familycircle.domain.cooperationorganizer.networkinteractor.abstraction.CooperationNetworkInteractorCallback
import com.tydeya.familycircle.domain.cooperationorganizer.networkinteractor.details.CooperationNetworkInteractorImpl

class CooperationInteractor : CooperationNetworkInteractorCallback, CooperationInteractorObservable {

    private val observers: ArrayList<CooperationInteractorCallback> = ArrayList()

    var cooperationData =  ArrayList<Cooperation>()

    private val networkInteractor: CooperationNetworkInteractor =
            CooperationNetworkInteractorImpl(this)

    init {
        networkInteractor.listenCooperationData()
    }

    override fun cooperationDataFromServerUpdate(cooperationData: ArrayList<Cooperation>) {
        this.cooperationData = cooperationData
        notifyObserversKitchenDataUpdated()
    }

    /**
     * Callbacks
     * */

    private fun notifyObserversKitchenDataUpdated() {
        for (callback in observers) {
            callback.cooperationDataFromServerUpdated()
        }
    }

    override fun subscribe(callback: CooperationInteractorCallback) {
        if (!observers.contains(callback)) {
            observers.add(callback)
            callback.cooperationDataFromServerUpdated()
        }
    }

    override fun unsubscribe(callback: CooperationInteractorCallback) {
        observers.remove(callback)
    }


}