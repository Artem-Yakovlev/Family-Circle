package com.tydeya.familycircle.data.messenger

import com.tydeya.familycircle.data.messenger.ChatMessage

data class Conversation(
        val id: String,
        val title: String,
        var unreadMessagesCounter: Int = 0,
        var members: ArrayList<String>,
        var messages: ArrayList<ChatMessage> = ArrayList()
)