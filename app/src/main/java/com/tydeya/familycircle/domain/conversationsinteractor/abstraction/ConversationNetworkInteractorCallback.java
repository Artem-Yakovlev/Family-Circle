package com.tydeya.familycircle.domain.conversationsinteractor.abstraction;

import com.tydeya.familycircle.data.conversation.Conversation;

import java.util.ArrayList;

public interface ConversationNetworkInteractorCallback {

    void conversationsAllDataUpdated(ArrayList<Conversation> conversations);

    void conversationUpdate(Conversation conversation);
}
