package com.tydeya.familycircle.data.messenger.chat

import java.util.*
import kotlin.collections.ArrayList

data class ChatMessage(
        val id: String = "",
        var authorPhoneNumber: String = "",
        var text: String = "",
        var dateTime: Date = Date(),
        var isViewed: Boolean = false,
        var whoSawMessage: ArrayList<String> = ArrayList()
)