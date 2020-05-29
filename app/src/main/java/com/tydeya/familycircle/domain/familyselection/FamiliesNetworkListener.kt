package com.tydeya.familycircle.domain.familyselection

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_PHONE_TAG
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable

class FamiliesNetworkListener(
        private val callback: FamiliesNetworkListenerCallback
) :
        EventListener<QuerySnapshot>, EventListenerObservable {

    private val phoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: ""

    private val userRef = FirebaseFirestore.getInstance()
            .collection(FIRESTORE_USERS_COLLECTION)
            .whereEqualTo(FIRESTORE_USERS_PHONE_TAG, phoneNumber)

    private lateinit var registration: ListenerRegistration

    override fun register() {
        registration = userRef.addSnapshotListener(this)
    }

    override fun unregister() {
        registration.remove()
    }

    override fun onEvent(querySnapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {

    }

}
