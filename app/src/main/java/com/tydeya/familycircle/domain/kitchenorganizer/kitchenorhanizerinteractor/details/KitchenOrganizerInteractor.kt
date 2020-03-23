package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details

import android.util.Log
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenNetworkInteractorCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerObservable
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.details.KitchenOrganizerNetworkInteractorImpl

class KitchenOrganizerInteractor : KitchenNetworkInteractorCallback, KitchenOrganizerObservable {

    val buyCatalogs: ArrayList<BuyCatalog> = ArrayList()

    private val networkInteractor: KitchenOrganizerNetworkInteractor =
            KitchenOrganizerNetworkInteractorImpl(this)

    private val observers: ArrayList<KitchenOrganizerCallback> = ArrayList()

    init {
        networkInteractor.requireKitchenBuyCatalogData()
    }

    override fun buyCatalogsAllDataUpdated(buyCatalogs: ArrayList<BuyCatalog>) {
        this.buyCatalogs.clear()
        this.buyCatalogs.addAll(buyCatalogs)
        notifyObserversConversationsDataUpdated()
        networkInteractor.setUpdateKitchenDataListener(buyCatalogs)
    }

    override fun buyCatalogDataUpdated(buyCatalog: BuyCatalog) {
        for (index in 0 until buyCatalogs.size) {
            if (buyCatalogs[index].id == buyCatalog.id) {
                buyCatalogs[index] = buyCatalog
                break
            }
        }
        notifyObserversConversationsDataUpdated()
    }

    fun createBuyCatalog(title: String) {
        networkInteractor.createBuyList(title)
    }


    /**
     * Callbacks
     * */

    private fun notifyObserversConversationsDataUpdated() {
        for (callback in observers) {
            callback.kitchenDataFromServerUpdated()
        }
    }

    override fun subscribe(callback: KitchenOrganizerCallback) {
        if (!observers.contains(callback)) {
            observers.add(callback)
            callback.kitchenDataFromServerUpdated()
        }
    }

    override fun unsubscribe(callback: KitchenOrganizerCallback) {
        observers.remove(callback)
    }

}