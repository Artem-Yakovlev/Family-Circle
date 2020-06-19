package com.tydeya.familycircle.domain.statistic

import android.util.ArrayMap
import com.tydeya.familycircle.utils.Resource

interface StatisticEventListenerCallback {

    fun kitchenStatisticUpdated(statistics: Resource<ArrayMap<String, ArrayMap<String, Double>>>)

    fun taskStatisticUpdated(statistics: Resource<ArrayMap<String, ArrayMap<String, Double>>>)

    fun messengerStatisticUpdated(statistics: Resource<ArrayMap<String, ArrayMap<String, Double>>>)

    fun eventsStatisticUpdated(statistics: Resource<ArrayMap<String, ArrayMap<String, Double>>>)

}