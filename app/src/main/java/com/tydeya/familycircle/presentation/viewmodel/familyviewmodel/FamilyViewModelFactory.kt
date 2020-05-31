package com.tydeya.familycircle.presentation.viewmodel.familyviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class FamilyViewModelFactory(private val familyId: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FamilyViewModel::class.java)) {
            return FamilyViewModel(familyId) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}