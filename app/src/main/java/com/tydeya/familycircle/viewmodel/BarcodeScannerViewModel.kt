package com.tydeya.familycircle.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.constants.progaOnlineAuthentic
import com.tydeya.familycircle.data.constants.progaOnlineBaseUrl
import com.tydeya.familycircle.data.constants.progaOnlineTokenApi
import com.tydeya.familycircle.data.constants.progaOnlineUserApi
import com.tydeya.familycircle.data.kitchenorganizer.barcodescanner.BarcodeQuery
import com.tydeya.familycircle.data.kitchenorganizer.barcodescanner.BarcodeResource
import com.tydeya.familycircle.data.kitchenorganizer.barcodescanner.ScannedProduct
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.domain.kitchenorganizer.barcodescanner.BarcodeScannerServer
import com.tydeya.familycircle.domain.kitchenorganizer.utils.addFoodInFridgeFirebaseProcessing
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class BarcodeScannerViewModel : ViewModel() {

    val barcodeResourse: MutableLiveData<BarcodeResource<ScannedProduct>> =
            MutableLiveData(BarcodeResource.AwaitingScan())

    private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(progaOnlineBaseUrl).build()

    private val server = retrofit.create(BarcodeScannerServer::class.java)

    fun addProductToFridge(food: Food) {
        addFoodInFridgeFirebaseProcessing(food)
        barcodeResourse.value = BarcodeResource.AwaitingScan()
    }

    fun requireProductDataByBarcode(barcode: String) {
        barcodeResourse.value = BarcodeResource.Loading()

        server.getScannedProduct(BarcodeQuery(progaOnlineUserApi, progaOnlineTokenApi, barcode))
                .enqueue(object : Callback<ScannedProduct> {

                    override fun onFailure(call: Call<ScannedProduct>, t: Throwable) {
                        barcodeResourse.value = BarcodeResource.Failure(t)
                    }

                    override fun onResponse(
                            call: Call<ScannedProduct>,
                            response: Response<ScannedProduct>
                    ) {
                        response.body()?.let {
                            barcodeResourse.value = if (it.isSuccessful == progaOnlineAuthentic) {
                                BarcodeResource.Success(it)
                            } else {
                                BarcodeResource.Failure(
                                        IllegalStateException("The barcode is not genuine")
                                )
                            }
                        }
                    }
                })
    }

}