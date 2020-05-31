package com.tydeya.familycircle.domain.familyselection

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_FAMILY_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_IDS
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_INVITES
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_SIZES
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_TITLES

fun createFamilyInFirestore(title: String) {

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

fun addFamilyMemberInFirestore(familyId: String, phoneNumber: String) {
    val firebaseFirestore = FirebaseFirestore.getInstance()

    firebaseFirestore
            .collection(FIRESTORE_USERS_COLLECTION)
            .document(phoneNumber).get()
            .addOnSuccessListener {
                val invites = it.getListByTag<String>(FIRESTORE_USERS_FAMILY_INVITES)
                invites.add(familyId)
                firebaseFirestore.collection(FIRESTORE_USERS_COLLECTION)
                        .document(phoneNumber)
                        .update(mapOf(FIRESTORE_USERS_FAMILY_INVITES to invites))
            }
}