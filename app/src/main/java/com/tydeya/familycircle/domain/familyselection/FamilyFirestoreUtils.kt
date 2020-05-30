package com.tydeya.familycircle.domain.familyselection

import com.google.firebase.firestore.DocumentSnapshot
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_AUTHOR_PHONE_TAG
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_FAMILY_TITLE_TAG
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_IDS
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_SIZES
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_FAMILY_TITLES

fun createNewFamilyFirestoreData(title: String, authorPhone: String) =
        hashMapOf(
                FIRESTORE_FAMILY_TITLE_TAG to title,
                FIRESTORE_AUTHOR_PHONE_TAG to authorPhone
        ) as Map<String, Any>


fun createFamilyDataForUserFirestore(ids: List<String>, titles: List<String>, sizes: List<Int>) =
        hashMapOf(
                FIRESTORE_USERS_FAMILY_IDS to ids,
                FIRESTORE_USERS_FAMILY_TITLES to titles,
                FIRESTORE_USERS_FAMILY_SIZES to sizes
        ) as Map<String, Any>


@Suppress("UNCHECKED_CAST")
fun <T> DocumentSnapshot.getListByTag(tag: String): ArrayList<T> {
    return (get(tag) ?: ArrayList<T>()) as ArrayList<T>
}