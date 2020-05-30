package com.tydeya.familycircle.domain.oldfamilyinteractor.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FCM_TOKEN

fun updateUserTokenInFirebase(phoneNumber: String, tokenFcm: String) {
    FirebaseFirestore.getInstance().collection(FIRESTORE_USERS_COLLECTION)
            .document(phoneNumber)
            .update(hashMapOf<String, Any>(FIRESTORE_USERS_FCM_TOKEN to tokenFcm))
}