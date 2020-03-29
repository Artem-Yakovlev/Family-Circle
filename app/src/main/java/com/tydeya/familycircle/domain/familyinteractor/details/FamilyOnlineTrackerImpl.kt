package com.tydeya.familycircle.domain.familyinteractor.details

import android.util.ArrayMap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.data.onlinetracker.OnlineTrackerActivity
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyOnlineTracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FamilyOnlineTrackerImpl : FamilyOnlineTracker {

    private val usersIsOnlineMap: ArrayMap<String, Boolean> = ArrayMap()

    private var onlineTrackerActivities = booleanArrayOf(false, false, false)

    private var isUserOnline = false

    override fun getOnlineUsersNumber(): Int {
        var onlineUsersNumber = 0
        usersIsOnlineMap.keys.forEach {
            if (usersIsOnlineMap[it] == true) {
                onlineUsersNumber++
            }
        }
        return onlineUsersNumber
    }

    override fun getOnlineUsersArrayMap(): ArrayMap<String, Boolean> = usersIsOnlineMap

    override fun isUserOnlineByPhone(phoneNumber: String) = usersIsOnlineMap[phoneNumber] == true

    override fun isUsersOnlineDataUpdate(isUserOnlineMap: ArrayMap<String, Boolean>) {
        this.usersIsOnlineMap.clear()
        this.usersIsOnlineMap.putAll(isUserOnlineMap)
    }

    override fun userOpenActivity(activity: OnlineTrackerActivity) {
        onlineTrackerActivities[activity.ordinal] = true
        if (!isUserOnline) {
            isUserOnline = true
            GlobalScope.launch(Dispatchers.Default) {
                setUserOnlineStatus(true)
            }
        }
    }

    override fun userCloseActivity(activity: OnlineTrackerActivity) {
        onlineTrackerActivities[activity.ordinal] = false
        changeUserOnlineStatus()
    }

    private fun changeUserOnlineStatus() {
        GlobalScope.launch(Dispatchers.Default) {
            delay(2000)
            if (!onlineTrackerActivities.contains(true)) {
                isUserOnline = false
                setUserOnlineStatus(false)
            }
        }
    }

    private fun setUserOnlineStatus(isOnline: Boolean) {
        FirebaseFirestore.getInstance()
                .collection(FIRESTORE_USERS_COLLECTION)
                .whereEqualTo(FIRESTORE_USERS_PHONE_TAG,
                        FirebaseAuth.getInstance().currentUser!!.phoneNumber)
                .get().addOnSuccessListener { querySnapshot ->
                    run {
                        querySnapshot.documents[0]
                                .reference.update(FIRESTORE_USERS_ONLINE_TAG, isOnline)
                    }
                }
    }
}