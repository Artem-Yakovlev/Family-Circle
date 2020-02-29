package com.tydeya.familycircle.data.conversationsassistant.abstraction;

import com.tydeya.familycircle.domain.conversation.Conversation;

public interface ConversationsAssistant {

    Conversation getConversationByKey(String key);
}
