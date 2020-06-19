package com.tydeya.familycircle.presentation.viewmodel.statisticviewmodel

import android.util.ArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.domain.statistic.StatisticEventListenerCallback
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.notifyObservers

class StatisticViewModel(
        private val familyId: String
) : ViewModel(), StatisticEventListenerCallback {

    private val kitchenStatisticsMutableLiveData =
            MutableLiveData<Resource<ArrayMap<String, ArrayMap<String, Double>>>>()
    val kitchenStatisticLiveData: LiveData<Resource<ArrayMap<String, ArrayMap<String, Double>>>>
        get() = kitchenStatisticsMutableLiveData

    private val taskStatisticsMutableLiveData =
            MutableLiveData<Resource<ArrayMap<String, ArrayMap<String, Double>>>>()
    val taskStatisticsLiveData: LiveData<Resource<ArrayMap<String, ArrayMap<String, Double>>>>
        get() = taskStatisticsMutableLiveData

    private val messengerStatisticsMutableLiveData =
            MutableLiveData<Resource<ArrayMap<String, ArrayMap<String, Double>>>>()
    val messengerStatisticsLiveData: LiveData<Resource<ArrayMap<String, ArrayMap<String, Double>>>>
        get() = messengerStatisticsMutableLiveData

    private val eventsStatisticsMutableLiveData =
            MutableLiveData<Resource<ArrayMap<String, ArrayMap<String, Double>>>>()
    val eventsStatisticsLiveData: LiveData<Resource<ArrayMap<String, ArrayMap<String, Double>>>>
        get() = eventsStatisticsMutableLiveData

    override fun kitchenStatisticUpdated(
            statistics: Resource<ArrayMap<String, ArrayMap<String, Double>>>
    ) {
        refreshData(kitchenStatisticsMutableLiveData, statistics)
    }

    override fun taskStatisticUpdated(
            statistics: Resource<ArrayMap<String, ArrayMap<String, Double>>>
    ) {
        refreshData(taskStatisticsMutableLiveData, statistics)
    }

    override fun messengerStatisticUpdated(
            statistics: Resource<ArrayMap<String, ArrayMap<String, Double>>>
    ) {
        refreshData(messengerStatisticsMutableLiveData, statistics)
    }

    override fun eventsStatisticUpdated(
            statistics: Resource<ArrayMap<String, ArrayMap<String, Double>>>
    ) {
        refreshData(eventsStatisticsMutableLiveData, statistics)
    }

    private fun refreshData(
            actualStatistic: MutableLiveData<Resource<ArrayMap<String, ArrayMap<String, Double>>>>,
            statistic: Resource<ArrayMap<String, ArrayMap<String, Double>>>
    ) {
        if (statistic is Resource.Success) {

            val actualValue = actualStatistic.value

            if (actualValue is Resource.Success) {
                for ((key, value) in statistic.data) {
                    actualValue.data[key] = value
                }
            } else {
                actualStatistic.value = statistic
            }

        } else {
            actualStatistic.value = statistic
        }

        actualStatistic.notifyObservers()
    }

    init {

    }

    override fun onCleared() {
        super.onCleared()
    }
}