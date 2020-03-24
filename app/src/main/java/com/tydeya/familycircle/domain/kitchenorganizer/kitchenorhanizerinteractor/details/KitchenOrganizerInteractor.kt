package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details

import android.util.Log
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenNetworkInteractorCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerObservable
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.details.KitchenOrganizerNetworkInteractorImpl
import java.util.*
import kotlin.collections.ArrayList

class KitchenOrganizerInteractor : KitchenNetworkInteractorCallback, KitchenOrganizerObservable {

    val buyCatalogs: ArrayList<BuyCatalog> = ArrayList()

    private val networkInteractor: KitchenOrganizerNetworkInteractor =
            KitchenOrganizerNetworkInteractorImpl(this)

    private val observers: ArrayList<KitchenOrganizerCallback> = ArrayList()

    init {
        networkInteractor.requireKitchenBuyCatalogData()
    }

    /**
     * Data updates
     * */

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
     * Catalog data
     * */

    fun requireCatalogData(id: String, withListener: Boolean): BuyCatalog {
        var resultBuyCatalog = BuyCatalog(id, "...", Date(), ArrayList())

        for (buyCatalog in buyCatalogs) {
            if (buyCatalog.id == id) {
                if (withListener) {
                    networkInteractor.setUpdateCatalogDataListener(buyCatalog)
                }
                resultBuyCatalog = buyCatalog
            }
        }

        return resultBuyCatalog
    }

    fun stopListenCatalogData(id: String) {
        networkInteractor.removeUpdateCatalogDataListener(id)
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