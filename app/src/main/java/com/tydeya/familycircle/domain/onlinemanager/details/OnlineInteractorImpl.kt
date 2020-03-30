package com.tydeya.familycircle.domain.onlinemanager.details

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.domain.onlinemanager.abstraction.OnlineInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class OnlineInteractorImpl : OnlineInteractor {

    private val isUserOnlineMap = HashMap<String, Boolean>()

    init {
        listenOnlineUsersData()
    }

    private fun listenOnlineUsersData() {
        FirebaseFirestore.getInstance().collection(FIRESTORE_USERS_COLLECTION)
                .addSnapshotListener { querySnapshot, _ ->
                    GlobalScope.launch(Dispatchers.Main) {
                        isUserOnlineMap.clear()
                        querySnapshot.documents.forEach {

                            val lastInteractionTime = it.getDate(FIRESTORE_USERS_LAST_ONLINE)
                                    ?: Date(123213)

                            isUserOnlineMap[it.getString(FIRESTORE_USERS_PHONE_TAG)] =
                                    isUserOnlineByLastInteraction(lastInteractionTime)
                        }
                    }
                }
    }

    private fun isUserOnlineByLastInteraction(lastInteraction: Date): Boolean =
            Date().time - lastInteraction.time < 120000


    override fun registerUserActivity() {
        GlobalScope.launch(Dispatchers.Default) {
            val phoneNumber = FirebaseAuth.getInstance().currentUser!!.phoneNumber
            FirebaseFirestore.getInstance().collection(FIRESTORE_USERS_COLLECTION)
                    .whereEqualTo(FIRESTORE_USERS_PHONE_TAG, phoneNumber).get()
                    .addOnSuccessListener {
                        it.documents[0].reference
                                .update(hashMapOf(FIRESTORE_USERS_LAST_ONLINE to Date())
                                        as Map<String, Any>)
                    }
        }
    }

    override fun isUserOnline(phoneNumber: String): Boolean = isUserOnlineMap[phoneNumber] ?: false


}