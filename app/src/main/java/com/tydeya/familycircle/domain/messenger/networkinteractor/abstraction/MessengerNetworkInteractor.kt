package com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction

import com.tydeya.familycircle.data.chatmessage.ChatMessage
import com.tydeya.familycircle.ui.conversationpart.inconversation.InConversationActivity

interface MessengerNetworkInteractor {

    fun requireData()

    fun createConversation(title: String, members: ArrayList<String>)

    fun sendMessage(conversationId: String, message: ChatMessage, unreadByPhones: ArrayList<String>)

    fun readAllMessages(conversationId: String)
}