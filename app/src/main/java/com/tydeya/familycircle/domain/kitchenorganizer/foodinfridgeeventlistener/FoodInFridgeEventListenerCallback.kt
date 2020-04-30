package com.tydeya.familycircle.domain.kitchenorganizer.foodinfridgeeventlistener

import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.utils.Resource

interface FoodInFridgeEventListenerCallback {

    fun foodInFridgeProductsUpdated(productsResource: Resource<ArrayList<Food>>)
}