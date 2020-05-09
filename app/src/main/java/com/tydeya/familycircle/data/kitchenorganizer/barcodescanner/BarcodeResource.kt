package com.tydeya.familycircle.data.kitchenorganizer.barcodescanner

sealed class BarcodeResource<out T> {
    class Loading<out T> : BarcodeResource<T>()

    class AwaitingScan<out T> : BarcodeResource<T>()

    data class Success<out T>(val data: T) : BarcodeResource<T>()

    data class Failure<out T>(val throwable: Throwable) : BarcodeResource<T>()
}