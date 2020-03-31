package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details

import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.App
import com.tydeya.familycircle.data.cooperation.Cooperation
import com.tydeya.familycircle.data.cooperation.CooperationType
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.data.kitchenorganizer.kitchendatastatus.KitchenDataStatus
import com.tydeya.familycircle.domain.cooperationorganizer.interactor.details.CooperationInteractor
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenNetworkInteractorCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.details.KitchenOrganizerNetworkInteractorImpl
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.abstraction.KitchenOrganizerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.abstraction.KitchenOrganizerObservable
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class KitchenOrganizerInteractor : KitchenNetworkInteractorCallback, KitchenOrganizerObservable {

    val buyCatalogs: ArrayList<BuyCatalog> = ArrayList()
    var buyCatalogsStatus = KitchenDataStatus.DATA_PENDING

    val foodsInFridge: ArrayList<Food> = ArrayList()
    var foodsInFridgeStatus = KitchenDataStatus.DATA_PENDING

    private val networkInteractor: KitchenOrganizerNetworkInteractor =
            KitchenOrganizerNetworkInteractorImpl(this)

    private val observers: ArrayList<KitchenOrganizerCallback> = ArrayList()

    @Inject
    lateinit var cooperationInteractor: CooperationInteractor

    init {
        App.getComponent().injectInteractor(this)
        networkInteractor.requireKitchenBuyCatalogData()
        networkInteractor.requireFoodInFridgeData()
    }

    /**
     * Utils
     * */

    private fun getCatalogById(id: String): BuyCatalog? {
        buyCatalogs.forEach {
            if (it.id == id) {
                return it
            }
        }
        return null
    }

    /**
     * Buy catalog updates
     * */

    override fun buyCatalogsAllDataUpdated(buyCatalogs: ArrayList<BuyCatalog>) {
        this.buyCatalogs.clear()
        this.buyCatalogs.addAll(buyCatalogs)
        buyCatalogsStatus = KitchenDataStatus.DATA_RECEIVED
        notifyObserversKitchenDataUpdated()
    }

    override fun buyCatalogDataUpdated(id: String, products: ArrayList<Food>) {
        for (index in 0 until buyCatalogs.size) {
            if (buyCatalogs[index].id == id) {
                buyCatalogs[index].products = products
                break
            }
        }
        notifyObserversKitchenDataUpdated()
    }

    /**
     * Fridge updates
     * */

    override fun foodInFridgeDataUpdate(foodInFridge: ArrayList<Food>) {
        this.foodsInFridge.clear()
        this.foodsInFridge.addAll(foodInFridge)
        foodsInFridgeStatus = KitchenDataStatus.DATA_RECEIVED
        notifyObserversKitchenDataUpdated()
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
        buyCatalogs.add(0, BuyCatalog("", title, Date(), ArrayList()))
        notifyObserversKitchenDataUpdated()
        networkInteractor.createBuyList(title)
    }

    fun renameCatalog(id: String, newTitle: String) {
        getCatalogById(id)?.title = newTitle
        notifyObserversKitchenDataUpdated()
        networkInteractor.renameBuyList(id, newTitle)
    }

    fun deleteCatalog(id: String) {
        getCatalogById(id).let {
            buyCatalogs.remove(it)
        }
        notifyObserversKitchenDataUpdated()
        networkInteractor.deleteBuyList(id)
    }

    /**
     * Food in catalog data
     * */

    fun createProductInCatalog(catalogId: String, title: String) {
        getCatalogById(catalogId)?.let {
            val food = Food(title, "", FoodStatus.NEED_BUY, .0, .0, .0)
            it.products.add(food)
            sortCatalog(catalogId)
            notifyObserversKitchenDataUpdated()
        }
        networkInteractor.createProductInFirebase(catalogId, title)
    }

    fun editProductInCatalog(catalogId: String, actualTitle: String, newTitle: String) {
        getCatalogById(catalogId)?.let {
            for (food in it.products) {
                if (food.title == actualTitle) {
                    food.title = newTitle
                    break
                }
            }
            sortCatalog(catalogId)
            notifyObserversKitchenDataUpdated()
        }
        networkInteractor.editProductInFirebase(catalogId, actualTitle, newTitle)
    }

    fun deleteProductInCatalog(catalogId: String, title: String) {
        val catalog = getCatalogById(catalogId)
        catalog?.let {
            for (food in catalog.products) {
                if (food.title == title) {
                    catalog.products.remove(food)
                    break
                }
            }
            notifyObserversKitchenDataUpdated()
        }
        networkInteractor.deleteProductInFirebase(catalogId, title)
    }

    fun buyProduct(catalogId: String, title: String) {
        networkInteractor.buyProductFirebaseProcessing(catalogId, title)

        cooperationInteractor.registerCooperation(
                Cooperation("", FirebaseAuth.getInstance().currentUser!!.phoneNumber!!,
                        title, CooperationType.ADD_PRODUCT, Date()))
    }

    private fun sortCatalog(catalogId: String) {
        val catalog = getCatalogById(catalogId) ?: return
        catalog.products.sortWith(kotlin.Comparator { o1, o2 -> -compareValues(o1.title, o2.title) })
    }

    /**
     * Food in fridge data
     * */

    fun addNewFoodInFridge(title: String) {
        foodsInFridge.add(Food(title, "", FoodStatus.IN_FRIDGE, .0, .0, .0))
        foodsInFridge.sortWith(kotlin.Comparator { o1, o2 -> -compareValues(o1.title, o2.title) })
        networkInteractor.addFoodInFridge(title)
    }

    fun deleteFromFridgeEatenFood(title: String) {
        deleteFoodFromFridge(title)
        cooperationInteractor.registerCooperation(
                Cooperation("", FirebaseAuth.getInstance().currentUser!!.phoneNumber!!,
                        title, CooperationType.EAT_PRODUCT, Date()))
    }

    fun deleteFromFridgeBadFood(title: String) {
        deleteFoodFromFridge(title)
        cooperationInteractor.registerCooperation(
                Cooperation("", FirebaseAuth.getInstance().currentUser!!.phoneNumber!!,
                        title, CooperationType.DROP_PRODUCT, Date()))
    }

    private fun deleteFoodFromFridge(title: String) {
        for (food in foodsInFridge) {
            if (food.title == title) {
                foodsInFridge.remove(food)
                break
            }
        }
        notifyObserversKitchenDataUpdated()
        networkInteractor.deleteFoodFromFridge(title)

    }

    /**
     * Callbacks
     * */

    private fun notifyObserversKitchenDataUpdated() {
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