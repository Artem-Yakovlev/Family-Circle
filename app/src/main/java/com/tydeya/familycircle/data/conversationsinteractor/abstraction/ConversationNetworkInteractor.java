package com.tydeya.familycircle.data.conversationsinteractor.abstraction;

import com.tydeya.familycircle.domain.chatmessage.ChatMessage;
import com.tydeya.familycircle.domain.conversation.Conversation;

public interface ConversationNetworkInteractor {

    void requireConversationsDataFromServer();

    void sendChatMessageToServer(ChatMessage chatMessage, Conversation conversation);
}
