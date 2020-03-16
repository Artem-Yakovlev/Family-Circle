package com.tydeya.familycircle.domain.conversationsinteractor.abstraction;

import com.tydeya.familycircle.data.chatmessage.ChatMessage;
import com.tydeya.familycircle.data.conversation.Conversation;

import java.util.ArrayList;

public interface ConversationNetworkInteractor {

    void requireConversationsDataFromServer();

    void sendChatMessageToServer(ChatMessage chatMessage, Conversation conversation, ArrayList<String> phoneNumbers);

    void makeMessagesRead(Conversation conversation);

    void setUpdateConversationsListener(ArrayList<Conversation> conversations);


}
