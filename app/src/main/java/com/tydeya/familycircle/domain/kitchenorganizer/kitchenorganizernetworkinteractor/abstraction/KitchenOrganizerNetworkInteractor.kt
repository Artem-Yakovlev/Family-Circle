package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction

interface KitchenOrganizerNetworkInteractor {

    fun requireKitchenBuyCatalogData()

    fun requireFoodInFridgeData()

    fun createBuyList(title: String)

    fun createProductInFirebase(id: String, title: String)

    fun editProductInFirebase(id: String, actualTitle: String, newTitle: String)

    fun deleteProductInFirebase(catalogId: String, title: String)

    fun buyProductFirebaseProcessing(catalogId: String, title: String)

    fun renameBuyList(catalogId: String, newTitle: String)

    fun deleteBuyList(catalogId: String)
}