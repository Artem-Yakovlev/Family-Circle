package com.tydeya.familycircle.domain.messenger.conversationlistener

import com.tydeya.familycircle.data.chatmessage.ChatMessage

interface ConversationListenerCallback {

    fun conversationMessagesUpdated(conversationId: String, messages: ArrayList<ChatMessage>, unreadCounter: Int)

}