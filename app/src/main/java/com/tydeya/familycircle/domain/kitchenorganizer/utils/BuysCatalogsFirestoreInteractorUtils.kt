package com.tydeya.familycircle.domain.kitchenorganizer.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.Firebase
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

private fun createProductFromTitle(title: String, foodStatusNumber: Int) = hashMapOf(
        Firebase.FIRESTORE_FOOD_TITLE to title,
        Firebase.FIRESTORE_FOOD_DESCRIPTION to "",
        Firebase.FIRESTORE_FOOD_STATUS to foodStatusNumber,
        Firebase.FIRESTORE_FOOD_CALORIES to 0,
        Firebase.FIRESTORE_FOOD_PROTEIN to 0,
        Firebase.FIRESTORE_FOOD_FATS to 0
) as Map<String, Any>

fun createBuysCatalogInFirebase(title: String) = GlobalScope.launch {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION).add(
            hashMapOf(
                    Firebase.FIRESTORE_BUYS_CATALOG_TITLE to title,
                    Firebase.FIRESTORE_BUYS_CATALOG_DATE to Date()
            ) as Map<String, Any>)
}

fun deleteBuyCatalogInFirebase(catalogId: String) = GlobalScope.launch {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(catalogId).delete()

    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(catalogId).collection(Firebase.FIRESTORE_BUYS_CATALOG_FOODS).get()
            .addOnSuccessListener {
                for (document in it.documents) {
                    document.reference.delete()
                }
            }
}

fun createProductInFirebase(id: String, title: String) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(id).collection(Firebase.FIRESTORE_BUYS_CATALOG_FOODS)
            .add(createProductFromTitle(title, 0))
}

fun deleteProductInFirebase(catalogId: String, productId: String) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(catalogId).collection(Firebase.FIRESTORE_BUYS_CATALOG_FOODS)
            .document(productId).delete()
}

fun editProductInFirebase(id: String, actualTitle: String, newTitle: String) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(id).collection(Firebase.FIRESTORE_BUYS_CATALOG_FOODS)
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
            .document(catalogId).update(Firebase.FIRESTORE_BUYS_CATALOG_TITLE, newTitle)
}

/**
 * Processing
 * */

fun buyProductFirebaseProcessing(catalogId: String, food: Food) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(catalogId).collection(Firebase.FIRESTORE_BUYS_CATALOG_FOODS)
            .document(food.id).update(Firebase.FIRESTORE_FOOD_STATUS, 1)

    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_FRIDGE_COLLECTION)
            .add(createProductFromTitle(food.title, 1))
}

fun updateBuysCatalogProductsInfo(catalogId: String) {
    val actualBuysCatalog = FirebaseFirestore.getInstance()
            .collection(Firebase.FIRESTORE_KITCHEN_COLLECTION).document(catalogId)

    actualBuysCatalog.collection(Firebase.FIRESTORE_BUYS_CATALOG_FOODS)
            .get().addOnSuccessListener {
                val nPurchased = it.documents.filter { product ->
                    product.getLong(Firebase.FIRESTORE_FOOD_STATUS) == 1L
                }.size
                actualBuysCatalog.update(Firebase.FIRESTORE_BUYS_CATALOG_NUMBER_PURCHASED, nPurchased)
                actualBuysCatalog.update(Firebase.FIRESTORE_BUYS_CATALOG_NUMBER_PRODUCTS, it.documents.size)
            }
}

/**
 * Fridge
 * */

fun deleteFoodFromFridgeInFirebaseProcessing(title: String) {
    FirebaseFirestore.getInstance()
            .collection(Firebase.FIRESTORE_FRIDGE_COLLECTION).document(title).delete()
}

fun addFoodInFridgeFirebaseProcessing(title: String) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_FRIDGE_COLLECTION)
            .add(createProductFromTitle(title, 1))
}