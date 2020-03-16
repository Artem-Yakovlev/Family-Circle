package com.tydeya.familycircle.domain.conversationsassistant.details;

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.domain.conversationsassistant.abstraction.ConversationsAssistant;
import com.tydeya.familycircle.domain.conversationsinteractor.details.ConversationInteractor;
import com.tydeya.familycircle.data.conversation.Conversation;

import javax.inject.Inject;

public class ConversationsAssistantImpl implements ConversationsAssistant {

    @Inject
    ConversationInteractor conversationInteractor;

    public ConversationsAssistantImpl() {
        App.getComponent().injectConversationAssistant(this);
    }

    @Override
    public Conversation getConversationByKey(String key) {
        for (Conversation conversation: conversationInteractor.getConversations()) {
            if (conversation.getKey().equals(key)) {
                return conversation;
            }
        }
        return null;
    }
}
