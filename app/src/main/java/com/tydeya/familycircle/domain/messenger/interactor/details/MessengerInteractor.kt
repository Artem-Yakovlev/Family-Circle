package com.tydeya.familycircle.domain.messenger.interactor.details

import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.data.messenger.ChatMessage
import com.tydeya.familycircle.data.messenger.Conversation
import com.tydeya.familycircle.domain.messenger.conversationlistener.ConversationListener
import com.tydeya.familycircle.domain.messenger.conversationlistener.ConversationListenerCallback
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorCallback
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorObservable
import com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction.MessengerNetworkInteractor
import com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction.MessengerNetworkInteractorCallback
import com.tydeya.familycircle.domain.messenger.networkinteractor.details.MessengerNetworkInteractorImpl
import com.tydeya.familycircle.utils.extensions.getUserPhone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

object MessengerInteractor
    :
        MessengerNetworkInteractorCallback, MessengerInteractorObservable,
        ConversationListenerCallback {

    private val observers: ArrayList<MessengerInteractorCallback> = ArrayList()

    private var networkInteractor: MessengerNetworkInteractor? = null

    var conversations = ArrayList<Conversation>()
    private var conversationsListeners = ArrayList<ConversationListener>()

    var actualConversationId = ""

    val numberOfUnreadMessages: Int
        get() {
            var counter = 0
            conversations.forEach { counter += it.unreadMessagesCounter }
            return counter
        }

    /**
     * Data updates
     * */

    fun connectToFamily(familyId: String) {
        this.networkInteractor = MessengerNetworkInteractorImpl(familyId, this)
        networkInteractor?.connect()
    }

    fun disconnect() {
        networkInteractor?.disconnect()
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

    override fun conversationMessagesUpdated(
            conversationId: String,
            messages: ArrayList<ChatMessage>,
            unreadCounter: Int
    ) {
        for (i in 0 until conversations.size) {
            if (conversations[i].id == conversationId) {
                conversations[i].unreadMessagesCounter = unreadCounter
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
     * Data editing conversations
     * */

    fun createConversation(title: String, members: ArrayList<String>) {
        networkInteractor?.createConversation(title, members)
    }

    fun editConversationTitle(conversationId: String, actualTitle: String) {
        networkInteractor?.editConversationTitle(conversationId, actualTitle)
    }

    fun leaveConversation(conversationId: String) {
        conversationById(conversationId)?.let {
            it.members.remove(FirebaseAuth.getInstance().currentUser!!.phoneNumber)
            networkInteractor?.changeConversationMembers(conversationId, it.members)
        }
    }

    fun addMemberInConversation(conversationId: String, newMemberPhoneNumber: String) {
        conversationById(conversationId)?.let {
            if (!it.members.contains(newMemberPhoneNumber)) {
                it.members.add(newMemberPhoneNumber)
                networkInteractor?.changeConversationMembers(conversationId, it.members)
            }
        }
    }

    /**
     * Data editing messages
     * */

    fun sendMessage(conversationId: String, text: String) {
        networkInteractor?.sendMessage(
                conversationId = conversationId,
                message = ChatMessage(getUserPhone(), text, Date(), true),
                unreadByPhones = unreadPhoneNumbers(conversationId)
        )
    }

    fun readAllMessages(conversationId: String) {
        conversationById(conversationId)?.let {
            if (it.unreadMessagesCounter != 0) {
                it.unreadMessagesCounter = 0
                networkInteractor?.readAllMessages(conversationId)
            }
        }
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

    private fun unreadPhoneNumbers(conversationId: String): ArrayList<String> {
        val unreadPhoneNumbers = ArrayList<String>()
        conversationById(conversationId)?.members?.forEach {
            if (it != getUserPhone()) {
                unreadPhoneNumbers.add(it)
            }
        }
        return unreadPhoneNumbers
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
