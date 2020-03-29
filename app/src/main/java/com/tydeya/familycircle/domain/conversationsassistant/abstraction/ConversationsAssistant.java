package com.tydeya.familycircle.domain.conversationsassistant.abstraction;

import com.tydeya.familycircle.data.oldconversation.OldConversation;

public interface ConversationsAssistant {

    OldConversation getConversationByKey(String key);
}
