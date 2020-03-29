package com.tydeya.familycircle.domain.conversationsassistant.details;

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.domain.conversationsassistant.abstraction.ConversationsAssistant;
import com.tydeya.familycircle.domain.conversationsinteractor.details.ConversationInteractor;
import com.tydeya.familycircle.data.oldconversation.OldConversation;

import javax.inject.Inject;

public class ConversationsAssistantImpl implements ConversationsAssistant {

    @Inject
    ConversationInteractor conversationInteractor;

    public ConversationsAssistantImpl() {
        App.getComponent().injectConversationAssistant(this);
    }

    @Override
    public OldConversation getConversationByKey(String key) {
        for (OldConversation oldConversation : conversationInteractor.getOldConversations()) {
            if (oldConversation.getKey().equals(key)) {
                return oldConversation;
            }
        }
        return null;
    }
}
