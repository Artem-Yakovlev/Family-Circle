package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction

import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.data.kitchenorganizer.food.Food

interface KitchenNetworkInteractorCallback {

    fun buyCatalogsAllDataUpdated(buyCatalogs: ArrayList<BuyCatalog>)

    fun buyCatalogDataUpdated(id: String, products: ArrayList<Food>)
}