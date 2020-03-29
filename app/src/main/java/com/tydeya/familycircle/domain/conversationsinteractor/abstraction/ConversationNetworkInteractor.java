package com.tydeya.familycircle.domain.conversationsinteractor.abstraction;

import com.tydeya.familycircle.data.chatmessage.ChatMessage;
import com.tydeya.familycircle.data.oldconversation.OldConversation;

import java.util.ArrayList;

public interface ConversationNetworkInteractor {

    void requireConversationsDataFromServer();

    void sendChatMessageToServer(ChatMessage chatMessage, OldConversation oldConversation, ArrayList<String> phoneNumbers);

    void makeMessagesRead(OldConversation oldConversation);

    void setUpdateConversationsListener(ArrayList<OldConversation> oldConversations);


}
