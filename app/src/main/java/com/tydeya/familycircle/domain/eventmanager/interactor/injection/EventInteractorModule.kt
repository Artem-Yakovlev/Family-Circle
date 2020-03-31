package com.tydeya.familycircle.domain.eventmanager.interactor.injection

import com.tydeya.familycircle.domain.eventmanager.interactor.details.EventInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class EventInteractorModule {

    @Singleton
    @Provides
    fun provideEventInteractor(): EventInteractor {
        return EventInteractor()
    }

}