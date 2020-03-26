package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction

import com.tydeya.familycircle.data.kitchenorganizer.food.Food

interface KitchenOrganizerNetworkInteractor {

    fun requireKitchenBuyCatalogData()

    fun requireFoodInFridgeData()



    fun createProductInFirebase(id: String, title: String)

    fun editProductInFirebase(id: String, actualTitle: String, newTitle: String)

    fun deleteProductInFirebase(catalogId: String, title: String)

    fun buyProductFirebaseProcessing(catalogId: String, title: String)



    fun createBuyList(title: String)

    fun renameBuyList(catalogId: String, newTitle: String)

    fun deleteBuyList(catalogId: String)



    fun addFoodInFridge(title: String)

    fun deleteFoodFromFridge(title: String)
}