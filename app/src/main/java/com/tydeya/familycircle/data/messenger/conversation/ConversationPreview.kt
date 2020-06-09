package com.tydeya.familycircle.data.messenger.conversation

import java.util.*

data class ConversationPreview(
        val id: String,
        val title: String,
        val userName: String = "",
        val userMessage: String = "",
        val userImageAddress: String = "",
        val userMessageTime: Date = Date(),
        val unreadMessagesCounter: Int = 0,
        val isEmpty: Boolean = true
)
