package com.tydeya.familycircle.domain.eventreminder.interactor.injection

import com.tydeya.familycircle.domain.eventreminder.interactor.details.EventInteractor
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