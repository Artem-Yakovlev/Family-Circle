package com.tydeya.familycircle.data.kitchenorganizer.food

import android.os.Parcelable
import com.tydeya.familycircle.data.constants.FireStore
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Food(val id: String, var title: String, var foodStatus: FoodStatus,
                var quantityOfMeasure: BigDecimal, var measureType: MeasureType,
                val shelfLifeTimeStamp: Long) : Parcelable {

    fun toFirestoreObject() = hashMapOf(
            FireStore.FIRESTORE_FOOD_TITLE to title,
            FireStore.FIRESTORE_FOOD_QUANTITY_OF_MEASURE to quantityOfMeasure.toDouble(),
            FireStore.FIRESTORE_FOOD_MEASURE_TYPE to measureType.ordinal,
            FireStore.FIRESTORE_FOOD_STATUS to foodStatus.ordinal,
            FireStore.FIRESTORE_FOOD_SHELF_LIFE_TIMESTAMP to shelfLifeTimeStamp
    ) as Map<String, Any>

}