package com.tydeya.familycircle.domain.kitchenorganizer.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

fun createBuysCatalogInFirebase(title: String) = GlobalScope.launch {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION).add(
            hashMapOf(
                    Firebase.FIRESTORE_BUY_CATALOG_TITLE to title,
                    Firebase.FIRESTORE_BUY_CATALOG_DATE to Date()
            ) as Map<String, Any>)
}

fun renameBuysCatalogInFirebase(catalogId: String, newTitle: String) = GlobalScope.launch {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(catalogId).update(Firebase.FIRESTORE_BUY_CATALOG_TITLE, newTitle)
}

fun deleteBuyCatalogInFirebase(catalogId: String) = GlobalScope.launch {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(catalogId).delete()
}
