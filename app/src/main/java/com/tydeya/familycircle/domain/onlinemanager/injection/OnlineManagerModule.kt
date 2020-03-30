package com.tydeya.familycircle.domain.onlinemanager.injection

import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.domain.onlinemanager.details.OnlineInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class OnlineManagerModule {

    @Singleton
    @Provides
    fun provideOnlineInteractor(): OnlineInteractorImpl {
        return OnlineInteractorImpl()
    }
}