package com.tydeya.familycircle.domain.familyinteractor.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun updateUserTokenInFirebase(phoneNumber: String, tokenFcm: String) {

    FirebaseFirestore.getInstance().collection(FIRESTORE_USERS_COLLECTION)
            .whereEqualTo(FIRESTORE_USERS_PHONE_TAG, phoneNumber).get()
            .addOnSuccessListener { querySnapshot ->
                GlobalScope.launch(Dispatchers.IO) {
                    if (querySnapshot.documents.size > 0) {
                        querySnapshot.documents[0].reference
                                .update(hashMapOf<String, Any>(FIRESTORE_USERS_FCM_TOKEN to tokenFcm))
                    }
                }
            }
}