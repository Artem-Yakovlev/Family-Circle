package com.tydeya.familycircle.data.familyInteractor.injection;

import com.tydeya.familycircle.data.familyInteractor.details.FamilyInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FamilyInteractorModule {

    @Singleton
    @Provides
    FamilyInteractor provideNetworkUtils() {
        return new FamilyInteractor();
    }

}
