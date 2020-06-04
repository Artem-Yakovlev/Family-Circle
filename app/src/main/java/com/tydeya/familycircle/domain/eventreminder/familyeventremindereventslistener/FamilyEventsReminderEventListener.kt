package com.tydeya.familycircle.domain.eventreminder.familyeventremindereventslistener

import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.constants.FireStore
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable


class FamilyEventsReminderEventListener(
        private val callback: FamilyEventsReminderEventListenerCallback
) :
        EventListener<QuerySnapshot>, EventListenerObservable {

    private val familyEventsCollectionRef = FirebaseFirestore.getInstance()
            .collection(FireStore.KITCHEN_COLLECTION)
            .orderBy(FireStore.BUYS_CATALOG_DATE, Query.Direction.DESCENDING)

    private lateinit var registration: ListenerRegistration

    override fun register() {
        registration = familyEventsCollectionRef.addSnapshotListener(this)
    }

    override fun unregister() {
        registration.remove()
    }

    override fun onEvent(querySnapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {

    }

}