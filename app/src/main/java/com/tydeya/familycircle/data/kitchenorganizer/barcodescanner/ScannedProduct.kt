package com.tydeya.familycircle.data.kitchenorganizer.barcodescanner

import com.squareup.moshi.Json


data class ScannedProduct(

        @field:Json(name = "kod")
        val barcode: String,

        @field:Json(name = "name")
        val name: String,

        @field:Json(name = "image")
        val imageSource: String?,

        @field:Json(name = "strana")
        val country: String,

        @field:Json(name = "proverka")
        val isSuccessful: String

)