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

fun createProductInFirebase(id: String, title: String) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(id).collection(Firebase.FIRESTORE_BUY_CATALOG_FOODS)
            .add(createProductFromTitle(title, 0))
}

private fun createProductFromTitle(title: String, foodStatusNumber: Int) = hashMapOf(
        Firebase.FIRESTORE_FOOD_TITLE to title,
        Firebase.FIRESTORE_FOOD_DESCRIPTION to "",
        Firebase.FIRESTORE_FOOD_STATUS to foodStatusNumber,
        Firebase.FIRESTORE_FOOD_CALORIES to 0,
        Firebase.FIRESTORE_FOOD_PROTEIN to 0,
        Firebase.FIRESTORE_FOOD_FATS to 0
) as Map<String, Any>
