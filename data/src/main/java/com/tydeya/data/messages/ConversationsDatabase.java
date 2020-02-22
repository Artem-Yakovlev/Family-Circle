package com.tydeya.data.messages;

import com.tydeya.domain.conversations.Conversation;

import java.util.ArrayList;

public interface ConversationsDatabase {
    ArrayList<Conversation> getConversationsData();
}
