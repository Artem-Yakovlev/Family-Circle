package com.tydeya.familycircle.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.domain.kitchenorganizer.buycatalogeventlistener.BuyCatalogEventListenerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.buycatalogeventlistener.KitchenBuyCatalogEventListener
import com.tydeya.familycircle.domain.kitchenorganizer.utils.*
import com.tydeya.familycircle.utils.Resource

class BuyCatalogViewModel(val catalogId: String) : ViewModel(), BuyCatalogEventListenerCallback {

    private val buyCatalogEventListener: EventListenerObservable =
            KitchenBuyCatalogEventListener(catalogId, this)

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
        createProductInFirebase(catalogId, food)
        updateBuysCatalogProductsInfo(catalogId)
    }

    fun deleteProduct(productId: String) {
        deleteProductInFirebase(catalogId, productId)
        updateBuysCatalogProductsInfo(catalogId)
    }

    fun editProduct(food: Food) {
        editProductInFirebase(catalogId, food)
    }

    fun buyProduct(food: Food) {
        buyProductFirebaseProcessing(catalogId, food)
        updateBuysCatalogProductsInfo(catalogId)
    }

    override fun onCleared() {
        super.onCleared()
        buyCatalogEventListener.unregister()
    }
}