package com.tydeya.familycircle.domain.kitchenorganizer.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.utils.extensions.firestoreFamily
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*

fun createBuysCatalogInFirebase(familyId: String, title: String) = GlobalScope.launch {
    firestoreFamily(familyId).collection(FireStore.KITCHEN_COLLECTION).add(
            hashMapOf(
                    FireStore.BUYS_CATALOG_TITLE to title,
                    FireStore.BUYS_CATALOG_DATE to Date()
            ) as Map<String, Any>)
}

fun deleteBuyCatalogInFirebase(familyId: String, catalogId: String) = GlobalScope.launch {
    firestoreFamily(familyId).collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).delete()

    firestoreFamily(familyId).collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).collection(FireStore.BUYS_CATALOG_FOODS).get()
            .addOnSuccessListener {
                for (document in it.documents) {
                    document.reference.delete()
                }
            }
}

fun createProductInFirebase(familyId: String, catalogId: String, food: Food) {
    firestoreFamily(familyId).collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).collection(FireStore.BUYS_CATALOG_FOODS)
            .add(food.toFirestoreObject())
}

fun deleteProductInFirebase(familyId: String, catalogId: String, productId: String) {
    firestoreFamily(familyId).collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).collection(FireStore.BUYS_CATALOG_FOODS)
            .document(productId).delete()
}

fun editProductInFirebase(familyId: String, catalogId: String, food: Food) {
    firestoreFamily(familyId).collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).collection(FireStore.BUYS_CATALOG_FOODS)
            .document(food.id).update(food.toFirestoreObject())
}

fun editBuysCatalogTitle(familyId: String, catalogId: String, newTitle: String) {
    firestoreFamily(familyId).collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).update(FireStore.BUYS_CATALOG_TITLE, newTitle)
}

/**
 * Processing
 * */

fun buyProductFirebaseProcessing(familyId: String, catalogId: String, food: Food) {
    firestoreFamily(familyId).collection(FireStore.KITCHEN_COLLECTION)
            .document(catalogId).collection(FireStore.BUYS_CATALOG_FOODS)
            .document(food.id).update(FireStore.FOOD_STATUS, 1)

    FirebaseFirestore.getInstance().collection(FireStore.FRIDGE_COLLECTION)
            .add(food.toFirestoreObject())
}

fun updateBuysCatalogProductsInfo(familyId: String, catalogId: String) {
    val actualBuysCatalog = firestoreFamily(familyId)
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

fun deleteFoodFromFridgeInFirebaseProcessing(familyId: String, title: String) {
    firestoreFamily(familyId)
            .collection(FireStore.FRIDGE_COLLECTION).document(title).delete()
}

fun addFoodInFridgeFirebaseProcessing(familyId: String, food: Food) {
    firestoreFamily(familyId).collection(FireStore.FRIDGE_COLLECTION)
            .add(food.toFirestoreObject())
}

fun editFoodInFridgeDataFirebaseProcessing(familyId: String, food: Food) {
    firestoreFamily(familyId).collection(FireStore.FRIDGE_COLLECTION)
            .document(food.id).update(food.toFirestoreObject())
}

fun eatFoodFromFridgeFirebaseProcessing(familyId: String, eatenAmount: BigDecimal, food: Food) {
    val remainingAmount = food.quantityOfMeasure - eatenAmount

    if (remainingAmount > BigDecimal.ZERO) {
        firestoreFamily(familyId).collection(FireStore.FRIDGE_COLLECTION)
                .document(food.id)
                .update(food.copy(quantityOfMeasure = remainingAmount).toFirestoreObject())
    } else {
        firestoreFamily(familyId).collection(FireStore.FRIDGE_COLLECTION)
                .document(food.id).delete()
    }
}