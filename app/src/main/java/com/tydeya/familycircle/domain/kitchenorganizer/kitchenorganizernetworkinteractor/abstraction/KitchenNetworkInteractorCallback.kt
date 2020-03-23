package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction

import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog

interface KitchenNetworkInteractorCallback {

    fun buyCatalogsAllDataUpdated(buyCatalogs: ArrayList<BuyCatalog>)

    fun buyCatalogDataUpdated(buyCatalog: BuyCatalog)
}