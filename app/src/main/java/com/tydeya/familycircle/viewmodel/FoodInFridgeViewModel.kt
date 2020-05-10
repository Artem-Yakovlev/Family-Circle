package com.tydeya.familycircle.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.domain.kitchenorganizer.foodinfridgeeventlistener.FoodInFridgeEventListener
import com.tydeya.familycircle.domain.kitchenorganizer.foodinfridgeeventlistener.FoodInFridgeEventListenerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.utils.addFoodInFridgeFirebaseProcessing
import com.tydeya.familycircle.domain.kitchenorganizer.utils.deleteFoodFromFridgeInFirebaseProcessing
import com.tydeya.familycircle.domain.kitchenorganizer.utils.eatFoodFromFridgeFirebaseProcessing
import com.tydeya.familycircle.domain.kitchenorganizer.utils.editFoodInFridgeDataFirebaseProcessing
import com.tydeya.familycircle.utils.Resource
import java.math.BigDecimal

class FoodInFridgeViewModel : ViewModel(), FoodInFridgeEventListenerCallback {
    private val foodInFridgeEventListener: FoodInFridgeEventListener = FoodInFridgeEventListener(this)

    val products: MutableLiveData<Resource<ArrayList<Food>>> = MutableLiveData(Resource.Loading())

    override fun foodInFridgeProductsUpdated(productsResource: Resource<ArrayList<Food>>) {
        this.products.value = productsResource
    }

    init {
        foodInFridgeEventListener.register()
    }

    override fun onCleared() {
        super.onCleared()
        foodInFridgeEventListener.unregister()
    }

    fun deleteFromFridgeEatenFood(title: String) {
        deleteFoodFromFridgeInFirebaseProcessing(title)
    }

    fun deleteFromFridgeBadFood(id: String) {
        deleteFoodFromFridgeInFirebaseProcessing(id)
    }

    fun addNewFoodInFridge(food: Food) {
        addFoodInFridgeFirebaseProcessing(food)
    }

    fun editFoodInFridgeData(food: Food) {
        editFoodInFridgeDataFirebaseProcessing(food)
    }

    fun eatFoodFromFridge(eatenAmount: BigDecimal, food: Food) {
        eatFoodFromFridgeFirebaseProcessing(eatenAmount, food)
    }
}