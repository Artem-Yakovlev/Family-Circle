package com.tydeya.familycircle.domain.conversationsinteractor.injection;

import com.tydeya.familycircle.domain.conversationsinteractor.details.ConversationInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ConversationInteractorModule {

    @Singleton
    @Provides
    ConversationInteractor provideConversationInteractor() {
        return new ConversationInteractor();
    }
}