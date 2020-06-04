package com.tydeya.familycircle.presentation.viewmodel.kitchen.allcatalogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class AllBuyCatalogsViewModelFactory(
        private val familyId: String
) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllBuyCatalogsViewModel::class.java)) {
            return AllBuyCatalogsViewModel(familyId) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}