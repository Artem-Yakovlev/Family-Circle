package com.tydeya.familycircle.domain.kitchenorganizer.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.Firebase
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

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

fun createProductInFirebase(catalogId: String, food: Food) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(catalogId).collection(Firebase.FIRESTORE_BUYS_CATALOG_FOODS)
            .add(food.toFirestoreObject())
}

fun deleteProductInFirebase(catalogId: String, productId: String) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(catalogId).collection(Firebase.FIRESTORE_BUYS_CATALOG_FOODS)
            .document(productId).delete()
}

fun editProductInFirebase(catalogId: String, food: Food) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
            .document(catalogId).collection(Firebase.FIRESTORE_BUYS_CATALOG_FOODS)
            .document(food.id).update(food.toFirestoreObject())
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
            .add(food.toFirestoreObject())
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

fun addFoodInFridgeFirebaseProcessing(food: Food) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_FRIDGE_COLLECTION)
            .add(food.toFirestoreObject())
}

fun editFoodInFridgeDataFirebaseProcessing(food: Food) {
    FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_FRIDGE_COLLECTION)
            .document(food.id).update(food.toFirestoreObject())
}

fun eatFoodFromFridgeFirebaseProcessing(eatenAmount: Double, food: Food) {

    val remainingAmount = food.quantityOfMeasure - eatenAmount
    Log.d("ASMR", remainingAmount.toString())

    if (remainingAmount > 0) {
        Log.d("ASMR", ">0")
        FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
                .document(food.id).update(food.copy(quantityOfMeasure = remainingAmount).toFirestoreObject())
    } else {
        FirebaseFirestore.getInstance().collection(Firebase.FIRESTORE_KITCHEN_COLLECTION)
                .document(food.id).delete()
    }
}