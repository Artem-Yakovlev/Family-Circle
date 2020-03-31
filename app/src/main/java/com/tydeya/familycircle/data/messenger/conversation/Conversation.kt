package com.tydeya.familycircle.data.messenger.conversation

import com.tydeya.familycircle.data.messenger.chatmessage.ChatMessage

data class Conversation(val id: String, val title: String, var unreadMessagesCounter: Int,
                        var members: ArrayList<String>, var messages: ArrayList<ChatMessage>) {
}