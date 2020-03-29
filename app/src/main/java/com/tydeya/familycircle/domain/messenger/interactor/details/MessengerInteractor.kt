package com.tydeya.familycircle.domain.messenger.interactor.details

import com.tydeya.familycircle.data.messenger.conversation.Conversation
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorCallback
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorObservable
import com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction.MessengerNetworkInteractor
import com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction.MessengerNetworkInteractorCallback
import com.tydeya.familycircle.domain.messenger.networkinteractor.details.MessengerNetworkInteractorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessengerInteractor
    :
        MessengerNetworkInteractorCallback, MessengerInteractorObservable {

    private val observers: ArrayList<MessengerInteractorCallback> = ArrayList()

    private val networkInteractor: MessengerNetworkInteractor =
            MessengerNetworkInteractorImpl(this)

    var conversations = ArrayList<Conversation>()

    /**
     * Data updates
     * */

    fun requireData() {
        networkInteractor.requireData()
    }

    override fun messengerConversationDataUpdated(conversations: ArrayList<Conversation>) {
        GlobalScope.launch(Dispatchers.Default) {
            this@MessengerInteractor.conversations = conversations
            withContext(Dispatchers.Main) {
                notifyObserversKitchenDataUpdated()
            }
        }
    }

    /**
     * Data editing
     * */

    fun createConversation(title: String, members: ArrayList<String>) {
        networkInteractor.createConversation(title, members)
    }

    /**
     * Callbacks
     * */

    private fun notifyObserversKitchenDataUpdated() {
        for (callback in observers) {
            callback.messengerDataFromServerUpdated()
        }
    }

    override fun subscribe(callback: MessengerInteractorCallback) {
        if (!observers.contains(callback)) {
            observers.add(callback)
            callback.messengerDataFromServerUpdated()
        }
    }

    override fun unsubscribe(callback: MessengerInteractorCallback) {
        observers.remove(callback)
    }
}