package com.tydeya.familycircle.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class BuyCatalogViewModelFactory(private val buyCatalogId: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BuyCatalogViewModel::class.java)) {
            return BuyCatalogViewModel(buyCatalogId) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}