package com.tydeya.familycircle.domain.messenger.inject

import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MessengerModule {

    @Singleton
    @Provides
    fun provideMessengerInteractor(): MessengerInteractor {
        return MessengerInteractor()
    }
}