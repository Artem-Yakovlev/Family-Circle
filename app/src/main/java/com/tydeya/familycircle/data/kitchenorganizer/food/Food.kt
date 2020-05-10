package com.tydeya.familycircle.data.kitchenorganizer.food

import android.os.Parcelable
import com.tydeya.familycircle.data.constants.Firebase
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Food(val id: String, var title: String, var foodStatus: FoodStatus,
                var quantityOfMeasure: BigDecimal, var measureType: MeasureType) : Parcelable {

    fun toFirestoreObject() = hashMapOf(
            Firebase.FIRESTORE_FOOD_TITLE to title,
            Firebase.FIRESTORE_FOOD_QUANTITY_OF_MEASURE to quantityOfMeasure.toDouble(),
            Firebase.FIRESTORE_FOOD_MEASURE_TYPE to measureType.ordinal,
            Firebase.FIRESTORE_FOOD_STATUS to foodStatus.ordinal
    ) as Map<String, Any>

}