package com.tydeya.familycircle.presentation.viewmodel.kitchen.barcodescanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class BarcodeScannerViewModelFactory(
        private val familyId: String
) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BarcodeScannerViewModel::class.java)) {
            return BarcodeScannerViewModel(familyId) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}