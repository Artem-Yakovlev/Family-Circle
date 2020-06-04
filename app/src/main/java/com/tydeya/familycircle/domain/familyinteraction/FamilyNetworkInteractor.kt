package com.tydeya.familycircle.domain.familyinteraction

import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.constants.FireStore.FAMILY_AUTHOR_PHONE_TAG
import com.tydeya.familycircle.data.constants.FireStore.FAMILY_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FAMILY_TITLE_TAG
import com.tydeya.familycircle.data.constants.FireStore.USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.USERS_FAMILY_IDS
import com.tydeya.familycircle.data.family.Family
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.utils.Resource

class FamilyNetworkInteractor(
        private val familyId: String,
        private val callback: FamilyNetworkInteractorCallback
) :
        EventListenerObservable {

    private val familyDataRef = FirebaseFirestore.getInstance()
            .collection(FAMILY_COLLECTION)
            .document(familyId)

    private lateinit var familyDataRegistration: ListenerRegistration

    private val familyMembersRef = FirebaseFirestore.getInstance()
            .collection(USERS_COLLECTION)
            .whereArrayContains(USERS_FAMILY_IDS, familyId)

    private lateinit var familyMembersRegistration: ListenerRegistration

    override fun register() {
        familyDataRegistration = familyDataRef.addSnapshotListener(familyDataListener)
        familyMembersRegistration = familyMembersRef.addSnapshotListener(familyMembersListener)
    }

    override fun unregister() {
        familyDataRegistration.remove()
        familyMembersRegistration.remove()
    }

    private val familyDataListener = EventListener<DocumentSnapshot> { document, exception ->
        if (exception == null) {
            document?.let {

                callback.familyDataUpdated(Resource.Success(Family(
                        id = it.id,
                        title = it.getString(FAMILY_TITLE_TAG) ?: "",
                        author = it.getString(FAMILY_AUTHOR_PHONE_TAG) ?: ""
                )))
            }
        } else {
            callback.familyDataUpdated(Resource.Failure(exception))
        }
    }

    private val familyMembersListener = EventListener<QuerySnapshot> { snapshot, exception ->
        if (exception == null) {
            snapshot?.let { querySnapshot ->
                callback.familyMembersUpdated(
                        Resource.Success(querySnapshot.documents
                                .map(::convertFirestoreDataToFamilyMember)
                                .toCollection(ArrayList())
                        ))
            }
        } else {
            callback.familyMembersUpdated(Resource.Failure(exception))
        }
    }
}