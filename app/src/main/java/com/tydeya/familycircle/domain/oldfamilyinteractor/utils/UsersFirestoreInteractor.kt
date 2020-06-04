package com.tydeya.familycircle.domain.oldfamilyinteractor.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore.USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.USERS_FCM_TOKEN

fun updateUserTokenInFirebase(phoneNumber: String, tokenFcm: String) {
    FirebaseFirestore.getInstance().collection(USERS_COLLECTION)
            .document(phoneNumber)
            .update(hashMapOf<String, Any>(USERS_FCM_TOKEN to tokenFcm))
}