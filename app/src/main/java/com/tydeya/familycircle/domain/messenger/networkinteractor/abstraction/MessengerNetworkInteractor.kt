package com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction

import com.tydeya.familycircle.data.chatmessage.ChatMessage

interface MessengerNetworkInteractor {

    fun requireData()

    fun createConversation(title: String, members: ArrayList<String>)

    fun editConversationTitle(conversationId: String, title: String)

    fun sendMessage(conversationId: String, message: ChatMessage, unreadByPhones: ArrayList<String>)

    fun readAllMessages(conversationId: String)

    fun changeConversationMembers(conversationId: String, members: ArrayList<String>)
}