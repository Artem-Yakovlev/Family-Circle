package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor

import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.data.kitchenorganizer.food.Food

interface KitchenNetworkInteractorCallback {

    fun buyCatalogsAllDataUpdated(buyCatalogs: ArrayList<BuyCatalog>)

    fun foodInFridgeDataUpdate(foodInFridge: ArrayList<Food>)

}