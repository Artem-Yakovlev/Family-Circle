package com.tydeya.familycircle.data.conversationsinteractor.abstraction;

public interface ConversationInteractorObservable {

    void subscribe(ConversationInteractorCallback callback);

    void unsubscribe(ConversationInteractorCallback callback);
}
