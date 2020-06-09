package com.tydeya.familycircle.data.messenger.chat

data class FullChatMessage(
        val userName: String = "",
        val imageAddress: String = "",
        val isUserOnline: Boolean = false,
        val chatMessage: ChatMessage
) {

}