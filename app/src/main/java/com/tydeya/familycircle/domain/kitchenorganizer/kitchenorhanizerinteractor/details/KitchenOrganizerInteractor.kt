package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details

import android.util.Log
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenNetworkInteractorCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.details.KitchenOrganizerNetworkInteractorImpl
import java.util.*
import kotlin.collections.ArrayList

class KitchenOrganizerInteractor: KitchenNetworkInteractorCallback {

    val buyCatalogs: MutableList<BuyCatalog> = ArrayList()

    val networkInteractor: KitchenOrganizerNetworkInteractor =
            KitchenOrganizerNetworkInteractorImpl(this)

    init {
        val birthdayCatalog = ArrayList<Food>()
        birthdayCatalog
                .add(Food("Банан", "...", FoodStatus.IN_FRIDGE, .0, .0, .0))
        birthdayCatalog
                .add(Food("Яблоко", "...", FoodStatus.NEED_BUY, .0, .0, .0))
        birthdayCatalog
                .add(Food("Газировка", "...", FoodStatus.NEED_BUY, .0, .0, .0))

        val cheeseCatalog = ArrayList<Food>()
        cheeseCatalog
                .add(Food("Сыр", "...", FoodStatus.IN_FRIDGE, .0, .0, .0))

        buyCatalogs.add(BuyCatalog("", "Birthday", Date(), birthdayCatalog))
        buyCatalogs.add(BuyCatalog("", "Cheese", Date(), cheeseCatalog))

        networkInteractor.requireKitchenBuyCatalogData()
    }

    override fun buyCatalogsDataUpdated(buyCatalogs: List<BuyCatalog>) {
        Log.d("ASMR", "Pam-param")
    }

}