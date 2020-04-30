package com.tydeya.familycircle.domain.kitchenorganizer.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.Firebase
import kotlinx.coroutines.Dispatchers
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

fun deleteProductInFirebase(catalogId: String, title: String) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(catalogId).collection(Firebase.FIRESTORE_BUY_CATALOG_FOODS)
            .whereEqualTo(Firebase.FIRESTORE_FOOD_TITLE, title).get()
            .addOnSuccessListener { querySnapshot ->
                GlobalScope.launch(Dispatchers.Default) {
                    for (document in querySnapshot.documents) {
                        document.reference.delete()
                    }
                }
            }
}

fun editProductInFirebase(id: String, actualTitle: String, newTitle: String) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(id).collection(Firebase.FIRESTORE_BUY_CATALOG_FOODS)
            .whereEqualTo(Firebase.FIRESTORE_FOOD_TITLE, actualTitle).get()
            .addOnSuccessListener { querySnapshot ->
                GlobalScope.launch(Dispatchers.Default) {
                    if (querySnapshot.documents.size != 0) {
                        querySnapshot.documents[0].reference.update(Firebase.FIRESTORE_FOOD_TITLE, newTitle)
                    }
                }
            }
}

fun editBuysCatalogTitle(catalogId: String, newTitle: String) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(catalogId).update(Firebase.FIRESTORE_BUY_CATALOG_TITLE, newTitle)
}

fun buyProductFirebaseProcessing(catalogId: String, title: String) {

    val firestore = FirebaseFirestore.getInstance()

    firestore.collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(catalogId).collection(Firebase.FIRESTORE_BUY_CATALOG_FOODS)
            .whereEqualTo(Firebase.FIRESTORE_FOOD_TITLE, title).get()
            .addOnSuccessListener { querySnapshot ->
                GlobalScope.launch(Dispatchers.Default) {
                    // In FIRESTORE food_status 1 == FOOD_IN_FRIDGE
                    if (querySnapshot.documents.size != 0) {
                        querySnapshot.documents[0].reference.update(Firebase.FIRESTORE_FOOD_STATUS, 1)
                    }
                }
            }
    firestore.collection(Firebase.FIRESTORE_FRIDGE_COLLECTION)
            .add(createProductFromTitle(title, 1))
}