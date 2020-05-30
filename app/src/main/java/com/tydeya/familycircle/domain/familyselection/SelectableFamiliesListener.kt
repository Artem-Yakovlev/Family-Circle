package com.tydeya.familycircle.domain.familyselection

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.constants.FireStore
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_PHONE_TAG
import com.tydeya.familycircle.data.family.FamilyDTO
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            GlobalScope.launch(Dispatchers.Default) {
                querySnapshot?.let { snapshot ->
                    snapshot.documents[0]?.let {

                        val selectableFamilies = Resource.Success(parseToFamilyDTO(
                                titles = it.getListByTag(FireStore.FIRESTORE_USERS_FAMILY_TITLES),
                                sizes = it.getListByTag(FireStore.FIRESTORE_USERS_FAMILY_SIZES),
                                ids = it.getListByTag(FireStore.FIRESTORE_USERS_FAMILY_IDS))
                        )

                        withContext(Dispatchers.Main) {
                            callback.selectableFamiliesUpdated(selectableFamilies)
                        }
                    }
                }
            }
        } else {
            callback.selectableFamiliesUpdated(Resource.Failure(exception))
        }
    }

    private fun parseToFamilyDTO(titles: List<String>,
                                 sizes: List<Int>,
                                 ids: List<String>
    ):
            List<FamilyDTO> {

        return ArrayList<FamilyDTO>().apply {
            for (i in ids.indices) {
                add(FamilyDTO(ids[i], titles[i], sizes[i]))
            }
        }
    }

}
