package com.tydeya.familycircle.domain.kitchenorganizer.barcodescanner

import com.tydeya.familycircle.data.kitchenorganizer.barcodescanner.BarcodeQuery
import com.tydeya.familycircle.data.kitchenorganizer.barcodescanner.ScannedProduct
import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface BarcodeScannerServer {

    @POST("/kod/api/info")
    fun getScannedProduct(@Body barcodeQuery: BarcodeQuery): Call<ScannedProduct>
}