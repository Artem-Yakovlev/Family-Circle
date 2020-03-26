package com.tydeya.familycircle.domain.familyinteractor.abstraction

import android.util.ArrayMap
import com.tydeya.familycircle.data.onlinetracker.OnlineTrackerActivity

interface FamilyOnlineTracker {

    fun getOnlineUsersNumber(): Int

    fun getOnlineUsersArrayMap(): ArrayMap<String, Boolean>

    fun isUserOnlineByPhone(phoneNumber: String): Boolean

    fun isUsersOnlineDataUpdate(isUserOnlineMap: ArrayMap<String, Boolean>)

    fun userOpenActivity(activity: OnlineTrackerActivity)

    fun userCloseActivity(activity: OnlineTrackerActivity)
}