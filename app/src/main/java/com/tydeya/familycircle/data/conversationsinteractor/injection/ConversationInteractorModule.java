package com.tydeya.familycircle.data.conversationsinteractor.injection;

import com.tydeya.familycircle.data.conversationsinteractor.details.ConversationInteractor;

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