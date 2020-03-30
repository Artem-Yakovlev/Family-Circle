package com.tydeya.familycircle.domain.messenger.interactor.details

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
import java.util.*
import kotlin.collections.ArrayList

class MessengerInteractor
    :
        MessengerNetworkInteractorCallback, MessengerInteractorObservable, ConversationListenerCallback {

    private val observers: ArrayList<MessengerInteractorCallback> = ArrayList()

    private val networkInteractor: MessengerNetworkInteractor =
            MessengerNetworkInteractorImpl(this)

    var conversations = ArrayList<Conversation>()
    var conversationsListeners = ArrayList<ConversationListener>()

    var actualConversationId = ""

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
                conversations[i].messages.sortWith(Comparator { o1: ChatMessage, o2: ChatMessage ->
                    o1.dateTime.compareTo(o2.dateTime)
                })

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
     * Utils
     * */

    fun conversationById(conversationId: String): Conversation? {
        for (conversation in conversations) {
            if (conversation.id == conversationId) {
                return conversation
            }
        }
        return null
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