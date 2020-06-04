package com.tydeya.familycircle.presentation.viewmodel.kitchen.buycatalogviewmodel

import androidx.lifecycle.MutableLiveData
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.domain.kitchenorganizer.buycatalogeventlistener.BuyCatalogEventListenerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.buycatalogeventlistener.KitchenBuyCatalogEventListener
import com.tydeya.familycircle.domain.kitchenorganizer.utils.*
import com.tydeya.familycircle.presentation.viewmodel.base.FirestoreViewModel
import com.tydeya.familycircle.utils.Resource

class BuyCatalogViewModel(
        val familyId: String,
        val catalogId: String
) :
        FirestoreViewModel(), BuyCatalogEventListenerCallback {

    private val buyCatalogEventListener: EventListenerObservable =
            KitchenBuyCatalogEventListener(catalogId, familyId, this)

    val products: MutableLiveData<Resource<ArrayList<Food>>> = MutableLiveData(Resource.Loading())

    init {
        buyCatalogEventListener.register()
    }

    override fun buyCatalogProductsUpdated(catalogId: String, productsResource: Resource<ArrayList<Food>>) {
        this.products.value = productsResource
    }

    fun isThereInBuysCatalogProductWithName(name: String): Boolean {
        val actualResource = products.value
        if (actualResource is Resource.Success) {
            for (product in actualResource.data) {
                if (product.title == name) {
                    return true
                }
            }
            return false
        }
        return true
    }

    fun createProduct(food: Food) {
        createProductInFirebase(familyId, catalogId, food)
        updateBuysCatalogProductsInfo(familyId, catalogId)
    }

    fun deleteProduct(productId: String) {
        deleteProductInFirebase(familyId, catalogId, productId)
        updateBuysCatalogProductsInfo(familyId, catalogId)
    }

    fun editProduct(food: Food) {
        editProductInFirebase(familyId, catalogId, food)
    }

    fun buyProduct(food: Food) {
        buyProductFirebaseProcessing(familyId, catalogId, food)
        updateBuysCatalogProductsInfo(familyId, catalogId)
    }

    override fun onCleared() {
        super.onCleared()
        buyCatalogEventListener.unregister()
    }
}