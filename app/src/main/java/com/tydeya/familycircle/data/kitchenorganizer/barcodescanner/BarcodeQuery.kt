package com.tydeya.familycircle.data.kitchenorganizer.barcodescanner

import com.squareup.moshi.Json

data class BarcodeQuery(

        @field:Json(name = "api_user_id")
        val userId: String,

        @field:Json(name = "api_token")
        val token: String,

        @field:Json(name = "kod")
        val barcode: String

)