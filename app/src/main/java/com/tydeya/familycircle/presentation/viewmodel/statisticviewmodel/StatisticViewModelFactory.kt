package com.tydeya.familycircle.presentation.viewmodel.statisticviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tydeya.familycircle.presentation.viewmodel.tasks.TasksViewModel

@Suppress("UNCHECKED_CAST")
class StatisticViewModelFactory(private val familyId: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            return StatisticViewModel(familyId) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}