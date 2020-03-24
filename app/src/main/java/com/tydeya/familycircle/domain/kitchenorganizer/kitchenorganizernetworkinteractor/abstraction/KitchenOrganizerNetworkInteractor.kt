package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction

import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog

interface KitchenOrganizerNetworkInteractor {

    fun requireKitchenBuyCatalogData()

    fun setUpdateKitchenDataListener(buyCatalogs: ArrayList<BuyCatalog>)

    fun createBuyList(title: String)

    fun createProductInFirebase(id: String, title: String)

    fun editProductInFirebase(id: String, actualTitle: String, newTitle: String)

    fun deleteProductInFirebase(catalogId: String, title: String)

    fun renameBuyList(catalogId: String, newTitle: String)

    fun deleteBuyList(catalogId: String)
}