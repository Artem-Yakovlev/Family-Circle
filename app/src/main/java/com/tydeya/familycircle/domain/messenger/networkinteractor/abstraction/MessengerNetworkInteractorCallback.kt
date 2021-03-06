package com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction

import com.tydeya.familycircle.data.messenger.conversation.Conversation

interface MessengerNetworkInteractorCallback {

    fun messengerConversationDataUpdated(conversations: ArrayList<Conversation>)

}