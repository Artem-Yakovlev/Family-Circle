package com.tydeya.familycircle.presentation.viewmodel.kitchen.foodinfridge

import androidx.lifecycle.MutableLiveData
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.domain.kitchenorganizer.foodinfridgeeventlistener.FoodInFridgeEventListener
import com.tydeya.familycircle.domain.kitchenorganizer.foodinfridgeeventlistener.FoodInFridgeEventListenerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.utils.addFoodInFridgeFirebaseProcessing
import com.tydeya.familycircle.domain.kitchenorganizer.utils.deleteFoodFromFridgeInFirebaseProcessing
import com.tydeya.familycircle.domain.kitchenorganizer.utils.eatFoodFromFridgeFirebaseProcessing
import com.tydeya.familycircle.domain.kitchenorganizer.utils.editFoodInFridgeDataFirebaseProcessing
import com.tydeya.familycircle.presentation.viewmodel.base.FirestoreViewModel
import com.tydeya.familycircle.utils.Resource
import java.math.BigDecimal

class FoodInFridgeViewModel(
        private val familyId: String
) :
        FirestoreViewModel(), FoodInFridgeEventListenerCallback {

    private val foodInFridgeEventListener = FoodInFridgeEventListener(familyId, this)

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
        deleteFoodFromFridgeInFirebaseProcessing(familyId, title)
    }

    fun deleteFromFridgeBadFood(id: String) {
        deleteFoodFromFridgeInFirebaseProcessing(familyId, id)
    }

    fun addNewFoodInFridge(food: Food) {
        addFoodInFridgeFirebaseProcessing(familyId, food)
    }

    fun editFoodInFridgeData(food: Food) {
        editFoodInFridgeDataFirebaseProcessing(familyId, food)
    }

    fun eatFoodFromFridge(eatenAmount: BigDecimal, food: Food) {
        eatFoodFromFridgeFirebaseProcessing(familyId, eatenAmount, food)
    }
}