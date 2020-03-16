package com.tydeya.familycircle.domain.conversationsassistant.abstraction;

import com.tydeya.familycircle.data.conversation.Conversation;

public interface ConversationsAssistant {

    Conversation getConversationByKey(String key);
}
