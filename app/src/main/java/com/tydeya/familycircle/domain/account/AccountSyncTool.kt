package com.tydeya.familycircle.domain.account

import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore.USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.USERS_FAMILY_IDS
import com.tydeya.familycircle.data.constants.FireStore.USERS_PHONE_TAG
import com.tydeya.familycircle.domain.familyselection.getListByTag

class AccountSyncTool(
        private val callbacks: AccountExistingCheckUpCallback
) {

    fun isAccountWithPhoneExist(fullPhoneNumber: String) {
        FirebaseFirestore.getInstance()
                .collection(USERS_COLLECTION)
                .whereEqualTo(USERS_PHONE_TAG, fullPhoneNumber)
                .get()
                .addOnSuccessListener {
                    if (it.documents.isNotEmpty()) {
                        callbacks.accountIsExist(it.documents[0].id, it.documents[0].getListByTag(USERS_FAMILY_IDS))
                    } else {
                        callbacks.accountIsNotExist()
                    }
                }.addOnFailureListener {
                    callbacks.accountIsNotExist()
                }
    }
}
