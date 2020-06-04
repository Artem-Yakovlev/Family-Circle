package com.tydeya.familycircle.domain.kitchenorganizer.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*

fun createBuysCatalogInFirebase(title: String) = GlobalScope.launch {
    FirebaseFirestore.getInstance().collection(FireStore.KITCHEN_COLLECTION).add(
            hashMapOf(
                    FireStore.BUYS_CATALOG_TITLE to title,
                    FireStore.BUYS_CATALOG_DATE to Date()
            ) as Map<String, Any>)
}

fun deleteBuyCatalogInFirebase(catalogId: String) = GlobalScope.launch {
    FirebaseFirestore.getInstance().collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).delete()

    FirebaseFirestore.getInstance().collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).collection(FireStore.BUYS_CATALOG_FOODS).get()
            .addOnSuccessListener {
                for (document in it.documents) {
                    document.reference.delete()
                }
            }
}

fun createProductInFirebase(catalogId: String, food: Food) {
    FirebaseFirestore.getInstance().collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).collection(FireStore.BUYS_CATALOG_FOODS)
            .add(food.toFirestoreObject())
}

fun deleteProductInFirebase(catalogId: String, productId: String) {
    FirebaseFirestore.getInstance().collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).collection(FireStore.BUYS_CATALOG_FOODS)
            .document(productId).delete()
}

fun editProductInFirebase(catalogId: String, food: Food) {
    FirebaseFirestore.getInstance().collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).collection(FireStore.BUYS_CATALOG_FOODS)
            .document(food.id).update(food.toFirestoreObject())
}

fun editBuysCatalogTitle(catalogId: String, newTitle: String) {
    FirebaseFirestore.getInstance().collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).update(FireStore.BUYS_CATALOG_TITLE, newTitle)
}

/**
 * Processing
 * */

fun buyProductFirebaseProcessing(catalogId: String, food: Food) {
    FirebaseFirestore.getInstance().collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).collection(FireStore.BUYS_CATALOG_FOODS)
            .document(food.id).update(FireStore.FOOD_STATUS, 1)

    FirebaseFirestore.getInstance().collection(FireStore.FRIDGE_COLLECTION)
            .add(food.toFirestoreObject())
}

fun updateBuysCatalogProductsInfo(catalogId: String) {
    val actualBuysCatalog = FirebaseFirestore.getInstance()
            .collection(FireStore.KITCHEN_COLLECTION).document(catalogId)

    actualBuysCatalog.collection(FireStore.BUYS_CATALOG_FOODS)
            .get().addOnSuccessListener {
                val nPurchased = it.documents.filter { product ->
                    product.getLong(FireStore.FOOD_STATUS) == 1L
                }.size
                actualBuysCatalog.update(FireStore.BUYS_CATALOG_NUMBER_PURCHASED, nPurchased)
                actualBuysCatalog.update(FireStore.BUYS_CATALOG_NUMBER_PRODUCTS, it.documents.size)
            }
}

/**
 * Fridge
 * */

fun deleteFoodFromFridgeInFirebaseProcessing(title: String) {
    FirebaseFirestore.getInstance()
            .collection(FireStore.FRIDGE_COLLECTION).document(title).delete()
}

fun addFoodInFridgeFirebaseProcessing(food: Food) {
    FirebaseFirestore.getInstance().collection(FireStore.FRIDGE_COLLECTION)
            .add(food.toFirestoreObject())
}

fun editFoodInFridgeDataFirebaseProcessing(food: Food) {
    FirebaseFirestore.getInstance().collection(FireStore.FRIDGE_COLLECTION)
            .document(food.id).update(food.toFirestoreObject())
}

fun eatFoodFromFridgeFirebaseProcessing(eatenAmount: BigDecimal, food: Food) {
    val remainingAmount = food.quantityOfMeasure - eatenAmount

    if (remainingAmount > BigDecimal.ZERO) {
        FirebaseFirestore.getInstance().collection(FireStore.FRIDGE_COLLECTION)
                .document(food.id)
                .update(food.copy(quantityOfMeasure = remainingAmount).toFirestoreObject())
    } else {
        FirebaseFirestore.getInstance().collection(FireStore.FRIDGE_COLLECTION)
                .document(food.id).delete()
    }
}