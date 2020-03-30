package com.tydeya.familycircle.domain.onlinemanager.abstraction

interface OnlineInteractor {

    fun registerUserActivity()

    fun isUserOnline(phoneNumber: String): Boolean

}