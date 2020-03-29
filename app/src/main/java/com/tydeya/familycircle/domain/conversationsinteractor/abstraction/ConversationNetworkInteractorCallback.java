package com.tydeya.familycircle.domain.conversationsinteractor.abstraction;

import com.tydeya.familycircle.data.oldconversation.OldConversation;

import java.util.ArrayList;

public interface ConversationNetworkInteractorCallback {

    void conversationsAllDataUpdated(ArrayList<OldConversation> oldConversations);

    void conversationUpdate(OldConversation oldConversation);
}
