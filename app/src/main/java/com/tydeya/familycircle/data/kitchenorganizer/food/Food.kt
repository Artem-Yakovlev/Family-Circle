package com.tydeya.familycircle.data.kitchenorganizer.food

import com.tydeya.familycircle.data.constants.Firebase

data class Food(val id: String, var title: String, var foodStatus: FoodStatus,
                var quantityOfMeasure: Double, var measureType: MeasureType) {

    fun toFirestoreObject() = hashMapOf(
            Firebase.FIRESTORE_FOOD_TITLE to title,
            Firebase.FIRESTORE_FOOD_QUANTITY_OF_MEASURE to quantityOfMeasure,
            Firebase.FIRESTORE_FOOD_MEASURE_TYPE to measureType.ordinal,
            Firebase.FIRESTORE_FOOD_STATUS to foodStatus.ordinal
    ) as Map<String, Any>

}