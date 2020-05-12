package com.tydeya.familycircle.domain.kitchenorganizer.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.tydeya.familycircle.data.constants.FireStore
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.data.kitchenorganizer.food.MeasureType
import java.math.BigDecimal


fun convertServerDataToFood(documentSnapshot: DocumentSnapshot): Food {
    return Food(documentSnapshot.id,
            documentSnapshot.get(FireStore.FIRESTORE_FOOD_TITLE).toString(),
            FoodStatus.values()[(documentSnapshot.getLong(FireStore.FIRESTORE_FOOD_STATUS)
                    ?: 0).toInt()],
            BigDecimal.valueOf(documentSnapshot
                    .getDouble(FireStore.FIRESTORE_FOOD_QUANTITY_OF_MEASURE) ?: .0),
            MeasureType
                    .values()[(documentSnapshot.getLong(FireStore.FIRESTORE_FOOD_MEASURE_TYPE) ?: 0)
                    .toInt()],
            documentSnapshot.getLong(FireStore.FIRESTORE_FOOD_SHELF_LIFE_TIMESTAMP)
                    ?: -1L
    )
}