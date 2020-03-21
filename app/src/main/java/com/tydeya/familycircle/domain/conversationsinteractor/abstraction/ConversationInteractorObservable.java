package com.tydeya.familycircle.domain.conversationsinteractor.abstraction;

public interface ConversationInteractorObservable {

    void subscribe(ConversationInteractorCallback callback);

    void unsubscribe(ConversationInteractorCallback callback);
}
