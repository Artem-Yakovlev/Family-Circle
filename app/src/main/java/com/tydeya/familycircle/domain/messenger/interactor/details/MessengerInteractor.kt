package com.tydeya.familycircle.domain.messenger.interactor.details

import android.util.Log
import com.tydeya.familycircle.data.chatmessage.ChatMessage
import com.tydeya.familycircle.data.messenger.conversation.Conversation
import com.tydeya.familycircle.domain.messenger.conversationlistener.ConversationListener
import com.tydeya.familycircle.domain.messenger.conversationlistener.ConversationListenerCallback
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
        MessengerNetworkInteractorCallback, MessengerInteractorObservable, ConversationListenerCallback {

    private val observers: ArrayList<MessengerInteractorCallback> = ArrayList()

    private val networkInteractor: MessengerNetworkInteractor =
            MessengerNetworkInteractorImpl(this)

    var conversations = ArrayList<Conversation>()
    var conversationsListeners = ArrayList<ConversationListener>()

    /**
     * Data updates
     * */

    init {
        networkInteractor.requireData()
    }

    override fun messengerConversationDataUpdated(conversations: ArrayList<Conversation>) {
        GlobalScope.launch(Dispatchers.Default) {
            unregisterListenersOfAllConversations()

            this@MessengerInteractor.conversations = conversations

            registerListenersOfAllConversations()

            withContext(Dispatchers.Main) {
                notifyObserversKitchenDataUpdated()
            }
        }
    }

    override fun conversationMessagesUpdated(conversationId: String, messages: ArrayList<ChatMessage>) {

        for (i in 0 until conversations.size) {
            if (conversations[i].id == conversationId) {
                conversations[i].messages = messages
                notifyObserversKitchenDataUpdated()
                break
            }
        }
    }


    /**
     * Conversation listener utils
     * */

    private fun registerListenersOfAllConversations() {

        this.conversations.forEach {
            conversationsListeners.add(ConversationListener(it.id, this))
        }

        this.conversationsListeners.forEach {
            it.register()
        }
    }

    private fun unregisterListenersOfAllConversations() {
        this.conversationsListeners.forEach {
            it.unregister()
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