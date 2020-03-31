package com.tydeya.familycircle.domain.cooperationorganizer.inject

import com.tydeya.familycircle.domain.cooperationorganizer.interactor.details.CooperationInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CooperationModule {

    @Singleton
    @Provides
    fun provideCooperationInteractor(): CooperationInteractor {
        return CooperationInteractor()
    }
}