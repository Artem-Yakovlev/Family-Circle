package com.tydeya.familycircle.data.messenger.chat

import java.util.*
import kotlin.collections.ArrayList

data class ChatMessage(
        val id: String,
        var authorPhoneNumber: String,
        var text: String,
        var dateTime: Date,
        var isViewed: Boolean,
        var whoSawMessage: ArrayList<String> = ArrayList()
)