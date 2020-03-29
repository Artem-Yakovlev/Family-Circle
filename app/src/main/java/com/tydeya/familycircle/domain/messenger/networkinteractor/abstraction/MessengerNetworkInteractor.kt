package com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction

interface MessengerNetworkInteractor {

    fun requireData()

    fun createConversation(title: String, members: ArrayList<String>)
}