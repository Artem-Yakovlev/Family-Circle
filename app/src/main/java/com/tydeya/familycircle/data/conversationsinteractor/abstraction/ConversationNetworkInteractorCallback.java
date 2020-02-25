package com.tydeya.familycircle.data.conversationsinteractor.abstraction;

import com.tydeya.familycircle.domain.conversation.Conversation;

import java.util.ArrayList;

public interface ConversationNetworkInteractorCallback {

    void conversationsAllDataUpdated(ArrayList<Conversation> conversations);

    void conversationUpdate(Conversation conversation);
}
