package com.tydeya.familycircle.domain.familyselection

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_FAMILY_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_IDS
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_SIZES
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_TITLES
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_PHONE_TAG

fun createFamilyInFirebase(title: String) {

    val firebaseFirestore = FirebaseFirestore.getInstance()
    val authorPhone = FirebaseAuth.getInstance().currentUser?.phoneNumber!!

    firebaseFirestore.collection(FIRESTORE_FAMILY_COLLECTION)
            .add(createNewFamilyFirestoreData(title, authorPhone))
            .addOnSuccessListener { familyDocument ->

                firebaseFirestore.collection(FIRESTORE_USERS_COLLECTION)
                        .document(authorPhone).get()
                        .addOnSuccessListener {
                            it?.let { document ->

                                val titles = document
                                        .getListByTag<String>(FIRESTORE_USERS_FAMILY_TITLES)
                                        .apply {
                                            add(title)
                                        }
                                val ids = document
                                        .getListByTag<String>(FIRESTORE_USERS_FAMILY_IDS)
                                        .apply {
                                            add(familyDocument.id)
                                        }
                                val numberOfUsers = document
                                        .getListByTag<Int>(FIRESTORE_USERS_FAMILY_SIZES)
                                        .apply {
                                            add(1)
                                        }
                                firebaseFirestore.collection(FIRESTORE_USERS_COLLECTION)
                                        .document(document.id)
                                        .update(createFamilyDataForUserFirestore(
                                                ids = ids,
                                                titles = titles,
                                                sizes = numberOfUsers
                                        ))
                            }
                        }
            }

}