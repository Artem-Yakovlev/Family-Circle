package com.tydeya.familycircle.data.messenger.chat

import java.util.*

data class ChatMessage(
        val id: String,
        var authorPhoneNumber: String,
        var text: String,
        var dateTime: Date,
        var isViewed: Boolean
)