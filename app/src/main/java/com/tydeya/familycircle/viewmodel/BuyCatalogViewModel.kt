package com.tydeya.familycircle.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.domain.kitchenorganizer.buycatalogeventlistener.BuyCatalogEventListenerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.domain.kitchenorganizer.buycatalogeventlistener.KitchenBuyCatalogEventListener
import com.tydeya.familycircle.domain.kitchenorganizer.utils.createProductInFirebase
import com.tydeya.familycircle.domain.kitchenorganizer.utils.deleteProductInFirebase
import com.tydeya.familycircle.domain.kitchenorganizer.utils.editProductInFirebase
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

    fun createProduct(title: String) {
        createProductInFirebase(catalogId, title)
    }

    fun deleteProduct(title: String) {
        deleteProductInFirebase(catalogId, title)
    }

    fun editProduct(actualTitle: String, newTitle: String) {
        editProductInFirebase(catalogId, actualTitle, newTitle)
    }

    override fun onCleared() {
        super.onCleared()
        buyCatalogEventListener.unregister()
    }
}