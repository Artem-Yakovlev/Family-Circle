package com.tydeya.familycircle.data.messenger.conversation

import com.tydeya.familycircle.data.chatmessage.ChatMessage

data class Conversation(val id: String, val title: String,
                        var members: ArrayList<String>, var messages: ArrayList<ChatMessage>) {
}