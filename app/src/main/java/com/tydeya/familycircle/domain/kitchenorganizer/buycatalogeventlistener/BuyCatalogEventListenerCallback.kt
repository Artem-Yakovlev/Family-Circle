package com.tydeya.familycircle.domain.kitchenorganizer.buycatalogeventlistener

import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.utils.Resource

interface BuyCatalogEventListenerCallback {

    fun buyCatalogProductsUpdated(catalogId: String, productsResource: Resource<ArrayList<Food>>)

}