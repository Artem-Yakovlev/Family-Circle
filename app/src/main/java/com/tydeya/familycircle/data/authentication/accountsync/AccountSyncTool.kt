package com.tydeya.familycircle.data.authentication.accountsync

import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_IDS
import com.tydeya.familycircle.domain.familyselection.getListByTag

class AccountSyncTool(
        private val callbacks: AccountExistingCheckUpCallback
) {

    fun isAccountWithPhoneExist(fullPhoneNumber: String) {
        FirebaseFirestore.getInstance()
                .collection(FIRESTORE_USERS_COLLECTION)
                .document(fullPhoneNumber)
                .get()
                .addOnSuccessListener {
                    callbacks.accountIsExist(it.id, it.getListByTag(FIRESTORE_USERS_FAMILY_IDS))
                }.addOnFailureListener {
                    callbacks.accountIsNotExist()
                }
    }
}
