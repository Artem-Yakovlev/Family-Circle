package com.tydeya.familycircle.presentation.viewmodel.kitchen.foodinfridge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class FoodInFridgeViewModelFactory(
        private val familyId: String
) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodInFridgeViewModel::class.java)) {
            return FoodInFridgeViewModel(familyId) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}