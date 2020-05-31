package com.tydeya.familycircle.domain.familyselection

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_IDS
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_INVITES
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_SIZES
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_TITLES
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
        EventListener<DocumentSnapshot>, EventListenerObservable {

    private val phoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber!!

    private val userRef = FirebaseFirestore.getInstance()
            .collection(FIRESTORE_USERS_COLLECTION).document(phoneNumber)

    private lateinit var registration: ListenerRegistration

    override fun register() {
        registration = userRef.addSnapshotListener(this)
    }

    override fun unregister() {
        registration.remove()
    }

    override fun onEvent(document: DocumentSnapshot?, exception: FirebaseFirestoreException?) {
        if (exception == null) {
            GlobalScope.launch(Dispatchers.Default) {
                document?.let {

                    val selectableFamilies = Resource.Success(parseToFamilyDTO(
                            titles = it.getListByTag(FIRESTORE_USERS_FAMILY_TITLES),
                            sizes = it.getListByTag(FIRESTORE_USERS_FAMILY_SIZES),
                            ids = it.getListByTag(FIRESTORE_USERS_FAMILY_IDS))
                    )

                    val inviteCodes = Resource.Success(
                            it.getListByTag<String>(FIRESTORE_USERS_FAMILY_INVITES)
                    )

                    withContext(Dispatchers.Main) {
                        callback.selectableFamiliesUpdated(selectableFamilies, inviteCodes)
                    }
                }
            }
        } else {
            callback.selectableFamiliesUpdated(Resource.Failure(exception), Resource.Failure(exception))
        }
    }

    private fun parseToFamilyDTO(titles: List<String>, sizes: List<Int>, ids: List<String>
    ):
            List<FamilyDTO> {

        return ArrayList<FamilyDTO>().apply {
            for (i in ids.indices) {
                add(FamilyDTO(ids[i], titles[i], sizes[i]))
            }
        }
    }

}
