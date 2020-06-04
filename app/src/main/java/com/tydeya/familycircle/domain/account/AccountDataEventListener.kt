package com.tydeya.familycircle.domain.account

import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.constants.FireStore.USERS_BIRTH_TAG
import com.tydeya.familycircle.data.constants.FireStore.USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.USERS_IMAGE_PATH
import com.tydeya.familycircle.data.constants.FireStore.USERS_NAME_TAG
import com.tydeya.familycircle.data.constants.FireStore.USERS_STUDY_TAG
import com.tydeya.familycircle.data.constants.FireStore.USERS_WORK_TAG
import com.tydeya.familycircle.data.familymember.EditableFamilyMember
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.utils.Resource

class AccountDataEventListener(
        private val userPhoneNumber: String,
        private val callback: AccountDataEventListenerCallback
) :
        EventListenerObservable, EventListener<DocumentSnapshot> {

    private val accountDataRef = FirebaseFirestore.getInstance()
            .collection(USERS_COLLECTION)
            .document(userPhoneNumber)

    private lateinit var accountDataRegistration: ListenerRegistration

    override fun register() {
        accountDataRegistration = accountDataRef.addSnapshotListener(this)
    }

    override fun unregister() {
        accountDataRegistration.remove()
    }

    override fun onEvent(documentSnapshot: DocumentSnapshot?, exception: FirebaseFirestoreException?) {
        if (exception != null) {
            callback.accountDataUpdated(Resource.Failure(exception))
        } else {
            documentSnapshot?.let {
                val accountData = EditableFamilyMember(
                        name = documentSnapshot.getString(USERS_NAME_TAG) ?: "",
                        imageAddress = documentSnapshot.getString(USERS_IMAGE_PATH) ?: "",
                        birthdate = documentSnapshot.getDate(USERS_BIRTH_TAG)?.time ?: -1,
                        workPlace = documentSnapshot.getString(USERS_WORK_TAG) ?: "",
                        studyPlace = documentSnapshot.getString(USERS_STUDY_TAG) ?: ""
                )
                callback.accountDataUpdated(Resource.Success(accountData))
            }
        }
    }
}