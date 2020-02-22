package com.tydeya.data.messages;

public class ConversationsRepository {
    private ConversationsDatabase conversationsDatabase;

    ConversationsRepository(ConversationsDatabase conversationsDatabase) {
        this.conversationsDatabase = conversationsDatabase;
    }
}
