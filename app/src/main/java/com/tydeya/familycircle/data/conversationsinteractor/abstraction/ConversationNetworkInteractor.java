package com.tydeya.familycircle.data.conversationsinteractor.abstraction;

import com.tydeya.familycircle.domain.chatmessage.ChatMessage;
import com.tydeya.familycircle.domain.conversation.Conversation;

import java.util.ArrayList;

public interface ConversationNetworkInteractor {

    void requireConversationsDataFromServer();

    void sendChatMessageToServer(ChatMessage chatMessage, Conversation conversation);

    void setUpdateConversationsListener(ArrayList<Conversation> conversations);
}
