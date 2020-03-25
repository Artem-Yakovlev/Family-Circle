package com.tydeya.familycircle.data.kitchenorganizer.buylist

import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import java.util.*
import kotlin.collections.ArrayList

class BuyCatalog(val id: String, var title: String, val dateOfCreate: Date, var products: ArrayList<Food>) {
}