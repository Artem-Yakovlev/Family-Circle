package com.tydeya.familycircle.utils.extensions

import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.data.messenger.chat.ChatMessage
import com.tydeya.familycircle.data.messenger.chat.FullChatMessage
import com.tydeya.familycircle.data.messenger.conversation.Conversation
import com.tydeya.familycircle.data.messenger.conversation.ConversationPreview

fun List<ChatMessage>.toFullMessages(
        familyMembers: ArrayList<FamilyMember>
):
        List<FullChatMessage> {

    val familyMembersMap = familyMembers.associateBy(FamilyMember::fullPhoneNumber)

    return map {
        val user = familyMembersMap[it.authorPhoneNumber]
        FullChatMessage(
                userName = user?.description?.name ?: "",
                imageAddress = user?.description?.imageAddress ?: "",
                isUserOnline = user?.isOnline ?: false,
                chatMessage = it
        )
    }.toArrayList()
}

fun List<Conversation>.toConversationsPreview(
        familyMembers: ArrayList<FamilyMember>
):
        List<ConversationPreview> {

    val familyMembersMap = familyMembers.associateBy(FamilyMember::fullPhoneNumber)

    return map {
        if (it.messages.isNotEmpty()) {
            val user = familyMembersMap[it.messages.last().authorPhoneNumber]
            ConversationPreview(
                    id = it.id,
                    title = it.title,
                    userName = user?.description?.name ?: "",
                    userMessage = it.messages.last().text,
                    userImageAddress = user?.description?.imageAddress ?: "",
                    userMessageTime = it.messages.last().dateTime,
                    unreadMessagesCounter = it.unreadMessagesCounter,
                    isEmpty = false
            )
        } else {
            ConversationPreview(
                    id = it.id,
                    title = it.title
            )
        }
    }.toArrayList()
}
