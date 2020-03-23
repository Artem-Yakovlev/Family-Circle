package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction

import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog

interface KitchenOrganizerNetworkInteractor {

    fun requireKitchenBuyCatalogData()

    fun setUpdateKitchenDataListener(buyCatalogs: ArrayList<BuyCatalog>)

    fun createBuyList(title: String)
}