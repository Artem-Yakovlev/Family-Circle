package com.tydeya.familycircle.domain.familyselection

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_ICONS
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_IDS
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_SIZES
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_TITLES
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_PHONE_TAG
import com.tydeya.familycircle.data.family.FamilyDTO
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.utils.Resource

class SelectableFamiliesListener(
        private val callback: SelectableFamilyListenerCallback
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
        if (exception == null) {
            querySnapshot?.let { snapshot ->
                snapshot.documents[0]?.let {
                    callback.selectableFamiliesUpdated(Resource.Success(parseToFamilyDTO(
                            titles = it.getListByTag(FIRESTORE_USERS_FAMILY_TITLES),
                            icons = it.getListByTag(FIRESTORE_USERS_FAMILY_ICONS),
                            sizes = it.getListByTag(FIRESTORE_USERS_FAMILY_SIZES),
                            ids = it.getListByTag(FIRESTORE_USERS_FAMILY_IDS))
                    ))
                }
            }
        } else {
            callback.selectableFamiliesUpdated(Resource.Failure(exception))
        }
    }

    private fun parseToFamilyDTO(titles: List<String>,
                                 icons: List<Int>,
                                 sizes: List<Int>,
                                 ids: List<String>
    ):
            List<FamilyDTO> {

        return ArrayList<FamilyDTO>().apply {
            for (i in ids.indices) {
                add(FamilyDTO(
                        ids[i],
                        titles[i],
                        sizes[i])
                )
            }
        }
    }

}
