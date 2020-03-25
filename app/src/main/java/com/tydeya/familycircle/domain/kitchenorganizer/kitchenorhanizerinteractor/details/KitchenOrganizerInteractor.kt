package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details

import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenNetworkInteractorCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerObservable
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.details.KitchenOrganizerNetworkInteractorImpl
import java.util.*
import kotlin.collections.ArrayList

class KitchenOrganizerInteractor : KitchenNetworkInteractorCallback, KitchenOrganizerObservable {

    val buyCatalogs: ArrayList<BuyCatalog> = ArrayList()
    val foodsInFridge: ArrayList<Food> = ArrayList()

    private val networkInteractor: KitchenOrganizerNetworkInteractor =
            KitchenOrganizerNetworkInteractorImpl(this)

    private val observers: ArrayList<KitchenOrganizerCallback> = ArrayList()

    init {
        networkInteractor.requireKitchenBuyCatalogData()
        networkInteractor.requireFoodInFridgeData()
    }

    /**
     * Data updates
     * */

    override fun buyCatalogsAllDataUpdated(buyCatalogs: ArrayList<BuyCatalog>) {
        this.buyCatalogs.clear()
        this.buyCatalogs.addAll(buyCatalogs)
        notifyObserversConversationsDataUpdated()
    }

    override fun buyCatalogDataUpdated(id: String, products: ArrayList<Food>) {

        for (index in 0 until buyCatalogs.size) {
            if (buyCatalogs[index].id == id) {
                buyCatalogs[index].products = products
                break
            }
        }
        notifyObserversConversationsDataUpdated()
    }

    override fun foodInFridgeDataUpdate(foodInFridge: ArrayList<Food>) {
        this.foodsInFridge.clear()
        this.foodsInFridge.addAll(foodInFridge)
        notifyObserversConversationsDataUpdated()
    }

    /**
     * Catalog data
     * */

    fun requireCatalogData(id: String): BuyCatalog {
        var resultBuyCatalog = BuyCatalog(id, "...", Date(), ArrayList())

        for (buyCatalog in buyCatalogs) {
            if (buyCatalog.id == id) {
                resultBuyCatalog = buyCatalog
            }
        }

        return resultBuyCatalog
    }

    fun createBuyCatalog(title: String) {
        networkInteractor.createBuyList(title)
    }

    fun renameCatalog(id: String, newName: String) {
        networkInteractor.renameBuyList(id, newName)
    }

    fun deleteCatalog(id: String) {
        networkInteractor.deleteBuyList(id)
    }

    /**
     * Food data
     * */

    fun createProduct(catalogId: String, title: String) {
        networkInteractor.createProductInFirebase(catalogId, title)
    }

    fun editProduct(catalogId: String, actualTitle: String, newTitle: String) {
        networkInteractor.editProductInFirebase(catalogId, actualTitle, newTitle)
    }

    fun deleteProduct(catalogId: String, title: String) {
        networkInteractor.deleteProductInFirebase(catalogId, title)
    }

    fun buyProduct(catalogId: String, title: String) {
        networkInteractor.buyProductFirebaseProcessing(catalogId, title)
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