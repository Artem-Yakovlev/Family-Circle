package com.tydeya.familycircle.domain.firebaseinstanceservice

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tydeya.familycircle.data.constants.CLOUD_MESSAGING_KITCHEN_FRIDGE_DATA_TAG
import com.tydeya.familycircle.domain.familyinteractor.utils.updateUserTokenInFirebase


class FamilyCircleFirebaseInstanceService : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            updateToken(it.token)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val fridgeDataJSON = remoteMessage.data[CLOUD_MESSAGING_KITCHEN_FRIDGE_DATA_TAG] ?: ""

        val moshi = Moshi.Builder().build()
        val map = Types.newParameterizedType(MutableMap::class.java, String::class.java, ProductFromMessage::class.java)
        val jsonAdapter: JsonAdapter<Map<String, ProductFromMessage>> = moshi.adapter(map)
        val productFromMessageMap: Map<String, ProductFromMessage> = jsonAdapter.fromJson(fridgeDataJSON)!!
    }

    data class ProductFromMessage(
            @field:Json(name = "title")
            val title: String,

            @field:Json(name = "timestamp")
            val timestamp: Long
    )

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        updateToken(token)
    }

    private fun updateToken(token: String) {
        val phoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber
        phoneNumber?.let {
            updateUserTokenInFirebase(phoneNumber, token)
        }
    }

}