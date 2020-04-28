package com.tydeya.familycircle.domain.kitchenorganizer.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.tydeya.familycircle.data.constants.Firebase
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus


fun convertServerDataToFood(documentSnapshot: DocumentSnapshot): Food {
    return Food(documentSnapshot.get(Firebase.FIRESTORE_FOOD_TITLE).toString(),
            documentSnapshot.get(Firebase.FIRESTORE_FOOD_DESCRIPTION).toString(),
            when (documentSnapshot.getLong(Firebase.FIRESTORE_FOOD_STATUS)) {
                0L -> FoodStatus.NEED_BUY
                else -> FoodStatus.IN_FRIDGE
            },
            documentSnapshot.getDouble(Firebase.FIRESTORE_FOOD_CALORIES),
            documentSnapshot.getDouble(Firebase.FIRESTORE_FOOD_PROTEIN),
            documentSnapshot.getDouble(Firebase.FIRESTORE_FOOD_FATS)
    )
}