package com.tydeya.familycircle.domain.account

import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore.USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.USERS_FAMILY_IDS
import com.tydeya.familycircle.domain.familyselection.getListByTag

class AccountSyncTool(
        private val callbacks: AccountExistingCheckUpCallback
) {

    fun isAccountWithPhoneExist(fullPhoneNumber: String) {
        FirebaseFirestore.getInstance()
                .collection(USERS_COLLECTION)
                .document(fullPhoneNumber)
                .get()
                .addOnSuccessListener {
                    callbacks.accountIsExist(it.id, it.getListByTag(USERS_FAMILY_IDS))
                }.addOnFailureListener {
                    callbacks.accountIsNotExist()
                }
    }
}
