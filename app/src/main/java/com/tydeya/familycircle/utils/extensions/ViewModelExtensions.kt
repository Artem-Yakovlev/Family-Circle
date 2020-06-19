package com.tydeya.familycircle.utils.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObservers() {
    this.value = this.value
}