package com.tydeya.familycircle.utils.extensions

import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.data.messenger.chat.ChatMessage
import com.tydeya.familycircle.data.messenger.chat.FullChatMessage

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