package com.tydeya.familycircle.domain.familyselection

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore.FAMILY_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FAMILY_NUMBER_OF_MEMBERS_TAG
import com.tydeya.familycircle.data.constants.FireStore.FAMILY_TITLE_TAG
import com.tydeya.familycircle.data.constants.FireStore.USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.USERS_FAMILY_IDS
import com.tydeya.familycircle.data.constants.FireStore.USERS_FAMILY_INVITES
import com.tydeya.familycircle.data.constants.FireStore.USERS_FAMILY_SIZES
import com.tydeya.familycircle.data.constants.FireStore.USERS_FAMILY_TITLES
import com.tydeya.familycircle.utils.extensions.firestoreFamily

fun createFamilyInFirestore(title: String) {

    val firebaseFirestore = FirebaseFirestore.getInstance()
    val authorPhone = FirebaseAuth.getInstance().currentUser?.phoneNumber!!

    firebaseFirestore.collection(FAMILY_COLLECTION)
            .add(createNewFamilyFirestoreData(title, authorPhone))
            .addOnSuccessListener { familyDocument ->

                firebaseFirestore.collection(USERS_COLLECTION)
                        .document(authorPhone).get()
                        .addOnSuccessListener {
                            it?.let { document ->

                                val titles = document
                                        .getListByTag<String>(USERS_FAMILY_TITLES)
                                        .apply {
                                            add(title)
                                        }
                                val ids = document
                                        .getListByTag<String>(USERS_FAMILY_IDS)
                                        .apply {
                                            add(familyDocument.id)
                                        }
                                val numberOfUsers = document
                                        .getListByTag<Int>(USERS_FAMILY_SIZES)
                                        .apply {
                                            add(1)
                                        }
                                firebaseFirestore.collection(USERS_COLLECTION)
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
            .collection(USERS_COLLECTION)
            .document(phoneNumber).get()
            .addOnSuccessListener {
                val invites = it.getListByTag<String>(USERS_FAMILY_INVITES)
                val ids = it.getListByTag<String>(USERS_FAMILY_IDS)

                if (familyId !in invites && familyId !in ids) {
                    invites.add(familyId)
                    firebaseFirestore.collection(USERS_COLLECTION)
                            .document(phoneNumber)
                            .update(mapOf(USERS_FAMILY_INVITES to invites))
                }
            }
}

fun acceptFamilyInvite(familyId: String) {
    val phoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: "_"
    firestoreFamily(familyId).get()
            .addOnSuccessListener { familyDoc ->
                val title = familyDoc.getString(FAMILY_TITLE_TAG) ?: ""
                val nMembers = familyDoc.getLong(FAMILY_NUMBER_OF_MEMBERS_TAG) ?: 1
                FirebaseFirestore.getInstance()
                        .collection(USERS_COLLECTION)
                        .document(phoneNumber).get()
                        .addOnSuccessListener { userDoc ->
                            val invites = userDoc.getListByTag<String>(USERS_FAMILY_INVITES)
                            invites.remove(familyId)

                            val titles = userDoc
                                    .getListByTag<String>(USERS_FAMILY_TITLES)
                                    .apply {
                                        add(title)
                                    }
                            val ids = userDoc
                                    .getListByTag<String>(USERS_FAMILY_IDS)
                                    .apply {
                                        add(familyId)
                                    }
                            val numberOfUsers = userDoc
                                    .getListByTag<Int>(USERS_FAMILY_SIZES)
                                    .apply {
                                        add(nMembers.toInt())
                                    }

                            userDoc.reference.update(mapOf(
                                    USERS_FAMILY_INVITES to invites,
                                    USERS_FAMILY_TITLES to titles,
                                    USERS_FAMILY_IDS to ids,
                                    USERS_FAMILY_SIZES to numberOfUsers
                            ))
                        }
            }
}

fun refuseFamilyInvite(familyId: String) {
    val phoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: "_"
    FirebaseFirestore.getInstance()
            .collection(USERS_COLLECTION)
            .document(phoneNumber).get()
            .addOnSuccessListener {
                val invites = it.getListByTag<String>(USERS_FAMILY_INVITES)
                invites.remove(familyId)
                it.reference.update(mapOf(USERS_FAMILY_INVITES to invites))
            }
}