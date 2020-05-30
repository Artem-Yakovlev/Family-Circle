package com.tydeya.familycircle.data.authentication.accountsync

import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_IDS
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_PHONE_TAG
import com.tydeya.familycircle.domain.familyselection.getListByTag

class AccountSyncTool(
        private val callbacks: AccountExistingCheckUpCallback
) {

    fun isAccountWithPhoneExist(fullPhoneNumber: String) {

        FirebaseFirestore.getInstance()
                .collection(FIRESTORE_USERS_COLLECTION)
                .whereEqualTo(FIRESTORE_USERS_PHONE_TAG, fullPhoneNumber)
                .get()
                .addOnSuccessListener {
                    if (it.documents.size == 0) {
                        callbacks.accountIsNotExist()
                    } else {

                        callbacks.accountIsExist(
                                it.documents[0].id,
                                it.documents[0].getListByTag(FIRESTORE_USERS_FAMILY_IDS)
                        )
                    }
                }
    }
}
