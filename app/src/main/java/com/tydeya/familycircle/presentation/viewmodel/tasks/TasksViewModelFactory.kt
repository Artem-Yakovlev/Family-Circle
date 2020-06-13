package com.tydeya.familycircle.presentation.viewmodel.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


@Suppress("UNCHECKED_CAST")
class TasksViewModelFactory(private val familyId: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            return TasksViewModel(familyId) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}